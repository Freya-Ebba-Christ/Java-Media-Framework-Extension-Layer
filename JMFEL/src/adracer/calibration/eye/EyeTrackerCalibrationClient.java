/*
 * EyeTrackerCalibrationClientGUI.java
 *
 * Created on 25. September 2007, 13:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package adracer.calibration.eye;

import utilities.graphics.active_rendering.ApplicationContainer;
import adracer.tools.AdracerPropertiesReader;
import java.awt.image.BufferedImage;
import java.util.Properties;
import utilities.CSVReader;
import utilities.Point2f;
import utilities.PropertiesReader;

/**
 *
 * @author Administrator
 */
public class EyeTrackerCalibrationClient extends ApplicationContainer{
    private Point2f[] coords = new Point2f[42];

    public EyeTrackerCalibrationClient() {
    }
    
    public EyeTrackerCalibrationClient(int width, int height, boolean fullscreen) {
        super(width,height, fullscreen);
        init(width,height);
        start();
    }
    
    public EyeTrackerCalibrationClient(BufferedImage image, boolean fullscreen){
        super(image.getWidth(),image.getHeight(),fullscreen);
        init(image.getWidth(),image.getHeight());
        start();
    }
    
    public void init(int width, int height){
        EyeTrackerCalibrationUDPClient aEyeTrackerCalibrationUDPClient = new EyeTrackerCalibrationUDPClient();
        getSurface().setFPSEnabled(true);
        
        CSVReader csvReader = new CSVReader();
        csvReader.init(42,2);
        String file_separator = System.getProperty("file.separator");
        csvReader.read(System.getProperty("user.dir")+file_separator+"adracer"+file_separator+"calibration"+file_separator+"eye"+file_separator+"aoi_positions.csv");
        coords = new Point2f[42];
        for(int y = 0; y < csvReader.getNumRows(); y++) {
            coords[y] = new Point2f((float)csvReader.getNumbers()[y][0],(float)csvReader.getNumbers()[y][1]);
        }
        
        AOIPainter aoiPainter = new AOIPainter();
        aoiPainter.getPanel().setAoiCoordinates(coords);
        aoiPainter.getPanel().setWidth(width);
        aoiPainter.getPanel().setHeight(height);
        aEyeTrackerCalibrationUDPClient.addObserver(aoiPainter.getPanel());
        getSurface().addPainter(aoiPainter);
    }
    
    public static void main( String args[] ) {
        String file_separator = System.getProperty("file.separator");
        PropertiesReader reader = new PropertiesReader();
        Properties props = reader.readProperties(System.getProperty("user.dir")+file_separator+"adracer"+file_separator+"calibration"+file_separator+"eye"+file_separator+"client.ini");
        int width = Integer.valueOf(props.getProperty("WIDTH"));
        int height = Integer.valueOf(props.getProperty("HEIGHT"));
        if(props.getProperty("FULLSCREEN").equalsIgnoreCase("TRUE")){
            EyeTrackerCalibrationClient gui = new EyeTrackerCalibrationClient(width,height,true);
        }else{
            EyeTrackerCalibrationClient gui = new EyeTrackerCalibrationClient(width,height,false);
        }
    }
}