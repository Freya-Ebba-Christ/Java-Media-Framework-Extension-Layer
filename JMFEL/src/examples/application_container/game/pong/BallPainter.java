/*
 * BallPainter.java
 *
 */

package examples.application_container.game.pong;

import utilities.graphics.AbstractPainter;

/**
 *
 * @author Administrator
 */
public class BallPainter extends AbstractPainter{
    
    private BallPanel ballPanel = new BallPanel(); 
            
    public BallPainter() {
    }

    public BallPanel getBallPanel() {
        return ballPanel;
    }

    public void setBallPanel(BallPanel ballPanel) {
        this.ballPanel = ballPanel;
    }
    
    public void repaint() {
        ballPanel.render(getGraphics());
    }
}