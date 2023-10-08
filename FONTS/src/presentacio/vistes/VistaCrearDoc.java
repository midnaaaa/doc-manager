package presentacio.vistes;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import domini.exceptions.*;
import presentacio.CtrlPresentacio;


public class VistaCrearDoc {

  // ---------- ATRIBUTS ----------
  private CtrlPresentacio iCtrlPresentacion;
  private JFrame frameVista = new JFrame("Crear Document");
  private JPanel panelContent = new JPanel();
  private JPanel panelForm = new JPanel();
  private JLabel labelCrear = new JLabel("Crear ");
  private JLabel labelDocument = new JLabel("Document");
  private JLabel labelNomAutor = new JLabel("Autor:");
  private JLabel labelNomTitol = new JLabel("Titol:");
  private JLabel labelNomContingut = new JLabel("Contingut:");
  private JTextField nomAutor = new JTextField();
  private JTextField nomTitol = new JTextField();
  private JTextArea contingut = new JTextArea();
  private JButton crearDoc = new JButton("Crear");


  // ---------- CONSTRUCTORES ----------
  public VistaCrearDoc (CtrlPresentacio pCtrlPresentacion) {
    iCtrlPresentacion = pCtrlPresentacion;
    inicializarComponentes();
  }

  /**
   * Fa visivble la vista per crear documents
   * 
   */
  public void hacerVisible() {
    frameVista.pack();
    frameVista.setVisible(true);
  }

  /**
   * Fa invisivble la vista per crear documents
   * 
   */
  public void hacerInvisible() {
    frameVista.setVisible(false);
  }

  /**
   * Crea un document a partir dels inputs de l'usuari
   * @param event representa la accio que s'ha dut a terme
   */
  public void actionPerformed_buttonCrearDoc(ActionEvent event) {
    String titol = nomTitol.getText();
    String autor = nomAutor.getText();
    String Contingut = contingut.getText();
    try {
       Integer result = iCtrlPresentacion.crearDoc(titol, autor, Contingut);
       JOptionPane.showMessageDialog(null, "Document creat amb exit", "Document creat", JOptionPane.INFORMATION_MESSAGE);
       iCtrlPresentacion.actualitzaLlistaDocuments();
       nomAutor.setText("");
       nomTitol.setText("");
       contingut.setText("");
       frameVista.dispose();
    } catch (JaExisteixException e) {
       JOptionPane.showMessageDialog(null, "Error: El document ja existeix", "ERROR", JOptionPane.ERROR_MESSAGE);
    } catch (TitolIncorrecteException e) {
       if (titol.isEmpty() & autor.isEmpty()) JOptionPane.showMessageDialog(null, "Error: Titol i Autor no poden ser buits", "ERROR", JOptionPane.ERROR_MESSAGE);
       else JOptionPane.showMessageDialog(null, "Error: Titol no pot ser buit", "ERROR", JOptionPane.ERROR_MESSAGE);
    } catch (AutorIncorrecteException e) {
       JOptionPane.showMessageDialog(null, "Error: Autor no pot ser buit", "ERROR", JOptionPane.ERROR_MESSAGE);
    } catch (NoExisteixException e) {}
  }

  /**
   * Posa els listeners corresponents dels components que ens interesen
   * 
   */
  private void asignar_listenersComponentes() {

    crearDoc.addActionListener
      (new ActionListener() {
        public void actionPerformed (ActionEvent event) {
          actionPerformed_buttonCrearDoc(event);
        }
      });
  }

  /**
   * Inicialitzacio de tots els panells de la vista
   * 
   */
  private void inicializarComponentes() {
    inicializar_frameVista();
    inicializar_panelContent();
    inicializar_panelForm();
    asignar_listenersComponentes();
  }

  /**
   * Inicialitzar els components del panell principal
   * 
   */
  private void inicializar_frameVista() {
    // Mida
    frameVista.setMinimumSize(new Dimension(400,600));
    frameVista.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    frameVista.setPreferredSize(frameVista.getMinimumSize());
    frameVista.setResizable(true);
    // Posicion y operaciones por defecto
    frameVista.setLocationRelativeTo(null);
    // Se agrega panelContenidos al contentPane (el panelContenidos se
    // podria ahorrar y trabajar directamente sobre el contentPane)
    JPanel contentPane = (JPanel) frameVista.getContentPane();
    //contentPane.add(panelContenidos);
    contentPane.add(panelContent);
  }

  /**
   * Inicialitza els components que s'utilitzen per obtenir informació
   * 
   */
  private void inicializar_panelForm() {
    
    BoxLayout boxlayout = new BoxLayout(panelForm, BoxLayout.Y_AXIS);
    panelForm.setLayout(boxlayout);
    
    //Titol Frame
    JPanel titolPanel = new JPanel();
    titolPanel.setLayout(new GridLayout());
    labelCrear.setBorder(BorderFactory.createEmptyBorder( 10, 10, 10, 10));
    titolPanel.add(labelCrear);
    labelDocument.setBorder(BorderFactory.createEmptyBorder( 10, 10, 10, 10));
    titolPanel.add(labelDocument);
    panelForm.add(titolPanel);
    
    //autor
    JPanel autorPanel = new JPanel();
    autorPanel.setLayout(new GridLayout());
    labelNomAutor.setBorder(BorderFactory.createEmptyBorder( 10, 10, 10, 10));
    autorPanel.add(labelNomAutor);
    //nomAutor.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
    autorPanel.add(nomAutor);
    panelForm.add(autorPanel);

    //titol
    JPanel titPanel = new JPanel();
    titPanel.setLayout(new GridLayout());
    labelNomTitol.setBorder(BorderFactory.createEmptyBorder( 10, 10, 10, 10));
    titPanel.add(labelNomTitol);
    //nomTitol.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
    titPanel.add(nomTitol);
    panelForm.add(titPanel);

    //Contingut
    labelNomContingut.setBorder(BorderFactory.createEmptyBorder( 10, 10, 10, 10));
    panelForm.add(labelNomContingut);

    
    JScrollPane scrollContent = new JScrollPane(contingut);    
    scrollContent.setPreferredSize(new Dimension(400, 300));
    scrollContent.setBorder(BorderFactory.createEmptyBorder( 10, 10, 10, 10));
    panelForm.add(scrollContent);

    //Crear
    crearDoc.setBorder(BorderFactory.createEmptyBorder( 10, 10, 10, 10)); 
    panelForm.add(crearDoc);
  }

  /**
   * Inicialitza els components utilitzats per representar l'informació
   * 
   */
  private void inicializar_panelContent() {
    panelContent.setLayout(new BorderLayout());
    panelContent.add(panelForm,BorderLayout.CENTER);
  }

}
