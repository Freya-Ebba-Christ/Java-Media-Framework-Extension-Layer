/*
 * ImpedanceCheckPanel.java
 *
 * Created on 16. Juli 2007, 17:14
 */

package media.protocol.gtec;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import utilities.math.Rounding;

/**
 *
 * @author  Administrator
 */
public class ImpedanceCheckPanel extends javax.swing.JPanel {
    
    private final int NUMCHANNELS = 16;
    private JLabel[] labels = new JLabel[NUMCHANNELS+4];
    private JLabel[] channelNumberLabels = new JLabel[NUMCHANNELS+4];
    public static String START = "START";
    
    
    /** Creates new form ImpedanceCheckPanel */
    public ImpedanceCheckPanel() {
        initComponents();
        
        labels[0] = labelGroupAChannel1;
        labels[1] = labelGroupAChannel2;
        labels[2] = labelGroupAChannel3;
        labels[3] = labelGroupAChannel4;
        
        labels[4] = labelGroupBChannel1;
        labels[5] = labelGroupBChannel2;
        labels[6] = labelGroupBChannel3;
        labels[7] = labelGroupBChannel4;
        
        labels[8] = labelGroupCChannel1;
        labels[9] = labelGroupCChannel2;
        labels[10] = labelGroupCChannel3;
        labels[11] = labelGroupCChannel4;
        
        labels[12] = labelGroupDChannel1;
        labels[13] = labelGroupDChannel2;
        labels[14] = labelGroupDChannel3;
        labels[15] = labelGroupDChannel4;
        
        labels[16] = labelGroupReferenceA;
        labels[17] = labelGroupReferenceB;
        labels[18] = labelGroupReferenceC;
        labels[19] = labelGroupReferenceD;
        
        channelNumberLabels[0] = labelIndexA1;
        channelNumberLabels[1] = labelIndexA2;
        channelNumberLabels[2] = labelIndexA3;
        channelNumberLabels[3] = labelIndexA4;
        
        channelNumberLabels[4] = labelIndexB1;
        channelNumberLabels[5] = labelIndexB2;
        channelNumberLabels[6] = labelIndexB3;
        channelNumberLabels[7] = labelIndexB4;
        
        channelNumberLabels[8] = labelIndexC1;
        channelNumberLabels[9] = labelIndexC2;
        channelNumberLabels[10] = labelIndexC3;
        channelNumberLabels[11] = labelIndexC4;
        
        channelNumberLabels[12] = labelIndexD1;
        channelNumberLabels[13] = labelIndexD2;
        channelNumberLabels[14] = labelIndexD3;
        channelNumberLabels[15] = labelIndexD4;
        
        channelNumberLabels[16] = labelIndexAR;
        channelNumberLabels[17] = labelIndexBR;
        channelNumberLabels[18] = labelIndexCR;
        channelNumberLabels[19] = labelIndexDR;
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ImpedanceCheckPanel aImpedanceCheckPanel = new ImpedanceCheckPanel();
        frame.add(aImpedanceCheckPanel,BorderLayout.CENTER);
        frame.setSize(600,390);
        frame.setVisible(true);
    }
    
    public int getNumChannels(){
        return NUMCHANNELS;
    }
    
    private JLabel getLabel(int index){
        return labels[index];
    }
    
    public JLabel getChannelNumberLabel(int index){
        return channelNumberLabels[index];
    }
    
    public JLabel getImpedanceGroupAChannel1(){
        return getLabel(0);
    }
    
    public JLabel getImpedanceGroupAChannel2(){
        return getLabel(1);
    }
    
    public JLabel getImpedanceGroupAChannel3(){
        return getLabel(2);
    }
    
    public JLabel getImpedanceGroupAChannel4(){
        return getLabel(3);
    }
    
    public JLabel getImpedanceGroupBChannel5(){
        return getLabel(4);
    }
    
    public JLabel getImpedanceGroupBChannel6(){
        return getLabel(5);
    }
    
    public JLabel getImpedanceGroupBChannel7(){
        return getLabel(6);
    }
    
    public JLabel getImpedanceGroupBChannel8(){
        return getLabel(7);
    }
    
    public JLabel getImpedanceGroupCChannel9(){
        return getLabel(8);
    }
    
    public JLabel getImpedanceGroupCChannel10(){
        return getLabel(9);
    }
    
