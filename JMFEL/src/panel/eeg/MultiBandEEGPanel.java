/*
 * MultiBandEEGPanel.java
 *
 * Created on 28. Mai 2007, 12:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package panel.eeg;

import eeg.EEGDataModel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;
import visual_signal_components.passive_rendering.AbstractTextPanel;
import visual_signal_components.passive_rendering.SignalPanel;

/**
 *
 * @author Administrator
 */
public class MultiBandEEGPanel extends AbstractTextPanel implements Observer{
    
    private boolean initialized = false;
    private BasicStroke thickstroke = new BasicStroke(3);
    private BasicStroke thinstroke = new BasicStroke(1);
    private SignalPanel deltaSignalPanel;
    private SignalPanel thetaSignalPanel;
    private SignalPanel alphaSignalPanel;
    private SignalPanel betaSignalPanel;
    private SignalPanel gammaSignalPanel;
    private double[] thetaSignal = new double[1024];
    private double[] alphaSignal= new double[1024];
    private double[] betaSignal= new double[1024];
    private double[] gammaSignal= new double[1024];
    private double[] deltaSignal= new double[1024];
    
    private int currentChannel = 1;
    private EEGDataModel eegData;
    
    /** Creates a new instance of MultiBandEEGPanel */
    public MultiBandEEGPanel() {
        initSignalPanel();
    }
    
    public void update(Observable o, Object arg) {
        eegData = (EEGDataModel)o;
        thetaSignal = eegData.getThetaSignal(currentChannel-1);
        alphaSignal = eegData.getAlphaSignal(currentChannel-1);
        betaSignal = eegData.getBetaSignal(currentChannel-1);
        gammaSignal = eegData.getGammaSignal(currentChannel-1);
        deltaSignal = eegData.getDeltaSignal(currentChannel-1);
    }
    
    public EEGDataModel getEEGData() {
        return eegData;
    }
    
    public void setEEGData(EEGDataModel eegData) {
        this.eegData = eegData;
    }
    
    public SignalPanel getAlphaSignalPanel() {
        return alphaSignalPanel;
    }
    
    public SignalPanel getBetaSignalPanel() {
        return betaSignalPanel;
    }
    
    public SignalPanel getDeltaSignalPanel() {
        return deltaSignalPanel;
    }
    
    public SignalPanel getGammaSignalPanel() {
        return gammaSignalPanel;
    }
    
    public SignalPanel getThetaSignalPanel() {
        return thetaSignalPanel;
    }
    
    public void setAlphaSignalPanel(SignalPanel alphaSignalPanel) {
        this.alphaSignalPanel = alphaSignalPanel;
    }
    
    public void setBetaSignalPanel(SignalPanel betaSignalPanel) {
        this.betaSignalPanel = betaSignalPanel;
    }
    
    public void setDeltaSignalPanel(SignalPanel deltaSignalPanel) {
        this.deltaSignalPanel = deltaSignalPanel;
    }
    
    public void setGammaSignalPanel(SignalPanel gammaSignalPanel) {
        this.gammaSignalPanel = gammaSignalPanel;
    }
    
    public void initSignalPanel(){
        deltaSignalPanel = new SignalPanel();
        deltaSignalPanel.setMaxVoltage(25.0f);
        deltaSignalPanel.setSampleRate(1024);
        deltaSignalPanel.setBufferLength(1024);
        deltaSignalPanel.setTitleColor(Color.WHITE);
        deltaSignalPanel.setSignalColor(Color.RED);
        deltaSignalPanel.setBorderColor(Color.WHITE);
        
        
        thetaSignalPanel = new SignalPanel();
        thetaSignalPanel.setMaxVoltage(25.0f);
        thetaSignalPanel.setSampleRate(1024);
        thetaSignalPanel.setBufferLength(1024);
        thetaSignalPanel.setTitleColor(Color.WHITE);
        thetaSignalPanel.setSignalColor(Color.RED);
        thetaSignalPanel.setBorderColor(Color.WHITE);
        
        
        alphaSignalPanel = new SignalPanel();
        alphaSignalPanel.setMaxVoltage(25.0f);
        alphaSignalPanel.setSampleRate(1024);
        alphaSignalPanel.setBufferLength(1024);
        alphaSignalPanel.setTitleColor(Color.WHITE);
        alphaSignalPanel.setSignalColor(Color.RED);
        alphaSignalPanel.setBorderColor(Color.WHITE);
        
        
        betaSignalPanel = new SignalPanel();
        betaSignalPanel.setMaxVoltage(25.0f);
        betaSignalPanel.setSampleRate(1024);
        betaSignalPanel.setBufferLength(1024);
        betaSignalPanel.setTitleColor(Color.WHITE);
        betaSignalPanel.setSignalColor(Color.RED);
        betaSignalPanel.setBorderColor(Color.WHITE);
        
        
        gammaSignalPanel = new SignalPanel();
        gammaSignalPanel.setMaxVoltage(25.0f);
        gammaSignalPanel.setSampleRate(1024);
        gammaSignalPanel.setBufferLength(1024);
        gammaSignalPanel.setTitleColor(Color.WHITE);
        gammaSignalPanel.setSignalColor(Color.RED);
        gammaSignalPanel.setBorderColor(Color.WHITE);
        
    }
    
    public int getCurrentChannel() {
        return currentChannel;
    }
    
    public void setCurrentChannel(int currentChannel) {
        this.currentChannel = currentChannel;
    }
    
