
package test.domini;

import domini.classes.Document;
import domini.exceptions.AutorIncorrecteException;
import domini.exceptions.TitolIncorrecteException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class DocumentTest {

    @Test 
    public void creadoraDocument() {
        Document d = new Document();
        assertNotNull(d);
        assertEquals(d.getClass(), Document.class);
    }

    @Test 
    public void creadoraDocumentParametres() throws TitolIncorrecteException, AutorIncorrecteException {
        String s = "Això es el contingut del Document d";
        Document d = new Document("titol_prova", "autor_prova", s);
        assertEquals("titol_prova", d.getTitol());
        assertEquals("autor_prova", d.getAutor());
        assertEquals(s, d.consultarContingut());
    }

    @Test 
    public void calcul_llistarTF() throws TitolIncorrecteException, AutorIncorrecteException{
        String s = "Document de prova per veure si la funció llistarTF() funciona correctament.";
        ArrayList<String> text = new ArrayList<String>(Arrays.asList(s.split(" ")));
        Document d = new Document("titol_prova", "autor_prova", s);
        HashMap<String, Double> prova_TF = new HashMap<>();
        Double freq = 1.0/(text.size()+2);
        prova_TF.put("document", freq);
        prova_TF.put("prova", freq);
        prova_TF.put("veure", freq);
        prova_TF.put("funció", freq);
        prova_TF.put("llistartf()", freq);
        prova_TF.put("funciona", freq);
        prova_TF.put("correctament", freq);
        prova_TF.put("titol_prova", freq);
        prova_TF.put("autor_prova", freq);
        assertEquals(prova_TF, d.llistarTF());
    }

    @Test 
    public void calcul_llistarBOW() throws TitolIncorrecteException, AutorIncorrecteException{
        String s = "Document de prova per veure si la funció llistarBOW() funciona correctament.";
        Document d = new Document("titol_prova", "autor_prova", s);
        HashMap<String, Double> prova_BOW = new HashMap<>();
        prova_BOW.put("document", 1.0);
        prova_BOW.put("prova", 1.0);
        prova_BOW.put("veure", 1.0);
        prova_BOW.put("funció", 1.0);
        prova_BOW.put("llistarbow()", 1.0);
        prova_BOW.put("funciona", 1.0);
        prova_BOW.put("correctament", 1.0);
        prova_BOW.put("titol_prova", 1.0);
        prova_BOW.put("autor_prova", 1.0);
        assertEquals(prova_BOW, d.llistarBOW());
    }

    @Test 
    public void consultarContingut() throws TitolIncorrecteException, AutorIncorrecteException{
        String s = "Document de prova per veure si la funció llistarContingut() funciona correctament.";
        Document d = new Document("titol_prova", "autor_prova", s);
        assertEquals(s, d.consultarContingut());
    }

    @Test 
    public void getFrases() throws TitolIncorrecteException, AutorIncorrecteException{
        ArrayList<ArrayList<String>> prova_frase = new ArrayList<>();
        String s = "Primera frase acaba amb. Segona frase acaba amb? Tercera frase acaba amb!";
        Document d2 = new Document("titol_prova", "autor_prova", s);

        ArrayList<String> frase4 = new ArrayList<>();
        frase4.add("titol_prova");
        ArrayList<String> frase5 = new ArrayList<>();
        frase5.add("autor_prova");;
        ArrayList<String> frase1 = new ArrayList<>();
        frase1.add("primera");
        frase1.add("frase");
        frase1.add("acaba");
        frase1.add("amb");
        ArrayList<String> frase2 = new ArrayList<>();
        frase2.add("segona");
        frase2.add("frase");
        frase2.add("acaba");
        frase2.add("amb");
        ArrayList<String> frase3 = new ArrayList<>();
        frase3.add("tercera");
        frase3.add("frase");
        frase3.add("acaba");
        frase3.add("amb");

        prova_frase.add(frase4);
        prova_frase.add(frase5);
        prova_frase.add(frase1);
        prova_frase.add(frase2);
        prova_frase.add(frase3);
        assertEquals(prova_frase, d2.getFrases());

        prova_frase.clear();
        s = "¿I si provo d'aquesta manera? Si, molt bé. ¡AAAAA!";
        Document d3 = new Document("titol_prova2", "autor_prova2", s);
        frase4 = new ArrayList<>();
        frase4.add("titol_prova2");
        frase5 = new ArrayList<>();
        frase5.add("autor_prova2");;
        frase1 = new ArrayList<>();
        frase1.add("i");
        frase1.add("si");
        frase1.add("provo");
        frase1.add("d'aquesta");
        frase1.add("manera");
        frase2 = new ArrayList<>();
        frase2.add("si");
        frase2.add("molt");
        frase2.add("bé");
        frase3 = new ArrayList<>();
        frase3.add("aaaaa");
        prova_frase = new ArrayList<>();
        prova_frase.add(frase4);
        prova_frase.add(frase5);
        prova_frase.add(frase1);
        prova_frase.add(frase2);
        prova_frase.add(frase3);
        assertEquals(prova_frase, d3.getFrases());
    }
    
    @Test 
    public void modificarContingut() throws TitolIncorrecteException, AutorIncorrecteException{
        String s = "Prova modificarContingut()";
        Document d2 = new Document("titol_prova2", "autor_prova2", s);
        String nouContingut = "";
        d2.modificarContingut(nouContingut);
        assertEquals(nouContingut, d2.consultarContingut());
        s = "Contingut modificat";
        ArrayList<String> aux = new ArrayList<>();
        nouContingut = s;
        d2.modificarContingut(nouContingut);
        assertEquals(nouContingut, d2.consultarContingut());
    }

    @Test 
    public void getTitol() throws TitolIncorrecteException, AutorIncorrecteException{
        Document d = new Document("titol_prova", "autor_prova", "");
        assertEquals("titol_prova", d.getTitol());
    }

    @Test 
    public void getAutor() throws TitolIncorrecteException, AutorIncorrecteException{
        Document d = new Document("titol_prova", "autor_prova", "");
        assertEquals("autor_prova", d.getAutor());
    }
}
