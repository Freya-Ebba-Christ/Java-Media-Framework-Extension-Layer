/*
 * DataSource.java
 *
 * Created on 15. Oktober 2007, 12:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.usbAmpB;

import media.protocol.generic.GenericDataSource;

/**
 *
 * @author Administrator
 */
public class DataSource extends GenericDataSource{
    
    public DataSource() {
        setCaptureDevice(new CustomCaptureDevice());
    }
}