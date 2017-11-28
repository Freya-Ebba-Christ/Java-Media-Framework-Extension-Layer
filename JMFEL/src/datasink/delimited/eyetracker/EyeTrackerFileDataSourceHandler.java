/*
 * EyeTrackerDataSink.java
 *
 * Created on 16. April 2007, 11:50
 *
 */

package datasink.delimited.eyetracker;

import eye.insight.InsightDatagramPacketDecoder;
import datasink.delimited.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.media.MediaLocator;
import utilities.graphics.CoordinateConverter;


/**
 *
 * @author christ
 */

public class EyeTrackerFileDataSourceHandler extends AbstractDelimitedFileDataSourceHandler{
    private InsightDatagramPacketDecoder dataGramPacketDecoder;
    private Date date = new Date();
    private SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yy hh:mm:ss.S");
    private String sessionID = "12";
    private CoordinateConverter coordinateConverter;
    private int screenWidth = 800;
    private int screenHeight = 600;
    private int horizontalMaximum = 350;
    private int horizontalMinimum = -350;
    private int verticalMaximum = 550;
    private int verticalMinimum = -50;
   
    /** Creates a new instance of EyeTrackerDataSink */
    public EyeTrackerFileDataSourceHandler() {
        // set the titles for each column
        setTitle("SessionID; MainTimeStamp ;TimeStampEyeOpening,PacketId, EyeOpeningLeft, EyeOpeningRight, EyeLeftConfidence, EyeRightConfidence, " +
                "TimeStampFatigue, FatiguePerclos70, FatiguePerclos80, FatiguePerclosEMEA70, FatiguePerclosEMEA80, " +
                "TimeStampPupil, PupilPositionLeftX, PupilPositionLeftY, PupilPositionRightX, PupilPositionRightY, " +
                "PupilAreaLeft, PupilAreaRight, PupilDiameterLeft, PupilDiameterRight, TimeStampStatus, " +
                "SystemStatus, SystemStatusConfidence, TimeStampHeadPose, HeadX, HeadY, HeadZ, HeadPitch, HeadYaw, HeadRoll, " +
                "TimeStampGaze, LeftEyeX, LeftEyeY, LeftEyeZ, RightEyeX, RightEyeY, RightEyeZ, LeftGazeFocalPlaneIntersectionX, " +
                "LeftGazeFocalPlaneIntersectionY, LeftGazeFocalPlaneIntersectionZ, RightGazeFocalPlaneIntersectionX, RightGazeFocalPlaneIntersectionY, " +
                "RightGazeFocalPlaneIntersectionZ, CombinedGazeVectorOriginX, CombinedGazeVectorOriginY, CombinedGazeVectorOriginZ, " +
                "CombinedGazeVectorFocalPlaneIntersectionX, CombinedGazeVectorFocalPlaneIntersectionY, CombinedGazeVectorFocalPlaneIntersectionZ, HitAOI\n");
        setDelimiter(';');
        setDataGramPacketDecoder(new InsightDatagramPacketDecoder());
        coordinateConverter = new CoordinateConverter();
        coordinateConverter.setHorizontalMaximum(getHorizontalMaximum());
        coordinateConverter.setHorizontalMinimum(getHorizontalMinimum());
        coordinateConverter.setVerticalMaximum(getVerticalMaximum());
        coordinateConverter.setVerticalMinimum(getVerticalMinimum());
        coordinateConverter.setScreenWidth(getScreenWidth());
        coordinateConverter.setScreenHeight(getScreenHeight());
    }
    
    public void setDataGramPacketDecoder(InsightDatagramPacketDecoder dataGramPacketDecoder) {
        this.dataGramPacketDecoder = dataGramPacketDecoder;
    }
    
    public InsightDatagramPacketDecoder getDataGramPacketDecoder() {
        return dataGramPacketDecoder;
    }
    
    public SimpleDateFormat getFormatter() {
        return formatter;
    }
    
    public void setFormatter(SimpleDateFormat formatter) {
        this.formatter = formatter;
    }
    
    public Date getDate() {
        return date;
    }

    public int getHorizontalMaximum() {
        return horizontalMaximum;
    }

