/*
 * ScalingPanel.java
 *
 * Created on 16. Juli 2007, 05:44
 */

package media.protocol.gtec;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import utilities.math.Rounding;

/**
 *
 * @author  Administrator
 */
public class ScalingPanel extends javax.swing.JPanel {
    private final int NUMCHANNELS = 16;
    public static final String OK_BUTTON = "OK_BUTTON";
    private JTextField[] scalingTextField = new JTextField[NUMCHANNELS];
    private JComboBox[] unitComboBox = new JComboBox[NUMCHANNELS];
    
    /** Creates new form ScalingPanel */
    public ScalingPanel() {
        initComponents();
        
        //now put the textfields into an array for easy access...
        scalingTextField[0] = getScaleTextField1();
        scalingTextField[1] = getScaleTextField2();
        scalingTextField[2] = getScaleTextField3();
        scalingTextField[3] = getScaleTextField4();
        scalingTextField[4] = getScaleTextField5();
        scalingTextField[5] = getScaleTextField6();
        scalingTextField[6] = getScaleTextField7();
        scalingTextField[7] = getScaleTextField8();
        scalingTextField[8] = getScaleTextField9();
        scalingTextField[9] = getScaleTextField10();
        scalingTextField[10] = getScaleTextField11();
        scalingTextField[11] = getScaleTextField12();
        scalingTextField[12] = getScaleTextField13();
        scalingTextField[13] = getScaleTextField14();
        scalingTextField[14] = getScaleTextField15();
        scalingTextField[15] = getScaleTextField16();
        
        unitComboBox[0] = getUnitComboBox1();
        unitComboBox[1] = getUnitComboBox2();
        unitComboBox[2] = getUnitComboBox3();
        unitComboBox[3] = getUnitComboBox4();
        unitComboBox[4] = getUnitComboBox5();
        unitComboBox[5] = getUnitComboBox6();
        unitComboBox[6] = getUnitComboBox7();
        unitComboBox[7] = getUnitComboBox8();
        unitComboBox[8] = getUnitComboBox9();
        unitComboBox[9] = getUnitComboBox10();
        unitComboBox[10] = getUnitComboBox11();
        unitComboBox[11] = getUnitComboBox12();
        unitComboBox[12] = getUnitComboBox13();
        unitComboBox[13] = getUnitComboBox14();
        unitComboBox[14] = getUnitComboBox15();
        unitComboBox[15] = getUnitComboBox16();
    }
    
    public int getNumChannels(){
        return NUMCHANNELS;
    }
    
    public JTextField getScalingChannelTextField(int channel){
        return scalingTextField[channel];
    }
    
    public double getScaling(int channel){
        return Double.parseDouble(scalingTextField[channel].getText());
    }
    
    public void setScaling(int channel, double value){
        scalingTextField[channel].setText(Double.toString(Rounding.round(value,3)));
    }
    
    JComboBox getUnitComboBox(int channel){
        return unitComboBox[channel];
    }

    public JButton getOkButton() {
        return okButton;
    }
    
    public int getUnit(int channel){
        return getUnitComboBox(channel).getSelectedIndex();
    }
    
    public void setUnit(int channel, int index){
        getUnitComboBox(channel).setSelectedIndex(index);
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ScalingPanel aScalingPanel = new ScalingPanel();
        frame.add(aScalingPanel,BorderLayout.CENTER);
        frame.setSize(400,750);
        frame.setVisible(true);
    }
    
    public JComboBox getUnitComboBox1() {
        return unitComboBox1;
    }
    
    public JComboBox getUnitComboBox2() {
        return unitComboBox2;
    }
    
    public JComboBox getUnitComboBox3() {
        return unitComboBox3;
    }
    
    public JComboBox getUnitComboBox4() {
        return unitComboBox4;
    }
    
    public JComboBox getUnitComboBox5() {
        return unitComboBox5;
    }
    
    public JComboBox getUnitComboBox6() {
        return unitComboBox6;
    }
    
    public JComboBox getUnitComboBox7() {
        return unitComboBox7;
    }
    
    public JComboBox getUnitComboBox8() {
        return unitComboBox8;
    }
    
    public JComboBox getUnitComboBox9() {
        return unitComboBox9;
    }
    
