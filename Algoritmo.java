import java.util.ArrayList;

/**
 *
 * @author ADN
 */
public class Algoritmo {

    private int q;
    private int m;
    private double e;
    private ArrayList<String> lsIN;
    private ArrayList<Patron> lsPatrones;
    private Trie t;
    private char[] abc;

    //Creadora
    public Algoritmo() {

        q           = 0;
        m           = 0;
        e           = 0;
        lsIN        = new ArrayList();
        lsPatrones  = new ArrayList();
        t           = new Trie();
        abc         = new char[]{'A', 'C', 'G', 'T'};
    }

    public Algoritmo(int mIN, double eIN, int qIN) {

        if (qIN < 0) qIN = 0;
        if (eIN < 0) eIN = 0;
        if (mIN < 0) mIN = 0;

        q           = qIN;
        m           = mIN;
        e           = eIN;
        lsIN        = new ArrayList();
        lsPatrones  = new ArrayList();
        t           = new Trie();
        abc         = new char[]{'A', 'C', 'G', 'T'};
    }

    public void insertaSecuenciasSeleccionadas() {

        int n = lsIN.size();
        t.setTamOccBits(n);
        for (int i = 0; i < n; i++) t.insertaSecuencia(lsIN.get(i), i);
    }

    public void experimentoBusqueda( String patron ){
        
        Patron np           = new Patron(patron, lsIN.size());
        Camino camino       = new Camino(0, lsIN.size());
        ArrayList<Node> lh  = new ArrayList();
        
        getHermanos( t.getRaiz(), lh );
        
        buscaCoincidencias( np, lh, 0, camino );
        
        if ( np.aceptado( q ) ) lsPatrones.add( np );

    }
    
    public void experimentoEstandard() {

        Patron p = new Patron(lsIN.size());
        for (char c : abc) expand(p, c);
    }
    
    public void experimentoPotencial( String patron ){
        
        Patron np           = new Patron( patron, lsIN.size() );
        Camino camino       = new Camino( 0, lsIN.size() );
        ArrayList<Node> lh  = new ArrayList();
        
        getHermanos( t.getRaiz(), lh );
        
        buscaCoincidencias( np, lh, 0, camino );
        
        if ( np.aceptado( q ) ) lsPatrones.add( np );
    }
    
    private void expand(Patron p, char c) {

        Patron np           = new Patron(p.getPatron() + c, lsIN.size());
        Camino camino       = new Camino(0, lsIN.size());
        ArrayList<Node> lh  = new ArrayList();

        getHermanos( t.getRaiz(), lh );
        buscaCoincidencias( np, lh, 0, camino );

        int tamP = np.getPatron().length();
        if ( np.aceptado( q ) && tamP <= m ) {
            
            if ( tamP == m ) lsPatrones.add( np );

            for ( char cc : abc ) expand( np, cc );
        }
    }

    private void buscaCoincidencias(Patron p, ArrayList<Node> n, int pos, Camino c) {

        int errMax = (int)Math.ceil( e * ( p.getPatron().length() ) );

        if ( pos == p.getPatron().length() ) {
            
            p.addCamino( c ); return;
        }
        for ( Node nodo : n ) {

            int error = c.getError();

            if ( nodo.getLetra() != p.getPatron().charAt(pos) ) error++;
            
            if ( error <= errMax && !nodo.hijoVacio() ){

                ArrayList<Node> ln = new ArrayList();

                getHermanos(nodo.getHijo(), ln);

                Camino nc = new Camino( c.getCamino()+nodo.getLetra(), error, lsIN.size() );
                nc.setOccDist( calculaDistancias( c.getOccDist(), nodo.getOccDist(), c.getCamino().length() ) );
                
                buscaCoincidencias( p, ln, pos + 1, nc );
            }
        }
    }
    
    private ArrayList<Integer>[] calculaDistancias(ArrayList<Integer>[] occDC, ArrayList<Integer>[] occDN, int tamCamino ) {

        ArrayList<Integer>[] resultado = new ArrayList[occDC.length];

        for (int i = 0; i < resultado.length; i++) {

            if(occDC[i] == null || occDN[i] == null) resultado[i] = null;
            else{
                if(occDC[i].isEmpty()) resultado[i] = occDN[i];
                else{
                    resultado[i] = new ArrayList();
                    for (Integer d : occDC[i]){
                        if ( occDN[i].contains( d-tamCamino ) )resultado[i].add(d);
                    }
                }
            }
        }
        return resultado;
    }
    
    private void getHermanos(Node n, ArrayList<Node> rh) {

        if (n != null) {
            
            if (!n.letraNula()) rh.add(n);
            getHermanos(n.getHermano(), rh);
        }
    }
    
