/*
 * MotorCortexEEGDataModel.java
 *
 * Created on 3. Dezember 2007, 15:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package eeg;

import java.util.Observable;
import utilities.Callable;
import utilities.DoubleDataBufferContainer;
import utilities.signal_processing.EEGSignalProcessor;

/**
 *
 * @author Administrator
 */
public class BasicEEGDataModel extends Observable implements Callable{
    
    private EEGSignalProcessor eegSignalProcessor;
    private DoubleDataBufferContainer aDoubleDataBufferContainer;
    private int channel = 0;
    private double[][] signal;
    private int numChannels = 0;

    public BasicEEGDataModel() {
    }
    
    public void init(){
        for (int i = 0; i < getNumChannels(); i++) {
            signal = new double[aDoubleDataBufferContainer.getBufferLength()][i];
        }
    }
    
    public int getNumChannels() {
        return numChannels;
    }
    
    public void setNumChannels(int numChannels) {
        this.numChannels = numChannels;
    }
    
    public DoubleDataBufferContainer getDoubleDataBufferContainer() {
        return aDoubleDataBufferContainer;
    }
    
    public void setDoubleDataBufferContainer(DoubleDataBufferContainer aDoubleDataBufferContainer) {
        this.aDoubleDataBufferContainer = aDoubleDataBufferContainer;
    }
    
    private EEGSignalProcessor getEEGSignalProcessor() {
        return eegSignalProcessor;
    }
    
    public void setEEGSignalProcessor(EEGSignalProcessor eegSignalProcessor) {
        this.eegSignalProcessor = eegSignalProcessor;
    }
    
    public double[] getSignal(int channel) {
        return signal[channel];
    }
    
    public int getChannel() {
        return channel;
    }
    
    public void performAction(int id) {
        channel = id;
        signal[channel] = new double[getDoubleDataBufferContainer().getDataBuffer(id).toArray().length];
        System.arraycopy(getDoubleDataBufferContainer().getDataBuffer(id).toArray(),0, getSignal(channel),0,getDoubleDataBufferContainer().getDataBuffer(id).toArray().length);
        
        //if all the signal processing is done...notify observers...
        if((getChannel()+1)==getNumChannels()){
            setChanged();
            notifyObservers();
        }
    }
    
    public void performAction() {
    }
}