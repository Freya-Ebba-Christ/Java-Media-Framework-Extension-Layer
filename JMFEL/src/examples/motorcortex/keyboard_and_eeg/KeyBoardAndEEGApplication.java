/*
 * This application stores EEG and keyboard strokes to disk.
 */
package examples.motorcortex.keyboard_and_eeg;

import application_container.EEGMultiBandApplication;
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
import java.awt.Cursor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Properties;
import java.util.Vector;
import javax.media.MediaLocator;
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
import utilities.signal_processing.EEGSignalProcessor;

/**
 *
 * @author Administrator
 */
public class KeyBoardAndEEGApplication implements CaptureApplication {

    private CaptureStateApplication aCaptureStateApplication;
    private DataSource eegDS = null;
    private DataSource keyboardDS = null;
    private SignalDataSource eegSignalDataSource;
    private SignalDataSource keyboardSignalDataSource;
    private ProcessorPlayer eegProcessorPlayer;
    private ProcessorPlayer eegEnergyProcessorPlayer;
    private ProcessorPlayer keyBoardProcessorPlayer;
    private PropertiesReader propertiesReader = new PropertiesReader();
    private String file_separator = System.getProperty("file.separator");
    private EEGDataPlugin eegEnergyDataPlugin;
    private KeyboardDataPlugin keyBoardDataPlugin;
    private DoubleDataBufferContainer eegDoubleDataBufferContainer;
    private DoubleDataBufferContainer subbandDoubleDataBufferContainer;
    private DoubleDataBufferContainer keyBoardDoubleDataBufferContainer;
    private Configuration eegConfig;
    private ScalingConfiguration scalingConfig;
    private SingleEEGChannelContainer keyBoardChannelContainer;
    private Vector codecs;
    private Vector keyBoardCodecs;
    private Vector energyCodecs;
    private SimpleCaptureController simpleCaptureController;
    private File path;
    private SimpleDataSink aSimpleDataSink;
    private CloneableDataSource cloneableDataSource;
    private CloneableDataSource cloneableKeyboardDataSource;
    private DataSource dsClone = null;
    private DataSource dsEEGEnergyClone = null;
    private DataSource dsKeyboardClone = null;
    private EEGMultiBandApplication eegMultiBandApplication;

    public KeyBoardAndEEGApplication() {
    }

    private Configuration getAmplifierConfiguration(String name) {
        Configuration settings = new Configuration();
        settings.setModel(new ConfigurationTableModel());
        settings.setCommonGround(new CommonGround());
        settings.setCommonReference(new CommonReference());
        String settingsFile = System.getProperty("user.dir") + file_separator + "media" + file_separator + "protocol" + file_separator + name + file_separator + "configuration.ini";
        settings.load(settingsFile);
        return settings;
    }

    private ScalingConfiguration getScalingConfiguration(String name) {
        ScalingConfiguration scalingConfiguration = new ScalingConfiguration();
        String settingsFile = System.getProperty("user.dir") + file_separator + "media" + file_separator + "protocol" + file_separator + name + file_separator + "scaling.ini";
        scalingConfiguration.load(settingsFile);
        return scalingConfiguration;
    }

