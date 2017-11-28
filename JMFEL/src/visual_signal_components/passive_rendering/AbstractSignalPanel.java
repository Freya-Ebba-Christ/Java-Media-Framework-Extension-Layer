package visual_signal_components.passive_rendering;
import java.awt.Color;
import visual_signal_components.*;

public abstract class AbstractSignalPanel extends AbstractPanel{
    private Color signalColor;
    private int alpha;
    
    public AbstractSignalPanel(){
    }
    
    public void setAlpha(int aValue){
        alpha = aValue;
    }
    
    public void setSignalColor(Color aColor){
        signalColor = aColor;
    }
    
    public Color getSignalColor(){
        return signalColor;
    }
    
    public int getAlpha(){
        return alpha;
    }
    
    public void drawSignal(int channel){
    }
}