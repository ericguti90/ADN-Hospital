import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;


/**
 * Controlador Algoritmo. Su función principal es comunicar la clase algoritmo, con las demás partes del sistema, ejecutandi así todas sus operaciones.
 * 
 * @author ADN
 */
public class CtrlAlgoritmo {
    
    private static Algoritmo a;
    
    /**
    * Se encarga de invocar las operaciones necesárias de la clase Algoritmo para realizar un experimento.
    * Recibe los parámetros para realizar la selección de los pacientes y patologia pertinentes.
    *
    * @param edadI edad inicial del rango de edades a considerar
    * @param edadF edad final del rango de edades a considerar
    * @param nh numerho de historial del paciente
    * @param sexo sexo ( m / f / x )
    * @param pat nombre de la patologia
    * @param nhp ArrayList de numeros de historiales de pacientes
    * @param vpat ArrayList de nombres de patologias
    * @param opcion tipo de experimento
    * @param m mida de las ristras
    * @param e error permitido
    * @param q numero mínimo de apariciones
    */
    public static void experimentoEstandard( ArrayList<String> ls, int m, double e, int q, int eI, int eF, char sexo, String p ){
        
        a = new Algoritmo( m, e, q );
        a.AddAllLsIN( ls );
        a.generarTrie();
        
        Date data = new Date(); Calendar c = Calendar.getInstance(); c.setTime(data); long inici = c.getTimeInMillis();
        a.experimentoEstandard();
        Date fi = new Date(); c.setTime(fi); String t = String.valueOf( (c.getTimeInMillis() - inici) ) + "ms";
        
        mostrarResultadoExperimentoEstandard( a.getlsPatrones(), m, e, q, t, eI, eF, sexo, p, ls );
        
        ArrayList<String> lp = new ArrayList(); lp.add(p);
        CtrlResultado.crearTemporal(eI, eF, sexo, q, m, e, "Experimento Estandard", lp, ls, a.getlsPatrones()  );
    }
    public static void experimentoGeneralizada (ArrayList<String> ls, int m, double e, int q, String p ){
        
        a = new Algoritmo( m, e, q );
        a.AddAllLsIN( ls );
        a.generarTrie();
        
        Date data = new Date(); Calendar c = Calendar.getInstance(); c.setTime(data); long inici = c.getTimeInMillis();
        a.experimentoEstandard();
        Date fi = new Date(); c.setTime(fi); String t = String.valueOf( (c.getTimeInMillis() - inici) ) + "ms";// System.out.println("temps: " + (c.getTimeInMillis() - inici) + " milisegons"); 
        
        //Ponemos en la primera posición la más generalizada
        Patron p1, p2;
        ArrayList<Patron> lp;
        lp = a.getlsPatrones();
        for (int i = 0; i < lp.size(); i++) {
            
            if( (i+1) < lp.size() ){
                
                p1 = lp.get(0); p2 = lp.get(i+1);
                
                if( ( p1.getNumSec() < p2.getNumSec()  ) ||
                    ( p1.getNumSec() == p2.getNumSec() ) && ( p1.getNumApCaminos() < p2.getNumApCaminos() ) ||
                    ( p1.getNumSec() == p2.getNumSec() ) && ( p1.getNumApCaminos() == p2.getNumApCaminos()) && ( p1.getNumError() > p2.getNumError()) 
                  ){ 
                    Collections.swap(lp, 0, i+1);
                }
            }
        }
        if(!lp.isEmpty()) CtrlPatologia.setRistraGeneralizada( p, lp.get(0).getPatron() );
        
        mostrarResultadoExperimentoGeneralizada( a.getlsPatrones(), m, e, q, t, p, ls );
    }
    public static void experimentoPotencial( int nh, double e ){
        
            
        String adn = CtrlPaciente.get1Adn( nh );
        ArrayList<String> p = CtrlPaciente.getPaciente(nh);
        ArrayList<String> lpg = CtrlPatologia.Potenciales( nh );
        
        ArrayList<String> lp  = new ArrayList();
        ArrayList<String> lg  = new ArrayList();
        ArrayList< ArrayList<Patron> > lpt = new ArrayList();
        for ( int i = 0; i< lpg.size(); i=i+2 ){ lp.add( lpg.get(i) );   lg.add( lpg.get(i+1) ); }
        
        int m = 0;
        int q = 1;
        a = new Algoritmo( m, e, q );
        a.addLsIN( adn );
        a.generarTrie();
        
        Date data = new Date(); Calendar c = Calendar.getInstance(); c.setTime(data); long inici = c.getTimeInMillis();
        for (int i=0; i<lp.size(); i++ ){
            
            a.setM( lg.get(i).length() );
            a.experimentoPotencial( lg.get(i) );
            
            if( a.getlsPatrones().isEmpty() ){ lp.remove(i); lg.remove(i); i--; }
            else{
                lpt.add( (ArrayList<Patron>)a.getlsPatrones().clone() );
                a.deleteLsPatrones();
            }
        }
        Date fi = new Date(); c.setTime(fi); String t = String.valueOf( (c.getTimeInMillis() - inici) ) + "ms";
        
        if( !lp.isEmpty() )CtrlPaciente.setPotenciales( lp, nh );
        
        int edad = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt( p.get(4) );
        mostrarResultadoExperimentoPotencial( e, t, adn, nh, p.get(0), p.get(1), p.get(2), p.get(3), edad, lp, lpt, lg  );
    }
    public static void experimentoPatronPersonalizado( int nh, double e, String pi ){
        
            
        String adn = CtrlPaciente.get1Adn( nh );
        ArrayList<String> p = CtrlPaciente.getPaciente(nh);
        
        int m = pi.length();
        int q = 1;
        a = new Algoritmo( m, e, q );
        a.addLsIN( adn );
        a.generarTrie();
        
        Date data = new Date(); Calendar c = Calendar.getInstance(); c.setTime(data); long inici = c.getTimeInMillis();
        a.experimentoPotencial( pi );
        Date fi = new Date(); c.setTime(fi); String t = String.valueOf( (c.getTimeInMillis() - inici) ) + "ms";
        
        int edad = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt( p.get(4) );
        mostrarResultadoExperimentoPatronPersonalizado( e, t, adn, nh, p.get(0), p.get(1), p.get(2), p.get(3), edad, a.getlsPatrones(), pi  );
    }
    
