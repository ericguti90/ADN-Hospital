
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JFileChooser;

/**
 * 
 * @author ADN
 */
public class CtrlResultado {
    
    private static CjtResultados cjres;
    private static Resultado temporal;
    private static Resultado ultimo;
    
    /**
     * pre: CjtResultados esta inicializado
     * @see el controlador tiene el nuevo CjtResultados
     * @param cjresR
     */
    public static void setCjtResultados(CjtResultados cjresR){
        if (cjresR == null){
            cjres = new CjtResultados();
            temporal = new Resultado();
            ultimo = new Resultado();
        }
        else cjres = cjresR;
    }

    public static void crearTemporal(int edadI, int edadF, char sexo, int minap, int mida, double error, String def, ArrayList<String> pat, ArrayList<String> sec, ArrayList<Patron> patrones){
        int idR = cjres.getIdMax();
        ArrayList<Patologia> pato = new ArrayList();
        pato.addAll(CtrlPatologia.getPatologias(pat));
        Calendar c1 = Calendar.getInstance();
        int any = c1.get(Calendar.YEAR);
        temporal = new Resultado(idR+1, any, edadI, edadF, sexo, minap, mida, error, def, pato, sec, patrones);
    }

    public static void insertaTemporal (){
        cjres.anadirResultado(temporal);
        CtrlPatologia.actualizarPatologiaResI(temporal.getId());
    }

    /**
     * pre: los parametros son correctos
     * @see anade un nuevo resultado a cjres
     * @param edadI
     * @param edadF
     * @param sexo
     * @param pat
     * @param vp
     */
    public static void nuevoResultado( int edadI, int edadF, char sexo, int minap, int mida, double error, String def,ArrayList<String> pat, ArrayList<String> sec, ArrayList<Patron> patr){

        int idR = cjres.getIdMax();
        ArrayList<Patologia> pato = CtrlPatologia.getPatologias(pat);
        Calendar c1 = Calendar.getInstance();
        int any = c1.get(Calendar.YEAR);
        Resultado r = new Resultado(idR+1,any, edadI, edadF, sexo, minap, mida, error, def, pato, sec, patr);
        cjres.anadirResultado(r);
    }
    

    
    /**
     * pre:
     * @return devuelve un String con todos los datos de CjtResultados con el formato id;patron;year;edadI;edadF;sexo;numP;pat1;...patN;
     */