    public JComboBox getUnitComboBox10() {
        return unitComboBox10;
    }
    
    public JComboBox getUnitComboBox11() {
        return unitComboBox11;
    }
    
    public JComboBox getUnitComboBox12() {
        return unitComboBox12;
    }
    public JComboBox getUnitComboBox13() {
        return unitComboBox13;
    }
    public JComboBox getUnitComboBox14() {
        return unitComboBox14;
    }
    public JComboBox getUnitComboBox15() {
        return unitComboBox15;
    }
    public JComboBox getUnitComboBox16() {
        return unitComboBox16;
    }
    
    public JTextField getScaleTextField1() {
        return scaleTextField1;
    }
    
    public JTextField getScaleTextField2() {
        return scaleTextField2;
    }
    
    public JTextField getScaleTextField3() {
        return scaleTextField3;
    }
    
    public JTextField getScaleTextField4() {
        return scaleTextField4;
    }
    
    public JTextField getScaleTextField5() {
        return scaleTextField5;
    }
    public JTextField getScaleTextField6() {
        return scaleTextField6;
    }
    public JTextField getScaleTextField7() {
        return scaleTextField7;
    }
    public JTextField getScaleTextField8() {
        return scaleTextField8;
    }
    public JTextField getScaleTextField9() {
        return scaleTextField9;
    }
    public JTextField getScaleTextField10() {
        return scaleTextField10;
    }
    public JTextField getScaleTextField11() {
        return scaleTextField11;
    }
    
    public JTextField getScaleTextField12() {
        return scaleTextField12;
    }
    public JTextField getScaleTextField13() {
        return scaleTextField13;
    }
    public JTextField getScaleTextField14() {
        return scaleTextField14;
    }
    public JTextField getScaleTextField15() {
        return scaleTextField15;
    }
    public JTextField getScaleTextField16() {
        return scaleTextField16;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        textLabel = new javax.swing.JLabel();
        imageLabel1 = new javax.swing.JLabel();
        imageLabel2 = new javax.swing.JLabel();
        settingsLabel = new javax.swing.JLabel();
        channelPanel1 = new javax.swing.JPanel();
        channelLabel1 = new javax.swing.JLabel();
        scaleTextField1 = new javax.swing.JTextField();
        unitComboBox1 = new javax.swing.JComboBox();
        channelPanel2 = new javax.swing.JPanel();
        channelLabel2 = new javax.swing.JLabel();
        scaleTextField2 = new javax.swing.JTextField();
        unitComboBox2 = new javax.swing.JComboBox();
        channelPanel3 = new javax.swing.JPanel();
        channelLabel3 = new javax.swing.JLabel();
        scaleTextField3 = new javax.swing.JTextField();
        unitComboBox3 = new javax.swing.JComboBox();
        channelPanel4 = new javax.swing.JPanel();
        channelLabel4 = new javax.swing.JLabel();
        scaleTextField4 = new javax.swing.JTextField();
        unitComboBox4 = new javax.swing.JComboBox();
        channelPanel5 = new javax.swing.JPanel();
        channelLabel5 = new javax.swing.JLabel();
        scaleTextField5 = new javax.swing.JTextField();
        unitComboBox5 = new javax.swing.JComboBox();
        channelPanel6 = new javax.swing.JPanel();
        channelLabel6 = new javax.swing.JLabel();
        scaleTextField6 = new javax.swing.JTextField();
        unitComboBox6 = new javax.swing.JComboBox();
        channelPanel7 = new javax.swing.JPanel();
        channelLabel7 = new javax.swing.JLabel();
        scaleTextField7 = new javax.swing.JTextField();
        unitComboBox7 = new javax.swing.JComboBox();
        channelPanel8 = new javax.swing.JPanel();
        channelLabel8 = new javax.swing.JLabel();
        scaleTextField8 = new javax.swing.JTextField();
        unitComboBox8 = new javax.swing.JComboBox();
        channelPanel9 = new javax.swing.JPanel();
        channelLabel9 = new javax.swing.JLabel();
        scaleTextField9 = new javax.swing.JTextField();
        unitComboBox9 = new javax.swing.JComboBox();
        channelPanel10 = new javax.swing.JPanel();
        channelLabel10 = new javax.swing.JLabel();
        scaleTextField10 = new javax.swing.JTextField();
        unitComboBox10 = new javax.swing.JComboBox();
        channelPanel11 = new javax.swing.JPanel();
        channelLabel11 = new javax.swing.JLabel();
        scaleTextField11 = new javax.swing.JTextField();
        unitComboBox11 = new javax.swing.JComboBox();
        channelPanel12 = new javax.swing.JPanel();
        channelLabel12 = new javax.swing.JLabel();
        scaleTextField12 = new javax.swing.JTextField();
        unitComboBox12 = new javax.swing.JComboBox();
        channelPanel13 = new javax.swing.JPanel();
        channelLabel13 = new javax.swing.JLabel();
        scaleTextField13 = new javax.swing.JTextField();
        unitComboBox13 = new javax.swing.JComboBox();
        channelPanel14 = new javax.swing.JPanel();
        channelLabel14 = new javax.swing.JLabel();
        scaleTextField14 = new javax.swing.JTextField();
        unitComboBox14 = new javax.swing.JComboBox();
        channelPanel15 = new javax.swing.JPanel();
        channelLabel15 = new javax.swing.JLabel();
        scaleTextField15 = new javax.swing.JTextField();
        unitComboBox15 = new javax.swing.JComboBox();
        channelPanel16 = new javax.swing.JPanel();
        channelLabel16 = new javax.swing.JLabel();
        scaleTextField16 = new javax.swing.JTextField();
        unitComboBox16 = new javax.swing.JComboBox();
        okButton = new javax.swing.JButton();
        titlePanel = new javax.swing.JPanel();
        channelLabel = new javax.swing.JLabel();
        scaleLabel = new javax.swing.JLabel();
        unitLabel = new javax.swing.JLabel();

        setLayout(null);

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(400, 800));
        setMinimumSize(new java.awt.Dimension(400, 800));
        textLabel.setBackground(new java.awt.Color(255, 255, 255));
        textLabel.setFont(new java.awt.Font("Arial", 0, 14));
        textLabel.setText("Define scaling for each channel: ");
        add(textLabel);
        textLabel.setBounds(10, 100, 210, 40);

