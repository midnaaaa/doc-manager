package test.drivers;

import domini.classes.Paraula;
import domini.controladors.CtrlParaula;
import domini.exceptions.NoExisteixException;
import domini.utils.Pair;

import java.util.*;

public class DriverCtrlParaula {

    private static Scanner in = new Scanner(System.in);
    private static CtrlParaula ctrlp = new CtrlParaula();

    public static void main (String [] args) throws NoExisteixException {

        System.out.println("Driver de testeig de CtrlParaula");

        mostra_metodes();

        String input = in.nextLine();
        while (!"0".equals(input) && !"sortir".equals(input)) {
            switch (input) {
                case "1":
                case "afegirParaula": {
                    provaAfegirParaula();
                    break;
                }
                case "2":
                case "obtenirParaula": {
                    provaObtenirParaula();
                    break;
                }
                case "3":
                case "obtenirNumTotalParaules": {
                    provaObtenirNumTotalParaules();
                    break;
                }
                case "4":
                case "obtenirFrasesParaula": {
                    provaObtenirFrasesParaula();
                    break;
                }
                case "5":
                case "obtenirFrases": {
                    provaObtenirFrases();
                    break;
                }
                case "6":
                case "eliminarParaula": {
                    provaEliminaParaula();
                    break;
                }
                case "7":
                case "recalcularIDF": {
                    provaRecalcularIDF();
                    break;
                }
                default:
                    System.out.println("Valor invalid");
                    break;
            }
            input = in.nextLine();
        }
        in.close();
    }

    private static void mostra_metodes() {
        System.out.println("(1|afegirParaula) - Afegeix una paraula a CtrlParaula");
        System.out.println("(2|obtenirParaula) - Obte la paraula que es consulta");
        System.out.println("(3|obtenirNumTotalParaules) - Obte el numero de paraules totals de CtrlParaula");
        System.out.println("(4|obtenirFrasesParaula) - Obte les frases de la paraula que es consulta");
        System.out.println("(5|obtenirFrases) - Obte totes les frases de CtrlParaula");
        System.out.println("(6|eliminarParaula) - Elimina una paraula");
        System.out.println("(7|recalcularIDF) - Recalcula el IDF de les paraules de CtrlParaula");
        System.out.println("\n(0|sortir) - Tancar driver");
    }

    private static void provaAfegirParaula() throws NoExisteixException{
        System.out.println("Introdueix la paraula: ");
        String paraula = in.nextLine();

        System.out.println("Introdueix el titol del document: ");
        String titol = in.nextLine();

        System.out.println("Introdueix l'autor del document: ");
        String autor = in.nextLine();

        System.out.println("Introdueix la frase del document on es troba la paraula: ");
        System.out.println("Posa stop per parar de introduir paraules");

        ArrayList<String> frase = new ArrayList<>();

        String paraula_frase = in.nextLine();
        String stop = "stop";

        while (!stop.equals(paraula_frase)) {
            frase.add(paraula_frase);
            paraula_frase = in.nextLine();
        }
        ctrlp.afegirParaula(paraula, titol, autor, frase);
        System.out.println("La paraula s'ha afegit correctament");
    }

    private static void provaObtenirParaula() throws NoExisteixException {
        System.out.println("Introdueix la paraula a consultar: ");
        String paraula = in.nextLine();

        try {
            Paraula p = ctrlp.getParaula(paraula);
            System.out.println("Paraula retornada: "+ p.getParaula());

        }
        catch (NoExisteixException e ){
            System.out.println("No existeix la paraula");
        }
    }

    private static void provaObtenirNumTotalParaules() {
        Integer total = ctrlp.getNumTotalParaules();
        System.out.println("Actualment hi han: "+ total +" paraules");
    }

    private static void provaObtenirFrasesParaula() throws NoExisteixException{
        System.out.println("Introdueix la paraula a consultar: ");
        String paraula = in.nextLine();

        HashSet<Pair<Pair<String, String>, ArrayList<String>>> frases = ctrlp.getFrasesParaula(paraula);
        for (Pair<Pair<String, String>, ArrayList<String>> p : frases) {
            System.out.println("Document amb titol: "+ p.first().first()+ " i autor: "+ p.first().second());
            for (String s : p.second()) {
                System.out.println(s);
            }
        }
    }

    private static void provaObtenirFrases() {
        System.out.println("Les frases son:");
        HashSet<Pair<Pair<String, String>, ArrayList<String>>> frases = ctrlp.getTotesFrases();

        for (Pair<Pair<String, String>, ArrayList<String>> p : frases) {
            System.out.println("Document amb titol: "+ p.first().first()+ " i autor: "+ p.first().second());
            System.out.println("Frases: ");
            for (String s : p.second()) {
                System.out.println(s);
            }
        }
    }

    private static void provaEliminaParaula() throws NoExisteixException {
        System.out.println("Introdueix la paraula a eliminar: ");
        String paraula = in.nextLine();

        System.out.println("Introdueix el titol del document on es troba "+ paraula+ " :");
        String titol = in.nextLine();

        System.out.println("Introdueix l'autor del document on es troba "+ paraula+ " :");
        String autor = in.nextLine();

        try {
            ctrlp.eliminarParaula(paraula, titol, autor);
            System.out.println(paraula+ " s'ha eliminat amb exit");
        }
        catch (NoExisteixException e){
            System.out.println("No existeix la paraula");
        }
    }

    private static void provaRecalcularIDF() {
        System.out.println("Introdueix el numero de documents al sistema: ");
        String numdoc = in.nextLine();
        Integer docs = Integer.parseInt(numdoc);

        HashMap<String,Double> idfs = ctrlp.recalcularIDF(docs);

        System.out.println("IDFS recalculats:");
        for (Map.Entry<String, Double> entry : idfs.entrySet()) {
            System.out.println(entry.getKey()+ " "+ entry.getValue());
        }
    }

}