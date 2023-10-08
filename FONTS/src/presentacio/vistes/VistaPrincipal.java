package presentacio.vistes;

import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import domini.exceptions.*;
import domini.utils.Pair;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import presentacio.CtrlPresentacio;

import java.util.*;

////////////////////////
public class VistaPrincipal {

  // ---------- ATRIBUTS ----------
  private CtrlPresentacio iCtrlPresentacion;
  private JFrame frameVista = new JFrame("Vista Principal");
  private JPanel panelContenidos = new JPanel();
  private JPanel panelPrincipal = new JPanel();
  private JPanel panelAuxiliar = new JPanel();
  private PanellConsultaDoc panelDocument; 
  private PanellConsultaExpBool panelExpBool;
  private PanellAutor panelAutor;
  private JButton buttonDocuments = new JButton("Documents");
  private JButton buttonExpBool = new JButton("Expressions Booleanes");
  private JButton buttonAutors = new JButton("Autors");
  private JButton buttonImportDoc = new JButton("ImportarDocument");
  private JMenuBar menubarVista = new JMenuBar();
  private int iPanelActivo = 0;

  // ---------- CONSTRUCTORES ----------
  public VistaPrincipal (CtrlPresentacio pCtrlPresentacion) {
    iCtrlPresentacion = pCtrlPresentacion;
    panelDocument = new PanellConsultaDoc(this);
    panelExpBool = new PanellConsultaExpBool(this);
    panelAutor = new PanellAutor(this);

    inicializarComponentes();
  }

  /**
   * Fa visible la vista principal
   * 
   */
  public void hacerVisible() {
    frameVista.pack();
    frameVista.setVisible(true);
  }

  /**
   * Fa invisible la vista principal
   * 
   */
  public void activar() {
    frameVista.setEnabled(true);
  }

  /**
   * Desactiva la vista
   * 
   */
  public void desactivar() {
    frameVista.setEnabled(false);
  }

  /**
   * Obra la vista per visualitzar i modificar
   * @param autor representa el identificador del qual es vol obrir el document o expressio
   * @param titol representa el titol o expressio del qual es vol obrir el document o expressio
   * @param type representa si es vol obrir un document o una expressio
   */
  public void obrirDoc(String autor, String titol, Integer type) {
    iCtrlPresentacion.obraVisualitzaModificar(autor, titol, type);
  }

  /**
   * Obre la vista per crear documents
   * 
   */
  public void obrirPanellCrearDocs() {
    iCtrlPresentacion.sincronizacionVistaPrincipal_a_CrearDoc();
  }

  /**
   * Obre la vista per crear expressions booleanes
   * 
   */
  public void obrirCrearExpBool() {
    iCtrlPresentacion.sincronizacionVistaPrincipal_a_ExpBool();
  }

  /**
   * Obra un dialeg per fer import 
   * 
   * @param event representa l'accio que s'ha dut a terme
   */
  public void actionPerformed_buttonAbrirJChooseFile (ActionEvent event) {
    //String path
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

    jfc.setMultiSelectionEnabled(true);
		jfc.showOpenDialog(null);
    File[] files = jfc.getSelectedFiles();
    
    for (File f : files){
      try {
        iCtrlPresentacion.importarDocs(f);
        JOptionPane.showMessageDialog(null, "Document creat");
      }
      catch (JaExisteixException e) {
        JOptionPane.showMessageDialog(null, "Error: El document ja existeix", "ERROR", JOptionPane.ERROR_MESSAGE);
      } catch (TitolIncorrecteException e) {
        JOptionPane.showMessageDialog(null, "Error: Titol no pot ser buit", "ERROR", JOptionPane.ERROR_MESSAGE);
      } catch (AutorIncorrecteException e) {
        JOptionPane.showMessageDialog(null, "Error: Autor no pot ser buit", "ERROR", JOptionPane.ERROR_MESSAGE);
      } catch (NoExisteixException e) {
        JOptionPane.showMessageDialog(null, "Error: El fitxer no existeix", "ERROR", JOptionPane.ERROR_MESSAGE);
      } catch (ArxiuNoCorrecteException e){
        JOptionPane.showMessageDialog(null, "Error: El fitxer no es correcte", "ERROR", JOptionPane.ERROR_MESSAGE);
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error", "ERROR", JOptionPane.ERROR_MESSAGE);
      }
    }
    iCtrlPresentacion.actualitzaLlistaDocuments();
  }

