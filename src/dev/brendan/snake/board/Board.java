




package dev.brendan.snake.board;

import dev.brendan.snake.Handler;
import dev.brendan.snake.entities.Entity;
import dev.brendan.snake.entities.EntityManager;
import dev.brendan.snake.entities.player.Player;
import dev.brendan.snake.entities.statics.Food;
import dev.brendan.snake.entities.statics.Obstacle;
import dev.brendan.snake.menu.Menu;
import dev.brendan.snake.sql.ScoreManager;
import dev.brendan.snake.states.EndGame;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Board {

    private Handler handler;
    private EntityManager entityManager;
    private Food food;
    private Player player;
    private Obstacle obstacle;
    private EndGame endGame;
    private Menu menu;
    private ScoreManager sm;
    
    private int width,height, index = 0;
    private ArrayList<Entity> entities;
    private String paused ="PAUSED";
    private boolean gamePaused = false;
    
    public Board(Handler handler, int width, int height){
        this.handler = handler;
        this.width = width;
        this.height = height;
        sm = new ScoreManager(handler);
        food = new Food(handler,1,1);
        player =  new Player(handler, 40, 40,new Color(244, 170, 66), food,sm);
        obstacle = new Obstacle(handler,1,1,Color.WHITE);
        entityManager = new EntityManager(handler,player,food,obstacle);
        menu = new Menu(handler,player,sm,entityManager,obstacle);
        endGame = new EndGame(handler,player,menu);
        
    }
    
    public void tick(){
        if(player.isGameOver()){
            endGame.tick();
        } else if (!gamePaused){
            menu.tick();
        }
        getPause();
        
        
    }
    public void render(Graphics g){

        g.setColor(new Color(28, 28, 28));
        g.fillRect(40, 40, handler.getWidth()-80, handler.getHeight()-80);//Fill board with black color
        
        if(player.isGameOver()){
            endGame.render(g);
            menu.getSb().render(g);
        } else
            menu.render(g);
        
        if(gamePaused){
            g.setColor(Color.WHITE);
            g.setFont(new Font("default", Font.BOLD, 32));
            FontMetrics fontMetrics = g.getFontMetrics();
            g.drawString("PAUSED", handler.getWidth()/2 - fontMetrics.stringWidth(paused)/2, handler.getHeight()/2);
        }
    }
    
    public void getPause(){
        gamePaused = handler.getKeyManager().pauseGame && player.isIsPlaying();
    }
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ScoreManager getSm() {
        return sm;
    }

    public void setSm(ScoreManager sm) {
        this.sm = sm;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public EndGame getEndGame() {
        return endGame;
    }

    public void setEndGame(EndGame endGame) {
        this.endGame = endGame;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
    
    
    
    
    
    
    
    
}
