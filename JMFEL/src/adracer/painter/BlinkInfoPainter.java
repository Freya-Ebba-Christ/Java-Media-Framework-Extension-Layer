/*
 * BlinkInfoPanel.java
 *
 * Created on 16. Mai 2007, 22:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package adracer.painter;

import panel.eye.EyeTrackerBlinkPanel;
import utilities.graphics.AbstractPainter;

/**
 *
 * @author Administrator
 */
public class BlinkInfoPainter extends AbstractPainter{
    
    private EyeTrackerBlinkPanel infoPanel = new EyeTrackerBlinkPanel();

    public EyeTrackerBlinkPanel getInfoPanel() {
        return infoPanel;
    }

    public void setInfoPanel(EyeTrackerBlinkPanel infoPanel) {
        this.infoPanel = infoPanel;
    }
    
    public BlinkInfoPainter() {
    }
    
    public void repaint() {
        infoPanel.render(getGraphics());
    }
}