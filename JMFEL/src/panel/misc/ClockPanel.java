/*
 * ClockPanel.java
 *
 * Created on 24. August 2007, 20:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package panel.misc;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.SimpleDateFormat;
import java.util.Date;
import visual_signal_components.passive_rendering.AbstractTextPanel;

/**
 *
 * @author Administrator
 */
public class ClockPanel extends AbstractTextPanel{
    private Font font = new Font("Arial Black", Font.BOLD, 25);
    private SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    private Date date = new Date();
    
    /** Creates a new instance of ChannelNumberPanel */
    public ClockPanel() {
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SimpleDateFormat getFormatter() {
        return formatter;
    }

    public void setFormatter(SimpleDateFormat formatter) {
        this.formatter = formatter;
    }
    
    public Date getDate() {
        return date;
    }
    
    
    
    public void renderGraphics(Graphics2D g2) {
        g2.setFont(font);
        Composite originalComposite = g2.getComposite();
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f);
        g2.setComposite(ac);
        g2.setColor(Color.DARK_GRAY);
        g2.drawRect(getX()+1,getY()+1,getWidth(),getHeight());
        g2.setColor(Color.WHITE);
        g2.fillRect(getX(),getY(),getWidth(),getHeight());
        ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.8f);
        g2.setComposite(ac);
        g2.setColor(Color.WHITE);
        int y_offset = (int)(getStringHelper().getStringHeight("00:00:00")/1.4);
        getDate().setTime(System.currentTimeMillis());
        
        String time = getFormatter().format(getDate());
        
        setHeight(getStringHelper().getStringHeight("00:00:00"));
        setWidth(getStringHelper().getStringWidth(font,"00:00:00")+15);
        g2.drawString(time,getX()+5,getY()+y_offset);
        g2.setComposite(originalComposite);
        g2.drawRect(getX()-2,getY()-2,getWidth(),getHeight());
    }
}