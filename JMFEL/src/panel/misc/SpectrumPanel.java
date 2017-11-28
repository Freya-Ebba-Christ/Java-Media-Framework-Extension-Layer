/*
 * SpectrumPanel.java
 *
 * Created on 4. Dezember 2007, 15:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package panel.misc;

import eeg.BasicEEGDataModel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.Observable;
import java.util.Observer;
import utilities.math.Rounding;
import utilities.signal_processing.EEGSignalProcessor;
import visual_signal_components.passive_rendering.AbstractTextPanel;
import visual_signal_components.passive_rendering.PercentageBar;

/**
 *
 * @author Administrator
 */

public class SpectrumPanel extends AbstractTextPanel implements Observer{
    
    private boolean initialized = false;
    private BasicStroke thickstroke = new BasicStroke(3);
    private BasicStroke thinstroke = new BasicStroke(1);
    private Color light_blue = new Color(187,187,210,128);
    private int currentChannel = 1;
    private BasicEEGDataModel eegData;
    private PercentageBar percentageBar;
    public static final int BARS = 0;
    public static final int LINE = 1;
    public static final int LINE_AND_BARS = 2;
    private int style = BARS;
    private GeneralPath path = new GeneralPath();
    private AffineTransform trans;
    private double[] signalSpectrum;
    private double[] normalizedFFTValues;
    private double cnt = 0.0;
    private double stepSize = 0.0;
    private int fftLength;
    private int[] array;
    private boolean dataReady = false;
    
    public SpectrumPanel() {
        initSignalPanel();
    }
    
    public int getStyle() {
        return style;
    }
    
    public void setStyle(int style) {
        this.style = style;
    }
    
    public BasicEEGDataModel getEEGData() {
        return eegData;
    }
    
    public void setEEGData(BasicEEGDataModel eegData) {
        this.eegData = eegData;
    }
    
    public void update(Observable o, Object arg) {
        eegData = (BasicEEGDataModel)o;
        double[] fftValues = EEGSignalProcessor.getInstance().getFFT(eegData.getSignal(currentChannel-1));
        signalSpectrum = new double[fftValues.length/2];
        //convenience copy...;-)
        System.arraycopy(fftValues,0,signalSpectrum,0,signalSpectrum.length);
        
        fftLength = signalSpectrum.length;
        
        cnt = 0.0;
        stepSize = (double)getWidth()/(double)fftLength;
        
        //NOTE!!!!
        //NORMALIZATION IS FOR DISPLAY ONLY!!!
        //Each frequency bin collects the energy from a small frequency range. Or in other words, the highest amplitude of the respective frequency.
        //
        
        normalizedFFTValues = new double[fftLength];
        double totalEnergy = 1.0;
        
        for (int i = 0; i < fftLength; i++) {
            totalEnergy+=Math.abs(signalSpectrum[i]);
        }
        
        for (int i = 0; i < fftLength; i++) {
            normalizedFFTValues[i] = Math.abs(signalSpectrum[i])/totalEnergy;
        }
        //NEVER DO THIS, IF YOU WANT TO USE THE FFT-VALUES FOR ANYTHING ELSE THAN PRESENTATION
        
        array = new int[fftLength];
        for (int i = 0; i < fftLength; i++) {
            array[i]=(int)(getHeight()*normalizedFFTValues[i]);
        }
        dataReady = true;
    }
    
    public void initSignalPanel(){
        percentageBar = new PercentageBar();
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
        //g2.drawRoundRect(getX()+2,getY()+2,getWidth(),getHeight(),8,8);
        g2.setColor(Color.WHITE);
        //g2.fillRoundRect(getX(),getY(),getWidth(),getHeight(),8,8);
        //g2.setComposite(originalComposite);
        g2.setColor(getColor());
        //g2.drawRoundRect(getX(),getY(),getWidth(),getHeight(),8,8);
        percentageBar.setGraphics2D(g2);
        percentageBar.setDirection(PercentageBar.BOTTOM_TOP);
        percentageBar.setBarColor(light_blue);
        percentageBar.setBorderColor(Color.WHITE);
        percentageBar.setTitle(" ");
        percentageBar.setEnablePercentageValue(false);
        percentageBar.setWidth(4);
        percentageBar.setHeight(getHeight()-20);
        percentageBar.setEnableBorder(false);
        
        if(dataReady){
            if(getStyle()==BARS||getStyle()==LINE_AND_BARS){
                for (int i = 0; i < fftLength; i++) {
                    cnt+=stepSize;
                    percentageBar.drawPercentage(normalizedFFTValues[i]);
                    percentageBar.setLocation(getX()-5+(int)Rounding.round(cnt,0),getY()+10);
                    percentageBar.drawPanel();
                }
            }
            
            cnt = 0;
            if(getStyle()==LINE||getStyle()==LINE_AND_BARS){
                path.reset();
                trans = new AffineTransform();
                path.moveTo(getX(), getHeight()-array[0]);
                for (int x = 0;x<array.length;x++){
                    cnt+=stepSize;
                    path.lineTo(x,getHeight()-array[x]);
                }
                trans.translate(getX()+4,getY());
                trans.scale((float)getWidth()/(float)fftLength,1.0);
                path.transform(trans);
                g2.draw(path);
                g2.setStroke(thinstroke);
            }
        }
        g2.drawString("0Hz",12,getHeight()+10);
        g2.drawString(fftLength+"Hz",getWidth()-getStringHelper().getStringWidth(g2.getFont(),fftLength+"Hz")+10,getHeight()+10);
    }
}