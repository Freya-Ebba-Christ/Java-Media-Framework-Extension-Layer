/*
 * AbstractActiveRenderingFrame.java
 *
 * Created on 24. September 2007, 18:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilities.graphics.active_rendering;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import utilities.graphics.active_rendering.NullRepaintManager;

/**
 *
 * @author Administrator
 */
public abstract class AbstractActiveRenderingFrame extends JFrame implements KeyListener{
    private BufferStrategy aBufferStrategy;
    private Graphics2D gc = null;
    private Dimension dimension;
    
    /** Creates a new instance of ActiveRenderingFrame */
    public AbstractActiveRenderingFrame() {
    }
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==e.VK_ESCAPE){
            System.exit(0);
        }
    }
    
    public void keyTyped(KeyEvent e) {
        
    }
    
    public void keyReleased(KeyEvent e) {
        
    }
    public Dimension getDimension() {
        return dimension;
    }
    
    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }
    
    public void setGraphics2D(Graphics2D gc) {
        this.gc = gc;
    }
    
    public Graphics2D getGraphics2D() {
        return gc;
    }
    
    public abstract void renderPanel();
    
    public void initFrame(Dimension aDimension, boolean use_fullscreen) {
        addKeyListener(this);
        setDimension(aDimension);
        setUndecorated(use_fullscreen);
        initComponents();
        //again we are using active rendering here
        setIgnoreRepaint(true);
        initActiveRendering(use_fullscreen);
    }
    
    public abstract void initComponents();
    public abstract void start();
    
    public void initActiveRendering(boolean use_fullscreen){
        if(use_fullscreen){
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice device = env.getDefaultScreenDevice();
            GraphicsConfiguration gc = device.getDefaultConfiguration();
            device.setFullScreenWindow(this);
            if (device.isDisplayChangeSupported()) {
                device.setDisplayMode(new DisplayMode((int)getDimension().getWidth(),(int)getDimension().getHeight(),32,0));
            }
        }
        createBufferStrategy(2);
        if(use_fullscreen){
            NullRepaintManager.install();
        }
    }
}