    public JLabel getImpedanceGroupCChannel11(){
        return getLabel(10);
    }
    
    public JLabel getImpedanceGroupCChannel12(){
        return getLabel(11);
    }
    
    
    public JLabel getImpedanceGroupDChannel13(){
        return getLabel(12);
    }
    
    public JLabel getImpedanceGroupDChannel14(){
        return getLabel(13);
    }
    
    public JLabel getImpedanceGroupDChannel15(){
        return getLabel(14);
    }
    
    public JLabel getImpedanceGroupDChannel16(){
        return getLabel(15);
    }
    
    public JLabel getReferenceGroupA(){
        return getLabel(16);
    }
    
    public JLabel getReferenceGroupB(){
        return getLabel(17);
    }
    
    public JLabel getReferenceGroupC(){
        return getLabel(18);
    }
    
    public JLabel getReferenceGroupD(){
        return getLabel(19);
    }
    
    public void setImpedanceGroupAChannel1(double value){
        getLabel(0).setText(String.valueOf((int)Rounding.round(value,0)));
    }
    
    public void setImpedanceGroupAChannel2(double value){
        getLabel(1).setText(String.valueOf((int)Rounding.round(value,0)));
    }
    
    public void setImpedanceGroupAChannel3(double value){
        getLabel(2).setText(String.valueOf((int)Rounding.round(value,0)));
    }
    
    public void setImpedanceGroupAChannel4(double value){
        getLabel(3).setText(String.valueOf((int)Rounding.round(value,0)));
    }
    
    public void setImpedanceGroupBChannel5(double value){
        getLabel(4).setText(String.valueOf((int)Rounding.round(value,0)));
    }
    
    public void setImpedanceGroupBChannel6(double value){
        getLabel(5).setText(String.valueOf((int)Rounding.round(value,0)));
    }
    
    public void setImpedanceGroupBChannel7(double value){
        getLabel(6).setText(String.valueOf((int)Rounding.round(value,0)));
    }
    
    public void setImpedanceGroupBChannel8(double value){
        getLabel(7).setText(String.valueOf((int)Rounding.round(value,0)));
    }
    
    public void setImpedanceGroupCChannel9(double value){
        getLabel(8).setText(String.valueOf((int)Rounding.round(value,0)));
    }
    
    public void setImpedanceGroupCChannel10(double value){
        getLabel(9).setText(String.valueOf((int)Rounding.round(value,0)));
    }
    
    public void setImpedanceGroupCChannel11(double value){
        getLabel(10).setText(String.valueOf((int)Rounding.round(value,0)));
    }
    
    public void setImpedanceGroupCChannel12(double value){
        getLabel(11).setText(String.valueOf((int)Rounding.round(value,0)));
    }
    
    public void setImpedanceGroupDChannel13(double value){
        getLabel(12).setText(String.valueOf((int)Rounding.round(value,0)));
    }
    
    public void setImpedanceGroupDChannel14(double value){
        getLabel(13).setText(String.valueOf((int)Rounding.round(value,0)));
    }
    
    public void setImpedanceGroupDChannel15(double value){
        getLabel(14).setText(String.valueOf((int)Rounding.round(value,0)));
    }
    
    public void setImpedanceGroupDChannel16(double value){
        getLabel(15).setText(String.valueOf((int)Rounding.round(value,0)));
    }
    
    public void setReferenceGroupA(double value){
        getLabel(16).setText(String.valueOf((int)Rounding.round(value,0)));
    }
    
    public void setReferenceGroupB(double value){
        getLabel(17).setText(String.valueOf((int)Rounding.round(value,0)));
    }
    
    public void setReferenceGroupC(double value){
        getLabel(18).setText(String.valueOf((int)Rounding.round(value,0)));
    }
    
