package dev.brendan.snake.entities.player;

import dev.brendan.snake.Handler;
import dev.brendan.snake.entities.statics.Food;
import dev.brendan.snake.sql.ScoreManager;
import dev.brendan.snake.states.EndGame;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.LinkedList;

public class Player extends Creature{
  
    private Color color;
    
    private Food food;
    private ScoreManager sm;
    private EndGame endGame;
    private LinkedList<Tail> tail; 
    private static ArrayList<Point> prev;
    
    private long lastMove, coolDown = 125, timer = coolDown;
    private long collLastMove, collCoolDown = 125, collTimer = collCoolDown;
    private long addLM, addCoolDown = 125, addTimer = addCoolDown;
    
    private boolean adding = false, gameOver = false, isPlaying = false , dead = false;
    private int addIndex = 0;
    private Point location;
    private Color tailColor;
    private String length,highScore;
    
    private String playerName;
    public Player(Handler handler, int x, int y,Color color, Food food, ScoreManager sm){
        super(handler, x, y, Creature.DEFAULT_WIDTH, Creature.DEFAULT_HEIGHT);
        
        
        
        this.food = food;
        this.tail = new LinkedList<>();
        this.prev = new ArrayList<>();
        this.sm = sm;
        
        this.color = color;
        tailColor = Color.green;
        
        bounds.x = 0;
        bounds.y = 0;
        bounds.width = Creature.DEFAULT_WIDTH;
        bounds.height = Creature.DEFAULT_HEIGHT;
        
    }

    @Override
    public void tick() {
        isPlaying = true;
        handler.getKeyManager().setIsPlaying(true);
        timer += System.currentTimeMillis() - lastMove;
        lastMove = System.currentTimeMillis();
        collTimer +=System.currentTimeMillis() - collLastMove;
        collLastMove = System.currentTimeMillis();
        addTimer += System.currentTimeMillis() - addLM;
        addLM = System.currentTimeMillis();
        
        for(Tail t : tail){
            t.tick();
        }
        getInput();
        move();
        checkValidPlayerLocation();
        savePrevLocs();
        checkFoodCollision();
        addTail();
    }
    
    public void getInput(){
        
        if((handler.getKeyManager().up || handler.getKeyManager().upArrow) && yMove != speed){
            xMove = 0;//cant move diagonal
            yMove = -speed;
        } else if((handler.getKeyManager().down || handler.getKeyManager().downArrow) && yMove != -speed){
            xMove = 0;
            yMove = speed;
        } else if((handler.getKeyManager().left || handler.getKeyManager().leftArrow) && xMove != speed){
            yMove = 0;
            xMove = -speed;
        } else if((handler.getKeyManager().right || handler.getKeyManager().rightArrow) && xMove != -speed){
            yMove = 0;
            xMove = speed;  
        }
        if (handler.getKeyManager().esc){
            gameReset();
            setGameOver(false);
            exitGameToMenu();
        }
    }

    @Override
    public void render(Graphics g) {
        
        if(!dead)
            g.setColor(color);
        g.setColor(color);
        g.fillRect(x+bounds.x, y+bounds.y, bounds.width, bounds.height);//draw player
        for(Tail t : tail){
            t.render(g);
        }
            
        updateScore(g);
    }
    
    public void addTail(){
        if(adding && addTimer >= coolDown){
           addTimer = 0;
           tail.add(new Tail(handler,prev.get(prev.size()-1).x, prev.get(prev.size()-1).y, tail.size(), tailColor)); 
           addIndex++;
           if(addIndex > 4){
               adding = false;
               addIndex = 0;
           }
           food.setTailC(tail);
        }
    }
    public void checkValidPlayerLocation(){
        if(dead)
            die();
        if(x < 40 || y < 40 || x > handler.getWidth()-40 || y > handler.getHeight() -40)
            dead = true;
        
        for(Tail t : tail){
            if(t.getX() == this.x && t.getY() == this.y){
                dead = true;
                setColor(Color.MAGENTA);
            }
                
        }
    }
    public void checkFoodCollision(){
        if(this.x == food.getX() && this.y == food.getY() && collTimer >= collCoolDown){
            collTimer = 0;
            adding = true;
            food.die();
        }
    }
    
