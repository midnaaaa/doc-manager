package domini.controladors;

import domini.classes.Document;
import domini.classes.IndexDocuments;
import domini.classes.IndexExpBool;
import domini.classes.IndexParaula;
import domini.classes.indexAutors;
import domini.exceptions.*;
import domini.utils.Pair;
import persistencia.controladors.CtrlPersistencia;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;

public class CtrlDomini {

    // ---------- ATRIBUTS ----------
    private CtrlAutor ControladorAutors = new CtrlAutor(); //Variable que emmagatzema el controlador d'autors
    private CtrlParaula ControladorParaula = new CtrlParaula(); //Variable que emmagatzema el controlador de paraules
    private CtrlDocument ControladorDocs = new CtrlDocument(ControladorAutors, ControladorParaula); //Variable que emmagatzema el controlador de documents
    private CtrlExpBool ControladorExpsBools = new CtrlExpBool(ControladorParaula);
    private CtrlPersistencia ControladorPersistencia = new CtrlPersistencia();
    private Timer timer = new Timer();
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            ArrayList<Document> docs = ControladorDocs.allDocuments();
            for(Document d : docs){
                if(d.estaEnMemoria()){
                    int TTLActual = d.getTTL();
                    d.setTTL(TTLActual -= 1);
                    if(TTLActual == 0){
                        guardarDoc(d);
                    }
                }
            }
        }
    };

    // ---------- CONSTRUCTORES ----------
    public CtrlDomini() {
    }

    public void iniciarTimer(){
        timer.schedule(task, 0, 1000 * 20);
    }



    // ---------- GETTERS ----------
    /**
     * Donat un enter k, retorna els k documents mes semblants, l'enter k selecciona quin algoritme a utilitzar entre TFIDF o BOW, i l'enter ord selecciona com ordenar els resultats
     *
     * @param titol representa el titol del Document del qual es vol buscar la semblança
     * @param autor representa el autor del Document del qual es vol buscar la semblança
     * @param k representa quants documents de sortida es volen
     * @param type representa si es vol fer servir BOW o TF-IDF
     * @param ord representa com ordenar la sortida (0 per semblança, 1 de forma ascendent, altrament forma descendent)
     * @return Un ArrayList<Pair<String, String>> que representa els Documents més semblants
     */
    public ArrayList<Pair<String, String>> consultarDocSemblanca(String titol, String autor, Integer k, Integer type, Integer ord) throws NoExisteixException {
        return ControladorDocs.consultarDocSemblanca(titol, autor, k, type, ord);
    }

    /**
     * Retorna el contingut del document identificat amb el titol i autor passats com a parametres
     *
     * @param titol representa el titol del Document del qual es vol consultar el contingut
     * @param autor representa el autor del Document del qual es vol consultar el contingut
     * @return Un String amb el contingut del Document donat
     */
    public String consultarContingut(String titol, String autor) throws NoExisteixException {
        if(!ControladorDocs.estaEnMemoria(titol,autor)){
            carregarContingut(titol,autor);
        }
        ControladorDocs.setTTL(titol, autor, 5);
        return ControladorDocs.consultarContingut(titol, autor);
    }

    /**
     * Retorna el conjunt de Titols que pertanyen a l'autor identifica amb el parametre nomAutor, ordenats segons l'enter ord
     *
     * @param nomAutor representa el nom del autor del qual es volen consultar els titols
     * @param ord representa la forma de ordenar la sortida (1 ascendentment, altrament descendentment)
     * @return Un ArrayList<String> que representa els titols del autor donat
     */
    public ArrayList<String> consultarTitolsAutor(String nomAutor, Integer ord) throws NoExisteixException {
        return ControladorAutors.getTitols(nomAutor, ord);
    }

    /**
     * Retorna els noms de tots els autors, el nom dels quals contingui el prefix passat com a parametre, ordenats segons l'enter ord
     *
     * @param prefix representa el prefix que es vol fer servir
     * @param ord representa la forma de ordenar la sortida (1 ascendentment, altrament descendentment)
     * @return Un ArrayList<String> que representa els autors que contenen el prefix donat
     */
    public ArrayList<String> consultarAutorPerPrefix(String prefix, Integer ord) {
        return ControladorAutors.getAutorsPrefix(prefix, ord);
    }

    /**
     * Retorna el contingut de l'expressió booleana identificada amb nomExp
     * 
     * @param nomExp representa el nom de la expressio a buscar
     * @return Un String que representa la expressio integra
     */
    public String consultaExpBool(String nomExp) throws NoExisteixException {
        return ControladorExpsBools.getExpBool(nomExp);
    }

    /**
     * Retorna el conjunt de documents que cumpleixen l'expressió booleana passada com a paràmetre, per a cada un retorna el titol i l'autor, ordenats segons l'enter ord
     *
     * @param nomExp representa el nom de la expressio que es vol fer servir per fer la consulta
     * @param ord representa la forma de ordenar la sortida (1 ascendentment, altrament descendentment)
     * @return Un ArrayList<Pair<String, String>> que representa tots els documents que compleixen la expressio booleana
     */
    public ArrayList<Pair<String, String>> consultaDocExpBool(String nomExp, Integer ord) throws NoExisteixException {
        return ControladorExpsBools.consultarDocExpBool(nomExp, ord);
    }

    // ---------- SETTERS ----------

    /**
     * Es crea un document amb el titol, autor i contingut passats com a paràmetres
     *
     * @param titol representa el titol del Document a crear
     * @param autor representa el autor del Document a crear
     * @param contingut representa el contingut del Document a crear
     * @return Un Integer que representa si s'ha creat el document amb exit
     */
    public Integer crearDoc(String titol, String autor, String contingut) throws JaExisteixException, TitolIncorrecteException, AutorIncorrecteException, NoExisteixException {
        return ControladorDocs.crearDoc(titol, autor, contingut);
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
        if(!ControladorDocs.estaEnMemoria(titol,autor)) {
            carregarContingut(titol,autor);
        }
        ControladorDocs.eliminarDoc(titol,autor);
        ControladorPersistencia.eliminarDoc(titol,autor);

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
        if(!ControladorDocs.estaEnMemoria(titol,autor)){
            carregarContingut(titol,autor);
        }
        ControladorDocs.setTTL(titol, autor, 5);
        return ControladorDocs.modificarDoc(titol, autor, nouContingut);
    }

    /**
     * Crea una expressió booleana amb nom i la expressio integra passats per paràmetres
     *
     * @param nomExp representa el nom de la expressio booleana a crear
     * @param expressio representa la expressio integra que es vol crear
     * @return Un Integer que indica si s'ha creat amb èxit
     */
    public Integer crearExpBool(String nomExp, String expressio)
            throws ExpressioNoValidaException, NomExpressioIncorrecte, JaExisteixException, NoExisteixException {
        return ControladorExpsBools.crearExpBool(nomExp, expressio);
    }

    /**
     * Modifica l'expressió booleana identificada amb el nom passat com a paràmetre
     *
     * @param nomExp representa el nom de la expressio booleana a modificar
     * @param expressio representa la expressio integra que es vol modificar
     * @return Un Integer que indica si s'ha modificat amb èxit
     */
    public Integer modificarExpBool(String nomExp, String expressio) throws ExpressioNoValidaException, JaExisteixException, NoExisteixException, NomExpressioIncorrecte {
        return ControladorExpsBools.modificarExpBool(nomExp, expressio);
    }

    /**
     * Elimina l'expressió booleana identificada amb el nom passat com a paràmetre
     * 
     * @param nomExp representa el nom de la expressio booleana a eliminar
     */
    public void eliminarExpBool(String nomExp) {
        ControladorExpsBools.eliminarExpBool(nomExp);
    }


    /**
     * Retorna una llista amb tots els identificadors de document del sistema
     *
     * @return Un ArrayList<Pair<String,String>> que representa tots els documents del sistema
     */
    public ArrayList<Pair<String, String>> allDocs() {
        return ControladorDocs.allDocs();
    }

    /**
     * Retorna una llista amb totes les expressions booleanes del sistema
     *
     * @return Un ArrayList<Pair<String,String>> que representa totes les expressions booleanes del sistema
     */
    public ArrayList<Pair<String, String>> allExpBool() {
        return ControladorExpsBools.allExpBool();
    }

    /**
     * Funcio que guarda tot a disc, es guarden uns serials dels índexs Paraula, ExpBool i Autor, i per a cada Document
     * es guarda un serial amb el tota la classe Document però amb el contingut i les frases a null i per cada document
     * un serial que representa el contingut del document i un altre serial que representa les frases del document
     */
    public void guardarADisc(){
        guardarIndexExpBoolDisc();
        guardarIndexParaulaDisc();
        guardarIndexAutorsDisc();
        guardarIndexDocsDisc();
    }
    /**
     * Funcio que carrega tot des de disc excepte el contingut i les frases de cada document
     */
    public void carregarDeDisc() throws NoExisteixException{
        carregarIndexExpBoolDisc();
        carregarIndexParaulaDisc();
        carregarIndexAutorsDisc();
        carregarIndexDocsDisc();
    }

    /**
     * Carrega el IndexExpBool des de el Disc
     */
    private void carregarIndexExpBoolDisc() throws NoExisteixException{
        IndexExpBool iexp = (IndexExpBool) ControladorPersistencia.carregarExps("IndexExpBool");
        ControladorExpsBools.setIndex(iexp);
    }

    /**
     * Guarda el IndexExpBool al disc
     */
    private void guardarIndexExpBoolDisc(){
        IndexExpBool iexp = ControladorExpsBools.getIndex();
        ControladorPersistencia.guardarExps((Object) iexp, "IndexExpBool");
    }

    /**
     * Carrega el IndexParaula des de el disc
     */
    private void carregarIndexParaulaDisc() throws NoExisteixException{
        IndexParaula ip = (IndexParaula) ControladorPersistencia.carregarParaules("IndexParaula");
        ControladorParaula.setIndex(ip);
    }

    /**
     * Guarda el IndexParaula al disc
     */
    private void guardarIndexParaulaDisc(){
        IndexParaula ip = ControladorParaula.getIndex();
        ControladorPersistencia.guardarParaules((Object) ip, "IndexParaula");
    }

    /**
     * Carrega tots els Documents des de el disc pero sense instanciar el contingut ni les frases dels documents
     */
    private void carregarIndexDocsDisc() throws NoExisteixException{
        IndexDocuments id = new IndexDocuments();

        ArrayList<Object> docs = ControladorPersistencia.carregarTotsDocuments();
        
        for (Object o : docs){
            Document d = (Document) o;
            id.setDoc(d);
            id.setTTL(d.getTitol(),d.getAutor(),0);
        }
        ControladorDocs.setIndex(id);
    }

    /**
     * Guarda el Document donat, a disc (un serial amb la classe sense el contingut ni les frases, un serial amb el contingut i un serial amb les frases)
     * @param doc representa el document que es vol guardar a disc
     */
    private void guardarDoc(Document doc){
        ArrayList<ArrayList<String>> frases = doc.getFrases();
        String contingut = doc.consultarContingut();
        doc.nullificarContingut();
        ControladorPersistencia.guardarDoc((Object) doc, "/docs/" + doc.getTitol() + "_" + doc.getAutor());
        ControladorPersistencia.guardarDoc((Object) contingut, "/docs/continguts/contingut_"+ doc.getTitol() + "_" + doc.getAutor());
        ControladorPersistencia.guardarDoc((Object) frases, "/docs/continguts/frases_"+ doc.getTitol() + "_" + doc.getAutor());
    }

    /**
     * Guarda el IndexDocs a disc
     */
    private void guardarIndexDocsDisc(){
        ArrayList<Document> docs  = ControladorDocs.allDocuments();
        for(Document doc : docs){
            if(doc.estaEnMemoria()) {
                guardarDoc(doc);
            }
        }
    }

    /**
     * Carrega el IndexAutors des de el Disc
     */
    private void carregarIndexAutorsDisc() throws NoExisteixException{
        indexAutors ia = (indexAutors) ControladorPersistencia.carregarAutors("IndexAutors");
        ControladorAutors.setIndex(ia);
    }

    /**
     * Guarda IndexAutors a disc
     */
    private void guardarIndexAutorsDisc(){
        indexAutors ia = ControladorAutors.getIndex();
        ControladorPersistencia.guardarAutors((Object) ia, "IndexAutors");
    }

    /**
     * Carrega el contingut del Document donat
     * @param titol representa el titol del Document del qual es vol carregar el contingut
     * @param autor representa el autor del Document del qual es vol carregar el contingut
     */
    private void carregarContingut(String titol, String autor) throws NoExisteixException{
        ArrayList<ArrayList<String>> frases = (ArrayList<ArrayList<String>>) ControladorPersistencia.carregarContingutOFrases(titol, autor, "frases");
        String contingut = (String) ControladorPersistencia.carregarContingutOFrases(titol,autor,"contingut");
        ControladorDocs.setContingut(titol, autor, contingut, frases);
    }

    /**
     * Exporta un document del programa al sistema operatiu al qual se li dona el nom que el usuari vol
     *
     * @param nomFinal representa el nom del document que es vol exportar
     * @param titol representa el titol del Document que es vol exportar
     * @param autor representa el autor del Document que es vol exportar
     */
    public void exportarDocument(String nomFinal, String titol, String autor) throws NoExisteixException, JaExisteixException{
        String res;
        if(!ControladorDocs.estaEnMemoria(titol,autor)){
            carregarContingut(titol,autor);
        }
        ControladorDocs.setTTL(titol, autor, 5);
        String cont = ControladorDocs.getDoc(titol, autor).consultarContingut();
        if(nomFinal.endsWith(".txt")){
            res = autor + "\n" + titol + "\n" + cont;
        }
        else if(nomFinal.endsWith(".xml")){
            res = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"; //Capcalera
            res = res + "<document>\n"; //Root
            res = res + "\t<author>" + autor + "</author>\n" + "\t<title>" + titol + "</title>\n" + "\t<content>" + cont + "</content>\n";
            res = res + "</document>"; //Final Root
        }
        else{
            res = "autor -> " + autor + "\ntitol -> " + titol + "\ncontingut -> " + cont; //format propietari
        }
        ControladorPersistencia.exportarDocument(nomFinal, res);
    }

    /**
     * Importa documents del sistema operatiu al disc en 3 formats diferents (fpg, xml i txt)
     *
     * @param path representa el path del document que es vol importar
     */
    public void importarDocuments(String path) throws Exception, JaExisteixException, TitolIncorrecteException, AutorIncorrecteException, NoExisteixException{
        String arxiu = ControladorPersistencia.llegirArxiuEnString(path);
        int startAuthorIndex, endAuthorIndex, startTitleIndex, endTitleIndex, startContentIndex, endContentIndex;
        String titol = "", autor = "", contingut = "";
        if(path.endsWith(".fpg")){
            startAuthorIndex = arxiu.indexOf("autor -> ");
            endAuthorIndex = arxiu.indexOf("\ntitol -> ");
            startTitleIndex = arxiu.indexOf("titol -> ");
            endTitleIndex = arxiu.indexOf("\ncontingut -> ");
            startContentIndex = arxiu.indexOf("contingut -> ");
            if(startAuthorIndex != -1 && endAuthorIndex != -1 && startTitleIndex != -1 && endTitleIndex != -1 && startContentIndex != -1){
                titol = arxiu.substring(startTitleIndex + 9,endTitleIndex);
                autor = arxiu.substring(startAuthorIndex + 9, endAuthorIndex);
                contingut = arxiu.substring(startContentIndex + 13);
                contingut = contingut.substring(0,contingut.length()-1);
                crearDoc(titol,autor,contingut);
            }
            else throw new ArxiuNoCorrecteException("Arxiu no correcte");
        }
        else if(path.endsWith(".xml")){
            startAuthorIndex = arxiu.indexOf("<author>");
            endAuthorIndex = arxiu.indexOf("</author>");
            startTitleIndex = arxiu.indexOf("<title>");
            endTitleIndex = arxiu.indexOf("</title>");
            startContentIndex = arxiu.indexOf("<content>");
            endContentIndex = arxiu.indexOf("</content>");
            if(startAuthorIndex != -1 && endAuthorIndex != -1 && startTitleIndex != -1 && endTitleIndex != -1 && startContentIndex != -1 && endContentIndex != -1){
                titol = arxiu.substring(startTitleIndex + 7, endTitleIndex);
                autor = arxiu.substring(startAuthorIndex + 8, endAuthorIndex);
                contingut = arxiu.substring(startContentIndex + 9, endContentIndex);
                crearDoc(titol,autor,contingut);
            }
            else throw new ArxiuNoCorrecteException("Arxiu no correcte");
        }
        else if (path.endsWith(".txt")){
            Reader stringReader = new StringReader(arxiu);
            BufferedReader reader = new BufferedReader(stringReader);
            int i = 0;
            if((autor = reader.readLine()) != null){
                ++i;
                if((titol = reader.readLine()) != null){
                    ++i;
                    String line;
                    while((line = reader.readLine()) != null){
                        contingut += line + "\n";
                    }
                }
            }
            if(i < 2) throw new ArxiuNoCorrecteException("Arxiu no correcte");
            else {
                if(!contingut.equals("")){
                    contingut = contingut.substring(0,contingut.length()-1);
                }
                crearDoc(titol,autor,contingut);
            }
        }
        else throw new ArxiuNoCorrecteException("Arxiu no correcte");
    }
}
