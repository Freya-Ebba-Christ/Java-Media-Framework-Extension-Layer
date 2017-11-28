/*
 * This game extends examples.application_container.game.pong.PongGame and stores EEG signals and keyboard strokes to disk.
 */

package examples.motorcortex.keyboard_and_eeg;

import capture_controller.CaptureApplication;
import capture_controller.SimpleCaptureController;
import capture_controller.gui.CaptureStateApplication;
import datasink.SimpleDataSink;
import custom_renderer.NullAudioRenderer;
import datasource.CloneableDataSource;
import datasource.DataSourceMerger;
import datasource.SignalDataSource;
import examples.application_container.game.pong.PongGame;
import java.awt.Cursor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Properties;
import java.util.Vector;
import javax.media.Format;
import javax.media.MediaLocator;
import javax.media.control.TrackControl;
import javax.media.format.AudioFormat;
import javax.media.format.VideoFormat;
import javax.media.protocol.DataSource;
import javax.media.protocol.FileTypeDescriptor;
import javax.swing.JFrame;
import simple_player.ProcessorPlayer;
import utilities.PropertiesReader;

/**
 *
 * @author Administrator
 */

public class BrainPong extends PongGame implements CaptureApplication{
    private CaptureStateApplication aCaptureStateApplication;
    private DataSource eegDS  = null;
    private DataSource pongDS  = null;
    private SignalDataSource eegSignalDataSource;
    private SignalDataSource pongSignalDataSource;
    private ProcessorPlayer eegProcessorPlayer;
    private ProcessorPlayer pongProcessorPlayer;
    private PropertiesReader propertiesReader = new PropertiesReader();
    private String file_separator = System.getProperty("file.separator");
    private SimpleCaptureController simpleCaptureController;
    private File path;
    private SimpleDataSink aSimpleDataSink;
    private CloneableDataSource cloneableDataSource;
    private CloneableDataSource cloneablePongDataSource;
    private DataSource dsClone = null;
    private DataSource dsPongClone = null;
    
    public BrainPong() {
    }
    
    public void open(File file) {
        if(eegProcessorPlayer!=null){
            eegProcessorPlayer.dispose();
        }
        path = file;
    }
    
    public void pause() {
        if(eegProcessorPlayer!=null){
            eegProcessorPlayer.stop();
            pongProcessorPlayer.stop();
        }
        
        if(aSimpleDataSink != null){
            aSimpleDataSink.pause();
        }
    }
    
