
import java.util.ArrayList;

/**
 * 
 * @author Ferran
 */
public class CjtResultados {
    
    private NodeRes raiz;
    private int numNodes;
    private int idMax;
    private boolean Hh;
    
    /**
     * pre: creadora por defecto
     * @see Genera un nuevo CjtResultados inicializado y idMax = 1;
     */
    public CjtResultados(){
        raiz = null;
        numNodes = 0;
        idMax = 0;
        Hh = false;
    }
  
    
    
    /**
     * Pre:
     * @see Devuelve el numero de resultados del conjunto
     * @return 
     */
    public int getNumResultados(){
        return numNodes;
    } 
    
    /**
     * pre:
     * @see Devuelve un entero con la id maxima
     * @return 
     */
    public int getIdMax(){
        return idMax;
    }    

    /**
     * Pre: Avl inicializado
     * @see devuelve true si no existe raiz, false lo contrario
     * @return 
     */

    public boolean ArbolVacio(){
        return ArbolVacio(raiz);
    }

    private boolean ArbolVacio(NodeRes R){
	return (R == null);
    }
    
    
    
    /**
     * Pre:
     * @param id
     * @see Devuelve cierto si el resultado esta en el conjunto, false en caso contrario
     * @return 
     */
    public boolean ExisteResultado(Integer id){
	NodeRes aux = raiz;                                                        
	boolean resin = false;
	while (aux != null){                                                    
            if ( id == aux.getResultado().getId() ){                                              
                resin = true;
		aux = null;
	    }
	    else{                                                               
		if (id > aux.getResultado().getId()) aux = aux.getHijo_dcho(); 
                else aux = aux.getHijo_izq();                                       
            }
	}
	return resin;
    }
    
    
     /**
     * pre:
     * @see anade el resultado r al conjunto si no estaba, y si su id es mas grande que la idMax, actualiza idMax
     * @param r
     */
    public void anadirResultado(Resultado r){
        if(idMax < r.getId()) idMax = r.getId();
        if (!ExisteResultado(r.getId())){
            NodeRes info = new NodeRes();
            info.setResultado(r);
            raiz= InsertarBalanceado (raiz, info);
            ++numNodes;
        }    
    }
    
    private NodeRes InsertarBalanceado(NodeRes R, NodeRes Nodo){
        NodeRes N1;
        NodeRes info = Nodo;
        if (ArbolVacio(R)){
            R = info;
            Hh=true;                                                            
        }
        else if (Nodo.getResultado().getId() < R.getResultado().getId()){
            R.setHijo_izq(InsertarBalanceado(R.getHijo_izq(), Nodo));
            if (Hh) switch(R.getFactbalance()){
                case 1:
                    R.setFactbalance(0);
                    Hh=false;
                    break;
                case 0:	R.setFactbalance(-1); 
                    break;
                 //se reestructura ya que pasaria a valer-2 y se desequilibra a la izq
                case -1:
                    N1=R.getHijo_izq();
                    if (N1.getFactbalance() == -1) R = RotacionIzquierdaIzquierda(R,N1);
                    else R = RotacionIzquierdaDerecha(R,N1);
                    Hh = false;
                    break;
            }		
        }
        else{	
            if (Nodo.getResultado().getId() > R.getResultado().getId()){
                R.setHijo_dcho(InsertarBalanceado(R.getHijo_dcho(),Nodo));
                if (Hh)	switch(R.getFactbalance()){
                    case -1:
                        R.setFactbalance(0);
                        Hh=false;	
                        break;
                    case 0:
                        R.setFactbalance(1); 
                        break;
                    //se reestructura ya que pasaria a valer-2 y se desequilibra a la izq
                    case 1:{
                        N1=R.getHijo_dcho();
                        if (N1.getFactbalance()==1) R = RotacionDerechaDerecha(R,N1);
                        else R = RotacionDerechaIzquierda(R,N1);
                        Hh = false;
                    }
                    break;
                }	
            }
            else Hh = false;
        }    
        return R;	
    }
    
    private NodeRes RotacionDerechaDerecha(NodeRes N, NodeRes N1){
	N.setHijo_dcho(N1.getHijo_izq());
	N1.setHijo_izq(N);
	if (N1.getFactbalance()==1) {
            N.setFactbalance(0);
            N1.setFactbalance(0);
	}
	else{
            N.setFactbalance(1);
            N1.setFactbalance(-1);
	}
	N = N1;
	return N;
    }
	
