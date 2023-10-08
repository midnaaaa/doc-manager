package presentacio.vistes;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

import javax.swing.*;
import javax.swing.event.*;

import domini.exceptions.NoExisteixException;
import domini.utils.Pair;

public class PanellConsultaExpBool extends JPanel{
    
    // ---------- ATRIBUTS ----------
    private VistaPrincipal vp;
    private ArrayList<Pair<String, String>> expBool;
    private JPanel panelExpBoolBtn = new JPanel();
    private JPanel panelExpBoolLlista = new JPanel();
    private JButton crearExpBool = new JButton("Crear"); 
    private JButton btnBuscarExp = new JButton("Buscar: ");  
    private JTextField tfSearchExp = new JTextField();
    private ComponentItemLlista rowExp;

    // ---------- CONSTRUCTORES ----------
    public PanellConsultaExpBool (VistaPrincipal vi) {
        vp = vi;

        inicializar_panelExpressio();
        inicializar_panelExpressioBtn();
        inicializar_panelExpressioLlista();
        actualitzaLlistaExpBool();
        asignar_listenersComponentes();
    }

    /**
     * Actualitza la llista de expressions booleanes.
     * 
     */
    public void actualitzaLlistaExpBool() {
      expBool = vp.getExpBools();
      vuidarPanelExpBoolLlista();
      afegirItems_panelExpBoolLlista();
      panelExpBoolLlista.updateUI();
    } 

    /**
     * Consulta les expressions booleanes.
     * 
     * @param event representa la accio que s'ha dut a terme
     */
    public void consultaExpBoolEvent (ActionEvent event) {
      String nomExp = tfSearchExp.getText();
      panelExpBoolLlista.removeAll();

      try{
        if (nomExp.isEmpty()) {
          totExpBool();
        }
        else {
          String exp = vp.consultaExpBool(nomExp);
          for(Pair<String, String> atrExpBool : expBool) {
            if (nomExp.equals(atrExpBool.first())) {
              rowExp = new ComponentItemLlista(vp, 1, atrExpBool);
              panelExpBoolLlista.add(rowExp);  
            }
          }
        }
        panelExpBoolLlista.updateUI();
      }
      catch (NoExisteixException e){
        JOptionPane.showMessageDialog(null, "Error: L'expressio no existeix", "ERROR", JOptionPane.ERROR_MESSAGE);
      }
    }

    /**
     * Posa els listeners corresponents dels components que ens interesen
     * 
     */
    private void asignar_listenersComponentes() {

      crearExpBool.addActionListener
          (new ActionListener() {
              public void actionPerformed (ActionEvent event) {
              String texto = ((JButton) event.getSource()).getText();
              vp.obrirCrearExpBool();
              }
          });
          
      btnBuscarExp.addActionListener
      (new ActionListener() {
          public void actionPerformed (ActionEvent event) {
            consultaExpBoolEvent(event);
          }
      });    
    }
    
    /**
     * Inicialitza tots els components necesaris per crear el panell que conte butons i textFields
     * 
     */
    private void inicializar_panelExpressioBtn() {
        panelExpBoolBtn.setLayout(new GridLayout(1, 4, 10, 10));
        //button crear
        panelExpBoolBtn.add(crearExpBool);
        //label buscar
        panelExpBoolBtn.add(btnBuscarExp);
        //textField
        panelExpBoolBtn.add(tfSearchExp);
        
      }
    
    /**
     * Inicialitzar tots els components necesaris per crear la llista de expressions booleanes.
     * 
     */
    private void  inicializar_panelExpressioLlista() {
  
      GridLayout layout = new GridLayout(10,1);
      layout.setVgap(5);
      panelExpBoolLlista.setLayout(layout);
  
     
    }

    /**
     * Afegeix les expressions booleanes a la llista.
     * 
     */
    private void afegirItems_panelExpBoolLlista() {
      totExpBool();
    }

    /**
     * Mostra en pantalla totes les expressions booleanes.
     * 
     */
    private void totExpBool() {
      if (expBool.size() <= 0) {
        
        JPanel row_ExpVuida = new JPanel();
        row_ExpVuida.setBackground(new java.awt.Color(153, 204, 255));
        row_ExpVuida.setLayout(new GridLayout(1, 1, 10, 10));
  
        row_ExpVuida.setPreferredSize(new Dimension(40, 20));
        JLabel textContent = new JLabel("No hi ha cap expressio booleana");
  
        row_ExpVuida.add(textContent, BorderLayout.NORTH);

        panelExpBoolLlista.add(row_ExpVuida);
      }
      else {
        for(Pair<String, String> atrExpBool : expBool) {
          rowExp = new ComponentItemLlista(vp, 1, atrExpBool);
          panelExpBoolLlista.add(rowExp);  
        }
      }
    }

    /**
     * Buida el llistat de expressions booleanes.
     * 
     */
    private void vuidarPanelExpBoolLlista() {
      panelExpBoolLlista.removeAll();
    }

    /**
     * Inicialitza els components necesaris per el panell de les expressions.
     * 
     */
    private void inicializar_panelExpressio() {
      // Layout
      this.setLayout(new BorderLayout());
      this.setPreferredSize(new Dimension(650, 300));

      // Componentes
      this.add(panelExpBoolBtn, BorderLayout.NORTH);
      this.add(panelExpBoolLlista, BorderLayout.CENTER);
    }
      
}
