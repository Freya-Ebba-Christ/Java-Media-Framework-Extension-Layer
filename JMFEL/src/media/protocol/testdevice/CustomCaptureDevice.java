package media.protocol.testdevice;
import java.nio.ByteBuffer;
import javax.media.format.*;
import java.net.*;
import java.io.*;
import java.util.Properties;
import media.protocol.generic.GenericCustomCaptureDevice;
import utilities.PropertiesReader;

public class CustomCaptureDevice extends GenericCustomCaptureDevice{
    
    private DatagramSocket dataggramSocket = null;
    private DatagramPacket datagramPacket = null;
    private float numsec = 0;
    private float[] inBuffer;
    private int bufferSize;
    private int maxChannels;
    private ByteBuffer buf = ByteBuffer.allocate(2);
    private int arrayAddress = 0;
    private Properties configuration = null;
    private int transferBuffersize = 0;
    private PropertiesReader propertiesReader = new PropertiesReader();
    private String file_separator = System.getProperty("file.separator");
    
    public CustomCaptureDevice() {
    }
    
    public void init(){
        loadIni();
        setFormat(new AudioFormat(AudioFormat.LINEAR, getSamplerate()*getNumChannels(), getBitsPerSample(), 1));
        int frameSize = (getFormat().getSampleSizeInBits() * getFormat().getChannels())/16;
        setMaxDataLength((int)(getFormat().getSampleRate() * frameSize / 32));
        setTransferBuffer(new byte[getMaxDataLength()]);
        setInitialized(true);
    }
    
    public byte[] getByteArray(float aValue){
        buf.putShort((short)aValue);
        buf.rewind();
        return buf.array();
    }
    
    public void loadIni(){
        configuration = propertiesReader.readProperties(System.getProperty("user.dir") + file_separator + "media" + file_separator + "protocol" + file_separator + "testdevice" + file_separator + "config.ini");
        setNumChannels(Integer.parseInt((String)configuration.getProperty("CHANNELS_TO_TRANSFER")));
        bufferSize = Integer.parseInt((String)configuration.getProperty("BUFFER_SIZE"));
        maxChannels = Integer.parseInt((String)configuration.getProperty("MAX_CHANNELS"));
        setSamplerate(Integer.parseInt((String)configuration.getProperty("SAMPLERATE")));
        setBitsPerSample(Integer.parseInt((String)configuration.getProperty("BITS_PER_SAMPLE")));
    }
    
    public int getBufferSize(){
        return bufferSize;
    }
    
    public void buildInBuffer(){
        
        int counter = 0;
        float dummyValues[] = new float[16];
        dummyValues[0] = 0.0f;
        dummyValues[1] = 250.0f;
        dummyValues[2] = -250.0f;
        dummyValues[3] = 200.0f;
        dummyValues[4] = 250.0f;
        dummyValues[5] = 30.0f;
        dummyValues[6] = 35.0f;
        dummyValues[7] = 40.0f;
        dummyValues[8] = 45.0f;
        dummyValues[9] = 50.0f;
        dummyValues[10] = 55.0f;
        dummyValues[11] = 60.0f;
        dummyValues[12] = 65.0f;
        dummyValues[13] = 70.0f;
        dummyValues[14] = 75.0f;
        dummyValues[15] = 80.0f;
        for (int x = 0; x < bufferSize*maxChannels; x++) {
            if (counter>15){
                counter=0;
            }
            inBuffer[x] = dummyValues[counter];
            counter++;
        }
    }
    
    public void makeTransferBuffer(){
        int cnt = 0;
        int channel = 0;
        float value = 0.0f;
        byte [] byteValue;
        for (int x = 0; x < inBuffer.length; x++) {
            if(channel<getNumChannels()){
                value = inBuffer[x];
                byteValue = getByteArray(value);
                //split into 2 bytes
                getTransferBuffer()[cnt] = byteValue[0];
                getTransferBuffer()[cnt+1] = byteValue[1];
                cnt+=2;
            }
            channel++;
            if(channel >= maxChannels) {
                channel=0;
            }
        }
    }
    
    public byte [] getAvailableData(){
        setTransferBuffer(new byte[getMaxDataLength()]);
        //no buffering at this point. Just drop packets that cant be processed fast enough...
        //get the data from the device
        inBuffer = new float[bufferSize*maxChannels];
        int counter = 0;
        buildInBuffer();
        makeTransferBuffer();
        return getTransferBuffer();
    }
}