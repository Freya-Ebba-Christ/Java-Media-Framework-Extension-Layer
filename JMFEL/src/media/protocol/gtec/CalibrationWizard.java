/*
 * CalibrationWizard.java
 *
 * Created on 17. Juli 2007, 17:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.gtec;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 *
 * @author Administrator
 */
public class CalibrationWizard {
    private CalibrationPanel calibrationPanel;
    private AbstractUSBAmp usbAmp;
    private CalibrationWizardButtonListener calibrationWizardButtonListener;
    private JFrame frame = new JFrame();
    private float[] factor = new float[16];
    private float[] offset = new float[16];
    private int mode = -1;
    
    
    /** Creates a new instance of CalibrationWizard */
    public CalibrationWizard() {
        calibrationPanel = new CalibrationPanel();
        calibrationPanel.getStartButton().setActionCommand(calibrationPanel.START);
        calibrationPanel.getSaveButton().setActionCommand(calibrationPanel.SAVE);
        calibrationWizardButtonListener = new CalibrationWizardButtonListener();
        calibrationPanel.getStartButton().addActionListener(calibrationWizardButtonListener);
        calibrationPanel.getSaveButton().addActionListener(calibrationWizardButtonListener);
        
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(calibrationPanel,BorderLayout.CENTER);
        frame.setSize(400,550);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                if(usbAmp!=null)
                    try{
                        usbAmp.close();
                    }catch (Exception e){System.out.println(e);};
                    System.exit(0);
            }
        });
    }
    
    public AbstractUSBAmp getUsbAmp() {
        return usbAmp;
    }
    
    public void setVisible(boolean flag){
        frame.setVisible(true);
    }
    
    public void setUsbAmp(AbstractUSBAmp usbAmp) {
        this.usbAmp = usbAmp;
        calibrationPanel.getSerialLabel().setText(calibrationPanel.getSerialLabel().getText()+getUsbAmp().getSerialNumber());
    }
    
    class CalibrationWizardButtonListener implements ActionListener{
        
        public void actionPerformed(ActionEvent e) {
            
            if(e.getActionCommand().equalsIgnoreCase(calibrationPanel.START)){
                try{
                    calibrationPanel.getStartButton().setEnabled(false);
                    calibrationPanel.getSaveButton().setEnabled(false);
                    mode = getUsbAmp().getMode();
                    getUsbAmp().setMode(Amplifier.M_CALIBRATE);
                    getUsbAmp().calibrate(factor,offset);
                    
                    for (int chn = 0; chn < 16; chn++) {
                        calibrationPanel.setOffset(chn,offset[chn]);
                        calibrationPanel.setScaling(chn,factor[chn]);
                    }
                }catch(Exception ex){System.out.println(e);};
                calibrationPanel.getStartButton().setEnabled(true);
                calibrationPanel.getSaveButton().setEnabled(true);
            }
            
            if(e.getActionCommand().equalsIgnoreCase(calibrationPanel.SAVE)){
                try{
                    getUsbAmp().setScale(factor,offset);
                }catch(Exception ex){System.out.println(e);};
            }
            try{
                getUsbAmp().setMode(mode);
            }catch(Exception ex){System.out.println(e);};
        }
    }
    
    private static void usage(){
        System.out.println("-serial <SERIAL_NUMBER>");
    }
    
    public static void main(String[] args) {
        if(args.length!=2){
            usage();
        }else if(!args[0].equalsIgnoreCase("-serial")){
            usage();
        }else{
            CalibrationWizard aCalibrationWizard = new CalibrationWizard();
            USBAmp amp = new USBAmp();
            amp.setSerialNumber(args[1]);
            amp.setAmp(new NativeAmplifierA());
            
            try{
                amp.open();
                try{
                    amp.setSampleRate(256);
                }catch(Exception e){System.out.println(e);};
                
                aCalibrationWizard.setUsbAmp(amp);
            }catch(Exception ex){System.out.println(ex);};
            aCalibrationWizard.setVisible(true);
        }
    }
}


