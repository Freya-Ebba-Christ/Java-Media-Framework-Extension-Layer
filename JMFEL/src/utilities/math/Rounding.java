/*
 * Rounding.java
 *
 * Created on 12. Juni 2007, 20:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilities.math;

/**
 *
 * @author Administrator
 */
public class Rounding {
    
    /** Creates a new instance of Rounding */
    public Rounding() {
    }
    
    public static double round(double val, int digits) {
        long factor = (long)Math.pow(10,digits);
        val = val * factor;
        long tmp = Math.round(val);
        return (double)tmp / factor;
    }
    
    public static float round(float val, int digits) {
        return (float)round((double)val, digits);
    }
}
