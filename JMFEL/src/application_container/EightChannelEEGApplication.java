/*
 * EightChannelEEGApplicationava
 *
 * Created on 11. Dezember 2007, 18:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package application_container;

import application_container.painter.EightChannelEEGPainter;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import utilities.graphics.BasicImageUtility;
import utilities.graphics.passive_rendering.ApplicationContainer;
import utilities.graphics.passive_rendering.Surface;

/**
 *
 * @author Administrator
 */
public class EightChannelEEGApplication extends ApplicationContainer{
    
    private String backGround;
    private EightChannelEEGPainter multiChannelEEGPainter;
    
    public String getBackGround() {
        return backGround;
    }
    
    public EightChannelEEGPainter getMultiChannelEEGPainter() {
        return multiChannelEEGPainter;
    }
    
    public void setMultiChannelEEGPainter(EightChannelEEGPainter multiChannelEEGPainter) {
        this.multiChannelEEGPainter = multiChannelEEGPainter;
    }
    
    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }
    
    public void init(){
        String file_separator = System.getProperty("file.separator");
        //this enables that the last location known is stored to disk and restored next time...
        setRestoreLocationEnabled(true);
        setLocationINIPath(System.getProperty("user.dir")+file_separator+"application_container"+file_separator+"large16ChannelEEG.ini");
        
        BufferedImage image = BasicImageUtility.getInstance().load(System.getProperty("user.dir")+"\\resources\\"+getBackGround(),BufferedImage.TYPE_3BYTE_BGR);
        Surface surface = new Surface();
        surface.setSize(image.getWidth(),image.getHeight());
        surface.setPreferredSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setMinimumSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setMaximumSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setCycles(20);
        surface.setBackgroundImage(BasicImageUtility.getInstance().toCompatibleImage(BasicImageUtility.getInstance().scale(image,1.0,1.0)));
        multiChannelEEGPainter = new EightChannelEEGPainter();
        multiChannelEEGPainter.getPanel().setX(10);
        multiChannelEEGPainter.getPanel().setY(10);
        multiChannelEEGPainter.getPanel().setWidth(surface.getWidth()-20);
        multiChannelEEGPainter.getPanel().setHeight(surface.getHeight()-20);
        multiChannelEEGPainter.getPanel().initSignalPanel();
        surface.addPainter(multiChannelEEGPainter);
        surface.setFPSEnabled(true);
        surface.start();
        setVisualComponent(surface);
        super.init();
    }
    
    public EightChannelEEGApplication(){
    }
    
    public static void main(String[] args) {
        //System.setProperty("sun.java2d.translaccel","true");
        EightChannelEEGApplication aEightChannelEEGApplication = new EightChannelEEGApplication();
        aEightChannelEEGApplication.setBackGround("background_large16ChannelEEG_measurement.jpg");
        aEightChannelEEGApplication.init();
        aEightChannelEEGApplication.setVisible(true);
    }
}