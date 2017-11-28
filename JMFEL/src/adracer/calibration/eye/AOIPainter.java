/*
 * AOIPainter.java
 *
 * Created on 25. September 2007, 14:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package adracer.calibration.eye;
import utilities.graphics.AbstractPainter;

/**
 *
 * @author Administrator
 */
public class AOIPainter extends AbstractPainter{
    private AOIPanel panel = new AOIPanel();
    
    public AOIPainter() {
    }

    public void setPanel(AOIPanel panel) {
        this.panel = panel;
    }

    public AOIPanel getPanel() {
        return panel;
    }

    public void repaint() {
        getPanel().renderGraphics(getGraphics());
    }
}