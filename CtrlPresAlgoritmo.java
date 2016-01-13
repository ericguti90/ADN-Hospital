import java.util.ArrayList;
import javax.swing.JSplitPane;

/**
 *
 * @author ADN
 */
public class CtrlPresAlgoritmo {
    
    private static ExperimentoPanel pExp = CtrlPresentation.getM().getExpPan();
    
    public static ArrayList<String> getNombrePatologias() {
        return CtrlPatologia.getNombres();
    }

    public static ArrayList<Integer> getNombrePacientes() {
        return CtrlPaciente.getNHtodosOrd();
    }
    public static String getAdnPaciente (int numH){
        return CtrlPaciente.get1Adn(numH);
    }
    
    public static void experimentoEstandard( ArrayList<String> ls, int m, double e, int q, int eI, int eF, char sexo, String p ) {
        
        CtrlAlgoritmo.experimentoEstandard( ls, m, e, q, eI, eF, sexo, p );
    }
    public static void mostrarResultadoExperimentoEstandard( String t ) {
        
        JSplitPane jspH  = pExp.getEstSplitPaneHort();  jspH.setDividerLocation(15);
        JSplitPane jspV  = pExp.getEstSplitPaneVer();  jspV.setDividerLocation(20);

        pExp.mostrarResultadoExperimentoEstandard( cabecera()+t );
    }
    public static void experimentoGeneralizada( ArrayList<String> ls, int m, double e, int q, String p ) {
        
        CtrlAlgoritmo.experimentoGeneralizada( ls, m, e, q, p );
    }
    public static void mostrarResultadoExperimentoGeneralizada( String t ) {
        
        JSplitPane jsp  = pExp.getGenSplitPane();  jsp.setDividerLocation(15);
       
        pExp.mostrarResultadoExperimentoGeneralizada( cabecera()+t );
    }
    public static void experimentoPotencial( int nh, double e){
        
        CtrlAlgoritmo.experimentoPotencial( nh, e );
    }
    public static void mostrarResultadoExperimentoPotencial(  String t  ){

        
        pExp.mostrarResultadoExperimentoPotencial( cabecera()+t );
    }
    
    
    public static void experimentoPatronIndependiente( int nh, double e, String pi){
        
        CtrlAlgoritmo.experimentoPatronPersonalizado( nh, e, pi );
    }
    public static void mostrarResultadoExperimentoPatronPersonalizado(  String t  ){

        
        pExp.mostrarResultadoExperimentoPatronPersonalizado( cabecera()+t );
    }
    
    
    
    private static String cabecera(){
        
        String c = "";
        c += "<html><head><style type=\"text/css\">" + estilo() + "</style></head>";
        return c;
    }
    private static String estilo(){
        
        String c = "";
        c += 
          "body{ margin: 0; padding: 0; background-color: white;/*#F7F7F7;*//*#A6CEEA;*/ font-family: \"Palatino Linotype\", Verdana, Arial; /*\"Baskerville Old Face\"*/ font-size: 10px; }" 
        + "table{ margin: 0; padding: 0; width: 100%; background-color: #F7F7F7;/*#A6CEEA;*/ font-size: 10px; }" 
        + ".tituloT{ margin: 0; padding: 2px 0 5px 0; background-color: /*#F7F7F7;*/#A6CEEA; text-align:center;font-size: 11px; }"
        + ".tituloT2{ margin: 0; padding: 2px 0 5px 0; background-color: /*#F7F7F7;*/#A6CEEA; text-align:center;font-size: 11px; }" 
        + ".thInfo{ margin: 0; padding: 0px 10px 0px 0; text-align:right;}"
        + ".thPat{ padding: 2px 0 10px 5px; height:10px; color:white; background-color: #2B547E/*#F7F7F7;*/ /*#B0E0E6*/; text-align:left;}" 
        + ".tdNormal{ padding: 2px 0 0px 0; text-align:left; font:bold 9px \"Palatino Linotype\", Verdana, Arial;  }" 
        + ".tdNormalFlojo{ height: 5px; border-spacing:0; padding: 0; text-align:left; font:bold 9px \"Palatino Linotype\", Verdana, Arial; color: #808080;  }" 
        + ".tdNormalFlojo span{ margin: 0; padding: 0;}"
        + ".tdNormalFlojo .spanCont{ color: black; margin: 0; padding: 0;}"
        + "#A{color:#DCDCDC; background-color: #0000CD; display:block;} " 
        + "#C{color:#DCDCDC; background-color: #FF0000; display:block;} " 
        + "#G{color:black; background-color: #32CD32; display:block;}"  
        + "#T{color:black; background-color: #FFD700; display:block;}";
        
        return c;
    }
    

    public static boolean patologiasVacio() {
        if (CtrlPatologia.existenPatologias() == true) return false;
        else return true;
    }

    public static int getCantidadPacientes() {
        return CtrlPaciente.getNumPacientes();
    }

    public static void guardaResultado() {
        CtrlResultado.insertaTemporal();
    }

    public static void actualizaResultados() {
        CtrlPresentation.actualizaResultados();
    }
}
