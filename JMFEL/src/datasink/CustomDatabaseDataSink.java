/*
 * CustomDatabaseDataSink.java
 *
 * Created on 17. Juli 2007, 16:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package datasink;

import datasink.database.AbstractDatabaseSourceHandler;
import datasink.delimited.AbstractDelimitedFileDataSourceHandler;
import datasink.delimited.BasicDataSinkListener;
import javax.media.IncompatibleSourceException;
import javax.media.MediaLocator;

/**
 *
 * @author Administrator
 */
public class CustomDatabaseDataSink extends BasicCustomDataSink{
    
    private AbstractDatabaseSourceHandler sourceHandler;
    private String databaseLocation;
    
    /** Creates a new instance of AbtractCustomFileDataSink */
    public CustomDatabaseDataSink() {
    }
    
    public AbstractDatabaseSourceHandler getSourceHandler() {
        return sourceHandler;
    }
    
    public String getDatabaseLocation() {
        return databaseLocation;
    }
    
    public void setDatabaseLocation(String databaseLocation) {
        this.databaseLocation = databaseLocation;
    }
    
    public void setSourceHandler(AbstractDatabaseSourceHandler handler) {
        sourceHandler = (AbstractDatabaseSourceHandler) handler;
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
    
    
    public void handleSourceHandler(){
        sourceHandler = getSourceHandler();
        sourceHandler.setOutputLocator(new MediaLocator(getDatabaseLocation()));
        
        try {
            sourceHandler.setSource(getOutputDataSource());
        } catch (IncompatibleSourceException e) {
            System.err.println("Cannot handle the output DataSource from the processor: " + getOutputDataSource());
        }
        
        sourceHandler.addDataSinkListener(new BasicDataSinkListener());
        sourceHandler.start();
    }
}
