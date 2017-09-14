    package dev.brendan.snake.entities;

import dev.brendan.snake.Handler;
import dev.brendan.snake.entities.player.Player;
import dev.brendan.snake.entities.player.Tail;
import dev.brendan.snake.entities.statics.Food;
import dev.brendan.snake.entities.statics.Obstacle;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class EntityManager {
    
    private Handler handler;
    private Player player;
    private Food food;
    private Tail tail;
    private Obstacle obstacle;
    
    private ArrayList<Entity> entities;
    
    private Random r = new Random();
    private int obstacleAmt;
    public EntityManager(Handler handler, Player player, Food food, Obstacle obstacle){
        this.handler = handler;
        this.player = player;
        this.food = food;
        this.obstacle = obstacle;
        entities = new ArrayList<>();
        
      //  addObject(obstacle);
        addObject(food);
        addObject(player);
    }
    
    public void tick(){
        for(Entity e : entities){
            e.tick();
        }
    }
    
    public void render(Graphics g){
        for(Entity e : entities){
            e.render(g);
        }
    }
    
    public void addObject(Entity e){
        entities.add(e);
    }
    
    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Tail getTail() {
        return tail;
    }

    public void setTail(Tail tail) {
        this.tail = tail;
    }
    
    
}
