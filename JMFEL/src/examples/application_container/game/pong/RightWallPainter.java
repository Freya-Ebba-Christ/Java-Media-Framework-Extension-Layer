/*
 * RightWallPainter.java
 *
 * Created on 25. September 2007, 20:01
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
public class RightWallPainter extends AbstractPainter{
    
    private RightWallPanel panel = new RightWallPanel();

    public RightWallPanel getPanel() {
        return panel;
    }

    public void setPanel(RightWallPanel panel) {
        this.panel = panel;
    }

    public RightWallPainter() {
    }
    
    public void repaint() {
        panel.render(getGraphics());
    }
}