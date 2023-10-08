package persistencia.controladors;
import domini.exceptions.JaExisteixException;
import domini.exceptions.NoExisteixException;
import persistencia.classes.*;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class CtrlPersistencia {


  private GestorDocs GDocs = new GestorDocs();
  private GestorParaules GPar = new GestorParaules();
  private GestorAutors GAut = new GestorAutors();
  private GestorExps GExp = new GestorExps();

  public CtrlPersistencia(){

  }

  /**
   * Carrega el contingut o les frasses del Document donat
   *
   * @param titol representa el titol del document del qual es volen les frases o el contingut
   * @param autor representa el autor del document del qual es volen les frases o el contingut
   * @param contOFrases un string que representa si es volen les frases o el contingut
   * @return Un Object del qual es farà casteig al Controlador de Domini
   */
  public Object carregarContingutOFrases(String titol, String autor, String contOFrases) throws NoExisteixException{
    Object o = new Object();
    try {
      o = GDocs.carregarObjecte(File.separator + "docs" + File.separator + "continguts" + File.separator + contOFrases + "_" + titol+"_"+autor); // /docs/contingut
    } catch (IOException e) {
     e.printStackTrace();
      throw new NoExisteixException("No existeixen els binaris");
    }
    return o;
  }

  /**
   * Carrega paraules de Disc
   *
   * @param nomF representa el nom del fitxer que es vol carregar
   * @return Un Object del qual es farà casteig al Controlador de Domini
   */
  public Object carregarParaules(String nomF) throws NoExisteixException{
    Object o = new Object();
    try {
      o = GPar.carregarObjecte(nomF);
    } catch (IOException e) {
      e.printStackTrace();
      throw new NoExisteixException("No existeixen els binaris");
    }
    return o;
  }

  /**
   * Carrega expressions booleanes de Disc
   *
   * @param nomF representa el nom del fitxer que es vol carregar
   * @return Un Object del qual es farà casteig al Controlador de Domini
   */
  public Object carregarExps(String nomF) throws NoExisteixException{
    Object o = new Object();
    try {
      o = GExp.carregarObjecte(nomF);
    } catch (IOException e) {
      e.printStackTrace();
      throw new NoExisteixException("No existeixen els binaris");
    }
    return o;
  }

  /**
   * Carrega els autors de Disc
   *
   * @param nomF representa el nom del fitxer que es vol carregar
   * @return Un Object del qual es farà casteig al Controlador de Domini
   */
  public Object carregarAutors(String nomF) throws NoExisteixException {
    Object o = new Object();
    try {
      o = GAut.carregarObjecte(nomF);
    } catch (IOException e) {
      e.printStackTrace();
      throw new NoExisteixException("No existeixen els binaris");
    }
    return o;
  }


  /**
   * Carrega tots els documents del Disc
   *
   * @return Un ArrayList<Object> del qual es farà casteig al Controlador de Domini
   */
  public ArrayList<Object> carregarTotsDocuments() throws NoExisteixException{
    File folder = new File("src" + File.separator + "files" + File.separator + "docs" + File.separator);
    File[] listOfFiles = folder.listFiles();
    ArrayList<Object> docs = new ArrayList<>();

    for (File file : listOfFiles) {
      if (!file.getName().endsWith("README.md")){
        if (file.isFile()) {
          Object o = new Object();
          try {
            o = GDocs.carregarObjecte(File.separator + "docs" + File.separator + file.getName());
          } catch (IOException e) {
            e.printStackTrace();
          }
          docs.add(o);
        }
      }
    }
    return docs;
  }

  /**
   * Retorna un string d'un fitxer seleccionat
   *
   * @param path representa el path del fitxer seleccionat
   * @return Un String que representa el contingut del fitxer seleccionat
   */
  public String llegirArxiuEnString(String path) throws Exception{
    return GDocs.llegirStringArxiu(path);
  }

  /**
   * Exporta el document amb el nom final que es proporciona i amb el string proporcionat
   *
   * @param nomFinal representa el nom final del fitxer a exportar
   * @param res representa el string que serà el contingut del fitxer exportat
   */
  public void exportarDocument(String nomFinal, String res) throws JaExisteixException {
    GDocs.exportarDocument(nomFinal,res);
  }

  /**
   * Elimina el document amb el titol i autor donat
   *
   * @param titol representa el titol del document que es vol eliminar
   * @param autor representa el autor del document que es vol eliminar
   */
  public void eliminarDoc(String titol, String autor){
    GDocs.eliminarDoc(titol,autor);
  }

  /**
   * Guarda els autors a disc
   *
   * @param o representa el objecte que es vol guardar, representa que el Controlador de Domini farà un cast del index de Autors a la classe Object
   * @param nomF representa el nom final que tindrà el serial guardat
   */
  public void guardarAutors(Object o, String nomF){
    GAut.guardarObjecte(o, nomF);
  }


  /**
   * Guarda les expressions a disc
   *
   * @param o representa el objecte que es vol guardar, representa que el Controlador de Domini farà un cast del index de expressions a la classe Object
   * @param nomF representa el nom final que tindrà el serial guardat
   */
  public void guardarExps(Object o, String nomF){
    GExp.guardarObjecte(o, nomF);
  }


  /**
   * Guarda les expressions a disc
   *
   * @param o representa el objecte que es vol guardar, representa que el Controlador de Domini farà un cast del document, del contingut o de les frasses a la classe Object
   * @param nomF representa el nom final que tindrà el serial guardat
   */
  public void guardarDoc(Object o, String nomF){
    GDocs.guardarObjecte(o,nomF);
  }


  /**
   * Guarda les expressions a disc
   *
   * @param o representa el objecte que es vol guardar, representa que el Controlador de Domini farà un cast del index de paraules a la classe Object
   * @param nomF representa el nom final que tindrà el serial guardat
   */
  public void guardarParaules(Object o, String nomF){
    GPar.guardarObjecte(o,nomF);
  }


}
