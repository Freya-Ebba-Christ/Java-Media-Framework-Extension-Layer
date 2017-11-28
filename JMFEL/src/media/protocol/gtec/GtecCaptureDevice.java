/*
 * GtecCaptureDevice.java
 *
 * Created on 21. Dezember 2007, 12:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.gtec;

import java.nio.ByteBuffer;
import java.util.Properties;
import java.util.Vector;
import javax.media.format.AudioFormat;
import media.protocol.eye.CustomCaptureDevice;
import media.protocol.generic.GenericCustomCaptureDevice;
import utilities.PropertiesReader;

/**
 *
 * @author Administrator
 */
public class GtecCaptureDevice extends GenericCustomCaptureDevice{
    private float numsec = 0;
    private float[] inBuffer;
    private int bufferSize;
    private ByteBuffer buf = ByteBuffer.allocate(2);
    private int arrayAddress = 0;
    private Properties configuration = null;
    private int transferBuffersize = 0;
    private PropertiesReader propertiesReader = new PropertiesReader();
    private String file_separator = System.getProperty("file.separator");
    private final boolean DEBUG = true;
    private Configuration settings;
    private FilterSpecList aNotchFilterSpecList;
    private FilterSpecList aFilterSpecList;
    private USBAmp amplifier;
    private String serial;
    private boolean initialized = false;
    private Properties serial_ini = null;
    private AudioFormat audioFormat;
    public static final int DEVICE_A = 0;
    public static final int DEVICE_B = 1;
    public static final int DEVICE_C = 2;
    public static final int DEVICE_D = 3;
    public int device = DEVICE_A;
    
    public GtecCaptureDevice() {
    }
    
    public void setDevice(int device) {
        this.device = device;
    }
    
    public int getDevice() {
        return device;
    }
    
    public USBAmp getAmplifier() {
        return amplifier;
    }
    
    public void setAmplifier(USBAmp amplifier) {
        this.amplifier = amplifier;
    }
    
    public String getSerial() {
        return serial;
    }
    
    public void setSerial(String serial) {
        this.serial = serial;
    }
    
    public Configuration getSettings() {
        return settings;
    }
    
    public void setSettings(Configuration settings) {
        this.settings = settings;
    }
    
    public void init(){
        setInitialized(false);
        loadIni();
        //16 bit and channels will always be set to 16 and 1 respectively, because N channels are interleaved into a single channel
        setFormat(new AudioFormat(AudioFormat.LINEAR, getSamplerate()*getNumChannels(), 16, 1));
        setInitialized(true);
    }
    
    public byte[] getByteArray(float aValue){
        buf.putShort((short)aValue);
        buf.rewind();
        return buf.array();
    }
    
    public void start(){
        try{
            getAmplifier().start();
        }catch(Exception e){System.out.println(e);};
    }
    
    public void stop(){
        try{
            getAmplifier().stop();
            System.out.println("device stopped");
        }catch(Exception e){System.out.println(e);};
    }
    
    public void close(){
        try{
            getAmplifier().close();
            System.out.println("device closed");
        }catch(Exception e){System.out.println(e);};
    }
    
