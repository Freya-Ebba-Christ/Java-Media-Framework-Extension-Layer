/*
 * StopWatchPainter.java
 *
 * Created on 4. Oktober 2007, 12:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package examples.motorcortex.eeg_recording;

import utilities.graphics.AbstractPainter;

/**
 *
 * @author Administrator
 */
public class StopwatchPainter extends AbstractPainter{
    private StopwatchPanel panel = new StopwatchPanel();
            
    /** Creates a new instance of FunPainter */
    public StopwatchPainter() {
    }

    public StopwatchPanel getPanel() {
        return panel;
    }

    public void setPanel(StopwatchPanel panel) {
        this.panel = panel;
    }
    
    public void repaint() {
        panel.render(getGraphics());
    }
}
