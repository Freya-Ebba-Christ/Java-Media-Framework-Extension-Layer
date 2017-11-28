/*
 * MasterSlave.java
 *
 * This example clones a data source twice and plays them indvidually with a ProcessorPlayer
 * The players - the slave players, which play the clones are controlled by the player - the master player, which plays the original datasource.
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
public class MasterSlave {
    
    /** Creates a new instance of MasterSlave */
    public MasterSlave() {
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
        
        aPlayer_dsClonable = new ProcessorPlayer();
        aPlayer_dsCloneA = new ProcessorPlayer();
        aPlayer_dsCloneB = new ProcessorPlayer();
        aPlayer_dsCloneA.setControlPanelComponentVisible(false);
        aPlayer_dsCloneB.setControlPanelComponentVisible(false);
        
        aPlayer_dsClonable.setDataSource(dsClonable);
        aPlayer_dsCloneA.setDataSource(dsCloneA);
        aPlayer_dsCloneB.setDataSource(dsCloneB);
        
        //The Player_dsClonable master player controls the two slaves 
        try{
            aPlayer_dsCloneA.getProcessor().addController(aPlayer_dsCloneB.getProcessor());
            aPlayer_dsClonable.getProcessor().addController(aPlayer_dsCloneA.getProcessor());
        }catch(Exception e){System.out.println(e);};
        
        aPlayer_dsClonable.setVisible(true);
        aPlayer_dsCloneA.setVisible(true);
        aPlayer_dsCloneB.setVisible(true);
        
        aPlayer_dsClonable.setTitle("MASTER");
        aPlayer_dsCloneA.setTitle("SLAVE A");
        aPlayer_dsCloneB.setTitle("SLAVE B");
        
        aPlayer_dsClonable.start();
        aPlayer_dsCloneA.start();
        aPlayer_dsCloneB.start();
    }
}