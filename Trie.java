

import java.util.ArrayList;

/**
 *
 * @author ADN
 */
public class Trie {

    private Node    raiz;
    private int     tamOccBits;
    
    public Trie() {

        raiz        = null;
        tamOccBits  = 0;
    }
    
    public void insertaSecuencia( String s, int numSeq ) {
        
        s = s.toUpperCase();
        s = s.concat("#");
        
        if( controlSecuencia(s) ){
        
            for (int i = 0; i < s.length()-1; i++) {

                if ( raiz == null ) {
 
                    raiz = new Node( s.charAt(i), tamOccBits, numSeq, s.length()-1 );
                    if ( i < s.length() - 1 ) raiz.insertarHijos( s.substring(1), tamOccBits, numSeq, s.length()-2 );

                }else raiz.insertarSufix( s.substring(i), tamOccBits, numSeq, s.length()-1-i );
            }
        }
    }
    
    private Boolean controlSecuencia( String s ){
        
        Boolean ok = true;
        char c;
        for (int i = 0; i < s.length() && ok; i++) {
            
            c = s.charAt(i);
            
            if( c != 'A' && c != 'C' && c != 'G' && c !='T' && c !='#'){
                System.out.println("Secuencia "+ s +" descartada."); ok = false;
            }
        }
        return ok;
    }

    public void setTamOccBits(int tOccBits) {
        tamOccBits = tOccBits;
    }
    
    public void imprimirPreorden(){
        imprimirPre(raiz);
        System.out.println();
    }
//    private void imprimirPre( Node n ){
//        
//        if ( n != null ){
//            
//            System.out.print(n.getLetra() + " [");
//            for ( boolean b : n.getOccBits() ) {
//                if( b ) System.out.print("1");
//                else    System.out.print("0");
//            }
//           System.out.print("]"); System.out.println();
//           imprimirPre( n.getHijo() );
//           imprimirPre( n.getHermano() );
//        }
//    }
    private void imprimirPre( Node n ){
        
        if ( n != null ){
            
            System.out.print(n.getLetra() + " [");
            for ( ArrayList<Integer> ld : n.getOccDist() ) {
                if( ld != null ){
                    for (Integer d : ld) System.out.print(d);    
                }
                else    System.out.print("N");
                System.out.print("|");
            }
           System.out.print("]"); System.out.println();
           imprimirPre( n.getHijo() );
           imprimirPre( n.getHermano() );
        }
    }
    
    public Node getRaiz(){
        return raiz;
    }

}