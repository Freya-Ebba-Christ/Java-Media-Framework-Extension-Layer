/*
 * @(#)TickerCanvas.java	1.2 01/03/13
 *
 * Copyright (c) 1999-2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */
package custom_renderer;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class TickerCanvas extends Canvas implements MouseListener {
    public static final String NOTE = "This demo renders live video on a 3D cylinder surface using JMF and Java 3D. Use mouse left button to rotate, middle button to zoom and right button to translate. Double click in text area to dismiss help message. Double click again to resume displaying help message";
    public static final String TITLE = "JMF & Java 3D demo: live video on 3D surface";
    
    int x, y;
    Font f;
    boolean dispMessage;
    int[] sync = new int[0];

    public TickerCanvas () {
	super();
	this.setBackground(Color.black);
	this.x = 200;
	this.y = 15;
	this.dispMessage = true;
	this.f = new Font("Serif", Font.BOLD, 14);
	this.addMouseListener(this);
    }
    
    public void updateCoord() {
	synchronized ( sync ) {
	    x -= 10;
	    if ( x < -1600 )
		x = 200;
	}
    }

    public void update(Graphics g) {
	paint(g);
	requestFocus();
    }

    public void paint(Graphics g) {
	if ( dispMessage ) {
	    Image buffer = this.createImage(296,20);
	    Graphics bg = buffer.getGraphics();
	    bg.setFont(f);
	    bg.setColor(Color.yellow);
	    bg.drawString(NOTE, x, y);
	    bg.dispose();
	    
	    g.drawImage(buffer, 0, 0, null);
	} else {
	    Image buffer = this.createImage(296,20);
	    Graphics bg = buffer.getGraphics();
	    bg.setFont(f);
	    bg.setColor(Color.yellow);
	    bg.drawString(TITLE, 2, y);
	    bg.dispose();
	    
	    g.drawImage(buffer, 0, 0, null);
	}

    }
    
    // ------ MouseEvent
    public void mouseClicked(MouseEvent evt) {
	int clickCount = evt.getClickCount();
	if ( clickCount >= 2 ) {
	    synchronized ( sync ) {
		if ( dispMessage ) {
		    x = -3000;
		    dispMessage = false;
		    this.repaint();
		} else { 
		    x = 200;
		    dispMessage = true;
		}
	    }
	}
    }

    public void mousePressed(MouseEvent evt) {
    }

    public void mouseReleased(MouseEvent evt) {
    }

    public void mouseEntered(MouseEvent evt) {
    }

    public void mouseExited(MouseEvent evt) {
    }

    // ------------
    public boolean getDisplayFlag() {
	return dispMessage;
    }
}
    

