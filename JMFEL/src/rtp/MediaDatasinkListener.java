/*
 * MediaDatasinkListener.java
 *
 * Created on 4. Juni 2007, 19:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package rtp;

import javax.media.datasink.DataSinkErrorEvent;
import javax.media.datasink.DataSinkEvent;
import javax.media.datasink.DataSinkListener;
import javax.media.datasink.EndOfStreamEvent;

/**
 *
 * @author Administrator
 */
public class MediaDatasinkListener implements DataSinkListener{
        
    private boolean fileDone = false;
    private boolean fileSuccess = true;
    private Object waitFileSync = new Object();

    public boolean isFileDone() {
        return fileDone;
    }

    public boolean isFileSuccess() {
        return fileSuccess;
    }

    public void setFileDone(boolean fileDone) {
        this.fileDone = fileDone;
    }

    public void setFileSuccess(boolean fileSuccess) {
        this.fileSuccess = fileSuccess;
    }

    /** Creates a new instance of MediaDatasinkListener */
    public MediaDatasinkListener() {
    }
    
    public void dataSinkUpdate(DataSinkEvent evt) {
        
        if (evt instanceof EndOfStreamEvent) {
            synchronized (waitFileSync) {
                fileDone = true;
                waitFileSync.notifyAll();
            }
        } else if (evt instanceof DataSinkErrorEvent) {
            synchronized (waitFileSync) {
                fileDone = true;
                fileSuccess = false;
                waitFileSync.notifyAll();
            }
        }
    }

    /**
     * Block until file writing is done.
     */
    public boolean waitForFileDone() {
        System.err.print("  ");
        synchronized (waitFileSync) {
            try {
                while (!fileDone) {
                    waitFileSync.wait(1000);
                    System.err.print(".");
                }
            } catch (Exception e) {}
        }
        System.err.println("");
        return fileSuccess;
    }
}
