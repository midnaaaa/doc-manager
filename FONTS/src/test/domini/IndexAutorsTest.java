package test.domini;

import domini.classes.Autor;
import domini.classes.indexAutors;
import domini.exceptions.NoExisteixException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
public class IndexAutorsTest {

    @Test
    public void iniIndexAutor() {
        indexAutors indexAutor = new indexAutors();
        assertNotNull(indexAutor);
        assertEquals("creadora per defecte correcte", indexAutor.getClass(), indexAutors.class);
    }

    @Test
    public void creadoraAutor() throws NoExisteixException{
        Set<String> titols = setTitols();
        indexAutors inAu = new indexAutors();
        inAu.crearAutor("NomAutor",titols);

        Autor a = inAu.getAutor("NomAutor");
        assertEquals("NomAutor", a.getNom());
        assertEquals(titols, a.getTitols());
    }


    @Test
    public void testGetAutor() throws NoExisteixException{
        indexAutors inAu = new indexAutors();
        Set<String> titols = setTitols();
        inAu.crearAutor("NomAutor", titols);
        Autor a = inAu.getAutor("NomAutor");
        
        assertEquals("NomAutor", a.getNom());
        assertEquals(titols, a.getTitols());
    }

    @Test
    public void testGetAutorsPrefix() {
        indexAutors inAu = new indexAutors();
        Set<String> titols = setTitols();
        Set<String> alter = setAlternatiu();
        inAu.crearAutor("NomAutor", titols);
        inAu.crearAutor("autor2", alter);

        ArrayList<String> nomA = inAu.getAutorsPrefix("a");

        assertTrue(nomA.contains("autor2"));
    }

    @Test
    public void testEliminarAutor() {
        indexAutors inAu = new indexAutors();
        Set<String> titols = setTitols();
        inAu.crearAutor("NomAutor", titols);
        inAu.eliminarAutor("NomAutor");

        assertEquals("elimnarAutor correctament", 0, inAu.sizeAutor());
    }
    

    @Test
    public void testAfegirTitol() throws NoExisteixException {
        indexAutors inAu = new indexAutors();
        Set<String> titols = setTitols();
        inAu.crearAutor("NomAutor",titols);

        inAu.afegirTitol("NomAutor", "zzz");

        Autor a = inAu.getAutor("NomAutor");

        Set<String> alt = setAlternatiu();

        assertEquals("afegirTitol correctament", alt, a.getTitols());
    }

    @Test
    public void testEliminarTitol() throws NoExisteixException {
        indexAutors inAu = new indexAutors();
        Set<String> titols = setTitols();
        Set<String> alt =  setAlternatiu();
        inAu.crearAutor("NomAutor", alt);
        inAu.eliminarTitol("NomAutor", "zzz");

        Autor a = inAu.getAutor("NomAutor");
        Set<String> result = a.getTitols();

        assertEquals(result, titols);
    }

    public Set<String> setTitols() {
        Set<String> titols = new HashSet<String>();
        titols.add("aaa");
        titols.add("bbb");
        titols.add("ccc");
        titols.add("eee");

        return titols;
    }

    public Set<String> setAlternatiu() {
        Set<String> alternatiu = new HashSet<>();
        alternatiu.add("aaa");
        alternatiu.add("bbb");
        alternatiu.add("ccc");
        alternatiu.add("eee");
        alternatiu.add("zzz");
        return alternatiu;
    }
}