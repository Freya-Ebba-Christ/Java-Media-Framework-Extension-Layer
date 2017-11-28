/*
 * Merging_1.java
 *
 * This example merges two DataSources into one merging datasource, which is then played by a ProcessorPlayer.
 */

package examples.basic.advanced;

import datasource.DataSourceMerger;
import datasource.LiveStreamDataSource;
import datasource.SignalDataSource;
import datasource.VideoDataSource;
import java.util.Vector;
import javax.media.MediaLocator;
import javax.media.protocol.DataSource;
import simple_player.ProcessorPlayer;

/**
 *
 * @author Administrator
 */
public class Merging {
    
    /** Creates a new instance of Merging_1 */
    public Merging() {
    }
    
    public static void main(String [] args) {
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
        
        mergingDataSourcePlayer.setDataSource(DataSourceMerger.mergeDataSources(datasources));
        mergingDataSourcePlayer.setVisible(true);
        mergingDataSourcePlayer.setTitle("Two merged streams displayed at once");
        mergingDataSourcePlayer.start();
    }
}
