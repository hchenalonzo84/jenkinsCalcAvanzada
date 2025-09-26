/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.calculadoraavanzada;

/**
 *
 * @author rapaz
 */
public class CalculadoraAvanzada {

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
    public int sumar(int a, int b) { return a + b; }
    public int restar(int a, int b) { return a - b; }
    public int multiplicar(int a, int b) { return a * b; }
    public double dividir(int a, int b) {
    if (b == 0) throw new IllegalArgumentException("No se puede dividir entre cero");
    return (double)a / b;
    }
    public boolean esPar(int n){ return n%2==0; }
     public int max(int... valores) {
    if (valores == null || valores.length == 0) {
        throw new IllegalArgumentException("Debe proporcionar al menos un valor");
    }
    int mayor = valores[0];
    for (int v : valores) {
        if (v > mayor) {
            mayor = v;
        }
    }
    return mayor;
}

    public int potencia(int base, int exp) {
     if (exp < 0) {
        throw new IllegalArgumentException("El exponente no puede ser negativo");
    }
    return (int) Math.pow(base, exp);
    }

    
}
