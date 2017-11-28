/*
 * FilterSpec.java
 *
 * Created on 6. Juli 2007, 19:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.gtec;

/**
 *
 * @author Administrator
 */
public class FilterSpec {
    
    private float lowFreqency = 0.0f;
    private float highFreqency = 0.0f;
    private float samplingrate = 0.0f;
    private float order = 0.0f;
    private float type = 0.0f;
    private int index = -1;
    
    /** Creates a new instance of FilterSpec */
    public FilterSpec() {
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    public float getHighFreqency() {
        return highFreqency;
    }
    
    public float getLowFreqency() {
        return lowFreqency;
    }
    
    public float getOrder() {
        return order;
    }
    
    public float getSamplingrate() {
        return samplingrate;
    }
    
    public float getType() {
        return type;
    }
    
    public void setHighFreqency(float highFreqency) {
        this.highFreqency = highFreqency;
    }
    
    public void setLowFreqency(float lowFreqency) {
        this.lowFreqency = lowFreqency;
    }
    
    public void setOrder(float order) {
        this.order = order;
    }
    
    public void setSamplingrate(float samplingrate) {
        this.samplingrate = samplingrate;
    }
    
    public void setType(float type) {
        this.type = type;
    }
    
    public String filterTypeToString(float type){
        if(type == Amplifier.F_BESSEL){
            return "BESSEL";
        }else if(type == Amplifier.F_BUTTERWORTH){
            return "BUTTERWORTH";
        }if(type == Amplifier.F_CHEBYSHEV){
            return "CHEBYSHEV";
        }
        return "UNKNOWN";
    }
    
    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("HF:");
        buffer.append(getHighFreqency());
        buffer.append(" LF:");
        buffer.append(getLowFreqency());
        buffer.append(" TYPE:");
        buffer.append(filterTypeToString(getType()));
        buffer.append(" ORDER:");
        buffer.append(getOrder());
        buffer.append(" SR:");
        buffer.append(getSamplingrate());
        return buffer.toString();
    }
}
