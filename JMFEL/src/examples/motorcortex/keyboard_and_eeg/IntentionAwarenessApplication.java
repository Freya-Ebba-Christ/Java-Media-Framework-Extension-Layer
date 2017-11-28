/*
 * This application displays Wundt's clock and records EEG and keyboard strokes to disk. 
 */

package examples.motorcortex.keyboard_and_eeg;

import application_container.SingleEEGChannelContainer;
import capture_controller.CaptureApplication;
import capture_controller.SimpleCaptureController;
import capture_controller.gui.CaptureStateApplication;
import custom_renderer.NullAudioRenderer;
import datasink.SimpleDataSink;
import datasource.CloneableDataSource;
import datasource.DataSourceMerger;
import datasource.SignalDataSource;
import eeg.EEGDataModel;
import examples.motorcortex.eeg_recording.StopwatchApplication;
import java.awt.Cursor;
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
import utilities.DoubleDataBuffer;
import utilities.DoubleDataBufferContainer;
import utilities.PropertiesReader;

/**
 *
 * @author Administrator
 */
public class IntentionAwarenessApplication implements CaptureApplication{
    private CaptureStateApplication aCaptureStateApplication;
    private DataSource eegDS  = null;
    private DataSource stopWatchDS  = null;
    private SignalDataSource eegSignalDataSource;
    private SignalDataSource stopWatchSignalDataSource;
    private ProcessorPlayer eegProcessorPlayer;
    private ProcessorPlayer stopWatchProcessorPlayer;
    private ProcessorPlayer eegOscilloscopeProcessorPlayer;
    private PropertiesReader propertiesReader = new PropertiesReader();
    private String file_separator = System.getProperty("file.separator");
    private EEGDataPlugin eegDataPlugin;
    private DoubleDataBufferContainer eegDoubleDataBufferContainer;
    private Configuration eegConfig;
    private ScalingConfiguration scalingConfig;
    private SingleEEGChannelContainer singleEEGChannelContainer;
    private Vector codecs;
    private Vector stopWatchCodecs;
    private SimpleCaptureController simpleCaptureController;
    private File path;
    private SimpleDataSink aSimpleDataSink;
    private CloneableDataSource cloneableDataSource;
    private CloneableDataSource cloneableStopWatchDataSource;
    private DataSource dsClone = null;
    private DataSource dsStopWatchClone = null;
    private StopwatchApplication stopwatchApplication = null;
    
    public IntentionAwarenessApplication() {
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
        eegProcessorPlayer = null;
        eegOscilloscopeProcessorPlayer = null;
        eegSignalDataSource = new SignalDataSource();
        eegSignalDataSource.setMediaLocator(new MediaLocator("usbAmpA://"));
        eegConfig = getAmplifierConfiguration("usbAmpA");
        scalingConfig = getScalingConfiguration("usbAmpA");
        
        stopWatchSignalDataSource = new SignalDataSource();
        stopWatchSignalDataSource.setMediaLocator(new MediaLocator("stopWatch://"));
        stopWatchSignalDataSource.init();
        eegDataPlugin = new EEGDataPlugin();
        eegDataPlugin.setNumChannels(eegConfig.getChannelsInUse());
        
        eegDoubleDataBufferContainer = new DoubleDataBufferContainer();
        eegDoubleDataBufferContainer.initializeBuffers(eegConfig.getSampleRate(),eegConfig.getChannelsInUse());
        
        for (int i = 0; i < eegConfig.getChannelsInUse(); i++) {
            eegDoubleDataBufferContainer.getDataBuffer(i).setDistance(5);
            try{
                eegDoubleDataBufferContainer.getDataBuffer(i).setMaxVoltage(scalingConfig.toMicroVolt(i));
            }catch(Exception e){System.out.println(e);};
            eegDoubleDataBufferContainer.getDataBuffer(i).setUnit(scalingConfig.getUnit(i));
        }
        
        EEGDataModel eegData = new EEGDataModel();
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
    }
    
    public void pause() {
        if(eegProcessorPlayer!=null){
            eegProcessorPlayer.stop();
            stopWatchProcessorPlayer.stop();
            eegOscilloscopeProcessorPlayer.stop();
        }
        
        if(aSimpleDataSink != null){
            aSimpleDataSink.pause();
        }
    }
    
