/*
 * TopWallPainter.java
 *
 * Created on 25. September 2007, 19:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package examples.application_container.game.pong;

import utilities.graphics.AbstractPainter;

/**
 *
 * @author Administrator
 */
public class TopWallPainter extends AbstractPainter{
    
    private TopWallPanel panel = new TopWallPanel();

    public TopWallPanel getPanel() {
        return panel;
    }

    public void setPanel(TopWallPanel panel) {
        this.panel = panel;
    }

    public TopWallPainter() {
    }
    
    public void repaint() {
        panel.render(getGraphics());
    }
}