/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import entidades.Matricula;
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
public class ServicioMatriculas {
    private final List<Matricula> matriculas = new ArrayList<>();
    private static final String ARCHIVO = "matriculas.txt";

    public ServicioMatriculas() {
        cargarMatriculas();
    }

    public void cargarMatriculas() {
        matriculas.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 4) {
                    Matricula m = new Matricula(
                        partes[0], partes[1], partes[2], 
                        Double.parseDouble(partes[3])
                    );
                    matriculas.add(m);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar matrículas: " + e.getMessage());
        }
    }

    public boolean registrarMatricula(String codigoCurso, String dniEstudiante, String fecha, double monto) {
        Matricula m = new Matricula(codigoCurso, dniEstudiante, fecha, monto);
        matriculas.add(m);
        ArchivoUtil.guardarMatricula(m, ARCHIVO);
        System.out.println("Matrícula registrada exitosamente");
        return true;
    }

    public List<Matricula> obtenerMatriculasPorEstudiante(String dniEstudiante) {
        List<Matricula> resultados = new ArrayList<>();
        for (Matricula m : matriculas) {
            if (m.getDniEstudiante().equals(dniEstudiante)) {
                resultados.add(m);
            }
        }
        return resultados;
    }

    public List<Matricula> obtenerMatriculasPorCurso(String codigoCurso) {
        List<Matricula> resultados = new ArrayList<>();
        for (Matricula m : matriculas) {
            if (m.getCodigoCurso().equals(codigoCurso)) {
                resultados.add(m);
            }
        }
        return resultados;
    }

    public boolean estaMatriculado(String dniEstudiante, String codigoCurso) {
        for (Matricula m : matriculas) {
            if (m.getDniEstudiante().equals(dniEstudiante) && 
                m.getCodigoCurso().equals(codigoCurso)) {
                return true;
            }
        }
        return false;
    }

    public List<Matricula> obtenerTodasMatriculas() {
        return new ArrayList<>(matriculas);
    }

    public int getTotalMatriculas() {
        return matriculas.size();
    }
}
