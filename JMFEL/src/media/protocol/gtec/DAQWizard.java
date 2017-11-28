/*
 * DAQWizard.java
 *
 * Created on 19. Juli 2007, 13:38
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
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import java.util.HashMap;

/**
 *
 * @author Administrator
 */

public class DAQWizard {
    private JFrame frame = new JFrame();
    private AbstractUSBAmp usbAmp;
    private DaqSettingsPanel daqSettingsPanel;
    private CommonGround aCommonGround;
    private FilterSpecList aNotchFilterSpecList;
    private FilterSpecList aFilterSpecList;
    private CommonReference aCommonReference;
    private byte signal;
    private int signalIndex;
    private int amplitude;
    private int frequency;
    private int offset;
    private int sampleRate;
    private byte[] signals = new byte[4];
    private Configuration configuration = new Configuration();
    private String iniPath = System.getProperty("user.dir");
    
    //we use a hashmap to preserve the filterindex of each definition
    private HashMap bandPassFilterIndexMap = new HashMap<String,Integer>();
    private HashMap notchFilterIndexMap = new HashMap<String,Integer>();
    
    public Configuration getConfiguration() {
        return configuration;
    }
    
    public String getIniPath() {
        return iniPath;
    }
    
    public void setIniPath(String iniPath) {
        this.iniPath = iniPath;
    }
    
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
    
    /** Creates a new instance of DAQWizard */
    public DAQWizard() {
        daqSettingsPanel = new DaqSettingsPanel();
        DAQWizardButtonListener aDAQWizardButtonListener = new DAQWizardButtonListener();
        daqSettingsPanel.getOkButton().setActionCommand(daqSettingsPanel.OK);
        daqSettingsPanel.getOkButton().addActionListener(aDAQWizardButtonListener);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(daqSettingsPanel,BorderLayout.CENTER);
        frame.setSize(870,700);
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
    
    public int getSampleRate() {
        return sampleRate;
    }
    
    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }
    
    public AbstractUSBAmp getUsbAmp() {
        return usbAmp;
    }
    
    public int getFrequency() {
        return frequency;
    }
    
    public byte getSignal() {
        return signal;
    }
    
    public int getAmplitude() {
        return amplitude;
    }
    
    public int getOffset() {
        return offset;
    }
    
    public void setAmplitude(int amplitude) {
        this.amplitude = amplitude;
    }
    
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
    
    public void setOffset(int offset) {
        this.offset = offset;
    }
    
    public void setSignal(byte signal) {
        this.signal = signal;
    }
    
    public void setUsbAmp(AbstractUSBAmp usbAmp) {
        this.usbAmp = usbAmp;
        daqSettingsPanel.getSerialLabel().setText(daqSettingsPanel.getSerialLabel().getText()+getUsbAmp().getSerialNumber());
    }
    
    public void setVisible(boolean flag){
        frame.setVisible(flag);
    }
    
    private void handleCommonGround(){
        try{
            aCommonGround = new CommonGround();
            aCommonGround.setGroupA(daqSettingsPanel.getGroundGroupCheckBoxA().isSelected());
            aCommonGround.setGroupB(daqSettingsPanel.getGroundGroupCheckBoxB().isSelected());
            aCommonGround.setGroupC(daqSettingsPanel.getGroundGroupCheckBoxC().isSelected());
            aCommonGround.setGroupD(daqSettingsPanel.getGroundGroupCheckBoxD().isSelected());
            getUsbAmp().setGround(aCommonGround);
            getConfiguration().setCommonGround(aCommonGround);
            
        }catch(Exception ex){System.out.println(ex);};
    }
    
