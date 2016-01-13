

import java.util.ArrayList;


/**
 *
 * @author ADN
 * 
 *  Description: Node
 *               Esta clase implementa la estructura de un nodo que es usado
 *               como un componente en la estructura del abrol trie
*/
public class Node {
    
    private char                    letra;      // contenido del nodo.
    //private boolean[]           occBits;    // vector boleano que indicael número de la ristra introducida
    private ArrayList<Integer>[]    occDist;
    private Node                    hijo;       // hijo
    private Node                    hermano;    // hermano
    
    /**
        * Crea un nuevo nodo
        * @param   None
        * @return  None
        */
    
    public Node( char c, int tamOccDist, int numSec, int dist ){
        
        letra           = c;
        occDist         = new ArrayList[tamOccDist];
        occDist[numSec] = new ArrayList();
        occDist[numSec].add(dist);
        //occBits         = new boolean[tamOccBits];
        //occBits[numSec] = true;
        hijo            = null;
        hermano         = null;
        
    }
    
    public boolean hijoVacio(){
        return hijo == null;
    }
    public boolean hermanoVacio(){
        return hermano == null;
    }   
    public boolean letraNula(){
        return letra == '#';
    }
    
//    public void activaPosOccBits( int pos ){
//        
//        occBits[pos] = true;
//    }
    
    public void insertarSufix( String s, int tamOccBits, int numSec, int dist ){
      
        if( letra == s.charAt(0) ){

            //if( !occBits[numSec] ) occBits[numSec] = true;
    
            if( occDist[numSec] == null ) occDist[numSec] = new ArrayList();
            occDist[numSec].add(dist);
            
            if( hijo == null ){

                if( !s.substring(1).equals("") )                    hijo = new Node( s.charAt(1), tamOccBits, numSec, dist-1 );
                if( s.length()>1 && !s.substring(2).equals("") )    hijo.insertarHijos( s.substring(2), tamOccBits, numSec, dist-2 );
            }
            else if( !s.substring(1).equals("") ) hijo.insertarSufix( s.substring(1), tamOccBits, numSec, dist-1 );
            
        }else{
            if( hermano == null ){ 
                hermano = new Node( s.charAt(0), tamOccBits, numSec, dist );
                if( !s.substring(1).equals("") ) hermano.insertarHijos( s.substring(1), tamOccBits, numSec, dist-1 );
            }
            else hermano.insertarSufix( s.substring(0), tamOccBits, numSec, dist );
        }
    }
    
//    public void insertarSufix( String s, int tamOccBits, int numSec ){
//      
//        if( letra == s.charAt(0) ){
//
//            if( !occBits[numSec] ) occBits[numSec] = true;
//            
//            if( hijo == null ){
//
//                if( !s.substring(1).equals("") )                    hijo = new Node( s.charAt(1), tamOccBits, numSec );
//                if( s.length()>1 && !s.substring(2).equals("") )    hijo.insertarHijos( s.substring(2), tamOccBits, numSec );
//            }
//            else if( !s.substring(1).equals("") ) hijo.insertarSufix( s.substring(1), tamOccBits, numSec );
//            
//        }else{
//            if( hermano == null ){ 
//                hermano = new Node( s.charAt(0), tamOccBits, numSec );
//                if( !s.substring(1).equals("") ) hermano.insertarHijos( s.substring(1), tamOccBits, numSec );
//            }
//            else hermano.insertarSufix( s.substring(0), tamOccBits, numSec );
//        }
//    }
    
    
    
    
    
    public void insertarHijos( String s, int tamOccBits, int numSec, int dist ){
        
        if( !s.equals( "" ) ){
            
            hijo = new Node( s.charAt(0), tamOccBits, numSec, dist );
            hijo.insertarHijos( s.substring(1), tamOccBits, numSec, dist-1 );
        }
    }

//    public boolean[] getOccBits() {
//        return occBits;
//    }
    public ArrayList<Integer>[] getOccDist(){
        return occDist;
    }
//    public int getQ(){
//        
//        int q = 0;
//        for (boolean b : occBits) if(b) q++;
//        return q;
//    }
    
    public int getQ(){
        
        int q = 0;
        for (ArrayList<Integer> ld : occDist) {
            if( ld != null ) q++;
        }
        return q;
    }
    public int getQPlus(){
        
        int q = 0;
        for (ArrayList<Integer> ld : occDist) {
            if( ld != null ) q += ld.size();
        }
        return q;
    }
    
    public Node getHermano() {
        return hermano;
    }
    public void setHermano(Node h) {
        hermano = h;
    }
    public Node getHijo() {
        return hijo;
    }
    public void setHijo(Node h) {
        hijo = h;
    }
    public char getLetra() {
        return letra;
    }
    public void setLetra(char l) {
        letra = l;
    }
    
}
