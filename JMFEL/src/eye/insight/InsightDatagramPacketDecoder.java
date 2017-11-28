/*
 * InsightDataGramPacketDecoder.java
 *
 * Created on 15. Mai 2007, 16:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package eye.insight;
import java.nio.ByteBuffer;

public class InsightDatagramPacketDecoder{
    /**
     * Class to handle UDP-datagram-buffers sent by the SMI inSight eye-tracker
     * This class was written based on information provided by SMI
     * (c) 2002-2004 Sensomotoric Instruments GmbH
     *
     * Use this class as follows:
     * Use setBuffer(datagramPacket.getData()) to set the bytebuffer
     * Each time you set a new buffer, setFixedPointMultiplier() is called
     * to compute the fixedpoint multiplier used to convert to
     * floating point values.
     * Note: for simplicity all values are in double format.
     *
     * Insight UDP format (taken from the Insight documentation):
     *
     * Name                                         type                          range
     *
     * Packet Header
     * ----------------------------------------------------------------------------------
     * MagicNumber                                  Int64                         0,7
     * PacketId                                     Int64                         8,15
     * FixedPointMultiplier                         Int32                         16,19
     * Padding                                      Int8                          20,79
     * ----------------------------------------------------------------------------------
     *
     * Eye opening measurement (~120Hz, ~2.5s delayed)
     * ----------------------------------------------------------------------------------
     * TimeStampEyeOpening                          Float64                       80,87
     * EyeOpeningLeft                               Float32                       88,91
     * EyeOpeningRight                              Float32                       92,95
     * EyeLeftConfidence                            Float32                       96,99
     * EyeRightConfidence                           Float32                       100,103
     * Padding                                      Int8                          104,167
     * ----------------------------------------------------------------------------------
     *
     * Fatigue measurement (~120Hz, ~2.5s delayed)
     * ----------------------------------------------------------------------------------
     * TimeStampFatigue                             Float64                       168,175
     * FatiguePerclos70                             Float32                       176,179
     * FatiguePerclos80                             Float32                       180,183
     * FatiguePerclosEMEA70                         Float32                       184,187
     * FatiguePerclosEMEA80                         Float32                       188,191
     * Padding                                      Int8                          192,267
     * ----------------------------------------------------------------------------------
     *
     * Pupil measurement (~120Hz)
     * ----------------------------------------------------------------------------------
     * TimeStampPupil                               Float64                       268,275
     * PupilPositionLeftX                           Float32                       276,279
     * PupilPositionLeftY                           Float32                       280,283
     * PupilPositionRightX                          Float32                       284,287
     * PupilPositionRightY                          Float32                       288,291
     * PupilAreaLeft                                Float32                       292,295
     * PupilAreaRight                               Float32                       296,299
     * PupilDiameterLeft                            Float32                       300,303
     * PupilDiameterRight                           Float32                       304,307
     * Padding                                      Int8                          308,371
     * ----------------------------------------------------------------------------------
     *
     * System status (~120Hz)
     * ----------------------------------------------------------------------------------
     * TimeStampStatus                              Float64                       372,379
     * SystemStatus                                 Int32                         380,383
     * SystemStatusConfidence                       Float32                       384,387
     * Padding                                      Int8                          388,451
     * ----------------------------------------------------------------------------------
     *
     * Head-pose measurement (~60Hz)
     * ----------------------------------------------------------------------------------
     * TimeStampHeadPose                            Float64                       452,459
     * HeadX                                        Float32                       460,463
     * HeadY                                        Float32                       464,467
     * HeadZ                                        Float32                       468,471
     * HeadPitch                                    Float32                       472,475
     * HeadYaw                                      Float32                       476,479
     * HeadRoll                                     Float32                       480,483
     * Padding                                      Float32                       484,547
     * ----------------------------------------------------------------------------------
     *
     * Gaze measurement (~60Hz)
     * ----------------------------------------------------------------------------------
     * TimeStampGaze                                Float64                       548,555
     * LeftEyeX                                     Float32                       556,559
     * LeftEyeY                                     Float32                       560.563
     * LeftEyeZ                                     Float32                       564,567
     * RightEyeX                                    Float32                       568,571
     * RightEyeY                                    Float32                       572,575
     * RightEyeZ                                    Float32                       576,579
     * LeftGazeFocalPlaneIntersectionX              Float32                       580,583
     * LeftGazeFocalPlaneIntersectionY              Float32                       584,587
     * LeftGazeFocalPlaneIntersectionZ              Float32                       588,591
     * RightGazeFocalPlaneIntersectionX             Float32                       592,595
     * RightGazeFocalPlaneIntersectionY             Float32                       596,599
     * RightGazeFocalPlaneIntersectionZ             Float33                       600,603
     * CombinedGazeVector OriginX                   Float32                       604,607
     * CombinedGazeVector OriginY                   Float32                       608,611
     * CombinedGazeVector OriginZ                   Float32                       612,615
     * CombinedGazeVector FocalPlaneIntersectionX   Float32                       616,619
     * CombinedGazeVector FocalPlaneIntersectionY   Float32                       620,623
     * CombinedGazeVector FocalPlaneIntersectionZ   Float32                       624,627
     * HitAOI                                       Int32                         628,631
     * Padding                                      Int8                          632,707
     * Padding                                      Int8                          708,1023
     */
    
    /**
     *
     * @author Olaf Christ
     */

    private byte[] buffer;
    private float fixedPointMultiplier = 1.0f;
    private ByteBuffer eightByteBuffer = ByteBuffer.allocate(8);
    private ByteBuffer fourByteBuffer = ByteBuffer.allocate(4);
    
    public InsightDatagramPacketDecoder() {
    }
    
    public float getFixedPointMultiplier() {
        return fixedPointMultiplier;
    }
    
    public void setBuffer(byte[] aBuffer){
        buffer = aBuffer;
        setFixedPointMultiplier();
    }
    
    public long getMagicNumber(){
        return eightByteBuffer.wrap(buffer,0,8).getLong();
    }
    
    public long getPacketId(){
        return eightByteBuffer.wrap(buffer,8,8).getLong();
    }
    
    private void setFixedPointMultiplier(){
        fixedPointMultiplier = (float)fourByteBuffer.wrap(buffer,16,4).getInt();
    }
    
    public float getTimeStampEyeOpening(){
        return eightByteBuffer.wrap(buffer,80,8).getLong()/fixedPointMultiplier;
    }
    
    public float getEyeOpeningLeft(){
        return fourByteBuffer.wrap(buffer,88,4).getInt()/fixedPointMultiplier;
    }
    
    public float getEyeOpeningRight(){
        return fourByteBuffer.wrap(buffer,92,4).getInt()/fixedPointMultiplier;
    }
    
    public float getEyeLeftConfidence(){
        return fourByteBuffer.wrap(buffer,96,4).getInt()/fixedPointMultiplier;
    }
    
    public float getEyeRightConfidence(){
        return fourByteBuffer.wrap(buffer,100,4).getInt()/fixedPointMultiplier;
    }
    
    public float getTimeStampFatigue(){
        return eightByteBuffer.wrap(buffer,168,8).getLong()/fixedPointMultiplier;
    }
    
    public float getFatiguePerclos70(){
        return fourByteBuffer.wrap(buffer,176,4).getInt()/fixedPointMultiplier;
    }
    
    public float getFatiguePerclos80(){
        return fourByteBuffer.wrap(buffer,180,4).getInt()/fixedPointMultiplier;
    }
    
    public float getFatiguePerclosEMEA70(){
        return fourByteBuffer.wrap(buffer,184,4).getInt()/fixedPointMultiplier;
    }
    
    public float getFatiguePerclosEMEA80(){
        return fourByteBuffer.wrap(buffer,188,4).getInt()/fixedPointMultiplier;
    }
    
    public float getTimeStampPupil(){
        return eightByteBuffer.wrap(buffer,268,8).getLong()/fixedPointMultiplier;
    }
    
    public float getPupilPositionLeftX(){
        return fourByteBuffer.wrap(buffer,276,4).getInt()/fixedPointMultiplier;
    }
    
    public float getPupilPositionLeftY(){
        return fourByteBuffer.wrap(buffer,280,4).getInt()/fixedPointMultiplier;
    }
    
    public float getPupilPositionRightX(){
        return fourByteBuffer.wrap(buffer,284,4).getInt()/fixedPointMultiplier;
    }
    
    public float getPupilPositionRightY(){
        return fourByteBuffer.wrap(buffer,288,4).getInt()/fixedPointMultiplier;
    }
    
    public float getPupilAreaLeft(){
        return fourByteBuffer.wrap(buffer,292,4).getInt()/fixedPointMultiplier;
    }
    
    public float getPupilAreaRight(){
        return fourByteBuffer.wrap(buffer,296,4).getInt()/fixedPointMultiplier;
    }
    
    public float getPupilDiameterLeft(){
        return fourByteBuffer.wrap(buffer,300,4).getInt()/fixedPointMultiplier;
    }
    
    public float getPupilDiameterRight(){
        return fourByteBuffer.wrap(buffer,304,4).getInt()/fixedPointMultiplier;
    }
    
    public float getTimeStampStatus(){
        return eightByteBuffer.wrap(buffer,372,8).getInt()/fixedPointMultiplier;
    }
    
    public float getSystemStatus(){
        return fourByteBuffer.wrap(buffer,380,4).getInt()/fixedPointMultiplier;
    }
    
    public float getSystemStatusConfidence(){
        return fourByteBuffer.wrap(buffer,384,4).getInt()/fixedPointMultiplier;
    }
    
    public float getTimeStampHeadPose(){
        return eightByteBuffer.wrap(buffer,452,8).getLong()/fixedPointMultiplier;
    }
    
    public float getHeadX(){
        return fourByteBuffer.wrap(buffer,460,4).getInt()/fixedPointMultiplier;
    }
    
    public float getHeadY(){
        return fourByteBuffer.wrap(buffer,464,4).getInt()/fixedPointMultiplier;
    }
    
    public float getHeadZ(){
        return fourByteBuffer.wrap(buffer,468,4).getInt()/fixedPointMultiplier;
    }
    
    public float getHeadPitch(){
        return fourByteBuffer.wrap(buffer,472,4).getInt()/fixedPointMultiplier;
    }
    
    public float getHeadYaw(){
        return fourByteBuffer.wrap(buffer,476,4).getInt()/fixedPointMultiplier;
    }
    
    public float getHeadRoll(){
        return fourByteBuffer.wrap(buffer,480,4).getInt()/fixedPointMultiplier;
    }
    
    public float getTimeStampGaze(){
        return eightByteBuffer.wrap(buffer,548,8).getLong()/fixedPointMultiplier;
    }
    
    public float getLeftEyeX(){
        return fourByteBuffer.wrap(buffer,556,4).getInt()/fixedPointMultiplier;
    }
    
    public float getLeftEyeY(){
        return fourByteBuffer.wrap(buffer,560,4).getInt()/fixedPointMultiplier;
    }
    
    public float getLeftEyeZ(){
        return fourByteBuffer.wrap(buffer,564,4).getInt()/fixedPointMultiplier;
    }
    
    public float getRightEyeX(){
        return fourByteBuffer.wrap(buffer,568,4).getInt()/fixedPointMultiplier;
    }
    
    public float getRightEyeY(){
        return fourByteBuffer.wrap(buffer,572,4).getInt()/fixedPointMultiplier;
    }
    
    public float getRightEyeZ(){
        return fourByteBuffer.wrap(buffer,576,4).getInt()/fixedPointMultiplier;
    }
    
    public float getLeftGazeFocalPlaneIntersectionX(){
        return fourByteBuffer.wrap(buffer,580,4).getInt()/fixedPointMultiplier;
    }
    
    public float getLeftGazeFocalPlaneIntersectionY(){
        return fourByteBuffer.wrap(buffer,584,4).getInt()/fixedPointMultiplier;
    }
    
    public float getLeftGazeFocalPlaneIntersectionZ(){
        return fourByteBuffer.wrap(buffer,588,4).getInt()/fixedPointMultiplier;
    }
    
    public float getRightGazeFocalPlaneIntersectionX(){
        return fourByteBuffer.wrap(buffer,592,4).getInt()/fixedPointMultiplier;
    }
    
    public float getRightGazeFocalPlaneIntersectionY(){
        return fourByteBuffer.wrap(buffer,596,4).getInt()/fixedPointMultiplier;
    }
    
    public float getRightGazeFocalPlaneIntersectionZ(){
        return fourByteBuffer.wrap(buffer,600,4).getInt()/fixedPointMultiplier;
    }
    
    public float getCombinedGazeVectorOriginX(){
        return fourByteBuffer.wrap(buffer,604,4).getInt()/fixedPointMultiplier;
    }
    
    public float getCombinedGazeVectorOriginY(){
        return fourByteBuffer.wrap(buffer,608,4).getInt()/fixedPointMultiplier;
    }
    
    public float getCombinedGazeVectorOriginZ(){
        return fourByteBuffer.wrap(buffer,612,4).getInt()/fixedPointMultiplier;
    }
    
    public float getCombinedGazeVectorFocalPlaneIntersectionX(){
        return fourByteBuffer.wrap(buffer,616,4).getInt()/fixedPointMultiplier;
    }
    
    public float getCombinedGazeVectorFocalPlaneIntersectionY(){
        return fourByteBuffer.wrap(buffer,620,4).getInt()/fixedPointMultiplier;
    }
    
    public float getCombinedGazeVectorFocalPlaneIntersectionZ(){
        return fourByteBuffer.wrap(buffer,624,4).getInt()/fixedPointMultiplier;
    }
    
    public float getHitAOI(){
        return fourByteBuffer.wrap(buffer,628,4).getInt();
    }
}