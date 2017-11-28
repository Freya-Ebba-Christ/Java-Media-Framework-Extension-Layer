/*
 * EnergyApplication.java
 *
 * Created on 23. August 2007, 13:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package application_container;

import utilities.graphics.passive_rendering.ApplicationContainer;
import utilities.graphics.passive_rendering.Surface;
import application_container.painter.ChannelNumberPainter;
import application_container.painter.SingleChannelEEGPainter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import utilities.graphics.BasicImageUtility;

/**
 *
 * @author Administrator
 */
public class SingleEEGChannelContainer extends ApplicationContainer{
    public static final String UP_BUTTON = "UP_BUTTON";
    public static final String DOWN_BUTTON = "DOWN_BUTTON";
    private ImageIcon up_button_glow = new ImageIcon(System.getProperty("user.dir")+"\\resources\\button_small_up_glow.gif");
    private ImageIcon up_button_no_glow = new ImageIcon(System.getProperty("user.dir")+"\\resources\\button_small_up_no_glow.gif");
    private ImageIcon down_button_glow = new ImageIcon(System.getProperty("user.dir")+"\\resources\\button_small_down_glow.gif");
    private ImageIcon down_button_no_glow = new ImageIcon(System.getProperty("user.dir")+"\\resources\\button_small_down_no_glow.gif");
    private JButton up_button = new JButton();
    private JButton down_button = new JButton();
    private ChannelButtonListener channelButtonListener;
    private ChannelNumberPainter channelNumberPainter;
    private int numChannels = 16;
    private String backGround;
    private SingleChannelEEGPainter singleChannelEEGPainter;
    
    /** Creates a new instance of EnergyApplication */
    public SingleEEGChannelContainer() {
    }
    
    public String getBackGround() {
        return backGround;
    }
    
    public SingleChannelEEGPainter getSingleChannelEEGPainter() {
        return singleChannelEEGPainter;
    }
    
