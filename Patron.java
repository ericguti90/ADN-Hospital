

import java.util.ArrayList;

/**
 *
 * @author ADN
 */
public class Patron {

    private String patron;
    private ArrayList<Camino> lc;
    private boolean[] occBits;

    public Patron(int numSec) {

        patron = "";
        lc = new ArrayList();
        occBits = new boolean[numSec];

    }

    public Patron(String p, int numSec) {

        patron = p;
        lc = new ArrayList();
        occBits = new boolean[numSec];

    }

    // GETTERS
    public String getPatron() {
        return patron;
    }

    public ArrayList<Camino> getC() {
        return lc;
    }

    public boolean[] getOcb() {
        return occBits;
    }

    public int getNumApCaminos(){
        
        int nap = 0;
        for( Camino c : lc ){
            for (int i = 0; i < c.getOccDist().length; i++){
                
                if(c.getOccDist()[i] != null ) nap += c.getOccDist()[i].size();
            }
        }
        return nap;
    }
    
    public int getNumError(){
        
        int nerr = 0;
        for( Camino c : lc ) nerr += c.getError();
        return nerr;
    }
    
    public int getNumSec(){
        
        int nsec = 0;
        for (boolean b : occBits) {
            if( b ) nsec++;
        }
        return nsec;
    }
    
    // SETTERS
    
    public void anadirCamino(String nomCamino, int errorCamino, int mida, ArrayList<Integer>[] occDist){
        Camino c = new Camino(nomCamino, errorCamino, mida );
        c.setOccDist(occDist);
        lc.add(c);
    }
    
    public void setPatron(String p) {
        patron = p;
    }

    public void setC(ArrayList<Camino> ca) {
        lc = ca;
    }

    public void setOcb(boolean[] ocb) {
        occBits = ocb;
    }
    
    private void setBit(int i) {

        if (i >= 0 && i < occBits.length) {
            occBits[i] = true;
        }
    }

    public Boolean aceptado(int q) {

        int cont = 0;
        for (boolean b : occBits) {
            if (b) {
                cont++;
            }
        }
        return cont >= q;
    }

    void addCamino(Camino cm) {
        boolean existe = false;
        for (int j = 0; j < lc.size() && !existe; j++) {
            if (lc.get(j).getCamino().equals(cm.getCamino())) {
                existe = true;
                for (int i = 0; i < lc.get(j).getOccDist().length; i++) {
                    ArrayList<Integer> ld = lc.get(j).getOccDist()[i];
                    if (ld != null) {
                        for (Integer d : cm.getOccDist()[i]) {
                            if (!ld.contains(d)) {
                                ld.add(d);
                            }
                        }
                    }
                }
            }
        }
        if (!existe) {
            lc.add(cm);
            ArrayList<Integer>[] ld = cm.getOccDist();
            for (int i = 0; i < occBits.length; i++) {
                if (ld[i] != null && ld[i].size() > 0) {
                    setBit(i);
                }
            }
        }
    }

}
