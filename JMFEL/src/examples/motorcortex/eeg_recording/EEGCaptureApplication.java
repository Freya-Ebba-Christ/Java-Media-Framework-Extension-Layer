/*
 * EEGCaptureApplication.java
 * This EEG capture application uses the reusable capture_controller.gui.CaptureStateApplication as a simple user interface.
 * The application uses the first channel for ECG and uses the application_container.ECGApplication to display the signal. 
 * The EEG signal is displayed in various ways by the EEGPowerApplication power bars. The EEG sub bands are displayed by the EEGMultiBandApplication.
 * The EEG is merged with a video signal and stored to disk.
 */

package examples.motorcortex.eeg_recording;

import application_container.ECGApplication;
import application_container.EEGMultiBandApplication;
import application_container.EEGPowerApplication;
import application_container.SingleEEGChannelContainer;
import capture_controller.CaptureApplication;
import capture_controller.SimpleCaptureController;
import capture_controller.gui.CaptureStateApplication;
import custom_renderer.NullAudioRenderer;
import datasink.SimpleDataSink;
import datasource.CloneableDataSource;
import datasource.DataSourceMerger;
import datasource.SignalDataSource;
import datasource.VideoDataSource;
import eeg.EEGDataModel;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Properties;
import java.util.Vector;
import javax.media.Format;
import javax.media.MediaLocator;
import javax.media.control.TrackControl;
import javax.media.format.AudioFormat;
import javax.media.format.VideoFormat;
import javax.media.protocol.DataSource;
import javax.media.protocol.FileTypeDescriptor;
import javax.swing.JFrame;
import media.protocol.gtec.CommonGround;
import media.protocol.gtec.CommonReference;
import media.protocol.gtec.Configuration;
import media.protocol.gtec.ConfigurationTableModel;
import media.protocol.gtec.ScalingConfiguration;
import plugins.eeg.EEGDataPlugin;
import simple_player.ProcessorPlayer;
import utilities.DoubleDataBufferContainer;
import utilities.PropertiesReader;
import utilities.signal_processing.EEGSignalProcessor;

/**
 *
 * @author Administrator
 */
public class EEGCaptureApplication implements CaptureApplication{
    private CaptureStateApplication aCaptureStateApplication;
    private DataSource eegDS  = null;
    private SignalDataSource eegSignalDataSource;
    private ProcessorPlayer eegProcessorPlayer;
    private ProcessorPlayer eegOscilloscopeProcessorPlayer;
    private ProcessorPlayer eegEnergyProcessorPlayer;
    private PropertiesReader propertiesReader = new PropertiesReader();
    private String file_separator = System.getProperty("file.separator");
    private EEGDataPlugin eegDataPlugin;
    private EEGDataPlugin eegEnergyDataPlugin;
    private EEGDataPlugin ecgDataPlugin;
    private DoubleDataBufferContainer eegDoubleDataBufferContainer;
    private DoubleDataBufferContainer subbandDoubleDataBufferContainer;
    private DoubleDataBufferContainer ecgDoubleDataBufferContainer;
    private Configuration eegConfig;
    private ScalingConfiguration scalingConfig;
    private SingleEEGChannelContainer singleEEGChannelContainer;
    private Vector codecs;
    private Vector energyCodecs;
    private SimpleCaptureController simpleCaptureController;
    private File path;
    private SimpleDataSink aSimpleDataSink;
    private CloneableDataSource cloneableDataSource;
    private DataSource dsClone = null;
    private DataSource dsEEGEnergyClone = null;
    private EEGPowerApplication eegPowerApplication;
    private EEGMultiBandApplication eegMultiBandApplication;
    private ECGApplication ecgApplication;
    private VideoDataSource aVideoDataSource;
    private CloneableDataSource cloneableVideoDataSource  = null;
    private DataSource dsVideoClone  = null;
    private DataSource dsVideoCloneable = null;
    private ProcessorPlayer videoPlayer;
    
    public EEGCaptureApplication() {
    }
    
    private Configuration getAmplifierConfiguration(String name){
        Configuration settings = new Configuration();
        settings.setModel(new ConfigurationTableModel());
        settings.setCommonGround(new CommonGround());
        settings.setCommonReference(new CommonReference());
        String file_separator = System.getProperty("file.separator");
        String settingsFile = System.getProperty("user.dir") + file_separator + "media" + file_separator + "protocol" + file_separator + name + file_separator + "configuration.ini";
        settings.load(settingsFile);
        return settings;
    }
    
