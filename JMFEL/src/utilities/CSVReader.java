/*
 * CSVReader.java
 *
 * Created on 12. Juni 2007, 17:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Administrator
 */
public class CSVReader {
    
    private double numbers[][];
    
    /** Creates a new instance of CSVReader */
    public CSVReader() {
    }
    
    public void init(int rows, int columns){
        numbers = new double[rows][columns];
    }
    
    public int getNumRows(){
        return numbers.length;
    }
    
    public int getNumColumns(){
        return numbers[0].length;
    }
    
    public double[][] getNumbers() {
        return numbers;
    }
    
    public void setNumbers(double[][] numbers) {
        this.numbers = numbers;
    }
    
    public void read(String path){
        try{
            File file = new File(path);
            BufferedReader aBufferedReader  = new BufferedReader(new FileReader(file));
            String line = null;
            int row = 0;
            int col = 0;
            
            while((line = aBufferedReader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line,",");
                while (st.hasMoreTokens()) {
                    numbers[row][col] = Double.parseDouble(st.nextToken());
                    col++;
                }
                row++;
                col=0;
            }
            aBufferedReader.close();
        }catch(IOException e){System.out.println(e);};
    }
    
    public static void main(String [] args) {
        CSVReader aCSVReader = new CSVReader();
        aCSVReader.init(6,7);
        aCSVReader.read(System.getProperty("user.dir")+"\\src\\resources\\eyetracker\\scene.csv");
    }
}


