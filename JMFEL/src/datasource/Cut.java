/*
 * CuttingStream.java
 *
 * Created on 20. Dezember 2007, 18:54
 *
 * This code was taken straight from the cut.java example http://java.sun.com/products/java-media/jmf/2.1.1/solutions/
 */

package datasource;
import java.awt.*;
import java.util.Vector;
import java.io.File;
import javax.media.*;
import javax.media.control.TrackControl;
import javax.media.control.QualityControl;
import javax.media.control.FramePositioningControl;
import javax.media.Format;
import javax.media.format.*;
import javax.media.datasink.*;
import javax.media.protocol.*;
import javax.media.protocol.DataSource;
import java.io.IOException;

public class Cut implements ControllerListener, DataSinkListener {
    
    /**
     * Main program
     */
    
    public static void main(String [] args) {
        
        String inputURL = null;
        String outputURL = null;
        long start[], end[];
        Vector startV = new Vector();
        Vector endV = new Vector();
        boolean frameMode = false;
        
        if (args.length == 0)
            prUsage();
        
        // Parse the arguments.
        int i = 0;
        while (i < args.length) {
            
            if (args[i].equals("-o")) {
                i++;
                if (i >= args.length)
                    prUsage();
                outputURL = args[i];
            } else if (args[i].equals("-f")) {
                frameMode = true;
            } else if (args[i].equals("-s")) {
                i++;
                if (i >= args.length)
                    prUsage();
                startV.addElement(new Long(args[i]));
            } else if (args[i].equals("-e")) {
                i++;
                if (i >= args.length)
                    prUsage();
                endV.addElement(new Long(args[i]));
                
                // For every end point, there should be a matching
                // start point; unless is the first point.
                if (startV.size() != endV.size()) {
                    if (startV.size() == 0)
                        startV.addElement(new Long(0));
                    else
                        prUsage();
                }
            } else {
                inputURL = args[i];
            }
            i++;
        }
        
        if (inputURL == null) {
            System.err.println("No input url specified.");
            prUsage();
        }
        
        if (outputURL == null) {
            System.err.println("No output url specified.");
            prUsage();
        }
        
        if (startV.size() == 0 && endV.size() == 0) {
            System.err.println("No start and end point specified.");
            prUsage();
        }
        
        // Pad the last end point if necessary.
        if (startV.size() > endV.size()) {
            if (startV.size() == endV.size() + 1)
                endV.addElement(new Long(Long.MAX_VALUE));
            else
                prUsage();
        }
        
        start = new long[startV.size()];
        end = new long[startV.size()];
        long prevEnd = 0;
        
        // Parse the start and end points.
        for (int j = 0; j < start.length; j++) {
            
            start[j] = ((Long)startV.elementAt(j)).longValue();
            end[j] = ((Long)endV.elementAt(j)).longValue();
            
            if (prevEnd > start[j]) {
                System.err.println("Previous end point cannot be > the next start point.");
                prUsage();
            } else if (start[j] >= end[j]) {
                System.err.println("Start point cannot be >= end point.");
                prUsage();
            }
            
            prevEnd = end[j];
        }
        
        if (frameMode) {
            System.err.println("Start and end points are specified in frames.");
        } else {
            // Times are in millseconds.  We'll turn them into nanoseconds.
            for (int j = 0; j < start.length; j++) {
                start[j] *= 1000000;
                if (end[j] != Long.MAX_VALUE)
                    end[j] *= 1000000;
            }
        }
        
        // Generate the input and output media locators.
        MediaLocator iml;
        MediaLocator oml;
        
        if ((iml = createMediaLocator(inputURL)) == null) {
            System.err.println("Cannot build media locator from: " + inputURL);
            System.exit(0);
        }
        
        if ((oml = createMediaLocator(outputURL)) == null) {
            System.err.println("Cannot build media locator from: " + outputURL);
            System.exit(0);
        }
        
        // Trancode with the specified parameters.
        Cut cut  = new Cut();
        
        if (!cut.doIt(iml, oml, start, end, frameMode)) {
            System.err.println("Failed to cut the input");
        }
        
        System.exit(0);
    }
    
    
    /**
     * Given a source media locator, destination media locator and
     * a start and end point, this program cuts the pieces out.
     */
    public boolean doIt(MediaLocator inML, MediaLocator outML, long start[], long end[], boolean frameMode) {
        
        // Guess the output content descriptor from the file extension.
        ContentDescriptor cd;
        
        if ((cd = fileExtToCD(outML.getRemainder())) == null) {
            System.err.println("Couldn't figure out from the file extension the type of output needed!");
            return false;
        }
        
        Processor p;
        
        try {
            System.err.println("- Create processor for: " + inML);
            p = Manager.createProcessor(inML);
        } catch (Exception e) {
            System.err.println("Yikes!  Cannot create a processor from the given url: " + e);
            return false;
        }
        
        System.err.println("- Configure the processor for: " + inML);
        if (!waitForState(p, p.Configured)) {
            System.err.println("Failed to configure the processor.");
            return false;
        }
        
        checkTrackFormats(p);
        
        System.err.println("- Realize the processor for: " + inML);
        if (!waitForState(p, p.Realized)) {
            System.err.println("Failed to realize the processor.");
            return false;
        }
        
        // Set the JPEG quality to .5.
        setJPEGQuality(p, 0.5f);
        
        // Translate frame # into time.
        if (frameMode) {
            FramePositioningControl fpc = (FramePositioningControl)p.getControl("javax.media.control.FramePositioningControl");
            
            if (fpc != null) {
                Time t;
                for (int i = 0; i < start.length; i++) {
                    t = fpc.mapFrameToTime((int)start[i]);
                    if (t == FramePositioningControl.TIME_UNKNOWN) {
                        fpc = null;
                        break;
                    } else
                        start[i] = t.getNanoseconds();
                    if (end[i] == Long.MAX_VALUE)
                        continue;
                    t = fpc.mapFrameToTime((int)end[i]);
                    if (t == FramePositioningControl.TIME_UNKNOWN) {
                        fpc = null;
                        break;
                    } else
                        end[i] = t.getNanoseconds();
                }
            }
            
            if (fpc == null) {
                System.err.println("Sorry... the given input media type does not support frame positioning.");
                return false;
            }
        }
        
        CuttingDataSource ds = new CuttingDataSource(p, inML, start, end);
        
        // Create the processor to generate the final output.
        try {
            p = Manager.createProcessor(ds);
        } catch (Exception e) {
            System.err.println("Failed to create a processor to concatenate the inputs.");
            return false;
        }
        
        p.addControllerListener(this);
        
        // Put the Processor into configured state.
        if (!waitForState(p, p.Configured)) {
            System.err.println("Failed to configure the processor.");
            return false;
        }
        
        // Set the output content descriptor on the final processor.
        System.err.println("- Set output content descriptor to: " + cd);
        if ((p.setContentDescriptor(cd)) == null) {
            System.err.println("Failed to set the output content descriptor on the processor.");
            return false;
        }
        
        // We are done with programming the processor.  Let's just
        // realize and prefetch it.
        if (!waitForState(p, p.Prefetched)) {
            System.err.println("Failed to realize the processor.");
            return false;
        }
        
        // Now, we'll need to create a DataSink.
        DataSink dsink;
        if ((dsink = createDataSink(p, outML)) == null) {
            System.err.println("Failed to create a DataSink for the given output MediaLocator: " + outML);
            return false;
        }
        
        dsink.addDataSinkListener(this);
        fileDone = false;
        
        System.err.println("- Start cutting...");
        
        // OK, we can now start the actual concatenation.
        try {
            p.start();
            dsink.start();
        } catch (IOException e) {
            System.err.println("IO error during concatenation");
            return false;
        }
        
        // Wait for EndOfStream event.
        waitForFileDone();
        
        // Cleanup.
        try {
            dsink.close();
        } catch (Exception e) {}
        p.removeControllerListener(this);
        
        System.err.println("  ...done cutting.");
        
        return true;
    }
    
    
    /**
     * Transcode the MPEG audio to linear and video to JPEG so
     * we can do the cutting.
     */
    void checkTrackFormats(Processor p) {
        
        TrackControl tc[] = p.getTrackControls();
        VideoFormat mpgVideo = new VideoFormat(VideoFormat.MPEG);
        AudioFormat rawAudio = new AudioFormat(AudioFormat.LINEAR);
        
        for (int i = 0; i < tc.length; i++) {
            Format preferred = null;
            
            if (tc[i].getFormat().matches(mpgVideo)) {
                preferred = new VideoFormat(VideoFormat.JPEG);
            } else if (tc[i].getFormat() instanceof AudioFormat &&
                    !tc[i].getFormat().matches(rawAudio)) {
                preferred = rawAudio;
            }
            
            if (preferred != null) {
                Format supported[] = tc[i].getSupportedFormats();
                Format selected = null;
                
                for (int j = 0; j < supported.length; j++) {
                    if (supported[j].matches(preferred)) {
                        selected = supported[j];
                        break;
                    }
                }
                
                if (selected != null) {
                    System.err.println("  Transcode:");
                    System.err.println("     from: " + tc[i].getFormat());
                    System.err.println("     to: " + selected);
                    tc[i].setFormat(selected);
                }
            }
        }
    }
    
    
    /**
     * Setting the encoding quality to the specified value on the JPEG encoder.
     * 0.5 is a good default.
     */
    void setJPEGQuality(Player p, float val) {
        
        Control cs[] = p.getControls();
        QualityControl qc = null;
        VideoFormat jpegFmt = new VideoFormat(VideoFormat.JPEG);
        
        // Loop through the controls to find the Quality control for
        // the JPEG encoder.
        for (int i = 0; i < cs.length; i++) {
            
            if (cs[i] instanceof QualityControl &&
                    cs[i] instanceof Owned) {
                Object owner = ((Owned)cs[i]).getOwner();
                
                // Check to see if the owner is a Codec.
                // Then check for the output format.
                if (owner instanceof Codec) {
                    Format fmts[] = ((Codec)owner).getSupportedOutputFormats(null);
                    for (int j = 0; j < fmts.length; j++) {
                        if (fmts[j].matches(jpegFmt)) {
                            qc = (QualityControl)cs[i];
                            qc.setQuality(val);
                            System.err.println("- Set quality to " +
                                    val + " on " + qc);
                            break;
                        }
                    }
                }
                if (qc != null)
                    break;
            }
        }
    }
    
