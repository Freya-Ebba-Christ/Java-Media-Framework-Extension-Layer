/*
 * DatasinkController.java
 *
 * Created on 4. Juni 2007, 18:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package custom_controller;

import javax.media.ConfigureCompleteEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.MediaTimeSetEvent;
import javax.media.Player;
import javax.media.PrefetchCompleteEvent;
import javax.media.Processor;
import javax.media.RealizeCompleteEvent;
import javax.media.ResourceUnavailableEvent;
import javax.media.StopAtTimeEvent;

/**
 *
 * @author Administrator
 */
public class DatasinkController implements ControllerListener {
    
    Processor p;
    Player player;
    Object waitSync = new Object();
    boolean stateTransitionOK = true;
    private boolean paused = false;
    
    public DatasinkController() {
        
    }
    
    public DatasinkController(Processor processor) {
        p = processor;
    }
    
    public DatasinkController(Player aPlayer) {
        player = aPlayer;
    }
    
    /**
     * Block until the processor has transitioned to the given state.
     * Return false if the transition failed.
     */
    
    public boolean waitForState(int state) {
        
        synchronized (waitSync) {
            if(p!=null){
                try {
                    while (p.getState() < state && stateTransitionOK)
                        waitSync.wait();
                } catch (Exception e) {}
            } else if (player != null){
                try {
                    while (player.getState() < state && stateTransitionOK)
                        waitSync.wait();
                } catch (Exception e) {}
            }
        }
        return stateTransitionOK;
    }
    
    public boolean isPaused() {
        return paused;
    }
    
    /**
     * Controller Listener.
     */
    public void controllerUpdate(ControllerEvent evt) {
        
        if (evt instanceof ConfigureCompleteEvent ||
                evt instanceof RealizeCompleteEvent ||
                evt instanceof PrefetchCompleteEvent) {
            synchronized (waitSync) {
                stateTransitionOK = true;
                waitSync.notifyAll();
            }
        } else if (evt instanceof ResourceUnavailableEvent) {
            synchronized (waitSync) {
                stateTransitionOK = false;
                waitSync.notifyAll();
            }
        } else if (evt instanceof EndOfMediaEvent) {
            evt.getSourceController().close();
        } else if (evt instanceof MediaTimeSetEvent) {
            System.err.println("- mediaTime set: " +
                    ((MediaTimeSetEvent)evt).getMediaTime().getSeconds());
        } else if (evt instanceof StopAtTimeEvent) {
            System.err.println("- stop at time: " +
                    ((StopAtTimeEvent)evt).getMediaTime().getSeconds());
            evt.getSourceController().close();
        }
    }
}