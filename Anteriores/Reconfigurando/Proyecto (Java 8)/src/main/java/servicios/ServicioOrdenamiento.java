/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import entidades.Curso;
import entidades.Estudiante;
import entidades.ExternalSort;
import entidades.Ordenaciones;
import entidades.Profesor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author BRYAN
 */
public class ServicioOrdenamiento {
    public List<Estudiante> ordenarEstudiantes(List<Estudiante> estudiantes, String criterio) {
        List<Estudiante> lista = new ArrayList<>(estudiantes);
        
        switch (criterio.toLowerCase()) {
            case "apellidos":
                Ordenaciones.ordenar(lista, Comparator.comparing(Estudiante::getApellidos));
                break;
            case "nombres":
                Ordenaciones.ordenar(lista, Comparator.comparing(Estudiante::getNombres));
                break;
            case "dni":
                Ordenaciones.ordenar(lista, Comparator.comparing(Estudiante::getDni));
                break;
            default:
                throw new IllegalArgumentException("Criterio no válido: " + criterio);
        }
        
        return lista;
    }

    public List<Profesor> ordenarProfesores(List<Profesor> profesores, String criterio) {
        List<Profesor> lista = new ArrayList<>(profesores);
        
        switch (criterio.toLowerCase()) {
            case "apellidos":
                Ordenaciones.ordenar(lista, Comparator.comparing(Profesor::getApellidos));
                break;
            case "especialidad":
                Ordenaciones.ordenar(lista, Comparator.comparing(Profesor::getEspecialidad));
                break;
            case "experiencia":
                Ordenaciones.ordenar(lista, Comparator.comparingInt(Profesor::getExperiencia).reversed());
                break;
            default:
                throw new IllegalArgumentException("Criterio no válido: " + criterio);
        }
        
        return lista;
    }

    public List<Curso> ordenarCursos(List<Curso> cursos, String criterio) {
        List<Curso> lista = new ArrayList<>(cursos);
        
        switch (criterio.toLowerCase()) {
            case "nombre":
                Ordenaciones.ordenar(lista, Comparator.comparing(Curso::getNombre));
                break;
            case "idioma":
                Ordenaciones.ordenar(lista, Comparator.comparing(Curso::getIdioma));
                break;
            case "precio":
                Ordenaciones.ordenar(lista, Comparator.comparingDouble(Curso::getPrecio));
                break;
            default:
                throw new IllegalArgumentException("Criterio no válido: " + criterio);
        }
        
        return lista;
    }

    public boolean ordenarArchivoExterno(String archivoOrigen, String archivoDestino, int tamanoBloque) {
        try {
            File entrada = new File(archivoOrigen);
            File salida = new File(archivoDestino);
            
            if (!entrada.exists()) {
                System.out.println("El archivo " + archivoOrigen + " no existe");
                return false;
            }
            
            ExternalSort.externalSort(entrada, salida, tamanoBloque);
            System.out.println("Archivo ordenado: " + archivoDestino);
            return true;
            
        } catch (IOException e) {
            System.out.println("Error en ordenación externa: " + e.getMessage());
            return false;
        }
    }

    public void mostrarPreviewArchivo(String archivo, int lineas) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            System.out.println("\n=== VISTA PREVIA (" + lineas + " líneas) ===");
            String linea;
            int count = 0;
            while ((linea = br.readLine()) != null && count < lineas) {
                System.out.println((count + 1) + ". " + linea);
                count++;
            }
            if (count == lineas) {
                System.out.println("... (archivo continúa)");
            }
        } catch (IOException e) {
            System.out.println("Error al leer archivo: " + e.getMessage());
        }
    }
}
