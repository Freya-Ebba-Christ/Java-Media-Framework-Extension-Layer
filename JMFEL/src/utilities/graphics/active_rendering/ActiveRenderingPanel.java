package utilities.graphics.active_rendering;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import utilities.graphics.*;
public abstract class ActiveRenderingPanel extends JPanel implements MouseListener, MouseMotionListener{
    private ByteImage aByteImage;
    private boolean running = false;
    private int x_MouseCoordinate = 0;
    private int y_MouseCoordinate = 0;
    private int sleepTime = 50;
    
    public ActiveRenderingPanel(){
    }
    
    public void setByteImage(ByteImage aByteImage) {
        this.aByteImage = aByteImage;
    }
    
    public ByteImage getByteImage() {
        return aByteImage;
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
    
    public void init(){
        aByteImage = new ByteImage(getSize().width,getSize().height,BufferedImage.TYPE_4BYTE_ABGR);
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    public abstract void render(int w, int h,Graphics2D g2d);
}
