/*
 * CaptureStateApplication.java
 *
 * Created on 11. Oktober 2007, 17:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package capture_controller.gui;
import application_container.painter.ClockPainter;
import capture_controller.CaptureApplication;
import examples.mediaplayer.MediaPlayerApplication;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import utilities.graphics.BasicImageUtility;
import utilities.graphics.passive_rendering.ApplicationContainer;
import utilities.graphics.passive_rendering.Surface;

/**
 *
 * @author Administrator
 */
public class CaptureStateApplication extends ApplicationContainer{
    private ClockPainter clockPainter;
    private String backGround;
    public static final String NEW_BUTTON = "NEW_BUTTON";
    public static final String PLAY_BUTTON = "PLAY_BUTTON";
    public static final String STOP_BUTTON = "STOP_BUTTON";
    public static final String PAUSE_BUTTON = "PAUSE_BUTTON";
    
    private ImageIcon new_button_image = new ImageIcon(System.getProperty("user.dir")+"\\resources\\diskbutton_small.png");
    private ImageIcon new_button_glow_image = new ImageIcon(System.getProperty("user.dir")+"\\resources\\diskbutton_glow_small.png");
    private ImageIcon play_button_image = new ImageIcon(System.getProperty("user.dir")+"\\resources\\playbutton_small.png");
    private ImageIcon play_button_glow_image = new ImageIcon(System.getProperty("user.dir")+"\\resources\\playbutton_glow_small.png");
    private ImageIcon stop_button_image = new ImageIcon(System.getProperty("user.dir")+"\\resources\\stopbutton_small.png");
    private ImageIcon stop_button_glow_image = new ImageIcon(System.getProperty("user.dir")+"\\resources\\stopbutton_glow_small.png");
    private ImageIcon pause_button_image = new ImageIcon(System.getProperty("user.dir")+"\\resources\\pausebutton_small.png");
    private ImageIcon pause_button_glow_image = new ImageIcon(System.getProperty("user.dir")+"\\resources\\pausebutton_glow_small.png");
    private JButton new_button = new JButton();
    private JButton new_button_glow = new JButton();
    private JButton play_button = new JButton();
    private JButton play_button_glow = new JButton();
    private JButton stop_button = new JButton();
    private JButton stop_button_glow = new JButton();
    private JButton pause_button = new JButton();
    private JButton pause_button_glow = new JButton();
    private CaptureStatePainter captureStatePainter;
    private JFileChooser chooser = new JFileChooser();
    private CaptureApplication captureApplication;
    public static final int MODE_OPEN = JFileChooser.OPEN_DIALOG;
    public static final int MODE_SAVE = JFileChooser.SAVE_DIALOG;
    private int mode = MODE_OPEN;
    private String newButtonToolTip = " ";
    private String playButtonToolTip = " ";
    private String stopButtonToolTip = " ";
    private String pauseButtonToolTip = " ";
    
    public void setMode(int mode) {
        this.mode = mode;
    }
    
    public String getNewButtonToolTip() {
        return newButtonToolTip;
    }
    
    public String getPauseButtonToolTip() {
        return pauseButtonToolTip;
    }
    
    public String getPlayButtonToolTip() {
        return playButtonToolTip;
    }
    
    public String getStopButtonToolTip() {
        return stopButtonToolTip;
    }
    
    public void setNewButtonToolTip(String newButtonToolTip) {
        this.newButtonToolTip = newButtonToolTip;
    }
    
    public void setPauseButtonToolTip(String pauseButtonToolTip) {
        this.pauseButtonToolTip = pauseButtonToolTip;
    }
    
    public void setPlayButtonToolTip(String playButtonToolTip) {
        this.playButtonToolTip = playButtonToolTip;
    }
    
    public void setStopButtonToolTip(String stopButtonToolTip) {
        this.stopButtonToolTip = stopButtonToolTip;
    }
    
    public int getMode() {
        return mode;
    }
    
    public ClockPainter getClockPainter() {
        return clockPainter;
    }
    
    public void setClockPainter(ClockPainter clockPainter) {
        this.clockPainter = clockPainter;
    }
    
