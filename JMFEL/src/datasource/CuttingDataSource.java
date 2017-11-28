/*
 * CuttingDataSource.java
 *
 * Created on 20. Dezember 2007, 18:52
 * This code was taken straight from the cut.java example http://java.sun.com/products/java-media/jmf/2.1.1/solutions/
 */

package datasource;

import javax.media.Control;
import javax.media.MediaLocator;
import javax.media.Processor;
import javax.media.Time;
import javax.media.control.TrackControl;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.PushBufferDataSource;
import javax.media.protocol.PushBufferStream;

/**
 *
 * @author Administrator
 */
public class CuttingDataSource extends PushBufferDataSource {
    
    Processor p;
    MediaLocator ml;
    PushBufferDataSource ds;
    CuttingStream streams[];
    
    public CuttingDataSource(Processor p, MediaLocator ml, long start[], long end[]) {
        this.p = p;
        this.ml = ml;
        this.ds = (PushBufferDataSource)p.getDataOutput();
        
        TrackControl tcs[] = p.getTrackControls();
        PushBufferStream pbs[] = ds.getStreams();
        
        streams = new CuttingStream[pbs.length];
        for (int i = 0; i < pbs.length; i++) {
            streams[i] = new CuttingStream(tcs[i], pbs[i], start, end);
        }
    }
    
    public void connect() throws java.io.IOException {
    }
    
    public PushBufferStream [] getStreams() {
        return streams;
    }
    
    public void start() throws java.io.IOException {
        p.start();
        ds.start();
    }
    
    public void stop() throws java.io.IOException {
    }
    
    public Object getControl(String name) {
        // No controls
        return null;
    }
    
    public Object [] getControls() {
        // No controls
        return new Control[0];
    }
    
    public Time getDuration() {
        return ds.getDuration();
    }
    
    public void disconnect() {
    }
    
    public String getContentType() {
        return ContentDescriptor.RAW;
    }
    
    public MediaLocator getLocator() {
        return ml;
    }
    
    public void setLocator(MediaLocator ml) {
        System.err.println("Not interested in a media locator");
    }
}