package dev.brendan.snake.entities.statics;

import dev.brendan.snake.Handler;
import dev.brendan.snake.entities.player.Player;
import dev.brendan.snake.entities.player.Tail;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

public class Food extends StaticEntity{

    private Player player;
    
    private LinkedList<Tail> tailC = new LinkedList<>();
    public int x,y;
    private Random r = new Random();
    private Color color = new Color(182, 86, 255);
    private boolean active = false;
    private Point location;
    private long foodLM, foodCoolDown = 125, foodTimer = foodCoolDown;
    
    public Food(Handler handler, int x, int y) {
        super(handler, x, y,18,18);
        this.x = x;
        this.y = y;
        bounds.x = 0;
        bounds.y = 0;
        bounds.width = 18;
        bounds.height = 18;
    }

    @Override
    public void tick() {
        foodTimer += System.currentTimeMillis() - foodLM;
        foodLM = System.currentTimeMillis();
        
        if (!active){
            do{
                do{
                    this.x = r.nextInt(handler.getGame().getWidth()-bounds.width);
                }while (this.x % 20 != 0 || this.x > handler.getWidth()-80 || this.x < 40);  
                do{
                    this.y = r.nextInt(handler.getGame().getHeight()-bounds.height);
                }while(this.y % 20 != 0 || this.y > handler.getHeight()-80 || this.y < 40);  
            }while(tailCheck());
            location = new Point(this.x,this.y);
            active = true;
        }
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(this.x, this.y, bounds.width+bounds.x, bounds.height+bounds.y);
       
    }
    @Override
    public void die(){
        active = false;
    }
    
    public boolean tailCheck(){
        return tailC.stream().anyMatch((t) -> (t.getX() == this.x && t.getY() == this.y));
    }
    

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public LinkedList<Tail> getTailC() {
        return tailC;
    }

    public void setTailC(LinkedList<Tail> tailC) {
        this.tailC = tailC;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
    
    
    
    
    
    
    
    

}
