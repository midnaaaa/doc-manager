package domini.classes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Autor implements Serializable {

    // ---------- ATRIBUTS ----------
    /**
     * String que emmagatzema el nom del autor
     */
    private String nom;
    /**
     * Set que emmagatzema els titols del autor
     */
    private Set<String> titols = new HashSet<>();


    // ---------- CONSTRUCTORES ----------

    /**
     * Creadora de la classe
     */
    public Autor(){}

    /**
     * Creadora de la classe a partir de un nom
     *
     * @param nom el nom del autor
     */
    public Autor(String nom){
        this.nom = nom;
    }
    /**
     * Creadora de la classe a partir de un nom i uns titols
     *
     * @param nom el nom del autor
     * @param titols titols del autor
     */
    public Autor(String nom, Set<String> titols){
        this.nom = nom;
        this.titols = titols;
    }

    // ---------- GETTERS ----------
    /**
     * Retorna el nom del autor
     *
     * @return nom del autor
     */
    public String getNom(){
        return nom;
    }

    /**
     * Consulta tots els titols de un autor
     *
     * @return Un set de String que representa la llista de titols
     */
    public Set<String> getTitols(){
        return titols;
    }

    // ---------- SETTERS ----------
    /**
     * Afegir un nou titol al autor
     *
     * @param nouTitol nou titol a afegir
     */
    public void afegirTitol(String nouTitol) {
        titols.add(nouTitol);
    }

    /**
     * Elimina un dels titols de l'autor
     *
     @param nomTitol nom del titol a eliminar
     **/
    public void eliminarTitol(String nomTitol) {
        titols.remove(nomTitol);
    }
}
