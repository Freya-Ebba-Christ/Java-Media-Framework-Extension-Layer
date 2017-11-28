/*
 * SimpleVideoStream.java
 *
 * This example opens the videostream datasource and displays it with a SimplePlayer
 */

package examples.basic.very_simple;

import datasource.LiveStreamDataSource;
import java.awt.Dimension;
import javax.media.Format;
import javax.media.MediaLocator;
import javax.media.format.VideoFormat;
import javax.media.protocol.DataSource;
import simple_player.SimplePlayer;

/**
 *
 * @author Administrator
 */
public class SimpleVideoStream {
    
    /** Creates a new instance of SimpleVideoStream */
    public SimpleVideoStream() {
    }
    
    public static void main(String [] args) {
        String file_separator = System.getProperty("file.separator");
        
        //This does almost exactly the same as the SimpleWebCam demo
        //The difference is, that this here we use the LiveStreamDataSource instead of the VideoDataSource 
        //The VideoDataSource automatically scans the system for an image capture device, e.g. a webcam whereas the LiveStreamDataSource does not do any of these things at all.
        DataSource dsVideo  = null;
        LiveStreamDataSource aLiveStreamDataSource;
        SimplePlayer aPlayer;
        aLiveStreamDataSource = new LiveStreamDataSource();

        aLiveStreamDataSource.setMediaLocator(new MediaLocator("videostream://"));
        aLiveStreamDataSource.init();
        dsVideo = aLiveStreamDataSource.getDataSource();
        aPlayer = new SimplePlayer(dsVideo);
        aPlayer.setVisible(true);
        aPlayer.start();
    }
}
