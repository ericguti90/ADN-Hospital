
import java.util.ArrayList;

/**
 * 
 * @author Eric Gutierrez
 */
public class CjtSintomas{
    private NodoAVL A;
    private boolean Hh;
	
    /**
     * Creadora por defecto
     */
    public void CjtSintomas(){
            A 		= null;
            Hh 		= false;
    }

    /**
     * Pre: El sintoma tiene que ser valido
     * @res Sintoma insertado en el avl, si ya no estaba
     * @param s
     */
    public void InsercionSintoma (Sintoma s){
        if (!ExisteSintoma(s.getNombre())){ 
            NodoAVL info = new NodoAVL(s); 
            A=InsertarBalanceado(A,info);
        }
    }

    private NodoAVL InsertarBalanceado(NodoAVL R, NodoAVL Nodo){
        NodoAVL N1;
        NodoAVL info = Nodo;
        if (ArbolVacio(R)){
            R = info;
            Hh=true;                                                            
        }
        else if (Nodo.getSint().getNombre().compareTo(R.getSint().getNombre()) < 0){
            R.setIzquierdo(InsertarBalanceado(R.getIzquierdo(),Nodo));
            if (Hh) switch(R.getFactbalance()){
                case 1:{
                    R.setFactbalance(0);
                    Hh=false;
                } break;
                case 0:	R.setFactbalance(-1); 
                  break;
                 //se reestructura ya que pasaria a valer-2 y se desequilibra a la izq
                case -1:{
                    N1=R.getIzquierdo();
                    if (N1.getFactbalance() == -1) R = RotacionIzquierdaIzquierda(R,N1);
                    else R = RotacionIzquierdaDerecha(R,N1);
                    Hh = false;
                } break;
            }		
        }
        else{	
            if (Nodo.getSint().getNombre().compareTo(R.getSint().getNombre()) > 0){
                R.setDerecho(InsertarBalanceado(R.getDerecho(),Nodo));
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
                        N1=R.getDerecho();
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

    /**
     * Pre: Avl inicializado
     * @return devuelve true si no existe raiz, false lo contrario
     */
    public boolean ArbolVacio(){
        return ArbolVacio(A);
    }

    private boolean ArbolVacio(NodoAVL R){
	return (R == null);
    }

    private NodoAVL RotacionDerechaDerecha(NodoAVL N, NodoAVL N1){
	N.setDerecho(N1.getIzquierdo());
	N1.setIzquierdo(N);
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
	
    private NodoAVL RotacionDerechaIzquierda(NodoAVL N, NodoAVL N1){
	NodoAVL N2;
	N2 = N1.getIzquierdo();
	N.setDerecho(N2.getIzquierdo());
	N2.setIzquierdo(N);
	N1.setIzquierdo(N2.getDerecho());
	N2.setDerecho(N1);
        if (N2.getFactbalance()==1) N.setFactbalance(-1);
        else N.setFactbalance(0);
	if (N2.getFactbalance()==-1)	N1.setFactbalance(1);
        else N1.setFactbalance(0);
	N2.setFactbalance(0);
	N=N2;
	return N;
    }
	
    private NodoAVL RotacionIzquierdaIzquierda(NodoAVL N, NodoAVL N1){
	N.setIzquierdo(N1.getDerecho());
	N1.setDerecho(N);
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
	
    private NodoAVL RotacionIzquierdaDerecha(NodoAVL N, NodoAVL N1){
	NodoAVL N2;
	N2=N1.getDerecho();
	N.setIzquierdo(N2.getDerecho());
	N2.setDerecho(N);
	N1.setDerecho(N2.getIzquierdo());
	N2.setIzquierdo(N1);
	if (N2.getFactbalance()==1) N1.setFactbalance(-1);
        else N1.setFactbalance(0);
	if (N2.getFactbalance()==-1)	N.setFactbalance(1);
        else N.setFactbalance(0);
	N2.setFactbalance(0);
	N=N2;
	return N;
    }
    
    /**
     * Pre: Parametro nombre valido
     * @param nombre
     * @return Devuelve sintoma null si no existe el sintoma con parametro null, sino devuelve sintoma existente
     */
    public Sintoma getSintoma(String nombre){
        NodoAVL Aux = A;
        Sintoma sinaux = null;
        while (Aux != null){
            if (nombre.equals(Aux.getSint().getNombre())){
                sinaux = Aux.getSint();
                Aux = null;
            }
            else{
                if (nombre.compareTo(Aux.getSint().getNombre()) > 0) Aux = Aux.getDerecho();
                else Aux = Aux.getIzquierdo();
           }
        }
        return sinaux;
    }

    /**
     * Pre: El parametro nombre debe de ser valido
     * @param nombre
     * @return Devuelve true si existe el sintoma, false en caso contrario
     */
    public boolean ExisteSintoma(String nombre){
	NodoAVL Aux = A;
	boolean sintomain = false;
	while (Aux != null){
        if (nombre.equals(Aux.getSint().getNombre())) {
            sintomain = true;
		    Aux = null;
	    }
	    else{
            if (nombre.compareTo(Aux.getSint().getNombre())>0) Aux = Aux.getDerecho();
            else Aux = Aux.getIzquierdo();
        }
	}
	return sintomain;
    }

    /**
     * Pre: Debe de existir el sintoma a modificar
     * @param nombre
     * @param descrip
     * @param pato
     * @return Devuelve true si se ha podido modificar el sintoma
     */
    public boolean ModificarSintoma(String nombre, String descrip, ArrayList<Patologia> pato) {
        if (ExisteSintoma(nombre)) {
            NodoAVL Aux = A;
            while (Aux != null){
               if (nombre.equals(Aux.getSint().getNombre())){
                    Aux.getSint().setDescripcion(descrip);
                    Aux.getSint().setPatologiaR(pato);
                    Aux = null; 
               }
               else{
                    if (nombre.compareTo(Aux.getSint().getNombre())>0) Aux = Aux.getDerecho(); 
                    else Aux = Aux.getIzquierdo();
               }
            }
            return true;
        }
        return false;
    }

    /**
     * Pre: El parametro nombre debe de ser valido
     * @param nombre
     * @return Devuelve true si se ha podido eliminar el sintoma
     */
    public boolean EliminarSintoma (String nombre) {
        if (ExisteSintoma(nombre)) {
            NodoAVL aux = null;
            A = Rec(A,aux,nombre);
            return true;
        }
        else return false;
    }

    private NodoAVL Rec(NodoAVL orig,NodoAVL cop,String nombre){
           if(!ArbolVacio(orig)) {
            Sintoma s = orig.getSint();
            if (!s.getNombre().equals(nombre)) {
                NodoAVL info = new NodoAVL(s);
                cop=InsertarBalanceado(cop,info);
            }
            cop = Rec(orig.getDerecho(),cop,nombre);
            cop = Rec(orig.getIzquierdo(),cop,nombre);
        }
        return cop;
    }

    /**
     * Pre: Arbol AVL inicializado
     * @return array con todos los sintomas
     */
    public ArrayList<Sintoma> listaSintomas(){
        ArrayList<Sintoma> aux = listaSintomas(A);
        return aux;
    }

    private ArrayList<Sintoma> listaSintomas(NodoAVL Nodo) {
        ArrayList<Sintoma> aux = new ArrayList();
        if (Nodo != null) {
            aux.addAll(listaSintomas (Nodo.getIzquierdo()));
            aux.add(Nodo.getSint());
            aux.addAll(listaSintomas (Nodo.getDerecho()));
        }
        return aux;
    }
    
    public ArrayList<String> getNombreSintomas(){
        ArrayList<String> aux = new ArrayList();
        aux.addAll(listaNombreSintomas(A));
        return aux;
    }
    
    private ArrayList<String> listaNombreSintomas(NodoAVL Nodo) {
        ArrayList<String> aux = new ArrayList();
        if (Nodo != null) {
            aux.addAll(listaNombreSintomas(Nodo.getIzquierdo()));
            aux.add(Nodo.getSint().getNombre());
            aux.addAll(listaNombreSintomas (Nodo.getDerecho()));
        }
        return aux;
    }


    private class NodoAVL{
	private Sintoma     sint;
    private int         Factbalance;
	private NodoAVL     Derecho, Izquierdo;
	
        public NodoAVL(){
            sint = null;
            Factbalance = 0;
            Derecho = null;
            Izquierdo = null;
        }
        
        public NodoAVL (Sintoma sin){
            sint = sin;
            Factbalance = 0;
            Derecho = null;
            Izquierdo = null;
	}

        public NodoAVL getDerecho() {
            return Derecho;
        }

        public void setDerecho(NodoAVL DerechoAVL) {
            Derecho = DerechoAVL;
        }

        public int getFactbalance() {
            return Factbalance;
        }

        public void setFactbalance(int FactbalanceAVL) {
            Factbalance = FactbalanceAVL;
        }

        public NodoAVL getIzquierdo() {
            return Izquierdo;
        }

        public void setIzquierdo(NodoAVL IzquierdoAVL) {
            Izquierdo = IzquierdoAVL;
        }

        public Sintoma getSint() {
            return sint;
        }

        public void setSint(Sintoma sintAVL) {
            sint = sintAVL;
        }
    }
}
