package domini.controladors;

import domini.classes.IndexParaula;
import domini.classes.Paraula;
import domini.exceptions.NoExisteixException;
import domini.utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class CtrlParaula {

    // ---------- ATRIBUTS ----------
    private IndexParaula indexPar = new IndexParaula(); //Variable que emmagatzema el index de Paraules


    
    // ---------- CONSTRUCTORES ----------
    public CtrlParaula(){
    }

    /**
     * Retorna el index de paraules del sistema
     *
     * @return Un IndexParaula que representa el index de paraules actual del sistema
     */
    public IndexParaula getIndex(){
        return this.indexPar;
    }

    /**
     * Fa que el index passat per paràmetre passi a ser el index de paraules del sistema
     *
     * @param ip representa el nou index de paraules
     */
    public void setIndex(IndexParaula ip){
        this.indexPar = ip;
    }

    // ---------- GETTERS ----------

    /**
     * Retorna l'objecte Paraula amb identificador paraula
     *
     * @param paraula representa la paraula que es busca
     * @return Una Paraula amb el identificador donat
     */
    public Paraula getParaula(String paraula) throws NoExisteixException {
        return indexPar.getParaula(paraula);
    }


    /**
     * Retorna el numero de paraules totals del index de Paraules
     * 
     * @return Un Integer que representa el nombre de paraules del sistema
     **/
    public Integer getNumTotalParaules(){
        return indexPar.getNumTotalParaules();
    }


    /**
     * Retorna els document i les frases on apareix el parametre paraula
     *
     * @param paraula representa la paraula de la qual es volen les frases
     * @return Un HashSet<Pair<Pair<String, String>, ArrayList<String>>> on Pair<String, String> es un parell titol-autor que identifiquen al Document
     *         i on ArrayList<String> son les frases on la paraula es present en aquell Document
     **/
    public HashSet<Pair<Pair<String,String>, ArrayList<String>>> getFrasesParaula(String paraula) throws NoExisteixException{
        HashSet<Pair<Pair<String,String>, ArrayList<String>>> frases = new HashSet<>();
        try {
            indexPar.getParaula(paraula);
            return indexPar.getFrasesParaula(paraula);
        }
        catch (NoExisteixException e){
            return frases;
        }
    }
    

    /**
     * Retorna els documents i les frases de cadascuna de les paraules del index de Paraules
     *
     * @return Un HashSet<Pair<Pair<String,String>, ArrayList<String>>> on Pair<String, String> es un parell titol-autor que identifiquen al Document
     *         i on ArrayList<String> son les frases on la paraula es present en aquell Document
     **/
    public HashSet<Pair<Pair<String,String>, ArrayList<String>>> getTotesFrases(){
        return indexPar.getTotesFrases();
    }




    // ---------- SETTERS ----------

    /**
     * S'afegeix una paraula al index
     *
     * @param paraula representa la paraula que es vol afegir al index
     * @param titol representa el titol del Document on apareix la paraula
     * @param autor representa el autor del Document on apareix la paraula
     * @param frase representa la frase on està present la paraula
     */
    public void afegirParaula(String paraula, String titol, String autor, ArrayList<String> frase) throws NoExisteixException{
        indexPar.afegirParaula(paraula,titol,autor,frase);
    }


    /**
     * Recalcula els IDFS de les diferents paraules al index de Paraules
     *
     * @param numDocuments representa el nombre de documents del sistema
     * @return Un HashMap<String,Double> que representa el IDF de les paraules
     */
    public HashMap<String,Double> recalcularIDF(Integer numDocuments){
        return indexPar.recalcularIDF(numDocuments);
    }


    /**
     * Elimina una ocurrencia d'una paraula en un Document i totes les seves frases
     *
     * @param paraula representa la paraula la qual es vol eliminar la ocurrencia
     * @param titol representa el titol del Document on està la paraula a eliminar
     * @param autor representa el titol del Document on està la paraula a eliminar
     */
    public void eliminarParaula(String paraula, String titol, String autor) throws NoExisteixException{
        indexPar.eliminarOcurrencia(paraula, titol, autor);
    }

}
