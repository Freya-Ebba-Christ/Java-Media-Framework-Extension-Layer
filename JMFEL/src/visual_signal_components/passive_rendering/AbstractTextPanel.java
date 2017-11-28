/*
 * AbstractTextPanel.java
 *
 * Created on 20. August 2007, 18:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package visual_signal_components.passive_rendering;

/**
 *
 * @author Administrator
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import utilities.graphics.StringHelper;

public abstract class AbstractTextPanel{
    
    private String text="9999";
    private int width = 0;
    private int height = 0;
    private BasicStroke lineStroke = new BasicStroke(3);
    private Stroke backupStoke = new BasicStroke(1);
    private Color backupColor;
    private Color color;
    private StringHelper stringHelper = new StringHelper();
    private int x = 0;
    private int y = 0;
    
    public AbstractTextPanel(){
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String value) {
        text = value;
    }
    
    public void setBackupColor(Color backupColor) {
        this.backupColor = backupColor;
    }
    
    public Color getBackupColor() {
        return backupColor;
    }
    
    public void setLineStroke(BasicStroke value) {
        lineStroke = value;
    }
    
    public void setBackupStoke(Stroke backupStoke) {
        this.backupStoke = backupStoke;
    }
    
    public BasicStroke getLineStroke() {
        return lineStroke;
    }
    
    public Stroke getBackupStoke() {
        return backupStoke;
    }
    
    private void backup(Graphics2D g2){
        setBackupColor(g2.getColor());
        setBackupStoke(g2.getStroke());
        g2.setStroke(getLineStroke());
    }
    
    public StringHelper getStringHelper() {
        return stringHelper;
    }
    
    public void setStringHelper(StringHelper stringHelper) {
        this.stringHelper = stringHelper;
    }
    
    private void restore(Graphics2D g2){
        g2.setColor(getBackupColor());
        g2.setStroke(getBackupStoke());
    }
    
    public void render(Graphics2D g2) {
        backup(g2);
        getStringHelper().setGraphics(g2);
        renderGraphics(g2);
        restore(g2);
    }
    
    public abstract void renderGraphics(Graphics2D g2);
}