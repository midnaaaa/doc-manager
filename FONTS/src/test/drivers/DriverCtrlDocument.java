package test.drivers;

import domini.classes.Document;
import domini.controladors.CtrlAutor;
import domini.controladors.CtrlDocument;
import domini.controladors.CtrlParaula;
import domini.exceptions.AutorIncorrecteException;
import domini.exceptions.JaExisteixException;
import domini.exceptions.NoExisteixException;
import domini.exceptions.TitolIncorrecteException;
import domini.utils.Pair;

import java.util.*;
public class DriverCtrlDocument {

    private static Scanner in = new Scanner(System.in);
    private static CtrlDocument cd = new CtrlDocument(new CtrlAutor(), new CtrlParaula());

    public static void main (String [] args) throws NoExisteixException, TitolIncorrecteException, AutorIncorrecteException, JaExisteixException{

        System.out.println("Driver de testeig de CtrlAutor");

        mostra_metodes();

        String input = in.nextLine();
        while (!"0".equals(input) && !"sortir".equals(input)) {
            switch (input) {
                case "1":
                case "crearDoc": {
                    provaCrearDoc();
                    break;
                }
                case "2":
                case "getDoc": {
                    provaGetDoc();
                    break;
                }
                case "3":
                case "allDocs": {
                    provaAllDocs();
                    break;
                }
                case "4":
                case "consultarDocSemblanca": {
                    provaConsultarDocSemblanca();
                    break;
                }
                case "5":
                case "eliminarDoc": {
                    provaEliminarDoc();
                    break;
                }
                case "6":
                case "modificarDoc": {
                    provaModificarDoc();
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
        System.out.println("(1|crearDoc) - Crea el document");
        System.out.println("(2|getDoc) - Obte el document");
        System.out.println("(3|allDocs) - Obte tots els documetns");
        System.out.println("(4|consultarDocSemblanca) - Obte tots els doc per semblança");
        System.out.println("(5|eliminarDoc) - Elimina el document");
        System.out.println("(6|modificarDoc) - Modifica el document");
        System.out.println("\n(0|sortir) - Tancar driver");
    }

    private static void provaCrearDoc() throws TitolIncorrecteException, JaExisteixException, AutorIncorrecteException, NoExisteixException{
        System.out.println("Crea document");
        System.out.println("Introdueix el nom del titol: ");
        String titol = in.nextLine();
        System.out.println("Introdueix el nom de l'Autor: ");
        String nomAutor = in.nextLine();
        System.out.println("Introdueix el contingut: ");
        String s1 = in.nextLine();
        try {
            cd.crearDoc(titol, nomAutor, s1);
            System.out.println("Document creat amb exit");
        }
        catch (TitolIncorrecteException e){
            System.out.println("Titol incorrecte");
        }
        catch (AutorIncorrecteException e) {
            System.out.println("Autor incorrecte");
        }
        catch (JaExisteixException e) {
            System.out.println("Ja existeix el document");
        }
    }

    private static void provaGetDoc() throws NoExisteixException{
        System.out.println("Obte el document");
        System.out.println("Introdueix el nom del titol: ");
        String titol = in.nextLine();
        System.out.println("Introdueix el nom de l'Autor: ");
        String nomAutor = in.nextLine();
        try {
            String s = cd.consultarContingut(titol, nomAutor);
            System.out.println("El contingut del document es: ");
            System.out.println(s);  
        }
        catch (NoExisteixException e) {
            System.out.println("No existeix el Document");
        }
    }

    private static void provaAllDocs() {
        System.out.println("Tots els documents");
        ArrayList<Pair<String,String>> documents = cd.allDocs();
        System.out.println("Tots els documents: ");
        for (Pair<String, String> p : documents) {
            System.out.println(p.first() + " " + p.second());
        }
    }

    private static void provaConsultarDocSemblanca() throws NoExisteixException{
        System.out.println("Obte els documents semblants");
        System.out.println("Introdueix el nom del titol: ");
        String titol = in.nextLine();

        System.out.println("Introdueix el nom de l'Autor: ");
        String nomAutor = in.nextLine();

        System.out.println("Introdueix el numero: ");
        Integer numero = in.nextInt();

        System.out.println("Introdueix el numero type: ");
        Integer numType = in.nextInt();

        System.out.println("Introdueix un enter per representar la manera de ordenar (0 ordre decreixent per semblança, 1 creixent alfabeticament per titol, altrament decreixent alfabeticament per titol");
        Integer ord = in.nextInt();

        try {
            ArrayList<Pair<String, String>> docSemblanca = cd.consultarDocSemblanca(titol, nomAutor, numero, numType, ord);
            for(Pair<String,String> doc : docSemblanca){
                System.out.println("Titol: " + doc.first() + " autor: " + doc.second());
            }
        }
        catch (NoExisteixException e) {
            System.out.println("No existeix document o autor\n");
        }
    }

    private static void provaEliminarDoc() throws NoExisteixException{
        System.out.println("Eliminar el document");
        System.out.println("Introdueix el nom del titol: ");
        String titol = in.nextLine();
        System.out.println("Introdueix el nom de l'Autor: ");
        String nomAutor = in.nextLine();

        try {
            cd.eliminarDoc(titol, nomAutor);
            System.out.println("Document eliminat correctament");
        }
        catch (NoExisteixException e){
            System.out.println("No existeix el document");
        }
    }

    private static void provaModificarDoc() throws NoExisteixException {
        System.out.println("Modifica el document");
        System.out.println("Introdueix el nom del titol: ");
        String titol = in.nextLine();
        System.out.println("Introdueix el nom de l'Autor: ");
        String nomAutor = in.nextLine();
        System.out.println("Introdueix el contingut: ");
        String contingut = in.nextLine();
        try{
            cd.modificarDoc(titol,nomAutor,contingut);
            System.out.println("Document modificat correctament");
        }
        catch (NoExisteixException e){
            System.out.println("No existeix el Document");
        }
    }
}

