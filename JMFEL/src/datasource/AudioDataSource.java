package datasource;
import javax.media.*;
import javax.media.protocol.*;
import javax.media.control.*;
import javax.media.format.*;

public class AudioDataSource{
    private DataSource aDataSource;
    private Format af = new AudioFormat(AudioFormat.LINEAR, 22050, 8, 1);
    private MediaLocator aMediaLocator = new MediaLocator("dsound://");
    
    public AudioDataSource() {
    }
    
    private DataSource createAudioDataSource() {
        DataSource ads = null; // Audio DataSource
        CaptureDevice acd = null; // Audio CaptureDevice
        FormatControl [] afc = null; // Audio FormatControl
        try {
            ads = Manager.createDataSource(aMediaLocator);
            acd = (CaptureDevice)ads; // casting Audio DataSource to CaptureDevice
            afc = acd.getFormatControls();
            if (afc != null) afc[0].setFormat((AudioFormat)af);
        }catch(Exception ioex){System.out.println(ioex);};
        return ads;
    }
    
    public void setAudioCaptureFormat(Format format) {
        af = format;
    }
    
    public void setMediaLocator(MediaLocator medialocator){
        aMediaLocator = medialocator;
    }
    
    public void init(){
        aDataSource = createAudioDataSource();
    }
    
    public DataSource getDataSource(){
        return aDataSource;
    }
}