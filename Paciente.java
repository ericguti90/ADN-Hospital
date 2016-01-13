
import java.util.ArrayList;



/** *
 * @author ADN
 */
public class Paciente {
    
    private int                 nHistorial;
    private String              nombre;
    private String              apellido1;
    private String              apellido2;
    private char                sexo;
    private int                 nacimiento;
    private ArrayList<String>   adn;
    private ArrayList<Integer>  edadAdn;
    private ArrayList<Patologia>  patologiasR;
    private ArrayList<Patologia>  patologiasP;
    private ArrayList<Sintoma>   sintomas;


    /**
     * Pre: Creadora por defecto
     */
    public Paciente() {

        nHistorial  = -1;
        nombre      = null;
        apellido1   = null;
        apellido2   = null;
        sexo        = 'x';
        nacimiento  = -1;
        adn         = null;
        edadAdn     = new ArrayList();
        edadAdn.add(0, -1);
        patologiasR = null;
        patologiasP = null;
        sintomas    = null;
    }

    /**
     * Pre: Creadora inicializadora
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
     *    
     */
    public Paciente(int nHistorialP, String nombreP, String apellido1P, String apellido2P, char sexoP, int nacimientoP, ArrayList<String> adnP, ArrayList<Integer> edadAdnP, ArrayList<Patologia> patologiasRP, ArrayList<Patologia> patologiasPP, ArrayList<Sintoma> sintomaP) {
        
        nHistorial  = nHistorialP;
        nombre      = nombreP;
        apellido1   = apellido1P;
        apellido2   = apellido2P;
        sexo        = sexoP;
        nacimiento  = nacimientoP;
        adn         = new ArrayList(adnP);
        edadAdn     = new ArrayList();
        if (edadAdnP.size() < 1) edadAdn.add(0, -1);
        else edadAdn = new ArrayList(edadAdnP);
        patologiasR = new ArrayList(patologiasRP);
        patologiasP = new ArrayList(patologiasPP);
        sintomas    = new ArrayList(sintomaP);
    }

    
    // GETTER
    /**
     * Pre: 
     * @return Devuelve el numero de historial del paciente objeto
     */
    public int getnHistorial() {
        return nHistorial;
    }
    
    /**
     * Pre: 
     * @return Devuelve el nombre del paciente objeto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Pre:
     * @return Devuelve el primer apellido del paciente objeto
     */
    public String getApellido1() {
        return apellido1;
    }
    
    /**
     * Pre:
     * @return Devuelve el segundo apellido del paciente objeto
     */
    public String getApellido2() {
        return apellido2;
    }
    
    /**
     * Pre:
     * @return Devuelve el sexo del paciente objeto
     */
    public char getSexo() {
        return sexo;
    }    
    
    /**
     * Pre:
     * @return Devuelve el año de nacimiento del paciente objeto
     */
    public int getNacimiento() {
        return nacimiento;
    }
    
    /**
     * Pre:
     * @return Devuelve todas las ristras de adn del paciente objeto o un ArraList vacio si no tiene
     */
    public ArrayList<String> getAdn() {
        return adn;
    }
    
    /**
     * Pre:
     * @param i
     * @return Devuelve la secuencia de adn en la posición pasada por parámetro del paciente objeto o un string vacio si no existe
     */
    public String getAdn(int i){
        return adn.get(i);
    }
    
    
    /**
     * Pre:
     * @return Devuelve todas las edades de ristras de adn del paciente objeto o un ArraList vacio si no tiene
     */
    public ArrayList<Integer> getEdadAdn() {
        return edadAdn;
    }
    /**
     * Pre:
     * @param i
     * @return Devuelve la edad de la secuencia de adn en la posición pasada por parámetro del paciente objeto o un -1 si no existe
     */
    public int getEdadAdn( int i){
        return edadAdn.get(i);
    }

    /**
     * Pre:
     * @return Devuelve todas las patologias diagnosticadas del paciente objeto o un ArraList vacio si no tiene
     */
    public ArrayList<Patologia> getPatologiasR() {
        return patologiasR;
    }

    
    /**
     * Pre:
     * @return Devuelve todas las patologias potenciales del paciente objeto o un ArraList vacio si no tiene
     */
    public ArrayList<Patologia> getPatologiasP() {
        return patologiasP;
    }
      
