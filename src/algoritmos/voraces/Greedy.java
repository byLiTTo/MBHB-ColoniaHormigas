/**
 * 
 */
package algoritmos.voraces;

import java.util.ArrayList;

import algoritmos.Algoritmo;
import estructuras.Solucion;

/**
 * @author Carlos Garcia Silva
 *
 */
public class Greedy extends Algoritmo {

	@Override
	public boolean monoEjecucion() {
		return true;
	}

	@Override
	public Solucion Ejecuta(int n, int[][] D, double[][] heuristicas) {
		int[] visitados = new int[n];
		ArrayList<Integer> noVisitados = new ArrayList<Integer>(n - 1);

		for (int i = 1; i < n; i++) {
			noVisitados.add(i);
		}
		
		for (int i = 0; i < n - 1; i++) {
			int distancia = Integer.MAX_VALUE;
			int posicion = 0;
			for (int j = 0; j < noVisitados.size(); j++) {
				if (D[visitados[i]][noVisitados.get(j)] < distancia) {
					distancia = D[visitados[i]][noVisitados.get(j)];
					posicion = j;
				}
			}
			visitados[i + 1] = noVisitados.remove(posicion);
		}

		return new Solucion(visitados, FuncionEvaluacion(n, D, visitados),
				getEvaluaciones());
	}

}
