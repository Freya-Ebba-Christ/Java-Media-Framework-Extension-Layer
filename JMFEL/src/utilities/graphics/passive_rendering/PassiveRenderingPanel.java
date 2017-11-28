/*
 * Another2DTest.java
 *
 * Created on 7. Mai 2007, 16:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilities.graphics.passive_rendering;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.RepaintManager;
import javax.swing.Timer;

/**
 *Another2DTestdministrator
 */

public abstract class PassiveRenderingPanel extends JPanel implements MouseListener, MouseMotionListener{
    
    private BufferedImage bimg;
    private Toolkit toolkit;
    private int cycles = 50;
    private int x_MouseCoordinate = 0;
    private int y_MouseCoordinate  = 0;
    private Timer timer;
    
    public PassiveRenderingPanel() {
        //System.setProperty("sun.java2d.opengl", "true");
        //System.setProperty("sun.java2d.translaccel","true");
        toolkit = toolkit = getToolkit();
    }
    
    public abstract void render(int w, int h, Graphics2D g2);
    
    public void start() {
        addMouseListener(this);
        addMouseMotionListener(this);
        PassiveRenderingPaintTrigger passiveRenderingPaintTrigger = new PassiveRenderingPaintTrigger();
        passiveRenderingPaintTrigger.setPassiveRenderingPanel(this);
        timer = new Timer(getCycles(),passiveRenderingPaintTrigger);
        timer.start();
    }
    
    public void mouseClicked(MouseEvent e) {
    }
    
    public void mouseEntered(MouseEvent e) {
    }
    
    public void mouseExited(MouseEvent e) {
    }
    
    public void mousePressed(MouseEvent e) {
    }
    
    public void mouseReleased(MouseEvent e) {
    }
    
    public void mouseDragged(MouseEvent e) {
        x_MouseCoordinate = e.getX();
        y_MouseCoordinate = e.getY();
    }
    
    public void mouseMoved(MouseEvent e) {
        x_MouseCoordinate = e.getX();
        y_MouseCoordinate = e.getY();
    }
    
    public int getMousePositionX(){
        return x_MouseCoordinate;
    }
    
    public int getMousePositionY(){
        return y_MouseCoordinate;
    }
    
    public void stop() {
        timer.stop();
    }
    
    public int getCycles() {
        return cycles;
    }
    
    public void setCycles(int cycles) {
        this.cycles = cycles;
    }
    
    public void paintImmediately(int x,int y,int w, int h) {
        RepaintManager repaintManager = null;
        boolean save = true;
        if (!isDoubleBuffered()) {
            repaintManager = RepaintManager.currentManager(this);
            save = repaintManager.isDoubleBufferingEnabled();
            repaintManager.setDoubleBufferingEnabled(false);
        }
        super.paintImmediately(x, y, w, h);
        
        if (repaintManager != null) {
            repaintManager.setDoubleBufferingEnabled(save);
        }
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        
        Dimension d = getSize();
        if(bimg==null){
            bimg = createBufferedImage((Graphics2D)g, d.width, d.height);
        }
        
        Graphics2D g2 = createGraphics2D(d.width, d.height, bimg, g);
        render(d.width, d.height, g2);
        g2.dispose();
        
        if (bimg != null)  {
            g.drawImage(bimg, 0, 0, null);
            toolkit.sync();
        }
    }
    
    private Graphics2D createGraphics2D(int width,
            int height,
            BufferedImage bi,
            Graphics g) {
        
        Graphics2D g2 = null;
        
        if (bi != null) {
            g2 = bi.createGraphics();
        } else {
            g2 = (Graphics2D) g;
        }
        
        g2.setBackground(getBackground());
        g2.clearRect(0, 0, width, height);
        g2.setClip(0,0,width, height);
        return g2;
    }
    
    private BufferedImage createBufferedImage(Graphics2D g2, int w, int h) {
        BufferedImage bi = (BufferedImage) g2.getDeviceConfiguration().createCompatibleImage(w, h);
        return bi;
    }
}

class PassiveRenderingPaintTrigger implements ActionListener{
    
    PassiveRenderingPanel passiveRenderingPanel;
    /** Creates a nPassiveRenderingPaintTrigger */
    public PassiveRenderingPaintTrigger() {
    }
    
    public PassiveRenderingPanel getPassiveRenderingPanel() {
        return passiveRenderingPanel;
    }
    
    public void setPassiveRenderingPanel(PassiveRenderingPanel passiveRenderingPanel) {
        this.passiveRenderingPanel = passiveRenderingPanel;
    }

    public void actionPerformed(ActionEvent e) {
        getPassiveRenderingPanel().repaint();
    }
}