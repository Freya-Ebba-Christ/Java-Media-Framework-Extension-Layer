/*
 * AbtractCustomFileDataSink.java
 *
 * Created on 17. Juli 2007, 16:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package datasink;

import datasink.delimited.AbstractDelimitedFileDataSourceHandler;
import datasink.delimited.BasicDataSinkListener;
import javax.media.IncompatibleSourceException;
import javax.media.MediaLocator;

/**
 *
 * @author Administrator
 */
public class CustomFileDataSink extends BasicCustomDataSink{
    private String filename;
    private AbstractDelimitedFileDataSourceHandler sourceHandler;
    
    
    /** Creates a new instance of AbtractCustomFileDataSink */
    public CustomFileDataSink() {
    }
    
    public AbstractDelimitedFileDataSourceHandler getSourceHandler() {
        return sourceHandler;
    }
    
    public void setSourceHandler(AbstractDelimitedFileDataSourceHandler handler) {
        sourceHandler = (AbstractDelimitedFileDataSourceHandler) handler;
    }
    
    public void close(){
        getSourceHandler().close();
    }
    
    public void open(){
        getSourceHandler().open();
    }
    
    public void start() {
        super.start();
        sourceHandler.start();
        sourceHandler.open();
    }
    
    public void stop() {
        super.stop();
        sourceHandler.stop();
        sourceHandler.close();
    }
    
    public String getFilename() {
        return filename;
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public void handleSourceHandler(){
        sourceHandler = getSourceHandler();
        sourceHandler.setOutputLocator(new MediaLocator(getFilename()));
        
        try {
            sourceHandler.setSource(getOutputDataSource());
        } catch (IncompatibleSourceException e) {
            System.err.println("Cannot handle the output DataSource from the processor: " + getOutputDataSource());
        }
        
        sourceHandler.addDataSinkListener(new BasicDataSinkListener());
        sourceHandler.start();
    }
}
