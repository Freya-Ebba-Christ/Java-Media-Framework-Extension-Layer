/*
 * BasicDataAccessorExample.java
 *
 * This example demonstrates how to use plugins
 */

package examples.basic.advanced;

import datasource.SignalDataSource;
import java.util.Vector;
import javax.media.MediaLocator;
import javax.media.protocol.DataSource;
import simple_player.ProcessorPlayer;

/**
 *
 * @author Administrator
 */
public class BasicDataAccessorExample {
    
    /** Creates a new instance of BasicDataAccessorExample */
    public BasicDataAccessorExample() {
    }
    
    //This example shows how to use a codec with a ProcessorPlayer
    public static void main(String [] args) {
        String file_separator = System.getProperty("file.separator");
        DataSource dsFile  = null;
        SignalDataSource aFileDataSource;
        Vector plugin = new Vector();
        DataAccessorPlugin aDataAccessorPlugin = new DataAccessorPlugin();
        aDataAccessorPlugin.setNumChannels(1);
        plugin.add(aDataAccessorPlugin);
        ProcessorPlayer aPlayer;
        aFileDataSource = new SignalDataSource();
        aFileDataSource.setMediaLocator(new MediaLocator("file:\\"+System.getProperty("user.dir")+file_separator+"examples"+file_separator+"basic"+file_separator+"audio_and_video"+file_separator+"testaudio.wav"));
        aFileDataSource.init();
        dsFile = aFileDataSource.getDataSource();
        aPlayer = new ProcessorPlayer(dsFile,plugin);
        aPlayer.setVisible(true);
        aPlayer.start();
    }
}
