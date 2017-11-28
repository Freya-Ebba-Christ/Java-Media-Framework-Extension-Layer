/*
 * AdracerDataAcquisition.java
 *
 * Created on 16. August 2007, 19:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import adracer.AdracerApplications;
import adracer.BottomMenuFrame;
import eeg.EEGDataModel;
import adracer.ToolBarButtonListener;
import adracer.TopMenuFrame;
import plugins.eeg.EEGDataPlugin;
import adracer.plugin.eye.EyeTrackerDataPlugin;
import adracer.tools.AdracerPropertiesReader;
import datasink.CustomDatabaseDataSink;
import datasink.database.eeg.EEGDatabaseSourceHandler;
import datasink.database.eye.EyetrackerDataSourceHandler;
import datasource.CloneableDataSource;
import datasource.SignalDataSource;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Vector;
import javax.media.MediaLocator;
import javax.media.protocol.DataSource;
import javax.swing.JFrame;
import media.protocol.gtec.Configuration;
import media.protocol.gtec.ScalingConfiguration;
import simple_player.ProcessorPlayer;
import utilities.DoubleDataBufferContainer;
import utilities.math.Rounding;
import utilities.signal_processing.EEGSignalProcessor;

/**
 *
 * @author Administrator
 */
public class AdracerDataAcquisition {

    private ToolBarButtonListener toolBarButtonListener = new ToolBarButtonListener();
    private TopMenuFrame topMenuFrame = new TopMenuFrame();
    private BottomMenuFrame bottomMenuFrame = new BottomMenuFrame();
    private AdracerApplications adracerApplications = new AdracerApplications();
    private DataSource dsEyetracker = null;
    private DataSource dsEEG = null;
    private SignalDataSource aTrackerDataSource;
    private SignalDataSource eegDataSource;
    private ProcessorPlayer aEyeTrackerProcessorPlayer;
    private ProcessorPlayer eegNormalProcessorPlayer;
    private ProcessorPlayer eegEnergyProcessorPlayer;
    private ProcessorPlayer eegMainProcessorPlayer;
    private CloneableDataSource cloneableTrackerDS = null;
    private CloneableDataSource cloneableEEGDS = null;
    private DataSource dsTrackerClone = null;
    private DataSource dsEEGSinkClone = null;
    private DataSource dsEEGNormalClone = null;
    private DataSource dsEEGEnergyClone = null;
    private CustomDatabaseDataSink eyetrackerDataBaseSink = null;
    private CustomDatabaseDataSink eegDataBaseSink = null;
    private EyetrackerDataSourceHandler anEyetrackerDatabaseSourceHandler = null;
    private EEGDatabaseSourceHandler anEEGDatabaseSourceHandler = null;
    private AdracerPropertiesReader propertiesReader = new AdracerPropertiesReader();
    private EyeTrackerDataPlugin aEyeTrackerDataPlugin;
    private EEGDataPlugin eegDataPlugin;
    private EEGDataPlugin eegEnergyDataPlugin;
    private Configuration eegConfiguration;
    private ScalingConfiguration scalingConfiguration;
    private EEGSignalProcessor eegSignalProcessor;

    public ScalingConfiguration getScalingConfiguration() {
        return scalingConfiguration;
    }

    public void setScalingConfiguration(ScalingConfiguration scalingConfiguration) {
        this.scalingConfiguration = scalingConfiguration;
    }

    public BottomMenuFrame getBottomMenuFrame() {
        return bottomMenuFrame;
    }

    public Configuration getEEGConfiguration() {
        return eegConfiguration;
    }

    public void setEEGConfiguration(Configuration eegConfiguration) {
        this.eegConfiguration = eegConfiguration;
    }

    public AdracerApplications getAdracerApplications() {
        return adracerApplications;
    }

    public void setEEGDatabaseSourceHandler(EEGDatabaseSourceHandler anEEGDatabaseSourceHandler) {
        this.anEEGDatabaseSourceHandler = anEEGDatabaseSourceHandler;
    }

    public EEGDatabaseSourceHandler getEEGDatabaseSourceHandler() {
        return anEEGDatabaseSourceHandler;
    }

    public CustomDatabaseDataSink getEEGDataBaseSink() {
        return eegDataBaseSink;
    }

