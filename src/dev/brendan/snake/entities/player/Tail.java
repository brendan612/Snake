package dev.brendan.snake.entities.player;

import dev.brendan.snake.Handler;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JOptionPane;

public class Tail extends Creature{

    private Color color;
    private Player player;
    private int x,y;
    private int id;
    private boolean collected = false;
    public Tail(Handler handler, int x, int y, int id, Color color) {
        super(handler, x, y, Creature.DEFAULT_WIDTH, Creature.DEFAULT_HEIGHT);
        this.x = x;
        this.y = y;
        this.id = id;

        this.color = color;
        bounds.x = 0;
        bounds.y = 0;
        bounds.width = Creature.DEFAULT_WIDTH;
        bounds.height = Creature.DEFAULT_HEIGHT;
    }
    public void tick() {
        this.x = Player.getXCoords(this.id);
        this.y = Player.getYCoords(this.id);
            
    }
    public void render(Graphics g) {
        g.setColor(color);

        g.fillRect(this.x,this.y, bounds.width, bounds.height);

    }
    @Override
    public String toString(){
        return "tail";
    }
    @Override
    public void die() {
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    

}