    public void setReferenceGroupD(double value){
        getLabel(19).setText(String.valueOf((int)Rounding.round(value,0)));
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
        imageLabel1 = new javax.swing.JLabel();
        imageLabel2 = new javax.swing.JLabel();
        serialLabel = new javax.swing.JLabel();
        groupPanelA = new javax.swing.JPanel();
        labelGroupAChannel1 = new javax.swing.JLabel();
        labelGroupAChannel3 = new javax.swing.JLabel();
        labelGroupAChannel2 = new javax.swing.JLabel();
        labelGroupAChannel4 = new javax.swing.JLabel();
        labelGroupReferenceA = new javax.swing.JLabel();
        labelIndexA1 = new javax.swing.JLabel();
        labelIndexA3 = new javax.swing.JLabel();
        labelIndexA2 = new javax.swing.JLabel();
        labelIndexA4 = new javax.swing.JLabel();
        labelIndexAR = new javax.swing.JLabel();
        groupLabelA = new javax.swing.JLabel();
        startButton = new javax.swing.JButton();
        groupPanelB = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        labelGroupBChannel4 = new javax.swing.JLabel();
        labelGroupBChannel3 = new javax.swing.JLabel();
        labelGroupBChannel1 = new javax.swing.JLabel();
        labelGroupBChannel2 = new javax.swing.JLabel();
        labelGroupReferenceB = new javax.swing.JLabel();
        labelIndexBR = new javax.swing.JLabel();
        labelIndexB2 = new javax.swing.JLabel();
        labelIndexB4 = new javax.swing.JLabel();
        labelIndexB3 = new javax.swing.JLabel();
        labelIndexB1 = new javax.swing.JLabel();
        groupLabelB = new javax.swing.JLabel();
        groupPanelC = new javax.swing.JPanel();
        labelGroupCChannel4 = new javax.swing.JLabel();
        labelGroupCChannel3 = new javax.swing.JLabel();
        labelIndexC2 = new javax.swing.JLabel();
        labelGroupCChannel1 = new javax.swing.JLabel();
        labelGroupCChannel2 = new javax.swing.JLabel();
        labelGroupReferenceC = new javax.swing.JLabel();
        labelIndexCR = new javax.swing.JLabel();
        labelIndexC4 = new javax.swing.JLabel();
        labelIndexC3 = new javax.swing.JLabel();
        labelIndexC1 = new javax.swing.JLabel();
        groupLabelC = new javax.swing.JLabel();
        groupPanelD = new javax.swing.JPanel();
        labelGroupDChannel4 = new javax.swing.JLabel();
        labelGroupDChannel3 = new javax.swing.JLabel();
        labelGroupDChannel1 = new javax.swing.JLabel();
        labelGroupDChannel2 = new javax.swing.JLabel();
        labelGroupReferenceD = new javax.swing.JLabel();
        labelIndexDR = new javax.swing.JLabel();
        labelIndexD4 = new javax.swing.JLabel();
        labelIndexD3 = new javax.swing.JLabel();
        labelIndexD1 = new javax.swing.JLabel();
        labelIndexD2 = new javax.swing.JLabel();
        groupLabelD = new javax.swing.JLabel();

        setLayout(null);

        setBackground(new java.awt.Color(255, 255, 255));
        imageLabel1.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir")+"\\resources\\gtec\\graphics\\gUSBampLabel.png"));
        add(imageLabel1);
        imageLabel1.setBounds(70, 20, 240, 64);

        imageLabel2.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir")+"\\resources\\gtec\\graphics\\USBamp.png"));
        add(imageLabel2);
        imageLabel2.setBounds(320, 0, 160, 120);

        serialLabel.setFont(new java.awt.Font("Arial", 0, 14));
        serialLabel.setText("Serial:");
        add(serialLabel);
        serialLabel.setBounds(10, 90, 160, 20);

        groupPanelA.setLayout(null);

        groupPanelA.setBackground(new java.awt.Color(255, 255, 255));
        groupPanelA.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        labelGroupAChannel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGroupAChannel1.setText("12");
        labelGroupAChannel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), java.awt.Color.lightGray, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        labelGroupAChannel1.setOpaque(true);
        groupPanelA.add(labelGroupAChannel1);
        labelGroupAChannel1.setBounds(20, 10, 30, 30);