    public String getBackGround() {
        return backGround;
    }
    
    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }
    
    public CaptureStatePainter getCaptureStatePainter() {
        return captureStatePainter;
    }
    
    public void setCaptureStatePainter(CaptureStatePainter captureStatePainter) {
        this.captureStatePainter = captureStatePainter;
    }
    
    public CaptureApplication getCaptureApplication() {
        return captureApplication;
    }
    
    public void setCaptureApplication(CaptureApplication captureApplication) {
        this.captureApplication = captureApplication;
    }
    
    public void init(){
        if(getMode()==MODE_OPEN){
            play_button_image = new ImageIcon(System.getProperty("user.dir")+"\\resources\\playbutton_small.png");
            play_button_glow_image = new ImageIcon(System.getProperty("user.dir")+"\\resources\\playbutton_glow_small.png");
        }else if(getMode()==MODE_SAVE){
            play_button_image = new ImageIcon(System.getProperty("user.dir")+"\\resources\\recordbutton_small.png");
            play_button_glow_image = new ImageIcon(System.getProperty("user.dir")+"\\resources\\recordbutton_glow_small.png");
        }
        String file_separator = System.getProperty("file.separator");
        setRestoreLocationEnabled(true);
        setLocationINIPath(System.getProperty("user.dir")+file_separator+"capture_controller"+file_separator+"gui"+file_separator+"capture_controllerGUI.ini");
        BufferedImage image = BasicImageUtility.getInstance().load(System.getProperty("user.dir")+file_separator+"resources"+file_separator+getBackGround(),BufferedImage.TYPE_3BYTE_BGR);
        Surface surface = new Surface();
        surface.setSize(image.getWidth(),image.getHeight());
        surface.setPreferredSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setMinimumSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setMaximumSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setCycles(20);
        surface.setBackgroundImage(image);
        clockPainter = new ClockPainter();
        clockPainter.getClockPanel().setX(10);
        clockPainter.getClockPanel().setY(10);
        
        captureStatePainter = new CaptureStatePainter();
        captureStatePainter.getPanel().setX(10);
        captureStatePainter.getPanel().setY(80);
        captureStatePainter.getPanel().setWidth(image.getWidth()-20);
        captureStatePainter.getPanel().setHeight(image.getHeight()-80);
        
        surface.addPainter(clockPainter);
        surface.addPainter(captureStatePainter);
        surface.setFPSEnabled(true);
        surface.start();
        setVisualComponent(surface);
        super.init();
        new_button.setPressedIcon(new_button_glow_image);
        new_button.setIcon(new_button_image);
        new_button.setOpaque(false);
        new_button.setContentAreaFilled(false);
        new_button.setBorderPainted(false);
        new_button.setFocusable(false);
        new_button.setBounds(20, 20, new_button_image.getIconWidth(), new_button_image.getIconHeight());
        
        play_button.setPressedIcon(play_button_glow_image);
        play_button.setIcon(play_button_image);
        play_button.setOpaque(false);
        play_button.setContentAreaFilled(false);
        play_button.setBorderPainted(false);
        play_button.setFocusable(false);
        play_button.setBounds(20, 20, play_button_image.getIconWidth(), play_button_image.getIconHeight());
        
        stop_button.setPressedIcon(stop_button_glow_image);
        stop_button.setIcon(stop_button_image);
        stop_button.setOpaque(false);
        stop_button.setContentAreaFilled(false);
        stop_button.setBorderPainted(false);
        stop_button.setFocusable(false);
        stop_button.setBounds(20, 20, stop_button_image.getIconWidth(), stop_button_image.getIconHeight());
        
        pause_button.setPressedIcon(pause_button_glow_image);
        pause_button.setIcon(pause_button_image);
        pause_button.setOpaque(false);
        pause_button.setContentAreaFilled(false);
        pause_button.setBorderPainted(false);
        pause_button.setFocusable(false);
        pause_button.setBounds(20, 20, pause_button_image.getIconWidth(), pause_button_image.getIconHeight());
        
        new_button.setLocation(getVisualComponent().getWidth()/2-(int)(new_button_image.getIconWidth()*2.0),getVisualComponent().getHeight()-(int)(new_button_image.getIconHeight()*1.3));
        play_button.setLocation(getVisualComponent().getWidth()/2-(int)(play_button_glow_image.getIconWidth()*1.0),getVisualComponent().getHeight()-(int)(play_button_glow_image.getIconHeight()*1.3));
        stop_button.setLocation(getVisualComponent().getWidth()/2,getVisualComponent().getHeight()-(int)(stop_button_glow_image.getIconHeight()*1.3));
        pause_button.setLocation(getVisualComponent().getWidth()/2-(int)(pause_button_glow_image.getIconWidth()*-1.0),getVisualComponent().getHeight()-(int)(pause_button_glow_image.getIconHeight()*1.3));
        
        getVisualComponent().add(new_button);
        getVisualComponent().add(play_button);
        getVisualComponent().add(pause_button);
        getVisualComponent().add(stop_button);
        
        new_button.setActionCommand(NEW_BUTTON);
        play_button.setActionCommand(PLAY_BUTTON);
        stop_button.setActionCommand(STOP_BUTTON);
        pause_button.setActionCommand(PAUSE_BUTTON);
        
        ButtonEventHandler buttonEventHandler = new ButtonEventHandler();
        
        new_button.addActionListener(buttonEventHandler);
        play_button.addActionListener(buttonEventHandler);
        stop_button.addActionListener(buttonEventHandler);
        pause_button.addActionListener(buttonEventHandler);
        
        new_button.addMouseListener(buttonEventHandler);
        play_button.addMouseListener(buttonEventHandler);
        stop_button.addMouseListener(buttonEventHandler);
        pause_button.addMouseListener(buttonEventHandler);
    }
    
    public CaptureStateApplication() {
    }
    
    class ButtonEventHandler implements ActionListener, MouseListener{
        private boolean fileOpen = false;
        
        public ButtonEventHandler(){
        }
        
        public void mouseReleased(MouseEvent e) {
        }
        
        public void mousePressed(MouseEvent e) {
        }
        
        public void mouseExited(MouseEvent e) {
            getCaptureStatePainter().getPanel().setToolTip(" ");
        }
        
        public void mouseEntered(MouseEvent e) {
            if(((JButton)e.getSource()).getActionCommand().equalsIgnoreCase(NEW_BUTTON)){
                getCaptureStatePainter().getPanel().setToolTip(getNewButtonToolTip());
            }else if(((JButton)e.getSource()).getActionCommand().equalsIgnoreCase(PLAY_BUTTON)){
                getCaptureStatePainter().getPanel().setToolTip(getPlayButtonToolTip());
            }else if(((JButton)e.getSource()).getActionCommand().equalsIgnoreCase(STOP_BUTTON)){
                getCaptureStatePainter().getPanel().setToolTip(getStopButtonToolTip());
            }else if(((JButton)e.getSource()).getActionCommand().equalsIgnoreCase(PAUSE_BUTTON)){
                getCaptureStatePainter().getPanel().setToolTip(getPauseButtonToolTip());
            }
        }
        
        public void mouseClicked(MouseEvent e) {
        }
        
        public void actionPerformed(ActionEvent e) {
            
            if(e.getActionCommand().equalsIgnoreCase(NEW_BUTTON)){
                
                JFrame frame = new JFrame();
                if(getMode()==JFileChooser.OPEN_DIALOG){
                    int returnVal = chooser.showOpenDialog(frame);
                    if(chooser.getSelectedFile()!=null){
                        getCaptureStatePainter().getPanel().setState(getCaptureStatePainter().getPanel().STOPPED);
                        getCaptureApplication().stop();
                        getCaptureStatePainter().getPanel().setFilename(chooser.getSelectedFile().getName());
                        getCaptureApplication().open(chooser.getSelectedFile());
                        fileOpen = true;
                    }
                }else if(getMode()==JFileChooser.SAVE_DIALOG){
                    int returnVal = chooser.showSaveDialog(frame);
                    if(chooser.getSelectedFile()!=null){
                        getCaptureStatePainter().getPanel().setState(getCaptureStatePainter().getPanel().STOPPED);
                        getCaptureApplication().stop();
                        getCaptureStatePainter().getPanel().setFilename(chooser.getSelectedFile().getName());
                        getCaptureApplication().open(chooser.getSelectedFile());
                        fileOpen = true;
                    }
                }
            }else if(e.getActionCommand().equalsIgnoreCase(PLAY_BUTTON)){
                if(getCaptureStatePainter().getPanel().getState()!=getCaptureStatePainter().getPanel().RUNNING&&fileOpen){
                    getCaptureStatePainter().getPanel().setState(getCaptureStatePainter().getPanel().RUNNING);
                    getCaptureApplication().start();
                }
            }else if(e.getActionCommand().equalsIgnoreCase(STOP_BUTTON)){
                if(getCaptureStatePainter().getPanel().getState()!=getCaptureStatePainter().getPanel().STOPPED&&fileOpen){
                    getCaptureStatePainter().getPanel().setState(getCaptureStatePainter().getPanel().STOPPED);
                    getCaptureApplication().stop();
                    fileOpen = false;
                }
            }else if(e.getActionCommand().equalsIgnoreCase(PAUSE_BUTTON)){
                if(getCaptureStatePainter().getPanel().getState()!=getCaptureStatePainter().getPanel().PAUSED&&fileOpen){
                    getCaptureStatePainter().getPanel().setState(getCaptureStatePainter().getPanel().PAUSED);
                    getCaptureApplication().pause();
                }
            }
        }
    }
    
    public static void main(String[] args) {
        CaptureStateApplication aCaptureStateApplication = new CaptureStateApplication();
        aCaptureStateApplication.setBackGround("background_capture_monitor.jpg");
        aCaptureStateApplication.setCaptureApplication(new MediaPlayerApplication());
        aCaptureStateApplication.init();
        aCaptureStateApplication.setVisible(true);
    }
}