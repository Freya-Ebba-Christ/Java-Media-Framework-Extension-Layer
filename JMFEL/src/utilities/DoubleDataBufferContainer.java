/*
 * DoubleDataBufferContainer.java
 *
 * Created on 23. April 2007, 15:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilities;

/**
 *
 * @author christ
 */
public class DoubleDataBufferContainer {
    
    private DoubleDataBuffer[] dataBuffers;
    private int numDataBuffers=0;
    private int refreshRate = 30;
    private int bufferLength = 0;
    
    public DoubleDataBufferContainer() {
    }
    
    public int getNumDataBuffers() {
        return numDataBuffers;
    }
    
    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }
    
    public int getRefreshRate() {
        return refreshRate;
    }
    
    public int getBufferLength(){
        return bufferLength;
    }
    
    public DoubleDataBuffer getDataBuffer(int index){
        return dataBuffers[index];
    }
    
    public void initializeBuffers(int buffersize, int numBuffers){
        numDataBuffers = numBuffers;
        bufferLength = buffersize;
        dataBuffers = new DoubleDataBuffer[numBuffers];
        for (int i = 0; i < numDataBuffers; i++) {
            dataBuffers[i] = new DoubleDataBuffer(buffersize);
            dataBuffers[i].setRefreshRate(getRefreshRate());
            dataBuffers[i].setID(i);
        }
    }
}