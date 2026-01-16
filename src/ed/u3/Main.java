package ed.u3;
import ed.u3.Algoritmos.BFS;
import ed.u3.Algoritmos.DFS;
import java.util.Scanner;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String rutaBase = "src/ed/u3/txt/";

        // bucle para que no se cierre el programa hasta salir
        while (true) {
            System.out.println(Utils.C_AMARILLO + "\n==========================================" + Utils.C_RESET);
            System.out.println(Utils.C_CELESTE + "             GRAFOS (DFS/BFS)              " + Utils.C_RESET);
            System.out.println(Utils.C_AMARILLO + "==========================================" + Utils.C_RESET);
            System.out.println(Utils.C_VERDE + "| ~ 1. DataSet Grafo NO Dirigido" + Utils.C_RESET);
            System.out.println(Utils.C_VERDE + "| ~ 2. Dataset Grafo Dirigido" + Utils.C_RESET);
            System.out.println(Utils.C_VERDE + "| ~ 3. Buscar datasets" + Utils.C_RESET);
            System.out.println(Utils.C_ROJO + "| ~ 4. Salir" + Utils.C_RESET);
            System.out.print(Utils.C_AZUL + "| -  Ingrese una opcion: " + Utils.C_RESET);
            // usa la funcion nueva pa que no truene si meten letras
            int opcion = ingresarNumero(sc);
            // si elige 4 rompe el while y sale del sistema
            if (opcion == 4) {
                System.out.println(Utils.C_ROJO + "\n| ~ Saliendo... Bendiciones Abundantes" + Utils.C_RESET);
                break;
            }
            String archivoSeleccionado = "";
            boolean esDirigido = false;
            // variable para ver si sigue o vuelve al menu por error
            boolean continuarCarga = true;

            switch (opcion) {
                case 1 -> archivoSeleccionado = rutaBase + "g_nodirigido_matriz.txt";
                case 2 -> archivoSeleccionado = rutaBase + "g_dirigido_matriz.txt";
                case 3 -> {
                    // busca los archivos en la carpeta
                    File carpeta = new File(rutaBase);
                    // filtro pa que solo salgan los txt
                    File[] archivosEncontrados = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
                    // si no hay nada o es null
                    if (archivosEncontrados == null || archivosEncontrados.length == 0) {
                        System.out.println(Utils.C_ROJO + "\nAVISO: No se encontraron archivos .txt en la ruta: " + rutaBase + Utils.C_RESET);
                        continuarCarga = false; // marca error
                    } else {
                        System.out.println(Utils.C_AZUL + "\n| -------- Archivos Encontrados -------- |" + Utils.C_RESET);
                        for (int i = 0; i < archivosEncontrados.length; i++) {
                            // muestra la lista de archivos encontrados
                            System.out.println(Utils.C_AMARILLO + "| ~ [" + (i + 1) + "] " + Utils.C_RESET + archivosEncontrados[i].getName());
                        }

                        System.out.print(Utils.C_VERDE + "\n| - Ingrese el numero del archivo: " + Utils.C_RESET);
                        int indice = ingresarNumero(sc);

                        // valida q no ponga cualquier cosa
                        if (indice < 1 || indice > archivosEncontrados.length) {
                            System.out.println(Utils.C_ROJO + "| ~ Ingrese otra opcion" + Utils.C_RESET);
                            continuarCarga = false;
                        } else {
                            // saca el archivo del array
                            File fileElegido = archivosEncontrados[indice - 1];
                            archivoSeleccionado = fileElegido.getAbsolutePath();
                            System.out.println(Utils.C_CELESTE + "| ~ Dataset seleccionado: " + fileElegido.getName() + Utils.C_RESET);

                        }
                    }
                }
                default -> {
                    System.out.println(Utils.C_ROJO + "| ~ Ingrese otra opcion" + Utils.C_RESET);
                    continuarCarga = false;
                }
            }
            // si algo salio mal arriba vuelve al inicio del while
            if (!continuarCarga) {
                continue;
            }
            // revisa si existe el archivo de verdad
            File file = new File(archivoSeleccionado);
            if(!file.exists()) {
                System.out.println(Utils.C_ROJO + "\n| ~ No existe el archivo: " +  archivoSeleccionado + Utils.C_RESET);
                continue; // regresa al menu
            }
            System.out.println(Utils.C_AZUL + "\n| ~ Archivo cargado: " + Utils.C_AMARILLO + archivoSeleccionado + Utils.C_RESET);
            Grafo grafo = CargarGrafo.cargar(archivoSeleccionado);

            if (grafo != null) {
                grafo.mostrarResumen();
                System.out.print(Utils.C_VERDE + "\n| - Ingrese el vertice de origen (0 a " + (grafo.getnVertices() - 1) + "): " + Utils.C_RESET);
                int origen = ingresarNumero(sc);

                if (origen < 0 || origen >= grafo.getnVertices()) {
                    System.out.println(Utils.C_ROJO + "| ~ Vertice fuera del rango" + Utils.C_RESET);
                } else {
                    // ejecuta los algoritmos
                    System.out.println(Utils.C_AMARILLO + "\n| ~ Ejecutando BFS desde nodo: " + origen + "." + Utils.C_RESET);
                    BFS bfs = new BFS();
                    bfs.ejecutar(grafo, origen);

                    System.out.println(Utils.C_AMARILLO + "\n| ~ Ejecutando DFS desde nodo: " + origen + "." + Utils.C_RESET);
                    DFS dfs = new DFS();
                    dfs.ejecutar(grafo, origen);
                }
            }
            // separador para q se vea mejor al repetir el menu
            System.out.println(Utils.C_AZUL + "\n| ---------------------------------------- |" + Utils.C_RESET);
        }
        sc.close();
    }
    // funcion pa validar que ingresen numeros y no letras
    public static int ingresarNumero(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print(Utils.C_ROJO + "| ~ Ingrese un numero: " + Utils.C_RESET);
            sc.next(); // limpia lo que escribio mal
        }
        return sc.nextInt();
    }
}