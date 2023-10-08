package domini.controladors;

import domini.classes.Document;
import domini.classes.IndexDocuments;
import domini.exceptions.AutorIncorrecteException;
import domini.exceptions.JaExisteixException;
import domini.exceptions.NoExisteixException;
import domini.exceptions.TitolIncorrecteException;
import domini.utils.Pair;
import domini.utils.Utils;

import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Math.sqrt;

public class CtrlDocument {

    // ---------- ATRIBUTS ----------
    private IndexDocuments indexDoc; // Variable que emmagatzema el index de Documents
    private CtrlParaula ControladorParaula; // Variable que emmagatzema el controlador de paraules
    private CtrlAutor ControladorAutor; // Variable que emmagatzema el controlador de autors
    private HashMap<Pair<String, String>, HashMap<String, Double>> TFIDF; // Variable que emmagatzema el TFIDF de tots els Documents

    // ---------- CONSTRUCTORES ----------
    public CtrlDocument(CtrlAutor ControladorAutors, CtrlParaula ControladorParaula) {
        indexDoc = new IndexDocuments();
        this.ControladorParaula = ControladorParaula;
        ControladorAutor = ControladorAutors;
        TFIDF = new HashMap<>();
    }

    // ---------- GETTERS ----------
    /**
     * Retorna el contingut d'un Document
     *
     * @param titol representa el titol del Document a buscar
     * @param autor representa el autor del Document a buscar
     * @return Un String que representa el contingut del Document
     */
    public String consultarContingut(String titol, String autor) throws NoExisteixException {
        String cont = indexDoc.consultarContingut(titol, autor);
        //return autor + "\n" + titol + "\n" + cont + "\n";
        return cont;
    }

    /**
     * Retorna el Document identificat amb els paràmetres proporcionats
     *
     * @param titol representa el titol del Document a buscar
     * @param autor representa el autor del Document a buscar
     * @return Un Document identificat amb el titol i autor donats
     **/
    public Document getDoc(String titol, String autor) throws NoExisteixException {
        Document doc = indexDoc.getDoc(titol, autor);
        return doc;
    }
    /**
     *
     * Indica si el contingut i les frases del Document estan en memoria
     *
     * @param titol representa el titol del Document a buscar
     * @param autor representa el autor del Document a buscar
     * @return Un booleà a 1 si està en memoria i a 0 si no hi és
     **/
    public Boolean estaEnMemoria(String titol, String autor)throws NoExisteixException{
        return indexDoc.estaEnMemoria(titol,autor);
    }

    /**
     * Retorna una llista amb els k identificadors de Documents dels Documents més
     * semblants al Document proporcionat segons el metode desitjat
     * Type == 1: Retorna la llista amb els Documents calculats mitjançant la
     * tècnica de TFIDF
     * Type != 1: Retorna la llista amb els Documents calculats mitjançant la
     * tècnica de BOW (Bag of Words)
     *
     * @param titol representa el titol del Document al qual es vol aplicar la semblança
     * @param autor representa el autor del Document al qual es vol aplicar la semblança
     * @param k representa el nombre de documents que es volen
     * @param type representa si es vol fer servir TF-IDF o BOW (1 - TF-IDF, 2 - BOW)
     * @return Un ArrayList<Pair<String,String>> on el primer valor: titol i el segon valor:
     *         autor | Per a k Documents
     **/
    public ArrayList<Pair<String, String>> consultarDocSemblanca(String titol, String autor, Integer k, Integer type,
                                                                 Integer ord) throws NoExisteixException {
        if (type == 1) {
            ArrayList<Pair<String, String>> res = consultarDocSemblancaTFIDF(titol, autor, k);
            return Utils.ordenarDocuments(res, ord);
        } else {
            ArrayList<Pair<String, String>> res = consultarDocSemblancaBOW(titol, autor, k);
            return Utils.ordenarDocuments(res, ord);
        }
    }

    /**
     * Retorna tots els identificadors de tots els Documents
     *
     * @return Un Set<Pair<String,String>> on el primer valor del pair es el titol i el segon el autor de cada Document
     **/
    public ArrayList<Pair<String, String>> allDocs() {
        return indexDoc.allDocuments();
    }

    /**
     * Retorna tots els documents
     * 
     * @return Un ArrayList<Document> amb tots els documents del sistema
     */
    public ArrayList<Document> allDocuments(){
        return indexDoc.getAllDocs();
    }

