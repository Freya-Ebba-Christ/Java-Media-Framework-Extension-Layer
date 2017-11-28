/*
 * GazePanel.java
 *
 * Created on 24. Mai 2007, 16:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package panel.eye;

import eye.insight.InsightDatagramPacketDecoder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import utilities.Point2f;
import utilities.graphics.CoordinateConverter;
import visual_signal_components.passive_rendering.AbstractTextPanel;

/**
 *
 * @author Administrator
 */

public class GazePanel extends AbstractTextPanel{
    
    private int x = 0;
    private int y = 0;
    private Color darkBlue = new Color(71,71,173);
    private Dimension dimension;
    private CoordinateConverter coordinateConverter;
    private InsightDatagramPacketDecoder datagramdecoder;
    private Point cursorPosition = new Point(0,0);
    private Point2f[] aoiCoordinates = new Point2f[42];
    private Marker marker;
    
    /** Creates a new instance of GazePanel */
    
    public GazePanel() {
        coordinateConverter = new CoordinateConverter();
        marker = new Marker();
    }

    public Point2f[] getAOICoordinates() {
        return aoiCoordinates;
    }

    public void setAOICoordinates(Point2f[] aoiCoordinates) {
        this.aoiCoordinates = aoiCoordinates;
    }
    
    public InsightDatagramPacketDecoder getDatagramdecoder() {
        return datagramdecoder;
    }
    
    public void setDatagramdecoder(InsightDatagramPacketDecoder datagramdecoder) {
        this.datagramdecoder = datagramdecoder;
    }
    
    public void setCoordinateConverter(CoordinateConverter coordinateConverter) {
        this.coordinateConverter = coordinateConverter;
    }
    
    public CoordinateConverter getCoordinateConverter() {
        return coordinateConverter;
    }
    
    public Point getCursorPosition() {
        return cursorPosition;
    }
    
    public void setCursorPosition(Point cursorPosition) {
        this.cursorPosition = cursorPosition;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public void renderGraphics(Graphics2D g2) {
        g2.setColor(getColor());
        g2.setColor(Color.LIGHT_GRAY);
        g2.setColor(Color.DARK_GRAY);
        g2.drawRoundRect(getX()+2,getY()+2,getWidth(),getHeight(),8,8);
        try{
            g2.setColor(Color.WHITE);
            setCursorPosition(getCoordinateConverter().millimeterToPixel(
                    getDatagramdecoder().getCombinedGazeVectorFocalPlaneIntersectionX(),
                    getDatagramdecoder().getCombinedGazeVectorFocalPlaneIntersectionY()
                    ));
            int x = (int)getCursorPosition().getX()+getX();
            int y = (int)getCursorPosition().getY()+getY();
            g2.drawLine(getX(),y,getWidth()+getX(),y);
            g2.drawLine(x,getY(),x,getHeight()+getY());
        }catch(Exception e){};
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(getX(),getY(),getWidth(),getHeight(),8,8);
        
        for(int i = 0; i<aoiCoordinates.length;i++){
            marker.setX((int)(aoiCoordinates[i].getX()*getWidth()));
            marker.setY((int)(aoiCoordinates[i].getY()*getHeight()));
            marker.render(g2);
        }
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
        g2d.drawLine(getX()-getWidth(),getY(),getX()+getWidth(),getY());
        g2d.drawLine(getX(),getY()-getHeight(),getX(),getY()+getHeight());
    }
}