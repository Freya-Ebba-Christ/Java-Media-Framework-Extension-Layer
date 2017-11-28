/*
 * MediaPlayerApplication.java
 * This example uses the reusable gui capture_controller.gui.CaptureStateApplication to implement a simple media player  
 */


package examples.mediaplayer;
import capture_controller.CaptureApplication;
import capture_controller.gui.CaptureStateApplication;
import datasource.SignalDataSource;
import java.awt.Cursor;
import java.io.File;
import java.util.Properties;
import javax.media.Format;
import javax.media.MediaLocator;
import javax.media.control.TrackControl;
import javax.media.protocol.DataSource;
import simple_player.ProcessorPlayer;
import utilities.PropertiesReader;

/**
 *
 * @author Urkman_2
 */

public class MediaPlayerApplication implements CaptureApplication{
    private CaptureStateApplication aCaptureStateApplication;
    private DataSource dsFile  = null;
    private SignalDataSource aFileDataSource;
    private ProcessorPlayer aProcessorPlayer;
    private PropertiesReader propertiesReader = new PropertiesReader();
    private String file_separator = System.getProperty("file.separator");
    
    public MediaPlayerApplication() {
    }
    
    public void open(File file) {
        if(aProcessorPlayer!=null){
            aProcessorPlayer.dispose();
        }
        aProcessorPlayer = null;
        aFileDataSource = new SignalDataSource();
        aFileDataSource.setMediaLocator(new MediaLocator("file://"+file.getPath()));
    }
    
    public void pause() {
        if(aProcessorPlayer!=null){
            aProcessorPlayer.stop();
        }
    }
    
    public void start() {
        
        if(aProcessorPlayer==null&&aFileDataSource!=null){
            aCaptureStateApplication.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            aFileDataSource.init();
            dsFile = aFileDataSource.getDataSource();
            aProcessorPlayer = new ProcessorPlayer();
            aProcessorPlayer.setDataSource(dsFile);
            aProcessorPlayer.setVisible(true);
        }
        
        if(aProcessorPlayer!=null){
            aProcessorPlayer.start();
            aCaptureStateApplication.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            TrackControl tc[] = aProcessorPlayer.getProcessor().getTrackControls();
            Format[] formatArray = new Format[tc.length];
            for (int i = 0; i < tc.length; i++) {
                formatArray[i]=(tc[i].getFormat());
            }
            aCaptureStateApplication.getCaptureStatePainter().getPanel().setFormat(formatArray);
            aCaptureStateApplication.getCaptureStatePainter().getPanel().setProcessor(aProcessorPlayer.getProcessor());
        }
    }
    
    public void stop() {
        if(aProcessorPlayer!=null){
            aProcessorPlayer.stop();
        }
    }
    
    public void init(){
        aCaptureStateApplication = new CaptureStateApplication();
        aCaptureStateApplication.setBackGround("background_capture_monitor.jpg");
        aCaptureStateApplication.setCaptureApplication(this);
        Properties props = propertiesReader.readProperties(System.getProperty("user.dir")+file_separator+"examples"+file_separator+"mediaplayer"+file_separator+"tooltips.ini");
        aCaptureStateApplication.setMode(CaptureStateApplication.MODE_OPEN);
        aCaptureStateApplication.setNewButtonToolTip(props.getProperty("LOAD_SAVE_BUTTON"));
        aCaptureStateApplication.setPlayButtonToolTip(props.getProperty("PLAY_BUTTON"));
        aCaptureStateApplication.setStopButtonToolTip(props.getProperty("STOP_BUTTON"));
        aCaptureStateApplication.setPauseButtonToolTip(props.getProperty("PAUSE_BUTTON"));
        aCaptureStateApplication.init();
        aCaptureStateApplication.setVisible(true);
    }
    
    public static void main(String[] args) {
        MediaPlayerApplication aMediaPlayerApplication = new MediaPlayerApplication();
        aMediaPlayerApplication.init();
    }
}