    // ---------- SETTERS ----------
    /**
     * Crea un nou Document identificat amb els parametres proporcionats si no està
     * ja creat, instància totes les paraules del contingut del text
     * i instància el seu autor o afegeix el Document a la seva llista de títols
     *
     *
     * @param titol representa el titol del Document a crear
     * @param autor representa el autor del Document a crear
     * @param contingut representa el contingut del Document a crear
     * @return Un Integer que es 1 si s'ha creat
     **/
    public Integer crearDoc(String titol, String autor, String contingut) throws JaExisteixException, TitolIncorrecteException, AutorIncorrecteException, NoExisteixException {
        indexDoc.crearDoc(titol, autor, contingut);
        ArrayList<ArrayList<String>> frases = indexDoc.getFrases(titol, autor);
        ArrayList<String> text = indexDoc.getDoc(titol, autor).consultarContingutList();
        for (String paraula : text) {
            ArrayList<String> par = new ArrayList<String>(Arrays.asList(paraula.split(" ")));
            for (String p : par) {
                String s = Utils.getParaulaPura(p).toLowerCase();
                for (ArrayList<String> frase : frases) {
                    if (frase.contains(s)) {
                        ControladorParaula.afegirParaula(s, titol, autor, frase);
                    }
                }
            }
        }
        try {
            ControladorAutor.getAutor(autor);
            ControladorAutor.afegirTitol(autor, titol);
        } catch (NoExisteixException e) {
            Set<String> titols = new HashSet<>();
            titols.add(titol);
            ControladorAutor.crearAutor(autor, titols);
        }
        return 1;
    }

    public void setTTL(String titol, String autor, int TTL){
        indexDoc.setTTL(titol,autor,TTL);
    }


    /**
     * Elimina el Document identificat amb els parametres proporcionats, si no
     * existeix, no fa res.
     * Si existeix, elimina totes les paraules del contingut del text i elimina el
     * titol de la llista de titols del autor
     *
     * @param titol representa el titol del Document a eliminar
     * @param autor representa el autor del Document a eliminar
     **/
    public void eliminarDoc(String titol, String autor) throws NoExisteixException {
        Document doc = indexDoc.getDoc(titol, autor);
        ArrayList<String> paraulesPerBorrar = doc.consultarContingutList();
        indexDoc.eliminarDoc(titol, autor);
        ControladorAutor.eliminarTitol(autor, titol);
        for (String paraula : paraulesPerBorrar) {
            ArrayList<String> par = new ArrayList<String>(Arrays.asList(paraula.split(" ")));
            for (String p : par) {
                paraula = Utils.getParaulaPura(p).toLowerCase();
                ControladorParaula.eliminarParaula(paraula, titol, autor);
            }

        }
    }

    /**
     * Modifica el contingut del Document identificat amb els parametres
     * proporcionats, si no existeix, crea el Document amb el contingut.
     * Si existeix, elimina totes les paraules del contingut anterior i crea les
     * noves
     *
     * @param titol representa el titol del Document a modificar
     * @param autor representa el autor del Document a modificar
     * @param nouContingut representa el nou contingut del Document
     * @return Un boolean que representa si s'ha modificat correctament
     **/
    public boolean modificarDoc(String titol, String autor, String nouContingut) throws NoExisteixException {
        Document doc = indexDoc.getDoc(titol, autor);
        ArrayList<String> paraulesPerBorrar = indexDoc.getDoc(titol, autor).consultarContingutList();
        for (String paraula : paraulesPerBorrar) {
            ArrayList<String> par = new ArrayList<String>(Arrays.asList(paraula.split(" ")));
            for (String p : par) {
                paraula = Utils.getParaulaPura(p).toLowerCase();
                ControladorParaula.eliminarParaula(paraula, titol, autor);
            }
        }
        doc.modificarContingut(nouContingut);
        ArrayList<ArrayList<String>> frases = indexDoc.getFrases(titol, autor);
        ArrayList<String> text = indexDoc.getDoc(titol, autor).consultarContingutList();
        for (String paraulaNova : text) {
            ArrayList<String> par = new ArrayList<String>(Arrays.asList(paraulaNova.split(" ")));
            for (String p : par) {
                String s = Utils.getParaulaPura(p).toLowerCase();
                for (ArrayList<String> frase : frases) {
                    if (frase.contains(s)) {
                        ControladorParaula.afegirParaula(s, titol, autor, frase);
                    }
                }
            }
        }
        return true;
    }

    /**
     * Estableix el nou contingut del Document i les noves frases i posa el booleà de memoria a true indicant que aquests dos atributs del Document són a memoria
     *
     * @param titol representa el titol del Document al qual es vol afegir el contingut
     * @param autor representa el autor del Document al qual es vol afegir el contingut
     * @param contingut representa el nou contingut del Document
     * @param frases representa les noves frases del Document
     **/
    public void setContingut(String titol, String autor, String contingut, ArrayList<ArrayList<String>> frases){
        indexDoc.setContingut(titol,autor,contingut, frases);
    }

