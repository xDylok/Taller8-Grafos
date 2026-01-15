package ed.u3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Grafo {
    private int nVertices;
    private boolean dirigido;
    private List<List<Integer>> adj;
    // constructor q inicializa las listas vacias
    public Grafo(int nVertices, boolean dirigido) {
        this.nVertices = nVertices;
        this.dirigido = dirigido;
        this.adj = new ArrayList<>();
        // crea una lista para cada vertice
        for (int i = 0; i < nVertices; i++) {
            adj.add(new ArrayList<>());
        }
    }
    public void agregarArista(int origen, int destino) {
        // valida que no se salga del rango
        if (origen >= nVertices || destino >= nVertices || origen < 0 || destino < 0) return;

        // chequea si ya existe pa no repetir
        if (!adj.get(origen).contains(destino)) {
            adj.get(origen).add(destino);
        }
        // si no es dirigido agrega la vuelta tambien
        if (!dirigido) {
            if (!adj.get(destino).contains(origen)) {
                adj.get(destino).add(origen);
            }
        }
    }
    public int getnVertices() { return nVertices; }
    public List<List<Integer>> getAdj() { return adj; }
    public boolean esDirigido() { return dirigido; }

    public void mostrarResumen() {
        // imprime el encabezado con colores
        System.out.println(Utils.C_AMARILLO + "\n| ====================================== |" + Utils.C_RESET);
        System.out.println(Utils.C_CELESTE + "            Informacion del grafo         " + Utils.C_RESET);
        System.out.println(Utils.C_AMARILLO + "| ====================================== |" + Utils.C_RESET);

        System.out.println(Utils.C_VERDE + "| ~ Tipo: " + Utils.C_RESET + (dirigido ? "Dirigido" : "No dirigido"));
        System.out.println(Utils.C_VERDE + "| ~ Vertices: " + Utils.C_RESET + nVertices);

        System.out.println(Utils.C_AMARILLO + "\n| ------ Lista de adyacencia ------ |" + Utils.C_RESET);
        // recorre y muestra las conexiones
        for (int i = 0; i < nVertices; i++) {
            List<Integer> vecinos = adj.get(i);
            Collections.sort(vecinos);
            System.out.println(Utils.C_CELESTE + "| ~ Vertice: [" + i + "]" + Utils.C_RESET +
                    Utils.C_ROJO + " -> " + Utils.C_RESET + vecinos);
        }
        // si es dirigido muestra quien llega a quien
        if (dirigido) {
            System.out.println(Utils.C_VERDE + "\n| ------ Nodos entrantes ------ |" + Utils.C_RESET);
            for (int i = 0; i < nVertices; i++) {
                List<Integer> entrantes = new ArrayList<>();
                // busca quien apunta a i
                for (int j = 0; j < nVertices; j++) {
                    if (adj.get(j).contains(i)) {
                        entrantes.add(j);
                    }
                }
                Collections.sort(entrantes);
                System.out.println(Utils.C_CELESTE + "| ~ Vertice: [" + i + "]" + Utils.C_RESET +
                        Utils.C_VERDE + " <- " + Utils.C_RESET + entrantes);
            }
        }
        System.out.println(Utils.C_AMARILLO + "| ====================================== |" + Utils.C_RESET);
    }
}