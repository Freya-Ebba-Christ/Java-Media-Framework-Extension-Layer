/*
 * USBAmp_A.java
 *
 * Created on 6. Juli 2007, 14:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.gtec;

import exceptions.ClosingException;
import exceptions.DeviceException;
import exceptions.DeviceNotFoundException;
import exceptions.WorkingModeException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author Administrator
 */
public abstract class AbstractUSBAmp{
    
    private String serialNumber = "";
    private Amplifier amp = null;
    private int sampleRate = 128;
    private int[] channelArray = new int[16];
    private boolean bufferSizeOutdated = true;
    private int bufferSize = 1024;
    private FilterSpecList filterSpecList = new FilterSpecList();
    private FilterSpecList notchSpecList = new FilterSpecList();
    private ByteBuffer fourByteBuffer = ByteBuffer.allocate(4);
    private int scanSize;
    
    public AbstractUSBAmp() {
        for (int id = 0; id < channelArray.length; id++) {
            channelArray[id] = id+1;
        }
        //set byte order to little endian
        fourByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    }
    
    public String getSerialNumber() {
        return serialNumber;
    }
    
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    
    public Amplifier getAmp() {
        return amp;
    }
    
    public void setAmp(Amplifier amp) {
        this.amp = amp;
    }
    
    public void open()throws DeviceNotFoundException{
        if(!getAmp().openDevice(getSerialNumber())){
            throw new DeviceNotFoundException("Unable to detect the Device");
        }
    }
    
    public void close()throws ClosingException{
        if(!getAmp().closeDevice()){
            throw new ClosingException("Unable to close the Device");
        }
    }
    
    public boolean isBufferSizeOutdated() {
        return bufferSizeOutdated;
    }
    
    public float getDriverVersion(){
        return getAmp().getDriverVersion();
    }
    
    public void setChannels(int[] channels)throws DeviceException{
        channelArray = channels;
        if(!getAmp().setChannels(channels)){
            throw new DeviceException();
        }
        bufferSizeOutdated = true;
    }
    
    public int[] getChannels() {
        return channelArray;
    }
    
    public void setBufferSizeOutdated(boolean bufferSizeOutdated) {
        this.bufferSizeOutdated = bufferSizeOutdated;
    }
    
    public int getSampleRate() {
        return sampleRate;
    }
    
    public void setSampleRate(int sampleRate) throws DeviceException{
        if(!getAmp().setSampleRate(sampleRate)){
            throw new DeviceException();
        }
        
        this.sampleRate = sampleRate;
        bufferSizeOutdated = true;
    }
    
