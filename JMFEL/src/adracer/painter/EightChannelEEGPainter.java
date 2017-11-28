/*
 * Large16ChannelEEGPainter.java
 *
 * Created on 11. Dezember 2007, 18:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package adracer.painter;
import panel.eeg.EightChannelPanel;
import utilities.graphics.AbstractPainter;

/**
 *
 * @author Administrator
 */
public class EightChannelEEGPainter extends AbstractPainter{
    private EightChannelPanel panel = new EightChannelPanel();

    public EightChannelPanel getPanel() {
        return panel;
    }

    public void setPanel(EightChannelPanel panel) {
        this.panel = panel;
    }

    public void repaint() {
        panel.render(getGraphics());
    }
}