    /**
     * Utility class to block until a certain state had reached.
     */
    public class StateWaiter implements ControllerListener {
        
        Processor p;
        boolean error = false;
        
        StateWaiter(Processor p) {
            this.p = p;
            p.addControllerListener(this);
        }
        
        public synchronized boolean waitForState(int state) {
            
            switch (state) {
                case Processor.Configured:
                    p.configure(); break;
                case Processor.Realized:
                    p.realize(); break;
                case Processor.Prefetched:
                    p.prefetch(); break;
                case Processor.Started:
                    p.start(); break;
            }
            
            while (p.getState() < state && !error) {
                try {
                    wait(1000);
                } catch (Exception e) {
                }
            }
            //p.removeControllerListener(this);
            return !(error);
        }
        
        public void controllerUpdate(ControllerEvent ce) {
            if (ce instanceof ControllerErrorEvent) {
                error = true;
            }
            synchronized (this) {
                notifyAll();
            }
        }
    }
    
    
    /**
     * Create the DataSink.
     */
    DataSink createDataSink(Processor p, MediaLocator outML) {
        
        DataSource ds;
        
        if ((ds = p.getDataOutput()) == null) {
            System.err.println("Something is really wrong: the processor does not have an output DataSource");
            return null;
        }
        
        DataSink dsink;
        
        try {
            System.err.println("- Create DataSink for: " + outML);
            dsink = Manager.createDataSink(ds, outML);
            dsink.open();
        } catch (Exception e) {
            System.err.println("Cannot create the DataSink: " + e);
            return null;
        }
        
        return dsink;
    }
    
    
    /**
     * Block until the given processor has transitioned to the given state.
     * Return false if the transition failed.
     */
    boolean waitForState(Processor p, int state) {
        return (new StateWaiter(p)).waitForState(state);
    }
    
    
    /**
     * Controller Listener.
     */
    public void controllerUpdate(ControllerEvent evt) {
        
        if (evt instanceof ControllerErrorEvent) {
            System.err.println("Failed to cut the file.");
            System.exit(-1);
        } else if (evt instanceof EndOfMediaEvent) {
            evt.getSourceController().close();
        }
    }
    
    
    Object waitFileSync = new Object();
    boolean fileDone = false;
    boolean fileSuccess = true;
    
