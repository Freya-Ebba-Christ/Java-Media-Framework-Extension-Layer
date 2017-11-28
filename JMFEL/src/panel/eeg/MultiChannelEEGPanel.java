/*
 * EEGPanel32.java
 *
 * Created on 28. Mai 2007, 04:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package panel.eeg;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;
import utilities.DoubleDataBufferContainer;
import visual_signal_components.passive_rendering.AbstractTextPanel;
import visual_signal_components.passive_rendering.SignalPanel;

/**
 *
 * @author Administrator
 */

public class MultiChannelEEGPanel extends AbstractTextPanel{
    private Vector<SignalPanel> signalPanelVector = new Vector();
    private boolean initialized = false;
    private BasicStroke thickstroke = new BasicStroke(3);
    private BasicStroke thinstroke = new BasicStroke(1);
    private DoubleDataBufferContainer aDoubleDataBufferContainer;
    
    public MultiChannelEEGPanel() {
    }
    
    public Vector<SignalPanel> getSignalPanelVector() {
        return signalPanelVector;
    }
    
    public void initSignalPanel(){
        
        int yOffset = 0;
        //prepare the first 8 panels
        for (int indx = 0;indx<8;indx++){
            SignalPanel aSignalPanel = new SignalPanel();
            getSignalPanelVector().addElement(aSignalPanel);
            aSignalPanel.setHeight(getHeight()/10);
            aSignalPanel.setWidth(getWidth()/2-100);
            aSignalPanel.setSampleRate(1024);
            aSignalPanel.setBufferLength(1024);
            aSignalPanel.setMaxVoltage(25.0f);
            aSignalPanel.setTitleColor(Color.WHITE);
            aSignalPanel.setSignalColor(Color.RED);
            aSignalPanel.setBorderColor(Color.WHITE);
            aSignalPanel.setLocation(getX()+70,getY()+20+yOffset);
            yOffset+=(getHeight()/10)+20;
        }
        
        //now the next 8...
        yOffset = 0;
        
        for (int indx = 8;indx<16;indx++){
            SignalPanel aSignalPanel = new SignalPanel();
            getSignalPanelVector().addElement(aSignalPanel);
            aSignalPanel.setHeight(getHeight()/10);
            aSignalPanel.setWidth(getWidth()/2-100);
            aSignalPanel.setSampleRate(1024);
            aSignalPanel.setBufferLength(1024);
            aSignalPanel.setMaxVoltage(25.0f);
            aSignalPanel.setTitleColor(Color.WHITE);
            aSignalPanel.setSignalColor(Color.RED);
            aSignalPanel.setBorderColor(Color.WHITE);
            aSignalPanel.setLocation(getX()+50+aSignalPanel.getWidth()+80,getY()+20+yOffset);
            yOffset+=(getHeight()/10)+20;
        }
    }
    
    public DoubleDataBufferContainer getDoubleDataBufferContainer() {
        return aDoubleDataBufferContainer;
    }
    
    public void setDoubleDataBufferContainer(DoubleDataBufferContainer aDoubleDataBufferContainer) {
        this.aDoubleDataBufferContainer = aDoubleDataBufferContainer;
    }
    
    private void setSignalPanelVector(Vector<SignalPanel> signalPanelVector) {
        this.signalPanelVector = signalPanelVector;
    }
    
    private SignalPanel getSignalPanel(int index){
        return getSignalPanelVector().get(index);
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
        
        if(!initialized){
            try{
                for (int indx = 0;indx<getSignalPanelVector().size();indx++){
                    g2.setStroke(thickstroke);
                    getSignalPanel(indx).setGraphics2D(g2);
                    getSignalPanel(indx).setTitle("Channel: "+(indx+1));
                    g2.setStroke(thinstroke);
                    getSignalPanel(indx).setBufferLength(getDoubleDataBufferContainer().getBufferLength());
                    getSignalPanel(indx).setMaxVoltage(getDoubleDataBufferContainer().getDataBuffer(indx).getMaxVoltage());
                    getSignalPanel(indx).setUnit(getDoubleDataBufferContainer().getDataBuffer(indx).getUnit());
                }
            } catch(Exception ex){System.out.println(ex);};
            initialized=true;
        }
        try{
            for (int indx = 0;indx<getSignalPanelVector().size();indx++){
                getSignalPanel(indx).setGraphics2D(g2);
                g2.setStroke(thinstroke);
                getSignalPanel(indx).drawSignal(getDoubleDataBufferContainer().getDataBuffer(indx).toArray());
                g2.setStroke(thickstroke);
                getSignalPanel(indx).drawPanel();
                getSignalPanel(indx).drawTitle();
            }
        } catch(Exception ex){System.out.println(ex);};
    }
}