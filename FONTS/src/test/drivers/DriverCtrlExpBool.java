package test.drivers;

import domini.controladors.CtrlExpBool;
import domini.controladors.CtrlParaula;
import domini.exceptions.ExpressioNoValidaException;
import domini.exceptions.JaExisteixException;
import domini.exceptions.NoExisteixException;
import domini.exceptions.NomExpressioIncorrecte;
import domini.utils.Pair;

import java.util.ArrayList;
import java.util.Scanner;

public class DriverCtrlExpBool {

    private static Scanner in = new Scanner(System.in);
    private static CtrlParaula cp = new CtrlParaula();
    private static CtrlExpBool ceb = new CtrlExpBool(cp);
    public static void main (String [] args) throws ExpressioNoValidaException, NomExpressioIncorrecte, JaExisteixException, NoExisteixException{

        System.out.println("Driver de testeig de CtrlExpBool");

        mostra_metodes();
        System.out.println("Introdueix la funcionalitat que vulguis provar:");

        String input = in.nextLine();
        while (!"0".equals(input) && !"sortir".equals(input)) {
            switch (input) {
                case "1":
                case "CrearExpBool": {
                    CrearExpBoolTest();
                    break;
                }
                case "2":
                case "ModificarExpBool": {
                    modificarExpBoolTest();
                    break;
                }
                case "3":
                case "EliminarExpBool": {
                    eliminarExpBoolTest();
                    break;
                }
                case "4":
                case "GetExpBool": {
                    getExpBoolTest();
                    break;
                }
                case "5":
                case "ConsultarDocument": {
                    consultarDocumentTest();
                    break;
                }
                case "6":
                case "MostrarMetodes":{
                    mostra_metodes();
                    break;
                }
                default:
                    System.out.println("Valor invalid");
                    break;
            }
            System.out.println("Introdueix la funcionalitat que vulguis provar:");
            input = in.nextLine();
        }
        in.close();
    }

    private static void mostra_metodes() {
        System.out.println("(1|CrearExpBool) - Crea una Expressió Booleana");
        System.out.println("(2|ModificarExpBool) - Modifica el contingut d'una expressió");
        System.out.println("(3|EliminarExpBool) - Borra una instancia de ExpBool");
        System.out.println("(4|GetExpBool) - Obté el contingut d'una expressió Booleana");
        System.out.println("(5|ConsultarDocument) - Obté el conjunt de documents que compleixen una expressió");

        System.out.println("\n(0|sortir) - Tancar driver");
    }

    private static void CrearExpBoolTest() throws ExpressioNoValidaException, NomExpressioIncorrecte, JaExisteixException, NoExisteixException {
        System.out.println("Introdueix el nom de l'Expressió Booleana: ");
        String nom = in.nextLine();
        System.out.println("Introdueix el contingut de l'Expressió: ");
        String contingut = in.nextLine();

        try{
            ceb.crearExpBool(nom, contingut);
            System.out.println("Expressio creada");
        }
        catch (JaExisteixException e){
            System.out.println("Ja existeix la expressio booleana");
        }
        catch (NomExpressioIncorrecte e){
            System.out.println("Nom expressio no correcte");
        }
        catch (ExpressioNoValidaException e){
            System.out.println("Expressio booleana no es valida");
        }

    }

    private static void modificarExpBoolTest() throws NoExisteixException, ExpressioNoValidaException , JaExisteixException, NomExpressioIncorrecte{
        System.out.println("Introdueix el nom de l'Expressió Booleana que vol modificar: ");
        String nom = in.nextLine();
        System.out.println("Introdueix el nou contingut de l'Expressió: ");
        String contingut = in.nextLine();

        try {
            ceb.modificarExpBool(nom,contingut);
            System.out.println("Expressio modificada");
        }
        catch (NoExisteixException e){
            System.out.println("No existeix la expressio booleana");
        }
        catch (ExpressioNoValidaException e){
            System.out.println("Expressio booleana no correcta");
        }
    }

    private static void eliminarExpBoolTest() {
        System.out.println("Introdueix el nom de l'expressió que vol eliminar: ");
        String nom = in.nextLine();
        ceb.eliminarExpBool(nom);
        System.out.println("L'expressió s'ha eliminat correctament");
    }

    private static void getExpBoolTest() throws NoExisteixException{
        System.out.println("Introdueix el nom de l'expressió que vol consultar: ");
        String nom = in.nextLine();
        try {
            String exp = ceb.getExpBool(nom);
            System.out.println("El contingut de l'expressió seleccionada es: " + exp);
        }
        catch (NoExisteixException e) {
            System.out.println("No existeix la expressio Booleana");
        }
    }

    private static void consultarDocumentTest() throws NoExisteixException{
        System.out.println("Introdueix el nom de l'expressió amb la qual vols filtrar documents: ");
        String nom = in.nextLine();

        System.out.println("Introdueix un enter per representar la manera de ordenar (1 creixent alfabeticament per titol, altrament decreixent alfabeticament per titol");
        Integer ord = in.nextInt();

        try {
            ArrayList<Pair<String, String>> resultat = ceb.consultarDocExpBool(nom, ord);
            for (Pair<String,String> doc : resultat){
                System.out.println("Titol del document: " + doc.first()+"Autor del document: "+doc.second());
            }
        }
        catch (NoExisteixException e) {
            System.out.println("La expressio no existeix");
        }
    }
}
