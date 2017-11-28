package datasource;
import javax.media.*;
import javax.media.protocol.*;
import javax.media.control.*;
import javax.media.format.*;
import java.awt.Dimension;
import java.util.Vector;

public class VideoDataSource {
    private int width  = 320;
    private int height = 240;
    private VideoFormat vf = new VideoFormat(VideoFormat.RGB, new Dimension(width, height), Format.NOT_SPECIFIED, null, 30.0f);
    private DataSource aVideoDataSource = null;
    
    public VideoDataSource() {
    }
    
    public void setVideoCaptureFormat(VideoFormat aVideoFormat){
        vf = aVideoFormat;
    }
    
    private DataSource createVideoDataSource() throws Exception{
        DataSource ds;
        Vector devices;
        CaptureDeviceInfo cdi;
        MediaLocator ml;
        
        // Find devices for format
        devices = CaptureDeviceManager.getDeviceList(vf);
        if (devices.size() < 1) {
            System.err.println("! No Devices for " + vf);
            return null;
        }
        // Pick the first device
        cdi = (CaptureDeviceInfo) devices.elementAt(0);
        
        ml = cdi.getLocator();
        
        try {
            ds = Manager.createDataSource(ml);
            ds.connect();
            if (ds instanceof CaptureDevice) {
                setCaptureFormat((CaptureDevice) ds, vf);
            }
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
        return ds;
    }
    
    private void setCaptureFormat(CaptureDevice cdev, Format format) throws Exception{
        FormatControl [] fcs = cdev.getFormatControls();
        if (fcs.length < 1)
            return;
        FormatControl fc = fcs[0];
        Format [] formats = fc.getSupportedFormats();
        for (int i = 0; i < formats.length; i++) {
            if (formats[i].matches(format)) {
                format = formats[i].intersects(format);
                fc.setFormat(format);
                break;
            }
        }
    }
    
    public void init() throws Exception{
        aVideoDataSource = createVideoDataSource();
    }
    
    public DataSource getDataSource(){
        return aVideoDataSource;
    }
}