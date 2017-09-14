package dev.brendan.snake.menu;

import dev.brendan.snake.Handler;
import dev.brendan.snake.entities.EntityManager;
import dev.brendan.snake.entities.player.Player;
import dev.brendan.snake.entities.statics.Obstacle;
import dev.brendan.snake.menu.submenu.Controls;
import dev.brendan.snake.menu.submenu.Scoreboard;
import dev.brendan.snake.menu.submenu.Settings;
import dev.brendan.snake.sql.ScoreManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Menu {
    
    private Handler handler;
    private EntityManager entityManager;
    private Player player;
    private ScoreManager sm;
    private Controls ctrls;
    private Settings setting;
    private Obstacle obstacle;
    
    private Scoreboard sb;
    private FontMetrics fontMetrics;
    private String play = "Play", controls = "Controls", settings = "Settings", scoreboard = "Scoreboard",quit = "Quit", enterSelect = "ENTER = Select", returnMenu = "ESC = Return";
    private int currentSelected = 1;
    private boolean selectPlay = false, selectControls = false, selectSettings = false, selectScoreboard = false, inSubMenu = false;
    private long lastInput, coolDown = 170, timer = coolDown;
    public Menu(Handler handler, Player player, ScoreManager sm, EntityManager entityManager, Obstacle obstacle) {
        this.handler = handler;
        this.player = player;
        this.sm = sm;
        this.obstacle = obstacle;
        this.entityManager = entityManager;
        ctrls = new Controls(handler,this);
        setting = new Settings(handler,this,player,obstacle);
        sb = new Scoreboard(handler,this,sm);
        
        
        
    }
    
    public void tick(){
            timer += System.currentTimeMillis() - lastInput;
            lastInput = System.currentTimeMillis();
            getInput();
            
        if(selectPlay){
            handler.getKeyManager().setEsc(false);
            entityManager.tick();
        } else if(selectSettings){
            setting.tick();
        } else if (selectScoreboard){
            sb.tick();
        } else if(selectControls){
            ctrls.tick();
        }
        
    }
    
    public void render(Graphics g){
        if(selectPlay){
          entityManager.render(g);
        } else if(selectControls){
            inSubMenu = true;
            ctrls.render(g);
        } else if(selectSettings){
            inSubMenu = true;
            setting.render(g);
        } else if(selectScoreboard){
            inSubMenu = true;
            sb.render(g);
        } else {
            g.setFont(new Font("default", Font.BOLD, 64));
            fontMetrics = g.getFontMetrics();
            g.setColor(Color.red);
            if(currentSelected != 1)
                g.drawString(play,  handler.getWidth()/2 - fontMetrics.stringWidth(play)/2, handler.getHeight()/2 - (fontMetrics.getHeight()*2) - 100);
            if(currentSelected != 2)
                g.drawString(scoreboard, handler.getWidth()/2 - fontMetrics.stringWidth(scoreboard)/2, handler.getHeight()/2 - (fontMetrics.getHeight()) - 50);
            if(currentSelected != 3)
                g.drawString(controls,  handler.getWidth()/2 - fontMetrics.stringWidth(controls)/2, handler.getHeight()/2);
            if(currentSelected != 4)
                g.drawString(settings,  handler.getWidth()/2 - fontMetrics.stringWidth(settings)/2, handler.getHeight()/2 + (fontMetrics.getHeight()) + 50);
            if(currentSelected != 5)
                g.drawString(quit,  handler.getWidth()/2 - fontMetrics.stringWidth(quit)/2, handler.getHeight()/2 + (fontMetrics.getHeight()*2) + 100);
            
            g.setFont(new Font("default", Font.BOLD, 72));
            fontMetrics = g.getFontMetrics();
            
            switch (currentSelected) {
                case 1:
                    g.setColor(Color.white);
                    g.drawString(play,  handler.getWidth()/2 - fontMetrics.stringWidth(play)/2, handler.getHeight()/2 - (fontMetrics.getHeight()*2) - 100);
                    break;
                case 2:
                    g.setColor(Color.white);
                    g.drawString(scoreboard,  handler.getWidth()/2 - fontMetrics.stringWidth(scoreboard)/2, handler.getHeight()/2 - (fontMetrics.getHeight()) - 50);
                    break;
                case 3:
                    g.setColor(Color.white);
                    g.drawString(controls,  handler.getWidth()/2 - fontMetrics.stringWidth(controls)/2, handler.getHeight()/2);
                    break;
                case 4:
                    g.setColor(Color.white);
                    g.drawString(settings,  handler.getWidth()/2 - fontMetrics.stringWidth(settings)/2, handler.getHeight()/2 + (fontMetrics.getHeight()) + 50);
                    break;
                case 5:
                    g.setColor(Color.white);
                    g.drawString(quit,  handler.getWidth()/2 - fontMetrics.stringWidth(quit)/2, handler.getHeight()/2 + (fontMetrics.getHeight()*2) + 100);
                    break;
                default:
                    break;
            }
        }
        
        if (selectControls || selectSettings || selectScoreboard || !inSubMenu && !player.isIsPlaying()){
            g.setFont(new Font("default", Font.BOLD, 24));
            fontMetrics = g.getFontMetrics();
            g.setColor(Color.white);
            g.drawString(enterSelect, handler.getWidth() - 20 - fontMetrics.stringWidth(enterSelect), handler.getHeight() - 10);
            g.drawString(returnMenu, 20, handler.getHeight() - 10);
        }  
            
    }
    
    public void getInput(){
        
        if(handler.getKeyManager().downArrow || handler.getKeyManager().down && timer >= coolDown && !inSubMenu){
            currentSelected++;
            timer = 0;
        } else if (handler.getKeyManager().upArrow || handler.getKeyManager().up && timer >= coolDown && !inSubMenu){
            currentSelected--;
            timer = 0;
        }
        
        if(currentSelected == 1 && handler.getKeyManager().enter)
            selectPlay = true;
        else if (currentSelected == 2 && handler.getKeyManager().enter)
            selectScoreboard = true;
        else if (currentSelected == 3 && handler.getKeyManager().enter)
            selectControls = true;
        else if (currentSelected == 4 && handler.getKeyManager().enter)
            selectSettings = true;
        else if (currentSelected == 5 && handler.getKeyManager().enter)
            System.exit(0);
        else if (currentSelected == 6)
            currentSelected = 1;
        else if (currentSelected == 0)
            currentSelected = 5;
        

            
    }

    public boolean isSelectPlay() {
        return selectPlay;
    }

    public void setSelectPlay(boolean selectPlay) {
        this.selectPlay = selectPlay;
    }

    public boolean isSelectControls() {
        return selectControls;
    }

    public void setSelectControls(boolean selectControls) {
        this.selectControls = selectControls;
    }

    public boolean isSelectSettings() {
        return selectSettings;
    }

    public void setSelectSettings(boolean selectSettings) {
        this.selectSettings = selectSettings;
    }

    public boolean isSelectScoreboard() {
        return selectScoreboard;
    }

    public void setSelectScoreboard(boolean selectScoreboard) {
        this.selectScoreboard = selectScoreboard;
    }

    public boolean isInSubMenu() {
        return inSubMenu;
    }

    public void setInSubMenu(boolean inSubMenu) {
        this.inSubMenu = inSubMenu;
    }

    public Scoreboard getSb() {
        return sb;
    }

    public void setSb(Scoreboard sb) {
        this.sb = sb;
    }

    public int getCurrentSelected() {
        return currentSelected;
    }

    public void setCurrentSelected(int currentSelected) {
        this.currentSelected = currentSelected;
    }
    
    
    
    
    
    
    
    
    
}