    public static void guardaResultados(JFileChooser direc) {
        ArrayList<Resultado> res;
        ArrayList<Patologia> pato;
        ArrayList<String> secu;
        ArrayList<Patron> patr;
        boolean[] bits;
        ArrayList<Camino> cam;
        ArrayList<Integer>[] dist;
        ArrayList<Integer> d;
        Camino c;
        Patron p;
        String text = "Resultados\n";
        Resultado r;
        res = cjres.listaResultados();
        CtrlData.abrirFichero(direc);
        for( int i = 0; i < res.size(); i++ ){ 
            r = res.get(i); 
            text += Integer.toString(r.getId()) + ";" + Integer.toString(r.getFecha()) + ";" + Integer.toString(r.getEdadI()) + ";" + Integer.toString(r.getEdadF()) + ";";
            text += r.getSexo() + ";" + Integer.toString(r.getMinAp()) + ";" + Integer.toString(r.getMida()) + ";" + Double.toString(r.getError())+ ";" + r.getDef()+ ";";
            pato = r.getPatologiaR();
            text += pato.size();
            for(int j = 0; j < pato.size(); j++) text += ";" + pato.get(j).getNombre();
            secu = r.getSecuencias();
            text += ";" + Integer.toString(secu.size()) + ";";
            for(int j = 0; j < secu.size(); j++) text += secu.get(j)+ ";";
            patr = r.getPatrones();
            text += "$";   
            for(int j = 0; j < patr.size(); j++){
                p = patr.get(j);
                text += p.getPatron()+ ";";
                bits = p.getOcb();
                for (int k = 0 ; k < bits.length; k++) text += Boolean.toString(bits[k])+ ";";
                cam = p.getC();
                text += "@"; //marca final parametres del patro, abans de camino
                for (int k = 0 ; k < cam.size(); k++){
                    c = cam.get(k);
                    text += c.getCamino()+ ";";
                    text += Integer.toString(c.getError())+ ";";  
                    text += "|"; //marca que han acabat els parametres de camino, comença la matriu
                    dist = c.getOccDist();
                    for (int l = 0; l < dist.length; l++){
                        if(dist[l] == null) text += "?;|";
                        else { d = dist[l];                
                            for (int m = 0; m < d.size(); m++){
                                text += Integer.toString(d.get(m))+ ";";            
                            }
                            text += "|";
                        }
                    }
                    text += "@";
                }
                text += "$";                
            }
            text += "\n";
            if(((i%100)==0) || (i+1 == res.size())){
                CtrlData.guardarFichero(text);
                text = "";
            }
        } 
        CtrlData.cierraFichero();
    }
    

    
    /**
     * pre: r no es null
     * @see el cjres contiene el nuevo resultado
     * @param r
     */
     
 
    public static void cargarResultado (int id, int any, int edadI, int edadF, char sexo, int minap, int mida, double error, String def, ArrayList<String> pat, ArrayList<String> sec){
        ArrayList<Patologia> pato = CtrlPatologia.getPatologias(pat);
        ArrayList<Patron> patr = new ArrayList();
        Resultado r = new Resultado(id ,any, edadI, edadF, sexo, minap, mida, error, def, pato, sec, patr);
        cjres.anadirResultado(r);
        ultimo = r;
    }
     
    
    public static void anadirPatron(String seqPatron, ArrayList<Boolean> pres){
        
        boolean[] patBool = new boolean[pres.size()];
        for (int i=0; i<pres.size(); ++i){
           patBool[i]=pres.get(i);
        }
        ultimo.setPatron (seqPatron,patBool);
    }
    
    public static void anadirCamino(String nomCamino, int errorCamino, ArrayList<ArrayList<Integer>>distancias){
        ArrayList<Integer>[] occDist = new ArrayList[distancias.size()];
        for (int i=0; i<distancias.size(); ++i){
            ArrayList<Integer> aux = distancias.get(i);
            //occDist[i]= distancias.get(i);
            occDist[i]= aux;
        }        
        ArrayList<Patron> patr = ultimo.getPatrones(); // array de patrones de ultimo resultado
        Patron ultpatron = patr.get(patr.size()-1); // ultimo patron, al que hay que añadir el camino
        ultpatron.anadirCamino(nomCamino, errorCamino, occDist.length, occDist); //le añadimos el camino
        patr.remove(patr.size()-1);
        patr.add(ultpatron);
        ultimo.setPatrones(patr);
    }
    
    /**
     * pre: el array contiene resultados
     * @param resultados
     * @return devuelve un array de los patrones de los resultados
     */
    
    public static ArrayList<String> getIdS(ArrayList<Resultado> resultados){
        ArrayList<String> patrones = new ArrayList();
        //ArrayList<Patron> p = new ArrayList();
        for( int i = 0; i < resultados.size(); i++){
            patrones.add(Integer.toString(resultados.get(i).getId()));
            /*
            p.clear();
            p = resultados.get(i).getPatrones();
            for (int j = 0; i < p.size(); j++){
                patrones.add(p.get(j).getPatron());
            } 
            */
        }
        return patrones;
    }
    
    /**
     * pre: id existe en algun resultado
     * @see elimin el resultado y actualiza las patologias que lo podrían tener asignado
     * @param id
     */
    public static void eliminaResultado(int id){
        CtrlPatologia.actualizarPatologiaResE(id);
        cjres.eliminaResultado(id);
    }


    /**
     * pre: a es id de algun resultado
     * @param r
     * @return devuelve el resultado con id = r
     */
    
