/*
 * BallPanel.java
 *
 * Created on 30. August 2007, 21:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package examples.application_container.game.pong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import visual_signal_components.passive_rendering.AbstractTextPanel;

/**
 *
 * @author Administrator
 */

public class BallPanel extends AbstractTextPanel implements ActionListener{
    
    private Ellipse2D oval = new Ellipse2D.Float(0,0,0,0);
    private Area area = new Area(oval);
    private Shape ball = area;
    private Ballbehaviour ballbehaviour;
    
    public BallPanel() {
    }
    
    public Shape getBall() {
        return ball;
    }
    
    public Ballbehaviour getBallbehaviour() {
        return ballbehaviour;
    }
    
    public void setBallbehaviour(Ballbehaviour ballbehaviour) {
        this.ballbehaviour = ballbehaviour;
    }
    
    public void actionPerformed(ActionEvent e) {
        ballbehaviour.setBallPanel(this);
        ballbehaviour.updateBall();
    }
    
    public void handleCollision(CollisionDetection collisionDetection){
        ballbehaviour.handleCollision(collisionDetection);
    }
    
    public void setBall(Shape ball) {
        this.ball = ball;
    }
    
    public Area getArea() {
        return area;
    }
    
    public Ellipse2D getOval() {
        return oval;
    }
    
    public void setArea(Area area) {
        this.area = area;
    }
    
    public void setOval(Ellipse2D oval) {
        this.oval = oval;
    }
    
    public void renderGraphics(Graphics2D g2) {
        oval.setFrame(getX(),getY(),getWidth(),getHeight());
        g2.setColor(Color.DARK_GRAY);
        g2.fillOval(getX()+2,getY()+2,getWidth(),getHeight());
        g2.setColor(Color.WHITE);
        g2.fillOval(getX(),getY(),getWidth(),getHeight());
    }
}