
package domini.classes;

import domini.exceptions.JaExisteixException;
import domini.exceptions.NoExisteixException;
import domini.exceptions.NomExpressioIncorrecte;
import domini.utils.Pair;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class IndexExpBool implements Serializable {

    // ---------- ATRIBUTS ----------
    private HashMap<String, ExpBool> expressionsBools; //Variable que emmagatzema les expressions booleanes i el seu identificador


    // ---------- CONSTRUCTORES ----------
    public IndexExpBool(){
        expressionsBools = new HashMap<>();
    }


    // ---------- GETTERS ----------

    /**
     * Retorna totes les expressions booleanes del sistema
     *
     * @return Un ArrayList<Pair<String, String>> que representa totes les expressions booleanes del sistema
     **/
    public ArrayList<Pair<String, String>> allExpBool() {
        ArrayList<Pair<String, String>> totExpBol = new ArrayList<>(); 

        for (ExpBool expBool : expressionsBools.values()) {
            Pair<String, String> newNameExpBool = new Pair<String,String>(expBool.getNomExpBool(), expBool.getExpressioAsString());
            totExpBol.add(newNameExpBool);
        }

        return totExpBol;
    }


    /**
     * Retorna la expressio booleana amb el nom donat
     *
     * @param nomExp representa el nom de la expressio buscada
     * @return Un ExpBool amb el nom donat
     **/
    public ExpBool getExpBool(String nomExp) throws NoExisteixException {
        ExpBool exp = expressionsBools.get(nomExp);
        if(exp == null){
            throw new NoExisteixException("No existeix al Expressio");
        }
        return exp;
    }

    /**
     * Retorna el string que representa l'expressio booleana
     *
     * @param nomExp representa el nom de la expressio buscada
     * @return Un String amb la expressio integra del nom donat
     **/
    public String getExpressioAsString(String nomExp){
        return expressionsBools.get(nomExp).getExpressioAsString();
    }

    /**
     * Retorna totes les expressions booleanes
     *
     * @return Un HashMap<String, ExpBool> amb totes les instàncies de expressió del sistema i el seu nom
     **/
    public HashMap<String, ExpBool> getMapa(){
        return this.expressionsBools;
    }

    // ---------- SETTERS ----------
    /**
     * Crea una Expressio Booleana i l'afegeix al index d'Expressions Booleanes
     *
     * @param nomExp representa el nom de la expressio a crear
     * @param expressio representa la expressio integra a crear
     **/
    public void crearExpBool(String nomExp, String expressio) throws NomExpressioIncorrecte, NoExisteixException, JaExisteixException{
        try {
            ExpBool exp = getExpBool(nomExp);
            throw new JaExisteixException("Ja existeix la expressio booleana");
        }
        catch (NoExisteixException e){
            ExpBool novaExp = new ExpBool(nomExp, expressio);
            expressionsBools.put(nomExp,novaExp);
        }
    }

    /**
     * Afegeix al index l'objecte ExpBool passat com a parametre
     * 
     * @param exp representa la instància ExpBool que es vol afegir al conjunt de expressions booleanes
     **/
    public void putExpBool(ExpBool exp){
        expressionsBools.put(exp.getNomExpBool(),exp);
    }

    /**
     * Modifica l'expressio que es passa com a parametre
     *
     * @param nomExp representa el nom de la expressio a modificar
     * @param expressio representa la expressio integra a modificar
     **/
    public void modificarExpBool(String nomExp, String expressio) throws NomExpressioIncorrecte, NoExisteixException, JaExisteixException{
        ExpBool exp = expressionsBools.get(nomExp);
        if(exp == null){
            throw new NoExisteixException("No existeix la expressio booleana");
        }
        else {
            expressionsBools.remove(nomExp);
            crearExpBool(nomExp,expressio);
        }
    }

    /** 
     * Elimina la expressio booleana amb identificador nomExp
     * 
     * @param nomExp representa el nom de la expressio que es vol eliminar del sistema
     **/
    public void eliminarExpBool(String nomExp) {
        expressionsBools.remove(nomExp);
    }
}

