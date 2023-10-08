package test.utils;
import domini.utils.Pair;
import org.junit.Test;
import static org.junit.Assert.*;


public class PairTest {

    @Test
    public void creadoraPairTest (){
        Pair<String, String> p = new Pair<String,String>("first", "second");

        assertEquals("first", p.first());
        assertEquals("second", p.second());
    }

    @Test
    public void getFirstTest(){
        Pair<String,String> x = new Pair<String, String>("first","second");  

        assertEquals("first", x.first());
    }

    @Test
    public void  getSecondTest(){
        Pair<String,String> x = new Pair<String, String>("first","second");  

        assertEquals("second", x.second());
    }

    public void setFirstTest(){
        Pair<String,String> x = new Pair<String, String>("first","second");  
        x.setFirst("hola");

        assertEquals("hola", x.first());
    }

    @Test
    public void setSecondTest(){
        Pair<String,String> x = new Pair<String, String>("first","second");  
        x.setSecond("hola");

        assertEquals("hola", x.second());
    }

    @Test
    public void testEquals_Symmetric() {
        Pair<String,String> x = new Pair<String, String>("first","second");  
        Pair<String,String> y = new Pair<String, String>("first", "second");
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.hashCode() == y.hashCode());
    }
}
