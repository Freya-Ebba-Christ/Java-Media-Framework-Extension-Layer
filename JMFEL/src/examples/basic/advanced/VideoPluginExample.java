/*
 * VideoPluginExample.java
 *
 * This example opens a video file and uses a plugin, which draws a date and the current time on into video frames
 */

package examples.basic.advanced;

import datasource.SignalDataSource;
import java.util.Vector;
import javax.media.MediaLocator;
import javax.media.protocol.DataSource;
import plugins.video.VideoCounter;
import simple_player.ProcessorPlayer;

/**
 *
 * @author Administrator
 */
public class VideoPluginExample {
    
    /** Creates a new instance of VideoPluginExample */
    public VideoPluginExample() {
    }
    
    public static void main(String [] args) {
        String file_separator = System.getProperty("file.separator");
        DataSource dsFile  = null;
        SignalDataSource aFileDataSource;
        ProcessorPlayer aPlayer;
        //This plugin draws a date and the current time on into video frames
        VideoCounter aVideoCounter = new VideoCounter();
        Vector plugins = new Vector();
        plugins.add(aVideoCounter);
        aFileDataSource = new SignalDataSource();
        aFileDataSource.setMediaLocator(new MediaLocator("file:\\"+System.getProperty("user.dir")+file_separator+"examples"+file_separator+"basic"+file_separator+"audio_and_video"+file_separator+"testvideo.avi"));
        aFileDataSource.init();
        dsFile = aFileDataSource.getDataSource();
        aPlayer = new ProcessorPlayer();
        aPlayer.setUseAudioCodecs(false);
        aPlayer.setUseVideoCodecs(true);
        aPlayer.setDataSource(dsFile,plugins);
        aPlayer.start();
        aPlayer.setVisible(true);
    }
}