/*
 * Simple2DGame.java
 *
 * Created on 1. November 2007, 13:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilities.game;

/**
 *
 * @author Administrator
 */
public interface Simple2DGame {
    
    public void moveUP();
    public void moveDown();
    public void moveLeft();
    public void moveRight();
    public void moveTopLeft();
    public void moveTopRight();
    public void moveBottomLeft();
    public void moveBottomRight();
    public void updateScore();
}