/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject1;

/**
 *
 * @author USER
 */
public class Mavenproject1 {
    // Función recursiva
    public static long factorial(int n) {
        if(n <= 1){
            return 1;// Caso base
        } else {
            return n * factorial(n - 1); // Llamada recursiva
        }
    }

    public static void main(String[] args) {
        int numero = 5;
        long resultado=factorial(numero);

        System.out.println("El factorial de " + numero + " es: " + resultado);
    }
}
