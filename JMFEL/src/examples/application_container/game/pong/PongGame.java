/*
 * PongGame.java
 *
 * This is a simple Pong like game
 * Every component component/panel is painted by the respective painter.
 * The collision detection observes the game and its components to detect collisions. 
 */

package examples.application_container.game.pong;

import utilities.graphics.passive_rendering.ApplicationContainer;
import utilities.graphics.passive_rendering.Surface;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import utilities.game.Simple2DGame;
import utilities.game.SimplePongGame;
import utilities.graphics.BasicImageUtility;

/**
 *
 * @author Administrator
 */

public class PongGame extends ApplicationContainer implements Simple2DGame, SimplePongGame{
    
    private PaddlePainter paddlePainter;
    private SimpleDigitalGameInputDevice simpleDigitalGameInputDevice;
    private Surface surface;
    private ScorePainter scorePainter;
    private BallPainter ballPainter;
    private CollisionDetection collisionDetection;
    private TopWallPainter topWallPainter;
    private LeftWallPainter leftWallPainter;
    private RightWallPainter rightWallPainter;
    private Ballbehaviour ballbehaviour;
    
    public PongGame() {
    }
    
    public void updateScore() {
        getScorePainter().getScorePanel().increaseScore();
    }
    
    public void moveBottomLeft() {
    }
    
    public void moveBottomRight() {
    }
    
    public void moveDown() {
    }
    
    public void moveLeft() {
        if(getPaddlePainter().getPaddlePanel().getX()-getPaddlePainter().getPaddlePanel().getWidth()/2>-getPaddlePainter().getPaddlePanel().getWidth()){
            getPaddlePainter().getPaddlePanel().setX(getPaddlePainter().getPaddlePanel().getX()-1);
        }
    }
    
    public void moveRight() {
        if(getPaddlePainter().getPaddlePanel().getX()+getPaddlePainter().getPaddlePanel().getWidth()/2<getSurface().getWidth()){
            getPaddlePainter().getPaddlePanel().setX(getPaddlePainter().getPaddlePanel().getX()+1);
        }
    }
    
    public int getBallPositionX() {
        if(getBallPainter()!=null){
            return getBallPainter().getBallPanel().getX();
        }else return 0;
    }
    
    public int getBallPositionY() {
        if(getBallPainter()!=null){
            return getBallPainter().getBallPanel().getY();
        }else return 0;
    }
    
    public int getPaddlePositionX() {
        if(getPaddlePainter()!=null){
            return getPaddlePainter().getPaddlePanel().getX();
        }else return 0;
    }
    
    public int getPaddlePositionY() {
        if(getPaddlePainter()!=null){
            return getPaddlePainter().getPaddlePanel().getY();
        }else return 0;
    }
    
    public void moveTopLeft() {
    }
    
    public void moveTopRight() {
    }
    
    public void moveUP() {
    }
    
    public BallPainter getBallPainter() {
        return ballPainter;
    }
    
    public void setBallPainter(BallPainter ballPainter) {
        this.ballPainter = ballPainter;
    }
    
    public ScorePainter getScorePainter() {
        return scorePainter;
    }
    
    public Surface getSurface() {
        return surface;
    }
    
    public void setScorePainter(ScorePainter scorePainter) {
        this.scorePainter = scorePainter;
    }
    
    public void setSurface(Surface surface) {
        this.surface = surface;
    }
    
    public PaddlePainter getPaddlePainter() {
        return paddlePainter;
    }
    
    public void setPaddlePainter(PaddlePainter paddlePainter) {
        this.paddlePainter = paddlePainter;
    }
    
    public SimpleDigitalGameInputDevice getSimpleDigitalGameInputDevice() {
        return simpleDigitalGameInputDevice;
    }
    
    public void setSimpleDigitalGameInputDevice(SimpleDigitalGameInputDevice simpleDigitalGameInputDevice) {
        this.simpleDigitalGameInputDevice = simpleDigitalGameInputDevice;
    }
    
    public CollisionDetection getCollisionDetection() {
        return collisionDetection;
    }
    
    public void setCollisionDetection(CollisionDetection collisionDetection) {
        this.collisionDetection = collisionDetection;
    }
    
