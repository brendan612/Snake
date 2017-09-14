package dev.brendan.snake;

import dev.brendan.snake.audio.MusicPlayer;
import dev.brendan.snake.display.Display;
import dev.brendan.snake.input.KeyManager;
import dev.brendan.snake.input.MouseManager;
import dev.brendan.snake.states.GameState;
import dev.brendan.snake.states.State;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game implements Runnable{
    
    private Handler handler;
    private KeyManager keyManager;
    private MouseManager mouseManager;
    
    private GameState gameState;
    private Display display;
    
    private boolean running = false;
    private Thread thread;
    private Thread musicPlayer;
    private BufferStrategy bs;
    private Graphics g;
    
    private int width,height;
    private String title;
    
    public Game(String title, int width, int height){
        this.width = width;
        this.height = height;
        this.title = title;
        
        MusicPlayer player = new MusicPlayer("music");
        
        mouseManager = new MouseManager();
    }
    public void init(){

        handler = new Handler(this);
        gameState = new GameState(handler);
        keyManager = new KeyManager(handler);
        display = new Display(title,width,height);
        display.getFrame().addKeyListener(keyManager);
        State.setState(gameState);
        
    }
    public void tick(){
        keyManager.tick();
        if (State.getState() != null){
            State.getState().tick();
        }

    }
    public void render(){
        bs = display.getCanvas().getBufferStrategy();
        if(bs == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);
        
        if (State.getState() != null){
            State.getState().render(g);
        }
        //End draw
        bs.show();
        g.dispose();
    }
    public void run(){
        init();
        
        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;
        while(running){
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;
            
            if( delta >= 1 ){
                tick();
                render();
                ticks++;
                delta--;
            }
            if ( timer >= 1000000000 ){
                ticks = 0;
                timer = 0;
                
            }
        }
        stop();
    }
    public synchronized void start(){
        if(running)
            return;
        running = true;
        
        thread = new Thread(this);
     //   musicPlayer = new Thread(player);
        thread.start();
        musicPlayer.start();
    }
    public synchronized void stop(){
        if (!running)
            return;
        running = false;
        
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public void setKeyManager(KeyManager keyManager) {
        this.keyManager = keyManager;
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public void setMouseManager(MouseManager mouseManager) {
        this.mouseManager = mouseManager;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    
    
    
    
    
    
    
    

}
