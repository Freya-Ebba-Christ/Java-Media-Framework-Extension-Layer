/*
 * FunApplication.java
 *
 * This example uses passive rendering to display the funpanel's content on the screen. 
 */

package examples.funapplication;
        
import utilities.graphics.passive_rendering.ApplicationContainer;
import utilities.graphics.passive_rendering.Surface;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import utilities.graphics.BasicImageUtility;

/**
 *
 * @author Administrator
 */
public class FunApplication{
    
    /** Creates a new instance of FunApplication */
    public FunApplication() {
    }
    
    public static void main(String[] args) {
        ApplicationContainer aFunApplication = new ApplicationContainer();
        BufferedImage image = BasicImageUtility.getInstance().load(System.getProperty("user.dir")+"\\resources\\background_funapplication.jpg",BufferedImage.TYPE_4BYTE_ABGR_PRE);
        int width = image.getWidth();
        int height = image.getHeight();
        Surface surface = new Surface();
        FunPainter funpainter = new FunPainter();
        surface.addPainter(funpainter);
        surface.setSize(width,height);
        surface.setPreferredSize(new Dimension(width,height));
        surface.setMinimumSize(new Dimension(width,height));
        surface.setMaximumSize(new Dimension(width,height));
        surface.setCycles(0);
        surface.setBackgroundImage(BasicImageUtility.getInstance().toCompatibleImage(image));
        surface.setFPSEnabled(true);
        surface.start();
        aFunApplication.setVisualComponent(surface);
        aFunApplication.init();
        aFunApplication.setVisible(true);
    }
}
