/*
 * CaptureStatePainter.java
 *
 * Created on 14. Oktober 2007, 03:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package capture_controller.gui;

import utilities.graphics.AbstractPainter;

/**
 *
 * @author Urkman_2
 */
public class CaptureStatePainter extends AbstractPainter{
    private CaptureStatePanel panel = new CaptureStatePanel();
    
    /** Creates a new instance of CaptureStatePainter */
    public CaptureStatePainter() {
    }

    public CaptureStatePanel getPanel() {
        return panel;
    }

    public void setPanel(CaptureStatePanel panel) {
        this.panel = panel;
    }
    
    public void repaint() {
        panel.render(getGraphics());
    }
}