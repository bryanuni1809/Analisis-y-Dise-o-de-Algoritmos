/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import entidades.Profesor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import util.ArchivoUtil;

/**
 *
 * @author BRYAN
 */
public class ServicioProfesores {
    private final List<Profesor> profesores = new ArrayList<>();
    private static final String ARCHIVO = "profesores.txt";

    public ServicioProfesores() {
        cargarProfesores();
    }

    public void cargarProfesores() {
        profesores.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 8) {
                    Profesor p = new Profesor(
                        partes[0], partes[1], partes[2], partes[3],
                        partes[4], partes[5], partes[6], 
                        Integer.parseInt(partes[7])
                    );
                    profesores.add(p);
                }
            }
        } catch (IOException e) {
            System.out.println("⚠️  No se pudo cargar profesores: " + e.getMessage());
        }
    }

    public boolean registrarProfesor(String dni, String nombres, String apellidos, 
                                   String direccion, String telefono, String correo,
                                   String especialidad, int experiencia) {
        
        // Verificar si ya existe
        for (Profesor p : profesores) {
            if (p.getDni().equals(dni)) {
                System.out.println("❌ El profesor con DNI " + dni + " ya existe");
                return false;
            }
        }

        Profesor p = new Profesor(dni, nombres, apellidos, direccion, telefono, correo, especialidad, experiencia);
        profesores.add(p);
        ArchivoUtil.guardarProfesor(p, ARCHIVO);
        System.out.println("✅ Profesor registrado exitosamente");
        return true;
    }

    public Profesor buscarProfesor(String dni) {
        for (Profesor p : profesores) {
            if (p.getDni().equals(dni)) {
                return p;
            }
        }
        return null;
    }

    public List<Profesor> buscarProfesoresPorEspecialidad(String especialidad) {
        List<Profesor> resultados = new ArrayList<>();
        for (Profesor p : profesores) {
            if (p.getEspecialidad().toLowerCase().contains(especialidad.toLowerCase())) {
                resultados.add(p);
            }
        }
        return resultados;
    }

    public boolean modificarProfesor(String dni, String nuevaEspecialidad, Integer nuevaExperiencia) {
        Profesor p = buscarProfesor(dni);
        if (p == null) {
            System.out.println("Profesor no encontrado");
            return false;
        }

        if (nuevaEspecialidad != null) p.setEspecialidad(nuevaEspecialidad);
        if (nuevaExperiencia != null) p.setExperiencia(nuevaExperiencia);

        ArchivoUtil.sobrescribirProfesores(profesores, ARCHIVO);
        System.out.println("Profesor modificado exitosamente");
        return true;
    }

    public boolean eliminarProfesor(String dni) {
        for (int i = 0; i < profesores.size(); i++) {
            if (profesores.get(i).getDni().equals(dni)) {
                profesores.remove(i);
                ArchivoUtil.sobrescribirProfesores(profesores, ARCHIVO);
                System.out.println("✅ Profesor eliminado exitosamente");
                return true;
            }
        }
        System.out.println("❌ Profesor no encontrado");
        return false;
    }

    public List<Profesor> obtenerTodosProfesores() {
        return new ArrayList<>(profesores);
    }

    public boolean existeProfesor(String dni) {
        return buscarProfesor(dni) != null;
    }

    public int getTotalProfesores() {
        return profesores.size();
    }

    List<Profesor> obtenerTodosProfesores() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
