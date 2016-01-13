
import javax.swing.UnsupportedLookAndFeelException;

//import presentation.ExeDriversUI;
/**
 *
 * @author ADN
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        
        //Creamos los conjuntos
        CjtPaciente cjpac   = new CjtPaciente();
        CjtPatologia cjpat  = new CjtPatologia();
        CjtSintomas cjs     = new CjtSintomas();
        CjtResultados cjr   = new CjtResultados();
        
        //Los asignamos a cada controlador
        CtrlPaciente.setCjtPaciente (cjpac);
        CtrlPatologia.setCjtPatologia(cjpat);
        CtrlSintoma.setCjtSintomas(cjs);
        CtrlResultado.setCjtResultados(cjr);
        
        //Iniciamos el programa
        CtrlData.inicializaCtrlData();
        CtrlPresentation.inicializaInterficie();
    }
}
