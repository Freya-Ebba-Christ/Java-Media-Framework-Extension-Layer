/*
 * ScorePainter.java
 *
 * Created on 30. August 2007, 20:38
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
public class ScorePainter extends AbstractPainter{
    
    private ScorePanel scorePanel = new ScorePanel(); 
            
    public ScorePainter() {
    }

    public ScorePanel getScorePanel() {
        return scorePanel;
    }

    public void setScorePanel(ScorePanel scorePanel) {
        this.scorePanel = scorePanel;
    }
    
    public void repaint() {
        scorePanel.render(getGraphics());
    }
}