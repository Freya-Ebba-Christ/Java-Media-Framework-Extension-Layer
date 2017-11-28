/*
 * DBExportStateApplication.java
 *
 * Created on 3. September 2007, 06:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package adracer.application_container;

import utilities.graphics.passive_rendering.ApplicationContainer;
import utilities.graphics.passive_rendering.Surface;
import application_container.painter.ClockPainter;
import application_container.painter.ExportStatePainter;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import utilities.graphics.BasicImageUtility;

/**
 *
 * @author Administrator
 */
public class DBExportStateApplication extends ApplicationContainer{
    private String backGround;
    private ClockPainter clockPainter;
    private ExportStatePainter exportStatePainter;
    
    public String getBackGround() {
        return backGround;
    }

    public ClockPainter getClockPainter() {
        return clockPainter;
    }

    public ExportStatePainter getExportStatePainter() {
        return exportStatePainter;
    }

    public void setExportStatePainter(ExportStatePainter exportStatePainter) {
        this.exportStatePainter = exportStatePainter;
    }
    
    public void setClockPainter(ClockPainter clockPainter) {
        this.clockPainter = clockPainter;
    }
    
    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }
    
    public void init(){
        BufferedImage image = BasicImageUtility.getInstance().load(System.getProperty("user.dir")+"\\resources\\"+getBackGround(),BufferedImage.TYPE_3BYTE_BGR);
        Surface surface = new Surface();
        surface.setSize(image.getWidth(),image.getHeight());
        surface.setPreferredSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setMinimumSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setMaximumSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setCycles(20);
        surface.setBackgroundImage(BasicImageUtility.getInstance().toCompatibleImage(BasicImageUtility.getInstance().scale(image,1.0,1.0)));
        clockPainter = new ClockPainter();
        clockPainter.getClockPanel().setX(10);
        clockPainter.getClockPanel().setY(10);
        surface.addPainter(clockPainter);
        
        exportStatePainter = new ExportStatePainter();
        exportStatePainter.getExportStatePanel().setX(10);
        exportStatePainter.getExportStatePanel().setY(60);
        exportStatePainter.getExportStatePanel().setWidth(300);
        exportStatePainter.getExportStatePanel().setHeight(35);
        surface.addPainter(exportStatePainter);
        
        surface.setFPSEnabled(true);
        surface.start();
        setVisualComponent(surface);
        super.init();
    }
    
    public DBExportStateApplication() {
    }
    
    public static void main(String[] args) {
        System.setProperty("sun.java2d.translaccel","true");
        DBExportStateApplication aDBExportStateApplication = new DBExportStateApplication();
        aDBExportStateApplication.setBackGround("background_export_state_monitor.jpg");
        aDBExportStateApplication.init();
        aDBExportStateApplication.setVisible(true);
    }
}