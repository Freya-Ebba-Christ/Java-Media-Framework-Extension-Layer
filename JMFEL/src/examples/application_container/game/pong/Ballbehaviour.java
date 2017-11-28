/*
 * Ballbehaviour.java
 *
 * The ball behaviour defines how the ball moves across the screen
 */

package examples.application_container.game.pong;

/**
 *
 * @author Administrator
 */
public class Ballbehaviour {
    
    private BallPanel ballPanel;
    private CollisionDetection collisionState;
    private double xVelocity = -1;
    private double yVelocity = 1;
    
    public Ballbehaviour() {
    }
    
    public BallPanel getBallPanel() {
        return ballPanel;
    }
    
    public void setBallPanel(BallPanel ballPanel) {
        this.ballPanel = ballPanel;
    }
    
    public void handleCollision(CollisionDetection collisionDetection){
        collisionState = collisionDetection;
        if(collisionState.isCollisionWithPaddle()){
            if(yVelocity==1){
                yVelocity=-1;
            }
        }
        if(collisionState.isCollisionWithLeftWall()){
            if(xVelocity==-1){
                xVelocity=1;
            }
        }
        
        if(collisionState.isCollisionWithTopWall()){
            if(yVelocity==-1){
                yVelocity=1;
            }
        }
        if(collisionState.isCollisionWithRightWall()){
            if(xVelocity==1){
                xVelocity=-1;
            }
        }
    }
    
    public void updateBall(){
        int xPosition = getBallPanel().getX();
        int yPosition = getBallPanel().getY();
        xPosition+=xVelocity;
        yPosition+=yVelocity;
        getBallPanel().setX(xPosition);
        getBallPanel().setY(yPosition);
    }
}