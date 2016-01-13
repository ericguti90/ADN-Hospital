
import java.util.ArrayList;


/**
 *
 * @author Merce Pedraza
 */
public class CjtPaciente { 

    private NodePac raizNH;
    private NodePac raizADN;
    private int numNodes;

    /**
     * Pre: Creadora por defecto
     */
    public CjtPaciente(){
        raizNH=new NodePac();
        raizADN=new NodePac();
        numNodes=0;   
    }
    
    
    /**
     * Pre:
     * @return Devuelve el numero de pacientes del conjunto
     */
    public int numPacientes(){
        return numNodes;
    }    

/*
    //para imprimir desde el driver
    public NodePac getRaizNH(){
        return raizNH;
    }
    //para imprimir desde el driver
    public NodePac getRaizADN(){
        return raizADN;
    }
*/
    
    public boolean pacVacio(NodePac n){
        return (n.getPaciente().getnHistorial()== -1);
    }
    
    
    /**
     * Pre:
     * @param nh
     * @return Devuelve cierto si el paciente esta en el conjunto
     */
    public boolean ExistePaciente(Integer nh){
	NodePac aux = raizNH;                                                        
	boolean pacin = false;
	while (aux != null){                                                    
            if (nh.equals(aux.getPaciente().getnHistorial())) {                                              
                pacin = true;
		aux = null;
	    }
	    else{                                                               
		if (nh.compareTo(aux.getPaciente().getnHistorial())>0) aux = aux.getHijo_dchoNH(); 
                else aux = aux.getHijo_izqNH();                                       
            }
	}
	return pacin;
    }
    
          
    
    /**
     * Pre: p debe ser un paciente valido
     * @param p
     */
    public void insertaPac(Paciente p){
        if (!ExistePaciente(p.getnHistorial())){
            NodePac n= insertaNHPac(p);
            if (!pacVacio(n)) insertaADNPac(n);
        }
    }
    
    private NodePac insertaNHPac(Paciente p){ 
        NodePac n= insNHPac(raizNH, p);
        return n;
    }
    
    private NodePac insNHPac (NodePac np,Paciente pa){  
        NodePac n= new NodePac();
        int nh= np.getPaciente().getnHistorial();
        
        if (nh ==-1){ //Nodo vacío
            np.setPaciente(pa);
            n=np;
            ++numNodes;
        }        
        else if (pa.getnHistorial()< nh){
            if (np.Hijo_izqNH_Vacio()) np.setHijo_izqNH(new NodePac());
            n=insNHPac(np.getHijo_izqNH(), pa);
        }
        else if (pa.getnHistorial()> nh){
            if (np.Hijo_dchoNH_Vacio())np.setHijo_dchoNH(new NodePac());
            n=insNHPac(np.getHijo_dchoNH(), pa);
        }
        return n;
    }

    
    private void insertaADNPac(NodePac np){
        int nhNp=np.getPaciente().getnHistorial();
        if (raizNH.getPaciente().getnHistorial()!= nhNp) insADNPac(raizADN, np);
        else raizADN=np;
    }
    
    private NodePac insADNPac (NodePac n_padre, NodePac n_aInsertar){
        
        NodePac aux= new NodePac();
        int edad_adnPadre= n_padre.getPaciente().getEdadAdn(0);
        int edad_adnPac= n_aInsertar.getPaciente().getEdadAdn(0);
        int nh=n_aInsertar.getPaciente().getnHistorial();
        
        if (edad_adnPac <= edad_adnPadre){
            if (n_padre.Hijo_izqADN_Vacio() || n_padre.getHijo_izqADN().getPaciente().getnHistorial()== nh){
                n_padre.setHijo_izqADN(n_aInsertar);
                aux= n_padre;
            }
            else insADNPac(n_padre.getHijo_izqADN(), n_aInsertar);
        }            
        else if (edad_adnPac > edad_adnPadre){
            if (n_padre.Hijo_dchoADN_Vacio() || n_padre.getHijo_dchoADN().getPaciente().getnHistorial()== nh){
                n_padre.setHijo_dchoADN(n_aInsertar);
                aux= n_padre;
            }
            else aux=insADNPac(n_padre.getHijo_dchoADN(), n_aInsertar);
        }
        return aux;
    }
   
  
    /**
     * Pre:
     * @param nh
     * @return Devuelve el paciente solicitado si existe, sino un paciente vacio
     */
    public Paciente getPaciente(int nh){
        Paciente p=new Paciente();
        if (!pacVacio(raizNH)) p=consPac(raizNH, nh);
        return p;
    }
            
