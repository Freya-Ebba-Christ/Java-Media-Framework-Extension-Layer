/*
 * EEGDataSink.java
 *
 * Created on 16. April 2007, 13:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package datasink.delimited.eeg;

import datasink.delimited.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import utilities.ByteUtility;

/**
 *
 * @author christ
 */

public class EEGFileDataSourceHandler extends AbstractDelimitedFileDataSourceHandler{
    
    private int numChannels = 32;
    private ByteUtility aByteUtility;
    private byte[] byteBuffer = new byte[2];
    private float devider = 100f;
    private String sessionID = "1";
    private float voltage = 0f;
    private Date date = new Date();
    private SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yy hh:mm:ss");
    private double samplerate = 1024;
    
    /** Creates a new instance of EEGDataSink */
    public EEGFileDataSourceHandler() {
        aByteUtility = new ByteUtility();
        setDelimiter(';');
        setTitle("SessionID; Channel; Value; TimeStamp; Voltage; Samplerate\n");
    }
    
    public void setVoltage(float voltage) {
        this.voltage = voltage;
    }

    public void setSamplerate(double samplerate) {
        this.samplerate = samplerate;
    }

    public double getSamplerate() {
        return samplerate;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public SimpleDateFormat getFormatter() {
        return formatter;
    }
    
    public void setFormatter(SimpleDateFormat formatter) {
        this.formatter = formatter;
    }
    
    public float getVoltage() {
        return voltage;
    }
    
    public void setDevider(float devider) {
        this.devider = devider;
    }
    
    public double getDevider() {
        return devider;
    }
    
    public int getNumChannels() {
        return numChannels;
    }
    
    public void setNumChannels(int numChannels) {
        this.numChannels = numChannels;
    }
    
    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
    
    public String getSessionID() {
        return sessionID;
    }
    
    public void process() {
        
        try{
            int channelIndex = 0;
            //go through all the channels and add the value to the line
            getDate().setTime(System.currentTimeMillis());
            for (int x = 0; x < getDataLength(); x+=2) {
                short value = getValue(x);
                channelIndex++;
                
                //System.out.println(getSessionID()+";"+(channelIndex-1)+";"+getFormatter().format(getDate())+";"+getVoltage()+getDelimiter());
                getBufferedWriter().append(getSessionID()+";"+(channelIndex-1)+";"+value/getDevider()+";"+getFormatter().format(getDate())+";"+getVoltage()+";"+getSamplerate()+",");
                getBufferedWriter().append("\n");
                if(channelIndex>=getNumChannels()){
                    channelIndex = 0;
                }
            }
        }catch(Exception ex){System.out.println("error"+ex);};
    }
    
    public static void main(String[] args) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yy hh:mm:ss");
        date.setTime(System.currentTimeMillis());
        System.out.println(formatter.format(date));
        
    }
    
    private short getValue(int pos){
        byteBuffer[0]=getData()[pos];
        byteBuffer[1]=getData()[pos+1];
        return aByteUtility.joinBytes(byteBuffer);
    }
}