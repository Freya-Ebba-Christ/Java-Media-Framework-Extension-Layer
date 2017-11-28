/*
 * DigitalIO.java
 *
 * Created on 6. Juli 2007, 17:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.gtec;

/**
 *
 * @author Administrator
 */
public class DigitalIO {
    
    private boolean din_1 = false;
    private boolean din_2 = false;
    private boolean dout_1 = false;
    private boolean dout_2 = false;
    
    /** Creates a new instance of DigitalIO */
    
    public DigitalIO() {
    }

    public boolean isDIN1() {
        return din_1;
    }

    public boolean isDIN2() {
        return din_2;
    }

    public boolean isDOUT1() {
        return dout_1;
    }

    public boolean isDOUT2() {
        return dout_2;
    }

    public void setDIN1(boolean din_1) {
        this.din_1 = din_1;
    }

    public void setDIN2(boolean din_2) {
        this.din_2 = din_2;
    }

    public void setDOUT1(boolean dout_1) {
        this.dout_1 = dout_1;
    }

    public void setDOUT2(boolean dout_2) {
        this.dout_2 = dout_2;
    }
}