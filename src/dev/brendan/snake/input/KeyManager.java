package dev.brendan.snake.input;

import dev.brendan.snake.Handler;
import dev.brendan.snake.entities.player.Player;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener{
    private Handler handler;
    private Player player;
    private boolean[] keys;
    public boolean up, down, left, right, esc, enter, upArrow, downArrow, leftArrow, rightArrow, pauseGame, 
            paused, replay;
    private boolean isPlaying = false;
    
    public KeyManager(Handler handler){
        this.handler = handler;
        player = handler.getGame().getGameState().getBoard().getPlayer();
        keys = new boolean[256];
    }
    public void tick(){
        
        up = keys[KeyEvent.VK_W];
        upArrow = keys[KeyEvent.VK_UP];
        
        down = keys[KeyEvent.VK_S];
        downArrow = keys[KeyEvent.VK_DOWN];
        
        left = keys[KeyEvent.VK_A];
        leftArrow = keys[KeyEvent.VK_LEFT];
        
        right = keys[KeyEvent.VK_D];
        rightArrow = keys[KeyEvent.VK_RIGHT];
        
        esc = keys[KeyEvent.VK_ESCAPE];
        enter = keys[KeyEvent.VK_ENTER];
        pauseGame = keys[KeyEvent.VK_SPACE];
        replay = keys[KeyEvent.VK_R];
    }

    @Override
    public void keyPressed(KeyEvent e) {
      //  System.out.println(e.getKeyCode());
        if(e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
            return;
        
        if (e.getKeyCode() == 32 && player.isIsPlaying())
            keys[e.getKeyCode()] = true;
        else if (e.getKeyCode() == 32 && player.isIsPlaying() && pauseGame){
            pauseGame = false;
            System.out.println(e.getKeyCode());
        }
        else if(e.getKeyCode() != 32)
            keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() != 32)
            keys[e.getKeyCode()] = false;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    public boolean isEsc() {
        return esc;
    }

    public void setEsc(boolean esc) {
        this.esc = esc;
    }

    public boolean isIsPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
    
    
    
    
}
