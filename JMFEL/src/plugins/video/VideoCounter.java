package plugins.video;
import java.awt.Font;
import java.text.SimpleDateFormat;
import utilities.graphics.StringHelper;
import java.util.Date;
import javax.swing.*;

public class VideoCounter extends VideoDataAccessor{
    private int dateStringWidth = 0;
    private int dateStringHeight = 0;
    private int timeStringWidth = 0;
    private int timeStringHeight = 0;
    private StringHelper aStringHelper;
    
    //The GregorianTimer is actually an actionlistener for the swing timer
    // and gets all values from a Calendar instance
    private GregorianTime gregorianTime;
    private Timer swingTimer;
    private int x_Offset = -5;
    private int y_Offset = 20;
    
    public VideoCounter() {
    }
    
    public void initializeUserData(){
        // We are NOT using a proportional font.
        //Therfore, height an width can be computed in advance.
        aStringHelper = new StringHelper();
        aStringHelper.setGraphics(getGraphics2D());
        dateStringHeight = aStringHelper.getStringHeight("  MM-dd-yy");
        dateStringWidth = aStringHelper.getStringWidth(getFont(),"  MM-dd-yy");
        timeStringHeight = aStringHelper.getStringHeight("  MM-dd-yy");
        timeStringWidth = aStringHelper.getStringWidth(getFont(),"  MM-dd-yy");
        //define insets and spacings to help position graphics elements within the video
        setInsets(5,5,5,5);
        setVerticalSpacing(dateStringHeight);
        setHorizontalSpacing(10);
        gregorianTime = new GregorianTime();
    }
    
    public void processOutData(){
        gregorianTime.update();
        getGraphics2D().drawRect(x_Offset + getWidth()-dateStringWidth-getInsetRight(),y_Offset + getHeight()-getInsetBottom()-dateStringHeight-3*getHorizontalSpacing(),dateStringWidth, dateStringHeight+getHorizontalSpacing()+1);
        getGraphics2D().drawString(gregorianTime.getDateString(),x_Offset+5 + getWidth()-timeStringWidth-3*getInsetRight(),y_Offset + getHeight()-getInsetBottom()-dateStringHeight-2*getHorizontalSpacing());
        getGraphics2D().drawString(gregorianTime.getTimeString(),x_Offset+5 + getWidth()-timeStringWidth-3*getInsetRight(),y_Offset + getHeight()-2*getInsetBottom()-timeStringHeight);
    }
}

class GregorianTime{
    
    private Font font = new Font("Arial Black", Font.BOLD, 25);
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("  MM-dd-yy");
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("  hh:mm:ss");
    private Date date = new Date();
    private String dateString = "";
    private String timeString = "";
    
    public GregorianTime(){
    }
    
    public Date getDate() {
        return date;
    }
    
    public Font getFont() {
        return font;
    }

    public SimpleDateFormat getDateFormatter() {
        return dateFormatter;
    }

    public SimpleDateFormat getTimeFormatter() {
        return timeFormatter;
    }

    public void setDateFormatter(SimpleDateFormat dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    public void setTimeFormatter(SimpleDateFormat timeFormatter) {
        this.timeFormatter = timeFormatter;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public void setFont(Font font) {
        this.font = font;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getDateString() {
        return dateString;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }
    
    public void update(){
        getDate().setTime(System.currentTimeMillis());
        setTimeString(getTimeFormatter().format(getDate()));
        setDateString(getDateFormatter().format(getDate()));
    }
}