

import java.util.ArrayList;

/**
 * 
 * @author ADN
 */
public class Resultado {
    
    private int                     id;
    private int                     any;
    private int                     edadI;
    private int                     edadF;
    private char                    sexo;
    private int                     q;
    private int                     m;
    private double                  e;
    private String                  def;
    private ArrayList<Patologia>    patologias;
    private ArrayList<String>       secuencias;
    private ArrayList<Patron>       patrones;

    /**
     * pre: creadora por defecto
     * @see genera un resultado por defecto
     */
    public Resultado (){
        id         = -1;
        any        =  0;
        edadI      = -1;
        edadF      = -1;
        sexo       = 'x';
        q          = 0;
        m          = 0;
        e          = 0;
        def        = null;
        patologias = null;
        secuencias = null;
        patrones   = null;
    }
    
    /**
     * pre: creadora con parametros
     * @see el nuevo resultado esta inicializado con los datos pasados por parametros
     * @param idR
     * @param patronR
     * @param fechaR
     * @param edadIR
     * @param edadFR
     * @param sexoR
     * @param patologiaRR
     */
    public Resultado(int idR, int fechaR, int edadIR, int edadFR, char sexoR, int minap, int mida, double error, String defi, ArrayList<Patologia> patologiaRR, ArrayList<String> sec, ArrayList<Patron> patronesR) {
        
        id         = idR;
        any        = fechaR;
        edadI      = edadIR;
        edadF      = edadFR;
        sexo       = sexoR; 
        q          = minap;
        m          = mida;
        e          = error;
        def        = defi;
        patologias = patologiaRR;
        secuencias = sec;
        patrones   = patronesR;
    }

    /**
     * pre: 
     * @return devuelve el entero edad final
     */
    public int getEdadF() {
        return edadF;
    }
    /**
     * pre: 
     * @return devuelve el entero edad inicial
     */
    public int getEdadI() {
        return edadI;
    }
    /**
     * pre:
     * @return devuelve el entero con el año
     */
    public int getFecha() {
        return any;
    }
    /**
     * pre:
     * @return devuelve la id del resultado
     */
    public int getId() {
        return id;
    }
    /**
     * pre:
     * @return devuelve la patologia del resultado
     */
    public ArrayList<Patologia> getPatologiaR() {
        return patologias;
    }
    /**
     * pre:
     * @return devuelve un String con el patron del resultado
     */
    public ArrayList<Patron> getPatrones() {
        return patrones;
    }
    /**
     * pre:
     * @return devuelve un char con el sexo: m (masculino), f (femenino) o x(ambos sexos)
     */
    public char getSexo() {
        return sexo;
    }
    
    public int getMinAp() {
        return q;
    }
    
    public int getMida() {
        return m;
    }
    
    public double getError() {
        return e;
    }
    
    public String getDef() {
        return def;
    }

    public ArrayList<String> getSecuencias() {
        return secuencias;
    }
    
    public void setPatron(String seqPatron, boolean[] patBool) {
        Patron p = new Patron(patBool.length);
        p.setPatron(seqPatron);
        p.setOcb(patBool);
        patrones.add(p);
    }
    
    
    public void setPatrones(ArrayList<Patron> patr){
        patrones = patr;
    }
    
    
    
}
