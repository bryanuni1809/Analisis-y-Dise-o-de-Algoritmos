/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import entidades.Curso;
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
public class ServicioCursos {
    private final List<Curso> cursos = new ArrayList<>();
    private static final String ARCHIVO = "cursos.txt";

    public ServicioCursos() {
        cargarCursos();
    }

    public void cargarCursos() {
        cursos.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 10) {
                    Curso c = new Curso(
                        partes[0], partes[1], partes[2], partes[3],
                        partes[4], partes[5], Integer.parseInt(partes[6]),
                        Integer.parseInt(partes[7]), Double.parseDouble(partes[8]),
                        partes[9]
                    );
                    cursos.add(c);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar cursos: " + e.getMessage());
        }
    }

    public boolean registrarCurso(String codigo, String nombre, String idioma, String nivel,
                                String profesorDni, String horario, int duracion, 
                                int capacidad, double precio, String observaciones) {
        
        // Verificar si el código ya existe
        for (Curso c : cursos) {
            if (c.getCodigo().equals(codigo)) {
                System.out.println("El curso con código " + codigo + " ya existe");
                return false;
            }
        }

        Curso c = new Curso(codigo, nombre, idioma, nivel, profesorDni, horario, 
                           duracion, capacidad, precio, observaciones);
        cursos.add(c);
        ArchivoUtil.guardarCurso(c, ARCHIVO);
        System.out.println("Curso registrado exitosamente");
        return true;
    }

    public Curso buscarCurso(String codigo) {
        for (Curso c : cursos) {
            if (c.getCodigo().equals(codigo)) {
                return c;
            }
        }
        return null;
    }

    public List<Curso> buscarCursosPorIdioma(String idioma) {
        List<Curso> resultados = new ArrayList<>();
        for (Curso c : cursos) {
            if (c.getIdioma().equalsIgnoreCase(idioma)) {
                resultados.add(c);
            }
        }
        return resultados;
    }

    public List<Curso> obtenerCursosDisponibles() {
        return new ArrayList<>(cursos);
    }

    public boolean modificarCurso(String codigo, String nuevoHorario, Integer nuevaCapacidad, String nuevasObservaciones) {
        Curso c = buscarCurso(codigo);
        if (c == null) {
            System.out.println("Curso no encontrado");
            return false;
        }

        if (nuevoHorario != null) c.setHorario(nuevoHorario);
        if (nuevaCapacidad != null) c.setCapacidadMaxima(nuevaCapacidad);
        if (nuevasObservaciones != null) c.setObservaciones(nuevasObservaciones);

        ArchivoUtil.sobrescribirCursos((ArrayList<Curso>) cursos, ARCHIVO);
        System.out.println("✅ Curso modificado exitosamente");
        return true;
    }

    public boolean eliminarCurso(String codigo) {
        for (int i = 0; i < cursos.size(); i++) {
            if (cursos.get(i).getCodigo().equals(codigo)) {
                cursos.remove(i);
                ArchivoUtil.sobrescribirCursos((ArrayList<Curso>) cursos, ARCHIVO);
                System.out.println("✅ Curso eliminado exitosamente");
                return true;
            }
        }
        System.out.println("❌ Curso no encontrado");
        return false;
    }

    public List<Curso> obtenerTodosCursos() {
        return new ArrayList<>(cursos);
    }

    public boolean existeCurso(String codigo) {
        return buscarCurso(codigo) != null;
    }

    public int getTotalCursos() {
        return cursos.size();
    }
}

