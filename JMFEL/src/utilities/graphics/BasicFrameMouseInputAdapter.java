/*
 * FrameMouseInputAdapter.java
 *
 * Created on 12. Juni 2007, 11:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilities.graphics;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

/**
 *
 * @author Administrator
 *
 */

public class BasicFrameMouseInputAdapter extends MouseInputAdapter{
    private Point offset;
    private JFrame frame;
    
    public BasicFrameMouseInputAdapter(){
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    private void showPopup(MouseEvent e) {
        final JPopupMenu m = new JPopupMenu();
        
        m.add(new AbstractAction("Hide") {
            public void actionPerformed(ActionEvent e) {
                frame.setState(JFrame.ICONIFIED);
            }
        });
        m.add(new AbstractAction("Close") {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        m.pack();
        m.show(e.getComponent(), e.getX(), e.getY());
    }
    public void mousePressed(MouseEvent e) {
        offset = e.getPoint();
        if (e.isPopupTrigger()) {
            showPopup(e);
        }
    }
    public void mouseDragged(MouseEvent e) {
        if (!SwingUtilities.isLeftMouseButton(e))
            return;
        Point where = e.getPoint();
        where.translate(-offset.x, -offset.y);
        Point loc = frame.getLocationOnScreen();
        loc.translate(where.x, where.y);
        frame.setLocation(loc.x, loc.y);
    }
    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
            showPopup(e);
        }
    }
}