    public void updateFilterDefinitionsColumn(){
        ConfigurationTable configurationTable = daqSettingsPanel.getConfigurationTable();
        JComboBox notchComboBox = new JComboBox();
        JComboBox filterComboBox = new JComboBox();
        
        try{
            getUsbAmp().reloadNotchFilterSpecList();
            notchComboBox.addItem("NO FILTER");
            aNotchFilterSpecList = getUsbAmp().getNotchFilterSpecList();
            for (int filterSpec = 0; filterSpec < aNotchFilterSpecList.getSize(); filterSpec++) {
                FilterSpec aFilterSpec = getUsbAmp().getNotchFilterSpecList().getFilterSpec(filterSpec);
                aFilterSpec.setIndex(filterSpec);
                notchComboBox.addItem(aFilterSpec);
                notchFilterIndexMap.put(aFilterSpec.toString(),new Integer(filterSpec));
            }
        }catch(Exception e){System.out.println(e);};
        
        try{
            getUsbAmp().reloadFilterSpecList();
            filterComboBox.addItem("NO FILTER");
            aFilterSpecList = getUsbAmp().getFilterSpecList();
            for (int filterSpec = 0; filterSpec < aFilterSpecList.getSize(); filterSpec++) {
                FilterSpec aFilterSpec = getUsbAmp().getFilterSpecList().getFilterSpec(filterSpec);
                aFilterSpec.setIndex(filterSpec);
                filterComboBox.addItem(aFilterSpec);
                bandPassFilterIndexMap.put(aFilterSpec.toString(),new Integer(filterSpec));
            }
        }catch(Exception e){System.out.println(e);};
        
        configurationTable.setUpFilterColumn(configurationTable, configurationTable.getColumnModel().getColumn(2),filterComboBox);
        configurationTable.setUpNotchColumn(configurationTable, configurationTable.getColumnModel().getColumn(3),notchComboBox);
    }
    
    private void handleCommonReference(){
        try{
            aCommonReference = new CommonReference();
            aCommonReference.setGroupA(daqSettingsPanel.getReferenceGroupCheckBoxA().isSelected());
            aCommonReference.setGroupB(daqSettingsPanel.getReferenceGroupCheckBoxB().isSelected());
            aCommonReference.setGroupC(daqSettingsPanel.getReferenceGroupCheckBoxC().isSelected());
            aCommonReference.setGroupD(daqSettingsPanel.getReferenceGroupCheckBoxD().isSelected());
            getUsbAmp().setReference(aCommonReference);
            getConfiguration().setCommonReference(aCommonReference);
            
        }catch(Exception ex){System.out.println(ex);};
    }
    
    private void handleOptions(){
        try{
            getUsbAmp().enableTriggerLine(daqSettingsPanel.getTriggerCheckBox().isSelected());
            getUsbAmp().setSlave(daqSettingsPanel.getSlaveCheckBox().isSelected());
            getUsbAmp().enableSC(daqSettingsPanel.getShortcutCheckBox().isSelected());
            
            getConfiguration().setTrigger(daqSettingsPanel.getTriggerCheckBox().isSelected());
            getConfiguration().setSlave(daqSettingsPanel.getSlaveCheckBox().isSelected());
            getConfiguration().setShortcut(daqSettingsPanel.getShortcutCheckBox().isSelected());
            
        }catch(Exception ex){System.out.println(ex);};
    }
    
    private void handleTestSignal(){
        try{
            signals[0] = Amplifier.WS_SINE;
            signals[1] = Amplifier.WS_SQUARE;
            signals[2] = Amplifier.WS_SAWTOOTH;
            signals[3] = Amplifier.WS_NOISE;
            signal = signals[daqSettingsPanel.getTestSignalComboBox().getSelectedIndex()];
            signalIndex = daqSettingsPanel.getTestSignalComboBox().getSelectedIndex();
            amplitude = Integer.parseInt(daqSettingsPanel.getAmplitudeTextField().getText());
            frequency = Integer.parseInt(daqSettingsPanel.getFrequencyTextField().getText());
            offset = Integer.parseInt(daqSettingsPanel.getOffsetTextField().getText());
            getUsbAmp().setDAC(signal,amplitude,frequency,offset);
            
            getConfiguration().setSignalTypeIndex(signalIndex);
            getConfiguration().setAmplitude(amplitude);
            getConfiguration().setFrequency(frequency);
            getConfiguration().setOffset(offset);
            getConfiguration().setSignalType(signal);
            
        }catch(Exception ex){System.out.println(ex);};
    }
    
    private void handleMode(){
        getConfiguration().setModeMeasure(false);
        getConfiguration().setModeTestSignal(false);
        
        try{
            if(daqSettingsPanel.getMeasureRadioButton().isSelected()){
                getUsbAmp().setMode(Amplifier.M_NORMAL);
                getConfiguration().setModeMeasure(true);
            } else{
                getUsbAmp().setMode(Amplifier.M_CALIBRATE);
                getConfiguration().setModeTestSignal(true);
            }
        }catch(Exception ex){System.out.println(ex);};
    }
    
