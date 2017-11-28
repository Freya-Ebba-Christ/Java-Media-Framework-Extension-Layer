/*
 * EyeTrackerUDPSendServer.java
 *
 * Created on 7. Juni 2007, 16:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package eye.insight.calibration.eye;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Administrator
 */
public class EyeTrackerUDPSendServer extends Thread{
    public boolean running = false;
    private String toIP = "";
    private DatagramPacket toPacket;
    private InetAddress toIA;
    private byte toData[];
    private DatagramSocket toSocket;
    private int toPort = 0;
    private int currentArea = 0;
    private boolean newDataAvailable = false;
    
    public EyeTrackerUDPSendServer(){
    }
    
    public boolean isRunning() {
        return running;
    }
    
    public void setNewDataAvailable(boolean newDataAvailable) {
        this.newDataAvailable = newDataAvailable;
    }
    
    public boolean isNewDataAvailable() {
        return newDataAvailable;
    }
    
    public int getCurrentArea() {
        return currentArea;
    }
    
    public void setCurrentArea(int currentArea) {
        this.currentArea = currentArea;
    }
    
    public void setRunning(boolean running) {
        this.running = running;
    }
    
    public byte[] getToData() {
        return toData;
    }
    
    public InetAddress getToIA() {
        return toIA;
    }
    
    public String getToIP() {
        return toIP;
    }
    
    public DatagramPacket getToPacket() {
        return toPacket;
    }
    
    public int getToPort() {
        return toPort;
    }
    
    public DatagramSocket getToSocket() {
        return toSocket;
    }
    
    public void setToData(byte[] toData) {
        this.toData = toData;
    }
    
    public void setToIA(InetAddress toIA) {
        this.toIA = toIA;
    }
    
    public void setToIP(String toIP) {
        this.toIP = toIP;
    }
    
    public void setToPacket(DatagramPacket toPacket) {
        this.toPacket = toPacket;
    }
    
    public void setToPort(int toPort) {
        this.toPort = toPort;
    }
    
    public void setToSocket(DatagramSocket toSocket) {
        this.toSocket = toSocket;
    }
    
    public void startServer(){
        try{
            toData = new byte[1];
            toIA = InetAddress.getByName(getToIP());
            toSocket = new DatagramSocket();
            toPacket = new DatagramPacket( toData, toData.length, toIA, toPort);
        }catch(Exception e){System.out.println(e);};
        
        running = true;
        start();
    }
    
    public void stopServer(){
        toSocket.close();
        running = false;
    }
    
    protected void finalize() throws Throwable {
        try {
            toSocket.close();
        } finally {
            super.finalize();
        }
    }
    
    public void run() {
        while(running){
            try{
                sleep(250);
                if(isNewDataAvailable()){
                    toData[0]=(byte)getCurrentArea();
                    toSocket.send(toPacket);
                    setNewDataAvailable(false);
                }
            }catch(Exception e){System.out.println(e);};
        }
    }
}