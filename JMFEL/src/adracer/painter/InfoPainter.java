/*
 * InfoPainter.java
 *
 * Created on 16. Mai 2007, 18:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package adracer.painter;

import panel.eye.EyetrackerInfoPanel;
import eye.insight.InsightDatagramPacketDecoder;
import java.awt.Color;
import utilities.graphics.AbstractPainter;

/**
 *
 * @author Administrator
 */
public class InfoPainter extends AbstractPainter{
    
    private EyetrackerInfoPanel infoPanel = new EyetrackerInfoPanel();
    private InsightDatagramPacketDecoder datagramdecoder;
    
    public InfoPainter() {
        infoPanel.setX(20);
        infoPanel.setY(20);
        infoPanel.setColor(Color.WHITE);
    }

    public InsightDatagramPacketDecoder getDatagramdecoder() {
        return datagramdecoder;
    }

    public void setDatagramdecoder(InsightDatagramPacketDecoder datagramdecoder) {
        this.datagramdecoder = datagramdecoder;
    }
    
    public void repaint() {
        try{
            if(getDatagramdecoder().getHeadX()>-1000){
                infoPanel.setPitch(getDatagramdecoder().getHeadPitch()/10);
                infoPanel.setYaw(getDatagramdecoder().getHeadYaw()/10);
                infoPanel.setRoll(getDatagramdecoder().getHeadRoll()/10);
                infoPanel.setXPosition(getDatagramdecoder().getHeadX()/10);
                infoPanel.setYPosition(getDatagramdecoder().getHeadY()/10);
                infoPanel.setZPosition(getDatagramdecoder().getHeadZ()/10);
            }else {
                infoPanel.setPitch(0.0f);
                infoPanel.setYaw(0.0f);
                infoPanel.setRoll(0.0f);
                infoPanel.setXPosition(0.0f);
                infoPanel.setYPosition(0.0f);
                infoPanel.setZPosition(0.0f);
            }
        }catch(Exception e){};
            
        infoPanel.render(getGraphics());
    }
}