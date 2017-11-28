package datasink;
import custom_controller.CustomController;
import datasink.delimited.AbstractDelimitedFileDataSourceHandler;
import datasink.delimited.AbstractFileDataSourceHandler;
import datasink.delimited.BasicDataSinkListener;
import javax.media.IncompatibleSourceException;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Processor;
import javax.media.protocol.*;

public abstract class BasicCustomDataSink{
    
    private Processor p;
    private CustomController controllerListener;
    private DataSource inputDataSource = null;
    private DataSource outputDataSource = null;
    
    /**
     * Given a DataSource, create a processor and hook up the output
     * DataSource from the processor to a customed DataSink.
     */
    
    public BasicCustomDataSink(){
    }
    
    public void setDataSource(DataSource inputDataSource) {
        this.inputDataSource = inputDataSource;
        createProcessor();
    }
    
    public DataSource getOutputDataSource() {
        return outputDataSource;
    }
    
    public void setOutputDataSource(DataSource outputDataSource) {
        this.outputDataSource = outputDataSource;
    }
    
    public DataSource getDataSource() {
        return inputDataSource;
    }
    
    public void start(){
        p.start();
    }
    
    public void close(){
    }
    
    public void stop(){
        p.stop();
    }
    
    public void resume(){
        p.start();
    }
    
    private void createProcessor() {
        
        try {
            p = Manager.createProcessor(getDataSource());
        } catch (Exception e) {
            System.err.println("Failed to create a processor from the given DataSource: " + e);
        }
        controllerListener = new CustomController(p);
        p.addControllerListener(controllerListener);
        
        // Put the Processor into configured state.
        p.configure();
        if (!controllerListener.waitForState(p.Configured)) {
            System.err.println("Failed to configure the processor.");
        }
        
        // Get the raw output from the processor.
        p.setContentDescriptor(new ContentDescriptor(ContentDescriptor.RAW));
        
        p.realize();
        if (!controllerListener.waitForState(p.Realized)) {
            System.err.println("Failed to realize the processor.");
        }
        
        // Get the output DataSource from the processor and
        // hook it up to the DataSourceHandler.
        setOutputDataSource(p.getDataOutput());
        
        handleSourceHandler();
        
        // Prefetch the processor.
        p.prefetch();
        if (!controllerListener.waitForState(p.Prefetched)) {
            System.err.println("Failed to prefetch the processor.");
        }
    }
    
    public abstract void handleSourceHandler();
}