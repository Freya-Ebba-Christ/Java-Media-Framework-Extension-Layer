/*
 *@auhor olaf christ
 */

package utilities.graphics;
public class PixelColor {
    
    private int pixelColor_r = 0;
    private int pixelColor_g = 0;
    private int pixelColor_b = 0;
    private int pixelColor_a = 0;
    
    public PixelColor() {
    }
    
    public void setPixelColor(int a,int b, int g, int r){
        pixelColor_r=r;
        pixelColor_g=g;
        pixelColor_b=b;
        pixelColor_a=a;
    }
    
    public void setPixelColor(int b, int g, int r){
        pixelColor_r=r;
        pixelColor_g=g;
        pixelColor_b=b;
    }
    
    
    public int getRed(){
        return pixelColor_r;
    }
    
    public int getGreen(){
        return pixelColor_g;
    }
    
    public int getBlue(){
        return pixelColor_b;
    }
    
    public int getAlpha(){
        return pixelColor_a;
    }
}