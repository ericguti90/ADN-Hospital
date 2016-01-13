
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 *
 * @author ADN
 */
public class CtrlPresSintoma {
    
    public static boolean existeSintoma(String s){
        return CtrlSintoma.existe_sintoma(s);
    }

    public static ArrayList<String> getSintoma(String sin){
        return CtrlSintoma.getSintoma(sin);
    }
   
   
    public static void nuevoSintoma(ArrayList<String> sinL){
        if (!CtrlSintoma.existe_sintoma(sinL.get(0))){
            ArrayList<String> pato = new ArrayList();
            for(int i = 2; i < sinL.size();++i){
                pato.add(sinL.get(i));
            }
            if(CtrlSintoma.introducirSintoma(sinL.get(0), sinL.get(1), pato)) {
            CtrlPatologia.actualizar_patologiaI(sinL.get(0), pato);
            }
        }
        else modificarSintoma(sinL);
    }
    
     public static void modificarSintoma(ArrayList<String> sinL){
        ArrayList<String> patA = CtrlPatologia.getPatologiasNombres(CtrlSintoma.getPatologiaR(sinL.get(0)));
        ArrayList<String> pato = new ArrayList();
        if(sinL.size()>2){
            for (int i = 2; i<sinL.size(); ++i) pato.add(sinL.get(i));
        }
        CtrlSintoma.modificarSintoma(sinL.get(0),sinL.get(1),pato);
            int cont = pato.size();
            int index = 0;
            for (int i = 0; i < cont; ++i) {
                if (patA.remove(pato.get(index))) {
                    pato.remove(index);
                    --cont;
                }
                else ++index;
            }
            CtrlPatologia.actualizar_patologiaI(sinL.get(0),pato);
            CtrlPatologia.actualizar_patologiaE(sinL.get(0), patA);
    }

    public static void eliminarSintoma(String s){
        ArrayList<String> pataux = CtrlPatologia.getPatologiasNombres(CtrlSintoma.getPatologiaR(s));
        if(!pataux.isEmpty()) CtrlPatologia.actualizar_patologiaE(s, pataux);
        CtrlPaciente.actualizaSint(s);
        CtrlSintoma.eliminarSintoma(s);
    }
    
    public static void guardarSintomas(JFileChooser direc){
        CtrlSintoma.guardarSintomas(direc);
    }

    public static ArrayList<String> getNombreSintomas() {
        return CtrlSintoma.getNombreSintomas();
    }

    public static ArrayList<String> getNombrePatologias() {
        return CtrlPatologia.getNombres();
    }

    public static void actualizaSintomas() {
        CtrlPresentation.actualizaSintomas();
    }

    public static void muestraTabPatologia() {
        CtrlPresentation.muestraTabPatologia();
    }
}
