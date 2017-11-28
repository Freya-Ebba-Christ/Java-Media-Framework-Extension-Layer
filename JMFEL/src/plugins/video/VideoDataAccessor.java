package plugins.video;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.media.*;
import javax.media.format.*;
import java.awt.Dimension;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.Font;
import utilities.graphics.ByteImage;

public abstract class VideoDataAccessor implements Effect {
    Format inputFormat;
    Format outputFormat;
    Format[] inputFormats;
    Format[] outputFormats;
    byte [] inData;
    byte [] outData;
    private Buffer inDataBuffer;
    private int iw = 0;
    private int ih = 0;
    private ByteImage videoOut = null;
    private Graphics2D g2d;
    private int insetTop                = 0;
    private int insetBottom             = 0;
    private int insetLeft               = 0;
    private int insetRight              = 0;
    private int verticalSpacing         = 0;
    private int horizontalSpacing       = 0;
    private String title = "";
    private int strokeThickness = 2;
    private Stroke thickStroke = new BasicStroke(strokeThickness);
    private Stroke strokeBackUp = null;
    private Font usedFont = new Font("Dialog", Font.BOLD, 10);
    private boolean initialized = false;
    private boolean userDataInitialized = false;
    
    public void setFont(Font aFont){
        usedFont = aFont;
        g2d.setFont(usedFont);
    }
    
    public Font getFont(){
        return usedFont;
    }
    
    public Buffer getInDataBuffer(){
        return inDataBuffer;
    }
    
    public void setInsets(int topInset, int rightInset, int bottomInset, int leftInset ){
        
        setInsetTop(topInset);
        setInsetRight(rightInset);
        setInsetBottom(bottomInset);
        setInsetLeft(leftInset);
    }
    
    public void setVerticalSpacing(int aVal){
        verticalSpacing = aVal;
    }
    
    public int getVerticalSpacing(){
        return verticalSpacing;
    }
    
    public void setHorizontalSpacing(int aVal){
        horizontalSpacing = aVal;
    }
    
    public int getHorizontalSpacing(){
        return horizontalSpacing;
    }
    
    public void setInsetTop(int aVal){
        insetTop = aVal;
    }
    
    public int getInsetTop(){
        return insetTop;
    }
    
    public void setInsetBottom(int aVal){
        insetBottom = aVal;
    }
    
    public int getInsetBottom(){
        return insetBottom;
    }
    
    public void setInsetLeft(int aVal){
        insetLeft = aVal;
    }
    
    public int getInsetLeft(){
        return insetLeft;
    }
    
    public void setInsetRight(int aVal){
        insetRight = aVal;
    }
    
    public int getInsetRight(){
        return insetRight;
    }
    
    public VideoDataAccessor() {
        inputFormats = new Format[] {
            new RGBFormat(null,
                    Format.NOT_SPECIFIED,
                    Format.byteArray,
                    Format.NOT_SPECIFIED,
                    24,
                    3, 2, 1,
                    3, Format.NOT_SPECIFIED,
                    Format.TRUE,
                    Format.NOT_SPECIFIED)
        };
        
        outputFormats = new Format[] {
            new RGBFormat(null,
                    Format.NOT_SPECIFIED,
                    Format.byteArray,
                    Format.NOT_SPECIFIED,
                    24,
                    3, 2, 1,
                    3, Format.NOT_SPECIFIED,
                    Format.TRUE,
                    Format.NOT_SPECIFIED)
        };
        
    }
    
    // methods for interface Codec
    public Format[] getSupportedInputFormats() {
        return inputFormats;
    }
    
    public Format [] getSupportedOutputFormats(Format input) {
        if (input == null) {
            return outputFormats;
        }
        
        if (matches(input, inputFormats) != null) {
            return new Format[] { outputFormats[0].intersects(input) };
        } else {
            return new Format[0];
        }
    }
    
    public Format setInputFormat(Format input) {
        inputFormat = input;
        return input;
    }
    
    public ByteImage getVideoImage(){
        
        return videoOut;
    }
    
