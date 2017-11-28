/*
 * ChannelNumberPanel.java
 *
 * Created on 23. August 2007, 14:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package panel.eeg;
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
public class ChannelNumberPanel extends AbstractTextPanel{
    
    private int numChannels = 16;
    private int currentChannel = 1;
    private Font arial = new Font("Arial Black", Font.BOLD, 35);
    
    /** Creates a new instance of ChannelNumberPanel */
    public ChannelNumberPanel() {
    }
    
    public int getCurrentChannel() {
        return currentChannel;
    }
    
    public int getNumChannels() {
        return numChannels;
    }
    
    public void setCurrentChannel(int currentChannel) {
        this.currentChannel = currentChannel;
    }
    
    public void setNumChannels(int numChannels) {
        this.numChannels = numChannels;
    }
    
    private String getChannelString(){
        String channelString = channelString = Integer.toString(getCurrentChannel());
        return channelString;
    }
    
    public void renderGraphics(Graphics2D g2) {
        g2.setFont(arial);
        Composite originalComposite = g2.getComposite();
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f);
        g2.setComposite(ac);
        g2.setColor(Color.DARK_GRAY);
        g2.drawOval(getX()+1,getY()+1,getWidth(),getHeight());
        g2.setColor(Color.WHITE);
        g2.fillOval(getX(),getY(),getWidth(),getHeight());
        ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.8f);
        g2.setComposite(ac);
        g2.setColor(Color.WHITE);
        int x_offset = 6;
        if(getCurrentChannel()==1){
            x_offset = 8;
        }
        g2.drawString(getChannelString(),getX()+getWidth()/2-getStringHelper().getStringWidth(arial,getChannelString())/2-x_offset,(getStringHelper().getStringHeight(getChannelString())/4)+getY()+getHeight()/2);
        g2.setComposite(originalComposite);
        g2.drawOval(getX()-2,getY()-2,getWidth(),getHeight());
    }
}
