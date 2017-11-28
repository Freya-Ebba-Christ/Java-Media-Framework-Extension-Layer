/*
 * MultiBandEEGPainter.java
 *
 * Created on 28. Mai 2007, 12:14
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package application_container.painter;

import panel.eeg.MultiBandEEGPanel;
import utilities.graphics.AbstractPainter;

/**
 *
 * @author Administrator
 */

public class MultiBandEEGPainter extends AbstractPainter{
    private MultiBandEEGPanel panel = new MultiBandEEGPanel();
    
    /** Creates a new instance of MultiChannelEEGPainter */
    public MultiBandEEGPainter() {
    }

    public MultiBandEEGPanel getPanel() {
        return panel;
    }

    public void setPanel(MultiBandEEGPanel panel) {
        this.panel = panel;
    }
    
    public void repaint() {
        panel.render(getGraphics());
    }
}