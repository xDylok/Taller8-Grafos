package ed.u3.Algoritmos;
import ed.u3.Grafo;
import ed.u3.Utils;
import java.util.ArrayList;
import java.util.List;

public class DFS {
    private boolean[] visitado;
    private List<Integer> ordenVisita;
    private boolean hayCiclo;
    private boolean[] enPilaRecursion;

    public List<Integer> ejecutar(Grafo grafo, int origen) {
        int n = grafo.getnVertices();
        visitado = new boolean[n];
        enPilaRecursion = new boolean[n];
        ordenVisita = new ArrayList<>();
        hayCiclo = false;
        // llama al recursivo
        dfsRecursivo(grafo, origen, -1);

        // revisa si quedaron nodos sin visitar por si hay islas
        for (int i = 0; i < n; i++) {
            if (!visitado[i]) {
                dfsRecursivo(grafo, i, -1);
            }
        }
        imprimirResultados();
        return ordenVisita;
    }
    private void dfsRecursivo(Grafo g, int actual, int padre) {
        visitado[actual] = true;
        enPilaRecursion[actual] = true;
        ordenVisita.add(actual);
        // recorre los vecinos
        for (int vecino : g.getAdj().get(actual)) {
            if (!visitado[vecino]) {
                dfsRecursivo(g, vecino, actual);
            } else {
                // logica para detectar ciclos
                if (!g.esDirigido() && vecino != padre) hayCiclo = true;
                if (g.esDirigido() && enPilaRecursion[vecino]) hayCiclo = true;
            }
        }
        enPilaRecursion[actual] = false;
    }
    private void imprimirResultados() {
        String borde = Utils.C_AMARILLO + "------------------------------------------" + Utils.C_RESET;
        System.out.println("\n" + borde);
        System.out.println(Utils.C_CELESTE + "              Resultados dfs              " + Utils.C_RESET);
        System.out.println(borde);
        System.out.println(Utils.C_AMARILLO + "| ~ Orden de visita (ruta):" + Utils.C_RESET);
        System.out.println("|   " + ordenVisita);
        System.out.println();
        // avisa si encontro ciclo o no
        String cicloMsg = hayCiclo ? Utils.C_ROJO + "Si detectado" + Utils.C_RESET
                : Utils.C_VERDE + "No detectado" + Utils.C_RESET;
        System.out.println("| ~ Ciclo en el grafo: " + cicloMsg);
        System.out.println(borde);
    }
}