/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.boos.client;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Ejemplo de java 8 con lambdas sencillo
 * @author julianosorio
 */
public class TestIntroduccion {
    
    public TestIntroduccion() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void test_ordenacion_inversa() {

        List<String> nombres = Arrays.asList("Juan", "Antonia", "Pedro");

        // asigna a comparadorLongitud un comparador que ordene los strings
        // segun la longitud de MAYOR a MENOR (es decir, al contrario que el ejemplo de
        // la presentacion.
        // * solo debes modificar la siguiente linea, el resto de codigo debe quedar igual *
        Comparator<String> comparadorLongitud = (o1, o2) -> o2.length() - o1.length();

        assertNotNull("No has creado aun el comparador", comparadorLongitud);

        Collections.sort(nombres, comparadorLongitud);

        assertEquals("El primer elemento deberia ser Antonia", "Antonia", nombres.get(0));
        assertEquals("El segundo elemnento deberia ser Pedro", "Pedro", nombres.get(1));
        assertEquals("El tercer elemento debeia ser Juan", "Juan", nombres.get(2));
    }
    
    @Test
    public void test_stream_number(){
        List<Integer> listaNumeros = new ArrayList<>();
        listaNumeros.add(1);
        listaNumeros.add(2);
        listaNumeros.add(5);
        listaNumeros.add(6);
        
        Stream st = listaNumeros.stream();
        
        //contar cuantas veces esta el numero 5 en la lista de numeros
        assertEquals(st.filter(Predicate.isEqual(5)).count(), 1);
    }
}
