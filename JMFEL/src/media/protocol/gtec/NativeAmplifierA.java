/*
 * GusbAMP.java
 *
 * Created on 25. Juni 2007, 12:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.gtec;

/**
 *
 * @author Administrator
 */
public class NativeAmplifierA implements Amplifier{
    
    static {
        try{
            System.loadLibrary("amplifierA");
            System.out.println("dll loaded...");
        }catch(Exception e){System.out.println(e);};
    }
    
    /** Creates a new instance of GusbAMP */
    public NativeAmplifierA() {
    }
    
    //gather machine dependent information
    public native int getHeaderSize();
    public native int getSizeOfFloat();
    
    /*COMMOMN FUNCTIONS*/
    public native float getDriverVersion();
    public native boolean probeDevice(int port);
    public native boolean openDevice(int portNumber);
    public native boolean openDevice(String serial);
    public native boolean closeDevice();
    public native int getData(byte[]buffer);
    public native boolean setBufferSize(int bufferSize);
    public native boolean setSampleRate(int bufferSize);
    public native boolean start();
    public native boolean stop();
    public native boolean setChannels(int[] channels);
    public native boolean setDigitalOut(int number, boolean value);
    public native boolean getDigitalIO(boolean[] digital_in, boolean[] digital_out);
    public native void getLastError();
    public native int getLastErrorCode();
    public native String getLastErrorMessage();
    public native boolean resetTransfer();
    public native String getSerial();
    public native boolean enableTriggerLine(boolean flag);
    public native double getImpedance(int channel);
    public native boolean calibrate(float[]factor,float[]offset);
    
    /*FILTER*/
    public native boolean setScale(float[]factor,float[]offset);
    public native boolean getScale(float[]factor,float[]offset);
    public native boolean getFilterSpec(float[]filterSpec,int filterID);
    public native int getNumberOfFilters();
    public native boolean setBandPass(int filterID, int channelID);
    public native boolean getNotchFilterSpec(float[]filterSpec,int filterID);
    public native int getNumberOfNotchFilters();
    public native boolean setNotch(int filterID, int channelID);
    
    /*MODE*/
    public native boolean setMode(int mode);
    public native int getMode();
    public native boolean setGround(boolean[]gnd);
    public native boolean getGround(boolean[]gnd);
    public native boolean setReference(boolean[]gnd);
    public native boolean getReference(boolean[]gnd);
    public native boolean setBipolar(int bipoChannel[]);
    public native boolean setDRLChannel(int drlChannel[]);
    public native boolean enableSC(boolean flag);
    public native boolean setSlave(boolean flag);
    public native boolean setDAC(byte waveShape,int amplitude, int frequency, int offset);
    
