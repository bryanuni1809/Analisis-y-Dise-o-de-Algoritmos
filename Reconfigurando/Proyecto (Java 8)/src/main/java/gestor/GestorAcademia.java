/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestor;

import entidades.Calificacion;
import entidades.Curso;
import entidades.Estudiante;
import entidades.Matricula;
import entidades.Profesor;
import java.util.Scanner;
import servicios.ServicioCalificaciones;
import servicios.ServicioCursos;
import servicios.ServicioEstudiantes;
import servicios.ServicioIdiomas;
import servicios.ServicioMatriculas;
import servicios.ServicioOrdenamiento;
import servicios.ServicioProfesores;
import servicios.ServicioReportes;


/**
 *
 * @author BRYAN
 */
public class GestorAcademia{
    private final Scanner scanner = new Scanner(System.in);
    private final ServicioEstudiantes servicioEstudiantes = new ServicioEstudiantes();
    private final ServicioProfesores servicioProfesores = new ServicioProfesores();
    private final ServicioCursos servicioCursos = new ServicioCursos();
    private final ServicioMatriculas servicioMatriculas = new ServicioMatriculas();
    private final ServicioCalificaciones servicioCalificaciones = new ServicioCalificaciones();
    private final ServicioIdiomas servicioIdiomas = new ServicioIdiomas();
    private final ServicioReportes servicioReportes = new ServicioReportes();
    private final ServicioOrdenamiento servicioOrdenamiento = new ServicioOrdenamiento();

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n==================================================");
            System.out.println("GESTOR ACADÉMICO - MULTILINGUA");
            System.out.println("==================================================");
            System.out.println("1. Gestión de Estudiantes");
            System.out.println("2. Gestión de Profesores");
            System.out.println("3. Gestión de Cursos");
            System.out.println("4. Matrículas y Calificaciones");
            System.out.println("5. Niveles de Idioma");
            System.out.println("6. Generar Reportes HTML");
            System.out.println("7. Ordenar Listas");
            System.out.println("8. Búsquedas Avanzadas");
            System.out.println("0. Salir");
            System.out.println("==================================================");
            System.out.print("Seleccione una opción: ");
            
            opcion = leerEntero();
            