  /**
   * Mostra el panell de documents
   * 
   * @param event representa l'accio que s'ha dut a terme
   */
  public void action_ActivePanellDoc (ActionEvent event) {
    iPanelActivo = 1;
  }

  /**
   * Mostre el panell de expressions booleanes
   * 
   * @param event representa l'accio que s'ha dut a terme
   */
  public void action_ActivePanellExp (ActionEvent event) {
    iPanelActivo = 2;
  }
  
  /**
   * Mostre el panell d'autors 
   * 
   * @param event representa l'accio que s'ha dut a terme
   */
  public void action_ActivePanellAutor (ActionEvent event) {
    iPanelActivo = 3;
  }

  /**
   * Actualitza la llista d'autors
   * 
   * @param event representa l'accio que s'ha dut a terme
   */
  public void action_actLlistaAutors (ActionEvent event) {
    panelAutor.actLlistaAutor();
  }

  /**
   * Fa el canvi de panells
   * 
   * @param event representa l'accio que s'ha dut a terme
   */
  public void actionPerformed_buttonCambiarPanel (ActionEvent event) {
    // Cambio de panel
    if (iPanelActivo != 0) {
      panelPrincipal.remove(panelAuxiliar);

      //Posa el panell Document
      if (iPanelActivo == 1) {
        panelAuxiliar = panelDocument;
      }

      //Posa el panell Expressio Booleana
      else if (iPanelActivo == 2) {
        panelAuxiliar = panelExpBool;
      }

      //Posa el panell Autor
      else {
        panelAuxiliar = panelAutor;
      }

      panelPrincipal.add(panelAuxiliar);
      frameVista.pack();
      frameVista.repaint();
    }
  }

