package domini.utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class Node implements Serializable{
    
    // ---------- ATRIBUTS ----------
    private String data; //Variable que emmagatzema el valor de un Node
    private Node dreta; //Variable que emmagatzema el Node dret
    private Node esquerre; //Variable que emmagatzema el Node esquerra



    // ---------- CONSTRUCTORES ----------
    public Node(String element){
        data = element;
        dreta = null;
        esquerre = null;
    }
    public Node(ArrayList<Node> nodes) {
        Stack<Node> pilaAux = new Stack<>();
        Integer numNodes = nodes.size();
        for (int i = 0; i < nodes.size(); i++) {
            if(nodes.size() == 1) break;
            Node nod = nodes.get(i);
            if (nod.getData().matches("[&|]")) {
                if (i < 2 && (nod.getDreta() == null || nod.getEsquerre() == null)) break;
                nod.esquerre = nodes.get(i - 1);
                nod.dreta = nodes.get(i - 2);
                nodes.remove(i - 1);
                nodes.remove(i - 2);
                i -= 2;
            } else if (nod.getData().equals("!")) {
                if (numNodes <= 1) break;
                if(i - 1 < 0) {
                    nodes = new ArrayList<>();
                    break;
                }
                nod.esquerre = nodes.get(i - 1);
                nodes.remove(i - 1);
                i -= 1;
            }
        }
        if (nodes.size() != 1) {
            data = "&";
            dreta = null;
            esquerre = null;
        } else {
            Node nodFinal = nodes.get(0);
            data = nodFinal.getData();
            dreta = nodFinal.getDreta();
            esquerre = nodFinal.getEsquerre();
        }
    }
    
    // ---------- GETTERS ----------
    /**
     * Retorna la data que conte el node
     *
     * @return String
     **/
    public String getData() {
        return data;
    }

    /**
     * Retorna el node Dret
     * 
     * @return Node
     */
    public Node getDreta(){return dreta;}

    /**
     * Retorna el node Esquerra
     * 
     * @return Node
     */
    public Node getEsquerre(){return esquerre;}

    /**
     * Retorna cert si els dos nodes son iguals
     * 
     * @param a és un Node
     * @param b és un Node
     * @return Un boolean que representa si dos Nodes son iguals
     */
    public boolean equals (Node a, Node b){
        if (a == null && b == null)
            return true;
        if (a != null && b != null){
            return (a.data.equals(b.data) && equals(a.esquerre, b.esquerre) && equals(a.dreta, b.dreta));
        }
        return false;
    }

    // ---------- SETTERS ----------
    /**
     * S'assigna un node dret amb valor
     * 
     * @param dreta el nou node dret
     */
    public void setDreta(Node dreta){ this.dreta = dreta;}

    /**
     * S'assigna un node esquerra amb valor
     * 
     * @param esquerre el nou node esquerra
     */
    public void setEsquerre(Node esquerre){ this.esquerre = esquerre;}

   
   
}