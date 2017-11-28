/*
 * Splitting.java
 *
 * This example loads a video with sound and splits it into its media tracks - the audio and video track,
 * which are then played individually by a ProcessorPlayer.
 */

package examples.basic.advanced;

import datasource.SignalDataSource;
import datasource.SplitDataSource;
import java.util.Vector;
import javax.media.MediaLocator;
import javax.media.protocol.DataSource;
import simple_player.ProcessorPlayer;
import simple_player.SimpleProcessor;

/**
 *
 * @author Administrator
 */
public class Splitting {
    
    public Splitting() {
    }
    
    public static void main(String [] args) {
        String file_separator = System.getProperty("file.separator");
        DataSource dsFile  = null;
        SignalDataSource aFileDataSource;
        SimpleProcessor aProcessor;
        ProcessorPlayer aPlayer_1;
        ProcessorPlayer aPlayer_2;
        
        // load a video file that also contains an audio and a video track.
        aFileDataSource = new SignalDataSource();
        aFileDataSource.setMediaLocator(new MediaLocator("file:\\"+System.getProperty("user.dir")+file_separator+"examples"+file_separator+"basic"+file_separator+"audio_and_video"+file_separator+"testvideo.avi"));
        aFileDataSource.init();
        
        dsFile = aFileDataSource.getDataSource();
        //no codecs needed here
        aProcessor = new SimpleProcessor(dsFile, new Vector());
        //split the merged data streams into two media tracks
        SplitDataSource aSplitDataSource_1 = new SplitDataSource(aProcessor.getProcessor(),0);
        SplitDataSource aSplitDataSource_2 = new SplitDataSource(aProcessor.getProcessor(),1);
        
        //initialize a player for each media track
        aPlayer_1 = new ProcessorPlayer(aSplitDataSource_1);
        aPlayer_2 = new ProcessorPlayer(aSplitDataSource_2);
        
        //make them visible
        aPlayer_1.setVisible(true);
        aPlayer_2.setVisible(true);
        
        //label them
        aPlayer_1.setTitle("Video");
        aPlayer_2.setTitle("Audio");
        
        //and start them
        aPlayer_1.start();
        aPlayer_2.start();
    }
}