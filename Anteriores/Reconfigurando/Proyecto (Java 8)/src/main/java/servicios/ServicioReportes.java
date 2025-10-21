/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import entidades.Calificacion;
import entidades.Curso;
import entidades.Estudiante;
import entidades.Profesor;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author BRYAN
 */
public class ServicioReportes {
    private final ServicioEstudiantes servicioEstudiantes;
    private final ServicioProfesores servicioProfesores;
    private final ServicioCursos servicioCursos;
    private final ServicioMatriculas servicioMatriculas;
    private final ServicioCalificaciones servicioCalificaciones;
    private final ServicioIdiomas servicioIdiomas;

    public ServicioReportes() {
        this.servicioEstudiantes = new ServicioEstudiantes();
        this.servicioProfesores = new ServicioProfesores();
        this.servicioCursos = new ServicioCursos();
        this.servicioMatriculas = new ServicioMatriculas();
        this.servicioCalificaciones = new ServicioCalificaciones();
        this.servicioIdiomas = new ServicioIdiomas();
    }

    public void mostrarMenuReportes() {
        System.out.println("\n=== GENERACIÓN DE REPORTES HTML ===");
        System.out.println("1. 📊 Reporte General del Sistema");
        System.out.println("2. 👥 Reporte de Estudiantes");
        System.out.println("3. 👨‍🏫 Reporte de Profesores");
        System.out.println("4. 📚 Reporte de Cursos");
        System.out.println("5. 📝 Reporte de Matrículas");
        System.out.println("6. 🎯 Reporte de Calificaciones");
        System.out.println("7. 🌍 Reporte de Niveles de Idioma");
        System.out.println("0. ↩️  Volver");
        System.out.print("Seleccione una opción: ");
    }

