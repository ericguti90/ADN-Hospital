
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 *
 * @author Ferran
 */
public class CtrlPresResultado {
    
    //**************************************************************************
     /***************************************************************************
     * CASO DE USO: Tratamiento Resultados
     **************************************************************************/

   
    public static ArrayList<String> getResultado(int res){
        return CtrlResultado.getResultado(res);
    }
    
    public static String mostrarResultado(int res){
        return CtrlResultado.mostrarResultado(res);
    }
    

    public static ArrayList<Integer>getIdResultados (){
        return CtrlResultado.getIDtodos();
    }
    
    public static void eliminarResultado(int id) {
        CtrlResultado.eliminaResultado(id);
    }
    
    public static void guardarResultados(JFileChooser direc){
        CtrlResultado.guardaResultados(direc);
    }

    public static void actualizaResultado() {
        CtrlPresentation.actualizaResultados();
    }
}
