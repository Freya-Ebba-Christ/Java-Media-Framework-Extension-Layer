/*
 * SimplePongGame.java
 *
 * Created on 1. November 2007, 13:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilities.game;

/**
 *
 * @author Administrator
 */
public interface SimplePongGame extends Simple2DGame{
    public void centerBall();
    public void centerPaddle();
    public int getPaddlePositionX();
    public int getPaddlePositionY();
    public int getBallPositionX();
    public int getBallPositionY();
}
