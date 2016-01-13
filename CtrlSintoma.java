
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 *
 * @author ADN
 */
public class CtrlSintoma {
    
    private static CjtSintomas cjs;
       
    public static void setCjtSintomas(CjtSintomas cjsin) {
        if (cjsin == null) cjs = new CjtSintomas();
        else cjs = cjsin;
    }
    
    public static boolean introducirSintoma(String nombre, String descrip, ArrayList<String> Pato){
        if (cjs.ExisteSintoma(nombre)) return false;
        ArrayList<Patologia> a = CtrlPatologia.getPatologias(Pato); // Falta modificar a CtrlPatologia la funcio
        Sintoma s = new Sintoma(nombre, descrip, a);
        cjs.InsercionSintoma(s);
        return true;
    }
    
    public static ArrayList<Sintoma> getSintomaConjunto(ArrayList<String> nombres){
        ArrayList<Sintoma> s = new ArrayList();
        for(int i= 0; i < nombres.size(); ++i){
            if(cjs.ExisteSintoma(nombres.get(i))) s.add(cjs.getSintoma(nombres.get(i)));
        }
        return s;
    }
    
    public static boolean existe_sintoma(String nombre) {
        return cjs.ExisteSintoma(nombre);
    }

    public static ArrayList<Patologia> getPatologiaR(String nombre) {
        return cjs.getSintoma(nombre).getPatologiaR();
    }

    public static ArrayList<String> getSintoma(ArrayList<Sintoma> sin){
        ArrayList<String> p = new ArrayList();
        for(int i = 0; i < sin.size(); ++i) {
            p.add(sin.get(i).getNombre());
        }
        return p;
    }
    public static Sintoma get1Sintoma(String nombre) {
        return cjs.getSintoma(nombre);
    }

    public static ArrayList<String> getSintoma( String sin){
        Sintoma sinto = cjs.getSintoma(sin);
        ArrayList<String> s = new ArrayList();
        if(sinto != null) {
            s.add(sinto.getNombre());
            s.add(sinto.getDescripcion());
            ArrayList<String> pato = CtrlPatologia.getPatologiasNombres(sinto.getPatologiaR());
            s.addAll(pato);
        }
        return s;
    }

    public static void modificarSintoma( String nombre, String descrip, ArrayList<String> pato ){
        ArrayList<Patologia> pp = CtrlPatologia.getPatologias(pato); // Falta modificar a CtrlPatologia la funcio
        cjs.ModificarSintoma(nombre, descrip, pp);
    }
    
    public static boolean eliminarSintoma( String s ){
        if(!cjs.ExisteSintoma(s)) return false;
        else cjs.EliminarSintoma(s);
        return true;
    }
    
    public static void actualizar_sintomaI(String nombre, ArrayList<String> sin){
        Patologia p = CtrlPatologia.get1Patologia(nombre); // Falta modificar a CtrlPatologia la funcio
        for(int i = 0; i < sin.size();++i){
            Sintoma saux = cjs.getSintoma(sin.get(i));
            if (saux != null) {
                ArrayList<Patologia> aux = saux.getPatologiaR();
                aux.add(p);
                cjs.ModificarSintoma(saux.getNombre(), saux.getDescripcion(), aux);
            }
        }
    }
    
    public static void actualizar_sintomaE(String nombre, ArrayList<String> sin ){
        Patologia p = CtrlPatologia.get1Patologia(nombre); // Falta modificar a CtrlPatologia la funcio
        for(int i = 0; i < sin.size();++i){
            Sintoma saux = cjs.getSintoma(sin.get(i));
            ArrayList<Patologia> aux = saux.getPatologiaR();
            aux.remove(p);
            cjs.ModificarSintoma(saux.getNombre(), saux.getDescripcion(), aux);
        }
    }
    
    public static void guardarSintomas(JFileChooser direc) {
        ArrayList<Sintoma> sin;
        ArrayList<Patologia> pat;
        String text = "Sintomas\n";
        Sintoma s;
        sin = cjs.listaSintomas();
        CtrlData.abrirFichero(direc);
        for( int i = 0; i < sin.size(); i++ ){  //Esquema por Sintoma: Nom;Descripcio;NumPat;NomPat1;NomPat2;...NomPatn\n
            s = sin.get(i); 
            text += s.getNombre();
            text += ";" + s.getDescripcion();
            pat = s.getPatologiaR();
            text += ";" + pat.size();
            for( int j = 0; j < pat.size(); j++ ) text += ";" + pat.get(j).getNombre();
            text += "\n";
            if(((i%100)==0) || (i+1 == sin.size())){
                CtrlData.guardarFichero(text);
                text = "";
            }
        } 
        CtrlData.cierraFichero();
    }
    
    public static ArrayList<String> getNombreSintomas(){
        ArrayList<String> aux = new ArrayList();
        if (!cjs.ArbolVacio()) {
            aux.addAll(cjs.getNombreSintomas());
        }
        return aux;
    }
}