    public static ArrayList<String> getResultado(int r){
        Resultado res = cjres.getResultado(r);
        ArrayList<String> s = new ArrayList();
        if(res != null) {
            s.add(Integer.toString(res.getId()));
            s.add(Integer.toString(res.getFecha()));
            s.add(Integer.toString(res.getEdadI()));
            s.add(Integer.toString(res.getEdadF()));
            s.add(String.valueOf(res.getSexo()));
            s.add(Integer.toString(res.getMinAp()));
            s.add(Integer.toString(res.getMida()));
            s.add(Double.toString(res.getError()));
            s.add(res.getDef());
            
            ArrayList<Patologia> patos = res.getPatologiaR();
            s.add(String.valueOf(patos.size()));
            if(!patos.isEmpty()){
                for (int i= 0; i< patos.size(); ++i) s.add(patos.get(i).getNombre());
            }
            
            ArrayList<String> secs = res.getSecuencias();
            s.add(String.valueOf(secs.size()));
            if(!secs.isEmpty()) s.addAll(secs);
            
            ArrayList<Patron> patr = res.getPatrones();
            Patron p;
            int midaP;
            boolean[] bits;
            ArrayList<Camino> cam;
            Camino c;
            ArrayList<Integer>[] matriz;
            ArrayList<Integer> dist;
            s.add(String.valueOf(patr.size()));
            if (!patr.isEmpty()){
                for (int i = 0; i < patr.size(); ++i){  //para cada patron del array
                    p = patr.get(i);    //guardo el patron
                    s.add(p.getPatron());
                    bits = p.getOcb();
                    midaP = bits.length;
                    s.add(String.valueOf(midaP));
                    for(int j = 0; j < bits.length ; ++j){
                        s.add(String.valueOf(bits[j]));
                    }
                    cam = p.getC(); //array de caminos
                    s.add(String.valueOf(cam.size()));
                    for (int j=0; j< cam.size(); ++j){  //para cada camino
                        c = cam.get(j);
                        s.add(c.getCamino());
                        s.add(String.valueOf(c.getError()));
                        matriz = c.getOccDist();
                        s.add(String.valueOf(matriz.length));
                        for (int k = 0; k < matriz.length; ++k){
                            dist = matriz[k];
                            s.add(String.valueOf(dist.size()));
                            for (int l=0; l< dist.size(); ++l){
                                s.add(String.valueOf(dist.get(l)));
                            }
                        }
                    }
                }
            }
        }
        return s;
    }

    
    
    public static Resultado getRes(int idR){
        return cjres.getResultado(idR);
    }
    
    /**
     * Pre:
     * @return Retorna el id de todos los resultados del conjunto
     */
    public static ArrayList<Integer> getIDtodos(){
        return cjres.getIDtodos();
    }

    /**
     * pre: 
     * @param res
     * @return devuelve el id de todos los resultados que se han pasado por parametro
     */
    public static ArrayList<Integer> getID(ArrayList<Resultado> res){
        ArrayList<Integer> p = new ArrayList();
        for(int i = 0; i < res.size(); ++i) {
            p.add(res.get(i).getId());
        }
        return p;
    }
    
    
    private static String colorea( String s ){
        
        String colorS = "";
        for (char c : s.toCharArray() ) {
            colorS += "<span id=\""+ c +"\">"+ c + "</span>";
        }
        return colorS;
    }
    
    
    
