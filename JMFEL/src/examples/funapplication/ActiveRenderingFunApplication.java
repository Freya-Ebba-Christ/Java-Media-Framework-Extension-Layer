/*
 * ActiveRenderingFunApplication.java
 *
 * This example uses active rendering to display the funpanel's content on the screen. 
 */

package examples.funapplication;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import utilities.graphics.BasicImageUtility;
import utilities.graphics.active_rendering.ApplicationContainer;
import utilities.graphics.active_rendering.Surface;

/**
 *
 * @author Administrator
 */
public class ActiveRenderingFunApplication {
    
    /** Creates a new instance of ActiveRenderingFunApplication */
    public ActiveRenderingFunApplication() {
    }
    
    public static void main(String[] args) {
        
        BufferedImage image = BasicImageUtility.getInstance().load(System.getProperty("user.dir")+"\\resources\\background_funapplication.jpg",BufferedImage.TYPE_4BYTE_ABGR_PRE);
        int width = image.getWidth();
        int height = image.getHeight();
        ApplicationContainer container = new ApplicationContainer(image.getWidth(),image.getHeight(),true);
        Surface surface = container.getSurface();
        //please note, that we are using the same painter for different surfaces
        //And since the application looks almost the same, a high degree of interoperability is guaranteed.
        //Basically this means, that windowed passive rendering applications can be very easily transformed into windowed/fullscreen active rendering applications.
        FunPainter funpainter = new FunPainter();
        surface.addPainter(funpainter);
        surface.setSize(width,height);
        surface.setPreferredSize(new Dimension(width,height));
        surface.setMinimumSize(new Dimension(width,height));
        surface.setMaximumSize(new Dimension(width,height));
        surface.setBackgroundImage(BasicImageUtility.getInstance().toCompatibleImage(image));
        surface.setFPSEnabled(true);
        container.setCycles(0);
        container.start();
    }
}