    public void setSingleChannelEEGPainter(SingleChannelEEGPainter aSingleChannelEEGPainter) {
        this.singleChannelEEGPainter = aSingleChannelEEGPainter;
    }
    
    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }
    
    public void setNumChannels(int numChannels) {
        this.numChannels = numChannels;
    }
    
    public int getNumChannels() {
        return numChannels;
    }
    
    public void init() {
        String file_separator = System.getProperty("file.separator");
        //this enables that the last location known is stored to disk and restored next time...
        setRestoreLocationEnabled(true);
        setLocationINIPath(System.getProperty("user.dir")+file_separator+"application_container"+file_separator+"singleChannelEEG.ini");
        
        BufferedImage image = BasicImageUtility.getInstance().load(System.getProperty("user.dir")+"\\resources\\"+getBackGround(),BufferedImage.TYPE_3BYTE_BGR);
        Surface surface = new Surface();
        surface.setSize(image.getWidth(),image.getHeight());
        surface.setPreferredSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setMinimumSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setMaximumSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setCycles(20);
        
        channelNumberPainter = new ChannelNumberPainter();
        channelNumberPainter.getChannelPanel().setWidth(60);
        channelNumberPainter.getChannelPanel().setHeight(60);
        channelNumberPainter.getChannelPanel().setX(surface.getWidth()/2-channelNumberPainter.getChannelPanel().getWidth()/2);
        channelNumberPainter.getChannelPanel().setY(surface.getHeight()-channelNumberPainter.getChannelPanel().getHeight()-10);
        channelNumberPainter.getChannelPanel().setNumChannels(getNumChannels());
        surface.addPainter(channelNumberPainter);
        
        singleChannelEEGPainter = new SingleChannelEEGPainter();
        singleChannelEEGPainter.getPanel().setWidth(surface.getWidth()-20);
        singleChannelEEGPainter.getPanel().setHeight(surface.getHeight()-20-up_button_no_glow.getIconHeight());
        singleChannelEEGPainter.getPanel().setX(10);
        singleChannelEEGPainter.getPanel().setY(10);
        singleChannelEEGPainter.getPanel().getSignalPanel().setMaxVoltage(25.0f);
        singleChannelEEGPainter.getPanel().getSignalPanel().setSampleRate(1024);
        singleChannelEEGPainter.getPanel().getSignalPanel().setBufferLength(1024);
        singleChannelEEGPainter.getPanel().getSignalPanel().setTitleColor(Color.BLACK);
        singleChannelEEGPainter.getPanel().getSignalPanel().setSignalColor(Color.RED);
        singleChannelEEGPainter.getPanel().getSignalPanel().setBorderColor(Color.WHITE);
        
        surface.addPainter(singleChannelEEGPainter);
        surface.setBackgroundImage(BasicImageUtility.getInstance().toCompatibleImage(BasicImageUtility.getInstance().scale(image,1.0,1.0)));
        surface.setFPSEnabled(true);
        surface.start();
        setVisualComponent(surface);
        super.init();
        up_button.setPressedIcon(up_button_glow);
        up_button.setIcon(up_button_no_glow);
        up_button.setOpaque(false);
        up_button.setContentAreaFilled(false);
        up_button.setBorderPainted(false);
        up_button.setFocusable(false);
        up_button.setBounds(20, 20, up_button_glow.getIconWidth(), up_button_glow.getIconHeight());
        
        down_button.setPressedIcon(down_button_glow);
        down_button.setIcon(down_button_no_glow);
        down_button.setOpaque(false);
        down_button.setContentAreaFilled(false);
        down_button.setBorderPainted(false);
        down_button.setFocusable(false);
        down_button.setBounds(20, 20, down_button_glow.getIconWidth(), down_button_glow.getIconHeight());
        
        up_button.setLocation(getVisualComponent().getWidth()/2-(int)(up_button_glow.getIconWidth()*1.5),getVisualComponent().getHeight()-up_button_glow.getIconHeight());
        down_button.setLocation(getVisualComponent().getWidth()/2-up_button_glow.getIconWidth()+(int)(down_button_glow.getIconWidth()*1.5),getVisualComponent().getHeight()-down_button_glow.getIconHeight());
        
        getVisualComponent().add(up_button);
        getVisualComponent().add(down_button);
        
        up_button.setActionCommand(UP_BUTTON);
        down_button.setActionCommand(DOWN_BUTTON);
        channelButtonListener = new ChannelButtonListener();
        up_button.addActionListener(channelButtonListener);
        down_button.addActionListener(channelButtonListener);
    }
    
    public static void main(String[] args) {
        System.setProperty("sun.java2d.translaccel","true");
        SingleEEGChannelContainer aSingleEEGChannelContainer = new SingleEEGChannelContainer();
        aSingleEEGChannelContainer.setBackGround("background_singleChannel.jpg");
        aSingleEEGChannelContainer.setNumChannels(16);
        aSingleEEGChannelContainer.init();
        aSingleEEGChannelContainer.setVisible(true);
    }
    
    class ChannelButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            int maxChannels = channelNumberPainter.getChannelPanel().getNumChannels();
            int currentChannel = channelNumberPainter.getChannelPanel().getCurrentChannel();
            
            if(e.getActionCommand().equalsIgnoreCase(UP_BUTTON)){
                if(channelNumberPainter.getChannelPanel().getCurrentChannel()<maxChannels){
                    channelNumberPainter.getChannelPanel().setCurrentChannel(currentChannel+1);
                    getSingleChannelEEGPainter().getPanel().setCurrentChannel(channelNumberPainter.getChannelPanel().getCurrentChannel()-1);
                }
            }
            
            if(e.getActionCommand().equalsIgnoreCase(DOWN_BUTTON)){
                if(channelNumberPainter.getChannelPanel().getCurrentChannel()>1){
                    channelNumberPainter.getChannelPanel().setCurrentChannel(currentChannel-1);
                    getSingleChannelEEGPainter().getPanel().setCurrentChannel(channelNumberPainter.getChannelPanel().getCurrentChannel()-1);
                }
            }
        }
    }
}
