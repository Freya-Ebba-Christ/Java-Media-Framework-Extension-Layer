/*
 * AlphaBetaPanel.java
 *
 * Created on 22. August 2007, 17:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package panel.eeg;

import eeg.BasicEEGDataModel;
import eeg.utilities.ElectrodeAssignment;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;
import utilities.signal_processing.EEGSignalProcessor;
import visual_signal_components.passive_rendering.AbstractTextPanel;
import visual_signal_components.passive_rendering.PercentageBar;

/**
 *
 * @author Administrator
 */

public class AlphaBetaPanel extends AbstractTextPanel implements Observer{
    
    private boolean initialized = false;
    private BasicStroke thickstroke = new BasicStroke(3);
    private BasicStroke thinstroke = new BasicStroke(1);
    private Color alpha_color = new Color(200,255,200);
    private Color beta_color = new Color(255,200,200);
    
    private BasicEEGDataModel eegData;
    private int barHeight = 20;
    
    private PercentageBar c3_8To10HzPowerBar = new PercentageBar();
    private PercentageBar c3_10To12HzPowerBar = new PercentageBar();
    private PercentageBar c3_12To14HzPowerBar = new PercentageBar();
    private PercentageBar c3_14To16HzPowerBar = new PercentageBar();
    private PercentageBar c3_16To18HzPowerBar = new PercentageBar();
    private PercentageBar c3_18To20HzPowerBar = new PercentageBar();
    private PercentageBar c3_20To22HzPowerBar = new PercentageBar();
    private PercentageBar c3_22To24HzPowerBar = new PercentageBar();
    private PercentageBar c3_24To26HzPowerBar = new PercentageBar();
    private PercentageBar c3_26To28HzPowerBar = new PercentageBar();
    private PercentageBar c3_28To30HzPowerBar = new PercentageBar();
    private PercentageBar c3_30To32HzPowerBar = new PercentageBar();
    
    private PercentageBar c4_8To10HzPowerBar = new PercentageBar();
    private PercentageBar c4_10To12HzPowerBar = new PercentageBar();
    private PercentageBar c4_12To14HzPowerBar = new PercentageBar();
    private PercentageBar c4_14To16HzPowerBar = new PercentageBar();
    private PercentageBar c4_16To18HzPowerBar = new PercentageBar();
    private PercentageBar c4_18To20HzPowerBar = new PercentageBar();
    private PercentageBar c4_20To22HzPowerBar = new PercentageBar();
    private PercentageBar c4_22To24HzPowerBar = new PercentageBar();
    private PercentageBar c4_24To26HzPowerBar = new PercentageBar();
    private PercentageBar c4_26To28HzPowerBar = new PercentageBar();
    private PercentageBar c4_28To30HzPowerBar = new PercentageBar();
    private PercentageBar c4_30To32HzPowerBar = new PercentageBar();
    
    private double c4_8To10HzPower;
    private double c4_10To12HzPower;
    private double c4_12To14HzPower;
    private double c4_14To16HzPower;
    private double c4_16To18HzPower;
    private double c4_18To20HzPower;
    private double c4_20To22HzPower;
    private double c4_24To26HzPower;
    private double c4_26To28HzPower;
    private double c4_30To32HzPower;
    
    private double c3_8To10HzPower;
    private double c3_10To12HzPower;
    private double c3_12To14HzPower;
    private double c3_14To16HzPower;
    private double c3_16To18HzPower;
    private double c3_18To20HzPower;
    private double c3_20To22HzPower;
    private double c3_24To26HzPower;
    private double c3_26To28HzPower;
    private double c3_30To32HzPower;
    
    private double normc4_8To10HzPower;
    private double normc4_10To12HzPower;
    private double normc4_12To14HzPower;
    private double normc4_14To16HzPower;
    private double normc4_16To18HzPower;
    private double normc4_18To20HzPower;
    private double normc4_20To22HzPower;
    private double normc4_22To24HzPower;
    private double normc4_24To26HzPower;
    private double normc4_26To28HzPower;
    private double normc4_28To30HzPower;
    private double normc4_30To32HzPower;
    
    private double normc3_8To10HzPower;
    private double normc3_10To12HzPower;
    private double normc3_12To14HzPower;
    private double normc3_14To16HzPower;
    private double normc3_16To18HzPower;
    private double normc3_18To20HzPower;
    private double normc3_20To22HzPower;
    private double normc3_22To24HzPower;
    private double normc3_24To26HzPower;
    private double normc3_26To28HzPower;
    private double normc3_28To30HzPower;
    private double normc3_30To32HzPower;
    
    private double c3Power = 0;
    private double c4Power = 0;
    
