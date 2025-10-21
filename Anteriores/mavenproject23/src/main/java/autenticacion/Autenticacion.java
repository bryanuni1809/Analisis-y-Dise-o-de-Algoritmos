/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autenticacion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author BRYAN
 */
public class Autenticacion {
    private static final String ARCHIVO= "usuarios.txt";
    //Este es el metodo que valida si un usuario y contraseña existen en el archivo
    //Aqui se aplica una busqueda lineal O(n)
    //Porque revisa linea por línea hasta encontrar el usuario. Si hay n usuarios, en el peor caso lee todas las líneas.
    public boolean validarCredenciales(String usuario, String contrasena) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    String u = partes[0].trim();
                    String c = partes[1].trim();
                    if (u.equals(usuario) && c.equals(contrasena)) {
                        return true;
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Error al leer usuarios: " + ex.getMessage());
        }
        return false;
    }
    private boolean usuarioExiste(String usuario){
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    String u = partes[0].trim();
                    if (u.equals(usuario)) {
                        return true;
                     }
                }
            }
        } catch(IOException ex) {
        }
        return false;
    }
    //Este es el metodo que registra un nuevo usuario en el archivo
    //Aqui se hace una registro simple al archivo. O(1)
    //Porque solo agrega una nueva línea al final del archivo, sin importar cuantos usuarios existan.
    public void registrarUsuario(String usuario, String contrasena) {
        if (usuarioExiste(usuario)) {
            System.out.println("El usuario '" + usuario + "' ya esta registrado.");
            return;
        }
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
        writer.write(usuario + "," + contrasena);
        writer.newLine();
        System.out.println("Usuario registrado con exito.");
    } catch (IOException e) {
        System.out.println("Error al registrar usuario: " + e.getMessage());
    }
}

}