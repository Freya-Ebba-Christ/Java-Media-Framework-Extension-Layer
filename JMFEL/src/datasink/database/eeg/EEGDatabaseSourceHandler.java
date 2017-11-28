/*
 * EEGDatabaseSourceHandler.java
 *
 * Created on 4. September 2007, 17:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package datasink.database.eeg;

import datasink.database.AbstractDatabaseSourceHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import utilities.ByteUtility;

/**
 *
 * @author Administrator
 */
public class EEGDatabaseSourceHandler extends AbstractDatabaseSourceHandler{
    
    private int numChannels = 32;
    private ByteUtility aByteUtility;
    private byte[] byteBuffer = new byte[2];
    private String sessionID = "3";
    private float voltage = 0f;
    private Date date = new Date();
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
    private double samplerate = 1024;
    private int batchChunkLength = 1000;
    private String[] inserts = new String[batchChunkLength];
    private int batchCounter = 0;
    private int sampleNumber = 0;
    private boolean running = false;
    private boolean enabled = true;
    
    /** Creates a new instance of EEGDataSink */
    public EEGDatabaseSourceHandler() {
        aByteUtility = new ByteUtility();
    }
    
    public void setVoltage(float voltage) {
        this.voltage = voltage;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public boolean isRunning() {
        return running;
    }
    
    public void setRunning(boolean running) {
        this.running = running;
    }
    
    public void close(){
        super.close();
        setRunning(false);
    }
    
    public void setBatchChunkLength(int batchChunkLength) {
        this.batchChunkLength = batchChunkLength;
        String[] inserts = new String[batchChunkLength];
    }
    
    public int getBatchChunkLength() {
        return batchChunkLength;
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
    
    public void open(){
        super.open();
        String insert = "Insert into data.tblEXGSetup values ("+getSessionID()+","+getVoltage()+","+getSamplerate()+")";
        System.out.println(insert);
        execute(insert);
        setRunning(true);
    }
    
    public void process() {
        if(isEnabled()){
            try{
                int channelIndex = 0;
                //go through all the channels and add the value to the line
                getDate().setTime(System.currentTimeMillis());
                sampleNumber = 0;
                for (int x = 0; x < getDataLength(); x+=2) {
                     short value = getValue(x);
                    channelIndex++;
                    inserts[batchCounter] = "Insert into TMP.tmpEXGData values ("+getSessionID()+","+getSequenceNumber()+","+ sampleNumber +","+(channelIndex-1)+","+value+",'"+getFormatter().format(getDate())+"')";
                    batchCounter++;
                    if(batchCounter==inserts.length){
                        batchCounter=0;
                        executeBatch(inserts);
                    }
                    if(channelIndex>=getNumChannels()){
                        channelIndex = 0;
                        sampleNumber++;
                    }
                }
            }catch(Exception ex){};
        }
    }
    
    private short getValue(int pos){
        byteBuffer[0]=getData()[pos];
        byteBuffer[1]=getData()[pos+1];
        return aByteUtility.joinBytes(byteBuffer);
    }
}