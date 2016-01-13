
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JFileChooser;

/**
 *
 * @author Merce Pedraza
 */
public class CtrlPaciente {
    
    private static CjtPaciente cjpac;
    
    
    /**
     * Pre:
     * @see Post: Se asigna un conjunto de pacientes estatico al controlador
     * @param cjpaciente
     */
    public static void setCjtPaciente(CjtPaciente cjpaciente){
        if (cjpaciente == null) cjpac = new CjtPaciente();
        else cjpac = cjpaciente;
    }

    /**
     * Pre:
     * @param nh
     * @return Devuelve true si el paciente con nh esta en el conjunto
     */
    public static boolean existePaciente(int nh){
        return cjpac.ExistePaciente(nh);
    }
    
    
    private static boolean esVacio(String s){
        return s.trim().isEmpty();
    }
    
    
    
    private static boolean sexoVal(char sx){
        return (sx == 'm' || sx == 'f');
    }
    
    
    /**
     * Pre:
     * @param nHistorialP
     * @param nombreP
     * @param apellido1P
     * @param apellido2P
     * @param sexoP
     * @param nacimientoP
     * @param adnP
     * @param edadAdnP
     * @param patologiasRP
     * @param patologiasPP
     * @param sintomaP
     * @return Introduce el paciente con los atributos pasados por parametro devuelve true si el paciente ya estaba en el conjunto
     */
    public static boolean introducirPaciente(int nHistorialP, String nombreP, String apellido1P, String apellido2P, char sexoP, int nacimientoP, ArrayList<String> adnP, ArrayList<Integer> edadAdnP, ArrayList<String> patologiasRP, ArrayList<String> patologiasPP, ArrayList<String> sintomaP){
        
        Boolean existe = cjpac.ExistePaciente(nHistorialP);
        
        if (!existe){
            HashSet<String> hs = new HashSet<String>();
            hs.addAll(patologiasRP);
            patologiasRP.clear();
            patologiasRP.addAll(hs);
            ArrayList<Patologia> patR;
            patR = CtrlPatologia.getPatologias(patologiasRP); 
            
            hs.clear();
            hs.addAll(patologiasPP);
            patologiasPP.clear();
            patologiasPP.addAll(hs);
            ArrayList<Patologia> patP;
            patP = CtrlPatologia.getPatologias(patologiasPP);   

            hs.clear();
            hs.addAll(sintomaP);
            sintomaP.clear();
            sintomaP.addAll(hs);
            ArrayList<Sintoma> sin;
            sin = CtrlSintoma.getSintomaConjunto(sintomaP);  
            
            boolean valido = nHistorialP>0 && !esVacio(nombreP) && !esVacio(apellido1P) && !esVacio(apellido2P) && sexoVal(sexoP) && nacimientoP>=1900 && nacimientoP<=2012;
            if (valido ){
                Paciente p = new Paciente(nHistorialP, nombreP, apellido1P, apellido2P, sexoP, nacimientoP, adnP, edadAdnP , patR, patP, sin);
                cjpac.insertaPac(p); 
            }
        }
        return existe;
    }
    
    
    
