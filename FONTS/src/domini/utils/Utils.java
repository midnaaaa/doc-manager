package domini.utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {

    // ---------- ATRIBUTS ----------
    //Variable que emmagatzema el valor de totes les paraules que son stop words
    private static HashSet<String> stopWords = Stream.of("últim",
            "última",
            "últimes",
            "últims",
            "a",
            "abans",
            "això",
            "al",
            "algun",
            "alguna",
            "algunes",
            "alguns",
            "allà",
            "allí",
            "allò",
            "als",
            "altra",
            "altre",
            "altres",
            "amb",
            "aprop",
            "aquí",
            "aquell",
            "aquella",
            "aquelles",
            "aquells",
            "aquest",
            "aquesta",
            "aquestes",
            "aquests",
            "cada",
            "catorze",
            "cent",
            "cert",
            "certa",
            "certes",
            "certs",
            "cinc",
            "com",
            "cosa",
            "d'",
            "darrer",
            "darrera",
            "darreres",
            "darrers",
            "davant",
            "de",
            "del",
            "dels",
            "després",
            "deu",
            "dinou",
            "disset",
            "divuit",
            "dos",
            "dotze",
            "durant",
            "el",
            "ell",
            "ella",
            "elles",
            "ells",
            "els",
            "en",
            "encara",
            "et",
            "extra",
            "fins",
            "hi",
            "i",
            "jo",
            "l'",
            "la",
            "les",
            "li",
            "llur",
            "lo",
            "los",
            "més",
            "m'",
            "ma",
            "massa",
            "mateix",
            "mateixa",
            "mateixes",
            "mateixos",
            "mes",
            "meu",
            "meva",
            "mig",
            "molt",
            "molta",
            "moltes",
            "molts",
            "mon",
            "mons",
            "n'",
            "na",
            "ni",
            "no",
            "nosaltres",
            "nostra",
            "nostre",
            "nou",
            "ns",
            "o",
            "on",
            "onze",
            "pel",
            "per",
            "però",
            "perquè",
            "perque",
            "poc",
            "poca",
            "pocs",
            "poques",
            "primer",
            "primera",
            "primeres",
            "primers",
            "prop",
            "què",
            "qual",
            "quals",
            "qualsevol",
            "qualssevol",
            "quan",
            "quant",
            "quanta",
            "quantes",
            "quants",
            "quatre",
            "que ",
            "qui",
            "quin",
            "quina",
            "quines",
            "quins",
            "quinze",
            "res",
            "s'",
            "sa",
            "segon",
            "segona",
            "segones",
            "segons",
            "sense",
            "ses",
            "set",
            "setze",
            "seu",
            "seus",
            "seva",
            "seves",
            "sino",
            "sis",
            "sobre",
            "son",
            "sons",
            "sota",
            "t'",
            "ta",
            "tal",
            "tals",
            "tan",
            "tant",
            "tanta",
            "tantes",
            "tants",
            "tes",
            "teu",
            "teus",
            "teva",
            "teves",
            "ton",
            "tons",
            "tot",
            "tota",
            "totes",
            "tots",
            "tres",
            "tretze",
            "tu",
            "un",
            "una",
            "unes",
            "uns",
            "vint",
            "vos",
            "vosaltres",
            "vosté",
            "vostés",
            "vostra",
            "vostre",
            "vuit",
            "a's",
            "able",
            "about",
            "above",
            "according",
            "accordingly",
            "across",
            "actually",
            "after",
            "afterwards",
            "again",
            "against",
            "ain't",
            "all",
            "allow",
            "allows",
            "almost",
            "alone",
            "along",
            "already",
            "also",
            "although",
            "always",
            "am",
            "among",
            "amongst",
            "an",
            "and",
            "another",
            "any",
            "anybody",
            "anyhow",
            "anyone",
            "anything",
            "anyway",
            "anyways",
            "anywhere",
            "apart",
            "appear",
            "appreciate",
            "appropriate",
            "are",
            "aren't",
            "around",
            "as",
            "aside",
            "ask",
            "asking",
            "associated",
            "at",
            "available",
            "away",
            "awfully",
            "b",
            "be",
            "became",
            "because",
            "become",
            "becomes",
            "becoming",
            "been",
            "before",
            "beforehand",
            "behind",
            "being",
            "believe",
            "below",
            "beside",
            "besides",
            "best",
            "better",
            "between",
            "beyond",
            "both",
            "brief",
            "but",
            "by",
            "c",
            "c'mon",
            "c's",
            "came",
            "can",
            "can't",
            "cannot",
            "cant",
            "cause",
            "causes",
            "certain",
            "certainly",
            "changes",
            "clearly",
            "co",
            "come",
            "comes",
            "concerning",
            "consequently",
            "consider",
            "considering",
            "contain",
            "containing",
            "contains",
            "corresponding",
            "could",
            "couldn't",
            "course",
            "currently",
            "d",
            "definitely",
            "described",
            "despite",
            "did",
            "didn't",
            "different",
            "do",
            "does",
            "doesn't",
            "doing",
            "don't",
            "done",
            "down",
            "downwards",
            "during",
            "e",
            "each",
            "edu",
            "eg",
            "eight",
            "either",
            "else",
            "elsewhere",
            "enough",
            "entirely",
            "especially",
            "etc",
            "even",
            "ever",
            "every",
            "everybody",
            "everyone",
            "everything",
            "everywhere",
            "ex",
            "exactly",
            "example",
            "except",
            "f",
            "far",
            "few",
            "fifth",
            "first",
            "five",
            "followed",
            "following",
            "follows",
            "for",
            "former",
            "formerly",
            "forth",
            "four",
            "from",
            "further",
            "furthermore",
            "g",
            "get",
            "gets",
            "getting",
            "given",
            "gives",
            "go",
            "goes",
            "going",
            "gone",
            "got",
            "gotten",
            "greetings",
            "h",
            "had",
            "hadn't",
            "happens",
            "hardly",
            "has",
            "hasn't",
            "have",
            "haven't",
            "having",
            "he",
            "he's",
            "hello",
            "help",
            "hence",
            "her",
            "here",
            "here's",
            "hereafter",
            "hereby",
            "herein",
            "hereupon",
            "hers",
            "herself",
            "him",
            "himself",
            "his",
            "hither",
            "hopefully",
            "how",
            "howbeit",
            "however",
            "i'd",
            "i'll",
            "i'm",
            "i've",
            "ie",
            "if",
            "ignored",
            "immediate",
            "in",
            "inasmuch",
            "inc",
            "indeed",
            "indicate",
            "indicated",
            "indicates",
            "inner",
            "insofar",
            "instead",
            "into",
            "inward",
            "is",
            "isn't",
            "it",
            "it'd",
            "it'll",
            "it's",
            "its",
            "itself",
            "j",
            "just",
            "k",
            "keep",
            "keeps",
            "kept",
            "know",
            "knows",
            "known",
            "l",
            "last",
            "lately",
            "later",
            "latter",
            "latterly",
            "least",
            "less",
            "lest",
            "let",
            "let's",
            "like",
            "liked",
            "likely",
            "little",
            "look",
            "looking",
            "looks",
            "ltd",
            "m",
            "mainly",
            "many",
            "may",
            "maybe",
            "me",
            "mean",
            "meanwhile",
            "merely",
            "might",
            "more",
            "moreover",
            "most",
            "mostly",
            "much",
            "must",
            "my",
            "myself",
            "n",
            "name",
            "namely",
            "nd",
            "near",
            "nearly",
            "necessary",
            "need",
            "needs",
            "neither",
            "never",
            "nevertheless",
            "new",
            "next",
            "nine",
            "nobody",
            "non",
            "none",
            "noone",
            "nor",
            "normally",
            "not",
            "nothing",
            "novel",
            "now",
            "nowhere",
            "obviously",
            "of",
            "off",
            "often",
            "oh",
            "ok",
            "okay",
            "old",
            "once",
            "one",
            "ones",
            "only",
            "onto",
            "or",
            "other",
            "others",
            "otherwise",
            "ought",
            "our",
            "ours",
            "ourselves",
            "out",
            "outside",
            "over",
            "overall",
            "own",
            "p",
            "particular",
            "particularly",
            "perhaps",
            "placed",
            "please",
            "plus",
            "possible",
            "presumably",
            "probably",
            "provides",
            "q",
            "que",
            "quite",
            "qv",
            "r",
            "rather",
            "rd",
            "re",
            "really",
            "reasonably",
            "regarding",
            "regardless",
            "regards",
            "relatively",
            "respectively",
            "right",
            "s",
            "said",
            "same",
            "saw",
            "say",
            "saying",
            "says",
            "second",
            "secondly",
            "see",
            "seeing",
            "seem",
            "seemed",
            "seeming",
            "seems",
            "seen",
            "self",
            "selves",
            "sensible",
            "sent",
            "serious",
            "seriously",
            "seven",
            "several",
            "shall",
            "she",
            "should",
            "shouldn't",
            "since",
            "six",
            "so",
            "some",
            "somebody",
            "somehow",
            "someone",
            "something",
            "sometime",
            "sometimes",
            "somewhat",
            "somewhere",
            "soon",
            "sorry",
            "specified",
            "specify",
            "specifying",
            "still",
            "sub",
            "such",
            "sup",
            "sure",
            "t",
            "t's",
            "take",
            "taken",
            "tell",
            "tends",
            "th",
            "than",
            "thank",
            "thanks",
            "thanx",
            "that",
            "that's",
            "thats",
            "the",
            "their",
            "theirs",
            "them",
            "themselves",
            "then",
            "thence",
            "there",
            "there's",
            "thereafter",
            "thereby",
            "therefore",
            "therein",
            "theres",
            "thereupon",
            "these",
            "they",
            "they'd",
            "they'll",
            "they're",
            "they've",
            "think",
            "third",
            "this",
            "thorough",
            "thoroughly",
            "those",
            "though",
            "three",
            "through",
            "throughout",
            "thru",
            "thus",
            "to",
            "together",
            "too",
            "took",
            "toward",
            "towards",
            "tried",
            "tries",
            "truly",
            "try",
            "trying",
            "twice",
            "two",
            "u",
            "under",
            "unfortunately",
            "unless",
            "unlikely",
            "until",
            "unto",
            "up",
            "upon",
            "us",
            "use",
            "used",
            "useful",
            "uses",
            "using",
            "usually",
            "uucp",
            "v",
            "value",
            "various",
            "very",
            "via",
            "viz",
            "vs",
            "w",
            "want",
            "wants",
            "was",
            "wasn't",
            "way",
            "we",
            "we'd",
            "we'll",
            "we're",
            "we've",
            "welcome",
            "well",
            "went",
            "were",
            "weren't",
            "what",
            "what's",
            "whatever",
            "when",
            "whence",
            "whenever",
            "where",
            "where's",
            "whereafter",
            "whereas",
            "whereby",
            "wherein",
            "whereupon",
            "wherever",
            "whether",
            "which",
            "while",
            "whither",
            "who",
            "who's",
            "whoever",
            "whole",
            "whom",
            "whose",
            "why",
            "will",
            "willing",
            "wish",
            "with",
            "within",
            "without",
            "won't",
            "wonder",
            "would",
            "wouldn't",
            "x",
            "y",
            "yes",
            "yet",
            "you",
            "you'd",
            "you'll",
            "you're",
            "you've",
            "your",
            "yours",
            "yourself",
            "yourselves",
            "z",
            "zero",
            "actualmente",
            "adelante",
            "además",
            "afirmó",
            "agregó",
            "ahora",
            "ahí",
            "algo",
            "algunas",
            "alguno",
            "algunos",
            "algún",
            "alrededor",
            "ambos",
            "ante",
            "anterior",
            "antes",
            "apenas",
            "aproximadamente",
            "aseguró",
            "así",
            "aunque",
            "ayer",
            "añadió",
            "aún",
            "bajo",
            "bien",
            "buen",
            "buena",
            "buenas",
            "bueno",
            "buenos",
            "casi",
            "cerca",
            "cierto",
            "cinco",
            "comentó",
            "como",
            "con",
            "conocer",
            "considera",
            "consideró",
            "contra",
            "cosas",
            "creo",
            "cual",
            "cuales",
            "cualquier",
            "cuando",
            "cuanto",
            "cuatro",
            "cuenta",
            "cómo",
            "da",
            "dado",
            "dan",
            "dar",
            "debe",
            "deben",
            "debido",
            "decir",
            "dejó",
            "demás",
            "dentro",
            "desde",
            "después",
            "dice",
            "dicen",
            "dicho",
            "dieron",
            "diferente",
            "diferentes",
            "dijeron",
            "dijo",
            "dio",
            "donde",
            "durante",
            "ejemplo",
            "ellas",
            "ello",
            "ellos",
            "embargo",
            "encuentra",
            "entonces",
            "entre",
            "era",
            "eran",
            "es",
            "esa",
            "esas",
            "ese",
            "eso",
            "esos",
            "esta",
            "estaba",
            "estaban",
            "estamos",
            "estar",
            "estará",
            "estas",
            "este",
            "esto",
            "estos",
            "estoy",
            "estuvo",
            "está",
            "están",
            "existe",
            "existen",
            "explicó",
            "expresó",
            "fin",
            "fue",
            "fuera",
            "fueron",
            "gran",
            "grandes",
            "ha",
            "haber",
            "habrá",
            "había",
            "habían",
            "hace",
            "hacen",
            "hacer",
            "hacerlo",
            "hacia",
            "haciendo",
            "han",
            "hasta",
            "hay",
            "haya",
            "hecho",
            "hemos",
            "hicieron",
            "hizo",
            "hoy",
            "hubo",
            "igual",
            "incluso",
            "indicó",
            "informó",
            "junto",
            "lado",
            "las",
            "le",
            "llegó",
            "lleva",
            "llevar",
            "luego",
            "lugar",
            "manera",
            "manifestó",
            "mayor",
            "mediante",
            "mejor",
            "mencionó",
            "menos",
            "mi",
            "mientras",
            "misma",
            "mismas",
            "mismo",
            "mismos",
            "momento",
            "mucha",
            "muchas",
            "mucho",
            "muchos",
            "muy",
            "más",
            "nada",
            "nadie",
            "ninguna",
            "ningunas",
            "ninguno",
            "ningunos",
            "ningún",
            "nos",
            "nosotras",
            "nosotros",
            "nuestra",
            "nuestras",
            "nuestro",
            "nuestros",
            "nueva",
            "nuevas",
            "nuevo",
            "nuevos",
            "nunca",
            "ocho",
            "otra",
            "otras",
            "otro",
            "otros",
            "para",
            "parece",
            "parte",
            "partir",
            "pasada",
            "pasado",
            "pero",
            "pesar",
            "pocas",
            "poco",
            "pocos",
            "podemos",
            "podrá",
            "podrán",
            "podría",
            "podrían",
            "poner",
            "por",
            "porque",
            "posible",
            "primero",
            "primeros",
            "principalmente",
            "propia",
            "propias",
            "propio",
            "propios",
            "próximo",
            "próximos",
            "pudo",
            "pueda",
            "puede",
            "pueden",
            "pues",
            "quedó",
            "queremos",
            "quien",
            "quienes",
            "quiere",
            "quién",
            "qué",
            "realizado",
            "realizar",
            "realizó",
            "respecto",
            "se",
            "sea",
            "sean",
            "segunda",
            "segundo",
            "según",
            "seis",
            "ser",
            "será",
            "serán",
            "sería",
            "señaló",
            "si",
            "sido",
            "siempre",
            "siendo",
            "siete",
            "sigue",
            "siguiente",
            "sin",
            "sola",
            "solamente",
            "solas",
            "solo",
            "solos",
            "su",
            "sus",
            "sí",
            "sólo",
            "también",
            "tampoco",
            "tanto",
            "tendrá",
            "tendrán",
            "tenemos",
            "tener",
            "tenga",
            "tengo",
            "tenido",
            "tenía",
            "tercera",
            "tiene",
            "tienen",
            "toda",
            "todas",
            "todavía",
            "todo",
            "todos",
            "total",
            "tras",
            "trata",
            "través",
            "tuvo",
            "unas",
            "uno",
            "unos",
            "usted",
            "va",
            "vamos",
            "van",
            "varias",
            "varios",
            "veces",
            "ver",
            "vez",
            "ya",
            "yo",
            "él",
            "ésta",
            "éstas",
            "éste",
            "éstos",
            "últimas",
            "último",
            "últimos",
            "").collect(Collectors.toCollection(HashSet::new)); ; //Paraules que s´han de obviar, a la segona entrega es llegiran d´un fitxer


    // ---------- CONSULTORES ----------
    
    /**
     * Retorna un boolea, true si la paraula no es una stopWord, false en cas contrari
     * 
     * @param paraula representa la paraula la qual es vol evaluar
     * @return Un boolean que representia si és StopWord o no
     */
    public static boolean paraulaValida(String paraula) {
        return !stopWords.contains(paraula);
    }


    /**
     * Retorna  la paraula introduida sense cap caracter extra, com podria ser !?
     * que estigui el final o principi de la paraula
     * 
     * @param paraula representa la paraula la qual es vol pasar a pura
     * @return Un String amb la paraula pura
     */
    public static String getParaulaPura(String paraula) {
        Integer tamanyParaula = paraula.length();
        if (paraula.equals("")) return paraula;
        char ultimChar = paraula.charAt(tamanyParaula - 1);
        char primerChar = paraula.charAt(0);
        if(primerChar == '¿' && ultimChar == '?' || primerChar == '¡' && ultimChar == '!') return paraula.substring(1,tamanyParaula-1);
        else if (ultimChar == '.' || ultimChar == ',' || ultimChar == '?' || ultimChar == '!') {
            return paraula.substring(0, tamanyParaula - 1);
        }
        else if (primerChar == '¿' || primerChar == '¡') return paraula.substring(1);
        else return paraula;
    }

    /**
     * Retorna un set corresponent a la interseccio de dos sets
     * 
     * @param frasesEsquerra representa les frases per la esquerra
     * @param frasesDreta representa les frases per la dreta
     * @return Un HashSet<Pair<Pair<String, String>, ArrayList<String>>> que representa els documents i les frases que son interseccio
     */
    public static HashSet<Pair<Pair<String, String>, ArrayList<String>>> interseccio(HashSet<Pair<Pair<String, String>, ArrayList<String>>> frasesEsquerra, HashSet<Pair<Pair<String, String>, ArrayList<String>>> frasesDreta) {
        HashSet<Pair<Pair<String, String>, ArrayList<String>>> resultat = new HashSet<>();
        if (frasesEsquerra.size() > frasesDreta.size()) {
            for (Pair<Pair<String, String>, ArrayList<String>> frase : frasesDreta) {
                if (frasesEsquerra.contains(frase)) {
                    resultat.add(frase);
                }
            }
        } else {
            for (Pair<Pair<String, String>, ArrayList<String>> frase : frasesEsquerra) {
                if (frasesDreta.contains(frase)) {
                    resultat.add(frase);
                }
            }
        }
        return resultat;
    }


    /**
     * Retorna un set corresponent a la unio de dos sets
     * 
     * @param frasesEsquerra representa les frases per la esquerra
     * @param frasesDreta representa les frases per la dreta
     * @return Un HashSet<Pair<Pair<String, String>, ArrayList<String>>> que representa els documents i les frases que son unio
     */
    public static HashSet<Pair<Pair<String, String>, ArrayList<String>>> unio(HashSet<Pair<Pair<String, String>, ArrayList<String>>> frasesEsquerra, HashSet<Pair<Pair<String, String>, ArrayList<String>>> frasesDreta) {
        HashSet<Pair<Pair<String, String>, ArrayList<String>>> resultat = new HashSet<>();
        for (Pair<Pair<String, String>, ArrayList<String>> frase : frasesDreta) {
            resultat.add(frase);
        }
        for (Pair<Pair<String, String>, ArrayList<String>> frase : frasesEsquerra) {
            resultat.add(frase);
        }
        return resultat;
    }

    /**
     * Retorna un Set corresponent a la resta entre dos sets
     * 
     * @param totesFrases representa totes les frases del sistema
     * @param frasesNot representa les frases que no es volen
     * @return Un HashSet<Pair<Pair<String, String>, ArrayList<String>>> que representa la resta de conjunts de frases
     */
    public static HashSet<Pair<Pair<String, String>, ArrayList<String>>> resta(HashSet<Pair<Pair<String, String>, ArrayList<String>>> totesFrases, HashSet<Pair<Pair<String, String>, ArrayList<String>>> frasesNot) {
        for(Pair<Pair<String, String>, ArrayList<String>> frases : frasesNot){
            if(totesFrases.contains(frases)) totesFrases.remove(frases);
        }
        return totesFrases;
    }

    /**
     * Retorna a partir d'un string, retorna true si el parentesisi es correcte altrament false
     * 
     * @param expressio representa una expressio booleana a evaluar
     * @return Un boolean que representa si els parentesis son correctes
     */
    public static boolean parentesisCorrectes(String expressio) {
        Stack<Character> parentesis = new Stack<>();
        for (char caracter : expressio.toCharArray()) {
            if (caracter == '(' || caracter == '{') parentesis.push(caracter);
            else {
                if(caracter == ')' || caracter == '}'){
                    if(parentesis.empty()) return false;
                    else{
                        if(caracter == ')' && parentesis.peek() == '(') parentesis.pop();
                        else if(caracter == '}' && parentesis.peek() == '{') parentesis.pop();
                    }
                }
            }
        }
        if (parentesis.empty()) return true;
        return false;
    }

    /**
     * A partir de una string que conte parentesis, si te curly brackets{} 
     * els converteix en parentesis normals ()
     * 
     * @param expressio representa la expressio booleana a la qual es volen eliminar els brackets
     * @return Un String amb la expressio sense brackets
     */
    public static String replaceBrackets(String expressio){
        String novaExp = "";
        boolean insideSet = false;
        for(int i = 0; i < expressio.length(); ++i){
            char element = expressio.charAt(i);
            if(element == '{') {
                novaExp = novaExp + '(';
                insideSet = true;
            }
            else if(element == '}') {
                novaExp = novaExp + ')';
                insideSet = false;
            }
            else if(insideSet && element == ' ') novaExp = novaExp.concat(" & ");
            else novaExp = novaExp + element;
        }
        return novaExp;
    }


    /**
     * Si ord és 1 ordena acendentment
     * altrament ordena decendentment
     * 
     * @param res representa la llista a ordenar
     * @param ord representa la forma de ordenar la llista
     * @return Un ArrayList<String> ordenat de la forma demanada
     */
    public static ArrayList<String> ordenarTitolsOAutors(ArrayList<String> res, Integer ord){
        if(ord == 1) {
            Collections.sort(res);
            return res;
        }
        else{
            res.sort((o1, o2) -> {
                Integer ret = o1.compareTo(o2);
                if(ret < 0) return 1;
                else if(ret == 0) return 0;
                else return -1;
            });
            return res;
        }
    }

    /**
     * si ord es 0 ordena no ordena
     * si ord es 1 ordena acendent
     * altrament ordena decendent
     * 
     * @param res representa la llista a ordenar
     * @param ord representa la forma de ordenar la llista
     * @return Un ArrayList<Pair<String, String>> ordenat de la forma demanada
     */
    public static ArrayList<Pair<String, String>> ordenarDocuments(ArrayList<Pair<String,String>> res, Integer ord){
        if(ord == 0) return res;
        else if(ord == 1){
            res.sort((o1, o2) -> {
                Integer ret = o1.first().compareTo(o2.first());
                if(ret < 0) return -1;
                else if(ret == 0) return 0;
                else return 1;
            });
            return res;
        }
        else {
            res.sort((o1, o2) -> {
                Integer ret = o1.first().compareTo(o2.first());
                if(ret < 0) return 1;
                else if(ret == 0) return 0;
                else return -1;
            });
            return res;
        }
    }
}