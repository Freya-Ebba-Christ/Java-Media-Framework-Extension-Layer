/*
 * ClockPainter.java
 *
 * Created on 24. August 2007, 20:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package application_container.painter;

import panel.misc.ClockPanel;
import utilities.graphics.AbstractPainter;

/**
 *
 * @author Administrator
 */
public class ClockPainter extends AbstractPainter{
    
    private ClockPanel clockPanel = new ClockPanel();

    public ClockPainter() {
    }

    public ClockPanel getClockPanel() {
        return clockPanel;
    }

    public void setClockPanel(ClockPanel clockPanel) {
        this.clockPanel = clockPanel;
    }
    
    public void repaint() {
        clockPanel.render(getGraphics());
    }
}
