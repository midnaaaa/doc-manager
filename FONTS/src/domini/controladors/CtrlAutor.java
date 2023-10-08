package domini.controladors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import domini.classes.Autor;
import domini.classes.indexAutors;
import domini.exceptions.NoExisteixException;
import domini.utils.Utils;

public class CtrlAutor {

    // ---------- ATRIBUTS ----------

    private indexAutors indexAut; //Variable que emmagatzema el index de Documents



    // ---------- CONSTRUCTORES ----------
    
    public CtrlAutor(){
        indexAut = new indexAutors();
    }

    /**
     * Crea un autor amb nom i conjunt de titols passats per paràmetre
     * 
     * @param nom representa el nom del autor a crear
     * @param titols un Set<String> que representa els titols del autor a crear
     **/
    public void crearAutor(String nom, Set<String> titols){
        indexAut.crearAutor(nom, titols);
    }

    public void setIndex(indexAutors ia){
        this.indexAut = ia;
    }

    public indexAutors getIndex(){
        return this.indexAut;
    }

    // ---------- GETTERS ----------
    
    /**
     * Retorna l'autor identificat amb el nom passat per paràmetre
     * 
     * @param nom representa el nom del autor buscat
     * @return Un Autor que s'identifica amb el nom donat
     **/
    public Autor getAutor(String nom) throws NoExisteixException{
        return indexAut.getAutor(nom);
    }

    /**
     * Retorna els conjunt de titols de l'autor, seguint un ordre indicat pel parametre ord
     * 
     * @param nom representa el nom del autor del qual es vols buscar els titols
     * @param ord representa la forma de ordenar la sortida
     * @return Un ArrayList<String> que representa els titols del autor
     */
    public ArrayList<String> getTitols(String nom, Integer ord) throws NoExisteixException {
        Autor autor = new Autor();
        autor = indexAut.getAutor(nom);
        ArrayList<String> res = new ArrayList<>(autor.getTitols());
        return Utils.ordenarTitolsOAutors(res, ord);


    }

    /**
     * Retorna el conjunt d'autors el nom dels quals conté el prefix passat per paràmetre
     * ordenats amb l'ordre indicat pel paràmetre ord
     * 
     * @param prefix representa el prefix del qual es busquen els autors
     * @param ord representa la forma de ordenar la sortida
     * @return Un ArrayList<String> que representa els autors que contenen el prefix
     */
    public ArrayList<String> getAutorsPrefix(String prefix, Integer ord){
        return Utils.ordenarTitolsOAutors(indexAut.getAutorsPrefix(prefix), ord);
    }

    // ---------- SETTERS ----------
    
    /**
     * Eliminar un Autor identificat pel paràmetre nom
     * 
     * @param nom representa el nom del autor a eliminar
     */
    public void eliminarAutor(String nom){
        indexAut.eliminarAutor(nom);
    }

    /**
     * Afegeix un titol al conjunt de titols de l'autor identificat per nomAutor
     * 
     * @param nomAutor representa el nom del autor al qual es vol afegir el titol
     * @param nouTitol representa el nou titol del autor
     **/
    public void afegirTitol(String nomAutor, String nouTitol) throws NoExisteixException{
        indexAut.afegirTitol(nomAutor, nouTitol);
    }

    /**
     * Elimina el titol identificat per nomTitol del conjunt de titols de l'autor identificat per nomAutor
     * 
     * @param nomAutor representa el nom del autor al qual es vol eliminar el titol
     * @param nomTitol representa el titol que es vol eliminar
     */
    public void eliminarTitol(String nomAutor,String nomTitol) throws NoExisteixException{
        indexAut.eliminarTitol(nomAutor,nomTitol);
    }
}
