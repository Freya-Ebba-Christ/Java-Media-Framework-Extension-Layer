/*
 * BasicImageLoader.java
 *
 * Created on 16. Mai 2007, 10:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilities.graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Administrator
 */
public class BasicImageUtility {
    
    private int imagetypeType = BufferedImage.TYPE_3BYTE_BGR;
    private BufferedImage image = null;
    private AffineTransform trans;
    private static BasicImageUtility instance = null;
    
    /** Creates a new instance of BasicImageLoader */
    private BasicImageUtility() {
        trans = new AffineTransform();
    }
    
    public static BasicImageUtility getInstance(){
        if(instance == null){
            instance = new BasicImageUtility();
        }
        return instance;
    }
    
    public BufferedImage load(String path, int type){
        try{
            Image img = new ImageIcon(ImageIO.read(new File(path))).getImage();
            image = createBufferedImage(img,type);
        }catch(Exception e){System.out.println(e);};
        return image;
    }
    
    public BufferedImage createBufferedImage(Image image, int type) {
        BufferedImage bi = new BufferedImage(image.getWidth(null), image.getHeight(null),type);
        Graphics2D g = bi.createGraphics();
        g.drawImage(image, 0, 0, null);
        return bi;
    }
    
    public BufferedImage toCompatibleImage(BufferedImage image) {
        GraphicsEnvironment e =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice d = e.getDefaultScreenDevice();
        GraphicsConfiguration c = d.getDefaultConfiguration();
        BufferedImage compatibleImage = c.createCompatibleImage(
                image.getWidth(), image.getHeight());
        Graphics g = compatibleImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return compatibleImage;
    }
    
    public BufferedImage scale(BufferedImage img, double scale_x, double scale_y){
        trans.scale(scale_x,scale_y);
        AffineTransformOp scaler = new AffineTransformOp(trans,AffineTransformOp.TYPE_BICUBIC);
        BufferedImage tmp_image = scaler.filter(img,null);
        return tmp_image;
    }
    
    public BufferedImage rotate(BufferedImage img, double degree){
        trans.rotate(Math.toRadians(degree));
        AffineTransformOp rotator = new AffineTransformOp(trans,AffineTransformOp.TYPE_BICUBIC);
        BufferedImage tmp_image = rotator.filter(img,null);
        return tmp_image;
    }
    
    //from http://forum.java.sun.com/thread.jspa?threadID=5199259
    
    public BufferedImage rotate(BufferedImage in, double angle, double x, double y, RenderingHints rh, int afop, boolean copy) {
        int iw = in.getWidth();
        int ih = in.getHeight();
        double cos = Math.abs(Math.cos(angle));
        double sin = Math.abs(Math.sin(angle));
        double w = iw*cos + ih*sin;
        double h = ih*cos + iw*sin;
        double tx = (w - iw)/2;
        double ty = (h - ih)/2;
        AffineTransform aff = AffineTransform.getTranslateInstance(tx, ty);
        aff.rotate(angle, x, y);
        
        // The transform op
        AffineTransformOp op = new AffineTransformOp(aff, afop);
        // Rotate the image
        in=op.filter(in,null);
        
        return copy ? copy(in, rh) : in;
    }
    
    public BufferedImage copy(BufferedImage src, RenderingHints rh) {
        int w = src.getWidth();
        int h = src.getHeight();
        int type = src.getType();
        BufferedImage dst = new BufferedImage(w, h, type);
        Graphics2D g2 = dst.createGraphics();
        if(rh != null)
            g2.setRenderingHints(rh);
        g2.drawImage(src, 0, 0, null);
        g2.dispose();
        return dst;
    }
    
    public BufferedImage scale(BufferedImage in, double scale_x, double scale_y, RenderingHints rh, int afop, boolean copy) {
        trans = new AffineTransform();
        trans.scale(scale_x,scale_y);
        AffineTransformOp scaler = new AffineTransformOp(trans,afop);
        BufferedImage tmp_image = scaler.filter(in,null);
        
        return copy ? copy(tmp_image, rh) : tmp_image;
    }
}