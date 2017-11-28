/*
 * ALNClassifier.java
 *
 * Created on 19. Juni 2007, 18:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package classification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import utilities.math.Rounding;

/**
 *
 * @author Administrator
 */

public class ALNClassifier implements Classifier{
    private double numbers[][];
    
    
    static {
        try{
            System.loadLibrary("Classifier");
        }catch(Exception e){System.out.println(e);};
    }
    
    /** Creates a new instance of ALNClassifier */
    public ALNClassifier() {
    }
    
    public native double init(String fileName);
    
    public native double classify(double[] features);
    
    public static void main(String[] args) {
        ALNClassifier classifier = new ALNClassifier();
        classifier.init(570,31);
        classifier.read(System.getProperty("user.dir")+"/classification/Breast_Cancer.txt");
        classifier.init(System.getProperty("user.dir")+"/classification/Breast_CancerDTREE.dtr");
        System.out.println(System.getProperty("user.dir")+"\\classification\\Breast_CancerDTREE.dtr");
        double[] argument = new double[30];
        
        long time = System.currentTimeMillis();
        int cnt = 0;
        while((System.currentTimeMillis()-time)<1000){
            for (int y = 0; y <570; y++) {
                for (int x = 0; x < argument.length; x++) {
                    argument[x]=classifier.getNumbers()[y][x];
                        Rounding.round(classifier.classify(argument),0);
                }
            }
            cnt++;
        }
        System.out.println(cnt*570);
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
                StringTokenizer st = new StringTokenizer(line,"\t");
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
}