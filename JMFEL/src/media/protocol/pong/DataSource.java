/*
 * DataSource.java
 *
 * Created on 31. Oktober 2007, 18:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.pong;

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