package dev.brendan.snake.states;

import dev.brendan.snake.Handler;
import dev.brendan.snake.entities.player.Player;
import dev.brendan.snake.menu.Menu;
import dev.brendan.snake.menu.submenu.Scoreboard;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;

public class EndGame {
    
    private Handler handler;
    private Player player;
    private Menu menu;
    private Scoreboard sb;
    private ArrayList<String> scores = new ArrayList<>();
    private String gameOver = "GAME OVER!";
    private String thisScore;
   
    private long lastInput, coolDown = 170, timer = coolDown;
    
    public EndGame(Handler handler, Player player, Menu menu){
        this.handler = handler;
        this.player = player;
        this.menu = menu;
    }
    
    public void tick(){
        
        if(handler.getKeyManager().esc){
            menu.setInSubMenu(false);
            menu.setSelectPlay(false);
            handler.getKeyManager().esc = false;
            menu.setCurrentSelected(1);
            player.setGameOver(false);
            player.gameReset();
            
        } else if (handler.getKeyManager().replay){
            handler.getGame().getGameState().getBoard().getObstacle().genObstacles();
            handler.getKeyManager().esc = false;
            player.setGameOver(false);
            player.setIsPlaying(true);
            player.gameReset();
        }
    }
    public void render(Graphics g){
        
        g.setFont(new Font("default", Font.BOLD, 32));
        FontMetrics fontMetrics = g.getFontMetrics();
        g.setColor(Color.red);
        g.drawString(gameOver, handler.getWidth()/2 - fontMetrics.stringWidth(gameOver)/2, handler.getHeight()/5);

        thisScore = "Score : "+(this.player.getTail().size()+1)+ " ( "+handler.getGame().getGameState().getBoard().getSm().getPlayerName()+" )";
        
        g.setColor(Color.ORANGE);
        g.drawString(thisScore, handler.getWidth()/2 - fontMetrics.stringWidth(thisScore)/2, handler.getHeight()/4);
        
        g.setFont(new Font("default", Font.BOLD, 24));
        fontMetrics = g.getFontMetrics();
        g.setColor(Color.white);
        g.drawString("ESC = Main Menu", handler.getWidth() - 20 - fontMetrics.stringWidth("ESC = Main Menu"), handler.getHeight() - 10);
        g.drawString("R = Play Again", 20, handler.getHeight() - 10);
        
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
    
    
}