    public static String mostrarResultado(int id){
        Resultado res = cjres.getResultado(id);  
        
        /*
        ArrayList<Patologia> pato = new ArrayList();
        pato.add(CtrlPatologia.get1Patologia("aaa"));
        
        ArrayList<String> sec = new ArrayList();
        sec.add("AAAAAA");
        ArrayList<Patron> patr = new ArrayList();
        Patron pattron = new Patron (1);
        patr.add(pattron);
        Resultado res = new Resultado(1 ,1990, 0, 110, 'm', 3, 5, 1, "estandar", pato, sec, patr);
        //int idR, int fechaR, int edadIR, int edadFR, char sexoR, int minap, int mida, double error, String defi, ArrayList<Patologia> patologiaRR, ArrayList<String> sec, ArrayList<Patron> patronesR
        
         * 
         */
        ArrayList<Patron> lp = res.getPatrones();
        ArrayList<String> ls = res.getSecuencias();

        String m = Integer.toString(res.getMida());
        String e = Double.toString(res.getError());
        String q = Integer.toString(res.getMinAp());

        String sexo = "";
        char sx= res.getSexo();
        switch (sx) {
            case 'x': sexo = "Ambos"; break;
            case 'f': sexo = "Mujer"; break;
            case 'm': sexo = "Hombre";break;
            default: break;
        }   

        String t = "";
        
        ArrayList<String> pato = CtrlPatologia.getPatologiasNombres(res.getPatologiaR());
        
        //String pa = "patologia de prueba";
        String pa = pato.get(0);
        
        t += "<body>" + "<table cellspacing=\"0\"; >"
          + "<tr><th colspan=\"4\" class=\"tituloT\">Experimento Estandard Nº "+ res.getId() +" ("+ res.getFecha()+")</th></tr>"
          + "<tr><th class=\"thInfo\" width=\"40%\">Patología</th><td width=\"10%\">"+ pa;
        t += "</td><th class=\"thInfo\" width=\"15%\">Longitud</th><td width=\"40%\">"+ m +"</td></tr>"
          + "<tr><th class=\"thInfo\">Edad min.</th><td>"+ res.getEdadI() +"</td><th class=\"thInfo\">Error</th><td>"+ String.valueOf(Double.parseDouble(e)*100) +" %</td></tr>"
          + "<tr><th class=\"thInfo\">Edad max.</th><td>"+ res.getEdadF() +"</td><th class=\"thInfo\">Min. Apariciones</th><td>"+ q +"</td></tr>"
          + "<tr><th class=\"thInfo\">Sexo</th><td>"+ sexo +"</td></tr>"
          + "</table>"
          + "<table cellspacing=\"0\"; cellpadding=\"0\" >"
          + "<tr><th colspan=\"2\" class=\"tituloT\">Patrones encontrados &nbsp;&nbsp;( "+lp.size()+" )</th></tr>";
        

        ArrayList<Integer> lds;
        int aux;
        String sa;
        for ( Patron p : lp ) {

            int numCaminos = 0;
            ArrayList<Camino> lc = p.getC();
            Camino cmt;
            for (int i = 0; i < lc.size(); i++) {
                cmt = lc.get(i);
                for (ArrayList<Integer> ld : cmt.getOccDist() ) {
                    if( ld != null ) numCaminos += ld.size();
                }
            }
            t += "<tr><th class=\"thPat\" >&nbsp;&nbsp;"+p.getPatron()+" &nbsp;&nbsp;&nbsp;&nbsp;( "+ numCaminos +" )</th><th class=\"thPat\" align=\"right\" style=\"padding-right:8px;\">Mutaciones</th></tr>";
            
            for ( Camino c : p.getC() ) {
                
                for (int i = 0; i < c.getOccDist().length; i++) {
                 
                    lds = c.getOccDist()[i];
                    sa = ls.get(i);
                    if( lds != null ){
                        for (int j = 0; j < lds.size(); j++) {

                            aux = sa.length()-lds.get(j);
                            t += "<tr><td class=\"tdNormalFlojo\"><span class=\"spanCont\">&nbsp;&nbsp;"+i+". </span>" +sa.substring( 0, aux) + colorea( c.getCamino() ) + sa.substring( aux+c.getCamino().length(), sa.length()) + "</td>"
                            + "<td align=\"right\" style=\"padding-right:8px;\">"+c.getError()+"</td></tr>";
                        }
                    }
                }
            }
        } 
        return t;
    }
}