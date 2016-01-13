
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 *
 * @author ADN
 */
public class CtrlPatologia {
    
    
    private static CjtPatologia cjpat;
    private static boolean iniciado;
    
    /**
     * Asigna cjp como el CjtPatologia
     * @param cjp
     */
    public static void setCjtPatologia(CjtPatologia cjp){
        iniciado = false;
        if (cjp == null) cjpat = new CjtPatologia();
        else cjpat = cjp;
    }
    
    
    /**
     * Pre: parametros correctos, conjunto paciente inicializado
     * @param nombre
     * @param descrip
     * @param PatologiasR
     * @param SintomasR
     * @return devuelve true si ha introducido la patologia, false lo contrario
     */
    public static boolean introducirPatologia(String nombre, String descrip, ArrayList<String> PatologiasR,ArrayList<String> SintomasR){
        if (cjpat.ExistePatologia(nombre)) return false;
        ArrayList<Sintoma> sin = CtrlSintoma.getSintomaConjunto(SintomasR);
        ArrayList<Patologia> p = getPatologias(PatologiasR);
        ArrayList<Resultado> res = new ArrayList();
        Patologia pat = new Patologia(nombre, descrip, null, p, sin, res);
        cjpat.InsercionPatologia(pat);
        iniciado = true;
        return true;
    }
    
    /**
     * Pre: El conjunto debe existir
     * @param nombre
     * @return Devuelve la patologia pedida
     */
    public static Patologia get1Patologia(String nombre) {
        return cjpat.getPatologia(nombre);
    }

    /**
     * Pre: El conjunto debe de existir
     * @param p
     * @return Devuelve un array con los la patologia
     */
    public static ArrayList<String> getPatologia(String p){
        Patologia pat = cjpat.getPatologia(p);
        ArrayList<String> s = new ArrayList();
        if(pat != null) {
            s.add(pat.getNombre());
            s.add(pat.getDescripcion());
            String aux = pat.getGeneralizada();
            if (aux == null) s.add("no_existe");
            else s.add(aux);
            ArrayList<String> pato = getPatologiasNombres(pat.getPatologiasR());
            s.add(String.valueOf(pato.size()));
            if(!pato.isEmpty()) s.addAll(pato);
            ArrayList<String> sint = CtrlSintoma.getSintoma(pat.getSintomas());
            s.add(String.valueOf(sint.size()));
            if(!sint.isEmpty())s.addAll(sint);
            ArrayList<String> res = CtrlResultado.getIdS(pat.getResultados());
            s.add(String.valueOf(res.size()));
            if(!res.isEmpty())s.addAll(res);
        }
        return s;
    }
    
    /**
     * Pre: El conjunto debe de estar inicializado
     * @param nombres
     * @return Devuelve un array de patologias
     */
    public static ArrayList<Patologia> getPatologias(ArrayList<String> nombres) {
        ArrayList<Patologia> p = new ArrayList();
        for(int i= 0; i < nombres.size(); ++i){
            if(cjpat.ExistePatologia(nombres.get(i))) p.add(cjpat.getPatologia(nombres.get(i)));
        }
        return p;
    }
       
    /**
     * Pre: array de conjunto valida
     * @param cjtpat
     * @return Devuelve array con los nombres de las patologias
     */
    public static ArrayList<String> getPatologiasNombres( ArrayList<Patologia> cjtpat ){
        ArrayList<String> p = new ArrayList();
        for(int i = 0; i < cjtpat.size(); ++i) {
            p.add(cjtpat.get(i).getNombre());
        }
        return p;
    }

    public static ArrayList<Resultado> getResultados(String nombre){
        return cjpat.getPatologia(nombre).getResultados();
    }

    /**
     * Pre: La patologia debe de existir
     * @see Post: inserta la patologia modificada
     * @param nombre
     * @param descrip
     * @param pato
     * @param sin
     */
    public static void modificarPatologia(String nombre, String descrip, ArrayList<String> pato,ArrayList<String> sin){
        ArrayList<Patologia> p = getPatologias(pato);
        ArrayList<Sintoma> s = CtrlSintoma.getSintomaConjunto(sin);
        Patologia pat = cjpat.getPatologia(nombre);
        cjpat.ModificarPatologia(nombre, descrip, pat.getGeneralizada(), p, s, pat.getResultados());
    }
    
