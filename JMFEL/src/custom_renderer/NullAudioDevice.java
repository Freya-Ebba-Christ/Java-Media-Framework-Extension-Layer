/*
 * NullAudioDevice.java
 *
 * This more like a very dirty little trick...
 * We are actually still using the JavaSoundOutput, but we are shut it mute for once and for all...
 */

package custom_renderer;

import com.sun.media.renderer.audio.device.JavaSoundOutput;
import com.sun.media.rtp.TrueRandom;

/**
 *
 * @author Administrator
 */
public class NullAudioDevice extends JavaSoundOutput{
    
    /** Creates a new instance of NullAudioDevice */
    public NullAudioDevice() {
    }

    public void setMute(boolean m) {
        super.setMute(true);
    }

    public boolean getMute() {
        return true;
    }
}
