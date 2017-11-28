/*
 * EEGPowerPainter.java
 *
 * Created on 28. Mai 2007, 14:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package adracer.painter;

import panel.eeg.EEGPowerPanel;
import utilities.graphics.AbstractPainter;


/**
 *
 * @author Administrator
 */
public class EEGPowerPainter extends AbstractPainter{
    private EEGPowerPanel panel = new EEGPowerPanel();

    public EEGPowerPanel getPanel() {
        return panel;
    }

    public void setPanel(EEGPowerPanel panel) {
        this.panel = panel;
    }
    
    /** Creates a new instance of MultiChannelEEGPainter */
    public EEGPowerPainter() {
    }
    
    public void repaint() {
        panel.render(getGraphics());
    }
}