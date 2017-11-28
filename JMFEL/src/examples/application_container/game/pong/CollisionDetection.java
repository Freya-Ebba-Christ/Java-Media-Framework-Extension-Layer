/*
 * CollisionDetection.java
 *
 */

package examples.application_container.game.pong;

import java.util.Observable;
import java.util.Observer;
import utilities.game.SimplePongGame;
import utilities.graphics.passive_rendering.ApplicationContainer;

/**
 *
 * @author Administrator
 */
public class CollisionDetection implements Observer{
    private SimplePongGame game;
    private TopWallPanel topWallPanel;
    private LeftWallPanel leftWallPanel;
    private RightWallPanel rightWallPanel;
    private BallPanel ballpanel;
    private PaddlePanel paddlepanel;
    private boolean collisionWithPaddle = false;
    private boolean collisionWithLeftWall = false;
    private boolean collisionWithRightWall = false;
    private boolean collisionWithTopWall = false;
    
    /** Creates a new instance of CollisionDetection */
    public CollisionDetection() {
    }
    
    public PaddlePanel getPaddlePanel() {
        return paddlepanel;
    }
    
    public void setPaddlePanel(PaddlePanel paddlepanel) {
        this.paddlepanel = paddlepanel;
    }
    
    public BallPanel getBallPanel() {
        return ballpanel;
    }
    
    public void setBallPanel(BallPanel ballpanel) {
        this.ballpanel = ballpanel;
    }
    
    public LeftWallPanel getLeftWallPanel() {
        return leftWallPanel;
    }
    
    public RightWallPanel getRightWallPanel() {
        return rightWallPanel;
    }
    
    public TopWallPanel getTopWallPanel() {
        return topWallPanel;
    }
    
    public void setLeftWallPanel(LeftWallPanel leftWallPanel) {
        this.leftWallPanel = leftWallPanel;
    }
    
    public void setRightWallPanel(RightWallPanel rightWallPanel) {
        this.rightWallPanel = rightWallPanel;
    }
    
    public void setTopWallPanel(TopWallPanel topWallPanel) {
        this.topWallPanel = topWallPanel;
    }
    
    public SimplePongGame getGame() {
        return game;
    }
    
    public void setGame(SimplePongGame game) {
        this.game = game;
    }
    
    public boolean isCollisionWithLeftWall() {
        return collisionWithLeftWall;
    }
    
    public boolean isCollisionWithPaddle() {
        return collisionWithPaddle;
    }
    
    public boolean isCollisionWithRightWall() {
        return collisionWithRightWall;
    }
    
    public boolean isCollisionWithTopWall() {
        return collisionWithTopWall;
    }
    
    public void setCollisionWithLeftWall(boolean collisionWithLeftWall) {
        this.collisionWithLeftWall = collisionWithLeftWall;
    }
    
    public void setCollisionWithPaddle(boolean collisionWithPaddle) {
        this.collisionWithPaddle = collisionWithPaddle;
    }
    
    public void setCollisionWithRightWall(boolean collisionWithRightWall) {
        this.collisionWithRightWall = collisionWithRightWall;
    }
    
    public void setCollisionWithTopWall(boolean collisionWithTopWall) {
        this.collisionWithTopWall = collisionWithTopWall;
    }
    
    public void update(Observable o, Object arg) {
        
        setCollisionWithPaddle(getBallPanel().getOval().getBounds2D().intersects(paddlepanel.getRect().getBounds2D()));
        setCollisionWithLeftWall(getBallPanel().getOval().getBounds2D().intersects(leftWallPanel.getRect().getBounds2D()));
        setCollisionWithRightWall(getBallPanel().getOval().getBounds2D().intersects(rightWallPanel.getRect().getBounds2D()));
        setCollisionWithTopWall(getBallPanel().getOval().getBounds2D().intersects(topWallPanel.getRect().getBounds2D()));
        
        if(isCollisionWithPaddle()){
            game.updateScore();
            getBallPanel().handleCollision(this);
        } else if(isCollisionWithLeftWall()){
            getBallPanel().handleCollision(this);
        } else if(isCollisionWithRightWall()){
            getBallPanel().handleCollision(this);
        }else if(isCollisionWithTopWall()){
            getBallPanel().handleCollision(this);
        }else if(getBallPanel().getY()>((ApplicationContainer)getGame()).getHeight()){
            getGame().centerBall();
            getGame().centerPaddle();
        }
    }
}