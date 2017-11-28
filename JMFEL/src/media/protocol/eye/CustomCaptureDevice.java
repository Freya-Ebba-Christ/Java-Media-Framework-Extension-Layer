/*
 * CustomCaptureDevice.java
 *
 * Created on 10. Oktober 2007, 11:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.eye;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Properties;
import javax.media.format.AudioFormat;
import media.protocol.generic.GenericCustomCaptureDevice;
import utilities.PropertiesReader;

/**
 *
 * @author Administrator
 */

public class CustomCaptureDevice extends GenericCustomCaptureDevice{
    private PropertiesReader propertiesReader = new PropertiesReader();
    private DatagramSocket dataggramSocket = null;
    private DatagramPacket datagramPacket = null;
    private byte [] buffer;
    private boolean initialized = false;
    private AudioFormat audioFormat;
    private int maxDataLength;
    private Properties configuration = null;
    private int samplerate;
    private int bits_per_sample;
    private int port;
    private String file_separator = System.getProperty("file.separator");
    
    public CustomCaptureDevice() {
    }
    
    public void init(){
        try {
            //loading the configuration here looks a bit strange, but this is the right place. For sure ;-)
            configuration = propertiesReader.readProperties(System.getProperty("user.dir") + file_separator + "media" + file_separator + "protocol" + file_separator + "eye" + file_separator + "config.ini");
            port = Integer.parseInt((String)configuration.getProperty("UDP_PORT"));
            InetAddress address = InetAddress.getByName((String)configuration.getProperty("IP_ADDRESS"));
            buffer = new byte[1024];
            datagramPacket = new DatagramPacket(buffer, 1024);
            dataggramSocket = new DatagramSocket(port,address);
        } catch (Exception e) {System.err.println(e);};
        
        initialized = true;
    }
    
    public byte [] getAvailableData(){
        //no buffering at this point. Just drop packets that cant be processed fast enough...
        try {
            dataggramSocket.receive(datagramPacket);
            buffer = datagramPacket.getData();
        } catch (Exception e) {System.err.println(e);};
        return buffer;
    }
    
    public int getMaxDataLength(){
        return 1024;
    };
    
    public AudioFormat getFormat(){
        
        configuration = propertiesReader.readProperties(System.getProperty("user.dir") + file_separator + "media" + file_separator + "protocol" + file_separator + "eye" + file_separator + "config.ini");
        samplerate = Integer.parseInt((String)configuration.getProperty("SAMPLERATE"));
        bits_per_sample = Integer.parseInt((String)configuration.getProperty("BITS_PER_SAMPLE"));
        audioFormat = new AudioFormat(AudioFormat.LINEAR, samplerate*getMaxDataLength(), bits_per_sample, 1);
        return audioFormat;
    }
}