    /**
     * Estableix el nou index de Documents
     *
     * @param id
     */
    public void setIndex(IndexDocuments id){
        this.indexDoc = id;
    }

    /**
     * Retorna una instància del index de Documents actual
     *
     * @return Un IndexDocuments que representa el index del sistema
     */
    public IndexDocuments getIndex(){
        return this.indexDoc;
    }

    // ---------- FUNCIONS PRIVADES ----------
    /**
     * Calcula el dot product mitjançant el TFIDF de tots els documents respecte el
     * Document de entrada
     *
     * @param titol representa el titol del Document del qual es vol calcular el Cosine Similarity
     * @param autor representa el autor del Document del qual es vol calcular el Cosine Similarity
     * @return Un ArrayList<Pair<Pair<String,String>, Double>> on el primer valor del
     *          pair es el titol i autor del Document i el segon representa la
     *          semblança amb el document proporcionat com entrada
     **/
    private ArrayList<Pair<Pair<String, String>, Double>> cosineSimilarityTFIDF(String titol, String autor) {
        Pair<String, String> doc = new Pair<>(titol, autor);
        HashMap<String, Double> TFIDFDoc = TFIDF.get(doc);
        ArrayList<Pair<Pair<String, String>, Double>> DocumentsSemblants = new ArrayList<>();
        double denominadorA = 0.0;
        for (Map.Entry<String, Double> A : TFIDFDoc.entrySet()) {
            double valorParaulaA = A.getValue();
            denominadorA += valorParaulaA * valorParaulaA;
        }
        for (Map.Entry<Pair<String, String>, HashMap<String, Double>> entry : TFIDF.entrySet()) { // Per cada TFIDF de
            // cada Document
            double numerador = 0.0;
            double denominadorB = 0.0;
            for (Map.Entry<String, Double> B : entry.getValue().entrySet()) { // Per cada paraula del TFIDF del
                // DocumentActual
                String paraula = B.getKey();
                double valorParaulaB = B.getValue();
                if (TFIDFDoc.get(paraula) != null) {
                    numerador += valorParaulaB * TFIDFDoc.get(paraula);
                }
                denominadorB += valorParaulaB * valorParaulaB;
            }
            if (denominadorB == 0.0 || denominadorA == 0.0) {
                Pair<Pair<String, String>, Double> docSemblant = new Pair<>(entry.getKey(), 0.0);
                DocumentsSemblants.add(docSemblant);
            } else {
                Pair<Pair<String, String>, Double> docSemblant = new Pair<>(entry.getKey(),
                        numerador / (sqrt(denominadorA) * sqrt(denominadorB)));
                DocumentsSemblants.add(docSemblant);
            }
        }
        return DocumentsSemblants;
    }

    /**
     * Calcula el TFIDF de tots els Documents
     *
     * @param IDFs representa els IDFs de totes les paraules
     * @param TFList representa la llista de TF de tots els Documents
     **/
    private void calcularTFIDF(HashMap<String, Double> IDFs,
                               HashMap<Pair<String, String>, HashMap<String, Double>> TFList) {
        TFIDF.clear();
        for (Map.Entry<Pair<String, String>, HashMap<String, Double>> entry : TFList.entrySet()) {
            HashMap<String, Double> mapAInsertar = new HashMap<>();
            for (Map.Entry<String, Double> pes : entry.getValue().entrySet()) {
                double val = (double) pes.getValue() * IDFs.get(pes.getKey());
                if (val != 0)
                    mapAInsertar.put(pes.getKey(), val);
            }
            TFIDF.put(entry.getKey(), mapAInsertar);
        }
    }

    /**
     * Calcula els K documents més semblants al Document proporcionat com entrada
     * mitjançant TFIDF i el dot Product
     *
     * @param titol representa el titol del Document el qual es vol calcular els documents per semblança
     * @param autor representa el autor del Document el qual es vol calcular els documents per semblança
     * @param k representa quants documents es vol de sortida
     * @return Un ArrayList<Pair<String,String>> que representa la llista de tots els Documents indicant
     *          el titol i el autor
     **/
    private ArrayList<Pair<String, String>> consultarDocSemblancaTFIDF(String titol, String autor, Integer k)
            throws NoExisteixException {
        indexDoc.getDoc(titol, autor);
        Pair<String, String> idDocABuscar = new Pair<>(titol, autor);
        HashMap<Pair<String, String>, HashMap<String, Double>> TFList = new HashMap<>();
        TFList = indexDoc.llistarTotsPesosTF();
        Integer numDocuments = TFList.size();
        HashMap<String, Double> IDFs;
        IDFs = ControladorParaula.recalcularIDF(numDocuments);
        TFIDF.clear();
        calcularTFIDF(IDFs, TFList);
        ArrayList<Pair<String, String>> resultat = new ArrayList<>();
        ArrayList<Pair<Pair<String, String>, Double>> totsDocuments = cosineSimilarityTFIDF(titol, autor);
        totsDocuments.sort((o1, o2) -> o2.second().compareTo(o1.second()));
        for (Pair<Pair<String, String>, Double> docs : totsDocuments) {
            if (k <= 0) {
                return resultat;
            }
            if (!docs.first().equals(idDocABuscar)) {
                resultat.add(docs.first());
                --k;
            }
        }
        return resultat;

    }

