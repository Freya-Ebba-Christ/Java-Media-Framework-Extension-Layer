/*
 * CommonGround.java
 *
 * Created on 6. Juli 2007, 20:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.gtec;

/**
 *
 * @author Administrator
 */
public class CommonGround {
    
    private boolean groupA = false;
    private boolean groupB = false;
    private boolean groupC = false;
    private boolean groupD = false;
    
    /** Creates a new instance of CommonGround */
    public CommonGround() {
    }

    public void setGroupA(boolean groupA) {
        this.groupA = groupA;
    }

    public void setGroupB(boolean groupB) {
        this.groupB = groupB;
    }

    public void setGroupC(boolean groupC) {
        this.groupC = groupC;
    }

    public void setGroupD(boolean groupD) {
        this.groupD = groupD;
    }

    public boolean isGroupA() {
        return groupA;
    }

    public boolean isGroupB() {
        return groupB;
    }

    public boolean isGroupC() {
        return groupC;
    }

    public boolean isGroupD() {
        return groupD;
    }
}
