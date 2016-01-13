
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * 
 * @author ADN
 */
public class CtrlData {
    
    private static GestionFichero gf;
    private static boolean cargadoPat;
    private static boolean cargadoSin;
    /**
     * pre: el nombre de fichero nombreF existe en la direccion y es tipo un fichero correcto
     * @res carga los datos del fichero elegido y los pone el el conjunto correspondiente
     * @param nombreF
     * @param direccion
     */
    public static void inicializaCtrlData(){
        cargadoPat = false;
        cargadoSin = false;
    }
    
    public static void recuperaFicheroGeneral(JFileChooser direccion) {
        
        gf = new GestionFichero();
        String text = gf.cargarFicheroCom(direccion, 1);
        
        if ( text.equals("Patologias\n")) recuperaFicheroPatologias(direccion);
        else if (text.equals("Resultados\n")){
            if(cargadoPat) recuperaFicheroResultados(direccion);
            else {
                int opcio = JOptionPane.showConfirmDialog(null, "Para matener la consistencia de datos, se recomienda cargar el fichero de patologías antes de proceder con los resultados.\nDesea continuar cargando resultados?.");
                if(opcio == 0){
                    recuperaFicheroResultados(direccion);
                }
            }
        }else if ( text.equals("Sintomas\n")){
            if( cargadoPat ) recuperaFicheroSintomas(direccion);
            else {
                int opcio = JOptionPane.showConfirmDialog(null, "Para matener la consistencia de datos, se recomienda cargar el fichero de patologías antes de proceder con los síntomas.\nDesea continuar cargando síntomas?.");
                if(opcio == 0){
                    recuperaFicheroSintomas(direccion);
                }
            }
        }else if ( text.equals("Pacientes\n")){
            if( cargadoPat && cargadoSin ) recuperaFicheroPacientes(direccion);
            else {
                int opcio = JOptionPane.showConfirmDialog(null, "Para matener la consistencia de datos, se recomienda cargar los ficheros de patologías y síntomas antes de proceder con los pacientes.\nDesea continuar cargando pacientes?.");
                if(opcio == 0){
                    recuperaFicheroPacientes(direccion);
                }
            }
        }
    }

    /**
     * pre: el fichero que senala direccion es del tipo pacientes y es correcto
     * @res carga los datos del fichero y los pone en el conjunto
     * @param direccion
     */
    public static void recuperaFicheroPacientes(JFileChooser direccion) {

        ArrayList<String> listaAdn  = new ArrayList();
        ArrayList<String> patR      = new ArrayList();
        ArrayList<String> patP      = new ArrayList();
        ArrayList<String> listaSint = new ArrayList();
        ArrayList<Integer> listaA   = new ArrayList();
        String linea, nombre, apellido1, apellido2, text;
        int nHist, nacimiento, cont;
        char sexo;
        CjtPaciente cjpac = new CjtPaciente();
        
        CtrlPaciente.setCjtPaciente(cjpac);
        gf = new GestionFichero();
        text = gf.cargarFicheroCom(direccion, 100);                              
        StringTokenizer tokens = new StringTokenizer(text, "\n");
        
        if(tokens.hasMoreTokens()){
            linea = tokens.nextToken(); 
            if(linea.equals("Pacientes")){
                while (text.equals("") == false){
                    while(tokens.hasMoreTokens()){
                        linea = tokens.nextToken();
                        StringTokenizer lineas = new StringTokenizer(linea, ";");
                        if(lineas.hasMoreTokens()){
                            //Datos básicos
                            nHist = Integer.parseInt(lineas.nextToken());
                            nombre = lineas.nextToken();
                            apellido1 = lineas.nextToken();
                            apellido2 = lineas.nextToken();
                            sexo = lineas.nextToken().charAt(0);
                            nacimiento = Integer.parseInt(lineas.nextToken());
                            //Sequencias de ADN
                            cont = Integer.parseInt(lineas.nextToken());
                            for(int i = 0; i < cont; i++) listaAdn.add(lineas.nextToken());
                            //Años de las sequencias
                            cont = Integer.parseInt(lineas.nextToken());
                            for(int i = 0; i < cont; i++) listaA.add(Integer.parseInt(lineas.nextToken()));
                            //Patologias R
                            cont = Integer.parseInt(lineas.nextToken());
                            for(int i = 0; i < cont; i++) patR.add(lineas.nextToken());
                            //Patologias P
                            cont = Integer.parseInt(lineas.nextToken());
                            for(int i = 0; i < cont; i++) patP.add(lineas.nextToken());
                            //Sintomas
                            cont = Integer.parseInt(lineas.nextToken());
                            for(int i = 0; i < cont; i++) listaSint.add(lineas.nextToken());
                            
                            CtrlPaciente.introducirPaciente(nHist, nombre, apellido1, apellido2, sexo, nacimiento, listaAdn, listaA, patR, patP, listaSint);
                            
                            listaA.clear();
                            listaSint.clear();
                            patR.clear();
                            patP.clear();
                            listaAdn.clear();
                        }
                    }
                    text = gf.cargarFicheroCom(direccion, 100);
                    tokens = new StringTokenizer(text, "\n");
                }
            }
        }
    }
    
