/*
 * ScalingWizard.java
 *
 * Created on 17. Juli 2007, 21:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.gtec;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import utilities.PropertiesReader;

/**
 *
 * @author Administrator
 */

public class ScalingWizard {
    private ScalingPanel scalingPanel;
    private JFrame frame = new JFrame();
    private PropertiesReader propertiesReader = new PropertiesReader();
    private ButtonHandler buttonHandler = new ButtonHandler();
    private ScalingConfiguration scalingConfiguration = new ScalingConfiguration();
    private String iniPath;
    
    /** Creates a new instance of ScalingWizard */
    public ScalingWizard() {
        scalingPanel = new ScalingPanel();
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(scalingPanel,BorderLayout.CENTER);
        frame.setSize(400,750);
        scalingPanel.getOkButton().setActionCommand(ScalingPanel.OK_BUTTON);
        scalingPanel.getOkButton().addActionListener(buttonHandler);
    }
    
    private static void usage(){
        System.out.println("-device <DEVICE_NAME>");
        System.out.println("where DEVICE_NAME can be usbAmpA, usbAmpB, usbAmpC or usbAmpD");
    }
    
    public String getIniPath() {
        return iniPath;
    }
    
    public void setIniPath(String iniPath) {
        this.iniPath = iniPath;
    }
    
    public double getScaling(int channel){
        return scalingPanel.getScaling(channel);
    }
    
    public int getUnit(int channel){
        return scalingPanel.getUnit(channel);
    }
    
    public void setScalingConfiguration(ScalingConfiguration scalingConfiguration) {
        this.scalingConfiguration = scalingConfiguration;
        for (int channel = 0; channel < scalingConfiguration.getNumChannels(); channel++) {
            scalingPanel.setScaling(channel,scalingConfiguration.getScaling(channel));
            scalingPanel.setUnit(channel,scalingConfiguration.getUnitIndex(channel));
        }
    }
    
    public ScalingConfiguration getScalingConfiguration(){
        for (int channel = 0; channel < scalingConfiguration.getNumChannels(); channel++) {
            scalingConfiguration.setScaling(channel,(int)getScaling(channel), getUnit(channel));
        }
        return scalingConfiguration;
    }
    
    public void setVisible(boolean flag){
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        
        if(args.length!=2){
            usage();
        }else if(!args[0].equalsIgnoreCase("-device")){
            usage();
        }else{
            String file_separator = System.getProperty("file.separator");
            String iniPath = System.getProperty("user.dir") + file_separator + "media" + file_separator + "protocol" + file_separator + args[1] + file_separator;
            ScalingWizard aScalingWizard = new ScalingWizard();
            aScalingWizard.setIniPath(iniPath+"scaling.ini");
            aScalingWizard.setVisible(true);
            ScalingConfiguration scalingConfiguration = new ScalingConfiguration();
            scalingConfiguration.load(aScalingWizard.getIniPath());
            aScalingWizard.setScalingConfiguration(scalingConfiguration);
        }
    }
    
    class ButtonHandler implements ActionListener{
        
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase(ScalingPanel.OK_BUTTON)){
                getScalingConfiguration().save(getIniPath());
            }
        }
    }
}