/*
 * CustomCaptureDevice.java
 *
 * Created on 31. Oktober 2007, 18:42
 *
 * This shows how to easily stream data from a pong game and a keyboard
 */

package media.protocol.pong;

import javax.media.format.AudioFormat;
import media.protocol.generic.GenericCustomCaptureDevice;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier.Key;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import utilities.game.SimplePongGame;

/**
 *
 * @author Administrator
 */

public class CustomCaptureDevice extends GenericCustomCaptureDevice{
    private boolean initialized = false;
    private AudioFormat audioFormat;
    private int maxDataLength;
    private int samplerate;
    private int bits_per_sample;
    private Controller[] controllers;
    private Controller keyboard;
    private SimplePongGame simplePongGame;
    private SimpleGameDataPacketCodec simpleGameDataPacketCodec;
    private long packetId = 0L;
    
    /** Creates a new instance of CustomCaptureDevice */
    public CustomCaptureDevice() {
        controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        keyboard=null;
        for(int i=0;i<controllers.length && keyboard==null;i++) {
            if(controllers[i].getType()==Controller.Type.KEYBOARD) {
                keyboard = controllers[i];
            }
        }
        if(keyboard==null) {
            System.out.println("Found no keyboard");
            System.exit(0);
        }
    }
    
    public SimplePongGame getSimplePongGame() {
        return simplePongGame;
    }
    
    public void setSimplePongGame(SimplePongGame simplePongGame) {
        this.simplePongGame = simplePongGame;
    }
    
    public void init(){
        try {
            simpleGameDataPacketCodec = new SimpleGameDataPacketCodec();
            bits_per_sample = 8;
            samplerate = 1024;
        } catch (Exception e) {System.err.println(e);};
        
        initialized = true;
    }
    
    public byte [] getAvailableData(){
        
        byte[] packet = new byte[getMaxDataLength()];
        
        simpleGameDataPacketCodec.setDatapacket(packet);
        if(getSimplePongGame()!=null){
            keyboard.poll();
            Component[] components = keyboard.getComponents();
            for(int i=0;i<components.length;i++) {
                Key key = (Key)components[i].getIdentifier();
                if(key == Key.LCONTROL){
                    if(components[i].getPollData()==1.0f) {
                        getSimplePongGame().moveLeft();
                        simpleGameDataPacketCodec.setKeyLeft(true);
                        simpleGameDataPacketCodec.setKeyRight(false);
                        simpleGameDataPacketCodec.setKeyUp(false);
                        simpleGameDataPacketCodec.setKeyDown(false);
                    }
                }else if(key == Key.RCONTROL){
                    if(components[i].getPollData()==1.0f) {
                        getSimplePongGame().moveRight();
                        simpleGameDataPacketCodec.setKeyLeft(false);
                        simpleGameDataPacketCodec.setKeyRight(true);
                        simpleGameDataPacketCodec.setKeyUp(false);
                        simpleGameDataPacketCodec.setKeyDown(false);
                    }
                }else{
                    simpleGameDataPacketCodec.setKeyLeft(false);
                    simpleGameDataPacketCodec.setKeyRight(false);
                    simpleGameDataPacketCodec.setKeyUp(false);
                    simpleGameDataPacketCodec.setKeyDown(false);
                }
            }
            
            //ok ok, this is very very unlikely to happen, but just to be sure, do the checking.
            //hint: 9223372036854775807L is the largest positive 64 integer value
            
            if(packetId==9223372036854775807L){
                packetId=0L;
            }
            packetId++;
            
            //and the rest of the values...
            simpleGameDataPacketCodec.setPaddlePositionX(getSimplePongGame().getPaddlePositionX());
            simpleGameDataPacketCodec.setPaddlePositionY(getSimplePongGame().getPaddlePositionY());
            
            simpleGameDataPacketCodec.setBallPositionX(getSimplePongGame().getBallPositionX());
            simpleGameDataPacketCodec.setBallPositionY(getSimplePongGame().getBallPositionY());
            simpleGameDataPacketCodec.setPacketID(packetId);
            //encode all values ito a 32 bytes long byte array
            simpleGameDataPacketCodec.encode();
        }
        //return the data packet
        return simpleGameDataPacketCodec.getDatapacket();
    }
    
    public int getMaxDataLength(){
        return 32;
    };
    
    public AudioFormat getFormat(){
        audioFormat = new AudioFormat(AudioFormat.LINEAR, samplerate*getMaxDataLength(), bits_per_sample, 1);
        return audioFormat;
    }
}