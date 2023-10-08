package domini.utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

public class Pair<F, S> implements Serializable{

    // ---------- ATRIBUTS ----------
    private F first; //Variable que emmagatzema el primer valor del pair
    private S second; //Variable que emmagatzema el segon valor del pair

    // ---------- CONSTRUCTORES ----------
    public Pair (F first, S second){
        this.first = first;
        this.second = second;
    }

    public static <A, B> Pair <A, B> create(A a, B b) {
        return new Pair<A, B>(a, b);
    }

    @Override
    public boolean equals (Object o){
        if(!(o instanceof Pair)){
            return false;
        }
        Pair<?,?> p = (Pair<?,?>) o;
        return Objects.equals(p.first,first) && Objects.equals(p.second, second);
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (first ==null? 0 : second.hashCode());
        result = prime * result + (first ==null? 0 : second.hashCode());
        return result;
    }

    
    // ---------- GETTERS ----------
    /**
     * Retorna el primer element del pair
     * 
     * @return F
     */
    public F first(){
        return first;
    }

    /**
     * Retorna el segon element del pair
     * 
     * @return S
     */
    public S second(){
        return second;
    }

    // ---------- SETTERS ----------
    /**
     * S'assigna un nou element en el priemr lloc del pair
     * 
     * @param first és el nou primer element
     */
    public void setFirst(F first){
        this.first = first;
    }


    /**
     * S'assigna un nou element en el segon lloc del pair
     * 
     * @param sec és el nou segon element
     */
    public void setSecond(S sec){
        second = sec;
    }

    
}
