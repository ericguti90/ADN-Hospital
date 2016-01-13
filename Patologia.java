
import java.util.ArrayList;

/**
 *
 * @author ADN
 */
public class Patologia {
    
    private String                  nombre;
    private String                  descripcion;
    private String                  generalizada;
    private ArrayList<Patologia>    patologiasR;
    private ArrayList<Sintoma>      sintomas;
    private ArrayList<Resultado>    resultados;

    
    
    /**
     * pre: Creadora por defecto
     */
    public Patologia (){
        
        nombre         = null;
        descripcion    = null;
        generalizada   = null;
        patologiasR    = null;
        sintomas       = null;
        resultados     = null;          
    }
    
    
    
    /**
     * pre: Creadora pasando 6 parametros
     * @param nombreP
     * @param descripcionP
     * @param generalizadaP
     * @param patologiaRP
     * @param sintomaP
     * @param resultadoP
     */
    public Patologia(String nombreP, String descripcionP, String generalizadaP, ArrayList<Patologia> patologiaRP, ArrayList<Sintoma> sintomaP, ArrayList<Resultado> resultadoP) {
        
        nombre         = nombreP;
        descripcion    = descripcionP;
        generalizada   = generalizadaP;
        patologiasR    = patologiaRP;
        sintomas       = sintomaP;
        resultados     = resultadoP;
    }
    
    
    // GETTERS

    
    /**
     * pre: La patologia debe existir
     * @return Devuelve el nombre de la patologia
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * pre: La patologia debe existir
     * @return Devuelve la descripcion de la patologia
     */
    public String getDescripcion() {
        return descripcion;
    }
    
    /**
     * pre: La patologia debe existir
     * @return Devuelve la ristra generalizada de la patologia
     */
    public String getGeneralizada() {
        return generalizada;
    }
    
    /**
     * pre: La patologia debe existir
     * @return Devuelve el array de patologias relacionadas
     */
    public ArrayList<Patologia> getPatologiasR() {

        
        return patologiasR;
    }
    
    /**
     * pre: La patologia debe existir
     * @return Devuelve el array de sintomas relacionados con la patologia
     */
    public ArrayList<Sintoma> getSintomas() {
        return sintomas;
    }
    
    /**
     * pre: La patologia debe existir
     * @return Devuelve el array de resultados relacionados con la patologia
     */
    public ArrayList<Resultado> getResultados() {
        return resultados;
    }


    // SETTERS
 
    /**
     * pre: La patologia debe existir
     * @param nombreP
     * @see El parametro nombreP es el nuevo nombre de la patologia
     */
    public void setNombre(String nombreP) {
        nombre = nombreP;
    }
    
    /**
     * pre: La patologia debe existir
     * @param descripcionP
     * @see El parametro descripcionP es la nueva descripcion de la patologia
     */
    public void setDescripcion(String descripcionP) {
        descripcion = descripcionP;
    }
    
    /**
     * pre: La patologia debe existir
     * @param generalizadaP
     * @see El parametro generalizadaP es la nueva generalizada
     */
    public void setGeneralizada(String generalizadaP) {
        generalizada = generalizadaP;
    }

    /**
     * pre: La patologia debe existir
     * @param patologiasRP
     * @see El parametro patologiasRP es la nueva array de patologiasR
     */
    public void setPatologiasR(ArrayList<Patologia> patologiasRP) {
        patologiasR = patologiasRP;
    }
    
    /**
     * pre: La patologia debe existir
     * @param patologiaRP
     * @see La patologia patologiasRP se añade a la array de patologiasR
     */
    public void set1PatologiaR(Patologia patologiaRP) {
        patologiasR.add(patologiaRP);
    }

    /**
     * pre: La patologia debe existir
     * @param sintomasP
     * @see El parametro sintomasP es la nueva array de sintomas
     */
    public void setSintomas(ArrayList<Sintoma> sintomasP) {
        sintomas = sintomasP;
    }
    
    /**
     * pre: La patologia debe existir
     * @param sintomasP
     * @see Se añade el sintomasP a la array de sintomas
     */
    public void set1Sintoma(Sintoma sintomasP) {
        sintomas.add(sintomasP);
    }
    
    /**
     * pre: La patologia debe existir
     * @param resultadosP
     * @see El parametro resultadosP es la nueva array de resultados
     */
    public void setResultados(ArrayList<Resultado> resultadosP) {
        resultados = resultadosP;
    }
    
    /**
     * pre: La patologia debe existir
     * @param resultadosP
     * @see se añade resultadosP a la array de resultados
     */
    public void set1Resultado(Resultado resultadosP) {
        resultados.add(resultadosP);
    }    
    
}
