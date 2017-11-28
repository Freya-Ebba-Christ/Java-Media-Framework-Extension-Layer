/*
 * LeftWallPainter.java
 *
 * Created on 25. September 2007, 19:55
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
public class LeftWallPainter extends AbstractPainter{
    
    private LeftWallPanel panel = new LeftWallPanel();

    public LeftWallPanel getPanel() {
        return panel;
    }

    public void setPanel(LeftWallPanel panel) {
        this.panel = panel;
    }

    public LeftWallPainter() {
    }
    
    public void repaint() {
        panel.render(getGraphics());
    }
}