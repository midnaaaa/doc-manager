package presentacio;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import domini.controladors.CtrlDomini;
import domini.utils.Pair;
import presentacio.vistes.VistaCrearDoc;
import presentacio.vistes.VistaCrearExpBool;
import presentacio.vistes.VistaPrincipal;
import presentacio.vistes.VistaVisualitzaModificar;
import domini.exceptions.*;

public class CtrlPresentacio {

    // ---------- ATRIBUTS ----------
    private CtrlDomini ctrlDomini;
    private VistaPrincipal vistaPrincipal = null;    
    private VistaCrearDoc vistaCrearDoc = null;
    private VistaCrearExpBool vistaCrearExpBool = null;
    private VistaVisualitzaModificar ObrirVM = null;

    
    // ---------- CONSTRUCTORES ----------
    public CtrlPresentacio(){
        ctrlDomini = new CtrlDomini();
        try{
            ctrlDomini.carregarDeDisc();
        }
        catch (NoExisteixException e){
            System.out.println("No existeixen els binaris");
        }
        ctrlDomini.iniciarTimer();
        if (vistaPrincipal == null)  
          vistaPrincipal = new VistaPrincipal(this);
    }
    
    /**
     * Inicialitza la presentacio fent visible la vista principal.
     * 
     */
    public void inicialitzarPresentacio() {
        vistaPrincipal.hacerVisible();
    }

    /**
     * Sincronitza la vista principal amb la vista de crear document
     * 
     */
    public void sincronizacionVistaPrincipal_a_CrearDoc() {
        if (vistaCrearDoc == null)
            vistaCrearDoc = new VistaCrearDoc(this);
        vistaCrearDoc.hacerVisible();
    }

    /**
     * Sincronitza la vista principal amb la vista de crear una expressio boleana
     * 
     */
    public void sincronizacionVistaPrincipal_a_ExpBool() {
        if (vistaCrearExpBool == null) 
            vistaCrearExpBool = new VistaCrearExpBool(this);
        vistaCrearExpBool.hacerVisible();
    }

    /**
     * Obra la vista per poder visualitzar i modificar.
     * 
     * @param autor representa el autor del Document a modificar o el nomExp
     * @param titol representa el titol del Document o la expressio a modificar
     * @param type representa si es vol modificar una expressio o un document
     */
    public void obraVisualitzaModificar(String autor, String titol, Integer type) {
        ObrirVM = new VistaVisualitzaModificar(this, autor, titol, type); 
        ObrirVM.hacerVisible();
    }

    /**
     * Actualitza la llista de documents
     * 
     */
    public void actualitzaLlistaDocuments() {
        vistaPrincipal.actualitzaLlistaDocuments();
    }

    /**
     * Actualitza la llista de expressions booleanes
     * 
     */
    public void actualitzaLlistaExpBool() {
      vistaPrincipal.actualitzaLlistaExpBool();
    }

    //CRIDA A DOMINI
    //Documents

    /**
     * Crida la funcio que permet obtindre tots els documents
     * 
     * @return ArrayList<Pair<String, String>>
     */
    public ArrayList<Pair<String, String>> getDocuments () {
        return ctrlDomini.allDocs();
    }

    /**
     * Crida la funcio que permet crea el document amb els atributs adecuats
     * @param Titol representa el titol del Document a crear
     * @param Autor representa el autor del Document a crear
     * @param Contingut representa el contingut del Document a crear
     * @return Integer
     */
    public Integer crearDoc(String Titol, String Autor, String Contingut) throws JaExisteixException, TitolIncorrecteException, AutorIncorrecteException, NoExisteixException {
        return ctrlDomini.crearDoc(Titol, Autor, Contingut);
    }

    /**
     * Crida la funcio que permet elimina un document concret.
     * @param titol representa el titol del Document a eliminar
     * @param autor representa el autor del Document a eliminar
     */
    public void eliminarDoc(String titol, String autor) throws NoExisteixException {
        ctrlDomini.eliminarDoc(titol, autor);
    }

    /**
     * Crida la funcio que permet consulta el contingut d'un document concret
     * @param titol representa el titol del Document a consultar
     * @param autor representa el autor del Document a consultar
     * @return Un String que representa el Contingut del document
     */
    public String consultarContingut(String titol, String autor) throws NoExisteixException {
        return ctrlDomini.consultarContingut(titol, autor);
    }

    /**
     * Crida la funcio que permet modifica el contingut d'un document concret.
     * @param titol representa el titol del Document a modificar
     * @param autor representa el autor del Document a modificar
     * @param nouContingut representa el nou contingut del Document
     * @return Un boolean que indica si s'ha modificat amb exit
     */
    public boolean modificarContingut(String titol, String autor, String nouContingut) throws NoExisteixException {
        return ctrlDomini.modificarDoc(titol, autor, nouContingut);
    }