    private ElectrodeAssignment electrodeAssignment;
    
    public AlphaBetaPanel() {
    }
    
    private void initPanels(Graphics2D g2d){
        
        c3_8To10HzPowerBar.setTitleColor(Color.WHITE);
        c3_8To10HzPowerBar.setBorderColor(alpha_color);
        c3_8To10HzPowerBar.setBarColor(alpha_color);
        c3_8To10HzPowerBar.setDirection(PercentageBar.LEFT_RIGHT);
        c3_8To10HzPowerBar.setEnablePercentageValue(false);
        
        c3_10To12HzPowerBar.setTitleColor(Color.WHITE);
        c3_10To12HzPowerBar.setBorderColor(alpha_color);
        c3_10To12HzPowerBar.setBarColor(alpha_color);
        c3_10To12HzPowerBar.setDirection(PercentageBar.LEFT_RIGHT);
        c3_10To12HzPowerBar.setEnablePercentageValue(false);
        
        c3_12To14HzPowerBar.setTitleColor(Color.WHITE);
        c3_12To14HzPowerBar.setBorderColor(alpha_color);
        c3_12To14HzPowerBar.setBarColor(alpha_color);
        c3_12To14HzPowerBar.setDirection(PercentageBar.LEFT_RIGHT);
        c3_12To14HzPowerBar.setEnablePercentageValue(false);
        
        c3_14To16HzPowerBar.setTitleColor(Color.WHITE);
        c3_14To16HzPowerBar.setBorderColor(alpha_color);
        c3_14To16HzPowerBar.setBarColor(alpha_color);
        c3_14To16HzPowerBar.setDirection(PercentageBar.LEFT_RIGHT);
        c3_14To16HzPowerBar.setEnablePercentageValue(false);
        
        c3_16To18HzPowerBar.setTitleColor(Color.WHITE);
        c3_16To18HzPowerBar.setBorderColor(alpha_color);
        c3_16To18HzPowerBar.setBarColor(alpha_color);
        c3_16To18HzPowerBar.setDirection(PercentageBar.LEFT_RIGHT);
        c3_16To18HzPowerBar.setEnablePercentageValue(false);
        
        c3_18To20HzPowerBar.setTitleColor(Color.WHITE);
        c3_18To20HzPowerBar.setBorderColor(alpha_color);
        c3_18To20HzPowerBar.setBarColor(alpha_color);
        c3_18To20HzPowerBar.setDirection(PercentageBar.LEFT_RIGHT);
        c3_18To20HzPowerBar.setEnablePercentageValue(false);
        
        c3_20To22HzPowerBar.setTitleColor(Color.WHITE);
        c3_20To22HzPowerBar.setBorderColor(alpha_color);
        c3_20To22HzPowerBar.setBarColor(alpha_color);
        c3_20To22HzPowerBar.setDirection(PercentageBar.LEFT_RIGHT);
        c3_20To22HzPowerBar.setEnablePercentageValue(false);
        
        c3_22To24HzPowerBar.setTitleColor(Color.WHITE);
        c3_22To24HzPowerBar.setBorderColor(alpha_color);
        c3_22To24HzPowerBar.setBarColor(alpha_color);
        c3_22To24HzPowerBar.setDirection(PercentageBar.LEFT_RIGHT);
        c3_22To24HzPowerBar.setEnablePercentageValue(false);
        
        c3_24To26HzPowerBar.setTitleColor(Color.WHITE);
        c3_24To26HzPowerBar.setBorderColor(alpha_color);
        c3_24To26HzPowerBar.setBarColor(alpha_color);
        c3_24To26HzPowerBar.setDirection(PercentageBar.LEFT_RIGHT);
        c3_24To26HzPowerBar.setEnablePercentageValue(false);
        
        c3_26To28HzPowerBar.setTitleColor(Color.WHITE);
        c3_26To28HzPowerBar.setBorderColor(alpha_color);
        c3_26To28HzPowerBar.setBarColor(alpha_color);
        c3_26To28HzPowerBar.setDirection(PercentageBar.LEFT_RIGHT);
        c3_26To28HzPowerBar.setEnablePercentageValue(false);
        
        c3_28To30HzPowerBar.setTitleColor(Color.WHITE);
        c3_28To30HzPowerBar.setBorderColor(alpha_color);
        c3_28To30HzPowerBar.setBarColor(alpha_color);
        c3_28To30HzPowerBar.setDirection(PercentageBar.LEFT_RIGHT);
        c3_28To30HzPowerBar.setEnablePercentageValue(false);
        
        c3_30To32HzPowerBar.setTitleColor(Color.WHITE);
        c3_30To32HzPowerBar.setBorderColor(alpha_color);
        c3_30To32HzPowerBar.setBarColor(alpha_color);
        c3_30To32HzPowerBar.setDirection(PercentageBar.LEFT_RIGHT);
        c3_30To32HzPowerBar.setEnablePercentageValue(false);
        
        /////////////////////////////////////////////////////////////
        
        c4_8To10HzPowerBar.setTitleColor(Color.WHITE);
        c4_8To10HzPowerBar.setBorderColor(alpha_color);
        c4_8To10HzPowerBar.setBarColor(alpha_color);
        c4_8To10HzPowerBar.setDirection(PercentageBar.RIGHT_LEFT);
        c4_8To10HzPowerBar.setEnablePercentageValue(false);
        
        c4_10To12HzPowerBar.setTitleColor(Color.WHITE);
        c4_10To12HzPowerBar.setBorderColor(alpha_color);
        c4_10To12HzPowerBar.setBarColor(alpha_color);
        c4_10To12HzPowerBar.setDirection(PercentageBar.RIGHT_LEFT);
        c4_10To12HzPowerBar.setEnablePercentageValue(false);
        
        c4_12To14HzPowerBar.setTitleColor(Color.WHITE);
        c4_12To14HzPowerBar.setBorderColor(alpha_color);
        c4_12To14HzPowerBar.setBarColor(alpha_color);
        c4_12To14HzPowerBar.setDirection(PercentageBar.RIGHT_LEFT);
        c4_12To14HzPowerBar.setEnablePercentageValue(false);
        
        c4_14To16HzPowerBar.setTitleColor(Color.WHITE);
        c4_14To16HzPowerBar.setBorderColor(alpha_color);
        c4_14To16HzPowerBar.setBarColor(alpha_color);
        c4_14To16HzPowerBar.setDirection(PercentageBar.RIGHT_LEFT);
        c4_14To16HzPowerBar.setEnablePercentageValue(false);
        
        c4_16To18HzPowerBar.setTitleColor(Color.WHITE);
        c4_16To18HzPowerBar.setBorderColor(alpha_color);
        c4_16To18HzPowerBar.setBarColor(alpha_color);
        c4_16To18HzPowerBar.setDirection(PercentageBar.RIGHT_LEFT);
        c4_16To18HzPowerBar.setEnablePercentageValue(false);
        
        c4_18To20HzPowerBar.setTitleColor(Color.WHITE);
        c4_18To20HzPowerBar.setBorderColor(alpha_color);
        c4_18To20HzPowerBar.setBarColor(alpha_color);
        c4_18To20HzPowerBar.setDirection(PercentageBar.RIGHT_LEFT);
        c4_18To20HzPowerBar.setEnablePercentageValue(false);
        
        c4_20To22HzPowerBar.setTitleColor(Color.WHITE);
        c4_20To22HzPowerBar.setBorderColor(alpha_color);
        c4_20To22HzPowerBar.setBarColor(alpha_color);
        c4_20To22HzPowerBar.setDirection(PercentageBar.RIGHT_LEFT);
        c4_20To22HzPowerBar.setEnablePercentageValue(false);
        
        c4_22To24HzPowerBar.setTitleColor(Color.WHITE);
        c4_22To24HzPowerBar.setBorderColor(alpha_color);
        c4_22To24HzPowerBar.setBarColor(alpha_color);
        c4_22To24HzPowerBar.setDirection(PercentageBar.RIGHT_LEFT);
        c4_22To24HzPowerBar.setEnablePercentageValue(false);
        
        c4_24To26HzPowerBar.setTitleColor(Color.WHITE);
        c4_24To26HzPowerBar.setBorderColor(alpha_color);
        c4_24To26HzPowerBar.setBarColor(alpha_color);
        c4_24To26HzPowerBar.setDirection(PercentageBar.RIGHT_LEFT);
        c4_24To26HzPowerBar.setEnablePercentageValue(false);
        
        c4_26To28HzPowerBar.setTitleColor(Color.WHITE);
        c4_26To28HzPowerBar.setBorderColor(alpha_color);
        c4_26To28HzPowerBar.setBarColor(alpha_color);
        c4_26To28HzPowerBar.setDirection(PercentageBar.RIGHT_LEFT);
        c4_26To28HzPowerBar.setEnablePercentageValue(false);
        
        c4_28To30HzPowerBar.setTitleColor(Color.WHITE);
        c4_28To30HzPowerBar.setBorderColor(alpha_color);
        c4_28To30HzPowerBar.setBarColor(alpha_color);
        c4_28To30HzPowerBar.setDirection(PercentageBar.RIGHT_LEFT);
        c4_28To30HzPowerBar.setEnablePercentageValue(false);
        
        c4_30To32HzPowerBar.setTitleColor(Color.WHITE);
        c4_30To32HzPowerBar.setBorderColor(alpha_color);
        c4_30To32HzPowerBar.setBarColor(alpha_color);
        c4_30To32HzPowerBar.setDirection(PercentageBar.RIGHT_LEFT);
        c4_30To32HzPowerBar.setEnablePercentageValue(false);
    }
    
