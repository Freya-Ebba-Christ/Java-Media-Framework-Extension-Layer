package utilities.graphics;
/*
 *@auhor olaf christ
 *
 * 06.21.2007
 *
 * bgr/abgr handling was wrong
 * 
 */

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.awt.image.ColorModel;

public abstract class AbstractByteImage {
    private BufferedImage buffImage = null;
    private int width, height;
    private byte [] pixels;
    private int pixStride = 4;
    private int numPixels = 0;
    private DataBufferByte data_buffer = null;
    private WritableRaster write_raster;
    private ColorModel color_model;
    private PixelColor pixelColor = new PixelColor();
    private int imageType;
    
    public int getImageType() {
        return imageType;
    }
    
    public void setImageType(int imageType) {
        this.imageType = imageType;
    }
    
    public AbstractByteImage(){
    }
    
    public int getHeight(){
        return height;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getNumPixels(){
        return numPixels;
    }
    
    public byte[] getPixels(){
        return pixels;
    }
    
    
    public AbstractByteImage(int aWidth, int aHeight){
        width = aWidth;
        height = aHeight;
        setImageType(BufferedImage.TYPE_4BYTE_ABGR);
        buffImage = new BufferedImage(width, height, getImageType());
        write_raster = buffImage.getRaster();
        data_buffer = (DataBufferByte)write_raster.getDataBuffer();
        pixels = data_buffer.getData();
        numPixels = pixels.length;
    }
    
    public AbstractByteImage(int aWidth, int aHeight, int imageType){
        width = aWidth;
        height = aHeight;
        setImageType(imageType);
        buffImage = new BufferedImage(width, height, getImageType());
        write_raster = buffImage.getRaster();
        data_buffer = (DataBufferByte)write_raster.getDataBuffer();
        pixels = data_buffer.getData();
        numPixels = pixels.length;
        pixStride = buffImage.getColorModel().getPixelSize()/8;
    }
    
    public BufferedImage getBufferedImage(){
        
        return buffImage;
    }
    
    public void setPixel(int x, int y, int r, int g, int b) {
        setPixel(x, y, r, g, b, 255);
    }
    
    public void setPixel(int x, int y, int r, int g, int b, int a) {
        //pixels = data_buffer.getData();
        int xpos = x;
        int ypos = y;
        
        if (xpos>=width) {
            xpos = width-1;
        }
        if (ypos>=height) {
            ypos = height-1;
        }
        
        
        int pixelAddress = pixStride*(ypos*width+xpos);
        
        if(pixStride==3){
            pixels[pixelAddress] = (byte)b;
            pixels[pixelAddress+1] = (byte)g;
            pixels[pixelAddress+2] = (byte)r;
        }
        
        if(pixStride>3){
            pixels[pixelAddress] = (byte)a;
            pixels[pixelAddress+1] = (byte)b;
            pixels[pixelAddress+2] = (byte)g;
            pixels[pixelAddress+3] = (byte)r;
        }
    }
    
    public PixelColor getPixel(int x, int y){
        
        //pixels = data_buffer.getData();
        int pixelAddress = pixStride*(y*width+x);
        //set the RGB values
        if(pixStride>3){
            pixelColor.setPixelColor(pixels[pixelAddress] & 255, pixels[pixelAddress+1]& 255, pixels[pixelAddress+2]& 255,pixels[pixelAddress+3]& 255 );
        } else {
            pixelColor.setPixelColor(pixels[pixelAddress]& 255, pixels[pixelAddress+1]& 255,pixels[pixelAddress+2]& 255 );
        }
        return pixelColor;
    }
}