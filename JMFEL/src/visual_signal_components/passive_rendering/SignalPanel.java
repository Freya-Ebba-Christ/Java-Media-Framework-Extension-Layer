package visual_signal_components.passive_rendering;
import java.awt.BasicStroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import utilities.DoubleDataBufferContainer;
import utilities.math.Rounding;

public class SignalPanel extends AbstractSignalPanel{
    
    private int offset = 0;
    private int arrayIndex = 0;
    private int strokeThickness = 2;
    private float[] dashPattern = {2,0,2,2};
    private BasicStroke dashed_stroke = new BasicStroke(1, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 10, dashPattern, 0);
    private BasicStroke regularStroke = new BasicStroke(1);
    private int bufferLength = 0;
    private int sampleRate = 0;
    private float maxVoltage = 0f;
    private DoubleDataBufferContainer aDoubleDataBufferContainer;
    private boolean drawDashedSignalLine = false;
    private GeneralPath path = new GeneralPath();
    private AffineTransform trans;
    private int signalXOffset = 0;
    private String MICROVOLT = "\u00b5V";
    private String MILLIVOLT = "mV";
    private String VOLT = "V";
    private String PERCENT = "%";
    public static final String UNIT_MICROVOLT = "MICROVOLT";
    public static final String UNIT_MILLIVOLT = "MILLIVOLT";
    public static final String UNIT_VOLT = "VOLT";
    public static final String UNIT_PERCENT = "PERCENT";
    private String unit = UNIT_MICROVOLT;
    private boolean durationShown = true;
    
    public SignalPanel(){
    }
    
    public boolean isDurationShown() {
        return durationShown;
    }
    
    public void setDurationShown(boolean durationShown) {
        this.durationShown = durationShown;
    }
    
    public int getSignalXOffset() {
        return signalXOffset;
    }
    
    public void setSignalXOffset(int signalXOffset) {
        this.signalXOffset = signalXOffset;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    public int getBufferLength() {
        return bufferLength;
    }
    
    public void setBufferLength(int bufferLength) {
        this.bufferLength = bufferLength;
    }
    
    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }
    
    public int getSampleRate() {
        return sampleRate;
    }
    
    public DoubleDataBufferContainer getDoubleDataBufferContainer() {
        return aDoubleDataBufferContainer;
    }
    
    public void setDoubleDataBufferContainer(DoubleDataBufferContainer aDoubleDataBufferContainer) {
        this.aDoubleDataBufferContainer = aDoubleDataBufferContainer;
    }
    
    public float getMaxVoltage() {
        return maxVoltage;
    }
    
    public void setMaxVoltage(float maxVoltage) {
        this.maxVoltage = maxVoltage;
    }
    
    public void drawTitle(){
        
        String duration = Integer.toString(getBufferLength()/getSampleRate())+"sec";
        
        //backup the current drawing color
        backUpCurrentColor();
        getGraphics2D().setColor(getTitleColor());
        //draw title
        getGraphics2D().drawString(getTitle(),getLocationX()+getWidth()/2-getStringWidth()/2,getLocationY()-getStringHeight()/4);
        //draw voltages
        
        if(getUnit().equalsIgnoreCase(UNIT_MILLIVOLT)){
            getGraphics2D().drawString(Float.toString(getMaxVoltage()/1000f)+MILLIVOLT,getLocationX()-getStringHelper().getStringWidth(getGraphics2D().getFont(),Float.toString(getMaxVoltage()/1000f)+MILLIVOLT),getLocationY()+getStringHeight()/4);
            getGraphics2D().drawString("-"+Float.toString(getMaxVoltage()/1000f)+MILLIVOLT,getLocationX()-getStringHelper().getStringWidth(getGraphics2D().getFont(),"-"+Float.toString(getMaxVoltage()/1000f)+MILLIVOLT),getLocationY()+getHeight()+getStringHeight()/4);
        }else if(getUnit().equalsIgnoreCase(UNIT_VOLT)){
            getGraphics2D().drawString(Float.toString(getMaxVoltage()/1000000f)+VOLT,getLocationX()-getStringHelper().getStringWidth(getGraphics2D().getFont(),Float.toString(getMaxVoltage()/1000000f)+VOLT),getLocationY()+getStringHeight()/4);
            getGraphics2D().drawString("-"+Float.toString(getMaxVoltage()/1000000f)+VOLT,getLocationX()-getStringHelper().getStringWidth(getGraphics2D().getFont(),"-"+Float.toString(getMaxVoltage()/1000000f)+VOLT),getLocationY()+getHeight()+getStringHeight()/4);
        }else if(getUnit().equalsIgnoreCase(UNIT_PERCENT)){
            getGraphics2D().drawString(Float.toString(getMaxVoltage())+PERCENT,getLocationX()-getStringHelper().getStringWidth(getGraphics2D().getFont(),Float.toString(getMaxVoltage())+PERCENT),getLocationY()+getStringHeight()/4);
            getGraphics2D().drawString("-"+Float.toString(getMaxVoltage())+PERCENT,getLocationX()-getStringHelper().getStringWidth(getGraphics2D().getFont(),"-"+Float.toString(getMaxVoltage())+PERCENT),getLocationY()+getHeight()+getStringHeight()/4);
        }else if(getUnit().equalsIgnoreCase(UNIT_MICROVOLT)){
            getGraphics2D().drawString(Float.toString(getMaxVoltage())+MICROVOLT,getLocationX()-getStringHelper().getStringWidth(getGraphics2D().getFont(),Float.toString(getMaxVoltage())+MICROVOLT),getLocationY()+getStringHeight()/4);
            getGraphics2D().drawString("-"+Float.toString(getMaxVoltage())+MICROVOLT,getLocationX()-getStringHelper().getStringWidth(getGraphics2D().getFont(),"-"+Float.toString(getMaxVoltage())+MICROVOLT),getLocationY()+getHeight()+getStringHeight()/4);
        }
        
        if(isDurationShown()){
            //draw signal length in seconds
            getGraphics2D().drawString(duration,getLocationX()+getWidth()-getStringHelper().getStringWidth(getGraphics2D().getFont(),duration),getLocationY()+getHeight()+getStringHeight()*3/4);
        }
        //restore the original drawing color
        restoreColor();
    }
    