    public void start(){
        
        if(eegProcessorPlayer==null&&eegSignalDataSource!=null){
            aCaptureStateApplication.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            eegSignalDataSource.init();
            
            stopWatchDS = stopWatchSignalDataSource.getDataSource();
            eegDS = eegSignalDataSource.getDataSource();
            cloneableDataSource = new CloneableDataSource();
            cloneableDataSource.setDataSource(eegDS);
            
            cloneableStopWatchDataSource = new CloneableDataSource();
            cloneableStopWatchDataSource.setDataSource(stopWatchDS);
            dsStopWatchClone = cloneableStopWatchDataSource.getClone();
            
            eegOscilloscopeProcessorPlayer = new ProcessorPlayer();
            eegOscilloscopeProcessorPlayer.setUseVideoCodecs(false);
            eegOscilloscopeProcessorPlayer.setAudioRenderer(new NullAudioRenderer());
            
            dsClone = cloneableDataSource.getClone();
            eegProcessorPlayer = new ProcessorPlayer();
            eegProcessorPlayer.setUseVideoCodecs(false);
            eegProcessorPlayer.setAudioRenderer(new NullAudioRenderer());
            stopWatchProcessorPlayer = new ProcessorPlayer();
            stopWatchProcessorPlayer.setUseVideoCodecs(false);
            stopWatchProcessorPlayer.setAudioRenderer(new NullAudioRenderer());
                    
            codecs = new Vector();
            codecs.add(eegDataPlugin);
            
            stopWatchProcessorPlayer.setDataSource(dsStopWatchClone);
            stopWatchProcessorPlayer.setUseVideoCodecs(false);
            stopWatchProcessorPlayer.setUseAudioCodecs(true);
            eegProcessorPlayer.setDataSource(cloneableDataSource.getCloneableDataSource());
            eegOscilloscopeProcessorPlayer.setDataSource(dsClone,codecs);
        }
        
        if(eegProcessorPlayer!=null){
            eegProcessorPlayer.start();
            eegProcessorPlayer.setTitle("eegProcessorPlayer");
            stopWatchProcessorPlayer.start();
            eegOscilloscopeProcessorPlayer.start();
            
            TrackControl tc[] = eegOscilloscopeProcessorPlayer.getProcessor().getTrackControls();
            Format[] formatArray = new Format[tc.length];
            for (int i = 0; i < tc.length; i++) {
                formatArray[i]=(tc[i].getFormat());
            }
            
            aCaptureStateApplication.getCaptureStatePainter().getPanel().setFormat(formatArray);
            aCaptureStateApplication.getCaptureStatePainter().getPanel().setPlayer(null);
            
            Vector datasources = new Vector();
            datasources.add(cloneableDataSource.getCloneableDataSource());
            datasources.add(cloneableStopWatchDataSource.getCloneableDataSource());
            
            if(aSimpleDataSink == null){
                aSimpleDataSink = new SimpleDataSink();
                aSimpleDataSink.setFileType(new FileTypeDescriptor(FileTypeDescriptor.QUICKTIME));
                //no video here... just take some format...
                aSimpleDataSink.setVideoOutputFormat(new VideoFormat(VideoFormat.CINEPAK));
                aSimpleDataSink.setAudioOutputFormat(new AudioFormat(AudioFormat.LINEAR));
                aSimpleDataSink.setDataSource(DataSourceMerger.mergeDataSources(datasources));
                aSimpleDataSink.setFileName(path.getAbsolutePath());
                aSimpleDataSink.init();
                aSimpleDataSink.start();
                
            }else if(aSimpleDataSink != null){
                aSimpleDataSink.resume();
            }
            
            stopWatchProcessorPlayer.setVisible(true);
            eegProcessorPlayer.setVisible(true);
            singleEEGChannelContainer.setVisible(true);
            eegOscilloscopeProcessorPlayer.setVisible(true);
            stopwatchApplication.init();
            stopwatchApplication.getPainter().getPanel().setEnableClock(false);
            stopwatchApplication.getPainter().getPanel().setEnableLeftRight(true);
            ((media.protocol.stopWatch.CustomCaptureDevice)((media.protocol.stopWatch.DataSource)stopWatchSignalDataSource.getDataSource()).getCaptureDevice()).setStopwatchApplication(stopwatchApplication);
            stopwatchApplication.setVisible(true);
            aCaptureStateApplication.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
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
                stopWatchProcessorPlayer.stop();
                eegOscilloscopeProcessorPlayer.stop();
                eegSignalDataSource.getDataSource().stop();
                eegSignalDataSource.getDataSource().disconnect();
                eegProcessorPlayer.dispose();
                stopWatchProcessorPlayer.dispose();
                singleEEGChannelContainer.dispose();
                eegOscilloscopeProcessorPlayer.dispose();
                stopwatchApplication.dispose();
                
            }catch(Exception ex){System.out.println(ex);};
            eegProcessorPlayer=null;
            eegSignalDataSource=null;
            stopWatchProcessorPlayer = null;
            eegOscilloscopeProcessorPlayer = null;
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
        stopwatchApplication = new StopwatchApplication();
        stopwatchApplication.setUndecorated(true);
        
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
        IntentionAwarenessApplication aIntentionAwarenessApplication = new IntentionAwarenessApplication();
        aIntentionAwarenessApplication.init();
    }
}