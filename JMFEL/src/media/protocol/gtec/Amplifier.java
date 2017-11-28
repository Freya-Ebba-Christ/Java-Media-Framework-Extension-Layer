/*
 * Amplifier.java
 *
 * Created on 23. Juli 2007, 18:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.gtec;

/**
 *
 * @author Administrator
 */
public interface Amplifier {
    
    public static final byte M_NORMAL = 0;
    public static final byte M_IMPEDANCE = 1;
    public static final byte M_CALIBRATE = 2;
    
    public static final byte M_NORMAL_RETURN = 1;
    public static final byte M_IMPEDANCE_RETURN = 3;
    public static final byte M_CALIBRATE_RETURN = 2;
    
    // FILTER
    public static final int F_CHEBYSHEV = 0;
    public static final int F_BUTTERWORTH = 1;
    public static final int F_BESSEL = 2;
    
    //WAVESHAPES
    public static final byte WS_SQUARE = 0x01;
    public static final byte WS_SAWTOOTH = 0x02;
    public static final byte WS_SINE = 0x03;
    public static final byte WS_DRL = 0x04;
    public static final byte WS_NOISE = 0x05;
    
    //gather machine dependent information
    public int getHeaderSize();
    public int getSizeOfFloat();
    
    /*COMMOMN FUNCTIONS*/
    public float getDriverVersion();
    public boolean probeDevice(int port);
    public boolean openDevice(int portNumber);
    public boolean openDevice(String serial);
    public boolean closeDevice();
    public int getData(byte[]buffer);
    public boolean setBufferSize(int bufferSize);
    public boolean setSampleRate(int samplerate);
    public boolean start();
    public boolean stop();
    public boolean setChannels(int[] channels);
    public boolean setDigitalOut(int number, boolean value);
    public boolean getDigitalIO(boolean[] digital_in, boolean[] digital_out);
    public void getLastError();
    public int getLastErrorCode();
    public String getLastErrorMessage();
    public boolean resetTransfer();
    public String getSerial();
    public boolean enableTriggerLine(boolean flag);
    public double getImpedance(int channel);
    public boolean calibrate(float[]factor,float[]offset);
    
    /*FILTER*/
    public boolean setScale(float[]factor,float[]offset);
    public boolean getScale(float[]factor,float[]offset);
    public boolean getFilterSpec(float[]filterSpec,int filterID);
    public int getNumberOfFilters();
    public boolean setBandPass(int filterID, int channelID);
    public boolean getNotchFilterSpec(float[]filterSpec,int filterID);
    public int getNumberOfNotchFilters();
    public boolean setNotch(int filterID, int channelID);
    
    /*MODE*/
    public boolean setMode(int mode);
    public int getMode();
    public boolean setGround(boolean[]gnd);
    public boolean getGround(boolean[]gnd);
    public boolean setReference(boolean[]gnd);
    public boolean getReference(boolean[]gnd);
    public boolean setBipolar(int bipoChannel[]);
    public boolean setDRLChannel(int drlChannel[]);
    public boolean enableSC(boolean flag);
    public boolean setSlave(boolean flag);
    public boolean setDAC(byte waveShape,int amplitude, int frequency, int offset);
}
