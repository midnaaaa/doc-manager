package presentacio.vistes;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import presentacio.CtrlPresentacio;
import domini.exceptions.*;


public class VistaCrearExpBool {

  // ---------- ATRIBUTS ----------
  private CtrlPresentacio iCtrlPresentacion;
  private JFrame frameVista = new JFrame("Crear Exp Bool");
  private JPanel panelContenidos = new JPanel();
  private JPanel panelForm = new JPanel();
  private JLabel labelCrear = new JLabel("Crear ");
  private JLabel labelExpBool = new JLabel("ExpBool");
  private JLabel labelNomExpressio = new JLabel("Nom Expressió:");
  private JLabel labelExpressio = new JLabel("Expressió Booleana:");
  private JTextField tfnomExpressio = new JTextField();
  private JTextField tfExpressio = new JTextField();
  private JButton crearExpBool = new JButton("Crear");

  // ---------- CONSTRUCTORES ----------
  public VistaCrearExpBool (CtrlPresentacio pCtrlPresentacion) {
    iCtrlPresentacion = pCtrlPresentacion;
    inicializarComponentes();
  }

  /**
   * Fa visible la vista per crear documents
   * 
   */
  public void hacerVisible() {
    frameVista.pack();
    frameVista.setVisible(true);
  }

  /**
   * Fa invisible la vista per crear documents
   * 
   */
  public void hacerInvisible() {
    frameVista.setVisible(false);
  }

  /**
   * Crea una expressio booleana
   * @param event representa la accio que s'ha dut a terme
   */
  public void actionPerformed_buttoncrearExpBool(ActionEvent event) {
    String nomExp = tfnomExpressio.getText();
    String expressio = tfExpressio.getText();
    try {
      Integer result = iCtrlPresentacion.crearExpBool(nomExp, expressio);
      JOptionPane.showMessageDialog(null, "Expressió booleana creada amb exit", "Expressió creada", JOptionPane.INFORMATION_MESSAGE);
      tfnomExpressio.setText("");
      tfExpressio.setText("");
      iCtrlPresentacion.actualitzaLlistaExpBool();
      frameVista.dispose();
    } catch (ExpressioNoValidaException e) {
      if (expressio.isEmpty() & nomExp.isEmpty()) JOptionPane.showMessageDialog(null, "Error: El nom de l'expressió i l'expressió booleana no poden ser buits", "ERROR", JOptionPane.ERROR_MESSAGE);
      else JOptionPane.showMessageDialog(null, "Error: Expressió booleana no valida", "ERROR", JOptionPane.ERROR_MESSAGE);
    } catch (NomExpressioIncorrecte e) {
      JOptionPane.showMessageDialog(null, "Error: El nom de l'expressió no pot ser buit", "ERROR", JOptionPane.ERROR_MESSAGE);
    } catch (JaExisteixException e) {
      JOptionPane.showMessageDialog(null, "Error: L'expressió booleana ja existeix", "ERROR", JOptionPane.ERROR_MESSAGE);
    } catch (NoExisteixException e) {}
  }

  /**
   * Posa els listeners corresponents dels components que ens interesen
   * 
   */
  private void asignar_listenersComponentes() {

    crearExpBool.addActionListener
     (new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        actionPerformed_buttoncrearExpBool(event);
      }
    });
  }

  /**
   * Inicialitzacio de tots els panels
   * 
   */
  private void inicializarComponentes() {
    inicializar_frameVista();
    inicializar_panelContenidos();
    inicializar_panelForm();
    asignar_listenersComponentes();
  }

  /**
   * Inicialitzacio del panell principal
   * 
   */
  private void inicializar_frameVista() {
    // Mida
    frameVista.setMinimumSize(new Dimension(600,350));
    frameVista.setPreferredSize(frameVista.getMinimumSize());
    frameVista.setResizable(true);
    frameVista.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    // Posicion y operaciones por defecto
    frameVista.setLocationRelativeTo(null);
    // Se agrega panelContenidos al contentPane (el panelContenidos se
    // podria ahorrar y trabajar directamente sobre el contentPane)
    JPanel contentPane = (JPanel) frameVista.getContentPane();
    contentPane.add(panelContenidos);
  }

  /**
   * Inicialitzacio del panell que conte panells
   * 
   */
  private void inicializar_panelContenidos() {
    // Layout
    panelContenidos.setLayout(new BorderLayout());
    // Paneles
    panelContenidos.add(panelForm,BorderLayout.CENTER);
  }

  /**
   * Inicialitzacio dels components per obtenir informació
   * 
   */
  private void inicializar_panelForm() {
    panelForm.setLayout(new GridLayout(5, 2, 10, 10));
    
    //Titol Frame
    labelCrear.setBorder(BorderFactory.createEmptyBorder( 10, 10, 10, 10));
    panelForm.add(labelCrear);
    panelForm.add(labelExpBool);

    //nom de la expressio
    labelNomExpressio.setBorder(BorderFactory.createEmptyBorder( 10, 10, 10, 10));
    panelForm.add(labelNomExpressio);
    panelForm.add(tfnomExpressio);

    //Expressio booleana
    labelExpressio.setBorder(BorderFactory.createEmptyBorder( 10, 10, 10, 10));
    panelForm.add(labelExpressio);
    panelForm.add(tfExpressio);

    //Crear
    panelForm.add(crearExpBool);
  }

}
