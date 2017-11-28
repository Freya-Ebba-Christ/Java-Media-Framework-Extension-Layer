/*
 * DaqSettingsPanel.java
 *
 * Created on 16. Juli 2007, 09:24
 */

package media.protocol.gtec;

import java.awt.BorderLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author  Administrator
 */
public class DaqSettingsPanel extends javax.swing.JPanel {
    private static final int NUMCHANNELS = 16;
    public static String OK = "OK";
    private JCheckBox[] channelCheckBoxes = new JCheckBox[16];
    private ConfigurationTable configurationTable;
    private ConfigurationTableModel configurationTableModel;
    
    /** Creates new form DaqSettingsPanel */
    public DaqSettingsPanel() {
        initComponents();
        
        channelCheckBoxes[0] = getChannelCheckBox01();
        channelCheckBoxes[1] = getChannelCheckBox02();
        channelCheckBoxes[2] = getChannelCheckBox03();
        channelCheckBoxes[3] = getChannelCheckBox04();
        channelCheckBoxes[4] = getChannelCheckBox05();
        channelCheckBoxes[5] = getChannelCheckBox06();
        channelCheckBoxes[6] = getChannelCheckBox07();
        channelCheckBoxes[7] = getChannelCheckBox08();
        channelCheckBoxes[8] = getChannelCheckBox09();
        channelCheckBoxes[9] = getChannelCheckBox10();
        channelCheckBoxes[10] = getChannelCheckBox11();
        channelCheckBoxes[11] = getChannelCheckBox12();
        channelCheckBoxes[12] = getChannelCheckBox13();
        channelCheckBoxes[13] = getChannelCheckBox14();
        channelCheckBoxes[14] = getChannelCheckBox15();
        channelCheckBoxes[15] = getChannelCheckBox16();
        
        configurationTableModel = new ConfigurationTableModel();
        configurationTable = new ConfigurationTable(configurationTableModel);
        configurationTable.setPreferredScrollableViewportSize(new Dimension(600, 70));
        configurationTable.setFillsViewportHeight(true);
        JScrollPane channelSettingsScrollPanel = new JScrollPane(configurationTable);
        
        configurationTable.initColumnSizes(configurationTable);
        configurationTable.setUpChannelColumn(configurationTable, configurationTable.getColumnModel().getColumn(0));
        configurationTable.setUpBipolarColumn(configurationTable, configurationTable.getColumnModel().getColumn(1));
        configurationTable.setUpDRLColumn(configurationTable, configurationTable.getColumnModel().getColumn(4));
        configurationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(channelSettingsScrollPanel);
        channelSettingsScrollPanel.setBounds(10, 390, 840, 210);
    }
    
    public ConfigurationTableModel getConfigurationTableModel() {
        return configurationTableModel;
    }
    
    public ConfigurationTable getConfigurationTable() {
        return configurationTable;
    }
    
    JCheckBox getChannelCheckBox(int channel){
        return channelCheckBoxes[channel];
    }
    
    public static int getNumChannels(){
        return NUMCHANNELS;
    }
    
    public JLabel getSerialLabel() {
        return serialLabel;
    }
    
    public JCheckBox getGroundGroupCheckBoxA() {
        return groundGroupCheckBoxA;
    }
    
    public JCheckBox getGroundGroupCheckBoxB() {
        return groundGroupCheckBoxB;
    }
    
    public JCheckBox getGroundGroupCheckBoxC() {
        return groundGroupCheckBoxC;
    }
    
    public JCheckBox getGroundGroupCheckBoxD() {
        return groundGroupCheckBoxD;
    }
    
    public JTextField getAmplitudeTextField() {
        return amplitudeTextField;
    }
    
    public JCheckBox getChannelCheckBox01() {
        return channelCheckBox01;
    }
    
    public JCheckBox getChannelCheckBox02() {
        return channelCheckBox02;
    }
    
    public JCheckBox getChannelCheckBox03() {
        return channelCheckBox03;
    }
    
