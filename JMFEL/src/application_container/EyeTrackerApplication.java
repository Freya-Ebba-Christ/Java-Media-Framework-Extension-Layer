/*
 * EyeTrackerApplication.java
 *
 * Created on 24. August 2007, 17:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package application_container;

import utilities.graphics.passive_rendering.ApplicationContainer;
import utilities.graphics.passive_rendering.Surface;
import application_container.painter.BlinkInfoPainter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import utilities.graphics.BasicImageUtility;

/**
 *
 * @author Administrator
 */
public class EyeTrackerApplication extends ApplicationContainer{
    
    private String backGround;
    private BlinkInfoPainter blinkInfoPainter;
    
    public String getBackGround() {
        return backGround;
    }
    
    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }
    
    public BlinkInfoPainter getBlinkInfoPainter() {
        return blinkInfoPainter;
    }
    
    public void setBlinkInfoPainter(BlinkInfoPainter blinkInfoPainter) {
        this.blinkInfoPainter = blinkInfoPainter;
    }
    
    public void init(){
        String file_separator = System.getProperty("file.separator");
        //this enables that the last location known is stored to disk and restored next time...
        setRestoreLocationEnabled(true);
        setLocationINIPath(System.getProperty("user.dir")+file_separator+"application_container"+file_separator+"eyeTracking.ini");
        
        BufferedImage image = BasicImageUtility.getInstance().load(System.getProperty("user.dir")+"\\resources\\"+getBackGround(),BufferedImage.TYPE_3BYTE_BGR);
        Surface surface = new Surface();
        surface.setSize(image.getWidth(),image.getHeight());
        surface.setPreferredSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setMinimumSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setMaximumSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setCycles(20);
        surface.setBackgroundImage(BasicImageUtility.getInstance().toCompatibleImage(BasicImageUtility.getInstance().scale(image,1.0,1.0)));
        
        blinkInfoPainter = new BlinkInfoPainter();
        
        blinkInfoPainter.getInfoPanel().setWidth(surface.getWidth()-20);
        blinkInfoPainter.getInfoPanel().setHeight(surface.getHeight()-20);
        blinkInfoPainter.getInfoPanel().setX(10);
        blinkInfoPainter.getInfoPanel().setY(10);
        blinkInfoPainter.getInfoPanel().setColor(Color.WHITE);
        blinkInfoPainter.getInfoPanel().getSignalPanel().setMaxVoltage(10.0f);
        blinkInfoPainter.getInfoPanel().getSignalPanel().setSampleRate(60);
        blinkInfoPainter.getInfoPanel().getSignalPanel().setBufferLength(4*60);
        blinkInfoPainter.getInfoPanel().getSignalPanel().setTitleColor(Color.BLACK);
        blinkInfoPainter.getInfoPanel().getSignalPanel().setSignalColor(Color.RED);
        blinkInfoPainter.getInfoPanel().getSignalPanel().setBorderColor(Color.WHITE);
        surface.addPainter(blinkInfoPainter);
        surface.start();
        setVisualComponent(surface);
        super.init();
    }
    
    /** Creates a new instance of EyeTrackerApplication */
    public EyeTrackerApplication() {
    }
    
    
    public static void main(String[] args) {
        System.setProperty("sun.java2d.translaccel","true");
        EyeTrackerApplication aEyeTrackerApplication = new EyeTrackerApplication();
        aEyeTrackerApplication.setBackGround("background_blink_measurement.jpg");
        aEyeTrackerApplication.init();
        aEyeTrackerApplication.setVisible(true);
    }
}
