/*
 * WrongWorkingModeException.java
 *
 * Created on 6. Juli 2007, 14:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package exceptions;

/**
 *
 * @author Administrator
 */
public class WorkingModeException extends Exception{
    
    /** Creates a new instance of WrongWorkingModeException */
    public WorkingModeException() {
    }
    
    public WorkingModeException(String message) {
        super(message);
    }
}
