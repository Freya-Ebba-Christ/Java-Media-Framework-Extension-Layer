/*
 * FunPainter.java
 * paints the Funpanel on the screen
 */

package examples.funapplication;

import javax.swing.JPanel;
import utilities.graphics.AbstractPainter;

/**
 *
 * @author Administrator
 */
public class FunPainter extends AbstractPainter{
    private FunPanel funPanel = new FunPanel();
    
    /** Creates a new instance of FunPainter */
    public FunPainter() {
    }
    
    public void repaint() {
        
        int width = ((JPanel)getSurface()).getWidth();
        int height = ((JPanel)getSurface()).getHeight();
        
        funPanel.setWidth(width);
        funPanel.setHeight(height);
        
        getGraphics().setColor(((JPanel)getSurface()).getForeground());
        funPanel.render(getGraphics());
    }
}