    private Paciente consPac (NodePac n, int numh){
        Paciente pa=new Paciente();
        int nh_node= n.getPaciente().getnHistorial();
        if (nh_node== numh) pa=n.getPaciente();
        else if (numh < nh_node && !n.Hijo_izqNH_Vacio()) pa = consPac(n.getHijo_izqNH(), numh);  
        else if (numh > nh_node&& !n.Hijo_dchoNH_Vacio()) pa = consPac(n.getHijo_dchoNH(), numh);   
        return pa;
    }
    
    
    
        
    /**
     * Pre:
     * @param nh
     */
    public void eliminaPac(int nh){
        
        int nh_raiz= raizNH.getPaciente().getnHistorial();
        if (nh_raiz== nh){
            raizNH = elimPac(raizNH, nh);
            if (raizNH==null) raizNH= new NodePac();
        }
        else if (nh_raiz!= -1)elimPac(raizNH, nh);
        
        /*
                System.out.println("Imprimo arbol adn tras eliminar fuera del modificar:");
                imprime_Cjt_ADN(raizADN);
                System.out.println(" ");
                System.out.println("Imprimo arbol nh tras eliminar fuera del modificar:");
                imprime_Cjt_NH(raizNH);
                System.out.println(" ");
                * 
                */
      }  
    
      private NodePac elimPac(NodePac n, int nhist){

          int nhist_n= n.getPaciente().getnHistorial();
          if (nhist_n==nhist){ //encontrado el elemento a eliminar
              n=eliminaPacADN(n);

              if (n.Hijo_izqNH_Vacio() && n.Hijo_dchoNH_Vacio()) n =null;
              else if (n.Hijo_izqNH_Vacio())n=n.getHijo_dchoNH();
              else if (n.Hijo_dchoNH_Vacio())n=n.getHijo_izqNH();
              else{ //tiene los dos hijos
                 NodePac aux= n;   
                 n.setHijo_izqNH(elimina2Hijos(n.getHijo_izqNH(), aux)); 
            }
            --numNodes;
          }
          else if (nhist < nhist_n && !n.Hijo_izqNH_Vacio())n.setHijo_izqNH(elimPac(n.getHijo_izqNH(), nhist));
          else if (nhist > nhist_n && !n.Hijo_dchoNH_Vacio())n.setHijo_dchoNH(elimPac(n.getHijo_dchoNH(), nhist));

          return n;
      }

    
    private NodePac elimina2Hijos (NodePac a, NodePac p) {
        
        NodePac resul;
        if (!a.Hijo_dchoNH_Vacio()) {
            resul = a;
            a.setHijo_dchoNH(elimina2Hijos (a.getHijo_dchoNH(), p));
        }
        else {
            p.setPaciente(a.getPaciente());
            resul = a.getHijo_izqNH();
        }
        return resul;
    }
    
