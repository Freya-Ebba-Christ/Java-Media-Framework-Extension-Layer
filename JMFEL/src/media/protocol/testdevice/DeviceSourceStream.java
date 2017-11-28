/*
 * DeviceSourceStream.java
 *
 * Created on 5. Oktober 2007, 13:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.testdevice;

import javax.media.protocol.PushBufferStream;
import media.protocol.generic.GenericCustomCaptureDevice;
import media.protocol.generic.GenericDeviceSourceStream;

/**
 *
 * @author Urkman_2
 */
public class DeviceSourceStream extends GenericDeviceSourceStream{
    
    public DeviceSourceStream(GenericCustomCaptureDevice aCaptureDevice) {
        super(aCaptureDevice);
    }
}
