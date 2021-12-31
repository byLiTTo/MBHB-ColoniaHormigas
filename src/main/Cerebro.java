/**
 * 
 */
package main;

import java.util.Locale;

import algoritmos.Algoritmo;
import algoritmos.OCH.*;
import algoritmos.voraces.*;
import estructuras.Solucion;;

/**
 * @author Carlos Garcia Silva
 *
 */
public class Cerebro {

	// ==============================================================================
	// VARIABLES
	// ==============================================================================

	private int n;
	private int[][] D;
	private double[][] heuristicas;

	private int[] semillas = { 
			840612802, 
			1314640371, 
			1227221349, 
			342269428,
			1603221416, 
			//796392706,
			// 2116959593,
			// 2020525633,
			// 532496463,
			// 1841414609,
	};

	private Algoritmo[] algoritmos = {
			new Greedy(),
			//new MultiGreedy(),
			new SH(),
			new SHE(),
			new SCH(),
	};

	private String[] nombres = {
			"Greedy",
			//"MultiGreedy",
			"Sistema de Hormigas",
			"Sistema de Hormigas Elitista",
			"Sistema de Colonia de Hormigas",
	};

	// ==============================================================================
	// CONTRUCTORES
	// ==============================================================================

	public Cerebro(int n, int[][] D, double[][] heuristicas) {
		this.n = n;
		this.D = D;
		this.heuristicas = heuristicas;
	}

	// ==============================================================================
	// METODOS
	// ==============================================================================

	public void run() {
		int ejecuciones = this.semillas.length;
		int casos = this.algoritmos.length;

		long[] costes = new long[ejecuciones];
		int[] evaluaciones = new int[ejecuciones];

		Algoritmo algoritmo;
		Solucion solucion;

		for (int i = 0; i < casos; i++) {
			System.out.println("Resultados de ejecutar " + this.nombres[i]);

			algoritmo = this.algoritmos[i];

			if (!algoritmo.monoEjecucion()) {
				System.out.println("\t\tCostes" + "\tEvaluaciones" + "\tTiempo");

				long sumatorioCostes = 0;
				long sumatorioEvaluaciones = 0;

				for (int j = 0; j < ejecuciones; j++) {
					algoritmo.setSemilla(this.semillas[j]);

					long tiempoINI = System.currentTimeMillis();
					solucion = algoritmo.Ejecuta(this.n, D, heuristicas);
					long tiempoFIN = System.currentTimeMillis();

					costes[j] = solucion.getCoste();
					evaluaciones[j] = solucion.getEvaluaciones();

					System.out.println("Ejecucion " + (j + 1) + ":\t" + costes[j]
							+ "\t" + evaluaciones[j] + "\t     " + "\t"
							+ (tiempoFIN - tiempoINI) + " ms");

					sumatorioCostes += costes[j];
					sumatorioEvaluaciones += evaluaciones[j];
				}

				double mediaCostes = ((double) sumatorioCostes) / ejecuciones;
				double mediaEvaluaciones = ((double) sumatorioEvaluaciones)
						/ ejecuciones;

				System.out.printf(Locale.US, "\nMedia costes: %.2f\n", mediaCostes);
				System.out.printf(Locale.US, "\nMedia evaluaciones: %.2f\n",
						mediaEvaluaciones);

				sumatorioCostes = 0;
				sumatorioEvaluaciones = 0;

				for (int j = 0; j < ejecuciones; j++) {
					sumatorioCostes += Math.pow(mediaCostes - costes[j], 2);
					sumatorioEvaluaciones += Math
							.pow(mediaEvaluaciones - evaluaciones[j], 2);
				}

				double desviacionCostes = Math.sqrt(sumatorioCostes / ejecuciones);
				double desviacionEvaluaciones = Math
						.sqrt(sumatorioEvaluaciones / ejecuciones);

				System.out.printf(Locale.US, "\nDesviacion costes: %.2f\n",
						desviacionCostes);
				System.out.printf(Locale.US, "\nDesviacion evaluaciones: %.2f\n",
						desviacionEvaluaciones);
			} else {
				algoritmo.setSemilla(this.semillas[0]);

				long tiempoINI = System.currentTimeMillis();
				solucion = algoritmo.Ejecuta(n, D, heuristicas);
				long tiempoFIN = System.currentTimeMillis();

				System.out.println("Coste: " + solucion.getCoste());
				System.out.println("Evaluaciones: " + solucion.getEvaluaciones());
				System.out.println("Tiempo: " + (tiempoFIN - tiempoINI + " ms"));
			}

			System.out.println(
					"\n---------------------------------------------------");
		}
	}
}