    public ElectrodeAssignment getElectrodeAssignment() {
        return electrodeAssignment;
    }
    
    public void setElectrodeAssignment(ElectrodeAssignment electrodeAssignment) {
        this.electrodeAssignment = electrodeAssignment;
    }
    
    public BasicEEGDataModel getEEGData() {
        return eegData;
    }
    
    public void setEEGData(BasicEEGDataModel eegData) {
        this.eegData = eegData;
    }
    
    public void update(Observable o, Object arg) {
        eegData = (BasicEEGDataModel)o;

        double c3_8To10HzPower = EEGSignalProcessor.getInstance().getSignalPower(EEGSignalProcessor.getInstance().get8To10HzSignal(eegData.getSignal(electrodeAssignment.getC4())));
        double c3_10To12HzPower = EEGSignalProcessor.getInstance().getSignalPower(EEGSignalProcessor.getInstance().get10To12HzSignal(eegData.getSignal(electrodeAssignment.getC4())));
        double c3_12To14HzPower = EEGSignalProcessor.getInstance().getSignalPower(EEGSignalProcessor.getInstance().get12To14HzSignal(eegData.getSignal(electrodeAssignment.getC4())));
        double c3_14To16HzPower = EEGSignalProcessor.getInstance().getSignalPower(EEGSignalProcessor.getInstance().get14To16HzSignal(eegData.getSignal(electrodeAssignment.getC4())));
        double c3_16To18HzPower = EEGSignalProcessor.getInstance().getSignalPower(EEGSignalProcessor.getInstance().get16To18HzSignal(eegData.getSignal(electrodeAssignment.getC4())));
        double c3_18To20HzPower = EEGSignalProcessor.getInstance().getSignalPower(EEGSignalProcessor.getInstance().get18To20HzSignal(eegData.getSignal(electrodeAssignment.getC4())));
        double c3_20To22HzPower = EEGSignalProcessor.getInstance().getSignalPower(EEGSignalProcessor.getInstance().get20To22HzSignal(eegData.getSignal(electrodeAssignment.getC4())));
        double c3_22To24HzPower = EEGSignalProcessor.getInstance().getSignalPower(EEGSignalProcessor.getInstance().get22To24HzSignal(eegData.getSignal(electrodeAssignment.getC4())));
        double c3_24To26HzPower = EEGSignalProcessor.getInstance().getSignalPower(EEGSignalProcessor.getInstance().get24To26HzSignal(eegData.getSignal(electrodeAssignment.getC4())));
        double c3_26To28HzPower = EEGSignalProcessor.getInstance().getSignalPower(EEGSignalProcessor.getInstance().get26To28HzSignal(eegData.getSignal(electrodeAssignment.getC4())));
        double c3_28To30HzPower = EEGSignalProcessor.getInstance().getSignalPower(EEGSignalProcessor.getInstance().get28To30HzSignal(eegData.getSignal(electrodeAssignment.getC4())));
        double c3_30To32HzPower = EEGSignalProcessor.getInstance().getSignalPower(EEGSignalProcessor.getInstance().get30To32HzSignal(eegData.getSignal(electrodeAssignment.getC4())));
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        double c4_8To10HzPower = EEGSignalProcessor.getInstance().getSignalPower(EEGSignalProcessor.getInstance().get8To10HzSignal(eegData.getSignal(electrodeAssignment.getC4())));
        double c4_10To12HzPower = EEGSignalProcessor.getInstance().getSignalPower(EEGSignalProcessor.getInstance().get10To12HzSignal(eegData.getSignal(electrodeAssignment.getC4())));
        double c4_12To14HzPower = EEGSignalProcessor.getInstance().getSignalPower(EEGSignalProcessor.getInstance().get12To14HzSignal(eegData.getSignal(electrodeAssignment.getC4())));
        double c4_14To16HzPower = EEGSignalProcessor.getInstance().getSignalPower(EEGSignalProcessor.getInstance().get14To16HzSignal(eegData.getSignal(electrodeAssignment.getC4())));
        double c4_16To18HzPower = EEGSignalProcessor.getInstance().getSignalPower(EEGSignalProcessor.getInstance().get16To18HzSignal(eegData.getSignal(electrodeAssignment.getC4())));
        double c4_18To20HzPower = EEGSignalProcessor.getInstance().getSignalPower(EEGSignalProcessor.getInstance().get18To20HzSignal(eegData.getSignal(electrodeAssignment.getC4())));
        double c4_20To22HzPower = EEGSignalProcessor.getInstance().getSignalPower(EEGSignalProcessor.getInstance().get20To22HzSignal(eegData.getSignal(electrodeAssignment.getC4())));
        double c4_22To24HzPower = EEGSignalProcessor.getInstance().getSignalPower(EEGSignalProcessor.getInstance().get22To24HzSignal(eegData.getSignal(electrodeAssignment.getC4())));
        double c4_24To26HzPower = EEGSignalProcessor.getInstance().getSignalPower(EEGSignalProcessor.getInstance().get24To26HzSignal(eegData.getSignal(electrodeAssignment.getC4())));
        double c4_26To28HzPower = EEGSignalProcessor.getInstance().getSignalPower(EEGSignalProcessor.getInstance().get26To28HzSignal(eegData.getSignal(electrodeAssignment.getC4())));
        double c4_28To30HzPower = EEGSignalProcessor.getInstance().getSignalPower(EEGSignalProcessor.getInstance().get28To30HzSignal(eegData.getSignal(electrodeAssignment.getC4())));
        double c4_30To32HzPower = EEGSignalProcessor.getInstance().getSignalPower(EEGSignalProcessor.getInstance().get30To32HzSignal(eegData.getSignal(electrodeAssignment.getC4())));
        
        c3Power = c3_8To10HzPower+c3_10To12HzPower+c3_12To14HzPower+c3_14To16HzPower+c3_16To18HzPower+c3_18To20HzPower+c3_20To22HzPower+c3_22To24HzPower+c3_24To26HzPower+c3_26To28HzPower+c3_28To30HzPower+c3_30To32HzPower;
        c4Power = c4_8To10HzPower+c4_10To12HzPower+c4_12To14HzPower+c4_14To16HzPower+c4_16To18HzPower+c4_18To20HzPower+c4_20To22HzPower+c4_22To24HzPower+c4_24To26HzPower+c4_26To28HzPower+c4_28To30HzPower+c4_30To32HzPower;
        
        normc3_8To10HzPower = c3_8To10HzPower/c3Power;
        normc3_10To12HzPower = c3_10To12HzPower/c3Power;
        normc3_12To14HzPower = c3_12To14HzPower/c3Power;
        normc3_14To16HzPower = c3_14To16HzPower/c3Power;
        normc3_16To18HzPower = c3_16To18HzPower/c3Power;
        normc3_18To20HzPower = c3_18To20HzPower/c3Power;
        normc3_20To22HzPower = c3_20To22HzPower/c3Power;
        normc3_22To24HzPower = c3_22To24HzPower/c3Power;
        normc3_24To26HzPower = c3_24To26HzPower/c3Power;
        normc3_26To28HzPower = c3_26To28HzPower/c3Power;
        normc3_28To30HzPower = c3_28To30HzPower/c3Power;
        normc3_30To32HzPower = c3_30To32HzPower/c3Power;
        
        normc4_8To10HzPower = c4_8To10HzPower/c4Power;
        normc4_10To12HzPower = c4_10To12HzPower/c4Power;
        normc4_12To14HzPower = c4_12To14HzPower/c4Power;
        normc4_14To16HzPower = c4_14To16HzPower/c4Power;
        normc4_16To18HzPower = c4_16To18HzPower/c4Power;
        normc4_18To20HzPower = c4_18To20HzPower/c4Power;
        normc4_20To22HzPower = c4_20To22HzPower/c4Power;
        normc4_22To24HzPower = c4_22To24HzPower/c4Power;
        normc4_24To26HzPower = c4_24To26HzPower/c4Power;
        normc4_26To28HzPower = c4_26To28HzPower/c4Power;
        normc4_28To30HzPower = c4_28To30HzPower/c4Power;
        normc4_30To32HzPower = c4_30To32HzPower/c4Power;
    }
    
