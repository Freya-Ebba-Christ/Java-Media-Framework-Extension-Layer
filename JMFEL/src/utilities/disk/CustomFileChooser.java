/*
 * CustomFileChooser.java
 *
 * Created on 18. Juni 2007, 13:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilities.disk;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author Administrator
 */
public class CustomFileChooser {
    private FileChooserDialogGUI aDialogGUI;
    private boolean visible = false;
    
    /** Creates a new instance of CustomFileChooser */
    public CustomFileChooser() {
        aDialogGUI = new FileChooserDialogGUI();
    }
    
    public void hideGUI(){
        visible = false;
        aDialogGUI.setVisible(false);
    }
    
    public boolean isVisible(){
        return aDialogGUI.isVisible();
    }
    
    public void showGUI(){
        if(!visible){
            aDialogGUI.setCustomFileChooser(this);
            aDialogGUI.setSize(428, 620);
            aDialogGUI.setLocation(500, 500);
            aDialogGUI.center();
            visible = true;
            aDialogGUI.setVisible(true);
        }
    }
    
    public static void main(String [] args) {
        CustomFileChooser customFileChooser = new CustomFileChooser();
        customFileChooser.showGUI();
    }
}