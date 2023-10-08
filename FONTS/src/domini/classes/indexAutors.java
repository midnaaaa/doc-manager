package domini.classes;

import domini.exceptions.NoExisteixException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class indexAutors implements Serializable{

    // ---------- ATRIBUTS ----------
    private Map<String, Autor> autors = new HashMap<>(); //Variable que emmagatzema els autors i el seu nom


    // ---------- CONSTRUCTORES ----------
    public indexAutors(){

    }

    /**
     * Crea un autor amb un nom i conjunt de titols donats
     * 
     * @param nom indica el nom del autor
     * @param titols indica el conjunt de titols del autor
     */
    public void crearAutor(String nom, Set<String> titols){
        Autor autor = new Autor(nom, titols);
        autors.put(nom, autor);
    }

    // ---------- GETTERS ----------

    /**
     * Retorna la quantitat d'autors
     *
     * @return Un Int que representa quants autors hi ha al sistema
     */
    public int sizeAutor() {
        return autors.size();
    }

    /**
     * Retorna el autor amb el nom proporcionat
     *
     * @param nom indica el nom del autor que es busca
     * @return Un Autor amb el nom proporcionat per paràmetre
     */
    public Autor getAutor(String nom) throws NoExisteixException{
        if (!autors.containsKey(nom)) {
            throw new NoExisteixException("No existeix el Autor");
        }
        return autors.get(nom);
    }

    /**
     * Retorna la llista de autors que tenen nom dels quals els autors son prefix del parametre
     *
     * @param prefix representa el prefix del qual s'està buscant autors
     * @return Un ArrayList amb la llista d'autors que tenen el prefix
     */
    public ArrayList<String> getAutorsPrefix(String prefix){
        ArrayList<String> prefixAutors = new ArrayList<>();

        //iterem el map per veure els autors que coincideixen amb el prefix
        for (String nomAutor : autors.keySet())  {
            Pattern patro = Pattern.compile(prefix);
            String regEx = nomAutor + ".*";                     // .* Matches any string that contains zero or more occurrences of some letter
            Matcher match = patro.matcher(regEx);
            boolean coincideix = match.find();

            if(coincideix) {
                prefixAutors.add(nomAutor);
            }
        }
        return prefixAutors;
    }

    // ---------- SETTERS ----------
    
    /**
     * Eliminar l'autor amb el nom donat
     *
     * @param nom indica el nom del Autor que es vol eliminar
     */
    public void eliminarAutor(String nom){
        if (autors.containsKey(nom)) {
            autors.remove(nom);
        }
    }

    /**
     * Afegeix un titol al autor donat
     *
     * @param nomAutor indica el nom del autor al qual es vol afegir el nou titol
     * @param nouTitol el nom del titol que es vol afegir a la llista d'autors
     */
    public void afegirTitol(String nomAutor, String nouTitol) throws NoExisteixException {
        Autor autor = autors.get(nomAutor);
        if(autor == null) throw new NoExisteixException("No existeix el autor");
        autor.afegirTitol(nouTitol);
    }

    /**
     * Eliminar el titol donat del autor donat
     *
     * @param nomAutor indica el nom del autor del qual es vol eliminar el titol donat
     * @param nomTitol el nom del titol que es vol eliminar de la llista de titols del autor
     * @throws NoExisteixException
     */
    public void eliminarTitol(String nomAutor,String nomTitol) throws NoExisteixException {
        Autor autor = autors.get(nomAutor);
        if(autor == null) throw new NoExisteixException("No existeix el autor");
        Set<String> titols = new HashSet<>();
        titols = autor.getTitols();
        autor.eliminarTitol(nomTitol);
        if (titols.size() < 1) {
            autors.remove(nomAutor);
        }
    }
}
