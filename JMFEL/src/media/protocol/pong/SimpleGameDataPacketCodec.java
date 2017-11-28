/*
 * SimpleGameDataPacketCodec.java
 *
 * Created on 1. November 2007, 15:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.pong;

import java.nio.ByteBuffer;

/**
 *
 * @author olaf christ
 *
 *  The values are organized in the byte array as follows:
 *
 *  position:       value:                  length:
 *
 *  bytes 0 to 7:   packetID                64 bit
 *  bytes 8 to 11:  paddlePositionX         32 bit
 *  bytes 12 to 15: paddlePositionY         32 bit
 *  bytes 16 to 19: ballPositionX           32 bit
 *  bytes 20 to 23: ballPositionY           32 bit
 *  byte  24:       keyLeft                  8 bit
 *  byte  25:       keyRight                 8 bit
 *  byte  26:       keyUp                    8 bit
 *  byte  27:       keyDown                  8 bit
 *
 *  Decoding:
 *  use setDatapacket() to provide data packet
 *  use decode() to decode the packet and then use the get() methodes to retrieve the values
 *
 *  Encoding:
 *
 *  use set() methods to set the values to be encoded
 *  use encode() to encode the values. This makes a new data packet
 *  use getDatapacket() to get the new byte array
 */

public class SimpleGameDataPacketCodec {
    private byte[] datapacket = new byte[32];
    private int paddlePositionX = 0;
    private int paddlePositionY = 0;
    private int ballPositionX = 0;
    private int ballPositionY = 0;
    private long packetID = 0;
    
    private boolean keyLeft = false;
    private boolean keyRight = false;
    private boolean keyUp = false;
    private boolean keyDown = false;
    
    private ByteBuffer eightByteBuffer = ByteBuffer.allocate(8);
    private ByteBuffer fourByteBuffer = ByteBuffer.allocate(4);
    private byte[] byteBufferInt = new byte[4];
    private byte[] byteBufferLong = new byte[8];
    
    public SimpleGameDataPacketCodec() {
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
        
        //decode the paddle position x
        fourByteArray[0] = datapacket[8];
        fourByteArray[1] = datapacket[9];
        fourByteArray[2] = datapacket[10];
        fourByteArray[3] = datapacket[11];
        
        setPaddlePositionX(joinFourBytes(fourByteArray));
        
        //decode the paddle position y
        fourByteArray[0] = datapacket[12];
        fourByteArray[1] = datapacket[13];
        fourByteArray[2] = datapacket[14];
        fourByteArray[3] = datapacket[15];
        
        setPaddlePositionY(joinFourBytes(fourByteArray));
        
        //decode the ball position x
        fourByteArray[0] = datapacket[16];
        fourByteArray[1] = datapacket[17];
        fourByteArray[2] = datapacket[18];
        fourByteArray[3] = datapacket[19];
        
        setBallPositionX(joinFourBytes(fourByteArray));
        
        //decode the ball position y
        fourByteArray[0] = datapacket[20];
        fourByteArray[1] = datapacket[21];
        fourByteArray[2] = datapacket[22];
        fourByteArray[3] = datapacket[23];
        
        setBallPositionY(joinFourBytes(fourByteArray));
        
        if((datapacket[24]&255) == 1){
            setKeyLeft(true);
        }else setKeyLeft(false);
        
        if((datapacket[25]&255) == 1){
            setKeyRight(true);
        }else setKeyRight(false);
        
        if((datapacket[26]&255) == 1){
            setKeyUp(true);
        }else setKeyUp(false);
        
        if((datapacket[27]&255) == 1){
            setKeyDown(true);
        }else setKeyDown(false);
    }
    
    public void encode(){
        
        byte[] eightByteArray = new byte[8];
        byte[] fourByteArray = new byte[4];
        
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
        
        //encode the paddle position x
        fourByteArray = splitFourByteValue(getPaddlePositionX());
        datapacket[8] = fourByteArray[0];
        datapacket[9] = fourByteArray[1];
        datapacket[10] = fourByteArray[2];
        datapacket[11] = fourByteArray[3];
        
        //encode the paddle position y
        fourByteArray = splitFourByteValue(getPaddlePositionY());
        datapacket[12] = fourByteArray[0];
        datapacket[13] = fourByteArray[1];
        datapacket[14] = fourByteArray[2];
        datapacket[15] = fourByteArray[3];
        
        //encode the ball position x
        fourByteArray = splitFourByteValue(getBallPositionX());
        datapacket[16] = fourByteArray[0];
        datapacket[17] = fourByteArray[1];
        datapacket[18] = fourByteArray[2];
        datapacket[19] = fourByteArray[3];
        
        //encode the ball position y
        fourByteArray = splitFourByteValue(getBallPositionY());
        datapacket[20] = fourByteArray[0];
        datapacket[21] = fourByteArray[1];
        datapacket[22] = fourByteArray[2];
        datapacket[23] = fourByteArray[3];
        
        //encode the key strokes
        if(keyLeft){
            datapacket[24]=(byte)1;
        }else datapacket[24]=(byte)0;
        
        if(keyRight){
            datapacket[25]=(byte)1;
        }else datapacket[25]=(byte)0;
        
        if(keyUp){
            datapacket[26]=(byte)1;
        }else datapacket[26]=(byte)0;
        
        if(keyDown){
            datapacket[27]=(byte)1;
        }else datapacket[27]=(byte)0;
        
        //the rest of the 32 bytes are unused and just for padding
    }
    
