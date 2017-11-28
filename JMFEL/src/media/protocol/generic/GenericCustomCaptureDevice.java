/*
 * GenericCustomCaptureDevice.java
 *
 * Created on 5. Oktober 2007, 11:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.generic;

import javax.media.format.AudioFormat;

/**
 *
 * @author Urkman_2
 */
public abstract class GenericCustomCaptureDevice {
    private boolean initialized = false;
    private int samplerate;
    private int bitsPerSample;
    private int numChannels;
    private int maxDataLength;
    private byte[] transferBuffer;
    private AudioFormat format;
    
    public GenericCustomCaptureDevice(){
    }
    
    public boolean isInitialized() {
        return initialized;
    }
    
    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
    
    public void init(){
    }
    
    public int getBitsPerSample() {
        return bitsPerSample;
    }
    
    public void setBitsPerSample(int bitsPerSample) {
        this.bitsPerSample = bitsPerSample;
    }
    
    public int getNumChannels() {
        return numChannels;
    }
    
    public void setNumChannels(int numChannels) {
        this.numChannels = numChannels;
    }
    
    public void setSamplerate(int samplerate) {
        this.samplerate = samplerate;
    }
    
    public int getSamplerate(){
        
        return samplerate;
    }
    
    public byte[] getTransferBuffer() {
        return transferBuffer;
    }
    
    public void setTransferBuffer(byte[] transferBuffer) {
        this.transferBuffer = transferBuffer;
    }
    
    public byte[] getAvailableData(){
        return getTransferBuffer();
    }
    
    public void setMaxDataLength(int maxDataLength) {
        this.maxDataLength = maxDataLength;
    }
    
    public int getMaxDataLength() {
        return maxDataLength;
    }
    
    public void setFormat(AudioFormat format) {
        this.format = format;
    }
    
    public AudioFormat getFormat(){
        return format;
    }
}