        imageLabel1.setIcon(new javax.swing.ImageIcon("C:\\Dokumente und Einstellungen\\Administrator\\Beaker\\src\\media\\protocol\\gtec\\graphics\\gUSBampLabel.png"));
        add(imageLabel1);
        imageLabel1.setBounds(0, 0, 240, 0);

        imageLabel2.setIcon(new javax.swing.ImageIcon("C:\\Dokumente und Einstellungen\\Administrator\\Beaker\\src\\media\\protocol\\gtec\\graphics\\USBamp.png"));
        add(imageLabel2);
        imageLabel2.setBounds(230, 0, 170, 130);

        settingsLabel.setBackground(new java.awt.Color(255, 255, 255));
        settingsLabel.setFont(new java.awt.Font("Arial", 0, 14));
        settingsLabel.setText("Settings:");
        add(settingsLabel);
        settingsLabel.setBounds(10, 140, 60, 17);

        channelPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        channelPanel1.setBackground(new java.awt.Color(255, 255, 255));
        channelPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        channelLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        channelLabel1.setText("1");
        channelLabel1.setMaximumSize(new java.awt.Dimension(40, 14));
        channelLabel1.setMinimumSize(new java.awt.Dimension(40, 14));
        channelLabel1.setPreferredSize(new java.awt.Dimension(40, 14));
        channelPanel1.add(channelLabel1);

        scaleTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        scaleTextField1.setText("250");
        scaleTextField1.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel1.add(scaleTextField1);

        unitComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "\u00b5V", "mV", "V", "%" }));
        unitComboBox1.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel1.add(unitComboBox1);

        add(channelPanel1);
        channelPanel1.setBounds(70, 180, 260, 30);

        channelPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        channelPanel2.setBackground(new java.awt.Color(255, 255, 255));
        channelPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        channelLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        channelLabel2.setText("2");
        channelLabel2.setMaximumSize(new java.awt.Dimension(40, 14));
        channelLabel2.setMinimumSize(new java.awt.Dimension(40, 14));
        channelLabel2.setPreferredSize(new java.awt.Dimension(40, 14));
        channelPanel2.add(channelLabel2);

        scaleTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        scaleTextField2.setText("250");
        scaleTextField2.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel2.add(scaleTextField2);

        unitComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "\u00b5V", "mV", "V", "%" }));
        unitComboBox2.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel2.add(unitComboBox2);

        add(channelPanel2);
        channelPanel2.setBounds(70, 210, 260, 30);

        channelPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        channelPanel3.setBackground(new java.awt.Color(255, 255, 255));
        channelPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        channelLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        channelLabel3.setText("3");
        channelLabel3.setMaximumSize(new java.awt.Dimension(40, 14));
        channelLabel3.setMinimumSize(new java.awt.Dimension(40, 14));
        channelLabel3.setPreferredSize(new java.awt.Dimension(40, 14));
        channelPanel3.add(channelLabel3);

        scaleTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        scaleTextField3.setText("250");
        scaleTextField3.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel3.add(scaleTextField3);

        unitComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "\u00b5V", "mV", "V", "%" }));
        unitComboBox3.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel3.add(unitComboBox3);

        add(channelPanel3);
        channelPanel3.setBounds(70, 240, 260, 30);

        channelPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        channelPanel4.setBackground(new java.awt.Color(255, 255, 255));
        channelPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        channelLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        channelLabel4.setText("4");
        channelLabel4.setMaximumSize(new java.awt.Dimension(40, 14));
        channelLabel4.setMinimumSize(new java.awt.Dimension(40, 14));
        channelLabel4.setPreferredSize(new java.awt.Dimension(40, 14));
        channelPanel4.add(channelLabel4);

        scaleTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        scaleTextField4.setText("250");
        scaleTextField4.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel4.add(scaleTextField4);

        unitComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "\u00b5V", "mV", "V", "%" }));
        unitComboBox4.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel4.add(unitComboBox4);

        add(channelPanel4);
        channelPanel4.setBounds(70, 270, 260, 30);

        channelPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        channelPanel5.setBackground(new java.awt.Color(255, 255, 255));
        channelPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        channelLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        channelLabel5.setText("5");
        channelLabel5.setMaximumSize(new java.awt.Dimension(40, 14));
        channelLabel5.setMinimumSize(new java.awt.Dimension(40, 14));
        channelLabel5.setPreferredSize(new java.awt.Dimension(40, 14));
        channelPanel5.add(channelLabel5);

        scaleTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        scaleTextField5.setText("250");
        scaleTextField5.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel5.add(scaleTextField5);

        unitComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "\u00b5V", "mV", "V", "%" }));
        unitComboBox5.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel5.add(unitComboBox5);

        add(channelPanel5);
        channelPanel5.setBounds(70, 300, 260, 30);

        channelPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        channelPanel6.setBackground(new java.awt.Color(255, 255, 255));
        channelPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        channelLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        channelLabel6.setText("6");
        channelLabel6.setMaximumSize(new java.awt.Dimension(40, 14));
        channelLabel6.setMinimumSize(new java.awt.Dimension(40, 14));
        channelLabel6.setPreferredSize(new java.awt.Dimension(40, 14));
        channelPanel6.add(channelLabel6);

        scaleTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        scaleTextField6.setText("250");
        scaleTextField6.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel6.add(scaleTextField6);

        unitComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "\u00b5V", "mV", "V", "%" }));
        unitComboBox6.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel6.add(unitComboBox6);

        add(channelPanel6);
        channelPanel6.setBounds(70, 330, 260, 30);

        channelPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        channelPanel7.setBackground(new java.awt.Color(255, 255, 255));
        channelPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        channelLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        channelLabel7.setText("7");
        channelLabel7.setMaximumSize(new java.awt.Dimension(40, 14));
        channelLabel7.setMinimumSize(new java.awt.Dimension(40, 14));
        channelLabel7.setPreferredSize(new java.awt.Dimension(40, 14));
        channelPanel7.add(channelLabel7);

        scaleTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        scaleTextField7.setText("250");
        scaleTextField7.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel7.add(scaleTextField7);

        unitComboBox7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "\u00b5V", "mV", "V", "%" }));
        unitComboBox7.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel7.add(unitComboBox7);

        add(channelPanel7);
        channelPanel7.setBounds(70, 360, 260, 30);

        channelPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        channelPanel8.setBackground(new java.awt.Color(255, 255, 255));
        channelPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        channelLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        channelLabel8.setText("8");
        channelLabel8.setMaximumSize(new java.awt.Dimension(40, 14));
        channelLabel8.setMinimumSize(new java.awt.Dimension(40, 14));
        channelLabel8.setPreferredSize(new java.awt.Dimension(40, 14));
        channelPanel8.add(channelLabel8);

        scaleTextField8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        scaleTextField8.setText("250");
        scaleTextField8.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel8.add(scaleTextField8);

        unitComboBox8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "\u00b5V", "mV", "V", "%" }));
        unitComboBox8.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel8.add(unitComboBox8);

        add(channelPanel8);
        channelPanel8.setBounds(70, 390, 260, 30);

        channelPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        channelPanel9.setBackground(new java.awt.Color(255, 255, 255));
        channelPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        channelLabel9.setBackground(new java.awt.Color(255, 255, 255));
        channelLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        channelLabel9.setText("9");
        channelLabel9.setMaximumSize(new java.awt.Dimension(40, 14));
        channelLabel9.setMinimumSize(new java.awt.Dimension(40, 14));
        channelLabel9.setPreferredSize(new java.awt.Dimension(40, 14));
        channelPanel9.add(channelLabel9);

        scaleTextField9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        scaleTextField9.setText("250");
        scaleTextField9.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel9.add(scaleTextField9);

        unitComboBox9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "\u00b5V", "mV", "V", "%" }));
        unitComboBox9.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel9.add(unitComboBox9);

        add(channelPanel9);
        channelPanel9.setBounds(70, 420, 260, 30);

        channelPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        channelPanel10.setBackground(new java.awt.Color(255, 255, 255));
        channelPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        channelLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        channelLabel10.setText("10");
        channelLabel10.setMaximumSize(new java.awt.Dimension(40, 14));
        channelLabel10.setMinimumSize(new java.awt.Dimension(40, 14));
        channelLabel10.setPreferredSize(new java.awt.Dimension(40, 14));
        channelPanel10.add(channelLabel10);

        scaleTextField10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        scaleTextField10.setText("250");
        scaleTextField10.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel10.add(scaleTextField10);

        unitComboBox10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "\u00b5V", "mV", "V", "%" }));
        unitComboBox10.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel10.add(unitComboBox10);

        add(channelPanel10);
        channelPanel10.setBounds(70, 450, 260, 30);

        channelPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        channelPanel11.setBackground(new java.awt.Color(255, 255, 255));
        channelPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        channelLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        channelLabel11.setText("11");
        channelLabel11.setMaximumSize(new java.awt.Dimension(40, 14));
        channelLabel11.setMinimumSize(new java.awt.Dimension(40, 14));
        channelLabel11.setPreferredSize(new java.awt.Dimension(40, 14));
        channelPanel11.add(channelLabel11);

        scaleTextField11.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        scaleTextField11.setText("250");
        scaleTextField11.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel11.add(scaleTextField11);

        unitComboBox11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "\u00b5V", "mV", "V", "%" }));
        unitComboBox11.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel11.add(unitComboBox11);

        add(channelPanel11);
        channelPanel11.setBounds(70, 480, 260, 30);

        channelPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        channelPanel12.setBackground(new java.awt.Color(255, 255, 255));
        channelPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        channelLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        channelLabel12.setText("12");
        channelLabel12.setMaximumSize(new java.awt.Dimension(40, 14));
        channelLabel12.setMinimumSize(new java.awt.Dimension(40, 14));
        channelLabel12.setPreferredSize(new java.awt.Dimension(40, 14));
        channelPanel12.add(channelLabel12);

        scaleTextField12.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        scaleTextField12.setText("250");
        scaleTextField12.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel12.add(scaleTextField12);

        unitComboBox12.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "\u00b5V", "mV", "V", "%" }));
        unitComboBox12.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel12.add(unitComboBox12);

        add(channelPanel12);
        channelPanel12.setBounds(70, 510, 260, 30);

        channelPanel13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        channelPanel13.setBackground(new java.awt.Color(255, 255, 255));
        channelPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        channelLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        channelLabel13.setText("13");
        channelLabel13.setMaximumSize(new java.awt.Dimension(40, 14));
        channelLabel13.setMinimumSize(new java.awt.Dimension(40, 14));
        channelLabel13.setPreferredSize(new java.awt.Dimension(40, 14));
        channelPanel13.add(channelLabel13);

        scaleTextField13.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        scaleTextField13.setText("250");
        scaleTextField13.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel13.add(scaleTextField13);

        unitComboBox13.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "\u00b5V", "mV", "V", "%" }));
        unitComboBox13.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel13.add(unitComboBox13);

        add(channelPanel13);
        channelPanel13.setBounds(70, 540, 260, 30);

        channelPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        channelPanel14.setBackground(new java.awt.Color(255, 255, 255));
        channelPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        channelLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        channelLabel14.setText("14");
        channelLabel14.setMaximumSize(new java.awt.Dimension(40, 14));
        channelLabel14.setMinimumSize(new java.awt.Dimension(40, 14));
        channelLabel14.setPreferredSize(new java.awt.Dimension(40, 14));
        channelPanel14.add(channelLabel14);

        scaleTextField14.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        scaleTextField14.setText("250");
        scaleTextField14.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel14.add(scaleTextField14);

        unitComboBox14.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "\u00b5V", "mV", "V", "%" }));
        unitComboBox14.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel14.add(unitComboBox14);

        add(channelPanel14);
        channelPanel14.setBounds(70, 570, 260, 30);

        channelPanel15.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        channelPanel15.setBackground(new java.awt.Color(255, 255, 255));
        channelPanel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        channelLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        channelLabel15.setText("15");
        channelLabel15.setMaximumSize(new java.awt.Dimension(40, 14));
        channelLabel15.setMinimumSize(new java.awt.Dimension(40, 14));
        channelLabel15.setPreferredSize(new java.awt.Dimension(40, 14));
        channelPanel15.add(channelLabel15);

        scaleTextField15.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        scaleTextField15.setText("250");
        scaleTextField15.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel15.add(scaleTextField15);

        unitComboBox15.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "\u00b5V", "mV", "V", "%" }));
        unitComboBox15.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel15.add(unitComboBox15);

        add(channelPanel15);
        channelPanel15.setBounds(70, 600, 260, 30);

        channelPanel16.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        channelPanel16.setBackground(new java.awt.Color(255, 255, 255));
        channelPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        channelLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        channelLabel16.setText("16");
        channelLabel16.setMaximumSize(new java.awt.Dimension(40, 14));
        channelLabel16.setMinimumSize(new java.awt.Dimension(40, 14));
        channelLabel16.setPreferredSize(new java.awt.Dimension(40, 14));
        channelPanel16.add(channelLabel16);

        scaleTextField16.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        scaleTextField16.setText("250");
        scaleTextField16.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel16.add(scaleTextField16);

        unitComboBox16.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "\u00b5V", "mV", "V", "%" }));
        unitComboBox16.setPreferredSize(new java.awt.Dimension(100, 20));
        channelPanel16.add(unitComboBox16);

        add(channelPanel16);
        channelPanel16.setBounds(70, 630, 260, 30);

        okButton.setText("OK");
        add(okButton);
        okButton.setBounds(160, 670, 73, 23);

        titlePanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        titlePanel.setBackground(new java.awt.Color(255, 255, 255));
        titlePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        channelLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        channelLabel.setText("Channel");
        channelLabel.setMaximumSize(new java.awt.Dimension(40, 14));
        channelLabel.setMinimumSize(new java.awt.Dimension(40, 14));
        channelLabel.setPreferredSize(new java.awt.Dimension(40, 14));
        titlePanel.add(channelLabel);

        scaleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        scaleLabel.setText("Scale");
        scaleLabel.setMaximumSize(new java.awt.Dimension(100, 14));
        scaleLabel.setMinimumSize(new java.awt.Dimension(100, 14));
        scaleLabel.setPreferredSize(new java.awt.Dimension(100, 14));
        titlePanel.add(scaleLabel);

        unitLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        unitLabel.setText("Unit");
        unitLabel.setMaximumSize(new java.awt.Dimension(100, 14));
        unitLabel.setMinimumSize(new java.awt.Dimension(100, 14));
        unitLabel.setPreferredSize(new java.awt.Dimension(100, 14));
        titlePanel.add(unitLabel);

        add(titlePanel);
        titlePanel.setBounds(70, 150, 260, 30);

    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel channelLabel;
    private javax.swing.JLabel channelLabel1;
    private javax.swing.JLabel channelLabel10;
    private javax.swing.JLabel channelLabel11;
    private javax.swing.JLabel channelLabel12;
    private javax.swing.JLabel channelLabel13;
    private javax.swing.JLabel channelLabel14;
    private javax.swing.JLabel channelLabel15;
    private javax.swing.JLabel channelLabel16;
    private javax.swing.JLabel channelLabel2;
    private javax.swing.JLabel channelLabel3;
    private javax.swing.JLabel channelLabel4;
    private javax.swing.JLabel channelLabel5;
    private javax.swing.JLabel channelLabel6;
    private javax.swing.JLabel channelLabel7;
    private javax.swing.JLabel channelLabel8;
    private javax.swing.JLabel channelLabel9;
    private javax.swing.JPanel channelPanel1;
    private javax.swing.JPanel channelPanel10;
    private javax.swing.JPanel channelPanel11;
    private javax.swing.JPanel channelPanel12;
    private javax.swing.JPanel channelPanel13;
    private javax.swing.JPanel channelPanel14;
    private javax.swing.JPanel channelPanel15;
    private javax.swing.JPanel channelPanel16;
    private javax.swing.JPanel channelPanel2;
    private javax.swing.JPanel channelPanel3;
    private javax.swing.JPanel channelPanel4;
    private javax.swing.JPanel channelPanel5;
    private javax.swing.JPanel channelPanel6;
    private javax.swing.JPanel channelPanel7;
    private javax.swing.JPanel channelPanel8;
    private javax.swing.JPanel channelPanel9;
    private javax.swing.JLabel imageLabel1;
    private javax.swing.JLabel imageLabel2;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel scaleLabel;
    private javax.swing.JTextField scaleTextField1;
    private javax.swing.JTextField scaleTextField10;
    private javax.swing.JTextField scaleTextField11;
    private javax.swing.JTextField scaleTextField12;
    private javax.swing.JTextField scaleTextField13;
    private javax.swing.JTextField scaleTextField14;
    private javax.swing.JTextField scaleTextField15;
    private javax.swing.JTextField scaleTextField16;
    private javax.swing.JTextField scaleTextField2;
    private javax.swing.JTextField scaleTextField3;
    private javax.swing.JTextField scaleTextField4;
    private javax.swing.JTextField scaleTextField5;
    private javax.swing.JTextField scaleTextField6;
    private javax.swing.JTextField scaleTextField7;
    private javax.swing.JTextField scaleTextField8;
    private javax.swing.JTextField scaleTextField9;
    private javax.swing.JLabel settingsLabel;
    private javax.swing.JLabel textLabel;
    private javax.swing.JPanel titlePanel;
    private javax.swing.JComboBox unitComboBox1;
    private javax.swing.JComboBox unitComboBox10;
    private javax.swing.JComboBox unitComboBox11;
    private javax.swing.JComboBox unitComboBox12;
    private javax.swing.JComboBox unitComboBox13;
    private javax.swing.JComboBox unitComboBox14;
    private javax.swing.JComboBox unitComboBox15;
    private javax.swing.JComboBox unitComboBox16;
    private javax.swing.JComboBox unitComboBox2;
    private javax.swing.JComboBox unitComboBox3;
    private javax.swing.JComboBox unitComboBox4;
    private javax.swing.JComboBox unitComboBox5;
    private javax.swing.JComboBox unitComboBox6;
    private javax.swing.JComboBox unitComboBox7;
    private javax.swing.JComboBox unitComboBox8;
    private javax.swing.JComboBox unitComboBox9;
    private javax.swing.JLabel unitLabel;
    // End of variables declaration//GEN-END:variables
    
}
