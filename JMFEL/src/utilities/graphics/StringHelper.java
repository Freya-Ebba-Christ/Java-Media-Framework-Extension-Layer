package utilities.graphics;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class StringHelper {
    private Graphics2D g2d;
            
    public StringHelper() {
    }
    
    public void setGraphics(Graphics2D aGraphics2D){
        g2d = aGraphics2D;
    }
    
    public int getStringHeight(String aString){
        FontMetrics fm=g2d.getFontMetrics();
        return fm.getHeight();
    }
    
    public int getStringWidth(Font aVal, String aString){
        Font font = aVal;
        FontRenderContext frc = g2d.getFontRenderContext();
        TextLayout layout = new TextLayout(aString, font, frc);
        Rectangle2D bounds = layout.getBounds();
        return (int)(Math.ceil(bounds.getWidth()));
    }
}
