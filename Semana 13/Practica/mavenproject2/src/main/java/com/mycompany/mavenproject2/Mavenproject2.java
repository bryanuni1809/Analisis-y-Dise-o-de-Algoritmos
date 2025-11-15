/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject2;

/**
 *
 * @author USER
 */
public class Mavenproject2 {

    // 1 = camino, 0 = pared
    static int[][] laberinto = {
            {1, 0, 0, 0},
            {1, 1, 0, 1},
            {0, 1, 1, 1},
            {0, 0, 0, 1}
    };

    static int N = laberinto.length;

    public static void main(String[] args) {
        int[][] solucion = new int[N][N];

        if (resolver(0, 0, solucion)) {
            System.out.println("Camino encontrado:");
            imprimir(solucion);
        } else {
            System.out.println("No existe camino.");
        }
    }

    // Función recursiva con backtracking
    public static boolean resolver(int x, int y, int[][] sol) {

        // Si llegamos a la meta
        if (x == N - 1 && y == N - 1 && laberinto[x][y] == 1) {
            sol[x][y] = 1;
            return true;
        }

        // Validar movimiento
        if (esSeguro(x, y)) {
            sol[x][y] = 1; // Marco paso

            // mover abajo
            if (resolver(x + 1, y, sol)) return true;

            // mover derecha
            if (resolver(x, y + 1, sol)) return true;

            // Retroceder (backtrack)
            sol[x][y] = 0;
            return false;
        }

        return false;
    }

    // Verifica si la posición es válida
    public static boolean esSeguro(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < N && laberinto[x][y] == 1;
    }

    // Imprimir matriz solución
    public static void imprimir(int[][] sol) {
        for (int[] fila : sol) {
            for (int celda : fila) {
                System.out.print(celda + " ");
            }
            System.out.println();
        }
    }
}
