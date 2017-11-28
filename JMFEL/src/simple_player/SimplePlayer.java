package simple_player;
import custom_controller.CustomController;
import javax.media.*;
import javax.media.protocol.*;
import javax.media.control.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

public class SimplePlayer extends Frame{
    
    private Player player = null;
    private CustomController controllerListener = null;
    private DataSource dataSource = null;
    private Component cc;
    private Component vc;
    private boolean started = false;
    
    public SimplePlayer(DataSource aDataSource) {
        dataSource = aDataSource;
        createPlayer();
    }
    
    public SimplePlayer() {
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
    
    public void renderSignal(byte[] signal){
    }
    
    public void center(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int)dim.getWidth()/2-getWidth()/2,(int)dim.getHeight()/2-getHeight()/2);
    }
    
    public void renderSignal(){
    }
    
    public Component getVisualComponent(){
        return player.getVisualComponent();
    }
    
    public FramePositioningControl getFramePositionControl(){
        return (FramePositioningControl)player.getControl("javax.media.control.FramePositioningControl");
    }
    
    public Component getControlPanelComponent(){
        return player.getControlPanelComponent();
    }
    
    private void createPlayer(){
        
        try {
            player = Manager.createPlayer(dataSource);
        } catch (Exception e) {
            System.err.println("Failed to create a player from the given DataSource: " + e);
            
        }
        controllerListener = new CustomController(player);
        
        player.addControllerListener(controllerListener);
        
        // Realize the player.
        player.prefetch();
        if (!controllerListener.waitForState(player.Prefetched)) {
            System.err.println("Failed to realize the player.");
        }
        
        // Display the visual & control component if there's one.
        
        setLayout(new BorderLayout());
        
        
        if ((vc = player.getVisualComponent()) != null) {
            add("Center", vc);
        }
        
        if ((cc = player.getControlPanelComponent()) != null) {
            add("South", cc);
        }
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                player.close();
                System.exit(0);
            }
        });
        pack();
    }
    
    public void start(Time aTime) {
        setStarted(true);
        player.syncStart(aTime);
    }
    
    public void start(){
        setStarted(true);
        player.start();
    }
    
    public void stop(){
        setStarted(false);
        player.stop();
    }
}