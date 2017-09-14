package dev.brendan.snake;

import dev.brendan.snake.board.Board;
import dev.brendan.snake.entities.statics.Food;
import dev.brendan.snake.input.KeyManager;
import dev.brendan.snake.input.MouseManager;
import dev.brendan.snake.states.GameState;

public class Handler {
    private Game game;
    private GameState gamestate;
    
    private Board board;
    private Food food;
    public Handler(Game game){
        this.game = game;
    }
    
    public Game getGame(){
        return game;
    }
    public void setGame(Game game){
        this.game = game;
    }
    public Food getFood(){
        return food;
    }
    public Board getBoard(){
        return board;
    }
    public KeyManager getKeyManager(){
        return game.getKeyManager();
    }
    public MouseManager getMouseManager(){
        return game.getMouseManager();
    }
    
    public int getWidth(){
        return game.getWidth();
    }
    
    public int getHeight(){
        return game.getHeight();
    }
}
