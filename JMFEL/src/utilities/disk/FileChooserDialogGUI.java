/*
 * DialogGUI.java
 *
 * Created on 18. Juni 2007, 09:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilities.disk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import utilities.graphics.BasicFrameMouseInputAdapter;

/**
 *
 * @author Administrator
 */

public class FileChooserDialogGUI extends JFrame{
    private JFileChooser fileChooser;
    private JLabel background;
    private JButton exitButton = new JButton();
    private EventHandler eventHandler;
    private CustomFileChooser customFileChooser;
    
    public JFileChooser getFileChooser() {
        return fileChooser;
    }
    
    public void setBackground(JLabel background) {
        this.background = background;
    }
    
    public void setCustomFileChooser(CustomFileChooser customFileChooser) {
        this.customFileChooser = customFileChooser;
    }
    
    public void center(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int)dim.getWidth()/2-getWidth()/2,(int)dim.getHeight()/2-getHeight()/2);
    }
    
    public FileChooserDialogGUI() {
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        }catch(Exception e){System.out.println(e);};
        
        setUndecorated(true);
        eventHandler = new EventHandler();
        exitButton.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\exit_small.png"));
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusable(false);
        exitButton.setActionCommand("EXIT");
        exitButton.addActionListener(eventHandler);
        
        getContentPane().add(exitButton);
        exitButton.setBounds(-8, 10, 80, 60);
        
        initComponents();
        
        JComponent component_0 = ((JComponent)fileChooser.getComponents()[0]);
        JComponent component_1 = ((JComponent)fileChooser.getComponents()[1]);
        JComponent component_2 = ((JComponent)fileChooser.getComponents()[2]);
        JComponent component_3 = ((JComponent)fileChooser.getComponents()[3]);
        component_0.setOpaque(false);
        
        JComponent component_4 = ((JComponent)component_0.getComponents()[0]);
        component_4.setOpaque(false);
        JComponent component_5 = ((JComponent)component_0.getComponents()[2]);
        component_5.setOpaque(false);
        JComponent component_6 = ((JComponent)component_3.getComponents()[3]);
        Color blue = new Color(33,79,156);
        component_6.setBackground(blue);
        JComponent component_7 = ((JComponent)component_3.getComponents()[0]);
        component_7.setBackground(blue);
        JComponent component_8 = ((JComponent)component_3.getComponents()[2]);
        component_8.setBackground(blue);
        JComponent component_9 = ((JComponent)component_6.getComponents()[0]);
        JComponent component_11 = ((JComponent)component_3.getComponents()[1]);
        
        component_11.setVisible(false);
    }
    
    class EventHandler implements ActionListener{
        
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase(exitButton.getActionCommand())){
                customFileChooser.hideGUI();
                System.out.println(getFileChooser().getSelectedFile());
            }
        }
    }
    
    private void initComponents() {
        fileChooser = new JFileChooser();
        background = new JLabel();
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        fileChooser.setBackground(new java.awt.Color(33, 79, 216));
        fileChooser.setDialogType(JFileChooser.APPROVE_OPTION);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setBorder(null);
        getContentPane().add(fileChooser);
        fileChooser.setBounds(10, 60, 400, 470);
        
        background.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\resources\\filedialog.png"));
        getContentPane().add(background);
        background.setBounds(0, -23, 450, 665);
        
        JPanel bg = new JPanel();
        bg.setBounds(0, 0, 450, 620);
        getContentPane().add(bg);
        BasicFrameMouseInputAdapter aBasicFrameMouseInputAdapter = new BasicFrameMouseInputAdapter();
        aBasicFrameMouseInputAdapter.setFrame(this);
        addMouseListener(aBasicFrameMouseInputAdapter);
        addMouseMotionListener(aBasicFrameMouseInputAdapter);
        center();
        pack();
    }
}