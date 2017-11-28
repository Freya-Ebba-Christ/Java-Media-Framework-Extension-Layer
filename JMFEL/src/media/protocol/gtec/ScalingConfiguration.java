/*
 * ScalingConfiguration.java
 *
 * Created on 5. September 2007, 16:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.gtec;

import exceptions.FormatMismatchException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 *
 * @author Administrator
 */
public class ScalingConfiguration {
    
    public static final int MICROVOLT = 0;
    public static final int MILLIVOLT = 1;
    public static final int VOLT = 2;
    public static final int PERCENT = 3;
    
    private int[] scaleArray = new int[16];
    private int[] unitArray = new int[16];
    private Properties props = new Properties();
    
    /** Creates a new instance of ScalingConfiguration */
    public ScalingConfiguration() {
    }
    
    public int getNumChannels(){
        return 16;
    }
    
    public void setScaling(int channel, int scaling, int unit){
        scaleArray[channel] = scaling;
        unitArray[channel] = unit;
    }
    
    public int getScaling(int channel){
        return scaleArray[channel];
    }
    
    public int getUnitIndex(int channel){
        return unitArray[channel];
    }
    
    public int toMicroVolt(int channel) throws FormatMismatchException{
        int value = 0;
        if(unitArray[channel]==MILLIVOLT){
            value = scaleArray[channel]*1000;
        } else if(unitArray[channel]==VOLT){
            value = scaleArray[channel]*1000000;
        } else if (unitArray[channel]==MICROVOLT){
            value = scaleArray[channel];
        }else if(unitArray[channel]==PERCENT){
            throw new FormatMismatchException("unable to convert to percentage");
        }
        return value;
    }
    
    public String getUnit(int channel){
        if(unitArray[channel]==MICROVOLT){
            return "MICROVOLT";
        }else if(unitArray[channel]==MILLIVOLT){
            return "MILLIVOLT";
        }else if(unitArray[channel]==VOLT){
            return "VOLT";
        }else if(unitArray[channel]==PERCENT){
            return "PERCENT";
        }else return "UNKNOWN";
    }
    
    public int getUnit(String unit){
        if(unit.equalsIgnoreCase("MICROVOLT")){
            return MICROVOLT;
        }else if(unit.equalsIgnoreCase("MILLIVOLT")){
            return MILLIVOLT;
        }else if(unit.equalsIgnoreCase("VOLT")){
            return VOLT;
        }else if(unit.equalsIgnoreCase("PERCENT")){
            return PERCENT;
        }else return -1;
    }
    
    public String toString(){
        
        StringBuffer buffer = new StringBuffer();
        for (int channel = 0; channel < getNumChannels(); channel++) {
            buffer.append("Channel: "+channel+" Scaling: "+ getScaling(channel)+" Unit: "+getUnit(channel));
            buffer.append("\n");
        }
        return buffer.toString();
    }
    
    public void load(String filename){
        FileInputStream in = null;
        try{
            in = new FileInputStream(filename);
        }catch(java.io.FileNotFoundException fnfex){System.err.println(fnfex);
        };
        try{
            props.load(in);
            in.close();
            
            for (int channel = 0; channel < getNumChannels(); channel++) {
                
                int scaling = Integer.parseInt(props.getProperty("scaling_"+channel));
                String unit = props.getProperty("unit_"+channel);
                setScaling(channel,scaling,getUnit(unit));
            }
            
        }catch(java.io.IOException ioex){System.err.println(ioex);
        };
    }
    
    public void save(String filename){
        FileOutputStream out = null;
        try{
            out = new FileOutputStream(filename);
        }catch(java.io.FileNotFoundException fnfex){System.err.println(fnfex);
        };
        
        for (int channel = 0; channel < getNumChannels(); channel++) {
            props.put("scaling_"+channel, String.valueOf(getScaling(channel)));
            props.put("unit_"+channel,String.valueOf(getUnit(channel)));
        }
        
        try{
            props.store(out,"");
            out.close();
        }catch(java.io.IOException ioex){System.err.println(ioex);
        };
    }
    
    public static void main(String[] args) {
        ScalingConfiguration aScalingConfiguration = new ScalingConfiguration();
    }
}