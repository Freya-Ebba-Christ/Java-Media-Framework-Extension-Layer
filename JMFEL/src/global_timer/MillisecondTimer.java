/*
 * MillisecondTimer.java
 *
 * Created on 26. Februar 2007, 10:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package global_timer;

import java.util.Observable;
import java.util.TimerTask;
import java.util.Timer;

/**
 *
 * @author christ
 */
public class MillisecondTimer extends Observable{
    private Timer timer;
    private MillisecondTimerTask millisecondTimerTask;
    private static MillisecondTimer millisecondTimer = null;
    private boolean running = false;
    
    /** Creates a new instance of MillisecondTimer */
    private  MillisecondTimer() {
    }
    
    public static MillisecondTimer getInstance(){
        if(millisecondTimer==null){
            millisecondTimer = new MillisecondTimer();
        }
        return millisecondTimer;
    }
    
    public synchronized boolean isRunning() {
        return running;
    }
    
    public synchronized void setRunning(boolean running) {
        this.running = running;
    }
    
    //make sure that the timer is started only once
    public synchronized void start(){
        if(!isRunning()){
            millisecondTimerTask = new MillisecondTimerTask();
            millisecondTimerTask.setMillisecondTimer(this);
            timer = new Timer();
            timer.scheduleAtFixedRate(millisecondTimerTask,0,1);
            setRunning(true);
        }
    }
    
    public void stop(){
        setRunning(false);
        timer.cancel();
    }
    
    public void updateObservers(){
        setChanged();
        notifyObservers();
    }
    
    //test timer
    public static void main(String[] args) {
        MillisecondTimer aMillisecondTimer = MillisecondTimer.getInstance();
        Prescaler aPrescaler = new Prescaler();
        TimerObserverExample aTimerObserver = new TimerObserverExample();
        aMillisecondTimer.addObserver(aPrescaler);
        aPrescaler.setUpdateCycle(125);
        aPrescaler.addObserver(aTimerObserver);
        aMillisecondTimer.start();
    }
}

class MillisecondTimerTask extends TimerTask{
    
    private MillisecondTimer millisecondTimer;
    
    public MillisecondTimerTask(){
    }
    
    public void setMillisecondTimer(MillisecondTimer millisecondTimer) {
        this.millisecondTimer = millisecondTimer;
    }
    
    public MillisecondTimer getMillisecondTimer() {
        return millisecondTimer;
    }
    
    public void run() {
        getMillisecondTimer().updateObservers();
    }
}