    //Modificadoras
    public void setE(int eIN) {
        
        if (eIN < 0) eIN = 0;
        e = eIN;
    }

    public void setM(int mIN) {
        
        if (mIN < 0) mIN = 0;
        m = mIN;
    }

    public void setQ(int qIN) {
        
        if (qIN < 0) qIN = 0;
        q = qIN;
    }

    public void setLsIN(ArrayList<String> listaS_IN) {
        lsIN = listaS_IN;
    }

    public void addLsIN(String listaS_IN) {
        lsIN.add(listaS_IN);
    }
    public void delLsIN(){
        lsIN.clear();
    }
    public void AddAllLsIN(ArrayList<String> listaS_IN) {
        lsIN.addAll(listaS_IN);
    }

    public void generarTrie() {

        int n = lsIN.size();
        if ( n == 0 ) ;//System.out.println("No se han introducido ristras!");
        else{
            t.setTamOccBits(n);
            for (int i = 0; i < n; i++) t.insertaSecuencia( lsIN.get(i), i );
        }
    }
    
    //Consultoras
    public double getE() {
        return e;
    }

    public int getM() {
        return m;
    }

    public int getQ() {
        return q;
    }

    public ArrayList<String> getLsIN() {
        return lsIN;
    }

    public ArrayList<Patron> getlsPatrones(){
        return lsPatrones;
    }
    
    public Trie getT() {
        return t;
    }

    // Escrituras
    public void imprimirTrieExperimento() {
        t.imprimirPreorden();
    }

    public void hermanosToString(ArrayList<Node> rh) {

        System.out.println("HERMANOS:");
        for (Node node : rh) {
            System.out.print(node.getLetra());
        }
        System.out.println();
    }

    public void patronesResultadoToString() {

        System.out.println("Patron\tCamino");
        
        for ( Patron p : lsPatrones ) {
            
            int numCaminos = 0;
            ArrayList<Camino> lc = p.getC();
            Camino cmt;
            for (int i = 0; i < lc.size(); i++) {
                cmt = lc.get(i);
                for (ArrayList<Integer> ld : cmt.getOccDist() ) {
                    if( ld != null ) numCaminos += ld.size();
                }
            }
               
            System.out.println( p.getPatron() + "\t" + numCaminos );
            
            for ( Camino c : p.getC() ) {
                System.out.print("\t"+ c.getCamino() + "[");
                for( ArrayList<Integer> ld : c.getOccDist() ){
                    
                    if( ld == null )            System.out.print("N.");
                    else if( ld.isEmpty() )     System.out.print("E.");
                    else{
                        for (Integer d : ld)    System.out.print(d+".");
                    }
                    System.out.print("|");
                }
                System.out.print("]  e = [");
                System.out.print(c.getError());
                System.out.print("]\n");
            }
        }
    }
    public void patronesResultadoBusquedaToString(){
        
       
        System.out.println("Patron\tCamino");
        
        for ( Patron p : lsPatrones ) {
            
            int numCaminos = 0;
            ArrayList<Camino> lc = p.getC();
            Camino cmt;
            for (int i = 0; i < lc.size(); i++) {
                cmt = lc.get(i);
                for (ArrayList<Integer> ld : cmt.getOccDist() ) {
                    if( ld != null ) numCaminos += ld.size();
                }
            }
               
            System.out.println( p.getPatron() + "\t" + numCaminos );
            
            for ( Camino c : p.getC() ) {
                System.out.print("\t"+ c.getCamino() + "[");
               
                for( ArrayList<Integer> ld : c.getOccDist() ){
                    
                    if( ld == null )            System.out.print("N.");
                    else if( ld.isEmpty() )     System.out.print("E.");
                    else{
                        for (Integer d : ld)    System.out.print(d+".");
                    }
                    System.out.print("|");
                }
                System.out.print("]  e = [");
                System.out.print(c.getError());
                System.out.print("]\n");
            }
        }
        
    }
    
    public int getNumCaminosEncontrados(){
        
        //System.out.println("Patron\tCamino");
        int numCaminos = 0;
        for ( Patron p : lsPatrones ) {
            
            ArrayList<Camino> lc = p.getC();
            Camino cmt;
            for (int i = 0; i < lc.size(); i++) {
                cmt = lc.get(i);
                for (ArrayList<Integer> ld : cmt.getOccDist() ) {
                    if( ld != null ) numCaminos += ld.size();
                }
            }
        }
        //System.out.println( "Caminos encontrados: " + numCaminos );
        return numCaminos;
    }
    public void newTrie(){
        t = new Trie();
    }
    
    // Utiles
    public void deleteLsPatrones(){
        lsPatrones.clear();
    }
}
