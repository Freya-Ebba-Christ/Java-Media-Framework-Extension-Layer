/*
 * DataAccessorPlugin.java
 *
 * This plugin shows how to get access to audio data
 */

package examples.basic.advanced;

import plugins.basic.BasicDataAccessor;

/**
 *
 * @author olaf christ
 */

public class DataAccessorPlugin extends BasicDataAccessor{
    
    //this is smallest example of a codec/plugin 
    public DataAccessorPlugin() {
    }

    public void processInData() {
        //assuming 16 it values
        for (int i = 0; i < getInLength(); i+=2) {
            //read and write the value...
            setValue(i,getValue(i));
        }
    }
}
