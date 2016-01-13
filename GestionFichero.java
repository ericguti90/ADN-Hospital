

import java.io.*;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;

/**
 * 
 * @author ADN
 */
public class GestionFichero {

    private File            archivo;
    private FileReader      fr;
    private BufferedReader  br;
    private int             inicio;
    //para escribir
    private FileWriter      fichero;
    private PrintWriter     pw;

    /**
     * pre: creadora por defecto
     * post: la linea de inicio es igual a 0
     */
    public GestionFichero(){
        archivo = null;
        fr      = null;
        br      = null;
        fichero = null;
        pw      = null;
        inicio  = 0;
    }

    /**
     * pre: la direccion es valida y el fichero existe
     * @param dirF
     * @param numLineas
     * @return devuelve un String con el contenido de las lineas [inicio, inicio+numLineas], y actualiza inicio a la ultima linea consultada+1
     */
    public String cargarFicheroCom(JFileChooser buscF, int numLineas){
        String t="";
        try {
            archivo = buscF.getSelectedFile();
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);
            String linea;
            for( int i = 0; (linea=br.readLine()) != null && i < inicio+numLineas ; i++){ 
                if(i >= inicio) t += linea + "\n";
            }
            inicio += numLineas;
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
            if( null != fr ){
               fr.close();
            }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }

        return t;
    }
    //__________________GUARDAR FICHERO_____________________
    /**
     * pre: la direccionG existe
     * post: guarda el fichero nombreF en la direccion direccionG con el contenido de t
     * @param t
     * @param direccionG
     * @param nombreF
     */
    public void guardaFicheroNuevo(JFileChooser direc){
        String nom = direc.getSelectedFile() + ".txt";
        try{
            fichero = new FileWriter(nom);
            pw = new PrintWriter(fichero);
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
    
    /**
     * pre: la direccionG existe
     * post: guarda el fichero nombreF en la direccion direccionG con el contenido de t
     * @param t
     * @param direccionG
     * @param nombreF
     */
    public void guardaFicheroAnade(String t){
        StringTokenizer st = new StringTokenizer(t,"\n");
        try{
            while(st.hasMoreTokens()){
                String line = st.nextToken();
                pw.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardaFicheroCierra() {
        try{
            if (null != fichero) fichero.close();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