    /**
     * Calcula el dot product mitjançant el BOW de tots els documents respecte el
     * Document de entrada
     *
     * @param titol representa el titol del Document el qual es vol calcular el Cosine Similarity en format BOW
     * @param autor representa el autor del Document el qual es vol calcular el Cosine Similarity en format BOW
     * @param BOWList representa la llista de ocurrencies de les paraules de tots els documents
     * @return Un ArrayList<Pair<Pair<String,String>, Double>> on el primer valor del
     *          pair es el titol i autor del Document i el segon representa la
     *          semblança amb el document proporcionat com entrada
     **/
    private ArrayList<Pair<Pair<String, String>, Double>> cosineSimilarityBOW(String titol, String autor,
                                                                              HashMap<Pair<String, String>, HashMap<String, Double>> BOWList) throws NoExisteixException {
        Pair<String, String> doc = new Pair<>(titol, autor);
        HashMap<String, Double> BOWDoc = indexDoc.llistarPesosBOW(titol, autor);
        ArrayList<Pair<Pair<String, String>, Double>> DocumentsSemblants = new ArrayList<>();
        double denominadorA = 0.0;
        for (Map.Entry<String, Double> A : BOWDoc.entrySet()) {
            double valorParaulaA = A.getValue();
            denominadorA += valorParaulaA * valorParaulaA;
        }
        for (Map.Entry<Pair<String, String>, HashMap<String, Double>> entry : BOWList.entrySet()) {// Per cada BOW de
            // cada Document
            double numerador = 0.0;
            double denominadorB = 0.0;
            for (Map.Entry<String, Double> B : entry.getValue().entrySet()) { // Per cada paraula del BOW del
                // DocumentActual
                String paraula = B.getKey();
                double valorParaulaB = B.getValue();
                if (BOWDoc.get(paraula) != null) {
                    numerador += valorParaulaB * BOWDoc.get(paraula);
                }
                denominadorB += valorParaulaB * valorParaulaB;
            }
            if (denominadorB == 0.0 || denominadorA == 0.0) {
                Pair<Pair<String, String>, Double> docSemblant = new Pair<>(entry.getKey(), 0.0);
                DocumentsSemblants.add(docSemblant);
            } else {
                Pair<Pair<String, String>, Double> docSemblant = new Pair<>(entry.getKey(),
                        numerador / (sqrt(denominadorA) * sqrt(denominadorB)));
                DocumentsSemblants.add(docSemblant);
            }
        }
        return DocumentsSemblants;
    }

    /**
     * Calcula els K documents més semblants al Document proporcionat com entrada
     * mitjançant BOW i el dot Product
     *
     * @param titol representa el titol del Document al qual es vol aplicar la semblança
     * @param autor representa el autor del Document al qual es vol aplicar la semblança
     * @param k representa el nombre de documents que es volen
     * @return Un ArrayList<Pair<String,String>> que representa la llista de tots els Documents indicant
     *          el titol i el autor
     **/
    private ArrayList<Pair<String, String>> consultarDocSemblancaBOW(String titol, String autor, Integer k)
            throws NoExisteixException {
        indexDoc.getDoc(titol, autor);
        Pair<String, String> idDocABuscar = new Pair<>(titol, autor);
        HashMap<Pair<String, String>, HashMap<String, Double>> BOWList = new HashMap<>();
        BOWList = indexDoc.llistarTotsPesosBOW();
        ArrayList<Pair<String, String>> resultat = new ArrayList<>();
        ArrayList<Pair<Pair<String, String>, Double>> totsDocuments = cosineSimilarityBOW(titol, autor, BOWList);
        totsDocuments.sort((o1, o2) -> o2.second().compareTo(o1.second()));
        for (Pair<Pair<String, String>, Double> docs : totsDocuments) {
            if (k <= 0) {
                return resultat;
            }
            if (!docs.first().equals(idDocABuscar)) {
                resultat.add(docs.first());
                --k;
            }
        }
        return resultat;
    }
}