        labelGroupAChannel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGroupAChannel3.setText("12");
        labelGroupAChannel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), java.awt.Color.lightGray, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        labelGroupAChannel3.setOpaque(true);
        groupPanelA.add(labelGroupAChannel3);
        labelGroupAChannel3.setBounds(70, 10, 30, 30);

        labelGroupAChannel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGroupAChannel2.setText("12");
        labelGroupAChannel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), java.awt.Color.lightGray, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        labelGroupAChannel2.setOpaque(true);
        groupPanelA.add(labelGroupAChannel2);
        labelGroupAChannel2.setBounds(20, 50, 30, 30);

        labelGroupAChannel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGroupAChannel4.setText("12");
        labelGroupAChannel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), java.awt.Color.lightGray, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        labelGroupAChannel4.setOpaque(true);
        groupPanelA.add(labelGroupAChannel4);
        labelGroupAChannel4.setBounds(70, 50, 30, 30);

        labelGroupReferenceA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGroupReferenceA.setText("12");
        labelGroupReferenceA.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), java.awt.Color.lightGray, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        labelGroupReferenceA.setOpaque(true);
        groupPanelA.add(labelGroupReferenceA);
        labelGroupReferenceA.setBounds(70, 90, 30, 30);

        labelIndexA1.setText("1");
        groupPanelA.add(labelIndexA1);
        labelIndexA1.setBounds(10, 20, 10, 30);

        labelIndexA3.setText("3");
        groupPanelA.add(labelIndexA3);
        labelIndexA3.setBounds(60, 20, 10, 30);

        labelIndexA2.setText("2");
        groupPanelA.add(labelIndexA2);
        labelIndexA2.setBounds(10, 60, 10, 30);

        labelIndexA4.setText("4");
        groupPanelA.add(labelIndexA4);
        labelIndexA4.setBounds(60, 60, 10, 30);

        labelIndexAR.setText("R");
        groupPanelA.add(labelIndexAR);
        labelIndexAR.setBounds(60, 100, 10, 30);

        add(groupPanelA);
        groupPanelA.setBounds(20, 160, 120, 130);

        groupLabelA.setFont(new java.awt.Font("Arial", 0, 14));
        groupLabelA.setText("Group A:");
        add(groupLabelA);
        groupLabelA.setBounds(20, 140, 70, 17);

        startButton.setText("Start");
        add(startButton);
        startButton.setBounds(260, 310, 70, 23);

        groupPanelB.setLayout(null);

        groupPanelB.setBackground(new java.awt.Color(255, 255, 255));
        groupPanelB.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel5.setFont(new java.awt.Font("Arial", 0, 14));
        jLabel5.setText("Group A");
        groupPanelB.add(jLabel5);
        jLabel5.setBounds(20, 150, 60, 17);

        labelGroupBChannel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGroupBChannel4.setText("12");
        labelGroupBChannel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), java.awt.Color.lightGray, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        labelGroupBChannel4.setOpaque(true);
        groupPanelB.add(labelGroupBChannel4);
        labelGroupBChannel4.setBounds(70, 50, 30, 30);

        labelGroupBChannel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGroupBChannel3.setText("12");
        labelGroupBChannel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), java.awt.Color.lightGray, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        labelGroupBChannel3.setOpaque(true);
        groupPanelB.add(labelGroupBChannel3);
        labelGroupBChannel3.setBounds(70, 10, 30, 30);

        labelGroupBChannel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGroupBChannel1.setText("12");
        labelGroupBChannel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), java.awt.Color.lightGray, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        labelGroupBChannel1.setOpaque(true);
        groupPanelB.add(labelGroupBChannel1);
        labelGroupBChannel1.setBounds(20, 10, 30, 30);

        labelGroupBChannel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGroupBChannel2.setText("12");
        labelGroupBChannel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), java.awt.Color.lightGray, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        labelGroupBChannel2.setOpaque(true);
        groupPanelB.add(labelGroupBChannel2);
        labelGroupBChannel2.setBounds(20, 50, 30, 30);

        labelGroupReferenceB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGroupReferenceB.setText("12");
        labelGroupReferenceB.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), java.awt.Color.lightGray, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        labelGroupReferenceB.setOpaque(true);
        groupPanelB.add(labelGroupReferenceB);
        labelGroupReferenceB.setBounds(70, 90, 30, 30);

        labelIndexBR.setText("R");
        groupPanelB.add(labelIndexBR);
        labelIndexBR.setBounds(60, 100, 10, 30);

        labelIndexB2.setText("6");
        groupPanelB.add(labelIndexB2);
        labelIndexB2.setBounds(10, 60, 10, 30);

        labelIndexB4.setText("8");
        groupPanelB.add(labelIndexB4);
        labelIndexB4.setBounds(60, 60, 10, 30);

        labelIndexB3.setText("7");
        groupPanelB.add(labelIndexB3);
        labelIndexB3.setBounds(60, 20, 10, 30);

        labelIndexB1.setText("5");
        groupPanelB.add(labelIndexB1);
        labelIndexB1.setBounds(10, 20, 10, 30);

        add(groupPanelB);
        groupPanelB.setBounds(170, 160, 120, 130);

        groupLabelB.setFont(new java.awt.Font("Arial", 0, 14));
        groupLabelB.setText("Group B:");
        add(groupLabelB);
        groupLabelB.setBounds(170, 140, 70, 17);

        groupPanelC.setLayout(null);

        groupPanelC.setBackground(new java.awt.Color(255, 255, 255));
        groupPanelC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        labelGroupCChannel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGroupCChannel4.setText("12");
        labelGroupCChannel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), java.awt.Color.lightGray, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        labelGroupCChannel4.setOpaque(true);
        groupPanelC.add(labelGroupCChannel4);
        labelGroupCChannel4.setBounds(70, 50, 30, 30);

        labelGroupCChannel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGroupCChannel3.setText("12");
        labelGroupCChannel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), java.awt.Color.lightGray, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        labelGroupCChannel3.setOpaque(true);
        groupPanelC.add(labelGroupCChannel3);
        labelGroupCChannel3.setBounds(70, 10, 30, 30);

        labelIndexC2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelIndexC2.setText("10");
        groupPanelC.add(labelIndexC2);
        labelIndexC2.setBounds(5, 60, 20, 30);

        labelGroupCChannel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGroupCChannel1.setText("12");
        labelGroupCChannel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), java.awt.Color.lightGray, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        labelGroupCChannel1.setOpaque(true);
        groupPanelC.add(labelGroupCChannel1);
        labelGroupCChannel1.setBounds(20, 10, 30, 30);

        labelGroupCChannel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGroupCChannel2.setText("12");
        labelGroupCChannel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), java.awt.Color.lightGray, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        labelGroupCChannel2.setOpaque(true);
        groupPanelC.add(labelGroupCChannel2);
        labelGroupCChannel2.setBounds(20, 50, 30, 30);

        labelGroupReferenceC.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGroupReferenceC.setText("12");
        labelGroupReferenceC.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), java.awt.Color.lightGray, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        labelGroupReferenceC.setOpaque(true);
        groupPanelC.add(labelGroupReferenceC);
        labelGroupReferenceC.setBounds(70, 90, 30, 30);

        labelIndexCR.setText("R");
        groupPanelC.add(labelIndexCR);
        labelIndexCR.setBounds(60, 100, 10, 30);

        labelIndexC4.setText("12");
        groupPanelC.add(labelIndexC4);
        labelIndexC4.setBounds(55, 60, 20, 30);

        labelIndexC3.setText("11");
        groupPanelC.add(labelIndexC3);
        labelIndexC3.setBounds(55, 20, 20, 30);

        labelIndexC1.setText("9");
        groupPanelC.add(labelIndexC1);
        labelIndexC1.setBounds(10, 20, 10, 30);

        add(groupPanelC);
        groupPanelC.setBounds(310, 160, 120, 130);

        groupLabelC.setFont(new java.awt.Font("Arial", 0, 14));
        groupLabelC.setText("Group C:");
        add(groupLabelC);
        groupLabelC.setBounds(310, 140, 70, 17);

        groupPanelD.setLayout(null);

        groupPanelD.setBackground(new java.awt.Color(255, 255, 255));
        groupPanelD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        labelGroupDChannel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGroupDChannel4.setText("12");
        labelGroupDChannel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), java.awt.Color.lightGray, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        labelGroupDChannel4.setOpaque(true);
        groupPanelD.add(labelGroupDChannel4);
        labelGroupDChannel4.setBounds(70, 50, 30, 30);

        labelGroupDChannel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGroupDChannel3.setText("12");
        labelGroupDChannel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), java.awt.Color.lightGray, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        labelGroupDChannel3.setOpaque(true);
        groupPanelD.add(labelGroupDChannel3);
        labelGroupDChannel3.setBounds(70, 10, 30, 30);

        labelGroupDChannel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGroupDChannel1.setText("12");
        labelGroupDChannel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), java.awt.Color.lightGray, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        labelGroupDChannel1.setOpaque(true);
        groupPanelD.add(labelGroupDChannel1);
        labelGroupDChannel1.setBounds(20, 10, 30, 30);

        labelGroupDChannel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGroupDChannel2.setText("12");
        labelGroupDChannel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), java.awt.Color.lightGray, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        labelGroupDChannel2.setOpaque(true);
        groupPanelD.add(labelGroupDChannel2);
        labelGroupDChannel2.setBounds(20, 50, 30, 30);

        labelGroupReferenceD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGroupReferenceD.setText("12");
        labelGroupReferenceD.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), java.awt.Color.lightGray, new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));
        labelGroupReferenceD.setOpaque(true);
        groupPanelD.add(labelGroupReferenceD);
        labelGroupReferenceD.setBounds(70, 90, 30, 30);

        labelIndexDR.setText("R");
        groupPanelD.add(labelIndexDR);
        labelIndexDR.setBounds(60, 100, 10, 30);

        labelIndexD4.setText("16");
        groupPanelD.add(labelIndexD4);
        labelIndexD4.setBounds(55, 60, 20, 30);

        labelIndexD3.setText("15");
        groupPanelD.add(labelIndexD3);
        labelIndexD3.setBounds(55, 20, 20, 30);

        labelIndexD1.setText("13");
        groupPanelD.add(labelIndexD1);
        labelIndexD1.setBounds(5, 20, 20, 30);

        labelIndexD2.setText("14");
        groupPanelD.add(labelIndexD2);
        labelIndexD2.setBounds(5, 60, 20, 30);

        add(groupPanelD);
        groupPanelD.setBounds(450, 160, 120, 130);

        groupLabelD.setFont(new java.awt.Font("Arial", 0, 14));
        groupLabelD.setText("Group D:");
        add(groupLabelD);
        groupLabelD.setBounds(450, 140, 70, 17);

    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel groupLabelA;
    private javax.swing.JLabel groupLabelB;
    private javax.swing.JLabel groupLabelC;
    private javax.swing.JLabel groupLabelD;
    private javax.swing.JPanel groupPanelA;
    private javax.swing.JPanel groupPanelB;
    private javax.swing.JPanel groupPanelC;
    private javax.swing.JPanel groupPanelD;
    private javax.swing.JLabel imageLabel1;
    private javax.swing.JLabel imageLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel labelGroupAChannel1;
    private javax.swing.JLabel labelGroupAChannel2;
    private javax.swing.JLabel labelGroupAChannel3;
    private javax.swing.JLabel labelGroupAChannel4;
    private javax.swing.JLabel labelGroupBChannel1;
    private javax.swing.JLabel labelGroupBChannel2;
    private javax.swing.JLabel labelGroupBChannel3;
    private javax.swing.JLabel labelGroupBChannel4;
    private javax.swing.JLabel labelGroupCChannel1;
    private javax.swing.JLabel labelGroupCChannel2;
    private javax.swing.JLabel labelGroupCChannel3;
    private javax.swing.JLabel labelGroupCChannel4;
    private javax.swing.JLabel labelGroupDChannel1;
    private javax.swing.JLabel labelGroupDChannel2;
    private javax.swing.JLabel labelGroupDChannel3;
    private javax.swing.JLabel labelGroupDChannel4;
    private javax.swing.JLabel labelGroupReferenceA;
    private javax.swing.JLabel labelGroupReferenceB;
    private javax.swing.JLabel labelGroupReferenceC;
    private javax.swing.JLabel labelGroupReferenceD;
    private javax.swing.JLabel labelIndexA1;
    private javax.swing.JLabel labelIndexA2;
    private javax.swing.JLabel labelIndexA3;
    private javax.swing.JLabel labelIndexA4;
    private javax.swing.JLabel labelIndexAR;
    private javax.swing.JLabel labelIndexB1;
    private javax.swing.JLabel labelIndexB2;
    private javax.swing.JLabel labelIndexB3;
    private javax.swing.JLabel labelIndexB4;
    private javax.swing.JLabel labelIndexBR;
    private javax.swing.JLabel labelIndexC1;
    private javax.swing.JLabel labelIndexC2;
    private javax.swing.JLabel labelIndexC3;
    private javax.swing.JLabel labelIndexC4;
    private javax.swing.JLabel labelIndexCR;
    private javax.swing.JLabel labelIndexD1;
    private javax.swing.JLabel labelIndexD2;
    private javax.swing.JLabel labelIndexD3;
    private javax.swing.JLabel labelIndexD4;
    private javax.swing.JLabel labelIndexDR;
    private javax.swing.JLabel serialLabel;
    private javax.swing.JButton startButton;
    // End of variables declaration//GEN-END:variables
    
}
