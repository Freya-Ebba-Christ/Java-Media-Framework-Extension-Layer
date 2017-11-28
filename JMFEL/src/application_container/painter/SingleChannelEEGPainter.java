/*
 * SingleChannelEEGPainter.java
 *
 * Created on 28. Mai 2007, 15:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package application_container.painter;
import panel.eeg.SingleChannelEEGPanel;
import utilities.DoubleDataBufferContainer;
import utilities.graphics.AbstractPainter;

/**
 *
 * @author Administrator
 */
public class SingleChannelEEGPainter extends AbstractPainter{
    private SingleChannelEEGPanel panel = new SingleChannelEEGPanel();
    private boolean initialized = false;
    private DoubleDataBufferContainer aDoubleDataBufferContainer;
    
    /** Creates a new instance of MultiChannelEEGPainter */
    public SingleChannelEEGPainter() {
    }

    public SingleChannelEEGPanel getPanel() {
        return panel;
    }

    public void setPanel(SingleChannelEEGPanel panel){
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