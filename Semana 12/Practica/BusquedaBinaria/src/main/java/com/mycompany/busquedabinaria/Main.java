/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.busquedabinaria;

/**
 *
 * @author BRYAN
 */
public class Main {
    public static void main(String[] args){
        Estudiante[] alumnos={
            new Estudiante(101,"Ana"),
            new Estudiante(103,"Luis"),
            new Estudiante(107,"Maria"),
            new Estudiante(112,"Carlos")
        };
        int idBuscar=107;
        int resultadoLineal=Busqueda.busquedaLineal(alumnos,idBuscar);
        int resultadoBinario=Busqueda.busquedaBinaria(alumnos,idBuscar);
        System.out.println("Buscando alumno con ID: "+idBuscar );
        System.out.println("Lineal: " + (resultadoLineal!=-1 
            ? alumnos[resultadoLineal].getNombre() 
            : "No encontrado"));
        System.out.println("Binaria: "+(resultadoBinario!=-1 
            ? alumnos[resultadoBinario].getNombre() 
            : "No encontrado"));
    }
}