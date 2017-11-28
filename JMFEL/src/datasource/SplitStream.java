package datasource;
import javax.media.*;
import javax.media.protocol.*;

public class SplitStream implements PushBufferStream, BufferTransferHandler {
    
    PushBufferStream pbs;
    
    BufferTransferHandler bth;
    Format format;
    
    public SplitStream(PushBufferStream pbs) {
        this.pbs = pbs;
        pbs.setTransferHandler(this);
    }
    
    public void read(Buffer buf){
    }
    
    public ContentDescriptor getContentDescriptor() {
        return new ContentDescriptor(ContentDescriptor.RAW);
    }
    
    public boolean endOfStream() {
        return pbs.endOfStream();
    }
    
    public long getContentLength() {
        return LENGTH_UNKNOWN;
    }
    
    public Format getFormat() {
        return pbs.getFormat();
    }
    
    public void setTransferHandler(BufferTransferHandler bth) {
        this.bth = bth;
    }
    
    public Object getControl(String name) {
        return null;
    }
    
    public Object [] getControls() {
        return new Control[0];
    }
    
    public synchronized void transferData(PushBufferStream pbs) {
        if (bth != null)
            bth.transferData(pbs);
    }
}