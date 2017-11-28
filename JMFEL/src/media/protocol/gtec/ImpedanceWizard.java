/*
 * ImpedanceWizard.java
 *
 * Created on 19. Juli 2007, 05:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.gtec;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import media.protocol.gtec.ImpedanceWizard.ImpedanceWizardButtonListener.ImpedanceCheckThread;

/**
 *
 * @author Administrator
 */
public class ImpedanceWizard {
    
    private ImpedanceCheckPanel impedanceCheckPanel;
    private JFrame frame = new JFrame();
    private AbstractUSBAmp usbAmp;
    private int mode = -1;
    
    /** Creates a new instance of ScalingWizard */
    public ImpedanceWizard() {
        impedanceCheckPanel = new ImpedanceCheckPanel();
        ImpedanceWizardButtonListener aImpedanceWizardButtonListener = new ImpedanceWizardButtonListener();
        impedanceCheckPanel.getStartButton().setActionCommand(impedanceCheckPanel.START);
        impedanceCheckPanel.getStartButton().addActionListener(aImpedanceWizardButtonListener);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(impedanceCheckPanel,BorderLayout.CENTER);
        frame.setSize(600,390);
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
    
    public void setUsbAmp(AbstractUSBAmp usbAmp) {
        this.usbAmp = usbAmp;
        impedanceCheckPanel.getSerialLabel().setText(impedanceCheckPanel.getSerialLabel().getText()+getUsbAmp().getSerialNumber());
    }
    
    public void setVisible(boolean flag){
        frame.setVisible(flag);
    }
    
    class ImpedanceWizardButtonListener implements ActionListener{
        
        private boolean running = false;
        private ImpedanceCheckThread impedanceCheckThread;
        
        public void actionPerformed(ActionEvent e) {
            if(!running){
                //backup the current mode
                try{
                    mode = getUsbAmp().getMode();
                    getUsbAmp().setMode(Amplifier.M_IMPEDANCE);
                }catch(Exception ex){System.out.println(ex);};
                
                impedanceCheckPanel.getStartButton().setText("Stop");
                impedanceCheckThread = new ImpedanceCheckThread();
                impedanceCheckThread.startThread();
                running = true;
                
            }else{
                impedanceCheckPanel.getStartButton().setText("Start");
                
                impedanceCheckThread.stopThread();
                try{
                    getUsbAmp().setMode(mode);
                }catch(Exception ex){System.out.println(ex);};
                
                running = false;
            }
        }
        
        class ImpedanceCheckThread extends Thread {
            
            private boolean checking = false;
            private double value = 0.0;
            
            public void run() {
                while(checking){
                    try{
                        
                        for (int i = 0; i <impedanceCheckPanel.getNumChannels()+4 ; i++) {
                            impedanceCheckPanel.getChannelNumberLabel(i).setForeground(Color.BLACK);
                        }
                        
                        impedanceCheckPanel.getChannelNumberLabel(0).setForeground(Color.GREEN);
                        value = getUsbAmp().getImpedance(1)/1000.0;
                        if(value<=5.0){
                            impedanceCheckPanel.getImpedanceGroupAChannel1().setBackground(Color.GREEN);
                        }else if(value>5.0&&value<=10.0){
                            impedanceCheckPanel.getImpedanceGroupAChannel1().setBackground(Color.YELLOW);
                        }else if(value>10.0){
                            impedanceCheckPanel.getImpedanceGroupAChannel1().setBackground(Color.RED);
                        }
                        impedanceCheckPanel.setImpedanceGroupAChannel1(value);
                        
                        if(!checking){
                            break;
                        }
                        
                        impedanceCheckPanel.getChannelNumberLabel(0).setForeground(Color.BLACK);
                        impedanceCheckPanel.getChannelNumberLabel(1).setForeground(Color.GREEN);
                        value = getUsbAmp().getImpedance(2)/1000.0;
                        if(value<=5.0){
                            impedanceCheckPanel.getImpedanceGroupAChannel2().setBackground(Color.GREEN);
                        }else if(value>5.0&&value<=10.0){
                            impedanceCheckPanel.getImpedanceGroupAChannel2().setBackground(Color.YELLOW);
                        }else if(value>10.0){
                            impedanceCheckPanel.getImpedanceGroupAChannel2().setBackground(Color.RED);
                        }
                        impedanceCheckPanel.setImpedanceGroupAChannel2(value);
                        
                        if(!checking){
                            break;
                        }
                        
                        impedanceCheckPanel.getChannelNumberLabel(1).setForeground(Color.BLACK);
                        impedanceCheckPanel.getChannelNumberLabel(2).setForeground(Color.GREEN);
                        value = getUsbAmp().getImpedance(3)/1000.0;
                        if(value<=5.0){
                            impedanceCheckPanel.getImpedanceGroupAChannel3().setBackground(Color.GREEN);
                        }else if(value>5.0&&value<=10.0){
                            impedanceCheckPanel.getImpedanceGroupAChannel3().setBackground(Color.YELLOW);
                        }else if(value>10.0){
                            impedanceCheckPanel.getImpedanceGroupAChannel3().setBackground(Color.RED);
                        }
                        
                        impedanceCheckPanel.setImpedanceGroupAChannel3(value);
                        
                        if(!checking){
                            break;
                        }
                        
                        impedanceCheckPanel.getChannelNumberLabel(2).setForeground(Color.BLACK);
                        impedanceCheckPanel.getChannelNumberLabel(3).setForeground(Color.GREEN);
                        value = getUsbAmp().getImpedance(4)/1000.0;
                        if(value<=5.0){
                            impedanceCheckPanel.getImpedanceGroupAChannel4().setBackground(Color.GREEN);
                        }else if(value>5.0&&value<=10.0){
                            impedanceCheckPanel.getImpedanceGroupAChannel4().setBackground(Color.YELLOW);
                        }else if(value>10.0){
                            impedanceCheckPanel.getImpedanceGroupAChannel4().setBackground(Color.RED);
                        }
                        
                        impedanceCheckPanel.setImpedanceGroupAChannel4(value);
                        
                        if(!checking){
                            break;
                        }
                        
                        impedanceCheckPanel.getChannelNumberLabel(3).setForeground(Color.BLACK);
                        impedanceCheckPanel.getChannelNumberLabel(4).setForeground(Color.GREEN);
                        value = getUsbAmp().getImpedance(5)/1000.0;
                        if(value<=5.0){
                            impedanceCheckPanel.getImpedanceGroupBChannel5().setBackground(Color.GREEN);
                        }else if(value>5.0&&value<=10.0){
                            impedanceCheckPanel.getImpedanceGroupBChannel5().setBackground(Color.YELLOW);
                        }else if(value>10.0){
                            impedanceCheckPanel.getImpedanceGroupBChannel5().setBackground(Color.RED);
                        }
                        
                        impedanceCheckPanel.setImpedanceGroupBChannel5(value);
                        
                        if(!checking){
                            break;
                        }
                        
                        impedanceCheckPanel.getChannelNumberLabel(4).setForeground(Color.BLACK);
                        impedanceCheckPanel.getChannelNumberLabel(5).setForeground(Color.GREEN);
                        value = getUsbAmp().getImpedance(6)/1000.0;
                        if(value<=5.0){
                            impedanceCheckPanel.getImpedanceGroupBChannel6().setBackground(Color.GREEN);
                        }else if(value>5.0&&value<=10.0){
                            impedanceCheckPanel.getImpedanceGroupBChannel6().setBackground(Color.YELLOW);
                        }else if(value>10.0){
                            impedanceCheckPanel.getImpedanceGroupBChannel6().setBackground(Color.RED);
                        }
                        
                        impedanceCheckPanel.setImpedanceGroupBChannel6(value);
                        
                        if(!checking){
                            break;
                        }
                        
                        impedanceCheckPanel.getChannelNumberLabel(5).setForeground(Color.BLACK);
                        impedanceCheckPanel.getChannelNumberLabel(6).setForeground(Color.GREEN);
                        value = getUsbAmp().getImpedance(7)/1000.0;
                        if(value<=5.0){
                            impedanceCheckPanel.getImpedanceGroupBChannel7().setBackground(Color.GREEN);
                        }else if(value>5.0&&value<=10.0){
                            impedanceCheckPanel.getImpedanceGroupBChannel7().setBackground(Color.YELLOW);
                        }else if(value>10.0){
                            impedanceCheckPanel.getImpedanceGroupBChannel7().setBackground(Color.RED);
                        }
                        
                        impedanceCheckPanel.setImpedanceGroupBChannel7(value);
                        
                        if(!checking){
                            break;
                        }
                        
                        impedanceCheckPanel.getChannelNumberLabel(6).setForeground(Color.BLACK);
                        impedanceCheckPanel.getChannelNumberLabel(7).setForeground(Color.GREEN);
                        value = getUsbAmp().getImpedance(8)/1000.0;
                        if(value<=5.0){
                            impedanceCheckPanel.getImpedanceGroupBChannel8().setBackground(Color.GREEN);
                        }else if(value>5.0&&value<=10.0){
                            impedanceCheckPanel.getImpedanceGroupBChannel8().setBackground(Color.YELLOW);
                        }else if(value>10.0){
                            impedanceCheckPanel.getImpedanceGroupBChannel8().setBackground(Color.RED);
                        }
                        
                        impedanceCheckPanel.setImpedanceGroupBChannel8(value);
                        
                        if(!checking){
                            break;
                        }
                        
                        impedanceCheckPanel.getChannelNumberLabel(7).setForeground(Color.BLACK);
                        impedanceCheckPanel.getChannelNumberLabel(8).setForeground(Color.GREEN);
                        value = getUsbAmp().getImpedance(9)/1000.0;
                        if(value<=5.0){
                            impedanceCheckPanel.getImpedanceGroupCChannel9().setBackground(Color.GREEN);
                        }else if(value>5.0&&value<=10.0){
                            impedanceCheckPanel.getImpedanceGroupCChannel9().setBackground(Color.YELLOW);
                        }else if(value>10.0){
                            impedanceCheckPanel.getImpedanceGroupCChannel9().setBackground(Color.RED);
                        }
                        
                        
                        impedanceCheckPanel.setImpedanceGroupCChannel9(value);
                        
                        if(!checking){
                            break;
                        }
                        
                        impedanceCheckPanel.getChannelNumberLabel(8).setForeground(Color.BLACK);
                        impedanceCheckPanel.getChannelNumberLabel(9).setForeground(Color.GREEN);
                        value = getUsbAmp().getImpedance(10)/1000.0;
                        if(value<=5.0){
                            impedanceCheckPanel.getImpedanceGroupCChannel10().setBackground(Color.GREEN);
                        }else if(value>5.0&&value<=10.0){
                            impedanceCheckPanel.getImpedanceGroupCChannel10().setBackground(Color.YELLOW);
                        }else if(value>10.0){
                            impedanceCheckPanel.getImpedanceGroupCChannel10().setBackground(Color.RED);
                        }
                        
                        impedanceCheckPanel.setImpedanceGroupCChannel10(value);
                        
                        if(!checking){
                            break;
                        }
                        
                        impedanceCheckPanel.getChannelNumberLabel(9).setForeground(Color.BLACK);
                        impedanceCheckPanel.getChannelNumberLabel(10).setForeground(Color.GREEN);
                        value = getUsbAmp().getImpedance(11)/1000.0;
                        if(value<=5.0){
                            impedanceCheckPanel.getImpedanceGroupCChannel11().setBackground(Color.GREEN);
                        }else if(value>5.0&&value<=10.0){
                            impedanceCheckPanel.getImpedanceGroupCChannel11().setBackground(Color.YELLOW);
                        }else if(value>10.0){
                            impedanceCheckPanel.getImpedanceGroupCChannel11().setBackground(Color.RED);
                        }
                        
                        impedanceCheckPanel.setImpedanceGroupCChannel11(value);
                        
                        if(!checking){
                            break;
                        }
                        
                        impedanceCheckPanel.getChannelNumberLabel(10).setForeground(Color.BLACK);
                        impedanceCheckPanel.getChannelNumberLabel(11).setForeground(Color.GREEN);
                        value = getUsbAmp().getImpedance(12)/1000.0;
                        if(value<=5.0){
                            impedanceCheckPanel.getImpedanceGroupCChannel12().setBackground(Color.GREEN);
                        }else if(value>5.0&&value<=10.0){
                            impedanceCheckPanel.getImpedanceGroupCChannel12().setBackground(Color.YELLOW);
                        }else if(value>10.0){
                            impedanceCheckPanel.getImpedanceGroupCChannel12().setBackground(Color.RED);
                        }
                        
                        impedanceCheckPanel.setImpedanceGroupCChannel12(value);
                        
                        if(!checking){
                            break;
                        }
                        
                        impedanceCheckPanel.getChannelNumberLabel(11).setForeground(Color.BLACK);
                        impedanceCheckPanel.getChannelNumberLabel(12).setForeground(Color.GREEN);
                        value = getUsbAmp().getImpedance(13)/1000.0;
                        if(value<=5.0){
                            impedanceCheckPanel.getImpedanceGroupDChannel13().setBackground(Color.GREEN);
                        }else if(value>5.0&&value<=10.0){
                            impedanceCheckPanel.getImpedanceGroupDChannel13().setBackground(Color.YELLOW);
                        }else if(value>10.0){
                            impedanceCheckPanel.getImpedanceGroupDChannel13().setBackground(Color.RED);
                        }
                        
                        
                        impedanceCheckPanel.setImpedanceGroupDChannel13(value);
                        
                        if(!checking){
                            break;
                        }
                        
                        impedanceCheckPanel.getChannelNumberLabel(12).setForeground(Color.BLACK);
                        impedanceCheckPanel.getChannelNumberLabel(13).setForeground(Color.GREEN);
                        
                        value = getUsbAmp().getImpedance(14)/1000.0;
                        if(value<=5.0){
                            impedanceCheckPanel.getImpedanceGroupDChannel14().setBackground(Color.GREEN);
                        }else if(value>5.0&&value<=10.0){
                            impedanceCheckPanel.getImpedanceGroupDChannel14().setBackground(Color.YELLOW);
                        }else if(value>10.0){
                            impedanceCheckPanel.getImpedanceGroupDChannel14().setBackground(Color.RED);
                        }
                        
                        impedanceCheckPanel.setImpedanceGroupDChannel14(value);
                        
                        if(!checking){
                            break;
                        }
                        
                        impedanceCheckPanel.getChannelNumberLabel(13).setForeground(Color.BLACK);
                        impedanceCheckPanel.getChannelNumberLabel(14).setForeground(Color.GREEN);
                        value = getUsbAmp().getImpedance(15)/1000.0;
                        if(value<=5.0){
                            impedanceCheckPanel.getImpedanceGroupDChannel15().setBackground(Color.GREEN);
                        }else if(value>5.0&&value<=10.0){
                            impedanceCheckPanel.getImpedanceGroupDChannel15().setBackground(Color.YELLOW);
                        }else if(value>10.0){
                            impedanceCheckPanel.getImpedanceGroupDChannel15().setBackground(Color.RED);
                        }
                        
                        impedanceCheckPanel.setImpedanceGroupDChannel15(value);
                        
                        if(!checking){
                            break;
                        }
                        
                        impedanceCheckPanel.getChannelNumberLabel(14).setForeground(Color.BLACK);
                        impedanceCheckPanel.getChannelNumberLabel(15).setForeground(Color.GREEN);
                        value = getUsbAmp().getImpedance(16)/1000.0;
                        if(value<=5.0){
                            impedanceCheckPanel.getImpedanceGroupDChannel16().setBackground(Color.GREEN);
                        }else if(value>5.0&&value<=10.0){
                            impedanceCheckPanel.getImpedanceGroupDChannel16().setBackground(Color.YELLOW);
                        }else if(value>10.0){
                            impedanceCheckPanel.getImpedanceGroupDChannel16().setBackground(Color.RED);
                        }
                        
                        impedanceCheckPanel.setImpedanceGroupDChannel16(value);
                        
                        if(!checking){
                            break;
                        }
                        
                        impedanceCheckPanel.getChannelNumberLabel(15).setForeground(Color.BLACK);
                        impedanceCheckPanel.getChannelNumberLabel(16).setForeground(Color.GREEN);
                        value = getUsbAmp().getImpedance(17)/1000.0;
                        if(value<=5.0){
                            impedanceCheckPanel.getReferenceGroupA().setBackground(Color.GREEN);
                        }else if(value>5.0&&value<=10.0){
                            impedanceCheckPanel.getReferenceGroupA().setBackground(Color.YELLOW);
                        }else if(value>10.0){
                            impedanceCheckPanel.getReferenceGroupA().setBackground(Color.RED);
                        }
                        
                        
                        impedanceCheckPanel.setReferenceGroupA(value);
                        
                        if(!checking){
                            break;
                        }
                        
                        impedanceCheckPanel.getChannelNumberLabel(16).setForeground(Color.BLACK);
                        impedanceCheckPanel.getChannelNumberLabel(17).setForeground(Color.GREEN);
                        value = getUsbAmp().getImpedance(18)/1000.0;
                        if(value<=5.0){
                            impedanceCheckPanel.getReferenceGroupB().setBackground(Color.GREEN);
                        }else if(value>5.0&&value<=10.0){
                            impedanceCheckPanel.getReferenceGroupB().setBackground(Color.YELLOW);
                        }else if(value>10.0){
                            impedanceCheckPanel.getReferenceGroupB().setBackground(Color.RED);
                        }
                        
                        impedanceCheckPanel.setReferenceGroupB(value);
                        
                        if(!checking){
                            break;
                        }
                        
                        impedanceCheckPanel.getChannelNumberLabel(17).setForeground(Color.BLACK);
                        impedanceCheckPanel.getChannelNumberLabel(18).setForeground(Color.GREEN);
                        value = getUsbAmp().getImpedance(19)/1000.0;
                        if(value<=5.0){
                            impedanceCheckPanel.getReferenceGroupC().setBackground(Color.GREEN);
                        }else if(value>5.0&&value<=10.0){
                            impedanceCheckPanel.getReferenceGroupC().setBackground(Color.YELLOW);
                        }else if(value>10.0){
                            impedanceCheckPanel.getReferenceGroupC().setBackground(Color.RED);
                        }
                        
                        impedanceCheckPanel.setReferenceGroupC(value);
                        
                        if(!checking){
                            break;
                        }
                        
                        
                        impedanceCheckPanel.getChannelNumberLabel(18).setForeground(Color.BLACK);
                        impedanceCheckPanel.getChannelNumberLabel(19).setForeground(Color.GREEN);
                        value = getUsbAmp().getImpedance(20)/1000.0;
                        if(value<=5.0){
                            impedanceCheckPanel.getReferenceGroupD().setBackground(Color.GREEN);
                        }else if(value>5.0&&value<=10.0){
                            impedanceCheckPanel.getReferenceGroupD().setBackground(Color.YELLOW);
                        }else if(value>10.0){
                            impedanceCheckPanel.getReferenceGroupD().setBackground(Color.RED);
                        }
                        
                        impedanceCheckPanel.setReferenceGroupD(value);
                        
                    }catch(Exception e){System.out.println(e);};
                }
            }
            
            public void startThread(){
                checking = true;
                start();
            }
            
            public void stopThread(){
                checking = false;
            }
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
            ImpedanceWizard aImpedanceWizard = new ImpedanceWizard();
            USBAmp amp = new USBAmp();
            amp.setSerialNumber(args[1]);
            
            amp.setAmp(new NativeAmplifierA());
            
            try{
                amp.open();
                
                try{
                    amp.setSampleRate(256);
                }catch(Exception e){System.out.println(e);};
                
                aImpedanceWizard.setUsbAmp(amp);
            }catch(Exception ex){System.out.println(ex);};
            aImpedanceWizard.setVisible(true);
        }
    }
}
