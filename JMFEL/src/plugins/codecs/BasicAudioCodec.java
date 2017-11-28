/*
 * BasicCodec.java
 *
 * Created on 3. Mai 2007, 12:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package plugins.codecs;

import com.sun.media.codec.audio.AudioCodec;
import java.nio.ByteOrder;
import javax.media.Buffer;
import javax.media.Format;
import javax.media.format.AudioFormat;
import utilities.ByteUtility;
import utilities.DoubleDataBufferContainer;

/**
 *
 * @author christ
 */

public abstract class BasicAudioCodec extends AudioCodec{
    
    private byte[] inData = null;
    private int inLength = 0;
    private byte[] outData = null;
    private int outLength = 0;
    private ByteUtility byteUtility = new ByteUtility();
    private byte[] byteBuffer = new byte[2];
    private int transferBufferSize;
    private int numChannels = 0;
    private ByteOrder endianess = ByteOrder.LITTLE_ENDIAN;
    private boolean enableAutoEndianess = false;
    private DoubleDataBufferContainer doubleDataBufferContainer;
    
    public BasicAudioCodec() {
        inputFormats = new Format[] {
            new AudioFormat(AudioFormat.LINEAR)
        };
    }
    
    public DoubleDataBufferContainer getDoubleDataBufferContainer() {
        return doubleDataBufferContainer;
    }
    
    public void setDoubleDataBufferContainer(DoubleDataBufferContainer doubleDataBufferContainer) {
        this.doubleDataBufferContainer = doubleDataBufferContainer;
    }
    
    public String getName() {
        return "BasicAudioCodec";
    }
    
    public double[][] arrayToMatrix(byte[] buffer){
        double[][] matrix = new double[getNumChannels()][getTransferBufferSize()];
        for (int channel = 0; channel < getNumChannels(); channel++) {
            for (int bufferPosition = 0; bufferPosition < getTransferBufferSize(); bufferPosition++) {
                matrix[channel][bufferPosition] = ((double)getValue(channel,bufferPosition));
            }
        }
        return matrix;
    }
    
    public void matrixToArray(double[][] matrix){
        for (int channel = 0; channel < getNumChannels(); channel++) {
            for (int bufferPosition = 0; bufferPosition < getTransferBufferSize(); bufferPosition++) {
                setValue(channel,bufferPosition,(short)(matrix[channel][bufferPosition]));
            }
        }
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
    
    public void setEndianess(ByteOrder endianess) {
        this.endianess = endianess;
    }
    
    public Format[] getSupportedOutputFormats(Format input) {
        if ( input == null ) {
            return new Format[] {
                new AudioFormat(AudioFormat.LINEAR)
            };
            
        }
        
        if ( input instanceof AudioFormat) {
            AudioFormat af = (AudioFormat) input;
            int ssize = af.getSampleSizeInBits();
            int chnl = af.getChannels();
            int endian = af.getEndian();
            int signed = af.getSigned();
            outputFormats = new Format[] {
                new AudioFormat(AudioFormat.LINEAR,
                        af.getSampleRate(),
                        ssize,
                        chnl,
                        endian,
                        signed)
            };
        } else {
            outputFormats = new Format[0];
        }
        return outputFormats;
    }
    
    public void setNumChannels(int numChannels) {
        this.numChannels = numChannels;
    }
    
    public int getNumChannels() {
        return numChannels;
    }
    
    public int getTransferBufferSize() {
        return transferBufferSize;
    }
    
    public void setTransferBufferSize(int transferBufferSize) {
        this.transferBufferSize = transferBufferSize;
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
    
    public ByteUtility getByteUtility() {
        return byteUtility;
    }
    
    public byte[] getInData(){
        return inData;
    }
    
    public int getInLength(){
        return inLength;
    }
    
    public byte[] getOutData(){
        return outData;
    }
    
    public int getOutLength(){
        return outLength;
    }
    
    public void processInData(){
    }
    
    public void processOutData(){
    }
    
    public synchronized int process(Buffer inputBuffer, Buffer outputBuffer) {
        if (!checkInputBuffer(inputBuffer) ) {
            //System.out.println("return due to  bad input buffer");
            return BUFFER_PROCESSED_FAILED;
        }
        
        if (isEOM(inputBuffer) ) {
            propagateEOM(outputBuffer);
            return BUFFER_PROCESSED_OK;
        }
        
        inData = (byte[])inputBuffer.getData();
        inLength = inputBuffer.getLength();
        
        //configure the byteUtility according to endianess
        
        if(((AudioFormat)inputBuffer.getFormat()).getEndian()==AudioFormat.LITTLE_ENDIAN){
            byteUtility.setEndianess(ByteOrder.LITTLE_ENDIAN);
        }else {
            byteUtility.setEndianess(ByteOrder.BIG_ENDIAN);
        }
        
        if(!enableAutoEndianess){
            getByteUtility().setEndianess(endianess);
        }
        
        processInData();
        
        int inOffset = inputBuffer.getOffset();
        outData = validateByteArraySize(outputBuffer, inLength);
        int outOffset = outputBuffer.getOffset();
        int j = outOffset;
        outLength = inLength;
        
        System.arraycopy(inData, 0, outData, j, outLength);
        
        //configure the byteUtility according to endianess
        if(((AudioFormat)inputBuffer.getFormat()).getEndian()==AudioFormat.LITTLE_ENDIAN){
            byteUtility.setEndianess(ByteOrder.LITTLE_ENDIAN);
        }else {
            byteUtility.setEndianess(ByteOrder.BIG_ENDIAN);
        }
        
        if(!enableAutoEndianess){
            getByteUtility().setEndianess(endianess);
        }
        
        processOutData();
        updateOutput(outputBuffer,inputFormat, outLength, outOffset);
        return BUFFER_PROCESSED_OK;
    }
}