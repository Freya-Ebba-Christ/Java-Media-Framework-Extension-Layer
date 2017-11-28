/*
 * JinputTest.java
 *
 * Created on 10. Oktober 2007, 12:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package media.protocol.keyboard;

import java.util.concurrent.ArrayBlockingQueue;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier.Key;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

/**
 *
 * @author Administrator
 */
public class JinputTest {

    /** Creates a new instance of JinputTest */
    public JinputTest() {
    }

    public static void main(String[] args) {
        KeyboardPollThread producer = new KeyboardPollThread();
        producer.start();
    }
}

class KeyboardPollThread extends Thread {

    //private Buffer buffer;
    private int fifoSize = 10;
    private ArrayBlockingQueue fifo = new ArrayBlockingQueue(fifoSize);
    private Controller[] controllers;
    private Controller keyboard;
    private EventQueue queue;
    private float pressed = 0;
    private byte value = 0;
    private byte[] byteArray = new byte[fifoSize];

    public KeyboardPollThread() {

        controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        keyboard = null;
        for (int i = 0; i < controllers.length && keyboard == null; i++) {
            if (controllers[i].getType() == Controller.Type.KEYBOARD) {
                keyboard = controllers[i];
            }
        }
        if (keyboard == null) {
            System.out.println("Found no keyboard");
            System.exit(0);
        }
    }

    public void run() {
        while (true) {
            try {
                scan();
                fifo.put(new Byte(value));
                if (fifo.size() == fifoSize) {
                    for (int i = 0; i < fifoSize; i++) {
                        byteArray[i] = ((Byte) fifo.poll()).byteValue();
                        System.out.println(byteArray[i]);
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void scan() {
        try {
            keyboard.poll();
            queue = keyboard.getEventQueue();
            Event event = new Event();
            while (queue.getNextEvent(event)) {
                Component comp = event.getComponent();
                pressed = event.getValue();
                Key key = (Key) comp.getIdentifier();
                if (pressed == 1.0f) {
                    if (key == Key.RCONTROL) {
                        value = 1;

                    } else if (key == Key.LCONTROL) {
                        System.out.println(2);
                        value = 2;
                    }
                } else {
                    value = 0;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}