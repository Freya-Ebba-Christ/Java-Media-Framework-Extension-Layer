/*
 * ScorePanel.java
 *
 * Created on 30. August 2007, 20:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package examples.application_container.game.pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import visual_signal_components.passive_rendering.AbstractTextPanel;

/**
 *
 * @author Administrator
 */
public class ScorePanel extends AbstractTextPanel{
    
    private int score = 0;
    private Font font = new Font("Arial Black", Font.BOLD, 25);
    
    public ScorePanel() {
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void increaseScore(){
        score+=1;
    }
    
    public void reset(){
        score = 0;
    }
            
    public void renderGraphics(Graphics2D g2) {
        g2.setFont(font);
        g2.setColor(Color.DARK_GRAY);
        g2.drawString("Score: "+ getScore(), getX(), getY());
        g2.setColor(Color.WHITE);
        g2.drawString("Score: "+ getScore(), getX()-2, getY()-2);
    }
}