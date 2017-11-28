/*
 * SimpleDataSink.java
 * 
 * Saving, trancoding, start, stop resume, this class does it all for you.
 * Both, CaptureController and SimpleCaptureController use this class internally.
 *
 * Created on 11. April 2006, 16:44
 *
 */

package datasink;
import custom_controller.CustomController;
import javax.media.*;
import javax.media.protocol.*;
import javax.media.format.*;

/**
 *
 * @author christ
 */

public class SimpleDataSink{
    private DataSource datasource;
    private FileTypeDescriptor outputType = new FileTypeDescriptor(FileTypeDescriptor.QUICKTIME);
    private String fileName = "";
    private AudioFormat audioOutPutFormat = new AudioFormat(AudioFormat.LINEAR);
    private VideoFormat videoOutPutFormat = new VideoFormat(VideoFormat.CINEPAK);
    private Processor processor = null;
    private DataSink datasink = null;
    private CustomController controllerListener = null;
    
    public SimpleDataSink() {
    }
    
    public void setDataSource(DataSource aDataSource) {
        datasource = aDataSource;
    }
    
    public void setAudioOutputFormat(AudioFormat aAudioFormat){
        audioOutPutFormat = aAudioFormat;
    }
    
    public void setVideoOutputFormat(VideoFormat aVideoFormat){
        videoOutPutFormat = aVideoFormat;
    }
    
    public void setFileType(FileTypeDescriptor aFileTypeDescriptor){
        outputType = aFileTypeDescriptor;
    }
    
    public void setFileName(String aFilename){
        fileName = aFilename;
    }
    
    public Time getSyncTime(){
        return processor.getSyncTime();
    }
    
    public void start(Time aTime) {
        
        DataSource outputDS = processor.getDataOutput();
        
        try {
            MediaLocator ml = new MediaLocator("file:"+ fileName + ".mov");
            datasink = Manager.createDataSink(outputDS, ml);
            datasink.open();
            datasink.start();
        } catch (Exception e) {
            System.err.println(e);
        }
        processor.syncStart(aTime);
        System.out.println("Started saving...");
    }
    
    public void start(){
        DataSource outputDS = processor.getDataOutput();
        
        try {
            MediaLocator ml = new MediaLocator("file:"+ fileName);
            datasink = Manager.createDataSink(outputDS, ml);
            datasink.open();
            datasink.start();
        } catch (Exception e) {
            System.err.println(e);
        }
        processor.start();
        System.out.println("Started saving...");
    }
    
    public void stop(){
        
        // Stop the capture and the file writer (DataSink)
        processor.stop();
        processor.close();
        datasink.close();
        processor = null;
        System.out.println("Done saving.");
    }
    
    public void pause(){
        processor.stop();
    }
    
    public void resume(){
        processor.start();
    }
    
    public void init(){
        processor = createRealizedProcessor(datasource, outputType);
        controllerListener = new CustomController(processor);
        processor.addControllerListener(controllerListener);
        // Prefetch the player.
        processor.prefetch();
        if (!controllerListener.waitForState(processor.Prefetched)) {
            System.err.println("Failed to configure the processor.");
        }
    }
    
    private Processor createRealizedProcessor(DataSource aDataSource, FileTypeDescriptor anOutputType){
        Processor aProcessor = null;
        
        if (aDataSource != null) {
            // Set the preferred content type for the Processor's output
            Format formats[] = new Format[((PushBufferDataSource)aDataSource).getStreams().length];
            
            PushBufferStream[] aPushBufferStreamArray = ((PushBufferDataSource)aDataSource).getStreams();
            
            for (int i = 0; i < formats.length; i++) {
                if(aPushBufferStreamArray[i].getFormat() instanceof VideoFormat){
                    formats[i] = videoOutPutFormat;
                }
            }
            
            for (int i = 0; i < formats.length; i++) {
                if(aPushBufferStreamArray[i].getFormat() instanceof AudioFormat){
                    formats[i] = audioOutPutFormat;
                }
            }
            
            ProcessorModel pm = new ProcessorModel(aDataSource, formats, anOutputType);
            try {
                aProcessor = Manager.createRealizedProcessor(pm);
                
            } catch (Exception me) {
                System.err.println("trying to createRealizedProcessor "+ me);
                // Make sure the capture devices are released
                aDataSource.disconnect();
            }
        }
        return aProcessor;
    }
}