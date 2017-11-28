/*
 * DeviceSourceStream.java
 *
 * Created on 31. Oktober 2007, 18:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.pong;

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