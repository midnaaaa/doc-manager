import presentacio.CtrlPresentacio;

public class MainFrame {
    public static void main (String[] args) {   
        javax.swing.SwingUtilities.invokeLater(
            new Runnable() {
                /**
                 * Arrenca el programag
                 */
                public void run() {
                    CtrlPresentacio ctrlPresentacio = new CtrlPresentacio();
                    ctrlPresentacio.inicialitzarPresentacio();
                }
            }
        );
    }
}


 
  


