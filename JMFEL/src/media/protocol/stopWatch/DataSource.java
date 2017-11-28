/*
 * DataSource.java
 *
 * Created on 14. Dezember 2007, 18:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.stopWatch;

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