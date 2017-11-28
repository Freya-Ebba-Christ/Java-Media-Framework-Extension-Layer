/*
 * CalibrationPanel.java
 *
 * Created on 16. Juli 2007, 17:37
 */

package media.protocol.gtec;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import utilities.math.Rounding;

/**
 *
 * @author  Administrator
 */

public class CalibrationPanel extends JPanel {
    public static String SAVE = "SAVE";
    public static String START = "START";
    private final int NUMCHANNELS = 16;
    private JTextField[] offsetChannelTextFields = new JTextField[NUMCHANNELS];
    private JTextField[] scalingChannelTextFields = new JTextField[NUMCHANNELS];
    
    /** Creates new form CalibrationPanel */
    public CalibrationPanel() {
        initComponents();
        
        //now put the textfields into an array for easy access...
        offsetChannelTextFields[0] = getOffsetChannel01();
        offsetChannelTextFields[1] = getOffsetChannel02();
        offsetChannelTextFields[2] = getOffsetChannel03();
        offsetChannelTextFields[3] = getOffsetChannel04();
        offsetChannelTextFields[4] = getOffsetChannel05();
        offsetChannelTextFields[5] = getOffsetChannel06();
        offsetChannelTextFields[6] = getOffsetChannel07();
        offsetChannelTextFields[7] = getOffsetChannel08();
        offsetChannelTextFields[8] = getOffsetChannel09();
        offsetChannelTextFields[9] = getOffsetChannel10();
        offsetChannelTextFields[10] = getOffsetChannel11();
        offsetChannelTextFields[11] = getOffsetChannel12();
        offsetChannelTextFields[12] = getOffsetChannel13();
        offsetChannelTextFields[13] = getOffsetChannel14();
        offsetChannelTextFields[14] = getOffsetChannel15();
        offsetChannelTextFields[15] = getOffsetChannel16();
        
        //now put the textfields into an array for easy access...
        scalingChannelTextFields[0] = getScalingChannel01();
        scalingChannelTextFields[1] = getScalingChannel02();
        scalingChannelTextFields[2] = getScalingChannel03();
        scalingChannelTextFields[3] = getScalingChannel04();
        scalingChannelTextFields[4] = getScalingChannel05();
        scalingChannelTextFields[5] = getScalingChannel06();
        scalingChannelTextFields[6] = getScalingChannel07();
        scalingChannelTextFields[7] = getScalingChannel08();
        scalingChannelTextFields[8] = getScalingChannel09();
        scalingChannelTextFields[9] = getScalingChannel10();
        scalingChannelTextFields[10] = getScalingChannel11();
        scalingChannelTextFields[11] = getScalingChannel12();
        scalingChannelTextFields[12] = getScalingChannel13();
        scalingChannelTextFields[13] = getScalingChannel14();
        scalingChannelTextFields[14] = getScalingChannel15();
        scalingChannelTextFields[15] = getScalingChannel16();
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        CalibrationPanel aCalibrationPanel = new CalibrationPanel();
        frame.add(aCalibrationPanel,BorderLayout.CENTER);
        frame.setSize(400,550);
        frame.setVisible(true);
    }
    
    public int getNumChannels(){
        return NUMCHANNELS;
    }
    
    public double getOffset(int channel){
        return Double.parseDouble(offsetChannelTextFields[channel].getText());
    }
    
    public double getScaling(int channel){
        return Double.parseDouble(scalingChannelTextFields[channel].getText());
    }
    
    public void setOffset(int channel, double value){
        offsetChannelTextFields[channel].setText(Double.toString(Rounding.round(value,3)));
    }
    
    public void setScaling(int channel, double value){
        scalingChannelTextFields[channel].setText(Double.toString(Rounding.round(value,3)));
    }
    
    public JTextField getOffsetChannelTextField(int index){
        return offsetChannelTextFields[index];
    }
    
    public JTextField getScalingChannelTextField(int index){
        return scalingChannelTextFields[index];
    }
    
    public JTextField getOffsetChannel01() {
        return offsetChannel01;
    }
    
    public JTextField getOffsetChannel02() {
        return offsetChannel02;
    }
    
