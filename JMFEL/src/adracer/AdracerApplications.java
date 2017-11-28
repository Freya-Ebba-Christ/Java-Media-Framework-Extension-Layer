/*
 * AdracerApplications.java
 *
 * Created on 28. August 2007, 11:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package adracer;


import adracer.application_container.DBExportStateApplication;
import application_container.EEGEnergyApplication;
import application_container.EEGMultiBandApplication;
import application_container.EEGPowerApplication;
import application_container.EyeTrackerApplication;
import application_container.EyeTrackerGazeApplication;
import application_container.MultichannelEEGApplication;
import application_container.SingleEEGChannelContainer;
import application_container.VideoPlayerApplication;


import datasink.CustomDatabaseDataSink;
import datasink.database.eeg.EEGDatabaseSourceHandler;
import datasink.database.eye.EyetrackerDataSourceHandler;
import eye.insight.calibration.eye.EyeTrackerCalibrator;
import javax.swing.JFrame;
import simple_player.ProcessorPlayer;

/**
 *
 * @author Administrator
 */
public class AdracerApplications {

    private EEGEnergyApplication eegEnergyApplication;
    private EEGMultiBandApplication eegMultibandApplication;
    private EyeTrackerApplication eyeTrackerApplication;
    private EyeTrackerGazeApplication eyeTrackerGazeApplication;
    private MultichannelEEGApplication multichannelEEGApplication;
    private SingleEEGChannelContainer singleEEGChannelContainer;
    private EEGPowerApplication eegPowerApplication;
    private VideoPlayerApplication videoPlayerApplication;
    private DBExportStateApplication dbExportStateApplication;
    private EyetrackerDataSourceHandler eyetrackerDataSourceHandler;
    private CustomDatabaseDataSink eyeTrackerDatabaseDatasink;
    private CustomDatabaseDataSink eegDatabaseDatasink;
    private ProcessorPlayer eyeTrackerProcessorPlayer;
    private ProcessorPlayer eegMainProcessorPlayer;
    private ProcessorPlayer eegNormalProcessorPlayer;
    private ProcessorPlayer eegEnergyProcessorPlayer;
    private EyeTrackerCalibrator eyeTrackerCalibrator; 
    private EEGDatabaseSourceHandler eegDatabaseSourceHandler; 
    
    public AdracerApplications() {
    }
    
    public ProcessorPlayer getEEGEnergyProcessorPlayer() {
        return eegEnergyProcessorPlayer;
    }

    public ProcessorPlayer getEEGMainProcessorPlayer() {
        return eegMainProcessorPlayer;
    }

    public ProcessorPlayer getEEGNormalProcessorPlayer() {
        return eegNormalProcessorPlayer;
    }

    public void setEEGEnergyProcessorPlayer(ProcessorPlayer eegEnergyProcessorPlayer) {
        this.eegEnergyProcessorPlayer = eegEnergyProcessorPlayer;
    }

    public void setEEGMainProcessorPlayer(ProcessorPlayer eegMainProcessorPlayer) {
        this.eegMainProcessorPlayer = eegMainProcessorPlayer;
    }

    public void setEEGNormalProcessorPlayer(ProcessorPlayer eegNormalProcessorPlayer) {
        this.eegNormalProcessorPlayer = eegNormalProcessorPlayer;
    }
    
    public CustomDatabaseDataSink getEEGDatabaseDatasink() {
        return eegDatabaseDatasink;
    }

    public void setEEGDatabaseSourceHandler(EEGDatabaseSourceHandler eegDatabaseSourceHandler) {
        this.eegDatabaseSourceHandler = eegDatabaseSourceHandler;
    }
    
    public EEGDatabaseSourceHandler getEEGDatabaseSourceHandler() {
        return eegDatabaseSourceHandler;
    }

    public void setEEGDatabaseDatasink(CustomDatabaseDataSink eegDatabaseDatasink) {
        this.eegDatabaseDatasink = eegDatabaseDatasink;
    }
    
    public ProcessorPlayer getEyeTrackerProcessorPlayer() {
        return eyeTrackerProcessorPlayer;
    }

    public void setEyeTrackerProcessorPlayer(ProcessorPlayer eyeTrackerProcessorPlayer) {
        this.eyeTrackerProcessorPlayer = eyeTrackerProcessorPlayer;
    }
    
    public void setEyetrackerDataSourceHandler(EyetrackerDataSourceHandler eyetrackerDataSourceHandler) {
        this.eyetrackerDataSourceHandler = eyetrackerDataSourceHandler;
    }

    public EyetrackerDataSourceHandler getEyetrackerDataSourceHandler() {
        return eyetrackerDataSourceHandler;
    }

    public CustomDatabaseDataSink getEyeTrackerDatabaseDatasink() {
        return eyeTrackerDatabaseDatasink;
    }

    public void setEyeTrackerDatabaseDatasink(CustomDatabaseDataSink eyeTrackerDatabaseDatasink) {
        this.eyeTrackerDatabaseDatasink = eyeTrackerDatabaseDatasink;
    }
    
    public VideoPlayerApplication getVideoPlayerApplication() {
        return videoPlayerApplication;
    }
    
    public void setVideoPlayerApplication(VideoPlayerApplication videoPlayerApplication) {
        this.videoPlayerApplication = videoPlayerApplication;
    }
    
