package datasink.delimited;

import exceptions.FormatMismatchException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import javax.media.Buffer;
import javax.media.DataSink;
import javax.media.IncompatibleSourceException;
import javax.media.MediaLocator;
import javax.media.datasink.DataSinkErrorEvent;
import javax.media.datasink.DataSinkEvent;
import javax.media.datasink.DataSinkListener;
import javax.media.datasink.EndOfStreamEvent;
import javax.media.format.AudioFormat;
import javax.media.pim.PlugInManager;
import javax.media.protocol.BufferTransferHandler;
import javax.media.protocol.DataSource;
import javax.media.protocol.PullBufferDataSource;
import javax.media.protocol.PullBufferStream;
import javax.media.protocol.PushBufferDataSource;
import javax.media.protocol.PushBufferStream;
import javax.media.protocol.SourceStream;

public abstract class AbstractFileDataSourceHandler implements DataSink, BufferTransferHandler {
    private DataSource source;
    private PullBufferStream pullStrms[] = null;
    private PushBufferStream pushStrms[] = null;
    private long timeStamp;
    private long sequenceNumber;
    
    // Data sink listeners.
    private Vector listeners = new Vector(1);
    
    // Stored all the streams that are not yet finished (i.e. EOM
    // has not been received.
    private SourceStream unfinishedStrms[] = null;
    private byte[] data;
    private int dataLength;
    
    // Loop threads to pull data from a PullBufferDataSource.
    // There is one thread per each PullSourceStream.
    private Loop loops[] = null;
    
    private Buffer readBuffer;

    public void setSequenceNumber(long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
    
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }
    
