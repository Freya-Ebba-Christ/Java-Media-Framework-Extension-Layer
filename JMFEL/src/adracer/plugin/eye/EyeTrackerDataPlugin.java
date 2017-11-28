/*
 * dataPlugin.java
 *
 * Created on 28. August 2007, 16:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package adracer.plugin.eye;

import custom_renderer.BasicAudioRenderer;
import eye.insight.InsightDatagramPacketDecoder;
import utilities.DoubleDataBufferContainer;

/**
 *
 * @author Administrator
 */
public class EyeTrackerDataPlugin extends BasicAudioRenderer{
    private InsightDatagramPacketDecoder datagramdecoder;
    private DoubleDataBufferContainer doubleDataBufferContainer;
    private int samplerate = 60;
    
    /** Creates a new instance of EyeTrackerRenderer */
    public EyeTrackerDataPlugin() {
        setDatagramdecoder(new InsightDatagramPacketDecoder());
    }
    
    public InsightDatagramPacketDecoder getDatagramdecoder() {
        return datagramdecoder;
    }
    
    public void setDatagramdecoder(InsightDatagramPacketDecoder datagramdecoder) {
        this.datagramdecoder = datagramdecoder;
    }

    public DoubleDataBufferContainer getDoubleDataBufferContainer() {
        return doubleDataBufferContainer;
    }

    public int getSamplerate() {
        return samplerate;
    }

    public void setDoubleDataBufferContainer(DoubleDataBufferContainer doubleDataBufferContainer) {
        this.doubleDataBufferContainer = doubleDataBufferContainer;
    }

    public void setSamplerate(int samplerate) {
        this.samplerate = samplerate;
    }
    
    public void initBuffers(){
        //init the DataBufferContainer
        setDoubleDataBufferContainer(new DoubleDataBufferContainer());
        getDoubleDataBufferContainer().initializeBuffers(4*getSamplerate(),2);
        getDoubleDataBufferContainer().getDataBuffer(0).setDistance(1);
        getDoubleDataBufferContainer().getDataBuffer(1).setDistance(1);
    }
    
    public void processInData() {
        getDatagramdecoder().setBuffer(getInData());
        float eyeOpeningLeft = getDatagramdecoder().getEyeOpeningLeft();
        float eyeOpeningRight = getDatagramdecoder().getEyeOpeningRight();
        if(eyeOpeningLeft>20.0f||eyeOpeningRight>20.0f){
            eyeOpeningRight = 0f;
            eyeOpeningLeft = 0f;
            getDoubleDataBufferContainer().getDataBuffer(0).addElement(eyeOpeningLeft);
            getDoubleDataBufferContainer().getDataBuffer(1).addElement(eyeOpeningRight);
        }else{
            getDoubleDataBufferContainer().getDataBuffer(0).addElement(eyeOpeningLeft-10.0);
            getDoubleDataBufferContainer().getDataBuffer(1).addElement(eyeOpeningRight-10.0);
        }
    }
}