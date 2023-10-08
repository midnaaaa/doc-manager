package domini.classes;

import domini.exceptions.NoExisteixException;
import domini.utils.Pair;
import domini.utils.Utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class IndexParaula implements Serializable{

    // ---------- ATRIBUTS ----------
    private HashMap<String,Paraula> paraules; // Variable que emmagatzema les diferents paraules amb ocurrencies


    // ---------- CONSTRUCTORES ----------
    public IndexParaula(){
        paraules = new HashMap<>();
    }

    // ---------- GETTERS ----------
    /**
     * Retorna l'objecte Paraula amb identificador paraula
     *
     * @param paraula representa la paraula que es busca
     * @return Una Paraula que té de identificador el String donat
     **/
    public Paraula getParaula(String paraula) throws NoExisteixException {
        if (!paraules.containsKey(paraula)) throw new NoExisteixException("No existeix la paraula");
        return paraules.get(paraula);
    }

    /**
     * Retorna el numero total de Paraules en el index 
     *
     * @return Un Integer que representa el nombre total de paraules al sistema
     **/
    public Integer getNumTotalParaules(){
        return paraules.size();
    }

    /**
     * Retorna els document i les frases on apareix el parametre paraula
     *
     * @param paraula representa la paraula de la qual es busquen els documents i frases on apareix
     * @return Un HashSet<Pair<Pair<String, String>, ArrayList<String>>> on Pair<String, String> es un parell titol-autor que identifiquen al Document i on ArrayList<String> son les frases on la paraula es present en aquell Document
     **/
    public HashSet<Pair<Pair<String,String>, ArrayList<String>>> getFrasesParaula(String paraula){
        return paraules.get(paraula).getFrases();
    }

    /**
     * Retorna els documents i les frases de cadascuna de les paraules del index de Paraules
     *
     * @return Un HashSet<Pair<Pair<String,String>, ArrayList<String>>> on Pair<String, String> es un parell titol-autor que identifiquen al Document i on ArrayList<String> son les frases on la paraula es present en aquell Document
     **/
    public HashSet<Pair<Pair<String,String>, ArrayList<String>>> getTotesFrases(){
        HashSet<Pair<Pair<String,String>, ArrayList<String>>> totesFrases = new HashSet<>();
        for(Map.Entry<String,Paraula> entry : paraules.entrySet()) {
            totesFrases.addAll(entry.getValue().getFrases());
        }
        return totesFrases;
    }

    // ---------- SETTERS ----------
    /**
     * Si la paraula ja existia es crea una nova ocurrencia. En cas contrari es crea la paraula 
     *
     * @param paraula representa la paraula la qual es vol crear o afegir una ocurrencia
     * @param titol representa el titol del Document on s'ha trobat la paraula
     * @param autor representa el autor del Document on s'ha trobat la paraula
     * @param frase indica la frase on s'ha trobat la paraula
     * @return Un boolean que és 1 si la paraula s'ha afegit amb exit o 0 si no
     **/
    public boolean afegirParaula(String paraula, String titol, String autor, ArrayList<String> frase) throws NoExisteixException{
        try {
            Paraula par = getParaula(paraula);
            return par.afegirOcurrencia(titol,autor,frase);
        }
        catch (NoExisteixException e){
            Paraula novaParaula = new Paraula(paraula, titol, autor, frase);
            paraules.put(paraula,novaParaula);
            return true;
        }
    }


    /**
     * Recalcula el valor del IDF de les diferents paraules del index
     *
     * @param numDocuments represneta el nombre de Documents al sistema
     * @return Un HashMap<String, Double> on la Key es la paraula i el Value es el seu IDF
     **/
    public HashMap<String,Double> recalcularIDF(Integer numDocuments){
        HashMap<String,Double> IDFs = new HashMap<>();

        for (Map.Entry<String,Paraula> entry : paraules.entrySet()) {
            if(Utils.paraulaValida(entry.getKey())){
                IDFs.put(entry.getKey(),entry.getValue().recalcularIDF(numDocuments));
            }
        }
        return IDFs;
    }

    /**
     * Elimina una ocurrencia d'una paraula en un Document i totes les seves frases
     *
     * @param paraula representa la paraula la qual es vol eliminar o eliminar una ocurrencia
     * @param titol representa el titol del Document on està la paraula a eliminar
     * @param autor representa el autor del Document on està la paraula a eliminar
     **/
    public void eliminarOcurrencia(String paraula, String titol, String autor){
        Paraula par = paraules.get(paraula);
        if(par != null) {
            par.eliminarOcurrencia(titol,autor);
            if(par.getNumOcurrencies() == 0) paraules.remove(paraula);
        }
    }
}
