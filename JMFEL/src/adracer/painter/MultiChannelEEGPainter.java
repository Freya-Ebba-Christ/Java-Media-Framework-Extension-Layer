/*
 * MultiChannelEEGPainter.java
 *
 * Created on 28. Mai 2007, 06:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package adracer.painter;

import panel.eeg.MultiChannelEEGPanel;
import utilities.graphics.AbstractPainter;

/**
 *
 * @author Administrator
 */
public class MultiChannelEEGPainter extends AbstractPainter{
    private MultiChannelEEGPanel panel = new MultiChannelEEGPanel();
    
    /** Creates a new instance of MultiChannelEEGPainter */
    public MultiChannelEEGPainter() {
    }

    public MultiChannelEEGPanel getPanel() {
        return panel;
    }

    public void setPanel(MultiChannelEEGPanel panel) {
        this.panel = panel;
    }
    
    public void repaint() {
        panel.render(getGraphics());
    }
}