    /**
     * Pre:
     * @return Devuelve todos los sintomas del paciente objeto o un ArraList vacio si no tiene
     */
    public ArrayList<Sintoma> getSintomas(){
        return sintomas;
    }
    
    
    // SETTERS
    
    /**
     * Pre: El numero de historial debe ser mayor de 0
     * @param nHistorialP
     */
    public void setnHistorial(int nHistorialP) {
        nHistorial = nHistorialP;
    }
    
    /**
     * Pre: nombreP no puede ser vacio
     * @param nombreP
     */
    public void setNombre(String nombreP) {
        nombre = nombreP;
    }

    /**
     * Pre: apellido1P no puede ser vacio
     * @param apellido1P
     */
    public void setApellido1(String apellido1P) {
        apellido1 = apellido1P;
    }
    
    /**
     * Pre: apellido2 no puede ser vacio
     * @param apellido2P
     */
    public void setApellido2(String apellido2P) {
        apellido2 = apellido2P;
    }
    
    /**
     * Pre: sexo P tiene que ser m o f
     * @param sexoP
     */
    public void setSexo(char sexoP) {
        sexo = sexoP;
    }
    
    /**
     * Pre: nacimientoP debe estar entre 1900 y el año actual
     * @param nacimientoP
     */
    public void setNacimiento(int nacimientoP) {
        nacimiento = nacimientoP;
    }
    
    /**
     * Pre:
     * @param adnP
     */
    public void setAdn(ArrayList<String> adnP) {
        adn = adnP;
    }    
    
    /**
     * Pre: adnP no puede ser vacio
     * @param adnP
     */
    public void set1Adn(String adnP) {
        adn.add(0,adnP);
    }     
        
    /**
     * Pre: El numero de edades debe ser el mismo que de ristras introducidas cada edad debe estar entre 0 y 125
     * @param edadAdnP
     */
    public void setEdadAdn(ArrayList<Integer> edadAdnP) {
        edadAdn = edadAdnP;
    }

    /**
     * Pre: La edad debe estar entre 0 y 125, debe haberse introducido una secuencia de adn antes
     * @param edadAdnP
     */
    public void set1EdadAdn(Integer edadAdnP) {
        edadAdn.add(0, edadAdnP);
    }
    
    /**
     * Pre:
     * @param patologiasRP
     */
    public void setPatologiasR(ArrayList<Patologia> patologiasRP) {
        patologiasR = patologiasRP;
    }
    
    /**
     * Pre:
     * @param patRP
     */
    public void set1PatologiaR(Patologia patRP) {
        patologiasR.add(patRP);
    }
    
    
    /**
     * Pre:
     * @param patologiasPP
     */
    public void setPatologiasP(ArrayList<Patologia> patologiasPP) {
        patologiasP = patologiasPP;
    }
    
    /**
     * Pre:
     * @param patPP
     */
    public void set1PatologiaP(Patologia patPP) {
        patologiasP.add(patPP);
    }

    /**
     * Pre:
     * @param sintomasP
     */
    public void setSintomas(ArrayList<Sintoma> sintomasP) {
        sintomas = sintomasP;
    }
    
    /**
     * Pre:
     * @param s
     */
    public void set1Sintoma(Sintoma s) {
        sintomas.add(s);
    }


    /**
     * Pre:
     * @param s
     * @return Se elimina el sintoma si existe
     */
    public boolean eliminarSintoma(Sintoma s){
        return sintomas.remove(s);
    }
    
    /**
     * Pre:
     * @param p
     * @return Se elimina la patologia diagnosticada si existe, devuelve true si existia y ha sido eliminada.
     */
    public boolean eliminarPatologiaR(Patologia p){
        
        boolean e=false;
        if (patologiasR.contains(p)){
            int index = patologiasR.indexOf(p);
            patologiasR.remove(index);
            e=true;
        }
        return e;
    }
    
}
