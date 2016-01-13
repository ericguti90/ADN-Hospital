

/**
 *
 * @author ADN
 */
public class NodeRes {
    private Resultado res;
    private NodeRes hijo_izq;
    private NodeRes hijo_dcho;
    private int Factbalance;
    
    
    /**
     * Creadora por defecto
     */   
    
   public NodeRes(){
        res = new Resultado();
        hijo_izq  = null;
        hijo_dcho = null;
    }
    

    /**
     * Pre:
     * @param rhijo
     */
    public void setHijo_izq(NodeRes rhijo){
        hijo_izq = rhijo;
    }

    /**
     * Pre:
     * @param phijo
     */
    public void setHijo_dcho(NodeRes rhijo){
        hijo_dcho = rhijo;
    }

    

    /**
     * Pre:
     * @param r
     */
    public void setResultado(Resultado r){
        res = r;
    }

    /**
     * Pre:
     * @see Devuelve el resultado del nodo
     * @return 
     */
    public Resultado getResultado(){
        return res;
    }

    /**
     * Pre:
     * @see Devuelve el hijo izquierdo
     * @return 
     */
    public NodeRes getHijo_izq(){
       return hijo_izq;
    }

    /**
     * Pre:
     * @see Devuelve el hijo derecho
     * @return
     */
    public NodeRes getHijo_dcho(){
       return hijo_dcho;
    }
    
    
    /**
     * Pre:
     * @see Devuelve el valor del factor de balance
     * @return
     */
    
    public int getFactbalance() {
        return Factbalance;
    }

    
    /**
     * Pre:
     * @see Asigna el valor del factor de balance
     * @return
     */    
    public void setFactbalance(int FactbalanceAVL) {
        Factbalance = FactbalanceAVL;
    }
    
    
    
    /**
     * Pre:
     * @see Devuelve cierto si el hijo derecho es vacio
     * @return 
     */
    public boolean Hijo_dcho_Vacio(){
        return (hijo_dcho == null);
    }

    /**
     * Pre:
     * @see Devuelve cierto si el hijo izquierdo es vacio
     * @return 
     */
    public boolean Hijo_izq_Vacio(){
        return (hijo_izq == null);
    }
    
    public boolean es_vacio(){
        return (res.getId()== -1);
    }
  
    
}
