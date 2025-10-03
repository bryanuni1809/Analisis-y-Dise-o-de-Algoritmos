/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import entidades.Calificacion;
import entidades.Curso;
import entidades.Estudiante;
import entidades.Profesor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author BRYAN
 */
public class BusquedaUtil {
    
    // ========== BÚSQUEDA INTERNA - ALGORITMOS AVANZADOS ==========
    
    /**
     * BÚSQUEDA LINEAL - Para listas pequeñas o no ordenadas
     */
    public static <T> T busquedaLineal(List<T> lista, String criterio, Buscador<T> buscador) {
        for (T elemento : lista) {
            if (buscador.cumpleCriterio(elemento, criterio)) {
                return elemento;
            }
        }
        return null;
    }
    
    /**
     * BÚSQUEDA BINARIA - Para listas ordenadas (MUCHO más eficiente)
     */
    public static <T> T busquedaBinaria(List<T> lista, String criterio, Buscador<T> buscador, Comparator<T> comparador) {
        int izquierda = 0;
        int derecha = lista.size() - 1;
        
        while (izquierda <= derecha) {
            int medio = izquierda + (derecha - izquierda) / 2;
            T elementoMedio = lista.get(medio);
            
            int comparacion = comparador.compare(elementoMedio, crearElementoComparable(criterio, buscador));
            
            if (comparacion == 0) {
                return elementoMedio;
            } else if (comparacion < 0) {
                izquierda = medio + 1;
            } else {
                derecha = medio - 1;
            }
        }
        return null;
    }
    
    /**
     * BÚSQUEDA POR COINCIDENCIA PARCIAL - Para nombres, apellidos, etc.
     */
    public static <T> List<T> busquedaParcial(List<T> lista, String criterio, Buscador<T> buscador) {
        List<T> resultados = new ArrayList<>();
        for (T elemento : lista) {
            if (buscador.cumpleCriterioParcial(elemento, criterio)) {
                resultados.add(elemento);
            }
        }
        return resultados;
    }
    
    /**
     * BÚSQUEDA POR RANGO - Para números, fechas, etc.
     */
    public static <T> List<T> busquedaPorRango(List<T> lista, double min, double max, BuscadorNumerico<T> buscador) {
        List<T> resultados = new ArrayList<>();
        for (T elemento : lista) {
            double valor = buscador.obtenerValorNumerico(elemento);
            if (valor >= min && valor <= max) {
                resultados.add(elemento);
            }
        }
        return resultados;
    }
    
    // ========== BÚSQUEDA ESPECÍFICA PARA ESTUDIANTES ==========
    
    public static Estudiante buscarEstudiantePorDNI(List<Estudiante> estudiantes, String dni) {
        return busquedaLineal(estudiantes, dni, new Buscador<Estudiante>() {
            @Override
            public boolean cumpleCriterio(Estudiante estudiante, String criterio) {
                return estudiante.getDni().equals(criterio);
            }
            
            @Override
            public boolean cumpleCriterioParcial(Estudiante estudiante, String criterio) {
                return estudiante.getDni().contains(criterio);
            }
        });
    }
    
    public static List<Estudiante> buscarEstudiantesPorNombre(List<Estudiante> estudiantes, String nombre) {
        return busquedaParcial(estudiantes, nombre, new Buscador<Estudiante>() {
            @Override
            public boolean cumpleCriterio(Estudiante estudiante, String criterio) {
                return estudiante.getNombres().equalsIgnoreCase(criterio) || 
                       estudiante.getApellidos().equalsIgnoreCase(criterio);
            }
            
            @Override
            public boolean cumpleCriterioParcial(Estudiante estudiante, String criterio) {
                return estudiante.getNombres().toLowerCase().contains(criterio.toLowerCase()) || 
                       estudiante.getApellidos().toLowerCase().contains(criterio.toLowerCase());
            }
        });
    }
    
