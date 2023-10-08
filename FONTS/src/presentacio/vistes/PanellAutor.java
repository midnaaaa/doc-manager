package presentacio.vistes;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

public class PanellAutor extends JPanel{

    // ---------- ATRIBUTS ----------
    private VistaPrincipal vp;
    private ArrayList<String> autors = new ArrayList<>();
    private JTextField tfSearchAutor = new JTextField();
    private JButton btnBuscarAutor = new JButton("Buscar: ");  
    private JList llistaAutors = new JList<>();
    private JPanel panelAutorBtn = new JPanel();
    private JPanel panelAutorLlista = new JPanel();
    private JPanel laminaLlista = new JPanel();

    // ---------- CONSTRUCTORES ----------
    public PanellAutor(VistaPrincipal ivp) {
        vp = ivp;
        inicializar_panelAutors();
        inicializar_panelAutorsBtn();
        inicializar_panelAutorsLlista();

        asignar_listenersComponentes();
    }

    /**
     * Posa els listeners de events que provenen del components.
     * 
     */
    private void asignar_listenersComponentes() {          
      btnBuscarAutor.addActionListener
      (new ActionListener() {
          public void actionPerformed (ActionEvent event) {
            consultaAutorEvent(event);
          }
      });    
    }
    
    /**
     * Crida a consultar els autors per prefix i mostra el resultat
     * En cas d'error surt un JOptions
     * 
     * @param event representa la accio que s'ha dut a terme
     */
    public void consultaAutorEvent(ActionEvent event) {
      String nomAutorTF = tfSearchAutor.getText();
      llistaAutors.removeAll();

      autors = vp.consultarAutorPerPrefix(nomAutorTF, 1);
      
      DefaultListModel listModel = new DefaultListModel();
      for (int i = 0; i < autors.size(); i++) {
          listModel.addElement(autors.get(i));
      }
      llistaAutors.setModel(listModel);
      
      panelAutorLlista.updateUI();
    }

    /**
     * Inicialitza els components del panell Autor
     * 
     */
    private void inicializar_panelAutors() {
      // Layout
      this.setLayout(new BorderLayout());
      this.setPreferredSize(new Dimension(650, 300));
      
      // Componentes
      this.add(panelAutorBtn, BorderLayout.NORTH);
      this.add(panelAutorLlista, BorderLayout.CENTER);
    }

    /**
     * Inicialitza els components del panell que conte els butons.
     * 
     */
    private void inicializar_panelAutorsBtn() {
      
      panelAutorBtn.setLayout(new GridLayout(1, 2, 10, 10));
      //label buscar
      panelAutorBtn.add(btnBuscarAutor);
      //textField
      panelAutorBtn.add(tfSearchAutor);

    }

    /**
     * Inicialitza els components del panell que conte la llista.
     * 
     */
    private void inicializar_panelAutorsLlista() {
      
      panelAutorLlista.setLayout(new BorderLayout());
      
      actLlistaAutor();

      //el nombre de items que es veuran a la llista 5
      llistaAutors.setPreferredSize(new Dimension(200, 200));
      llistaAutors.setVisibleRowCount(5);

      JScrollPane scroll = new JScrollPane(llistaAutors);

      laminaLlista.add(scroll);

      panelAutorLlista.add(laminaLlista);
    }

    /**
     * Actualitza el llistat d'autors
     * 
     */
    public void actLlistaAutor() {
      autors = vp.consultarAutorPerPrefix("", 1);

      DefaultListModel listModel = new DefaultListModel();
      for (int i = 0; i < autors.size(); i++) {
          listModel.addElement(autors.get(i));
      }
      llistaAutors.setModel(listModel);
    }

}
