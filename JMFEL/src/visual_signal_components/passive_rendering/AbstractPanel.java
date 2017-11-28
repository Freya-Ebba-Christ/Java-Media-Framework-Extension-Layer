package visual_signal_components.passive_rendering;
import java.awt.Color;
import java.awt.Graphics2D;
import utilities.graphics.StringHelper;
import visual_signal_components.*;

public abstract class AbstractPanel{
    private int width = 0;
    private int height = 0;
    private int location_x = 0;
    private int location_y = 0;
    private StringHelper aStringHelper = new StringHelper();
    private String title;
    private int stringWidth = 0;
    private int stringHeight = 0;
    private Color titlelColor;
    private Color borderColor;
    private Color backupColor;
    private Graphics2D gc;
    private double dcOffset = 0.0;
    
    public AbstractPanel(){
    }
    
    public StringHelper getStringHelper(){
        return aStringHelper;
    }
    
    public double getDCOffset(){
        return dcOffset;
    }
    
    public void setDCOffset(double anOffset){
        dcOffset = anOffset;
    }
    
    public void backUpCurrentColor(){
        backupColor =  gc.getColor();
    }
    
    public void restoreColor(){
        gc.setColor(backupColor);
    }
    
    public void setBorderColor(Color aColor){
        borderColor = aColor;
    }
    
    public void setTitleColor(Color aColor){
        titlelColor = aColor;
    }
    
    public void setWidth(int aWidth){
        width = aWidth;
    }
    
    public void setHeight(int aHeight){
        height = aHeight;
    }
    
    public void setLocation(int x, int y){
        location_x = x;
        location_y = y;
    }
    
    public void setTitle(String aText){
        title = aText;
        aStringHelper.setGraphics(gc);
        stringHeight = aStringHelper.getStringHeight(title);
        stringWidth = aStringHelper.getStringWidth(gc.getFont(),title);
    }
    
    public void setGraphics2D(Graphics2D aGraphics2D){
        gc = aGraphics2D;
    }
    
    public String getTitle(){
        return title;
    }
    
    public Graphics2D getGraphics2D(){
        return gc;
    }
    
    public int getHeight(){
        return height;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getLocationX(){
        return location_x;
    }
    
    public int getLocationY(){
        return location_y;
    }
    
    public Color getTitleColor(){
        return titlelColor;
    }
    
    public Color getBorderColor(){
        return borderColor;
    }
    
    public int getStringWidth(){
        return stringWidth;
    }
    
    public int getStringHeight(){
        return stringHeight;
    }
    
    public void drawTitle(){
    }
    
    public void drawPanel(){
    }
}