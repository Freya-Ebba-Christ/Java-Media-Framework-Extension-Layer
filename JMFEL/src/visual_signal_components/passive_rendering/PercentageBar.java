package visual_signal_components.passive_rendering;
import java.awt.Color;

public class PercentageBar extends AbstractPanel{
    private Color barColor;
    public static int LEFT_RIGHT = 0;
    public static int RIGHT_LEFT = 1;
    public static int BOTTOM_TOP = 2;
    public static int TOP_BOTTOM = 3;
    private int direction = LEFT_RIGHT;
    private boolean enableBorder = true;
    private boolean enablePercentageValue = true;
    
    public PercentageBar(){
    }
    
    public boolean isEnableBorder() {
        return enableBorder;
    }
    
    public void setEnableBorder(boolean enableBorder) {
        this.enableBorder = enableBorder;
    }
    
    public int getDirection() {
        return direction;
    }
    
    public void setEnablePercentageValue(boolean enablePercentageValue) {
        this.enablePercentageValue = enablePercentageValue;
    }
    
    public boolean isEnablePercentageValue() {
        return enablePercentageValue;
    }
    
    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    public void setBarColor(Color aColor){
        barColor = aColor;
    }
    
    public Color getBarColor(){
        return barColor;
    }
    
    public void drawPanel() {
        //backup the current drawing color
        backUpCurrentColor();
        getGraphics2D().setColor(getBorderColor());
        if(isEnableBorder()){
            getGraphics2D().drawRect(getLocationX(),getLocationY(),getWidth(),getHeight());
        }
        //restore the original drawing color
        restoreColor();
    }
    
    public void drawTitle(){
        //backup the current drawing color
        backUpCurrentColor();
        getGraphics2D().setColor(getTitleColor());
        getGraphics2D().drawString(getTitle(),getLocationX()+getWidth()/2-getStringWidth()/2,getLocationY()+getHeight()/2+getStringHeight()/4);
        //restore the original drawing color
        restoreColor();
    }
    
    public void drawPercentage(double percentage){
        backUpCurrentColor();
        if(getDirection()==LEFT_RIGHT){
            getGraphics2D().setColor(barColor);
            getGraphics2D().fillRect(getLocationX()+1,getLocationY()+1,(int)Math.rint(getWidth()*percentage)-1,getHeight()-1);
            getGraphics2D().setColor(getTitleColor());
            if(isEnablePercentageValue()){
                getGraphics2D().drawString("%: " + String.valueOf(percentage*100.0),(int)Math.rint(getWidth()*0.75)+getLocationX(), getLocationY()+getHeight()/2+getStringHeight()/4);
            }
            restoreColor();
        }else if(getDirection()==RIGHT_LEFT) {
            getGraphics2D().setColor(barColor);
            //(int)Math.rint(getWidth()*1.0-percentage)
            getGraphics2D().fillRect(getLocationX()+1+getWidth()-(int)Math.rint((getWidth()*(percentage))),getLocationY()+1,(int)Math.rint(getWidth()*percentage)-1,getHeight()-1);
            getGraphics2D().setColor(getTitleColor());
            if(isEnablePercentageValue()){
                getGraphics2D().drawString("%: " + String.valueOf(percentage*100.0),(int)Math.rint(getWidth()*0.75)+getLocationX(), getLocationY()+getHeight()/2+getStringHeight()/4);
            }
            restoreColor();
        }else if(getDirection()==BOTTOM_TOP) {
            getGraphics2D().setColor(barColor);
            //(int)Math.rint(getWidth()*1.0-percentage)
            getGraphics2D().fillRect(getLocationX()+1,getLocationY()+1+getHeight()-(int)Math.rint((getHeight()*(percentage))),getWidth()-1,(int)Math.rint(getHeight()*percentage)-1);
            getGraphics2D().setColor(getTitleColor());
            if(isEnablePercentageValue()){
                getGraphics2D().drawString("%: " + String.valueOf(percentage*100.0),(int)Math.rint(getWidth()*0.75)+getLocationX(), getLocationY()+getHeight()/2+getStringHeight()/4);
            }
            restoreColor();
        }else if(getDirection()==TOP_BOTTOM){
            getGraphics2D().setColor(barColor);
            getGraphics2D().fillRect(getLocationX()+1,getLocationY()+1,getWidth()-1,(int)Math.rint(getHeight()*percentage)-1);
            getGraphics2D().setColor(getTitleColor());
            if(isEnablePercentageValue()){
                getGraphics2D().drawString("%: " + String.valueOf(percentage*100.0),(int)Math.rint(getWidth()*0.75)+getLocationX(), getLocationY()+getHeight()/2+getStringHeight()/4);
            }
            restoreColor();
        }
    }
}