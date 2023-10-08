package test.drivers;

import domini.controladors.CtrlAutor;
import domini.exceptions.NoExisteixException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class DriverCtrlAutor {

    private static Scanner in = new Scanner(System.in);
    private static CtrlAutor ca = new CtrlAutor();

    public static void main (String [] args) throws NoExisteixException{

        System.out.println("Driver de testeig de CtrlAutor");

        mostra_metodes();

        String input = in.nextLine();
        while (!"0".equals(input) && !"sortir".equals(input)) {
            switch (input) {
                case "1":
                case "construircrearAutor": {
                    provaConstruirCrearAutor();
                    break;
                }
                case "2":
                case "obtenirTitolsDunAutor": {
                    provaObtenirTitolsDunAutor();
                    break;
                }
                case "3":
                case "obtenirAutorsPrefix": {
                    provaObtenirAutorsPrefix();
                    break;
                }
                case "4":
                case "eliminarAutor": {
                    provaEliminarAutor();
                    break;
                }
                case "5":
                case "afegirTitol": {
                    provaAfegirTitol();
                    break;
                }
                case "6":
                case "eliminarTitol": {
                    provaEliminarTitol();
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
        System.out.println("(1|construircrearAutor) - Crear instancia de CtrlAutor");
        System.out.println("(2|obtenirTitolsDunAutor) - Obte els titols d'un autor");
        System.out.println("(3|obtenirAutorsPrefix) - Obte els autors que tenen el prefix");
        System.out.println("(4|eliminarAutor) - Elimina l'autor");
        System.out.println("(5|afegirTitol) - Afegir un titol a un autor");
        System.out.println("(6|eliminarTitol) - Eliminar un titol d'un autor");
        System.out.println("\n(0|sortir) - Tancar driver");
    }

    private static void provaConstruirCrearAutor() {
        System.out.println("Introdueix el nom de l'autor: ");
        String nom = in.nextLine();

        System.out.println("Introdueix els titols d'un autor: ");
        System.out.println("Posa stop per parar de introduir titols");

        Set<String> titols = new HashSet<>();

        String nomTitol = in.nextLine();

        while ( !"stop".equals(nomTitol)) {
            titols.add(nomTitol);
            nomTitol = in.nextLine();
        }
        ca.crearAutor(nom, titols);;
    }


    private static void provaObtenirTitolsDunAutor() throws NoExisteixException {
        System.out.println("Introdueix el nom de l'autor: ");
        String nomAutor = in.nextLine();

        System.out.println("Introdueix manera de ordenar (1 creixentment alfabeticament, altrament decreixentment): ");
        Integer ord = in.nextInt();
        try {
            ArrayList<String> titols = ca.getTitols(nomAutor, ord);
            System.out.println("Els titols son: ");
            for (String s : titols) {
                System.out.println(s);
            }
        }
        catch (NoExisteixException e) {
            System.out.println("No existeix el Autor");
        }

    }

    private static void provaObtenirAutorsPrefix() {
        System.out.println("Introdueix un prefix: ");
        String prefix = in.nextLine();

        System.out.println("Introdueix manera de ordenar (1 creixentment alfabeticament, altrament decreixentment): ");
        Integer ord = in.nextInt();

        ArrayList<String> autors = ca.getAutorsPrefix(prefix, ord);
        System.out.println("Els autors corresponents son:");
        System.out.println(autors);
    }

    private static void provaEliminarAutor() {
        System.out.println("Introdueix el nom de l'autor que vols eliminar: ");
        String nomAutor = in.nextLine();

        ca.eliminarAutor(nomAutor);
        System.out.println("Autor eliminat");
    }

    private static void provaAfegirTitol() throws NoExisteixException{
        System.out.println("Introdueix l'Autor en que vols afegir el titol: ");
        String nomAutor = in.nextLine();

        System.out.println("Introdueix el nou titol: ");
        String nomTitol = in.nextLine();

        try {
            ca.afegirTitol(nomAutor, nomTitol);
            System.out.println("Titol afegit");
        }
        catch (NoExisteixException e) {
            System.out.println("No existeix el Autor");
        }
    }

    private static void provaEliminarTitol() throws NoExisteixException{ //Si el autor no te titols, al eliminar un titol, s´eliminara el Autor
        System.out.println("Introdueix l'Autor en que vols eliminar el titol: ");
        String nomAutor = in.nextLine();

        System.out.println("Introdueix el titol que es vol eliminar: ");
        String nomTitol = in.nextLine();

        try {
            ca.eliminarTitol(nomAutor, nomTitol);
            System.out.println("Titol eliminat");
        }
        catch (NoExisteixException e) {
            System.out.println("No existeix el Autor");
        }
    }

}
