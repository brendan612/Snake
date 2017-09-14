package dev.brendan.snake.menu.submenu;

import dev.brendan.snake.Handler;
import dev.brendan.snake.menu.Menu;
import dev.brendan.snake.sql.ScoreManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Scoreboard {

    private Handler handler;
    private ScoreManager sm;
    private Menu menu;
    
    private String topScores = "All-Time High Scores";
    private long lastInput, coolDown = 170, timer = coolDown;
    
    public Scoreboard(Handler handler, Menu menu, ScoreManager sm){
        this.handler = handler;
        this.menu = menu;
        this.sm = sm;
    }
    public void tick(){
        timer += System.currentTimeMillis() - lastInput;
        lastInput = System.currentTimeMillis();
        
        if(handler.getKeyManager().esc && timer >= coolDown){
            menu.setInSubMenu(false);
            menu.setSelectScoreboard(false);
            handler.getKeyManager().esc = false;
            timer = 0;
        }

            
           
    }
    public void render(Graphics g){
        sm.getScores();
        g.setFont(new Font("default", Font.BOLD, 50));
        FontMetrics fontMetrics = g.getFontMetrics();
        g.setColor(Color.WHITE);
        
        g.drawString(topScores, handler.getWidth()/2 - fontMetrics.stringWidth(topScores)/2, handler.getHeight()/2-75);
        g.drawLine(handler.getWidth()/2 - fontMetrics.stringWidth(topScores)/2 + 5, handler.getHeight()/2-65, handler.getWidth()/2 + fontMetrics.stringWidth(topScores)/2 - 5, handler.getHeight()/2-65);
        int index = 0;
        for(int i = 0; i < sm.getHighScores().size(); i++){
            switch (i) {
                case 0:
                    g.setColor(Color.ORANGE);
                    break;
                case 1:
                    g.setColor(Color.LIGHT_GRAY);
                    break;
                case 2:
                    g.setColor(new Color(205,127,50));
                    break;
                default:
                    g.setColor(Color.white);
                    break;
            }
            fontMetrics = g.getFontMetrics();
            g.drawString((i+1)+". "+sm.readNames(i), handler.getWidth()/2 - fontMetrics.stringWidth(topScores)/2 + 5, handler.getHeight()/2+((i+1)*50)-65);
            g.drawString(""+(sm.readScores(i)),  handler.getWidth()/2 + fontMetrics.stringWidth(topScores)/2 - 5 - (fontMetrics.stringWidth(""+(sm.readScores(i)))), handler.getHeight()/2+((i+1)*50)-65);
        }
    }
}