    /**
     * Pre:
     * @param nh
     * @return Devuelve los datos del paciente con nh pasado por parametro, si no existe los devuelve con valores de inicializacion
     */
    public static ArrayList<String> getPaciente(int nh){
        Paciente pac = cjpac.getPaciente(nh);
        ArrayList<String> s = new ArrayList();
        if(pac.getnHistorial() != -1) {
            s.add(pac.getNombre());
            s.add(pac.getApellido1());
            s.add(pac.getApellido2());
            s.add(String.valueOf(pac.getSexo()));
            s.add(Integer.toString(pac.getNacimiento()));

            ArrayList<String> adns = pac.getAdn();
            s.add(String.valueOf(adns.size()));
            if(!adns.isEmpty()) s.addAll(adns);
            
            ArrayList<Integer> edadesInt = pac.getEdadAdn();
            ArrayList<String> edades = new ArrayList();
            for(int i=0; i < edadesInt.size(); ++i) edades.add(Integer.toString(edadesInt.get(i)));
            s.add(String.valueOf(edades.size()));
            if(!edades.isEmpty()) s.addAll(edades);  
        
            ArrayList<String> patoR = CtrlPatologia.getPatologiasNombres(pac.getPatologiasR());
            s.add(String.valueOf(patoR.size()));
            if(!patoR.isEmpty()) s.addAll(patoR);

            ArrayList<String> patoP = CtrlPatologia.getPatologiasNombres(pac.getPatologiasP());
            s.add(String.valueOf(patoP.size()));
            if(!patoP.isEmpty()) s.addAll(patoP);
                        
            ArrayList<String> sint = CtrlSintoma.getSintoma(pac.getSintomas());
            s.add(String.valueOf(sint.size()));
            if(!sint.isEmpty())s.addAll(sint);
           
        }
        return s;
    }
    
    
        
        
    /**
     * Pre: nHistorialP, nombreP, apellido1P, apellido2P, sexoP y nacimiento no pueden ser vacios, sexoP tiene que ser m o f, nacimiento tiene que estar entre 1900 y el año actual, el tamaño de adnP y edadAdnP debe ser igual
     * @param nHistorialP
     * @param nombreP
     * @param apellido1P
     * @param apellido2P
     * @param sexoP
     * @param nacimientoP
     * @param adnP
     * @param edadAdnP
     * @param patologiasRP
     * @param patologiasPP
     * @param sintomaP
     * @return Se modifica el paciente con nHistorial con los parametros pasados
     */
    public static boolean modificarPaciente( int nHistorialP, String nombreP, String apellido1P, String apellido2P, char sexoP, int nacimientoP, ArrayList<String> adnP, ArrayList<Integer> edadAdnP, ArrayList<String> patologiasRP, ArrayList<String> patologiasPP, ArrayList<String> sintomaP ){
        Paciente pac = cjpac.getPaciente(nHistorialP);
        pac.setNombre(nombreP);
        pac.setApellido1(apellido1P);
        pac.setApellido2(apellido2P);
        pac.setSexo(sexoP);
        pac.setNacimiento(nacimientoP);
        pac.setAdn(adnP);
        if (edadAdnP.size() < 1) edadAdnP.add(0, -1);
        pac.setEdadAdn(edadAdnP);
        
        HashSet<String> hs = new HashSet<String>();
        hs.addAll(patologiasRP);
        patologiasRP.clear();
        patologiasRP.addAll(hs);
        pac.setPatologiasR(CtrlPatologia.getPatologias(patologiasRP));
        
        hs.clear();
        hs.addAll(patologiasPP);
        patologiasPP.clear();
        patologiasPP.addAll(hs);
        pac.setPatologiasP(CtrlPatologia.getPatologias(patologiasPP));  
        
        hs.clear();
        hs.addAll(sintomaP);
        sintomaP.clear();
        sintomaP.addAll(hs);        
        pac.setSintomas(CtrlSintoma.getSintomaConjunto(sintomaP));
        
        return cjpac.modificaPac(pac); 
    }
    
    /**
     * Pre:
     * @param nh
     * @return Se elimina el paciente con nh si existe, retorna true si el paciente existia antes de ser eliminado
     */
    public static boolean eliminarPaciente( int nh ){
        if(!cjpac.ExistePaciente(nh)) return false;
        cjpac.eliminaPac(nh);
        return true;       
    }
    
    /*
    public static void patologiasPotenciales(Integer nh){   //devuelve los nombres de las patologias
        Paciente p = cjpac.getPaciente(nh);      
        ArrayList<String> poten = new ArrayList();
        if( p.getnHistorial() != -1 ) poten = CtrlPatologia.seleccionarSimilares(p.getAdn(0));      
        CtrlPresentation.muestraPotenciales( poten );
    }
    
*/
    
    /**
     * Pre:
     * @param pat
     * @return Se retorna un listado de ristras de los pacientes con patologia pat
     */
    public static ArrayList<String> getListaNhPacientes( String pat ){     
        return cjpac.seleccionaPac( 'x', -1, -1, pat );
    }
    
    
    /**
     * Pre: sexo debe ser m,f o x, edadI y edadF deben estar entre -1 y 120
     * @param sexo
     * @param edadI
     * @param edadF
     * @param pat
     * @return Se retorna un listado de ristras de los pacientes seleccionados por criterios de edad, patologia y sexo
     */
    public static ArrayList<String> seleccionaPac( char sexo, int edadI, int edadF, String pat ){       

        return cjpac.seleccionaPac( sexo, edadI, edadF, pat );
    }


    
    /**
     * Pre:
     * @param nhp
     * @return Devuelce las ristras de todos los pacientes de los que se pasa el nh, si existe, sino la estructura vacia
     */
    public static ArrayList<String> seleccionPorNH( ArrayList<String> nhp ){       
        
        ArrayList<String> adns = new ArrayList();
        for (int i= 0; i<nhp.size(); ++i){
            adns.add(cjpac.getPaciente(i).getAdn(0));            
        }
        return adns;
    }

