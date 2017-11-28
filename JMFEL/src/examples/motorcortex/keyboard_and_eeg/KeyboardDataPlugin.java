/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package examples.motorcortex.keyboard_and_eeg;

import plugins.basic.BasicDataAccessor;

/**
 *
 * @author Administrator
 */

public class KeyboardDataPlugin extends BasicDataAccessor{
    
    public KeyboardDataPlugin() {
    }
    
    public void processValue(){
        super.processValue();
        setProcessedValue(getProcessedValue());
    }
    
    public void processInData() {
        super.processInData();
        getDoubleDataBufferContainer().getDataBuffer(0);
    }
}