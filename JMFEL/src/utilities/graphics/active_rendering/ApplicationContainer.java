/*
 * ApplicationContainer.java
 *
 * Created on 24. September 2007, 18:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilities.graphics.active_rendering;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import utilities.graphics.BasicImageUtility;

/**
 *
 * @author Administrator
 */
public class ApplicationContainer extends AbstractActiveRenderingFrame{
    
    private Surface surface;
    private int cycles = 20;
    
    public ApplicationContainer(int width, int height, boolean use_fullscreen) {
        initFrame(new Dimension(width,height),use_fullscreen);
    }
    
    public ApplicationContainer() {
    }
    
    public void start() {
        ActiveRenderingPaintTrigger activeRenderingPaintTrigger = new ActiveRenderingPaintTrigger();
        activeRenderingPaintTrigger.setApplicationContainer(this);
        Timer timer = new Timer(getCycles(),activeRenderingPaintTrigger);
        timer.start();
    }

    public int getCycles() {
        return cycles;
    }

    public void setCycles(int cycles) {
        this.cycles = cycles;
    }
    
    public void initComponents() {
        setSurface(new Surface());
        //set close operation and add panel to window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getSurface().setLayout(new BorderLayout());
        getSurface().setSize(getDimension());
        getSurface().setMinimumSize(getDimension());
        getSurface().setMaximumSize(getDimension());
        getSurface().setPreferredSize(getDimension());
        getContentPane().add(surface, BorderLayout.CENTER);
        setSize(getDimension());
        setVisible(true);
        getSurface().init();
        pack();
    }
    
    public Surface getSurface() {
        return surface;
    }
    
    public void setSurface(Surface surface) {
        this.surface = surface;
    }
    
    public void renderPanel(){
        if (getBufferStrategy() != null){
            setGraphics2D((Graphics2D) getBufferStrategy().getDrawGraphics());
            getGraphics2D().translate(getInsets().left,getInsets().top);
            getSurface().render(getSurface().getWidth(),getSurface().getHeight(),getGraphics2D());
            getGraphics2D().dispose();
            getBufferStrategy().show();
        }
    }
    
    public static void main( String args[] ) {
        String file_separator = System.getProperty("file.separator");
        BufferedImage image = BasicImageUtility.getInstance().load(System.getProperty("user.dir")+file_separator+"resources"+file_separator+"background_multichannel_measurement.jpg",BufferedImage.TYPE_3BYTE_BGR);
        ApplicationContainer container = new ApplicationContainer(image.getWidth(),image.getHeight(),true);
        container.getSurface().setBackgroundImage(image);
        container.getSurface().setFPSEnabled(true);
        container.setCycles(0);
        container.start();
    }
}

class ActiveRenderingPaintTrigger implements ActionListener{
    
    ApplicationContainer applicationContainer;
    /** Creates a new instance of TimerObserver */
    public ActiveRenderingPaintTrigger() {
    }
    
    public ApplicationContainer getApplicationContainer() {
        return applicationContainer;
    }
    
    public void setApplicationContainer(ApplicationContainer applicationContainer) {
        this.applicationContainer = applicationContainer;
    }

    public void actionPerformed(ActionEvent e) {
        getApplicationContainer().renderPanel();
    }
}