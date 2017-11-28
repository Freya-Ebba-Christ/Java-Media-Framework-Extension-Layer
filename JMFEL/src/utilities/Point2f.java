/*
 * Point2f.java
 *
 * Created on 27. September 2007, 12:11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilities;

/**
 *
 * @author Administrator
 */
public class Point2f {
    
    private float x;
    private float y;
    
    public Point2f() {
    }
    
    public Point2f(float x,float y) {
        setX(x);
        setY(y);
    }
    
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
