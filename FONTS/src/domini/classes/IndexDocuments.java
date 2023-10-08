package domini.classes;

import domini.exceptions.AutorIncorrecteException;
import domini.exceptions.JaExisteixException;
import domini.exceptions.NoExisteixException;
import domini.exceptions.TitolIncorrecteException;
import domini.utils.Pair;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class IndexDocuments implements Serializable{

    // ---------- ATRIBUTS ----------
    private HashMap<Pair<String, String>, Document> Documents; // Variable amb el titol i el autor de cada Document i la
    // seva instancia

    // ---------- CONSTRUCTORES ----------
    public IndexDocuments() {
        Documents = new HashMap<>();
    }


    // ---------- GETTERS ----------
    /**
     * Retorna el Document identificat amb el titol i autor proporcionats
     *
     * @param titol representa el titol del Document que es busca
     * @param autor representa el autor del Document que es busca
     * @return Un Document amb el titol i autor proporcionats
     **/
    public Document getDoc(String titol, String autor) throws NoExisteixException {
        Pair<String, String> idDoc = new Pair<>(titol, autor);
        Document d = Documents.get(idDoc);
        if (d == null)
            throw new NoExisteixException("No existeix el Document");
        else
            return d;
    }

    /**
     * Retorna les frases del Document identificat amb el titol i autor proporcionats
     *
     * @param titol representa el titol del Document que es busca
     * @param autor representa el autor del Document que es busca
     * @return Un ArrayList que representa les frases del Document
     **/
    public ArrayList<ArrayList<String>> getFrases(String titol, String autor) throws NoExisteixException {
        Document doc = getDoc(titol, autor);
        return doc.getFrases();
    }

    /**
     * Retorna tots els Documents
     *
     * @return Un ArrayList<Document> que representa tots els Documents del sistema
     */
    public ArrayList<Document> getAllDocs(){
        ArrayList<Document> docs = new ArrayList<>();
        for(Map.Entry<Pair<String, String>,Document> entry : Documents.entrySet()) {      
           docs.add(entry.getValue());
        } 
        return docs;
    }

    /**
     * Retorna el BOW del Document amb el titol i autor proporcionats
     *
     * @param titol representa el titol del Document que es busca
     * @param autor representa el autor del Document que es busca
     * @return Un HashMap<String, Double> on el primer valor es cada paraula del Document i el segon el nombre de ocurrencies al Document
     **/
    public HashMap<String, Double> llistarPesosBOW(String titol, String autor) throws NoExisteixException {
        Document doc = getDoc(titol, autor);
        return doc.llistarBOW();
    }

    /**
     * Retorna els TF de tots els Documents
     *
     * @return Un HashMap<Pair<String,String>, HashMap<String,Double>> on el primer valor es el identificador del Document i el segon el TF del Document
     **/
    public HashMap<Pair<String, String>, HashMap<String, Double>> llistarTotsPesosTF() {
        HashMap<Pair<String, String>, HashMap<String, Double>> TFList = new HashMap<>();
        for (Map.Entry<Pair<String, String>, Document> entry : Documents.entrySet()) {
            Pair<String, String> doc = new Pair<>(entry.getKey().first(), entry.getKey().second());
            TFList.put(doc, entry.getValue().llistarTF());
        }
        return TFList;
    }

    /**
     * Retorna els BOW de tots els Documents
     *
     * @return Un HashMap<Pair<String,String>, HashMap<String,Double>> on el primer valor es el identificador del Document i el segon el BOW del Document
     **/
    public HashMap<Pair<String, String>, HashMap<String, Double>> llistarTotsPesosBOW() {
        HashMap<Pair<String, String>, HashMap<String, Double>> BOWList = new HashMap<>();
        for (Map.Entry<Pair<String, String>, Document> entry : Documents.entrySet()) {
            Pair<String, String> doc = new Pair<>(entry.getKey().first(), entry.getKey().second());
            BOWList.put(doc, entry.getValue().llistarBOW());
        }
        return BOWList;
    }

    /**
     * Retorna tots els identificadors de tots els Documents
     *
     * @return Set<Pair<String,String>> on el primer valor del pair es el titol i el segon el autor de cada Document
     **/
    public ArrayList<Pair<String, String>> allDocuments() {
        ArrayList<Pair<String, String>> result = new ArrayList<>();

        for (Pair<String, String> idDoc : Documents.keySet()) {
            result.add(idDoc);
        }
        return result;
    }

    // ---------- SETTERS ----------
    /**
     * Crea una nova instancia de la classe Document amb els paràmetres proporcionats i el introdueix al index de Documents
     *
     * @param titol representa el titol del Document que es busca
     * @param autor representa el autor del Document que es busca
     * @param contingut representa el contingut del Document que es vol crear
     **/
    public void crearDoc(String titol, String autor, String contingut) throws JaExisteixException, TitolIncorrecteException, AutorIncorrecteException {
        Pair<String, String> doc = new Pair<>(titol, autor);
        if (Documents.get(doc) != null)
            throw new JaExisteixException("Document ja existeix");
        Document newDoc = new Document(titol, autor, contingut);
        Pair<String, String> idDoc = new Pair<>(titol, autor);
        Documents.put(idDoc, newDoc);
    }

    /**
     * Afegeix el Document passat per paràmetre al conjunt de Documents del sistema
     *
     * @param d representa el Document que es vol afegir al sistema
     **/
    public void setDoc(Document d){
        Pair<String, String> doc = new Pair<>(d.getTitol(), d.getAutor());
        Documents.put(doc,d);
    }

    /**
     * Elimina el Document identificat amb els paràmetres proporcionats, del index
     *
     * @param titol representa el titol del Document que es busca
     * @param autor representa el autor del Document que es busca
     **/
    public void eliminarDoc(String titol, String autor) {
        Pair<String, String> idDoc = new Pair<>(titol, autor);
        Documents.remove(idDoc);
    }

    /**
     * Retorna el contingut del document identifica amb els parametres proporcionats
     *
     * @param titol representa el titol del Document que es busca
     * @param autor representa el autor del Document que es busca
     * @return Un String que representa el contingut del Document buscat
     */
    public String consultarContingut(String titol, String autor) throws NoExisteixException {
        Pair<String, String> idDoc = new Pair<>(titol, autor);
        Document doc = Documents.get(idDoc);
        if (doc == null)
            throw new NoExisteixException("No existeix el document");
        return doc.consultarContingut();
    }

    /**
     * Indica si el contingut i les frases del Document estan en memoria
     *
     * @param titol representa el titol del Document que es busca
     * @param autor representa el autor del Document que es busca
     * @return Un booleà a 1 si està en memoria i a 0 si no hi és
     **/
    public Boolean estaEnMemoria(String titol, String autor) throws NoExisteixException{
        Pair<String, String> idDoc = new Pair<>(titol, autor);
        Document doc = Documents.get(idDoc);
        if(doc == null) throw new NoExisteixException("No existeix el document");
        return doc.estaEnMemoria();
    }


    /**
     * Estableix el nou contingut del Document i les noves frases i posa el booleà de memoria a true indicant que aquests dos atributs del Document són a memoria
     *
     * @param titol representa el titol del Document que es busca
     * @param autor representa el autor del Document que es busca
     * @param contingut el nou contingut del Document
     * @param frases les noves frases del Document
     **/
    public void setContingut(String titol, String autor, String contingut, ArrayList<ArrayList<String>> frases){
        Pair<String, String> idDoc = new Pair<>(titol, autor);
        Document doc = Documents.get(idDoc);
        doc.setContingut(contingut, frases);
    }


    /**
     * Retorna el TTL (Time-To-Live) del Document
     *
     * @param titol representa el titol del Document que es busca
     * @param autor representa el autor del Document que es busca
     * @param TTL representa el nou Time-To-Live del Document
     **/
    public void setTTL(String titol, String autor, int TTL){
        Pair<String, String> idDoc = new Pair<>(titol, autor);
        Document doc = Documents.get(idDoc);
        doc.setTTL(TTL);
    }
}
