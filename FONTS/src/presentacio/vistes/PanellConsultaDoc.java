package presentacio.vistes;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.*;
import javax.swing.event.*;

import domini.exceptions.*;
import domini.utils.Pair;

public class PanellConsultaDoc extends JPanel{

    // ---------- ATRIBUTS ----------
    private VistaPrincipal vp;
    private ArrayList<Pair<String, String>> docs;
    private JPanel panelDocumentBtn = new JPanel();
    private JPanel panelDocumentLlista = new JPanel();
    private JButton crearDocuments = new JButton("Crear"); 
    private JButton btnBuscarDoc = new JButton("Buscar: ");
    private JTextField tfSearchDoc = new JTextField();
    private JTextField tfAutorDoc = new JTextField();
    private JTextField tfKDoc = new JTextField();
    private JComboBox comboxDoc = new JComboBox();
    private JRadioButton rbtnAscendent = new JRadioButton("Ascendent");
    private JRadioButton rbtnDescendent = new JRadioButton("Descendent");
    private JRadioButton rbtnSemblanca = new JRadioButton("Semblança");

    //Button group
    ButtonGroup bg = new ButtonGroup();
    
    //Componentes
    ComponentItemLlista rowDoc;

    // ---------- CONSTRUCTORES ----------
    public PanellConsultaDoc (VistaPrincipal vi) {
        vp = vi;
        //ini Panell Documents
        inicializar_panelDocument();
        inicializar_panelDocumentBtn();

        inicializar_panelDocumentLlista();
        actualitzaLlistaDocuments();
    
        asignar_listenersComponentes();
    }

    /**
     * Obra el Frame que ens permet crear documents
     * 
     * @param event
     */
    public void actionPerformed_buttonAbrirJFrame (ActionEvent event) {
      vp.obrirPanellCrearDocs();
    }

    /**
     * Mira quin tipus de cerca es vol fer, i col·loca els components necesaris per tal de poder fer la cerca.
     * 
     * @param event
     */
    public void changeSelection(ActionEvent event) {

      panelDocumentBtn.removeAll();

      if ("TF-IDF" == comboxDoc.getSelectedItem()) {
        panelDocumentBtn.add(crearDocuments);
        //label buscar
        panelDocumentBtn.add(btnBuscarDoc);
        //textField
        panelDocumentBtn.add(tfSearchDoc);
        panelDocumentBtn.add(tfAutorDoc);
        panelDocumentBtn.add(tfKDoc);
        //seleccionador
        panelDocumentBtn.add(comboxDoc);
        //radio button
        rbtnAscendent.setSelected(true);
        bg.add(rbtnAscendent);
        bg.add(rbtnDescendent);
        bg.add(rbtnSemblanca);
        panelDocumentBtn.add(rbtnAscendent);
        panelDocumentBtn.add(rbtnDescendent);
        panelDocumentBtn.add(rbtnSemblanca);
      }
      else if ("BOW" == comboxDoc.getSelectedItem()) {
        panelDocumentBtn.add(crearDocuments);
        //label buscar
        panelDocumentBtn.add(btnBuscarDoc);
        //textField
        panelDocumentBtn.add(tfSearchDoc);
        panelDocumentBtn.add(tfAutorDoc);
        panelDocumentBtn.add(tfKDoc);
        //seleccionador
        panelDocumentBtn.add(comboxDoc);
        //radio button
        rbtnAscendent.setSelected(true);
        bg.add(rbtnAscendent);
        bg.add(rbtnDescendent);
        bg.add(rbtnSemblanca);
        panelDocumentBtn.add(rbtnAscendent);
        panelDocumentBtn.add(rbtnDescendent);
        panelDocumentBtn.add(rbtnSemblanca);
      }
      else {
        panelDocumentBtn.add(crearDocuments);
        //label buscar
        panelDocumentBtn.add(btnBuscarDoc);
        //textField
        panelDocumentBtn.add(tfSearchDoc);
        //seleccionador
        panelDocumentBtn.add(comboxDoc);
        //radio button
        rbtnAscendent.setSelected(true);
        bg.add(rbtnAscendent);
        bg.add(rbtnDescendent);
        panelDocumentBtn.add(rbtnAscendent);
        panelDocumentBtn.add(rbtnDescendent);
      }
      panelDocumentBtn.updateUI();
    }

