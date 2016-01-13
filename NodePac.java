

/**
 *
 * @author ADN
 */
public class NodePac {
    private Paciente    pac;
    private NodePac     h_izqADN;
    private NodePac     h_dchoADN;
    private NodePac     h_izqNH;
    private NodePac     h_dchoNH;


    /**
     * Creadora por defecto
     */
    public NodePac(){
        pac = new Paciente();
        h_izqADN = null;
        h_dchoADN = null;
        h_izqNH = null;
        h_dchoNH = null;        
    }

    /**
     * Pre:
     * @param phijo
     */
    public void setHijo_izqADN(NodePac phijo){
        h_izqADN = phijo;
    }

    /**
     * Pre:
     * @param phijo
     */
    public void setHijo_dchoADN(NodePac phijo){
        h_dchoADN = phijo;
    }
    /**
     * Pre:
     * @param phijo
     */
    public void setHijo_izqNH(NodePac phijo){
        h_izqNH = phijo;
    }

    /**
     * Pre:
     * @param phijo
     */
    public void setHijo_dchoNH(NodePac phijo){
        h_dchoNH = phijo;
    }

    /**
     * Pre:
     * @param p
     */
    public void setPaciente(Paciente p){
        pac = p;
    }

    /**
     *Pre:
     * @return Devuelve el paciente del nodo
     */
    public Paciente getPaciente(){
        return pac;
    }

    /**
     * Pre:
     * @return Devuelve el hijo izquierdo por adn
     */
    public NodePac getHijo_izqADN(){
       return h_izqADN;
    }

    /**
     * Pre:
     * @return Devuelve el hijo derecho por adn
     */
    public NodePac getHijo_dchoADN(){
       return h_dchoADN;
    }
    
    /**
     * Pre:
     * @return Devuelve el hijo izquierdo por numero de historial
     */
    public NodePac getHijo_izqNH(){
       return h_izqNH;
    }

    /**
     * Pre:
     * @return Devuelve el hijo derecho por numero de historial
     */
    public NodePac getHijo_dchoNH(){
       return h_dchoNH;
    }

    /**
     * Pre:
     * @return Devuelve cierto si el hijo derecho por adn es vacio
     */
    public boolean Hijo_dchoADN_Vacio(){
        return (h_dchoADN == null);
    }

    /**
     * Pre:
     * @return Devuelve cierto si el hijo izquierdo por adn es vacio
     */
    public boolean Hijo_izqADN_Vacio(){
        return (h_izqADN == null);
    }

    /**
     * Pre:
     * @return Devuelve cierto si el hijo derecho por numero de historial es vacio
     */
    public boolean Hijo_dchoNH_Vacio(){
        return (h_dchoNH == null);
    }

    /**
     * Pre:
     * @return Devuelve cierto si el hijo izquierdo por numero de historial es vacio
     */
    public boolean Hijo_izqNH_Vacio(){
        return (h_izqNH == null);
    }    
    
}
