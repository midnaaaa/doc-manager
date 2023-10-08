package presentacio.vistes;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import domini.exceptions.ExpressioNoValidaException;
import domini.exceptions.JaExisteixException;
import domini.exceptions.NoExisteixException;
import domini.exceptions.NomExpressioIncorrecte;
import presentacio.CtrlPresentacio;

public class VistaVisualitzaModificar {

  // ---------- ATRIBUTS ----------
  private CtrlPresentacio iCtrlPresentacion;
  private JFrame frameVista = new JFrame("Modificar contingut");
  private JPanel panelContent = new JPanel();
  private JPanel panelForm = new JPanel();
  private JLabel labelContingut = new JLabel("Contingut");
  private JLabel labelNomAutor = new JLabel("Autor:");
  private JLabel labelAutor;
  private JLabel labelNomTitol = new JLabel("Titol:");
  private JLabel labelTitol;
  private JLabel labelNomExpressio = new JLabel("Nom Expressió:");
  private JLabel labelNomExpBool;
  private JLabel labelExpressioBool = new JLabel("Expressió Booleana:");
  private JTextArea contingut;
  private JTextField expressio;
  private JButton Modificarbtn = new JButton("Guardar Canvis");

  int tipus;

  // ---------- CONSTRUCTORES ----------
  public VistaVisualitzaModificar (CtrlPresentacio pCtrlPresentacion, String autor, String titol, Integer type) {
    iCtrlPresentacion = pCtrlPresentacion;
    tipus = type;

    if (tipus == 0) { //DOC
      labelAutor = new JLabel(autor);
      labelTitol = new JLabel(titol);
      try { 
        String content = iCtrlPresentacion.consultarContingut(titol, autor);
        contingut = new JTextArea(content);
      } catch (NoExisteixException e) {
        JOptionPane.showMessageDialog(null, "Error: El document no existeix", "ERROR", JOptionPane.ERROR_MESSAGE);
      }
    }
    else { //EXPBOOL
      labelNomExpBool = new JLabel(titol);
      try {
        String expbool = iCtrlPresentacion.consultaExpBool(titol);
        expressio = new JTextField(expbool);
      } catch (NoExisteixException e) {
        JOptionPane.showMessageDialog(null, "Error: L'expressió no existeix", "ERROR", JOptionPane.ERROR_MESSAGE);
      }
    }
    
    //ini components
    inicializar_frameVista();
    inicializar_panelContent();
    inicializar_panelForm();
    asignar_listenersComponentes();
  }

  /**
   * Fa visivble la vista per visualitzar i modificar
   * 
   */
  public void hacerVisible() {
    frameVista.pack();
    frameVista.setVisible(true);
  }

  /**
   * Fa invisivble la vista per visualitzar i modificar
   * 
   */
  public void hacerInvisible() {
    frameVista.setVisible(false);
  }

  /**
   * Crida a la funcio de modificar amb el text dels inputs
   * 
   */
  public void actionPerformed_buttonModificar(ActionEvent event) {
    if (tipus == 0) {
      String c = contingut.getText();
      String a = labelAutor.getText();
      String t = labelTitol.getText();
      try {
        boolean b = iCtrlPresentacion.modificarContingut(t, a, c);
        JOptionPane.showMessageDialog(null, "Document modificat amb exit", "Document modificat", JOptionPane.INFORMATION_MESSAGE);
        frameVista.dispose();
      } catch (NoExisteixException e) {}
    }
    else {
      String exp = expressio.getText();
      String nom = labelNomExpBool.getText();
      try {
        Integer res = iCtrlPresentacion.modificarExpBool(nom, exp);
        JOptionPane.showMessageDialog(null, "Expressió Booleana modificada ", "Expressió modificada", JOptionPane.INFORMATION_MESSAGE);
        iCtrlPresentacion.actualitzaLlistaExpBool();
        frameVista.dispose();
      } catch (ExpressioNoValidaException e) {
        JOptionPane.showMessageDialog(null, "Error: L'expressió no es correcte", "ERROR", JOptionPane.ERROR_MESSAGE);
      } catch (NomExpressioIncorrecte e) {

      } catch (NoExisteixException e) {

      } catch (JaExisteixException e) {}
    }
  }

  /**
   * Posa els listeners corresponents dels components que ens interesen
   * 
   */
  private void asignar_listenersComponentes() {
      Modificarbtn.addActionListener
        (new ActionListener() {
          public void actionPerformed (ActionEvent event) {
            actionPerformed_buttonModificar(event);
          }
        });
  }

  /**
   * Inicialitzar el frame vista
   * 
   */
  private void inicializar_frameVista() {
    if (tipus == 0) frameVista.setMinimumSize(new Dimension(400,600));
    else frameVista.setMinimumSize((new Dimension(400, 200)));
    frameVista.setPreferredSize(frameVista.getMinimumSize());
    frameVista.setResizable(true);
    frameVista.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    // Posicion y operaciones por defecto
    frameVista.setLocationRelativeTo(null);
    JPanel contentPane = (JPanel) frameVista.getContentPane();
    contentPane.add(panelContent);
  }

  /**
   * Inicialitza els components que conte el panell amb butons
   * 
   */
  private void inicializar_panelForm() {
  
    if (tipus == 0) {
        BoxLayout boxlayout = new BoxLayout(panelForm, BoxLayout.Y_AXIS);
        panelForm.setLayout(boxlayout);

        //autor
        JPanel autorPanel = new JPanel();
        autorPanel.setLayout(new GridLayout());
        labelNomAutor.setBorder(BorderFactory.createEmptyBorder( 10, 10, 10, 10));
        autorPanel.add(labelNomAutor);
        autorPanel.add(labelAutor);
        panelForm.add(autorPanel);
  
        //titol
        JPanel titolPanel = new JPanel();
        titolPanel.setLayout(new GridLayout());
        labelNomTitol.setBorder(BorderFactory.createEmptyBorder( 10, 10, 10, 10));
        titolPanel.add(labelNomTitol);
        titolPanel.add(labelTitol);
        panelForm.add(titolPanel);
  
        //Contingut
        labelContingut.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelForm.add(labelContingut);

        JScrollPane scrollContent = new JScrollPane(contingut);    
        scrollContent.setPreferredSize(new Dimension(400, 300));
        scrollContent.setBorder(BorderFactory.createEmptyBorder( 10, 10, 10, 10));
        panelForm.add(scrollContent);
        Modificarbtn.setBorder(BorderFactory.createEmptyBorder( 10, 10, 10, 10)); 
    }
    else {
      panelForm.setLayout(new GridLayout(5, 2, 10, 10));
      labelNomExpressio.setBorder(BorderFactory.createEmptyBorder( 10, 10, 10, 10));
      panelForm.add(labelNomExpressio);
      labelNomExpBool.setBorder(BorderFactory.createEmptyBorder( 10, 10, 10, 10));
      panelForm.add(labelNomExpBool);
      labelExpressioBool.setBorder(BorderFactory.createEmptyBorder( 10, 10, 10, 10));
      panelForm.add(labelExpressioBool);
      panelForm.add(expressio);
    }
    panelForm.add(Modificarbtn);
  }

  /**
   * Inicialitza els components amb contingut
   * 
   */
  private void inicializar_panelContent() {
    panelContent.setLayout(new BorderLayout());
    // Paneles
    panelContent.add(panelForm,BorderLayout.CENTER);
  }
    
}
