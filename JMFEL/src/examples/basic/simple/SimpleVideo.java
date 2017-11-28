/*
 * SimpleVideo.java
 *
 * This example opens a videofile and displays it with a ProcessorPlayer
 */

package examples.basic.simple;

import datasource.SignalDataSource;
import javax.media.MediaLocator;
import javax.media.protocol.DataSource;
import simple_player.ProcessorPlayer;

/**
 *
 * @author Administrator
 */

public class SimpleVideo {
    
    /** Creates a new instance of SimpleVideo */
    public SimpleVideo() {
    }
    
    //This is almost exactly the same as in the very simple package. Whithout any plugins, the ProcessorPlayer is just a player
    public static void main(String [] args) {
        String file_separator = System.getProperty("file.separator");
        DataSource dsFile  = null;
        SignalDataSource aFileDataSource;
        ProcessorPlayer aPlayer;
        aFileDataSource = new SignalDataSource();
        aFileDataSource.setMediaLocator(new MediaLocator("file:\\"+System.getProperty("user.dir")+file_separator+"examples"+file_separator+"basic"+file_separator+"audio_and_video"+file_separator+"testvideo.avi"));
        aFileDataSource.init();
        dsFile = aFileDataSource.getDataSource();
        aPlayer = new ProcessorPlayer(dsFile);
        aPlayer.setVisible(true);
        aPlayer.start();
    }
}