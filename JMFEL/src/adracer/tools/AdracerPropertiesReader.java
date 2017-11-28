/*
 * AdracerPropertiesReader.java
 *
 * Created on 3. September 2007, 15:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package adracer.tools;

import java.util.Properties;
import media.protocol.gtec.CommonGround;
import media.protocol.gtec.CommonReference;
import media.protocol.gtec.Configuration;
import media.protocol.gtec.ConfigurationTableModel;
import media.protocol.gtec.ScalingConfiguration;
import utilities.PropertiesReader;

/**
 *
 * @author Administrator
 */

public class AdracerPropertiesReader extends PropertiesReader{
    private Configuration settings;
    private String configLocation;
    private ScalingConfiguration scalingConfiguration;
    private String file_separator = System.getProperty("file.separator");
    
    /** Creates a new instance of AdracerPropertiesReader */
    public AdracerPropertiesReader() {
        Properties props = readProperties(System.getProperty("user.dir") + file_separator + "adracer" + file_separator + "tools" + file_separator + "adracer_properties_reader.ini");
        configLocation = props.getProperty("LOCATION");
    }
    
    public ScalingConfiguration getScalingConfiguration() {
        return scalingConfiguration;
    }
    
    public Properties getEyeTrackerProperties(){
        return readProperties(System.getProperty("user.dir") + file_separator + "media" + file_separator + "protocol" + file_separator + "eye" + file_separator + "config.ini");
    }
    
    public Properties getDataBaseProperties(){
        return readProperties(System.getProperty("user.dir") + file_separator + "datasink" + file_separator + "database" + file_separator + "location" + file_separator + "database_location.ini");
    }
    
    public Properties getConfig(){
        return readProperties(configLocation);
    }
    
    public Configuration getAmplifierConfiguration(String name){
        settings = new Configuration();
        settings.setModel(new ConfigurationTableModel());
        settings.setCommonGround(new CommonGround());
        settings.setCommonReference(new CommonReference());
        String file_separator = System.getProperty("file.separator");
        String settingsFile = System.getProperty("user.dir") + file_separator + "media" + file_separator + "protocol" + file_separator + name + file_separator + "configuration.ini";
        settings.load(settingsFile);
        return settings;
    }
    
    public ScalingConfiguration getScalingConfiguration(String name){
        scalingConfiguration = new ScalingConfiguration();
        String settingsFile = System.getProperty("user.dir") + file_separator + "media" + file_separator + "protocol" + file_separator + name + file_separator + "scaling.ini";
        scalingConfiguration.load(settingsFile);
        return scalingConfiguration;
    }
}