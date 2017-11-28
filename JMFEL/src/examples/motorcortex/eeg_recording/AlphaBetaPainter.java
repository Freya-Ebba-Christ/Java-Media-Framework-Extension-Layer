/*
 * TopographicMappingPanel.java
 *
 * Created on 22. August 2007, 17:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package examples.motorcortex.eeg_recording;

import panel.eeg.AlphaBetaPanel;
import utilities.graphics.AbstractPainter;

/**
 *
 * @author Administrator
 */
public class AlphaBetaPainter extends AbstractPainter{
    private AlphaBetaPanel panel = new AlphaBetaPanel();
            
    /** Creates a new instance of FunPainter */
    public AlphaBetaPainter() {
    }

    public AlphaBetaPanel getPanel() {
        return panel;
    }

    public void setPanel(AlphaBetaPanel panel) {
        this.panel = panel;
    }
    
    public void repaint() {
        panel.render(getGraphics());
    }
}