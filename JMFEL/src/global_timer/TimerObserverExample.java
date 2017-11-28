/*
 * TimerObserver.java
 *
 * Created on 26. Februar 2007, 14:42
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
public class TimerObserverExample implements Observer{
    
    /** Creates a new instance of TimerObserver */
    public TimerObserverExample() {
    }
    
    public void update(Observable o, Object arg) {
        System.out.println("updated");
    }
}
