package test.domini;

import domini.classes.Document;
import domini.classes.IndexDocuments;
import domini.exceptions.AutorIncorrecteException;
import domini.exceptions.JaExisteixException;
import domini.exceptions.NoExisteixException;
import domini.exceptions.TitolIncorrecteException;
import domini.utils.Pair;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


public class IndexDocumentTest {
    
    private Document initdoc() throws NoExisteixException, JaExisteixException, TitolIncorrecteException, AutorIncorrecteException {
        ArrayList<String> contingut = new ArrayList<>();
        String s ="Hola. Aquest document, És una prova.";
        contingut = new ArrayList<String>(Arrays.asList(s.split(" ")));
        contingut.add(0, "Joan");
        contingut.add(0, "Prova");
        Document doc = new Document("Prova", "Joan", s);
        return doc;
    }

    @Test
    public void CreadoraTest(){
        IndexDocuments test = new IndexDocuments();
        assertNotNull(test);
        assertEquals(test.getClass(), IndexDocuments.class);
    }

    @Test
    public void crearDocTest() throws NoExisteixException, JaExisteixException, TitolIncorrecteException, AutorIncorrecteException{
        Document expected = initdoc();
        IndexDocuments test = new IndexDocuments();
        ArrayList<String> contingut = new ArrayList<>();
        String s ="Hola. Aquest document, És una prova.";
        contingut = new ArrayList<String>(Arrays.asList(s.split(" ")));
        contingut.add(0, "Joan");
        contingut.add(0, "Prova");

        test.crearDoc("Prova", "Joan", s);
        Document result = test.getDoc("Prova", "Joan");
        boolean correcte = expected.equals(result);
        assertTrue(correcte);
    }

    @Test
    public void allDocumentsTest() throws NoExisteixException, JaExisteixException, TitolIncorrecteException, AutorIncorrecteException{
        ArrayList<String> contingut = new ArrayList<>();
        IndexDocuments test = new IndexDocuments();
        test.crearDoc("T1","A1","");
        test.crearDoc("T2","A1","");
        test.crearDoc("T1","A2","");
        test.crearDoc("T2","A2","");
        ArrayList<Pair<String,String>> expected = new ArrayList<>();
        expected.add(new Pair<>("T1","A1"));
        expected.add(new Pair<>("T2","A1"));
        expected.add(new Pair<>("T1","A2"));
        expected.add(new Pair<>("T2","A2"));
        
        ArrayList<Pair<String,String>> result = test.allDocuments();

        assertTrue(expected.equals(result));
    }

    @Test
    public void eliminarDocTest() throws NoExisteixException, JaExisteixException, TitolIncorrecteException, AutorIncorrecteException{
        ArrayList<String> contingut = new ArrayList<>();
        IndexDocuments test = new IndexDocuments();
        test.crearDoc("T1","A1","");
        test.crearDoc("T2","A1","");
        test.crearDoc("T1","A2","");
        ArrayList<Pair<String,String>> expected = new ArrayList<>();
        expected.add(new Pair<>("T1","A1"));
        expected.add(new Pair<>("T2","A1"));
        
        test.eliminarDoc("T1", "A2");
        ArrayList<Pair<String,String>> result = test.allDocuments();

        assertTrue(expected.equals(result));
    }

    @Test
     public void consultarContingutTest() throws NoExisteixException, JaExisteixException, TitolIncorrecteException, AutorIncorrecteException{
        String s ="Hola. Aquest document, És una prova.";
        IndexDocuments test = new IndexDocuments();
        test.crearDoc("Prova", "Joan", s);
        assertEquals(s, test.consultarContingut("Prova", "Joan"));
    }

    @Test
    public void llistarPesosBOWTest() throws NoExisteixException, JaExisteixException, TitolIncorrecteException, AutorIncorrecteException{
        ArrayList<String> contingut = new ArrayList<>();
        String s ="Hola. Aquest document, És una prova.";
        contingut = new ArrayList<String>(Arrays.asList(s.split(" ")));
        contingut.add(0, "Joan");
        contingut.add(0, "Prova");
        IndexDocuments test = new IndexDocuments();
        test.crearDoc("Prova", "Joan", s);
        HashMap<String, Double> pesosExpected = new HashMap<>();
        pesosExpected.put("joan", 1.0);
        pesosExpected.put("prova", 2.0);
        pesosExpected.put("document", 1.0);
        pesosExpected.put("és", 1.0);
        pesosExpected.put("hola", 1.0);
        assertEquals(pesosExpected, test.llistarPesosBOW("Prova", "Joan"));
    }

    @Test
    public void llistarTotsPesosBOWTest() throws NoExisteixException, JaExisteixException, TitolIncorrecteException, AutorIncorrecteException{
        ArrayList<String> contingut = new ArrayList<>();
        String s ="Hola. Aquest document, És una prova.";
        contingut = new ArrayList<String>(Arrays.asList(s.split(" ")));
        contingut.add(0, "Joan");
        contingut.add(0, "Prova");
        IndexDocuments test = new IndexDocuments();
        test.crearDoc("Prova", "Joan", s);
        HashMap<Pair<String, String>, HashMap<String, Double>> pesosExpected = new HashMap<>();
        Pair<String, String> doc = new Pair<>("Prova", "Joan");
        HashMap<String, Double> pesos = new HashMap<>();
        pesos.put("joan", 1.0);
        pesos.put("prova", 2.0);
        pesos.put("document", 1.0);
        pesos.put("és", 1.0);
        pesos.put("hola", 1.0);
        pesosExpected.put(doc, pesos);
        assertEquals(pesosExpected, test.llistarTotsPesosBOW());
    }

    @Test
    public void llistarTotsPesosTFIDFTest() throws NoExisteixException, JaExisteixException, TitolIncorrecteException, AutorIncorrecteException{
        ArrayList<String> contingut = new ArrayList<>();
        String s ="Hola. Aquest document, És una prova.";
        contingut = new ArrayList<String>(Arrays.asList(s.split(" ")));
        contingut.add(0, "Joan");
        contingut.add(0, "Prova");
        IndexDocuments test = new IndexDocuments();
        test.crearDoc("Prova", "Joan", s);
        HashMap<Pair<String, String>, HashMap<String, Double>> pesosExpected = new HashMap<>();
        Pair<String, String> doc = new Pair<>("Prova", "Joan");
        HashMap<String, Double> pesos = new HashMap<>();
        pesos.put("joan", 0.125);
        pesos.put("prova", 0.25);
        pesos.put("document", 0.125);
        pesos.put("és", 0.125);
        pesos.put("hola", 0.125);
        pesosExpected.put(doc, pesos);
        assertEquals(pesosExpected, test.llistarTotsPesosTF());
    }

}