    public void setEEGDataBaseSink(CustomDatabaseDataSink eegDataBaseSink) {
        this.eegDataBaseSink = eegDataBaseSink;
    }

    public void setAdracerApplications(AdracerApplications adracerApplications) {
        this.adracerApplications = adracerApplications;
    }

    public void setBottomMenuFrame(BottomMenuFrame bottomMenuFrame) {
        this.bottomMenuFrame = bottomMenuFrame;
    }

    public TopMenuFrame getTopMenuFrame() {
        return topMenuFrame;
    }

    public void setTopMenuFrame(TopMenuFrame topMenuFrame) {
        this.topMenuFrame = topMenuFrame;
    }

    public ToolBarButtonListener getToolBarButtonListener() {
        return toolBarButtonListener;
    }

    public void setToolBarButtonListener(ToolBarButtonListener toolBarButtonListener) {
        this.toolBarButtonListener = toolBarButtonListener;
    }

    public void centerHorizontal(JFrame frame) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((int) dim.getWidth() / 2 - frame.getWidth() / 2, (int) frame.getLocation().getY());
    }

    public void centerVertical(JFrame frame) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((int) frame.getLocation().getX(), (int) dim.getHeight() / 2 - frame.getHeight() / 2);
    }

    private void addButtonHandler() {

        getTopMenuFrame().getEyeTrackerButton().addActionListener(getToolBarButtonListener());
        getTopMenuFrame().getEyeTrackerButton().setActionCommand(TopMenuFrame.EYETRACKER_BUTTON);

        getTopMenuFrame().getGazeButton().addActionListener(getToolBarButtonListener());
        getTopMenuFrame().getGazeButton().setActionCommand(TopMenuFrame.GAZE_BUTTON);

        getTopMenuFrame().getMultiChannelEEGButton().addActionListener(getToolBarButtonListener());
        getTopMenuFrame().getMultiChannelEEGButton().setActionCommand(TopMenuFrame.MULTICHANNEL_EEG_BUTTON);

        getTopMenuFrame().getSingleChannelEEGButton().addActionListener(getToolBarButtonListener());
        getTopMenuFrame().getSingleChannelEEGButton().setActionCommand(TopMenuFrame.SINGLE_CHANNEL_EEG_BUTTON);

        getTopMenuFrame().getSubbandEEGButton().addActionListener(getToolBarButtonListener());
        getTopMenuFrame().getSubbandEEGButton().setActionCommand(TopMenuFrame.SUBBAND_EEG_BUTTON);

        getTopMenuFrame().getEnergyBandButton().addActionListener(getToolBarButtonListener());
        getTopMenuFrame().getEnergyBandButton().setActionCommand(TopMenuFrame.ENERGY_BANDS_BUTTON);

        getTopMenuFrame().getPowerBandButton().addActionListener(getToolBarButtonListener());
        getTopMenuFrame().getPowerBandButton().setActionCommand(TopMenuFrame.POWER_BANDS_BUTTON);

        getTopMenuFrame().getCameraButton().addActionListener(getToolBarButtonListener());
        getTopMenuFrame().getCameraButton().setActionCommand(TopMenuFrame.CAMERA_BUTTON);

        getBottomMenuFrame().getCalibrateTrackerButton().addActionListener(getToolBarButtonListener());
        getBottomMenuFrame().getCalibrateTrackerButton().setActionCommand(BottomMenuFrame.CALIBRATE_TRACKER_BUTTON);

        getBottomMenuFrame().getStartStopTrackerButton().addActionListener(getToolBarButtonListener());
        getBottomMenuFrame().getStartStopTrackerButton().setActionCommand(BottomMenuFrame.START_STOP_TRACKER_BUTTON);
        getBottomMenuFrame().getStartStopTrackerButton().setText("Tracker is stopped");

        getBottomMenuFrame().getStartStopEEGButton().addActionListener(getToolBarButtonListener());
        getBottomMenuFrame().getStartStopEEGButton().setActionCommand(BottomMenuFrame.START_STOP_EEG_BUTTON);
        getBottomMenuFrame().getStartStopEEGButton().setText("EEG is stopped");

        getBottomMenuFrame().getExportStateMonitorButton().addActionListener(getToolBarButtonListener());
        getBottomMenuFrame().getExportStateMonitorButton().setActionCommand(BottomMenuFrame.EXPORT_STATE_MONITOR);

        getBottomMenuFrame().getStartStopTrackerExportButton().addActionListener(getToolBarButtonListener());
        getBottomMenuFrame().getStartStopTrackerExportButton().setActionCommand(BottomMenuFrame.START_STOP_TRACKER_EXPORT);
        getBottomMenuFrame().getStartStopTrackerExportButton().setText("DB Export is stopped");
    }

    public EyetrackerDataSourceHandler getEyetrackerDatabaseSourceHandler() {
        return anEyetrackerDatabaseSourceHandler;
    }

    public ProcessorPlayer getEyeTrackerProcessorPlayer() {
        return aEyeTrackerProcessorPlayer;
    }

    public CustomDatabaseDataSink getEyetrackerDataBaseSink() {
        return eyetrackerDataBaseSink;
    }

    /** Creates a new instance of AdracerDataAcquisition */
    public AdracerDataAcquisition() {

        //do basic initializations
        addButtonHandler();

        //perform initializations. NOTE: this uses default settings, you can reset(literally) them later. Take the reconfiguration of the EyeTrackerApplication as an examlple
        getAdracerApplications().initApplications();
        getToolBarButtonListener().setAdracerApplications(getAdracerApplications());

        //the DataPlugin/EyeTrackerDataPlugin provides access to the data sent by the eyetracker.
        aEyeTrackerDataPlugin = new EyeTrackerDataPlugin();

        //likewise the EEGDataPlugin provides access to the data sent by the eeg-device.

        //the Adracerapplication is designed support up to 16 channels or only one amplifier at a time!
        //usbAmpA is just one of the JMF-devices, it really is just a name, since the actual physical device is depicted by the serial number, which can be changed at any time.
        //In other words usbAmpA, usbAmpB, usbAmpC and usbAmpD are all equally good.
        eegDataPlugin = new EEGDataPlugin();
        eegEnergyDataPlugin = new EEGDataPlugin();
        setEEGConfiguration(propertiesReader.getAmplifierConfiguration("usbAmpA"));
        setScalingConfiguration(propertiesReader.getScalingConfiguration("usbAmpA"));
        eegDataPlugin.setNumChannels(getEEGConfiguration().getChannelsInUse());
        eegEnergyDataPlugin.setNumChannels(getEEGConfiguration().getChannelsInUse());

        // make new databuffers. These buffers are actually very high speed fixed size ringbuffers.
        //The distance is the number of inserts that will pass by before the a new copy of the content is made. These copies can then be optained by the toArray() method.
        //Also callbacks are called during each interval.

        DoubleDataBufferContainer eegDoubleDataBufferContainer = new DoubleDataBufferContainer();
        eegDoubleDataBufferContainer.initializeBuffers(getEEGConfiguration().getSampleRate() * 5, getEEGConfiguration().getChannelsInUse());
        for (int i = 0; i < getEEGConfiguration().getChannelsInUse(); i++) {
            eegDoubleDataBufferContainer.getDataBuffer(i).setDistance((int) Rounding.round(16.0, 0));
            try {
                eegDoubleDataBufferContainer.getDataBuffer(i).setMaxVoltage(getScalingConfiguration().toMicroVolt(i));
            } catch (Exception e) {
                System.out.println(e);
            }
            ;
            eegDoubleDataBufferContainer.getDataBuffer(i).setUnit(getScalingConfiguration().getUnit(i));
        }
        eegDataPlugin.setDoubleDataBufferContainer(eegDoubleDataBufferContainer);

        DoubleDataBufferContainer subbandDoubleDataBufferContainer = new DoubleDataBufferContainer();
        subbandDoubleDataBufferContainer.initializeBuffers(getEEGConfiguration().getSampleRate(), getEEGConfiguration().getChannelsInUse());

        for (int i = 0; i < getEEGConfiguration().getChannelsInUse(); i++) {
            subbandDoubleDataBufferContainer.getDataBuffer(i).setDistance((int) Rounding.round(16.0, 0));
            try {
                subbandDoubleDataBufferContainer.getDataBuffer(i).setMaxVoltage(getScalingConfiguration().toMicroVolt(i));
            } catch (Exception e) {
                System.out.println(e);
            }
            ;
            subbandDoubleDataBufferContainer.getDataBuffer(i).setUnit(getScalingConfiguration().getUnit(i));
        }
        eegEnergyDataPlugin.setDoubleDataBufferContainer(subbandDoubleDataBufferContainer);

        //now connect the eeg user interfaces with the data buffers.

        //"model" to store filtered signals
        EEGDataModel eegData = new EEGDataModel();
        // single channel panel
        getAdracerApplications().getSingleEEGChannelContainer().getSingleChannelEEGPainter().setDoubleDataBufferContainer(eegDoubleDataBufferContainer);
        getAdracerApplications().getSingleEEGChannelContainer().getSingleChannelEEGPainter().getPanel().getSignalPanel().setSampleRate(getEEGConfiguration().getSampleRate());

        //multi channel eeg panel
        getAdracerApplications().getMultichannelEEGApplication().getMultiChannelEEGPainter().getPanel().setDoubleDataBufferContainer(eegDoubleDataBufferContainer);

        //(re)configure some signal specific settings
        for (int i = 0; i < getEEGConfiguration().getChannelsInUse(); i++) {
            getAdracerApplications().getMultichannelEEGApplication().getMultiChannelEEGPainter().getPanel().getSignalPanelVector().get(i).setSampleRate(getEEGConfiguration().getSampleRate());
        }

        for (int i = 0; i < getEEGConfiguration().getChannelsInUse(); i++) {
            subbandDoubleDataBufferContainer.getDataBuffer(i).registerCallback(eegData);
            subbandDoubleDataBufferContainer.getDataBuffer(i).setID(i);
        }

        //sub band panel
        eegSignalProcessor = EEGSignalProcessor.getInstance();
        eegSignalProcessor.initialze(getEEGConfiguration().getSampleRate());
        eegData.setEEGSignalProcessor(eegSignalProcessor);
        eegData.setDoubleDataBufferContainer(subbandDoubleDataBufferContainer);
        eegData.setNumChannels(getEEGConfiguration().getChannelsInUse());
        eegData.init();

        eegData.addObserver(getAdracerApplications().getEEGMultibandApplication().getMultiBandEEGPainter().getPanel());
        eegData.addObserver(getAdracerApplications().getEEGEnergyApplication().getEEGEnergyPainter().getPanel());
        eegData.addObserver(getAdracerApplications().getEEGPowerApplication().getEEGPowerPainter().getPanel());

        getAdracerApplications().getEEGMultibandApplication().getMultiBandEEGPainter().getPanel().getAlphaSignalPanel().setSampleRate(getEEGConfiguration().getSampleRate());
        getAdracerApplications().getEEGMultibandApplication().getMultiBandEEGPainter().getPanel().getBetaSignalPanel().setSampleRate(getEEGConfiguration().getSampleRate());
        getAdracerApplications().getEEGMultibandApplication().getMultiBandEEGPainter().getPanel().getDeltaSignalPanel().setSampleRate(getEEGConfiguration().getSampleRate());
        getAdracerApplications().getEEGMultibandApplication().getMultiBandEEGPainter().getPanel().getGammaSignalPanel().setSampleRate(getEEGConfiguration().getSampleRate());
        getAdracerApplications().getEEGMultibandApplication().getMultiBandEEGPainter().getPanel().getThetaSignalPanel().setSampleRate(getEEGConfiguration().getSampleRate());

        //connect the signalpanel of the Eyetracker blink panel with the data plugin.
        // tell the player what to play and where to send the data for further processing
        aEyeTrackerDataPlugin.setSamplerate(Integer.parseInt((String) propertiesReader.getEyeTrackerProperties().getProperty("SAMPLERATE")));
        aEyeTrackerDataPlugin.initBuffers();

        //override default settings
        getAdracerApplications().getEyeTrackerApplication().getBlinkInfoPainter().getInfoPanel().getSignalPanel().setSampleRate(Integer.parseInt((String) propertiesReader.getEyeTrackerProperties().getProperty("SAMPLERATE")));
        getAdracerApplications().getEyeTrackerApplication().getBlinkInfoPainter().getInfoPanel().getSignalPanel().setDoubleDataBufferContainer(aEyeTrackerDataPlugin.getDoubleDataBufferContainer());
        getAdracerApplications().getEyeTrackerApplication().getBlinkInfoPainter().getInfoPanel().getSignalPanel().setBufferLength(aEyeTrackerDataPlugin.getDoubleDataBufferContainer().getBufferLength());

        //connect the gaze plugin with the datagramdecoder
        getAdracerApplications().getEyeTrackerGazeApplication().getGazeDataPainter().getGazePanel().setDatagramdecoder(aEyeTrackerDataPlugin.getDatagramdecoder());

        //now connect the eyetracker panels with the datasource
        aTrackerDataSource = new SignalDataSource();
        aTrackerDataSource.setMediaLocator(new MediaLocator("eye://"));
        aTrackerDataSource.init();
        cloneableTrackerDS = new CloneableDataSource();
        dsEyetracker = aTrackerDataSource.getDataSource();
        cloneableTrackerDS.setDataSource(dsEyetracker);
        dsTrackerClone = cloneableTrackerDS.getClone();
        aEyeTrackerProcessorPlayer = new ProcessorPlayer();

        aEyeTrackerProcessorPlayer.setAudioRenderer(aEyeTrackerDataPlugin);
        aEyeTrackerProcessorPlayer.setDataSource(cloneableTrackerDS.getCloneableDataSource());

        //now do the same thing with the eeg
        eegDataSource = new SignalDataSource();
        eegDataSource.setMediaLocator(new MediaLocator("usbAmpA://"));
        eegDataSource.init();
        cloneableEEGDS = new CloneableDataSource();
        dsEEG = eegDataSource.getDataSource();
        cloneableEEGDS.setDataSource(dsEEG);
        dsEEGSinkClone = cloneableEEGDS.getClone();
        dsEEGNormalClone = cloneableEEGDS.getClone();
        dsEEGEnergyClone = cloneableEEGDS.getClone();

        eegNormalProcessorPlayer = new ProcessorPlayer();
        eegNormalProcessorPlayer.setUseVideoCodecs(false);
        eegNormalProcessorPlayer.setUseAudioCodecs(true);

        eegEnergyProcessorPlayer = new ProcessorPlayer();
        eegEnergyProcessorPlayer.setUseVideoCodecs(false);
        eegEnergyProcessorPlayer.setUseAudioCodecs(true);

        eegMainProcessorPlayer = new ProcessorPlayer();
        eegMainProcessorPlayer.setUseVideoCodecs(false);
        eegMainProcessorPlayer.setUseAudioCodecs(true);

        Vector normalCodecs = new Vector();
        Vector energyCodecs = new Vector();

        normalCodecs.add(eegDataPlugin);
        energyCodecs.add(eegEnergyDataPlugin);

        eegMainProcessorPlayer.setDataSource(cloneableEEGDS.getCloneableDataSource());
        eegNormalProcessorPlayer.setDataSource(dsEEGNormalClone, normalCodecs);
        eegEnergyProcessorPlayer.setDataSource(dsEEGEnergyClone, energyCodecs);

        eegNormalProcessorPlayer.setMute(true);
        eegEnergyProcessorPlayer.setMute(true);
        eegMainProcessorPlayer.setMute(true);

        getAdracerApplications().setEEGMainProcessorPlayer(eegMainProcessorPlayer);
        getAdracerApplications().setEEGNormalProcessorPlayer(eegNormalProcessorPlayer);
        getAdracerApplications().setEEGEnergyProcessorPlayer(eegEnergyProcessorPlayer);

        //make new custom data sink
        eyetrackerDataBaseSink = new CustomDatabaseDataSink();
        eyetrackerDataBaseSink.setDatabaseLocation(propertiesReader.getDataBaseProperties().getProperty("DB_LOCATION"));
        getAdracerApplications().setEyeTrackerDatabaseDatasink(eyetrackerDataBaseSink);
        // make new EyetrackerDataSourceHandler
        anEyetrackerDatabaseSourceHandler = new EyetrackerDataSourceHandler();
        getAdracerApplications().setEyetrackerDataSourceHandler(getEyetrackerDatabaseSourceHandler());
        // this is a database handler that uses batch processing, so we have to set a batch chunk length to reasonable value, 60 schould be fine...;-)
        anEyetrackerDatabaseSourceHandler.setBatchChunkLength(60);

        // now make the handler aware of the physical properties of the driving simulator, the tracker is built into.
        anEyetrackerDatabaseSourceHandler.setScreenWidth(getAdracerApplications().getEyeTrackerGazeApplication().getGazeDataPainter().getGazePanel().getWidth());
        anEyetrackerDatabaseSourceHandler.setScreenHeight(getAdracerApplications().getEyeTrackerGazeApplication().getGazeDataPainter().getGazePanel().getHeight());
        anEyetrackerDatabaseSourceHandler.setHorizontalMaximum(Integer.parseInt((String) propertiesReader.getEyeTrackerProperties().getProperty("HORIZONTAL_MAXIMUM")));
        anEyetrackerDatabaseSourceHandler.setHorizontalMinimum(Integer.parseInt((String) propertiesReader.getEyeTrackerProperties().getProperty("HORIZONTAL_MINIMUM")));
        anEyetrackerDatabaseSourceHandler.setVerticalMaximum(Integer.parseInt((String) propertiesReader.getEyeTrackerProperties().getProperty("VERTICAL_MAXIMUM")));
        anEyetrackerDatabaseSourceHandler.setVerticalMinimum(Integer.parseInt((String) propertiesReader.getEyeTrackerProperties().getProperty("VERTICAL_MINIMUM")));

        // initially set the session id
        getEyetrackerDatabaseSourceHandler().setSessionID(propertiesReader.getConfig().getProperty("SESSION_ID"));

        //now set the sourcehandler for the database sink, so the sink how to handle the data that is coming from the data source, hence the name source handler and NOT the name sink handler.
        eyetrackerDataBaseSink.setSourceHandler(anEyetrackerDatabaseSourceHandler);

        //also tell the sink, what data to process
        eyetrackerDataBaseSink.setDataSource(dsTrackerClone);
        //finally start the transmission
        getAdracerApplications().setEyeTrackerProcessorPlayer(aEyeTrackerProcessorPlayer);

        eegDataBaseSink = new CustomDatabaseDataSink();
        anEEGDatabaseSourceHandler = new EEGDatabaseSourceHandler();
        getEEGDataBaseSink().setDatabaseLocation(propertiesReader.getDataBaseProperties().getProperty("DB_LOCATION"));
        getAdracerApplications().setEEGDatabaseSourceHandler(getEEGDatabaseSourceHandler());
        getAdracerApplications().setEEGDatabaseDatasink(getEEGDataBaseSink());
        getEEGDatabaseSourceHandler().setSessionID(propertiesReader.getConfig().getProperty("SESSION_ID"));
        getEEGDatabaseSourceHandler().setSamplerate(getEEGConfiguration().getSampleRate());
        getEEGDatabaseSourceHandler().setBatchChunkLength(1024);
        getEEGDatabaseSourceHandler().setEnabled(false);
        getEEGDataBaseSink().setSourceHandler(getEEGDatabaseSourceHandler());
        getEEGDataBaseSink().setDataSource(dsEEGSinkClone);
        getAdracerApplications().getDBExportStateApplication().getExportStatePainter().getExportStatePanel().setEyetrackerDataSourceHandler(getEyetrackerDatabaseSourceHandler());
        getAdracerApplications().getDBExportStateApplication().getExportStatePainter().getExportStatePanel().setEEGDatabaseSourceHandler(getEEGDatabaseSourceHandler());
        eegMainProcessorPlayer.start();

        getTopMenuFrame().setLocation(0, 0);
        centerHorizontal(getTopMenuFrame());
        getTopMenuFrame().setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        getBottomMenuFrame().setLocation(0, (int) dim.getHeight() - getBottomMenuFrame().getHeight() * 2);
        centerHorizontal(getBottomMenuFrame());
        getBottomMenuFrame().setVisible(true);

    }

    public static void main(String[] args) {
        AdracerDataAcquisition aAdracerDataAcquisition = new AdracerDataAcquisition();
    }
}