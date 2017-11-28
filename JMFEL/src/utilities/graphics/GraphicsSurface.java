/*
 * GraphicsSurface.java
 *
 * Created on 9. Oktober 2007, 17:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilities.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Observer;

/**
 *
 * @author Urkman_2
 */
public interface GraphicsSurface {
    public int getLastFPS();
    public boolean isFPSEnabled();
    public void setFPSEnabled(boolean fpsEnabled);
    public BufferedImage getBackgroundImage();
    public void setBackgroundImage(BufferedImage image);
    public void addObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void removeAllObservers();
    public int countObservers();
    public void addPainter(AbstractPainter painter);
    public void removePainter(AbstractPainter painter);
    public void removeAllPainter();
    public int countPainter();
    public void render(int w, int h, Graphics2D g2);
}
