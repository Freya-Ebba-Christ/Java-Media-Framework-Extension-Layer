/*
 * CustomCaptureDevice.java
 *
 * Created on 10. Oktober 2007, 17:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package media.protocol.keyboard;

import java.nio.ByteOrder;
import java.util.concurrent.ArrayBlockingQueue;
import javax.media.format.AudioFormat;
import media.protocol.generic.GenericCustomCaptureDevice;
import utilities.ByteUtility;

/**
 *
 * @author Administrator
 */
public class CustomCaptureDevice extends GenericCustomCaptureDevice {

    private byte[] byteBufferArray;
    private ArrayBlockingQueue fifo;
    private KeyboardPollThread keyboardPollThread;
    private final int fifoSize = 20;
    private ByteUtility byteUtility = new ByteUtility();

    /** Creates a new instance of CustomCaptureDevice */
    public CustomCaptureDevice() {
    }

    public void init() {
        try {
            byteUtility.setEndianess(ByteOrder.LITTLE_ENDIAN);
            setInitialized(false);
            setSamplerate(1000);
            setNumChannels(1);
            setBitsPerSample(16);
            setFormat(new AudioFormat(AudioFormat.LINEAR, getSamplerate() * getNumChannels(), getBitsPerSample(), 1));
            setMaxDataLength(2000);
            setTransferBuffer(new byte[fifoSize]);
            keyboardPollThread = new KeyboardPollThread();
            keyboardPollThread.init(fifoSize);
            keyboardPollThread.start();

        } catch (Exception e) {
            System.err.println(e);
        }
        setInitialized(true);
    }

    public byte[] getAvailableData() {
        setTransferBuffer(new byte[0]);
        if (keyboardPollThread.getFifo().size() == fifoSize) {
            setTransferBuffer(new byte[fifoSize*2]);
            for (int i = 0; i < fifoSize*2; i+=2) {
                short keyValue = ((Short) keyboardPollThread.getFifo().poll()).shortValue();
                byte[] byteValue = byteUtility.splitValue(keyValue);
                getTransferBuffer()[i] = byteValue[0];
                getTransferBuffer()[i + 1] = byteValue[1];
            }
        }
        return getTransferBuffer();
    }
}