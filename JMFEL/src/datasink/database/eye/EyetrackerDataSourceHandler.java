/*
 * EEGDataSinkHandler.java
 *
 * Created on 17. Juli 2007, 14:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package datasink.database.eye;
import eye.insight.InsightDatagramPacketDecoder;
import datasink.database.AbstractDatabaseSourceHandler;
import java.awt.geom.Point2D;
import java.text.SimpleDateFormat;
import java.util.Date;
import utilities.graphics.CoordinateConverter;

/**
 * @author Administrator
 */

public class EyetrackerDataSourceHandler extends AbstractDatabaseSourceHandler{
    private InsightDatagramPacketDecoder dataGramPacketDecoder;
    private Date date = new Date();
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
    private String sessionID = "1";
    private CoordinateConverter coordinateConverter;
    private int screenWidth = 800;
    private int screenHeight = 600;
    private int horizontalMaximum = 350;
    private int horizontalMinimum = -350;
    private int verticalMaximum = 550;
    private int verticalMinimum = -50;
    private int batchCounter = 0;
    private int samplerate = 60;
    private int batchChunkLength = samplerate;
    private String[] inserts = new String[batchChunkLength];
    private StringBuffer insert = new StringBuffer();
    private String delimiter = ",";
    private boolean running = false;
    private boolean enabled = true;
    
    /** Creates a new instance of EyeTrackerDataSink */
    public EyetrackerDataSourceHandler() {
        
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
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isRunning() {
        return running;
    }
    
    public void setRunning(boolean running) {
        this.running = running;
    }
    
    public int getBatchChunkLength() {
        return batchChunkLength;
    }
    
    public void setBatchChunkLength(int batchChunkLength) {
        this.batchChunkLength = batchChunkLength;
        String[] inserts = new String[batchChunkLength];
    }
    
    public InsightDatagramPacketDecoder getDataGramPacketDecoder() {
        return dataGramPacketDecoder;
    }
    
    public SimpleDateFormat getFormatter() {
        return formatter;
    }
    
    public int getSamplerate() {
        return samplerate;
    }
    
    public void setSamplerate(int samplerate) {
        this.samplerate = samplerate;
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
    
    public void close(){
        super.close();
        setRunning(false);
    }
    
    public void open(){
        super.open();
        String insert = "Insert into data.tblEyetrackSetup values ("+getSessionID()+","+getSamplerate()+")";
        System.out.println(insert);
        execute(insert);
        setRunning(true);
    }
    
    //here, the data is decoded and written to the data base.
    public void process(){
        if(isEnabled()){
            getDataGramPacketDecoder().setBuffer(getData());
            
            if(batchCounter==inserts.length){
                batchCounter=0;
                executeBatch(inserts);
            }
            insert = new StringBuffer();
            insert.append("Insert into TMP.tmpEyetrackdata values (");
            insert.append(getSessionID());
            insert.append(delimiter);
            getDate().setTime(System.currentTimeMillis());
            insert.append("'"+getFormatter().format(getDate())+"'");
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getTimeStampEyeOpening());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getPacketId());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getEyeOpeningLeft());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getEyeOpeningRight());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getEyeLeftConfidence());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getEyeRightConfidence());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getTimeStampFatigue());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getFatiguePerclos70());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getFatiguePerclos80());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getFatiguePerclosEMEA70());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getFatiguePerclosEMEA80());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getTimeStampPupil());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getPupilPositionLeftX());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getPupilPositionLeftY());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getPupilPositionRightX());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getPupilPositionRightY());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getPupilAreaLeft());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getPupilAreaRight());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getPupilDiameterLeft());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getPupilDiameterRight());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getTimeStampStatus());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getSystemStatus());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getSystemStatusConfidence());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getTimeStampHeadPose());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getHeadX());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getHeadY());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getHeadZ());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getHeadPitch());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getHeadYaw());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getHeadRoll());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getTimeStampGaze());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getLeftEyeX());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getLeftEyeY());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getLeftEyeZ());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getRightEyeX());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getRightEyeY());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getRightEyeZ());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getLeftGazeFocalPlaneIntersectionX());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getLeftGazeFocalPlaneIntersectionY());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getLeftGazeFocalPlaneIntersectionZ());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getRightGazeFocalPlaneIntersectionX());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getRightGazeFocalPlaneIntersectionY());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getRightGazeFocalPlaneIntersectionZ());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getCombinedGazeVectorOriginX());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getCombinedGazeVectorOriginY());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getCombinedGazeVectorOriginZ());
            insert.append(delimiter);
            Point2D gaze = coordinateConverter.millimeterToPixel(getDataGramPacketDecoder().getCombinedGazeVectorFocalPlaneIntersectionX(),getDataGramPacketDecoder().getCombinedGazeVectorFocalPlaneIntersectionY());
            insert.append(gaze.getX()/(double)coordinateConverter.getScreenWidth());
            insert.append(delimiter);
            insert.append((gaze.getY()/(double)coordinateConverter.getScreenHeight()));
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getCombinedGazeVectorFocalPlaneIntersectionZ());
            insert.append(delimiter);
            insert.append(getDataGramPacketDecoder().getHitAOI());
            insert.append(")");
            inserts[batchCounter] = insert.toString();
            //System.out.println(inserts[batchCounter]);
            batchCounter++;
        }
    }
}