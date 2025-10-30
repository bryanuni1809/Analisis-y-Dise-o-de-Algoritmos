/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.busquedabinaria;

/**
 *
 * @author BRYAN
 */
public class Busqueda{
    public static int busquedaLineal(Estudiante[] estudiantes,int idBuscado){
        for(int i=0;i<estudiantes.length;i++){
            if(estudiantes[i].getId()==idBuscado){
                return i;
            }
        }
        return-1;
    }
    public static int busquedaBinaria(Estudiante[] estudiantes,int idBuscado){
        int izquierda=0;
        int derecha=estudiantes.length-1;

        while(izquierda<=derecha){
            int medio = (izquierda + derecha)/2;
            if(estudiantes[medio].getId()==idBuscado)return medio;
            if(estudiantes[medio].getId()<idBuscado)izquierda=medio + 1;
            else derecha=medio-1;
        }
        return-1;
    }
}
