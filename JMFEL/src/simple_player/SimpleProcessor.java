package simple_player;
import custom_controller.CustomController;
import javax.media.*;
import javax.media.protocol.*;
import javax.media.control.TrackControl;
import javax.media.format.*;
import java.util.Vector;

public class SimpleProcessor{
    
    private Processor processor = null;
    private CustomController controllerListener = null;
    private DataSource inputDataSource = null;
    private DataSource outputDataSource = null;
    private Codec[] codecs = null;
    private boolean useAudioCodecs = true;
    private boolean useVideoCodecs = true;
    
    public SimpleProcessor() {
    }
    
    public void start(Time aTime) {
        processor.syncStart(aTime);
    }
    
    public void setUseVideoCodecs(boolean useVideoCodecs) {
        this.useVideoCodecs = useVideoCodecs;
    }
    
    public void setUseAudioCodecs(boolean useAudioCodecs) {
        this.useAudioCodecs = useAudioCodecs;
    }
    
    public boolean isUseVideoCodecs() {
        return useVideoCodecs;
    }
    
    public boolean isUseAudioCodecs() {
        return useAudioCodecs;
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
    
    public SimpleProcessor(DataSource aDataSource, Vector codecChain) {
        inputDataSource = aDataSource;
        codecs = new Codec[codecChain.size()];
        codecs = (Codec[])(codecChain.toArray(codecs));
        createProcessor();
    }
    
    public void setDataSource(DataSource aDataSource, Vector codecChain){
        inputDataSource = aDataSource;
        codecs = new Codec[codecChain.size()];
        codecs = (Codec[])(codecChain.toArray(codecs));
        createProcessor();
    }
    
    public DataSource getDataSource(){
        try{
            outputDataSource = processor.getDataOutput();
        }catch (Exception ex){System.err.println(ex);
        };
        return outputDataSource;
    }

    public Processor getProcessor() {
        return processor;
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
        
        processor.setContentDescriptor(new ContentDescriptor(ContentDescriptor.RAW));
        TrackControl tc[] = processor.getTrackControls();
        if (tc == null) {
            System.err.println("Failed to obtain track controls from the processor.");
        }
        // Search for the track control for the video track.
        TrackControl audiotrack = null;
        TrackControl videotrack = null;
        for (int i = 0; i < tc.length; i++) {
            if (tc[i].getFormat() instanceof AudioFormat) {
                audiotrack = tc[i];
                break;
            }
        }
        
        for (int i = 0; i < tc.length; i++) {
            if (tc[i].getFormat() instanceof VideoFormat) {
                videotrack = tc[i];
                break;
            }
        }
        
        
        
        try {
            
            if(isUseAudioCodecs()){
                if (audiotrack != null&&codecs.length>0) {
                    audiotrack.setCodecChain(codecs);
                    System.out.println("audio codec added");
                }
            }
            
            if(isUseVideoCodecs()){
                if (videotrack != null&&codecs.length>0) {
                    videotrack.setCodecChain(codecs);
                    System.out.println("video codec added");
                }
            }
            
        } catch (UnsupportedPlugInException e) {
            System.err.println("The processor does not support effects.");
        }
        
        // Prefetch the processor.
        processor.prefetch();
        
        if (!controllerListener.waitForState(processor.Prefetched)) {
            System.err.println("Failed to prefetch the processor.");
        }
    }
}