
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 *
 * @author Ferran
 */
public class CtrlPresPatologia {

    public static boolean existePatologia(String nombre){
        return CtrlPatologia.existe_patologia(nombre);
    }

    public static ArrayList<String> getPatologia(String pat){
        return CtrlPatologia.getPatologia(pat);
    }
    
    public static void nuevaPatologia(ArrayList<String> patL) {
        if (!CtrlPatologia.existe_patologia(patL.get(0))){
            ArrayList<String> pato = new ArrayList();
            int aux = Integer.parseInt(patL.get(2));
            for (int i = 0; i < aux; ++i) pato.add(patL.get(3+i));
            ArrayList<String> sin = new ArrayList();
            int aux1 = Integer.parseInt(patL.get(aux+3));
            for (int i = 0; i < aux1;++i) sin.add(patL.get(aux+4+i));
            if(CtrlPatologia.introducirPatologia(patL.get(0),patL.get(1),pato,sin)) {
                CtrlSintoma.actualizar_sintomaI(patL.get(0),sin);
                CtrlPatologia.actualizar_patologiaPI(patL.get(0),pato);
            }
        }
        else modificarPatologia(patL);
    }
    
    public static void modificarPatologia(ArrayList<String> patL) {
        ArrayList<String> sinA = CtrlSintoma.getSintoma(CtrlPatologia.getSintoma(patL.get(0)));
        ArrayList<String> patA = CtrlPatologia.getPatologiasNombres(CtrlPatologia.getPatologiaR(patL.get(0)));
        ArrayList<String> pato = new ArrayList();
        int aux = Integer.parseInt(patL.get(2));
        for (int i = 0; i < aux; ++i) pato.add(patL.get(3+i));
        ArrayList<String> sin = new ArrayList();
        int aux1 = Integer.parseInt(patL.get(aux+3));
        for (int i = 0; i < aux1;++i) sin.add(patL.get(aux+4+i));
        CtrlPatologia.modificarPatologia(patL.get(0),patL.get(1),pato,sin);
        int cont = sin.size();
        int contA = sinA.size();
        int index = 0;
        for (int i = 0; i < cont; ++i) {
            if (sinA.remove(sin.get(index))) {
                sin.remove(index);
                --cont;
            }
            else ++index;
        }
        CtrlSintoma.actualizar_sintomaI(patL.get(0),sin);
        CtrlSintoma.actualizar_sintomaE(patL.get(0),sinA);
        cont = pato.size();
        contA = patA.size();
        index = 0;
        for (int i = 0; i < cont; ++i) {
            if (patA.remove(pato.get(index))) {
                pato.remove(index);
                --cont;
            }
            else ++index;
        }
        CtrlPatologia.actualizar_patologiaPI(patL.get(0),pato);
        CtrlPatologia.actualizar_patologiaPE(patL.get(0),patA);
    }
    
    public static void eliminarPatologia(String p){
        ArrayList<String> sinaux = CtrlSintoma.getSintoma(CtrlPatologia.getSintoma(p));
        ArrayList<String> pataux = CtrlPatologia.getPatologiasNombres(CtrlPatologia.getPatologiaR(p));
        ArrayList<Integer> resaux = CtrlResultado.getID(CtrlPatologia.getResultados(p));
        for(int i = 0; i < resaux.size();++i){
            CtrlResultado.eliminaResultado(resaux.get(i));
        }
        if(!sinaux.isEmpty()) CtrlSintoma.actualizar_sintomaE(p, sinaux);
        if(!pataux.isEmpty()) CtrlPatologia.actualizar_patologiaPE(p, pataux);
        CtrlPaciente.actualizaPato(p);
        CtrlPatologia.eliminarPatologia(p);
    }
    
    public static ArrayList<String> getNombreSintomas(){
        return CtrlSintoma.getNombreSintomas();
    }
    
   
    public static ArrayList<String> getNombrePatologias(){
        return CtrlPatologia.getNombres();
    }
    
    public static void guardarPatologias(JFileChooser direc){
        CtrlPatologia.guardaPatologias(direc);
    }

    public static void actualizaPatologias() {
        CtrlPresentation.actualizaPatologias();
    }

    public static void muestraTabSintoma() {
        CtrlPresentation.muestraTabSintoma();
    }

}

