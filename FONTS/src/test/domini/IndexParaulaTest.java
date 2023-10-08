package test.domini;

import domini.classes.IndexParaula;
import domini.exceptions.NoExisteixException;
import domini.utils.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static java.lang.Math.log;
import static org.junit.Assert.*;



public class IndexParaulaTest {

    @Test 
    public void creadoraIndexParaula() {
        IndexParaula i = new IndexParaula();
        assertNotNull(i);
        assertEquals(i.getClass(), IndexParaula.class);
    }

    @Test 
    public void afegirParaula() throws NoExisteixException {
        IndexParaula p = new IndexParaula();
        ArrayList<String> frase = new ArrayList<>();
        frase.add("primera frase prova");
        frase.add("segona frase per provar");
        boolean b = p.afegirParaula("prova", "titol_prova", "autor_prova", frase);
        assertTrue(b);
        
        ArrayList<String> frase2 = new ArrayList<>();
        frase2.add("prova afegint mateixa paraula");
        boolean b2 = p.afegirParaula("prova", "titol_prova2", "autor_prova2", frase2);
        assertTrue(b2);

        frase = new ArrayList<>();
        frase.add("mateix titol i autor per mateixa paraula");
        boolean b3 = p.afegirParaula("prova", "titol_prova2", "autor_prova2", frase);
        assertFalse(b3);

        frase2 = new ArrayList<>();
        frase2.add("diferent prova amb titol i autor ja guardats");
        boolean b4 = p.afegirParaula("hola", "titol_prova", "autor_prova", frase2);
        assertTrue(b4);
    }

    @Test 
    public void getParaula() throws NoExisteixException {
        ArrayList<String> frase =  new ArrayList<>();
        frase.add("prova a getParaula()");
        IndexParaula  p = new IndexParaula();
        p.afegirParaula("abcd", "titol", "autor", frase);
        assertEquals("abcd", p.getParaula("abcd").getParaula());
    }

    @Test 
    public void getNumTotalParaules() throws NoExisteixException {
        IndexParaula p = new IndexParaula();
        Integer resultat = 0;
        assertEquals(resultat, p.getNumTotalParaules());
        
        ArrayList<String> frase = new ArrayList<>();
        frase.add("aixo es la frase 1");
        p.afegirParaula("paraula1", "titol1", "autor1", frase);
        ArrayList<String> frase2 = new ArrayList<>();
        frase2.add("aixo es la frase 2");
        p.afegirParaula("paraula2", "titol2", "autor2", frase2);
        ArrayList<String> frase3 = new ArrayList<>();
        frase3.add("aixo es la frase 3");
        p.afegirParaula("paraula1", "titol3", "autor3", frase3);
        resultat = 2;
        assertEquals(resultat, p.getNumTotalParaules());
    }

    @Test 
    public void recalcularIDF() throws NoExisteixException {
        IndexParaula p = new IndexParaula();
        ArrayList<String> frase = new ArrayList<>();
        frase.add("aquesta frase conte la paraula prova");
        p.afegirParaula("prova", "titol", "autor", frase);
        ArrayList<String> frase2 = new ArrayList<>();
        frase2.add("aquesta segona frase tambe conte la paraula prova");
        p.afegirParaula("prova", "titol2", "autor2", frase2);
        ArrayList<String> frase3 = new ArrayList<>();
        frase3.add("nova frase 3");
        p.afegirParaula("nova", "titol3", "autor3", frase3);
        HashMap<String, Double> map = p.recalcularIDF(3);
        HashMap<String, Double> prova_map = new HashMap<>();
        Double idf_paraula1 = log((double)3/2);
        prova_map.put("prova", idf_paraula1);
        Double idf_paraula2 = log((double)3/1);
        prova_map.put("nova", idf_paraula2);
        assertEquals(prova_map, map);
    }

    @Test 
    public void eliminarOcurrencia() throws NoExisteixException {
        IndexParaula p = new IndexParaula();
        ArrayList<String> frase = new ArrayList<>();
        frase.add("aquesta frase es una prova");
        p.afegirParaula("prova", "titol", "autor", frase);
        p.eliminarOcurrencia("prova", "titol", "autor");
        try {
            p.getParaula("prova");
        }
        catch (NoExisteixException e){
            assertNull(null);
        }
    }

    @Test 
    public void getFrasesParaula() throws NoExisteixException {
        IndexParaula p = new IndexParaula();
        ArrayList<String> frase = new ArrayList<>();
        p.afegirParaula("prova", "titol", "autor", frase);
        HashSet<Pair<Pair<String, String>, ArrayList<String>>> prova_set = new HashSet<>();
        Pair<String, String> x = new Pair<>("titol", "autor");
        prova_set.add(new Pair<>(x, frase));
        assertEquals(prova_set, p.getFrasesParaula("prova"));

        ArrayList<String> frase2 = new ArrayList<>();
        frase2.add("prova de frasesParaula");
        p.afegirParaula("prova", "titol2", "autor2", frase2);
        Pair<String, String> y = new Pair<>("titol2", "autor2");
        prova_set.add(new Pair<>(y, frase2));
        assertEquals(prova_set, p.getFrasesParaula("prova"));
    }

    @Test 
    public void getTotesFrases() throws NoExisteixException {
        IndexParaula p = new IndexParaula();
        ArrayList<String> frase = new ArrayList<>();
        p.afegirParaula("prova", "titol", "autor", frase);
        HashSet<Pair<Pair<String, String>, ArrayList<String>>> prova_set = new HashSet<>();
        Pair<String, String> x = new Pair<>("titol", "autor");
        prova_set.add(new Pair<>(x, frase));
        assertEquals(prova_set, p.getTotesFrases());

        ArrayList<String> frase1 = new ArrayList<>();
        frase1.add("Frase de paraula prova");
        p.afegirParaula("prova", "titol2", "autor2", frase1);
        Pair<String, String> y = new Pair<>("titol2", "autor2");
        prova_set.add(new Pair<>(y, frase1));
        ArrayList<String> frase2 = new ArrayList<>();
        frase2.add("Frase de paraula paraula");
        p.afegirParaula("paraula", "titol3", "autor3", frase2);
        Pair<String, String> z = new Pair<>("titol3", "autor3");
        prova_set.add(new Pair<>(z, frase2));
        assertEquals(prova_set, p.getTotesFrases());
    }
}