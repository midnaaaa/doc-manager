package test.domini;

import domini.classes.Autor;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

public class AutorTest {

    @Test
    public void iniAutor() {
        Autor autor = new Autor();
        assertNotNull(autor);
        assertEquals("creadora per defecte correcte",autor.getClass(), Autor.class);
    }

    @Test
    public void creadoraAutor() {
        Autor a1 = new Autor("angel");
        assertNotNull(a1);
        assertEquals("creadora amb nom","angel", a1.getNom());
    }

    @Test
    public void creadoraAutorComplet() {
        Set<String> titols = setTitols();
        Autor a1 = new Autor("angel",titols);
        assertNotNull(a1);
        assertEquals("creadora amb nom correcte", "angel", a1.getNom());
        assertEquals("creadora amb titols correcte", titols, a1.getTitols());
    }


    @Test
    public void testGetTitols() {
        Set<String> titols = setTitols();
        Autor autor = crearAutor("angel", titols);
        assertEquals("titolsCorrecte", titols, autor.getTitols());
    }

    @Test 
    public void testGetNom() {
        Set<String> titols = setTitols();
        String au = "angel";
        Autor autor = crearAutor(au, titols);
        assertEquals("nomCorrecte", au , autor.getNom());
    }

    @Test
    public void testAfegirTitol() {
        Set<String> titols = setTitols();
        Set<String> titolMes = setTitols();
        titolMes.add("zzz");

        Autor autor = crearAutor("angel", titols);
        autor.afegirTitol("zzz");

        assertEquals("afegirTitol", titolMes, autor.getTitols());
    }

    @Test
    public void testEliminarTitol(){
        Set<String> titols = setTitols();
        Set<String> titolMes = setTitols();
        titolMes.add("zzz");

        Autor autor = crearAutor("angel", titolMes);
        autor.eliminarTitol("zzz");

        assertEquals("afegirTitol", titols, autor.getTitols());
    }

    public Autor crearAutor(String nomAutor, Set<String> titols) {
        return new Autor(nomAutor, titols); 
    }

    public Set<String> setTitols() {
        Set<String> titols = new HashSet<String>();
        titols.add("aaa");
        titols.add("bbb");
        titols.add("ccc");
        titols.add("ddd");
        titols.add("eee");

        return titols;
    }

}
 