    /**
     * Crida la funcio que permet consultar els titols d'un autor concret
     * @param nomAutor representa el nom del autor del qual es volen consultar els titols
     * @param ord representa la forma de ordenar la sortida
     * @return Un ArrayList<String> amb els titols del autor
     */
    public ArrayList<String> consultarTitolsAutor(String nomAutor, Integer ord) throws NoExisteixException {
        return ctrlDomini.consultarTitolsAutor(nomAutor, ord);
    }

    /**
     * Crida la funcio que permet consulta els documents per semblança
     * @param titol representa el titol del Document del qual es vol consultar la semblança
     * @param autor representa el autor del Document del qual es vol consultar la semblança
     * @param k representa el nombre de documents que es volen
     * @param type representa el tipus de consulta
     * @param ord representa la forma de ordenar la sortida
     * @return Un ArrayList<Pair<String, String>> que representa els documents mes semblants
     */
    public ArrayList<Pair<String, String>> consultarDocSemblanca(String titol, String autor, int k, int type, int ord) throws NoExisteixException {
        return ctrlDomini.consultarDocSemblanca(titol, autor, k, type, ord);
    }

    /**
     * Crida la funcio que permet consulta els documents a partir d'una expressio boleana
     * @param nomExp representa la expressio booleana de la qual es vol fer la consulta
     * @param ord representa la forma de ordenar la sortida
     * @return Un ArrayList<Pair<String, String>> amb els documents
     */
    public ArrayList<Pair<String, String>> consultaDocExpBool(String nomExp, Integer ord) throws NoExisteixException {
        return ctrlDomini.consultaDocExpBool(nomExp, ord); 
    }

    /**
     * Crida la funcio que permet importar els documents
     * 
     * @param f representa el path del document a importar
     */
    public void importarDocs(File f) throws JaExisteixException, TitolIncorrecteException, AutorIncorrecteException, NoExisteixException, Exception{
        ctrlDomini.importarDocuments(f.getAbsolutePath());
    }

    /**
     * Crida la funcio que permet exportar els documents
     *
     * @param nomFinal representa el nom final del document a exportar
     * @param titol representa el titol del document a exportar
     * @param autor representa el autor del document a exportar
     */
    public void exportarDoc(String nomFinal, String titol, String autor) throws NoExisteixException, JaExisteixException{
        ctrlDomini.exportarDocument(nomFinal, titol, autor);
    }

    //Expressions

    /**
     * Crida la funcio que permet obtindre totes les expressions booleanes
     * @return Un ArrayList<Pair<String, String>> que representa totes les expressions
     */
    public ArrayList<Pair<String, String>> allExpBool() {
        return ctrlDomini.allExpBool();
    }

    /**
     * Crida la funcio que permet crear una expressio booleana
     * @param nomExp representa el nom de la expressio a crear
     * @param expressio representa la expressio integra a crear
     * @return Un integer que representa si s'ha creat amb exit
     */
    public Integer crearExpBool(String nomExp, String expressio) throws ExpressioNoValidaException, NomExpressioIncorrecte, JaExisteixException, NoExisteixException {
        return ctrlDomini.crearExpBool(nomExp, expressio);
    }

    /**
     * Crida la funcio que permet modifcar una expressio booleana concreta
     * @param nomExp representa el nom de la expressio a modificar
     * @param expressio representa la nova expressio integra
     * @return Un Integer que representa si s'ha modificat correctament
     */
    public Integer modificarExpBool(String nomExp, String expressio) throws ExpressioNoValidaException, JaExisteixException, NoExisteixException, NomExpressioIncorrecte {
        return ctrlDomini.modificarExpBool(nomExp, expressio);
    }

    /**
     * Crida la funcio que permet consultar una expressio booleana
     * @param nomExp representa el nom de la expressio de la qual es vol consultar la seva expressio integra
     * @return Un String que representa la expressio integra
     */
    public String consultaExpBool(String nomExp) throws NoExisteixException {
        return ctrlDomini.consultaExpBool(nomExp);
    }

    /**
     * Crida la funcio que permet eliminar una expressio booleana concreta
     * @param nomExp representa el nom de la expressio que es vol eliminar
     */
    public void eliminarExpBool(String nomExp) {
        ctrlDomini.eliminarExpBool(nomExp);
    }

    //autor

    /**
     * Crida la funcio que permet consultar els autors per prefix
     * @param prefix representa el prefix del qual es volen buscar els autors
     * @param ord representa la forma de ordenar la sortida
     * @return Un ArrayList<String> que representa la llista de autors que contenen el prefix
     */
    public ArrayList<String> consultarAutorPerPrefix(String prefix, Integer ord) {
        return ctrlDomini.consultarAutorPerPrefix(prefix, ord);
    }

    /**
     * Crida la funcio que permet guardar a disc
     */
    public void guardarADisc() {
        ctrlDomini.guardarADisc();
    }
    
}
