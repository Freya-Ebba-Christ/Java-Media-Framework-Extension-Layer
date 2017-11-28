/*
 * PropertiesReader.java
 *
 * Created on 3. September 2007, 14:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilities;

import java.io.FileInputStream;
import java.util.Properties;

/**
 *
 * @author Administrator
 */
public class PropertiesReader {
    
    /** Creates a new instance of PropertiesReader */
    public PropertiesReader() {
    }
    
    public Properties readProperties(String filename){
        Properties props = new Properties();
        FileInputStream in = null;
        String file_separator = System.getProperty("file.separator");
        try{
            in = new FileInputStream(filename);
        }catch(java.io.FileNotFoundException fnfex){System.err.println(fnfex);
        };
        try{
            props.load(in);
            in.close();
        }catch(java.io.IOException ioex){System.err.println(ioex);
        };
        return props;
    }
}