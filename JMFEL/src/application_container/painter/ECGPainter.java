/*
 * ECGPainter.java
 *
 * Created on 15. November 2007, 15:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package application_container.painter;


import panel.ecg.ECGPanel;
import utilities.DoubleDataBufferContainer;
import utilities.graphics.AbstractPainter;

/**
 *
 * @author Administrator
 */
public class ECGPainter extends AbstractPainter{
    private ECGPanel panel = new ECGPanel();
    private boolean initialized = false;
    private DoubleDataBufferContainer aDoubleDataBufferContainer;
    
    /** Creates a new instance of MultiChannelEEGPainter */
    public ECGPainter() {
    }

    public ECGPanel getPanel() {
        return panel;
    }

    public void setPanel(ECGPanel panel) {
        this.panel = panel;
    }
    
    public DoubleDataBufferContainer getDoubleDataBufferContainer() {
        return aDoubleDataBufferContainer;
    }
    
    public void setDoubleDataBufferContainer(DoubleDataBufferContainer aDoubleDataBufferContainer) {
        this.aDoubleDataBufferContainer = aDoubleDataBufferContainer;
    }
    
    public void repaint() {
        panel.setDoubleDataBufferContainer(getDoubleDataBufferContainer());
        panel.render(getGraphics());
    }
}