    public void savePrevLocs(){
        if(prev.size() <=((handler.getHeight()-20/20)*(handler.getWidth()-20/20)) && timer >= coolDown){
            timer = 0;
 
           prev.add(new Point(x,y));
           
        } else if(prev.size() > tail.size()+5 && tail.isEmpty())
            prev.remove(0);
        else if(prev.size() > tail.size()+1 && !tail.isEmpty())
            prev.remove(0);
        System.out.println(prev.size());
    }
    
    public void updateScore(Graphics g){
        if(tail.size()+1 > sm.getTopScore())
            sm.setTopScore(tail.size()+1);
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(new Font("default", Font.BOLD, 32));
        g2.setColor(Color.WHITE);
        FontMetrics fontMetrics = g2.getFontMetrics();
        
        length = "Length: "+(tail.size()+1);
        
        if(tail.size()+1 > sm.getTopScore()){
            sm.setTopScore(tail.size()+1);
            highScore = "High Score: "+sm.getTopScore() + " ("+sm.getPlayerName()+")";
        } else
            highScore = "High Score: "+sm.getTopScore() + " ("+sm.getTopPlayerName()+")";
        
        g2.drawString(length, 20, handler.getHeight()-10);
        g2.drawString(highScore, handler.getWidth() - fontMetrics.stringWidth(highScore) - 20, handler.getHeight()-10);
    }
    public void saveFinalScore(int score){
        sm.addScore(score);
    }
    
    @Override
    public void die() {
        saveFinalScore(tail.size()+1);
        gameOver = true;
        isPlaying = false;
        handler.getKeyManager().esc = false;
        
    }
    
    public void gameReset(){
        tail.removeAll(tail);
        x = 40;
        y = 40;
        xMove = 0;
        yMove = 0;
        dead = false;
        setColor(Color.ORANGE);
    }
    
    public void exitGameToMenu(){
        handler.getKeyManager().esc = false;
        
        handler.getGame().getGameState().getBoard().getMenu().setInSubMenu(false);
        handler.getGame().getGameState().getBoard().getMenu().setSelectPlay(false);
        handler.getGame().getGameState().getBoard().getMenu().setCurrentSelected(1);
    }
    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
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
    
    public static int getXCoords(int id){
        return prev.get(id).x;
    }
    public static int getYCoords(int id){
        return prev.get(id).y;
    }

    public LinkedList<Tail> getTail() {
        return tail;
    }

    public void setTail(LinkedList<Tail> tail) {
        this.tail = tail;
    }

    public long getCollTimer() {
        return collTimer;
    }

    public void setCollTimer(long collTimer) {
        this.collTimer = collTimer;
    }

    public boolean isAdding() {
        return adding;
    }

    public void setAdding(boolean adding) {
        this.adding = adding;
    }

    public long getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(long coolDown) {
        this.coolDown = coolDown;
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public EndGame getEndGame() {
        return endGame;
    }

    public void setEndGame(EndGame endGame) {
        this.endGame = endGame;
    }

    public static ArrayList<Point> getPrev() {
        return prev;
    }

    public static void setPrev(ArrayList<Point> prev) {
        Player.prev = prev;
    }

    public long getLastMove() {
        return lastMove;
    }

    public void setLastMove(long lastMove) {
        this.lastMove = lastMove;
    }

    public long getCollLastMove() {
        return collLastMove;
    }

    public void setCollLastMove(long collLastMove) {
        this.collLastMove = collLastMove;
    }

    public long getCollCoolDown() {
        return collCoolDown;
    }

    public void setCollCoolDown(long collCoolDown) {
        this.collCoolDown = collCoolDown;
    }

    public long getAddLM() {
        return addLM;
    }

    public void setAddLM(long addLM) {
        this.addLM = addLM;
    }

    public long getAddCoolDown() {
        return addCoolDown;
    }

    public void setAddCoolDown(long addCoolDown) {
        this.addCoolDown = addCoolDown;
    }

    public long getAddTimer() {
        return addTimer;
    }

    public void setAddTimer(long addTimer) {
        this.addTimer = addTimer;
    }

    public int getAddIndex() {
        return addIndex;
    }

    public void setAddIndex(int addIndex) {
        this.addIndex = addIndex;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getHighScore() {
        return highScore;
    }

    public void setHighScore(String highScore) {
        this.highScore = highScore;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isIsPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public Color getTailColor() {
        return tailColor;
    }

    public void setTailColor(Color tailColor) {
        this.tailColor = tailColor;
    }

    public ScoreManager getSm() {
        return sm;
    }

    public void setSm(ScoreManager sm) {
        this.sm = sm;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
    
    
    
    
    
    
    
    
}