    public JCheckBox getChannelCheckBox04() {
        return channelCheckBox04;
    }
    
    public JCheckBox getChannelCheckBox05() {
        return channelCheckBox05;
    }
    
    public JCheckBox getChannelCheckBox06() {
        return channelCheckBox06;
    }
    
    public JCheckBox getChannelCheckBox07() {
        return channelCheckBox07;
    }
    
    public JCheckBox getChannelCheckBox08() {
        return channelCheckBox08;
    }
    
    public JCheckBox getChannelCheckBox09() {
        return channelCheckBox09;
    }
    
    public JCheckBox getChannelCheckBox10() {
        return channelCheckBox10;
    }
    
    public JCheckBox getChannelCheckBox11() {
        return channelCheckBox11;
    }
    
    public JCheckBox getChannelCheckBox12() {
        return channelCheckBox12;
    }
    
    public JCheckBox getChannelCheckBox13() {
        return channelCheckBox13;
    }
    
    public JCheckBox getChannelCheckBox14() {
        return channelCheckBox14;
    }
    
    public JCheckBox getChannelCheckBox15() {
        return channelCheckBox15;
    }
    
    public JCheckBox getChannelCheckBox16() {
        return channelCheckBox16;
    }
    
    public JTextField getFrequencyTextField() {
        return frequencyTextField;
    }
    
    public JComboBox getJComboBox2() {
        return jComboBox2;
    }
    
    public JTextField getJTextField2() {
        return jTextField2;
    }
    
    public JRadioButton getMeasureRadioButton() {
        return measureRadioButton;
    }
    
    public ButtonGroup getModeButtonGroup() {
        return modeButtonGroup;
    }
    
    public JTextField getOffsetTextField() {
        return offsetTextField;
    }
    
    public JButton getOkButton() {
        return okButton;
    }
    
    public JCheckBox getReferenceGroupCheckBoxA() {
        return referenceGroupCheckBoxA;
    }
    
    public JCheckBox getReferenceGroupCheckBoxB() {
        return referenceGroupCheckBoxB;
    }
    
    public JCheckBox getReferenceGroupCheckBoxC() {
        return referenceGroupCheckBoxC;
    }
    
    public JCheckBox getReferenceGroupCheckBoxD() {
        return referenceGroupCheckBoxD;
    }
    
    public JComboBox getSamplingrateComboBox() {
        return samplingrateComboBox;
    }
    
    public JCheckBox getShortcutCheckBox() {
        return shortcutCheckBox;
    }
    
    public JCheckBox getSlaveCheckBox() {
        return slaveCheckBox;
    }
    
    public JComboBox getTestSignalComboBox() {
        return testSignalComboBox;
    }
    
    public JRadioButton getTestSignalRadioButton() {
        return testSignalRadioButton;
    }
    
