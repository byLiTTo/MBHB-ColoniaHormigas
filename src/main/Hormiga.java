package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Clase agente que recuerda los nodos que ha recorrido, utilizando una lista de
 * nodos visitados.
 * 
 * Al finalizar dicha lista es la que contiene la solucion.
 * 
 * En cada ciudad S decide a que ciudad R moverse, de las no visitadas. Segun la
 * regla probabilistica de transicion.
 * 
 * Dicha probabilidad depende de la preferencia heuristica y la feromona.
 * 
 * @author Carlos Garcia Silva.
 * 
 */
public class Hormiga {

	// ==============================================================================
	// VARIABLES
	// ==============================================================================

	private int n;
	private int[] visitados;
	private ArrayList<Integer> noVisitados;
	private int coste;
	private Random aleatorio;
	private int[][] feromonasLocales;

	// ==============================================================================
	// CONSTRUCTORES
	// ==============================================================================

	public Hormiga(int n, int inicio, Random aleatorio) {
		this.n = n;
		this.aleatorio = aleatorio;

		visitados = new int[n];
		visitados[0] = inicio;

		noVisitados = new ArrayList<Integer>(n - 1);
		for (int i = 0; i < n; i++) {
			if (i != inicio) {
				noVisitados.add(i);
			}
		}
		Collections.shuffle(noVisitados, aleatorio);
	}

	// ==============================================================================
	// GETs y SETs
	// ==============================================================================

	/**
	 * 
	 * @return Lista de nodos visitados. Al terminar el proceso es la solucion.
	 */
	public int[] getSolucion() {
		return this.visitados;
	}

	public void setCoste(int coste) {
		this.coste = coste;
	}

	public int getCoste() {
		return this.coste;
	}

	public void setFeromonasLocales(int[][] l) {
		this.feromonasLocales = l;
	}

	public int[][] getFeronomonasLocales() {
		return this.feromonasLocales.clone();
	}

	// ==============================================================================
	// METODOS
	// ==============================================================================

	/**
	 * La regla probabilistica de transicion mas habitual define la probabilidad con
	 * la que una hormigua K, situada en la ciudad R, decide moverse hacia la ciudad
	 * S. En este caso la hormiga K es la propia instancia de la clase (this).
	 * 
	 * @param feromonas   Conjunto de arcos de feromonas
	 * @param heuristicas Conjunto de inf. heuristicas de los arcos
	 * @param alfa        Peso que establece el equilibrio entre la importancia
	 *                    memoristica.
	 * @param beta        Peso que establece el equilibrio entre la importancia
	 *                    heuristica.
	 */
	public void AplicaReglaTransicion(double[][] feromonas, double[][] heuristicas,
			double alfa, double beta) {
		for (int r = 0; r < this.n - 1; r++) {
			double denominador = 0;

			for (int u : noVisitados) {
				denominador += Math.pow(feromonas[visitados[r]][u], alfa)
						* Math.pow(heuristicas[visitados[r]][u], beta);
			}

			double aletoriedad = aleatorio.nextDouble();

			// Si existe la division, aplicamos la formula, calculando el numerador
			if (denominador != 0) {
				for (int s = 0; s < noVisitados.size(); s++) {
					double numerador = Math
							.pow(feromonas[visitados[r]][noVisitados.get(s)], alfa)
							* Math.pow(heuristicas[visitados[r]][noVisitados.get(s)],
									beta);

					double probabilidad = numerador / denominador;

					aletoriedad -= probabilidad;

					if (aletoriedad <= 0 || s == noVisitados.size() - 1) {
						visitados[r + 1] = noVisitados.remove(s);
						break;
					}
				}
			}

			// Si no es divisible, no hay feromonas, tomamos una decision aleatoria
			else {
				visitados[r + 1] = noVisitados
						.remove((int) Math.floor(aletoriedad * noVisitados.size()));
			}
		}

	}

	public void AplicaReglaTransicionColonia(double[][] feromonas,
			double[][] heuristicas, double alfa, double beta, double q0) {
		for (int r = 0; r < this.n - 1; r++) {
			int pos = 0;
			double valorMax = 0;
			for (int s = 0; s < noVisitados.size(); s++) {
				double valor = Math.pow(feromonas[visitados[r]][noVisitados.get(s)],
						alfa)
						* Math.pow(heuristicas[visitados[r]][noVisitados.get(s)],
								beta);

				if (valor > valorMax) {
					valorMax = valor;
					pos = s;
				}
			}
			double q = aleatorio.nextDouble();

			// Regla de transicion de SCH
			if (q <= q0) {
				visitados[r + 1] = noVisitados.remove(pos);
				break;
			}
			// Regla de transicion de SH
			else {
				double denominador = 0;

				for (int u : noVisitados) {
					denominador += Math.pow(feromonas[visitados[r]][u], alfa)
							* Math.pow(heuristicas[visitados[r]][u], beta);
				}

				double aletoriedad = aleatorio.nextDouble();

				// Si existe la division, aplicamos la formula, calculando el
				// numerador
				if (denominador != 0) {
					for (int s = 0; s < noVisitados.size(); s++) {
						double numerador = Math.pow(
								feromonas[visitados[r]][noVisitados.get(s)], alfa)
								* Math.pow(heuristicas[visitados[r]][noVisitados
										.get(s)], beta);

						double probabilidad = numerador / denominador;

						aletoriedad -= probabilidad;

						if (aletoriedad <= 0 || s == noVisitados.size() - 1) {
							visitados[r + 1] = noVisitados.remove(s);
							break;
						}
					}
				}

				// Si no es divisible, no hay feromonas, tomamos una decision
				// aleatoria
				else {
					visitados[r + 1] = noVisitados.remove(
							(int) Math.floor(aletoriedad * noVisitados.size()));
				}
			}
		}
	}

	/**
	 * Para Sistema de Hormigas (SH)
	 * 
	 * Incrementa la tabla de aportaciones en las ciudades donde pasa la hormiga.
	 * 
	 * @param aportaciones Aportaciones totales de las hormigas.
	 */
	public void SueltaFeromonaGlobal(double[][] aportaciones) {
		double aporte = 1.0 / coste;

		// Actualizamos todos los arcos posibles con las ciudades que ha visitado
		for (int i = 0; i < visitados.length - 1; i++) {
			aportaciones[visitados[i]][visitados[i + 1]] += aporte;
			aportaciones[visitados[i + 1]][visitados[i]] += aporte;
		}

		// Actualizamos el ultimo arco
		aportaciones[visitados[visitados.length - 1]][visitados[0]] += aporte;
		aportaciones[visitados[0]][visitados[visitados.length - 1]] += aporte;
	}

}