    private ScalingConfiguration getScalingConfiguration(String name){
        ScalingConfiguration scalingConfiguration = new ScalingConfiguration();
        String settingsFile = System.getProperty("user.dir") + file_separator + "media" + file_separator + "protocol" + file_separator + name + file_separator + "scaling.ini";
        scalingConfiguration.load(settingsFile);
        return scalingConfiguration;
    }
    
    public void open(File file) {
        if(eegProcessorPlayer!=null){
            eegProcessorPlayer.dispose();
        }
        path = file;
        eegProcessorPlayer = null;
        eegOscilloscopeProcessorPlayer = null;
        eegSignalDataSource = new SignalDataSource();
        eegSignalDataSource.setMediaLocator(new MediaLocator("usbAmpA://"));
        eegConfig = getAmplifierConfiguration("usbAmpA");
        scalingConfig = getScalingConfiguration("usbAmpA");
        
        eegDataPlugin = new EEGDataPlugin();
        eegEnergyDataPlugin = new EEGDataPlugin();
        ecgDataPlugin = new EEGDataPlugin();
        
        eegDataPlugin.setNumChannels(eegConfig.getChannelsInUse());
        eegEnergyDataPlugin.setNumChannels(eegConfig.getChannelsInUse());
        ecgDataPlugin.setNumChannels(eegConfig.getChannelsInUse());
        
        ecgDoubleDataBufferContainer = new DoubleDataBufferContainer();
        ecgDoubleDataBufferContainer.initializeBuffers(eegConfig.getSampleRate()*10,eegConfig.getChannelsInUse());
        for (int i = 0; i < eegConfig.getChannelsInUse(); i++) {
            ecgDoubleDataBufferContainer.getDataBuffer(i).setDistance(5);
            try{
                ecgDoubleDataBufferContainer.getDataBuffer(i).setMaxVoltage(scalingConfig.toMicroVolt(i));
            }catch(Exception e){System.out.println(e);};
            ecgDoubleDataBufferContainer.getDataBuffer(i).setUnit(scalingConfig.getUnit(i));
        }
        ecgDataPlugin.setDoubleDataBufferContainer(ecgDoubleDataBufferContainer);
        
        eegDoubleDataBufferContainer = new DoubleDataBufferContainer();
        eegDoubleDataBufferContainer.initializeBuffers(eegConfig.getSampleRate(),eegConfig.getChannelsInUse());
        for (int i = 0; i < eegConfig.getChannelsInUse(); i++) {
            eegDoubleDataBufferContainer.getDataBuffer(i).setDistance(5);
            try{
                eegDoubleDataBufferContainer.getDataBuffer(i).setMaxVoltage(scalingConfig.toMicroVolt(i));
            }catch(Exception e){System.out.println(e);};
            eegDoubleDataBufferContainer.getDataBuffer(i).setUnit(scalingConfig.getUnit(i));
        }
        
        subbandDoubleDataBufferContainer = new DoubleDataBufferContainer();
        subbandDoubleDataBufferContainer.initializeBuffers(eegConfig.getSampleRate(),eegConfig.getChannelsInUse());
        
        for (int i = 0; i < eegConfig.getChannelsInUse(); i++) {
            subbandDoubleDataBufferContainer.getDataBuffer(i).setDistance(5);
            try{
                subbandDoubleDataBufferContainer.getDataBuffer(i).setMaxVoltage(scalingConfig.toMicroVolt(i));
            }catch(Exception e){System.out.println(e);};
            subbandDoubleDataBufferContainer.getDataBuffer(i).setUnit(scalingConfig.getUnit(i));
        }
        
        EEGDataModel eegData = new EEGDataModel();
        for (int i = 0; i < eegConfig.getChannelsInUse(); i++) {
            subbandDoubleDataBufferContainer.getDataBuffer(i).registerCallback(eegData);
            subbandDoubleDataBufferContainer.getDataBuffer(i).setID(i);
        }
        
        eegEnergyDataPlugin.setDoubleDataBufferContainer(subbandDoubleDataBufferContainer);
        
        //sub band panel
        EEGSignalProcessor eegSignalProcessor = EEGSignalProcessor.getInstance();
        eegSignalProcessor.initialze(eegConfig.getSampleRate());
        eegData.setEEGSignalProcessor(eegSignalProcessor);
        eegData.setDoubleDataBufferContainer(subbandDoubleDataBufferContainer);
        eegData.setNumChannels(eegConfig.getChannelsInUse());
        eegData.init();
        
        eegPowerApplication = new EEGPowerApplication();
        eegPowerApplication.setBackGround("background_energy_measurement.jpg");
        eegPowerApplication.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        eegPowerApplication.setNumChannels(eegConfig.getChannelsInUse());
        eegPowerApplication.init();
        
        eegMultiBandApplication = new EEGMultiBandApplication();
        eegMultiBandApplication.setBackGround("background_multiband_measurement.jpg");
        eegMultiBandApplication.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        eegMultiBandApplication.setNumChannels(eegConfig.getChannelsInUse());
        eegMultiBandApplication.init();
        
        ecgApplication = new ECGApplication();
        ecgApplication.setBackGround("background_ecg.jpg");
        ecgApplication.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ecgApplication.setECGChannel(0);
        ecgApplication.init();
        
        //override some defaults...
        ecgApplication.getECGPainter().setDoubleDataBufferContainer(ecgDoubleDataBufferContainer);
        ecgApplication.getECGPainter().getPanel().getSignalPanel().setSampleRate(eegConfig.getSampleRate());
        
        //we dont need any offset...
        ecgApplication.getECGPainter().getPanel().getSignalPanel().setSignalXOffset(0);
        
        eegData.addObserver(eegMultiBandApplication.getMultiBandEEGPainter().getPanel());
        eegData.addObserver(eegPowerApplication.getEEGPowerPainter().getPanel());
        
        eegMultiBandApplication.getMultiBandEEGPainter().getPanel().getAlphaSignalPanel().setSampleRate(eegConfig.getSampleRate());
        eegMultiBandApplication.getMultiBandEEGPainter().getPanel().getBetaSignalPanel().setSampleRate(eegConfig.getSampleRate());
        eegMultiBandApplication.getMultiBandEEGPainter().getPanel().getDeltaSignalPanel().setSampleRate(eegConfig.getSampleRate());
        eegMultiBandApplication.getMultiBandEEGPainter().getPanel().getGammaSignalPanel().setSampleRate(eegConfig.getSampleRate());
        eegMultiBandApplication.getMultiBandEEGPainter().getPanel().getThetaSignalPanel().setSampleRate(eegConfig.getSampleRate());
        
        eegDataPlugin.setDoubleDataBufferContainer(eegDoubleDataBufferContainer);
        singleEEGChannelContainer = new SingleEEGChannelContainer();
        singleEEGChannelContainer.setBackGround("background_singleChannel.jpg");
        singleEEGChannelContainer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        singleEEGChannelContainer.setNumChannels(eegConfig.getChannelsInUse());
        singleEEGChannelContainer.init();
        
        //override some defaults...
        singleEEGChannelContainer.getSingleChannelEEGPainter().setDoubleDataBufferContainer(eegDoubleDataBufferContainer);
        singleEEGChannelContainer.getSingleChannelEEGPainter().getPanel().getSignalPanel().setSampleRate(eegConfig.getSampleRate());
        
        //we dont need any offset...
        singleEEGChannelContainer.getSingleChannelEEGPainter().getPanel().getSignalPanel().setSignalXOffset(0);
        aVideoDataSource = new VideoDataSource();
        try{
            aVideoDataSource.setVideoCaptureFormat(new VideoFormat(VideoFormat.RGB, new Dimension(176,144), Format.NOT_SPECIFIED, null, 20.0f));
            aVideoDataSource.init();
        }catch(Exception e){System.out.println(e);};
        videoPlayer = new ProcessorPlayer();
        cloneableVideoDataSource = new CloneableDataSource();
        cloneableVideoDataSource.setDataSource(aVideoDataSource.getDataSource());
        dsVideoCloneable = cloneableVideoDataSource.getCloneableDataSource();
        dsVideoClone = cloneableVideoDataSource.getClone();
        videoPlayer.setDataSource(dsVideoClone);
    }
    
