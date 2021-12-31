/**
 * 
 */
package estructuras;

/**
 * @author Carlos Garcia Silva
 *
 */
public class Solucion {

	// ==============================================================================
	// VARIABLES
	// ==============================================================================

	private int[] S;
	private long coste;
	private int numEvaluaciones;

	// ==============================================================================
	// CONSTRUCTORES
	// ==============================================================================

	public Solucion(int[] S, long coste, int ejecuciones) {
		this.S = S.clone();
		this.coste = coste;
		this.numEvaluaciones = ejecuciones;
	}

	// ==============================================================================
	// GETs y SETs
	// ==============================================================================

	public int[] getS() {
		return this.S;
	}

	public long getCoste() {
		return this.coste;
	}

	public void setCoste(long coste) {
		this.coste = coste;
	}

	public int getEvaluaciones() {
		return this.numEvaluaciones;
	}

	// ==============================================================================
	// METODOS
	// ==============================================================================

	public void Actualiza(int[] S, long coste, int ejecuciones) {
		this.S = S.clone();
		this.coste = coste;
		this.numEvaluaciones = ejecuciones;
	}

	public boolean repetidos() {
		for (int i = 0; i < S.length; i++) {
			for (int j = i + 1; j < S.length; j++) {
				if (S[i] == S[j]) {
					return true;
				}
			}
		}
		return false;
	}

	// ==============================================================================
	// SOBRECARGADOS
	// ==============================================================================

	@Override
	public boolean equals(Object o) {
		if (o.getClass() == Solucion.class) {
			int[] aux = ((Solucion) o).S;
			for (int i = 0; i < S.length; i++) {
				if (S[i] != aux[i]) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

}
