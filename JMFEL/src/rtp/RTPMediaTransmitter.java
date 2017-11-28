/*
 * RTPMediaTransmitter.java
 *
 * Created on 4. April 2007, 10:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package rtp;

import javax.media.protocol.DataSource;
import javax.media.DataSink;
import javax.media.Manager;
import javax.media.MediaException;
import javax.media.MediaLocator;

/**
 *
 * @author christ
 */
public class RTPMediaTransmitter {
    private String rtpURL;
    private int port;
    private String ipAddress;
    private boolean transmitAudio = true;
    private boolean transmitVideo = true;
    private DataSource inputDataSource = null;
    private DataSink rtptransmitter = null;
    
    public RTPMediaTransmitter() {
    }
    
    public void setTransmitVideo(boolean transmitVideo) {
        this.transmitVideo = transmitVideo;
    }
    
    public void setTransmitAudio(boolean transmitAudio) {
        this.transmitAudio = transmitAudio;
    }
    
    public void setPort(int port) {
        this.port = port;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public void setDataSource(DataSource inputDataSource) {
        this.inputDataSource = inputDataSource;
    }
    
    public boolean isTransmitVideo() {
        return transmitVideo;
    }
    
    public String getRtpURL() {
        return rtpURL;
    }
    
    public int getPort() {
        return port;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public DataSource getDataSource() {
        return inputDataSource;
    }
    
    public boolean isTransmitAudio() {
        return transmitAudio;
    }
    
    public void start(){
        try{
            createTransmitter();
            rtptransmitter.open();
            rtptransmitter.start();
            getDataSource().start();
        }catch(Exception e){
            
            System.out.println("couldn't start RTP transmission"+e);
        }
    }
    
    public void stop(){
        try{
            getDataSource().stop();
            rtptransmitter.stop();
            rtptransmitter.close();
        }catch(Exception e){
            System.out.println("couldn't close RTP transmission");
        }
    }
    
    private void createTransmitter() {
        if(isTransmitAudio()){
            rtpURL = "rtp://" + getIpAddress() + ":" + getPort() + "/audio";
        } if(isTransmitVideo()){
            rtpURL = "rtp://" + getIpAddress() + ":" + getPort() + "/video";
        } if (isTransmitVideo()&&isTransmitAudio()){
            System.out.println("audio and video");
            rtpURL = "rtp://" + getIpAddress() + ":" + getPort();
        } if (!isTransmitVideo()&&!isTransmitAudio()){
            throw new IllegalArgumentException("don't you want to transmit at least one stream?");
        }
        System.out.println(rtpURL);
        try {
            MediaLocator outputLocator = new MediaLocator(rtpURL);
            rtptransmitter = Manager.createDataSink(getDataSource(), outputLocator);
        } catch (Exception me) {
            System.out.print("Couldn't create RTP data sink");
        }
    }
}