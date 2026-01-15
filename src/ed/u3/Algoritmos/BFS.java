package ed.u3.Algoritmos;
import ed.u3.Grafo;
import ed.u3.Utils;
import java.util.*;

public class BFS {

    public List<Integer> ejecutar(Grafo grafo, int nodoInicio) {
        int n = grafo.getnVertices();
        // usa una cola pal recorrido
        Queue<Integer> cola = new ArrayDeque<>();
        boolean[] visitado = new boolean[n];
        int[] dist = new int[n];
        int[] padre = new int[n];
        List<Integer> ordenVisita = new ArrayList<>();
        // pone todo en -1 al inicio
        for (int i = 0; i < n; i++) {
            dist[i] = -1;
            padre[i] = -1;
        }
        // inicializa el origen
        visitado[nodoInicio] = true;
        dist[nodoInicio] = 0;
        cola.add(nodoInicio);
        // bucle mientras haya cosas en la cola
        while (!cola.isEmpty()) {
            int actual = cola.poll();
            ordenVisita.add(actual);
            // revisa los vecinos del nodo actual
            for (int vecino : grafo.getAdj().get(actual)) {
                if (!visitado[vecino]) {
                    visitado[vecino] = true;
                    // calcula la distancia sumando 1
                    dist[vecino] = dist[actual] + 1;
                    padre[vecino] = actual;
                    cola.add(vecino);
                }
            }
        }
        // muestra la tabla fea
        imprimirResultados(ordenVisita, dist);
        return ordenVisita;
    }
    private void imprimirResultados(List<Integer> orden, int[] dist) {
        String borde = Utils.C_AMARILLO + "-------------------------------------------------------" + Utils.C_RESET;
        System.out.println();
        System.out.println(borde);
        System.out.println(Utils.C_CELESTE + "          Tabla bfs (dist[] y orden)           " + Utils.C_RESET);
        System.out.println(borde);
        // formato de columnas
        System.out.printf(Utils.C_AZUL + "| " + Utils.C_AMARILLO + "%-10s " + Utils.C_AZUL + "| " +
                        Utils.C_AMARILLO + "%-10s " + Utils.C_AZUL + "| " +
                        Utils.C_AMARILLO + "%-15s " + Utils.C_AZUL + "|\n" + Utils.C_RESET,
                "Vertice", "Distancia", "Orden");

        System.out.println(borde);
        for (int i = 0; i < dist.length; i++) {
            String dStr;
            // si la distancia sigue siendo -1 (el valor original)
            if (dist[i] == -1) {
                dStr = Utils.C_ROJO + "inf" + Utils.C_RESET;
            } else {
                // Si no es -1, muestra el número real ej: "2"
                dStr = String.valueOf(dist[i]);
            }
            String ordenStr = (i < orden.size()) ? String.valueOf(orden.get(i)) : "-";
            System.out.printf(Utils.C_AZUL + "| " + Utils.C_RESET + "%-10s " +
                            Utils.C_AZUL + "| " + Utils.C_RESET + "%-19s " +
                            Utils.C_AZUL + "| " + Utils.C_RESET + "%-15s " + Utils.C_AZUL + "|\n" + Utils.C_RESET,
                    i, dStr, ordenStr);
        }
        System.out.println(borde);
        System.out.println(Utils.C_VERDE + "| ~ Orden final: " + Utils.C_RESET + orden);
        System.out.println(borde);
    }
}