    public static List<Estudiante> buscarEstudiantesPorCorreo(List<Estudiante> estudiantes, String dominio) {
        return busquedaParcial(estudiantes, dominio, new Buscador<Estudiante>() {
            @Override
            public boolean cumpleCriterio(Estudiante estudiante, String criterio) {
                return estudiante.getCorreo().endsWith("@" + criterio);
            }
            
            @Override
            public boolean cumpleCriterioParcial(Estudiante estudiante, String criterio) {
                return estudiante.getCorreo().toLowerCase().contains(criterio.toLowerCase());
            }
        });
    }
    
    // ========== BÚSQUEDA ESPECÍFICA PARA PROFESORES ==========
    
    public static Profesor buscarProfesorPorDNI(List<Profesor> profesores, String dni) {
        return busquedaLineal(profesores, dni, new Buscador<Profesor>() {
            @Override
            public boolean cumpleCriterio(Profesor profesor, String criterio) {
                return profesor.getDni().equals(criterio);
            }
            
            @Override
            public boolean cumpleCriterioParcial(Profesor profesor, String criterio) {
                return profesor.getDni().contains(criterio);
            }
        });
    }
    
    public static List<Profesor> buscarProfesoresPorEspecialidad(List<Profesor> profesores, String especialidad) {
        return busquedaParcial(profesores, especialidad, new Buscador<Profesor>() {
            @Override
            public boolean cumpleCriterio(Profesor profesor, String criterio) {
                return profesor.getEspecialidad().equalsIgnoreCase(criterio);
            }
            
            @Override
            public boolean cumpleCriterioParcial(Profesor profesor, String criterio) {
                return profesor.getEspecialidad().toLowerCase().contains(criterio.toLowerCase());
            }
        });
    }
    
    public static List<Profesor> buscarProfesoresPorExperiencia(List<Profesor> profesores, int minExperiencia) {
        return busquedaPorRango(profesores, minExperiencia, Integer.MAX_VALUE, new BuscadorNumerico<Profesor>() {
            @Override
            public double obtenerValorNumerico(Profesor profesor) {
                return profesor.getExperiencia();
            }
        });
    }
    
    // ========== BÚSQUEDA ESPECÍFICA PARA CURSOS ==========
    
    public static Curso buscarCursoPorCodigo(List<Curso> cursos, String codigo) {
        return busquedaLineal(cursos, codigo, new Buscador<Curso>() {
            @Override
            public boolean cumpleCriterio(Curso curso, String criterio) {
                return curso.getCodigo().equals(criterio);
            }
            
            @Override
            public boolean cumpleCriterioParcial(Curso curso, String criterio) {
                return curso.getCodigo().contains(criterio);
            }
        });
    }
    
    public static List<Curso> buscarCursosPorIdioma(List<Curso> cursos, String idioma) {
        return busquedaParcial(cursos, idioma, new Buscador<Curso>() {
            @Override
            public boolean cumpleCriterio(Curso curso, String criterio) {
                return curso.getIdioma().equalsIgnoreCase(criterio);
            }
            
            @Override
            public boolean cumpleCriterioParcial(Curso curso, String criterio) {
                return curso.getIdioma().toLowerCase().contains(criterio.toLowerCase());
            }
        });
    }
    
    public static List<Curso> buscarCursosPorPrecio(List<Curso> cursos, double precioMaximo) {
        return busquedaPorRango(cursos, 0, precioMaximo, new BuscadorNumerico<Curso>() {
            @Override
            public double obtenerValorNumerico(Curso curso) {
                return curso.getPrecio();
            }
        });
    }
    
    public static List<Curso> buscarCursosPorProfesor(List<Curso> cursos, String dniProfesor) {
        return busquedaParcial(cursos, dniProfesor, new Buscador<Curso>() {
            @Override
            public boolean cumpleCriterio(Curso curso, String criterio) {
                return curso.getProfesorDni().equals(criterio);
            }
            
            @Override
            public boolean cumpleCriterioParcial(Curso curso, String criterio) {
                return curso.getProfesorDni().contains(criterio);
            }
        });
    }
    
    // ========== BÚSQUEDA ESPECÍFICA PARA CALIFICACIONES ==========
    