    public void drawPanel() {
        //backup the current drawing color
        backUpCurrentColor();
        getGraphics2D().setColor(getBorderColor());
        getGraphics2D().drawRect(getLocationX(),getLocationY(),getWidth(),getHeight());
        getGraphics2D().setStroke(dashed_stroke);
        getGraphics2D().drawLine(getLocationX(),getLocationY()+getHeight()/2,getLocationX()+getWidth(),getLocationY()+getHeight()/2);
        getGraphics2D().drawLine(getLocationX()+getWidth()/4,getLocationY(),getLocationX()+getWidth()/4,getLocationY()+getHeight());
        getGraphics2D().drawLine(getLocationX()+getWidth()/2,getLocationY(),getLocationX()+getWidth()/2,getLocationY()+getHeight());
        getGraphics2D().drawLine(getLocationX()+getWidth()*3/4,getLocationY(),getLocationX()+getWidth()*3/4,getLocationY()+getHeight());
        getGraphics2D().setStroke(regularStroke);
        //restore the original drawing color
        restoreColor();
    }
    
    public void setStrokeThickness(int strokeThickness) {
        this.strokeThickness = strokeThickness;
    }
    
    public int getStrokeThickness() {
        return strokeThickness;
    }
    
    public BasicStroke getDashed_stroke() {
        return dashed_stroke;
    }
    
    public BasicStroke getRegularStroke() {
        return regularStroke;
    }
    
    public void drawSignal(double[] array) {
        try{
            path.reset();
            trans = new AffineTransform();
            path.moveTo(0, 0);
            int stepSize = 1;
            if ((float)getWidth()/(float)getBufferLength()<1){
                stepSize = (int)Rounding.round(1f/((float)getWidth()/(float)getBufferLength()),0);
            }
            getGraphics2D().setColor(getSignalColor());
            if(drawDashedSignalLine){
                getGraphics2D().setStroke(dashed_stroke);
            }
            
            for (int x = 0;x<array.length;x+=stepSize){
                path.lineTo(x+getSignalXOffset(), convertToScreenCoordinates(1.0, (array[x]-getDCOffset())/getMaxVoltage(),getHeight()));
            }
            trans.translate(getLocationX()+2,getLocationY());
            trans.scale((float)getWidth()/(float)getBufferLength(),1.0);
            path.transform(trans);
            getGraphics2D().draw(path);
            getGraphics2D().setStroke(regularStroke);
        }catch(Exception e){System.out.println(e);};
    }
    
    public void drawSignal(int channel) {
        try{
            path.reset();
            trans = new AffineTransform();
            path.moveTo(0, 0);
            
            getGraphics2D().setColor(getSignalColor());
            if(drawDashedSignalLine){
                getGraphics2D().setStroke(dashed_stroke);
            }
            
            for (int x = 0;x<getBufferLength();x++){
                path.lineTo(x+getSignalXOffset(), convertToScreenCoordinates(1.0, (getDoubleDataBufferContainer().getDataBuffer(channel).toArray()[x]-getDCOffset())/getMaxVoltage(),getHeight()));
            }
            trans.translate(getLocationX()+2,getLocationY());
            trans.scale((float)getWidth()/(float)getBufferLength(),1.0);
            path.transform(trans);
            getGraphics2D().draw(path);
            getGraphics2D().setStroke(regularStroke);
        }catch(Exception e){System.out.println(e);};
    }
    
    public int convertToScreenCoordinates(double factor, double aVal, int height){
        double screenCoordinate = 0;
        //Assuming values between -1 and +1
        //We just shift the values into the interval 0,1
        
        //to get values between 0 and 1
        //for scale factors > 1.0 the values will, of course not be within the 0,1 interval
        screenCoordinate = (aVal*factor+1.0)/2.0;
        if (screenCoordinate>1){
            screenCoordinate=1;
        }
        
        if (screenCoordinate<0){
            screenCoordinate=0;
        }
        // to get values between 0 and getHeight(). Note: 0,0 is in the upper left corner.
        screenCoordinate = height-height*screenCoordinate;
        return (int)Math.rint(screenCoordinate);
    }
}