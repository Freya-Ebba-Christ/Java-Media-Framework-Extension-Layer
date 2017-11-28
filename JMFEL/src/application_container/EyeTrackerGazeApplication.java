/*
 * EyeTrackerGazeApplication.java
 *
 * Created on 24. August 2007, 20:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package application_container;

import utilities.graphics.passive_rendering.ApplicationContainer;
import utilities.graphics.passive_rendering.Surface;
import application_container.painter.ClockPainter;
import application_container.painter.GazeDataPainter;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.Properties;
import utilities.CSVReader;
import utilities.Point2f;
import utilities.graphics.BasicImageUtility;

/**
 *
 * @author Administrator
 */

public class EyeTrackerGazeApplication extends ApplicationContainer{
    private String backGround;
    private GazeDataPainter gazeDataPainter;
    
    public String getBackGround() {
        return backGround;
    }
    
    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }
    
    public GazeDataPainter getGazeDataPainter() {
        return gazeDataPainter;
    }
    
    public void setGazeDataPainter(GazeDataPainter gazeDataPainter) {
        this.gazeDataPainter = gazeDataPainter;
    }
    
    public void init(){
        String file_separator = System.getProperty("file.separator");
        //this enables that the last location known is stored to disk and restored next time...
        setRestoreLocationEnabled(true);
        setLocationINIPath(System.getProperty("user.dir")+file_separator+"application_container"+file_separator+"gazeTracking.ini");
        
        BufferedImage image = BasicImageUtility.getInstance().load(System.getProperty("user.dir")+"\\resources\\"+getBackGround(),BufferedImage.TYPE_3BYTE_BGR);
        Surface surface = new Surface();
        surface.setSize(image.getWidth(),image.getHeight());
        surface.setPreferredSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setMinimumSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setMaximumSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setCycles(20);
        surface.setBackgroundImage(BasicImageUtility.getInstance().toCompatibleImage(BasicImageUtility.getInstance().scale(image,1.0,1.0)));
        ClockPainter aClockPainter = new ClockPainter();
        aClockPainter.getClockPanel().setX(20);
        aClockPainter.getClockPanel().setY(20);
        surface.addPainter(aClockPainter);
        surface.setFPSEnabled(true);
        
        gazeDataPainter = new GazeDataPainter();
        gazeDataPainter.getGazePanel().setX(0);
        gazeDataPainter.getGazePanel().setY(0);
        gazeDataPainter.getGazePanel().setWidth(surface.getWidth());
        gazeDataPainter.getGazePanel().setHeight(surface.getHeight());
        surface.addPainter(gazeDataPainter);
        
        Properties props = new Properties();
        FileInputStream in = null;
        
        try{
            in = new FileInputStream(System.getProperty("user.dir") + file_separator + "media" + file_separator + "protocol" + file_separator + "eye" + file_separator + "config.ini");
        }catch(java.io.FileNotFoundException fnfex){System.err.println(fnfex);
        };
        try{
            props.load(in);
            in.close();
        }catch(java.io.IOException ioex){System.err.println(ioex);
        };
        
        gazeDataPainter.getGazePanel().getCoordinateConverter().setScreenWidth(surface.getWidth());
        gazeDataPainter.getGazePanel().getCoordinateConverter().setScreenHeight(surface.getHeight());
        gazeDataPainter.getGazePanel().getCoordinateConverter().setHorizontalMaximum(Integer.parseInt((String)props.getProperty("HORIZONTAL_MAXIMUM")));
        gazeDataPainter.getGazePanel().getCoordinateConverter().setHorizontalMinimum(Integer.parseInt((String)props.getProperty("HORIZONTAL_MINIMUM")));
        gazeDataPainter.getGazePanel().getCoordinateConverter().setVerticalMaximum(Integer.parseInt((String)props.getProperty("VERTICAL_MAXIMUM")));
        gazeDataPainter.getGazePanel().getCoordinateConverter().setVerticalMinimum(Integer.parseInt((String)props.getProperty("VERTICAL_MINIMUM")));
        
        CSVReader csvReader = new CSVReader();
        csvReader.init(42,2);
        csvReader.read(System.getProperty("user.dir")+file_separator+"adracer"+file_separator+"calibration"+file_separator+"eye"+file_separator+"aoi_positions.csv");
        Point2f[] coords = new Point2f[42];
        for(int y = 0; y < csvReader.getNumRows(); y++) {
            coords[y] = new Point2f((float)csvReader.getNumbers()[y][0],(float)csvReader.getNumbers()[y][1]);
        }
        gazeDataPainter.getGazePanel().setAOICoordinates(coords);
        
        surface.start();
        setVisualComponent(surface);
        super.init();
    }
    
    /** Creates a new instance of EyeTrackerApplication */
    public EyeTrackerGazeApplication() {
    }
    
    public static void main(String[] args) {
        System.setProperty("sun.java2d.translaccel","true");
        EyeTrackerGazeApplication aEyeTrackerGazeApplication = new EyeTrackerGazeApplication();
        aEyeTrackerGazeApplication.setBackGround("background_gaze_measurement.jpg");
        aEyeTrackerGazeApplication.init();
        aEyeTrackerGazeApplication.setVisible(true);
    }
}