    public static void mostrarResultadoExperimentoEstandard( ArrayList<Patron> lp, int mIN, double eIN, int qIN, String tiempo, int eI, int eF, char sx, String pat, ArrayList<String> ls ) {
        
        String m = String.valueOf(mIN);
        String e = String.valueOf(eIN);
        String q = String.valueOf(qIN);

        String sexo = "";
        switch (sx) {
            case 'x': sexo = "Ambos"; break;
            case 'f': sexo = "Mujer"; break;
            case 'm': sexo = "Hombre";break;
            default: break;
        }
        String t = "";
        
        t +="<body>"
          + "<table cellspacing=\"0\"; >"
          + "<tr><th colspan=\"4\" class=\"tituloT\">Experimento Estandard</th></tr>"
          + "<tr><th class=\"thInfo\" width=\"40%\">Patología</th><td width=\"10%\">"+ pat +"</td><th class=\"thInfo\" width=\"15%\">Longitud</th><td width=\"40%\">"+ m +"</td></tr>"
          + "<tr><th class=\"thInfo\">Edad min.</th><td>"+ eI +"</td><th class=\"thInfo\">Error</th><td>"+ String.valueOf(Double.parseDouble(e)*100) +" %</td></tr>"
          + "<tr><th class=\"thInfo\">Edad max.</th><td>"+ eF +"</td><th class=\"thInfo\">Min. Apariciones</th><td>"+ q +"</td></tr>"
          + "<tr><th class=\"thInfo\">Sexo</th><td>"+ sexo +"</td></tr>"
          + "</table>"
          + "<table cellspacing=\"0\"; cellpadding=\"0\" >"
          + "<tr><th colspan=\"2\" class=\"tituloT\">Patrones comunes encontrados &nbsp;&nbsp;( "+lp.size()+" ) en "+tiempo+"</th></tr>";
        
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
        CtrlPresAlgoritmo.mostrarResultadoExperimentoEstandard( t );
    }
    public static void mostrarResultadoExperimentoGeneralizada( ArrayList<Patron> lp, int mIN, double eIN, int qIN, String tiempo, String pat, ArrayList<String> ls ) {
        
        String m = String.valueOf(mIN);
        String e = String.valueOf(eIN);
        String q = String.valueOf(qIN);
       
        String t = "";
        
        t +="<body>"
          + "<table cellspacing=\"0\"; >"
          + "<tr><th colspan=\"2\" class=\"tituloT\">Experimento: Ristra Generalizada</th></tr>"
          + "<tr><th class=\"thInfo\">Patología</th><td>"+ pat +"</td></tr>"
          + "<tr><th class=\"thInfo\">Num. Pacientes</th><td>"+ ls.size() +"</td></tr>"
          + "<tr><th class=\"thInfo\">Longitud</th><td>"+ m +"</td></tr>"
          + "<tr><th class=\"thInfo\">Min. Apariciones</th><td>"+ q +"</td></tr>"
          + "<tr><th class=\"thInfo\">Error</th><td>"+ String.valueOf(Double.parseDouble(e)*100) +" %</td></tr>"
          + "</table>"
          + "<table cellspacing=\"0\"; cellpadding=\"0\" >"
          + "<tr><th colspan=\"2\" class=\"tituloT\">Ristras Candidatas detectadas &nbsp;&nbsp;( "+lp.size()+" ) en "+tiempo+"</th></tr>";
        
        ArrayList<Integer> lds;
        int aux;
        String sa;
        boolean primero = true;
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
            if(primero){ t += "<tr><th class=\"thPat\" style=\"background-color:#04B404;\" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+p.getPatron()+" &nbsp;&nbsp;&nbsp;&nbsp;( "+ numCaminos +" ) &nbsp;&nbsp;&nbsp;&nbsp; Seleccionada</th><th class=\"thPat\" align=\"right\" style=\"padding-right:8px; background-color:#04B404;\">Mutaciones</th></tr>"; primero = false; }
            else         t += "<tr><th class=\"thPat\" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+p.getPatron()+" &nbsp;&nbsp;&nbsp;&nbsp;( "+ numCaminos +" )</th><th class=\"thPat\" align=\"right\" style=\"padding-right:8px;\">Mutaciones</th></tr>";
            
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
        CtrlPresAlgoritmo.mostrarResultadoExperimentoGeneralizada( t );
    }
    public static void mostrarResultadoExperimentoPotencial(  double eIN, String tmp, String adn, int nh, String nombre, String ap1, String ap2, String sx, int edad, ArrayList<String> lp, ArrayList< ArrayList<Patron> > lpt, ArrayList<String> lg  ){

        
        String e = String.valueOf(eIN);
        ArrayList<Camino> lc;
        ArrayList<Integer> ld;
        int aux;
        
        String sexo;
        switch ( sx.charAt(0) ) {
            case 'f': sexo = "Mujer";       break;
            case 'm': sexo = "Hombre";      break;
            default:  sexo = "Desconocido"; break;
        }
        
        String t = "";
        
        t +="<body>"
          + "<table cellspacing=\"0\"; >"
          + "<tr><th colspan=\"2\" class=\"tituloT\">Experimento: Patologias Potenciales</th></tr>"
          + "<tr><th class=\"thInfo\">Paciente</th><td>"+ nombre +" "+ ap1 +" "+ ap2 +"</td></tr>"
          + "<tr><th class=\"thInfo\">Nº Historial</th><td>"+ nh +"</td></tr>"
          + "<tr><th class=\"thInfo\">Sexo</th><td>"+ sexo +"</td></tr>"
          + "<tr><th class=\"thInfo\">Edad</th><td>"+ edad +"</td></tr>"
          + "<tr><th class=\"thInfo\">*Error</th><td>"+ String.valueOf(Double.parseDouble(e)*100) +" %</td></tr>"
          + "</table>"
          + "<table cellspacing=\"0\"; cellpadding=\"0\" >"
          + "<tr><th colspan=\"2\" class=\"tituloT\">Patologias Potenciales Detectadas &nbsp;&nbsp;( "+lp.size()+" ) en "+tmp+"</th></tr>";
        
        for (int i = 0; i < lp.size(); i++) {
            
            t += "<tr><th class=\"thPat\" >"+lp.get(i)+": &nbsp;&nbsp;&nbsp;&nbsp;"+ lg.get(i) +"</th><th class=\"thPat\" align=\"right\" style=\"padding-right:8px;\">Mutaciones</th></tr>";
                  
            for ( int j = 0; j < lpt.get(i).size(); j++ ){
                
                lc = lpt.get(i).get(j).getC();
                for ( Camino c : lc ) {
                    
                    ld = c.getOccDist()[0];
                    
                    for(int k=0; k < ld.size(); k++ ){
                       aux = adn.length()-ld.get(k);
                       t += "<tr><td class=\"tdNormalFlojo\">" +adn.substring( 0, aux) + colorea( c.getCamino() ) + adn.substring( aux+c.getCamino().length(), adn.length()) + "</td>"
                         + "<td align=\"right\" style=\"padding-right:8px;\">"+c.getError()+"</td></tr>";
                    }
                }                
            }
        }
        t += "</table>";
        t += "</body>";
        CtrlPresAlgoritmo.mostrarResultadoExperimentoPotencial( t );
    }
    public static void mostrarResultadoExperimentoPatronPersonalizado(  double eIN, String tmp, String adn, int nh, String nombre, String ap1, String ap2, String sx, int edad, ArrayList<Patron> lpt, String pi  ){

        
        String e = String.valueOf(eIN);
        ArrayList<Camino> lc;
        ArrayList<Integer> ld;
        int aux;
        
        String sexo;
        switch (sx.charAt(0)) {
            case 'f': sexo = "Mujer";       break;
            case 'm': sexo = "Hombre";      break;
            default:  sexo = "Desconocido"; break;
        }
        
        String t = "";
        
        t +="<body>"
          + "<table cellspacing=\"0\"; >"
          + "<tr><th colspan=\"2\" class=\"tituloT\">Experimento: Patrón Personalizado</th></tr>"
          + "<tr><th class=\"thInfo\">Paciente</th><td>"+ nombre +" "+ ap1 +" "+ ap2 +"</td></tr>"
          + "<tr><th class=\"thInfo\">Nº Historial</th><td>"+ nh +"</td></tr>"
          + "<tr><th class=\"thInfo\">Sexo</th><td>"+ sexo +"</td></tr>"
          + "<tr><th class=\"thInfo\">Edad</th><td>"+ edad +"</td></tr>"
          + "<tr><th class=\"thInfo\">Error</th><td>"+ String.valueOf(Double.parseDouble(e)*100) +" %</td></tr>"
          + "</table>"
          + "<table cellspacing=\"0\"; cellpadding=\"0\" >";
        if( lpt.isEmpty() ) t += "<tr><th colspan=\"2\" class=\"tituloT\">Patrón "+pi+" no detectado. Tiempo de cálculo: "+tmp+"</th></tr>";
        else{
            t += "<tr><th colspan=\"2\" class=\"tituloT\">Patrón "+pi+" detectado en &nbsp;&nbsp;( "+lpt.get(0).getNumApCaminos()+" ) posiciones en "+tmp+"</th></tr>";
            t += "<tr><th class=\"thPat\" >Patron: "+lpt.get(0).getPatron()+"</th><th class=\"thPat\" align=\"right\" style=\"padding-right:8px;\">Mutaciones</th></tr>";      

            lc = lpt.get(0).getC();

            for ( Camino c : lc ) {

                ld = c.getOccDist()[0];

                for(int k=0; k < ld.size(); k++ ){
                    aux = adn.length()-ld.get(k);
                    t += "<tr><td class=\"tdNormalFlojo\">" +adn.substring( 0, aux) + colorea( c.getCamino() ) + adn.substring( aux+c.getCamino().length(), adn.length()) + "</td>"
                        + "<td align=\"right\" style=\"padding-right:8px;\">"+c.getError()+"</td></tr>";
                }
            }
           
        }
        t += "</table>";
        t += "</body>";
        CtrlPresAlgoritmo.mostrarResultadoExperimentoPatronPersonalizado( t );
    }
    private static String colorea( String s ){
        
        String colorS = "";
        for (char c : s.toCharArray() ) {
            colorS += "<span id=\""+ c +"\">"+ c + "</span>";
        }
        return colorS;
    }
    
    
    /**
    * Muestra los resultados del experimento, según el tipo de experimento que se ha realizado.
    * Esta función es temporal, ya que formará parte de la capa de presentación más adelant.
    *
    * @param edadI edad inicial del rango de edades a considerar
    * @param edadF edad final del rango de edades a considerar
    * @param nh numerho de historial del paciente
    * @param sexo sexo ( m / f / x )
    * @param pat nombre de la patologia
    * @param nhp ArrayList de numeros de historiales de pacientes
    * @param vp ArrayList de nombres de patologias
    * @param op tipo de experimento
    */
    
    /**
     * 
     * @param adn ristra de adn de la persona
     * @param pat ristra de la secuencia de la patologia a comprobar
     * @param m mida de las ristras
     * @param e error permitido
     * @param q numero mínimo de apariciones
     * @return true si la patologia si se ha obtenido una patologia potencial, false si no.
     */
    
    
}