    public void open(File file) {
        if (eegProcessorPlayer != null) {
            eegProcessorPlayer.dispose();
        }
        path = file;
        eegProcessorPlayer = null;
        eegProcessorPlayer = null;
        eegSignalDataSource = new SignalDataSource();
        eegSignalDataSource.setMediaLocator(new MediaLocator("testdevice://"));
        eegConfig = getAmplifierConfiguration("usbAmpA");
        scalingConfig = getScalingConfiguration("usbAmpA");

        keyboardSignalDataSource = new SignalDataSource();
        keyboardSignalDataSource.setMediaLocator(new MediaLocator("keyboard://"));
        keyboardSignalDataSource.init();

        eegEnergyDataPlugin = new EEGDataPlugin();
        keyBoardDataPlugin = new KeyboardDataPlugin();
        eegEnergyDataPlugin.setNumChannels(eegConfig.getChannelsInUse());
        keyBoardDataPlugin.setNumChannels(1);

        eegDoubleDataBufferContainer = new DoubleDataBufferContainer();
        eegDoubleDataBufferContainer.initializeBuffers(eegConfig.getSampleRate(), eegConfig.getChannelsInUse());

        keyBoardDoubleDataBufferContainer = new DoubleDataBufferContainer();
        keyBoardDoubleDataBufferContainer.initializeBuffers(1000, 1);
        keyBoardDoubleDataBufferContainer.getDataBuffer(0).setMaxVoltage(3);
        keyBoardDoubleDataBufferContainer.getDataBuffer(0).setUnit(DoubleDataBuffer.UNIT_MICROVOLT);

        for (int i = 0; i < eegConfig.getChannelsInUse(); i++) {
            eegDoubleDataBufferContainer.getDataBuffer(i).setDistance(5);
            try {
                eegDoubleDataBufferContainer.getDataBuffer(i).setMaxVoltage(scalingConfig.toMicroVolt(i));
            } catch (Exception e) {
                System.out.println(e);
            }
            eegDoubleDataBufferContainer.getDataBuffer(i).setUnit(scalingConfig.getUnit(i));
        }

        subbandDoubleDataBufferContainer = new DoubleDataBufferContainer();
        subbandDoubleDataBufferContainer.initializeBuffers(eegConfig.getSampleRate(), eegConfig.getChannelsInUse());

        for (int i = 0; i < eegConfig.getChannelsInUse(); i++) {
            subbandDoubleDataBufferContainer.getDataBuffer(i).setDistance(5);
            try {
                subbandDoubleDataBufferContainer.getDataBuffer(i).setMaxVoltage(scalingConfig.toMicroVolt(i));
            } catch (Exception e) {
                System.out.println(e);
            }
            subbandDoubleDataBufferContainer.getDataBuffer(i).setUnit(scalingConfig.getUnit(i));
        }

        EEGDataModel eegData = new EEGDataModel();
        for (int i = 0; i < eegConfig.getChannelsInUse(); i++) {
            subbandDoubleDataBufferContainer.getDataBuffer(i).registerCallback(eegData);
            subbandDoubleDataBufferContainer.getDataBuffer(i).setID(i);
        }

        eegEnergyDataPlugin.setDoubleDataBufferContainer(subbandDoubleDataBufferContainer);
        keyBoardDataPlugin.setDoubleDataBufferContainer(keyBoardDoubleDataBufferContainer);

        //sub band panel
        EEGSignalProcessor eegSignalProcessor = EEGSignalProcessor.getInstance();
        eegSignalProcessor.initialze(eegConfig.getSampleRate());
        eegData.setEEGSignalProcessor(eegSignalProcessor);
        eegData.setDoubleDataBufferContainer(subbandDoubleDataBufferContainer);
        eegData.setNumChannels(eegConfig.getChannelsInUse());
        eegData.init();

        eegMultiBandApplication = new EEGMultiBandApplication();
        eegMultiBandApplication.setBackGround("background_multiband_measurement.jpg");
        eegMultiBandApplication.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        eegMultiBandApplication.setNumChannels(eegConfig.getChannelsInUse());
        eegMultiBandApplication.init();

        eegData.addObserver(eegMultiBandApplication.getMultiBandEEGPainter().getPanel());

        eegMultiBandApplication.getMultiBandEEGPainter().getPanel().getAlphaSignalPanel().setSampleRate(eegConfig.getSampleRate());
        eegMultiBandApplication.getMultiBandEEGPainter().getPanel().getBetaSignalPanel().setSampleRate(eegConfig.getSampleRate());
        eegMultiBandApplication.getMultiBandEEGPainter().getPanel().getDeltaSignalPanel().setSampleRate(eegConfig.getSampleRate());
        eegMultiBandApplication.getMultiBandEEGPainter().getPanel().getGammaSignalPanel().setSampleRate(eegConfig.getSampleRate());
        eegMultiBandApplication.getMultiBandEEGPainter().getPanel().getThetaSignalPanel().setSampleRate(eegConfig.getSampleRate());


        keyBoardChannelContainer = new SingleEEGChannelContainer();
        keyBoardChannelContainer.setBackGround("background_singleChannel.jpg");
        keyBoardChannelContainer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        keyBoardChannelContainer.setNumChannels(1);
        keyBoardChannelContainer.init();

        //override some defaults...
        keyBoardChannelContainer.getSingleChannelEEGPainter().setDoubleDataBufferContainer(keyBoardDoubleDataBufferContainer);
        keyBoardChannelContainer.getSingleChannelEEGPainter().getPanel().getSignalPanel().setDurationShown(true);
        keyBoardChannelContainer.getSingleChannelEEGPainter().getPanel().getSignalPanel().setSampleRate(1000);
        //we dont need any offset...
        keyBoardChannelContainer.getSingleChannelEEGPainter().getPanel().getSignalPanel().setSignalXOffset(0);
        
    }

