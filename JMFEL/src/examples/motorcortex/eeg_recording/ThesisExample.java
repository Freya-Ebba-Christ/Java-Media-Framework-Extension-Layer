/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package examples.motorcortex.eeg_recording;

import application_container.SingleEEGChannelContainer;
import custom_renderer.NullAudioRenderer;
import datasink.CustomDatabaseDataSink;
import datasink.database.eeg.EEGDatabaseSourceHandler;
import datasource.CloneableDataSource;
import datasource.SignalDataSource;
import eeg.utilities.ElectrodeAssignment;
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
import plugins.eeg.EEGMotorCortexLaplacePlugin;
import simple_player.ProcessorPlayer;
import utilities.DoubleDataBufferContainer;
import utilities.PropertiesReader;

/**
 *
 * @author Urkman_2
 */
public class ThesisExample {

    private DataSource eegDS = null;
    private SignalDataSource eegSignalDataSource;
    private ProcessorPlayer eegProcessorPlayer;
    private PropertiesReader propertiesReader = new PropertiesReader();
    private String file_separator = System.getProperty("file.separator");
    private DoubleDataBufferContainer eegDoubleDataBufferContainer;
    private Configuration eegConfig;
    private ScalingConfiguration scalingConfig;
    private EEGDataPlugin eegDataPlugin;
    private Vector codecs;
    private SingleEEGChannelContainer singleEEGChannelContainer;
    private EEGMotorCortexLaplacePlugin eegMotorCortexLaplacePlugin;
    private ElectrodeAssignment electrodeAssignment;
    private CustomDatabaseDataSink eegDataBaseSink = null;
    private EEGDatabaseSourceHandler anEEGDatabaseSourceHandler = null;
    private CloneableDataSource cloneableEEGDS = null;
    private DataSource dsEEGSinkClone = null;

    private Configuration getAmplifierConfiguration(String name) {
        Configuration settings = new Configuration();
        settings.setModel(new ConfigurationTableModel());
        settings.setCommonGround(new CommonGround());
        settings.setCommonReference(new CommonReference());
        String settingsFile = System.getProperty("user.dir") + getFile_separator() + "media" + getFile_separator() + "protocol" + getFile_separator() + name + getFile_separator() + "configuration.ini";
        settings.load(settingsFile);
        return settings;
    }

    public ThesisExample() {
    }

    public EEGDatabaseSourceHandler getAnEEGDatabaseSourceHandler() {
        return anEEGDatabaseSourceHandler;
    }

    public CloneableDataSource getCloneableEEGDS() {
        return cloneableEEGDS;
    }

    public Vector getCodecs() {
        return codecs;
    }

    public DataSource getDsEEGSinkClone() {
        return dsEEGSinkClone;
    }

    public Configuration getEegConfig() {
        return eegConfig;
    }

    public DataSource getEegDS() {
        return eegDS;
    }

    public CustomDatabaseDataSink getEegDataBaseSink() {
        return eegDataBaseSink;
    }

    public EEGDataPlugin getEegDataPlugin() {
        return eegDataPlugin;
    }

    public DoubleDataBufferContainer getEegDoubleDataBufferContainer() {
        return eegDoubleDataBufferContainer;
    }

    public EEGMotorCortexLaplacePlugin getEegMotorCortexLaplacePlugin() {
        return eegMotorCortexLaplacePlugin;
    }

    public ProcessorPlayer getEegProcessorPlayer() {
        return eegProcessorPlayer;
    }

    public SignalDataSource getEegSignalDataSource() {
        return eegSignalDataSource;
    }

    public ElectrodeAssignment getElectrodeAssignment() {
        return electrodeAssignment;
    }

    public String getFile_separator() {
        return file_separator;
    }

    public PropertiesReader getPropertiesReader() {
        return propertiesReader;
    }

    public ScalingConfiguration getScalingConfig() {
        return scalingConfig;
    }

