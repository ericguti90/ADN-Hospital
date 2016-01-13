

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ADN
 */
public class Util{
    
    public static Date parseData(String data){
        
        Date parsed         = null;
        SimpleDateFormat df = new SimpleDateFormat("d/M/yyyy");
        try {
            parsed = df.parse(data);
        }
        catch(ParseException pe) {
            System.out.println("ERROR: No s'ha pogut llegir \"" + data + "\"");
        }
        return parsed;
    }
}
