/*
 * GazeDataPainter.java
 *
 * Created on 16. Mai 2007, 14:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package adracer.painter;

import panel.eye.GazePanel;
import utilities.graphics.AbstractPainter;

/**
 *
 * @author Administrator
 */

public class GazeDataPainter extends AbstractPainter{

    private GazePanel gazePanel = new GazePanel();
    
    /** Creates a new instance of GazeDataPainter */
    public GazeDataPainter() {

    }
    
    public GazePanel getGazePanel() {
        return gazePanel;
    }
    
    public void setGazePanel(GazePanel gazePanel) {
        this.gazePanel = gazePanel;
    }

    public void repaint() {
        getGazePanel().render(getGraphics());
    }
}