    public void pause() {
        if (eegProcessorPlayer != null) {
            eegProcessorPlayer.stop();
            eegEnergyProcessorPlayer.stop();
        }

        if (aSimpleDataSink != null) {
            aSimpleDataSink.pause();
        }
    }

    public void start() {

        if (eegProcessorPlayer == null && eegSignalDataSource != null) {
            aCaptureStateApplication.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            eegSignalDataSource.init();

            keyboardDS = keyboardSignalDataSource.getDataSource();
            eegDS = eegSignalDataSource.getDataSource();
            cloneableDataSource = new CloneableDataSource();
            cloneableDataSource.setDataSource(eegDS);

            cloneableKeyboardDataSource = new CloneableDataSource();
            cloneableKeyboardDataSource.setDataSource(keyboardDS);
            dsKeyboardClone = cloneableKeyboardDataSource.getClone();

            dsClone = cloneableDataSource.getClone();
            dsEEGEnergyClone = cloneableDataSource.getClone();
            eegProcessorPlayer = new ProcessorPlayer();
            eegEnergyProcessorPlayer = new ProcessorPlayer();
            eegProcessorPlayer.setUseVideoCodecs(false);
            eegEnergyProcessorPlayer.setUseVideoCodecs(false);
            eegProcessorPlayer.setAudioRenderer(new NullAudioRenderer());
            eegEnergyProcessorPlayer.setAudioRenderer(new NullAudioRenderer());

            keyBoardProcessorPlayer = new ProcessorPlayer();
            keyBoardProcessorPlayer.setUseVideoCodecs(false);
            //keyBoardProcessorPlayer.setAudioRenderer(keyboardDataRenderer);

            codecs = new Vector();
            energyCodecs = new Vector();
            energyCodecs.add(eegEnergyDataPlugin);
            codecs.add(keyBoardDataPlugin);

            keyBoardProcessorPlayer.setDataSource(dsKeyboardClone,codecs);
            keyBoardProcessorPlayer.setUseVideoCodecs(false);
            keyBoardProcessorPlayer.setUseAudioCodecs(true);

            eegEnergyProcessorPlayer.setDataSource(dsEEGEnergyClone, energyCodecs);
            eegEnergyProcessorPlayer.setUseVideoCodecs(false);
            eegEnergyProcessorPlayer.setUseAudioCodecs(true);

            eegProcessorPlayer.setDataSource(cloneableDataSource.getCloneableDataSource());
        }

        if (eegProcessorPlayer != null) {
            eegProcessorPlayer.start();
            eegProcessorPlayer.setTitle("eegProcessorPlayer");
            keyBoardProcessorPlayer.setTitle("Keyboard signal player");
            eegEnergyProcessorPlayer.start();
            keyBoardProcessorPlayer.start();

            eegEnergyProcessorPlayer.setTitle("energyProcessorPlayer");

            aCaptureStateApplication.getCaptureStatePainter().getPanel().setPlayer(null);

            Vector datasources = new Vector();
            datasources.add(cloneableDataSource.getCloneableDataSource());
            datasources.add(cloneableKeyboardDataSource.getCloneableDataSource());

            if (aSimpleDataSink == null) {
                aSimpleDataSink = new SimpleDataSink();
                aSimpleDataSink.setFileType(new FileTypeDescriptor(FileTypeDescriptor.QUICKTIME));
                //no video here... just take some format...
                aSimpleDataSink.setVideoOutputFormat(new VideoFormat(VideoFormat.CINEPAK));
                aSimpleDataSink.setAudioOutputFormat(new AudioFormat(AudioFormat.LINEAR));
                aSimpleDataSink.setDataSource(DataSourceMerger.mergeDataSources(datasources));
                aSimpleDataSink.setFileName(path.getAbsolutePath());
                aSimpleDataSink.init();
                aSimpleDataSink.start();

            } else if (aSimpleDataSink != null) {
                aSimpleDataSink.resume();
            }

            keyBoardProcessorPlayer.setVisible(true);
            eegProcessorPlayer.setVisible(true);
            eegEnergyProcessorPlayer.setVisible(true);
            eegMultiBandApplication.setVisible(true);
            keyBoardChannelContainer.setVisible(true);
            keyBoardChannelContainer.setTitle("keyboard signal channel");
            aCaptureStateApplication.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public void stop() {
        aCaptureStateApplication.setCursor(new Cursor(Cursor.WAIT_CURSOR));

        if (aSimpleDataSink != null) {
            aSimpleDataSink.stop();
            aSimpleDataSink = null;
        }

        if (eegProcessorPlayer != null) {
            try {
                ((media.protocol.usbAmpA.CustomCaptureDevice) ((media.protocol.usbAmpA.DataSource) eegSignalDataSource.getDataSource()).getCaptureDevice()).stop();
                ((media.protocol.usbAmpA.CustomCaptureDevice) ((media.protocol.usbAmpA.DataSource) eegSignalDataSource.getDataSource()).getCaptureDevice()).close();
                eegProcessorPlayer.stop();

                eegEnergyProcessorPlayer.stop();
                keyBoardProcessorPlayer.stop();
                eegSignalDataSource.getDataSource().stop();
                eegSignalDataSource.getDataSource().disconnect();
                eegProcessorPlayer.dispose();
                keyBoardProcessorPlayer.dispose();
                eegEnergyProcessorPlayer.dispose();
                eegMultiBandApplication.dispose();
                keyBoardChannelContainer.dispose();

            } catch (Exception ex) {
                System.out.println(ex);
            }
            eegProcessorPlayer = null;
            eegSignalDataSource = null;
            eegEnergyProcessorPlayer = null;
            keyBoardProcessorPlayer = null;
        }
        aCaptureStateApplication.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void init() {
        aCaptureStateApplication = new CaptureStateApplication();
        aCaptureStateApplication.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aCaptureStateApplication.setTitle("");
        aCaptureStateApplication.setBackGround("background_capture_monitor.jpg");
        aCaptureStateApplication.setCaptureApplication(this);
        Properties props = propertiesReader.readProperties(System.getProperty("user.dir") + file_separator + "examples" + file_separator + "motorcortex" + file_separator + "eeg_recording" + file_separator + "tooltips.ini");
        aCaptureStateApplication.setMode(CaptureStateApplication.MODE_SAVE);
        aCaptureStateApplication.setNewButtonToolTip(props.getProperty("LOAD_SAVE_BUTTON"));
        aCaptureStateApplication.setPlayButtonToolTip(props.getProperty("PLAY_BUTTON"));
        aCaptureStateApplication.setStopButtonToolTip(props.getProperty("STOP_BUTTON"));
        aCaptureStateApplication.setPauseButtonToolTip(props.getProperty("PAUSE_BUTTON"));

        aCaptureStateApplication.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
                if (eegSignalDataSource != null) {

                    if (aSimpleDataSink != null) {
                        aSimpleDataSink.stop();
                        aSimpleDataSink = null;
                    }

                    ((media.protocol.usbAmpA.CustomCaptureDevice) ((media.protocol.usbAmpA.DataSource) eegSignalDataSource.getDataSource()).getCaptureDevice()).stop();
                    ((media.protocol.usbAmpA.CustomCaptureDevice) ((media.protocol.usbAmpA.DataSource) eegSignalDataSource.getDataSource()).getCaptureDevice()).close();
                }
                System.exit(0);
            }
        });

        aCaptureStateApplication.init();
        aCaptureStateApplication.setVisible(true);
    }

    public static void main(String[] args) {
        KeyBoardAndEEGApplication aKeyBoardAndEEGApplication = new KeyBoardAndEEGApplication();
        aKeyBoardAndEEGApplication.init();
    }
}