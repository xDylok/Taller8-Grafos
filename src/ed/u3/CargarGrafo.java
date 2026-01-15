package ed.u3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CargarGrafo {

    public static Grafo cargar(String rutaArchivo, boolean esDirigido) {
        Grafo grafo = null;
        List<String[]> lineasLeidas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            // lee todo el archivo linea poir linea
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] partes = linea.trim().split("[\\s,]+");
                lineasLeidas.add(partes);
            }
            if (lineasLeidas.isEmpty()) return null;
            // cuenta las filas pa saber el tamaño
            int nroVertices = lineasLeidas.size();
            grafo = new Grafo(nroVertices, esDirigido);
            // recorre la matriz que guardo en memoria
            for (int i = 0; i < nroVertices; i++) {
                String[] fila = lineasLeidas.get(i);
                for (int j = 0; j < fila.length; j++) {
                    try {
                        int valor = Integer.parseInt(fila[j]);
                        // si hay un 1 es que hay conexion
                        if (valor == 1) {
                            if (esDirigido) {
                                grafo.agregarArista(i, j);
                            } else {
                                // pa no duplicar en no dirigidos
                                if (j >= i) grafo.agregarArista(i, j);
                            }
                        }
                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        // ignora errores raros
                    }
                }
            }
            System.out.println(Utils.C_VERDE + "| ~ Grafo cargado (" + nroVertices + " vertices)" + Utils.C_RESET);
        } catch (IOException e) {
            System.err.println(Utils.C_ROJO + "| [Error] Leyendo archivo: " + e.getMessage() + Utils.C_RESET);
        }
        return grafo;
    }
}