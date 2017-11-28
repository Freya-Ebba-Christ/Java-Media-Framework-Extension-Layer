/*
 * AbstractPainter.java
 *
 * Created on 9. Oktober 2007, 17:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilities.graphics;

import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Urkman_2
 */
public abstract class AbstractPainter implements Observer{
    private GraphicsObservable observable;
    private Graphics2D g2;
    private GraphicsSurface surface;
    
    /** Creates a new instance of AbstractPainter */
    public AbstractPainter() {
    }
    
    public GraphicsSurface getSurface() {
        return surface;
    }
    
    public void setSurface(GraphicsSurface surface) {
        this.surface = surface;
    }
    
    public Graphics2D getGraphics() {
        return g2;
    }
    
    public void setGraphics(Graphics2D graphics) {
        g2 = graphics;
    }
    
    public GraphicsObservable getObservable() {
        return observable;
    }
    
    public void update(Observable o, Object arg) {
        observable = ((GraphicsObservable)o);
        g2 = observable.getGraphics();
        setSurface(observable.getSurface());
        repaint();
    }
    
    public abstract void repaint();
}