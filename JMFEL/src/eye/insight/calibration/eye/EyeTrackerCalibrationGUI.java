/*
 * EyeTrackerCalibrationGUI.java
 *
 * Created on 12. Juni 2007, 15:05
 */

package eye.insight.calibration.eye;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 *
 * @author  Administrator
 */

public class EyeTrackerCalibrationGUI extends JFrame {
    
    private JButton area_1;
    private JButton area_10;
    private JButton area_11;
    private JButton area_12;
    private JButton area_13;
    private JButton area_14;
    private JButton area_15;
    private JButton area_16;
    private JButton area_17;
    private JButton area_18;
    private JButton area_19;
    private JButton area_2;
    private JButton area_20;
    private JButton area_21;
    private JButton area_22;
    private JButton area_23;
    private JButton area_24;
    private JButton area_25;
    private JButton area_26;
    private JButton area_27;
    private JButton area_28;
    private JButton area_29;
    private JButton area_3;
    private JButton area_30;
    private JButton area_31;
    private JButton area_32;
    private JButton area_33;
    private JButton area_34;
    private JButton area_35;
    private JButton area_36;
    private JButton area_37;
    private JButton area_38;
    private JButton area_39;
    private JButton area_4;
    private JButton area_40;
    private JButton area_41;
    private JButton area_42;
    private JButton area_5;
    private JButton area_6;
    private JButton area_7;
    private JButton area_8;
    private JButton area_9;
    private JLabel background;
    private JButton[] buttonContainer = new JButton[42];
    private EyeTrackerUDPSendServer eyeTrackerUDPSendServer = new EyeTrackerUDPSendServer();
    private EyeTrackerCalibrator eyeTrackerCalibrator;
    