    public void start(){
        
        if(eegProcessorPlayer==null){
            aCaptureStateApplication.setCursor(new Cursor(Cursor.WAIT_CURSOR));

            eegSignalDataSource = new SignalDataSource();
            eegSignalDataSource.setMediaLocator(new MediaLocator("testdevice://"));
            
            eegSignalDataSource.init();
            eegDS = eegSignalDataSource.getDataSource();
            cloneableDataSource = new CloneableDataSource();
            cloneableDataSource.setDataSource(eegDS);
            dsClone = cloneableDataSource.getClone();
            
            eegProcessorPlayer = new ProcessorPlayer();
            pongProcessorPlayer = new ProcessorPlayer();
            
            eegProcessorPlayer.setAudioRenderer(new NullAudioRenderer());
            pongProcessorPlayer.setAudioRenderer(new NullAudioRenderer());
            
            eegProcessorPlayer.setDataSource(dsClone);
            eegProcessorPlayer.setUseVideoCodecs(false);
            eegProcessorPlayer.setUseAudioCodecs(true);
            
            pongProcessorPlayer.setDataSource(dsPongClone);
            pongProcessorPlayer.setUseVideoCodecs(false);
            pongProcessorPlayer.setUseAudioCodecs(true);
        }
        
        if(eegProcessorPlayer!=null){
            eegProcessorPlayer.start();
            eegProcessorPlayer.setTitle("eegProcessorPlayer");
            pongProcessorPlayer.setTitle("pongProcessorPlayer");
            eegProcessorPlayer.start();
            pongProcessorPlayer.start();
            
            
            TrackControl tc[] = eegProcessorPlayer.getProcessor().getTrackControls();
            Format[] formatArray = new Format[tc.length];
            for (int i = 0; i < tc.length; i++) {
                formatArray[i]=(tc[i].getFormat());
            }
            
            aCaptureStateApplication.getCaptureStatePainter().getPanel().setFormat(formatArray);
            aCaptureStateApplication.getCaptureStatePainter().getPanel().setProcessor(pongProcessorPlayer.getProcessor());
            aCaptureStateApplication.getCaptureStatePainter().getPanel().setPlayer(null);
            
            Vector datasources = new Vector();
            datasources.add(cloneableDataSource.getCloneableDataSource());
            datasources.add(cloneablePongDataSource.getCloneableDataSource());
            
            if(aSimpleDataSink == null){
                aSimpleDataSink = new SimpleDataSink();
                aSimpleDataSink.setFileType(new FileTypeDescriptor(FileTypeDescriptor.QUICKTIME));
                //no video here... just take some format...
                aSimpleDataSink.setVideoOutputFormat(new VideoFormat(VideoFormat.CINEPAK));
                aSimpleDataSink.setAudioOutputFormat(new AudioFormat(AudioFormat.LINEAR));
                aSimpleDataSink.setDataSource(DataSourceMerger.mergeDataSources(datasources));
                aSimpleDataSink.setFileName(path.getAbsolutePath());
                aSimpleDataSink.init();
                aSimpleDataSink.start();
                
            }else if(aSimpleDataSink != null){
                aSimpleDataSink.resume();
            }
            
            pongProcessorPlayer.setVisible(true);
            eegProcessorPlayer.setVisible(true);
            setVisible(true);
            aCaptureStateApplication.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    public void stop() {
        aCaptureStateApplication.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        
        if(aSimpleDataSink != null){
            aSimpleDataSink.stop();
            aSimpleDataSink=null;
        }
        
        if(eegProcessorPlayer!=null){
            try{
                ((media.protocol.usbAmpA.CustomCaptureDevice)((media.protocol.usbAmpA.DataSource)eegSignalDataSource.getDataSource()).getCaptureDevice()).stop();
                ((media.protocol.usbAmpA.CustomCaptureDevice)((media.protocol.usbAmpA.DataSource)eegSignalDataSource.getDataSource()).getCaptureDevice()).close();
                eegProcessorPlayer.stop();
                pongProcessorPlayer.stop();
                eegSignalDataSource.getDataSource().stop();
                eegSignalDataSource.getDataSource().disconnect();
                eegProcessorPlayer.dispose();
                pongProcessorPlayer.dispose();
            }catch(Exception ex){System.out.println(ex);};
            eegProcessorPlayer=null;
            eegSignalDataSource=null;
            pongProcessorPlayer = null;
        }
        setVisible(false);
        aCaptureStateApplication.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    
    public void init(){
        aCaptureStateApplication = new CaptureStateApplication();
        aCaptureStateApplication.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aCaptureStateApplication.setTitle("Simple Biosignal Data Acquisition Application (alpha build version for Denmark)");
        aCaptureStateApplication.setBackGround("background_capture_monitor.jpg");
        aCaptureStateApplication.setCaptureApplication(this);
        Properties props = propertiesReader.readProperties(System.getProperty("user.dir")+file_separator+"examples"+file_separator+"motorcortex"+file_separator+"eeg_recording"+file_separator+"tooltips.ini");
        aCaptureStateApplication.setMode(CaptureStateApplication.MODE_SAVE);
        aCaptureStateApplication.setNewButtonToolTip(props.getProperty("LOAD_SAVE_BUTTON"));
        aCaptureStateApplication.setPlayButtonToolTip(props.getProperty("PLAY_BUTTON"));
        aCaptureStateApplication.setStopButtonToolTip(props.getProperty("STOP_BUTTON"));
        aCaptureStateApplication.setPauseButtonToolTip(props.getProperty("PAUSE_BUTTON"));
        
        aCaptureStateApplication.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                if(eegSignalDataSource!=null){
                    
                    if(aSimpleDataSink != null){
                        aSimpleDataSink.stop();
                        aSimpleDataSink=null;
                    }
                    
                    ((media.protocol.usbAmpA.CustomCaptureDevice)((media.protocol.usbAmpA.DataSource)eegSignalDataSource.getDataSource()).getCaptureDevice()).stop();
                    ((media.protocol.usbAmpA.CustomCaptureDevice)((media.protocol.usbAmpA.DataSource)eegSignalDataSource.getDataSource()).getCaptureDevice()).close();
                }
                System.exit(0);
            }
        });
        
        pongSignalDataSource = new SignalDataSource();
        pongSignalDataSource.setMediaLocator(new MediaLocator("pong://"));
        pongSignalDataSource.init();
        pongDS = pongSignalDataSource.getDataSource();
        cloneablePongDataSource = new CloneableDataSource();
        cloneablePongDataSource.setDataSource(pongDS);
        dsPongClone = cloneablePongDataSource.getClone();
        
        super.init();
        aCaptureStateApplication.init();
        aCaptureStateApplication.setVisible(true);
    }
    
    public void initGameInputDevice(){
        //set the reference to the pong game in media.protocol.pong.CustomCaptureDevice
        ((media.protocol.pong.CustomCaptureDevice)((media.protocol.pong.DataSource)pongDS).getCaptureDevice()).setSimplePongGame(this);
    }
    
    public static void main(String[] args) {
        BrainPong aBrainPong = new BrainPong();
        aBrainPong.init();
    }
}