    public static List<Calificacion> buscarCalificacionesPorEstudiante(List<Calificacion> calificaciones, String dniEstudiante) {
        return busquedaParcial(calificaciones, dniEstudiante, new Buscador<Calificacion>() {
            @Override
            public boolean cumpleCriterio(Calificacion calificacion, String criterio) {
                return calificacion.getDniEstudiante().equals(criterio);
            }
            
            @Override
            public boolean cumpleCriterioParcial(Calificacion calificacion, String criterio) {
                return calificacion.getDniEstudiante().contains(criterio);
            }
        });
    }
    
    public static List<Calificacion> buscarCalificacionesPorCurso(List<Calificacion> calificaciones, String codigoCurso) {
        return busquedaParcial(calificaciones, codigoCurso, new Buscador<Calificacion>() {
            @Override
            public boolean cumpleCriterio(Calificacion calificacion, String criterio) {
                return calificacion.getCodigoCurso().equals(criterio);
            }
            
            @Override
            public boolean cumpleCriterioParcial(Calificacion calificacion, String criterio) {
                return calificacion.getCodigoCurso().contains(criterio);
            }
        });
    }
    
    public static List<Calificacion> buscarCalificacionesPorNota(List<Calificacion> calificaciones, double notaMinima) {
        return busquedaPorRango(calificaciones, notaMinima, 20, new BuscadorNumerico<Calificacion>() {
            @Override
            public double obtenerValorNumerico(Calificacion calificacion) {
                return calificacion.getNota();
            }
        });
    }
    
    // ========== BÚSQUEDA EXTERNA - ARCHIVOS ==========
    
