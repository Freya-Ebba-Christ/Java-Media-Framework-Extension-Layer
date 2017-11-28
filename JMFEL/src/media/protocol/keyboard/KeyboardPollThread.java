/*
 * This thread uses jinput to access the keyboard 
 *
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
public class KeyboardPollThread extends Thread {

    //private Buffer buffer;
    private int fifoSize = 50;
    private ArrayBlockingQueue fifo;
    private Controller[] controllers;
    private Controller keyboard;
    private EventQueue queue;
    private float pressed = 0;
    private short value = 0;
    private short[] shortArray;

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

    public void init(int fifoSize) {
        shortArray = new short[fifoSize];
        fifo = new ArrayBlockingQueue(fifoSize);
    }

    public ArrayBlockingQueue getFifo() {
        return fifo;
    }

    public void run() {
        while (true) {
            try {
                scan();
                fifo.put(new Short(value));
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