    public void renderGraphics(Graphics2D g2) {
        
        if(!initialized){
            initPanels(g2);
            initialized = true;
        }
        barHeight = getHeight()/20;
        
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0,getWidth()+20,getHeight()+20);
        
        g2.setColor(Color.WHITE);
        g2.drawString("Left",getX()+30,getY()+getHeight()-30);
        g2.drawString("Right",getX()+getWidth()-60,getY()+getHeight()-30);
        
        if(initialized){
            
            c3_8To10HzPowerBar.setGraphics2D(g2);
            c3_8To10HzPowerBar.setTitle("C3 8-10");
            c3_8To10HzPowerBar.setHeight(barHeight);
            c3_8To10HzPowerBar.setWidth(getWidth()/2);
            c3_8To10HzPowerBar.setLocation(getX()+(getWidth()/2),getY()+5+barHeight*2+10);
            
            c3_10To12HzPowerBar.setGraphics2D(g2);
            c3_10To12HzPowerBar.setTitle("C3 10-12");
            c3_10To12HzPowerBar.setHeight(barHeight);
            c3_10To12HzPowerBar.setWidth(getWidth()/2);
            c3_10To12HzPowerBar.setLocation(getX()+(getWidth()/2),getY()+5+barHeight*3+15);
            
            c3_12To14HzPowerBar.setGraphics2D(g2);
            c3_12To14HzPowerBar.setTitle("C3 12-14");
            c3_12To14HzPowerBar.setHeight(barHeight);
            c3_12To14HzPowerBar.setWidth(getWidth()/2);
            c3_12To14HzPowerBar.setLocation(getX()+(getWidth()/2),getY()+5+barHeight*4+20);
            
            c3_14To16HzPowerBar.setGraphics2D(g2);
            c3_14To16HzPowerBar.setTitle("C3 14-16");
            c3_14To16HzPowerBar.setHeight(barHeight);
            c3_14To16HzPowerBar.setWidth(getWidth()/2);
            c3_14To16HzPowerBar.setLocation(getX()+(getWidth()/2),getY()+5+barHeight*5+25);
            
            c3_16To18HzPowerBar.setGraphics2D(g2);
            c3_16To18HzPowerBar.setTitle("C3 16-18");
            c3_16To18HzPowerBar.setHeight(barHeight);
            c3_16To18HzPowerBar.setWidth(getWidth()/2);
            c3_16To18HzPowerBar.setLocation(getX()+(getWidth()/2),getY()+5+barHeight*6+30);
            
            c3_18To20HzPowerBar.setGraphics2D(g2);
            c3_18To20HzPowerBar.setTitle("C3 18-20");
            c3_18To20HzPowerBar.setHeight(barHeight);
            c3_18To20HzPowerBar.setWidth(getWidth()/2);
            c3_18To20HzPowerBar.setLocation(getX()+(getWidth()/2),getY()+5+barHeight*7+35);
            
            c3_20To22HzPowerBar.setGraphics2D(g2);
            c3_20To22HzPowerBar.setTitle("C3 20-22");
            c3_20To22HzPowerBar.setHeight(barHeight);
            c3_20To22HzPowerBar.setWidth(getWidth()/2);
            c3_20To22HzPowerBar.setLocation(getX()+(getWidth()/2),getY()+5+barHeight*8+40);
            
            c3_22To24HzPowerBar.setGraphics2D(g2);
            c3_22To24HzPowerBar.setTitle("C3 22-24");
            c3_22To24HzPowerBar.setHeight(barHeight);
            c3_22To24HzPowerBar.setWidth(getWidth()/2);
            c3_22To24HzPowerBar.setLocation(getX()+(getWidth()/2),getY()+5+barHeight*9+45);
            
            c3_24To26HzPowerBar.setGraphics2D(g2);
            c3_24To26HzPowerBar.setTitle("C3 24-26");
            c3_24To26HzPowerBar.setHeight(barHeight);
            c3_24To26HzPowerBar.setWidth(getWidth()/2);
            c3_24To26HzPowerBar.setLocation(getX()+(getWidth()/2),getY()+5+barHeight*10+50);
            
            c3_26To28HzPowerBar.setGraphics2D(g2);
            c3_26To28HzPowerBar.setTitle("C3 26-28");
            c3_26To28HzPowerBar.setHeight(barHeight);
            c3_26To28HzPowerBar.setWidth(getWidth()/2);
            c3_26To28HzPowerBar.setLocation(getX()+(getWidth()/2),getY()+5+barHeight*11+55);
            
            c3_28To30HzPowerBar.setGraphics2D(g2);
            c3_28To30HzPowerBar.setTitle("C3 28-30");
            c3_28To30HzPowerBar.setHeight(barHeight);
            c3_28To30HzPowerBar.setWidth(getWidth()/2);
            c3_28To30HzPowerBar.setLocation(getX()+(getWidth()/2),getY()+5+barHeight*12+60);
            
            c3_30To32HzPowerBar.setGraphics2D(g2);
            c3_30To32HzPowerBar.setTitle("C3 30-32");
            c3_30To32HzPowerBar.setHeight(barHeight);
            c3_30To32HzPowerBar.setWidth(getWidth()/2);
            c3_30To32HzPowerBar.setLocation(getX()+(getWidth()/2),getY()+5+barHeight*13+65);
            
            ///////////////////////////////////
            c4_8To10HzPowerBar.setGraphics2D(g2);
            c4_8To10HzPowerBar.setTitle("C4 8-10");
            c4_8To10HzPowerBar.setHeight(barHeight);
            c4_8To10HzPowerBar.setWidth(getWidth()/2);
            c4_8To10HzPowerBar.setLocation(getX()+(getWidth()/2)-(c4_8To10HzPowerBar.getWidth()),getY()+5+barHeight*2+10);
            
            c4_10To12HzPowerBar.setGraphics2D(g2);
            c4_10To12HzPowerBar.setTitle("C4 10-12");
            c4_10To12HzPowerBar.setHeight(barHeight);
            c4_10To12HzPowerBar.setWidth(getWidth()/2);
            c4_10To12HzPowerBar.setLocation(getX()+(getWidth()/2)-(c4_10To12HzPowerBar.getWidth()),getY()+5+barHeight*3+15);
            
            c4_12To14HzPowerBar.setGraphics2D(g2);
            c4_12To14HzPowerBar.setTitle("C4 12-14");
            c4_12To14HzPowerBar.setHeight(barHeight);
            c4_12To14HzPowerBar.setWidth(getWidth()/2);
            c4_12To14HzPowerBar.setLocation(getX()+(getWidth()/2)-(c4_12To14HzPowerBar.getWidth()),getY()+5+barHeight*4+20);
            
            c4_14To16HzPowerBar.setGraphics2D(g2);
            c4_14To16HzPowerBar.setTitle("C4 14-16");
            c4_14To16HzPowerBar.setHeight(barHeight);
            c4_14To16HzPowerBar.setWidth(getWidth()/2);
            c4_14To16HzPowerBar.setLocation(getX()+(getWidth()/2)-(c4_14To16HzPowerBar.getWidth()),getY()+5+barHeight*5+25);
            
            c4_16To18HzPowerBar.setGraphics2D(g2);
            c4_16To18HzPowerBar.setTitle("C4 16-18");
            c4_16To18HzPowerBar.setHeight(barHeight);
            c4_16To18HzPowerBar.setWidth(getWidth()/2);
            c4_16To18HzPowerBar.setLocation(getX()+(getWidth()/2)-(c4_16To18HzPowerBar.getWidth()),getY()+5+barHeight*6+30);
            
            c4_18To20HzPowerBar.setGraphics2D(g2);
            c4_18To20HzPowerBar.setTitle("C4 18-20");
            c4_18To20HzPowerBar.setHeight(barHeight);
            c4_18To20HzPowerBar.setWidth(getWidth()/2);
            c4_18To20HzPowerBar.setLocation(getX()+(getWidth()/2)-(c4_18To20HzPowerBar.getWidth()),getY()+5+barHeight*7+35);
            
            c4_20To22HzPowerBar.setGraphics2D(g2);
            c4_20To22HzPowerBar.setTitle("C4 20-22");
            c4_20To22HzPowerBar.setHeight(barHeight);
            c4_20To22HzPowerBar.setWidth(getWidth()/2);
            c4_20To22HzPowerBar.setLocation(getX()+(getWidth()/2)-(c4_20To22HzPowerBar.getWidth()),getY()+5+barHeight*8+40);
            
            c4_22To24HzPowerBar.setGraphics2D(g2);
            c4_22To24HzPowerBar.setTitle("C4 22-24");
            c4_22To24HzPowerBar.setHeight(barHeight);
            c4_22To24HzPowerBar.setWidth(getWidth()/2);
            c4_22To24HzPowerBar.setLocation(getX()+(getWidth()/2)-(c4_22To24HzPowerBar.getWidth()),getY()+5+barHeight*9+45);
            
            c4_24To26HzPowerBar.setGraphics2D(g2);
            c4_24To26HzPowerBar.setTitle("C4 24-26");
            c4_24To26HzPowerBar.setHeight(barHeight);
            c4_24To26HzPowerBar.setWidth(getWidth()/2);
            c4_24To26HzPowerBar.setLocation(getX()+(getWidth()/2)-(c4_24To26HzPowerBar.getWidth()),getY()+5+barHeight*10+50);
            
            c4_26To28HzPowerBar.setGraphics2D(g2);
            c4_26To28HzPowerBar.setTitle("C4 26-28");
            c4_26To28HzPowerBar.setHeight(barHeight);
            c4_26To28HzPowerBar.setWidth(getWidth()/2);
            c4_26To28HzPowerBar.setLocation(getX()+(getWidth()/2)-(c4_26To28HzPowerBar.getWidth()),getY()+5+barHeight*11+55);
            
            c4_28To30HzPowerBar.setGraphics2D(g2);
            c4_28To30HzPowerBar.setTitle("C4 28-30");
            c4_28To30HzPowerBar.setHeight(barHeight);
            c4_28To30HzPowerBar.setWidth(getWidth()/2);
            c4_28To30HzPowerBar.setLocation(getX()+(getWidth()/2)-(c4_28To30HzPowerBar.getWidth()),getY()+5+barHeight*12+60);
            
            c4_30To32HzPowerBar.setGraphics2D(g2);
            c4_30To32HzPowerBar.setTitle("C4 30-32");
            c4_30To32HzPowerBar.setHeight(barHeight);
            c4_30To32HzPowerBar.setWidth(getWidth()/2);
            c4_30To32HzPowerBar.setLocation(getX()+(getWidth()/2)-(c4_30To32HzPowerBar.getWidth()),getY()+5+barHeight*13+65);
        }
        