    public void pause() {
        if(eegProcessorPlayer!=null){
            eegProcessorPlayer.stop();
            eegOscilloscopeProcessorPlayer.stop();
            eegEnergyProcessorPlayer.stop();
            videoPlayer.stop();
        }
        
        if(aSimpleDataSink != null){
            aSimpleDataSink.pause();
        }
    }
    
    public void start(){
        
        if(eegProcessorPlayer==null&&eegSignalDataSource!=null){
            aCaptureStateApplication.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            eegSignalDataSource.init();
            eegDS = eegSignalDataSource.getDataSource();
            cloneableDataSource = new CloneableDataSource();
            cloneableDataSource.setDataSource(eegDS);
            dsClone = cloneableDataSource.getClone();
            dsEEGEnergyClone = cloneableDataSource.getClone();
            eegProcessorPlayer = new ProcessorPlayer();
            eegOscilloscopeProcessorPlayer = new ProcessorPlayer();
            eegEnergyProcessorPlayer = new ProcessorPlayer();
            eegProcessorPlayer.setUseVideoCodecs(false);
            eegOscilloscopeProcessorPlayer.setUseVideoCodecs(false);
            eegEnergyProcessorPlayer.setUseVideoCodecs(false);
            eegProcessorPlayer.setAudioRenderer(new NullAudioRenderer());
            eegOscilloscopeProcessorPlayer.setAudioRenderer(new NullAudioRenderer());
            eegEnergyProcessorPlayer.setAudioRenderer(new NullAudioRenderer());
            
            codecs = new Vector();
            energyCodecs = new Vector();
            codecs.add(eegDataPlugin);
            codecs.add(ecgDataPlugin);
            energyCodecs.add(eegEnergyDataPlugin);
            eegEnergyProcessorPlayer.setDataSource(dsEEGEnergyClone,energyCodecs);
            eegEnergyProcessorPlayer.setUseVideoCodecs(false);
            eegEnergyProcessorPlayer.setUseAudioCodecs(true);
            //eegEnergyProcessorPlayer.setMute(true);
            
            eegProcessorPlayer.setDataSource(cloneableDataSource.getCloneableDataSource());
            //eegProcessorPlayer.setMute(true);
            eegOscilloscopeProcessorPlayer.setDataSource(dsClone,codecs);
            //eegOscilloscopeProcessorPlayer.setMute(true);
        }
        
        if(eegProcessorPlayer!=null){
            eegProcessorPlayer.start();
            eegProcessorPlayer.setTitle("eegProcessorPlayer");
            eegOscilloscopeProcessorPlayer.setTitle("eegOscilloscopeProcessorPlayer");
            eegOscilloscopeProcessorPlayer.start();
            eegEnergyProcessorPlayer.start();
            eegEnergyProcessorPlayer.setTitle("energyProcessorPlayer");
            TrackControl tc[] = eegOscilloscopeProcessorPlayer.getProcessor().getTrackControls();
            Format[] formatArray = new Format[tc.length];
            for (int i = 0; i < tc.length; i++) {
                formatArray[i]=(tc[i].getFormat());
            }
            
            aCaptureStateApplication.getCaptureStatePainter().getPanel().setFormat(formatArray);
            aCaptureStateApplication.getCaptureStatePainter().getPanel().setProcessor(eegOscilloscopeProcessorPlayer.getProcessor());
            aCaptureStateApplication.getCaptureStatePainter().getPanel().setPlayer(null);
            
            Vector datasources = new Vector();
            datasources.add(cloneableDataSource.getCloneableDataSource());
            datasources.add(dsVideoCloneable);
            
            if(aSimpleDataSink == null){
                aSimpleDataSink = new SimpleDataSink();
                aSimpleDataSink.setFileType(new FileTypeDescriptor(FileTypeDescriptor.QUICKTIME));
                //no video here... just take some format...
                aSimpleDataSink.setVideoOutputFormat(new VideoFormat(VideoFormat.MJPG));
                aSimpleDataSink.setAudioOutputFormat(new AudioFormat(AudioFormat.LINEAR));
                aSimpleDataSink.setDataSource(DataSourceMerger.mergeDataSources(datasources));
                aSimpleDataSink.setFileName(path.getAbsolutePath());
                aSimpleDataSink.init();
                aSimpleDataSink.start();
                
            }else if(aSimpleDataSink != null){
                aSimpleDataSink.resume();
            }
            eegProcessorPlayer.setVisible(true);
            eegEnergyProcessorPlayer.setVisible(true);
            eegOscilloscopeProcessorPlayer.setVisible(true);
            singleEEGChannelContainer.setVisible(true);
            eegPowerApplication.setVisible(true);
            eegMultiBandApplication.setVisible(true);
            ecgApplication.setVisible(true);
            aCaptureStateApplication.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        
        videoPlayer.start();
        videoPlayer.setVisible(true);
    }
    
    public void stop() {
        aCaptureStateApplication.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        
        if(aSimpleDataSink != null){
            aSimpleDataSink.stop();
            aSimpleDataSink=null;
        }
        
        if(eegProcessorPlayer!=null){
            try{
                ((media.protocol.usbAmpA.CustomCaptureDevice)((media.protocol.usbAmpA.DataSource)eegSignalDataSource.getDataSource()).getCaptureDevice()).stop();
                ((media.protocol.usbAmpA.CustomCaptureDevice)((media.protocol.usbAmpA.DataSource)eegSignalDataSource.getDataSource()).getCaptureDevice()).close();
                eegProcessorPlayer.stop();
                eegOscilloscopeProcessorPlayer.stop();
                eegEnergyProcessorPlayer.stop();
                videoPlayer.stop();
                eegSignalDataSource.getDataSource().stop();
                eegSignalDataSource.getDataSource().disconnect();
                eegProcessorPlayer.dispose();
                eegOscilloscopeProcessorPlayer.dispose();
                singleEEGChannelContainer.dispose();
                eegEnergyProcessorPlayer.dispose();
                eegPowerApplication.dispose();
                eegMultiBandApplication.dispose();
                ecgApplication.dispose();
                videoPlayer.dispose();
                
            }catch(Exception ex){System.out.println(ex);};
            eegProcessorPlayer=null;
            eegOscilloscopeProcessorPlayer = null;
            eegSignalDataSource=null;
            eegEnergyProcessorPlayer = null;
            videoPlayer = null;
        }
        
        
        aCaptureStateApplication.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    
    public void init(){
        aCaptureStateApplication = new CaptureStateApplication();
        aCaptureStateApplication.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aCaptureStateApplication.setTitle("Simple Biosignal Data Acquisition Application (alpha build version for Denmark)");
        aCaptureStateApplication.setBackGround("background_capture_monitor.jpg");
        aCaptureStateApplication.setCaptureApplication(this);
        Properties props = propertiesReader.readProperties(System.getProperty("user.dir")+file_separator+"examples"+file_separator+"motorcortex"+file_separator+"eeg_recording"+file_separator+"tooltips.ini");
        aCaptureStateApplication.setMode(CaptureStateApplication.MODE_SAVE);
        aCaptureStateApplication.setNewButtonToolTip(props.getProperty("LOAD_SAVE_BUTTON"));
        aCaptureStateApplication.setPlayButtonToolTip(props.getProperty("PLAY_BUTTON"));
        aCaptureStateApplication.setStopButtonToolTip(props.getProperty("STOP_BUTTON"));
        aCaptureStateApplication.setPauseButtonToolTip(props.getProperty("PAUSE_BUTTON"));
        
        aCaptureStateApplication.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                if(eegSignalDataSource!=null){
                    
                    if(aSimpleDataSink != null){
                        aSimpleDataSink.stop();
                        aSimpleDataSink=null;
                    }
                    
                    ((media.protocol.usbAmpA.CustomCaptureDevice)((media.protocol.usbAmpA.DataSource)eegSignalDataSource.getDataSource()).getCaptureDevice()).stop();
                    ((media.protocol.usbAmpA.CustomCaptureDevice)((media.protocol.usbAmpA.DataSource)eegSignalDataSource.getDataSource()).getCaptureDevice()).close();
                }
                System.exit(0);
            }
        });
        
        aCaptureStateApplication.init();
        aCaptureStateApplication.setVisible(true);
    }
    
    public static void main(String[] args) {
        EEGCaptureApplication aEEGCaptureApplication = new EEGCaptureApplication();
        aEEGCaptureApplication.init();
    }
}