    public void centerBall(){
        ballPainter.getBallPanel().setX(surface.getWidth()/2-ballPainter.getBallPanel().getWidth()/2);
        ballPainter.getBallPanel().setY(surface.getHeight()/2-ballPainter.getBallPanel().getHeight()-10);
    }
    
    public void centerPaddle(){
        paddlePainter.getPaddlePanel().setX(surface.getWidth()/2-paddlePainter.getPaddlePanel().getWidth()/2);
        paddlePainter.getPaddlePanel().setY(surface.getHeight()-paddlePainter.getPaddlePanel().getHeight()-10);
    }
    
    public void initGameInputDevice(){
        simpleDigitalGameInputDevice = new SimpleDigitalGameInputDevice();
        simpleDigitalGameInputDevice.setSimple2DGame(this);
        simpleDigitalGameInputDevice.connect();
    }
    
    public void init(){
        initGameInputDevice();
        paddlePainter = new PaddlePainter();
        paddlePainter.getPaddlePanel().setHeight(25);
        paddlePainter.getPaddlePanel().setWidth(150);
        
        surface = new Surface();
        BufferedImage image = BasicImageUtility.getInstance().load(System.getProperty("user.dir")+"\\resources\\background_pong.jpg",BufferedImage.TYPE_3BYTE_BGR);
        surface.setBackgroundImage(BasicImageUtility.getInstance().toCompatibleImage(BasicImageUtility.getInstance().scale(image,1.0,1.0)));
        surface.addPainter(paddlePainter);
        surface.setSize(image.getWidth(),image.getHeight());
        surface.setPreferredSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setMinimumSize(new Dimension(image.getWidth(),image.getHeight()));
        surface.setMaximumSize(new Dimension(image.getWidth(),image.getHeight()));
        centerPaddle();
        scorePainter = new ScorePainter();
        scorePainter.getScorePanel().setX(10);
        scorePainter.getScorePanel().setY(50);
        scorePainter.getScorePanel().setWidth(200);
        scorePainter.getScorePanel().setHeight(50);
        surface.addPainter(scorePainter);
        ballPainter = new BallPainter();
        ballPainter.getBallPanel().setHeight(25);
        ballPainter.getBallPanel().setWidth(25);
        //define how the ball bahaves...
        ballbehaviour = new Ballbehaviour();
        ballPainter.getBallPanel().setBallbehaviour(ballbehaviour);
        centerBall();
        surface.addPainter(ballPainter);
        
        topWallPainter = new TopWallPainter();
        topWallPainter.getPanel().setX(0);
        topWallPainter.getPanel().setY(0);
        topWallPainter.getPanel().setHeight(5);
        topWallPainter.getPanel().setWidth(image.getWidth());
        surface.addPainter(topWallPainter);
        
        leftWallPainter = new LeftWallPainter();
        leftWallPainter.getPanel().setX(0);
        leftWallPainter.getPanel().setY(0);
        leftWallPainter.getPanel().setHeight(image.getHeight());
        leftWallPainter.getPanel().setWidth(5);
        surface.addPainter(leftWallPainter);
        
        rightWallPainter = new RightWallPainter();
        rightWallPainter.getPanel().setX(image.getWidth()-5);
        rightWallPainter.getPanel().setY(0);
        rightWallPainter.getPanel().setHeight(image.getHeight());
        rightWallPainter.getPanel().setWidth(5);
        surface.addPainter(rightWallPainter);
        
        collisionDetection = new CollisionDetection();
        collisionDetection.setBallPanel(ballPainter.getBallPanel());
        collisionDetection.setPaddlePanel(paddlePainter.getPaddlePanel());
        collisionDetection.setLeftWallPanel(leftWallPainter.getPanel());
        collisionDetection.setRightWallPanel(rightWallPainter.getPanel());
        collisionDetection.setTopWallPanel(topWallPainter.getPanel());
        collisionDetection.setGame(this);
        surface.addObserver(collisionDetection);
        Timer ballTimer = new Timer(5,getBallPainter().getBallPanel());
        surface.setCycles(10);
        surface.setFPSEnabled(true);
        surface.start();
        ballTimer.start();
        setVisualComponent(surface);
        super.init();
    }
    
    public static void main(String[] args) {
        PongGame aPongGame = new PongGame();
        aPongGame.init();
        aPongGame.setVisible(true);
    }
}