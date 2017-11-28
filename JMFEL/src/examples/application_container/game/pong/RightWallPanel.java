/*
 * RightWallPanel.java
 *
 * Created on 25. September 2007, 20:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package examples.application_container.game.pong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import visual_signal_components.passive_rendering.AbstractTextPanel;

/**
 *
 * @author Administrator
 */
public class RightWallPanel extends AbstractTextPanel{
    
    private Rectangle2D rect = new Rectangle2D.Float(0,0,0,0);
    private Area area = new Area(rect);
    private Shape rightWall = area;
    
    /** Creates a new instance of TopWallPanel */
    public RightWallPanel() {
    }

    public Shape getRightWall() {
        return rightWall;
    }

    public void setRightWall(Shape rightWall) {
        this.rightWall = rightWall;
    }

    public Area getArea() {
        return area;
    }
    
    public Rectangle2D getRect() {
        return rect;
    }
    
    public void setArea(Area area) {
        this.area = area;
    }
    
    public void setRect(Rectangle2D rect) {
        this.rect = rect;
    }
    
    public void renderGraphics(Graphics2D g2) {
        rect.setFrame(getX(),getY(),getWidth(),getHeight());
        g2.setColor(Color.WHITE);
        g2.fillRect(getX(),getY(),getWidth(),getHeight());
    }
}