    private void handleChannels(){
        try{
            Vector<Integer> usedChannels = new Vector();
            for (int chn = 0; chn < daqSettingsPanel.getNumChannels(); chn++) {
                getConfiguration().setUsedChannel(chn,daqSettingsPanel.getChannelCheckBox(chn).isSelected());
                
                if(daqSettingsPanel.getChannelCheckBox(chn).isSelected()){
                    usedChannels.addElement(new Integer(chn));
                }
            }
            
            int[] channels = new int[usedChannels.size()];
            for (int chn = 0; chn < usedChannels.size(); chn++) {
                channels[chn] = usedChannels.get(chn).intValue()+1;
            }
            
            getUsbAmp().setChannels(channels);
            
        }catch(Exception ex){System.out.println(ex);};
    }
    
    private void handleSamplingRate(){
        try{
            sampleRate = Integer.parseInt((String)daqSettingsPanel.getSamplingrateComboBox().getSelectedItem());
            getUsbAmp().setSampleRate(sampleRate);
            getConfiguration().setSampleRateIndex(daqSettingsPanel.getSamplingrateComboBox().getSelectedIndex());
            getConfiguration().setSampleRate(sampleRate);
        }catch(Exception ex){System.out.println(ex);};
    }
    
    private void handleChannelConfiguration(){
        int[] bipolarChannels = new int[daqSettingsPanel.getNumChannels()];
        int[] drlChannels = new int[daqSettingsPanel.getNumChannels()];
        int bipolar = 0;
        int drl = 0;
        for (int chn = 0; chn < daqSettingsPanel.getNumChannels(); chn++) {
            bipolar = ((Integer)daqSettingsPanel.getConfigurationTableModel().getValueAt(chn,1)).intValue();
            drl = ((Integer)daqSettingsPanel.getConfigurationTableModel().getValueAt(chn,4)).intValue();
            
            bipolarChannels[chn] = bipolar;
            drlChannels[chn] = drl;
            
            String bandPassKey = daqSettingsPanel.getConfigurationTableModel().getValueAt(chn,2).toString();
            Object bandPassVal = bandPassFilterIndexMap.get(bandPassKey);
            
            String notchPassKey = daqSettingsPanel.getConfigurationTableModel().getValueAt(chn,3).toString();
            Object notchVal = notchFilterIndexMap.get(notchPassKey);
            
            if(bandPassVal!=null){
                int bandpass = ((Integer)bandPassVal).intValue();
                try{
                    getUsbAmp().setBandPass(bandpass,chn+1);
                }catch(Exception e){System.out.println(e);};
            }
            
            if(notchVal!=null){
                int notch = ((Integer)notchVal).intValue();
                try{
                    getUsbAmp().setNotch(notch,chn+1);
                }catch(Exception e){System.out.println(e);};
            }
        }
        
        try{
            getUsbAmp().setDRLChannel(drlChannels);
            getUsbAmp().setBipolar(bipolarChannels);
            getConfiguration().setModel(daqSettingsPanel.getConfigurationTableModel());
        }catch(Exception e){System.out.println(e);};
    }
    
