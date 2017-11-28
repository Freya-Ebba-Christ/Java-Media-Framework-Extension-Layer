/*
 * This class extends custom_renderer.BasicAudioRenderer to get access to eyetracking signals.
 * It uses the eye.insight.InsightDatagramPacketDecoder to decode the eye opeing of the left and the right eye. 
 */

package examples.video_and_eyetracking;

import custom_renderer.BasicAudioRenderer;
import eye.insight.InsightDatagramPacketDecoder;
import utilities.DoubleDataBufferContainer;

/**
 *
 * @author Administrator
 */

public class EyeTrackerRenderer extends BasicAudioRenderer{
    private InsightDatagramPacketDecoder datagramdecoder;
    private DoubleDataBufferContainer doubleDataBufferContainer;
    private int samplerate = 120;
    
    /** Creates a new instance of EyeTrackerRenderer */
    public EyeTrackerRenderer() {
        setDatagramdecoder(new InsightDatagramPacketDecoder());
        initBuffers();
    }
    
    public InsightDatagramPacketDecoder getDatagramdecoder() {
        return datagramdecoder;
    }
    
    public void setDatagramdecoder(InsightDatagramPacketDecoder datagramdecoder) {
        this.datagramdecoder = datagramdecoder;
    }
    
    private void initBuffers(){
        //init the DataBufferContainer
        setDoubleDataBufferContainer(new DoubleDataBufferContainer());
        getDoubleDataBufferContainer().initializeBuffers(8*samplerate,2);
        getDoubleDataBufferContainer().getDataBuffer(0).setDistance(4);
        getDoubleDataBufferContainer().getDataBuffer(1).setDistance(4);
        System.out.println(getDoubleDataBufferContainer());
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