      private NodePac eliminaPacADN(NodePac n_ADN){
          int nh_raiz= raizADN.getPaciente().getnHistorial();
          int nh= n_ADN.getPaciente().getnHistorial();          
          if (nh_raiz== nh){
            raizADN=elimPacADN(raizADN, n_ADN);            
            if (raizADN==null) raizADN= new NodePac();
          }
          else elimPacADN(raizADN, n_ADN);
          return n_ADN;
      }      
      
      
      private NodePac elimPacADN(NodePac n_arbol, NodePac n_ADN){
          
          int edad_arbol= n_arbol.getPaciente().getEdadAdn(0);
          int edad_elim= n_ADN.getPaciente().getEdadAdn(0);
          if (n_arbol.getPaciente().getnHistorial()==n_ADN.getPaciente().getnHistorial()){ //encontrado el elemento a eliminar
              if (n_arbol.Hijo_izqADN_Vacio() && n_arbol.Hijo_dchoADN_Vacio())    n_arbol =null;
              else if (n_arbol.Hijo_izqADN_Vacio())n_arbol=n_arbol.getHijo_dchoADN();
              else if (n_arbol.Hijo_dchoADN_Vacio())n_arbol=n_arbol.getHijo_izqADN();
              else{ //tiene los dos hijos
                 NodePac aux= n_arbol;
                 n_arbol.setHijo_izqADN(elimina2HijosADN(n_arbol.getHijo_izqADN(), aux)); 
              }
          }
          else if (edad_elim <= edad_arbol && !n_arbol.Hijo_izqADN_Vacio())n_arbol.setHijo_izqADN(elimPacADN(n_arbol.getHijo_izqADN(), n_ADN));
          else if (edad_elim > edad_arbol && !n_arbol.Hijo_dchoADN_Vacio())n_arbol.setHijo_dchoADN(elimPacADN(n_arbol.getHijo_dchoADN(), n_ADN));
          
          return n_arbol;          
      }
      
     private NodePac elimina2HijosADN (NodePac a, NodePac p) {
        NodePac resul;
        if (!a.Hijo_dchoADN_Vacio()) {
            resul = a;
            a.setHijo_dchoADN(elimina2HijosADN (a.getHijo_dchoADN(), p));
        }
        else {
            p.setPaciente(a.getPaciente());
            resul = a.getHijo_izqADN();
        }
        return resul;
    }     
      
   
     /**
      * Pre:
      * @param pNuevo
      * @return Modifica el paciente con los datos pasados por parametro
      */
     public boolean modificaPac(Paciente pNuevo) {
          boolean pres=false;
          if (!pacVacio(raizNH))pres = modPac(raizNH, pNuevo);
          return pres;
      }
      
      private boolean modPac(NodePac n, Paciente pacNuevo){
        boolean pr=false;
        int nh_ant= n.getPaciente().getnHistorial();
        int nh_nuevo= pacNuevo.getnHistorial();
        if (nh_ant==nh_nuevo){
            Paciente pacAntiguo = n.getPaciente();
            if (pacNuevo.getEdadAdn(0) != pacAntiguo.getEdadAdn(0)){
                eliminaPac(pacAntiguo.getnHistorial());
                insertaPac(pacNuevo); 
            }
            else{
                n.setPaciente(pacNuevo);
            }
            pr= true;
        }
        else if (nh_nuevo < nh_ant && !n.Hijo_izqNH_Vacio())pr=modPac(n.getHijo_izqNH(), pacNuevo);
        else if (nh_nuevo > nh_ant && !n.Hijo_dchoNH_Vacio()) pr=modPac(n.getHijo_dchoNH(), pacNuevo);  
        
        return pr;
      }
      
    
     
      /**
       * Pre: edadI y edadF no pueden ser menor de -1
       * @param sexo
       * @param edadI
       * @param edadF
       * @param pato
       * @return Devuelve un listado con las secuencias que cumplen las condiciones
       */
      public ArrayList<String> seleccionaPac(char sexo, int edadI, int edadF, String pato){
        ArrayList<String> lPac = new ArrayList();
        if (!pacVacio(raizADN)) lPac= selPac(raizADN, sexo, edadI, edadF, pato);
        return lPac;
    }
    
    
    
    private boolean tienePato (Paciente p, String pato){
        ArrayList<Patologia> lpato;
        lpato = p.getPatologiasR();
        int i=0;
        boolean t= false;       
        while (!t && i< lpato.size()) {
            t= lpato.get(i).getNombre().equals(pato);
            ++i;
        } 
        return t;
    }
    
    private boolean sexoValido(char sexoNodo, char sexoPedido){
        return ((sexoPedido == sexoNodo) || (sexoPedido == 'x'));
    }