    public Graphics2D getGraphics2D(){
        return g2d;
    }
    
    
    public Format setOutputFormat(Format output) {
        if (output == null || matches(output, outputFormats) == null)
            return null;
        RGBFormat incoming = (RGBFormat) output;
        
        Dimension size = incoming.getSize();
        int maxDataLength = incoming.getMaxDataLength();
        int lineStride = incoming.getLineStride();
        float frameRate = incoming.getFrameRate();
        int flipped = incoming.getFlipped();
        int endian = incoming.getEndian();
        
        if (size == null)
            return null;
        if (maxDataLength < size.width * size.height * 3)
            maxDataLength = size.width * size.height * 3;
        if (lineStride < size.width * 3)
            lineStride = size.width * 3;
        if (flipped != Format.FALSE)
            flipped = Format.FALSE;
        
        outputFormat = outputFormats[0].intersects(new RGBFormat(size,
                maxDataLength,
                null,
                frameRate,
                Format.NOT_SPECIFIED,
                Format.NOT_SPECIFIED,
                Format.NOT_SPECIFIED,
                Format.NOT_SPECIFIED,
                Format.NOT_SPECIFIED,
                lineStride,
                Format.NOT_SPECIFIED,
                Format.NOT_SPECIFIED));
        return outputFormat;
    }
    
    public byte[] getInData(){
        return inData;
    }
    
    public int getInLength(){
        return inData.length;
    }
    
    public byte[] getOutData(){
        return outData;
    }
    
    public int getOutLength(){
        return outData.length;
    }
    
    public void processInData(){
    }
    
    public void processOutData(){
    }
    
    public int getHeight(){
        return ih;
    }
    
    public int getWidth(){
        return iw;
    }
    
    public void initializeUserData(){
        
    }
    
    public void flipG2D(){
        g2d.scale(-1, -1);
        g2d.translate(-getWidth(),-getHeight());
        g2d.scale(-1, 1);
        g2d.translate(-getWidth(),0);
    }
    
    private void initialize(){
        
        //generate a ByteImage image that is used to access the pixels in the video.
        if (!initialized){
            videoOut =  new ByteImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            g2d = videoOut.getBufferedImage().createGraphics();
            flipG2D();
            initialized = true;
        }
    }
    
    public int process(Buffer inBuffer, Buffer outBuffer) {
        int outputDataLength = ((VideoFormat)outputFormat).getMaxDataLength();
        validateByteArraySize(outBuffer, outputDataLength);
        
        outBuffer.setLength(outputDataLength);
        outBuffer.setFormat(outputFormat);
        outBuffer.setFlags(inBuffer.getFlags());
        
        inData = (byte[]) inBuffer.getData();
        outData = (byte[]) outBuffer.getData();
        RGBFormat vfIn = (RGBFormat) inBuffer.getFormat();
        Dimension sizeIn = vfIn.getSize();
        int pixStrideIn = vfIn.getPixelStride();
        int lineStrideIn = vfIn.getLineStride();
        iw = sizeIn.width;
        ih = sizeIn.height;
        initialize();
        if(!userDataInitialized){
            initializeUserData();
            userDataInitialized = true;
        }
        System.arraycopy(getInData(), 0, videoOut.getPixels(), 0, getInLength());
        inDataBuffer = inBuffer;
        processInData();
        System.arraycopy(videoOut.getPixels(), 0, getOutData(), 0, getInLength());
        processOutData();
        System.arraycopy(videoOut.getPixels(), 0, getOutData(), 0, getOutLength());
        return BUFFER_PROCESSED_OK;
    }

    public String getName() {
        return "VideoDataAccessor";
    }
    
    public void open() {
    }
    
    public void close() {
    }
    
    public void reset() {
    }
    
    public Object getControl(String controlType) {
        return null;
    }
    
    public Object[] getControls() {
        return null;
    }

    Format matches(Format in, Format outs[]) {
        for (int i = 0; i < outs.length; i++) {
            if (in.matches(outs[i]))
                return outs[i];
        }
        
        return null;
    }
    
    byte[] validateByteArraySize(Buffer buffer,int newSize) {
        Object objectArray=buffer.getData();
        byte[] typedArray;
        
        if (objectArray instanceof byte[]) {
            typedArray=(byte[])objectArray;
            if (typedArray.length >= newSize ) {
                return typedArray;
            }
            
            byte[] tempArray=new byte[newSize];
            System.arraycopy(typedArray,0,tempArray,0,typedArray.length);
            typedArray = tempArray;
        } else {
            typedArray = new byte[newSize];
        }
        
        buffer.setData(typedArray);
        return typedArray;
    }
}