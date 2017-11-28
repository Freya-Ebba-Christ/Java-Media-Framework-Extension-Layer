/*
 * DataSource.java
 *
 * Created on 10. Oktober 2007, 17:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.keyboard;

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