    private ArrayList<String> selPac ( NodePac n, char sexo, int edadI, int edadF, String pato){
        ArrayList<String> listPac = new ArrayList();
        Paciente p = n.getPaciente();
        int edad_adn=p.getEdadAdn(0);
        if (edad_adn!= -1){
            if ((edadI==-1)&&(edadF == -1)){ //Explorar todo el arbol
                if (tienePato(p,pato) &&  sexoValido(p.getSexo(),sexo))listPac.add(p.getAdn(0));
                if (!n.Hijo_izqADN_Vacio()) listPac.addAll(selPac(n.getHijo_izqADN(),sexo, edadI, edadF, pato));
                if (!n.Hijo_dchoADN_Vacio())listPac.addAll(selPac(n.getHijo_dchoADN(),sexo, edadI, edadF, pato));  
            }

            else if (edadI==-1){ //menor que edadF (exluyente)
                if (edad_adn<edadF){
                    if (tienePato(p,pato) &&  sexoValido(p.getSexo(),sexo))listPac.add(p.getAdn(0));
                    if (!n.Hijo_dchoADN_Vacio()) listPac.addAll(selPac(n.getHijo_dchoADN(), sexo, edadI, edadF, pato));
                }
                if (!n.Hijo_izqADN_Vacio()) listPac.addAll(selPac(n.getHijo_izqADN(),sexo, edadI, edadF, pato));
            }

            else if (edadF== -1){ //mayor que edadI
                if (edad_adn>=edadI){
                    if (tienePato(p,pato) &&  sexoValido(p.getSexo(),sexo))listPac.add(p.getAdn(0));
                    if (!n.Hijo_izqADN_Vacio()) listPac.addAll(selPac(n.getHijo_izqADN(),sexo, edadI, edadF, pato));
                }
                if (!n.Hijo_dchoADN_Vacio())listPac.addAll(selPac(n.getHijo_dchoADN(),sexo, edadI, edadF, pato)); 
            }                

            else{ //entre edadI y edadF, no incluye edadF
                if (edadI<=edad_adn && edad_adn<edadF){
                    if (tienePato(p,pato) &&  sexoValido(p.getSexo(),sexo))listPac.add(p.getAdn(0));
                    if (!n.Hijo_izqADN_Vacio()) listPac.addAll(selPac(n.getHijo_izqADN(),sexo, edadI, edadF, pato));
                    if (!n.Hijo_dchoADN_Vacio())listPac.addAll(selPac(n.getHijo_dchoADN(),sexo, edadI, edadF, pato)); 
                }
                else if (edadI>edad_adn){
                    if (!n.Hijo_dchoADN_Vacio())listPac.addAll(selPac(n.getHijo_dchoADN(),sexo, edadI, edadF, pato)); 
                }
                else if (edadF<=edad_adn){
                    if (!n.Hijo_izqADN_Vacio()) listPac.addAll(selPac(n.getHijo_izqADN(),sexo, edadI, edadF, pato));
                }
             }
        }
        else if (!n.Hijo_dchoADN_Vacio())listPac.addAll(selPac(n.getHijo_dchoADN(),sexo, edadI, edadF, pato)); 
        
        return listPac;
    }
     
    
 
    /**
     * Pre:
     * @return Devuelve todos los pacientes del conjunto
     */
    public ArrayList<Paciente> listaPacientes(){
        return listPac(raizNH);
    }

    private ArrayList<Paciente> listPac(NodePac np){
        ArrayList<Paciente> pacs = new ArrayList();
        if(!pacVacio(np)){
            pacs.add(np.getPaciente());
            if (!np.Hijo_izqNH_Vacio())pacs.addAll(listPac(np.getHijo_izqNH()));
            if (!np.Hijo_dchoNH_Vacio())pacs.addAll(listPac(np.getHijo_dchoNH()));
        }
        return pacs;       
    }


    /**
     * Pre:
     * @param Pato
     */
    public void actualizaPato(String Pato) {
        actPato(Pato, raizNH);
    }


