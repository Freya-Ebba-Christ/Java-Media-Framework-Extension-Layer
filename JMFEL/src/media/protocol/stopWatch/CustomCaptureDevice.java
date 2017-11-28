/*
 * CustomCaptureDevice.java
 *
 * Created on 14. Dezember 2007, 18:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.stopWatch;


import examples.motorcortex.eeg_recording.StopwatchApplication;
import javax.media.format.AudioFormat;
import media.protocol.generic.GenericCustomCaptureDevice;
import media.protocol.pong.SimpleGameDataPacketCodec;
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
    private StopwatchApplication stopwatchApplication;
    private StopWatchDataPacketCodec stopWatchDataPacketCodec;
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
    
    public StopwatchApplication getStopwatchApplication() {
        return stopwatchApplication;
    }
    
    public void setStopwatchApplication(StopwatchApplication stopwatchApplication) {
        this.stopwatchApplication = stopwatchApplication;
    }
    
    public void init(){
        try {
            stopWatchDataPacketCodec = new StopWatchDataPacketCodec();
            bits_per_sample = 8;
            samplerate = 1024;
        } catch (Exception e) {System.err.println(e);};
        
        initialized = true;
    }
    
    public byte [] getAvailableData(){
        
        byte[] packet = new byte[getMaxDataLength()];
        
        if(getStopwatchApplication()!=null){
            stopWatchDataPacketCodec.setDatapacket(packet);
            stopWatchDataPacketCodec.setKeyLeft(false);
            stopWatchDataPacketCodec.setKeyRight(false);
            stopWatchDataPacketCodec.setCurrentDirection(getStopwatchApplication().getPainter().getPanel().getCurrentDirection());
            keyboard.poll();
            Component[] components = keyboard.getComponents();
            for(int i=0;i<components.length;i++) {
                Key key = (Key)components[i].getIdentifier();
                if(key == Key.LCONTROL){
                    if(components[i].getPollData()==1.0f) {
                        getStopwatchApplication().getPainter().getPanel().setStopped(true);
                        stopWatchDataPacketCodec.setKeyLeft(true);
                        stopWatchDataPacketCodec.setKeyRight(false);
                        stopWatchDataPacketCodec.setCurrentDirection(getStopwatchApplication().getPainter().getPanel().getCurrentDirection());
                    }
                }else if(key == Key.RCONTROL){
                    if(components[i].getPollData()==1.0f) {
                        getStopwatchApplication().getPainter().getPanel().setStopped(true);
                        stopWatchDataPacketCodec.setKeyLeft(false);
                        stopWatchDataPacketCodec.setKeyRight(true);
                        stopWatchDataPacketCodec.setCurrentDirection(getStopwatchApplication().getPainter().getPanel().getCurrentDirection());
                    }
                }
            }
            //ok ok, this is very very unlikely to happen, but just to be sure, do the checking.
            //hint: 9223372036854775807L is the largest positive 64 integer value
            
            if(packetId==9223372036854775807L){
                packetId=0L;
            }
            packetId++;
            
            stopWatchDataPacketCodec.setPacketID(packetId);
            //encode all values ito a 32 bytes long byte array
            stopWatchDataPacketCodec.encode();
        }
        
        //return the data packet
        return stopWatchDataPacketCodec.getDatapacket();
    }
    
    public int getMaxDataLength(){
        return 16;
    };
    
    public AudioFormat getFormat(){
        audioFormat = new AudioFormat(AudioFormat.LINEAR, samplerate*getMaxDataLength(), bits_per_sample, 1);
        return audioFormat;
    }
}