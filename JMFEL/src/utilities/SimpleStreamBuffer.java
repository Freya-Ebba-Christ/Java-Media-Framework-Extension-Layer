/*
 * SimpleRingBuffer.java
 */

package utilities;

import java.nio.ByteBuffer;

/**
 *
 * @author christ
 */
public class SimpleStreamBuffer {
    
    private byte[][] dataBuffer;
    private int[] headPosition = new int[2];
    private int currentBufferIndex = 1;
    private int cnt = 0;
    private byte[] byteBuffer = new byte[2];
    private ByteBuffer buf = ByteBuffer.allocate(2);
    private int bufferSize = 0;
    
    
    /** Creates a new instance of SimpleRingBuffer */
    public SimpleStreamBuffer() {
    }
    
    private byte[][] getDataBuffer() {
        return dataBuffer;
    }
    
    public void setHeadPosition(int position) {
        headPosition[currentBufferIndex] = position;
    }
    
    public int getHeadPosition() {
        return headPosition[currentBufferIndex];
    }
    
    public void addValue(short aValue){
        byteBuffer = splitValue(aValue);
        if (getHeadPosition()==bufferSize){
            switchBuffer();
        }        
        getDataBuffer()[currentBufferIndex][getHeadPosition()]=byteBuffer[0];
        //System.out.println("currentBuffer "+currentBufferIndex+" headPosition "+getHeadPosition());
        setHeadPosition(getHeadPosition()+1);
        getDataBuffer()[currentBufferIndex][getHeadPosition()]=byteBuffer[1];
        //System.out.println("currentBuffer "+currentBufferIndex+" headPosition "+getHeadPosition());
        setHeadPosition(getHeadPosition()+1);

    }
    
    public void setCurrentBufferIndex(int currentBufferIndex) {
        this.currentBufferIndex = currentBufferIndex;
    }
    
    public int getCurrentBufferIndex() {
        return currentBufferIndex;
    }
    
    public byte[] getData(){
        byte[] bufferToReturn = new byte[getHeadPosition()];
        System.arraycopy(getDataBuffer()[getCurrentBufferIndex()],0,bufferToReturn,0,getHeadPosition());
        switchBuffer();
        return bufferToReturn;
    }
    
    private void switchBuffer(){
        setHeadPosition(0);
        setCurrentBufferIndex(cnt%=2);
        cnt++;
    }
    
    public void init(int sizeInBytes){
        dataBuffer = new byte[2][sizeInBytes];
        bufferSize = sizeInBytes;
    }
    
    public byte[] splitValue(short aValue){
        buf.putShort((short)aValue);
        buf.rewind();
        return buf.array();
    }
    
    public static void main(String [] args) {
        SimpleStreamBuffer aNonBlockingRingBuffer = new SimpleStreamBuffer();
        aNonBlockingRingBuffer.init(2048);
        for(int i=0;i<2048;i++){
            aNonBlockingRingBuffer.addValue((short)10);
        }
    }
}