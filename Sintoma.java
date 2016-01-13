
import java.util.ArrayList;

/**
 *
 * @author ADN
 */
public class Sintoma {
    
    private String              nombre;
    private String              descripcion;
    private ArrayList<Patologia>  patologiaR;
    
    /**
     * Creadora por defecto
     */
    public Sintoma(){
        nombre = null;
        descripcion = null;
        patologiaR = null;
    }

    /**
     * Pre: creadora con tres parametros
     * @param nombreS
     * @param descripcionS
     * @param patologiaRS
     * 
     */
    public Sintoma(String nombreS, String descripcionS, ArrayList<Patologia> patologiaRS) {
        
        nombre         = nombreS;
        descripcion    = descripcionS;
        patologiaR     = patologiaRS;
    }
    
    // GETTERS

    /**
     * Pre: El sintoma debe existir
     * @return devuelve la descripcion del sintoma
     */
    public String getDescripcion() {
        return descripcion;
    }
    /**
     * Pre: El sintoma debe existir
     * @return devuelve el nombre del sintoma
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Pre: El sintoma debe existir
     * @return devuelve un array de patologias relacionadas
     */
    public ArrayList<Patologia> getPatologiaR() {
        return patologiaR;
    }
    
    // SETTERS

    /**
     * Pre: El sintoma debe existir
     * @param descripcionS
     * @see El parametro descripcionS es la nueva descripcion del sintoma
     */
    public void setDescripcion(String descripcionS) {
        descripcion = descripcionS;
    }
    /**
     * Pre: El sintoma debe existir
     * @param nombreS
     * @see El parametro nombreS es el nuevo nombre del sintoma
     */
    public void setNombre(String nombreS) {
        nombre = nombreS;
    }
    /**
     * Pre: El sintoma debe existir
     * @param patologiaRS
     * @see El parametro patologiaRS es el nuevo array de patologiaR
     */
    public void setPatologiaR(ArrayList<Patologia> patologiaRS) {
        patologiaR = patologiaRS;
    }   
}
