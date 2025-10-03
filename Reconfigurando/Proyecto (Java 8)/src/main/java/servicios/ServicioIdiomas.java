/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import entidades.IdiomaNivel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import util.ArchivoUtil;

/**
 *
 * @author BRYAN
 */
public class ServicioIdiomas {
    private final List<IdiomaNivel> nivelesIdioma = new ArrayList<>();
    private static final String ARCHIVO = "idiomas.txt";

    public ServicioIdiomas() {
        cargarNivelesIdioma();
    }

    public void cargarNivelesIdioma() {
        nivelesIdioma.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 4) {
                    IdiomaNivel in = new IdiomaNivel(partes[0], partes[1], partes[2], partes[3]);
                    nivelesIdioma.add(in);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar niveles de idioma: " + e.getMessage());
        }
    }

    public boolean registrarNivelIdioma(String codigo, String idioma, String nivel, String descripcion) {
        // Verificar si el código ya existe
        for (IdiomaNivel in : nivelesIdioma) {
            if (in.getCodigo().equals(codigo)) {
                System.out.println("El nivel con código " + codigo + " ya existe");
                return false;
            }
        }

        IdiomaNivel in = new IdiomaNivel(codigo, idioma, nivel, descripcion);
        nivelesIdioma.add(in);
        ArchivoUtil.guardarNivelIdioma(in, ARCHIVO);
        System.out.println("Nivel de idioma registrado exitosamente");
        return true;
    }

    public IdiomaNivel buscarNivelIdioma(String codigo) {
        for (IdiomaNivel in : nivelesIdioma) {
            if (in.getCodigo().equals(codigo)) {
                return in;
            }
        }
        return null;
    }

    public List<IdiomaNivel> buscarNivelesPorIdioma(String idioma) {
        List<IdiomaNivel> resultados = new ArrayList<>();
        for (IdiomaNivel in : nivelesIdioma) {
            if (in.getIdioma().equalsIgnoreCase(idioma)) {
                resultados.add(in);
            }
        }
        return resultados;
    }

    public boolean modificarNivelIdioma(String codigo, String nuevoNivel, String nuevaDescripcion) {
        IdiomaNivel in = buscarNivelIdioma(codigo);
        if (in == null) {
            System.out.println("❌ Nivel de idioma no encontrado");
            return false;
        }

        if (nuevoNivel != null) in.setNivel(nuevoNivel);
        if (nuevaDescripcion != null) in.setDescripcion(nuevaDescripcion);

        ArchivoUtil.sobrescribirNivelesIdioma(nivelesIdioma, ARCHIVO);
        System.out.println("✅ Nivel de idioma modificado exitosamente");
        return true;
    }

    public boolean eliminarNivelIdioma(String codigo) {
        for (int i = 0; i < nivelesIdioma.size(); i++) {
            if (nivelesIdioma.get(i).getCodigo().equals(codigo)) {
                nivelesIdioma.remove(i);
                ArchivoUtil.sobrescribirNivelesIdioma(nivelesIdioma, ARCHIVO);
                System.out.println("✅ Nivel de idioma eliminado exitosamente");
                return true;
            }
        }
        System.out.println("❌ Nivel de idioma no encontrado");
        return false;
    }

    public List<String> obtenerIdiomasDisponibles() {
        Set<String> idiomas = new HashSet<>();
        for (IdiomaNivel in : nivelesIdioma) {
            idiomas.add(in.getIdioma());
        }
        return new ArrayList<>(idiomas);
    }

    public List<IdiomaNivel> obtenerTodosNiveles() {
        return new ArrayList<>(nivelesIdioma);
    }

    public boolean existeNivelIdioma(String codigo) {
        return buscarNivelIdioma(codigo) != null;
    }

    public int getTotalNivelesIdioma() {
        return nivelesIdioma.size();
    }
}
