/*
 * BitDataAccessor.java
 *
 * Created on 10. Mai 2007, 17:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package plugins.basic;

import javax.media.format.AudioFormat;
import plugins.codecs.BasicAudioCodec;

/**
 *
 * @author Administrator
 */

public class DCOffsetCorrector extends BasicAudioCodec{
    private float devider = 100f;
    private int channel = 0;
    private long[] dcOffset;
    private byte[] byteBuffer = new byte[2];
    private int cnt = 0;
    private boolean offsetReady = true;
    private int dcPeriods = 5;
    private boolean offsetUsed = false;
    
    public DCOffsetCorrector() {
        super();
    }
    
    public String getName() {
        return "DCOffsetCorrector";
    }
    
    public boolean isOffsetReady() {
        return offsetReady;
    }
    
    public void setOffsetReady(boolean offsetReady) {
        this.offsetReady = offsetReady;
    }
    
    public boolean isOffsetUsed() {
        return offsetUsed;
    }
    
    public void setOffsetUsed(boolean offsetUsed) {
        this.offsetUsed = offsetUsed;
    }
    
    public void resetOffSet(){
        for ( int i = 0; i < dcOffset.length; i++ ){
            dcOffset[i]=0;
        }
    }
    
    public void setDcPeriods(int dcPeriods) {
        this.dcPeriods = dcPeriods;
    }
    
    public int getDcPeriods() {
        return dcPeriods;
    }
    
    public void makeDCOffset(){
        dcOffset = new long[getNumChannels()];
        resetOffSet();
        offsetReady = false;
        cnt = 0;
    }
    
    public double getDcOffset(int channel) {
        return dcOffset[channel];
    }
    
    public void setDcOffset(long offset, int channel) {
        dcOffset[channel] = offset;
    }
    
    public void setDevider(float aVal){
        devider = aVal;
    }
    
    public void processInData() {
        try{
            //the endianness is determined at start from the format, unfortunately, this may fail, thus you can set the endianess manually.
            channel = 0;
            double correctedValue = 0;
            for (int x = 0; x < getInLength(); x+=2) {
                short value = getValue(x);
                if(!isOffsetReady()){
                    dcOffset[channel]+=value;
                }
                
                if(isOffsetUsed()){
                    correctedValue = value-getDcOffset(channel);
                }else{
                    correctedValue = value;
                }
                dcOffset[channel] +=correctedValue;
                setValue(x,(short)correctedValue);
                
                channel++;
                
                // all channels processed?
                if(channel>=getNumChannels()){
                    channel = 0;
                    if(!isOffsetReady()){
                        cnt++;
                    }
                }
                //compute dc-offset times the getSampleRate()
                if(cnt>((AudioFormat)getInputFormat()).getSampleRate()*getDcPeriods()&&!isOffsetReady()){
                    offsetReady = true;
                    for ( int c = 0; c < dcOffset.length; c++ ){
                        setDcOffset(dcOffset[c]/cnt,c);
                    }
                }
            }
        }catch(Exception ex){System.out.println("error "+ex);};
    }
    
    public void setValue(int pos, short value){
        byteBuffer = splitValue(value);
        getInData()[pos]=byteBuffer[0];
        getInData()[pos+1]=byteBuffer[1];
    }
    
    public short getValue(int pos){
        byteBuffer[0] = getInData()[pos];
        byteBuffer[1] = getInData()[pos+1];
        return joinBytes(byteBuffer);
    }
}