    /**
     * Pre: parametro valido
     * @see Post: Devuelve true si se ha eliminado, false si no.
     * @param p
     * @return
     */
    public static boolean eliminarPatologia(String p){
        if(!cjpat.ExistePatologia(p)) return false;
        cjpat.EliminarPatologia(p);
        return true;
    }
    
    /**
     * Pre: nombre del sintoma valido, array de patologias valido
     * @see: actualiza la relacion entre sintoma y patologia
     * @param nombre
     * @param pat
     */
    public static void actualizar_patologiaI(String nombre,ArrayList<String> pat){
        Sintoma s = CtrlSintoma.get1Sintoma(nombre);
        for(int i = 0; i < pat.size();++i){
            Patologia paux = cjpat.getPatologia(pat.get(i));
            if (paux != null) {
                ArrayList<Sintoma> aux = paux.getSintomas();
                aux.add(s);
                cjpat.ModificarPatologia(paux.getNombre(), paux.getDescripcion(), paux.getGeneralizada(),
                    paux.getPatologiasR(), aux, paux.getResultados());
            }
        }
    }
    
    /**
     * Pre: nombre del sintoma valido, array de patologias valido
     * @see Post: elimina la relacion entre sintoma y patologia
     * @param nombre
     * @param pat
     */
    public static void actualizar_patologiaE(String nombre,ArrayList<String> pat){
        Sintoma s = CtrlSintoma.get1Sintoma(nombre);
        for(int i = 0; i < pat.size();++i){
            Patologia paux = cjpat.getPatologia(pat.get(i));
            ArrayList<Sintoma> aux = paux.getSintomas();
            aux.remove(s);
            cjpat.ModificarPatologia(paux.getNombre(), paux.getDescripcion(), paux.getGeneralizada(),
                    paux.getPatologiasR(), aux, paux.getResultados());
        }
    }
    
    /**
     * Pre: nombre de la patologia valido y array de patologias valido
     * @see Post: actualiza la relacion entre patologia y patologias relacionadas
     * @param nombre
     * @param pato
     */
    public static void actualizar_patologiaPI(String nombre, ArrayList<String> pato){
        Patologia p = cjpat.getPatologia(nombre);
        for(int i = 0; i < pato.size();++i){
            Patologia paux = cjpat.getPatologia(pato.get(i));
            if (paux != null) {
                ArrayList<Patologia> aux = paux.getPatologiasR();
                aux.add(p);
                cjpat.ModificarPatologia(paux.getNombre(),paux.getDescripcion(), paux.getGeneralizada(), aux,
                    paux.getSintomas(), paux.getResultados());
            }
        }
    }
    
    /**
     * Pre: nombre de la patologia valido y array de patologias valido
     * Post: elimina la relacion entre patologias y patologias relacionadas
     * @param nombre
     * @param pato
     */
    public static void actualizar_patologiaPE(String nombre, ArrayList<String> pato){
       Patologia p =  cjpat.getPatologia(nombre);
       for(int i = 0; i < pato.size();++i){
           Patologia paux = cjpat.getPatologia(pato.get(i));
           ArrayList<Patologia> aux = paux.getPatologiasR();
           aux.remove(p);
           cjpat.ModificarPatologia(paux.getNombre(), paux.getDescripcion(), paux.getGeneralizada(),
                    aux, paux.getSintomas(), paux.getResultados());
       }
    }

    /**
     * Pre: Conjunto Patologia declarado
     * @see Post: Devuelve true si existe la patologia, false si no existe
     * @param nombre
     * @return
     */
    public static boolean existe_patologia(String nombre) {
        return cjpat.ExistePatologia(nombre);
    }

    /**
     * Pre: nombre de la patologia existe
     * @see Post: devuelve un array de los sintomas que tiene la patologia
     * @param nombre
     * @return
     */
    public static ArrayList<Sintoma> getSintoma(String nombre) {
        return cjpat.getPatologia(nombre).getSintomas();
    }