    public int getBallPositionX() {
        return ballPositionX;
    }
    
    public int getBallPositionY() {
        return ballPositionY;
    }
    
    public boolean isKeyDown() {
        return keyDown;
    }
    
    public boolean isKeyLeft() {
        return keyLeft;
    }
    
    public boolean isKeyRight() {
        return keyRight;
    }
    
    public boolean isKeyUp() {
        return keyUp;
    }
    
    public void setKeyDown(boolean keyDown) {
        this.keyDown = keyDown;
    }
    
    public void setKeyLeft(boolean keyLeft) {
        this.keyLeft = keyLeft;
    }
    
    public void setKeyRight(boolean keyRight) {
        this.keyRight = keyRight;
    }
    
    public void setKeyUp(boolean keyUp) {
        this.keyUp = keyUp;
    }
    
    public long getPacketID() {
        return packetID;
    }
    
    public int getPaddlePositionX() {
        return paddlePositionX;
    }
    
    public void setBallPositionX(int ballPositionX) {
        this.ballPositionX = ballPositionX;
    }
    
    public int getPaddlePositionY() {
        return paddlePositionY;
    }
    
    public void setBallPositionY(int ballPositionY) {
        this.ballPositionY = ballPositionY;
    }
    
    public void setPacketID(long packetID) {
        this.packetID = packetID;
    }
    
    public void setPaddlePositionX(int paddlePositionX) {
        this.paddlePositionX = paddlePositionX;
    }
    
    public void setPaddlePositionY(int paddlePositionY) {
        this.paddlePositionY = paddlePositionY;
    }
    
    private byte[] splitFourByteValue(int aValue){
        fourByteBuffer.putInt((int)aValue);
        fourByteBuffer.rewind();
        byte[] tmpBuffer = fourByteBuffer.array();
        byteBufferInt[0] = tmpBuffer[0];
        byteBufferInt[1] = tmpBuffer[1];
        byteBufferInt[2] = tmpBuffer[2];
        byteBufferInt[3] = tmpBuffer[3];
        return byteBufferInt;
    }
    
    private int joinFourBytes(byte[] bytes){
        byteBufferInt[0] = bytes[0];
        byteBufferInt[1] = bytes[1];
        byteBufferInt[2] = bytes[2];
        byteBufferInt[3] = bytes[3];
        return fourByteBuffer.wrap(byteBufferInt).getInt();
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
        SimpleGameDataPacketCodec aSimpleGameDataPacketCodec = new SimpleGameDataPacketCodec();
        int testValueInt = 123456789;
        long testValueLong = 9223372036000005808L;
        
        aSimpleGameDataPacketCodec.setPacketID(9223372036000005808L);
        aSimpleGameDataPacketCodec.setPaddlePositionX(640);
        aSimpleGameDataPacketCodec.setPaddlePositionY(480);
        aSimpleGameDataPacketCodec.setBallPositionX(1024);
        aSimpleGameDataPacketCodec.setBallPositionY(768);
        aSimpleGameDataPacketCodec.setKeyLeft(false);
        aSimpleGameDataPacketCodec.setKeyRight(false);
        aSimpleGameDataPacketCodec.setKeyUp(false);
        aSimpleGameDataPacketCodec.setKeyDown(false);
        aSimpleGameDataPacketCodec.encode();
        
        byte[] array = aSimpleGameDataPacketCodec.getDatapacket();
        aSimpleGameDataPacketCodec = new SimpleGameDataPacketCodec();
        aSimpleGameDataPacketCodec.setDatapacket(array);
        aSimpleGameDataPacketCodec.decode();
        System.out.println(aSimpleGameDataPacketCodec.getPacketID());
        System.out.println(aSimpleGameDataPacketCodec.getPaddlePositionX());
        System.out.println(aSimpleGameDataPacketCodec.getPaddlePositionY());
        System.out.println(aSimpleGameDataPacketCodec.getBallPositionX());
        System.out.println(aSimpleGameDataPacketCodec.getBallPositionY());
        System.out.println(aSimpleGameDataPacketCodec.isKeyLeft());
        System.out.println(aSimpleGameDataPacketCodec.isKeyRight());
        System.out.println(aSimpleGameDataPacketCodec.isKeyUp());
        System.out.println(aSimpleGameDataPacketCodec.isKeyDown());
    }
}