    /**
     * Mira quin tipus de cerca es vol fer, i crida la funcio corresponent a la cerca.
     * @param event
     */
    public void consultarDocuments (ActionEvent event) {
      String nameSearch = tfSearchDoc.getText();
      int ordre = 0;
      if (rbtnAscendent.isSelected()) {
        ordre = 1;
      }
      else if (rbtnDescendent.isSelected()) {
        ordre = 2;
      }

      vuidarPanelDocumentLlista();
      if (nameSearch.isEmpty()) {
        docs = vp.getDocuments();
      }
      else {
        if ("TF-IDF" == comboxDoc.getSelectedItem()) {
          String autor = tfAutorDoc.getText();
          int k;
          if(tfKDoc.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: No has posat cap nombre", "ERROR", JOptionPane.ERROR_MESSAGE);
          }
          else if(autor.isEmpty()) JOptionPane.showMessageDialog(null, "Error: No has posat cap autor", "ERROR", JOptionPane.ERROR_MESSAGE);
          else {
            if(tfKDoc.getText().matches("[0-9]+")){
                k = Integer.parseInt(tfKDoc.getText());
                consultaDocSemblan(nameSearch, autor, k, 1, ordre);
            }
            else{
                JOptionPane.showMessageDialog(null, "Error: Has posat un valor incorrecte, has de posar un nombre", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
          }
        }
        else if("BOW" == comboxDoc.getSelectedItem()) {
            String autor = tfAutorDoc.getText();
            int k;
            if(tfKDoc.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Error: No has posat cap nombre", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else if(autor.isEmpty()) JOptionPane.showMessageDialog(null, "Error: No has posat cap autor", "ERROR", JOptionPane.ERROR_MESSAGE);
            else {
                if(tfKDoc.getText().matches("[0-9]+")){
                    k = Integer.parseInt(tfKDoc.getText());
                    consultaDocSemblan(nameSearch, autor, k, 2, ordre);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Error: Has posat un valor incorrecte, has de posar un nombre", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if("Autor" == comboxDoc.getSelectedItem()) {
          consultaDocsAutor(nameSearch, ordre);
        }
        else {
          consultaDocExpBool(nameSearch, ordre);
        }
      }
      

      afegirItems_panelDocumentLlista();
      panelDocumentLlista.updateUI();
    }

    /**
     * Actualitza el llistat de Documents
     * 
     */
    public void actualitzaLlistaDocuments() {
      docs = vp.getDocuments();
      vuidarPanelDocumentLlista();
      afegirItems_panelDocumentLlista();
      panelDocumentLlista.updateUI();
    } 

    /**
     * Posa els listeners corresponents dels components que ens interesen
     * 
     */
    private void asignar_listenersComponentes() {
        crearDocuments.addActionListener
          (new ActionListener() {
            public void actionPerformed (ActionEvent event) {
              String texto = ((JButton) event.getSource()).getText();
              actionPerformed_buttonAbrirJFrame(event);
            }
          });

        btnBuscarDoc.addActionListener
        (new ActionListener() {
          public void actionPerformed (ActionEvent event) {
            consultarDocuments(event);
          }
        });

        comboxDoc.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent event) {
              changeSelection(event);
            }
        });
        
    }

    /**
     * Inicialitza els components del panell del document.
     * 
     */
    private void inicializar_panelDocument() {
        // Layout
        this.setLayout(new BorderLayout());
        // Componentes
        this.add(panelDocumentBtn,BorderLayout.NORTH);
        this.add(panelDocumentLlista,BorderLayout.CENTER);
    }

    /**
     * Inicialitza els components del panell del document que contenen buttons o textField i textArea.
     * 
     */
    private void inicializar_panelDocumentBtn() {
        panelDocumentBtn.setLayout(new GridLayout(1, 4, 10, 10));
        //button crear
        panelDocumentBtn.add(crearDocuments);
        //label buscar
        panelDocumentBtn.add(btnBuscarDoc);
        //textField
        panelDocumentBtn.add(tfSearchDoc);
        //seleccionador
        panelDocumentBtn.add(comboxDoc);
        //radio button
        rbtnAscendent.setSelected(true);
        bg.add(rbtnAscendent);
        bg.add(rbtnDescendent);
        panelDocumentBtn.add(rbtnAscendent);
        panelDocumentBtn.add(rbtnDescendent);
    
        comboxDoc.addItem("Autor");
        comboxDoc.addItem("TF-IDF");
        comboxDoc.addItem("BOW");
        comboxDoc.addItem("ExpBool");
      }

    /**
     * Inicialitza els components del panell del document que generen la llista. 
     * 
     */
    private void inicializar_panelDocumentLlista() { 
      GridLayout layout = new GridLayout(10,1);
      layout.setVgap(5);
      panelDocumentLlista.setLayout(layout);
    }
    
    /**
     * Afegeix els ComponentItemLlista corresponents a cada document.
     * 
     */
    private void afegirItems_panelDocumentLlista() { 
    
      if (docs.size() <= 0) {
        JPanel row_vuida = new JPanel();
        row_vuida.setBackground(new java.awt.Color(153, 204, 255));
        row_vuida.setLayout(new GridLayout(1, 1, 10, 10));

        row_vuida.setPreferredSize(new Dimension(40, 20));
        JLabel textContent = new JLabel("No hi ha cap document");

        row_vuida.add(textContent, BorderLayout.NORTH);

        panelDocumentLlista.add(row_vuida);
      }
      else {
        for( Pair<String, String> docId : docs) {
          rowDoc = new ComponentItemLlista(vp, 0, docId);
          panelDocumentLlista.add(rowDoc);  
        }
      }
    }

    /**
     * Buida tota la llista.
     * 
     */
    private void vuidarPanelDocumentLlista() {
      panelDocumentLlista.removeAll();
    }

    /**
     * Crida la funcio per fer la consulta dels documents que te un autor
     * 
     */
    private void consultaDocsAutor(String name, int ordre) {
      ArrayList<Pair<String, String>> newDocs = new ArrayList<>();
      try{
        ArrayList<String> titols = new ArrayList<>();
        titols = vp.consultarTitolsAutor(name, ordre);

        for (String titol : titols) {
          Pair<String, String> newDoc = new Pair<String,String>(titol, name);
          newDocs.add(newDoc);
        }

        docs = newDocs;
      }
      catch (NoExisteixException e){
        JOptionPane.showMessageDialog(null, "Error: L'autor no existeix", "ERROR", JOptionPane.ERROR_MESSAGE);
      }
    }

    /**
     * Crida la funcio per fer la consulta dels documents per semblança.
     * 
     */
    private void consultaDocSemblan(String titol, String autor, int k, int type, int ord) {
      try{
        docs = vp.consultarDocSemblanca(titol, autor, k, type, ord);
      }
      catch (NoExisteixException e) {
        JOptionPane.showMessageDialog(null, "Error: El document no existeix", "ERROR", JOptionPane.ERROR_MESSAGE);
      }  
    }

    /**
     * Crida la funcio per fer la consulta dels documents per expressio booleana.
     * 
     */
    private void consultaDocExpBool(String nomExp, Integer ord)  {
      try{
        docs = vp.consultaDocExpBool(nomExp, ord); 
      }
      catch (NoExisteixException e) {
        JOptionPane.showMessageDialog(null, "Error: L'expressio no existeix", "ERROR", JOptionPane.ERROR_MESSAGE);
      } 
      
    }
   
}
