/**
 * 
 */
package estructuras;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Carlos Garcia Silva
 *
 */
public class Fichero {

	// ==============================================================================
	// VARIABLES
	// ==============================================================================

	private int n;
	private int[][] D;
	private double[][] heuristicas;

	// ==============================================================================
	// CONSTRUCTORES
	// ==============================================================================

	public Fichero(String nombre) throws IOException {
		FileReader f = new FileReader(nombre);
		BufferedReader bf = new BufferedReader(f);

		bf.readLine(); // NAME
		bf.readLine(); // COMMENT
		bf.readLine(); // TYPE

		// DIMENSION
		this.n = Integer.valueOf(eliminaEspacios(bf.readLine()).split(" ")[1]);
		this.D = new int[n][n];
		this.heuristicas = new double[n][n];

		bf.readLine(); // EDGE_WEIGHT_TYPE
		bf.readLine(); // NODE_COORD_SECTION

		String[] linea;
		double[] X = new double[n];
		double[] Y = new double[n];
		for (int i = 0; i < n; i++) {
			linea = eliminaEspacios(bf.readLine()).split(" ");
			// Double.valueOf(linea[0]) Corresponde con el id de la ciudad
			X[i] = Double.valueOf(linea[1]);
			Y[i] = Double.valueOf(linea[2]);
		}

		double Xi;
		double Yi;
		int distancia;
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				Xi = X[i] - X[j];
				Yi = Y[i] - Y[j];
				distancia = (int) Math
						.round(Math.sqrt(Math.pow(Xi, 2) + Math.pow(Yi, 2)));
				D[i][j] = distancia;
				D[j][i] = distancia;

				if (distancia != 0) {
					heuristicas[i][j] = 1.0 / distancia;
					heuristicas[j][i] = 1.0 / distancia;
				} else {
					heuristicas[i][j] = 10;
					heuristicas[j][i] = 10;
				}
			}
		}

		for (int i = 0; i < n; i++) {
			D[i][i] = Integer.MAX_VALUE;
		}

	}

	// ==============================================================================
	// GETs y SETs
	// ==============================================================================

	public int getN() {
		return this.n;
	}

	public int[][] getD() {
		int[][] aux = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				aux[i][j] = this.D[i][j];
			}
		}

		return aux;
	}

	public double[][] getHeuristicas() {
		double[][] aux = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				aux[i][j] = this.heuristicas[i][j];
			}
		}

		return aux;
	}

	// ==============================================================================
	// METODOS
	// ==============================================================================

	private String eliminaEspacios(String s) {
		return (s.trim()).replaceAll(" +", " ");
	}

}
