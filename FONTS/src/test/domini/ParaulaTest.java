package test.domini;

import domini.classes.Paraula;
import domini.utils.Pair;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.ArrayList;
import static java.lang.Math.log;


public class ParaulaTest {

    @Test
    public void creadoraParaula() {
        Paraula p = new Paraula();
        assertNotNull(p);
        assertEquals(p.getClass(), Paraula.class);
    }

    @Test
    public void creadoraParaulaParametres() {
        ArrayList<String> frase = new ArrayList<>();
        frase.add("Aixo es una paraula");
        Paraula p = new Paraula("paraula", "titol_prova", "autor_prova", frase);
        String titol_prova = "titol_prova";
        String autor_prova = "autor_prova";
        HashSet<Pair<Pair<String, String>, ArrayList<String>>> frase_prova = new HashSet<>();
        Pair<String, String> pair_prova = new Pair<>(titol_prova, autor_prova);
        frase_prova.add(new Pair<>(pair_prova, frase));
        Integer resultat = 1;
        assertEquals(resultat, p.getNumOcurrencies());
        assertEquals("paraula", p.getParaula());
        assertEquals(frase_prova, p.getFrases());
    }

    @Test
    public void afegirOcurrencia() {
        ArrayList<String> frase = new ArrayList<>();
        frase.add("Pokemon Platino");
        Paraula p = new Paraula("Pokemon", "Mewtwo Shiny", "Knekro", frase);
        String titol_prova = "titol_prova";
        String autor_prova = "autor_prova";
        ArrayList<String> frase_prova = new ArrayList<>();
        boolean res = p.afegirOcurrencia(titol_prova, autor_prova, frase_prova);
        Integer resultat = 2;
        assertEquals(resultat, p.getNumOcurrencies());
        res = p.afegirOcurrencia(titol_prova, autor_prova, frase_prova);
        assertFalse(res);
    }

    @Test
    public void getNumOcurrencies() {
        ArrayList<String> frase_1 = new ArrayList<>();
        ArrayList<String> frase_2 = new ArrayList<>();
        frase_2.add("Trabaja, plasma las palabras hazlas balas, haz rap a ráfagas");
        String titol_prova1 = " ";
        String autor_prova1 = " ";
        String titol_prova2 = "Efectos vocales";
        String autor_prova2 = "Nach";
        Paraula p1 = new Paraula( " ",titol_prova1, autor_prova1, frase_1);
        p1.afegirOcurrencia(titol_prova2, autor_prova2, frase_2);
        Integer resultat = 2;
        assertEquals(resultat, p1.getNumOcurrencies());
    } 

    @Test
    public void recalcularIDF() {
        String paraula = "càlcul";
        ArrayList<String> frase_1 = new ArrayList<>();
        frase_1.add("Les multiplicacions son importants en el càlcul");
        Paraula p = new Paraula(paraula, "Matemàtiques 2", "Gauss", frase_1);
        ArrayList<String> frase_2 = new ArrayList<>();
        frase_2.add("Aritmètica i càlcul, càlcul i aritmètica");
        p.afegirOcurrencia("Aritmètica", " ", frase_2);
        Integer numDocuments = 5;
        Double resultat = log((double)numDocuments/2);
        Double IDF = p.recalcularIDF(numDocuments);
        assertEquals(resultat, IDF);
    }

    @Test 
    public void eliminarOcurrencia() {
        ArrayList<String> frase = new ArrayList<>();
        frase.add("aaaaaaaaaaaaaaaaaaaaaaaaa");
        Paraula p = new Paraula("a", "Titol", "Mario", frase);
        p.eliminarOcurrencia("Titol", "Mario");
        Integer resultat = 0;
        assertEquals(resultat, p.getNumOcurrencies());
    }

    @Test 
    public void getFrases() {
        HashSet<Pair<Pair<String, String>, ArrayList<String>>> f = new HashSet<>();
        ArrayList<String> frase = new ArrayList<>();
        frase.add("Prova getFrases amb paraula");
        Paraula p = new Paraula("paraula", "Titol", "Autor", frase);
        Pair<String, String> doc = new Pair<>("Titol", "Autor");
        Pair<Pair<String, String>, ArrayList<String>> tot = new Pair<>(doc, frase);
        f.add(tot);
        assertEquals(f, p.getFrases());
    }

    @Test 
    public void getParaula() {
        ArrayList<String> frase = new ArrayList<>();
        frase.add("Aixo es una paraula");
        Paraula p = new Paraula("paraula", "titol_prova", "autor_prova", frase);
        assertEquals("paraula", p.getParaula());
    }
}