    public int getBufferSize() {
        return bufferSize;
    }
    
    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }
    
    public void start() throws WorkingModeException{
        if(!getAmp().start()){
            throw new WorkingModeException("Wrong working mode");
        }
    }
    
    public void stop()throws DeviceException{
        if(!getAmp().stop()){
            throw new DeviceException();
        }
    }
    
    public void setDigitalOut(int number, boolean value) throws DeviceException{
        if(!getAmp().setDigitalOut(number, value)){
            throw new DeviceException();
        }
    }
    
    public DigitalIO getDigitalIO() throws DeviceException{
        
        DigitalIO aDigitalIO;
        boolean[] digital_in = new boolean[2];
        boolean[] digital_out = new boolean[2];
        
        if(!getAmp().getDigitalIO(digital_in, digital_out)){
            throw new DeviceException("Unable to get digital IO");
        }else {
            aDigitalIO = new DigitalIO();
            aDigitalIO.setDIN1(digital_in[0]);
            aDigitalIO.setDIN2(digital_in[1]);
            aDigitalIO.setDOUT1(digital_out[0]);
            aDigitalIO.setDOUT2(digital_out[1]);
        }
        return aDigitalIO;
    }
    
    public void getLastError(){
        getAmp().getLastError();
    }
    
    public int getLastErrorCode(){
        return getAmp().getLastErrorCode();
    }
    
    public String getLastErrorMessage(){
        return getAmp().getLastErrorMessage();
    }
    
    public void enableTriggerLine(boolean flag)throws DeviceException{
        if(!getAmp().enableTriggerLine(flag)){
            throw new DeviceException();
        }
    }
    
    public double getImpedance(int channel){
        return getAmp().getImpedance(channel);
    }
    
    public void calibrate(float[]factor,float[]offset)throws DeviceException{
        if(!getAmp().calibrate(factor,offset)){
            throw new DeviceException();
        }
    }
    
    public void getScale(float[]factor,float[]offset)throws DeviceException{
        if(!getAmp().getScale(factor, offset)){
            throw new DeviceException();
        }
    }
    
    public void setScale(float[] factor,float[] offset)throws DeviceException{
        if(!getAmp().setScale(factor, offset)){
            throw new DeviceException();
        }
    }
    
    public FilterSpecList getFilterSpecList(){
        return filterSpecList;
    }
    
    public void reloadFilterSpecList()throws DeviceException{
        int numFilters = getAmp().getNumberOfFilters();
        filterSpecList.clear();
        for (int filterID = 0; filterID < numFilters; filterID++) {
            float[]filterSpec = new float[5];
            if(!getAmp().getFilterSpec(filterSpec,filterID)){
                throw new DeviceException();
            }else{
                FilterSpec aFilterSpec = new FilterSpec();
                aFilterSpec.setHighFreqency(filterSpec[0]);
                aFilterSpec.setSamplingrate(filterSpec[1]);
                aFilterSpec.setLowFreqency(filterSpec[2]);
                aFilterSpec.setOrder(filterSpec[3]);
                aFilterSpec.setType(filterSpec[4]);
                filterSpecList.addFilterSpec(aFilterSpec);
            }
        }
    }
    
    public void setBandPass(int filterID, int channel)throws DeviceException{
        if(!getAmp().setBandPass(filterID, channel)){
            throw new DeviceException();
        }
    }
    
    public FilterSpecList getNotchFilterSpecList(){
        return notchSpecList;
    }
    
    public void reloadNotchFilterSpecList()throws DeviceException{
        int numFilters = getAmp().getNumberOfNotchFilters();
        notchSpecList.clear();
        for (int filterID = 0; filterID < numFilters; filterID++) {
            float[]filterSpec = new float[5];
            if(!getAmp().getNotchFilterSpec(filterSpec,filterID)){
                throw new DeviceException();
            }else{
                FilterSpec aFilterSpec = new FilterSpec();
                aFilterSpec.setHighFreqency(filterSpec[0]);
                aFilterSpec.setSamplingrate(filterSpec[1]);
                aFilterSpec.setLowFreqency(filterSpec[2]);
                aFilterSpec.setOrder(filterSpec[3]);
                aFilterSpec.setType(filterSpec[4]);
                notchSpecList.addFilterSpec(aFilterSpec);
            }
        }
    }
    
    public void setNotch(int filterID, int channel)throws DeviceException{
        if(!getAmp().setNotch(filterID, channel)){
            throw new DeviceException();
        }
    }
    
    public void setMode(int workingMode) throws DeviceException{
        if(!getAmp().setMode(workingMode)){
            throw new DeviceException();
        }
    }
    
    public int getMode(){
        return getAmp().getMode();
    }
    
    public void setGround(CommonGround commonGround) throws WorkingModeException{
        
        boolean[] ground = new boolean[4];
        
        ground[0] = commonGround.isGroupA();
        ground[1] = commonGround.isGroupB();
        ground[2] = commonGround.isGroupC();
        ground[3] = commonGround.isGroupD();
        
        if(!getAmp().setGround(ground)){
            throw new WorkingModeException();
        }
    }
    
    public CommonGround getGround()throws WorkingModeException{
        CommonGround commonGround;
        boolean[] ground = new boolean[4];
        
        if(!getAmp().getGround(ground)){
            throw new WorkingModeException();
        }else{
            commonGround = new CommonGround();
            commonGround.setGroupA(ground[0]);
            commonGround.setGroupB(ground[1]);
            commonGround.setGroupC(ground[2]);
            commonGround.setGroupD(ground[3]);
        }
        
        return commonGround;
    }
    
    public void setReference(CommonReference commonReference) throws WorkingModeException{
        
        boolean[] reference = new boolean[4];
        
        reference[0] = commonReference.isGroupA();
        reference[1] = commonReference.isGroupB();
        reference[2] = commonReference.isGroupC();
        reference[3] = commonReference.isGroupD();
        
        if(!getAmp().setReference(reference)){
            throw new WorkingModeException();
        }
    }
    
    public CommonReference getReference()throws WorkingModeException{
        CommonReference commonReference;
        boolean[] reference = new boolean[4];
        
        if(!getAmp().getReference(reference)){
            throw new WorkingModeException();
        }else{
            commonReference = new CommonReference();
            commonReference.setGroupA(reference[0]);
            commonReference.setGroupB(reference[1]);
            commonReference.setGroupC(reference[2]);
            commonReference.setGroupD(reference[3]);
        }
        return commonReference;
    }
    
    public void setBipolar(int[] bipoArray)throws DeviceException{
        if(!getAmp().setBipolar(bipoArray)){
            throw new DeviceException();
        }
    }
    
    public void setDRLChannel(int[] drlChannels)throws DeviceException{
        if(!getAmp().setDRLChannel(drlChannels)){
            throw new DeviceException();
        }
    }
    
    public void enableSC(boolean flag)throws DeviceException{
        if(!getAmp().enableSC(flag)){
            throw new DeviceException();
        }
    }
    
    public void setSlave(boolean flag)throws DeviceException{
        if(!getAmp().setSlave(flag)){
            throw new DeviceException();
        }
    }
    
    public void setDAC(byte waveShape,int amplitude, int frequency, int offset)throws DeviceException{
        if(!getAmp().setDAC(waveShape, amplitude, frequency, offset)){
            throw new DeviceException();
        }
    }
    
    public void calculateBufferSize(){
        setScanSize((int)Math.ceil(getSampleRate()*60*0.001));
        setBufferSize(getScanSize()*getChannels().length*getAmp().getSizeOfFloat()+getAmp().getHeaderSize());
    }
    
    public int getFloatBufferSize(){
        return (getBufferSize()-getAmp().getHeaderSize())/4;
    }
    
    public int getScanSize() {
        return scanSize;
    }
    
    public void setScanSize(int scanSize) {
        this.scanSize = scanSize;
        bufferSizeOutdated = true;
    }
    
    public float[] getData(){
        
        int validsamples = 0;
        
        //recalculate BufferSize if needed
        if(isBufferSizeOutdated()){
            calculateBufferSize();
            getAmp().setBufferSize(getScanSize());
            setBufferSizeOutdated(false);
        }
        
        byte[] rawbuffer = new byte[getBufferSize()];
        validsamples = getAmp().getData(rawbuffer);
        float[] floatbuffer = new float[validsamples];
        int end = validsamples*getAmp().getSizeOfFloat()+getAmp().getHeaderSize();
        
        int cnt = 0;
        for(int i = getAmp().getHeaderSize(); i<end;i+=4) {
            //revert byte order
            byte[] tmp = new byte[4];
            tmp[0] = rawbuffer[i+3];
            tmp[1] = rawbuffer[i+2];
            tmp[2] = rawbuffer[i+1];
            tmp[3] = rawbuffer[i];
            floatbuffer[cnt]=fourByteBuffer.wrap(tmp,0,4).getFloat();
           cnt++;
        }
        return floatbuffer;
    }
}
