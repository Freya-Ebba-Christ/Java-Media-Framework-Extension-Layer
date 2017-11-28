/*
 * CustomCaptureDevice.java
 *
 * Created on 15. Oktober 2007, 12:11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.usbAmpB;

import media.protocol.gtec.GtecCaptureDevice;

/**
 *
 * @author Administrator
 */
public class CustomCaptureDevice extends GtecCaptureDevice{
    
    public CustomCaptureDevice(){
        super();
        setDevice(DEVICE_B);
    }
}