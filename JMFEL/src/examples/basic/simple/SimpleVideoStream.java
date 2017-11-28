/*
 * SimpleVideoStream.java
 *
 * This example opens the videostream datasource and displays it with a ProcessorPlayer
 */

package examples.basic.simple;

import datasource.LiveStreamDataSource;
import javax.media.MediaLocator;
import javax.media.protocol.DataSource;
import simple_player.ProcessorPlayer;

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
        
        //This is almost exactly the same as in the very simple package. Whithout any plugins, the ProcessorPlayer is just a player
        //The difference is, that this here we use the LiveStreamDataSource instead of the VideoDataSource
        //The VideoDataSource automatically scans the system for an image capture device, e.g. a webcam whereas the LiveStreamDataSource does not do any of these things at all.
        DataSource dsVideo  = null;
        LiveStreamDataSource aLiveStreamDataSource;
        ProcessorPlayer aPlayer;
        aLiveStreamDataSource = new LiveStreamDataSource();

        aLiveStreamDataSource.setMediaLocator(new MediaLocator("videostream://"));
        aLiveStreamDataSource.init();
        dsVideo = aLiveStreamDataSource.getDataSource();
        aPlayer = new ProcessorPlayer(dsVideo);
        aPlayer.setVisible(true);
        aPlayer.start();
    }
}
