/**
 * 
 */
package algoritmos.OCH;

import algoritmos.Algoritmo;
import algoritmos.voraces.Greedy;
import estructuras.Solucion;

/**
 * @author Carlos Garcia Silva
 *
 */
public class SCH extends Algoritmo {

	// ==============================================================================
	// VARIABLES
	// ==============================================================================

	private int m = 10;

	private double alfa = 1;
	private double beta = 2;
	private double p = 0.1;
	private double q0 = 0.98;
	private double fi = 0.1;

	// ==============================================================================
	// METODOS PRIVADOS
	// ==============================================================================

	public double[][] SueltaFeromonasGlobal(int n, long coste, int[] visitados) {
		double[][] aportaciones = new double[n][n];
		double aporte = 1.0 / coste;

		// Actualizamos todos los arcos posibles con las ciudades que ha visitado
		for (int i = 0; i < visitados.length - 1; i++) {
			aportaciones[visitados[i]][visitados[i + 1]] += p * aporte;
			aportaciones[visitados[i + 1]][visitados[i]] += p * aporte;
		}

		// Actualizamos el ultimo arco
		aportaciones[visitados[n - 1]][visitados[0]] += p * aporte;
		aportaciones[visitados[0]][visitados[n - 1]] += p * aporte;

		return aportaciones;
	}

	private void ActualizaFeromonasGlobal(int n, double[][] aportaciones) {
		for (int r = 0; r < n; r++) {
			for (int s = r + 1; s < n; s++) {
				feromonas[r][s] = (1.0 - p) * feromonas[r][s] + aportaciones[r][s];
				feromonas[s][r] = (1.0 - p) * feromonas[s][r] + aportaciones[s][r];
			}
		}
	}

	public double[][] SueltaFeromonasLocal(int n, int coste, int[] visitados) {
		double[][] aportaciones = new double[n][n];
		double aporte = (1.0 / m * coste);

		// Actualizamos todos los arcos posibles con las ciudades que ha visitado
		for (int i = 0; i < visitados.length - 1; i++) {
			aportaciones[visitados[i]][visitados[i + 1]] += fi * aporte;
			aportaciones[visitados[i + 1]][visitados[i]] += fi * aporte;
		}

		// Actualizamos el ultimo arco
		aportaciones[visitados[n - 1]][visitados[0]] += p * aporte;
		aportaciones[visitados[0]][visitados[n - 1]] += p * aporte;

		return aportaciones;
	}

	private void ActualizaFeromonasLocal(int n, double[][] aportaciones, long C) {
		for (int r = 0; r < n; r++) {
			for (int s = r + 1; s < n; s++) {
				feromonas[r][s] = (1.0 - fi) * feromonas[r][s] + aportaciones[r][s];
				feromonas[s][r] = (1.0 - fi) * feromonas[s][r] + aportaciones[r][s];
			}
		}
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
		while (System.currentTimeMillis() < limite) {
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
				aportaciones = SueltaFeromonasGlobal(n, hormiguero[i].getCoste(),
						hormiguero[i].getSolucion());
				ActualizaFeromonasLocal(n, aportaciones, hormiguero[i].getCoste());
			}

			// ActualizaFeromonas(n, aportaciones);

			int pos = getMejorHormiga();

			if (hormiguero[pos].getCoste() < global.getCoste()) {
				global.Actualiza(hormiguero[pos].getSolucion().clone(),
						hormiguero[pos].getCoste(), 1);
			}

			aportaciones = SueltaFeromonasGlobal(n, global.getCoste(),
					global.getS());
			ActualizaFeromonasGlobal(n, aportaciones);

			costes.add(global.getCoste());
		}

		GeneraGrafica("SCH_poblacion_" + n);

		return new Solucion(global.getS(), global.getCoste(), getEvaluaciones());
	}

}
