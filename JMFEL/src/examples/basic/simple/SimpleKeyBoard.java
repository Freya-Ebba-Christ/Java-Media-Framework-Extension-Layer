/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

public class SimpleKeyBoard {

    public static void main(String[] args) {
        String file_separator = System.getProperty("file.separator");
        SignalDataSource keyBoardSignalDataSource = new SignalDataSource();
        DataSource dsKeyboardDataSource = null;
        ProcessorPlayer aPlayer;
        keyBoardSignalDataSource.setMediaLocator(new MediaLocator("keyboard://"));
        keyBoardSignalDataSource.init();
        dsKeyboardDataSource = keyBoardSignalDataSource.getDataSource();
        aPlayer = new ProcessorPlayer(dsKeyboardDataSource);
        aPlayer.setVisible(true);
        aPlayer.start();
    }
}