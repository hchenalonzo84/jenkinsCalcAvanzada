/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package PruebasAvanzadas;

import com.mycompany.calculadoraavanzada.CalculadoraAvanzada;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author rapaz
 */
public class TestCalculadoraAvanzada {
    
    public TestCalculadoraAvanzada() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
     CalculadoraAvanzada  calc = new CalculadoraAvanzada();
     
    }
    @Test
    public void estSumar() {
     CalculadoraAvanzada  calc = new CalculadoraAvanzada();
    assertEquals(8, calc.sumar(5,3));
    assertEquals(-2, calc.sumar(-5,3));
    }
    @Test
    public void testDividirPorCero() {
    CalculadoraAvanzada  calc = new CalculadoraAvanzada();
    assertThrows(IllegalArgumentException.class, () -> calc.dividir(5,0));
    }
    
    //  test para evualar si es par o impar un numero por medio de un booleano
    
    @Test
    public void testEsParVerdaderoYFalso() {
        CalculadoraAvanzada calc = new CalculadoraAvanzada();
        assertTrue(calc.esPar(4), "4 debería ser par");
        assertTrue(calc.esPar(0), "0 debería ser par");
        assertFalse(calc.esPar(5), "5 no debería ser par");
        assertFalse(calc.esPar(-3), "-3 no debería ser par");
    }
    
    @Test
void testMax() {
    CalculadoraAvanzada calc = new CalculadoraAvanzada();
    assertEquals(9, calc.max(1, 9, 3, 5));
    assertEquals(-1, calc.max(-5, -10, -1));
    assertEquals(7, calc.max(7));
}

@Test
void testMaxSinArgumentos() {
    CalculadoraAvanzada calc = new CalculadoraAvanzada();
    assertThrows(IllegalArgumentException.class, () -> calc.max());
}

@Test
void testPotencia() {
    CalculadoraAvanzada calc = new CalculadoraAvanzada();
    assertEquals(1, calc.potencia(5, 0), "Todo número a la potencia 0 es 1");
    assertEquals(8, calc.potencia(2, 3));
    assertEquals(81, calc.potencia(9, 2));
    assertEquals(0, calc.potencia(0, 5));
}

@Test
void testPotenciaExponenteNegativo() {
    CalculadoraAvanzada calc = new CalculadoraAvanzada();
    assertThrows(IllegalArgumentException.class, () -> calc.potencia(2, -3));
}

   
   
   
}
