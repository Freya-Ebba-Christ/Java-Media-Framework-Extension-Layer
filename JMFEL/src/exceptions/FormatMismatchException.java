/*
 * FormatMismatchException.java
 *
 * Created on 11. April 2007, 15:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package exceptions;

/**
 *
 * @author christ
 */
public class FormatMismatchException extends Exception{
    
    /** Creates a new instance of FormatMismatchException */
    public FormatMismatchException() {
    }
    
    public FormatMismatchException(String message) {
        super(message);
    }
}
