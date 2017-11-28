/*
 * MediaTranscoder.java
 *
 * Created on 2. April 2007, 14:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package datasource;

import custom_controller.CustomController;
import java.awt.Dimension;
import javax.media.*;
import javax.media.protocol.*;
import javax.media.control.TrackControl;
import javax.media.format.*;
import javax.media.control.QualityControl;

/**
 *
 * @author christ
 */
public class MediaTranscoder {
    
    private Processor processor = null;
    private CustomController controllerListener = null;
    private DataSource inputDataSource = null;
    private DataSource outputDataSource = null;
    private float encodingQuality = 0.5f;
    private String contentDescriptor = ContentDescriptor.RAW_RTP;
    private String transcodeAudioFormatType  = AudioFormat.DVI_RTP;
    private String transcodeVideoFormatType  = VideoFormat.H263_RTP;
    
    public MediaTranscoder() {
    }
    
    public void setTranscodeVideoFormatType(String transcodeVideoFormatType) {
        this.transcodeVideoFormatType = transcodeVideoFormatType;
    }
    
    public String getTranscodeVideoFormatType() {
        return transcodeVideoFormatType;
    }
    
    public void setTranscodeAudioFormatType(String transcodeAudioFormatType) {
        this.transcodeAudioFormatType = transcodeAudioFormatType;
    }
    
    public String getTranscodeAudioFormatType() {
        return transcodeAudioFormatType;
    }
    
    public void setEncodingQuality(float encodingQuality) {
        this.encodingQuality = encodingQuality;
    }
    
    public void setContentDescriptor(String contentDescriptor) {
        this.contentDescriptor = contentDescriptor;
    }
    
    public String getContentDescriptor() {
        return contentDescriptor;
    }
    
    public float getEncodingQuality() {
        return encodingQuality;
    }
    
    public void start(Time aTime) {
        processor.syncStart(aTime);
    }
    
    public void start() {
        processor.start();
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
        controllerListener = new CustomController(processor);
        processor.addControllerListener(controllerListener);
        // Put the Processor into configured state.
        processor.configure();
        if (!controllerListener.waitForState(processor.Configured)) {
            System.err.println("Failed to configure the processor.");
            
        }
        
        processor.setContentDescriptor(new ContentDescriptor(getContentDescriptor()));
        
        TrackControl tc[] = processor.getTrackControls();
        if (tc == null) {
            System.err.println("Failed to obtain track controls from the processor.");
        }
        
        // Search for the track control for the video track.
        
        // Search for the track control for the video track.
        TrackControl audiotrack = null;
        TrackControl videotrack = null;
        AudioFormat inFormat =   new AudioFormat(getTranscodeAudioFormatType());
        boolean audioTranscodingPossible = true;
        
        for (int i = 0; i < tc.length; i++) {
            if (tc[i].getFormat() instanceof AudioFormat) {
                audiotrack = tc[i];
                    audiotrack.setFormat(inFormat);
            }
        }
        
        for (int i = 0; i < tc.length; i++) {
            if (tc[i].isEnabled() && tc[i].getFormat() instanceof VideoFormat) {
                videotrack = tc[i];
     
                Dimension size = ((VideoFormat)videotrack.getFormat()).getSize();
                float frameRate = ((VideoFormat)videotrack.getFormat()).getFrameRate();
                System.out.println(size.getWidth()+" "+size.getHeight());
                int w = (size.width % 8 == 0 ? size.width :
                    (int)(size.width / 8) * 8);
                int h = (size.height % 8 == 0 ? size.height :
                    (int)(size.height / 8) * 8);
                VideoFormat videoFormat = new VideoFormat(getTranscodeVideoFormatType(),
                        new Dimension(w, h),
                        Format.NOT_SPECIFIED,
                        Format.byteArray,
                        frameRate);
                System.out.println(w+" "+h);
                videotrack.setFormat(videoFormat);
                System.err.println("Video transmitted as:");
                System.err.println("  " + videoFormat);
            }
        }
        
        // Prefetch the processor.
        processor.prefetch();
        adjustVideoEncodingQuality(processor,getEncodingQuality());
        if (!controllerListener.waitForState(processor.Prefetched)) {
            System.err.println("Failed to prefetch the processor.");
        }
    }
    
   public void adjustVideoEncodingQuality(Processor p, float val) {
        
        Control cs[] = p.getControls();
        QualityControl qc = null;
        VideoFormat videoFmt = new VideoFormat(getTranscodeVideoFormatType());
        
        for (int i = 0; i < cs.length; i++) {
            
            if (cs[i] instanceof QualityControl &&
                    cs[i] instanceof Owned) {
                Object owner = ((Owned)cs[i]).getOwner();
                
                // Check to see if the owner is a Codec.
                // Then check for the output format.
                if (owner instanceof Codec) {
                    Format fmts[] = ((Codec)owner).getSupportedOutputFormats(null);
                    for (int j = 0; j < fmts.length; j++) {
                        if (fmts[j].matches(videoFmt)) {
                            qc = (QualityControl)cs[i];
                            qc.setQuality(val);
                            System.err.println("- Setting quality to " +
                                    val + " on " + qc);
                            break;
                        }
                    }
                }
                if (qc != null)
                    break;
            }
        }
    }
}