    public SingleEEGChannelContainer getSingleEEGChannelContainer() {
        return singleEEGChannelContainer;
    }

    public void setAnEEGDatabaseSourceHandler(EEGDatabaseSourceHandler anEEGDatabaseSourceHandler) {
        this.anEEGDatabaseSourceHandler = anEEGDatabaseSourceHandler;
    }

    public void setCloneableEEGDS(CloneableDataSource cloneableEEGDS) {
        this.cloneableEEGDS = cloneableEEGDS;
    }

    public void setCodecs(Vector codecs) {
        this.codecs = codecs;
    }

    public void setDsEEGSinkClone(DataSource dsEEGSinkClone) {
        this.dsEEGSinkClone = dsEEGSinkClone;
    }

    public void setEegConfig(Configuration eegConfig) {
        this.eegConfig = eegConfig;
    }

    public void setEegDS(DataSource eegDS) {
        this.eegDS = eegDS;
    }

    public void setEegDataBaseSink(CustomDatabaseDataSink eegDataBaseSink) {
        this.eegDataBaseSink = eegDataBaseSink;
    }

    public void setEegDataPlugin(EEGDataPlugin eegDataPlugin) {
        this.eegDataPlugin = eegDataPlugin;
    }

    public void setEegDoubleDataBufferContainer(DoubleDataBufferContainer eegDoubleDataBufferContainer) {
        this.eegDoubleDataBufferContainer = eegDoubleDataBufferContainer;
    }

    public void setEegMotorCortexLaplacePlugin(EEGMotorCortexLaplacePlugin eegMotorCortexLaplacePlugin) {
        this.eegMotorCortexLaplacePlugin = eegMotorCortexLaplacePlugin;
    }

    public void setEegProcessorPlayer(ProcessorPlayer eegProcessorPlayer) {
        this.eegProcessorPlayer = eegProcessorPlayer;
    }

    public void setEegSignalDataSource(SignalDataSource eegSignalDataSource) {
        this.eegSignalDataSource = eegSignalDataSource;
    }

    public void setElectrodeAssignment(ElectrodeAssignment electrodeAssignment) {
        this.electrodeAssignment = electrodeAssignment;
    }

    public void setFile_separator(String file_separator) {
        this.file_separator = file_separator;
    }

    public void setPropertiesReader(PropertiesReader propertiesReader) {
        this.propertiesReader = propertiesReader;
    }

    public void setScalingConfig(ScalingConfiguration scalingConfig) {
        this.scalingConfig = scalingConfig;
    }

    public void setSingleEEGChannelContainer(SingleEEGChannelContainer singleEEGChannelContainer) {
        this.singleEEGChannelContainer = singleEEGChannelContainer;
    }

    private ScalingConfiguration getScalingConfiguration(String name) {
        ScalingConfiguration scalingConfiguration = new ScalingConfiguration();
        String settingsFile = System.getProperty("user.dir") + getFile_separator() + "media" + getFile_separator() + "protocol" + getFile_separator() + name + getFile_separator() + "scaling.ini";
        scalingConfiguration.load(settingsFile);
        return scalingConfiguration;
    }

