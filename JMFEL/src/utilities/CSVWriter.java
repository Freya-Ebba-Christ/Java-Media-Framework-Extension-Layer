/*
 * CSVWriter.java
 *
 * Created on 1. August 2007, 15:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * @author Administrator
 */
public class CSVWriter {
    private double[] data;
    private int numCols = 0;
    
    /** Creates a new instance of CSVWriter */
    public CSVWriter() {
    }
    
    public int getNumCols() {
        return numCols;
    }
    
    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }
    
    public double[] getData() {
        return data;
    }
    
    public void setData(double[] data) {
        this.data = data;
    }
    
    public void write(String path){
        try{
            File file = new File(path);
            BufferedWriter aBufferedWriter  = new BufferedWriter(new FileWriter(file));
            String line = null;
            int cnt = 0;
            for(int pos = 0; pos < data.length; pos++) {
                aBufferedWriter.write(Double.toString(getData()[pos]));
                if(pos < data.length-1){
                    aBufferedWriter.write(",");
                }
                cnt++;
                if(cnt==getNumCols()){
                    aBufferedWriter.write("\n");
                    cnt = 0;
                }
            }
            aBufferedWriter.close();
        }catch(IOException e){System.out.println(e);};
    }
    
    public static void main(String [] args) {
        double[] data = new double[64];
        double[] data2;
        CSVWriter writer = new CSVWriter();
        writer.setNumCols(64);
        writer.setData(data);
        writer.write("c:\\out.txt");
        
        CSVReader aCSVReader = new CSVReader();
        aCSVReader.init(1,64);
        aCSVReader.read("c:\\out.txt");
        data2 = aCSVReader.getNumbers()[0];
        for(int pos = 0; pos < data.length; pos++) {
            System.out.println(data[pos]);
        }
    }
}

