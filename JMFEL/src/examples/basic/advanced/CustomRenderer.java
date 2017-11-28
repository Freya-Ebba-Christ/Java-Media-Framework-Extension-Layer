/*
 * This example merges two video data sources and displays both with a single ProcessorPlayer.
 * It also uses assigns a custom renderer to each stream.
 * The same process will work with mixed streams, e.g. a video with an audio track.
 */
package examples.basic.advanced;

import custom_renderer.J3DRenderer;
import datasource.DataSourceMerger;
import datasource.LiveStreamDataSource;
import datasource.VideoDataSource;
import java.util.Vector;
import javax.media.MediaLocator;
import javax.media.protocol.DataSource;
import javax.media.renderer.VideoRenderer;
import simple_player.ProcessorPlayer;

/**
 *
 * @author Administrator
 */
public class CustomRenderer {

    public static void main(String[] args) {
        String file_separator = System.getProperty("file.separator");
        
        DataSource dsLiveStream_1  = null;
        DataSource dsLiveStream_2  = null;
        LiveStreamDataSource aLiveStreamDataSource_1;
        VideoDataSource aLiveStreamDataSource_2;
         
        aLiveStreamDataSource_1 = new LiveStreamDataSource();
        aLiveStreamDataSource_2 = new VideoDataSource();
        
        ProcessorPlayer mergingDataSourcePlayer;
        
        aLiveStreamDataSource_1.setMediaLocator(new MediaLocator("videostream://"));
        aLiveStreamDataSource_1.init();
        try{
            aLiveStreamDataSource_2.init();
        }catch(Exception e){System.out.println(e);};
        
        dsLiveStream_1 = aLiveStreamDataSource_1.getDataSource();
        dsLiveStream_2 = aLiveStreamDataSource_2.getDataSource();
        
        mergingDataSourcePlayer = new ProcessorPlayer();
        
        Vector datasources = new Vector();
        
        datasources.add(dsLiveStream_1);
        datasources.add(dsLiveStream_2);
        mergingDataSourcePlayer.addVideoRenderers(new J3DRenderer());
        mergingDataSourcePlayer.addVideoRenderers(new J3DRenderer());
        System.out.println(mergingDataSourcePlayer.getVideoRenderers().size());
        mergingDataSourcePlayer.setDataSource(DataSourceMerger.mergeDataSources(datasources));
        mergingDataSourcePlayer.setVisible(true);
        mergingDataSourcePlayer.setTitle("Two merged streams displayed at once with an individual renderer per stream");
        mergingDataSourcePlayer.start();
    }
}
