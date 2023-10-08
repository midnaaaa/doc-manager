package presentacio.vistes;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import domini.exceptions.NoExisteixException;
import domini.exceptions.JaExisteixException;
import domini.utils.Pair;

public class ComponentItemLlista extends JPanel{

    // ---------- ATRIBUTS ----------
    private JLabel labelTitol;
    private JLabel labelAutor;
    private JButton exportBtn = new JButton("Exportar");
    private JButton modificarBtn = new JButton("Obrir");
    private JButton deleteBtn = new JButton("Eliminar");

    private VistaPrincipal ivp;
    private int typeComp;

    // ---------- CONSTRUCTORES ----------
    /* 
     * TYPE = 0 => DOC
     * TYPE = 1 => EXP
     */
    ComponentItemLlista(VistaPrincipal pvp, int type, Pair<String, String> text) {
        ivp = pvp;
        typeComp = type;

        this.setBackground(new java.awt.Color(153, 204, 255));
        this.setLayout(new GridLayout());

        //Labels
        labelTitol = new JLabel(text.first());
        labelTitol.setPreferredSize(new Dimension(20,20));
        labelTitol.setHorizontalAlignment(JLabel.CENTER);
        this.add(labelTitol);

        labelAutor = new JLabel(text.second());
        labelAutor.setPreferredSize(new Dimension(20,20));
        labelAutor.setHorizontalAlignment(JLabel.CENTER);
        this.add(labelAutor);

        modificarBtn.setPreferredSize(new Dimension(40, 20));
        this.add(modificarBtn);

        if (typeComp == 0){
            //Action Button
            exportBtn.setPreferredSize(new Dimension(40,20));
            this.add(exportBtn);
        }

        deleteBtn.setPreferredSize(new Dimension(40, 20));
        this.add(deleteBtn);
        inicializarComponentes();
    }

    /**
     * Crida la funcio d'eliminar el document si l'usuari, fa click en que n'està segur que vol eliminar el document.
     * 
     * @param event representa l'accio que s'ha dut a terme
     */
    public void actionPerformed_buttonAbrirAvisDelete (ActionEvent event) {
        if (typeComp == 0) { //DOCS
          int result = JOptionPane.showConfirmDialog(null, "Si esborra el document no hi podra tornar a accedir\n"+"¿Estas segur?",
            "Alerta!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
          //si esta segur que es vol eliminar
          if (result == 1) {
            JOptionPane.showMessageDialog(null, "El document no s'ha eliminat", "Document no eliminat", JOptionPane.INFORMATION_MESSAGE);
          }
          else {
            JOptionPane.showMessageDialog(null, "El document s'ha eliminat amb exit", "Document eliminat", JOptionPane.INFORMATION_MESSAGE);
            String titol = labelTitol.getText();
            String autor = labelAutor.getText();
            try { 
               ivp.eliminarDoc(titol, autor);
            } catch (NoExisteixException e) {
               JOptionPane.showMessageDialog(null, "Error: El document no existeix", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            ivp.actualitzaLlistaDocuments();
          }
        }
        else { //ExpBool
            int result = JOptionPane.showConfirmDialog(null, "Si esborra l'expressió no hi podrà torna a accedir\n"+"¿Estas segur?", "ALERTA!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
               
            if (result == 1) JOptionPane.showMessageDialog(null, "L'expressió no s'ha eliminat", "Expressió no eliminada", JOptionPane.INFORMATION_MESSAGE);
            else {
                JOptionPane.showMessageDialog(null, "L'expressió ha sigut eliminada amb exit", "Expressió eliminada", JOptionPane.INFORMATION_MESSAGE);
                String nomExp = labelTitol.getText();
                ivp.eliminarExpBool(nomExp);
                ivp.actualitzaLlistaExpBool();  
            }
        }
    }

    /**
     * Crida a Obrir el document.
     * 
     * @param event representa la accio que s'ha dut a terme
     */
    public void actionPerformed_buttonModificarDoc (ActionEvent event) {
        String autor = labelAutor.getText();
        String titol = labelTitol.getText();
        ivp.obrirDoc(autor, titol, typeComp);
    }

    /**
     * Obra el Chooser File per poder fer export del fitxer
     * 
     * @param event representa la accio que s'ha dut a terme
     */
    public void actionPerformed_buttonAbrirJChooseFile (ActionEvent event) {
        //String path
            JFileChooser jfDirectory = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfDirectory.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileNameExtensionFilter txtFilter = new FileNameExtensionFilter(".txt", "txt");
            FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter(".xml", "xml");
            FileNameExtensionFilter fpgFilter = new FileNameExtensionFilter(".fpg", "fpg");
            jfDirectory.addChoosableFileFilter(xmlFilter);
            jfDirectory.addChoosableFileFilter(txtFilter);
            jfDirectory.addChoosableFileFilter(fpgFilter);
            jfDirectory.setAcceptAllFileFilterUsed(false);
            int returnValue = jfDirectory.showSaveDialog(null);
    
            if (returnValue == JFileChooser.APPROVE_OPTION) {

                File selectedFile = jfDirectory.getSelectedFile();
                String tipus = jfDirectory.getFileFilter().getDescription();
                String path = selectedFile.getAbsolutePath();
                String nomFinal = path + tipus;
                String autor = labelAutor.getText();
                String titol = labelTitol.getText();
        
                try {
                    ivp.exportarDoc(nomFinal,titol,autor);
                } catch (NoExisteixException e) {
                    JOptionPane.showMessageDialog(null, "Error: No existeix cap document amb aquests titol i autor", "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (JaExisteixException e) {
                    JOptionPane.showMessageDialog(null, "Error: Ja existeix un fitxer amb aquest nom", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
      }

    /**
     * Posa els listeners de events que provenen del components.  
     * 
     */  
    private void asignar_listenersComponentes() {

        exportBtn.addActionListener
            (new ActionListener() {
                public void actionPerformed (ActionEvent event) {
                actionPerformed_buttonAbrirJChooseFile(event);
                }
            });

        modificarBtn.addActionListener
        (new ActionListener() {
            public void actionPerformed (ActionEvent event) {
            String texto = ((JButton) event.getSource()).getText();
            actionPerformed_buttonModificarDoc(event);
            }
        });

        deleteBtn.addActionListener
            (new ActionListener() {
                public void actionPerformed (ActionEvent event) {
                String texto = ((JButton) event.getSource()).getText();
                actionPerformed_buttonAbrirAvisDelete(event);
                }
            });
    }
    
    /**
     * Inicialitzar els components
     * 
     */
    private void inicializarComponentes() {
        asignar_listenersComponentes();
      }

}
