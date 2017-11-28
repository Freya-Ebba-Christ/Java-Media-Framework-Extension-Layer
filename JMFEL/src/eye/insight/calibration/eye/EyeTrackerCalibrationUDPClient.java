/*
 * EyeTrackerCalibrationUDPClient.java
 *
 * Created on 25. September 2007, 11:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package eye.insight.calibration.eye;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Observable;
import java.util.Properties;
import utilities.PropertiesReader;

/**
 *
 * @author Administrator
 */

public class EyeTrackerCalibrationUDPClient extends Observable{
    private int currentAOI = 0;
    private EyetrackerCalibrationPollThread eyetrackerCalibrationPollThread = new EyetrackerCalibrationPollThread();
    private PropertiesReader propertiesReader = new PropertiesReader(); 
    
     public static void main( String args[] ) {
         EyeTrackerCalibrationUDPClient aEyeTrackerCalibrationUDPClient = new EyeTrackerCalibrationUDPClient();
     }
    
    public EyeTrackerCalibrationUDPClient(){
        String file_separator = System.getProperty("file.separator");
        Properties props = propertiesReader.readProperties(System.getProperty("user.dir")+file_separator+"adracer"+file_separator+"calibration"+file_separator+"eye"+file_separator+"client.ini");
        eyetrackerCalibrationPollThread.setFromIP(props.getProperty("IP"));
        eyetrackerCalibrationPollThread.setFromPort(Integer.parseInt(props.getProperty("PORT")));
        eyetrackerCalibrationPollThread.startThread();
    }
    
    private void updated(){
        setChanged();
        notifyObservers();
    }

    public int getCurrentAOI() {
        return currentAOI;
    }

    public void setCurrentAOI(int currentAOI) {
        this.currentAOI = currentAOI;
    }
    
    class EyetrackerCalibrationPollThread extends Thread{
        private boolean running = false;
        private String fromIP="";
        private DatagramPacket fromPacket;
        private InetAddress fromIA;
        private byte fromData[];
        private DatagramSocket fromSocket;
        private int fromPort = 4711;
        
        public EyetrackerCalibrationPollThread(){
        }
        
        public byte[] getFromData() {
            return fromData;
        }
        
        public InetAddress getFromIA() {
            return fromIA;
        }
        
        public DatagramPacket getFromPacket() {
            return fromPacket;
        }
        
        public DatagramSocket getFromSocket() {
            return fromSocket;
        }
        
        public void setFromData(byte[] fromData) {
            this.fromData = fromData;
        }
        
        public void setFromIA(InetAddress fromIA) {
            this.fromIA = fromIA;
        }
        
        public void setFromPacket(DatagramPacket fromPacket) {
            this.fromPacket = fromPacket;
        }
        
        public void setFromSocket(DatagramSocket fromSocket) {
            this.fromSocket = fromSocket;
        }
        
        public void setFromIP(String ip) {
            fromIP = ip;
        }
        
        public String getFromIP() {
            return fromIP;
        }
        
        public int getFromPort() {
            return fromPort;
        }
        
        public void setFromPort(int port) {
            fromPort = port;
        }
        
        public void run() {
            
            while(running){
                try{
                    fromSocket.receive(fromPacket);
                    setCurrentAOI(fromPacket.getData()[0]&255);
                    updated();
                    sleep(2);
                }catch(Exception e){System.out.println(e);};
            }
        }
        
        public boolean isRunning() {
            return running;
        }
        
        public void startThread(){
            try{
                fromData = new byte[1];
                fromIA = InetAddress.getByName(getFromIP());
                fromSocket = new DatagramSocket(getFromPort(),fromIA);
                fromPacket = new DatagramPacket( fromData, fromData.length );
            }catch(Exception e){System.out.println(e);};
            running = true;
            start();
        }
        
        public void stopThread(){
            running = false;
        }
        
        protected void finalize() throws Throwable {
            try {
                fromSocket.close();
            } finally {
                super.finalize();
            }
        }
    }
}