/*
 * ExportStatePainter.java
 *
 * Created on 3. September 2007, 06:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package application_container.painter;


import adracer.panel.utils.ExportStatePanel;
import utilities.graphics.AbstractPainter;

/**
 *
 * @author Administrator
 */
public class ExportStatePainter extends AbstractPainter{
    
    private ExportStatePanel exportStatePanel = new ExportStatePanel();

    public ExportStatePainter() {
    }

    public ExportStatePanel getExportStatePanel() {
        return exportStatePanel;
    }

    public void setExportStatePanel(ExportStatePanel exportStatePanel) {
        this.exportStatePanel = exportStatePanel;
    }
    
    public void repaint() {
        exportStatePanel.render(getGraphics());
    }
}
