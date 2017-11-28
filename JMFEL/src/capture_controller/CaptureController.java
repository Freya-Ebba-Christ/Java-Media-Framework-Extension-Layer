package capture_controller;
import custom_controller.CustomController;
import datasink.SimpleDataSink;
import datasource.CloneableDataSource;
import datasource.DataSourceMerger;
import javax.media.*;
import javax.media.protocol.*;
import javax.media.format.*;
import java.util.Vector;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

public class CaptureController extends Frame {
    
    private DataSink datasink = null;
    private Player previewPlayer = null;
    private DataSource clonedDataSource = null;
    private AudioFormat audioOutPutFormat = new AudioFormat(AudioFormat.LINEAR);
    private VideoFormat videoOutPutFormat = new VideoFormat(VideoFormat.CINEPAK);
    private FileTypeDescriptor outputType = new FileTypeDescriptor(FileTypeDescriptor.QUICKTIME);
    private String fileName = "";
    private SimpleDataSink aSimpleDataSink = null;
    private CustomController controllerListener = null;
    private DataSource mergedDataSource;
    
    public CaptureController() {
    }
    
    public Time getSyncTime(){
        return previewPlayer.getSyncTime();
    }
    
    public void startCapture() {
        
        aSimpleDataSink.start();
    }

    public FileTypeDescriptor getOutputType() {
        return outputType;
    }

    public void setOutputType(FileTypeDescriptor outputType) {
        this.outputType = outputType;
    }
    
    public void startSynchCapture(Time aTime) {
        aSimpleDataSink.start(aTime);
    }
    
    public void pauseCapture() {
        aSimpleDataSink.pause();
    }
    
    public void resumeCapture() {
        aSimpleDataSink.resume();
    }
    
    public void stopCapture() {
        aSimpleDataSink.stop();
    }
    
    public void setMute(boolean aValue){
        previewPlayer.getGainControl().setMute(aValue);
    }
    
    public TimeBase getTimeBase(){
        return previewPlayer.getTimeBase();
    }
    
    public void setTimeBase(TimeBase aTimeBase){
        try {
            previewPlayer.setTimeBase(aTimeBase);
        }catch (IncompatibleTimeBaseException ex){System.out.println(ex);
        }
    }
    
    public String getFileName(){
        return fileName;
    }
    
    public void setFileName(String aVal){
        fileName = aVal;
    }
    
    public void setAudioOutPutFormat(AudioFormat anAudioFormat){
        audioOutPutFormat = anAudioFormat;
    }
    
    public void setVideoOutPutFormat(VideoFormat aVideoFormat){
        videoOutPutFormat = aVideoFormat;
    }

    public void configure(Vector dataSourceVector){
        
        mergedDataSource = DataSourceMerger.mergeDataSources(dataSourceVector);
        CloneableDataSource aCloneableDataSource = new CloneableDataSource();
        aCloneableDataSource.setDataSource(mergedDataSource);
        clonedDataSource = aCloneableDataSource.getClone();
        aSimpleDataSink = new SimpleDataSink();
        
        try {
            previewPlayer = Manager.createPlayer(aCloneableDataSource.getCloneableDataSource());
        } catch (Exception e) {
            System.err.println("Failed to create a player from the given DataSource: " + e);
        }
        controllerListener = new CustomController(previewPlayer);
        previewPlayer.addControllerListener(controllerListener);
        aSimpleDataSink.setFileType(outputType);
        aSimpleDataSink.setVideoOutputFormat(videoOutPutFormat);
        aSimpleDataSink.setDataSource(clonedDataSource);
        aSimpleDataSink.setFileName(fileName);
        aSimpleDataSink.init();
        
        // Prefetch the player.
        previewPlayer.prefetch();
        if (!controllerListener.waitForState(previewPlayer.Prefetched)) {
            System.err.println("Failed to configure the processor.");
        }
        
        setLayout(new BorderLayout());
        
        Component cc;
        
        Component vc;
        if ((vc = previewPlayer.getVisualComponent()) != null) {
            add("Center", vc);
        }
        
        if ((cc = previewPlayer.getControlPanelComponent()) != null) {
            add("South", cc);
        }
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                previewPlayer.close();
                System.exit(0);
            }
        });
        pack();
        // Start the preview player.
        previewPlayer.start();
    }
}