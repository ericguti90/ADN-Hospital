
import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;
//import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 *
 * @author ADN
 */
public class CtrlPresentation {
    
    private static MainInterficie m;
    
    public static void inicializaInterficie() throws UnsupportedLookAndFeelException{
        
        UIManager.setLookAndFeel( new NimbusLookAndFeel() );
        m = new MainInterficie();
        m.setLocationRelativeTo(null);
        m.setVisible(true);
    }
    
    public static void nuevoEntorno(){
        CtrlPaciente.setCjtPaciente(null);
        CtrlSintoma.setCjtSintomas(null);
        CtrlPatologia.setCjtPatologia(null);
        CtrlResultado.setCjtResultados(null);
    }

    public static void recuperaFicheroGeneral(JFileChooser direc){
        CtrlData.recuperaFicheroGeneral(direc);
    }
    
    public static void muestraTabPatologia(){
	m.muestraTabPatologia();
    }

    public static void muestraTabSintoma(){
        m.muestraTabSintoma();
    }

    public static void actualizaPatologias(){
        m.actualizaPatologias();
    }

    public static void actualizaSintomas() {
        m.actualizaSintomas();
    }

    public static void actualizaPacientes() {
        m.actualizaPacientes();
    }

    static void actualizaResultados() {
        m.actualizaResultados();
    }
    
    public static MainInterficie getM() {
        return m;
    }

    
}