    public void renderGraphics(Graphics2D g2) {
        //Composite originalComposite = g2.getComposite();
        //AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f);
        try{
            //g2.setComposite(ac);
            g2.setColor(Color.DARK_GRAY);
            g2.drawRoundRect(getX()+2,getY()+2,getWidth(),getHeight(),8,8);
            g2.setColor(Color.WHITE);
            //g2.fillRoundRect(getX(),getY(),getWidth(),getHeight(),8,8);
            //g2.setComposite(originalComposite);
            g2.setColor(getColor());
            g2.drawRoundRect(getX(),getY(),getWidth(),getHeight(),8,8);
            
            deltaSignalPanel.setGraphics2D(g2);
            thetaSignalPanel.setGraphics2D(g2);
            alphaSignalPanel.setGraphics2D(g2);
            betaSignalPanel.setGraphics2D(g2);
            gammaSignalPanel.setGraphics2D(g2);
            
            if(!initialized){
                deltaSignalPanel.setHeight(getHeight()/7);
                deltaSignalPanel.setWidth(getWidth()-100);
                deltaSignalPanel.setLocation(getX()+50,getY()+20);
                
                thetaSignalPanel.setHeight(getHeight()/7);
                thetaSignalPanel.setWidth(getWidth()-100);
                thetaSignalPanel.setLocation(getX()+50,deltaSignalPanel.getLocationY()+deltaSignalPanel.getHeight()+20);
                
                alphaSignalPanel.setHeight(getHeight()/7);
                alphaSignalPanel.setWidth(getWidth()-100);
                alphaSignalPanel.setLocation(getX()+50,thetaSignalPanel.getLocationY()+thetaSignalPanel.getHeight()+20);
                
                betaSignalPanel.setHeight(getHeight()/7);
                betaSignalPanel.setWidth(getWidth()-100);
                betaSignalPanel.setLocation(getX()+50,alphaSignalPanel.getLocationY()+alphaSignalPanel.getHeight()+20);
                
                gammaSignalPanel.setHeight(getHeight()/7);
                gammaSignalPanel.setWidth(getWidth()-100);
                gammaSignalPanel.setLocation(getX()+50,betaSignalPanel.getLocationY()+betaSignalPanel.getHeight()+20);
                
                deltaSignalPanel.setTitle("Delta 0 - 3 Hz");
                thetaSignalPanel.setTitle("Theta 4 - 7 Hz");
                alphaSignalPanel.setTitle("Alpha 8 - 13 Hz");
                betaSignalPanel.setTitle("Beta 14 - 38 Hz");
                gammaSignalPanel.setTitle("Gamma 39 - 60 Hz");
                initialized = true;
            }
            
            g2.setStroke(thickstroke);
            deltaSignalPanel.drawPanel();
            deltaSignalPanel.drawTitle();
            g2.setStroke(thinstroke);
            deltaSignalPanel.setBufferLength(eegData.getDoubleDataBufferContainer().getBufferLength());
            deltaSignalPanel.setMaxVoltage(eegData.getDoubleDataBufferContainer().getDataBuffer(currentChannel-1).getMaxVoltage());
            deltaSignalPanel.setUnit(eegData.getDoubleDataBufferContainer().getDataBuffer(currentChannel-1).getUnit());
            deltaSignalPanel.drawSignal(deltaSignal);
            g2.setStroke(thickstroke);
            thetaSignalPanel.drawPanel();
            thetaSignalPanel.drawTitle();
            g2.setStroke(thinstroke);
            thetaSignalPanel.setBufferLength(eegData.getDoubleDataBufferContainer().getBufferLength());
            thetaSignalPanel.setMaxVoltage(eegData.getDoubleDataBufferContainer().getDataBuffer(currentChannel-1).getMaxVoltage());
            thetaSignalPanel.setUnit(eegData.getDoubleDataBufferContainer().getDataBuffer(currentChannel-1).getUnit());
            thetaSignalPanel.drawSignal(thetaSignal);
            g2.setStroke(thickstroke);
            alphaSignalPanel.drawPanel();
            alphaSignalPanel.drawTitle();
            g2.setStroke(thinstroke);
            alphaSignalPanel.setBufferLength(eegData.getDoubleDataBufferContainer().getBufferLength());
            alphaSignalPanel.setMaxVoltage(eegData.getDoubleDataBufferContainer().getDataBuffer(currentChannel-1).getMaxVoltage());
            alphaSignalPanel.setUnit(eegData.getDoubleDataBufferContainer().getDataBuffer(currentChannel-1).getUnit());
            alphaSignalPanel.drawSignal(alphaSignal);
            g2.setStroke(thickstroke);
            betaSignalPanel.drawPanel();
            betaSignalPanel.drawTitle();
            g2.setStroke(thinstroke);
            betaSignalPanel.setBufferLength(eegData.getDoubleDataBufferContainer().getBufferLength());
            betaSignalPanel.setMaxVoltage(eegData.getDoubleDataBufferContainer().getDataBuffer(currentChannel-1).getMaxVoltage());
            betaSignalPanel.setUnit(eegData.getDoubleDataBufferContainer().getDataBuffer(currentChannel-1).getUnit());
            betaSignalPanel.drawSignal(betaSignal);
            g2.setStroke(thickstroke);
            gammaSignalPanel.drawPanel();
            gammaSignalPanel.drawTitle();
            g2.setStroke(thinstroke);
            gammaSignalPanel.setBufferLength(eegData.getDoubleDataBufferContainer().getBufferLength());
            gammaSignalPanel.setMaxVoltage(eegData.getDoubleDataBufferContainer().getDataBuffer(currentChannel-1).getMaxVoltage());
            gammaSignalPanel.setUnit(eegData.getDoubleDataBufferContainer().getDataBuffer(currentChannel-1).getUnit());
            gammaSignalPanel.drawSignal(gammaSignal);
        }catch(Exception e){};
    }
}