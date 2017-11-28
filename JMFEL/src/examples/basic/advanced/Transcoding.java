/*
 * Transcoding.java
 *
 * This example demonstrates how to performs media transcoding
 */

package examples.basic.advanced;

import datasource.MediaTranscoder;
import datasource.SignalDataSource;
import javax.media.MediaLocator;
import javax.media.format.AudioFormat;
import javax.media.format.VideoFormat;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.DataSource;
import simple_player.ProcessorPlayer;

/**
 *
 * @author Administrator
 */
public class Transcoding {
    

    public Transcoding() {
    }
    
    
    //simple example on how to use the MediaTranscoder class
    public static void main(String [] args) {
        String file_separator = System.getProperty("file.separator");
        DataSource dsFile  = null;
        DataSource dsTranscoder = null;
        SignalDataSource aFileDataSource;
        ProcessorPlayer aPlayer;
        MediaTranscoder aMediaTranscoder = new MediaTranscoder();
        aMediaTranscoder.setContentDescriptor(ContentDescriptor.RAW);
        aMediaTranscoder.setTranscodeAudioFormatType(AudioFormat.MPEG);
        aMediaTranscoder.setTranscodeVideoFormatType(VideoFormat.MJPG);
        aFileDataSource = new SignalDataSource();
        aFileDataSource.setMediaLocator(new MediaLocator("file:\\"+System.getProperty("user.dir")+file_separator+"examples"+file_separator+"basic"+file_separator+"audio_and_video"+file_separator+"testvideo.avi"));
        aFileDataSource.init();
        dsFile = aFileDataSource.getDataSource();
        aMediaTranscoder.setDataSource(dsFile);
        dsTranscoder = aMediaTranscoder.getDataSource();
        aMediaTranscoder.start();
        aPlayer = new ProcessorPlayer(dsTranscoder);
        aPlayer.setVisible(true);
        aPlayer.start();
    }
}