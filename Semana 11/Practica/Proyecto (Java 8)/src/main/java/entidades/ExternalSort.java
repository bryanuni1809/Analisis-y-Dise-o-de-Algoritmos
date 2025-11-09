/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author BRYAN
 */
public class ExternalSort {

    // Divide el archivo en bloques, los ordena y guarda temporalmente
    public static List<File> dividirYOrdenar(File archivoEntrada, int tamanoBloque) throws IOException {
        List<File> archivosTemporales = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivoEntrada))) {
            List<String> buffer = new ArrayList<>();
            String linea;

            while ((linea = br.readLine()) != null) {
                buffer.add(linea);
                if (buffer.size() >= tamanoBloque) {
                    archivosTemporales.add(guardarOrdenado(buffer));
                    buffer.clear();
                }
            }

            if (!buffer.isEmpty()) {
                archivosTemporales.add(guardarOrdenado(buffer));
            }
        }
        return archivosTemporales;
    }

    // Guarda un bloque ordenado en un archivo temporal
    private static File guardarOrdenado(List<String> datos) throws IOException {
        datos.sort(Comparator.naturalOrder());
        File temp = File.createTempFile("bloque", ".txt");
        temp.deleteOnExit();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(temp))) {
            for (String linea : datos) {
                bw.write(linea);
                bw.newLine();
            }
        }
        return temp;
    }

    // Mezcla todos los archivos temporales en uno solo ordenado
    public static void mezclarArchivos(List<File> archivos, File archivoSalida) throws IOException {
        PriorityQueue<LineaArchivo> cola = new PriorityQueue<>(Comparator.comparing(LineaArchivo::getLinea));

        // Inicializar la cola con la primera línea de cada archivo
        for (File f : archivos) {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea = br.readLine();
            if (linea != null) {
                cola.add(new LineaArchivo(linea, br));
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoSalida))) {
            while (!cola.isEmpty()) {
                LineaArchivo la = cola.poll();
                bw.write(la.getLinea());
                bw.newLine();

                String siguiente = la.getReader().readLine();
                if (siguiente != null) {
                    cola.add(new LineaArchivo(siguiente, la.getReader()));
                } else {
                    la.getReader().close();
                }
            }
        }
    }

    // Ejecuta el proceso completo de ordenamiento externo
    public static void externalSort(File archivoEntrada, File archivoSalida, int tamanoBloque) throws IOException {
        List<File> archivos = dividirYOrdenar(archivoEntrada, tamanoBloque);
        mezclarArchivos(archivos, archivoSalida);
    }

    // Clase interna auxiliar para manejar pares línea–archivo
    private static class LineaArchivo {
        private final String linea;
        private final BufferedReader reader;

        public LineaArchivo(String linea, BufferedReader reader) {
            this.linea = linea;
            this.reader = reader;
        }

        public String getLinea() {
            return linea;
        }

        public BufferedReader getReader() {
            return reader;
        }
    }

    // Método de prueba
    public static void main(String[] args) throws IOException {
        File entrada = new File("estudiantes.txt");
        File salida = new File("estudiantes_ordenados.txt");

        externalSort(entrada, salida, 1000);

        System.out.println("Archivo ordenado generado: " + salida.getAbsolutePath());
    }
}

