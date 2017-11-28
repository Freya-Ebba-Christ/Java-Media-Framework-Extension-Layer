/*
 * SimpleAudio.java
 *
 * This example opens an audiofile and displays it with a SimplePlayer
 */

package examples.basic.very_simple;

import datasource.SignalDataSource;
import javax.media.MediaLocator;
import javax.media.protocol.DataSource;
import simple_player.ProcessorPlayer;
import simple_player.SimplePlayer;

/**
 *
 * @author Administrator
 */

public class SimpleAudio {
    
    /** Creates a new instance of SimpleAudio */
    public SimpleAudio() {
    }
    
    public static void main(String [] args) {
        String file_separator = System.getProperty("file.separator");
        DataSource dsFile  = null;
        SignalDataSource aFileDataSource;
        SimplePlayer aPlayer;
        aFileDataSource = new SignalDataSource();
        aFileDataSource.setMediaLocator(new MediaLocator("file:\\"+System.getProperty("user.dir")+file_separator+"examples"+file_separator+"basic"+file_separator+"audio_and_video"+file_separator+"testaudio.wav"));
        aFileDataSource.init();
        dsFile = aFileDataSource.getDataSource();
        aPlayer = new SimplePlayer(dsFile);
        aPlayer.setVisible(true);
        aPlayer.start();
    }
}