    private NodeRes RotacionDerechaIzquierda(NodeRes N, NodeRes N1){
	NodeRes N2;
	N2 = N1.getHijo_izq();
	N.setHijo_dcho(N2.getHijo_izq());
	N2.setHijo_izq(N);
	N1.setHijo_izq(N2.getHijo_dcho());
	N2.setHijo_dcho(N1);
        if (N2.getFactbalance()==1) N.setFactbalance(-1);
        else N.setFactbalance(0);
	if (N2.getFactbalance()==-1)	N1.setFactbalance(1);
        else N1.setFactbalance(0);
	N2.setFactbalance(0);
	N=N2;
	return N;
    }
	
    private NodeRes RotacionIzquierdaIzquierda(NodeRes N, NodeRes N1){
	N.setHijo_izq(N1.getHijo_dcho());
	N1.setHijo_dcho(N);
	if (N1.getFactbalance()==-1){
            N.setFactbalance(0);
            N1.setFactbalance(0);
	}
	else{
            N.setFactbalance(-1);
            N1.setFactbalance(1);
	}
	N=N1;
	return N;
    }
	
    private NodeRes RotacionIzquierdaDerecha(NodeRes N, NodeRes N1){
	NodeRes N2;
	N2=N1.getHijo_dcho();
	N.setHijo_izq(N2.getHijo_dcho());
	N2.setHijo_dcho(N);
	N1.setHijo_dcho(N2.getHijo_izq());
	N2.setHijo_izq(N1);
	if (N2.getFactbalance()==1) N1.setFactbalance(-1);
        else N1.setFactbalance(0);
	if (N2.getFactbalance()==-1)	N.setFactbalance(1);
        else N.setFactbalance(0);
	N2.setFactbalance(0);
	N=N2;
	return N;
    }
    
    
    /**
     * pre: id es entero
     * @param id
     * @return si existe un resultado con identificador igual a id, lo devuele. Si no, devuelve null
     */
    
    
    public Resultado getResultado(int id){
        NodeRes Aux = raiz;
        Resultado resaux = null;
        while (Aux != null){
            if (id == Aux.getResultado().getId()){
                resaux = Aux.getResultado();
                Aux = null;
            }
            else{
                if (id > Aux.getResultado().getId()) Aux = Aux.getHijo_dcho();
                else Aux = Aux.getHijo_izq();
           }
        }
        return resaux;
    }
        
    
    
    /**
     * pre:
     * @param id
     * @return si existe, elimina el resultado del conjunto y devuelve true, si no existe, devuelve false
     */
    
    public boolean eliminaResultado (int id) {
        if (ExisteResultado(id)) {
            NodeRes aux = null;
            raiz = Rec(raiz,aux,id);
            --numNodes;
            return true;
        }
        else return false;
    }

    private NodeRes Rec(NodeRes orig,NodeRes cop,int id){
           if(!ArbolVacio(orig)) {
            Resultado r = orig.getResultado();
            if (r.getId() != id) {
                NodeRes info = new NodeRes();
                info.setResultado(r);
                cop=InsertarBalanceado(cop,info);
            }
            cop = Rec(orig.getHijo_dcho(),cop,id);
            cop = Rec(orig.getHijo_izq(),cop,id);
        }
        return cop;
    }

    /**
     * Pre: Arbol AVL inicializado
     * @return array con todos los resultados
     */
    public ArrayList<Resultado> listaResultados(){
        ArrayList<Resultado> aux = listaResultados(raiz);
        return aux;
    }

    private ArrayList<Resultado> listaResultados(NodeRes Nodo) {
        ArrayList<Resultado> aux = new ArrayList();
        if (Nodo != null) {
            aux.addAll(listaResultados (Nodo.getHijo_izq()));
            aux.add(Nodo.getResultado());
            aux.addAll(listaResultados (Nodo.getHijo_dcho()));
        }
        return aux;
    }


        /**
     * Pre:
     * @return Devuelve el id de todos los resultados del conjunto
     */
    public ArrayList<Integer> getIDtodos(){
        return getRecIDtodos (raiz);
    }

    private ArrayList<Integer> getRecIDtodos (NodeRes n){
        ArrayList<Integer> nids = new ArrayList();
        if (n!= null){
            if(n.getResultado().getId() != -1){
            if (!n.Hijo_izq_Vacio()) nids.addAll(getRecIDtodos(n.getHijo_izq()));
            nids.add(n.getResultado().getId());
            if (!n.Hijo_dcho_Vacio()) nids.addAll(getRecIDtodos(n.getHijo_dcho()));
            }
        }
        return nids;
    }


}