    public void init() {
        setEegProcessorPlayer(null);
        setEegSignalDataSource(new SignalDataSource());
        getEegSignalDataSource().setMediaLocator(new MediaLocator("testdevice://"));
        setEegConfig(getAmplifierConfiguration("usbAmpA"));
        setScalingConfig(getScalingConfiguration("usbAmpA"));
        getEegSignalDataSource().init();
        setCloneableEEGDS(new CloneableDataSource());
        setEegDS(getEegSignalDataSource().getDataSource());
        getCloneableEEGDS().setDataSource(getEegDS());
        setDsEEGSinkClone(getCloneableEEGDS().getClone());

        setEegDataPlugin(new EEGDataPlugin());
        getEegDataPlugin().setNumChannels(getEegConfig().getChannelsInUse());

        setEegDoubleDataBufferContainer(new DoubleDataBufferContainer());
        setEegMotorCortexLaplacePlugin(new EEGMotorCortexLaplacePlugin());
        getEegMotorCortexLaplacePlugin().setNumChannels(getEegConfig().getChannelsInUse());
        setElectrodeAssignment(new ElectrodeAssignment());
        getElectrodeAssignment().read(System.getProperty("user.dir") + getFile_separator() + "resources" + getFile_separator() + "eeg" + getFile_separator() + "channels_to_electrodes_mapping.ini");
        getEegMotorCortexLaplacePlugin().setElectrodeToChannelAssignment(getElectrodeAssignment());

        getEegDoubleDataBufferContainer().initializeBuffers(getEegConfig().getSampleRate(), getEegConfig().getChannelsInUse());
        for (int i = 0; i < getEegConfig().getChannelsInUse(); i++) {
            getEegDoubleDataBufferContainer().getDataBuffer(i).setDistance(1);
            try {
                getEegDoubleDataBufferContainer().getDataBuffer(i).setMaxVoltage(getScalingConfig().toMicroVolt(i));
            } catch (Exception e) {
                System.out.println(e);
            }
            getEegDoubleDataBufferContainer().getDataBuffer(i).setUnit(getScalingConfig().getUnit(i));
        }
        getEegDataPlugin().setDoubleDataBufferContainer(getEegDoubleDataBufferContainer());

        setSingleEEGChannelContainer(new SingleEEGChannelContainer());
        getSingleEEGChannelContainer().setBackGround("background_singleChannel.jpg");
        getSingleEEGChannelContainer().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getSingleEEGChannelContainer().setNumChannels(getEegConfig().getChannelsInUse());
        getSingleEEGChannelContainer().init();

        setCodecs(new Vector());
        getCodecs().add(getEegMotorCortexLaplacePlugin());
        getCodecs().add(getEegDataPlugin());


        setEegDataBaseSink(new CustomDatabaseDataSink());
        getEegDataBaseSink().setDatabaseLocation("jdbc:sqlserver://192.168.1.207;databaseName=AdRacerDB;user=sa;password=test");
        setAnEEGDatabaseSourceHandler(new EEGDatabaseSourceHandler());
        getAnEEGDatabaseSourceHandler().setSessionID("4711");
        getAnEEGDatabaseSourceHandler().setSamplerate(getEegConfig().getSampleRate());
        getAnEEGDatabaseSourceHandler().setBatchChunkLength(1024);
        getAnEEGDatabaseSourceHandler().setEnabled(true);
        getEegDataBaseSink().setSourceHandler(getAnEEGDatabaseSourceHandler());
        getEegDataBaseSink().setDataSource(getDsEEGSinkClone());

        //override some defaults...
        getSingleEEGChannelContainer().getSingleChannelEEGPainter().setDoubleDataBufferContainer(getEegDoubleDataBufferContainer());
        getSingleEEGChannelContainer().getSingleChannelEEGPainter().getPanel().getSignalPanel().setSampleRate(getEegConfig().getSampleRate());
    }

    public void start() {
        setEegProcessorPlayer(new ProcessorPlayer());
        getEegProcessorPlayer().setUseAudioCodecs(true);
        getEegProcessorPlayer().setUseVideoCodecs(false);
        getEegProcessorPlayer().setAudioRenderer(new NullAudioRenderer());
        getEegProcessorPlayer().setDataSource(getCloneableEEGDS().getCloneableDataSource(), getCodecs());
        getEegProcessorPlayer().setVisible(true);
        getEegProcessorPlayer().start();
        getSingleEEGChannelContainer().setVisible(true);
    }

    public static void main(String[] args) {
        ThesisExample aThesisExample = new ThesisExample();
        aThesisExample.init();
        aThesisExample.start();
    }
}