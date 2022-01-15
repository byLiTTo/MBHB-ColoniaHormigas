# Algoritmos de optimización basados en colonias de hormigas
:computer: Modelos Bioinspirados y Heurísticas de Búsqueda.  
:school: Universidad de Huelva.  
:books: Curso 2020/2021.

___

# Introducción
En esta práctica, presentamos un problema de optimización a resolver. En este caso, nos enfrentaremos al conocido problema de optimización del Viajante de Comercio. Para ello, implementaremos algoritmos basados en diferentes estrategias, para finalmente compararlos. Se trata de:
- Algoritmos Greedy, de tipo constructivo y que sigan un criterio simple
- Algoritmos basados en colonias de hormigas, se procederá a obtener caminos cada vez mejores a partir de la información obtenida por todas las hormigas y las rutas que siguieron.   

Finalmente, se realizará un estudio comparativo de los algoritmos implementados ejecutados para los datasets:
- Ch130: Tamaño 130 y coste óptimo de 6.110.
- A280: Tamaño 280 y coste óptimo de 2.579.

___

# Algoritmos
Como ya hemos introducido, hemos implementado una solución para el problema del Viajante de Comercio, se trata de un algoritmo greedy y tres basados en colonias de hormigas.

## Algoritmo Greedy
Una de las opciones es el algoritmo greedy básico, implementado en la clase Greedy en el paquete algoritmos. Éste, toma como base una matriz de distancias formada por las ciudades del grafo, seleccionando una de ellas (hemos optado por la primera, la 0). Desde ahí y consecutivamente, seleccionará la ciudad más cercana de una lista de pendiente por visitar, una vez visitadas todas las ciudades se pasará a calcular el coste de la solución encontrada que no es más que la suma de todos los caminos recorridos para ir de una ciudad a otra.

Tras la ejecución en los datasets facilitados, obtenemos los siguientes resultados:
||Dataset Ch130| Dataset A280 |
|--:|:--:|:--:|
| Coste | 7579 | 3157 |
| Evaluaciones | 1 | 1 |

## Algoritmo de Sistema de Hormigas
El siguiente algoritmo forma parte de un conjunto que da título a esta práctica, algoritmos de optimización basados en colonias de hormigas. Este algoritmo, en particular, simulará el comportamiento que tendrían las hormigas, haciendo uso de dos matrices de igual tamaño, concretamente NxN. Una de ellas representa las feromonas que las hormigas depositan en los arcos entre dos ciudades por las que pasen; la otra matriz representa los costes de un arco entre una ciudad y otra. Cuando mejor sea una solución encontrada por una hormiga, mayor aporte de feromonas dejará en dicho camino.

Los resultados que hemos obtenido en los datasets son los siguientes:

<table style="width:100%">
    <tr>
        <th colspan='5'> Algoritmo SH</th>
    </tr>
    <tr>
        <th></th>
        <th colspan='2'>Dataset Ch130</th>
        <th colspan='2'>Dataset A280</th>
    </tr>
    <tr>
        <th></th>
        <th>Coste</th>
        <th>Evaluaciones</th>
        <th>Coste</th>
        <th>Evaluaciones</th>
    </tr>
    <tr>
        <th>Ejecución 1</th>
        <th>6.478</th>
        <th>680.061</th>
        <th>3.084</th>
        <th>143.481</th>
    </tr>
    <tr>
        <th>Ejecución 2</th>
        <th>6.674</th>
        <th>663.401</th>
        <th>3.155</th>
        <th>142.671</th>
    </tr>
    <tr>
        <th>Ejecución 3</th>
        <th>6.613</th>
        <th>669.941</th>
        <th>3.079</th>
        <th>139.971</th>
    </tr>
    <tr>
        <th>Ejecución 4</th>
        <th>6.529</th>
        <th>668.451</th>
        <th>3.079</th>
        <th>143.461</th>
    </tr>
    <tr>
        <th>Ejecución 5</th>
        <th>6.513</th>
        <th>679.861</th>
        <th>3.113</th>
        <th>143.431</th>
    </tr>
    <tr>
        <th>Media</th>
        <th>6.561,40</th>
        <th>672.343,00</th>
        <th>3.102,00</th>
        <th>142.603,00</th>
    </tr>
    <tr>
        <th>Desviación</th>
        <th>71,67</th>
        <th>6.587,33</th>
        <th>29,36</th>
        <th>1.350,90</th>
    </tr>
</table>

<img src="imagenes/SH_130.tiff" width="400px"/> <img src="imagenes/SH_280.tiff" width="400px"/>

