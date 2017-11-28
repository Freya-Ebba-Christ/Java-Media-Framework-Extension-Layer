/*
 * DeviceNotFoundException.java
 *
 * Created on 6. Juli 2007, 14:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package exceptions;

/**
 *
 * @author Administrator
 */
public class DeviceNotFoundException extends Exception{
    
    /** Creates a new instance of DeviceNotFoundException */
    public DeviceNotFoundException() {
    }
    
    public DeviceNotFoundException(String message) {
        super(message);
    }
}
