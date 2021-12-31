/**
 * 
 */
package algoritmos.OCH;

import algoritmos.Algoritmo;
import algoritmos.voraces.Greedy;
import estructuras.Solucion;

/**
 * Clase que implementa el algoritmo de optimizacion SISTEMA DE HORMIGAS
 * ELITISTA.
 * 
 * @author Carlos Garcia Silva
 *
 */
public class SHE extends Algoritmo {

	// ==============================================================================
	// VARIABLES
	// ==============================================================================

	private int m = 10; // Hormigas

	private int e = 15; // Hormigas elitistas

	private double alfa = 1;
	private double beta = 2;
	private double p = 0.1;

	// ==============================================================================
	// METODOS PRIVADOS
	// ==============================================================================

	private void ActualizaFeromonas(int n, double[][] aportaciones) {
		for (int r = 0; r < n; r++) {
			for (int s = r + 1; s < n; s++) {
				feromonas[r][s] = (1.0 - p) * feromonas[r][s] + aportaciones[r][s];
				feromonas[s][r] = (1.0 - p) * feromonas[s][r] + aportaciones[s][r];
			}
		}
	}

	// ==============================================================================
	// METODOS PUBLICOS
	// ==============================================================================

	/**
	 * Para Sistema de Hormigas Elitista (SHE)
	 * 
	 * Incrementa la tabla de aportaciones en las ciudades donde pasa la hormiga.
	 * 
	 */
	public double[][] SueltaFeromona(int n, double[][] aportaciones, long coste,
			int[] visitados) {
		double aporte = 1.0 / coste;

		// Actualizamos todos los arcos posibles con las ciudades que ha visitado
		for (int i = 0; i < visitados.length - 1; i++) {
			aportaciones[visitados[i]][visitados[i + 1]] += e * aporte;
			aportaciones[visitados[i + 1]][visitados[i]] += e * aporte;
		}

		// Actualizamos el ultimo arco
		aportaciones[visitados[n - 1]][visitados[0]] += e * aporte;
		aportaciones[visitados[0]][visitados[n - 1]] += e * aporte;

		return aportaciones;
	}

	// ==============================================================================
	// SOBRECARGADOS
	// ==============================================================================

	@Override
	public Solucion Ejecuta(int n, int[][] D, double[][] heuristicas) {

		Greedy greedy = new Greedy();
		Solucion exploracion = greedy.Ejecuta(n, D, heuristicas);
		numEvaluaciones++;

		double rastro = 1.0 / exploracion.getCoste();

		feromonas = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i != j) {
					feromonas[i][j] = rastro;
				}
			}
		}

		Solucion global = new Solucion(new int[] {}, Long.MAX_VALUE, 0);

		
		long limite = System.currentTimeMillis() + 10 * 60000;
		while(System.currentTimeMillis() < limite) {
		//while (numEvaluaciones < 10000 * n) {
			IniciaHormiguero(n, m);

			for (int i = 0; i < m; i++) {
				hormiguero[i].AplicaReglaTransicion(feromonas, heuristicas, alfa,
						beta);
			}

			for (int i = 0; i < m; i++) {
				hormiguero[i].setCoste(
						FuncionEvaluacion(n, D, hormiguero[i].getSolucion()));
			}

			double[][] aportaciones = new double[n][n];
			for (int i = 0; i < m; i++) {
				hormiguero[i].SueltaFeromonaGlobal(aportaciones);
			}

			int pos = getMejorHormiga();

			if (hormiguero[pos].getCoste() < global.getCoste()) {
				global.Actualiza(hormiguero[pos].getSolucion().clone(),
						hormiguero[pos].getCoste(), 1);
			}

			costes.add(global.getCoste());

			aportaciones = SueltaFeromona(n, aportaciones, global.getCoste(),
					global.getS());

			ActualizaFeromonas(n, aportaciones);

		}

		GeneraGrafica("SHE_poblacion_" + n);

		return new Solucion(global.getS(), global.getCoste(), getEvaluaciones());
	}

}
