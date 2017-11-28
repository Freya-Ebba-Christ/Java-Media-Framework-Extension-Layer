package utilities.graphics;
/*
 *@auhor olaf christ
 */

public class ByteImage extends AbstractByteImage{
    
    /** Creates a new instance of ByteImage */
    public ByteImage() {
        super();
    }
    
    public ByteImage(int aWidth, int aHeight) {
        super(aWidth, aHeight);
    }
    
    public ByteImage(int aWidth, int aHeight, int imageType) {
        super(aWidth, aHeight,imageType);
    }
    
        public void drawCross(int x, int y, int r, int g, int b, int a){
        // draw a small cross into the buffer and do basic clipping
        
        setPixel(x, y, r, g, b, a);
        
        if(y<=getHeight()-3){
            setPixel(x, y+1,r,g,b,a);
            setPixel(x, y+3,r,g,b,a);
        }
        
        if(y>=3){
            setPixel(x, y-1,r,g,b,a);
            setPixel(x, y-3,r,g,b,a);
        }
        
        if(x<=getWidth()-3){
            setPixel(x+1, y,r,g,b,a);
            setPixel(x+3, y,r,g,b,a);
        }
        
        if(x>=3){
            setPixel(x-1, y,r,g,b,a);
            setPixel(x-3, y,r,g,b,a);
        }
    }
}
