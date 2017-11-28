/*
 *This is a highspeed fixed sized array based ringbuffer.
 *Usually callbacks will register themselves as callables.
 *This ia a 1:1 relation and as basic and as fast as it can get.
 *However, you can also add observers to the buffer. As many as you want.
 */

package utilities;

import java.util.Observable;

public class DoubleDataBuffer extends Observable{
    private int arraySize;
    private double buffer[];
    private double array[];
    private int currentPosition;
    private int samples = 0;
    private int fps = 30;
    private Callable callBack;
    private int id = 0;
    private int distance = 1;
    private boolean enableCallbacks = true;
    private float maxVoltage = 250000f;
    public static final String UNIT_MICROVOLT = "MICROVOLT";
    public static final String UNIT_MILLIVOLT = "MILLIVOLT";
    public static final String UNIT_VOLT = "VOLT";
    public static final String UNIT_PERCENT = "PERCENT";
    private String unit = UNIT_MICROVOLT;
    
    public DoubleDataBuffer() {
        arraySize = 2048;
        currentPosition = 0;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    public float getMaxVoltage() {
        return maxVoltage;
    }
    
    public void setMaxVoltage(float maxVoltage) {
        this.maxVoltage = maxVoltage;
    }
    
    public void setRefreshRate(int aValue){
        fps = aValue;
        distance=(int)Math.rint(arraySize/fps);
    }
    
    public void setDistance(int distance) {
        this.distance = distance;
    }
    
    public void setEnableCallbacks(boolean enableCallbacks) {
        this.enableCallbacks = enableCallbacks;
    }
    
    public boolean isEnableCallbacks() {
        return enableCallbacks;
    }
    
    public int getRefreshRate(){
        return fps;
    }
    
    public int getDistance(){
        return distance;
    }
    
    public DoubleDataBuffer(int size) {
        arraySize = size;
        currentPosition = 0;
        array = new double[size];
        buffer = new double[size];
    }
    
    public void addElement(double element) {
        if(currentPosition < buffer.length)
            buffer[currentPosition++] = element;
        currentPosition %= buffer.length;
        samples++;
        if (samples>=distance){
            array = makeCopy();
            if(enableCallbacks&&callBack!=null){
                //notify callbacks and observers
                callBack.performAction(id);
                callBack.performAction();
                setChanged();
                notifyObservers();
            }
            samples = 0;
        }
    }
    
    public double getElementAt(int pos){
        double aValue = 0;
        if(currentPosition+pos<buffer.length){
            aValue = buffer[currentPosition+pos];
        }else {
            aValue = buffer[currentPosition+pos-buffer.length];
        }
        return aValue;
    }
    
    public void registerCallback(Callable aCallable){
        //assure, that only valid callbacks are registered
        if(aCallable!=null){
            callBack = aCallable;
        }
    }
    
    public int getDataBufferSize(){
        return arraySize;
    }
    
    private double[] makeCopy() {
        System.arraycopy(buffer, currentPosition, array, 0, buffer.length - currentPosition);
        System.arraycopy(buffer, 0, array, buffer.length - currentPosition, buffer.length - (buffer.length - currentPosition));
        return array;
    }
    
    public int getID(){
        return id;
    }
    
    public void setID(int aValue){
        id = aValue;
    }
    
    public double[] toArray() {
        return array;
    }
    
    public static void main(String[] args) {
        DoubleDataBuffer aDoubleDataBuffer = new DoubleDataBuffer(9);
        aDoubleDataBuffer.setEnableCallbacks(false);
        for (int indx = 0;indx<10;indx++){
            aDoubleDataBuffer.addElement((double)indx);
        }
        System.out.println(aDoubleDataBuffer.getElementAt(0));
        System.out.println(aDoubleDataBuffer.getElementAt(1));
        System.out.println(aDoubleDataBuffer.getElementAt(2));
        System.out.println(aDoubleDataBuffer.getElementAt(3));
        System.out.println(aDoubleDataBuffer.getElementAt(4));
        System.out.println(aDoubleDataBuffer.getElementAt(5));
        
        double[] buf = aDoubleDataBuffer.toArray();
        for (int indx = 0;indx<9;indx++){
            System.out.println(buf[indx]);
        }
    }
}