package ed.u3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CargarGrafo {

    public static Grafo cargar(String rutaArchivo) {
        Grafo grafo = null;
        List<String[]> lineasLeidas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            // lee todo el archivo linea por linea
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] partes = linea.trim().split("[\\s,]+");
                lineasLeidas.add(partes);
            }
        } catch (IOException e) {
            System.err.println(Utils.C_ROJO + "| ~ Error: Leyendo archivo: " + e.getMessage() + Utils.C_RESET);
            return null;
        }
        if (lineasLeidas.isEmpty()) return null;
        // cuenta las filas para saber el tamaño
        int nroVertices = lineasLeidas.size();
        int[][] matrizTemp = new int[nroVertices][nroVertices];
        // pasa todo a una matriz temporal de enteros primero
        for (int i = 0; i < nroVertices; i++) {
            String[] fila = lineasLeidas.get(i);
            for (int j = 0; j < fila.length; j++) {
                try {
                    if (j < nroVertices) {
                        matrizTemp[i][j] = Integer.parseInt(fila[j]);
                    }
                } catch (NumberFormatException e) {
                    // ignora errores raros o basura
                    matrizTemp[i][j] = 0;
                }
            }
        }
        // logica para ver si es dirigido o no (chequea simetria)
        boolean detectado = false;
        for (int i = 0; i < nroVertices; i++) {
            for (int j = 0; j < nroVertices; j++) {
                // si la ida es diferente a la vuelta, entonces es dirigido
                if (matrizTemp[i][j] != matrizTemp[j][i]) {
                    detectado = true;
                    break;
                }
            }
            if (detectado) break;
        }
        String tipo = detectado ? "Dirigido (Asimetrico)" : "No Dirigido (Simetrico)";
        System.out.println(Utils.C_CELESTE + "| ~ Auto detectado: " + tipo + Utils.C_RESET);

        grafo = new Grafo(nroVertices, detectado);
        // ahora si llena el grafo de verdad
        for (int i = 0; i < nroVertices; i++) {
            for (int j = 0; j < nroVertices; j++) {
                // si hay un 1 es que hay conexion
                if (matrizTemp[i][j] == 1) {
                    if (detectado) {
                        grafo.agregarArista(i, j);
                    } else {
                        // pars no duplicar en no dirigidos
                        if (j >= i) grafo.agregarArista(i, j);
                    }
                }
            }
        }
        System.out.println(Utils.C_VERDE + "| ~ Grafo cargado (" + nroVertices + " vertices)" + Utils.C_RESET);
        return grafo;
    }
}