/*
 * CoordinateConverter.java
 *
 * Created on 16. Mai 2007, 16:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilities.graphics;

import java.awt.Point;

/**
 *
 * @author Administrator
 */
public class CoordinateConverter{
    
    float horizontalMaximum = 0f;
    float horizontalMinimum = 0f;
    float verticalMaximum = 0f;
    float verticalMinimum = 0f;
    int screenWidth = 0;
    int screenHeight = 0;
    
    public CoordinateConverter() {
    }
    
    public float getHorizontalMaximum() {
        return horizontalMaximum;
    }
    
    public float getHorizontalMinimum() {
        return horizontalMinimum;
    }
    
    public float getVerticalMaximum() {
        return verticalMaximum;
    }
    
    public float getVerticalMinimum() {
        return verticalMinimum;
    }
    
    public void setHorizontalMaximum(float horizontalMaximum) {
        this.horizontalMaximum = horizontalMaximum;
    }
    
    public void setHorizontalMinimum(float horizontalMinimum) {
        this.horizontalMinimum = horizontalMinimum;
    }
    
    public void setVerticalMaximum(float verticalMaximum) {
        this.verticalMaximum = verticalMaximum;
    }
    
    public void setVerticalMinimum(float verticalMinimum) {
        this.verticalMinimum = verticalMinimum;
    }
    
    public int getScreenHeight() {
        return screenHeight;
    }
    
    public int getScreenWidth() {
        return screenWidth;
    }
    
    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }
    
    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }
    
    public Point millimeterToPixel(float x_value, float y_value){
        
        /*the coordinate system of the tracker is in the center.
          The screen coordinate system has its origin in the upper left
         */
        
        // make sure x and y are between maximum and minimum
        
        float x = x_value;
        float y = y_value;
        
        if(x<getHorizontalMinimum()){
            x=getHorizontalMinimum();
        }
        if(x>getHorizontalMaximum()){
            x=getHorizontalMaximum();
        }
        
        if(y<getVerticalMinimum()){
            y=getVerticalMinimum();
        }
        if(y>getVerticalMaximum()){
            y=getVerticalMaximum();
        }
        
        //shift to upper left
        
        x+=getHorizontalMaximum();
        y+=Math.abs(getVerticalMinimum());
        
        float screenHeightInMillimeters = getVerticalMaximum()+(Math.abs(getVerticalMinimum()));
        float screenWidthInMillimeters = getHorizontalMaximum()*2;
        return new Point((int)((x/screenWidthInMillimeters)*getScreenWidth()),(int)(getScreenHeight()-(y/screenHeightInMillimeters)*getScreenHeight()));
    }
    
    public static void main(String [] args) {
        CoordinateConverter coordinateConverter = new CoordinateConverter();
        coordinateConverter.setHorizontalMaximum(350);
        coordinateConverter.setHorizontalMinimum(-350);
        coordinateConverter.setVerticalMaximum(550);
        coordinateConverter.setVerticalMinimum(-50);
        coordinateConverter.setScreenHeight(600);
        coordinateConverter.setScreenWidth(800);
        
        System.out.println(coordinateConverter.millimeterToPixel(0,-50+300));
    }
}
