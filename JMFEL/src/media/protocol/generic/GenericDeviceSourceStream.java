package media.protocol.generic;

import javax.media.*;
import javax.media.protocol.*;
import com.sun.media.protocol.*;
import java.io.IOException;
import com.sun.media.util.LoopThread;
import com.sun.media.CircularBuffer;
import javax.media.format.AudioFormat;
import utilities.ByteUtility;

public class GenericDeviceSourceStream extends BasicSourceStream implements PushBufferStream {
    
    protected ContentDescriptor cd = new ContentDescriptor(ContentDescriptor.RAW);
    GenericCustomCaptureDevice capDevice = null;
    protected AudioFormat audioFormat;
    protected boolean started;
    protected Format format;
    protected int maxDataLength = 0;
    protected Thread thread;
    protected BufferTransferHandler transferHandler;
    protected Control [] controls = new Control[0];
    CircularBuffer cb = new CircularBuffer(8);
    PushThread pushThread = null;
    
    public GenericDeviceSourceStream(GenericCustomCaptureDevice aCaptureDevice) {
        init(aCaptureDevice);
    }
    
    /***************************************************************************
     * SourceStream
     ***************************************************************************/
    
    public void init(GenericCustomCaptureDevice aCaptureDevice){
        
        capDevice = aCaptureDevice;
        capDevice.init();
        maxDataLength = capDevice.getMaxDataLength();
        format = capDevice.getFormat();
    }
    
    public ContentDescriptor getContentDescriptor() {
        return cd;
    }
    
    public long getContentLength() {
        return LENGTH_UNKNOWN;
    }
    
    public boolean endOfStream() {
        return false;
    }
    
    /***************************************************************************
     * PushBufferStream
     ***************************************************************************/
    
    public Format getFormat() {
        return format;
    }
    
    public void read(Buffer in) throws IOException {
        Buffer buffer;
        Object data;
        buffer = cb.read();
        // Swap data with the input buffer and my own buffer.
        data = in.getData();
        in.copy(buffer);
        buffer.setData(data);
        
        synchronized (cb) {
            cb.readReport();
            cb.notify();
        }
    }
    
    public void setTransferHandler(BufferTransferHandler transferHandler) {
        synchronized (this) {
            this.transferHandler = transferHandler;
            notifyAll();
        }
    }
    
    void start() {
        
        if (started)
            return;
        
        // Flush the old data.
        synchronized (cb) {
            while (cb.canRead()) {
                cb.read();
                cb.readReport();
            }
            cb.notifyAll();
        }
        if (pushThread==null){
            pushThread= new PushThread();
        }
        pushThread.setSourceStream(this);
        
        pushThread.start();
        //set the prio to MIN_PRIORITY. We dont want to waste CPU cycles
        pushThread.setPriority(LoopThread.MIN_PRIORITY);
        started = true;
    }
    
    
    public void stop() throws IOException {
        if (!started)
            return;
        pushThread.pause();
        started = false;
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
    
    class PushThread extends LoopThread {
        
        private GenericDeviceSourceStream sourceStream;
        private long seqNo = 0;
        private SystemTimeBase systemTimeBase = new SystemTimeBase();
        
        public PushThread() {
            setName("DeviceData PushThread");
        }
        
        void setSourceStream(GenericDeviceSourceStream ss) {
            sourceStream = ss;
        }
        
        protected boolean process() {
            Buffer buffer;
            byte data[];
            int len;
            CircularBuffer cb = sourceStream.cb;
            BufferTransferHandler transferHandler = sourceStream.transferHandler;
            synchronized (cb) {
                while (!cb.canWrite()) {
                    //if we cant write drop the data...
                    try {
                        cb.read();
                        cb.readReport();
                        System.out.println("dropped some data");
                    } catch (Exception e) {}
                }
                buffer = cb.getEmptyBuffer();
            }
            
            if (buffer.getData() instanceof byte[])
                data = (byte[])buffer.getData();
            else
                data = null;
            
            if (data == null || data.length < sourceStream.maxDataLength) {
                data = new byte[sourceStream.maxDataLength];
                buffer.setData(data);
            }
            
            ///////////////////////////////////////////////////////////////
            //Here, the data from the Capturedevice is acquired
            byte[] capturedData = sourceStream.capDevice.getAvailableData();
            int dataLength = capturedData.length;
            System.arraycopy(capturedData,0,data,0,dataLength);
            len = dataLength;
            
            ///////////////////////////////////////////////////////////////
            
            buffer.setOffset(0);
            buffer.setLength(len);
            buffer.setFormat(sourceStream.format);
            buffer.setFlags(buffer.FLAG_SYSTEM_TIME | buffer.FLAG_LIVE_DATA);
            
            buffer.setTimeStamp(systemTimeBase.getNanoseconds());
            
            buffer.setSequenceNumber(seqNo++);
            if (len < 1) {
                buffer.setFlags(buffer.FLAG_DISCARD);
            }
            
            synchronized (cb) {
                cb.writeReport();
                cb.notify();
                
                if (transferHandler != null && len > 0)
                    //if we cant read, try to wait
                    if (!cb.canRead())
                        try {
                            cb.wait();
                        } catch (InterruptedException ie) {
                        }
                if (cb.canRead()){
                    transferHandler.transferData(sourceStream);
                }
            }
            
            try{
                //try to wait a bit more
                this.sleep(10);
            }catch (InterruptedException iex){};
            return true;
        }
    }
}