/*
 * MultichannelEEGApplication.java
 *
 * Created on 27. August 2007, 15:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package application_container;

import utilities.graphics.passive_rendering.ApplicationContainer;
import utilities.graphics.passive_rendering.Surface;
import application_container.painter.MultiChannelEEGPainter;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import utilities.graphics.BasicImageUtility;

/**
 *
 * @author Administrator
 */
public class MultichannelEEGApplication extends ApplicationContainer{
    
    private String backGround;
    private MultiChannelEEGPainter multiChannelEEGPainter;
    
    public String getBackGround() {
        return backGround;
    }
    
    public MultiChannelEEGPainter getMultiChannelEEGPainter() {
        return multiChannelEEGPainter;
    }
    
    public void setMultiChannelEEGPainter(MultiChannelEEGPainter multiChannelEEGPainter) {
        this.multiChannelEEGPainter = multiChannelEEGPainter;
    }
    
    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }
    
    public void init(){
        String file_separator = System.getProperty("file.separator");
        //this enables that the last location known is stored to disk and restored next time...
        setRestoreLocationEnabled(true);
        setLocationINIPath(System.getProperty("user.dir")+file_separator+"application_container"+file_separator+"multichannelEEG.ini");
        
        BufferedImage image = BasicImageUtility.getInstance().load(System.getProperty("user.dir")+"\\resources\\"+getBackGround(),BufferedImage.TYPE_3BYTE_BGR);
        Surface surface = new Surface();
        surface.setSize(image.getWidth(),image.getHeight());
        surface.setPreferredSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setMinimumSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setMaximumSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setCycles(20);
        surface.setBackgroundImage(BasicImageUtility.getInstance().toCompatibleImage(BasicImageUtility.getInstance().scale(image,1.0,1.0)));
        multiChannelEEGPainter = new MultiChannelEEGPainter();
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
    
    /** Creates a new instance of EyeTrackerApplication */
    public MultichannelEEGApplication() {
    }
    
    
    public static void main(String[] args) {
        //System.setProperty("sun.java2d.translaccel","true");
        MultichannelEEGApplication aMultichannelEEGApplication = new MultichannelEEGApplication();
        aMultichannelEEGApplication.setBackGround("background_multichannel_measurement.jpg");
        aMultichannelEEGApplication.init();
        aMultichannelEEGApplication.setVisible(true);
    }
}