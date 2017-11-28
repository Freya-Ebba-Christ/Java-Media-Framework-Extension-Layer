/*
 * ECGMonitor.java
 *
 * this examples opens the EEG amplifier and displays the subject's heartrate on the sceen.
 * It uses the panel.ecg.ECGPanel, which implements a simple adaptive threshold algorithm to compute the heartrate.
 */

package examples.heartrate;

import application_container.ECGApplication;
import datasource.CloneableDataSource;
import datasource.SignalDataSource;
import java.util.Vector;
import javax.media.MediaLocator;
import javax.media.protocol.DataSource;
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
import utilities.math.Rounding;

/**
 *
 * @author Administrator
 */
public class ECGMonitor {
    private PropertiesReader propertiesReader = new PropertiesReader();
    private String file_separator = System.getProperty("file.separator");
    private EEGDataPlugin eegDataPlugin;
    private DoubleDataBufferContainer ecgDoubleDataBufferContainer;
    private Configuration ecgConfig;
    private ScalingConfiguration scalingConfig;
    private ECGApplication ecgApplication;
    private Vector codecs;
    private DataSource ecgDS  = null;
    private SignalDataSource ecgSignalDataSource;
    private ProcessorPlayer ecgProcessorPlayer;
    
    /** Creates a new instance of ECGMonitor */
    public ECGMonitor() {
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
    
    public void init(){
        ecgSignalDataSource = new SignalDataSource();
        ecgSignalDataSource.setMediaLocator(new MediaLocator("usbAmpA://"));
        ecgConfig = getAmplifierConfiguration("usbAmpA");
        scalingConfig = getScalingConfiguration("usbAmpA");
        eegDataPlugin = new EEGDataPlugin();
        eegDataPlugin.setNumChannels(ecgConfig.getChannelsInUse());
        ecgDoubleDataBufferContainer = new DoubleDataBufferContainer();
        ecgDoubleDataBufferContainer.initializeBuffers(ecgConfig.getSampleRate()*5,ecgConfig.getChannelsInUse());
        for (int i = 0; i < ecgConfig.getChannelsInUse(); i++) {
            ecgDoubleDataBufferContainer.getDataBuffer(i).setDistance((int)Rounding.round(1.0,0));
            try{
                ecgDoubleDataBufferContainer.getDataBuffer(i).setMaxVoltage(scalingConfig.toMicroVolt(i));
            }catch(Exception e){System.out.println(e);};
            ecgDoubleDataBufferContainer.getDataBuffer(i).setUnit(scalingConfig.getUnit(i));
        }
        eegDataPlugin.setDoubleDataBufferContainer(ecgDoubleDataBufferContainer);

        ecgApplication = new ECGApplication();
        ecgApplication.setBackGround("background_singleChannel.jpg");
        ecgApplication.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ecgApplication.setECGChannel(1);
        ecgApplication.init();
        
        //override some defaults...
        ecgApplication.getECGPainter().setDoubleDataBufferContainer(ecgDoubleDataBufferContainer);
        ecgApplication.getECGPainter().getPanel().getSignalPanel().setSampleRate(ecgConfig.getSampleRate());
        ecgApplication.getECGPainter().getPanel().setECGChannel(0);
        
        //we dont need any offset...
        ecgApplication.getECGPainter().getPanel().getSignalPanel().setSignalXOffset(0);
    }
    
    public void start(){
        
        ecgSignalDataSource.init();
        ecgDS = ecgSignalDataSource.getDataSource();

        ecgProcessorPlayer = new ProcessorPlayer();
        codecs = new Vector();
        codecs.add(eegDataPlugin);
        ecgProcessorPlayer.setDataSource(ecgDS,codecs);
        ecgProcessorPlayer.setTitle("Heart rate monitor");
        ecgProcessorPlayer.setMute(true);
        ecgProcessorPlayer.setVisible(true);
        ecgApplication.setVisible(true);
        ecgProcessorPlayer.start();
    }
    
    public static void main(String[] args) {
        ECGMonitor aECGMonitor = new ECGMonitor();
        aECGMonitor.init();
        aECGMonitor.start();
    }
}
