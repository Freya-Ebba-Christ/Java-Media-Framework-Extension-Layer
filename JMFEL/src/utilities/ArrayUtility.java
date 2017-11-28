/*
 * ArrayUtility.java
 *
 * Created on 2. November 2006, 13:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilities;

/**
 *
 * @author christ
 */
public class ArrayUtility {
    
    /** Creates a new instance of ArrayUtility */
    public ArrayUtility() {
    }

    
    public double[][] arrayToMatrix(double[] array,int width, int height, boolean transpose){
        double[][] outMatrix;
        if(transpose){
            outMatrix = new double[width][height];
        } else {
            outMatrix = new double[height][width];
        }
        int cnt = 0;
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(transpose){
                    outMatrix[j][i] = array[cnt];
                }else {
                    outMatrix[i][j] = array[cnt];
                }
                cnt++;
            }
        }
        return outMatrix;
    }
    
    public double[] matrixToArray(double[][] matrix, int width, int height){
        double[] outArray = new double[width*height];
        int cnt = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                outArray[cnt] = matrix[j][i];
                cnt++;
            }
        }
        return outArray;
    }
}
