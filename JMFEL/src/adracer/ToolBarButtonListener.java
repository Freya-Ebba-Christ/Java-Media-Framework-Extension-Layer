/*
 * ToolBarButtonListener.java
 *
 * Created on 16. August 2007, 19:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package adracer;

import adracer.tools.AdracerPropertiesReader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;


/**
 *
 * @author Administrator
 */
public class ToolBarButtonListener implements ActionListener{
    private AdracerApplications adracerApplications;
    private AdracerPropertiesReader propertiesReader = new AdracerPropertiesReader();
    
    public ToolBarButtonListener() {
    }
    
    public AdracerApplications getAdracerApplications() {
        return adracerApplications;
    }
    
    public void setAdracerApplications(AdracerApplications adracerApplications) {
        this.adracerApplications = adracerApplications;
    }
    
    public void actionPerformed(ActionEvent e) {
        
        if(e.getActionCommand().equalsIgnoreCase(TopMenuFrame.EYETRACKER_BUTTON)){
            if(!getAdracerApplications().getEyeTrackerApplication().isVisible()){
                getAdracerApplications().getEyeTrackerApplication().setVisible(true);
            }else{getAdracerApplications().getEyeTrackerApplication().setVisible(false);
            }
            getAdracerApplications().getEyeTrackerApplication().setState(JFrame.NORMAL);
            
        }else if (e.getActionCommand().equalsIgnoreCase(TopMenuFrame.MULTICHANNEL_EEG_BUTTON)){
            if(!getAdracerApplications().getMultichannelEEGApplication().isVisible()){
                getAdracerApplications().getMultichannelEEGApplication().setVisible(true);
            }else{
                getAdracerApplications().getMultichannelEEGApplication().setVisible(false);
            }
            getAdracerApplications().getMultichannelEEGApplication().setState(JFrame.NORMAL);
            
        }else if (e.getActionCommand().equalsIgnoreCase(TopMenuFrame.SINGLE_CHANNEL_EEG_BUTTON)){
            if(!getAdracerApplications().getSingleEEGChannelContainer().isVisible()){
                getAdracerApplications().getSingleEEGChannelContainer().setVisible(true);
            }else {
                getAdracerApplications().getSingleEEGChannelContainer().setVisible(false);
            }
            getAdracerApplications().getSingleEEGChannelContainer().setState(JFrame.NORMAL);
        }else if (e.getActionCommand().equalsIgnoreCase(TopMenuFrame.SUBBAND_EEG_BUTTON)){
            if(!getAdracerApplications().getEEGMultibandApplication().isVisible()){
                getAdracerApplications().getEEGMultibandApplication().setVisible(true);
            }else {
                getAdracerApplications().getEEGMultibandApplication().setVisible(false);
            }
            getAdracerApplications().getEEGMultibandApplication().setState(JFrame.NORMAL);
        }else if (e.getActionCommand().equalsIgnoreCase(TopMenuFrame.ENERGY_BANDS_BUTTON)){
            
            if(!getAdracerApplications().getEEGEnergyApplication().isVisible()){
                getAdracerApplications().getEEGEnergyApplication().setVisible(true);
            }else {
                getAdracerApplications().getEEGEnergyApplication().setVisible(false);
            }
            getAdracerApplications().getEEGEnergyApplication().setState(JFrame.NORMAL);
        }else if (e.getActionCommand().equalsIgnoreCase(TopMenuFrame.POWER_BANDS_BUTTON)){
            if(!getAdracerApplications().getEEGPowerApplication().isVisible()){
                getAdracerApplications().getEEGPowerApplication().setVisible(true);
            }else {
                getAdracerApplications().getEEGPowerApplication().setVisible(false);
            }
            getAdracerApplications().getEEGPowerApplication().setState(JFrame.NORMAL);
        }else if (e.getActionCommand().equalsIgnoreCase(TopMenuFrame.CAMERA_BUTTON)){
            if(!getAdracerApplications().getVideoPlayerApplication().getVideoPlayer().isVisible()){
                getAdracerApplications().getVideoPlayerApplication().getVideoPlayer().setVisible(true);
            }else{
                getAdracerApplications().getVideoPlayerApplication().getVideoPlayer().setVisible(false);
            }
            getAdracerApplications().getVideoPlayerApplication().getVideoPlayer().setState(JFrame.NORMAL);
        }else if (e.getActionCommand().equalsIgnoreCase(TopMenuFrame.GAZE_BUTTON)){
            if(!getAdracerApplications().getEyeTrackerGazeApplication().isVisible()){
                getAdracerApplications().getEyeTrackerGazeApplication().setVisible(true);
            }else{
                getAdracerApplications().getEyeTrackerGazeApplication().setVisible(false);
            }
            getAdracerApplications().getEyeTrackerGazeApplication().setState(JFrame.NORMAL);
        }else if (e.getActionCommand().equalsIgnoreCase(BottomMenuFrame.CALIBRATE_TRACKER_BUTTON)){
            if(!getAdracerApplications().getEyeTrackerCalibrator().isVisible()){
                getAdracerApplications().getEyeTrackerCalibrator().showGUI();
            }else{
                getAdracerApplications().getEyeTrackerCalibrator().hideGUI();
            }
        }else if (e.getActionCommand().equalsIgnoreCase(BottomMenuFrame.START_STOP_TRACKER_BUTTON)){
            if(!getAdracerApplications().getEyeTrackerProcessorPlayer().isStarted()){
                getAdracerApplications().getEyeTrackerProcessorPlayer().start();
                ((JButton)e.getSource()).setText("Tracker is running");
            }else{
                getAdracerApplications().getEyeTrackerProcessorPlayer().stop();
                ((JButton)e.getSource()).setText("Tracker is stopped");
            }
        }else if (e.getActionCommand().equalsIgnoreCase(BottomMenuFrame.START_STOP_EEG_BUTTON)){
            if(!getAdracerApplications().getEEGEnergyProcessorPlayer().isStarted()&&!getAdracerApplications().getEEGNormalProcessorPlayer().isStarted()){
                getAdracerApplications().getEEGEnergyProcessorPlayer().start();
                getAdracerApplications().getEEGNormalProcessorPlayer().start();
                ((JButton)e.getSource()).setText("EEG is running");
            }else{
                getAdracerApplications().getEEGEnergyProcessorPlayer().stop();
                getAdracerApplications().getEEGNormalProcessorPlayer().stop();
                ((JButton)e.getSource()).setText("EEG is stopped");
            }
        }else if (e.getActionCommand().equalsIgnoreCase(BottomMenuFrame.EXPORT_STATE_MONITOR)){
            if(!getAdracerApplications().getDBExportStateApplication().isVisible()){
                getAdracerApplications().getDBExportStateApplication().setVisible(true);
            }else{
                getAdracerApplications().getDBExportStateApplication().setVisible(false);
            }
            getAdracerApplications().getDBExportStateApplication().setState(JFrame.NORMAL);
        }else if (e.getActionCommand().equalsIgnoreCase(BottomMenuFrame.START_STOP_TRACKER_EXPORT)){
            if(!getAdracerApplications().getEyetrackerDataSourceHandler().isRunning()&&!getAdracerApplications().getEEGDatabaseSourceHandler().isRunning()){
                getAdracerApplications().getEyetrackerDataSourceHandler().setEnabled(true);
                getAdracerApplications().getEEGDatabaseSourceHandler().setEnabled(true);
                getAdracerApplications().getEyetrackerDataSourceHandler().setSessionID(propertiesReader.getConfig().getProperty("SESSION_ID"));
                getAdracerApplications().getEEGDatabaseSourceHandler().setSessionID(propertiesReader.getConfig().getProperty("SESSION_ID"));
                getAdracerApplications().getEyeTrackerDatabaseDatasink().start();
                getAdracerApplications().getEEGDatabaseDatasink().start();
                ((JButton)e.getSource()).setText("DB Export is running");
            }else {
                getAdracerApplications().getEyetrackerDataSourceHandler().setEnabled(false);
                getAdracerApplications().getEEGDatabaseSourceHandler().setEnabled(false);
                getAdracerApplications().getEyeTrackerDatabaseDatasink().stop();
                getAdracerApplications().getEEGDatabaseDatasink().stop();
                ((JButton)e.getSource()).setText("DB Export is stopped");
            }
        }
    }
}