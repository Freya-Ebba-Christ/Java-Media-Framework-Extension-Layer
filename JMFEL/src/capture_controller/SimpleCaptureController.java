package capture_controller;
import custom_controller.CustomController;
import datasink.SimpleDataSink;
import datasource.DataSourceMerger;
import javax.media.*;
import javax.media.protocol.*;
import javax.media.format.*;
import java.util.Vector;

public class SimpleCaptureController{
    
    private DataSink datasink = null;
    private DataSource clonedDataSource = null;
    private int width  = 320;
    private int height = 240;
    private AudioFormat audioOutPutFormat = new AudioFormat(AudioFormat.LINEAR);
    private VideoFormat videoOutPutFormat = new VideoFormat(VideoFormat.RGB);
    private FileTypeDescriptor outputType = new FileTypeDescriptor(FileTypeDescriptor.QUICKTIME);
    private String fileName = "";
    private SimpleDataSink aSimpleDataSink = null;
    private CustomController controllerListener = null;
    private DataSource mergedDataSource;
    
    public SimpleCaptureController() {
    }
    
    public static void main(String[] args) {
    }

    public void startCapture() {
        aSimpleDataSink.start();
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
        aSimpleDataSink = new SimpleDataSink();

        aSimpleDataSink.setFileType(outputType);
        aSimpleDataSink.setVideoOutputFormat(videoOutPutFormat);
        aSimpleDataSink.setDataSource(mergedDataSource);
        aSimpleDataSink.setFileName(fileName);
        aSimpleDataSink.init();
    }
}