    /**
     * Block until file writing is done.
     */
    boolean waitForFileDone() {
        System.err.print("  ");
        synchronized (waitFileSync) {
            try {
                while (!fileDone) {
                    waitFileSync.wait(1000);
                    System.err.print(".");
                }
            } catch (Exception e) {}
        }
        System.err.println("");
        return fileSuccess;
    }
    
    
    /**
     * Event handler for the file writer.
     */
    public void dataSinkUpdate(DataSinkEvent evt) {
        
        if (evt instanceof EndOfStreamEvent) {
            synchronized (waitFileSync) {
                fileDone = true;
                waitFileSync.notifyAll();
            }
        } else if (evt instanceof DataSinkErrorEvent) {
            synchronized (waitFileSync) {
                fileDone = true;
                fileSuccess = false;
                waitFileSync.notifyAll();
            }
        }
    }
    
    
    /**
     * Convert a file name to a content type.  The extension is parsed
     * to determine the content type.
     */
    ContentDescriptor fileExtToCD(String name) {
        
        String ext;
        int p;
        
        // Extract the file extension.
        if ((p = name.lastIndexOf('.')) < 0)
            return null;
        
        ext = (name.substring(p + 1)).toLowerCase();
        
        String type;
        
        // Use the MimeManager to get the mime type from the file extension.
        if ( ext.equals("mp3")) {
            type = FileTypeDescriptor.MPEG_AUDIO;
        } else {
            if ((type = com.sun.media.MimeManager.getMimeType(ext)) == null)
                return null;
            type = ContentDescriptor.mimeTypeToPackageName(type);
        }
        
        return new FileTypeDescriptor(type);
    }
    
    
    /**
     * Create a media locator from the given string.
     */
    static MediaLocator createMediaLocator(String url) {
        
        MediaLocator ml;
        
        if (url.indexOf(":") > 0 && (ml = new MediaLocator(url)) != null)
            return ml;
        
        if (url.startsWith(File.separator)) {
            if ((ml = new MediaLocator("file:" + url)) != null)
                return ml;
        } else {
            String file = "file:" + System.getProperty("user.dir") + File.separator + url;
            if ((ml = new MediaLocator(file)) != null)
                return ml;
        }
        
        return null;
    }
    
    
    static void prUsage() {
        System.err.println("Usage: java Cut -o <output> <input> [-f] -s <startTime> -e <endTime> ...");
        System.err.println("     <output>: input URL or file name");
        System.err.println("     <input>: output URL or file name");
        System.err.println("     <startTime>: start time in milliseconds");
        System.err.println("     <endTime>: end time in milliseconds");
        System.err.println("     -f: specify the times in video frames instead of milliseconds");
        System.exit(0);
    }
}

