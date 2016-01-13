
import java.util.ArrayList;

/**
 * 
 * @author Eric Gutierrez
 */
public class CjtPatologia{
    private NodoAVL A;
    private boolean Hh;

    /**
     * Creadora por defecto
     */
    public void CjtPatologia(){
            A 		= null;
            Hh 		= false;
    }

    /**
     * Pre: Patologia p valida
     * @see Inserta patologia en al AVL si no existia previamente
     * @param p
     */
    public void InsercionPatologia (Patologia p){
        if (!ExistePatologia(p.getNombre())){ 
            NodoAVL info = new NodoAVL(p);
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
        else if (Nodo.getPato().getNombre().compareTo(R.getPato().getNombre()) < 0){ 
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
            if (Nodo.getPato().getNombre().compareTo(R.getPato().getNombre()) > 0){
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
                    } break;
                }
            }
            else Hh = false;
        }
        return R;
    }

    /**
     * Pre: AVL inicializado
     * @return Devuelve true si no hay raiz, false lo contrario
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
     * Pre: parametro string nombre valido
     * @param nombre
     * @return Devuelve Patologia null si no existe, si existe, devuelve la patologia
     */
    public Patologia getPatologia(String nombre){
        NodoAVL Aux = A;
        Patologia patoaux = null;
        while (Aux != null){
            if (nombre.equals(Aux.getPato().getNombre())){ 
                patoaux = Aux.getPato();
		Aux = null;
	    }
	    else{ 
		if (nombre.compareTo(Aux.getPato().getNombre()) > 0) Aux = Aux.getDerecho();
		else{
                    Aux = Aux.getIzquierdo();
		}
            }
	}
	return patoaux;
    }

    /**
     * Pre: AVL inicializado
     * @param nombre
     * @return Devuelve true si existe la Patologia, false si no existe
     */
    public boolean ExistePatologia(String nombre){
	NodoAVL Aux = A; 
	boolean patoin = false;
	while (Aux != null){
            if (nombre.equals(Aux.getPato().getNombre())) {
                patoin = true;
		Aux = null;
	    }
	    else{ 
		if (nombre.compareTo(Aux.getPato().getNombre())>0) Aux = Aux.getDerecho(); 
		else{
                    Aux = Aux.getIzquierdo();
		}
            }
	}
	return patoin;
    }

    /**
     * Pre: Parametros validos
     * @param nombre
     * @param descrip
     * @param general
     * @param patol
     * @param sin
     * @param result
     * @return Devuelve true si modifica correctamente la patologia
     */
    public boolean ModificarPatologia(String nombre, String descrip, String general, 
            ArrayList<Patologia> patol, ArrayList<Sintoma> sin, ArrayList<Resultado> result) {
        if (ExistePatologia(nombre)) {
            NodoAVL Aux = A;
            while (Aux != null){      
               if (nombre.equals(Aux.getPato().getNombre())){
                    Aux.getPato().setDescripcion(descrip);
                    Aux.getPato().setGeneralizada(general);
                    Aux.getPato().setPatologiasR(patol);
                    Aux.getPato().setSintomas(sin);
                    Aux.getPato().setResultados(result);
                    Aux = null;
               }
               else{ 
                    if (nombre.compareTo(Aux.getPato().getNombre())>0) Aux = Aux.getDerecho();
                    else{
                        Aux = Aux.getIzquierdo(); 
                    }
               }
           }
        return true;
        }
        return false;
    }

    /**
     * Pre: Parametro valido
     * @param nombre
     * @return Devuelve true si se elimana correctamente la patologia
     */
    public boolean EliminarPatologia (String nombre) {
        if (ExistePatologia(nombre)) {
            NodoAVL aux = null;
            A = Rec(A,aux,nombre);
            return true;
        }
        else return false;
    }

    private NodoAVL Rec(NodoAVL orig,NodoAVL cop,String nombre){
           if(!ArbolVacio(orig)) {
            Patologia p = orig.getPato();
            if (!p.getNombre().equals(nombre)) {
                NodoAVL info = new NodoAVL(p);
                cop=InsertarBalanceado(cop,info);
            }
            cop = Rec(orig.getDerecho(),cop,nombre);
            cop = Rec(orig.getIzquierdo(),cop,nombre);
        }
        return cop;
    }


    /**
     * Pre: AVL inicializado
     * @return array de patologias
     */
    public ArrayList<Patologia> listarPatologia(){
        return listarPatologia(A);
    }

    private ArrayList<Patologia> listarPatologia(NodoAVL Nodo) {
        ArrayList<Patologia> aux = new ArrayList();
        if (Nodo != null) {
            aux.addAll(listarPatologia(Nodo.getIzquierdo()));
            aux.add(Nodo.getPato());
            aux.addAll(listarPatologia(Nodo.getDerecho()));
            
        }
        return aux;
    }


    public ArrayList<String> Potenciales(){
        return selecPot (A);
    }

    private ArrayList<String> selecPot(NodoAVL n){
        ArrayList<String> patos = new ArrayList();
        if(n!= null && n.getPato() != null){
            if (n.getPato().getGeneralizada()!= null){
                patos.add(n.getPato().getNombre());
                patos.add(n.getPato().getGeneralizada());
            }
        }
        if (n.getIzquierdo()!= null) patos.addAll(selecPot(n.getIzquierdo()));
        if (n.getDerecho()!= null) patos.addAll(selecPot(n.getDerecho()));

        return patos;
    }

    /*
    public ArrayList<String> similares(Patologia p){
        return pato_similares (p, A);
    }
    
    public ArrayList<String> pato_similares(Patologia p, NodoAVL n){
        String original = p.getGeneralizada();
        ArrayList<String> patos = new ArrayList();
        String gen;
        boolean esSim;
        if(n.getPato() != null && n.getPato().getGeneralizada()!= null && !n.getPato().getNombre().equals(p.getNombre())){                
            gen = n.getPato().getGeneralizada();
            esSim = CtrlPatologia.relacionadas (original, gen);
            if (esSim)patos.add(gen);
        }
        patos.addAll(pato_similares(p ,n.getIzquierdo()));
        patos.addAll(pato_similares(p , n.getDerecho()));

        return patos;        
    }*/
    
  
            
            
    private class NodoAVL{
	private Patologia   pato;
        private int         Factbalance;
	private NodoAVL     Derecho, Izquierdo;

        public NodoAVL(){
            pato = null;
            Factbalance = 0;
            Derecho = null;
            Izquierdo = null;
        }

        public NodoAVL (Patologia patol){
            pato = patol;
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

        public Patologia getPato() {
            return pato;
        }

        public void setPato(Patologia patoAVL) {
            pato = patoAVL;
        }
    }


}
