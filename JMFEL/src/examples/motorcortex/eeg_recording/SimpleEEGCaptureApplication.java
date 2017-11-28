/*
 * This applcation uses the capture_controller.gui.CaptureStateApplication gui and captures an EEG signal and displays it with the application_container.MultichannelEEGApplication.
 * The signal can be stored to disk
 */

package examples.motorcortex.eeg_recording;

import application_container.MultichannelEEGApplication;
import capture_controller.CaptureApplication;
import capture_controller.SimpleCaptureController;
import capture_controller.gui.CaptureStateApplication;
import datasink.SimpleDataSink;
import datasource.CloneableDataSource;
import datasource.DataSourceMerger;
import datasource.SignalDataSource;
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
/**
 *
 * @author Urkman_2
 */
public class SimpleEEGCaptureApplication implements CaptureApplication{
    private CaptureStateApplication aCaptureStateApplication;
    private DataSource eegDS  = null;
    private SignalDataSource eegSignalDataSource;
    private ProcessorPlayer eegProcessorPlayer;
    private ProcessorPlayer eegOscilloscopeProcessorPlayer;
    private PropertiesReader propertiesReader = new PropertiesReader();
    private String file_separator = System.getProperty("file.separator");
    private EEGDataPlugin eegDataPlugin;
    private DoubleDataBufferContainer eegDoubleDataBufferContainer;
    private Configuration eegConfig;
    private ScalingConfiguration scalingConfig;
    private MultichannelEEGApplication multichannelEEGApplication;
    private Vector codecs;
    private SimpleCaptureController simpleCaptureController;
    private File path;
    private SimpleDataSink aSimpleDataSink;
    private CloneableDataSource cloneableDataSource;
    private DataSource dsClone = null;
    
    public SimpleEEGCaptureApplication() {
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
        
        eegDataPlugin.setDoubleDataBufferContainer(eegDoubleDataBufferContainer);
        multichannelEEGApplication = new MultichannelEEGApplication();
        multichannelEEGApplication.setBackGround("background_large16ChannelEEG_measurement.jpg");
        multichannelEEGApplication.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        multichannelEEGApplication.init();
        
        //override some defaults...
        multichannelEEGApplication.getMultiChannelEEGPainter().getPanel().setDoubleDataBufferContainer(eegDoubleDataBufferContainer);

        for (int i = 0; i < eegConfig.getChannelsInUse(); i++) {
            multichannelEEGApplication.getMultiChannelEEGPainter().getPanel().getSignalPanelVector().get(i).setSampleRate(eegConfig.getSampleRate());
        }
    }
    
    public void pause() {
        if(eegProcessorPlayer!=null){
            eegProcessorPlayer.stop();
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
            eegDS = eegSignalDataSource.getDataSource();
            cloneableDataSource = new CloneableDataSource();
            cloneableDataSource.setDataSource(eegDS);
            dsClone = cloneableDataSource.getClone();
            eegProcessorPlayer = new ProcessorPlayer();
            eegOscilloscopeProcessorPlayer = new ProcessorPlayer();
            eegProcessorPlayer.setUseVideoCodecs(false);
            eegOscilloscopeProcessorPlayer.setUseVideoCodecs(false);
            codecs = new Vector();
            codecs.add(eegDataPlugin);
            eegProcessorPlayer.setDataSource(cloneableDataSource.getCloneableDataSource());
            eegOscilloscopeProcessorPlayer.setDataSource(dsClone,codecs);
        }
        
        if(eegProcessorPlayer!=null){
            eegProcessorPlayer.start();
            eegProcessorPlayer.setTitle("eegProcessorPlayer");
            eegOscilloscopeProcessorPlayer.setTitle("eegOscilloscopeProcessorPlayer");
            eegOscilloscopeProcessorPlayer.start();
            
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
            
            if(aSimpleDataSink == null){
                aSimpleDataSink = new SimpleDataSink();
                aSimpleDataSink.setFileType(new FileTypeDescriptor(FileTypeDescriptor.WAVE));
                //no video here... just take some format...
                aSimpleDataSink.setAudioOutputFormat(new AudioFormat(AudioFormat.LINEAR));
                aSimpleDataSink.setDataSource(DataSourceMerger.mergeDataSources(datasources));
                aSimpleDataSink.setFileName(path.getAbsolutePath());
                aSimpleDataSink.init();
                aSimpleDataSink.start();
                
            }else if(aSimpleDataSink != null){
                aSimpleDataSink.resume();
            }
            eegProcessorPlayer.setVisible(false);
            eegProcessorPlayer.setMute(true);
            eegOscilloscopeProcessorPlayer.setMute(true);
            eegOscilloscopeProcessorPlayer.setVisible(false);
            multichannelEEGApplication.setVisible(true);
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
                eegOscilloscopeProcessorPlayer.stop();
                eegSignalDataSource.getDataSource().stop();
                eegSignalDataSource.getDataSource().disconnect();
                eegProcessorPlayer.dispose();
                eegOscilloscopeProcessorPlayer.dispose();
                multichannelEEGApplication.dispose();
            }catch(Exception ex){System.out.println(ex);};
            eegProcessorPlayer=null;
            eegOscilloscopeProcessorPlayer = null;
            eegSignalDataSource=null;
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
        SimpleEEGCaptureApplication aEEGCaptureApplication = new SimpleEEGCaptureApplication();
        aEEGCaptureApplication.init();
    }
}