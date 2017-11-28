/*
 * ECGPanel.java
 *
 * Created on 15. November 2007, 15:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package panel.ecg;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import utilities.DoubleDataBufferContainer;
import utilities.math.Rounding;
import visual_signal_components.passive_rendering.AbstractTextPanel;
import visual_signal_components.passive_rendering.SignalPanel;

/**
 *
 * @author Administrator
 */
public class ECGPanel extends AbstractTextPanel{
    
    private boolean initialized = false;
    private BasicStroke thickstroke = new BasicStroke(3);
    private BasicStroke thinstroke = new BasicStroke(1);
    private SignalPanel signalPanel = new SignalPanel();
    private int ecgChannel = 0;
    private DoubleDataBufferContainer aDoubleDataBufferContainer;
    private Font font = new Font("Arial Black", Font.BOLD, 20);
    private int heartRate = 0;
    private int peakCounter = 0;
    private boolean withinPeak = false;
    private boolean lastPeakState = false;
    
    public ECGPanel() {
    }
    
    public void setECGChannel(int ecgChannel) {
        this.ecgChannel = ecgChannel;
    }
    
    public int getECGChannel() {
        return ecgChannel;
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
        signalPanel.setTitle("Heart rate monitor");
        signalPanel.setTitleColor(Color.WHITE);
        g2.setStroke(thinstroke);
        signalPanel.setBufferLength(getDoubleDataBufferContainer().getBufferLength());
        signalPanel.setMaxVoltage(getDoubleDataBufferContainer().getDataBuffer(getECGChannel()).getMaxVoltage());
        signalPanel.setUnit(getDoubleDataBufferContainer().getDataBuffer(getECGChannel()).getUnit());
        int bufferSize = getDoubleDataBufferContainer().getDataBuffer(getECGChannel()).getDataBufferSize();
        double[] signalArray = new double[bufferSize];
        System.arraycopy(getDoubleDataBufferContainer().getDataBuffer(getECGChannel()).toArray(),0,signalArray,0,bufferSize);
        
        double maximum = 0.0;
        
        for (int i = 0; i < bufferSize; i++) {
            maximum = Math.max(maximum,signalArray[i]);
        }
        
        int peakDistance = 0;
        
        for (int i = 0; i < bufferSize; i++) {
            double value = signalArray[i]-maximum/2.0;
            if(value>0){
                signalArray[i] = getDoubleDataBufferContainer().getDataBuffer(getECGChannel()).getMaxVoltage();
                withinPeak = true;
            }else if((value<=0)){
                signalArray[i] = 0.0;
                withinPeak = false;
            }
            if(lastPeakState&&!withinPeak){
                peakCounter++;
            }
            lastPeakState = withinPeak;
        }
        heartRate = peakCounter*6;
        peakCounter=0;
        signalPanel.setSignalColor(Color.RED);
        signalPanel.drawSignal(signalArray);
        signalPanel.setSignalColor(Color.WHITE);
        signalPanel.drawSignal(getDoubleDataBufferContainer().getDataBuffer(getECGChannel()).toArray());
        g2.setStroke(thickstroke);
        signalPanel.drawPanel();
        signalPanel.drawTitle();
        g2.setStroke(thickstroke);
        g2.setFont(font);
        g2.setColor(Color.WHITE);
        g2.drawString("Heart rate: "+heartRate,getX()+getWidth()-250,getY()+getHeight()-50);
    }
}