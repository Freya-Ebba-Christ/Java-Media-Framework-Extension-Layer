package custom_controller;
import javax.media.*;

public class CustomController implements ControllerListener {
    
    Processor p;
    Player player;
    Object waitSync = new Object();
    boolean stateTransitionOK = true;
    private boolean paused = false;
    
    public CustomController() {
        
    }
    
    public CustomController(Processor processor) {
        p = processor;
    }
    
    public CustomController(Player aPlayer) {
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
                } catch (Exception e) {System.out.println(e);}
            } else if (player != null){
                try {
                    while (player.getState() < state && stateTransitionOK)
                        waitSync.wait();
                } catch (Exception e) {System.out.println(e);}
            }
        }
        return stateTransitionOK;
    }
    
    public boolean isPaused() {
        return paused;
    }
    
    public void controllerUpdate(ControllerEvent evt) {
        
        if(evt.getSourceController().getState() == evt.getSourceController().Prefetched){
            paused = true;
        } else
            if(evt.getSourceController().getState() == evt.getSourceController().Started){
            paused = false;
            }
        
        
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
            p.setMediaTime(new Time(0));
            /*
            if(p!=null){
                p.close();
            } else if (player != null){
                player.close();
            }
             */
        } else if (evt instanceof SizeChangeEvent) {
        }
    }
}