    public DBExportStateApplication getDBExportStateApplication() {
        return dbExportStateApplication;
    }
    
    public void setDBExportStateApplication(DBExportStateApplication dbExportStateApplication) {
        this.dbExportStateApplication = dbExportStateApplication;
    }

    public void setEyeTrackerCalibrator(EyeTrackerCalibrator eyeTrackerCalibrator) {
        this.eyeTrackerCalibrator = eyeTrackerCalibrator;
    }

    public EyeTrackerCalibrator getEyeTrackerCalibrator() {
        return eyeTrackerCalibrator;
    }
    
    public void initApplications(){
        //initialize the display that will show the EEG subband energy.
        setEEGEnergyApplication(new EEGEnergyApplication());
        getEEGEnergyApplication().setBackGround("background_energy_measurement.jpg");
        getEEGEnergyApplication().setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        getEEGEnergyApplication().setNumChannels(16);
        getEEGEnergyApplication().init();
        
        setEEGPowerApplication(new EEGPowerApplication());
        getEEGPowerApplication().setBackGround("background_energy_measurement.jpg");
        getEEGPowerApplication().setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        getEEGPowerApplication().setNumChannels(16);
        getEEGPowerApplication().init();
        
        setEEGMultibandApplication(new EEGMultiBandApplication());
        getEEGMultibandApplication().setBackGround("background_multiband_measurement.jpg");
        getEEGMultibandApplication().setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        getEEGMultibandApplication().setNumChannels(16);
        getEEGMultibandApplication().init();
        
        setEyeTrackerApplication(new EyeTrackerApplication());
        getEyeTrackerApplication().setBackGround("background_blink_measurement.jpg");
        getEyeTrackerApplication().setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        getEyeTrackerApplication().init();
        
        setEyeTrackerGazeApplication(new EyeTrackerGazeApplication());
        getEyeTrackerGazeApplication().setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        getEyeTrackerGazeApplication().setBackGround("background_gaze_measurement.jpg");
        getEyeTrackerGazeApplication().init();
        
        setMultichannelEEGApplication(new MultichannelEEGApplication());
        getMultichannelEEGApplication().setBackGround("background_large16ChannelEEG_measurement.jpg");
        getMultichannelEEGApplication().setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        getMultichannelEEGApplication().init();
        
        setSingleEEGChannelContainer(new SingleEEGChannelContainer());
        getSingleEEGChannelContainer().setBackGround("background_singleChannel.jpg");
        getSingleEEGChannelContainer().setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        getSingleEEGChannelContainer().setNumChannels(16);
        getSingleEEGChannelContainer().init();
        
        setDBExportStateApplication(new DBExportStateApplication());
        getDBExportStateApplication().setBackGround("background_export_state_monitor.jpg");
        getDBExportStateApplication().setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        getDBExportStateApplication().init();
        
        setVideoPlayerApplication(new VideoPlayerApplication());
        getVideoPlayerApplication().init();
        
        //note that this software is handled differently, because it has been written earlier... 
        setEyeTrackerCalibrator(new EyeTrackerCalibrator());
    }
    
    public EEGPowerApplication getEEGPowerApplication() {
        return eegPowerApplication;
    }
    
    public void setEEGPowerApplication(EEGPowerApplication eegPowerApplication) {
        this.eegPowerApplication = eegPowerApplication;
    }
    
    public EEGEnergyApplication getEEGEnergyApplication() {
        return eegEnergyApplication;
    }
    
    public EEGMultiBandApplication getEEGMultibandApplication() {
        return eegMultibandApplication;
    }
    
    public EyeTrackerApplication getEyeTrackerApplication() {
        return eyeTrackerApplication;
    }
    
    public EyeTrackerGazeApplication getEyeTrackerGazeApplication() {
        return eyeTrackerGazeApplication;
    }
    
    public MultichannelEEGApplication getMultichannelEEGApplication() {
        return multichannelEEGApplication;
    }
    
    public SingleEEGChannelContainer getSingleEEGChannelContainer() {
        return singleEEGChannelContainer;
    }
    
    public void setEEGEnergyApplication(EEGEnergyApplication eegEnergyApplication) {
        this.eegEnergyApplication = eegEnergyApplication;
    }
    
    public void setEEGMultibandApplication(EEGMultiBandApplication eegMultibandApplication) {
        this.eegMultibandApplication = eegMultibandApplication;
    }
    
    public void setEyeTrackerApplication(EyeTrackerApplication eyeTrackerApplication) {
        this.eyeTrackerApplication = eyeTrackerApplication;
    }
    
    public void setEyeTrackerGazeApplication(EyeTrackerGazeApplication eyeTrackerGazeApplication) {
        this.eyeTrackerGazeApplication = eyeTrackerGazeApplication;
    }
    
    public void setMultichannelEEGApplication(MultichannelEEGApplication multichannelEEGApplication) {
        this.multichannelEEGApplication = multichannelEEGApplication;
    }
    
    public void setSingleEEGChannelContainer(SingleEEGChannelContainer singleEEGChannelContainer) {
        this.singleEEGChannelContainer = singleEEGChannelContainer;
    }
    
    public static void main(String[] args) {
        AdracerApplications aAdracerApplications = new AdracerApplications();
        aAdracerApplications.initApplications();
    }
}