    private void actPato (String pato, NodePac n){
        ArrayList<Patologia> patsR;
        ArrayList<Patologia> patsP;
        String nomPat;
        boolean trobat=false;
        int i = 0;
        if(!pacVacio(n)){         
            patsR = n.getPaciente().getPatologiasR();
            patsP = n.getPaciente().getPatologiasP();
            while (i < patsR.size() && !trobat){          
                nomPat = patsR.get(i).getNombre();
                if (nomPat.equals(pato)){
                    patsR.remove(i);
                    trobat=true;
                }
                ++i;
            }
            
            trobat=false;
            i=0;
            while (i < patsP.size() && !trobat){          
                nomPat = patsP.get(i).getNombre();
                if (nomPat.equals(pato)){
                    patsP.remove(i);
                    trobat=true;
                }
                ++i;
            }

            if (!n.Hijo_izqNH_Vacio()) actPato(pato, n.getHijo_izqNH());
            if (!n.Hijo_dchoNH_Vacio()) actPato(pato, n.getHijo_dchoNH());
        }
    }


    /**
     * Pre:
     * @param Sint
     */
    public void actualizaSint(String Sint) {
        actSint (Sint, raizNH);
    }

    private void actSint (String sint, NodePac n){
        ArrayList<Sintoma> sintoms;
        String nomSint;
        boolean trobat=false;
        int i = 0;
        if(!pacVacio(n)){         
            sintoms = n.getPaciente().getSintomas();
            while (i < sintoms.size() && !trobat){
                nomSint = sintoms.get(i).getNombre();
                if (sint.equals(nomSint)){
                    sintoms.remove(i);
                    trobat=true;
                }
                ++i;
            }
            if (!n.Hijo_izqNH_Vacio()) actSint(sint, n.getHijo_izqNH());
            if (!n.Hijo_dchoNH_Vacio())actSint(sint, n.getHijo_dchoNH());
        }
    }



    /**
     * Pre:
     * @return Devuelve el nh de todos los pacientes del conjunto
     */
    public ArrayList<Integer> getNHtodosOrd(){
        return getRecNHtodosO (raizNH);
    }

    private ArrayList<Integer> getRecNHtodosO(NodePac n){
        ArrayList<Integer> nhs = new ArrayList();
        if(!pacVacio(n)){
           if (!n.Hijo_izqNH_Vacio()) nhs.addAll(getRecNHtodosO(n.getHijo_izqNH()));
           nhs.add(n.getPaciente().getnHistorial());
           if (!n.Hijo_dchoNH_Vacio()) nhs.addAll(getRecNHtodosO(n.getHijo_dchoNH()));
        }
        return nhs;
    }


    public ArrayList<Integer> getNHtodosDes(){
        return getRecNHtodosD (raizNH);
    }

    private ArrayList<Integer> getRecNHtodosD(NodePac n){
        ArrayList<Integer> nhs = new ArrayList();
        if(!pacVacio(n)){
           nhs.add(n.getPaciente().getnHistorial());
           if (!n.Hijo_izqNH_Vacio()) nhs.addAll(getRecNHtodosD(n.getHijo_izqNH()));
           if (!n.Hijo_dchoNH_Vacio()) nhs.addAll(getRecNHtodosD(n.getHijo_dchoNH()));
        }
        return nhs;
    } 
    
    
    public ArrayList<Integer> getPacientesPatologia(String pato){
        return getRecPacientespato (pato, raizNH);
    }
    
    
    private ArrayList<Integer> getRecPacientespato (String pato, NodePac n){
        ArrayList<Integer> nhs = new ArrayList();
        ArrayList<String> nomPatos;
        if(!pacVacio(n)){
           nomPatos = CtrlPatologia.getPatologiasNombres(n.getPaciente().getPatologiasR());
           if (nomPatos.contains(pato)) nhs.add(n.getPaciente().getnHistorial());
           if (!n.Hijo_izqNH_Vacio()) nhs.addAll(getRecPacientespato(pato, n.getHijo_izqNH()));
           if (!n.Hijo_dchoNH_Vacio()) nhs.addAll(getRecPacientespato(pato, n.getHijo_dchoNH()));
        }
        return nhs; 
    }
}
