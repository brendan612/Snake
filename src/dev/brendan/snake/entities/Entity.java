package dev.brendan.snake.entities;

import dev.brendan.snake.Handler;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Entity {
    
    protected Handler handler;
    
        protected int x,y,width,height;

    protected boolean active = true;
    
    protected Rectangle bounds;
    
    public Entity(Handler handler, int x, int y, int width, int height){
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        bounds = new Rectangle(0,0,width,height);
    }
    
    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract void die();
    public boolean checkCollision(){
        
        return true;
    }
    public Rectangle getCollisionBounds(){
        return new Rectangle(x + bounds.x, y+bounds.y,bounds.width,bounds.height);
    }
    public boolean isActive(){
        return active;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
}
