package media.protocol.generic;
import javax.media.Time;
import javax.media.protocol.*;
import java.io.IOException;

public abstract class GenericDataSource extends PushBufferDataSource {
    
    protected Object [] controls = new Object[0];
    protected boolean started = false;
    protected String contentType = "raw";
    protected boolean connected = false;
    protected Time duration = DURATION_UNKNOWN;
    protected GenericDeviceSourceStream[] streams = null;
    protected GenericDeviceSourceStream stream = null;
    private GenericCustomCaptureDevice aCaptureDevice;
    
    public GenericDataSource() {
    }

    public void setCaptureDevice(GenericCustomCaptureDevice aCaptureDevice) {
        this.aCaptureDevice = aCaptureDevice;
    }
    
    public String getContentType() {
        if (!connected){
            System.err.println("Error: DataSource not connected");
            return null;
        }
        return contentType;
    }
    
    public void connect() throws IOException {
        if (connected)
            return;
        connected = true;
    }
    
    public void disconnect() {
        try {
            if (started)
                stop();
        } catch (IOException e) {}
        connected = false;
    }
    
    public void start() throws IOException {
        // we need to throw error if connect() has not been called
        if (!connected)
            throw new java.lang.Error("DataSource must be connected before it can be started");
        if (started)
            return;
        started = true;
        stream.start();
    }
    
    public void stop() throws IOException {
        if ((!connected) || (!started))
            return;
        started = false;
        stream.stop();
    }
    
    public Object [] getControls() {
        return controls;
    }
    
    public Object getControl(String controlType) {
        try {
            Class  cls = Class.forName(controlType);
            Object cs[] = getControls();
            for (int i = 0; i < cs.length; i++) {
                if (cls.isInstance(cs[i]))
                    return cs[i];
            }
            return null;
            
        } catch (Exception e) {   // no such controlType or such control
            return null;
        }
    }
    
    public GenericCustomCaptureDevice getCaptureDevice(){
        return aCaptureDevice;
    }
    
    public Time getDuration() {
        return duration;
    }
    
    public PushBufferStream [] getStreams() {
        if (streams == null) {
            streams = new GenericDeviceSourceStream[1];
            stream = streams[0] = new GenericDeviceSourceStream(aCaptureDevice);
        }
        return streams;
    }
}