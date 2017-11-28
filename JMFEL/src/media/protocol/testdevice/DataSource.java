/*
 * DataSource.java
 *
 * Created on 5. Oktober 2007, 13:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */


package media.protocol.testdevice;

import media.protocol.generic.GenericDataSource;
/**
 *
 * @author Urkman_2
 */
public class DataSource extends GenericDataSource{
    
    /** Creates a new instance of DataSource */
    public DataSource() {
        setCaptureDevice(new CustomCaptureDevice());
    }
}
