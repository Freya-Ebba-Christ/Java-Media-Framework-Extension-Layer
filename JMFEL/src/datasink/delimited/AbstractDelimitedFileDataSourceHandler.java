/*
 * AbstractDelimitedFileDataSink.java
 *
 * Created on 11. April 2007, 16:44
 *
 */

package datasink.delimited;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.media.MediaLocator;

/**
 *
 * @author christ
 */

public abstract class AbstractDelimitedFileDataSourceHandler extends AbstractFileDataSourceHandler{
    
    private MediaLocator locator;
    private BufferedWriter bufferedWriter = null;
    private boolean append = false;
    private String delimiter;
    private String title;
    
    public AbstractDelimitedFileDataSourceHandler() {
    }
    
    public boolean isAppendEnabled() {
        return append;
    }
    
    public String getDelimiter() {
        return delimiter;
    }
    
    public void setDelimiter(char character) {
        delimiter = String.valueOf(character);
    }
    
    public void setAppendEnabled(boolean append) {
        this.append = append;
    }
    
    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }
    
    public void setBufferedWriter(BufferedWriter bufferedWriter) {
        this.bufferedWriter = bufferedWriter;
    }
    
    public void setOutputLocator(MediaLocator ml) {
        locator = ml;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return title;
    }
    
    public abstract void process();
    
    public MediaLocator getOutputLocator() {
        return locator;
    }
    
    public File getFile(){
        return new File(getOutputLocator().getRemainder());
    }
    
    public void open() {
        //open the file
        try{
            setBufferedWriter(new BufferedWriter(new FileWriter(getFile())));
            //write the header
            getBufferedWriter().append(getTitle());
        } catch (IOException e) {
            System.out.println(e.getStackTrace().toString());;
        }
    }
    
    public void close() {
        // close all sources
        super.close();
        // now close the file
        try {
            getBufferedWriter().close();
        }catch (IOException e) {
            System.out.println(e.getStackTrace().toString());;
        }
    }
}