    /**
     * Pre: nombre de la patologia existe
     * @see Post: devuelve un array con las patologias relacionadas de la patologia
     * @param nombre
     * @return
     */
    public static ArrayList<Patologia> getPatologiaR(String nombre) {
        return cjpat.getPatologia(nombre).getPatologiasR();
    }
    

/*
   public static void potenciales( String p ){
        
        String gen = cjpat.getPatologia(p).getGeneralizada();
        ArrayList<String> similares = seleccionarSimilares(gen);
        CtrlPresentation.muestraSimilares(similares);
    }
    */


   public static ArrayList<String> Potenciales(int nh){
       return cjpat.Potenciales();
   }

   /*public static boolean relacionadas (String ristra1, String ristra2){
       int m;
       if (ristra1.length()< ristra2.length()) m= ristra1.length();
       else m = ristra2.length();
       int q = 2;
       int e= 0;        //como lo calculamos??
       return CtrlAlgoritmo.calcularPotenciales( ristra1, ristra2, m, e, q);
    }

   public static void compara2(String pat1, String pat2){
       Patologia p1 = cjpat.getPatologia(pat1);
       Patologia p2 = cjpat.getPatologia(pat2);
       
       String gen1 = p1.getGeneralizada();
       String gen2 = p2.getGeneralizada();
       if (gen1 != null && gen2 != null){
           int m;
           if (gen1.length()< gen1.length()) m= gen1.length();
           else m = gen1.length();
           int q = 2;
           int e= 0;        //como lo calculamos??
           CtrlAlgoritmo.calcularPotenciales(gen1, gen1, m, e, q);   //otro metodo que devuelva qué tienen en común
       }                                                              //cual es la m??
       
       ArrayList <Patologia> patR1 = p1.getPatologiasR();
       ArrayList <Patologia> patR2 = p2.getPatologiasR();
       ArrayList <String> pR_comun = new ArrayList();
       Patologia paux;
       for (int i=0; i<patR1.size(); ++i){
           paux = patR1.get(i);
           if (patR2.contains(paux)) pR_comun.add(paux.getNombre());
       }
       
       ArrayList <Sintoma> s1 = p1.getSintomas();
       ArrayList <Sintoma> s2 = p2.getSintomas();
       ArrayList <String> s_comun = new ArrayList();
       Sintoma saux;
       for (int i=0; i<s1.size(); ++i){
           saux = s1.get(i);
           if (s2.contains(saux)) s_comun.add(saux.getNombre());
       }
       
       
   }*/
   
   
    
   /*public static void ristraGeneralizada( String p ){
        
        String generalizada = getRistraGeneralizada( p );
        CtrlPresentation.escribirGeneralizada( generalizada );
    }*/
   /**
    * Pre: patologia existe
    * Post: inserta ristra generalizada a la patologia
    * @param nombre
    * @param ristra
    */
   public static void setRistraGeneralizada( String nombre, String ristra ){
       cjpat.getPatologia(nombre).setGeneralizada(ristra);
   }
   
   /**
    * Pre: patologia existe
    * @param p
    * @return Post: devuelve la ristra generalizada de la patologia
    */
   public static String getRistraGeneralizada( String p){
        
        return cjpat.getPatologia(p).getGeneralizada();
    }
	

   /**
    * 
    * @return
    */
   public static void guardaPatologias(JFileChooser direc) {
        ArrayList<Patologia> pat;
        ArrayList<Patologia> patUsado = new ArrayList();
        String text = "Patologias\n";
        Patologia p;
        pat = cjpat.listarPatologia();
        CtrlData.abrirFichero(direc);
        for( int i = 0; i < pat.size(); i++ ){ //Esquema por Patologia: Nombre;Descripcion\n
            p = pat.get(i);
            text += p.getNombre();
            text += ";" + p.getDescripcion();
            if(p.getGeneralizada() != null) text += ";" + p.getGeneralizada();
            text += "\n";
            if(((i%100)==0) || (i+1 == pat.size())){
                CtrlData.guardarFichero(text);
                text = "";
            }
        }
        // Relaciones: Esquema: Rel:;Patologia1;Patologia2\n
        for( int i = 0; i < pat.size(); i++ ){
            p = pat.get(i);
            for( int j = 0; j < p.getPatologiasR().size(); j++){
                if(noEsta(patUsado, p.getPatologiasR().get(j))) text += "Rel:;" + p.getNombre() + ";" + p.getPatologiasR().get(j).getNombre() + "\n";
            }
            patUsado.add(p);
            if(((i%100)==0) || (i+1 == pat.size())){
                CtrlData.guardarFichero(text);
                text = "";
            }
        } 
        CtrlData.cierraFichero();
}




