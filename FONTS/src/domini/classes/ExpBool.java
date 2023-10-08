package domini.classes;

import domini.exceptions.NomExpressioIncorrecte;
import domini.utils.Node;
import domini.utils.Utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


public class ExpBool implements Serializable{

    // ---------- ATRIBUTS ----------
    private String nomExp; //Variable que emmagatzema el nom de la expressio
    private String expressio; //Variable que emmagatzema la expressio en forma de String
    private Node expressioParsed; //Variable que emmagatzema la expressio parsed


    // ---------- CONSTRUCTORES ----------
    public ExpBool(){}

    public ExpBool(String nom, String expressio) throws NomExpressioIncorrecte {
        if(nom.trim().length() == 0) throw new NomExpressioIncorrecte("El nom de la expressio es incorrecte");
        this.nomExp = nom;
        this.expressio = expressio;

        ArrayList<Node> nodes = passarPostFix(Utils.replaceBrackets(expressio));
        expressioParsed = new Node(nodes);
    }


    // ---------- GETTERS ----------
    /**
     * Retorna la expressio com un String
     *
     * @return String que representa la expressió
     */
    public String getExpressioAsString(){
        return expressio;
    }

    /**
     * Retorna el nom de la expressio com un String
     *
     * @return String que representa el nom de la expressió
     */
    public String getNomExpBool(){
        return this.nomExp;
    }

    /**
     * Retorna la expressio en forma d'arbre
     *
     * @return Node que representa la expressió en forma d'arbre
     */
    public Node getExpressioParsed(){
        return expressioParsed;
    }

    /**
     * Retorna cert si el nom de les expressions són iguals
     *
     * @param exp expressió a comparar
     * @return Booleà a 1 si són iguals o 0 si són diferents
     */
    public boolean equals (ExpBool exp){
        return nomExp.equals(exp.getNomExpBool());
    }

    // ---------- FUNCIONS PRIVADES ----------
    /**
     * Retorna la precedencia del operador & | !
     *
     * @param c representa el operador del que es vol saber la precedencia
     * @return Integer que representa el nombre de precedencia 2 si és ! 1 si és & i 0 si és |
     */
    private Integer getPrecedencia(String c){
        if(c.equals("&")) return 1;
        else if(c.equals("|")) return 0;
        else return 2;

    }

    /**
     * Aplica les regles per a construir la expressió en forma postfix
     *
     * @param element és el token que s'està evaluant
     * @param operadors un stack que representa tots els operadors que ja han sigut evaluats i estan a espera de ser introduits
     * @param nodesArbre un ArrayList que representa el resultat en ordre dels nodes que s'hauran d'unir per crear el arbre
     */
    private void applyRules(String element, Stack<String> operadors, ArrayList<Node> nodesArbre){
        element = element.strip();
        if(!element.matches("[(){}|&!]")){
            Node nod = new Node(element);
            nodesArbre.add(nod);
        }
        else if(element.equals(")") || element.equals("}")){
            String elementABuscar;
            if(element.equals(")")) elementABuscar = "(";
            else elementABuscar = "{";
            while (!operadors.peek().equals(elementABuscar)){
                String pop = operadors.pop();
                Node nod = new Node(pop);
                nodesArbre.add(nod);
            }
            operadors.pop();
        }
        else if(operadors.empty() || (operadors.peek().equals("(")  || operadors.peek().equals("{"))) operadors.push(element);
        else if(element.equals("(") || element.equals("{")) operadors.push(element);
        else if(!operadors.empty()){
            if (getPrecedencia(operadors.peek()) < getPrecedencia(element)) operadors.push(element);
            else if (getPrecedencia(operadors.peek()) == getPrecedencia(element)){
                Node nod = new Node(operadors.pop());
                nodesArbre.add(nod);
                operadors.push(element);
            }
            else if(getPrecedencia(operadors.peek()) > getPrecedencia(element)){
                Node nod = new Node(operadors.pop());
                nodesArbre.add(nod);
                applyRules(element,operadors,nodesArbre);
            }
        }
    }

    /**
     * Prepara la llista de nodes en postfix de cada element de l'expressio
     *
     * @param expressio representa la expressió booleana a ser passada a PostFix
     * @return Un ArrayList<Node> que representa una llista dels nodes
     */
    private ArrayList<Node> passarPostFix(String expressio){
        //{p1 p2 p3} & (“hola adéu” | pep) & !joan
        ArrayList<Node> nodesArbre = new ArrayList<>();
        ArrayList<String> cadaElement = new ArrayList<String>(Arrays.asList(expressio.split("(?<![^|&!(){}])|(?![^|&!(){}])")));
        Stack<String> operadors = new Stack<>();
        for(String element : cadaElement){
            if(!element.equals(" ")) applyRules(element.toLowerCase(), operadors, nodesArbre);
        }
        while(!operadors.empty()){
            Node nod = new Node(operadors.pop());
            nodesArbre.add(nod);
        }
        return nodesArbre;
    }
}