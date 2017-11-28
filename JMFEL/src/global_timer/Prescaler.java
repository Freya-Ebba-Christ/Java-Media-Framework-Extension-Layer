/*
 * Prescaler.java
 *
 * Created on 26. Februar 2007, 13:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package global_timer;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author christ
 */
public class Prescaler extends Observable implements Observer, Prescalable{
    private int cycle=1;
    private int cnt = 0;
    /** Creates a new instance of Prescaler */
    public Prescaler() {
    }
    
    public void update(Observable o, Object arg){
        int tmp = cnt%=cycle;
        if(tmp==0){
            setChanged();
            notifyObservers();
        }
        cnt++;
    }
    
    public void setUpdateCycle(int value){
        cycle = value;
    }
}