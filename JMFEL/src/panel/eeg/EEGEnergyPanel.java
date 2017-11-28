/*
 * EEGEnergyPanel.java
 *
 * Created on 28. Mai 2007, 14:06
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
import visual_signal_components.passive_rendering.PercentageBar;

/**
 *
 * @author Administrator
 */
public class EEGEnergyPanel extends AbstractTextPanel implements Observer{
    
    private boolean initialized = false;
    private BasicStroke thickstroke = new BasicStroke(3);
    private BasicStroke thinstroke = new BasicStroke(1);
    private PercentageBar deltaEnergyBar = new PercentageBar();
    private PercentageBar thetaEnergyBar = new PercentageBar();
    private PercentageBar alphaEnergyBar = new PercentageBar();
    private PercentageBar betaEnergyBar = new PercentageBar();
    private PercentageBar gammaEnergyBar = new PercentageBar();
    private Color light_blue = new Color(187,187,210,128);
    private int currentChannel = 1;
    private EEGDataModel eegData;
    private double normAlphaEnergy;
    private double normBetaEnergy;
    private double normGammaEnergy;
    private double normDeltaEnergy;
    private double normThetaEnergy;
    private boolean percentagesEnabled = false;
    
    /** Creates a new instance of MultiBandEEGPanel */
    public EEGEnergyPanel() {
        initSignalPanel();
    }
    
    public boolean isPercentagesEnabled() {
        return percentagesEnabled;
    }
    
    public void setPercentagesEnabled(boolean percentagesEnabled) {
        this.percentagesEnabled = percentagesEnabled;
    }
    
    public EEGDataModel getEEGData() {
        return eegData;
    }
    
    public void setEEGData(EEGDataModel eegData) {
        this.eegData = eegData;
    }
    
    public void update(Observable o, Object arg) {
        eegData = (EEGDataModel)o;
        double alphaEnergy = eegData.getAlphaEnergy(currentChannel-1);
        double betaEnergy = eegData.getBetaEnergy(currentChannel-1);
        double gammaEnergy = eegData.getGammaEnergy(currentChannel-1);
        double deltaEnergy = eegData.getDeltaEnergy(currentChannel-1);
        double thetaEnergy = eegData.getThetaEnergy(currentChannel-1);
        
        //I am aware, that this is NOT completely correct, but for the sake of viewing, it is ok.
        double totalEnergy = alphaEnergy+betaEnergy+gammaEnergy+deltaEnergy+thetaEnergy;
        normAlphaEnergy = alphaEnergy/totalEnergy;
        normBetaEnergy = betaEnergy/totalEnergy;
        normGammaEnergy = gammaEnergy/totalEnergy;
        normDeltaEnergy = deltaEnergy/totalEnergy;
        normThetaEnergy = thetaEnergy/totalEnergy;
    }
    
    public void initSignalPanel(){
        
        deltaEnergyBar.setTitleColor(Color.WHITE);
        deltaEnergyBar.setBorderColor(light_blue);
        deltaEnergyBar.setBarColor(light_blue);
        
        thetaEnergyBar.setTitleColor(Color.WHITE);
        thetaEnergyBar.setBorderColor(light_blue);
        thetaEnergyBar.setBarColor(light_blue);
        
        alphaEnergyBar.setTitleColor(Color.WHITE);
        alphaEnergyBar.setBorderColor(light_blue);
        alphaEnergyBar.setBarColor(light_blue);
        
        betaEnergyBar.setTitleColor(Color.WHITE);
        betaEnergyBar.setBorderColor(light_blue);
        betaEnergyBar.setBarColor(light_blue);
        
        gammaEnergyBar.setTitleColor(Color.WHITE);
        gammaEnergyBar.setBorderColor(light_blue);
        gammaEnergyBar.setBarColor(light_blue);
        
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
        
        //g2.setComposite(ac);
        g2.setColor(Color.DARK_GRAY);
        g2.drawRoundRect(getX()+2,getY()+2,getWidth(),getHeight(),8,8);
        g2.setColor(Color.WHITE);
        //g2.fillRoundRect(getX(),getY(),getWidth(),getHeight(),8,8);
        //g2.setComposite(originalComposite);
        g2.setColor(getColor());
        g2.drawRoundRect(getX(),getY(),getWidth(),getHeight(),8,8);
        
        deltaEnergyBar.setGraphics2D(g2);
        deltaEnergyBar.setTitle("Delta 0 - 3 Hz");
        thetaEnergyBar.setGraphics2D(g2);
        thetaEnergyBar.setTitle("Theta 4 - 7 Hz");
        alphaEnergyBar.setGraphics2D(g2);
        alphaEnergyBar.setTitle("Alpha 8 - 13 Hz");
        betaEnergyBar.setGraphics2D(g2);
        betaEnergyBar.setTitle("Beta 14 - 38 Hz");
        gammaEnergyBar.setGraphics2D(g2);
        gammaEnergyBar.setTitle("Gamma 39 - 60 Hz");
        
        deltaEnergyBar.setHeight(getHeight()/7);
        deltaEnergyBar.setWidth(getWidth()-50);
        deltaEnergyBar.setLocation(getX()+25,getY()+15);
        
        thetaEnergyBar.setHeight(getHeight()/7);
        thetaEnergyBar.setWidth(getWidth()-50);
        thetaEnergyBar.setLocation(getX()+25,deltaEnergyBar.getLocationY()+deltaEnergyBar.getHeight()+10);
        
        alphaEnergyBar.setHeight(getHeight()/7);
        alphaEnergyBar.setWidth(getWidth()-50);
        alphaEnergyBar.setLocation(getX()+25,thetaEnergyBar.getLocationY()+thetaEnergyBar.getHeight()+10);
        
        betaEnergyBar.setHeight(getHeight()/7);
        betaEnergyBar.setWidth(getWidth()-50);
        betaEnergyBar.setLocation(getX()+25,alphaEnergyBar.getLocationY()+alphaEnergyBar.getHeight()+10);
        
        gammaEnergyBar.setHeight(getHeight()/7);
        gammaEnergyBar.setWidth(getWidth()-50);
        gammaEnergyBar.setLocation(getX()+25,betaEnergyBar.getLocationY()+betaEnergyBar.getHeight()+10);
        
        
        //ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.9f);
        //g2.setComposite(ac);
        if(!isPercentagesEnabled()){
            deltaEnergyBar.setEnablePercentageValue(false);
            thetaEnergyBar.setEnablePercentageValue(false);
            betaEnergyBar.setEnablePercentageValue(false);
            alphaEnergyBar.setEnablePercentageValue(false);
            gammaEnergyBar.setEnablePercentageValue(false);
        }
        
        deltaEnergyBar.drawPanel();
        
        deltaEnergyBar.drawPercentage(normDeltaEnergy);
        
        thetaEnergyBar.drawPanel();
        
        thetaEnergyBar.drawPercentage(normThetaEnergy);
        
        alphaEnergyBar.drawPanel();
        
        alphaEnergyBar.drawPercentage(normAlphaEnergy);
        
        betaEnergyBar.drawPanel();
        
        betaEnergyBar.drawPercentage(normBetaEnergy);
        
        gammaEnergyBar.drawPanel();
        
        gammaEnergyBar.drawPercentage(normGammaEnergy);
        //g2.setComposite(originalComposite);
        
        deltaEnergyBar.drawTitle();
        thetaEnergyBar.drawTitle();
        alphaEnergyBar.drawTitle();
        betaEnergyBar.drawTitle();
        gammaEnergyBar.drawTitle();
    }
}