En cuanto a la implementación, como datos a destacar, podemos decir que no hemos hecho uso de la tabla L que se menciona en la teoría, en su lugar hemos creado una clase a modo de Agente, llamada Hormiga. Aquí se encuentra el vector solución que se construye a partir de la solución aleatoria inicial que se le indica en su constructor.   

A modo de optimización y a la hora de estructurar y plantear la programación nos pareció más sencillo, hemos optado por hacer uso de una tabla double NxN en la que cada hormiga suma los aportes, en lugar de para cada arco del grafo, comprobar si la hormiga ha pasado o no.

## Algoritmo de Sistema de Hormigas Elitista
En este algoritmo, se implementa una modificación con respecto al anterior que hace que la mejor ruta realizada hasta el momento, la mejor global, tenga también un aporte en la actualización de las feromonas, con el objetivo de alcanzar una mejor convergencia. Concretamente, aportará como si hubiera sido 15 hormigas que han hecho esa misma ruta.

Los datos obtenidos en los diferentes datasets son:

<table style="width:100%">
    <tr>
        <th colspan='5'> Algoritmo SHE</th>
    </tr>
    <tr>
        <th></th>
        <th colspan='2'>Dataset Ch130</th>
        <th colspan='2'>Dataset A280</th>
    </tr>
    <tr>
        <th></th>
        <th>Coste</th>
        <th>Evaluaciones</th>
        <th>Coste</th>
        <th>Evaluaciones</th>
    </tr>
    <tr>
        <th>Ejecución 1</th>
        <th>6.347</th>
        <th>210.341</th>
        <th>2.745</th>
        <th>106.741</th>
    </tr>
    <tr>
        <th>Ejecución 2</th>
        <th>6.366</th>
        <th>294.401</th>
        <th>2.769</th>
        <th>106.781</th>
    </tr>
    <tr>
        <th>Ejecución 3</th>
        <th>6.287</th>
        <th>231.861</th>
        <th>2.751</th>
        <th>109.411</th>
    </tr>
    <tr>
        <th>Ejecución 4</th>
        <th>6.545</th>
        <th>229.441</th>
        <th>2.652</th>
        <th>92.361</th>
    </tr>
    <tr>
        <th>Ejecución 5</th>
        <th>6.361</th>
        <th>222.181</th>
        <th>2.656</th>
        <th>95.341</th>
    </tr>
    <tr>
        <th>Media</th>
        <th>6.381,20</th>
        <th>237.645,00</th>
        <th>2.714,60</th>
        <th>102.127,00</th>
    </tr>
    <tr>
        <th>Desviación</th>
        <th>86,61</th>
        <th>29.348,42</th>
        <th>50,11</th>
        <th>6.891,01</th>
    </tr>
</table>

<img src="imagenes/SHE_130.tiff" width="400px"/> <img src="imagenes/SHE_280.tiff" width="400px"/>

## Algoritmo de Sistema de Colonia de Hormigas
Se podría decir que es una mejora o extensión del anterior algoritmo, sistema de hormigas. Se distingue de éste en tres aspectos: Se establece un equilibrio entre exploración y explotación en la regla probabilística de transición. Para la actualización global de la feromona solo se considera la hormiga con mejor resultado y solo se evaporan los arcos por donde ha pasado ésta. Se añade una actualización de la feromona local, es decir, en cada hormiga, consiguiendo así diversificación en la búsqueda.

Los resultados de este algoritmo en los datasets son:

<table style="width:100%">
    <tr>
        <th colspan='5'> Algoritmo SCE</th>
    </tr>
    <tr>
        <th></th>
        <th colspan='2'>Dataset Ch130</th>
        <th colspan='2'>Dataset A280</th>
    </tr>
    <tr>
        <th></th>
        <th>Coste</th>
        <th>Evaluaciones</th>
        <th>Coste</th>
        <th>Evaluaciones</th>
    </tr>
    <tr>
        <th>Ejecución 1</th>
        <th>6.355</th>
        <th>150.561</th>
        <th>2.753</th>
        <th>43.031</th>
    </tr>
    <tr>
        <th>Ejecución 2</th>
        <th>6.344</th>
        <th>159.361</th>
        <th>2.723</th>
        <th>42.601</th>
    </tr>
    <tr>
        <th>Ejecución 3</th>
        <th>6.497</th>
        <th>150.111</th>
        <th>2.694</th>
        <th>43.001</th>
    </tr>
    <tr>
        <th>Ejecución 4</th>
        <th>6.381</th>
        <th>152.141</th>
        <th>2.859</th>
        <th>44.331</th>
    </tr>
    <tr>
        <th>Ejecución 5</th>
        <th>6.359</th>
        <th>256.241</th>
        <th>2.718</th>
        <th>41.891</th>
    </tr>
    <tr>
        <th>Media</th>
        <th>6.387,20</th>
        <th>173.683,00</th>
        <th>2.749,40</th>
        <th>42.971,00</th>
    </tr>
    <tr>
        <th>Desviación</th>
        <th>56,20</th>
        <th>41.413,20</th>
        <th>57,91</th>
        <th>794,66</th>
    </tr>