    public static void main(String[] args) {
        NativeAmplifierA aAmplifier = new NativeAmplifierA();
        System.out.println("Starting a simple selftest:");
        System.out.println("The driver version is: " + aAmplifier.getDriverVersion());
        System.out.println("Headersize is: "+ aAmplifier.getHeaderSize());
        System.out.println("sizeof(float) is: "+ aAmplifier.getSizeOfFloat());
        
        System.out.println("scanning usb bus for devices: ");
        boolean found = false;
        boolean success = false;
        for (int port_id = 0; port_id < 5; port_id++) {
            System.out.println("Testing common functions:");
            System.out.println("probing port: "+port_id);
            found = aAmplifier.probeDevice(port_id);
            if(found){
                System.out.println("Found device at port: "+port_id);
                System.out.println("Opening device:");
                aAmplifier.openDevice(port_id);
                System.out.println("Getting serial");
                String serial = aAmplifier.getSerial();
                System.out.println("The serial is: "+serial);
                System.out.println("Closing device");
                success = aAmplifier.closeDevice();
                System.out.println("success: "+success);
                success = false;
                System.out.println("Reopening device with serial number");
                aAmplifier.openDevice(serial);
                System.out.println("Setting buffersize to 8");
                success = aAmplifier.setBufferSize(8);
                System.out.println("success: "+success);
                success = false;
                System.out.println("Setting samplerate to 128");
                success = aAmplifier.setSampleRate(128);
                System.out.println("success: "+success);
                success = false;
                System.out.println("setting 8 channels ");
                int[] channels = new int[8];
                channels[0] = 1;
                channels[1] = 2;
                channels[2] = 3;
                channels[3] = 4;
                channels[4] = 5;
                channels[5] = 6;
                channels[6] = 7;
                channels[7] = 8;
                success = aAmplifier.setChannels(channels);
                System.out.println("success: "+success);
                success = false;
                System.out.println("setting digital out 1|true");
                success = aAmplifier.setDigitalOut(1,true);
                System.out.println("success: "+success);
                success = false;
                System.out.println("getting digital in/out");
                boolean[] digital_in = new boolean[2];
                boolean[] digital_out = new boolean[2];
                success = aAmplifier.getDigitalIO(digital_in,digital_out);
                System.out.println("success: "+success);
                success = false;
                for (int state = 0; state < 2; state++) {
                    System.out.println("digital in: "+state +": "+digital_in[state]);
                    System.out.println("digital out: "+state +": "+digital_out[state]);
                }
                
                System.out.println("preparing last error");
                aAmplifier.getLastError();
                System.out.println("getting error code");
                System.out.println(aAmplifier.getLastErrorCode());
                System.out.println("getting last error message");
                System.out.println(aAmplifier.getLastErrorMessage());
                System.out.println("resetting transfer");
                success = aAmplifier.resetTransfer();
                System.out.println("success: "+success);
                success = false;
                System.out.println("enabling trigger line: false");
                success = aAmplifier.enableTriggerLine(false);
                System.out.println("success: "+success);
                success = false;
                System.out.println("getting impedances: ");
                for (int chn = 1; chn < 9; chn++) {
                    System.out.println("Channel: "+chn +" "+aAmplifier.getImpedance(chn));
                }
                System.out.println("calibrating...");
                
                float[] factor = new float[16];
                float[] offset = new float[16];
                
                success = aAmplifier.calibrate(factor,offset);
                System.out.println("success: "+success);
                success = false;
                
                for (int chn = 0; chn < 16; chn++) {
                    System.out.println("Channel "+chn + ": factor: "+factor[chn]+" offset: "+offset[chn]);
                }
                
                System.out.println("Testing filter functions:");
                System.out.println("testing setScale()");
                System.out.println("setting factor and offset for each channel to 1.0");
                for (int chn = 0; chn < 16; chn++) {
                    factor[chn]=1.0f;
                    offset[chn]=1.0f;
                }
                success = aAmplifier.setScale(factor,offset);
                System.out.println("success: "+success);
                success = false;
                
                System.out.println("testing getScale()");
                System.out.println("getting factor and offset for each channel");
                
                for (int chn = 0; chn < 16; chn++) {
                    factor[chn]=0.0f;
                    offset[chn]=0.0f;
                }
                System.out.println("call getScale()");
                success = aAmplifier.getScale(factor,offset);
                
                System.out.println("success: "+success);
                success = false;
                System.out.println("display factor and offset for each channel");
                for (int chn = 0; chn < 16; chn++) {
                    System.out.println("Channel "+chn + ": factor: "+factor[chn]+" offset: "+offset[chn]);
                }
                System.out.println("get the number of filters");
                int numberOfFilters = aAmplifier.getNumberOfFilters();
                System.out.println("There are "+numberOfFilters+" defined");
                float[]filterSpec = new float[5];
                
                for (int filterID = 0; filterID < numberOfFilters; filterID++) {
                    System.out.println("Reading filter spec for id: "+filterID);
                    success = aAmplifier.getFilterSpec(filterSpec,filterID);
                    System.out.println("success: "+success);
                    success = false;
                    System.out.println("getting the specs:");
                    System.out.println("fo: "+filterSpec[0]);
                    System.out.println("fs: "+filterSpec[1]);
                    System.out.println("fu: "+filterSpec[2]);
                    System.out.println("order: "+filterSpec[3]);
                    System.out.println("type: "+filterSpec[4]);
                }
                System.out.println("setting bandpass filter 1 for channel 4");
                success = aAmplifier.setBandPass(1,4);
                System.out.println("success: "+success);
                success = false;
                
                System.out.println("get the number of notches");
                int numberOfNotches = aAmplifier.getNumberOfNotchFilters();
                System.out.println("There are "+numberOfNotches+" defined");
                float[]notchSpec = new float[5];
                
                for (int notchID = 0; notchID < numberOfNotches; notchID++) {
                    System.out.println("Reading filter spec for id: "+notchID);
                    success = aAmplifier.getNotchFilterSpec(notchSpec,notchID);
                    System.out.println("success: "+success);
                    success = false;
                    System.out.println("getting the specs:");
                    System.out.println("fo: "+notchSpec[0]);
                    System.out.println("fs: "+notchSpec[1]);
                    System.out.println("fu: "+notchSpec[2]);
                    System.out.println("order: "+notchSpec[3]);
                    System.out.println("type: "+notchSpec[4]);
                }
                System.out.println("setting notch filter 2 for channel 5");
                success = aAmplifier.setNotch(2,5);
                System.out.println("success: "+success);
                success = false;
                int mode= -1;
                
                System.out.println("setting the mode to M_IMPEDANCE");
                success = aAmplifier.setMode(M_IMPEDANCE);
                System.out.println("success: "+success);
                success = false;
                mode = aAmplifier.getMode();
                if(mode==M_IMPEDANCE_RETURN){
                    System.out.println("mode changed to M_IMPEDANCE");
                }
                
                System.out.println("setting the mode to M_CALIBRATE");
                success = aAmplifier.setMode(M_CALIBRATE);
                System.out.println("success: "+success);
                success = false;
                mode = aAmplifier.getMode();
                if(mode==M_CALIBRATE_RETURN){
                    System.out.println("mode changed to M_CALIBRATE");
                }
                
                System.out.println("setting the mode to M_NORMAL");
                success = aAmplifier.setMode(M_NORMAL);
                System.out.println("success: "+success);
                success = false;
                mode = aAmplifier.getMode();
                if(mode==M_NORMAL_RETURN){
                    System.out.println("mode changed to M_NORMAL");
                }
                
                System.out.println();
                boolean[] commonGround = new boolean[4];
                commonGround[0]=true;
                commonGround[1]=false;
                commonGround[2]=false;
                commonGround[3]=true;
                System.out.println("Setting a ground configuration "+commonGround[0]+"|"+commonGround[1]+"|"+commonGround[2]+"|"+commonGround[3]);
                success = aAmplifier.setGround(commonGround);
                System.out.println("success: "+success);
                success = false;
                
                commonGround[0]=false;
                commonGround[1]=false;
                commonGround[2]=false;
                commonGround[3]=false;
                success = aAmplifier.getGround(commonGround);
                System.out.println("Getting a ground configuration "+commonGround[0]+"|"+commonGround[1]+"|"+commonGround[2]+"|"+commonGround[3]);
                System.out.println("success: "+success);
                success = false;
                
                System.out.println();
                boolean[] commonReference = new boolean[4];
                commonReference[0]=false;
                commonReference[1]=true;
                commonReference[2]=true;
                commonReference[3]=false;
                System.out.println("Setting a reference configuration "+commonReference[0]+"|"+commonReference[1]+"|"+commonReference[2]+"|"+commonReference[3]);
                success = aAmplifier.setReference(commonReference);
                System.out.println("success: "+success);
                success = false;                
                commonReference[0]=false;
                commonReference[1]=false;
                commonReference[2]=false;
                commonReference[3]=false;
                success = aAmplifier.getReference(commonReference);

                System.out.println("Getting a reference configuration "+commonReference[0]+"|"+commonReference[1]+"|"+commonReference[2]+"|"+commonReference[3]);
                System.out.println("success: "+success);
                success = false;
                
                System.out.println("Setting a bipolar configuration");
                
                int[] bipoArray = new int[16];
                bipoArray[0] = 1;
                bipoArray[1] = 2;
                bipoArray[2] = 3;
                bipoArray[3] = 4;
                bipoArray[4] = 5;
                bipoArray[5] = 6;
                bipoArray[6] = 7;
                bipoArray[7] = 8;
                bipoArray[8] = 9;
                bipoArray[9] = 10;
                bipoArray[10] = 11;
                bipoArray[11] = 12;
                bipoArray[12] = 13;
                bipoArray[13] = 14;
                bipoArray[14] = 15;
                bipoArray[15] = 1;
                
                success = aAmplifier.setBipolar(bipoArray);
                System.out.println("success: "+success);
                success = false;                   
                for (int id = 0; id < bipoArray.length; id++) {
                    System.out.println("channel: " +id + " and channel "+bipoArray[id]);
                }
                
                System.out.println("Setting a driven right leg configuration");
                
                int[] drlArray = new int[16];
                drlArray[0] = 1;
                drlArray[1] = 2;
                drlArray[2] = 3;
                drlArray[3] = 4;
                drlArray[4] = 5;
                drlArray[5] = 6;
                drlArray[6] = 7;
                drlArray[7] = 8;
                drlArray[8] = 9;
                drlArray[9] = 10;
                drlArray[10] = 11;
                drlArray[11] = 12;
                drlArray[12] = 13;
                drlArray[13] = 14;
                drlArray[14] = 15;
                drlArray[15] = 1;
                
                success = aAmplifier.setDRLChannel(drlArray);
                System.out.println("success: "+success);
                success = false;                  
                System.out.println("Setting shortcut to false");
                success = aAmplifier.enableSC(false);
                System.out.println("success: "+success);
                success = false;
                
                System.out.println("Setting DAC to: \nWAVESHAPE = SAWTOOTH, AMPLITUDE = 2, FREQENCY = 64 Hz, OFFSET = 0");
                
                success = aAmplifier.setDAC(WS_SAWTOOTH,2,64,0);
                System.out.println("success: "+success);
                success = false;
                
                System.out.println("Clsing the connection to the amplifier");
                success = aAmplifier.closeDevice();
                System.out.println("success: "+success);
                success = false;
            }
        }
    }
}