package test.utils;

import domini.utils.Utils;
import domini.utils.Pair;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.ArrayList;


public class UtilsTest {
    
    @Test
    public void paraulaValidaTest() {
        String a = "yo";
        String b = "fib";
        
        assertEquals(false, Utils.paraulaValida(a));
        assertTrue(Utils.paraulaValida(b));
    }

    @Test
    public void getParaulaPuraTest() {
        String paraula = " ";
        assertEquals(paraula, Utils.getParaulaPura(paraula));

        paraula = "interrogant?";
        assertEquals("interrogant", Utils.getParaulaPura(paraula));

        paraula = "exclamacio!";
        assertEquals("exclamacio", Utils.getParaulaPura(paraula));

        paraula = "¿interrogant?";
        assertEquals("interrogant", Utils.getParaulaPura(paraula));

        paraula = "¡exclamacio!";
        assertEquals("exclamacio", Utils.getParaulaPura(paraula));

        paraula = "punt.";
        assertEquals("punt", Utils.getParaulaPura(paraula));
        
        paraula = "coma,";
        assertEquals("coma", Utils.getParaulaPura(paraula));
    } 

    @Test
    public void interseccioTest() {
        HashSet<Pair<Pair<String, String>, ArrayList<String>>> fraseEsq = new HashSet<>();
        HashSet<Pair<Pair<String, String>, ArrayList<String>>> fraseDreta = new HashSet<>();
        HashSet<Pair<Pair<String, String>, ArrayList<String>>> resultat = new HashSet<>();
        assertEquals(resultat, Utils.interseccio(fraseEsq, fraseDreta));

        Pair<String, String> p1 = new Pair<>("titol1", "autor1");
        ArrayList<String> pp1 = new ArrayList<>();
        pp1.add("Aixo es frase prova1");
        fraseEsq.add(new Pair<>(p1, pp1));
        Pair<String, String> p2 = new Pair<>("titol2", "autor1");
        ArrayList<String> pp2 = new ArrayList<>();
        pp2.add("frase prova2");
        fraseDreta.add(new Pair<>(p2, pp2));
        assertEquals(resultat, Utils.interseccio(fraseEsq, fraseDreta));

        Pair<String, String> p3 = new Pair<>("titol3", "autor3");
        ArrayList<String> pp3 = new ArrayList<>();
        pp3.add("hola");
        fraseEsq.add(new Pair<>(p3, pp3));
        Pair<String, String> p4 = new Pair<>("titol3", "autor3");
        ArrayList<String> pp4 = new ArrayList<>();
        pp4.add("hola");
        fraseDreta.add(new Pair<>(p4, pp4));
        resultat.add(new Pair<>(p3, pp3));
        assertEquals(resultat, Utils.interseccio(fraseEsq, fraseDreta));
    } 

    @Test
    public void unioTest() {
        HashSet<Pair<Pair<String, String>, ArrayList<String>>> fraseEsq = new HashSet<>();
        HashSet<Pair<Pair<String, String>, ArrayList<String>>> fraseDreta = new HashSet<>();
        HashSet<Pair<Pair<String, String>, ArrayList<String>>> resultat = new HashSet<>();
        assertEquals(resultat, Utils.unio(fraseEsq, fraseDreta));

        Pair<String, String> p1 = new Pair<>("titol1", "autor1");
        ArrayList<String> pp1 = new ArrayList<>();
        pp1.add("Aixo es frase prova1");
        fraseEsq.add(new Pair<>(p1, pp1));
        Pair<String, String> p2 = new Pair<>("titol1", "autor1");
        ArrayList<String> pp2 = new ArrayList<>();
        pp2.add("Aixo es frase prova1");
        fraseDreta.add(new Pair<>(p2, pp2));
        resultat.add(new Pair<>(p1, pp1));
        assertEquals(resultat, Utils.unio(fraseEsq, fraseDreta));

        Pair<String, String> p3 = new Pair<>("titol3", "autor3");
        ArrayList<String> pp3 = new ArrayList<>();
        pp3.add("hola");
        fraseEsq.add(new Pair<>(p3, pp3));
        Pair<String, String> p4 = new Pair<>("titol4", "autor4");
        ArrayList<String> pp4 = new ArrayList<>();
        pp4.add("adeu");
        fraseDreta.add(new Pair<>(p4, pp4));
        resultat.add(new Pair<>(p3, pp3));
        resultat.add(new Pair<>(p4, pp4));
        assertEquals(resultat, Utils.unio(fraseEsq, fraseDreta));
    } 

