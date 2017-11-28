/*
 * Configuration.java
 *
 * Created on 22. Juli 2007, 00:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.gtec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;

/**
 *
 * @author Administrator
 */
public class Configuration {
    
    private CommonReference commonReference;
    private CommonGround commonGround;
    private boolean trigger;
    private boolean slave;
    private boolean shortcut;
    private boolean modeMeasure;
    private boolean modeTestSignal;
    private int signalTypeIndex;
    private int amplitude;
    private int frequency;
    private int offset;
    private boolean[] usedChannel = new boolean[16];
    private int sampleRateIndex;
    private ConfigurationTableModel model;
    private int sampleRate;
    private byte signalType;
    private Properties configurationProperties;
    
    /** Creates a new instance of Configuration */
    public Configuration() {
    }
    
    public int getChannelsInUse(){
        int channelsInUse = 0;
        for (int i = 0; i < usedChannel.length; i++) {
            if(getUsedChannel(i)){
                channelsInUse++;
            }
        }
        return channelsInUse;
    }
    
    public void makeProperties(){
        configurationProperties = new Properties();
        
        //common ground
        configurationProperties.put("CommonGroundA", ((Boolean)getCommonGround().isGroupA()).toString());
        configurationProperties.put("CommonGroundB", ((Boolean)getCommonGround().isGroupB()).toString());
        configurationProperties.put("CommonGroundC", ((Boolean)getCommonGround().isGroupC()).toString());
        configurationProperties.put("CommonGroundD", ((Boolean)getCommonGround().isGroupD()).toString());
        
        //common reference
        configurationProperties.put("CommonReferenceA", ((Boolean)getCommonReference().isGroupA()).toString());
        configurationProperties.put("CommonReferenceB", ((Boolean)getCommonReference().isGroupB()).toString());
        configurationProperties.put("CommonReferenceC", ((Boolean)getCommonReference().isGroupC()).toString());
        configurationProperties.put("CommonReferenceD", ((Boolean)getCommonReference().isGroupD()).toString());
        
        //options
        configurationProperties.put("Trigger",((Boolean)isTrigger()).toString());
        configurationProperties.put("Slave",((Boolean)isSlave()).toString());
        configurationProperties.put("Shortcut",((Boolean)isShortcut()).toString());
        
        //mode
        configurationProperties.put("Measure",((Boolean)isModeMeasure()).toString());
        configurationProperties.put("TestSignal",((Boolean)isModeTestSignal()).toString());
        
        //test signal
        configurationProperties.put("TestSignalTypeIndex",((Integer)getSignalTypeIndex()).toString());
        configurationProperties.put("TestSignalType",((Byte)getSignalType()).toString());
        configurationProperties.put("Amplitude",((Integer)getAmplitude()).toString());
        configurationProperties.put("Offset",((Integer)getOffset()).toString());
        configurationProperties.put("Frequency",((Integer)getFrequency()).toString());
        
        //channels
        configurationProperties.put("Channel_1",((Boolean)getUsedChannel(0)).toString());
        configurationProperties.put("Channel_2",((Boolean)getUsedChannel(1)).toString());
        configurationProperties.put("Channel_3",((Boolean)getUsedChannel(2)).toString());
        configurationProperties.put("Channel_4",((Boolean)getUsedChannel(3)).toString());
        configurationProperties.put("Channel_5",((Boolean)getUsedChannel(4)).toString());
        configurationProperties.put("Channel_6",((Boolean)getUsedChannel(5)).toString());
        configurationProperties.put("Channel_7",((Boolean)getUsedChannel(6)).toString());
        configurationProperties.put("Channel_8",((Boolean)getUsedChannel(7)).toString());
        configurationProperties.put("Channel_9",((Boolean)getUsedChannel(8)).toString());
        configurationProperties.put("Channel_10",((Boolean)getUsedChannel(9)).toString());
        configurationProperties.put("Channel_11",((Boolean)getUsedChannel(10)).toString());
        configurationProperties.put("Channel_12",((Boolean)getUsedChannel(11)).toString());
        configurationProperties.put("Channel_13",((Boolean)getUsedChannel(12)).toString());
        configurationProperties.put("Channel_14",((Boolean)getUsedChannel(13)).toString());
        configurationProperties.put("Channel_15",((Boolean)getUsedChannel(14)).toString());
        configurationProperties.put("Channel_16",((Boolean)getUsedChannel(15)).toString());
        
        //sampling rate
        configurationProperties.put("SampleRate",((Integer)getSampleRate()).toString());
        configurationProperties.put("SampleRateIndex",((Integer)getSampleRateIndex()).toString());
        
        //bipolar
        for (int chn = 0; chn <DaqSettingsPanel.getNumChannels(); chn++) {
            configurationProperties.put("Bipolar_"+chn,((Integer)getModel().getValueAt(chn,1)).toString());
        }
        
        //drl
        for (int chn = 0; chn <DaqSettingsPanel.getNumChannels(); chn++) {
            configurationProperties.put("DRL_"+chn,((Integer)getModel().getValueAt(chn,4)).toString());
        }
        
        // band pass filter
        for (int chn = 0; chn <DaqSettingsPanel.getNumChannels(); chn++) {
            if(getModel().getValueAt(chn,2) instanceof FilterSpec){
                FilterSpec spec = (FilterSpec)getModel().getValueAt(chn,2);
                configurationProperties.put("BANDPASS_Filter_HF_"+chn,String.valueOf(spec.getHighFreqency()));
                configurationProperties.put("BANDPASS_Filter_LF_"+chn,String.valueOf(spec.getLowFreqency()));
                configurationProperties.put("BANDPASS_Filter_TYPE_"+chn,String.valueOf(spec.getType()));
                configurationProperties.put("BANDPASS_Filter_ORDER_"+chn,String.valueOf(spec.getOrder()));
                configurationProperties.put("BANDPASS_Filter_SR_"+chn,String.valueOf(spec.getSamplingrate()));
                configurationProperties.put("BANDPASS_Filter_INDEX_"+chn,String.valueOf(spec.getIndex()));
            }else{
                configurationProperties.put("BANDPASS_Filter_"+chn,getModel().getValueAt(chn,2));
            }
        }
        
        // notch filter
        for (int chn = 0; chn <DaqSettingsPanel.getNumChannels(); chn++) {
            if(getModel().getValueAt(chn,3) instanceof FilterSpec){
                FilterSpec spec = (FilterSpec)getModel().getValueAt(chn,3);
                configurationProperties.put("NOTCH_Filter_HF_"+chn,String.valueOf(spec.getHighFreqency()));
                configurationProperties.put("NOTCH_Filter_LF_"+chn,String.valueOf(spec.getLowFreqency()));
                configurationProperties.put("NOTCH_Filter_TYPE_"+chn,String.valueOf(spec.getType()));
                configurationProperties.put("NOTCH_Filter_ORDER_"+chn,String.valueOf(spec.getOrder()));
                configurationProperties.put("NOTCH_Filter_SR_"+chn,String.valueOf(spec.getSamplingrate()));
                configurationProperties.put("NOTCH_Filter_INDEX_"+chn,String.valueOf(spec.getIndex()));
            }else{
                configurationProperties.put("NOTCH_Filter_"+chn,getModel().getValueAt(chn,3));
            }
        }
    }
    
