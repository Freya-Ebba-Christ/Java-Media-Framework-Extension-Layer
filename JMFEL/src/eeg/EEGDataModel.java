/*
 * EEGSubbandData.java
 *
 * Created on 18. September 2007, 16:50
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
public class EEGDataModel extends Observable implements Callable{
    
    private EEGSignalProcessor eegSignalProcessor;
    private DoubleDataBufferContainer aDoubleDataBufferContainer;
    private int channel = 0;
    private double[][] alphaSignal;
    private double[][] betaSignal;
    private double[][] gammaSignal;
    private double[][] deltaSignal;
    private double[][] thetaSignal;
    private double[][] signal;
    private double[] alphaPower;
    private double[] betaPower;
    private double[] gammaPower;
    private double[] deltaPower;
    private double[] thetaPower;
    private double[] alphaEnergy;
    private double[] betaEnergy;
    private double[] gammaEnergy;
    private double[] deltaEnergy;
    private double[] thetaEnergy;
    private double[] signalEnergy;
    private double[] signalPower;
    
    private int numChannels = 0;
    
    /**
     * Creates a new instance of EEGDataModel
     */
    public EEGDataModel() {
    }
    
    public void init(){
        for (int i = 0; i < getNumChannels(); i++) {
            alphaSignal = new double[aDoubleDataBufferContainer.getBufferLength()][i];
            betaSignal = new double[aDoubleDataBufferContainer.getBufferLength()][i];
            gammaSignal = new double[aDoubleDataBufferContainer.getBufferLength()][i];
            deltaSignal = new double[aDoubleDataBufferContainer.getBufferLength()][i];
            thetaSignal = new double[aDoubleDataBufferContainer.getBufferLength()][i];
            signal = new double[aDoubleDataBufferContainer.getBufferLength()][i];
        }
        alphaPower = new double[getNumChannels()];
        betaPower = new double[getNumChannels()];
        gammaPower = new double[getNumChannels()];
        deltaPower = new double[getNumChannels()];
        thetaPower = new double[getNumChannels()];
        
        alphaEnergy = new double[getNumChannels()];
        betaEnergy = new double[getNumChannels()];
        gammaEnergy = new double[getNumChannels()];
        deltaEnergy = new double[getNumChannels()];
        thetaEnergy = new double[getNumChannels()];
        signalEnergy = new double[getNumChannels()];
        signalPower = new double[getNumChannels()];        
    }
    
    public double[] getAlphaSignal(int channel) {
        return alphaSignal[channel];
    }
    
    public double[] getBetaSignal(int channel) {
        return betaSignal[channel];
    }
    
    public double[] getGammaSignal(int channel) {
        return gammaSignal[channel];
    }
    public double[] getDeltaSignal(int channel) {
        return deltaSignal[channel];
    }
    public double[] getThetaSignal(int channel) {
        return thetaSignal[channel];
    }
    
    public double getAlphaPower(int channel) {
        return alphaPower[channel];
    }
    
    public double getBetaPower(int channel) {
        return betaPower[channel];
    }
    
    public double getDeltaPower(int channel) {
        return deltaPower[channel];
    }
    
    public double getGammaPower(int channel) {
        return gammaPower[channel];
    }
    
    public double getThetaPower(int channel) {
        return thetaPower[channel];
    }
    
    public void setAlphaPower(int channel, double value) {
        alphaPower[channel] = value;
    }
    
    public void setBetaPower(int channel, double value) {
        betaPower[channel] = value;
    }
    
    public void setDeltaPower(int channel, double value) {
        deltaPower[channel] = value;
    }
    
    public void setGammaPower(int channel, double value) {
        gammaPower[channel] = value;
    }
    
    public void setThetaPower(int channel, double value) {
        thetaPower[channel] = value;
    }
    
    public double getAlphaEnergy(int channel) {
        return alphaEnergy[channel];
    }
    
    public double getBetaEnergy(int channel) {
        return betaEnergy[channel];
    }
    
    public double getDeltaEnergy(int channel) {
        return deltaEnergy[channel];
    }
    
    public double getGammaEnergy(int channel) {
        return gammaEnergy[channel];
    }
    
    public double getThetaEnergy(int channel) {
        return thetaEnergy[channel];
    }
    
    public void setAlphaEnergy(int channel, double value) {
        alphaEnergy[channel] = value;
    }
    
    public void setBetaEnergy(int channel, double value) {
        betaEnergy[channel] = value;
    }
    
    public void setDeltaEnergy(int channel, double value) {
        deltaEnergy[channel] = value;
    }
    
    public void setGammaEnergy(int channel, double value) {
        gammaEnergy[channel] = value;
    }
    
    public void setThetaEnergy(int channel, double value) {
        thetaEnergy[channel] = value;
    }

    public double getSignalEnergy(int channel) {
        return signalEnergy[channel];
    }

    public double getSignalPower(int channel) {
        return signalPower[channel];
    }

    public void setSignalEnergy(int channel, double value) {
        signalEnergy[channel] = value;
    }

    public void setSignalPower(int channel, double value) {
        signalPower[channel] = value;
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

        alphaSignal[channel] = getEEGSignalProcessor().getAlphaSignal(getSignal(channel));
        betaSignal[channel] = getEEGSignalProcessor().getBetaSignal(getSignal(channel));
        gammaSignal[channel] = getEEGSignalProcessor().getGammaSignal(getSignal(channel));
        deltaSignal[channel] = getEEGSignalProcessor().getDeltaSignal(getSignal(channel));
        thetaSignal[channel] = getEEGSignalProcessor().getThetaSignal(getSignal(channel));
        
        setAlphaEnergy(channel,getEEGSignalProcessor().getSignalEnergy(getAlphaSignal(channel)));
        setBetaEnergy(channel,getEEGSignalProcessor().getSignalEnergy(getBetaSignal(channel)));
        setGammaEnergy(channel,getEEGSignalProcessor().getSignalEnergy(getGammaSignal(channel)));
        setDeltaEnergy(channel,getEEGSignalProcessor().getSignalEnergy(getDeltaSignal(channel)));
        setThetaEnergy(channel,getEEGSignalProcessor().getSignalEnergy(getThetaSignal(channel)));
        
        setAlphaPower(channel,getEEGSignalProcessor().getSignalPower(getAlphaSignal(channel)));
        setBetaPower(channel,getEEGSignalProcessor().getSignalPower(getBetaSignal(channel)));
        setGammaPower(channel,getEEGSignalProcessor().getSignalPower(getGammaSignal(channel)));
        setDeltaPower(channel,getEEGSignalProcessor().getSignalPower(getDeltaSignal(channel)));
        setThetaPower(channel,getEEGSignalProcessor().getSignalPower(getThetaSignal(channel)));
        
        setSignalEnergy(channel,getEEGSignalProcessor().getSignalEnergy(getSignal(channel)));
        setSignalPower(channel,getEEGSignalProcessor().getSignalPower(getSignal(channel)));
        
        //if all the signal processing is done...notify observers...
        if((getChannel()+1)==getNumChannels()){
            setChanged();
            notifyObservers();
        }
    }
    
    public void performAction() {
    }
}