/*
 * ExportStatePanel.java
 *
 * Created on 3. September 2007, 06:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package adracer.panel.utils;

import datasink.database.eeg.EEGDatabaseSourceHandler;
import datasink.database.eye.EyetrackerDataSourceHandler;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import visual_signal_components.passive_rendering.AbstractTextPanel;

/**
 *
 * @author Administrator
 */
public class ExportStatePanel extends AbstractTextPanel{
    private Font font = new Font("Arial Black", Font.BOLD, 14);
    private String tracker_not_running = "Tracker DB Export is not running";
    private String tracker_running_0 = "Tracker DB Export is running";
    private String tracker_running_1 = "Tracker DB Export is running.";
    private String tracker_running_2 = "Tracker DB Export is running..";
    private String tracker_running_3 = "Tracker DB Export is running...";
    
    private String eeg_not_running = "EEG DB Export is not running";
    private String eeg_running_0 = "EEG DB Export is running";
    private String eeg_running_1 = "EEG DB Export is running.";
    private String eeg_running_2 = "EEG DB Export is running..";
    private String eeg_running_3 = "EEG DB Export is running...";
    
    private String[] tracker_strings = new String[4];
    private String[] eeg_strings = new String[4];
    private boolean tracker_state = false;
    private boolean eeg_state = false;
    private int seconds_to_wait = 1;
    private long lastTime = System.currentTimeMillis();
    private int tracker_stringIndex = 0;
    private int eeg_stringIndex = 0;
    private EyetrackerDataSourceHandler eyetrackerDataSourceHandler;
    private EEGDatabaseSourceHandler eegDatabaseSourceHandler;
    
    public ExportStatePanel() {
        tracker_strings[0]=tracker_running_0;
        tracker_strings[1]=tracker_running_1;
        tracker_strings[2]=tracker_running_2;
        tracker_strings[3]=tracker_running_3;
        
        eeg_strings[0]=eeg_running_0;
        eeg_strings[1]=eeg_running_1;
        eeg_strings[2]=eeg_running_2;
        eeg_strings[3]=eeg_running_3;
    }
    
    public Font getFont() {
        return font;
    }
    
    public void setFont(Font font) {
        this.font = font;
    }
    
    public EyetrackerDataSourceHandler getEyetrackerDataSourceHandler() {
        return eyetrackerDataSourceHandler;
    }
    
    public void setEyetrackerDataSourceHandler(EyetrackerDataSourceHandler eyetrackerDataSourceHandler) {
        this.eyetrackerDataSourceHandler = eyetrackerDataSourceHandler;
    }
    
    public EEGDatabaseSourceHandler getEEGDatabaseSourceHandler() {
        return eegDatabaseSourceHandler;
    }
    
    public void setEEGDatabaseSourceHandler(EEGDatabaseSourceHandler eegDatabaseSourceHandler) {
        this.eegDatabaseSourceHandler = eegDatabaseSourceHandler;
    }
    
    public void renderGraphics(Graphics2D g2) {
        g2.setFont(font);
        Composite originalComposite = g2.getComposite();
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f);
        g2.setComposite(ac);
        
        if(getEyetrackerDataSourceHandler()!=null){
            tracker_state = getEyetrackerDataSourceHandler().isRunning();
        }
        
        if(getEEGDatabaseSourceHandler()!=null){
            eeg_state = getEEGDatabaseSourceHandler().isRunning();
        }
        
        g2.setColor(Color.DARK_GRAY);
        g2.drawRect(getX()+1,getY()+1,getWidth(),getHeight());
        g2.drawRect(getX()+1,getY()+getHeight()+1+10,getWidth(),getHeight());
        g2.drawRect(getX()+1,getY()+getHeight()*2+1+20,getWidth(),getHeight());
        g2.setColor(Color.WHITE);
        g2.fillRect(getX(),getY(),getWidth(),getHeight());
        g2.fillRect(getX(),getY()+getHeight()+10,getWidth(),getHeight());
        g2.fillRect(getX(),getY()+getHeight()*2+20,getWidth(),getHeight());
        ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.8f);
        g2.setComposite(ac);
        g2.setColor(Color.WHITE);
        
        if(((System.currentTimeMillis()-lastTime)/1000)>seconds_to_wait){
            tracker_stringIndex++;
            eeg_stringIndex++;
            lastTime = System.currentTimeMillis();
        }
        
        if(tracker_stringIndex>tracker_strings.length-1){
            tracker_stringIndex = 0;
        }
        
        if(eeg_stringIndex>eeg_strings.length-1){
            eeg_stringIndex = 0;
        }
        
        if(eeg_state){
            g2.drawString(eeg_strings[eeg_stringIndex],getX()+5,getY()+65);
        }else{
            g2.drawString(eeg_not_running,getX()+5,getY()+65);
        }
        
        if(tracker_state){
            g2.drawString(tracker_strings[tracker_stringIndex],getX()+5,getY()+22);
        }else{
            g2.drawString(tracker_not_running,getX()+5,getY()+22);
        }
        
        if(getEyetrackerDataSourceHandler().getSessionID().equalsIgnoreCase(getEEGDatabaseSourceHandler().getSessionID())){
            g2.drawString("Session ID: "+getEyetrackerDataSourceHandler().getSessionID(),getX()+5,getY()+getHeight()*2+40);
        }
        
        g2.setComposite(originalComposite);
        g2.drawRect(getX()-2,getY()-2,getWidth(),getHeight());
        g2.drawRect(getX()-2,getY()-2+getHeight()+10,getWidth(),getHeight());
        g2.drawRect(getX()-2,getY()-2+getHeight()*2+20,getWidth(),getHeight());
    }
}