    public void save(String fileName){
        makeProperties();
        try{
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
            configurationProperties.store(out,"");
        }catch(Exception e){System.out.println(e);};
    }
    
    public void load(String fileName){
        configurationProperties = new Properties();
        
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            configurationProperties.load(in);
            
            getCommonGround().setGroupA(Boolean.parseBoolean((String)configurationProperties.get("CommonGroundA")));
            getCommonGround().setGroupB(Boolean.parseBoolean((String)configurationProperties.get("CommonGroundB")));
            getCommonGround().setGroupC(Boolean.parseBoolean((String)configurationProperties.get("CommonGroundC")));
            getCommonGround().setGroupD(Boolean.parseBoolean((String)configurationProperties.get("CommonGroundD")));
            
            getCommonReference().setGroupA(Boolean.parseBoolean((String)configurationProperties.get("CommonReferenceA")));
            getCommonReference().setGroupB(Boolean.parseBoolean((String)configurationProperties.get("CommonReferenceB")));
            getCommonReference().setGroupC(Boolean.parseBoolean((String)configurationProperties.get("CommonReferenceC")));
            getCommonReference().setGroupD(Boolean.parseBoolean((String)configurationProperties.get("CommonReferenceD")));
            
            setTrigger(Boolean.parseBoolean((String)configurationProperties.get("Trigger")));
            setSlave(Boolean.parseBoolean((String)configurationProperties.get("Slave")));
            setShortcut(Boolean.parseBoolean((String)configurationProperties.get("Shortcut")));
            
            setModeMeasure(Boolean.parseBoolean((String)configurationProperties.get("Measure")));
            setModeTestSignal(Boolean.parseBoolean((String)configurationProperties.get("TestSignal")));
            
            setSignalTypeIndex(Integer.parseInt((String)configurationProperties.get("TestSignalTypeIndex")));
            setSignalType(Byte.parseByte((String)configurationProperties.get("TestSignalType")));
            setAmplitude(Integer.parseInt((String)configurationProperties.get("Amplitude")));
            setOffset(Integer.parseInt((String)configurationProperties.get("Offset")));
            setFrequency(Integer.parseInt((String)configurationProperties.get("Frequency")));
            
            setUsedChannel(0,Boolean.parseBoolean((String)configurationProperties.get("Channel_1")));
            setUsedChannel(1,Boolean.parseBoolean((String)configurationProperties.get("Channel_2")));
            setUsedChannel(2,Boolean.parseBoolean((String)configurationProperties.get("Channel_3")));
            setUsedChannel(3,Boolean.parseBoolean((String)configurationProperties.get("Channel_4")));
            setUsedChannel(4,Boolean.parseBoolean((String)configurationProperties.get("Channel_5")));
            setUsedChannel(5,Boolean.parseBoolean((String)configurationProperties.get("Channel_6")));
            setUsedChannel(6,Boolean.parseBoolean((String)configurationProperties.get("Channel_7")));
            setUsedChannel(7,Boolean.parseBoolean((String)configurationProperties.get("Channel_8")));
            setUsedChannel(8,Boolean.parseBoolean((String)configurationProperties.get("Channel_9")));
            setUsedChannel(9,Boolean.parseBoolean((String)configurationProperties.get("Channel_10")));
            setUsedChannel(10,Boolean.parseBoolean((String)configurationProperties.get("Channel_11")));
            setUsedChannel(11,Boolean.parseBoolean((String)configurationProperties.get("Channel_12")));
            setUsedChannel(12,Boolean.parseBoolean((String)configurationProperties.get("Channel_13")));
            setUsedChannel(13,Boolean.parseBoolean((String)configurationProperties.get("Channel_14")));
            setUsedChannel(14,Boolean.parseBoolean((String)configurationProperties.get("Channel_15")));
            setUsedChannel(15,Boolean.parseBoolean((String)configurationProperties.get("Channel_16")));
            
            setSampleRate(Integer.parseInt((String)configurationProperties.get("SampleRate")));
            setSampleRateIndex(Integer.parseInt((String)configurationProperties.get("SampleRateIndex")));
        }catch(Exception e){System.out.println(e);};
        
