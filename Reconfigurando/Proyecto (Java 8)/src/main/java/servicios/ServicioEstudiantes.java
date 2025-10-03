/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import entidades.Estudiante;
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
public class ServicioEstudiantes {
    private final Map<String, Estudiante> estudiantes = new HashMap<>();
    private static final String ARCHIVO = "estudiantes.txt";

    public ServicioEstudiantes() {
        cargarEstudiantes();
    }

    public void cargarEstudiantes() {
        estudiantes.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 8) {
                    Estudiante e = new Estudiante(
                        partes[0], partes[1], partes[2], partes[3],
                        partes[4], partes[5], partes[6], partes[7]
                    );
                    estudiantes.put(e.getDni(), e);
                }
            }
        } catch (IOException e) {
            System.out.println("⚠No se pudo cargar estudiantes: " + e.getMessage());
        }
    }

    public boolean registrarEstudiante(String dni, String nombres, String apellidos, 
                                     String direccion, String telefono, String correo,
                                     String fechaNac, String nivel) {
        if (estudiantes.containsKey(dni)) {
            System.out.println("El estudiante con DNI " + dni + " ya existe");
            return false;
        }

        Estudiante e = new Estudiante(dni, nombres, apellidos, direccion, telefono, correo, fechaNac, nivel);
        estudiantes.put(dni, e);
        ArchivoUtil.guardarEstudiante(e, ARCHIVO);
        System.out.println("Estudiante registrado exitosamente");
        return true;
    }

    public Estudiante buscarEstudiante(String dni) {
        return estudiantes.get(dni);
    }

    public List<Estudiante> buscarEstudiantesPorNombre(String nombre) {
        List<Estudiante> resultados = new ArrayList<>();
        for (Estudiante e : estudiantes.values()) {
            if (e.getNombres().toLowerCase().contains(nombre.toLowerCase()) || 
                e.getApellidos().toLowerCase().contains(nombre.toLowerCase())) {
                resultados.add(e);
            }
        }
        return resultados;
    }

    public boolean modificarEstudiante(String dni, String nuevaDireccion, String nuevoTelefono, String nuevoNivel) {
        Estudiante e = estudiantes.get(dni);
        if (e == null) {
            System.out.println("Estudiante no encontrado");
            return false;
        }

        if (nuevaDireccion != null) e.setDireccion(nuevaDireccion);
        if (nuevoTelefono != null) e.setTelefono(nuevoTelefono);
        if (nuevoNivel != null) e.setNivelEstudios(nuevoNivel);

        ArchivoUtil.sobrescribirEstudiantes(new ArrayList<>(estudiantes.values()), ARCHIVO);
        System.out.println("Estudiante modificado exitosamente");
        return true;
    }

    public boolean eliminarEstudiante(String dni) {
        if (!estudiantes.containsKey(dni)) {
            System.out.println("Estudiante no encontrado");
            return false;
        }

        estudiantes.remove(dni);
        ArchivoUtil.sobrescribirEstudiantes(new ArrayList<>(estudiantes.values()), ARCHIVO);
        System.out.println("Estudiante eliminado exitosamente");
        return true;
    }

    public List<Estudiante> obtenerTodosEstudiantes() {
        return new ArrayList<>(estudiantes.values());
    }

    public boolean existeEstudiante(String dni) {
        return estudiantes.containsKey(dni);
    }

    public int getTotalEstudiantes() {
        return estudiantes.size();
    }
}
