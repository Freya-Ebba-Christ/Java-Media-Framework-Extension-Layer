/*
 * CaptureApplication.java
 *
 * Created on 14. Oktober 2007, 05:25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package capture_controller;

import java.io.File;

/**
 *
 * @author Urkman_2
 */
public interface CaptureApplication {
    public void start();
    public void pause();
    public void stop();
    public void open(File file);
}