        for (int chn = 0; chn <DaqSettingsPanel.getNumChannels(); chn++) {
            getModel().setValueAt(Integer.parseInt((String)configurationProperties.get("Bipolar_"+chn)),chn,1);
        }
        
        for (int chn = 0; chn <DaqSettingsPanel.getNumChannels(); chn++) {
            getModel().setValueAt(Integer.parseInt((String)configurationProperties.get("DRL_"+chn)),chn,4);
        }
        
        for (int chn = 0; chn <DaqSettingsPanel.getNumChannels(); chn++) {
            if(configurationProperties.containsKey("BANDPASS_Filter_"+chn)){
                getModel().setValueAt((String)configurationProperties.get("BANDPASS_Filter_"+chn),chn,2);
            }else{
                FilterSpec spec = new FilterSpec();
                spec.setHighFreqency(Float.parseFloat((String)configurationProperties.get("BANDPASS_Filter_HF_"+chn)));
                spec.setLowFreqency(Float.parseFloat((String)configurationProperties.get("BANDPASS_Filter_LF_"+chn)));
                spec.setType(Float.parseFloat((String)configurationProperties.get("BANDPASS_Filter_TYPE_"+chn)));
                spec.setOrder(Float.parseFloat((String)configurationProperties.get("BANDPASS_Filter_ORDER_"+chn)));
                spec.setSamplingrate(Float.parseFloat((String)configurationProperties.get("BANDPASS_Filter_SR_"+chn)));
                spec.setIndex(Integer.parseInt((String)configurationProperties.get("BANDPASS_Filter_INDEX_"+chn)));
                getModel().setValueAt(spec,chn,2);
            }
        }
        
