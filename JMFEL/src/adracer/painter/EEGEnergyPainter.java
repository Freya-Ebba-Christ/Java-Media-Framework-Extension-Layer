/*
 * EEGEnergyPainter.java
 *
 * Created on 28. Mai 2007, 14:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package adracer.painter;

import panel.eeg.EEGEnergyPanel;
import utilities.graphics.AbstractPainter;

/**
 *
 * @author Administrator
 */
public class EEGEnergyPainter extends AbstractPainter{
    private EEGEnergyPanel panel = new EEGEnergyPanel();

    public EEGEnergyPanel getPanel() {
        return panel;
    }

    public void setPanel(EEGEnergyPanel panel) {
        this.panel = panel;
    }
    
    /** Creates a new instance of MultiChannelEEGPainter */
    public EEGEnergyPainter() {
    }
    
    public void repaint() {
        panel.render(getGraphics());
    }
}