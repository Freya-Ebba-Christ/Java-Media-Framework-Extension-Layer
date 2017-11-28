/*
 * RealNullAudioDevice.java
 *
 * Created on 2. November 2007, 17:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package custom_renderer;

import javax.sound.sampled.*;
import javax.sound.sampled.AudioFormat.Encoding;
import com.sun.media.*;
import com.sun.media.renderer.audio.device.AudioOutput;

/**
 *
 * @author Administrator
 */
public class RealNullAudioDevice implements AudioOutput{
    static Mixer mixer = null;
    static Object initSync = new Object();
    protected boolean paused = true;
    long pos = 0;
    boolean mute = false;
    float rate = 0;
    static boolean open = true;
    long lastPos = 0;
    long originPos = 0;
    long totalCount = 0;
    
    protected int bufSize;
    protected javax.media.format.AudioFormat format;
    
    
    public RealNullAudioDevice() {
    }
    
    
    public boolean initialize(javax.media.format.AudioFormat format, int bufSize) {
        open = true;
        this.format = format;
        this.bufSize = bufSize;
        return true;
    }
    
    public void dispose() {
    }
    
    
    public void finalize() throws Throwable {
        dispose();
    }
    
    public void pause() {
        paused = true;
    }
    
    
    public void resume() {
        paused = false;
    }
    
    public void drain() {
    }
    
    
    public void flush() {
    }
    
    
    public javax.media.format.AudioFormat getFormat() {
        return format;
    }
    
    public long getMediaNanoseconds() {
        
        if (pos < lastPos) {
            // Wraps around.
            totalCount += lastPos - originPos;
            originPos = pos;
        }
        
        lastPos = pos;
        
        return (long)(((totalCount + pos - originPos) * 1000 / format.getSampleRate()) * 1000000);
    }
    
    public void setGain(double g) {
    }
    
    
    public double getGain() {
        return 1.0;
    }
    
    
    public void setMute(boolean m) {
        mute = m;
    }
    
    
    public boolean getMute() {
        return mute;
    }
    
    
    public float setRate(float r) {
        rate = r;
        return 1;
    }
    
    
    public float getRate() {
        return rate;
    }
    
    
    public int bufferAvailable() {
        return 1;
    }
    
    
    public int write(byte data[], int off, int len) {
        return data.length;
    }
    
    
    public static boolean isOpen() {
        
        return open;
    }
}