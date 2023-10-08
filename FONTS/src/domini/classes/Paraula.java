package domini.classes;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;

import domini.utils.Pair;

import static java.lang.Math.log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Paraula implements Serializable{

    // ---------- ATRIBUTS ----------
    private String paraula; //Variable que emmagatzema la paraula en forma de String
    private HashSet<Pair<String,String>> ocurrencies; //Variable que emmagatzema els documents en els que apareix la paraula
    private HashSet<Pair<Pair<String,String>, ArrayList<String>>> frases; //Variable que emmagatzema les frases i els documents on apareix la paraula
    private Double IDF; //Variable que representa el IDF de la paraula


    // ---------- CONSTRUCTORES ----------
    public Paraula(){
        ocurrencies = new HashSet<>();
        frases = new HashSet<>();
    }

    public Paraula(String paraula, String titol, String autor, ArrayList<String> frase){
        this.paraula = paraula;
        ocurrencies = new HashSet<>();
        frases = new HashSet<>();
        Pair<String, String> doc = new Pair<>(titol,autor);
        Pair<Pair<String, String>, ArrayList<String>> tot = new Pair<>(doc,frase);
        ocurrencies.add(doc);
        frases.add(tot);
    }


    // ---------- GETTERS ----------
    /**
     * Retorna el String que representa la paraula
     *
     * @return Un String que representa la paraula
     **/
    public String getParaula() {
        return paraula;
    }

    /**
     * Retorna el nombre de documents on la paraula es present
     *
     * @return Un Integer que indica en quants documents està la paraula
     **/
    public Integer getNumOcurrencies(){
        return ocurrencies.size();
    }

    /**
     * Retorna totes les frases i el document on es troba de cada paraula
     *
     * @return Un HashSet<Pair<Pair<String,String>, ArrayList<String>>> que representa totes les frases de la paraula i els documents on està cadascuna
     **/
    public HashSet<Pair<Pair<String,String>, ArrayList<String>>> getFrases(){return frases;}

    // ---------- SETTERS ----------
    /**
     * Afegeix una nova ocurrencia de la paraula si encara no existeix al Document, el mateix amb la frase
     *
     * @param titol represeta el titol del Document on ha aparegut la paraula
     * @param autor represeta el autor del Document on ha aparegut la paraula
     * @param frase represeta la frase del Document on ha aparegut la paraula
     * @return Un boolean que és 1 si la paraula s'ha afegit amb exit o 0 si no
     * **/
    public boolean afegirOcurrencia(String titol, String autor, ArrayList<String> frase){
        Pair<String, String> doc = new Pair<>(titol,autor);
        Pair<Pair<String, String>, ArrayList<String>> tot = new Pair<>(doc,frase);
        if(!ocurrencies.contains(doc)){
            ocurrencies.add(doc);
            if(!frases.contains(frase)){
                frases.add(tot);
            }
            return true;
        }
        if(!frases.contains(frase)){
            frases.add(tot);
        }
        return false;
    }

    /**
     * Elimina una ocurrencia de la paraula en un Document i totes les seves frases
     *
     * @param titol represeta el titol del Document on es vol eliminar la paraula
     * @param autor represeta el autor del Document on es vol eliminar la paraula
     **/
    public void eliminarOcurrencia(String titol, String autor){
        Pair<String, String> doc = new Pair<>(titol,autor);
        ocurrencies.remove(doc);
        HashSet<Pair<Pair<String,String>, ArrayList<String>>> frasesNoves = new HashSet<>();
        for(Pair<Pair<String,String>, ArrayList<String>> frase : frases){
            if(!frase.first().equals(doc)) frasesNoves.add(frase);
        }
        frases = frasesNoves;
    }

    /**
     * Recalcula el valor del IDF de la paraula
     *
     * @param numDocuments representa el nombre de Documents al sistema
     * @return Un Double que representa el valor del IDF calculat
     **/
    public double recalcularIDF(Integer numDocuments){
        IDF = log((double)numDocuments/ocurrencies.size());
        return IDF;
    }
}
