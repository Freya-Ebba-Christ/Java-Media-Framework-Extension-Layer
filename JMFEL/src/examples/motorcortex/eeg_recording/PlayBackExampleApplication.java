/*
 * PlayBackExampleApplication.java
 * This application displays the ECG, EEG and the video track provided by a test quicktime file. 
 */
package examples.motorcortex.eeg_recording;

import application_container.ECGApplication;
import application_container.MultichannelEEGApplication;
import datasource.SignalDataSource;
import java.awt.Color;
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

/**
 *
 * @author Administrator
 */
public class PlayBackExampleApplication {

    private String file_separator = System.getProperty("file.separator");
    private DataSource dsFile = null;
    private SignalDataSource aFileDataSource;
    private ProcessorPlayer aPlayer;
    private Vector plugins = new Vector();
    private Configuration eegConfig;
    private ScalingConfiguration scalingConfig;
    private EEGDataPlugin eegDataPlugin;
    private DoubleDataBufferContainer eegDoubleDataBufferContainer;
    private DoubleDataBufferContainer ecgDoubleDataBufferContainer;
    private MultichannelEEGApplication multichannelEEGApplication;
    private EEGDataPlugin ecgDataPlugin;
    private ECGApplication ecgApplication;

    /** Creates a new instance of PlayBackExampleApplication */
    public PlayBackExampleApplication() {
    }

    private Configuration getAmplifierConfiguration(String name) {
        Configuration settings = new Configuration();
        settings.setModel(new ConfigurationTableModel());
        settings.setCommonGround(new CommonGround());
        settings.setCommonReference(new CommonReference());
        String file_separator = System.getProperty("file.separator");
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

    public void open() {
        if (aPlayer != null) {
            aPlayer.dispose();
        }
        aPlayer = null;
        aPlayer = new ProcessorPlayer();
        aPlayer.setUseAudioCodecs(true);
        aPlayer.setUseVideoCodecs(false);
        aFileDataSource = new SignalDataSource();
        aFileDataSource.setMediaLocator(new MediaLocator("file://c:/Dokumente und Einstellungen/Administrator/Beaker/src/examples/recorded data/sleepeeg_3.qt"));
        aFileDataSource.init();
        eegConfig = getAmplifierConfiguration("usbAmpA");
        scalingConfig = getScalingConfiguration("usbAmpA");
        eegDataPlugin = new EEGDataPlugin();
        eegDataPlugin.setNumChannels(eegConfig.getChannelsInUse());
        eegDoubleDataBufferContainer = new DoubleDataBufferContainer();
        eegDoubleDataBufferContainer.initializeBuffers(eegConfig.getSampleRate(), eegConfig.getChannelsInUse());

        for (int i = 0; i < eegConfig.getChannelsInUse(); i++) {
            eegDoubleDataBufferContainer.getDataBuffer(i).setDistance(1);
            try {
                eegDoubleDataBufferContainer.getDataBuffer(i).setMaxVoltage(scalingConfig.toMicroVolt(i));
            } catch (Exception e) {
                System.out.println(e);
            }
            ;
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
            multichannelEEGApplication.getMultiChannelEEGPainter().getPanel().getSignalPanelVector().get(i).setSignalColor(Color.WHITE);
        }

        plugins.add(eegDataPlugin);

        ecgDataPlugin = new EEGDataPlugin();
        ecgDataPlugin.setNumChannels(eegConfig.getChannelsInUse());

        ecgDoubleDataBufferContainer = new DoubleDataBufferContainer();
        ecgDoubleDataBufferContainer.initializeBuffers(eegConfig.getSampleRate() * 10, eegConfig.getChannelsInUse());
        for (int i = 0; i < eegConfig.getChannelsInUse(); i++) {
            ecgDoubleDataBufferContainer.getDataBuffer(i).setDistance(5);
            try {
                ecgDoubleDataBufferContainer.getDataBuffer(i).setMaxVoltage(scalingConfig.toMicroVolt(i));
            } catch (Exception e) {
                System.out.println(e);
            }
            ecgDoubleDataBufferContainer.getDataBuffer(i).setUnit(scalingConfig.getUnit(i));
        }

        ecgDataPlugin.setDoubleDataBufferContainer(ecgDoubleDataBufferContainer);

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
        plugins.add(ecgDataPlugin);
    }

    public void start() {
        open();
        aPlayer.setDataSource(aFileDataSource.getDataSource(), plugins);
        aPlayer.setVisible(true);
        aPlayer.setMute(true);
        aPlayer.start();
        multichannelEEGApplication.setVisible(true);
        ecgApplication.setVisible(true);
    }

    public static void main(String[] args) {
        PlayBackExampleApplication aPlayBackExampleApplication = new PlayBackExampleApplication();
        aPlayBackExampleApplication.start();
    }
}