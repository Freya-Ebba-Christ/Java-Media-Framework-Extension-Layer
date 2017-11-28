/*
 * FunPanel.java
 * This panel loads some images and moves and rotates them on the screen.
 */

package examples.funapplication;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;
import utilities.graphics.BasicImageUtility;
import visual_signal_components.passive_rendering.AbstractTextPanel;

/**
 *
 * @author Administrator
 */
public class FunPanel extends AbstractTextPanel{
    
    private int x_cnt = 300;
    private int y_cnt = 300;
    private Vector<BufferedImage> imageVector = new Vector();
    private File[] files;
    private int index = 0;
    private float composite = 1.0f;
    private boolean ascending = false;
    private boolean scale_up = true;
    private boolean increaseOffset = true;
    private RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    private double deg = 0.0;
    private double scalex = 0.1;
    private double scaley = 0.1;
    private double maxSizeX = 0.5;
    private double maxSizeY = 0.5;
    private int offSetX = -100;
    private int offSetY = -100;
    private int maxOffSetX = 200;
    private int maxOffSetY = 200;
    private int minOffSetX = -200;
    private int minOffSetY = -200;

    
    /** Creates a new instance of FunPanel */
    public FunPanel() {
        File file = new File("y:\\Werbung");
        files = file.listFiles();
        
        for(int i = 0; i<50;i++){
            BufferedImage image = BasicImageUtility.getInstance().load(files[i].getAbsolutePath(),BufferedImage.TYPE_4BYTE_ABGR_PRE);
            imageVector.add(BasicImageUtility.getInstance().toCompatibleImage(BasicImageUtility.getInstance().scale(image,1.0,1.0)));
        }
    }
    
    public void renderGraphics(Graphics2D g2) {
        
        Composite originalComposite = g2.getComposite();
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,composite);
        g2.setColor(Color.BLACK);
        g2.setComposite(ac);
        
        BufferedImage tmpImage = imageVector.get(index);
        BufferedImage scaledImage = BasicImageUtility.getInstance().scale(tmpImage,scalex,scaley,renderingHints,AffineTransformOp.TYPE_BICUBIC,false);
        BufferedImage rotatedImage = BasicImageUtility.getInstance().rotate(scaledImage,Math.toRadians(deg),scaledImage.getWidth()/2,scaledImage.getHeight()/2,renderingHints,AffineTransformOp.TYPE_BICUBIC,false);
        
        g2.setClip(getWidth()/2-rotatedImage.getWidth()/2-100+offSetX,getHeight()/2-rotatedImage.getHeight()/2-200+offSetY,getWidth(),getHeight());
        
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/10+100+offSetX,getHeight()/2-rotatedImage.getHeight()/10+offSetY);
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/8+100+offSetX,getHeight()/2-rotatedImage.getHeight()/8+offSetY);
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/6+100+offSetX,getHeight()/2-rotatedImage.getHeight()/6+offSetY);
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/4+100+offSetX,getHeight()/2-rotatedImage.getHeight()/4+offSetY);
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/2+100+offSetX,getHeight()/2-rotatedImage.getHeight()/2+offSetY);
        
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/10+offSetX,getHeight()/2-rotatedImage.getHeight()/10+offSetY);
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/8+offSetX,getHeight()/2-rotatedImage.getHeight()/8+offSetY);
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/6+offSetX,getHeight()/2-rotatedImage.getHeight()/6+offSetY);
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/4+offSetX,getHeight()/2-rotatedImage.getHeight()/4+offSetY);
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/2+offSetX,getHeight()/2-rotatedImage.getHeight()/2+offSetY);
        
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/10-100+offSetX,getHeight()/2-rotatedImage.getHeight()/10+offSetY);
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/8-100+offSetX,getHeight()/2-rotatedImage.getHeight()/8+offSetY);
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/6-100+offSetX,getHeight()/2-rotatedImage.getHeight()/6+offSetY);
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/4-100+offSetX,getHeight()/2-rotatedImage.getHeight()/4+offSetY);
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/2-100+offSetX,getHeight()/2-rotatedImage.getHeight()/2+offSetY);
        
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/10+100+offSetX,getHeight()/2-rotatedImage.getHeight()/10-200+offSetY);
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/8+100+offSetX,getHeight()/2-rotatedImage.getHeight()/8-200+offSetY);
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/6+100+offSetX,getHeight()/2-rotatedImage.getHeight()/6-200+offSetY);
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/4+100+offSetX,getHeight()/2-rotatedImage.getHeight()/4-200+offSetY);
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/2+100+offSetX,getHeight()/2-rotatedImage.getHeight()/2-200+offSetY);
        
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/10+offSetX,getHeight()/2-rotatedImage.getHeight()/10-100+offSetY);
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/8+offSetX,getHeight()/2-rotatedImage.getHeight()/8-100+offSetY);
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/6+offSetX,getHeight()/2-rotatedImage.getHeight()/6-100+offSetY);
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/4+offSetX,getHeight()/2-rotatedImage.getHeight()/4-100+offSetY);
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/2+offSetX,getHeight()/2-rotatedImage.getHeight()/2-100+offSetY);
        
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/10-100+offSetX,getHeight()/2-rotatedImage.getHeight()/10+100+offSetY);
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/8-100+offSetX,getHeight()/2-rotatedImage.getHeight()/8+100+offSetY);
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/6-100+offSetX,getHeight()/2-rotatedImage.getHeight()/6+100+offSetY);
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/4-100+offSetX,getHeight()/2-rotatedImage.getHeight()/4+100+offSetY);
        g2.drawImage(rotatedImage ,null,getWidth()/2-rotatedImage.getWidth()/2-100+offSetX,getHeight()/2-rotatedImage.getHeight()/2+100+offSetY);
        
        index++;
        if(index==imageVector.size()){
            index=0;
        }
        if(ascending){
            composite+=0.01;
        }else{
            composite-=0.01;
        }
        if(composite>1.0f){
            composite=1.0f;
            
            ascending=false;
        }else if(composite<0.0f){
            composite=0.0f;
            ascending=true;
        }
        
        if(scale_up){
            scalex+=0.01;
            scaley+=0.01;
        }else {
            scalex-=0.01;
            scaley-=0.01;
        }
        
        if(scalex>maxSizeX){
            scalex=maxSizeX;
            scaley=maxSizeY;
            scale_up = false;
        } else if(scalex<0.1){
            scalex=0.1;
            scaley=0.1;
            scale_up = true;
        }
        
        if(increaseOffset){
            offSetX++;
            offSetY++;
        }else {
            offSetX--;
            offSetY--;
        }
        
        if(offSetX<minOffSetX){
            offSetX=minOffSetX;
            offSetY=minOffSetY;
            increaseOffset=true;
        } else if(offSetX>maxOffSetX){
            offSetX=maxOffSetX;
            offSetY=maxOffSetY;
            increaseOffset=false;
        }
        
        deg+=2;
        if(deg>350){
            deg = 0;
        }
        g2.setComposite(originalComposite);
    }
}