</table>

<img src="imagenes/SCH_130.tiff" width="400px"/> <img src="imagenes/SCH_280.tiff" width="400px"/>

---

# Comparación global de los algoritmos
A continuación, se presentan los resultados obtenidos realizarse cómodamente su comparación.

<table style="width:100%">
    <tr>
        <th colspan='5'> Dataset Ch130</th>
    </tr>
    <tr>
        <th>Algoritmo</th>
        <th>Mejor</th>
        <th>Media</th>
        <th>Peor</th>
        <th>Desviación</th>
    </tr>
    <tr>
        <th>Óptima</th>
        <th>-</th>
        <th>6.110</th>
        <th>-</th>
        <th>-</th>
    </tr>
    <tr>
        <th>Greedy</th>
        <th>-</th>
        <th>7579</th>
        <th>-</th>
        <th>-</th>
    </tr>
    <tr>
        <th>SH</th>
        <th>6.478</th>
        <th>6.561,40</th>
        <th>6.674</th>
        <th>71,67</th>
    </tr>
    <tr>
        <th>SHE</th>
        <th>6.287</th>
        <th>6.381,20</th>
        <th>6.545</th>
        <th>86,61</th>
    </tr>
    <tr>
        <th>SCH</th>
        <th>6.344</th>
        <th>6.387,20</th>
        <th>6.497</th>
        <th>56,20</th>
    </tr>
</table> <table style="width:100%">
    <tr>
        <th colspan='5'> Dataset A280</th>
    </tr>
    <tr>
        <th>Algoritmo</th>
        <th>Mejor</th>
        <th>Media</th>
        <th>Peor</th>
        <th>Desviación</th>
    </tr>
    <tr>
        <th>Óptima</th>
        <th>-</th>
        <th>2.579</th>
        <th>-</th>
        <th>-</th>
    </tr>
    <tr>
        <th>Greedy</th>
        <th>-</th>
        <th>3157</th>
        <th>-</th>
        <th>-</th>
    </tr>
    <tr>
        <th>SH</th>
        <th>3.079</th>
        <th>3.102,00</th>
        <th>3.155</th>
        <th>29,36</th>
    </tr>
    <tr>
        <th>SHE</th>
        <th>2.652</th>
        <th>2.714,60</th>
        <th>2.769</th>
        <th>50,11</th>
    </tr>
    <tr>
        <th>SCH</th>
        <th>2.694</th>
        <th>2.749,40</th>
        <th>2.859</th>
        <th>57,91</th>
    </tr>
</table>

Una de las primeras conclusiones que podemos sacar de los resultados es sobre el algoritmo greedy, a pesar de estar ante un problema NP, en el que existen una desorbitada cantidad de soluciones posibles, sorprende como la aplicación de una idea tan básica como es la de ir siempre a por la ciudad más cercana, aporta unos resultados más que decentes, aunque no significa que no puedan ser mejorables. Si queremos profundizar e ir hacia soluciones más notables, es necesario aumentar en gran medida el tiempo de cómputo.

Centrándonos en los algoritmos basados en colonias de hormigas, podemos observar como a pesar de variar las semillas, las desviaciones de sus costes son muy reducidas. Con esto, podríamos afirmar que los algoritmos tienen un funcionamiento correcto y cumplen su objetivo independientemente de las condiciones iniciales.

Observando el algoritmo SH, podemos ver como alcanza resultados mejores que greedy, pero no muy dispares, de hecho, en el dataset A280, es bastante cercano. Esto puede deberse al problema de convergencia expuesto en la teoría que suele presentar el algoritmo, que converge lento. Esto se puede confirmar al mirar las gráficas generadas, donde podemos apreciar “escalones” propios de caer en óptimos locales.


Como alternativa a este fallo propio del SH, se implementaron los algoritmos SHE y SCH, que como podemos observar, efectivamente tienen una mejora en comparación al SH. Ambos aportan soluciones próximas a la óptima y entre ellos la media podemos ver como son bastante similares, en el caso de Ch130, podríamos decir que la solución del SCH es más sólida, debido a la poca desviación entre sus soluciones. En cuanto al SHE podríamos decir que encuentra en ejecuciones separadas, mejores óptimos, ya que como podemos apreciar, en ambos dataset el mejor resultado lo tiene SHE, esto podría deberse a una mejor convergencia en ciertas situaciones, ya que SCH busca cierto equilibrio en su regla de transición.

---