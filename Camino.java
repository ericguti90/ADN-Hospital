

import java.util.ArrayList;

/**
 *
 * @author ADN
 */
public class Camino {
    
    private String      camino;
    private int         error;
    //private boolean []  occBits;
    private ArrayList<Integer>[] occDist;
    
    Camino( int e, int numSec ){
        
        camino  = "";
        error   = e;
        occDist = new ArrayList[numSec];
        for (int i = 0; i < numSec; i++) {
            occDist[i] = new ArrayList();
        }
    }
    
    Camino( String s,int e, int numSec ){
        
        camino  = s;
        error   = e;
        occDist = new ArrayList[numSec];
        for (int i = 0; i < numSec; i++) {
            occDist[i] = new ArrayList();
        }
    }
    
    
    
    
    // GETTERS
    
    public String getCamino(){
        return camino;
    }
    
    public int getError(){
        return error;
    }
    
     public ArrayList<Integer>[] getOccDist(){
        return occDist;
    }
    
    // SETTERS
    
    public void setCamino( String cm ){
        camino = cm;
    }
    public void setError( int e ){
        error = e;
    }
    
    public void setOccDist( ArrayList<Integer>[] ocd ){
        occDist = ocd;
    }
}

