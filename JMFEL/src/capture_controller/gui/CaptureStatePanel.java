/*
 * CaptureStatePanel.java
 *
 * Created on 14. Oktober 2007, 03:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package capture_controller.gui;

import java.awt.Font;
import java.awt.Graphics2D;
import javax.media.Format;
import javax.media.Player;
import javax.media.Processor;
import javax.media.format.AudioFormat;
import javax.media.format.VideoFormat;
import visual_signal_components.passive_rendering.AbstractTextPanel;

/**
 *
 * @author Urkman_2
 */
public class CaptureStatePanel extends AbstractTextPanel{
    private Font font = new Font("Arial Black", Font.BOLD, 14);
    private String filename = "";
    private String elapsedTime ="";
    private Format[] format;
    public static final int STOPPED = 0;
    public static final int RUNNING = 1;
    public static final int PAUSED = 2;
    private int state = STOPPED;
    private String toolTip = " ";
    private Processor processor;
    private Player player;
    
    public CaptureStatePanel() {
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public Processor getProcessor() {
        return processor;
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public void setProcessor(Processor processor) {
        this.processor = processor;
    }
    
    public Font getFont() {
        return font;
    }
    
    public String getFilename() {
        return filename;
    }
    
    public String getToolTip() {
        return toolTip;
    }
    
    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public String getElapsedTime() {
        return elapsedTime;
    }
    
    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
    
    public void setFont(Font font) {
        this.font = font;
    }
    
    public void setFormat(Format[] format) {
        this.format = format;
    }
    
    public Format[] getFormat() {
        return format;
    }
    
    public int getState() {
        return state;
    }
    
    public void setState(int state) {
        this.state = state;
    }
    
    public void renderGraphics(Graphics2D g2) {
        font = new Font("Arial Black", Font.BOLD, 14);
        
        if(getProcessor()!=null&&getPlayer()==null){
            int seconds = (int)(((processor.getMediaTime().getSeconds())%60.0));
            int minutes = (int)((processor.getMediaTime().getSeconds())/60.0);
            int hours = (int)(minutes/60);
            setElapsedTime(hours+":"+minutes+":"+seconds);
            
        }else if(getProcessor()==null&&getPlayer()!=null){
            int seconds = (int)(((processor.getMediaTime().getSeconds())%60.0));
            int minutes = (int)((processor.getMediaTime().getSeconds())/60.0);
            int hours = (int)(minutes/60);
            setElapsedTime(hours+":"+minutes+":"+seconds);
        }
        
        g2.setFont(getFont());
        g2.drawString("Filename: "+getFilename(),getX(),getY());
        if(getProcessor()!=null&&getPlayer()==null||getProcessor()==null&&getPlayer()!=null){
            g2.drawString("Media time: "+getElapsedTime(),getX(), getY()+getStringHelper().getStringHeight(getFilename()));
            
            if( getFormat()!=null){
                for (int i = 0; i < getFormat().length; i++) {
                    if(getFormat()[i] instanceof VideoFormat){
                        VideoFormat vfmt = (VideoFormat)getFormat()[i];
                        String vfmtString = "Video: " + "frame rate: "+(int)vfmt.getFrameRate()+" size: "+(int)vfmt.getSize().getWidth()+" x "+(int)vfmt.getSize().getHeight();
                        g2.drawString("Format: "+vfmtString,getX(), getY()+(2+i)*getStringHelper().getStringHeight(vfmtString));
                    }else if(getFormat()[i] instanceof AudioFormat){
                        AudioFormat afmt = (AudioFormat)getFormat()[i];
                        String afmtString = "Audio: " + "sample rate: "+(int)afmt.getSampleRate() + " size: "+ afmt.getSampleSizeInBits()+" bits";
                        g2.drawString("Format: "+afmtString,getX(), getY()+(2+i)*getStringHelper().getStringHeight(afmtString));
                    }
                }
                
                if(getState()==STOPPED){
                    g2.drawString("State: "+"stopped",getX(), getY()+(2+getFormat().length)*getStringHelper().getStringHeight("STOPPED"));
                }else if(getState()==RUNNING){
                    g2.drawString("State: "+"running",getX(), getY()+(2+getFormat().length)*getStringHelper().getStringHeight("RUNNING"));
                }else if(getState()==PAUSED){
                    g2.drawString("State: "+"paused",getX(), getY()+(2+getFormat().length)*getStringHelper().getStringHeight("PAUSED"));
                }
            }
        }
        font = new Font("Arial Black", Font.PLAIN, 12);
        g2.setFont(getFont());
        g2.drawString(getToolTip(),getWidth()/2-getStringHelper().getStringWidth(font,getToolTip())/2, getY()+getHeight()-getStringHelper().getStringHeight(getToolTip())/2);
    }
}