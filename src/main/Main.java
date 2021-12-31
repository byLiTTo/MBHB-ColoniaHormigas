/**
 * 
 */
package main;

import estructuras.Fichero;

/**
 * @author Carlos Garcia Silva
 *
 */
public class Main {

	private static String[] ficheros = { 
			"ch130.tsp",
			"a280.tsp"
	};

	public static void main(String[] args) {
		try {
			Fichero datos;
			Cerebro cerebro;
			for (int i = 0; i < ficheros.length; i++) {
				datos = new Fichero(ficheros[i]);
				System.out.println(
						"##################################################");
				System.out.println("   RESULTADOS PARA EL FICHERO " + ficheros[i]);
				System.out.println(
						"##################################################");
				System.out.println("");
				
				cerebro = new Cerebro(datos.getN(), datos.getD(),
						datos.getHeuristicas());
				cerebro.run();
				
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