    public void generarReporteGeneral() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("reporte_general.html"))) {
            bw.write(generarHeader("Reporte General del Sistema"));
            bw.write("<body>");
            bw.write("<h1>📊 REPORTE GENERAL - ACADEMIA MULTILINGUA</h1>");
            
            // Estadísticas generales
            bw.write("<div class='stats'>");
            bw.write("<h2>📈 Estadísticas del Sistema</h2>");
            bw.write("<ul>");
            bw.write("<li><strong>Total Estudiantes:</strong> " + servicioEstudiantes.getTotalEstudiantes() + "</li>");
            bw.write("<li><strong>Total Profesores:</strong> " + servicioProfesores.getTotalProfesores() + "</li>");
            bw.write("<li><strong>Total Cursos:</strong> " + servicioCursos.getTotalCursos() + "</li>");
            bw.write("<li><strong>Total Matrículas:</strong> " + servicioMatriculas.getTotalMatriculas() + "</li>");
            bw.write("<li><strong>Total Calificaciones:</strong> " + servicioCalificaciones.getTotalCalificaciones() + "</li>");
            bw.write("<li><strong>Total Niveles de Idioma:</strong> " + servicioIdiomas.getTotalNivelesIdioma() + "</li>");
            bw.write("</ul>");
            bw.write("</div>");
            
            bw.write("</body></html>");
            System.out.println("✅ Reporte general generado: reporte_general.html");
        } catch (IOException e) {
            System.out.println("❌ Error al generar reporte general: " + e.getMessage());
        }
    }

    public void generarReporteEstudiantes() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("reporte_estudiantes.html"))) {
            bw.write(generarHeader("Reporte de Estudiantes"));
            bw.write("<body>");
            bw.write("<h1>👥 ESTUDIANTES REGISTRADOS</h1>");
            
            List<Estudiante> estudiantes = servicioEstudiantes.obtenerTodosEstudiantes();
            if (estudiantes.isEmpty()) {
                bw.write("<p>No hay estudiantes registrados.</p>");
            } else {
                bw.write("<table>");
                bw.write("<tr><th>DNI</th><th>Nombres</th><th>Apellidos</th><th>Dirección</th><th>Teléfono</th><th>Correo</th><th>Fecha Nac.</th><th>Nivel</th></tr>");
                for (Estudiante e : estudiantes) {
                    bw.write("<tr>");
                    bw.write("<td>" + e.getDni() + "</td>");
                    bw.write("<td>" + e.getNombres() + "</td>");
                    bw.write("<td>" + e.getApellidos() + "</td>");
                    bw.write("<td>" + e.getDireccion() + "</td>");
                    bw.write("<td>" + e.getTelefono() + "</td>");
                    bw.write("<td>" + e.getCorreo() + "</td>");
                    bw.write("<td>" + e.getFechaNacimiento() + "</td>");
                    bw.write("<td>" + e.getNivelEstudios() + "</td>");
                    bw.write("</tr>");
                }
                bw.write("</table>");
                bw.write("<p><strong>Total:</strong> " + estudiantes.size() + " estudiantes</p>");
            }
            
            bw.write("</body></html>");
            System.out.println("✅ Reporte de estudiantes generado: reporte_estudiantes.html");
        } catch (IOException e) {
            System.out.println("❌ Error al generar reporte de estudiantes: " + e.getMessage());
        }
    }

    public void generarReporteProfesores() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("reporte_profesores.html"))) {
            bw.write(generarHeader("Reporte de Profesores"));
            bw.write("<body>");
            bw.write("<h1>👨‍🏫 PROFESORES REGISTRADOS</h1>");
            
            List<Profesor> profesores = servicioProfesores.obtenerTodosProfesores();
            if (profesores.isEmpty()) {
                bw.write("<p>No hay profesores registrados.</p>");
            } else {
                bw.write("<table>");
                bw.write("<tr><th>DNI</th><th>Nombres</th><th>Apellidos</th><th>Dirección</th><th>Teléfono</th><th>Correo</th><th>Especialidad</th><th>Experiencia</th></tr>");
                for (Profesor p : profesores) {
                    bw.write("<tr>");
                    bw.write("<td>" + p.getDni() + "</td>");
                    bw.write("<td>" + p.getNombres() + "</td>");
                    bw.write("<td>" + p.getApellidos() + "</td>");
                    bw.write("<td>" + p.getDireccion() + "</td>");
                    bw.write("<td>" + p.getTelefono() + "</td>");
                    bw.write("<td>" + p.getCorreo() + "</td>");
                    bw.write("<td>" + p.getEspecialidad() + "</td>");
                    bw.write("<td>" + p.getExperiencia() + " años</td>");
                    bw.write("</tr>");
                }
                bw.write("</table>");
                bw.write("<p><strong>Total:</strong> " + profesores.size() + " profesores</p>");
            }
            
            bw.write("</body></html>");
            System.out.println("✅ Reporte de profesores generado: reporte_profesores.html");
        } catch (IOException e) {
            System.out.println("❌ Error al generar reporte de profesores: " + e.getMessage());
        }
    }

    public void generarReporteCursos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("reporte_cursos.html"))) {
            bw.write(generarHeader("Reporte de Cursos"));
            bw.write("<body>");
            bw.write("<h1>📚 CURSOS REGISTRADOS</h1>");
            
            List<Curso> cursos = servicioCursos.obtenerTodosCursos();
            if (cursos.isEmpty()) {
                bw.write("<p>No hay cursos registrados.</p>");
            } else {
                bw.write("<table>");
                bw.write("<tr><th>Código</th><th>Nombre</th><th>Idioma</th><th>Nivel</th><th>Profesor</th><th>Horario</th><th>Duración</th><th>Capacidad</th><th>Precio</th></tr>");
                for (Curso c : cursos) {
                    bw.write("<tr>");
                    bw.write("<td>" + c.getCodigo() + "</td>");
                    bw.write("<td>" + c.getNombre() + "</td>");
                    bw.write("<td>" + c.getIdioma() + "</td>");
                    bw.write("<td>" + c.getNivel() + "</td>");
                    bw.write("<td>" + c.getProfesorDni() + "</td>");
                    bw.write("<td>" + c.getHorario() + "</td>");
                    bw.write("<td>" + c.getDuracion() + " semanas</td>");
                    bw.write("<td>" + c.getCapacidadMaxima() + " estudiantes</td>");
                    bw.write("<td>S/ " + c.getPrecio() + "</td>");
                    bw.write("</tr>");
                }
                bw.write("</table>");
                bw.write("<p><strong>Total:</strong> " + cursos.size() + " cursos</p>");
            }
            
            bw.write("</body></html>");
            System.out.println("✅ Reporte de cursos generado: reporte_cursos.html");
        } catch (IOException e) {
            System.out.println("❌ Error al generar reporte de cursos: " + e.getMessage());
        }
    }

    public void generarReporteCalificaciones() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("reporte_calificaciones.html"))) {
            bw.write(generarHeader("Reporte de Calificaciones"));
            bw.write("<body>");
            bw.write("<h1>🎯 CALIFICACIONES REGISTRADAS</h1>");
            
            List<Calificacion> calificaciones = servicioCalificaciones.obtenerTodasCalificaciones();
            if (calificaciones.isEmpty()) {
                bw.write("<p>No hay calificaciones registradas.</p>");
            } else {
                bw.write("<table>");
                bw.write("<tr><th>Curso</th><th>Estudiante</th><th>Fecha</th><th>Nota</th><th>Estado</th><th>Observaciones</th></tr>");
                for (Calificacion c : calificaciones) {
                    String estado = c.getNota() >= 11 ? "✅ Aprobado" : "❌ Desaprobado";
                    String color = c.getNota() >= 11 ? "green" : "red";
                    
                    bw.write("<tr>");
                    bw.write("<td>" + c.getCodigoCurso() + "</td>");
                    bw.write("<td>" + c.getDniEstudiante() + "</td>");
                    bw.write("<td>" + c.getFecha() + "</td>");
                    bw.write("<td>" + c.getNota() + "</td>");
                    bw.write("<td style='color: " + color + "'><strong>" + estado + "</strong></td>");
                    bw.write("<td>" + c.getObservaciones() + "</td>");
                    bw.write("</tr>");
                }
                bw.write("</table>");
                
                // Estadísticas
                long aprobados = calificaciones.stream().filter(c -> c.getNota() >= 11).count();
                long desaprobados = calificaciones.size() - aprobados;
                bw.write("<div class='stats'>");
                bw.write("<h3>📊 Estadísticas</h3>");
                bw.write("<p><strong>Total calificaciones:</strong> " + calificaciones.size() + "</p>");
                bw.write("<p><strong>Aprobados:</strong> " + aprobados + " (" + (aprobados * 100 / calificaciones.size()) + "%)</p>");
                bw.write("<p><strong>Desaprobados:</strong> " + desaprobados + " (" + (desaprobados * 100 / calificaciones.size()) + "%)</p>");
                bw.write("</div>");
            }
            
            bw.write("</body></html>");
            System.out.println("✅ Reporte de calificaciones generado: reporte_calificaciones.html");
        } catch (IOException e) {
            System.out.println("❌ Error al generar reporte de calificaciones: " + e.getMessage());
        }
    }

    private String generarHeader(String titulo) {
        return "<!DOCTYPE html>\n" +
               "<html>\n" +
               "<head>\n" +
               "    <title>" + titulo + "</title>\n" +
               "    <meta charset='UTF-8'>\n" +
               "    <style>\n" +
               "        body { font-family: Arial, sans-serif; margin: 20px; }\n" +
               "        h1 { color: #2c3e50; border-bottom: 2px solid #3498db; padding-bottom: 10px; }\n" +
               "        table { width: 100%; border-collapse: collapse; margin: 20px 0; }\n" +
               "        th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }\n" +
               "        th { background-color: #3498db; color: white; }\n" +
               "        tr:nth-child(even) { background-color: #f2f2f2; }\n" +
               "        .stats { background-color: #ecf0f1; padding: 15px; border-radius: 5px; margin: 20px 0; }\n" +
               "        .stats h2, .stats h3 { color: #2c3e50; margin-top: 0; }\n" +
               "    </style>\n" +
               "</head>\n";
    }
}
