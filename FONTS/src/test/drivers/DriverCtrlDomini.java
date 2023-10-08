package test.drivers;

import domini.controladors.CtrlDomini;
import domini.exceptions.*;
import domini.utils.Pair;

import java.util.ArrayList;
import java.util.Scanner;

public class DriverCtrlDomini {

    private static Scanner in = new Scanner(System.in);
    private static CtrlDomini cd = new CtrlDomini();

    public static void main(String[] args) throws NoExisteixException, JaExisteixException, TitolIncorrecteException,
            AutorIncorrecteException, ExpressioNoValidaException, NomExpressioIncorrecte, Exception {

        try{
            cd.carregarDeDisc();
        }
        catch (NoExisteixException e){
            System.out.println("No existeixen els binaris");
        }
        cd.iniciarTimer();

        System.out.println("Driver de testeig de CtrlDomini");

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
                case "eliminarDoc": {
                    provaEliminarDoc();
                    break;
                }
                case "3":
                case "modificarDoc": {
                    provaModificarDoc();
                    break;
                }
                case "4":
                case "consultarContingut": {
                    provaConsultarContingut();
                    break;
                }
                case "5":
                case "consultarTitolsAutor": {
                    provaConsultarTitolsAutor();
                    break;
                }
                case "6":
                case "consultarAutorPerPrefix": {
                    provaConsultarAutorPerPrefix();
                    break;
                }
                case "7":
                case "crearExpBool": {
                    provaCrearExpBool();
                    break;
                }
                case "8":
                case "modificarExpBool": {
                    provaModificarExpBool();
                    break;
                }
                case "9":
                case "eliminarExpBool": {
                    provaEliminarExpBool();
                    break;
                }
                case "10":
                case "consultaExpBool": {
                    provaConsultaExpBool();
                    break;
                }
                case "11":
                case "consultarDocSemblanca": {
                    provaConsultarDocSemblanca();
                    break;
                }
                case "12":
                case "consultarDocExpBool": {
                    provaConsultarDocExpBool();
                    break;
                }

                case "13":
                case "exportarDoc": {
                    provaExportarDoc();
                    break;
                }

                case "14":
                case "guardarDisc": {
                    provaGuardarDisc();
                    break;
                }
                case "15":
                case "carregarDisc": {
                    provaCarregarDisc();
                    break;
                }
                case "16":
                case "importarDoc": {
                    provaImportarDoc();
                    break;
                }
                default:
                    System.out.println("Valor invalid");
                    break;
            }
            System.out.println("Escriu una opció");
            input = in.nextLine();
        }
        in.close();
    }

    private static void mostra_metodes() {
        System.out.println("(1|crearDoc) - Crear un Document");
        System.out.println("(2|eliminarDoc) - Elimina un Document");
        System.out.println("(3|modificarDoc) - Modifica un Document");
        System.out.println("(4|consultarContingut) - Consulta el contingut de un Document");
        System.out.println("(5|consultarTitolsAutor) - Consulta els titols de un Autor");
        System.out.println("(6|consultarAutorPerPrefix) - Consulta tots els autors amb el prefix");
        System.out.println("(7|crearExpBool) - Crea una expressio booleana");
        System.out.println("(8|modificarExpBool) - Modifica una expressio booleana");
        System.out.println("(9|eliminarExpBool) - Elimina una expressio booleana");
        System.out.println("(10|consultaExpBool) - Consulta la expressio booleana");
        System.out.println("(11|consultarDocSemblanca) - Consulta els k documents mes semblants al donat");
        System.out.println("(12|consultarDocExpBool) - Consulta els documents que tenen frases que compleixen la expressio booleana");
        System.out.println("(13|exportarDoc) - Exporta el document");
        System.out.println("(14|guardarDisc) - Guarda els indexs");
        System.out.println("(15|carregarDisc) - Carrega els indexs");
        System.out.println("(16|importarDocs) - Importa el document");
        System.out.println("\n(0|sortir) - Tancar driver");

    }

    private static void provaImportarDoc() throws Exception, JaExisteixException, TitolIncorrecteException, AutorIncorrecteException, NoExisteixException{
        ArrayList<String> paths = new ArrayList<>();
        System.out.println("Introdueix un path: ");
        String input = in.nextLine();
        while(!"-1".equals(input)){
            paths.add(input);
            System.out.println("Introdueix un path: ");
            input = in.nextLine();
        }
        for(String s : paths){
            try {
                cd.importarDocuments(s);
                System.out.println("Document creat amb exit");
            } catch (TitolIncorrecteException e) {
                System.out.println("Titol incorrecte");
            } catch (AutorIncorrecteException e) {
                System.out.println("Autor incorrecte");
            } catch (JaExisteixException e) {
                System.out.println("Ja existeix el document");
            }
            catch (ArxiuNoCorrecteException e){
                System.out.println("Arxiu no correcte");
            }
        }
    }
    private static void provaExportarDoc() throws NoExisteixException, JaExisteixException{
        System.out.println("Introdueix el nom amb el que vols exportar el document: ");
        String nomFinal = in.nextLine();

        System.out.println("Introdueix el titol del document: ");
        String titol = in.nextLine();

        System.out.println("Introdueix el nom del autor: ");
        String autor = in.nextLine();

        try{
            cd.exportarDocument(nomFinal,titol,autor);
        }
        catch (NoExisteixException e){
            System.out.println("No existeix el fitxer");
        }
        catch (JaExisteixException e){
            System.out.println("Ja existeix el fitxer amb aquest nom");
        }
    }

    private static void provaCarregarDisc() throws NoExisteixException{
        cd.carregarDeDisc();
    }

    private static void provaGuardarDisc(){
        cd.guardarADisc();
    }

    private static void provaCrearDoc() throws NoExisteixException, JaExisteixException, TitolIncorrecteException, AutorIncorrecteException {
        System.out.println("Introdueix el nom del titol: ");
        String titol = in.nextLine();

        System.out.println("Introdueix el nom del autor: ");
        String autor = in.nextLine();

        System.out.println("Introdueix el contingut del Document: ");
        String contingut = in.nextLine();

        try {
            cd.crearDoc(titol, autor, contingut);
            System.out.println("Document creat amb exit");
        } catch (TitolIncorrecteException e) {
            System.out.println("Titol incorrecte");
        } catch (AutorIncorrecteException e) {
            System.out.println("Autor incorrecte");
        } catch (JaExisteixException e) {
            System.out.println("Ja existeix el document");
        }
    }

    private static void provaEliminarDoc() throws NoExisteixException {
        System.out.println("Introdueix el nom del titol: ");
        String titol = in.nextLine();

        System.out.println("Introdueix el nom del autor: ");
        String autor = in.nextLine();

        try {
            cd.eliminarDoc(titol, autor);
            System.out.println("Document eliminat amb exit");
        } catch (NoExisteixException e) {
            System.out.println("No existeix el Document");
        }
    }

    private static void provaModificarDoc() throws NoExisteixException {
        System.out.println("Introdueix el nom del titol: ");
        String titol = in.nextLine();

        System.out.println("Introdueix el nom del autor: ");
        String autor = in.nextLine();

        System.out.println("Introdueix el contingut del Document: ");
        String contingut = in.nextLine();

        try {
            cd.modificarDoc(titol, autor, contingut);
            System.out.println("Document modificat correctament");
        } catch (NoExisteixException e) {
            System.out.println("No existeix el Document");
        }
    }

    private static void provaConsultarContingut() throws NoExisteixException {
        System.out.println("Introdueix el nom del titol: ");
        String titol = in.nextLine();

        System.out.println("Introdueix el nom del autor: ");
        String autor = in.nextLine();
        try {
            String cont = cd.consultarContingut(titol, autor);
            System.out.println(cont);
        } catch (NoExisteixException e) {
            System.out.println("No existeix el Document");
        }
    }

    private static void provaConsultarTitolsAutor() throws NoExisteixException {
        System.out.println("Introdueix el nom del autor: ");
        String autor = in.nextLine();

        System.out.println("Introdueix manera de ordenar (1 creixentment alfabeticament, altrament decreixentment): ");
        Integer ord = in.nextInt();

        try {
            ArrayList<String> resultat = cd.consultarTitolsAutor(autor, ord);
            System.out.println(resultat);
        } catch (NoExisteixException e) {
            System.out.println("No existeix el Autor");
        }
    }

    private static void provaConsultarAutorPerPrefix() {
        System.out.println("Introdueix el prefix: ");
        String prefix = in.nextLine();

        System.out.println("Introdueix manera de ordenar (1 creixentment alfabeticament, altrament decreixentment): ");
        Integer ord = in.nextInt();

        System.out.println(cd.consultarAutorPerPrefix(prefix, ord));
    }

    private static void provaCrearExpBool() throws ExpressioNoValidaException, NomExpressioIncorrecte, JaExisteixException, NoExisteixException {
        System.out.println("Introdueix el nom de la expressio booleana: ");
        String nomExp = in.nextLine();

        System.out.println("Introdueix la expressio booleana: ");
        String expressio = in.nextLine();

        try {
            cd.crearExpBool(nomExp, expressio);
            System.out.println("Expressio creada");
        } catch (JaExisteixException e) {
            System.out.println("Ja existeix la expressio booleana");
        } catch (NomExpressioIncorrecte e) {
            System.out.println("Nom expressio no correcte");
        } catch (ExpressioNoValidaException e) {
            System.out.println("Expressio booleana no es valida");
        }
    }

    private static void provaModificarExpBool() throws ExpressioNoValidaException, NoExisteixException, NomExpressioIncorrecte, JaExisteixException {
        System.out.println("Introdueix el nom de la expressio booleana: ");
        String nomExp = in.nextLine();

        System.out.println("Introdueix la expressio booleana: ");
        String expressio = in.nextLine();

        try {
            cd.modificarExpBool(nomExp, expressio);
            System.out.println("Expressio modificada");
        } catch (NoExisteixException e) {
            System.out.println("No existeix la expressio booleana");
        } catch (ExpressioNoValidaException e) {
            System.out.println("Expressio booleana no correcta");
        }
    }

    private static void provaEliminarExpBool() {
        System.out.println("Introdueix el nom de la expressio booleana: ");
        String nomExp = in.nextLine();

        cd.eliminarExpBool(nomExp);
        System.out.println("Expressio eliminada");
    }

    private static void provaConsultaExpBool() throws NoExisteixException {
        System.out.println("Introdueix el nom de la expressio booleana: ");
        String nomExp = in.nextLine();

        try {
            String expressio = cd.consultaExpBool(nomExp);
            System.out.println(expressio);
        } catch (NoExisteixException e) {
            System.out.println("No existeix la expressio booleana");
        }
    }

    private static void provaConsultarDocSemblanca() throws NoExisteixException {
        System.out.println("Introdueix el titol: ");
        String titol = in.nextLine();

        System.out.println("Introdueix el autor: ");
        String autor = in.nextLine();

        System.out.println("Introdueix un enter k: ");
        Integer k = in.nextInt();

        System.out.println("Introdueix un enter per representar el metode dels pesos (1 TFIDF o 2 BOW): ");
        Integer type = in.nextInt();

        System.out.println(
                "Introdueix un enter per representar la manera de ordenar (0 ordre decreixent per semblança, 1 creixent alfabeticament per titol, altrament decreixent alfabeticament per titol");
        Integer ord = in.nextInt();

        try {
            ArrayList<Pair<String, String>> docSemblanca = cd.consultarDocSemblanca(titol, autor, k, type, ord);
            for (Pair<String, String> doc : docSemblanca) {
                System.out.println("Titol: " + doc.first() + " autor: " + doc.second());
            }
        } catch (NoExisteixException e) {
            System.out.println("No existeix Document amb aquell titol o aquell autor\n");
        }

    }

    private static void provaConsultarDocExpBool() throws NoExisteixException {
        System.out.println("Introdueix el nom de la expressio booleana: ");
        String nomExp = in.nextLine();

        System.out.println(
                "Introdueix un enter per representar la manera de ordenar (1 creixent alfabeticament per titol, altrament decreixent alfabeticament per titol");
        Integer ord = in.nextInt();

        try {
            ArrayList<Pair<String, String>> resultat = cd.consultaDocExpBool(nomExp, ord);
            for (Pair<String, String> doc : resultat) {
                System.out.println(doc.first() + " " + doc.second());
            }
        } catch (NoExisteixException e) {
            System.out.println("La expressio no existeix");
        }
    }
}
