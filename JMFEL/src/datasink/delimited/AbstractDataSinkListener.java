/*
 * AbstractDataSinkListener.java
 *
 * Created on 16. April 2007, 12:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package datasink.delimited;

import javax.media.datasink.DataSinkEvent;
import javax.media.datasink.DataSinkListener;
import javax.media.datasink.EndOfStreamEvent;

/**
 *
 * @author christ
 */
public abstract class AbstractDataSinkListener implements DataSinkListener{
    
    /** Creates a new instance of AbstractDataSinkListener */
    public AbstractDataSinkListener() {
    }
    
    public void dataSinkUpdate(DataSinkEvent evt) {
        
        if (evt instanceof EndOfStreamEvent) {
            System.err.println("All done!");
            evt.getSourceDataSink().close();
        }
    }
}
