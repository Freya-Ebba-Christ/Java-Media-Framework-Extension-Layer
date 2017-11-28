/*
 * SingleChannelEEGPanel.java
 *
 * Created on 28. Mai 2007, 15:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package panel.eeg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import utilities.DoubleDataBufferContainer;
import visual_signal_components.passive_rendering.AbstractTextPanel;
import visual_signal_components.passive_rendering.SignalPanel;

/**
 *
 * @author Administrator
 */
public class SingleChannelEEGPanel extends AbstractTextPanel{
    
    private boolean initialized = false;
    private BasicStroke thickstroke = new BasicStroke(3);
    private BasicStroke thinstroke = new BasicStroke(1);
    private SignalPanel signalPanel = new SignalPanel();
    private int currentChannel = 0;
    
    private DoubleDataBufferContainer aDoubleDataBufferContainer;
    
    /** Creates a new instance of MultiBandEEGPanel */
    public SingleChannelEEGPanel() {
    }
    
    public void setDoubleDataBufferContainer(DoubleDataBufferContainer aDoubleDataBufferContainer) {
        this.aDoubleDataBufferContainer = aDoubleDataBufferContainer;
    }
    
    public void setSignalPanel(SignalPanel signalPanel) {
        this.signalPanel = signalPanel;
    }
    
    public SignalPanel getSignalPanel() {
        return signalPanel;
    }
    
    public int getCurrentChannel() {
        return currentChannel;
    }
    
    public void setCurrentChannel(int currentChannel) {
        this.currentChannel = currentChannel;
    }
    
    public DoubleDataBufferContainer getDoubleDataBufferContainer() {
        return aDoubleDataBufferContainer;
    }
    
    public void renderGraphics(Graphics2D g2) {
        //Composite originalComposite = g2.getComposite();
        //AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f);
        //g2.setComposite(ac);
        g2.setColor(Color.DARK_GRAY);
        g2.drawRoundRect(getX()+2,getY()+2,getWidth(),getHeight(),8,8);
        g2.setColor(Color.WHITE);
        //g2.fillRoundRect(getX(),getY(),getWidth(),getHeight(),8,8);
        //g2.setComposite(originalComposite);
        g2.setColor(getColor());
        g2.drawRoundRect(getX(),getY(),getWidth(),getHeight(),8,8);
        
        signalPanel.setGraphics2D(g2);
        signalPanel.setHeight(getHeight()-50);
        signalPanel.setWidth(getWidth()-80);
        signalPanel.setLocation(getX()+50,getY()+20);
        signalPanel.setTitle("Channel: "+(getCurrentChannel()+1));
        signalPanel.setTitleColor(Color.WHITE);
        g2.setStroke(thinstroke);
        signalPanel.setBufferLength(getDoubleDataBufferContainer().getBufferLength());
        signalPanel.setMaxVoltage(getDoubleDataBufferContainer().getDataBuffer(getCurrentChannel()).getMaxVoltage());
        signalPanel.setUnit(getDoubleDataBufferContainer().getDataBuffer(getCurrentChannel()).getUnit());
        signalPanel.drawSignal(getDoubleDataBufferContainer().getDataBuffer(getCurrentChannel()).toArray());
        g2.setStroke(thickstroke);
        signalPanel.drawPanel();
        signalPanel.drawTitle();
        g2.setStroke(thickstroke);
    }
}