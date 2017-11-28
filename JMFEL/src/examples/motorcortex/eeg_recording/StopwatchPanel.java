/*
 * StopwatchPanel.java
 *
 * Created on 4. Oktober 2007, 13:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package examples.motorcortex.eeg_recording;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import utilities.graphics.StringHelper;
import visual_signal_components.passive_rendering.AbstractTextPanel;

/**
 *
 * @author Administrator
 */
public class StopwatchPanel extends AbstractTextPanel implements ActionListener{
    private Font font = new Font("Arial Black", Font.BOLD, 25);
    private boolean stopped = true;
    private double handPosition = 0.0;
    private int numberCounter = 5;
    private StringHelper stringhelper = new StringHelper();
    private int ticksToGo = 20;
    private int tickDelay = 10;
    private long time = 0;
    private long duration = 0;
    public static final int LEFT=0;
    public static final int RIGHT=1;
    private int currentDirection = RIGHT;
    private boolean enableLeftRight = true;
    private boolean enableClock = true;
    private boolean newDirectionReady = false;
    
    public StopwatchPanel() {
    }
    
    public void setNewDirectionReady(boolean newDirectionReady) {
        this.newDirectionReady = newDirectionReady;
    }
    
    public boolean isNewDirectionReady() {
        return newDirectionReady;
    }
    
    public boolean isEnableClock() {
        return enableClock;
    }
    
    public boolean isEnableLeftRight() {
        return enableLeftRight;
    }
    
    public void setEnableClock(boolean enableClock) {
        this.enableClock = enableClock;
    }
    
    public void setEnableLeftRight(boolean enableLeftRight) {
        this.enableLeftRight = enableLeftRight;
    }
    
    public int getCurrentDirection() {
        return currentDirection;
    }
    
    public void setCurrentDirection(int currentDirection) {
        this.currentDirection = currentDirection;
    }
    
    public long getDuration() {
        return duration;
    }
    
    public void setDuration(long duration) {
        this.duration = duration;
    }
    
    private void setNewDirection(){
        double randomNumber = Math.random();
        if(randomNumber>0.5){
            setCurrentDirection(LEFT);
        }else if(randomNumber<=0.5){
            setCurrentDirection(RIGHT);
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        if(stopped){
            if(isEnableClock()){
                if(numberCounter>0&&ticksToGo>0){
                    tickDelay--;
                    if(tickDelay==0){
                        numberCounter--;
                        tickDelay=10;
                    }
                }
                if(numberCounter==0&&ticksToGo>0){
                    ticksToGo--;
                }
                if(numberCounter==0&&ticksToGo==0){
                    setStopped(false);
                    numberCounter=5;
                    ticksToGo = 20;
                    handPosition=0.0;
                }
            }else {
                setStopped(false);
            }
        }else if(!stopped){
            handPosition+=0.01;
            if(handPosition>1.0){
                handPosition=0.0;
                setStopped(true);
            }
        }
    }
    
    public boolean isStopped() {
        return stopped;
    }
    
    public void setStopped(boolean stopped) {
        this.stopped = stopped;
        if(!stopped){
            time = System.nanoTime();
        }else if(stopped){
            duration = System.nanoTime()-time;
        }
    }
    
    private void drawDirection(int direction, Graphics2D g2){
        int ovalWidth = getWidth()/10;
        if(direction==LEFT){
            g2.setColor(Color.RED);
            g2.fillOval(100,100,ovalWidth,ovalWidth);
        }else if(direction==RIGHT){
            g2.setColor(Color.GREEN);
            g2.fillOval(getWidth()-(int)(ovalWidth*1.5),100,ovalWidth,ovalWidth);
        }
        g2.setColor(Color.WHITE);
        g2.setFont(font);
    }
    
    private void countDown(Graphics2D g2){
        if(stopped){
            if(numberCounter>0){
                font = new Font("Arial Black", Font.BOLD, getHeight()/5);
                g2.setFont(font);
                stringhelper.setGraphics(g2);
                String text = String.valueOf(numberCounter);
                g2.drawString(text, getWidth()/2-stringhelper.getStringWidth(font,text)/2,getHeight()/2+(stringhelper.getStringHeight(text)/4));
            }
            
            if(numberCounter==0){
                font = new Font("Arial Black", Font.BOLD, getHeight()/5);
                g2.setFont(font);
                stringhelper.setGraphics(g2);
                String text = "GO!";
                g2.drawString(text, getWidth()/2-stringhelper.getStringWidth(font,text)/2,getHeight()/2+(stringhelper.getStringHeight(text)/4));
            }
        }
    }
    
    private void drawHand(Graphics2D g2, double percent, int minRadius, int maxRadius, int xPosition, int yPosition) {
        
        double radians = (0.5 - percent) * Math.PI*2;
        double sine   = Math.sin(radians);
        double cosine = Math.cos(radians);
        
        int dxmin = xPosition + (int)(minRadius * sine);
        int dymin = yPosition + (int)(minRadius * cosine);
        int dxmax = xPosition + (int)(maxRadius * sine);
        int dymax = yPosition + (int)(maxRadius * cosine);
        g2.drawLine(dxmin, dymin, dxmax, dymax);
    }
    
    public void renderGraphics(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0,getWidth(),getHeight());
        g2.setColor(Color.WHITE);
        if(isEnableClock()){
            drawHand(g2,handPosition,0,getHeight()/2-50, getWidth()/2,getHeight()/2);
            g2.drawOval(getWidth()/2-getHeight()/2,0, getHeight(), getHeight());
            countDown(g2);
        }
        if(isEnableLeftRight()){
            
            if(!isNewDirectionReady()&&stopped){
                setNewDirection();
                setNewDirectionReady(true);
            }
            if(!stopped){
                setNewDirectionReady(false);
            }
            drawDirection(getCurrentDirection(),g2);
        }
    }
}