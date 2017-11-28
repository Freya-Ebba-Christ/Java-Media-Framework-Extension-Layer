/*
 * LiveStreamDataSource.java
 *
 * Created on 2. November 2007, 19:25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package datasource;

import java.awt.Dimension;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.control.FormatControl;
import javax.media.format.VideoFormat;
import javax.media.protocol.CaptureDevice;
import javax.media.protocol.DataSource;

/**
 *
 * @author Administrator
 */
public class LiveStreamDataSource{
    private int width  = 320;
    private int height = 240;
    private VideoFormat vf = new VideoFormat(VideoFormat.RGB, new Dimension(width, height), Format.NOT_SPECIFIED, null, 30.0f);
    private DataSource aVideoDataSource = null;
    private MediaLocator mediaLocator;
    
    public LiveStreamDataSource() {
    }
    
    public void setMediaLocator(MediaLocator mediaLocator) {
        this.mediaLocator = mediaLocator;
    }
    
    public void setVideoCaptureFormat(VideoFormat aVideoFormat){
        vf = aVideoFormat;
    }
    
    private DataSource createVideoDataSource() {
        DataSource ds;
        
        try {
            ds = Manager.createDataSource(mediaLocator);
            ds.connect();
            if (ds instanceof CaptureDevice) {
                setCaptureFormat((CaptureDevice)ds, vf);
            }
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
        return ds;
    }
    
    private void setCaptureFormat(CaptureDevice cdev, Format format) throws Exception{
        FormatControl [] fcs = cdev.getFormatControls();
        if (fcs.length < 1)
            return;
        FormatControl fc = fcs[0];
        Format [] formats = fc.getSupportedFormats();
        for (int i = 0; i < formats.length; i++) {
            if (formats[i].matches(format)) {
                format = formats[i].intersects(format);
                fc.setFormat(format);
                break;
            }
        }
    }
    
    public void init(){
        aVideoDataSource = createVideoDataSource();
    }
    
    public DataSource getDataSource(){
        return aVideoDataSource;
    }
}