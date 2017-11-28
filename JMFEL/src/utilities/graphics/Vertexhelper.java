package utilities.graphics;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import utilities.CSVReader;
import utilities.CSVWriter;
import utilities.Point2f;

/*
 * Vertexhelper.java
 * This strange utility extracts the coordinates of red pixels in the input image...
 * Useless??? Well, not if the red pixels depict coordinates e.g. for electrode positions...
 * This way, you dont need an extra vertex editor...
 * The coordinates/vertices are ordered from left to right and top to bottom.
 * The coordinates are within 0.0f and 1.0f
 */

/**
 *
 * @author Administrator
 */

public class Vertexhelper {
    
    private Vector<Point2f> vertices = new Vector();
    
    /** Creates a new instance of Vertexhelper */
    public Vertexhelper() {
    }
    
    public BufferedImage load(String path, int type){
        BufferedImage image = null;
        try{
            Image img = new ImageIcon(ImageIO.read(new File(path))).getImage();
            image = createBufferedImage(img,type);
        }catch(Exception e){System.out.println(e);};
        return image;
    }
    
    public Point2f getVertex(int index){
        return vertices.get(index);
    }
    
    public void addVertex(Point2f vertex){
        vertices.add(vertex);
    }
    
    public int getNumVertices(){
        return vertices.size();
    }
    
    private BufferedImage createBufferedImage(Image image, int type) {
        BufferedImage bi = new BufferedImage(image.getWidth(null), image.getHeight(null),type);
        Graphics2D g = bi.createGraphics();
        g.drawImage(image, 0, 0, null);
        return bi;
    }
    
    //construct a ByteImage out of regular BufferedImage
    public ByteImage toByteImage(BufferedImage image){
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster write_raster = image.getRaster();
        DataBufferByte data_buffer = (DataBufferByte)write_raster.getDataBuffer();
        byte[] pixels = data_buffer.getData();
        int numPixels = pixels.length;
        int pixStride = image.getColorModel().getPixelSize()/8;
        ByteImage bimage = new ByteImage(width, height, image.getType());
        System.arraycopy(pixels,0,bimage.getPixels(),0,numPixels);
        return bimage;
    }
    
    public void saveVertices(String file){
        //make new writer
        CSVWriter writer = new CSVWriter();
        double[] data = new double[getNumVertices()*2];
        //extract vertices
        int cnt=0;
        for(int pos = 0; pos < getNumVertices(); pos++) {
            data[cnt] = (double)getVertex(pos).getX();
            cnt++;
            data[cnt] = (double)getVertex(pos).getY();
            cnt++;
        }
        
        //set data to writer
        writer.setData(data);
        writer.setNumCols(2);
        writer.write(file);
    }
    
    public void extractVertices(BufferedImage image){
        ByteImage bimage = toByteImage(image);
        int height = bimage.getHeight();
        int width = bimage.getWidth();
        vertices = new Vector();
        for(float y = 0; y < (float)bimage.getHeight(); y++) {
            for(float x = 0; x < (float)bimage.getWidth(); x++) {
                PixelColor color = bimage.getPixel((int)x,(int)y);
                if(color.getRed()==255&&color.getGreen()==0&&color.getBlue()==0){
                    float newX = x/width;
                    float newY = y/height;
                    Point2f vertex = new Point2f();
                    vertex.setX(newX);
                    vertex.setY(newY);
                    vertices.add(vertex);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        //load the image
        //transform it into a ByteImage
        Vertexhelper aVertexhelper = new Vertexhelper();
        
        //extract coordinates of red pixels from picture and save them as vertices;-)
        String file_separator = System.getProperty("file.separator");
        String imagePath = System.getProperty("user.dir") + file_separator + "resources" + file_separator + "eyetracker" + file_separator + "aoi.bmp";
        String vertexPath = System.getProperty("user.dir") + file_separator + "resources" + file_separator + "eyetracker" + file_separator + "aoi_positions.txt";
        BufferedImage image = aVertexhelper.load(imagePath,BufferedImage.TYPE_3BYTE_BGR);
        aVertexhelper.extractVertices(image);
        aVertexhelper.saveVertices(vertexPath);
        System.out.println(aVertexhelper.getNumVertices());
        
        CSVReader aCSVReader = new CSVReader();
        aCSVReader.init(76,2);
        aCSVReader.read(vertexPath);
        Point2f[] vertices = new Point2f[76];
        for(int y = 0; y < aCSVReader.getNumRows(); y++) {
            vertices[y] = new Point2f((float)aCSVReader.getNumbers()[y][0],(float)aCSVReader.getNumbers()[y][1]);
        }
    }
}