        for (int chn = 0; chn <DaqSettingsPanel.getNumChannels(); chn++) {
            if(configurationProperties.containsKey("NOTCH_Filter_"+chn)){
                getModel().setValueAt((String)configurationProperties.get("NOTCH_Filter_"+chn),chn,3);
            }else{
                FilterSpec spec = new FilterSpec();
                spec.setHighFreqency(Float.parseFloat((String)configurationProperties.get("NOTCH_Filter_HF_"+chn)));
                spec.setLowFreqency(Float.parseFloat((String)configurationProperties.get("NOTCH_Filter_LF_"+chn)));
                spec.setType(Float.parseFloat((String)configurationProperties.get("NOTCH_Filter_TYPE_"+chn)));
                spec.setOrder(Float.parseFloat((String)configurationProperties.get("NOTCH_Filter_ORDER_"+chn)));
                spec.setSamplingrate(Float.parseFloat((String)configurationProperties.get("NOTCH_Filter_SR_"+chn)));
                spec.setIndex(Integer.parseInt((String)configurationProperties.get("NOTCH_Filter_INDEX_"+chn)));
                getModel().setValueAt(spec,chn,3);
            }
        }
    }
    
    public int getSampleRate() {
        return sampleRate;
    }
    
    public byte getSignalType() {
        return signalType;
    }
    
    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }
    
    public void setSignalType(byte signalType) {
        this.signalType = signalType;
    }
    
    public ConfigurationTableModel getModel() {
        return model;
    }
    
    public void setModel(ConfigurationTableModel model) {
        this.model = model;
    }
    
    public int getSampleRateIndex() {
        return sampleRateIndex;
    }
    
    public void setSampleRateIndex(int sampleRateIndex) {
        this.sampleRateIndex = sampleRateIndex;
    }
    
    public void setUsedChannel(int channel, boolean flag){
        usedChannel[channel] = flag;
    }
    
    public boolean getUsedChannel(int channel){
        return usedChannel[channel];
    }
    
    public void setTrigger(boolean trigger) {
        this.trigger = trigger;
    }
    
    public void setSlave(boolean slave) {
        this.slave = slave;
    }
    
    public void setSignalTypeIndex(int signalTypeIndex) {
        this.signalTypeIndex = signalTypeIndex;
    }
    
    public void setShortcut(boolean shortcut) {
        this.shortcut = shortcut;
    }
    
    public void setOffset(int offset) {
        this.offset = offset;
    }
    
    public void setModeTestSignal(boolean modeTestSignal) {
        this.modeTestSignal = modeTestSignal;
    }
    
    public void setModeMeasure(boolean modeMeasure) {
        this.modeMeasure = modeMeasure;
    }
    
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
    
    public void setAmplitude(int amplitude) {
        this.amplitude = amplitude;
    }
    
    public boolean isTrigger() {
        return trigger;
    }
    
    public boolean isSlave() {
        return slave;
    }
    
    public boolean isShortcut() {
        return shortcut;
    }
    
    public boolean isModeTestSignal() {
        return modeTestSignal;
    }
    
    public boolean isModeMeasure() {
        return modeMeasure;
    }
    
    public int getSignalTypeIndex() {
        return signalTypeIndex;
    }
    
    public int getOffset() {
        return offset;
    }
    
    public int getFrequency() {
        return frequency;
    }
    
    public int getAmplitude() {
        return amplitude;
    }
    
    public CommonGround getCommonGround() {
        return commonGround;
    }
    
    public CommonReference getCommonReference() {
        return commonReference;
    }
    
    public void setCommonGround(CommonGround commonGround) {
        this.commonGround = commonGround;
    }
    
    public void setCommonReference(CommonReference commonReference) {
        this.commonReference = commonReference;
    }
}