        c3_8To10HzPowerBar.drawPercentage(normc3_8To10HzPower);
        c3_8To10HzPowerBar.drawTitle();
        
        c3_10To12HzPowerBar.drawPercentage(normc3_10To12HzPower);
        c3_10To12HzPowerBar.drawTitle();
        
        c3_12To14HzPowerBar.drawPercentage(normc3_12To14HzPower);
        c3_12To14HzPowerBar.drawTitle();
        
        c3_14To16HzPowerBar.drawPercentage(normc3_14To16HzPower);
        c3_14To16HzPowerBar.drawTitle();
        
        c3_16To18HzPowerBar.drawPercentage(normc3_16To18HzPower);
        c3_16To18HzPowerBar.drawTitle();
        
        c3_18To20HzPowerBar.drawPercentage(normc3_18To20HzPower);
        c3_18To20HzPowerBar.drawTitle();
        
        c3_20To22HzPowerBar.drawPercentage(normc3_20To22HzPower);
        c3_20To22HzPowerBar.drawTitle();
        
        c3_22To24HzPowerBar.drawPercentage(normc3_22To24HzPower);
        c3_22To24HzPowerBar.drawTitle();
        
        c3_24To26HzPowerBar.drawPercentage(normc3_24To26HzPower);
        c3_24To26HzPowerBar.drawTitle();
        
