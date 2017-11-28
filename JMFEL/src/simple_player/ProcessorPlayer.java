package simple_player;

import custom_controller.CustomController;
import datasource.SignalDataSource;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.media.*;
import javax.media.protocol.*;
import javax.media.control.TrackControl;
import javax.media.format.*;
import java.util.Vector;
import javax.media.renderer.VideoRenderer;
import javax.swing.JFrame;

public class ProcessorPlayer extends JFrame implements KeyListener {

    private CustomController controllerListener = null;
    private DataSource inputDataSource = null;
    private Codec[] codecs = null;
    private Processor processor = null;
    private Component jmf_controls;
    private int height = 1;
    private int width = 1;
    private VideoRenderer videoRenderer = null;
    private Renderer audioRenderer = null;
    private Vector<VideoRenderer> videoRenderers = new Vector();
    private Vector<Renderer> audioRenderers = new Vector();
    private boolean controlPanelComponentVisible = true;
    private boolean useAudioCodecs = true;
    private boolean useVideoCodecs = true;
    private boolean started = false;
    private Vector<String> renderersToRemove = new Vector();

    public ProcessorPlayer() {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == e.VK_ESCAPE) {
            close();
            stop();
            System.exit(0);
        }
    }

    public Vector<String> getRenderersToRemove() {
        return renderersToRemove;
    }

    public void addRendererToRemove(String renderer) {
        renderersToRemove.add(renderer);
    }

    public Vector<VideoRenderer> getVideoRenderers() {
        return videoRenderers;
    }

    public void addVideoRenderers(VideoRenderer videoRenderer) {
        videoRenderers.add(videoRenderer);
    }

    public Vector<Renderer> getAudioRenderers() {
        return audioRenderers;
    }

    public void addAudioRenderers(Renderer audioRenderer) {
        audioRenderers.add(audioRenderer);
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void center() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int) dim.getWidth() / 2 - getWidth() / 2, (int) dim.getHeight() / 2 - getHeight() / 2);
    }

    public boolean isUseAudioCodecs() {
        return useAudioCodecs;
    }

    public boolean isUseVideoCodecs() {
        return useVideoCodecs;
    }

    public void setUseVideoCodecs(boolean useVideoCodecs) {
        this.useVideoCodecs = useVideoCodecs;
    }

    public void setUseAudioCodecs(boolean useAudioCodecs) {
        this.useAudioCodecs = useAudioCodecs;
    }

    public void setControlPanelComponentVisible(boolean controlPanelComponentVisible) {
        this.controlPanelComponentVisible = controlPanelComponentVisible;
    }

    public boolean isControlPanelComponentVisible() {
        return controlPanelComponentVisible;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public ProcessorPlayer(DataSource aDataSource) {
        inputDataSource = aDataSource;
        createProcessor();
        initVisualSignalComponent();
    }

    public ProcessorPlayer(DataSource aDataSource, Vector codecChain) {
        inputDataSource = aDataSource;
        codecs = new Codec[codecChain.size()];
        codecs = (Codec[]) (codecChain.toArray(codecs));
        createProcessor();
        initVisualSignalComponent();
    }

    public void setDataSource(DataSource aDataSource, Vector codecChain) {
        inputDataSource = aDataSource;
        codecs = new Codec[codecChain.size()];
        codecs = (Codec[]) (codecChain.toArray(codecs));
        createProcessor();
        initVisualSignalComponent();
    }

    public Renderer getAudioRenderer() {
        return audioRenderer;
    }

    public void setAudioRenderer(Renderer audioRenderer) {
        this.audioRenderer = audioRenderer;
    }

    public VideoRenderer getVideoRenderer() {
        return videoRenderer;
    }

    public void setVideoRenderer(VideoRenderer videoRenderer) {
        this.videoRenderer = videoRenderer;
    }

    public void start() {
        setStarted(true);
        getProcessor().start();
    }

    public void stop() {
        setStarted(false);
        getProcessor().stop();
    }

    public void setMute(boolean aValue) {
        getProcessor().getGainControl().setMute(aValue);
    }

    public void close() {
        setStarted(false);
        getProcessor().close();
    }

    public void start(Time aTime) {
        setStarted(true);
        getProcessor().syncStart(aTime);
    }

    public Time getSyncTime() {
        return getProcessor().getSyncTime();
    }

    public TimeBase getTimeBase() {
        return getProcessor().getTimeBase();
    }

    public void setTimeBase(TimeBase aTimeBase) {
        try {
            getProcessor().setTimeBase(aTimeBase);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void setDataSource(DataSource aDataSource) {
        inputDataSource = aDataSource;
        createProcessor();
        initVisualSignalComponent();
    }

    public Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Processor aProcessor) {
        processor = aProcessor;
    }

    private void createProcessor() {
        try {
            setProcessor(Manager.createProcessor(inputDataSource));
        } catch (Exception e) {
            System.err.println("Failed to create the processor" + e);
        }
        controllerListener = new CustomController(getProcessor());
        getProcessor().addControllerListener(controllerListener);
        // Put the Processor into configured state.
        getProcessor().configure();
        if (!controllerListener.waitForState(getProcessor().Configured)) {
            System.err.println("Failed to configure the processor.");
        }
        //so we can use it as a player
        getProcessor().setContentDescriptor(null);
        TrackControl tc[] = getProcessor().getTrackControls();
        if (tc == null) {
            System.err.println("Failed to obtain track controls from the processor.");
        }
        // Search for the track control for the video track.
        TrackControl audiotrack = null;

        for (int i = 0; i < tc.length; i++) {
            if (tc[i].getFormat() instanceof AudioFormat) {
                audiotrack = tc[i];
                break;
            }
        }

        // Search for the track control for the video track.
        TrackControl videoTrack = null;

        for (int i = 0; i < tc.length; i++) {
            if (tc[i].getFormat() instanceof VideoFormat) {
                videoTrack = tc[i];
                break;
            }
        }

        if (videoTrack == null) {
            System.err.println("The input media does not contain a video track.");
        }

        if (audiotrack == null) {
            System.err.println("The input media does not contain an audio track.");
        }

        try {
            for (int i = 0; i < getRenderersToRemove().size(); i++) {
                PlugInManager.removePlugIn(getRenderersToRemove().get(i), PlugInManager.RENDERER);
            }
        } catch (Exception e) {
            System.err.println(e);
        }

        try {

            if (isUseAudioCodecs()) {
                if (audiotrack != null && codecs != null) {
                    audiotrack.setCodecChain(codecs);
                }
            }

            if (isUseVideoCodecs()) {
                if (videoTrack != null && codecs != null) {
                    videoTrack.setCodecChain(codecs);
                }
            }

            //attach given custom audio renderer
            for (int i = 0; i < tc.length; i++) {
                if (tc[i].getFormat() instanceof AudioFormat) {
                    if (getAudioRenderer() != null && getAudioRenderers().isEmpty()) {
                        tc[i].setRenderer(getAudioRenderer());
                        break;
                    } else if (getAudioRenderer() == null && !getAudioRenderers().isEmpty()) {
                        tc[i].setRenderer(getAudioRenderers().get(i));
                    }

                }
            }


            //attach given custom video renderer
            for (int i = 0; i < tc.length; i++) {
                if (tc[i].getFormat() instanceof VideoFormat) {
                    if (getVideoRenderer() != null && getVideoRenderers().isEmpty()) {
                        tc[i].setRenderer(getVideoRenderer());
                        break;
                    } else if (getVideoRenderer() == null && !getVideoRenderers().isEmpty()) {
                        tc[i].setRenderer(getVideoRenderers().get(i));
                    }
                }
            }

        } catch (UnsupportedPlugInException e) {
            System.err.println("The processor does not support effects.");
        }

        // Prefetch the processor.
        getProcessor().prefetch();

        if (!controllerListener.waitForState(getProcessor().Prefetched)) {
            System.err.println("Failed to prefetch the processor.");
        }
    }

    public void initVisualSignalComponent() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Component cc;

        Component vc;
        if ((vc = getProcessor().getVisualComponent()) != null) {
            add("Center", vc);
        }

        if ((cc = getProcessor().getControlPanelComponent()) != null) {
            if (isControlPanelComponentVisible()) {
                add("South", cc);
            }
        }
        pack();
    }

    public static void main(String[] args) {

        DataSource dsFile = null;
        SignalDataSource aFileDataSource;
        ProcessorPlayer aProcessorPlayer;
        aFileDataSource = new SignalDataSource();
        aFileDataSource.setMediaLocator(new MediaLocator("file://c://mixedSource_out.wav"));
        aFileDataSource.init();
        dsFile = aFileDataSource.getDataSource();
        aProcessorPlayer = new ProcessorPlayer();
        aProcessorPlayer.setDataSource(dsFile);
        aProcessorPlayer.setVisible(true);
        aProcessorPlayer.start();

    /*
    DataSource dsFile  = null;
    SignalDataSource aFileDataSource;
    ProcessorPlayer aProcessorPlayer;
    aFileDataSource = new SignalDataSource();
    aFileDataSource.setMediaLocator(new MediaLocator("file://c://mixedSource_out.wav"));
    aFileDataSource.init();
    dsFile = aFileDataSource.getDataSource();
    Vector codecs = new Vector();
    DCOffsetCorrector aDCOffsetCorrector = new DCOffsetCorrector();
    aDCOffsetCorrector.setDcPeriods(5);
    aDCOffsetCorrector.setNumChannels(2);
    aDCOffsetCorrector.setOffsetUsed(true);
    aDCOffsetCorrector.setEndianess(ByteOrder.LITTLE_ENDIAN);
    aDCOffsetCorrector.makeDCOffset();
    codecs.add(aDCOffsetCorrector);
    aProcessorPlayer = new ProcessorPlayer();
    aProcessorPlayer.setDataSource(dsFile,codecs);
    aProcessorPlayer.setVisible(true);
    aProcessorPlayer.start();
     */

    /*
    DataSource dsFile  = null;
    SignalDataSource aFileDataSource;
    ProcessorPlayer aProcessorPlayer;
    aFileDataSource = new SignalDataSource();
    aFileDataSource.setMediaLocator(new MediaLocator("file://c://source3.wav"));
    aFileDataSource.init();
    dsFile = aFileDataSource.getDataSource();
    aProcessorPlayer = new ProcessorPlayer();
    //aProcessorPlayer.addRendererToRemove("com.sun.media.renderer.audio.DirectAudioRenderer");
    //aProcessorPlayer.addRendererToRemove("com.sun.media.renderer.audio.JavaSoundRenderer");
    //aProcessorPlayer.addRendererToRemove("com.sun.media.renderer.audio.SunAudioRenderer");
    A32ChannelDataAccessor aA32ChannelDataAccessor = new A32ChannelDataAccessor();
    aA32ChannelDataAccessor.setDevider(6000f);
    aA32ChannelDataAccessor.setSize(new Dimension(640,480));
    aA32ChannelDataAccessor.setMaxVoltage(1.0f);
    aA32ChannelDataAccessor.setSampleRate(8000);
    aA32ChannelDataAccessor.setNumChannels(2);
    aA32ChannelDataAccessor.setEndianess(ByteOrder.LITTLE_ENDIAN);
    aA32ChannelDataAccessor.init(8000);
    //aProcessorPlayer.setVideoRenderer(new J3DRenderer());
    aProcessorPlayer.setAudioRenderer(aA32ChannelDataAccessor);
    aProcessorPlayer.setDataSource(dsFile);
    aProcessorPlayer.add(aA32ChannelDataAccessor.getVisualComponent(),BorderLayout.CENTER);
    aProcessorPlayer.pack();
    aProcessorPlayer.setVisible(true);
    aProcessorPlayer.start();
     */
    }
}