    public void loadIni(){
        /*In order to communicate with the guger gtec gUSBamp EEG signal amplifier you have to instantiate the USBAmp class
         *Guger has given each amplifier a unique serial number which you can use to open the device you are currently using.
         *The serial is printed on the label on top of each amplifier.
         *The serial of this device has to be entered in the serial.ini.
         *Usually, the user communicates with the amplifier through the high level api which is implemented in the AbstractUSBAmp class.
         *USBAmp, right now, is actually nothing but a subclass of AbstractUSBAmp.
         *The actual comminication with guger's C++ Api is implemented in the
         *NativeAmplifierA, NativeAmplifierB, NativeAmplifierC, NativeAmplifierD classes which access this api through a corresponding JNI DLL.
         *These four classes implement some kind of low level api, very c-like, and relatively difficult to use. Also, there is no exception handling.
         *This is added in the high level api.
         */
        if(getDevice()==DEVICE_A){
            serial_ini = propertiesReader.readProperties(System.getProperty("user.dir") + file_separator + "media" + file_separator + "protocol" + file_separator + "usbAmpA" + file_separator + "serial.ini");
        }else if(getDevice()==DEVICE_B){
            serial_ini = propertiesReader.readProperties(System.getProperty("user.dir") + file_separator + "media" + file_separator + "protocol" + file_separator + "usbAmpB" + file_separator + "serial.ini");
        }else if(getDevice()==DEVICE_C){
            serial_ini = propertiesReader.readProperties(System.getProperty("user.dir") + file_separator + "media" + file_separator + "protocol" + file_separator + "usbAmpC" + file_separator + "serial.ini");
        }else if(getDevice()==DEVICE_D){
            serial_ini = propertiesReader.readProperties(System.getProperty("user.dir") + file_separator + "media" + file_separator + "protocol" + file_separator + "usbAmpD" + file_separator + "serial.ini");
        }
        
        serial = (String)serial_ini.getProperty("SERIAL");
        
        setAmplifier(new USBAmp());
        if(getDevice()==DEVICE_A){
            //System.out.println("using device a");
            getAmplifier().setAmp(new NativeAmplifierA());
        }else if(getDevice()==DEVICE_B){
            //System.out.println("using device b");
            getAmplifier().setAmp(new NativeAmplifierB());
        }else if(getDevice()==DEVICE_C){
            //System.out.println("using device c");
            getAmplifier().setAmp(new NativeAmplifierC());
        }else if(getDevice()==DEVICE_D){
            //System.out.println("using device d");
            getAmplifier().setAmp(new NativeAmplifierD());
        }
        
        getAmplifier().setSerialNumber(getSerial());
        //open the device specified by the serial read from the
        try{
            getAmplifier().open();
            if(DEBUG){
                System.out.println("device " +getSerial()+" is open");
            }
        }catch(Exception ex){System.out.println(ex);};
        
        settings = new Configuration();
        settings.setModel(new ConfigurationTableModel());
        settings.setCommonGround(new CommonGround());
        settings.setCommonReference(new CommonReference());
        String file_separator = System.getProperty("file.separator");
        String settingsFile = System.getProperty("user.dir") + file_separator + "media" + file_separator + "protocol" + file_separator + "usbAmpA" + file_separator + "configuration.ini";
        settings.load(settingsFile);
        
        try{
            getAmplifier().setSampleRate(getSettings().getSampleRate());
        }catch(Exception ex){System.out.println(ex);};
        
        if(getSettings().isModeTestSignal()){
            try{
                System.out.println("setting mode to calibrate");
                getAmplifier().setMode(Amplifier.M_CALIBRATE);
            }catch(Exception e){System.out.println(e);};
        }else {
            
            try{
                System.out.println("setting mode to calibrate");
                getAmplifier().setMode(Amplifier.M_CALIBRATE);
            }catch(Exception e){System.out.println(e);};
            
            System.out.println("calibrating...");
            float[] factor = new float[16];
            float[] offset = new float[16];
            try{
                getAmplifier().calibrate(factor,offset);
                for (int chn = 0; chn < 16; chn++) {
                    System.out.println("Channel "+chn + ": factor: "+factor[chn]+" offset: "+offset[chn]);
                }
            }catch(Exception e){System.out.println(e);};
            
            try{
                System.out.println("setting mode to measure");
                getAmplifier().setMode(Amplifier.M_NORMAL);
            }catch(Exception e){System.out.println(e);};
        }
        
        if(DEBUG){
            System.out.println("settings read from ini");
        }
        
        // now configure the device with the settings read from the file
        try{
            getAmplifier().setGround(getSettings().getCommonGround());
        }catch(Exception ex){System.out.println(ex);};
        
        try{
            getAmplifier().setReference(getSettings().getCommonReference());
        }catch(Exception ex){System.out.println(ex);};
        
        try{
            getAmplifier().enableTriggerLine(getSettings().isTrigger());
        }catch(Exception ex){System.out.println(ex);};
        
        try{
            getAmplifier().setSlave(getSettings().isSlave());
        }catch(Exception ex){System.out.println(ex);};
        
        try{
            getAmplifier().enableSC(getSettings().isShortcut());
        }catch(Exception ex){System.out.println(ex);};
        
        //tell the amp which and how many channels to use
        Vector<Integer> usedChannels = new Vector();
        
        for (int chn = 0; chn < DaqSettingsPanel.getNumChannels(); chn++) {
            if(getSettings().getUsedChannel(chn)){
                usedChannels.add(new Integer(chn+1));
            }
        }
        
        int[] channels = new int[usedChannels.size()];
        for (int chn = 0; chn < channels.length; chn++) {
            channels[chn] = usedChannels.get(chn).intValue();
        }
        
        try{
            getAmplifier().setChannels(channels);
        }catch(Exception ex){System.out.println(ex);};
        
        //setting bipolar configuration
        int[] bipolar = new int[DaqSettingsPanel.getNumChannels()];
        
        for (int chn = 0; chn <DaqSettingsPanel.getNumChannels(); chn++){
            bipolar[chn] = ((Integer)getSettings().getModel().getValueAt(chn,ConfigurationTableModel.BIPOLAR)).intValue();
        }
        
        try{
            getAmplifier().setBipolar(bipolar);
        }catch(Exception ex){System.out.println(ex);};
        
        //setting drl configuration
        int[] drl = new int[DaqSettingsPanel.getNumChannels()];
        
        for (int chn = 0; chn <DaqSettingsPanel.getNumChannels(); chn++){
            drl[chn] = ((Integer)getSettings().getModel().getValueAt(chn,ConfigurationTableModel.DRL)).intValue();
        }
        
        try{
            getAmplifier().setDRLChannel(drl);
        }catch(Exception ex){System.out.println(ex);};
        
        
        try{
            getAmplifier().reloadNotchFilterSpecList();
            aNotchFilterSpecList = getAmplifier().getNotchFilterSpecList();
        }catch(Exception e){System.out.println(e);};
        
        try{
            getAmplifier().reloadFilterSpecList();
            aFilterSpecList = getAmplifier().getFilterSpecList();
        }catch(Exception e){System.out.println(e);};
        
        
        System.out.println("Removing all filter settings...");
        for (int chn = 0; chn < DaqSettingsPanel.getNumChannels(); chn++) {
            try{
                getAmplifier().setBandPass(-1,chn+1);
                getAmplifier().setNotch(-1,chn+1);
            }catch(Exception ex){System.out.println(ex);};
        }
        
        System.out.println("setting filters...");
        //bandpass filters
        for (int chn = 0; chn < DaqSettingsPanel.getNumChannels(); chn++) {
            if(getSettings().getModel().getValueAt(chn,ConfigurationTableModel.FILTER) instanceof FilterSpec){
                int id = ((FilterSpec)getSettings().getModel().getValueAt(chn,ConfigurationTableModel.FILTER)).getIndex();
                try{
                    getAmplifier().setBandPass(id,chn+1);
                    System.out.println("Channel: "+(chn+1));
                    float[]filterSpec = new float[5];
                    
                    boolean success = getAmplifier().getAmp().getFilterSpec(filterSpec,id);
                    System.out.println("success: "+success);
                    success = false;
                    System.out.println("getting the specs:");
                    System.out.println("fo: "+filterSpec[0]);
                    System.out.println("fs: "+filterSpec[1]);
                    System.out.println("fu: "+filterSpec[2]);
                    System.out.println("order: "+filterSpec[3]);
                    System.out.println("type: "+filterSpec[4]);
                    
                }catch(Exception ex){System.out.println(ex);};
            }
        }
        
        //notch filters
        for (int chn = 0; chn < DaqSettingsPanel.getNumChannels(); chn++) {
            
            if(getSettings().getModel().getValueAt(chn,ConfigurationTableModel.NOTCH) instanceof FilterSpec){
                int id = ((FilterSpec)getSettings().getModel().getValueAt(chn,ConfigurationTableModel.NOTCH)).getIndex();
                try{
                    getAmplifier().setNotch(id,chn+1);
                    System.out.println("Channel: "+(chn+1));
                    
                    float[]filterSpec = new float[5];
                    
                    System.out.println("Reading notch filter spec for id: "+id);
                    boolean success = getAmplifier().getAmp().getNotchFilterSpec(filterSpec,id);
                    System.out.println("success: "+success);
                    success = false;
                    System.out.println("getting the specs:");
                    System.out.println("fo: "+filterSpec[0]);
                    System.out.println("fs: "+filterSpec[1]);
                    System.out.println("fu: "+filterSpec[2]);
                    System.out.println("order: "+filterSpec[3]);
                    System.out.println("type: "+filterSpec[4]);
                    
                }catch(Exception ex){System.out.println(ex);};
            }
        }
        
        setSamplerate(getSettings().getSampleRate());
        setNumChannels(getSettings().getChannelsInUse());
        getAmplifier().calculateBufferSize();
        setMaxDataLength(getAmplifier().getFloatBufferSize()*2);
        
        if(getSettings().isModeTestSignal()){
            try{
                System.out.println("setting dac");
                getAmplifier().setDAC(getSettings().getSignalType(),getSettings().getAmplitude(),getSettings().getFrequency(),getSettings().getOffset());
            }catch(Exception e){System.out.println(e);};
        }
        
        try{
            System.out.println("starting device");
            getAmplifier().start();
        }catch (Exception e){System.out.println(e);};
    }
    
    public int getBufferSize(){
        return bufferSize;
    }
    
    public void makeTransferBuffer(){
        int cnt = 0;
        float value = 0.0f;
        byte [] byteValue;
        for (int x = 0; x < inBuffer.length; x++) {
            
            value = inBuffer[x];
            byteValue = getByteArray(value);
            //split into 2 bytes
            getTransferBuffer()[cnt] = byteValue[0];
            cnt++;
            getTransferBuffer()[cnt] = byteValue[1];
            cnt++;
        }
    }
    
    public byte [] getAvailableData(){
        inBuffer = getAmplifier().getData();
        setTransferBuffer(new byte[inBuffer.length*2]);
        makeTransferBuffer();
        return getTransferBuffer();
    }
}