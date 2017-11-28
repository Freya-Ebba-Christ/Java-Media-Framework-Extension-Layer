/*
 * Surface.java
 *
 * Created on 20. August 2007, 13:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilities.graphics.passive_rendering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Observer;
import utilities.graphics.GraphicsSurface;
import utilities.graphics.AbstractPainter;
import utilities.graphics.GraphicsObservable;

/**
 *
 * @author Administrator
 */
public class Surface extends PassiveRenderingPanel implements GraphicsSurface{
    
    private BufferedImage backgroundImage;
    private GraphicsObservable graphicsObservable = new GraphicsObservable();
    private int fps = 0;
    private boolean fps_flag = true;
    private long time;
    private int lastFPS = 0;
    private boolean fpsEnabled = false;
    
    public Surface(){
        super();
        setLayout(null);
    }
    
    public int getLastFPS() {
        return lastFPS;
    }
    
    public boolean isFPSEnabled() {
        return fpsEnabled;
    }
    
    public void setFPSEnabled(boolean fpsEnabled) {
        this.fpsEnabled = fpsEnabled;
    }
    
    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }
    
    public void setBackgroundImage(BufferedImage image) {
        backgroundImage = image;
    }
    
    public void addObserver(Observer observer){
        graphicsObservable.addObserver(observer);
    }
    
    public void removeObserver(Observer observer){
        graphicsObservable.deleteObserver(observer);
    }
    
    public void removeAllObservers(){
        graphicsObservable.deleteObservers();
    }
    
    public int countObservers(){
        return graphicsObservable.countObservers();
    }
    
    public void addPainter(AbstractPainter painter){
        graphicsObservable.addObserver(painter);
    }
    
    public void removePainter(AbstractPainter painter){
        graphicsObservable.deleteObserver(painter);
    }
    
    public void removeAllPainter(){
        graphicsObservable.deleteObservers();
    }
    
    public int countPainter(){
        return graphicsObservable.countObservers();
    }
    
    public void render(int w, int h, Graphics2D g2) {
        
        g2.setColor(Color.BLACK);
        g2.clearRect(0,0,w,h);
        //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        
        if(getBackgroundImage()!=null){
            g2.drawImage(getBackgroundImage(),null,0,0);
        }
        
        graphicsObservable.setGraphics(g2);
        
        if(fps_flag){
            time = System.currentTimeMillis();
            fps_flag=false;
        }
        
        fps++;
        
        if((System.currentTimeMillis()-time)>=1000){
            lastFPS=fps;
            fps=0;
            fps_flag=true;
        }
        
        g2.setColor(Color.WHITE);
        if(isFPSEnabled()){
            g2.drawString("FPS: "+lastFPS,getWidth()-70,20);
        }
        
        graphicsObservable.setSurface(this);
        graphicsObservable.updated();
        
        paintComponents(g2);
    }
}