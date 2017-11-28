/*
 * EyetrackerFrame.java
 *
 * Created on 20. August 2007, 14:25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilities.graphics.passive_rendering;

import java.awt.BorderLayout;
import java.util.Properties;
import javax.swing.JFrame;
import utilities.PropertiesReader;
import utilities.PropertiesWriter;

/**
 *
 * @author Administrator
 */
public class ApplicationContainer extends JFrame{
    private Surface visualComponent;
    private PropertiesWriter propertiesWriter = new PropertiesWriter();
    private PropertiesReader propertiesReader = new PropertiesReader();
    private boolean restoreLocationEnabled = false;
    private String locationINIPath = "";
    
    public ApplicationContainer() {
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
    }
    
    public String getLocationINIPath() {
        return locationINIPath;
    }

    public boolean isRestoreLocationEnabled() {
        return restoreLocationEnabled;
    }

    public void setRestoreLocationEnabled(boolean restoreLocationEnabled) {
        this.restoreLocationEnabled = restoreLocationEnabled;
    }
    
    public void setLocationINIPath(String locationINIPath) {
        this.locationINIPath = locationINIPath;
    }
    
    public Surface getVisualComponent() {
        return visualComponent;
    }
    
    public void setVisualComponent(Surface visualComponent) {
        this.visualComponent = visualComponent;
    }
    
    public void init(){
        add(getVisualComponent(),BorderLayout.CENTER);
        pack();
        if(isRestoreLocationEnabled()){
            restoreLocation();
        }
    }
    
    private void storeLocation(int x, int y){
        Properties locationProperties = new Properties();
        locationProperties.setProperty("X",String.valueOf(x));
        locationProperties.setProperty("Y",String.valueOf(y));
        propertiesWriter.writeProperties(getLocationINIPath(),locationProperties);
    }
    
    private void restoreLocation(){
        Properties locationProperties;
        locationProperties = propertiesReader.readProperties(getLocationINIPath());
        setLocation(Integer.parseInt(locationProperties.getProperty("X")),Integer.parseInt(locationProperties.getProperty("Y")));
    }
    
    public void setVisible(boolean b) {
        if(isRestoreLocationEnabled()&&isVisible()&&!b){
            storeLocation((int)getLocationOnScreen().getX(),(int)getLocationOnScreen().getY());
        }
        super.setVisible(b);
    }
}