    /**
     * pre: el fichero que senala direccion es del tipo patologias y es correcto
     * @res carga los
     * çdatos del fichero y los pone en el conjunto
     * @param direccion
     */
    public static void recuperaFicheroPatologias(JFileChooser direccion) {

        String linea, nombre, descripcion, text, ristraG;
        ArrayList<String> aux = new ArrayList();
        CjtPatologia cjpat = new CjtPatologia();
        
        CtrlPatologia.setCjtPatologia(cjpat);
        cargadoPat = true;
        gf = new GestionFichero();
        text = gf.cargarFicheroCom(direccion, 100);    
        StringTokenizer tokens = new StringTokenizer(text, "\n");
        
        if(tokens.hasMoreTokens()){
            linea = tokens.nextToken();
            if(linea.equals("Patologias")){
                while (text.equals("") == false){
                    while(tokens.hasMoreTokens()){
                        linea = tokens.nextToken();
                        StringTokenizer lineas = new StringTokenizer(linea, ";");
                        if(lineas.hasMoreTokens()){
                            nombre = lineas.nextToken();
                            if(nombre.equals("Rel:")) CtrlPatologia.insertaRelacion(lineas.nextToken(), lineas.nextToken());
                            else{
                                aux.clear();
                                descripcion = lineas.nextToken();
                                CtrlPatologia.introducirPatologia(nombre, descripcion, aux, aux);
                                if(lineas.hasMoreTokens()){
                                    ristraG = lineas.nextToken();
                                    CtrlPatologia.setRistraGeneralizada(nombre, ristraG);
                                }
                            }
                        }  
                    }
                    text = gf.cargarFicheroCom(direccion, 100);
                    tokens = new StringTokenizer(text, "\n");
                }
            }
        }
    }

    /**
     * pre: el fichero que senala direccion es del tipo sintomas y es correcto
     * @res carga los datos del fichero y los pone en el conjunto
     * @param direccion
     */
    public static void recuperaFicheroSintomas(JFileChooser direccion) {
        
        String linea, aux, nombre, descripcion, text;
        ArrayList<String> p = new ArrayList();
        int numP;
        
        cargadoSin = true;
        text = gf.cargarFicheroCom(direccion, 100);  
        StringTokenizer tokens = new StringTokenizer(text, "\n");
        
        while (text.equals("") == false){
            while(tokens.hasMoreTokens()){
                linea = tokens.nextToken();
                StringTokenizer lineas = new StringTokenizer(linea, ";");
                if(lineas.hasMoreTokens()){
                    nombre = lineas.nextToken();
                    descripcion = lineas.nextToken();
                    numP = Integer.parseInt(lineas.nextToken());
                    p.clear();
                    for(int i = 0; i < numP; i++){
                        aux = lineas.nextToken();
                        if(aux != null) p.add(aux);
                    }
                    CtrlSintoma.introducirSintoma(nombre, descripcion, p);
                    CtrlPatologia.actualizar_patologiaI(nombre, p);
                }
            }
            text = gf.cargarFicheroCom(direccion, 100);
            tokens = new StringTokenizer(text, "\n");
        }
    }

