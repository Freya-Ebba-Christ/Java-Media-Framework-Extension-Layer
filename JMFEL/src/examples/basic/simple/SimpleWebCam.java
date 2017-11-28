/*
 * SimpleWebCam.java
 *
 * This example opens the webcam at 640x 480 pixels and displays it with a ProcessorPlayer
 */

package examples.basic.simple;

import datasource.VideoDataSource;
import java.awt.Dimension;
import javax.media.Format;
import javax.media.format.VideoFormat;
import javax.media.protocol.DataSource;
import simple_player.ProcessorPlayer;

/**
 *
 * @author Administrator
 */
public class SimpleWebCam {
    
    /** Creates a new instance of SimpleWebCam */
    public SimpleWebCam() {
    }
    
    public static void main(String [] args) {
        String file_separator = System.getProperty("file.separator");
        
        //The default settings are 320x240 and 30fps. If this is all you want,you don't have to set a video format
        //This is almost exactly the same as in the very simple package. Whithout any plugins, the ProcessorPlayer is just a player
        
        VideoFormat vf = new VideoFormat(VideoFormat.RGB, new Dimension(640, 480), Format.NOT_SPECIFIED, null, 30.0f);
        DataSource dsVideo  = null;
        VideoDataSource aVideoDataSource;
        ProcessorPlayer aPlayer;
        aVideoDataSource = new VideoDataSource();
        aVideoDataSource.setVideoCaptureFormat(vf);
        
        try{
            aVideoDataSource.init();
        }catch(Exception e){System.out.println(e);};
        
        dsVideo = aVideoDataSource.getDataSource();
        aPlayer = new ProcessorPlayer(dsVideo);
        aPlayer.setVisible(true);
        aPlayer.start();
    }
}
