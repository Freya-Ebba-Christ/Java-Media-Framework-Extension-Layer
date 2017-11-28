/*
 * DeviceException.java
 *
 * Created on 6. Juli 2007, 16:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package exceptions;

/**
 *
 * @author Administrator
 */
public class DeviceException extends Exception{
    
    /** Creates a new instance of DeviceException */
    public DeviceException() {
    }
    
    public DeviceException(String message) {
        super(message);
    }
}
