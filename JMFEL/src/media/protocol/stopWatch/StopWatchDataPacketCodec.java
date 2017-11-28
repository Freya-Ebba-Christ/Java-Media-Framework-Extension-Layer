/*
 * StopWatchDataPacketCodec.java
 *
 * Created on 14. Dezember 2007, 18:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.stopWatch;

import examples.motorcortex.eeg_recording.StopwatchPanel;
import java.nio.ByteBuffer;
import media.protocol.pong.SimpleGameDataPacketCodec;

/**
 *
 * @author Administrator
 */
public class StopWatchDataPacketCodec{
    private byte[] datapacket = new byte[16];
    private int currentDirection = 0;
    private long packetID = 0;
    private boolean keyLeft = false;
    private boolean keyRight = false;
    private ByteBuffer eightByteBuffer = ByteBuffer.allocate(8);
    private byte[] byteBufferLong = new byte[8];
    
    public StopWatchDataPacketCodec() {
    }
    
    public void setDatapacket(byte[] datapacket) {
        this.datapacket = datapacket;
    }
    
    public byte[] getDatapacket() {
        return datapacket;
    }
    
    public void decode(){
        
        byte[] eightByteArray = new byte[8];
        byte[] fourByteArray = new byte[4];
        
        //decode the packet id
        eightByteArray[0] = datapacket[0];
        eightByteArray[1] = datapacket[1];
        eightByteArray[2] = datapacket[2];
        eightByteArray[3] = datapacket[3];
        eightByteArray[4] = datapacket[4];
        eightByteArray[5] = datapacket[5];
        eightByteArray[6] = datapacket[6];
        eightByteArray[7] = datapacket[7];
        
        setPacketID(joinEightBytes(eightByteArray));
        
        if((datapacket[8]&255) == 1){
            setKeyLeft(true);
        }else setKeyLeft(false);
        
        if((datapacket[9]&255) == 1){
            setKeyRight(true);
        }else setKeyRight(false);
        
        setCurrentDirection(datapacket[10]&255);
    }
    
    public void encode(){
        
        byte[] eightByteArray = new byte[8];
        
        //encode the packet id
        eightByteArray = splitEightByteValue(getPacketID());
        datapacket[0] = eightByteArray[0];
        datapacket[1] = eightByteArray[1];
        datapacket[2] = eightByteArray[2];
        datapacket[3] = eightByteArray[3];
        datapacket[4] = eightByteArray[4];
        datapacket[5] = eightByteArray[5];
        datapacket[6] = eightByteArray[6];
        datapacket[7] = eightByteArray[7];
        
        //encode the key strokes
        if(keyLeft){
            datapacket[8]=(byte)1;
        }else datapacket[8]=(byte)0;
        
        if(keyRight){
            datapacket[9]=(byte)1;
        }else datapacket[9]=(byte)0;
        
        datapacket[10]=(byte)currentDirection;
        
        //the rest of the 16 bytes are unused and just for padding
    }
    
    public boolean isKeyLeft() {
        return keyLeft;
    }
    
    public boolean isKeyRight() {
        return keyRight;
    }
    
    public void setKeyLeft(boolean keyLeft) {
        this.keyLeft = keyLeft;
    }
    
    public void setKeyRight(boolean keyRight) {
        this.keyRight = keyRight;
    }
    
    public int getCurrentDirection() {
        return currentDirection;
    }
    
    public void setCurrentDirection(int currentDirection) {
        this.currentDirection = currentDirection;
    }
    
    public long getPacketID() {
        return packetID;
    }
    
    public void setPacketID(long packetID) {
        this.packetID = packetID;
    }
    
    private byte[] splitEightByteValue(long aValue){
        eightByteBuffer.putLong((long)aValue);
        eightByteBuffer.rewind();
        byte[] tmpBuffer = eightByteBuffer.array();
        byteBufferLong[0] = tmpBuffer[0];
        byteBufferLong[1] = tmpBuffer[1];
        byteBufferLong[2] = tmpBuffer[2];
        byteBufferLong[3] = tmpBuffer[3];
        byteBufferLong[4] = tmpBuffer[4];
        byteBufferLong[5] = tmpBuffer[5];
        byteBufferLong[6] = tmpBuffer[6];
        byteBufferLong[7] = tmpBuffer[7];
        return byteBufferLong;
    }
    
    private long joinEightBytes(byte[] bytes){
        byteBufferLong[0] = bytes[0];
        byteBufferLong[1] = bytes[1];
        byteBufferLong[2] = bytes[2];
        byteBufferLong[3] = bytes[3];
        byteBufferLong[4] = bytes[4];
        byteBufferLong[5] = bytes[5];
        byteBufferLong[6] = bytes[6];
        byteBufferLong[7] = bytes[7];
        return eightByteBuffer.wrap(byteBufferLong).getLong();
    }
    
    public static void main(String[] args) {
        
        //test the code
        StopWatchDataPacketCodec aStopWatchDataPacketCodec = new StopWatchDataPacketCodec();
        int testValueInt = 123456789;
        long testValueLong = 9223372036000005808L;
        aStopWatchDataPacketCodec.setPacketID(9223372036000005808L);
        aStopWatchDataPacketCodec.setKeyLeft(true);
        aStopWatchDataPacketCodec.setKeyRight(false);
        aStopWatchDataPacketCodec.setCurrentDirection(0);
        aStopWatchDataPacketCodec.encode();
        byte[] array = aStopWatchDataPacketCodec.getDatapacket();
        aStopWatchDataPacketCodec = new StopWatchDataPacketCodec();
        aStopWatchDataPacketCodec.setDatapacket(array);
        aStopWatchDataPacketCodec.decode();
        System.out.println(aStopWatchDataPacketCodec.getPacketID());
        System.out.println(aStopWatchDataPacketCodec.isKeyLeft());
        System.out.println(aStopWatchDataPacketCodec.isKeyRight());
        System.out.println(aStopWatchDataPacketCodec.getCurrentDirection());
    }
}