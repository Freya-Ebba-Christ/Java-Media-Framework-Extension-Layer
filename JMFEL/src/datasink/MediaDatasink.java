/*
 * RTPMediaTransmitter.java
 *
 * Created on 4. April 2007, 10:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package datasink;

import custom_controller.DatasinkController;
import javax.media.DataSink;
import javax.media.protocol.DataSource;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Processor;
import javax.media.Time;
import javax.media.TimeBase;
import javax.media.control.TrackControl;
import javax.media.format.AudioFormat;
import javax.media.format.VideoFormat;
import javax.media.protocol.FileTypeDescriptor;
import rtp.MediaDatasinkListener;

/**
 *
 * @author christ
 */

public class MediaDatasink {
    
    private Processor processor = null;
    private DatasinkController datasinkController = null;
    private MediaDatasinkListener mediaDatasinkListener = null;
    private DataSource inputDataSource = null;
    private DataSource outputDataSource = null;
    private DataSink datasink = null;
    private FileTypeDescriptor outputType = new FileTypeDescriptor(FileTypeDescriptor.QUICKTIME);
    private String fileName = "";
    private AudioFormat audioOutPutFormat = new AudioFormat(AudioFormat.LINEAR);
    private VideoFormat videoOutPutFormat = new VideoFormat(VideoFormat.CINEPAK);
    
    public MediaDatasink() {
    }
    
    public void start(Time aTime) {
        processor.syncStart(aTime);
    }
    
    public void start() {
        DataSource outputDS = processor.getDataOutput();
        
        try {
            MediaLocator ml = new MediaLocator(getFileName());
            datasink = Manager.createDataSink(outputDS, ml);
            datasink.addDataSinkListener(mediaDatasinkListener);
            mediaDatasinkListener.setFileDone(false);
            datasink.open();
            datasink.start();
        } catch (Exception e) {
            System.err.println(e);
        }
        processor.start();
        System.out.println("Started saving...");
        
        // Wait for EndOfStream event.
        mediaDatasinkListener.waitForFileDone();
        
        // Cleanup.
        
        try {
            datasink.close();
        } catch (Exception e) {}
        
         processor.removeControllerListener(datasinkController);
         System.out.println("done");
    }
    
    public FileTypeDescriptor getOutputType() {
        return outputType;
    }
    
    public AudioFormat getAudioOutPutFormat() {
        return audioOutPutFormat;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public DataSource getInputDataSource() {
        return inputDataSource;
    }
    
    public DataSource getOutputDataSource() {
        return outputDataSource;
    }
    
    public VideoFormat getVideoOutPutFormat() {
        return videoOutPutFormat;
    }
    
    public void setAudioOutPutFormat(AudioFormat audioOutPutFormat) {
        this.audioOutPutFormat = audioOutPutFormat;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public void setInputDataSource(DataSource inputDataSource) {
        this.inputDataSource = inputDataSource;
    }
    
    public void setOutputDataSource(DataSource outputDataSource) {
        this.outputDataSource = outputDataSource;
    }
    
    public void setOutputType(FileTypeDescriptor outputType) {
        this.outputType = outputType;
    }
    
    public void setVideoOutPutFormat(VideoFormat videoOutPutFormat) {
        this.videoOutPutFormat = videoOutPutFormat;
    }
    
    public void stop() {
        processor.stop();
    }
    
    public void close() {
        processor.close();
    }
    
    public Time getSyncTime(){
        return processor.getSyncTime();
    }
    
    public TimeBase getTimeBase(){
        return processor.getTimeBase();
    }
    
    public void setTimeBase(TimeBase aTimeBase){
        try{
            processor.setTimeBase(aTimeBase);
        }catch(Exception ex){System.out.println(ex);
        }
    }
    
    public MediaDatasink(DataSource aDataSource) {
        inputDataSource = aDataSource;
        createProcessor();
    }
    
    public void setDataSource(DataSource aDataSource){
        inputDataSource = aDataSource;
        createProcessor();
    }
    
    public DataSource getDataSource(){
        try{
            outputDataSource = processor.getDataOutput();
        }catch (Exception ex){System.err.println(ex);
        };
        return outputDataSource;
    }
    
    private void createProcessor(){
        
        try {
            processor = Manager.createProcessor(inputDataSource);
        } catch (Exception e) {
            System.err.println("Failed to create the processor" + e);
        }
        datasinkController = new DatasinkController(processor);
        mediaDatasinkListener = new MediaDatasinkListener();
        processor.addControllerListener(datasinkController);
        // Put the Processor into configured state.
        processor.configure();
        if (!datasinkController.waitForState(processor.Configured)) {
            System.err.println("Failed to configure the processor.");
            
        }
        
        processor.setContentDescriptor(getOutputType());
        TrackControl tc[] = processor.getTrackControls();
        if (tc == null) {
            System.err.println("Failed to obtain track controls from the processor.");
        }
        
        
        for (int i = 0; i < tc.length; i++) {
            if (tc[i].getFormat() instanceof AudioFormat) {
                tc[i].setFormat(audioOutPutFormat);
                break;
            }
        }
        
        for (int i = 0; i < tc.length; i++) {
            if (tc[i].getFormat() instanceof VideoFormat) {
                tc[i].setFormat(videoOutPutFormat);
                break;
            }
        }
        // Prefetch the processor.
        processor.prefetch();
        
        if (!datasinkController.waitForState(processor.Prefetched)) {
            System.err.println("Failed to prefetch the processor.");
        }
    }
}