    /**
     * Pre:
     * @return Retorna el numero de historial de todos los pacientes del conjunto
     */
    public static ArrayList<Integer> getNHtodosOrd(){
        return cjpac.getNHtodosOrd();
    }
    
    public static ArrayList<Integer> getNHtodosDes(){
        return cjpac.getNHtodosDes();
    }
    
    /**
     * Pre:
     * @return Retorna un string con todos los pacientes del conjunto siguiendo el esquema: nHist;nombre;apellido1;apellido2;sexo;añoNacimiento;numADN;ArrayADNs;
     */
    public static void guardarPacientes(JFileChooser direc) {
        ArrayList<Paciente> pac;
        ArrayList<Patologia> pacR, pacP;
        ArrayList<String> listaAdn;
        ArrayList<Integer> listaNum;
        ArrayList<Sintoma> listaSint;
        
        String text = "Pacientes\n";
        Paciente p;
        //Obtener un Array con todos los Strings
        pac = cjpac.listaPacientes(); 
        CtrlData.abrirFichero(direc);
        for( int i = 0; i < pac.size(); i++ ){  //Esquema: nHist;nombre;apellido1;apellido2;sexo;añoNacimiento;numADN;ArrayADNs;
            p = pac.get(i); 
            text += p.getnHistorial() + ";" + p.getNombre() + ";" + p.getApellido1() + ";" + p.getApellido2() + ";" + p.getSexo() + ";" + p.getNacimiento();
            //Sequencias de ADN
            listaAdn = p.getAdn();
            text += ";" + listaAdn.size();
            for(int j = 0; j < listaAdn.size(); j++) text += ";" + listaAdn.get(j);
            //Años de las sequencias
            listaNum = p.getEdadAdn();
            text += ";" + listaNum.size();
            for(int j = 0; j < listaNum.size(); j++) text += ";" + listaNum.get(j);
            //Patologias R            
            pacR = p.getPatologiasR();
            text += ";" + pacR.size();
            for(int j = 0; j < pacR.size(); j++) text += ";" + pacR.get(j).getNombre();
            //Patologias P 
            pacP = p.getPatologiasP();
            text += ";" + pacP.size();
            for(int j = 0; j < pacP.size(); j++) text += ";" + pacP.get(j).getNombre();
            //Sintomas 
            listaSint = p.getSintomas();
            text += ";" + listaSint.size();
            for(int j = 0; j < listaSint.size(); j++) text += ";" + listaSint.get(j).getNombre();
            text += "\n";
            //Guardar
            if(((i%100)==0) || (i+1 == pac.size())){
                CtrlData.guardarFichero(text);
                text = "";
            }
        } 
        CtrlData.cierraFichero();
    }

    /**
     * Pre:
     * @see Post: La patologia Pato se ha eliminado de todos los pacientes que la tienen
     * @param Pato
     */
    public static void actualizaPato(String Pato) {
        cjpac.actualizaPato(Pato);
    }
    
    /**
     * Pre:
     * @see Post: El sintoma Sint se ha eliminado de todos los pacientes que lo tienen
     * @param Sint
     */
    public static void actualizaSint(String Sint) {
        cjpac.actualizaSint(Sint);
    }

    /**
     * Pre:
     * @return Se retorna la secuencia de adn mas actual del paciente con nh, si el paciente no existe o no tiene secuencias se retorna un string vacio
     * @param nh
     */
    public static String get1Adn(Integer nh) {
        String adn = "";
        if (cjpac.ExistePaciente(nh)){
            Paciente p = cjpac.getPaciente(nh);
            Integer edad = p.getEdadAdn(0);
            if (edad != -1) adn = p.getAdn(0);
        }
        return adn;
    }


    /**
     * Pre:
     * @see Post: Se asignan las patologias potenciales pasadas por parametro al paciente con mumero de historial nh, si existe
     * @param potenciales
     * @param nh
     */
    public static void setPotenciales(ArrayList<String> potenciales, Integer nh ){
        Paciente p = cjpac.getPaciente(nh);
        if (p.getnHistorial()!= -1)p.setPatologiasP(CtrlPatologia.getPatologias(potenciales));
    }
    
    
    public static int getNumPacientes (){
        return cjpac.numPacientes();
    }
    
    
    public static ArrayList<Integer> getPacientesPatologia(String patologia){
        return cjpac.getPacientesPatologia(patologia);
    }
    
}
