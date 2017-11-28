/*
 * SpectrumPainter.java
 *
 * Created on 4. Dezember 2007, 15:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package adracer.painter;

import panel.misc.SpectrumPanel;
import utilities.graphics.AbstractPainter;

/**
 *
 * @author Administrator
 */
public class SpectrumPainter extends AbstractPainter{
    
    private SpectrumPanel panel = new SpectrumPanel();

    public SpectrumPanel getPanel() {
        return panel;
    }

    public void setPanel(SpectrumPanel panel) {
        this.panel = panel;
    }
    
    
    /** Creates a new instance of MultiChannelEEGPainter */
    public SpectrumPainter() {
    }
    
    public void repaint() {
        panel.render(getGraphics());
    }
}