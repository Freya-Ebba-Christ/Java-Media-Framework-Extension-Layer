/*
 * CalibrationTool.java
 *
 * Created on 6. Juni 2007, 16:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package eye.insight.calibration.eye;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import javax.swing.JButton;
import utilities.CSVReader;
import utilities.PropertiesReader;
import utilities.graphics.BasicFrameMouseInputAdapter;

/**
 *
 * @author Administrator
 */

public class EyeTrackerCalibrator {
    
    private EyeTrackerCalibrationGUI aEyeTrackerCalibrationGUI;
    private CSVReader aCSVReader;
    private double[][] scene;
    private boolean visible = false;
    private PropertiesReader propertiesReader;
    
    public EyeTrackerCalibrator() {
    }
    
    class AOIEventHandler implements ActionListener{
        private int lastAOI = 0;
        private int currentArea = 0;
        
        public AOIEventHandler(){
        }
        
        public void actionPerformed(ActionEvent e) {
            currentArea = Integer.parseInt(((JButton)e.getSource()).getActionCommand());
            if(lastAOI == currentArea){
                currentArea = 0;
            }
            aEyeTrackerCalibrationGUI.getEyeTrackerUDPSendServer().setCurrentArea(currentArea);
            aEyeTrackerCalibrationGUI.getEyeTrackerUDPSendServer().setNewDataAvailable(true);
            lastAOI = currentArea;
        }
    }
    
    public double[][] getScene() {
        return scene;
    }

    public boolean isVisible() {
        return visible;
    }
    
    public void setScene(double[][] scene) {
        this.scene = scene;
    }
    
    public void readSceneFile(String file){
        aCSVReader = new CSVReader();
        aCSVReader.init(6,7);
        aCSVReader.read(file);
        setScene(aCSVReader.getNumbers());
    }
    
    public void hideGUI(){
        visible=false;
        aEyeTrackerCalibrationGUI.getEyeTrackerUDPSendServer().stopServer();
        aEyeTrackerCalibrationGUI.dispose();
    }
    
    private void hideButtons(){
        int aoi = 0;
        for (int r = 0; r < aCSVReader.getNumRows(); r++) {
            for (int c = 0; c < aCSVReader.getNumColumns(); c++) {
                if(getScene()[r][c]==0){
                    aEyeTrackerCalibrationGUI.getAOI(aoi).setVisible(false);
                }
                aoi++;
            }
        }
    }
    
    public void showGUI(){
        if(!visible){
            propertiesReader = new PropertiesReader();
            AOIEventHandler aAOIEventHandler = new AOIEventHandler();
            aEyeTrackerCalibrationGUI = new EyeTrackerCalibrationGUI();
            String file_separator = System.getProperty("file.separator");
            Properties props = propertiesReader.readProperties(System.getProperty("user.dir")+file_separator+"adracer"+file_separator+"calibration"+file_separator+"eye"+file_separator+"server.ini");
            aEyeTrackerCalibrationGUI.getEyeTrackerUDPSendServer().setToIP(props.getProperty("IP"));
            aEyeTrackerCalibrationGUI.getEyeTrackerUDPSendServer().setToPort(Integer.parseInt(props.getProperty("PORT")));
            aEyeTrackerCalibrationGUI.getEyeTrackerUDPSendServer().startServer();
            
            aEyeTrackerCalibrationGUI.setSize(790,650);
            aEyeTrackerCalibrationGUI.setEyeTrackerCalibrator(this);
            for (int i = 0; i < aEyeTrackerCalibrationGUI.getNumAOIs(); i++) {
                aEyeTrackerCalibrationGUI.getAOI(i).addActionListener(aAOIEventHandler);
            }
            BasicFrameMouseInputAdapter aBasicFrameMouseInputAdapter = new BasicFrameMouseInputAdapter();
            aBasicFrameMouseInputAdapter.setFrame(aEyeTrackerCalibrationGUI);
            aEyeTrackerCalibrationGUI.addMouseListener(aBasicFrameMouseInputAdapter);
            aEyeTrackerCalibrationGUI.addMouseMotionListener(aBasicFrameMouseInputAdapter);
            aEyeTrackerCalibrationGUI.center();
            readSceneFile(System.getProperty("user.dir")+file_separator+"adracer"+file_separator+"calibration"+file_separator+"eye"+file_separator+"scene.csv");
            hideButtons();
            visible = true;
            aEyeTrackerCalibrationGUI.setVisible(true);
        }
    }
    
    public static void main(String [] args) {
        EyeTrackerCalibrator aEyeTrackerCalibrator = new EyeTrackerCalibrator();
        aEyeTrackerCalibrator.showGUI();
    }
}