/*
 * EEGMotorCortexLaplacePlugin.java
 *
 * Created on 28. September 2007, 22:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package plugins.eeg;

import eeg.utilities.ElectrodeAssignment;
import java.util.Iterator;
import org.fastica.math.Matrix;
import plugins.codecs.BasicAudioCodec;

/**
 *
 * @author Administrator
 */
public class EEGMotorCortexLaplacePlugin extends BasicAudioCodec{
    private ElectrodeAssignment electrodeToChannelAssignment;
    private double averageleftMotorCortex = 0.0;
    private double averageRightMotorCortex = 0.0;
    
    public EEGMotorCortexLaplacePlugin() {
    }
    
    public ElectrodeAssignment getElectrodeToChannelAssignment() {
        return electrodeToChannelAssignment;
    }
    
    public void setElectrodeToChannelAssignment(ElectrodeAssignment electrodeToChannelAssignment) {
        this.electrodeToChannelAssignment = electrodeToChannelAssignment;
    }
    
    public void processOutData() {
        setTransferBufferSize(getInLength()/getNumChannels()/2);
        
        //compute second derivation
        double[][] matrix = arrayToMatrix(getInData());
        for (int p = 0; p < getTransferBufferSize(); p++) {
            //subtract signals related to foot movement from adjacent electrodes
            averageleftMotorCortex = 0.0;
            //subtract signals related to foot movement from adjacent electrodes
            averageleftMotorCortex = averageleftMotorCortex + matrix[electrodeToChannelAssignment.getCp1()][p]  - matrix[electrodeToChannelAssignment.getCz()][p];
            averageleftMotorCortex = averageleftMotorCortex + matrix[electrodeToChannelAssignment.getCp3()][p];
            averageleftMotorCortex = averageleftMotorCortex + matrix[electrodeToChannelAssignment.getCp5()][p];
            //subtract signals related to foot movement from adjacent electrodes
            averageleftMotorCortex = averageleftMotorCortex + matrix[electrodeToChannelAssignment.getFc1()][p] - matrix[electrodeToChannelAssignment.getCz()][p];
            averageleftMotorCortex = averageleftMotorCortex + matrix[electrodeToChannelAssignment.getFc3()][p];
            averageleftMotorCortex = averageleftMotorCortex + matrix[electrodeToChannelAssignment.getFc5()][p];
            averageleftMotorCortex = averageleftMotorCortex/6.0;
            
            double tmpC3 = matrix[electrodeToChannelAssignment.getC3()][p];
            matrix[electrodeToChannelAssignment.getC3()][p] = tmpC3-averageleftMotorCortex;
            
            //subtract signals related to foot movement from adjacent electrodes
            averageRightMotorCortex = 0.0;
            averageRightMotorCortex = averageRightMotorCortex + matrix[electrodeToChannelAssignment.getCp2()][p] - matrix[electrodeToChannelAssignment.getCz()][p];
            averageRightMotorCortex = averageRightMotorCortex + matrix[electrodeToChannelAssignment.getCp4()][p];
            averageRightMotorCortex = averageRightMotorCortex + matrix[electrodeToChannelAssignment.getCp6()][p];
            //subtract signals related to foot movement from adjacent electrodes
            averageRightMotorCortex = averageRightMotorCortex + matrix[electrodeToChannelAssignment.getFc2()][p]  - matrix[electrodeToChannelAssignment.getCz()][p];
            averageRightMotorCortex = averageRightMotorCortex + matrix[electrodeToChannelAssignment.getFc4()][p];
            averageRightMotorCortex = averageRightMotorCortex + matrix[electrodeToChannelAssignment.getFc6()][p];
            averageRightMotorCortex = averageRightMotorCortex/6.0;
            
            double tmpC4 = matrix[electrodeToChannelAssignment.getC4()][p];
            matrix[electrodeToChannelAssignment.getC4()][p] = tmpC4-averageRightMotorCortex;
            
            //write values back 
            matrixToArray(matrix);
        }
    }
}