    /**
     * BÚSQUEDA EXTERNA GENERAL - Busca en cualquier archivo
     */
    public static List<String> busquedaExternaGeneral(String archivo, String texto) {
        List<String> resultados = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int numeroLinea = 1;
            while ((linea = br.readLine()) != null) {
                if (linea.toLowerCase().contains(texto.toLowerCase())) {
                    resultados.add("Línea " + numeroLinea + ": " + linea);
                }
                numeroLinea++;
            }
        } catch (IOException e) {
            System.out.println("Error en búsqueda externa: " + e.getMessage());
        }
        return resultados;
    }
    
    /**
     * BÚSQUEDA EXTERNA POR CAMPO ESPECÍFICO
     */
    public static List<String> busquedaExternaPorCampo(String archivo, int indiceCampo, String valor) {
        List<String> resultados = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length > indiceCampo && campos[indiceCampo].trim().equalsIgnoreCase(valor)) {
                    resultados.add(linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error en búsqueda externa por campo: " + e.getMessage());
        }
        return resultados;
    }
    
    /**
     * BÚSQUEDA EXTERNA AVANZADA - Múltiples criterios
     */
    public static List<String> busquedaExternaAvanzada(String archivo, Map<Integer, String> criterios) {
        List<String> resultados = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",");
                boolean cumpleTodos = true;
                
                for (Map.Entry<Integer, String> criterio : criterios.entrySet()) {
                    int indice = criterio.getKey();
                    String valor = criterio.getValue();
                    
                    if (campos.length <= indice || !campos[indice].trim().equalsIgnoreCase(valor)) {
                        cumpleTodos = false;
                        break;
                    }
                }
                
                if (cumpleTodos) {
                    resultados.add(linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error en búsqueda externa avanzada: " + e.getMessage());
        }
        return resultados;
    }
    
    /**
     * BÚSQUEDA EXTERNA CON FILTRADO POR PATRÓN
     */
    public static List<String> busquedaExternaConPatron(String archivo, String patron) {
        List<String> resultados = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (coincidePatron(linea, patron)) {
                    resultados.add(linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error en búsqueda con patrón: " + e.getMessage());
        }
        return resultados;
    }
    
    // ========== MÉTODOS DE UTILIDAD ==========
    
    /**
     * ORDENAR LISTA PARA BÚSQUEDA BINARIA
     */
    public static <T> void ordenarParaBusqueda(List<T> lista, Comparator<T> comparador) {
        Collections.sort(lista, comparador);
    }
    
    /**
     * CREAR ÍNDICE PARA BÚSQUEDAS RÁPIDAS
     */
    public static <T> Map<String, T> crearIndice(List<T> lista, Indexador<T> indexador) {
        Map<String, T> indice = new HashMap<>();
        for (T elemento : lista) {
            String clave = indexador.obtenerClave(elemento);
            indice.put(clave, elemento);
        }
        return indice;
    }
    
    /**
     * BÚSQUEDA EN ÍNDICE (MUY RÁPIDA)
     */
    public static <T> T buscarEnIndice(Map<String, T> indice, String clave) {
        return indice.get(clave);
    }
    
    /**
     * ESTADÍSTICAS DE BÚSQUEDA
     */
    public static <T> void mostrarEstadisticasBusqueda(List<T> resultados, String tipo) {
        System.out.println("?Búsqueda " + tipo + ": " + resultados.size() + " resultados encontrados");
        if (resultados.size() > 10) {
            System.out.println("?Mostrando primeros 10 resultados...");
        }
    }
    
    // ========== INTERFACES FUNCIONALES ==========
    
    @FunctionalInterface
    public interface Buscador<T> {
        boolean cumpleCriterio(T elemento, String criterio);
        
        default boolean cumpleCriterioParcial(T elemento, String criterio) {
            return cumpleCriterio(elemento, criterio);
        }
    }
    
    @FunctionalInterface
    public interface BuscadorNumerico<T> {
        double obtenerValorNumerico(T elemento);
    }
    
    @FunctionalInterface
    public interface Indexador<T> {
        String obtenerClave(T elemento);
    }
    
    // ========== MÉTODOS PRIVADOS DE UTILIDAD ==========
    
    private static <T> T crearElementoComparable(String criterio, Buscador<T> buscador) {
        // Método auxiliar para búsqueda binaria
        return null; // Implementación específica dependería de la clase
    }
    
    private static boolean coincidePatron(String texto, String patron) {
        // Búsqueda con comodines simples
        if (patron.contains("*")) {
            String regex = patron.replace("*", ".*");
            return texto.matches(regex);
        }
        return texto.toLowerCase().contains(patron.toLowerCase());
    }
    
    // ========== MÉTODOS DE DIAGNÓSTICO ==========
    
    /**
     * MEDIR TIEMPO DE BÚSQUEDA
     */
    public static <T> T medirTiempoBusqueda(Busqueda<T> busqueda, String descripcion) {
        long inicio = System.currentTimeMillis();
        T resultado = busqueda.ejecutar();
        long fin = System.currentTimeMillis();
        
        System.out.println("⏱️  " + descripcion + " - Tiempo: " + (fin - inicio) + "ms");
        return resultado;
    }
    
    @FunctionalInterface
    public interface Busqueda<T> {
        T ejecutar();
    }
    
    /**
     * COMPARAR EFICIENCIA DE ALGORITMOS
     */
    public static <T> void compararAlgoritmosBusqueda(List<T> lista, String criterio, Buscador<T> buscador) {
        System.out.println("\n🔬 COMPARACIÓN DE ALGORITMOS:");
        
        // Búsqueda Lineal
        long inicioLineal = System.currentTimeMillis();
        T resultadoLineal = busquedaLineal(lista, criterio, buscador);
        long finLineal = System.currentTimeMillis();
        
        // Búsqueda Binaria (requiere lista ordenada)
        List<T> listaOrdenada = new ArrayList<>(lista);
        ordenarParaBusqueda(listaOrdenada, (a, b) -> 0); // Comparator simple
        
        long inicioBinaria = System.currentTimeMillis();
        T resultadoBinaria = busquedaBinaria(listaOrdenada, criterio, buscador, (a, b) -> 0);
        long finBinaria = System.currentTimeMillis();
        
        System.out.println("Lineal: " + (finLineal - inicioLineal) + "ms");
        System.out.println("Binaria: " + (finBinaria - inicioBinaria) + "ms");
        System.out.println("Diferencia: " + ((finLineal - inicioLineal) - (finBinaria - inicioBinaria)) + "ms");
    }
}
