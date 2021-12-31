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
public class MultiGreedy extends Algoritmo {

	@Override
	public boolean monoEjecucion() {
		return true;
	}

	@Override
	public Solucion Ejecuta(int n, int[][] D, double[][] heuristicas) {
		Solucion global = new Solucion(new int[] {}, Long.MAX_VALUE, 0);

		for (int k = 0; k < n; k++) {
			int[] visitados = new int[n];
			visitados[0] = k;

			ArrayList<Integer> noVisitados = new ArrayList<Integer>(n - 1);
			for (int i = 0; i < n; i++) {
				if (i != k)
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

			Solucion iteracion = new Solucion(visitados,
					FuncionEvaluacion(n, D, visitados), 1);

			if (iteracion.getCoste() < global.getCoste()) {
				global = iteracion;
			}
		}

		return new Solucion(global.getS(), global.getCoste(), getEvaluaciones());
	}

}
