/*
 * Cloning.java
 * This example clones a data source twice and displays the clones as well as the original source with an individual player.
 * 
 */

package examples.basic.advanced;

import datasource.CloneableDataSource;
import datasource.SignalDataSource;
import javax.media.MediaLocator;
import javax.media.protocol.DataSource;
import simple_player.ProcessorPlayer;

/**
 *
 * @author Administrator
 */
public class Cloning {
    
    /** Creates a new instance of Cloning */
    public Cloning() {
    }
    
    public static void main(String [] args) {
        String file_separator = System.getProperty("file.separator");
        DataSource dsFile  = null;
        DataSource dsClonable  = null;
        DataSource dsCloneA  = null;
        DataSource dsCloneB  = null;
        CloneableDataSource aCloneableDataSource = new CloneableDataSource();
        
        SignalDataSource aFileDataSource;
        ProcessorPlayer aPlayer_dsClonable;
        ProcessorPlayer aPlayer_dsCloneA;
        ProcessorPlayer aPlayer_dsCloneB;
        
        aFileDataSource = new SignalDataSource();
        aFileDataSource.setMediaLocator(new MediaLocator("file:\\"+System.getProperty("user.dir")+file_separator+"examples"+file_separator+"basic"+file_separator+"audio_and_video"+file_separator+"testvideo.avi"));
        aFileDataSource.init();
        dsFile = aFileDataSource.getDataSource();
        aCloneableDataSource.setDataSource(dsFile);
        dsClonable = aCloneableDataSource.getCloneableDataSource();
        dsCloneA = aCloneableDataSource.getClone();
        dsCloneB = aCloneableDataSource.getClone();
        
        //////////////////////////////////////////////////////////////////////////////////////////////////
        // IMPORTANT!!!! ONCE YOU HAVE ARE CLONING DATASOURCES YOU CANNOT USE THE ORIGINAL DATASOURCE!!!!/
        // YOU HAVE TO USE THE CLONABLE AND ITS CLONES INSTEAD                                           /
        // SEE ALSO THE ORIGNAL JMF DOKUMENTATION ON THIS TOPIC                                          /
        //////////////////////////////////////////////////////////////////////////////////////////////////
        
        aPlayer_dsClonable = new ProcessorPlayer(dsClonable);
        aPlayer_dsCloneA = new ProcessorPlayer(dsCloneA);
        aPlayer_dsCloneB = new ProcessorPlayer(dsCloneB);
        
        aPlayer_dsClonable.setVisible(true);
        aPlayer_dsClonable.start();
        
        aPlayer_dsCloneA.setVisible(true);
        aPlayer_dsCloneA.start();
        
        aPlayer_dsCloneB.setVisible(true);
        aPlayer_dsCloneB.start();
        
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        // THE PLAYERS ARE NOT SYNCHRONIZED!!!                                                           //
        ///////////////////////////////////////////////////////////////////////////////////////////////////
    }
}