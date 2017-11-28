package datasource;
import javax.media.*;
import javax.media.protocol.*;

public class SignalDataSource{
    private DataSource aDataSource;
    private MediaLocator aMediaLocator = null;
    
    public SignalDataSource() {
    }
    
    public void setMediaLocator(MediaLocator medialocator){
        aMediaLocator = medialocator;
    }
    
    private void createSignalDataSource() {
        try{
            aDataSource = Manager.createDataSource(aMediaLocator);
        }catch (Exception ioex){System.out.println(ioex);
        }
    }
    
    public void init(){
        createSignalDataSource();
    }
    
    public DataSource getDataSource(){
        return aDataSource;
    }
}