    /**
     * Sets the media source this <code>MediaHandler</code>
     * should use to obtain content.
     */
    public void setSource(DataSource source) throws IncompatibleSourceException {
        
        // Different types of DataSources need to handled differently.
        if (source instanceof PushBufferDataSource) {
            
            pushStrms = ((PushBufferDataSource)source).getStreams();
            unfinishedStrms = new SourceStream[pushStrms.length];
            
            // Set the transfer handler to receive pushed data from
            // the push DataSource.
            for (int i = 0; i < pushStrms.length; i++) {
                pushStrms[i].setTransferHandler(this);
                unfinishedStrms[i] = pushStrms[i];
            }
            
            
        } else if (source instanceof PullBufferDataSource) {
            
            pullStrms = ((PullBufferDataSource)source).getStreams();
            unfinishedStrms = new SourceStream[pullStrms.length];
            
            // For pull data sources, we'll start a thread per
            // stream to pull data from the source.
            loops = new Loop[pullStrms.length];
            for (int i = 0; i < pullStrms.length; i++) {
                loops[i] = new Loop(this, pullStrms[i]);
                unfinishedStrms[i] = pullStrms[i];
            }
            
        } else {
            
            // This handler only handles push or pull buffer datasource.
            throw new IncompatibleSourceException();
            
        }
        
        this.source = source;
        readBuffer = new Buffer();
    }
    
    
    /**
     * For completeness, DataSink's require this method.
     * But we don't need it.
     */
    public void setOutputLocator(MediaLocator ml) {
    }
    
    
    public MediaLocator getOutputLocator() {
        return null;
    }
    
    
    public String getContentType() {
        return source.getContentType();
    }
    
    
    /**
     * Our DataSink does not need to be opened.
     */
    public void open() {
    }
    
    
    public void start() {
        try {
            source.start();
        } catch (IOException e) {
            System.err.println(e);
        }
        
        // Start the processing loop if we are dealing with a
        // PullBufferDataSource.
        if (loops != null) {
            for (int i = 0; i < loops.length; i++)
                loops[i].restart();
        }
    }
    
    
    public void stop() {
        try {
            source.stop();
        } catch (IOException e) {
            System.err.println("stop():"+e);
        }
        
        // Start the processing loop if we are dealing with a
        // PullBufferDataSource.
        if (loops != null) {
            for (int i = 0; i < loops.length; i++)
                loops[i].pause();
        }
    }
    
    
    public void close() {
        stop();
        if (loops != null) {
            for (int i = 0; i < loops.length; i++)
                loops[i].kill();
        }
    }
    
    
    public void addDataSinkListener(DataSinkListener dsl) {
        if (dsl != null)
            if (!listeners.contains(dsl))
                listeners.addElement(dsl);
    }
    
    
    public void removeDataSinkListener(DataSinkListener dsl) {
        if (dsl != null)
            listeners.removeElement(dsl);
    }
    
    
    protected void sendEvent(DataSinkEvent event) {
        if (!listeners.isEmpty()) {
            synchronized (listeners) {
                Enumeration list = listeners.elements();
                while (list.hasMoreElements()) {
                    DataSinkListener listener =
                            (DataSinkListener)list.nextElement();
                    listener.dataSinkUpdate(event);
                }
            }
        }
    }
    
    
    /**
     * This will get called when there's data pushed from the
     * PushBufferDataSource.
     */
    public void transferData(PushBufferStream stream) {
        
        try {
            stream.read(readBuffer);
        } catch (IOException e) {
            System.err.println(e);
            sendEvent(new DataSinkErrorEvent(this, e.getMessage()));
            return;
        }
        
        try{
            process(readBuffer);
        }catch(FormatMismatchException fme){System.out.println(fme);};
        
        // Check to see if we are done with all the streams.
        if (readBuffer.isEOM() && checkDone(stream)) {
            sendEvent(new EndOfStreamEvent(this));
        }
    }
    
    
    /**
     * This is called from the Loop thread to pull data from
     * the PullBufferStream.
     */
    public boolean readPullData(PullBufferStream stream) {
        try {
            stream.read(readBuffer);
        } catch (IOException e) {
            System.err.println(e);
            return true;
        }
        
        try{
            process(readBuffer);
        }catch(FormatMismatchException fme){System.out.println(fme);};
        
        if (readBuffer.isEOM()) {
            // Check to see if we are done with all the streams.
            if (checkDone(stream)) {
                System.err.println("All done!");
                close();
            }
            return true;
        }
        return false;
    }
    
    
    /**
     * Check to see if all the streams are processed.
     */
    public boolean checkDone(SourceStream strm) {
        boolean done = true;
        
        for (int i = 0; i < unfinishedStrms.length; i++) {
            if (strm == unfinishedStrms[i])
                unfinishedStrms[i] = null;
            else if (unfinishedStrms[i] != null) {
                // There's at least one stream that's not done.
                done = false;
            }
        }
        return done;
    }
    
    private void process(Buffer buffer) throws FormatMismatchException{
        if(buffer.getFormat() instanceof AudioFormat){
            setData((byte[])buffer.getData());
            setDataLength(getData().length);
            setSequenceNumber(buffer.getSequenceNumber());
            setTimeStamp(buffer.getTimeStamp());
        }else throw new FormatMismatchException("Only AudioFormat is supported");
        process();
    }
    
    public abstract void process();
    
    public int getDataLength() {
        return dataLength;
    }
    
    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }
    
    public void setData(byte[] data) {
        this.data = data;
    }
    
    public byte[] getData() {
        return data;
    }
    
    public Object [] getControls() {
        return new Object[0];
    }
    
    public Object getControl(String name) {
        return null;
    }
}

class Loop extends Thread {
    
    AbstractFileDataSourceHandler handler;
    PullBufferStream stream;
    boolean paused = true;
    boolean killed = false;
    
    public Loop(AbstractFileDataSourceHandler handler, PullBufferStream stream) {
        this.handler = handler;
        this.stream = stream;
        start();
    }
    
    public synchronized void restart() {
        paused = false;
        notify();
    }
    
    public synchronized void pause() {
        paused = true;
    }
    
    public synchronized void kill() {
        killed = true;
        notify();
    }
    
    public void run() {
        while (!killed) {
            try {
                while (paused && !killed) {
                    wait();
                }
            } catch (InterruptedException e) {}
            
            if (!killed) {
                boolean done = handler.readPullData(stream);
                if (done)
                    pause();
            }
        }
    }
}