            switch(opcion) {
                case 1: menuEstudiantes(); break;
                case 2: menuProfesores(); break;
                case 3: menuCursos(); break;
                case 4: menuMatriculasNotas(); break;
                case 5: menuNivelesIdioma(); break;
                case 6: servicioReportes.mostrarMenuReportes(); break;
                case 7: menuOrdenamientos(); break;
                case 8: menuBusquedasAvanzadas(); break;
                case 0: System.out.println("Saliendo del sistema..."); break;
                default: System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }
    private void menuEstudiantes() {
        int opcion;
        do {
            System.out.println("\n--- GESTION DE ESTUDIANTES ---");
            System.out.println("1. Registrar Estudiante");
            System.out.println("2. Buscar Estudiante por DNI");
            System.out.println("3. Buscar Estudiantes por Nombre");
            System.out.println("4. Modificar Estudiante");
            System.out.println("5. Eliminar Estudiante");
            System.out.println("6. Listar Todos los Estudiantes");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            
            opcion = leerEntero();
            
            switch (opcion) {
                case 1: registrarEstudiante(); break;
                case 2: buscarEstudiantePorDNI(); break;
                case 3: buscarEstudiantesPorNombre(); break;
                case 4: modificarEstudiante(); break;
                case 5: eliminarEstudiante(); break;
                case 6: listarEstudiantes(); break;
                case 0: break;
                default: System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void registrarEstudiante() {
        System.out.println("\n--- REGISTRAR ESTUDIANTE ---");
        System.out.print("DNI: "); String dni = scanner.nextLine();
        System.out.print("Nombres: "); String nombres = scanner.nextLine();
        System.out.print("Apellidos: "); String apellidos = scanner.nextLine();
        System.out.print("Direccion: "); String direccion = scanner.nextLine();
        System.out.print("Telefono: "); String telefono = scanner.nextLine();
        System.out.print("Correo: "); String correo = scanner.nextLine();
        System.out.print("Fecha Nacimiento: "); String fechaNac = scanner.nextLine();
        System.out.print("Nivel Estudios: "); String nivel = scanner.nextLine();
        
        servicioEstudiantes.registrarEstudiante(dni, nombres, apellidos, direccion, telefono, correo, fechaNac, nivel);
    }

    private void buscarEstudiantePorDNI() {
        System.out.print("\nIngrese DNI del estudiante: ");
        String dni = scanner.nextLine();
        
        Estudiante estudiante = servicioEstudiantes.buscarEstudiante(dni);
        if (estudiante != null) {
            System.out.println("Estudiante encontrado:");
            System.out.println(estudiante.mostrarInfo());
        } else {
            System.out.println("Estudiante no encontrado");
        }
    }

    private void buscarEstudiantesPorNombre() {
        System.out.print("\nIngrese nombre o apellido a buscar: ");
        String nombre = scanner.nextLine();
        
        java.util.List<Estudiante> estudiantes = servicioEstudiantes.buscarEstudiantesPorNombre(nombre);
        if (estudiantes.isEmpty()) {
            System.out.println("No se encontraron estudiantes");
        } else {
            System.out.println("Estudiantes encontrados (" + estudiantes.size() + "):");
            for (int i = 0; i < estudiantes.size(); i++) {
                System.out.println((i + 1) + ". " + estudiantes.get(i).mostrarInfo());
            }
        }
    }

    private void modificarEstudiante() {
        System.out.print("\nIngrese DNI del estudiante a modificar: ");
        String dni = scanner.nextLine();
        
        Estudiante estudiante = servicioEstudiantes.buscarEstudiante(dni);
        if (estudiante == null) {
            System.out.println("Estudiante no encontrado");
            return;
        }
        
        System.out.println("Estudiante actual: " + estudiante.mostrarInfo());
        System.out.print("Nueva direccion (Enter para mantener actual): ");
        String nuevaDireccion = scanner.nextLine();
        System.out.print("Nuevo telefono (Enter para mantener actual): ");
        String nuevoTelefono = scanner.nextLine();
        System.out.print("Nuevo nivel de estudios (Enter para mantener actual): ");
        String nuevoNivel = scanner.nextLine();
        
        // Si está vacío, mantener el valor actual (null)
        nuevaDireccion = nuevaDireccion.isEmpty() ? null : nuevaDireccion;
        nuevoTelefono = nuevoTelefono.isEmpty() ? null : nuevoTelefono;
        nuevoNivel = nuevoNivel.isEmpty() ? null : nuevoNivel;
        
        servicioEstudiantes.modificarEstudiante(dni, nuevaDireccion, nuevoTelefono, nuevoNivel);
    }

    private void eliminarEstudiante() {
        System.out.print("\nIngrese DNI del estudiante a eliminar: ");
        String dni = scanner.nextLine();
        
        servicioEstudiantes.eliminarEstudiante(dni);
    }

    private void listarEstudiantes() {
        java.util.List<Estudiante> estudiantes = servicioEstudiantes.obtenerTodosEstudiantes();
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados");
            return;
        }
        
        System.out.println("\n--- LISTA DE ESTUDIANTES (" + estudiantes.size() + ") ---");
        for (int i = 0; i < estudiantes.size(); i++) {
            System.out.println((i + 1) + ". " + estudiantes.get(i).mostrarInfo());
        }
    }
    private void menuProfesores() {
        int opcion;
        do {
            System.out.println("\n--- GESTION DE PROFESORES ---");
            System.out.println("1. Registrar Profesor");
            System.out.println("2. Buscar Profesor por DNI");
            System.out.println("3. Buscar Profesores por Especialidad");
            System.out.println("4. Modificar Profesor");
            System.out.println("5. Eliminar Profesor");
            System.out.println("6. Listar Todos los Profesores");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            
            opcion = leerEntero();
            
            switch (opcion) {
                case 1: registrarProfesor(); break;
                case 2: buscarProfesorPorDNI(); break;
                case 3: buscarProfesoresPorEspecialidad(); break;
                case 4: modificarProfesor(); break;
                case 5: eliminarProfesor(); break;
                case 6: listarProfesores(); break;
                case 0: break;
                default: System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void registrarProfesor() {
        System.out.println("\n--- REGISTRAR PROFESOR ---");
        System.out.print("DNI: "); String dni = scanner.nextLine();
        System.out.print("Nombres: "); String nombres = scanner.nextLine();
        System.out.print("Apellidos: "); String apellidos = scanner.nextLine();
        System.out.print("Direccion: "); String direccion = scanner.nextLine();
        System.out.print("Telefono: "); String telefono = scanner.nextLine();
        System.out.print("Correo: "); String correo = scanner.nextLine();
        System.out.print("Especialidad: "); String especialidad = scanner.nextLine();
        System.out.print("Años de experiencia: "); int experiencia = leerEntero();
        
        servicioProfesores.registrarProfesor(dni, nombres, apellidos, direccion, telefono, correo, especialidad, experiencia);
    }

    private void buscarProfesorPorDNI() {
        System.out.print("\nIngrese DNI del profesor: ");
        String dni = scanner.nextLine();
        
        Profesor profesor = servicioProfesores.buscarProfesor(dni);
        if (profesor != null) {
            System.out.println("Profesor encontrado:");
            System.out.println(profesor.mostrarInfo());
        } else {
            System.out.println("Profesor no encontrado");
        }
    }

    private void buscarProfesoresPorEspecialidad() {
        System.out.print("\nIngrese especialidad a buscar: ");
        String especialidad = scanner.nextLine();
        
        java.util.List<Profesor> profesores = servicioProfesores.buscarProfesoresPorEspecialidad(especialidad);
        if (profesores.isEmpty()) {
            System.out.println("No se encontraron profesores");
        } else {
            System.out.println("Profesores encontrados (" + profesores.size() + "):");
            for (int i = 0; i < profesores.size(); i++) {
                System.out.println((i + 1) + ". " + profesores.get(i).mostrarInfo());
            }
        }
    }

    private void modificarProfesor() {
        System.out.print("\nIngrese DNI del profesor a modificar: ");
        String dni = scanner.nextLine();
        
        Profesor profesor = servicioProfesores.buscarProfesor(dni);
        if (profesor == null) {
            System.out.println("Profesor no encontrado");
            return;
        }
        
        System.out.println("Profesor actual: " + profesor.mostrarInfo());
        System.out.print("Nueva especialidad (Enter para mantener actual): ");
        String nuevaEspecialidad = scanner.nextLine();
        System.out.print("Nueva experiencia en años (Enter para mantener actual): ");
        String expStr = scanner.nextLine();
        
        Integer nuevaExperiencia = null;
        if (!expStr.isEmpty()) {
            try {
                nuevaExperiencia = Integer.parseInt(expStr);
            } catch (NumberFormatException e) {
                System.out.println("Experiencia debe ser un numero");
                return;
            }
        }
        
        servicioProfesores.modificarProfesor(dni, nuevaEspecialidad, nuevaExperiencia);
    }

    private void eliminarProfesor() {
        System.out.print("\nIngrese DNI del profesor a eliminar: ");
        String dni = scanner.nextLine();
        
        servicioProfesores.eliminarProfesor(dni);
    }

    private void listarProfesores() {
        java.util.List<Profesor> profesores = servicioProfesores.obtenerTodosProfesores();
        if (profesores.isEmpty()) {
            System.out.println("No hay profesores registrados");
            return;
        }
        
        System.out.println("\n--- LISTA DE PROFESORES (" + profesores.size() + ") ---");
        for (int i = 0; i < profesores.size(); i++) {
            System.out.println((i + 1) + ". " + profesores.get(i).mostrarInfo());
        }
    }
    private void menuCursos() {
        int opcion;
        do {
            System.out.println("\n--- GESTION DE CURSOS ---");
            System.out.println("1. Registrar Curso");
            System.out.println("2. Buscar Curso por Codigo");
            System.out.println("3. Buscar Cursos por Idioma");
            System.out.println("4. Modificar Curso");
            System.out.println("5. Eliminar Curso");
            System.out.println("6. Listar Todos los Cursos");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            
            opcion = leerEntero();
            
            switch (opcion) {
                case 1: registrarCurso(); break;
                case 2: buscarCursoPorCodigo(); break;
                case 3: buscarCursosPorIdioma(); break;
                case 4: modificarCurso(); break;
                case 5: eliminarCurso(); break;
                case 6: listarCursos(); break;
                case 0: break;
                default: System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void registrarCurso() {
        System.out.println("\n--- REGISTRAR CURSO ---");
        System.out.print("Codigo del curso: "); String codigo = scanner.nextLine();
        System.out.print("Nombre del curso: "); String nombre = scanner.nextLine();
        System.out.print("Idioma: "); String idioma = scanner.nextLine();
        System.out.print("Nivel: "); String nivel = scanner.nextLine();
        System.out.print("DNI del profesor: "); String dniProfesor = scanner.nextLine();
        System.out.print("Horario: "); String horario = scanner.nextLine();
        System.out.print("Duracion (semanas): "); int duracion = leerEntero();
        System.out.print("Capacidad maxima: "); int capacidad = leerEntero();
        System.out.print("Precio: "); double precio = leerDouble();
        System.out.print("Observaciones: "); String observaciones = scanner.nextLine();
        
        servicioCursos.registrarCurso(codigo, nombre, idioma, nivel, dniProfesor, horario, duracion, capacidad, precio, observaciones);
    }

    private void buscarCursoPorCodigo() {
        System.out.print("\nIngrese codigo del curso: ");
        String codigo = scanner.nextLine();
        
        Curso curso = servicioCursos.buscarCurso(codigo);
        if (curso != null) {
            System.out.println("Curso encontrado:");
            System.out.println(curso.mostrarInfo());
        } else {
            System.out.println("Curso no encontrado");
        }
    }

    private void buscarCursosPorIdioma() {
        System.out.print("\nIngrese idioma a buscar: ");
        String idioma = scanner.nextLine();
        
        java.util.List<Curso> cursos = servicioCursos.buscarCursosPorIdioma(idioma);
        if (cursos.isEmpty()) {
            System.out.println("No se encontraron cursos");
        } else {
            System.out.println("Cursos encontrados (" + cursos.size() + "):");
            for (int i = 0; i < cursos.size(); i++) {
                System.out.println((i + 1) + ". " + cursos.get(i).mostrarInfo());
            }
        }
    }

    private void modificarCurso() {
        System.out.print("\nIngrese codigo del curso a modificar: ");
        String codigo = scanner.nextLine();
        
        Curso curso = servicioCursos.buscarCurso(codigo);
        if (curso == null) {
            System.out.println("Curso no encontrado");
            return;
        }
        
        System.out.println("Curso actual: " + curso.mostrarInfo());
        System.out.print("Nuevo horario (Enter para mantener actual): ");
        String nuevoHorario = scanner.nextLine();
        System.out.print("Nueva capacidad (Enter para mantener actual): ");
        String capStr = scanner.nextLine();
        System.out.print("Nuevas observaciones (Enter para mantener actual): ");
        String nuevasObservaciones = scanner.nextLine();
        
        Integer nuevaCapacidad = null;
        if (!capStr.isEmpty()) {
            try {
                nuevaCapacidad = Integer.parseInt(capStr);
            } catch (NumberFormatException e) {
                System.out.println("Capacidad debe ser un numero");
                return;
            }
        }
        
        servicioCursos.modificarCurso(codigo, nuevoHorario, nuevaCapacidad, nuevasObservaciones);
    }

    private void eliminarCurso() {
        System.out.print("\nIngrese codigo del curso a eliminar: ");
        String codigo = scanner.nextLine();
        
        servicioCursos.eliminarCurso(codigo);
    }

    private void listarCursos() {
        java.util.List<Curso> cursos = servicioCursos.obtenerTodosCursos();
        if (cursos.isEmpty()) {
            System.out.println("No hay cursos registrados");
            return;
        }
        
        System.out.println("\n--- LISTA DE CURSOS (" + cursos.size() + ") ---");
        for (int i = 0; i < cursos.size(); i++) {
            System.out.println((i + 1) + ". " + cursos.get(i).mostrarInfo());
        }
    }

    // ========== MENÚ MATRÍCULAS Y CALIFICACIONES ==========
    private void menuMatriculasNotas() {
        int opcion;
        do {
            System.out.println("\n--- MATRICULAS Y CALIFICACIONES ---");
            System.out.println("1. Registrar Matricula");
            System.out.println("2. Registrar Calificacion");
            System.out.println("3. Ver Matriculas por Estudiante");
            System.out.println("4. Ver Calificaciones por Curso");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            
            opcion = leerEntero();
            
            switch (opcion) {
                case 1: registrarMatricula(); break;
                case 2: registrarCalificacion(); break;
                case 3: verMatriculasEstudiante(); break;
                case 4: verCalificacionesCurso(); break;
                case 0: break;
                default: System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void registrarMatricula() {
        System.out.println("\n--- REGISTRAR MATRICULA ---");
        System.out.print("DNI del estudiante: "); String dniEstudiante = scanner.nextLine();
        System.out.print("Codigo del curso: "); String codigoCurso = scanner.nextLine();
        System.out.print("Fecha de matricula: "); String fecha = scanner.nextLine();
        
        // Buscar curso para obtener precio
        Curso curso = servicioCursos.buscarCurso(codigoCurso);
        if (curso == null) {
            System.out.println("Curso no encontrado");
            return;
        }
        
        double monto = curso.getPrecio();
        servicioMatriculas.registrarMatricula(codigoCurso, dniEstudiante, fecha, monto);
    }

    private void registrarCalificacion() {
        System.out.println("\n--- REGISTRAR CALIFICACION ---");
        System.out.print("Codigo del curso: "); String codigoCurso = scanner.nextLine();
        System.out.print("DNI del estudiante: "); String dniEstudiante = scanner.nextLine();
        System.out.print("Fecha: "); String fecha = scanner.nextLine();
        System.out.print("Nota (0-20): "); double nota = leerDouble();
        System.out.print("Observaciones: "); String observaciones = scanner.nextLine();
        
        servicioCalificaciones.registrarCalificacion(codigoCurso, dniEstudiante, fecha, nota, observaciones);
    }

    private void verMatriculasEstudiante() {
        System.out.print("\nIngrese DNI del estudiante: ");
        String dni = scanner.nextLine();
        
        java.util.List<Matricula> matriculas = servicioMatriculas.obtenerMatriculasPorEstudiante(dni);
        if (matriculas.isEmpty()) {
            System.out.println("No se encontraron matriculas");
        } else {
            System.out.println("Matriculas del estudiante:");
            for (Matricula m : matriculas) {
                System.out.println("- " + m.mostrarInfo());
            }
        }
    }

    private void verCalificacionesCurso() {
        System.out.print("\nIngrese codigo del curso: ");
        String codigo = scanner.nextLine();
        
        java.util.List<Calificacion> calificaciones = servicioCalificaciones.obtenerCalificacionesPorCurso(codigo);
        if (calificaciones.isEmpty()) {
            System.out.println("No se encontraron calificaciones");
        } else {
            System.out.println("Calificaciones del curso:");
            for (Calificacion c : calificaciones) {
                System.out.println("- " + c.mostrarInfo());
            }
        }
    }
    private void menuNivelesIdioma() {
        int opcion;
        do {
            System.out.println("\n--- NIVELES DE IDIOMA ---");
            System.out.println("1. Registrar Nivel de Idioma");
            System.out.println("2. Buscar Nivel por Codigo");
            System.out.println("3. Buscar Niveles por Idioma");
            System.out.println("4. Modificar Nivel");
            System.out.println("5. Eliminar Nivel");
            System.out.println("6. Listar Todos los Niveles");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            
            opcion = leerEntero();
            
            switch (opcion) {
                case 1: registrarNivelIdioma(); break;
                case 2: buscarNivelPorCodigo(); break;
                case 3: buscarNivelesPorIdioma(); break;
                case 4: modificarNivelIdioma(); break;
                case 5: eliminarNivelIdioma(); break;
                case 6: listarNivelesIdioma(); break;
                case 0: break;
                default: System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void registrarNivelIdioma() {
        System.out.println("\n--- REGISTRAR NIVEL DE IDIOMA ---");
        System.out.print("Codigo: "); String codigo = scanner.nextLine();
        System.out.print("Idioma: "); String idioma = scanner.nextLine();
        System.out.print("Nivel: "); String nivel = scanner.nextLine();
        System.out.print("Descripcion: "); String descripcion = scanner.nextLine();
        
        servicioIdiomas.registrarNivelIdioma(codigo, idioma, nivel, descripcion);
    }

    // ... métodos similares para niveles de idioma

    private void menuReportes() {
        int opcion;
        do {
            System.out.println("\n--- GENERAR REPORTES HTML ---");
            System.out.println("1. Reporte General del Sistema");
            System.out.println("2. Reporte de Estudiantes");
            System.out.println("3. Reporte de Profesores");
            System.out.println("4. Reporte de Cursos");
            System.out.println("5. Reporte de Calificaciones");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opcion: ");
            
            opcion = leerEntero();
            
            switch (opcion) {
                case 1: servicioReportes.generarReporteGeneral(); break;
                case 2: servicioReportes.generarReporteEstudiantes(); break;
                case 3: servicioReportes.generarReporteProfesores(); break;
                case 4: servicioReportes.generarReporteCursos(); break;
                case 5: servicioReportes.generarReporteCalificaciones(); break;
                case 0: break;
                default: System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    // ========== MENÚ ORDENAMIENTOS ==========
    private void menuOrdenamientos() {
        int opcion;
        do {
            System.out.println("\n--- ORDENAR LISTAS ---");
            System.out.println("1. Ordenar Estudiantes");
            System.out.println("2. Ordenar Profesores");
            System.out.println("3. Ordenar Cursos");
            System.out.println("4. Ordenacion Externa (Archivos)");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            
            opcion = leerEntero();
            
            switch (opcion) {
                case 1: ordenarEstudiantes(); break;
                case 2: ordenarProfesores(); break;
                case 3: ordenarCursos(); break;
                case 4: ordenacionExterna(); break;
                case 0: break;
                default: System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void ordenarEstudiantes() {
        System.out.println("\n--- ORDENAR ESTUDIANTES ---");
        System.out.println("1. Por Apellidos (A-Z)");
        System.out.println("2. Por Nombres (A-Z)");
        System.out.println("3. Por DNI");
        System.out.print("Seleccione criterio: ");
        
        int criterio = leerEntero();
        String tipo = "";
        
        switch (criterio) {
            case 1: tipo = "apellidos"; break;
            case 2: tipo = "nombres"; break;
            case 3: tipo = "dni"; break;
            default: 
                System.out.println("Criterio invalido.");
                return;
        }
        
        java.util.List<Estudiante> estudiantesOrdenados = servicioOrdenamiento.ordenarEstudiantes(
            servicioEstudiantes.obtenerTodosEstudiantes(), tipo
        );
        
        System.out.println("\n--- ESTUDIANTES ORDENADOS ---");
        for (int i = 0; i < estudiantesOrdenados.size(); i++) {
            System.out.println((i + 1) + ". " + estudiantesOrdenados.get(i).mostrarInfo());
        }
    }

    // ... métodos similares para ordenar profesores y cursos

    private void ordenacionExterna() {
        System.out.println("\n--- ORDENACION EXTERNA ---");
        System.out.println("1. Ordenar archivo de Estudiantes");
        System.out.println("2. Ordenar archivo de Profesores");
        System.out.print("Seleccione archivo: ");
        
        int archivo = leerEntero();
        String nombreArchivo = "";
        String nombreSalida = "";
        
        switch (archivo) {
            case 1: 
                nombreArchivo = "estudiantes.txt";
                nombreSalida = "estudiantes_ordenados.txt";
                break;
            case 2: 
                nombreArchivo = "profesores.txt";
                nombreSalida = "profesores_ordenados.txt";
                break;
            default: 
                System.out.println("Opcion invalida.");
                return;
        }
        
        System.out.print("Tamaño de bloque (recomendado 100): ");
        int tamanoBloque = leerEntero();
        
        servicioOrdenamiento.ordenarArchivoExterno(nombreArchivo, nombreSalida, tamanoBloque);
    }

    private void menuBusquedasAvanzadas() {
        int opcion;
        do {
            System.out.println("\n--- BUSQUEDAS AVANZADAS ---");
            System.out.println("1. Busqueda de Estudiantes");
            System.out.println("2. Busqueda de Profesores");
            System.out.println("3. Busqueda de Cursos");
            System.out.println("4. Busqueda en Archivos");
            System.out.println("5. Estadisticas del Sistema");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            
            opcion = leerEntero();
            
            switch (opcion) {
                case 1: menuBusquedaEstudiantes(); break;
                case 2: menuBusquedaProfesores(); break;
                case 3: menuBusquedaCursos(); break;
                case 4: menuBusquedaArchivos(); break;
                case 5: servicioBusqueda.mostrarEstadisticasSistema(); break;
                case 0: break;
                default: System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void menuBusquedaEstudiantes() {
        System.out.println("\n--- BUSQUEDA DE ESTUDIANTES ---");
        System.out.println("1. Por DNI (Exacta)");
        System.out.println("2. Por Nombre/Apellido (Parcial)");
        System.out.print("Seleccione tipo: ");
        
        int tipo = leerEntero();
        System.out.print("Ingrese criterio: ");
        String criterio = scanner.nextLine();
        
        switch (tipo) {
            case 1:
                Estudiante est = servicioBusqueda.buscarEstudianteExacto(criterio);
                if (est != null) {
                    System.out.println("Estudiante encontrado:");
                    System.out.println(est.mostrarInfo());
                } else {
                    System.out.println("No se encontro estudiante");
                }
                break;
                
            case 2:
                java.util.List<Estudiante> estudiantes = servicioBusqueda.buscarEstudiantesPorNombre(criterio);
                if (!estudiantes.isEmpty()) {
                    System.out.println("Estudiantes encontrados:");
                    for (int i = 0; i < estudiantes.size(); i++) {
                        System.out.println((i + 1) + ". " + estudiantes.get(i).mostrarInfo());
                    }
                } else {
                    System.out.println("No se encontraron estudiantes");
                }
                break;
                
            default:
                System.out.println("Tipo invalido");
        }
    }

    // ... métodos similares para otras búsquedas

    private void menuBusquedaArchivos() {
        System.out.println("\n--- BUSQUEDA EN ARCHIVOS ---");
        System.out.println("1. Buscar en Estudiantes.txt");
        System.out.println("2. Buscar en Profesores.txt");
        System.out.print("Seleccione archivo: ");
        
        int archivo = leerEntero();
        String nombreArchivo = "";
        
        switch (archivo) {
            case 1: nombreArchivo = "estudiantes.txt"; break;
            case 2: nombreArchivo = "profesores.txt"; break;
            default: 
                System.out.println("Opcion invalida.");
                return;
        }
        
        System.out.print("Texto a buscar: ");
        String texto = scanner.nextLine();
        
        java.util.List<String> resultados = servicioBusqueda.busquedaExternaEnArchivo(nombreArchivo, texto);
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron resultados");
        } else {
            System.out.println("Resultados encontrados:");
            for (String linea : resultados) {
                System.out.println(linea);
            }
        }
    }
    private int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private double leerDouble() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    private void buscarNivelPorCodigo() { /* implementar */ }
    private void buscarNivelesPorIdioma() { /* implementar */ }
    private void modificarNivelIdioma() { /* implementar */ }
    private void eliminarNivelIdioma() { /* implementar */ }
    private void listarNivelesIdioma() { /* implementar */ }
    private void ordenarProfesores() { /* implementar */ }
    private void ordenarCursos() { /* implementar */ }
    private void menuBusquedaProfesores() { /* implementar */ }
    private void menuBusquedaCursos() { /* implementar */ }
}

