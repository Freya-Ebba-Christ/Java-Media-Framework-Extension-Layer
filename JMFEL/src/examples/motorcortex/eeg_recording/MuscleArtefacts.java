/*
 * This application reads a quicktime file containing EEG signals and a video stream.
 * The video is displayed by a ProcessorPlayer and the EEG signal is displayed by the application_container.MultichannelEEGApplication
 */

package examples.motorcortex.eeg_recording;

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
import application_container.MultichannelEEGApplication;
import application_container.SingleEEGChannelContainer;
import custom_renderer.NullAudioDevice;
import custom_renderer.NullAudioRenderer;
import datasource.SignalDataSource;
import java.awt.Color;

public class MuscleArtefacts {

    private String file_separator = System.getProperty("file.separator");
    private DataSource dsEEG = null;
    private SignalDataSource aFileDataSource;
    private ProcessorPlayer eegProcessorPlayer;
    private Vector plugins = new Vector();
    private Configuration eegConfig;
    private ScalingConfiguration scalingConfig;
    private EEGDataPlugin eegDataPlugin;
    private DoubleDataBufferContainer eegDoubleDataBufferContainer;
    private MultichannelEEGApplication multichannelEEGApplication;
    private SingleEEGChannelContainer singleEEGChannelContainer;

    /** Creates a new instance of PlayBackExampleApplication */
    public MuscleArtefacts() {
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

    void enableSyncMux() {
        Vector muxes = javax.media.PlugInManager.getPlugInList(null, null, javax.media.PlugInManager.MULTIPLEXER);
        for (int i = 0; i < muxes.size(); i++) {
            String cname = (String) muxes.elementAt(i);
            if (cname.equals("com.sun.media.multiplexer.RawBufferMux")) {
                muxes.removeElementAt(i);
                break;
            }
        }
        javax.media.PlugInManager.setPlugInList(muxes, javax.media.PlugInManager.MULTIPLEXER);
    }

    public void open() {
        if (eegProcessorPlayer != null) {
            eegProcessorPlayer.dispose();
        }
        eegProcessorPlayer = null;
        eegProcessorPlayer = new ProcessorPlayer();
        aFileDataSource = new SignalDataSource();
        aFileDataSource.setMediaLocator(new MediaLocator("file://c:/Dokumente und Einstellungen/Administrator/Beaker/src/examples/recorded data/muscle_artefacts.qt"));
        aFileDataSource.init();

        dsEEG = aFileDataSource.getDataSource();

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
    }

    public void start() {
        open();
        NullAudioRenderer aNullAudioRenderer = new NullAudioRenderer();
        aNullAudioRenderer.setAudioDevice(new NullAudioDevice());
        eegProcessorPlayer.setAudioRenderer(aNullAudioRenderer);
        eegProcessorPlayer.setUseAudioCodecs(true);
        eegProcessorPlayer.setUseVideoCodecs(false);
        eegProcessorPlayer.setDataSource(dsEEG, plugins);

        eegProcessorPlayer.setVisible(true);
        eegProcessorPlayer.start();
        multichannelEEGApplication.setVisible(true);
    }

    public static void main(String[] args) {
        MuscleArtefacts aMuscleArtefacts = new MuscleArtefacts();
        aMuscleArtefacts.start();
    }
}