    public JCheckBox getTriggerCheckBox() {
        return triggerCheckBox;
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        DaqSettingsPanel aDaqSettingsPanel = new DaqSettingsPanel();
        frame.add(aDaqSettingsPanel,BorderLayout.CENTER);
        frame.setSize(760,800);
        frame.setVisible(true);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        modeButtonGroup = new javax.swing.ButtonGroup();
        jPanel5 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        imageLabel1 = new javax.swing.JLabel();
        serialLabel = new javax.swing.JLabel();
        imageLabel2 = new javax.swing.JLabel();
        settingsLabel = new javax.swing.JLabel();
        groundAndReferencePanel = new javax.swing.JPanel();
        groundGroupCheckBoxA = new javax.swing.JCheckBox();
        commonGroundLabel = new javax.swing.JLabel();
        groundGroupCheckBoxB = new javax.swing.JCheckBox();
        groundGroupCheckBoxC = new javax.swing.JCheckBox();
        groundGroupCheckBoxD = new javax.swing.JCheckBox();
        referenceGroupCheckBoxA = new javax.swing.JCheckBox();
        referenceGroupCheckBoxB = new javax.swing.JCheckBox();
        referenceGroupCheckBoxC = new javax.swing.JCheckBox();
        referenceGroupCheckBoxD = new javax.swing.JCheckBox();
        commonReferenceLabel = new javax.swing.JLabel();
        optionsPanel = new javax.swing.JPanel();
        optionsLabel = new javax.swing.JLabel();
        triggerCheckBox = new javax.swing.JCheckBox();
        slaveCheckBox = new javax.swing.JCheckBox();
        shortcutCheckBox = new javax.swing.JCheckBox();
        modePanel = new javax.swing.JPanel();
        modeLabel = new javax.swing.JLabel();
        measureRadioButton = new javax.swing.JRadioButton();
        testSignalRadioButton = new javax.swing.JRadioButton();
        analogOutputPanel = new javax.swing.JPanel();
        testSignalComboBox = new javax.swing.JComboBox();
        testSignalLabel = new javax.swing.JLabel();
        amplitudeLabel = new javax.swing.JLabel();
        amplitudeTextField = new javax.swing.JTextField();
        unitLabelAmplitude = new javax.swing.JLabel();
        unitLabelOffset = new javax.swing.JLabel();
        offsetTextField = new javax.swing.JTextField();
        offsetLabel = new javax.swing.JLabel();
        uniLabelFrequency = new javax.swing.JLabel();
        frequencyTextField = new javax.swing.JTextField();
        frequencyLabel = new javax.swing.JLabel();
        channelSelectionPanel = new javax.swing.JPanel();
        channelCheckBox01 = new javax.swing.JCheckBox();
        channelCheckBox02 = new javax.swing.JCheckBox();
        channelCheckBox03 = new javax.swing.JCheckBox();
        channelCheckBox04 = new javax.swing.JCheckBox();
        channelCheckBox05 = new javax.swing.JCheckBox();
        channelCheckBox06 = new javax.swing.JCheckBox();
        channelCheckBox07 = new javax.swing.JCheckBox();
        channelCheckBox08 = new javax.swing.JCheckBox();
        channelCheckBox09 = new javax.swing.JCheckBox();
        channelCheckBox10 = new javax.swing.JCheckBox();
        channelCheckBox11 = new javax.swing.JCheckBox();
        channelCheckBox12 = new javax.swing.JCheckBox();
        channelCheckBox13 = new javax.swing.JCheckBox();
        channelCheckBox14 = new javax.swing.JCheckBox();
        samplingrateLabel = new javax.swing.JLabel();
        channelCheckBox15 = new javax.swing.JCheckBox();
        channelCheckBox16 = new javax.swing.JCheckBox();
        samplingrateComboBox = new javax.swing.JComboBox();
        okButton = new javax.swing.JButton();

        jPanel5.setLayout(null);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "square", "sawtooth", "sine", "noise" }));
        jPanel5.add(jComboBox2);
        jComboBox2.setBounds(10, 30, 90, 20);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14));
        jLabel12.setText("Analog output:");
        jPanel5.add(jLabel12);
        jLabel12.setBounds(10, 10, 120, 17);

        jLabel13.setText("Amplitude:");
        jPanel5.add(jLabel13);
        jLabel13.setBounds(10, 60, 60, 20);

        jPanel5.add(jTextField2);
        jTextField2.setBounds(70, 60, 40, 20);

        jLabel14.setText("mV");
        jPanel5.add(jLabel14);
        jLabel14.setBounds(120, 60, 20, 20);

        setLayout(null);

        setBackground(new java.awt.Color(255, 255, 255));
        imageLabel1.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir")+"\\resources\\gtec\\graphics\\gUSBampLabel.png"));
        add(imageLabel1);
        imageLabel1.setBounds(130, 0, 246, 64);

        serialLabel.setFont(new java.awt.Font("Arial", 0, 14));
        serialLabel.setText("Serial:");
        add(serialLabel);
        serialLabel.setBounds(420, 110, 250, 17);

        imageLabel2.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir")+"\\resources\\gtec\\graphics\\USBamp.png"));
        add(imageLabel2);
        imageLabel2.setBounds(380, 0, 160, 116);

        settingsLabel.setFont(new java.awt.Font("Arial", 0, 14));
        settingsLabel.setText("Specify amplifier settings:");
        add(settingsLabel);
        settingsLabel.setBounds(10, 110, 170, 17);

        groundAndReferencePanel.setLayout(null);

        groundAndReferencePanel.setBackground(new java.awt.Color(255, 255, 255));
        groundAndReferencePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        groundGroupCheckBoxA.setBackground(new java.awt.Color(255, 255, 255));
        groundGroupCheckBoxA.setText("Group A ");
        groundGroupCheckBoxA.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        groundGroupCheckBoxA.setMargin(new java.awt.Insets(0, 0, 0, 0));
        groundAndReferencePanel.add(groundGroupCheckBoxA);
        groundGroupCheckBoxA.setBounds(10, 40, 80, 15);

        commonGroundLabel.setFont(new java.awt.Font("Arial", 0, 14));
        commonGroundLabel.setText("Common ground:");
        groundAndReferencePanel.add(commonGroundLabel);
        commonGroundLabel.setBounds(10, 10, 120, 17);

        groundGroupCheckBoxB.setBackground(new java.awt.Color(255, 255, 255));
        groundGroupCheckBoxB.setText("Group B");
        groundGroupCheckBoxB.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        groundGroupCheckBoxB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        groundAndReferencePanel.add(groundGroupCheckBoxB);
        groundGroupCheckBoxB.setBounds(10, 60, 80, 15);

        groundGroupCheckBoxC.setBackground(new java.awt.Color(255, 255, 255));
        groundGroupCheckBoxC.setText("Group C");
        groundGroupCheckBoxC.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        groundGroupCheckBoxC.setMargin(new java.awt.Insets(0, 0, 0, 0));
        groundAndReferencePanel.add(groundGroupCheckBoxC);
        groundGroupCheckBoxC.setBounds(10, 80, 80, 15);

        groundGroupCheckBoxD.setBackground(new java.awt.Color(255, 255, 255));
        groundGroupCheckBoxD.setText("Group D");
        groundGroupCheckBoxD.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        groundGroupCheckBoxD.setMargin(new java.awt.Insets(0, 0, 0, 0));
        groundAndReferencePanel.add(groundGroupCheckBoxD);
        groundGroupCheckBoxD.setBounds(10, 100, 90, 15);

        referenceGroupCheckBoxA.setBackground(new java.awt.Color(255, 255, 255));
        referenceGroupCheckBoxA.setText("Group A");
        referenceGroupCheckBoxA.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        referenceGroupCheckBoxA.setMargin(new java.awt.Insets(0, 0, 0, 0));
        groundAndReferencePanel.add(referenceGroupCheckBoxA);
        referenceGroupCheckBoxA.setBounds(130, 40, 90, 15);

        referenceGroupCheckBoxB.setBackground(new java.awt.Color(255, 255, 255));
        referenceGroupCheckBoxB.setText("Group B");
        referenceGroupCheckBoxB.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        referenceGroupCheckBoxB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        groundAndReferencePanel.add(referenceGroupCheckBoxB);
        referenceGroupCheckBoxB.setBounds(130, 60, 80, 15);

        referenceGroupCheckBoxC.setBackground(new java.awt.Color(255, 255, 255));
        referenceGroupCheckBoxC.setText("Group C");
        referenceGroupCheckBoxC.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        referenceGroupCheckBoxC.setMargin(new java.awt.Insets(0, 0, 0, 0));
        groundAndReferencePanel.add(referenceGroupCheckBoxC);
        referenceGroupCheckBoxC.setBounds(130, 80, 80, 15);

        referenceGroupCheckBoxD.setBackground(new java.awt.Color(255, 255, 255));
        referenceGroupCheckBoxD.setText("Group D");
        referenceGroupCheckBoxD.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        referenceGroupCheckBoxD.setMargin(new java.awt.Insets(0, 0, 0, 0));
        groundAndReferencePanel.add(referenceGroupCheckBoxD);
        referenceGroupCheckBoxD.setBounds(130, 100, 80, 15);

        commonReferenceLabel.setFont(new java.awt.Font("Arial", 0, 14));
        commonReferenceLabel.setText("Common reference:");
        groundAndReferencePanel.add(commonReferenceLabel);
        commonReferenceLabel.setBounds(130, 10, 140, 17);

        add(groundAndReferencePanel);
        groundAndReferencePanel.setBounds(10, 130, 260, 150);

        optionsPanel.setLayout(null);

        optionsPanel.setBackground(new java.awt.Color(255, 255, 255));
        optionsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        optionsLabel.setFont(new java.awt.Font("Arial", 0, 14));
        optionsLabel.setText("Options:");
        optionsPanel.add(optionsLabel);
        optionsLabel.setBounds(10, 10, 110, 17);

        triggerCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        triggerCheckBox.setText("Trigger");
        triggerCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        triggerCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        optionsPanel.add(triggerCheckBox);
        triggerCheckBox.setBounds(10, 40, 90, 15);

        slaveCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        slaveCheckBox.setText("Slave");
        slaveCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        slaveCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        optionsPanel.add(slaveCheckBox);
        slaveCheckBox.setBounds(10, 60, 100, 15);

        shortcutCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        shortcutCheckBox.setText("Shortcut");
        shortcutCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        shortcutCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        optionsPanel.add(shortcutCheckBox);
        shortcutCheckBox.setBounds(10, 80, 110, 15);

        add(optionsPanel);
        optionsPanel.setBounds(280, 130, 130, 150);

        modePanel.setLayout(null);

        modePanel.setBackground(new java.awt.Color(255, 255, 255));
        modePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        modeLabel.setFont(new java.awt.Font("Arial", 0, 14));
        modeLabel.setText("Mode:");
        modePanel.add(modeLabel);
        modeLabel.setBounds(10, 10, 110, 17);

        measureRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        modeButtonGroup.add(measureRadioButton);
        measureRadioButton.setSelected(true);
        measureRadioButton.setText("Measure");
        measureRadioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        measureRadioButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        modePanel.add(measureRadioButton);
        measureRadioButton.setBounds(10, 40, 100, 15);

        testSignalRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        modeButtonGroup.add(testSignalRadioButton);
        testSignalRadioButton.setText("Test signal");
        testSignalRadioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        testSignalRadioButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        modePanel.add(testSignalRadioButton);
        testSignalRadioButton.setBounds(10, 60, 100, 15);

        add(modePanel);
        modePanel.setBounds(420, 130, 130, 150);

        analogOutputPanel.setLayout(null);

        analogOutputPanel.setBackground(new java.awt.Color(255, 255, 255));
        analogOutputPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        testSignalComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "sine", "square", "sawtooth", "noise" }));
        analogOutputPanel.add(testSignalComboBox);
        testSignalComboBox.setBounds(10, 30, 160, 20);

        testSignalLabel.setFont(new java.awt.Font("Arial", 0, 14));
        testSignalLabel.setText("Test signal:");
        analogOutputPanel.add(testSignalLabel);
        testSignalLabel.setBounds(10, 10, 120, 17);

        amplitudeLabel.setText("Amplitude:");
        analogOutputPanel.add(amplitudeLabel);
        amplitudeLabel.setBounds(10, 60, 70, 20);

        amplitudeTextField.setText("12");
        analogOutputPanel.add(amplitudeTextField);
        amplitudeTextField.setBounds(90, 60, 60, 20);

        unitLabelAmplitude.setText("mV");
        analogOutputPanel.add(unitLabelAmplitude);
        unitLabelAmplitude.setBounds(160, 60, 20, 20);

        unitLabelOffset.setText("mV");
        analogOutputPanel.add(unitLabelOffset);
        unitLabelOffset.setBounds(160, 90, 20, 20);

        offsetTextField.setText("0");
        analogOutputPanel.add(offsetTextField);
        offsetTextField.setBounds(90, 90, 60, 20);

        offsetLabel.setText("Offset:");
        analogOutputPanel.add(offsetLabel);
        offsetLabel.setBounds(10, 90, 60, 20);

        uniLabelFrequency.setText("Hz");
        analogOutputPanel.add(uniLabelFrequency);
        uniLabelFrequency.setBounds(160, 120, 20, 20);

        frequencyTextField.setText("10");
        analogOutputPanel.add(frequencyTextField);
        frequencyTextField.setBounds(90, 120, 60, 20);

        frequencyLabel.setText("Frequency:");
        analogOutputPanel.add(frequencyLabel);
        frequencyLabel.setBounds(10, 120, 70, 20);

        add(analogOutputPanel);
        analogOutputPanel.setBounds(560, 130, 290, 150);

        channelSelectionPanel.setLayout(null);

        channelSelectionPanel.setBackground(new java.awt.Color(255, 255, 255));
        channelSelectionPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        channelCheckBox01.setBackground(new java.awt.Color(255, 255, 255));
        channelCheckBox01.setSelected(true);
        channelCheckBox01.setText("CH01");
        channelCheckBox01.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        channelCheckBox01.setMargin(new java.awt.Insets(0, 0, 0, 0));
        channelSelectionPanel.add(channelCheckBox01);
        channelCheckBox01.setBounds(10, 10, 50, 15);

        channelCheckBox02.setBackground(new java.awt.Color(255, 255, 255));
        channelCheckBox02.setSelected(true);
        channelCheckBox02.setText("CH02");
        channelCheckBox02.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        channelCheckBox02.setMargin(new java.awt.Insets(0, 0, 0, 0));
        channelSelectionPanel.add(channelCheckBox02);
        channelCheckBox02.setBounds(10, 30, 50, 15);

        channelCheckBox03.setBackground(new java.awt.Color(255, 255, 255));
        channelCheckBox03.setSelected(true);
        channelCheckBox03.setText("CH03");
        channelCheckBox03.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        channelCheckBox03.setMargin(new java.awt.Insets(0, 0, 0, 0));
        channelSelectionPanel.add(channelCheckBox03);
        channelCheckBox03.setBounds(70, 10, 50, 15);

        channelCheckBox04.setBackground(new java.awt.Color(255, 255, 255));
        channelCheckBox04.setSelected(true);
        channelCheckBox04.setText("CH04");
        channelCheckBox04.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        channelCheckBox04.setMargin(new java.awt.Insets(0, 0, 0, 0));
        channelSelectionPanel.add(channelCheckBox04);
        channelCheckBox04.setBounds(70, 30, 50, 15);

        channelCheckBox05.setBackground(new java.awt.Color(255, 255, 255));
        channelCheckBox05.setSelected(true);
        channelCheckBox05.setText("CH05");
        channelCheckBox05.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        channelCheckBox05.setMargin(new java.awt.Insets(0, 0, 0, 0));
        channelSelectionPanel.add(channelCheckBox05);
        channelCheckBox05.setBounds(130, 10, 50, 15);

        channelCheckBox06.setBackground(new java.awt.Color(255, 255, 255));
        channelCheckBox06.setSelected(true);
        channelCheckBox06.setText("CH06");
        channelCheckBox06.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        channelCheckBox06.setMargin(new java.awt.Insets(0, 0, 0, 0));
        channelSelectionPanel.add(channelCheckBox06);
        channelCheckBox06.setBounds(130, 30, 50, 15);

        channelCheckBox07.setBackground(new java.awt.Color(255, 255, 255));
        channelCheckBox07.setSelected(true);
        channelCheckBox07.setText("CH07");
        channelCheckBox07.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        channelCheckBox07.setMargin(new java.awt.Insets(0, 0, 0, 0));
        channelSelectionPanel.add(channelCheckBox07);
        channelCheckBox07.setBounds(190, 10, 50, 15);

        channelCheckBox08.setBackground(new java.awt.Color(255, 255, 255));
        channelCheckBox08.setSelected(true);
        channelCheckBox08.setText("CH08");
        channelCheckBox08.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        channelCheckBox08.setMargin(new java.awt.Insets(0, 0, 0, 0));
        channelSelectionPanel.add(channelCheckBox08);
        channelCheckBox08.setBounds(190, 30, 50, 15);

        channelCheckBox09.setBackground(new java.awt.Color(255, 255, 255));
        channelCheckBox09.setSelected(true);
        channelCheckBox09.setText("CH09");
        channelCheckBox09.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        channelCheckBox09.setMargin(new java.awt.Insets(0, 0, 0, 0));
        channelSelectionPanel.add(channelCheckBox09);
        channelCheckBox09.setBounds(250, 10, 60, 15);

        channelCheckBox10.setBackground(new java.awt.Color(255, 255, 255));
        channelCheckBox10.setSelected(true);
        channelCheckBox10.setText("CH10");
        channelCheckBox10.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        channelCheckBox10.setMargin(new java.awt.Insets(0, 0, 0, 0));
        channelSelectionPanel.add(channelCheckBox10);
        channelCheckBox10.setBounds(250, 30, 60, 15);

        channelCheckBox11.setBackground(new java.awt.Color(255, 255, 255));
        channelCheckBox11.setSelected(true);
        channelCheckBox11.setText("CH11");
        channelCheckBox11.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        channelCheckBox11.setMargin(new java.awt.Insets(0, 0, 0, 0));
        channelSelectionPanel.add(channelCheckBox11);
        channelCheckBox11.setBounds(310, 10, 50, 15);

        channelCheckBox12.setBackground(new java.awt.Color(255, 255, 255));
        channelCheckBox12.setSelected(true);
        channelCheckBox12.setText("CH12");
        channelCheckBox12.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        channelCheckBox12.setMargin(new java.awt.Insets(0, 0, 0, 0));
        channelSelectionPanel.add(channelCheckBox12);
        channelCheckBox12.setBounds(310, 30, 60, 15);

        channelCheckBox13.setBackground(new java.awt.Color(255, 255, 255));
        channelCheckBox13.setSelected(true);
        channelCheckBox13.setText("CH13");
        channelCheckBox13.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        channelCheckBox13.setMargin(new java.awt.Insets(0, 0, 0, 0));
        channelSelectionPanel.add(channelCheckBox13);
        channelCheckBox13.setBounds(370, 10, 60, 15);

        channelCheckBox14.setBackground(new java.awt.Color(255, 255, 255));
        channelCheckBox14.setSelected(true);
        channelCheckBox14.setText("CH14");
        channelCheckBox14.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        channelCheckBox14.setMargin(new java.awt.Insets(0, 0, 0, 0));
        channelSelectionPanel.add(channelCheckBox14);
        channelCheckBox14.setBounds(370, 30, 50, 15);

        samplingrateLabel.setText("Sampling rate:");
        channelSelectionPanel.add(samplingrateLabel);
        samplingrateLabel.setBounds(540, 10, 90, 20);

        channelCheckBox15.setBackground(new java.awt.Color(255, 255, 255));
        channelCheckBox15.setSelected(true);
        channelCheckBox15.setText("CH15");
        channelCheckBox15.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        channelCheckBox15.setMargin(new java.awt.Insets(0, 0, 0, 0));
        channelSelectionPanel.add(channelCheckBox15);
        channelCheckBox15.setBounds(430, 10, 60, 15);

        channelCheckBox16.setBackground(new java.awt.Color(255, 255, 255));
        channelCheckBox16.setSelected(true);
        channelCheckBox16.setText("CH16");
        channelCheckBox16.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        channelCheckBox16.setMargin(new java.awt.Insets(0, 0, 0, 0));
        channelSelectionPanel.add(channelCheckBox16);
        channelCheckBox16.setBounds(430, 30, 60, 15);

        samplingrateComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "32", "64", "128", "256", "512", "1200", "2400", "4800", "9600", "19200", "38400" }));
        channelSelectionPanel.add(samplingrateComboBox);
        samplingrateComboBox.setBounds(640, 10, 90, 20);

        add(channelSelectionPanel);
        channelSelectionPanel.setBounds(10, 290, 840, 60);

        okButton.setText("OK");
        add(okButton);
        okButton.setBounds(390, 620, 70, 23);

    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel amplitudeLabel;
    private javax.swing.JTextField amplitudeTextField;
    private javax.swing.JPanel analogOutputPanel;
    private javax.swing.JCheckBox channelCheckBox01;
    private javax.swing.JCheckBox channelCheckBox02;
    private javax.swing.JCheckBox channelCheckBox03;
    private javax.swing.JCheckBox channelCheckBox04;
    private javax.swing.JCheckBox channelCheckBox05;
    private javax.swing.JCheckBox channelCheckBox06;
    private javax.swing.JCheckBox channelCheckBox07;
    private javax.swing.JCheckBox channelCheckBox08;
    private javax.swing.JCheckBox channelCheckBox09;
    private javax.swing.JCheckBox channelCheckBox10;
    private javax.swing.JCheckBox channelCheckBox11;
    private javax.swing.JCheckBox channelCheckBox12;
    private javax.swing.JCheckBox channelCheckBox13;
    private javax.swing.JCheckBox channelCheckBox14;
    private javax.swing.JCheckBox channelCheckBox15;
    private javax.swing.JCheckBox channelCheckBox16;
    private javax.swing.JPanel channelSelectionPanel;
    private javax.swing.JLabel commonGroundLabel;
    private javax.swing.JLabel commonReferenceLabel;
    private javax.swing.JLabel frequencyLabel;
    private javax.swing.JTextField frequencyTextField;
    private javax.swing.JPanel groundAndReferencePanel;
    private javax.swing.JCheckBox groundGroupCheckBoxA;
    private javax.swing.JCheckBox groundGroupCheckBoxB;
    private javax.swing.JCheckBox groundGroupCheckBoxC;
    private javax.swing.JCheckBox groundGroupCheckBoxD;
    private javax.swing.JLabel imageLabel1;
    private javax.swing.JLabel imageLabel2;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JRadioButton measureRadioButton;
    private javax.swing.ButtonGroup modeButtonGroup;
    private javax.swing.JLabel modeLabel;
    private javax.swing.JPanel modePanel;
    private javax.swing.JLabel offsetLabel;
    private javax.swing.JTextField offsetTextField;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel optionsLabel;
    private javax.swing.JPanel optionsPanel;
    private javax.swing.JCheckBox referenceGroupCheckBoxA;
    private javax.swing.JCheckBox referenceGroupCheckBoxB;
    private javax.swing.JCheckBox referenceGroupCheckBoxC;
    private javax.swing.JCheckBox referenceGroupCheckBoxD;
    private javax.swing.JComboBox samplingrateComboBox;
    private javax.swing.JLabel samplingrateLabel;
    private javax.swing.JLabel serialLabel;
    private javax.swing.JLabel settingsLabel;
    private javax.swing.JCheckBox shortcutCheckBox;
    private javax.swing.JCheckBox slaveCheckBox;
    private javax.swing.JComboBox testSignalComboBox;
    private javax.swing.JLabel testSignalLabel;
    private javax.swing.JRadioButton testSignalRadioButton;
    private javax.swing.JCheckBox triggerCheckBox;
    private javax.swing.JLabel uniLabelFrequency;
    private javax.swing.JLabel unitLabelAmplitude;
    private javax.swing.JLabel unitLabelOffset;
    // End of variables declaration//GEN-END:variables
    
}