    public EyeTrackerCalibrationGUI() {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        initComponents();
        initButtonContainer();
        setActionCommands();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                eyeTrackerCalibrator.hideGUI();
            }
        });
    }
    
    public EyeTrackerCalibrator getEyeTrackerCalibrator() {
        return eyeTrackerCalibrator;
    }
    
    public void setEyeTrackerCalibrator(EyeTrackerCalibrator eyeTrackerCalibrator) {
        this.eyeTrackerCalibrator = eyeTrackerCalibrator;
    }
    
    public EyeTrackerUDPSendServer getEyeTrackerUDPSendServer() {
        return eyeTrackerUDPSendServer;
    }
    
    public int getNumAOIs(){
        return buttonContainer.length;
    }
    
    public JButton getAOI(int i){
        return buttonContainer[i];
    }
    
    public void center(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int)dim.getWidth()/2-getWidth()/2,(int)dim.getHeight()/2-getHeight()/2);
    }
    
    private void setActionCommands(){
        for (int i = 0; i < getNumAOIs(); i++) {
            getAOI(i).setActionCommand(String.valueOf(i+1));
        }
    }
    
    private void initButtonContainer(){
        buttonContainer[0] = area_1;
        buttonContainer[1] = area_2;
        buttonContainer[2] = area_3;
        buttonContainer[3] = area_4;
        buttonContainer[4] = area_5;
        buttonContainer[5] = area_6;
        buttonContainer[6] = area_7;
        buttonContainer[7] = area_8;
        buttonContainer[8] = area_9;
        buttonContainer[9] = area_10;
        buttonContainer[10] = area_11;
        buttonContainer[11] = area_12;
        buttonContainer[12] = area_13;
        buttonContainer[13] = area_14;
        buttonContainer[14] = area_15;
        buttonContainer[15] = area_16;
        buttonContainer[16] = area_17;
        buttonContainer[17] = area_18;
        buttonContainer[18] = area_19;
        buttonContainer[19] = area_20;
        buttonContainer[20] = area_21;
        buttonContainer[21] = area_22;
        buttonContainer[22] = area_23;
        buttonContainer[23] = area_24;
        buttonContainer[24] = area_25;
        buttonContainer[25] = area_26;
        buttonContainer[26] = area_27;
        buttonContainer[27] = area_28;
        buttonContainer[28] = area_29;
        buttonContainer[29] = area_30;
        buttonContainer[30] = area_31;
        buttonContainer[31] = area_32;
        buttonContainer[32] = area_33;
        buttonContainer[33] = area_34;
        buttonContainer[34] = area_35;
        buttonContainer[35] = area_36;
        buttonContainer[36] = area_37;
        buttonContainer[37] = area_38;
        buttonContainer[38] = area_39;
        buttonContainer[39] = area_40;
        buttonContainer[40] = area_41;
        buttonContainer[41] = area_42;
    }
    
    private void initComponents() {
        area_1 = new JButton();
        area_2 = new JButton();
        area_3 = new JButton();
        area_4 = new JButton();
        area_5 = new JButton();
        area_6 = new JButton();
        area_7 = new JButton();
        area_8 = new JButton();
        area_9 = new JButton();
        area_10 = new JButton();
        area_11 = new JButton();
        area_12 = new JButton();
        area_13 = new JButton();
        area_14 = new JButton();
        area_15 = new JButton();
        area_16 = new JButton();
        area_17 = new JButton();
        area_18 = new JButton();
        area_19 = new JButton();
        area_20 = new JButton();
        area_21 = new JButton();
        area_22 = new JButton();
        area_23 = new JButton();
        area_24 = new JButton();
        area_25 = new JButton();
        area_26 = new JButton();
        area_27 = new JButton();
        area_28 = new JButton();
        area_29 = new JButton();
        area_30 = new JButton();
        area_31 = new JButton();
        area_32 = new JButton();
        area_33 = new JButton();
        area_34 = new JButton();
        area_35 = new JButton();
        area_36 = new JButton();
        area_37 = new JButton();
        area_38 = new JButton();
        area_39 = new JButton();
        area_40 = new JButton();
        area_41 = new JButton();
        area_42 = new JButton();
        background = new JLabel();
        
        getContentPane().setLayout(null);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        
        area_1.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_1.setBorderPainted(false);
        area_1.setContentAreaFilled(false);
        area_1.setFocusable(false);
        area_1.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_1);
        area_1.setBounds(130, 90, 93, 80);
        
        area_2.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_2.setBorderPainted(false);
        area_2.setContentAreaFilled(false);
        area_2.setFocusable(false);
        area_2.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_2);
        area_2.setBounds(200, 90, 93, 80);
        
        area_3.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_3.setBorderPainted(false);
        area_3.setContentAreaFilled(false);
        area_3.setFocusable(false);
        area_3.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_3);
        area_3.setBounds(270, 90, 93, 80);
        
        area_4.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_4.setBorderPainted(false);
        area_4.setContentAreaFilled(false);
        area_4.setFocusable(false);
        area_4.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_4);
        area_4.setBounds(340, 90, 93, 80);
        
        area_5.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_5.setBorderPainted(false);
        area_5.setContentAreaFilled(false);
        area_5.setFocusable(false);
        area_5.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_5);
        area_5.setBounds(410, 90, 93, 80);
        
        area_6.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_6.setBorderPainted(false);
        area_6.setContentAreaFilled(false);
        area_6.setFocusable(false);
        area_6.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_6);
        area_6.setBounds(480, 90, 93, 80);
        
        area_7.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_7.setBorderPainted(false);
        area_7.setContentAreaFilled(false);
        area_7.setFocusable(false);
        area_7.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_7);
        area_7.setBounds(550, 90, 93, 80);
        
        area_8.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_8.setBorderPainted(false);
        area_8.setContentAreaFilled(false);
        area_8.setFocusable(false);
        area_8.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_8);
        area_8.setBounds(130, 160, 93, 80);
        
        area_9.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_9.setBorderPainted(false);
        area_9.setContentAreaFilled(false);
        area_9.setFocusable(false);
        area_9.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_9);
        area_9.setBounds(200, 160, 93, 80);
        
        area_10.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_10.setBorderPainted(false);
        area_10.setContentAreaFilled(false);
        area_10.setFocusable(false);
        area_10.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_10);
        area_10.setBounds(270, 160, 93, 80);
        
        area_11.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_11.setBorderPainted(false);
        area_11.setContentAreaFilled(false);
        area_11.setFocusable(false);
        area_11.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_11);
        area_11.setBounds(340, 160, 93, 80);
        
        area_12.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_12.setBorderPainted(false);
        area_12.setContentAreaFilled(false);
        area_12.setFocusable(false);
        area_12.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_12);
        area_12.setBounds(410, 160, 93, 80);
        
        area_13.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_13.setBorderPainted(false);
        area_13.setContentAreaFilled(false);
        area_13.setFocusable(false);
        area_13.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_13);
        area_13.setBounds(480, 160, 93, 80);
        
        area_14.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_14.setBorderPainted(false);
        area_14.setContentAreaFilled(false);
        area_14.setFocusable(false);
        area_14.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_14);
        area_14.setBounds(550, 160, 93, 80);
        
        area_15.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_15.setBorderPainted(false);
        area_15.setContentAreaFilled(false);
        area_15.setFocusable(false);
        area_15.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_15);
        area_15.setBounds(130, 230, 93, 80);
        
        area_16.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_16.setBorderPainted(false);
        area_16.setContentAreaFilled(false);
        area_16.setFocusable(false);
        area_16.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_16);
        area_16.setBounds(200, 230, 93, 80);
        
        area_17.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_17.setBorderPainted(false);
        area_17.setContentAreaFilled(false);
        area_17.setFocusable(false);
        area_17.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_17);
        area_17.setBounds(270, 230, 93, 80);
        
        area_18.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_18.setBorderPainted(false);
        area_18.setContentAreaFilled(false);
        area_18.setFocusable(false);
        area_18.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_18);
        area_18.setBounds(340, 230, 93, 80);
        
        area_19.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_19.setBorderPainted(false);
        area_19.setContentAreaFilled(false);
        area_19.setFocusable(false);
        area_19.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_19);
        area_19.setBounds(410, 230, 93, 80);
        
        area_20.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_20.setBorderPainted(false);
        area_20.setContentAreaFilled(false);
        area_20.setFocusable(false);
        area_20.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_20);
        area_20.setBounds(480, 230, 93, 80);
        
        area_21.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_21.setBorderPainted(false);
        area_21.setContentAreaFilled(false);
        area_21.setFocusable(false);
        area_21.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_21);
        area_21.setBounds(550, 230, 93, 80);
        
        area_22.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_22.setBorderPainted(false);
        area_22.setContentAreaFilled(false);
        area_22.setFocusable(false);
        area_22.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_22);
        area_22.setBounds(130, 300, 93, 80);
        
        area_23.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_23.setBorderPainted(false);
        area_23.setContentAreaFilled(false);
        area_23.setFocusable(false);
        area_23.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_23);
        area_23.setBounds(200, 300, 93, 80);
        
        area_24.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_24.setBorderPainted(false);
        area_24.setContentAreaFilled(false);
        area_24.setFocusable(false);
        area_24.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_24);
        area_24.setBounds(270, 300, 93, 80);
        
        area_25.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_25.setBorderPainted(false);
        area_25.setContentAreaFilled(false);
        area_25.setFocusable(false);
        area_25.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_25);
        area_25.setBounds(340, 300, 93, 80);
        
        area_26.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_26.setBorderPainted(false);
        area_26.setContentAreaFilled(false);
        area_26.setFocusable(false);
        area_26.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_26);
        area_26.setBounds(410, 300, 93, 80);
        
        area_27.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_27.setBorderPainted(false);
        area_27.setContentAreaFilled(false);
        area_27.setFocusable(false);
        area_27.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_27);
        area_27.setBounds(480, 300, 93, 80);
        
        area_28.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_28.setBorderPainted(false);
        area_28.setContentAreaFilled(false);
        area_28.setFocusable(false);
        area_28.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_28);
        area_28.setBounds(550, 300, 93, 80);
        
        area_29.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_29.setBorderPainted(false);
        area_29.setContentAreaFilled(false);
        area_29.setFocusable(false);
        area_29.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_29);
        area_29.setBounds(130, 370, 93, 80);
        
        area_30.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_30.setBorderPainted(false);
        area_30.setContentAreaFilled(false);
        area_30.setFocusable(false);
        area_30.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_30);
        area_30.setBounds(200, 370, 93, 80);
        
        area_31.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_31.setBorderPainted(false);
        area_31.setContentAreaFilled(false);
        area_31.setFocusable(false);
        area_31.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_31);
        area_31.setBounds(270, 370, 93, 80);
        
        area_32.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_32.setBorderPainted(false);
        area_32.setContentAreaFilled(false);
        area_32.setFocusable(false);
        area_32.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_32);
        area_32.setBounds(340, 370, 93, 80);
        
        area_33.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_33.setBorderPainted(false);
        area_33.setContentAreaFilled(false);
        area_33.setFocusable(false);
        area_33.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_33);
        area_33.setBounds(410, 370, 93, 80);
        
        area_34.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_34.setBorderPainted(false);
        area_34.setContentAreaFilled(false);
        area_34.setFocusable(false);
        area_34.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_34);
        area_34.setBounds(480, 370, 93, 80);
        
        area_35.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_35.setBorderPainted(false);
        area_35.setContentAreaFilled(false);
        area_35.setFocusable(false);
        area_35.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_35);
        area_35.setBounds(550, 370, 93, 80);
        
        area_36.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_36.setBorderPainted(false);
        area_36.setContentAreaFilled(false);
        area_36.setFocusable(false);
        area_36.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_36);
        area_36.setBounds(130, 440, 93, 80);
        
        area_37.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_37.setBorderPainted(false);
        area_37.setContentAreaFilled(false);
        area_37.setFocusable(false);
        area_37.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_37);
        area_37.setBounds(200, 440, 93, 80);
        
        area_38.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_38.setBorderPainted(false);
        area_38.setContentAreaFilled(false);
        area_38.setFocusable(false);
        area_38.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_38);
        area_38.setBounds(270, 440, 93, 80);
        
        area_39.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_39.setBorderPainted(false);
        area_39.setContentAreaFilled(false);
        area_39.setFocusable(false);
        area_39.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_39);
        area_39.setBounds(340, 440, 93, 80);
        
        area_40.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_40.setBorderPainted(false);
        area_40.setContentAreaFilled(false);
        area_40.setFocusable(false);
        area_40.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_40);
        area_40.setBounds(410, 440, 93, 80);
        
        area_41.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_41.setBorderPainted(false);
        area_41.setContentAreaFilled(false);
        area_41.setFocusable(false);
        area_41.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_41);
        area_41.setBounds(480, 440, 93, 80);
        
        area_42.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker.png"));
        area_42.setBorderPainted(false);
        area_42.setContentAreaFilled(false);
        area_42.setFocusable(false);
        area_42.setPressedIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\marker_pressed.png"));
        getContentPane().add(area_42);
        area_42.setBounds(550, 440, 93, 80);
        
        background.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\eyetracker\\background.jpg"));
        getContentPane().add(background);
        background.setBounds(0, 0, 1024, 768);
        pack();
    }
}