    public int getHorizontalMinimum() {
        return horizontalMinimum;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getVerticalMaximum() {
        return verticalMaximum;
    }

    public int getVerticalMinimum() {
        return verticalMinimum;
    }

    public void setHorizontalMaximum(int horizontalMaximum) {
        getCoordinateConverter().setHorizontalMaximum(horizontalMaximum);
        this.horizontalMaximum = horizontalMaximum;
    }

    public void setScreenHeight(int screenHeight) {
        getCoordinateConverter().setScreenHeight(screenHeight);
        this.screenHeight = screenHeight;
    }

    public void setHorizontalMinimum(int horizontalMinimum) {
        getCoordinateConverter().setHorizontalMinimum(horizontalMinimum);
        this.horizontalMinimum = horizontalMinimum;
    }

    public void setScreenWidth(int screenWidth) {
        getCoordinateConverter().setScreenWidth(screenWidth);
        this.screenWidth = screenWidth;
    }

    public void setVerticalMaximum(int verticalMaximum) {
        getCoordinateConverter().setVerticalMaximum(verticalMaximum);
        this.verticalMaximum = verticalMaximum;
    }

    public void setVerticalMinimum(int verticalMinimum) {
        getCoordinateConverter().setVerticalMinimum(verticalMinimum);
        this.verticalMinimum = verticalMinimum;
    }

    public CoordinateConverter getCoordinateConverter() {
        return coordinateConverter;
    }

    public void setCoordinateConverter(CoordinateConverter coordinateConverter) {
        this.coordinateConverter = coordinateConverter;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getSessionID() {
        return sessionID;
    }
    
    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
    
    //here, the data is decoded and written to disk.
    public void process(){
        
        //decode the eyetracker data by using the datagram decoder class
        
        getDataGramPacketDecoder().setBuffer(getData());
        try{
            getBufferedWriter().write(getSessionID()+getDelimiter());
            getDate().setTime(System.currentTimeMillis());
            getBufferedWriter().write(getFormatter().format(getDate())+getDelimiter());
            getDate().setTime((long)getDataGramPacketDecoder().getTimeStampEyeOpening());
            getBufferedWriter().write(getFormatter().format(getDate())+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getPacketId()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getEyeOpeningLeft()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getEyeOpeningRight()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getEyeLeftConfidence()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getEyeRightConfidence()+getDelimiter());
            getDate().setTime((long)getDataGramPacketDecoder().getTimeStampFatigue());
            getBufferedWriter().write(getFormatter().format(getDate())+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getFatiguePerclos70()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getFatiguePerclos80()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getFatiguePerclosEMEA70()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getFatiguePerclosEMEA80()+getDelimiter());
            getDate().setTime((long)getDataGramPacketDecoder().getTimeStampPupil());
            getBufferedWriter().write(getFormatter().format(getDate())+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getPupilPositionLeftX()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getPupilPositionLeftY()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getPupilPositionRightX()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getPupilPositionRightY()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getPupilAreaLeft()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getPupilAreaRight()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getPupilDiameterLeft()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getPupilDiameterRight()+getDelimiter());
            getDate().setTime((long)getDataGramPacketDecoder().getTimeStampStatus());
            getBufferedWriter().write(getFormatter().format(getDate())+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getSystemStatus()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getSystemStatusConfidence()+getDelimiter());
            getDate().setTime((long)getDataGramPacketDecoder().getTimeStampHeadPose());
            getBufferedWriter().write(getFormatter().format(getDate())+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getHeadX()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getHeadY()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getHeadZ()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getHeadPitch()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getHeadYaw()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getHeadRoll()+getDelimiter());
            getDate().setTime((long)getDataGramPacketDecoder().getTimeStampGaze());
            getBufferedWriter().write(getFormatter().format(getDate())+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getLeftEyeX()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getLeftEyeY()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getLeftEyeZ()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getRightEyeX()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getRightEyeY()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getRightEyeZ()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getLeftGazeFocalPlaneIntersectionX()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getLeftGazeFocalPlaneIntersectionY()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getLeftGazeFocalPlaneIntersectionZ()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getRightGazeFocalPlaneIntersectionX()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getRightGazeFocalPlaneIntersectionY()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getRightGazeFocalPlaneIntersectionZ()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getCombinedGazeVectorOriginX()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getCombinedGazeVectorOriginY()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getCombinedGazeVectorOriginZ()+getDelimiter());
            Point2D gaze = coordinateConverter.millimeterToPixel(getDataGramPacketDecoder().getCombinedGazeVectorFocalPlaneIntersectionX(),getDataGramPacketDecoder().getCombinedGazeVectorFocalPlaneIntersectionY());
            getBufferedWriter().write((gaze.getX()/(double)coordinateConverter.getScreenWidth())+getDelimiter());
            getBufferedWriter().write((gaze.getY()/(double)coordinateConverter.getScreenHeight())+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getCombinedGazeVectorFocalPlaneIntersectionZ()+getDelimiter());
            getBufferedWriter().write(getDataGramPacketDecoder().getHitAOI()+",\n");
        } catch (IOException e) {
            System.out.println();
        }
    }
    // just for testing
    public static void main(String[] args) {
        EyeTrackerFileDataSourceHandler aEyeTrackerFileDataSourceHandler = new EyeTrackerFileDataSourceHandler();
        aEyeTrackerFileDataSourceHandler.setOutputLocator(new MediaLocator("file://C:\\Dokumente und Einstellungen\\Urkman_2\\MicroSleepDetector\\fastICA\\mixedSource.wav"));
        System.out.println(aEyeTrackerFileDataSourceHandler.getFile().getPath());
    }
}
