/*
 * ECGApplication.java
 *
 * Created on 15. November 2007, 15:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package application_container;

import application_container.painter.ECGPainter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import utilities.graphics.BasicImageUtility;
import utilities.graphics.passive_rendering.ApplicationContainer;
import utilities.graphics.passive_rendering.Surface;

/**
 *
 * @author Administrator
 */

public class ECGApplication extends ApplicationContainer{

    private String backGround;
    private ECGPainter ecgPainter;
    private int ecgChannel = 0;
    public ECGApplication() {
    }
    
    public String getBackGround() {
        return backGround;
    }
    
    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }

    public int getECGChannel() {
        return ecgChannel;
    }

    public void setECGChannel(int ecgChannel) {
        this.ecgChannel = ecgChannel;
    }

    public ECGPainter getECGPainter() {
        return ecgPainter;
    }

    public void setECGPainter(ECGPainter ecgPainter) {
        this.ecgPainter = ecgPainter;
    }
    
    public void init() {
        String file_separator = System.getProperty("file.separator");
        //this enables that the last location known is stored to disk and restored next time...
        setRestoreLocationEnabled(true);
        setLocationINIPath(System.getProperty("user.dir")+file_separator+"application_container"+file_separator+"ecg.ini");
        
        BufferedImage image = BasicImageUtility.getInstance().load(System.getProperty("user.dir")+"\\resources\\"+getBackGround(),BufferedImage.TYPE_3BYTE_BGR);
        Surface surface = new Surface();
        surface.setSize(image.getWidth(),image.getHeight());
        surface.setPreferredSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setMinimumSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setMaximumSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setCycles(20);
        
        ecgPainter = new ECGPainter();
        ecgPainter.getPanel().setWidth(surface.getWidth()-20);
        ecgPainter.getPanel().setHeight(surface.getHeight()-20);
        ecgPainter.getPanel().setX(10);
        ecgPainter.getPanel().setY(10);
        ecgPainter.getPanel().setECGChannel(getECGChannel());
        ecgPainter.getPanel().getSignalPanel().setMaxVoltage(25.0f);
        ecgPainter.getPanel().getSignalPanel().setSampleRate(1024);
        ecgPainter.getPanel().getSignalPanel().setBufferLength(1024);
        ecgPainter.getPanel().getSignalPanel().setTitleColor(Color.BLACK);
        ecgPainter.getPanel().getSignalPanel().setSignalColor(Color.RED);
        ecgPainter.getPanel().getSignalPanel().setBorderColor(Color.WHITE);
        
        surface.addPainter(ecgPainter);
        surface.setBackgroundImage(BasicImageUtility.getInstance().toCompatibleImage(BasicImageUtility.getInstance().scale(image,1.0,1.0)));
        surface.setFPSEnabled(true);
        surface.start();
        setVisualComponent(surface);
        super.init();
    }
    
    public static void main(String[] args) {
        System.setProperty("sun.java2d.translaccel","true");
        ECGApplication aECGApplication = new ECGApplication();
        aECGApplication.setBackGround("background_singleChannel.jpg");
        aECGApplication.init();
        aECGApplication.setVisible(true);
    }
}