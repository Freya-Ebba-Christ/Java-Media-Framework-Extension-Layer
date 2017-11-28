/*
 * DeviceSourceStream.java
 *
 * Created on 14. Dezember 2007, 18:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.stopWatch;

import media.protocol.generic.GenericCustomCaptureDevice;
import media.protocol.generic.GenericDeviceSourceStream;

/**
 *
 * @author Administrator
 */
public class DeviceSourceStream extends GenericDeviceSourceStream{
    
    /** Creates a new instance of DeviceSourceStream */
    public DeviceSourceStream(GenericCustomCaptureDevice aCaptureDevice) {
        super(aCaptureDevice);
    }
}