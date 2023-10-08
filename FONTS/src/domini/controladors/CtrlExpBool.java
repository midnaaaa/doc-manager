package domini.controladors;

import domini.classes.ExpBool;
import domini.classes.IndexExpBool;
import domini.exceptions.ExpressioNoValidaException;
import domini.exceptions.JaExisteixException;
import domini.exceptions.NoExisteixException;
import domini.exceptions.NomExpressioIncorrecte;
import domini.utils.Node;
import domini.utils.Pair;
import domini.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CtrlExpBool {
    
    // ---------- ATRIBUTS ----------
    private CtrlParaula ControladorParaula; //Variable que emmagatzema el controlador de Paraules
    private IndexExpBool IndexExpBool = new IndexExpBool(); //Variable que emmagatzema el index d'Expressions Booleanes


    // ---------- CONSTRUCTORES ----------
    public CtrlExpBool(CtrlParaula ControladorParaula){
        this.ControladorParaula = ControladorParaula;
    }




    // ---------- GETTERS ----------
    public ArrayList<Pair<String, String>> allExpBool() {
        return IndexExpBool.allExpBool();
    }


    /**
     * Retorna l'expressio de l'ExpBool identificat per nomExp
     * 
     * @param nomExp representa el nom de la expressio booleana que es vol buscar
     * @return Un String que representa la expressio integra
     **/
    public String getExpBool(String nomExp) throws NoExisteixException{
        ExpBool exp = IndexExpBool.getExpBool(nomExp);
        return exp.getExpressioAsString();
    }


    public IndexExpBool getIndex(){
        return this.IndexExpBool;
    }

    /**
     * Retorna els Documents que satisfan l'expressio booleana identificada per nomExp
     *
     * @param nomExp representa el nom de la expressio de la qual es vol fer la consulta
     * @param ord representa la forma de ordenar de la sortida
     * @return Un ArrayList<Pair<String, String>> que representa els documents que compleixen la expressio booleana
     */
    public ArrayList<Pair<String, String>> consultarDocExpBool(String nomExp, Integer ord) throws NoExisteixException{
        ExpBool exp = IndexExpBool.getExpBool(nomExp);
        HashSet<Pair<Pair<String,String>, ArrayList<String>>> frases = new HashSet<>();
        HashSet<Pair<String,String>> resultat = new HashSet<>();
        Node expressioParsed = exp.getExpressioParsed();
        frases = evaluarExpressio(expressioParsed);
        for(Pair<Pair<String,String>, ArrayList<String>> frase : frases){
            resultat.add(frase.first());
        }
        ArrayList<Pair<String,String>> res = new ArrayList<>(resultat);
        return Utils.ordenarDocuments(res,ord);
    }




    // ---------- SETTERS ----------
    /**
     * Crea una expressió booleana amb nom i contingut passats per paràmetres
     *
     * @param nomExp representa el nom de la expressio booleana a crear
     * @param expressio representa la expressio integra que es vol crear
     * @return Un Integer que indica si s'ha creat amb èxit
     */
     public Integer crearExpBool(String nomExp, String expressio) throws ExpressioNoValidaException, NomExpressioIncorrecte, JaExisteixException, NoExisteixException {//-1 Existeix exp
        if(!expressioCorrecte(expressio)) throw new ExpressioNoValidaException("Expressio no valida");
        IndexExpBool.crearExpBool(nomExp,expressio);
        ExpBool exp = IndexExpBool.getExpBool(nomExp);
        if(!arbreCorrecte(exp.getExpressioParsed())) {
            IndexExpBool.eliminarExpBool(nomExp);
            throw new ExpressioNoValidaException("Expressio no valida");
        }
        else return 1;
    }

    /**
     * Fa que el index de expressions passi a ser el índex passat per paràmetre
     *
     * @param iexp representa el nou index de expressions
     */
    public void setIndex(IndexExpBool iexp){
        this.IndexExpBool = iexp;
    }


    /**
     * Modifica l'expressió booleana identificada amb el nom passat com a paràmetre
     *
     * @param nomExp representa el nom de la expressio booleana a modificar
     * @param expressio representa la expressio integra que es vol modificar
     * @return Un Integer que indica si s'ha modificat amb èxit
     */
    public Integer modificarExpBool(String nomExp, String expressio) throws JaExisteixException, ExpressioNoValidaException, NoExisteixException, NomExpressioIncorrecte {
        if(IndexExpBool.getExpBool(nomExp) == null) throw new NoExisteixException("La expressio no existeix");
        else {
            String expAntiga = IndexExpBool.getExpBool(nomExp).getExpressioAsString();
            if(!expressioCorrecte(expressio)) throw new ExpressioNoValidaException("Expressio no valida");
            eliminarExpBool(nomExp);
            IndexExpBool.crearExpBool(nomExp,expressio);
            ExpBool exp = IndexExpBool.getExpBool(nomExp);
            if(!arbreCorrecte(exp.getExpressioParsed())) {
                IndexExpBool.eliminarExpBool(nomExp);
                IndexExpBool.crearExpBool(nomExp, expAntiga);
                throw new ExpressioNoValidaException("Expressio no valida");
            }
            else return 1;
        }
    }




    /**
     * Elimina l'expressió booleana identificada amb el nom passat com a paràmetre
     *
     * @param nomExp representa el nom de la expressio booleana a eliminar
     */
    public void eliminarExpBool(String nomExp) {
        IndexExpBool.eliminarExpBool(nomExp);
    }




    // ---------- FUNCIONS PRIVADES ----------
    /**
     * Consulta si una expressio es correcte
     * 
     * @param expressio representa la expressio que es vol mirar si és correcte
     * @return Retorna True si l'expressio es correcte. False en cas contrari
     */
    private boolean expressioCorrecte(String expressio){
        if(!Utils.parentesisCorrectes(expressio)) return false;
        if(expressio.trim().length() == 0) return false;
        if(expressio.charAt(expressio.length()-1) == '&' || expressio.charAt(expressio.length()-1) == '|' || expressio.charAt(expressio.length()-1) == '!') return false;
        expressio = Utils.replaceBrackets(expressio);
        ArrayList<String> cadaElement = new ArrayList<String>(Arrays.asList(expressio.split("(?<![^|&!(){}])|(?![^|&!(){}])")));
        for (int i = 0; i < cadaElement.size(); ++i){
            String element = cadaElement.get(i).strip();
            if(!element.equals("")){
                if(element.charAt(0) == '"' && element.charAt(element.length()-1) != '"' || element.charAt(0) != '"' && element.charAt(element.length()-1) == '"') return false;
                if(element.contains(" ") && (element.charAt(0) != '"' && element.charAt(0) != '{')) return false;
                if(i + 1 < cadaElement.size()-1 && cadaElement.get(i).equals(cadaElement.get(i+1)) && (!cadaElement.get(i).equals("(") && !cadaElement.get(i).equals(")"))) return false;
            }
        }
        return true;
    }





    /**
     * Consulta si una expressio en forma d'arbre es correcte
     * 
     * @param exp representa el arbre de la expressio booleana
     * @return Retorna True si l'arbre es correcte. False en cas contrari
     */
    private boolean arbreCorrecte(Node exp){
        if(exp.getData().equals("&") || exp.getData().equals("|")){
            Integer count = 0;
            if(exp.getEsquerre() != null) ++count;
            if(exp.getDreta() != null) ++count;
            if(count == 2){
                return arbreCorrecte(exp.getDreta()) && arbreCorrecte(exp.getEsquerre());
            }
            return false;
        }
        else if(exp.getData().equals("!")){
            Integer count = 0;
            if(exp.getEsquerre() != null) ++count;
            if(exp.getDreta() != null) ++count;
            if(count == 1){
                if(exp.getEsquerre() != null) return arbreCorrecte(exp.getEsquerre());
                else if(exp.getDreta() != null) return arbreCorrecte(exp.getDreta());
            }
            return false;
        }
        else {
            if(exp.getEsquerre() != null || exp.getDreta() != null) return false;
            else return true;
        }
    }




    /**
     * Evalua l'expressio en forma d'arbre per a la consulta de Documents amb Expressions Booleanes
     * 
     * @param exp representa la expressio booleana en forma de arbre
     * @return Un HashSet<Pair<Pair<String, String>, ArrayList<String>>> amb els documents i les frases que es corresponen amb la expressio booleana
     **/
    private HashSet<Pair<Pair<String,String>, ArrayList<String>>> evaluarExpressio(Node exp) throws NoExisteixException{
        HashSet<Pair<Pair<String,String>, ArrayList<String>>> frasesEsquerra = new HashSet<>();
        HashSet<Pair<Pair<String,String>, ArrayList<String>>> frasesDreta = new HashSet<>();
        HashSet<Pair<Pair<String,String>, ArrayList<String>>> frasesNot = new HashSet<>();


        if(exp.getEsquerre() == null && exp.getDreta() != null) frasesNot = evaluarExpressio(exp.getDreta());
        else if(exp.getDreta() == null && exp.getEsquerre() != null) frasesNot = evaluarExpressio(exp.getEsquerre());
        else if(exp.getEsquerre() == null && exp.getDreta() == null) {
            if(exp.getData().contains("\"")){
                String seqABuscar = exp.getData().substring(1,exp.getData().length()-1);
                ArrayList<String> seqABuscarList = new ArrayList<String>(Arrays.asList(seqABuscar.split(" ")));
                int i = exp.getData().indexOf(' ');
                String primeraParaula = exp.getData().substring(1, i);
                HashSet<Pair<Pair<String,String>, ArrayList<String>>> frasesAux = ControladorParaula.getFrasesParaula(primeraParaula);
                HashSet<Pair<Pair<String,String>, ArrayList<String>>> resultat = new HashSet<>();
                for(Pair<Pair<String,String>, ArrayList<String>> frase : frasesAux){
                    int k = frase.second().indexOf(primeraParaula);
                    boolean afegir = true;
                    for(int j = 0; j < seqABuscarList.size() && afegir; ++j){
                        if(seqABuscarList.size() > frase.second().size()) afegir = false;
                        if(!seqABuscarList.get(j).equals(frase.second().get(k))) afegir = false;
                        k++;
                    }
                    if(afegir) resultat.add(frase);
                }
                return resultat;
            }
            else return ControladorParaula.getFrasesParaula(exp.getData());
        }
        if(exp.getEsquerre() != null && exp.getDreta() != null){
            frasesEsquerra = evaluarExpressio(exp.getEsquerre());
            frasesDreta = evaluarExpressio(exp.getDreta());
        }

        if(exp.getData().equals("&")) return Utils.interseccio(frasesEsquerra, frasesDreta);
        if(exp.getData().equals("|")) return Utils.unio(frasesEsquerra, frasesDreta);
        if(exp.getData().equals("!")) return Utils.resta(ControladorParaula.getTotesFrases(), frasesNot);

        else return null;
    }
}
