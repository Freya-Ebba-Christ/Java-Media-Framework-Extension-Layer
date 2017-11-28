/*
 * EyeTrackerDataProvider.java
 *
 * Created on 6. Juni 2007, 14:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package examples.video_and_eyetracking;
import custom_renderer.BasicAudioRenderer;
import eye.insight.InsightDatagramPacketDecoder;

/**
 *
 * @author Administrator
 */
public class EyeTrackerDataProvider extends BasicAudioRenderer{
    
    private InsightDatagramPacketDecoder datagramDecoder;
    
    /** Creates a new instance of EyeTrackerDataProvider */
    public EyeTrackerDataProvider() {
        setDatagramdecoder(new InsightDatagramPacketDecoder());
    }

    public InsightDatagramPacketDecoder getDatagramdecoder() {
        return datagramDecoder;
    }

    public void setDatagramdecoder(InsightDatagramPacketDecoder datagramDecoder) {
        this.datagramDecoder = datagramDecoder;
    }
    
    public void processInData(){
        getDatagramdecoder().setBuffer(getInData());
    }
}