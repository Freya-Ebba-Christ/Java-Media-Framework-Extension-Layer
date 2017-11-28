/*
 * EyeTrackerBlinkPanel.java
 *
 * Created on 16. Mai 2007, 22:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package panel.eye;
import java.awt.Color;
import java.awt.Graphics2D;
import utilities.Callable;
import visual_signal_components.passive_rendering.AbstractTextPanel;
import visual_signal_components.passive_rendering.SignalPanel;

/**
 *
 * @author Administrator
 */
public class EyeTrackerBlinkPanel extends AbstractTextPanel implements Callable{
    
    private SignalPanel signalPanel = new SignalPanel();
    private boolean registered = false;
    private double[] array_channel0;
    private double[] array_channel1;
    
    /** Creates a new instance of EyeTrackerBlinkPanel */
    public EyeTrackerBlinkPanel() {
    }
    
    public SignalPanel getSignalPanel() {
        return signalPanel;
    }
    
    public void setSignalPanel(SignalPanel signalPanel) {
        this.signalPanel = signalPanel;
    }
    
    public void performAction() {
        array_channel0 = signalPanel.getDoubleDataBufferContainer().getDataBuffer(0).toArray();
        array_channel1 = signalPanel.getDoubleDataBufferContainer().getDataBuffer(1).toArray();
    }
    
    public void performAction(int id) {
    }
    
    public void renderGraphics(Graphics2D g2) {
        
        int stringHeight = getStringHelper().getStringHeight(getText());
        int stringWidth = getStringHelper().getStringWidth(g2.getFont(),getText());
        //Composite originalComposite = g2.getComposite();
        //AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f);
        //g2.setComposite(ac);
        g2.setColor(Color.DARK_GRAY);
        g2.drawRoundRect(getX()+2,getY()+2,getWidth(),getHeight(),8,8);
        g2.setClip(getX(),getY(),getWidth(),getHeight());
        g2.setColor(Color.LIGHT_GRAY);
        if(!registered){
            array_channel0 = new double[signalPanel.getBufferLength()];
            array_channel1 = new double[signalPanel.getBufferLength()];
            signalPanel.getDoubleDataBufferContainer().getDataBuffer(0).registerCallback(this);
            signalPanel.getDoubleDataBufferContainer().getDataBuffer(1).registerCallback(this);
            registered=true;
        }
        
        //g2.fillRoundRect(getX(),getY(),getWidth(),getHeight(),8,8);
        //g2.setComposite(originalComposite);
        g2.setColor(getColor());
        g2.drawRoundRect(getX(),getY(),getWidth(),getHeight(),8,8);
        //make the signal panel just a little bit smaller
        signalPanel.setWidth(getWidth()-10);
        signalPanel.setHeight(getHeight()-10);
        signalPanel.setLocation(getX()+5,getY()+5);
        signalPanel.setGraphics2D(g2);
        signalPanel.drawPanel();
        signalPanel.setSignalColor(Color.RED);
        signalPanel.drawSignal(array_channel0);
        signalPanel.setSignalColor(Color.BLUE);
        signalPanel.drawSignal(array_channel1);
        g2.setColor(Color.BLUE);
        g2.drawString("Left Eye",getX()+20,getY()+40);
        g2.setColor(Color.RED);
        g2.drawString("Right Eye",getX()+20,getY()+60);
    }
}