    public JTextField getOffsetChannel03() {
        return offsetChannel03;
    }
    
    public JTextField getOffsetChannel04() {
        return offsetChannel04;
    }
    
    public JTextField getOffsetChannel05() {
        return offsetChannel05;
    }
    
    public JTextField getOffsetChannel06() {
        return offsetChannel06;
    }
    
    public JTextField getOffsetChannel07() {
        return offsetChannel07;
    }
    
    public JTextField getOffsetChannel08() {
        return offsetChannel08;
    }
    
    public JTextField getOffsetChannel09() {
        return offsetChannel09;
    }
    
    public JTextField getOffsetChannel10() {
        return offsetChannel10;
    }
    
    public JTextField getOffsetChannel11() {
        return offsetChannel11;
    }
    
    public JTextField getOffsetChannel12() {
        return offsetChannel12;
    }
    
    public JTextField getOffsetChannel13() {
        return offsetChannel13;
    }
    
    public JTextField getOffsetChannel14() {
        return offsetChannel14;
    }
    
    public JTextField getOffsetChannel15() {
        return offsetChannel15;
    }
    
    public JTextField getOffsetChannel16() {
        return offsetChannel16;
    }
    
    public JButton getSaveButton() {
        return saveButton;
    }
    
    public JTextField getScalingChannel01() {
        return scalingChannel01;
    }
    
    public JTextField getScalingChannel02() {
        return scalingChannel02;
    }
    
    public JTextField getScalingChannel03() {
        return scalingChannel03;
    }
    
    public JTextField getScalingChannel04() {
        return scalingChannel04;
    }
    
    public JTextField getScalingChannel05() {
        return scalingChannel05;
    }
    
    public JTextField getScalingChannel06() {
        return scalingChannel06;
    }
    
    public JTextField getScalingChannel07() {
        return scalingChannel07;
    }
    
    public JTextField getScalingChannel08() {
        return scalingChannel08;
    }
    
    public JTextField getScalingChannel09() {
        return scalingChannel09;
    }
    
    public JTextField getScalingChannel10() {
        return scalingChannel10;
    }
    
    public JTextField getScalingChannel11() {
        return scalingChannel11;
    }
    
    public JTextField getScalingChannel12() {
        return scalingChannel12;
    }
    
    public JTextField getScalingChannel13() {
        return scalingChannel13;
    }
    
    public JTextField getScalingChannel14() {
        return scalingChannel14;
    }
    
    public JTextField getScalingChannel15() {
        return scalingChannel15;
    }
    
    public JTextField getScalingChannel16() {
        return scalingChannel16;
    }
    
    public JLabel getSerialLabel() {
        return serialLabel;
    }
    
