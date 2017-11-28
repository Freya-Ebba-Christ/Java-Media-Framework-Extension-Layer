package datasource;
import java.io.IOException;
import javax.media.*;
import javax.media.protocol.*;

public class SplitDataSource extends PushBufferDataSource {
    
    Processor processor;
    PushBufferDataSource ds;
    PushBufferStream pbs[];
    SplitStream streams[];
    int idx;
    boolean done = false;
    protected boolean connected = false;
    protected boolean started = false;
    protected String contentType = "raw";
    
    public SplitDataSource(Processor p, int stream_idx) {
        processor = p;
        ds = (PushBufferDataSource)p.getDataOutput();
        idx = stream_idx;
        pbs = ds.getStreams();
        streams = new SplitStream[1];
        streams[0] = new SplitStream(pbs[idx]);
        connected = true;
    }
    
    public void connect() throws java.io.IOException {
        if (connected)
            return;
        connected = true;
    }
    
    public PushBufferStream [] getStreams() {
        return streams;
    }
    
    public Format getStreamFormat() {
        return pbs[idx].getFormat();
    }
    
    public void start() throws java.io.IOException {
        processor.start();
        ds.start();
    }
    
    public void stop() throws java.io.IOException {
        ds.stop();
        processor.stop();
    }
    
    public Object getControl(String name) {
        return null;
    }
    
    public Object [] getControls() {
        return new Control[0];
    }
    
    public Time getDuration() {
        return ds.getDuration();
    }
    
    public void disconnect() {
        try {
            if (started)
                stop();
        } catch (IOException e) {}
        connected = false;
    }
    
    public String getContentType() {
        if (!connected){
            System.err.println("Error: DataSource not connected");
            return null;
        }
        return contentType;
    }
    
    public MediaLocator getLocator() {
        return ds.getLocator();
    }
    
    public void setLocator(MediaLocator ml) {
        System.err.println("Not interested in a media locator");
    }
}