    class DAQWizardButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            
            handleCommonGround();
            handleCommonReference();
            handleOptions();
            handleTestSignal();
            handleMode();
            handleChannels();
            handleSamplingRate();
            handleChannelConfiguration();
            updateGuiFromConfiguration();
            getConfiguration().save(getIniPath() + "configuration.ini");
            //getConfiguration().load(getIniPath() + "configuration.ini");
        }
    }
    
    public void updateGuiFromConfiguration(){
        //update common ground
        daqSettingsPanel.getGroundGroupCheckBoxA().setSelected(getConfiguration().getCommonGround().isGroupA());
        daqSettingsPanel.getGroundGroupCheckBoxB().setSelected(getConfiguration().getCommonGround().isGroupB());
        daqSettingsPanel.getGroundGroupCheckBoxC().setSelected(getConfiguration().getCommonGround().isGroupC());
        daqSettingsPanel.getGroundGroupCheckBoxD().setSelected(getConfiguration().getCommonGround().isGroupD());
        //update common reference
        daqSettingsPanel.getReferenceGroupCheckBoxA().setSelected(getConfiguration().getCommonReference().isGroupA());
        daqSettingsPanel.getReferenceGroupCheckBoxB().setSelected(getConfiguration().getCommonReference().isGroupB());
        daqSettingsPanel.getReferenceGroupCheckBoxC().setSelected(getConfiguration().getCommonReference().isGroupC());
        daqSettingsPanel.getReferenceGroupCheckBoxD().setSelected(getConfiguration().getCommonReference().isGroupD());
        //update options
        daqSettingsPanel.getTriggerCheckBox().setSelected(getConfiguration().isTrigger());
        daqSettingsPanel.getSlaveCheckBox().setSelected(getConfiguration().isSlave());
        daqSettingsPanel.getShortcutCheckBox().setSelected(getConfiguration().isShortcut());
        //update mode
        daqSettingsPanel.getMeasureRadioButton().setSelected(getConfiguration().isModeMeasure());
        daqSettingsPanel.getTestSignalRadioButton().setSelected(getConfiguration().isModeTestSignal());
        
        //determine the signal type...
        
        daqSettingsPanel.getTestSignalComboBox().setSelectedIndex(getConfiguration().getSignalTypeIndex());
        
        //set the amplitude
        daqSettingsPanel.getAmplitudeTextField().setText(String.valueOf(getConfiguration().getAmplitude()));
        //set the offset
        daqSettingsPanel.getOffsetTextField().setText(String.valueOf(getConfiguration().getOffset()));
        //set the frequency
        daqSettingsPanel.getFrequencyTextField().setText(String.valueOf(getConfiguration().getFrequency()));
        
        //update channel checkboxes
        for (int chn = 0; chn < daqSettingsPanel.getNumChannels(); chn++) {
            daqSettingsPanel.getChannelCheckBox(chn).setSelected(getConfiguration().getUsedChannel(chn));
        }
        
        //set samplerate
        daqSettingsPanel.getSamplingrateComboBox().setSelectedIndex(getConfiguration().getSampleRateIndex());
        
        //set table values
        daqSettingsPanel.getConfigurationTable().setModel(getConfiguration().getModel());
    }
    
    private static void usage(){
        System.out.println("-serial <SERIAL_NUMBER> -device <DEVICE_NAME>");
        System.out.println("where DEVICE_NAME can be usbAmpA, usbAmpB, usbAmpC or usbAmpD");
    }
    
    public static void main(String[] args) {
        if(args.length!=4){
            usage();
        }else if(!args[0].equalsIgnoreCase("-serial")){
            usage();
        }else if(!args[2].equalsIgnoreCase("-device")){
            usage();
        }else{
            DAQWizard aDAQWizard = new DAQWizard();
            String file_separator = System.getProperty("file.separator");
            aDAQWizard.setIniPath(System.getProperty("user.dir") + file_separator + "media" + file_separator + "protocol" + file_separator + args[3] + file_separator);
            
            USBAmp amp = new USBAmp();
            amp.setAmp(new NativeAmplifierA());
            amp.setSerialNumber(args[1]);
            
            try{
                amp.open();
                
                try{
                    amp.getAmp().setSampleRate(256);
                }catch(Exception e){System.out.println(e);};
                
                aDAQWizard.setUsbAmp(amp);
                aDAQWizard.updateFilterDefinitionsColumn();
                aDAQWizard.handleCommonGround();
                aDAQWizard.handleCommonReference();
                aDAQWizard.handleOptions();
                aDAQWizard.handleTestSignal();
                aDAQWizard.handleMode();
                aDAQWizard.handleChannels();
                aDAQWizard.handleSamplingRate();
                aDAQWizard.handleChannelConfiguration();
                aDAQWizard.updateGuiFromConfiguration();
                aDAQWizard.getConfiguration().load(aDAQWizard.getIniPath()+"configuration.ini");
                aDAQWizard.updateGuiFromConfiguration();
            }catch(Exception ex){System.out.println(ex);};
            aDAQWizard.setVisible(true);
        }
    }
}