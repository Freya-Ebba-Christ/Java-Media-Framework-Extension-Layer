/*
 * VideoPlayerApplication.java
 *
 * Created on 30. August 2007, 15:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package application_container;

import datasource.LiveStreamDataSource;
import datasource.VideoDataSource;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Vector;
import javax.media.MediaLocator;
import javax.swing.JFrame;
import plugins.video.VideoCounter;
import simple_player.ProcessorPlayer;

/**
 *
 * @author Administrator
 */
public class VideoPlayerApplication{
    
    private ProcessorPlayer videoPlayer = null;
    
    /** Creates a new instance of VideoPlayerApplication */
    public VideoPlayerApplication() {
    }
    
    public ProcessorPlayer getVideoPlayer() {
        return videoPlayer;
    }
    
    public void setVideoPlayer(ProcessorPlayer videoPlayer) {
        this.videoPlayer = videoPlayer;
    }
    
    public void locateTopRight(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        videoPlayer.setLocation((int)dim.getWidth()-videoPlayer.getWidth(),0);
    }
    
    public void init(){
        boolean success = true;
        VideoDataSource videoDataSource = null;
        LiveStreamDataSource videoUnavailableDataSource = null;
        
        try{
        videoDataSource = new VideoDataSource();
        videoDataSource.init();
        }catch(Exception e){System.out.println(e);};

        if(videoDataSource.getDataSource()==null){
            success = false;
        }
        
        //if no webcam was found, display a stupid live stream...
        if(!success){
            videoUnavailableDataSource = new LiveStreamDataSource();
            videoUnavailableDataSource.setMediaLocator(new MediaLocator("videostream://"));
            videoUnavailableDataSource.init();
        }
        
        videoPlayer = new ProcessorPlayer();
        if(success){
            videoPlayer.setTitle("Webcam");
        }else {
            videoPlayer.setTitle("No webcam connected");
        }
        Vector videoCodec = new Vector();
        videoCodec.add(new VideoCounter());
        if(success){
            videoPlayer.setDataSource(videoDataSource.getDataSource(),videoCodec);
        }else {
            videoPlayer.setDataSource(videoUnavailableDataSource.getDataSource(),videoCodec);
        }
        videoPlayer.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        videoPlayer.setVisible(false);
        locateTopRight();
        videoPlayer.start();
    }
    
    public static void main(String[] args) {
        VideoPlayerApplication aVideoPlayerApplication = new VideoPlayerApplication();
        aVideoPlayerApplication.init();
        aVideoPlayerApplication.getVideoPlayer().setVisible(true);
    }
}