  /**
   * Posa els listeners corresponents dels components que ens interesen
   * 
   */
  private void asignar_listenersComponentes() {

    frameVista.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
          iCtrlPresentacion.guardarADisc();
          
        }
    });

    buttonDocuments.addActionListener
      (new ActionListener() {
        public void actionPerformed (ActionEvent event) {
          action_ActivePanellDoc(event);
          actionPerformed_buttonCambiarPanel(event);
        }
      });

    buttonExpBool.addActionListener
      (new ActionListener() {
        public void actionPerformed (ActionEvent event) {
          action_ActivePanellExp(event);
          actionPerformed_buttonCambiarPanel(event);
        }
      });

    buttonAutors.addActionListener
      (new ActionListener() {
        public void actionPerformed (ActionEvent event) {
          action_actLlistaAutors(event);
          action_ActivePanellAutor(event);
          actionPerformed_buttonCambiarPanel(event);
        }
      });  

    buttonImportDoc.addActionListener
      (new ActionListener() {
        public void actionPerformed (ActionEvent event) {
          actionPerformed_buttonAbrirJChooseFile(event);
        }
      });
  }

  /**
   * Inicialitza tots els components
   * 
   */
  private void inicializarComponentes() {
    inicializar_frameVista();
    afegir_menubarVista();
    afegir_panelContenidos();
    afegir_panelConsultaDoc();

    asignar_listenersComponentes();
  }

  /**
   * Incialitza el frame
   * 
   */
  private void inicializar_frameVista() {
    frameVista.setMinimumSize(new Dimension(1100,500));
    frameVista.setPreferredSize(frameVista.getMinimumSize());
    frameVista.setResizable(true);
    // Posicion y operaciones por defecto
    frameVista.setLocationRelativeTo(null);
    frameVista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel contentPane = (JPanel) frameVista.getContentPane();
    contentPane.add(panelContenidos);
  }

  /**
   * Posa el menu bar
   * 
   */
  private void afegir_menubarVista() {
    menubarVista.add(buttonDocuments);
    menubarVista.add(buttonExpBool);
    menubarVista.add(buttonAutors);
    menubarVista.add(buttonImportDoc);

    frameVista.setJMenuBar(menubarVista);    
  }

  /**
   * Afegeix el panell de conetindors
   * 
   */
  private void afegir_panelContenidos() {
    // Layout
    panelContenidos.setLayout(new BorderLayout());
    // Paneles
    panelContenidos.add(panelPrincipal,BorderLayout.CENTER);
  }

  /**
   * Afegeix el panell de consultar documents
   * 
   */
  private void afegir_panelConsultaDoc() {
    panelAuxiliar = panelDocument;
    iPanelActivo = 1;
    panelPrincipal.add(panelAuxiliar);
  }

  /**
   * Actualitza la llista de documents
   * 
   */
  public void actualitzaLlistaDocuments() {
    panelDocument.actualitzaLlistaDocuments();
  }

  /**
   * Actualitza la llista d'expressions booleanes
   * 
   */
  public void actualitzaLlistaExpBool() {
    panelExpBool.actualitzaLlistaExpBool();
  }

  /**
   * Obte tots els documents
   * 
   * @return ArrayList<Pair<String, String>>
   */
  public ArrayList<Pair<String, String>> getDocuments() {
    return iCtrlPresentacion.getDocuments();
  }

  /**
   * Elimina un d'document concret
   * 
   * @param titol representa el titol del Document que es vol eliminar
   * @param autor representa el autor del Document que es vol eliminar
   */
  public void eliminarDoc(String titol, String autor) throws NoExisteixException {
    iCtrlPresentacion.eliminarDoc(titol, autor);
  }

  /**
   * Elimina una expressio booleana concreta
   * 
   * @param nomExp representa la expressio booleana que es vol eliminar
   */
  public void eliminarExpBool(String nomExp) {
    iCtrlPresentacion.eliminarExpBool(nomExp);
  }

  /**
   * Consulta una expressio booleana
   * 
   * @param nomExp representa la expressio booleana que es vol consultar
   * @return Un String amb la expressio integra
   */
  public String consultaExpBool(String nomExp) throws NoExisteixException {
      return iCtrlPresentacion.consultaExpBool(nomExp);
  }

  /**
   * Obte totes les expressions booleanes
   * 
   * @return Un ArrayList<Pair<String, String>> amb totes les expressions booleanes del sistema
   */
  public ArrayList<Pair<String, String>> getExpBools() {
    return iCtrlPresentacion.allExpBool();
  }
  
  /**
   * Consultar els titols d'un autor
   * 
   * @param nomAutor representa el nom del autor del qual es volen consultar els titols
   * @param ord representa la forma de ordenar la sortida
   * @return Un ArrayList<String> que representa tots els titols del autor donat
   */
  public ArrayList<String> consultarTitolsAutor(String nomAutor, Integer ord) throws NoExisteixException {
      return iCtrlPresentacion.consultarTitolsAutor(nomAutor, ord);
  }

  /**
   * Consultar autor per prefix
   * 
   * @param prefix representa el prefix del qual es busquen els autors
   * @param ord representa la forma de ordenar la sortida
   * @return Un ArrayList<String> que representa els autors que contenen el prefix
   */
  public ArrayList<String> consultarAutorPerPrefix(String prefix, Integer ord) {
      return iCtrlPresentacion.consultarAutorPerPrefix(prefix, ord);
  }

  /**
   * Consultar document per semblança
   * 
   * @param titol representa el titol del document del qual es vol fer la consulta
   * @param autor representa el autor del document del qual es vol fer la consulta
   * @param k representa el nombre de documents que es volen de sortida
   * @param type representa si es vol fer servir TF-IDF o BOW
   * @param ord representa la froma de ordenar la sortida
   * @return Un ArrayList<Pair<String, String>> que representa els documents més semblants al document donat
   */
  public ArrayList<Pair<String, String>> consultarDocSemblanca(String titol, String autor, int k, int type, int ord) throws NoExisteixException {
      return iCtrlPresentacion.consultarDocSemblanca(titol, autor, k, type, ord);
  }

  /**
   * Consultar document a partir d'una expressio booleana
   * 
   * @param nomExp representa el nom de la expressio booleana per a fer la consulta
   * @param ord representa la forma de ordenar els documents
   * @return Un ArrayList<Pair<String, String>> que representa tots els documents que compleixen la expressio booleana
   */
  public ArrayList<Pair<String, String>> consultaDocExpBool(String nomExp, Integer ord) throws NoExisteixException {
      return iCtrlPresentacion.consultaDocExpBool(nomExp, ord); 
  }

  /**
   * Exportar un document
   * 
   * @param nomFinal representa el nom final que tindrà el document exportat
   * @param titol representa el titol de document que es vol exportar
   * @param autor representa el autor del document que es vol exportar
   */
  public void exportarDoc(String nomFinal, String titol, String autor) throws NoExisteixException, JaExisteixException{
    iCtrlPresentacion.exportarDoc(nomFinal,titol,autor);
  }
}
