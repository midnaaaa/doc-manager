package domini.classes;

import domini.exceptions.AutorIncorrecteException;
import domini.exceptions.TitolIncorrecteException;
import domini.utils.Utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Document implements Serializable{

    // ---------- ATRIBUTS ----------
    private String titol; //Variable que emmagatzema el valor del titol del Document
    private String autor; //Variable que emmagatzema el valor del autor del Document
    private String contingutString; //Variable que emmagatzema el contingut en forma de string
    private Boolean memoria; //Variable que emmagatzema si el contingut està carregat a memòria, true si hi és, false si no.
    private int TTL;
    private ArrayList<ArrayList<String>> frases; //Variable que emmagatzema el contingut del Document separat per frases
    private HashMap<String, Double> TF; //Variable que emmagatzema la frequencia de cada paraula del Document
    private HashMap<String, Double> BOW; //Variable que emmagatzema el nombre de ocurrencies de cada paraula del Document

    // ---------- CONSTRUCTORES ----------
    public Document(){};
    public Document(String titol, String autor, String contingut) throws TitolIncorrecteException, AutorIncorrecteException{
        if(titol.trim().length() == 0) throw new TitolIncorrecteException("Titol incorrecte");
        if(autor.trim().length() == 0) throw new AutorIncorrecteException("Autor incorrecte");
        this.TTL = 5;
        this.titol = titol;
        this.autor = autor;
        this.contingutString = contingut;
        ArrayList<String> paraules = new ArrayList<String>(Arrays.asList(contingut.split("[\\n\\s]")));
        paraules.add(0, autor);
        paraules.add(0, titol);
        this.TF = new HashMap<>();
        this.BOW = new HashMap<>();
        this.frases = crearFrases(paraules);
        calcularPesos(paraules);
        this.memoria = true; 
    }

    // ---------- GETTERS ----------
    /**
     * Retorna el TF del Document
     *
     * @return Un HashMap on el primer valor es la paraula i el segon la seva frequencia
     **/
    public HashMap<String,Double> llistarTF(){
        return TF;
    }

    /**
     * Retorna el BOW del Document
     *
     * @return Un HashMap on el primer valor es la paraula i el segon les seves ocurrencies al Document
     **/
    public HashMap<String,Double> llistarBOW(){
        return BOW;
    }

    /**
     * Retorna el contingut del Document
     *
     * @return Un ArrayList<String> on el String es cada paraula
     **/
    public ArrayList<String> consultarContingutList(){
        ArrayList<String> text = new ArrayList<String>(Arrays.asList(contingutString.split("[\\n\\s]")));
        text.add(0, autor);
        text.add(0, titol);
        return text;
    }


    /**
     * Retorna el TTL (Time-To-Live) del Document
     *
     * @return Un int que representa el Time-To-Live del Document
     **/
    public int getTTL(){
        return TTL;
    }

    /**
     * Retorna el TTL (Time-To-Live) del Document
     *
     * @param newTTL representa el nou Time-To-Live del Document
     **/
    public void setTTL(int newTTL){
        this.TTL = newTTL;
    }


    /**
     * Retorna el contingut en forma de String del Document
     *
     * @return String que representa el contingut del Document
     **/
    public String consultarContingut(){
        return this.contingutString;
    }


    /**
     * Estableix el nou contingut del Document i les noves frases i posa el booleà de memoria a true indicant que aquests dos atributs del Document són a memoria
     *
     * @param nouContingut el nou contingut del Document
     * @param frases les noves frases del Document
     **/
    public void setContingut(String nouContingut, ArrayList<ArrayList<String>> frases){
        this.contingutString = nouContingut;
        this.frases = frases;
        this.memoria = true;
    }


    /**
     * Retorna totes les frases del Document
     *
     * @return Un ArrayList on String es cada frase del Document
     **/
    public ArrayList<ArrayList<String>> getFrases(){
        return frases;
    }

    /**
     * Retorna el titol del Document
     *
     * @return Un String que es refereix al titol del Document
     **/
    public String getTitol() {
        return titol;
    }

    /** *
     * Retorna l'autor del Document
     *
     * @return Un String que es refereix al autor del Document
     **/
    public String getAutor() {
        return autor;
    }





    // ---------- SETTERS ----------

    /**
     * Esborra el contingut del document de memoria 
     */
    public void nullificarContingut(){
        this.contingutString = null;
        this.memoria = false;
        this.frases = null;
    }

    /**
     * Modifica el contingut del Document pel nouContingut
     *
     * @param nouContingut el nou contingut del Document
     **/
    public void modificarContingut(String nouContingut){
        this.contingutString = nouContingut;
        this.memoria = true;
        ArrayList<String> text = new ArrayList<String>(Arrays.asList(nouContingut.split("[\\n\\s]")));
        text.add(0, autor);
        text.add(0, titol);
        frases = crearFrases(text);
        calcularPesos(text);
    }


    /**
     * Indica si el contingut i les frases del Document estan en memoria
     *
     * @return Un booleà a 1 si està en memoria i a 0 si no hi és
     **/
    public boolean estaEnMemoria(){
        return this.memoria;
    }

    // ---------- OTHERS ----------


    /**
     * Indica si el Document del mètode i el paràmetre són iguals en titol autor i contingut
     *
     * @param d representa el Document que es vol comparar
     * @return Un booleà a 1 si està en memoria i a 0 si no hi és
     **/
    public boolean equals(Document d){
        return this.autor.equals(d.autor) && this.titol.equals(d.titol) && this.contingutString.equals(d.contingutString);
    }

    // ---------- FUNCIONS PRIVADES ----------
    /**
     * Separa el Document per frases (una frase es delimitada per un: .  !  ?
     *
     * @param contingut contingut per paraules del Document
     * @return Un ArrayList<String> amb totes les frases del Document
     **/
    private ArrayList<ArrayList<String>> crearFrases(ArrayList<String> contingut){
        ArrayList<ArrayList<String>> frases = new ArrayList<>();
        ArrayList<String> fraseAct = new ArrayList<>();
        Integer i = 0;
        for(String s : contingut){
            if(i == 0 || i == 1) {
                ArrayList<String> par = new ArrayList<String>(Arrays.asList(s.split(" ")));
                ArrayList<String> res = new ArrayList<>();
                for(String paraula : par){
                    paraula = Utils.getParaulaPura(paraula).toLowerCase();
                    res.add(paraula);
                }
                frases.add(res);
            }
            else if((s.contains(".") || s.contains("!") || s.contains("?")) || i == contingut.size()-1){
                fraseAct.add(Utils.getParaulaPura(s).toLowerCase());
                frases.add(fraseAct);
                fraseAct = new ArrayList<>();
            }
            else fraseAct.add(Utils.getParaulaPura(s).toLowerCase());
            ++i;
        }
        return frases;
    }

    /**
     * Calcula els pesos de cada paraula pesos de TF i pesos de BOW
     *
     * @param contingut contingut per paraules del Document
     **/
    private void calcularPesos(ArrayList<String> contingut){
        TF.clear();
        BOW.clear();
        ArrayList<String> tit = new ArrayList<String>(Arrays.asList(titol.split(" ")));
        ArrayList<String> aut = new ArrayList<String>(Arrays.asList(autor.split(" ")));
        Integer numParaules = (contingut.size() - 2) + tit.size() + aut.size();
        Double freq = 1.0/numParaules;
        Integer i = 0;
        for(String s : contingut){
            if(i == 0){
                for (String paraula : tit){
                    paraula = Utils.getParaulaPura(paraula).toLowerCase();
                    if(!TF.containsKey(paraula)) {
                        TF.put(paraula, freq);
                        BOW.put(paraula,1.0);
                    }
                    else {
                        TF.put(paraula, TF.get(paraula) + freq);
                        BOW.put(paraula, BOW.get(paraula) + 1.0);
                    }
                }
            }
            else if(i == 1){
                for(String paraula : aut){
                    paraula = Utils.getParaulaPura(paraula).toLowerCase();
                    if(!TF.containsKey(paraula)) {
                        TF.put(paraula, freq);
                        BOW.put(paraula,1.0);
                    }
                    else {
                        TF.put(paraula, TF.get(paraula) + freq);
                        BOW.put(paraula, BOW.get(paraula) + 1.0);
                    }
                }
            }
            else{
                String paraula = Utils.getParaulaPura(s).toLowerCase();
                if(Utils.paraulaValida(paraula)){
                    if(!TF.containsKey(paraula)) {
                        TF.put(paraula, freq);
                        BOW.put(paraula,1.0);
                    }
                    else {
                        TF.put(paraula, TF.get(paraula) + freq);
                        BOW.put(paraula, BOW.get(paraula) + 1.0);
                    }
                }
            }
            ++i;
        }
    }
}
