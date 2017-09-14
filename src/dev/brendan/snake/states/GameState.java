package dev.brendan.snake.states;

import dev.brendan.snake.Handler;
import dev.brendan.snake.board.Board;
import java.awt.Graphics;

public class GameState extends State{

    private Board board;
    
    public GameState(Handler handler) {
        super(handler);
        
        board = new Board(handler,handler.getWidth(),handler.getHeight());
    }

    public void tick() {
        board.tick();
    }

    public void render(Graphics g) {
        board.render(g);
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
    
    
}
