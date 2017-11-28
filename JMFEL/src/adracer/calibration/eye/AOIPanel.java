/*
 * AOIPanel.java
 *
 * Created on 25. September 2007, 15:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package adracer.calibration.eye;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;
import utilities.Point2f;

/**
 *
 * @author Administrator
 */
public class AOIPanel implements Observer{
    private EyeTrackerCalibrationUDPClient eyeTrackerCalibrationUDPClient;
    private Point2f[] aoiCoordinates = new Point2f[42];
    private int width = 0;
    private int height = 0;
    private Marker marker = new Marker();
    
    public AOIPanel() {
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public Point2f[] getAoiCoordinates() {
        return aoiCoordinates;
    }
    
    public void setAoiCoordinates(Point2f[] aoiCoordinates) {
        this.aoiCoordinates = aoiCoordinates;
    }
    
    public void update(Observable o, Object arg) {
        eyeTrackerCalibrationUDPClient = (EyeTrackerCalibrationUDPClient)o;
    }
    
    public void renderGraphics(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0, getWidth(), getHeight());
        if(eyeTrackerCalibrationUDPClient!=null&&eyeTrackerCalibrationUDPClient.getCurrentAOI()!=0){
            int x = (int)(getWidth()*aoiCoordinates[eyeTrackerCalibrationUDPClient.getCurrentAOI()-1].getX());
            int y = (int)(getHeight()*aoiCoordinates[eyeTrackerCalibrationUDPClient.getCurrentAOI()-1].getY());
            g2.setColor(Color.WHITE);
            marker.setX(x);
            marker.setY(y);
            marker.render(g2);
        }
    }
    
    class Marker{
        private Color lightBlue = new Color(99,189,227);
        private int width = 25;
        private int height = 25;
        private int x;
        private int y;
        
        public Marker(){
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void render(Graphics2D g2d){
            g2d.setColor(lightBlue);
            g2d.fillOval(getX()-getWidth()/2,getY()-getHeight()/2,getWidth(),getHeight());
            g2d.setColor(Color.RED);
            
            g2d.fillOval(getX()-(int)((getWidth()*0.5f)/2),getY()-(int)((getHeight()*0.5f)/2),(int)(getWidth()*0.5f),(int)(getHeight()*0.5f));
        }
    }
}