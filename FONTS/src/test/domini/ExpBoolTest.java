package test.domini;

import domini.classes.ExpBool;
import domini.exceptions.AutorIncorrecteException;
import domini.exceptions.NomExpressioIncorrecte;
import domini.utils.Node;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExpBoolTest{
    
    @Test
    public void CreadoraTest(){
        ExpBool test = new ExpBool();
        assertNotNull(test);
        assertEquals(test.getClass(), ExpBool.class);
    }

    @Test
    public void CreadoraAmbValorsTest() throws NomExpressioIncorrecte, AutorIncorrecteException {

        ExpBool expbool = new ExpBool("E1", "!Joan");
        assertNotNull(expbool);
        assertEquals(expbool.getClass(), ExpBool.class);
        assertEquals("Correcte", "E1", expbool.getNomExpBool());
        assertEquals("Correcte", "!Joan", expbool.getExpressioAsString());
        //falta comparar el node
    }

    @Test
    public void GetNomExpBoolTest() throws NomExpressioIncorrecte, AutorIncorrecteException{
        ExpBool expbool = new ExpBool("E1", "Prova");
        assertEquals("Correcte", "E1", expbool.getNomExpBool());
    }

     @Test  //falta afegir el metode equals de node
    public void getExpressioParsedTest() throws NomExpressioIncorrecte{
        ExpBool expbool = new ExpBool("E1", "a&b");
        Node expected = new Node("&");
        Node a = new Node("a");
        Node b = new Node("b");
        expected.setDreta(a);
        expected.setEsquerre(b);
        assertTrue(expected.equals(expected,expbool.getExpressioParsed()));
    }


    @Test
    public void getExpressioAsStringTest() throws NomExpressioIncorrecte, AutorIncorrecteException{
        ExpBool expbool = new ExpBool("E1", "a&b");
        assertEquals("Correcte", "a&b", expbool.getExpressioAsString());   
    }

}
 