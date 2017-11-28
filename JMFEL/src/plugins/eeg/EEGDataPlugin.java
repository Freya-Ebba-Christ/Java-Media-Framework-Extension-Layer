/*
 * EEGDataPlugin.java
 *
 * Created on 4. September 2007, 17:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package plugins.eeg;

import java.util.Vector;
import plugins.basic.BasicDataAccessor;
import utilities.DoubleDataBuffer;

/**
 *
 * @author Administrator
 */
public class EEGDataPlugin extends BasicDataAccessor{
    
    private Vector<DoubleDataBuffer> buffers = new Vector();
    
    public EEGDataPlugin() {
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