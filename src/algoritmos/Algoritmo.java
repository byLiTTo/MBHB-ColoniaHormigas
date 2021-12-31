/**
 * 
 */
package algoritmos;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import estructuras.Solucion;
import main.Hormiga;

/**
 * El problema del Viajante de Comercio (TSP) es uno de los problemas de
 * optimizacion combinatoria mas conocidos. En su formulacion mas general, dadas
 * una serie de ciudades, el objetivo consiste en encontrar el circuito de menor
 * coste que parta de una ciudad concreta, pase por todas las demas una sola vez
 * y retorne a la ciudad de origen.
 * 
 * @author Carlos Garcia Silva
 *
 */
public abstract class Algoritmo {

	// ==============================================================================
	// VARIABLES
	// ==============================================================================

	protected int semilla;
	protected Random aleatorio;
	protected Hormiga[] hormiguero;
	protected double[][] feromonas;
	protected ArrayList<Long> costes = new ArrayList<Long>();
	protected int numEvaluaciones = 0;

	// ==============================================================================
	// CONSTRUCTORES
	// ==============================================================================

	public Algoritmo() {
		this.aleatorio = new Random(24357);
	}

	// ==============================================================================
	// GETs y SETs
	// ==============================================================================

	public void setSemilla(int seed) {
		this.semilla = seed;
		this.aleatorio = new Random(seed);
	}

	public int getMejorHormiga() {
		int coste = Integer.MAX_VALUE;
		int pos = 0;

		for (int i = 0; i < hormiguero.length; i++) {
			if (hormiguero[i].getCoste() < coste) {
				coste = hormiguero[i].getCoste();
				pos = i;
			}
		}
		return pos;
	}

	public int getEvaluaciones() {
		int aux = this.numEvaluaciones;
		this.numEvaluaciones = 0;
		return aux;
	}

	// ==============================================================================
	// METODOS PROTEGIDOS
	// ==============================================================================

	protected void IniciaHormiguero(int n, int m) {
		this.hormiguero = new Hormiga[m];
		for (int i = 0; i < m; i++) {
			this.hormiguero[i] = new Hormiga(n, aleatorio.nextInt(n), aleatorio);
		}
	}

	protected void GeneraGrafica(String nombre) {
		try {
			Files.deleteIfExists(Paths.get(nombre + ".dat"));
			FileWriter file = new FileWriter(nombre, false);

			for (long i : costes) {
				file.write(i + "\n");
			}
			file.close();
			costes.clear();

			Runtime.getRuntime()
					.exec(new String[] { "python", "Plotear.py", nombre });
		} catch (Exception e) {
			System.out.println("No se ha podido generar la grafica: " + nombre);
		}
	}

	// ==============================================================================
	// METODOS PUBLICOS
	// ==============================================================================

	public int FuncionEvaluacion(int n, int[][] D, int[] S) {
		this.numEvaluaciones++;
		int sumatorio = 0;
		for (int i = 1; i < n; i++) {
			sumatorio += D[S[i - 1]][S[i]];
		}
		sumatorio += D[S[n - 1]][S[0]];

		return sumatorio;
	}

	public abstract Solucion Ejecuta(int n, int[][] D, double[][] heuristicas);

	public void VisualizaFeromonas() {
		int n = feromonas[0].length;
		for (int r = 0; r < n; r++) {
			for (int s = 0; s < n; s++) {
				System.out.print(feromonas[r][s] + "\t");
			}
			System.out.println("");
		}
	}

	public boolean monoEjecucion() {
		return false;
	}

}
