/*
 * This application captures the EEG signal and uses the application_container.SpectrumAnalyzerApplication to display the spectrum
 */

package examples.motorcortex.eeg_recording;

import application_container.SpectrumAnalyzerApplication;
import datasource.SignalDataSource;
import eeg.BasicEEGDataModel;
import eeg.utilities.ElectrodeAssignment;
import java.util.Vector;
import javax.media.MediaLocator;
import javax.media.protocol.DataSource;
import media.protocol.gtec.CommonGround;
import media.protocol.gtec.CommonReference;
import media.protocol.gtec.Configuration;
import media.protocol.gtec.ConfigurationTableModel;
import media.protocol.gtec.ScalingConfiguration;
import plugins.eeg.EEGDataPlugin;
import plugins.eeg.EEGMotorCortexLaplacePlugin;
import simple_player.ProcessorPlayer;
import utilities.DoubleDataBufferContainer;
import utilities.PropertiesReader;
import utilities.math.Rounding;
import utilities.signal_processing.EEGSignalProcessor;

/**
 *
 * @author Administrator
 */
public class SpectrumAnalyzer {
    
    private SignalDataSource eegSignalDataSource;
    private ProcessorPlayer eegProcessorPlayer;
    private EEGDataPlugin eegDataPlugin;
    private DoubleDataBufferContainer eegDoubleDataBufferContainer;
    private Vector codecs;
    private Configuration eegConfig;
    private ScalingConfiguration scalingConfig;
    private PropertiesReader propertiesReader = new PropertiesReader();
    private String file_separator = System.getProperty("file.separator");
    private DataSource eegDS  = null;
    private EEGMotorCortexLaplacePlugin eegMotorCortexLaplacePlugin;
    private ElectrodeAssignment electrodeAssignment;
    private BasicEEGDataModel eegDataModel;
    private SpectrumAnalyzerApplication spectrumAnalyzerApplication;
    
    /** Creates a new instance of SpectrumAnalyzer */
    public SpectrumAnalyzer() {
    }
    
    public void setSpectrumAnalyzerApplication(SpectrumAnalyzerApplication spectrumAnalyzerApplication) {
        this.spectrumAnalyzerApplication = spectrumAnalyzerApplication;
    }
    
    public SpectrumAnalyzerApplication getSpectrumAnalyzerApplication() {
        return spectrumAnalyzerApplication;
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
    
    private void initEEG(){
        eegSignalDataSource = new SignalDataSource();
        eegSignalDataSource.setMediaLocator(new MediaLocator("usbAmpA://"));
        eegConfig = getAmplifierConfiguration("usbAmpA");
        scalingConfig = getScalingConfiguration("usbAmpA");
        eegDataPlugin = new EEGDataPlugin();
        eegDataPlugin.setNumChannels(eegConfig.getChannelsInUse());
        eegDoubleDataBufferContainer = new DoubleDataBufferContainer();
        eegMotorCortexLaplacePlugin = new EEGMotorCortexLaplacePlugin();
        eegMotorCortexLaplacePlugin.setNumChannels(eegConfig.getChannelsInUse());
        electrodeAssignment = new ElectrodeAssignment();
        electrodeAssignment.read(System.getProperty("user.dir") + file_separator + "resources" + file_separator + "eeg" + file_separator +"channels_to_electrodes_mapping.ini");
        eegMotorCortexLaplacePlugin.setElectrodeToChannelAssignment(electrodeAssignment);
        
        eegDoubleDataBufferContainer.initializeBuffers(eegConfig.getSampleRate(),eegConfig.getChannelsInUse());
        eegDataModel = new BasicEEGDataModel();
        
        for (int i = 0; i < eegConfig.getChannelsInUse(); i++) {
            eegDoubleDataBufferContainer.getDataBuffer(i).setDistance((int)Rounding.round(1.0,0));
            try{
                eegDoubleDataBufferContainer.getDataBuffer(i).setMaxVoltage(scalingConfig.toMicroVolt(i));
            }catch(Exception e){System.out.println(e);};
            eegDoubleDataBufferContainer.getDataBuffer(i).setUnit(scalingConfig.getUnit(i));
            eegDoubleDataBufferContainer.getDataBuffer(i).setID(i);
            eegDoubleDataBufferContainer.getDataBuffer(i).registerCallback(eegDataModel);
        }
        
        EEGSignalProcessor.getInstance().initialze(eegConfig.getSampleRate());
        
        eegDataModel.setEEGSignalProcessor(EEGSignalProcessor.getInstance());
        eegDataModel.setDoubleDataBufferContainer(eegDoubleDataBufferContainer);
        eegDataModel.setNumChannels(eegConfig.getChannelsInUse());
        eegDataModel.init();
        
        eegDataPlugin.setDoubleDataBufferContainer(eegDoubleDataBufferContainer);
        eegMotorCortexLaplacePlugin.setDoubleDataBufferContainer(eegDoubleDataBufferContainer);
        eegSignalDataSource.init();
        eegDS = eegSignalDataSource.getDataSource();
        eegProcessorPlayer = new ProcessorPlayer();
        codecs = new Vector();
        codecs.add(eegMotorCortexLaplacePlugin);
        codecs.add(eegDataPlugin);
        eegProcessorPlayer.setDataSource(eegDS,codecs);
        eegProcessorPlayer.setMute(true);
        eegProcessorPlayer.setVisible(true);
        eegProcessorPlayer.start();
    }
    
    public void start(){
        initEEG();
        setSpectrumAnalyzerApplication(new SpectrumAnalyzerApplication());
        getSpectrumAnalyzerApplication().setBackGround("background_spectrum_analyzer.jpg");
        getSpectrumAnalyzerApplication().setNumChannels(eegConfig.getChannelsInUse());
        getSpectrumAnalyzerApplication().init();
        eegDataModel.addObserver(getSpectrumAnalyzerApplication().getSpectrumPainter().getPanel());
        getSpectrumAnalyzerApplication().setVisible(true);
    }
    
    public static void main(String[] args) {
        SpectrumAnalyzer aSpectrumAnalyzer = new SpectrumAnalyzer();
        aSpectrumAnalyzer.start();
    }
}