        c3_26To28HzPowerBar.drawPercentage(normc3_26To28HzPower);
        c3_26To28HzPowerBar.drawTitle();
        
        c3_28To30HzPowerBar.drawPercentage(normc3_28To30HzPower);
        c3_28To30HzPowerBar.drawTitle();
        
        c3_30To32HzPowerBar.drawPercentage(normc3_30To32HzPower);
        c3_30To32HzPowerBar.drawTitle();
        
        //////////////////////////////////////////////////////////
        
        c4_8To10HzPowerBar.drawPercentage(normc3_8To10HzPower);
        c4_8To10HzPowerBar.drawTitle();
        
        c4_10To12HzPowerBar.drawPercentage(normc3_10To12HzPower);
        c4_10To12HzPowerBar.drawTitle();
        
        c4_12To14HzPowerBar.drawPercentage(normc3_12To14HzPower);
        c4_12To14HzPowerBar.drawTitle();
        
        c4_14To16HzPowerBar.drawPercentage(normc3_14To16HzPower);
        c4_14To16HzPowerBar.drawTitle();
        
        c4_16To18HzPowerBar.drawPercentage(normc3_16To18HzPower);
        c4_16To18HzPowerBar.drawTitle();
        
        c4_18To20HzPowerBar.drawPercentage(normc3_18To20HzPower);
        c4_18To20HzPowerBar.drawTitle();
        
        c4_20To22HzPowerBar.drawPercentage(normc3_20To22HzPower);
        c4_20To22HzPowerBar.drawTitle();
        
        c4_22To24HzPowerBar.drawPercentage(normc3_22To24HzPower);
        c4_22To24HzPowerBar.drawTitle();
        
        c4_24To26HzPowerBar.drawPercentage(normc3_24To26HzPower);
        c4_24To26HzPowerBar.drawTitle();
        
        c4_26To28HzPowerBar.drawPercentage(normc3_26To28HzPower);
        c4_26To28HzPowerBar.drawTitle();
        
        c4_28To30HzPowerBar.drawPercentage(normc3_28To30HzPower);
        c4_28To30HzPowerBar.drawTitle();
        
        c4_30To32HzPowerBar.drawPercentage(normc3_30To32HzPower);
        c4_30To32HzPowerBar.drawTitle();
    }
}