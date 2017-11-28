/*
 * This application displays Wundt's clock
 */

package examples.motorcortex.eeg_recording;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import utilities.graphics.passive_rendering.ApplicationContainer;
import utilities.graphics.passive_rendering.Surface;
import javax.swing.Timer;

/**
 *
 * @author Administrator
 */

public class StopwatchApplication extends ApplicationContainer{
    
    private Surface surface;
    private StopwatchPainter painter;
    
    /** Creates a new instance of TopographicMapping */
    public StopwatchApplication() {
    }
    
    public StopwatchPainter getPainter() {
        return painter;
    }
    
    public void setPainter(StopwatchPainter painter) {
        this.painter = painter;
    }
    
    public Surface getSurface() {
        return surface;
    }
    
    public void setSurface(Surface surface) {
        this.surface = surface;
    }
    
    public void init() {
        setRestoreLocationEnabled(false);
        setLocation(0,0);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        surface = new Surface();
        painter = new StopwatchPainter();
        painter.getPanel().setX(10);
        painter.getPanel().setY(10);
        painter.getPanel().setWidth((int)toolkit.getScreenSize().getWidth());
        painter.getPanel().setHeight((int)toolkit.getScreenSize().getHeight());
        surface.addPainter(painter);
        surface.setSize((int)toolkit.getScreenSize().getWidth(),(int)toolkit.getScreenSize().getHeight());
        surface.setPreferredSize(new Dimension((int)toolkit.getScreenSize().getWidth(),(int)toolkit.getScreenSize().getHeight()));
        surface.setMinimumSize(new Dimension((int)toolkit.getScreenSize().getWidth(),(int)toolkit.getScreenSize().getHeight()));
        surface.setMaximumSize(new Dimension((int)toolkit.getScreenSize().getWidth(),(int)toolkit.getScreenSize().getHeight()));
        surface.setCycles(10);
        surface.setFPSEnabled(false);
        surface.start();
        Timer timer = new Timer(50,painter.getPanel());
        timer.start();
        setVisualComponent(surface);
        super.init();
    }
    
    public static void main(String[] args) {
        StopwatchApplication app = new StopwatchApplication();
        //fake fullscreen
        app.setUndecorated(true);
        app.init();
        app.setVisible(true);
    }
}