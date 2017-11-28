/*
 * PropertiesWriter.java
 *
 * Created on 20. September 2007, 20:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilities;

import java.io.FileOutputStream;
import java.util.Properties;

/**
 *
 * @author Administrator
 */
public class PropertiesWriter {
    
    /** Creates a new instance of PropertiesReader */
    public PropertiesWriter() {
    }
    
    public Properties writeProperties(String filename, Properties props){
        FileOutputStream out = null;
        try{
            out = new FileOutputStream(filename);
        }catch(java.io.FileNotFoundException fnfex){System.err.println(fnfex);
        };
        try{
            props.store(out,"");
            out.close();
        }catch(java.io.IOException ioex){System.err.println(ioex);
        };
        return props;
    }
}