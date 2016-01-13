
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 *
 * @author Ferran
 */
public class CtrlPresPaciente {
    
    public static boolean existePaciente(String s){
        return CtrlPaciente.existePaciente(Integer.parseInt(s));
    }

    public static ArrayList<String> getPaciente(int nh){
        return CtrlPaciente.getPaciente(nh);
    }


    public static void nuevoPaciente(ArrayList<String> datos){
        String aux;
        int nh= Integer.parseInt(datos.get(0));
        String nombre= datos.get(1);
        String ap1= datos.get(2);
        String ap2= datos.get(3);
        aux= datos.get(4);
        char sc= aux.charAt(0);
        aux= datos.get(5);
        int nac= Integer.parseInt(aux);

        int num= Integer.parseInt(datos.get(6)); //numero de secuencias de adn
        ArrayList<String> adn = new ArrayList();
        for (int i=0; i<num; ++i)adn.add(datos.get(7+i));

        int num1 = Integer.parseInt(datos.get(7+num)); //numero de edades de secuencias de adn
        ArrayList<Integer> edadAdn = new ArrayList();
        for (int i=0; i<num1; ++i) edadAdn.add(Integer.parseInt(datos.get(8+i+num)));

        int num2 = Integer.parseInt(datos.get(8+num+num1)); //numero de patologias diagnosticadas
        ArrayList<String> patR = new ArrayList();
        ArrayList<String> patP = new ArrayList();
        for (int i = 0; i < num2; ++i) patR.add (datos.get(9+num+num1+i));

        int num3 = Integer.parseInt(datos.get(9+num+num1+num2)); //numero de sintomas
        ArrayList<String> sintoma = new ArrayList();
        for(int i = 0; i < num3; ++i) sintoma.add(datos.get(10+num+num1+num2+i));

        if (!CtrlPaciente.existePaciente(nh)){
            CtrlPaciente.introducirPaciente( nh, nombre, ap1, ap2, sc, nac, adn, edadAdn , patR, patP, sintoma);
        }
        else{
            CtrlPaciente.modificarPaciente( nh, nombre, ap1, ap2, sc, nac, adn, edadAdn , patR, patP, sintoma);
        }
    }

    public static ArrayList<Integer> getNHtodosO(){
        return CtrlPaciente.getNHtodosOrd();
    }

    public static ArrayList<Integer> getNHtodosD(){
        return CtrlPaciente.getNHtodosDes();
    }

    public static void eliminaPaciente( int nh ){
        CtrlPaciente.eliminarPaciente(nh);
    }
    
    public static void guardarPacientes(JFileChooser direc){
        CtrlPaciente.guardarPacientes(direc);
    }

    public static ArrayList<String> getNombrePatologias() {
        return CtrlPatologia.getNombres();
    }

    public static ArrayList<String> getNombreSintomas() {
        return CtrlSintoma.getNombreSintomas();
    }

    public static void actualizaPacientes() {
        CtrlPresentation.actualizaPacientes();
    }

    public static void muestraTabSintoma() {
        CtrlPresentation.muestraTabSintoma();
    }

    public static void muestraTabPatologia() {
        CtrlPresentation.muestraTabPatologia();
    }
}
