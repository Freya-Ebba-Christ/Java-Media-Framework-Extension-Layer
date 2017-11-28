/*
 * BasicAudioRenderer.java
 *
 * Created on 9. Mai 2007, 19:24
 *
 * This is very simple AudioRenderer. In fact it is just a stripped down version of one of the JavaSound renderer (c) by Sun Microsystems, Inc.
 * Please refer to the JMF source if you want to know more.
 */

package custom_renderer;

import com.sun.media.ExclusiveUse;
import com.sun.media.controls.GainControlAdapter;
import com.sun.media.renderer.audio.device.AudioOutput;
import java.nio.ByteOrder;
import javax.media.Buffer;
import javax.media.Control;
import javax.media.Format;
import javax.media.ResourceUnavailableException;
import javax.media.format.AudioFormat;
import utilities.ByteUtility;
import utilities.DoubleDataBufferContainer;

public abstract class BasicAudioRenderer extends AudioRenderer implements ExclusiveUse {
    
    private String name = "BasicAudioRenderer";
    private Format linearOutputFormat;
    private Format linearFormat;
    private int inLength = 0;
    private byte[] inData = null;
    private int transferBufferSize;
    private int numChannels = 0;
    private ByteUtility byteUtility = new ByteUtility();
    private byte[] byteBuffer = new byte[2];
    private DoubleDataBufferContainer doubleDataBufferContainer;
    private ByteOrder endianess = ByteOrder.LITTLE_ENDIAN;
    private boolean enableAutoEndianess = false;
    private AudioOutput audioDevice = new RealNullAudioDevice();  
    
    
    public BasicAudioRenderer() {
        super();
        
        linearFormat = new javax.media.format.AudioFormat(
                AudioFormat.LINEAR);
        supportedFormats = new javax.media.Format[1];
        supportedFormats[0] = linearFormat;
        gainControl = new GCA(this);
    }

    public AudioOutput getAudioDevice() {
        return audioDevice;
    }

    public void setAudioDevice(AudioOutput audioDevice) {
        this.audioDevice = audioDevice;
    }
    
    public String getName() {
        return name;
    }
    
    public byte[] getInData() {
        return inData;
    }
    
    public int getInLength() {
        return inLength;
    }
    
    public int getNumChannels() {
        return numChannels;
    }
    
    public ByteUtility getByteUtility() {
        return byteUtility;
    }
    
    public void setNumChannels(int numChannels) {
        this.numChannels = numChannels;
    }
    
    public void setTransferBufferSize(int transferBufferSize) {
        this.transferBufferSize = transferBufferSize;
    }
    
    public int getTransferBufferSize() {
        return transferBufferSize;
    }
    
    public void setName(String val) {
        name = val;
    }
    
    public void setValue(int channel, int bufferPosition, short value){
        
        int xpos = channel;
        int ypos = bufferPosition;
        int channels = getNumChannels();
        int bufferSize = getTransferBufferSize();
        
        if (xpos>=channels) {
            xpos = channels-1;
        }
        if (ypos>=bufferSize) {
            ypos = bufferSize-1;
        }
        int address = 2*(ypos*channels+xpos);
        byteBuffer = splitValue(value);
        getInData()[address] = byteBuffer[0];
        getInData()[address+1] = byteBuffer[1];
    }
    
    public short getValue(int channel, int bufferPosition){
        
        int xpos = channel;
        int ypos = bufferPosition;
        int channels = getNumChannels();
        int bufferSize = getTransferBufferSize();
        
        if (xpos>=channels) {
            xpos = channels-1;
        }
        if (ypos>=bufferSize) {
            ypos = bufferSize-1;
        }
        int address = 2*(ypos*channels+xpos);
        
        byteBuffer[0] = getInData()[address];
        byteBuffer[1] = getInData()[address+1];
        return joinBytes(byteBuffer);
    }
    
    public byte[] getByteBuffer() {
        return byteBuffer;
    }
    
    public AudioFormat getAudioFormat(){
        return inputFormat;
    }
    
    public void setByteBuffer(byte[] byteBuffer) {
        this.byteBuffer = byteBuffer;
    }
    
    //split a 16bit value into 2 bytes
    public byte[] splitValue(short aValue){
        return byteUtility.splitValue(aValue);
    }
    
    public short joinBytes(byte[] highAndLowBytes){
        return byteUtility.joinBytes(highAndLowBytes);
    }
    
    public void open() throws ResourceUnavailableException {
        if (device == null && inputFormat != null) {
            if (!initDevice(inputFormat))
                throw new ResourceUnavailableException("Cannot intialize audio device for playback");
            device.pause();
        }
    }
    
    // use this method to do the processing and rendering
    public abstract void processInData();
    
    public DoubleDataBufferContainer getDoubleDataBufferContainer() {
        return doubleDataBufferContainer;
    }
    
    public void setDoubleDataBufferContainer(DoubleDataBufferContainer doubleDataBufferContainer) {
        this.doubleDataBufferContainer = doubleDataBufferContainer;
    }
    
    public boolean isExclusive() {
        return false;
    }
    
    protected boolean initDevice(javax.media.format.AudioFormat in) {
        javax.media.Format newInput = in;
        
        if(in.getEndian()==AudioFormat.LITTLE_ENDIAN){
            byteUtility.setEndianess(ByteOrder.LITTLE_ENDIAN);
        }else {
            byteUtility.setEndianess(ByteOrder.BIG_ENDIAN);
        }
        
        devFormat = in;
        return super.initDevice((javax.media.format.AudioFormat)newInput);
    }
    
    //overload to use different devices
    protected AudioOutput createDevice(javax.media.format.AudioFormat format) {
        return getAudioDevice();
    }
    
    public void setEndianess(ByteOrder endianess) {
        this.endianess = endianess;
    }
    
    public int processData(Buffer buffer) {
        if (!checkInput(buffer))
            return BUFFER_PROCESSED_FAILED;
        
        inData = (byte[])buffer.getData();
        inLength = inData.length;
        
        if(!enableAutoEndianess){
            getByteUtility().setEndianess(endianess);
        }
        //implement this to do whatever processing you want
        processInData();
        //send the data to the output device
        int ret = super.doProcessData(buffer);
        return ret;
    }
    
    public Object [] getControls() {
        Control c[] = new Control[] {bufferControl
        };
        return c;
    }
    
    class GCA extends GainControlAdapter {
        
        AudioRenderer renderer;
        
        protected GCA(AudioRenderer r) {
            super(false);
            renderer = r;
        }
        
        public void setMute(boolean mute) {
            if (renderer != null && renderer.device != null)
                renderer.device.setMute(mute);
            super.setMute(mute);
        }
        
        public float setLevel(float g) {
            float level = super.setLevel(g);
            if (renderer != null && renderer.device != null)
                renderer.device.setGain(getDB());
            return level;
        }
    }
}