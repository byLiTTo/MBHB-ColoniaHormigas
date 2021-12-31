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

| | | Algoritmo SH | | |
|:--:|:--:|:--:|:--:|:--:|
| | Dataset Ch130 | | Dataset A280 | |
| Ejecución 1 | 6.478 | 680.061 | 3.084 | 143.481 |
| Ejecución 2 | 6.674 | 663.401 | 3.155 | 142.671 |
| Ejecución 3 | 6.613 | 669.941 | 3.079 | 139.971 |
| Ejecución 4 | 6.529 | 668.451 | 3.079 | 143.461 |
| Ejecución 5 | 6.513 | 679.861 | 3.113 | 143.431 |
| Media | 6.561,40 | 672.343,00 | 3.102,00 | 142.603,00 |
| Desviación | 71,67 | 6.587,33 | 29,36 | 1.350,90 |

