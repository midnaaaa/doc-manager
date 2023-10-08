package test.domini;

import domini.classes.ExpBool;
import domini.classes.IndexExpBool;
import domini.exceptions.AutorIncorrecteException;
import domini.exceptions.JaExisteixException;
import domini.exceptions.NoExisteixException;
import domini.exceptions.NomExpressioIncorrecte;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class IndexExpBoolTest{


    @Test
    public void CreadoraTest(){
        IndexExpBool test = new IndexExpBool();
        assertNotNull(test);
        assertEquals(test.getClass(), IndexExpBool.class);
    }

    @Test
    public void getMapaTest(){
        HashMap<String, ExpBool> expected = new HashMap<>();
        IndexExpBool test = new IndexExpBool();
        assertTrue("correcte", expected.equals(test.getMapa()));        
    }

    @Test
    public void putExpBoolTest() throws NomExpressioIncorrecte, AutorIncorrecteException {
        HashMap<String, ExpBool> expected = new HashMap<>();
        ExpBool prova = new ExpBool("E1", "a&b");
        expected.put("E1", prova);

        IndexExpBool test = new IndexExpBool();
        test.putExpBool(prova);

        assertTrue("correcte", expected.equals(test.getMapa()));
    }

    @Test
    public void getExpBoolTest() throws NomExpressioIncorrecte, AutorIncorrecteException, NoExisteixException {
        ExpBool prova = new ExpBool("E1", "a&b");
        IndexExpBool test = new IndexExpBool();
        test.putExpBool(prova);
        assertTrue("correcte", prova.equals(test.getExpBool("E1")));

    }

    @Test
    public void getExpAsStringTest() throws NomExpressioIncorrecte, AutorIncorrecteException{
        ExpBool prova = new ExpBool("E1", "a&b");
        IndexExpBool test = new IndexExpBool();
        test.putExpBool(prova);
        assertEquals("correcte", test.getExpressioAsString("E1"), "a&b");

    }

    @Test
    public void crearExpBoolTest() throws NomExpressioIncorrecte, AutorIncorrecteException, NoExisteixException, JaExisteixException {
        ExpBool prova = new ExpBool("E1", "a&b");
        IndexExpBool test = new IndexExpBool();
        test.crearExpBool("E1", "a&b");
        ExpBool aux = test.getExpBool("E1");
        assertTrue("correcte", prova.equals(aux));

    }

    @Test
    public void modificarExpBoolTest() throws NomExpressioIncorrecte, AutorIncorrecteException, NoExisteixException , JaExisteixException {
        ExpBool prova = new ExpBool("E1", "a&b");
        ExpBool prova2 = new ExpBool("E1","b&a");
        IndexExpBool test = new IndexExpBool();
        test.putExpBool(prova);
        test.modificarExpBool("E1", "b&a");
        assertTrue("correcte", prova2.equals(test.getExpBool("E1")));
    }

    @Test
    public void eliminarExpBoolTest() throws NomExpressioIncorrecte, AutorIncorrecteException, NoExisteixException {
        HashMap<String, ExpBool> expected = new HashMap<>();
        ExpBool prova = new ExpBool("E1", "a&b");
        IndexExpBool test = new IndexExpBool();
        test.putExpBool(prova);
        test.eliminarExpBool("E1");
        assertTrue("correcte", expected.equals(test.getMapa()));

    }
}
