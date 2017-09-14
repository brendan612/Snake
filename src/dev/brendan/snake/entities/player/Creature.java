package dev.brendan.snake.entities.player;

import dev.brendan.snake.Handler;
import dev.brendan.snake.entities.Entity;

public abstract class Creature extends Entity{

    public static final int DEFAULT_SPEED = 20;
    
    public static final int DEFAULT_WIDTH = 18,
                            DEFAULT_HEIGHT = 18;
    
    protected int speed;
    protected float xMove, yMove;
    protected boolean moving = false;
    
    private long lastMove, coolDown = 125, timer = coolDown;
    
    public Creature(Handler handler, int x, int y, int width, int height) {
        super(handler, x, y, width, height);
        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
    }
    
    public void move(){
        
        timer += System.currentTimeMillis() - lastMove;
        lastMove = System.currentTimeMillis();
        
        if(timer < coolDown)
            return;
       
        if(xMove > 0 ){
            x += xMove;
        } else if(xMove < 0){
            x += xMove;
        } else if (yMove > 0){
            y += yMove;
        } else if (yMove < 0){
            y += yMove;
        }
        
        timer = 0;
    }

}
