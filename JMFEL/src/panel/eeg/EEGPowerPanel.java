/*
 * EEGPowerPanel.java
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
public class EEGPowerPanel extends AbstractTextPanel implements Observer{
    
    private boolean initialized = false;
    private BasicStroke thickstroke = new BasicStroke(3);
    private BasicStroke thinstroke = new BasicStroke(1);
    private PercentageBar deltaPowerBar = new PercentageBar();
    private PercentageBar thetaPowerBar = new PercentageBar();
    private PercentageBar alphaPowerBar = new PercentageBar();
    private PercentageBar betaPowerBar = new PercentageBar();
    private PercentageBar gammaPowerBar = new PercentageBar();
    private Color light_blue = new Color(187,187,210,128);
    private int currentChannel = 1;
    private EEGDataModel eegData;
    private double normAlphaPower;
    private double normBetaPower;
    private double normGammaPower;
    private double normDeltaPower;
    private double normThetaPower;
    private boolean percentagesEnabled = false;
    
    /** Creates a new instance of MultiBandEEGPanel */
    public EEGPowerPanel() {
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
        
        double alphaPower = eegData.getAlphaPower(currentChannel-1);
        double betaPower = eegData.getBetaPower(currentChannel-1);
        double gammaPower = eegData.getGammaPower(currentChannel-1);
        double deltaPower = eegData.getDeltaPower(currentChannel-1);
        double thetaPower = eegData.getThetaPower(currentChannel-1);
        
        //I am aware, that this is NOT completely correct, but for the sake of viewing, it is ok.
        double totalPower = alphaPower+betaPower+gammaPower+deltaPower+thetaPower;
        normAlphaPower = alphaPower/totalPower;
        normBetaPower = betaPower/totalPower;
        normGammaPower = gammaPower/totalPower;
        normDeltaPower = deltaPower/totalPower;
        normThetaPower = thetaPower/totalPower;
    }
    
    public void initSignalPanel(){
        
        deltaPowerBar.setTitleColor(Color.WHITE);
        deltaPowerBar.setBorderColor(light_blue);
        deltaPowerBar.setBarColor(light_blue);
        
        thetaPowerBar.setTitleColor(Color.WHITE);
        thetaPowerBar.setBorderColor(light_blue);
        thetaPowerBar.setBarColor(light_blue);
        
        alphaPowerBar.setTitleColor(Color.WHITE);
        alphaPowerBar.setBorderColor(light_blue);
        alphaPowerBar.setBarColor(light_blue);
        
        betaPowerBar.setTitleColor(Color.WHITE);
        betaPowerBar.setBorderColor(light_blue);
        betaPowerBar.setBarColor(light_blue);
        
        gammaPowerBar.setTitleColor(Color.WHITE);
        gammaPowerBar.setBorderColor(light_blue);
        gammaPowerBar.setBarColor(light_blue);
        
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
        
        
        deltaPowerBar.setGraphics2D(g2);
        deltaPowerBar.setTitle("Delta 0 - 3 Hz");
        thetaPowerBar.setGraphics2D(g2);
        thetaPowerBar.setTitle("Theta 4 - 7 Hz");
        alphaPowerBar.setGraphics2D(g2);
        alphaPowerBar.setTitle("Alpha 8 - 13 Hz");
        betaPowerBar.setGraphics2D(g2);
        betaPowerBar.setTitle("Beta 14 - 38 Hz");
        gammaPowerBar.setGraphics2D(g2);
        gammaPowerBar.setTitle("Gamma 39 - 60 Hz");
        
        deltaPowerBar.setHeight(getHeight()/7);
        deltaPowerBar.setWidth(getWidth()-50);
        deltaPowerBar.setLocation(getX()+25,getY()+15);
        
        thetaPowerBar.setHeight(getHeight()/7);
        thetaPowerBar.setWidth(getWidth()-50);
        thetaPowerBar.setLocation(getX()+25,deltaPowerBar.getLocationY()+deltaPowerBar.getHeight()+10);
        
        alphaPowerBar.setHeight(getHeight()/7);
        alphaPowerBar.setWidth(getWidth()-50);
        alphaPowerBar.setLocation(getX()+25,thetaPowerBar.getLocationY()+thetaPowerBar.getHeight()+10);
        
        betaPowerBar.setHeight(getHeight()/7);
        betaPowerBar.setWidth(getWidth()-50);
        betaPowerBar.setLocation(getX()+25,alphaPowerBar.getLocationY()+alphaPowerBar.getHeight()+10);
        
        gammaPowerBar.setHeight(getHeight()/7);
        gammaPowerBar.setWidth(getWidth()-50);
        gammaPowerBar.setLocation(getX()+25,betaPowerBar.getLocationY()+betaPowerBar.getHeight()+10);
        
        //ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.9f);
        //g2.setComposite(ac);
        
        
        if(!isPercentagesEnabled()){
            deltaPowerBar.setEnablePercentageValue(false);
            thetaPowerBar.setEnablePercentageValue(false);
            betaPowerBar.setEnablePercentageValue(false);
            alphaPowerBar.setEnablePercentageValue(false);
            gammaPowerBar.setEnablePercentageValue(false);
        }
        
        
        //remember, we are using the energy panels here...
        deltaPowerBar.drawPanel();
        deltaPowerBar.drawPercentage(normDeltaPower);
        
        thetaPowerBar.drawPanel();
        thetaPowerBar.drawPercentage(normThetaPower);
        
        alphaPowerBar.drawPanel();
        alphaPowerBar.drawPercentage(normAlphaPower);
        
        betaPowerBar.drawPanel();
        betaPowerBar.drawPercentage(normBetaPower);
        
        gammaPowerBar.drawPanel();
        gammaPowerBar.drawPercentage(normGammaPower);
        //g2.setComposite(originalComposite);
        
        deltaPowerBar.drawTitle();
        thetaPowerBar.drawTitle();
        alphaPowerBar.drawTitle();
        betaPowerBar.drawTitle();
        gammaPowerBar.drawTitle();
    }
}