    public JButton getStartButton() {
        return startButton;
    }
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        imageLabel2 = new javax.swing.JLabel();
        imageLabel1 = new javax.swing.JLabel();
        serialLabel = new javax.swing.JLabel();
        channelLabel01 = new javax.swing.JLabel();
        channelLabel02 = new javax.swing.JLabel();
        channelLabel03 = new javax.swing.JLabel();
        channelLabel04 = new javax.swing.JLabel();
        channelLabel05 = new javax.swing.JLabel();
        channelLabel06 = new javax.swing.JLabel();
        channelLabel07 = new javax.swing.JLabel();
        channelLabel08 = new javax.swing.JLabel();
        channelLabel09 = new javax.swing.JLabel();
        channelLabel10 = new javax.swing.JLabel();
        channelLabel11 = new javax.swing.JLabel();
        channelLabel12 = new javax.swing.JLabel();
        channelLabel13 = new javax.swing.JLabel();
        channelLabel14 = new javax.swing.JLabel();
        channelLabel15 = new javax.swing.JLabel();
        channelLabel16 = new javax.swing.JLabel();
        offsetChannel01 = new javax.swing.JTextField();
        offsetChannel02 = new javax.swing.JTextField();
        offsetChannel03 = new javax.swing.JTextField();
        offsetChannel04 = new javax.swing.JTextField();
        offsetChannel05 = new javax.swing.JTextField();
        offsetChannel06 = new javax.swing.JTextField();
        offsetChannel07 = new javax.swing.JTextField();
        offsetChannel08 = new javax.swing.JTextField();
        offsetChannel09 = new javax.swing.JTextField();
        offsetChannel10 = new javax.swing.JTextField();
        offsetChannel11 = new javax.swing.JTextField();
        offsetChannel12 = new javax.swing.JTextField();
        offsetChannel13 = new javax.swing.JTextField();
        offsetChannel14 = new javax.swing.JTextField();
        offsetChannel15 = new javax.swing.JTextField();
        offsetChannel16 = new javax.swing.JTextField();
        scalingChannel01 = new javax.swing.JTextField();
        scalingChannel02 = new javax.swing.JTextField();
        scalingChannel03 = new javax.swing.JTextField();
        scalingChannel04 = new javax.swing.JTextField();
        scalingChannel05 = new javax.swing.JTextField();
        scalingChannel06 = new javax.swing.JTextField();
        scalingChannel07 = new javax.swing.JTextField();
        scalingChannel08 = new javax.swing.JTextField();
        scalingChannel09 = new javax.swing.JTextField();
        scalingChannel10 = new javax.swing.JTextField();
        scalingChannel11 = new javax.swing.JTextField();
        scalingChannel12 = new javax.swing.JTextField();
        scalingChannel13 = new javax.swing.JTextField();
        scalingChannel14 = new javax.swing.JTextField();
        scalingChannel15 = new javax.swing.JTextField();
        scalingChannel16 = new javax.swing.JTextField();
        offsetLabel = new javax.swing.JLabel();
        scalingLabel = new javax.swing.JLabel();
        startButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        channelLabelScaling1 = new javax.swing.JLabel();
        channelLabelScaling2 = new javax.swing.JLabel();
        channelLabelScaling3 = new javax.swing.JLabel();
        channelLabelScaling4 = new javax.swing.JLabel();
        channelLabelScaling5 = new javax.swing.JLabel();
        channelLabelScaling6 = new javax.swing.JLabel();
        channelLabelScaling7 = new javax.swing.JLabel();
        channelLabelScaling8 = new javax.swing.JLabel();
        channelLabelScaling9 = new javax.swing.JLabel();
        channelLabelScaling10 = new javax.swing.JLabel();
        channelLabelScaling11 = new javax.swing.JLabel();
        channelLabelScaling12 = new javax.swing.JLabel();
        channelLabelScaling13 = new javax.swing.JLabel();
        channelLabelScaling14 = new javax.swing.JLabel();
        channelLabelScaling15 = new javax.swing.JLabel();
        channelLabelScaling16 = new javax.swing.JLabel();