    /**
     * pre: el fichero que senala direccion es del tipo resultados y es correcto
     * @res carga los datos del fichero y los pone en el conjunto
     * @param direccion
     */
    public static void recuperaFicheroResultados(JFileChooser direccion) {
        
        String text, linea, paramRes, paramPatron, paramCamino, seqPatron, paramVector, nomCamino, parametreCamino;
        int id, edadI, edadF, year, minAp, mida, errorCamino;
        double error;
        char sexo;
        String def;
        ArrayList<String> patos = new ArrayList();
        ArrayList<String> secs = new ArrayList();  
        ArrayList<String> patrones = new ArrayList();
        ArrayList<Integer> vectorValores;
        ArrayList<ArrayList<Integer>> distancias = new ArrayList();
        
        gf = new GestionFichero();
        text = gf.cargarFicheroCom(direccion, 100);                              
        StringTokenizer tokens = new StringTokenizer(text, "\n");
        
        if(tokens.hasMoreTokens()){                                             //Comprovem que sigui el fitxer correcte
            if(tokens.nextToken().equals("Resultados")){
                while (text.equals("") == false){
                    while(tokens.hasMoreTokens()){
                        linea = tokens.nextToken();                             // Agafem un resultado
                        StringTokenizer lineas = new StringTokenizer(linea, "$"); // Primera separacio
                        if(lineas.hasMoreTokens()){
                            paramRes = lineas.nextToken();
                            StringTokenizer sepParamRes = new StringTokenizer(paramRes, ";");
                            if(sepParamRes.hasMoreTokens()){
                                // Parametres resultado
                                id = Integer.parseInt(sepParamRes.nextToken());
                                year = Integer.parseInt(sepParamRes.nextToken());
                                edadI = Integer.parseInt(sepParamRes.nextToken());
                                edadF = Integer.parseInt(sepParamRes.nextToken());
                                sexo = sepParamRes.nextToken().charAt(0);
                                minAp = Integer.parseInt(sepParamRes.nextToken());
                                mida = Integer.parseInt(sepParamRes.nextToken());
                                error = Double.parseDouble(sepParamRes.nextToken()); 
                                def = sepParamRes.nextToken();
                                int numPatos = Integer.parseInt(sepParamRes.nextToken());
                                for(int i = 0; i < numPatos; i++) patos.add(sepParamRes.nextToken());
                                int numSecs = Integer.parseInt(sepParamRes.nextToken());
                                for(int i = 0; i < numSecs; i++) secs.add(sepParamRes.nextToken());
                                // Creamos resultado
                                CtrlResultado.cargarResultado(id, year, edadI, edadF, sexo, minAp, mida, error, def, patos, secs);
                                // Patrones
                                while(lineas.hasMoreTokens()){
                                    paramRes = lineas.nextToken();
                                    StringTokenizer paramResToken = new StringTokenizer(paramRes, "@");
                                    if(paramResToken.hasMoreTokens()){
                                        paramPatron = paramResToken.nextToken();
                                        StringTokenizer sepParamPatron = new StringTokenizer(paramPatron, ";");
                                        // Parametres patron
                                        seqPatron = sepParamPatron.nextToken();
                                        ArrayList<Boolean> patBool = new ArrayList();
                                        while(sepParamPatron.hasMoreTokens()) {
                                            if(sepParamPatron.nextToken().equals("true")) patBool.add(true);
                                            else patBool.add(false);
                                        }
                                        // Anadimos patron
                                        CtrlResultado.anadirPatron(seqPatron, patBool);
                                        // Camins
                                        while(paramResToken.hasMoreTokens()){
                                            paramPatron = paramResToken.nextToken();
                                            StringTokenizer paramPatronToken = new StringTokenizer(paramPatron, "|");
                                            if(paramPatronToken.hasMoreTokens()){
                                                paramCamino = paramPatronToken.nextToken();
                                                StringTokenizer sepParamCamino = new StringTokenizer(paramCamino, ";");
                                                // Parametres cami
                                                nomCamino = sepParamCamino.nextToken();
                                                errorCamino = Integer.parseInt(sepParamCamino.nextToken());
                                                // vectors
                                                while(paramPatronToken.hasMoreTokens()){
                                                    paramCamino = paramPatronToken.nextToken();
                                                    sepParamCamino = new StringTokenizer(paramCamino, ";");
                                                    // Tractar camino
                                                    vectorValores = new ArrayList();
                                                    while(sepParamCamino.hasMoreTokens()){
                                                        String tmp = sepParamCamino.nextToken();
                                                        if(tmp.equals("?")) vectorValores = null;
                                                        else vectorValores.add(Integer.parseInt(tmp));
                                                    }
                                                    distancias.add(vectorValores);
                                                }
                                                CtrlResultado.anadirCamino(nomCamino, errorCamino, distancias);
                                                distancias.clear();
                                            }
                                        }
                                    }
                                }
                                CtrlPatologia.actualizarPatologiaResI(id);
                            }
                        }
                    }
                    text = gf.cargarFicheroCom(direccion, 100);
                    tokens = new StringTokenizer(text, "\n");
                }
            }
        }
    }
    // _____________________________________________________________
    //|                                                             |
    //|               OPERACIONES PARA GUARDAR DATOS                |
    //|_____________________________________________________________|
    
    //_______________ABRIR FICHERO________________
    public static void abrirFichero(JFileChooser direc) {
        gf = new GestionFichero();
        gf.guardaFicheroNuevo(direc);
    }
    //_______________ESCRIVIR EN FICHERO________________
    public static void guardarFichero(String text) {
        gf.guardaFicheroAnade(text);
    }
    //_______________CERRAR FICHERO________________
    public static void cierraFichero() {
        gf.guardaFicheroCierra();
    }
}