    @Test
    public void restaTest() {
        HashSet<Pair<Pair<String, String>, ArrayList<String>>> fraseNot = new HashSet<>();
        HashSet<Pair<Pair<String, String>, ArrayList<String>>> totesfrases = new HashSet<>();
        HashSet<Pair<Pair<String, String>, ArrayList<String>>> resultat = new HashSet<>();
        assertEquals(resultat, Utils.resta(totesfrases, fraseNot));

        Pair<String, String> p = new Pair<>("titol", "autor");
        ArrayList<String> frase = new ArrayList<>();
        frase.add("primera frase");
        fraseNot.add(new Pair<>(p, frase));
        totesfrases.add(new Pair<>(p, frase));
        assertEquals(resultat, Utils.resta(totesfrases, fraseNot));
    } 

    @Test
    public void parentesisCorrectesTest() {
        String prova = "{(}()({})";
        boolean resultat = false;
        assertEquals(resultat, Utils.parentesisCorrectes(prova));

        prova = " ";
        resultat = true;
        assertEquals(resultat, Utils.parentesisCorrectes(prova));

        prova = "{}(){()}({})";
        assertEquals(resultat, Utils.parentesisCorrectes(prova));
    } 

    @Test
    public void replaceBracketsTest() {
        String prova = " ";
        String resultat = " ";
        assertEquals(resultat, Utils.replaceBrackets(prova));

        prova = "{()}";
        resultat = "(())";
        assertEquals(resultat, Utils.replaceBrackets(prova));
    }
    
    @Test
    public void ordenarTitolsOAutorsTest() {
       Integer ord = 1;
       ArrayList<String> prova = new ArrayList<>();
       prova.add("hola");
       prova.add("adeu");
       prova.add("bon dia");
       prova.add("fins despres");
       ArrayList<String> resultat = new ArrayList<>();
       resultat.add("adeu");
       resultat.add("bon dia");
       resultat.add("fins despres");
       resultat.add("hola");
       assertEquals(resultat, Utils.ordenarTitolsOAutors(prova, ord));

       ord = 0;
       resultat = new ArrayList<>();
       resultat.add("hola");
       resultat.add("fins despres");
       resultat.add("bon dia");
       resultat.add("adeu");
       assertEquals(resultat, Utils.ordenarTitolsOAutors(prova, ord));
    } 

    @Test
    public void ordenarDocumentsTest() {
       Integer ord = 0;
       ArrayList<Pair<String, String>> prova = new ArrayList<>();
       prova.add(new Pair<>("titol1", "autor1"));
       prova.add(new Pair<>("titol2", "autor2"));
       prova.add(new Pair<>("titol3", "autor3"));
       prova.add(new Pair<>("titol4", "autor4"));
       ArrayList<Pair<String, String>> resultat = new ArrayList<>();
       resultat.add(new Pair<>("titol1", "autor1"));
       resultat.add(new Pair<>("titol2", "autor2"));
       resultat.add(new Pair<>("titol3", "autor3"));
       resultat.add(new Pair<>("titol4", "autor4"));
       assertEquals(resultat, Utils.ordenarDocuments(prova, ord));

       ord = 1;
       prova = new ArrayList<>();
       prova.add(new Pair<>("titol3", "autor3"));
       prova.add(new Pair<>("titol2", "autor2"));
       prova.add(new Pair<>("titol4", "autor4"));
       prova.add(new Pair<>("titol1", "autor1"));
       assertEquals(resultat, Utils.ordenarDocuments(prova, ord));

       ord = 2;
       prova = new ArrayList<>();
       prova.add(new Pair<>("titol1", "autor1"));
       prova.add(new Pair<>("titol4", "autor4"));
       prova.add(new Pair<>("titol2", "autor2"));
       prova.add(new Pair<>("titol3", "autor3"));
       resultat = new ArrayList<>();
       resultat.add(new Pair<>("titol4", "autor4"));
       resultat.add(new Pair<>("titol3", "autor3"));
       resultat.add(new Pair<>("titol2", "autor2"));
       resultat.add(new Pair<>("titol1", "autor1"));
       assertEquals(resultat, Utils.ordenarDocuments(prova, ord));
    } 

}
