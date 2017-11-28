/*
 * AbstractDigitalController.java
 *
 * Created on 30. August 2007, 18:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package examples.application_container.game.pong;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier.Key;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import utilities.game.Simple2DGame;

/**
 *
 * @author Administrator
 */

public class SimpleDigitalGameInputDevice{
    private Simple2DGame simple2DGame;
    private Controller[] controllers;
    private Controller keyboard;
    private KeyBoardPollThread keyBoardPollThread;
    
    public SimpleDigitalGameInputDevice(){
        controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        keyboard=null;
        for(int i=0;i<controllers.length && keyboard==null;i++) {
            if(controllers[i].getType()==Controller.Type.KEYBOARD) {
                keyboard = controllers[i];
            }
        }
        if(keyboard==null) {
            System.out.println("Found no keyboard");
            System.exit(0);
        }
    }
    
    public Simple2DGame getSimple2DGame() {
        return simple2DGame;
    }
    
    public void setSimple2DGame(Simple2DGame simple2DGame) {
        this.simple2DGame = simple2DGame;
    }
    
    public void connect(){
        keyBoardPollThread = new KeyBoardPollThread();
        keyBoardPollThread.startThread();
    }
    
    public void disconnect(){
        keyBoardPollThread.stopThread();
    }
    
    private class KeyBoardPollThread extends Thread{
        private boolean running = true;
        
        public KeyBoardPollThread(){
        }
        
        public boolean isRunning() {
            return running;
        }
        
        public void setRunning(boolean running) {
            this.running = running;
        }
        
        public void startThread(){
            setRunning(true);
            start();
        }
        
        public void stopThread(){
            setRunning(false);
        }
        
        public void run() {
            while(isRunning()) {
                keyboard.poll();
                Component[] components = keyboard.getComponents();
                for(int i=0;i<components.length;i++) {
                    Key key = (Key)components[i].getIdentifier();
                    
                    if(key == Key.LCONTROL){
                        if(components[i].getPollData()==1.0f) {
                            getSimple2DGame().moveLeft();
                        }
                    }else if(key == Key.RCONTROL){
                        if(components[i].getPollData()==1.0f) {
                            getSimple2DGame().moveRight();
                        }                }
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}