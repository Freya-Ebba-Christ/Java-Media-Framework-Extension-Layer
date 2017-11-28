/*
 *
 * This application performs an EEG recording and uses the plugins.eeg.EEGMotorCortexLaplacePlugin to perform a surface laplacian.
 * The eeg.EEGDataModel is used to compute the power of 2 hz bands within the alpha beta range for C3 and C4.
 * The power bars are displayed by the AlphaBetaPanel
 */

package examples.motorcortex.eeg_recording;
import datasource.SignalDataSource;
import eeg.BasicEEGDataModel;
import eeg.EEGDataModel;
import eeg.utilities.ElectrodeAssignment;
import utilities.graphics.passive_rendering.ApplicationContainer;
import utilities.graphics.passive_rendering.Surface;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

public class AlphaBetaApplication extends ApplicationContainer implements KeyListener{
    
    private Surface surface;
    private AlphaBetaPainter painter;
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
    
    public AlphaBetaApplication() {
    }
    
    public AlphaBetaPainter getPainter() {
        return painter;
    }
    
    public void setPainter(AlphaBetaPainter painter) {
        this.painter = painter;
    }
    
    public Surface getSurface() {
        return surface;
    }
    
    public void setSurface(Surface surface) {
        this.surface = surface;
    }
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==e.VK_ESCAPE){
            System.exit(0);
        }
    }
    
    public void keyTyped(KeyEvent e) {
        
    }
    
    public void keyReleased(KeyEvent e) {
        
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
    
    public void init() {
        initEEG();
        
        addKeyListener(this);
        setRestoreLocationEnabled(false);
        setLocation(0,0);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        surface = new Surface();
        painter = new AlphaBetaPainter();
        painter.getPanel().setElectrodeAssignment(electrodeAssignment);
        eegDataModel.addObserver(painter.getPanel());
        painter.getPanel().setX(10);
        painter.getPanel().setY(10);
        painter.getPanel().setWidth((int)toolkit.getScreenSize().getWidth()-20);
        painter.getPanel().setHeight((int)toolkit.getScreenSize().getHeight()-20);
        surface.addPainter(painter);
        surface.setSize((int)toolkit.getScreenSize().getWidth(),(int)toolkit.getScreenSize().getHeight());
        surface.setPreferredSize(new Dimension((int)toolkit.getScreenSize().getWidth(),(int)toolkit.getScreenSize().getHeight()));
        surface.setMinimumSize(new Dimension((int)toolkit.getScreenSize().getWidth(),(int)toolkit.getScreenSize().getHeight()));
        surface.setMaximumSize(new Dimension((int)toolkit.getScreenSize().getWidth(),(int)toolkit.getScreenSize().getHeight()));
        surface.setCycles(30);
        surface.setFPSEnabled(true);
        surface.start();
        setVisualComponent(surface);
        super.init();
    }
    
    public static void main(String[] args) {
        AlphaBetaApplication aTopographicMapping = new AlphaBetaApplication();
        //fake fullscreen
        aTopographicMapping.setUndecorated(true);
        aTopographicMapping.init();
        aTopographicMapping.setVisible(true);
    }
}