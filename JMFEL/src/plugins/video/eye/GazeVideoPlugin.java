/*
 * GazeVideoPlugin.java
 *
 * Created on 5. Juni 2007, 19:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package plugins.video.eye;
import java.awt.Point;
import eye.insight.InsightDatagramPacketDecoder;
import java.awt.Color;
import plugins.video.VideoDataAccessor;
import utilities.graphics.CoordinateConverter;

/**
 *
 * @author Administrator
 */
public class GazeVideoPlugin extends VideoDataAccessor{
    
    private CoordinateConverter coordinateConverter;
    private Point cursorPosition = new Point(0,0);
    private InsightDatagramPacketDecoder datagramPacketDecoder;
    private int horizontalMaximum = 250;
    private int horizontalMinimum = -250;
    private int verticalMaximum = 380;
    private int verticalMinimum = -50;
    
    /** Creates a new instance of GazeVideoPlugin */
    public GazeVideoPlugin() {
        coordinateConverter = new CoordinateConverter();
    }

    public int getHorizontalMaximum() {
        return horizontalMaximum;
    }

    public int getHorizontalMinimum() {
        return horizontalMinimum;
    }

    public int getVerticalMaximum() {
        return verticalMaximum;
    }

    public int getVerticalMinimum() {
        return verticalMinimum;
    }

    public void setHorizontalMaximum(int horizontalMaximum) {
        this.horizontalMaximum = horizontalMaximum;
    }

    public void setHorizontalMinimum(int horizontalMinimum) {
        this.horizontalMinimum = horizontalMinimum;
    }

    public void setVerticalMaximum(int verticalMaximum) {
        this.verticalMaximum = verticalMaximum;
    }

    public void setVerticalMinimum(int verticalMinimum) {
        this.verticalMinimum = verticalMinimum;
    }
    
    public void setDatagramPacketDecoder(InsightDatagramPacketDecoder datagramPacketDecoder) {
        this.datagramPacketDecoder = datagramPacketDecoder;
    }
    
    public InsightDatagramPacketDecoder getDatagramPacketDecoder() {
        return datagramPacketDecoder;
    }
    
    public void setCoordinateConverter(CoordinateConverter coordinateConverter) {
        this.coordinateConverter = coordinateConverter;
    }
    
    public CoordinateConverter getCoordinateConverter() {
        return coordinateConverter;
    }
    
    public Point getCursorPosition() {
        return cursorPosition;
    }
    
    public void setCursorPosition(Point cursorPosition) {
        this.cursorPosition = cursorPosition;
    }
    
    public void processOutData() {
        try{
            coordinateConverter.setHorizontalMaximum(getHorizontalMaximum());
            coordinateConverter.setHorizontalMinimum(getHorizontalMinimum());
            coordinateConverter.setVerticalMaximum(getVerticalMaximum());
            coordinateConverter.setVerticalMinimum(getVerticalMinimum());
            
            //if the cursor points to a valid AOI, position the cursor
            //if(observable.getEyeTrackerRenderer().getDatagramdecoder().getHitAOI()>-1){
            getGraphics2D().setColor(Color.WHITE);
            setCursorPosition(getCoordinateConverter().millimeterToPixel(
                    getDatagramPacketDecoder().getCombinedGazeVectorFocalPlaneIntersectionX(),
                    getDatagramPacketDecoder().getCombinedGazeVectorFocalPlaneIntersectionY()
                    ));
            
            //setCursorPosition(getCoordinateConverter().millimeterToPixel(0,0));
            int x = (int)getCursorPosition().getX();
            int y = (int)getCursorPosition().getY();
            
            getCoordinateConverter().setScreenHeight(getHeight());
            getCoordinateConverter().setScreenWidth(getWidth());
            getGraphics2D().drawLine(0,y,getWidth(),y);
            getGraphics2D().drawLine(x,0,x,getHeight());
        }catch(Exception e){};
    }
}