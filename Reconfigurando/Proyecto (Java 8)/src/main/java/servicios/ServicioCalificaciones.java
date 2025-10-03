/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import entidades.Calificacion;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.ArchivoUtil;

/**
 *
 * @author BRYAN
 */
public class ServicioCalificaciones {
    private final List<Calificacion> calificaciones = new ArrayList<>();
    private static final String ARCHIVO = "calificaciones.txt";

    public ServicioCalificaciones() {
        cargarCalificaciones();
    }

    public void cargarCalificaciones() {
        calificaciones.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 5) {
                    Calificacion c = new Calificacion(
                        partes[0], partes[1], partes[2],
                        Double.parseDouble(partes[3]), partes[4]
                    );
                    calificaciones.add(c);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar calificaciones: " + e.getMessage());
        }
    }

    public boolean registrarCalificacion(String codigoCurso, String dniEstudiante, 
                                       String fecha, double nota, String observaciones) {
        
        // Validar nota
        if (nota < 0 || nota > 20) {
            System.out.println("La nota debe estar entre 0 y 20");
            return false;
        }

        Calificacion c = new Calificacion(codigoCurso, dniEstudiante, fecha, nota, observaciones);
        calificaciones.add(c);
        ArchivoUtil.guardarCalificacion(c, ARCHIVO);
        System.out.println("Calificación registrada exitosamente");
        return true;
    }

    public List<Calificacion> obtenerCalificacionesPorEstudiante(String dniEstudiante) {
        List<Calificacion> resultados = new ArrayList<>();
        for (Calificacion c : calificaciones) {
            if (c.getDniEstudiante().equals(dniEstudiante)) {
                resultados.add(c);
            }
        }
        return resultados;
    }

    public List<Calificacion> obtenerCalificacionesPorCurso(String codigoCurso) {
        List<Calificacion> resultados = new ArrayList<>();
        for (Calificacion c : calificaciones) {
            if (c.getCodigoCurso().equals(codigoCurso)) {
                resultados.add(c);
            }
        }
        return resultados;
    }

    public Double obtenerPromedioEstudiante(String dniEstudiante) {
        List<Calificacion> califs = obtenerCalificacionesPorEstudiante(dniEstudiante);
        if (califs.isEmpty()) return null;
        
        double suma = 0;
        for (Calificacion c : califs) {
            suma += c.getNota();
        }
        return suma / califs.size();
    }

    public Double obtenerPromedioCurso(String codigoCurso) {
        List<Calificacion> califs = obtenerCalificacionesPorCurso(codigoCurso);
        if (califs.isEmpty()) return null;
        
        double suma = 0;
        for (Calificacion c : califs) {
            suma += c.getNota();
        }
        return suma / califs.size();
    }

    public Map<String, Integer> obtenerEstadisticasCurso(String codigoCurso) {
        List<Calificacion> califs = obtenerCalificacionesPorCurso(codigoCurso);
        Map<String, Integer> stats = new HashMap<>();
        stats.put("Aprobados", 0);
        stats.put("Desaprobados", 0);
        stats.put("Total", califs.size());

        for (Calificacion c : califs) {
            if (c.getNota() >= 11) {
                stats.put("Aprobados", stats.get("Aprobados") + 1);
            } else {
                stats.put("Desaprobados", stats.get("Desaprobados") + 1);
            }
        }
        return stats;
    }

    public List<Calificacion> obtenerTodasCalificaciones() {
        return new ArrayList<>(calificaciones);
    }

    public int getTotalCalificaciones() {
        return calificaciones.size();
    }
}
