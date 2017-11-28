/*
 * ChannelNumberPainter.java
 *
 * Created on 23. August 2007, 14:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package application_container.painter;
import panel.eeg.ChannelNumberPanel;
import utilities.graphics.AbstractPainter;

/**
 *
 * @author Administrator
 */
public class ChannelNumberPainter extends AbstractPainter{
    
    private ChannelNumberPanel channelPanel = new ChannelNumberPanel();
    /** Creates a new instance of ChannelNumberPainter */
    public ChannelNumberPainter() {
    }

    public ChannelNumberPanel getChannelPanel() {
        return channelPanel;
    }

    public void setChannelPanel(ChannelNumberPanel channelPanel) {
        this.channelPanel = channelPanel;
    }
    
    public void repaint() {
        channelPanel.render(getGraphics());
    }
}