        setLayout(null);

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(500, 800));
        setMinimumSize(new java.awt.Dimension(500, 800));
    
        imageLabel2.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir")+"\\resources\\gtec\\graphics\\USBamp.png"));
        add(imageLabel2);
        imageLabel2.setBounds(230, 0, 160, 120);

        imageLabel1.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir")+"\\resources\\gtec\\graphics\\gUSBampLabel.png"));
        add(imageLabel1);
        imageLabel1.setBounds(0, 0, 250, 70);

        serialLabel.setFont(new java.awt.Font("Arial", 0, 14));
        serialLabel.setText("Serial:");
        add(serialLabel);
        serialLabel.setBounds(10, 100, 170, 17);

        channelLabel01.setText("CH01");
        add(channelLabel01);
        channelLabel01.setBounds(30, 140, 30, 14);

        channelLabel02.setText("CH02");
        add(channelLabel02);
        channelLabel02.setBounds(30, 160, 40, 14);

        channelLabel03.setText("CH03");
        add(channelLabel03);
        channelLabel03.setBounds(30, 180, 40, 14);

        channelLabel04.setText("CH04");
        add(channelLabel04);
        channelLabel04.setBounds(30, 200, 40, 14);

        channelLabel05.setText("CH05");
        add(channelLabel05);
        channelLabel05.setBounds(30, 220, 40, 14);

        channelLabel06.setText("CH06");
        add(channelLabel06);
        channelLabel06.setBounds(30, 240, 40, 14);

        channelLabel07.setText("CH07");
        add(channelLabel07);
        channelLabel07.setBounds(30, 260, 40, 14);

        channelLabel08.setText("CH08");
        add(channelLabel08);
        channelLabel08.setBounds(30, 280, 40, 14);

        channelLabel09.setText("CH09");
        add(channelLabel09);
        channelLabel09.setBounds(30, 300, 40, 14);

        channelLabel10.setText("CH10");
        add(channelLabel10);
        channelLabel10.setBounds(30, 320, 40, 14);

        channelLabel11.setText("CH11");
        add(channelLabel11);
        channelLabel11.setBounds(30, 340, 40, 14);

        channelLabel12.setText("CH12");
        add(channelLabel12);
        channelLabel12.setBounds(30, 360, 40, 14);

        channelLabel13.setText("CH13");
        add(channelLabel13);
        channelLabel13.setBounds(30, 380, 40, 14);

        channelLabel14.setText("CH14");
        add(channelLabel14);
        channelLabel14.setBounds(30, 400, 40, 14);

        channelLabel15.setText("CH15");
        add(channelLabel15);
        channelLabel15.setBounds(30, 420, 40, 14);

        channelLabel16.setText("CH16");
        add(channelLabel16);
        channelLabel16.setBounds(30, 440, 40, 14);

        offsetChannel01.setText("1");
        add(offsetChannel01);
        offsetChannel01.setBounds(70, 140, 60, 20);

        offsetChannel02.setText("1");
        add(offsetChannel02);
        offsetChannel02.setBounds(70, 160, 60, 20);

        offsetChannel03.setText("1");
        add(offsetChannel03);
        offsetChannel03.setBounds(70, 180, 60, 20);

        offsetChannel04.setText("1");
        add(offsetChannel04);
        offsetChannel04.setBounds(70, 200, 60, 20);

        offsetChannel05.setText("1");
        add(offsetChannel05);
        offsetChannel05.setBounds(70, 220, 60, 20);

        offsetChannel06.setText("1");
        add(offsetChannel06);
        offsetChannel06.setBounds(70, 240, 60, 20);

        offsetChannel07.setText("1");
        add(offsetChannel07);
        offsetChannel07.setBounds(70, 260, 60, 20);

        offsetChannel08.setText("1");
        add(offsetChannel08);
        offsetChannel08.setBounds(70, 280, 60, 20);

        offsetChannel09.setText("1");
        add(offsetChannel09);
        offsetChannel09.setBounds(70, 300, 60, 20);

        offsetChannel10.setText("1");
        add(offsetChannel10);
        offsetChannel10.setBounds(70, 320, 60, 20);

        offsetChannel11.setText("1");
        add(offsetChannel11);
        offsetChannel11.setBounds(70, 340, 60, 20);

        offsetChannel12.setText("1");
        add(offsetChannel12);
        offsetChannel12.setBounds(70, 360, 60, 20);

        offsetChannel13.setText("1");
        add(offsetChannel13);
        offsetChannel13.setBounds(70, 380, 60, 20);

        offsetChannel14.setText("1");
        add(offsetChannel14);
        offsetChannel14.setBounds(70, 400, 60, 20);

        offsetChannel15.setText("1");
        add(offsetChannel15);
        offsetChannel15.setBounds(70, 420, 60, 20);

        offsetChannel16.setText("1");
        add(offsetChannel16);
        offsetChannel16.setBounds(70, 440, 60, 20);

        scalingChannel01.setText("1");
        add(scalingChannel01);
        scalingChannel01.setBounds(300, 140, 60, 20);

        scalingChannel02.setText("1");
        add(scalingChannel02);
        scalingChannel02.setBounds(300, 160, 60, 20);

        scalingChannel03.setText("1");
        add(scalingChannel03);
        scalingChannel03.setBounds(300, 180, 60, 20);

        scalingChannel04.setText("1");
        add(scalingChannel04);
        scalingChannel04.setBounds(300, 200, 60, 20);

        scalingChannel05.setText("1");
        add(scalingChannel05);
        scalingChannel05.setBounds(300, 220, 60, 20);

        scalingChannel06.setText("1");
        add(scalingChannel06);
        scalingChannel06.setBounds(300, 240, 60, 20);

        scalingChannel07.setText("1");
        add(scalingChannel07);
        scalingChannel07.setBounds(300, 260, 60, 20);

        scalingChannel08.setText("1");
        add(scalingChannel08);
        scalingChannel08.setBounds(300, 280, 60, 20);

        scalingChannel09.setText("1");
        add(scalingChannel09);
        scalingChannel09.setBounds(300, 300, 60, 20);

        scalingChannel10.setText("1");
        add(scalingChannel10);
        scalingChannel10.setBounds(300, 320, 60, 20);

        scalingChannel11.setText("1");
        add(scalingChannel11);
        scalingChannel11.setBounds(300, 340, 60, 20);

        scalingChannel12.setText("1");
        add(scalingChannel12);
        scalingChannel12.setBounds(300, 360, 60, 20);

        scalingChannel13.setText("1");
        add(scalingChannel13);
        scalingChannel13.setBounds(300, 380, 60, 20);

        scalingChannel14.setText("1");
        add(scalingChannel14);
        scalingChannel14.setBounds(300, 400, 60, 20);

        scalingChannel15.setText("1");
        add(scalingChannel15);
        scalingChannel15.setBounds(300, 420, 60, 20);

        scalingChannel16.setText("1");
        add(scalingChannel16);
        scalingChannel16.setBounds(300, 440, 60, 20);

        offsetLabel.setText("Offset [\u00b5V]:");
        add(offsetLabel);
        offsetLabel.setBounds(70, 120, 70, 14);

        scalingLabel.setText("Scaling:");
        add(scalingLabel);
        scalingLabel.setBounds(300, 120, 60, 14);

        startButton.setText("Start");
        add(startButton);
        startButton.setBounds(120, 470, 70, 23);

        saveButton.setText("Save");
        add(saveButton);
        saveButton.setBounds(200, 470, 70, 23);

        channelLabelScaling1.setText("CH01");
        add(channelLabelScaling1);
        channelLabelScaling1.setBounds(260, 140, 40, 14);

        channelLabelScaling2.setText("CH02");
        add(channelLabelScaling2);
        channelLabelScaling2.setBounds(260, 160, 40, 14);

        channelLabelScaling3.setText("CH03");
        add(channelLabelScaling3);
        channelLabelScaling3.setBounds(260, 180, 40, 14);

        channelLabelScaling4.setText("CH04");
        add(channelLabelScaling4);
        channelLabelScaling4.setBounds(260, 200, 40, 14);

        channelLabelScaling5.setText("CH05");
        add(channelLabelScaling5);
        channelLabelScaling5.setBounds(260, 220, 40, 14);

        channelLabelScaling6.setText("CH06");
        add(channelLabelScaling6);
        channelLabelScaling6.setBounds(260, 240, 40, 14);

        channelLabelScaling7.setText("CH07");
        add(channelLabelScaling7);
        channelLabelScaling7.setBounds(260, 260, 40, 14);

        channelLabelScaling8.setText("CH08");
        add(channelLabelScaling8);
        channelLabelScaling8.setBounds(260, 280, 40, 14);

        channelLabelScaling9.setText("CH09");
        add(channelLabelScaling9);
        channelLabelScaling9.setBounds(260, 300, 40, 14);

        channelLabelScaling10.setText("CH10");
        add(channelLabelScaling10);
        channelLabelScaling10.setBounds(260, 320, 40, 14);

        channelLabelScaling11.setText("CH11");
        add(channelLabelScaling11);
        channelLabelScaling11.setBounds(260, 340, 40, 14);

        channelLabelScaling12.setText("CH12");
        add(channelLabelScaling12);
        channelLabelScaling12.setBounds(260, 360, 40, 14);

        channelLabelScaling13.setText("CH13");
        add(channelLabelScaling13);
        channelLabelScaling13.setBounds(260, 380, 40, 14);

        channelLabelScaling14.setText("CH14");
        add(channelLabelScaling14);
        channelLabelScaling14.setBounds(260, 400, 40, 14);

        channelLabelScaling15.setText("CH15");
        add(channelLabelScaling15);
        channelLabelScaling15.setBounds(260, 420, 40, 14);

        channelLabelScaling16.setText("CH16");
        add(channelLabelScaling16);
        channelLabelScaling16.setBounds(260, 440, 40, 14);

    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel channelLabel01;
    private javax.swing.JLabel channelLabel02;
    private javax.swing.JLabel channelLabel03;
    private javax.swing.JLabel channelLabel04;
    private javax.swing.JLabel channelLabel05;
    private javax.swing.JLabel channelLabel06;
    private javax.swing.JLabel channelLabel07;
    private javax.swing.JLabel channelLabel08;
    private javax.swing.JLabel channelLabel09;
    private javax.swing.JLabel channelLabel10;
    private javax.swing.JLabel channelLabel11;
    private javax.swing.JLabel channelLabel12;
    private javax.swing.JLabel channelLabel13;
    private javax.swing.JLabel channelLabel14;
    private javax.swing.JLabel channelLabel15;
    private javax.swing.JLabel channelLabel16;
    private javax.swing.JLabel channelLabelScaling1;
    private javax.swing.JLabel channelLabelScaling10;
    private javax.swing.JLabel channelLabelScaling11;
    private javax.swing.JLabel channelLabelScaling12;
    private javax.swing.JLabel channelLabelScaling13;
    private javax.swing.JLabel channelLabelScaling14;
    private javax.swing.JLabel channelLabelScaling15;
    private javax.swing.JLabel channelLabelScaling16;
    private javax.swing.JLabel channelLabelScaling2;
    private javax.swing.JLabel channelLabelScaling3;
    private javax.swing.JLabel channelLabelScaling4;
    private javax.swing.JLabel channelLabelScaling5;
    private javax.swing.JLabel channelLabelScaling6;
    private javax.swing.JLabel channelLabelScaling7;
    private javax.swing.JLabel channelLabelScaling8;
    private javax.swing.JLabel channelLabelScaling9;
    private javax.swing.JLabel imageLabel1;
    private javax.swing.JLabel imageLabel2;
    private javax.swing.JTextField offsetChannel01;
    private javax.swing.JTextField offsetChannel02;
    private javax.swing.JTextField offsetChannel03;
    private javax.swing.JTextField offsetChannel04;
    private javax.swing.JTextField offsetChannel05;
    private javax.swing.JTextField offsetChannel06;
    private javax.swing.JTextField offsetChannel07;
    private javax.swing.JTextField offsetChannel08;
    private javax.swing.JTextField offsetChannel09;
    private javax.swing.JTextField offsetChannel10;
    private javax.swing.JTextField offsetChannel11;
    private javax.swing.JTextField offsetChannel12;
    private javax.swing.JTextField offsetChannel13;
    private javax.swing.JTextField offsetChannel14;
    private javax.swing.JTextField offsetChannel15;
    private javax.swing.JTextField offsetChannel16;
    private javax.swing.JLabel offsetLabel;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField scalingChannel01;
    private javax.swing.JTextField scalingChannel02;
    private javax.swing.JTextField scalingChannel03;
    private javax.swing.JTextField scalingChannel04;
    private javax.swing.JTextField scalingChannel05;
    private javax.swing.JTextField scalingChannel06;
    private javax.swing.JTextField scalingChannel07;
    private javax.swing.JTextField scalingChannel08;
    private javax.swing.JTextField scalingChannel09;
    private javax.swing.JTextField scalingChannel10;
    private javax.swing.JTextField scalingChannel11;
    private javax.swing.JTextField scalingChannel12;
    private javax.swing.JTextField scalingChannel13;
    private javax.swing.JTextField scalingChannel14;
    private javax.swing.JTextField scalingChannel15;
    private javax.swing.JTextField scalingChannel16;
    private javax.swing.JLabel scalingLabel;
    private javax.swing.JLabel serialLabel;
    private javax.swing.JButton startButton;
    // End of variables declaration//GEN-END:variables
    
}
