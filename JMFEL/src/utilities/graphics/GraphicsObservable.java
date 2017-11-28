/*
 * GraphicsObservable.java
 *
 * Created on 9. Oktober 2007, 17:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilities.graphics;

import java.awt.Graphics2D;
import java.util.Observable;

/**
 *
 * @author Urkman_2
 */
public class GraphicsObservable extends Observable{

    private Graphics2D graphics;
    private GraphicsSurface surface;
    
    /** Creates a new instance of GenericObservable */
    public GraphicsObservable() {
    }

    public GraphicsSurface getSurface() {
        return surface;
    }
    
    public void setSurface(GraphicsSurface surface) {
        this.surface = surface;
    }
    
    public Graphics2D getGraphics() {
        return graphics;
    }

    public void setGraphics(Graphics2D graphics) {
        this.graphics = graphics;
    }
    
    public void updated(){
        setChanged();
        notifyObservers();
    }
}