   private static boolean noEsta(ArrayList<Patologia> patUsado, Patologia p){
                                                                                System.out.println("USEM: " + p.getNombre());
        for( int i = 0; i < patUsado.size(); i++ ){
                                                                                System.out.println("Comparem amb : " + patUsado.get(i).getNombre());
            if( patUsado.get(i).getNombre().equals(p.getNombre())) return false;
        }
                                                                                System.out.println("NO HI ERA");
        return true;
    }

   /**
    * 
    * @param r
    */
   public static void actualizarPatologiaResI(int idRes) {
       Resultado r = CtrlResultado.getRes(idRes);
       ArrayList<Patologia> paux = r.getPatologiaR();
       Patologia pato;
       ArrayList<Resultado> aux; 
       for (int i=0; i<paux.size(); ++i){
           pato = paux.get(i);
           aux = pato.getResultados();
           aux.add(r);
           pato.setResultados(aux);
       }
    }

   /**
    * 
    * @param r
    */
   public static void actualizarPatologiaResE(int idRes) {
       Resultado r = CtrlResultado.getRes(idRes);
       ArrayList<Patologia> paux = r.getPatologiaR();
       Patologia pato;
       ArrayList<Resultado> aux;
       for (int i=0; i<paux.size(); ++i){
           pato = paux.get(i);
           aux = pato.getResultados();
           aux.remove(r);
           pato.setResultados(aux);
       }
    }

   /**
    * Pre: Patologia existente
    * @return Devuelve las ristras de una patologia
    */
   public static ArrayList<String> getRistras(){
        ArrayList<Patologia> aux = cjpat.listarPatologia();
        ArrayList<String> ristras = new ArrayList();
        for (int i = 0; i < aux.size(); ++i) {
            if (aux.get(i).getGeneralizada() != null){
                ristras.add(aux.get(i).getGeneralizada());
            }
        }
        return ristras;
    }


   /**
    * Pre: array de nombres de patologias existentes
    * @param nombres
    * @return Post: devuelve todas las ristras de las patologias
    */
   public static ArrayList<String> getRistras(ArrayList<String> nombres) {
       ArrayList<String> aux = new ArrayList();
       for (int i = 0; i < nombres.size(); ++i){
           if (cjpat.getPatologia(nombres.get(i)).getGeneralizada() != null){
                aux.add(cjpat.getPatologia(nombres.get(i)).getGeneralizada());
           }
       }
       return aux;
   }


   /**
    * Pre:
    * @return Post: devuelve todos los nombres de las patologias
    */
   public static ArrayList<String> getNombres() {
        ArrayList<Patologia> aux = cjpat.listarPatologia();
        ArrayList<String> nombres = new ArrayList();
        for (int i = 0; i < aux.size(); ++i) {
            nombres.add(aux.get(i).getNombre());
        }
        return nombres;
    }


   /* public static ArrayList<String> similares(String nomp){
        ArrayList<String> lpatos = new ArrayList();
        Patologia pat = cjpat.getPatologia(nomp);
        if (pat.getGeneralizada()!= null ) lpatos = cjpat.similares(pat);
        ArrayList<Patologia> listp= new ArrayList();
        Patologia paux;
        for (int i= 0; i<lpatos.size(); ++i){
            paux = cjpat.getPatologia(lpatos.get(i));
            listp.add(paux);
        }
        pat.setPatologiasR(listp);

        return lpatos;
    }*/
   
   public static void insertaRelacion(String a, String b){
       if (cjpat.ExistePatologia(a) && cjpat.ExistePatologia(b)){
           cjpat.getPatologia(a).set1PatologiaR(cjpat.getPatologia(b));
           cjpat.getPatologia(b).set1PatologiaR(cjpat.getPatologia(a));
       } 
   }

    public static boolean existenPatologias() {
        return iniciado;
    }
   
   
   
}
