/*
 * BrainPong.java
 *
 * Brainpong extends the pong games and records from EEG and the keyboard while the player is playing
 */

package examples.application_container.game.pong;

import custom_renderer.BasicAudioRenderer;
import custom_renderer.NullAudioRenderer;
import datasource.DataSourceMerger;
import datasource.SignalDataSource;
import eeg.EEGDataModel;
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

public class BrainPong extends PongGame{
    private SignalDataSource eegSignalDataSource;
    private ProcessorPlayer eegProcessorPlayer;
    private SignalDataSource pongSignalDataSource;
    private ProcessorPlayer pongSignalProcessorPlayer;
    private ProcessorPlayer mergedSignalProcessorPlayer;
    private EEGDataPlugin eegDataPlugin;
    private DoubleDataBufferContainer eegDoubleDataBufferContainer;
    private Vector eegCodecs;
    private Vector dataSources;
    private Configuration eegConfig;
    private ScalingConfiguration scalingConfig;
    private PropertiesReader propertiesReader = new PropertiesReader();
    private String file_separator = System.getProperty("file.separator");
    private DataSource eegDS  = null;
    private DataSource pongSignalDS  = null;
    private DataSource mergedSignalDS  = null;
    private EEGMotorCortexLaplacePlugin eegMotorCortexLaplacePlugin;
    private ElectrodeAssignment electrodeAssignment;
    private EEGDataModel eegDataModel;
    private DataSourceMerger dataSourceMerger;
    
    
    public BrainPong() {
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
        
        //since everything can be used as a datasource as long as it can be made to stream its data, it is no big deal to use the keyboard and a pong game...
        //The pong datasource gets the data from the keyboard which it also offers the pong game as an input device.
        //Additionally, it streams the paddle and the ball position. The score is considered as not important and is therefore it is not streamed.
        pongSignalDataSource = new SignalDataSource();
        pongSignalDataSource.setMediaLocator(new MediaLocator("pong://"));
        pongSignalDataSource.init();
        pongSignalDS = pongSignalDataSource.getDataSource();
        
        eegSignalDataSource = new SignalDataSource();
        eegSignalDataSource.setMediaLocator(new MediaLocator("usbAmpA://"));
        eegSignalDataSource.init();
        eegDS = eegSignalDataSource.getDataSource();
        
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
        eegDataModel = new EEGDataModel();
        
        for (int i = 0; i < eegConfig.getChannelsInUse(); i++) {
            eegDoubleDataBufferContainer.getDataBuffer(i).setDistance((int)Rounding.round(4.0,0));
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
        
        pongSignalProcessorPlayer = new ProcessorPlayer();
        pongSignalProcessorPlayer.setUseVideoCodecs(false);
        pongSignalProcessorPlayer.setAudioRenderer(new NullAudioRenderer());
        pongSignalProcessorPlayer.setDataSource(pongSignalDS);
        //pongSignalProcessorPlayer.setMute(true);
        pongSignalProcessorPlayer.setVisible(true);
        
        eegProcessorPlayer = new ProcessorPlayer();
        eegProcessorPlayer.setUseVideoCodecs(false);
        eegProcessorPlayer.setAudioRenderer(new NullAudioRenderer());
        eegCodecs = new Vector();
        eegCodecs.add(eegMotorCortexLaplacePlugin);
        eegCodecs.add(eegDataPlugin);
        eegProcessorPlayer.setDataSource(eegDS,eegCodecs);
        //eegProcessorPlayer.setMute(true);
        eegProcessorPlayer.setVisible(true);
        
        dataSources = new Vector();
        dataSources.add(pongSignalDS);
        dataSources.add(eegDS);
        mergedSignalDS = DataSourceMerger.mergeDataSources(dataSources);
        
        mergedSignalProcessorPlayer = new ProcessorPlayer();
        mergedSignalProcessorPlayer.setUseVideoCodecs(false);
        mergedSignalProcessorPlayer.setAudioRenderer(new NullAudioRenderer());
        mergedSignalProcessorPlayer.setDataSource(mergedSignalDS);
        //mergedSignalProcessorPlayer.setMute(true);
        mergedSignalProcessorPlayer.setVisible(true);
        
        super.init();
        pongSignalProcessorPlayer.start();
        eegProcessorPlayer.start();
        mergedSignalProcessorPlayer.start();
    }
    
    public void initGameInputDevice(){
        //set the reference to the pong game in media.protocol.pong.CustomCaptureDevice
        ((media.protocol.pong.CustomCaptureDevice)((media.protocol.pong.DataSource)pongSignalDS).getCaptureDevice()).setSimplePongGame(this);
    }
    
    public static void main(String[] args) {
        BrainPong aBrainPong = new BrainPong();
        aBrainPong.init();
        aBrainPong.setVisible(true);
    }
}