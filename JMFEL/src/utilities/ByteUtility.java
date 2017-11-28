/*
 * ByteUtility.java
 *
 * Created on 28. Februar 2007, 12:15
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilities;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author christ
 */
public class ByteUtility {
    private byte[] byteBuffer = new byte[2];
    private ByteBuffer buf = ByteBuffer.allocate(2);
    
    /** Creates a new instance of ByteUtility */
    public ByteUtility() {
    }
    
    public ByteOrder getEndianess(){
        return buf.order();
    }
    
    public void setEndianess(ByteOrder endianess){
        buf.order(endianess);
    }
    
    //split a 16bit value into 2 bytes
    public byte[] splitValue(short aValue){
        buf.putShort((short)aValue);
        buf.rewind();
        byte[] tmpBuffer = buf.array();
            byteBuffer[0] = tmpBuffer[1];
            byteBuffer[1] = tmpBuffer[0];
        return byteBuffer;
    }
    
    public short joinBytes(byte[] highAndLowBytes){
            byteBuffer[0] = highAndLowBytes[0];
            byteBuffer[1] = highAndLowBytes[1];
        return buf.wrap(byteBuffer).getShort();
    }
    public static void main(String [] args) {
        ByteUtility aByteUtility = new ByteUtility();
        aByteUtility.setEndianess(ByteOrder.LITTLE_ENDIAN);
        System.out.println(aByteUtility.joinBytes(aByteUtility.splitValue((short)123)));
    }
}