/*
 * PaddlePainter.java
 *
 * Created on 30. August 2007, 19:29
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
public class PaddlePainter extends AbstractPainter{
    
    private PaddlePanel paddlePanel = new PaddlePanel(); 
            
    public PaddlePainter() {
    }
        
    public PaddlePanel getPaddlePanel() {
        return paddlePanel;
    }

    public void setPaddlePanel(PaddlePanel paddlePanel) {
        this.paddlePanel = paddlePanel;
    }

    public void repaint() {
        paddlePanel.render(getGraphics());
    }
}
