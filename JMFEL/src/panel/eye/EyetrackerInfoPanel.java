/*
 * TextPanel.java
 *
 * Created on 16. Mai 2007, 14:15
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package panel.eye;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import utilities.math.Rounding;
import visual_signal_components.passive_rendering.AbstractTextPanel;

/**
 *
 * @author Administrator
 */
public class EyetrackerInfoPanel extends AbstractTextPanel{
    
    private double xPosition = 0.0;
    private double yPosition = 0.0;
    private double zPosition = 0.0;
    private double pitch = 0.0;
    private double roll = 0.0;
    private double yaw = 0.0;
    
    public EyetrackerInfoPanel() {
        setWidth(180);
        setHeight(100);
    }
    
    public double getPitch() {
        return pitch;
    }
    
    public double getRoll() {
        return roll;
    }
    
    public double getXPosition() {
        return xPosition;
    }
    
    public double getYPosition() {
        return yPosition;
    }
    
    public double getYaw() {
        return yaw;
    }
    
    public double getZPosition() {
        return zPosition;
    }
    
    public void setPitch(double pitch) {
        this.pitch = pitch;
    }
    
    public void setRoll(double roll) {
        this.roll = roll;
    }
    
    public void setXPosition(double xPosition) {
        this.xPosition = xPosition;
    }
    
    public void setYPosition(double yPosition) {
        this.yPosition = yPosition;
    }
    
    public void setYaw(double yaw) {
        this.yaw = yaw;
    }
    
    public void setZPosition(double zPosition) {
        this.zPosition = zPosition;
    }
    
    public void renderGraphics(Graphics2D g2) {
        
        int stringHeight = getStringHelper().getStringHeight(getText());
        int stringWidth = getStringHelper().getStringWidth(g2.getFont(),getText());
        g2.setColor(Color.LIGHT_GRAY);
        Composite originalComposite = g2.getComposite();
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f);
        g2.setComposite(ac);
        g2.setColor(Color.DARK_GRAY);
        g2.drawRoundRect(getX()+2,getY()+2,getWidth(),getHeight(),8,8);
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRoundRect(getX(),getY(),getWidth(),getHeight(),8,8);
        g2.setComposite(originalComposite);
        g2.setColor(getColor());
        g2.drawRoundRect(getX(),getY(),getWidth(),getHeight(),8,8);
        
        g2.drawString("Head position and orientation",getX()+10,getY()+15);
        
        g2.drawString("X:   "+Rounding.round(getXPosition(),2),getX()+10,getY()+25+getStringHelper().getStringHeight("X: "));
        g2.drawString("cm",getX()+65,getY()+25+getStringHelper().getStringHeight("X: "));
        
        g2.drawString("Y:   "+Rounding.round(getYPosition(),2),getX()+10,getY()+25+getStringHelper().getStringHeight("X: ")*2);
        g2.drawString("cm",getX()+65,getY()+25+getStringHelper().getStringHeight("X: ")*2);
        
        g2.drawString("Z:   "+Rounding.round(getZPosition(),2),getX()+10,getY()+25+getStringHelper().getStringHeight("X: ")*3);
        g2.drawString("cm",getX()+65,getY()+25+getStringHelper().getStringHeight("X: ")*3);
        
        g2.drawString("Pitch:",getX()+95,getY()+25+getStringHelper().getStringHeight("X: "));
        g2.drawString(String.valueOf(Rounding.round(getPitch(),2)),getX()+125,getY()+25+getStringHelper().getStringHeight("X: "));
        
        g2.drawString("°",getX()+165,getY()+25+getStringHelper().getStringHeight("X: "));
        
        g2.drawString("Yaw:",getX()+95,getY()+25+getStringHelper().getStringHeight("X: ")*2);
        g2.drawString(String.valueOf(Rounding.round(getYaw(),2)),getX()+125,getY()+25+getStringHelper().getStringHeight("X: ")*2);
        
        g2.drawString("°",getX()+165,getY()+25+getStringHelper().getStringHeight("X: ")*2);
        
        g2.drawString("Roll:",getX()+95,getY()+25+getStringHelper().getStringHeight("X: ")*3);
        g2.drawString(String.valueOf(Rounding.round(getRoll(),2)),getX()+125,getY()+25+getStringHelper().getStringHeight("X: ")*3);
        
        g2.drawString("°",getX()+165,getY()+25+getStringHelper().getStringHeight("X: ")*3);
    }
}