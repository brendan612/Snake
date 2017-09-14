package dev.brendan.snake.menu.submenu;

import dev.brendan.snake.Handler;
import dev.brendan.snake.menu.Menu;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;

public class Controls {
    
    private Handler handler;
    private Menu menu;
    
    private long lastInput, coolDown = 170, timer = coolDown;
    
    private String ctrl = "Controls", key = "Key"; 
    private ArrayList<String> controls, keys;
    public Controls(Handler handler, Menu menu){
        this.handler = handler;
        this.menu = menu;
        controls = new ArrayList<>();
        keys = new ArrayList<>();
        
        controls.add("Move Up"); keys.add("W");
        controls.add("Move Left"); keys.add("A");
        controls.add("Move Down"); keys.add("S");
        controls.add("Move Right"); keys.add("D");
        controls.add("Pause"); keys.add("SPACE");
        controls.add("Previous Page"); keys.add("ESC");
        controls.add("Select"); keys.add("ENTER");
    }
    
    public void tick(){
        timer += System.currentTimeMillis() - lastInput;
        lastInput = System.currentTimeMillis();
        
        if(handler.getKeyManager().esc && timer >= coolDown){
            menu.setInSubMenu(false);
            menu.setSelectControls(false);
            handler.getKeyManager().esc = false;
            timer = 0;
        }
    }
    
    public void render(Graphics g){
     g.setFont(new Font("default", Font.BOLD, 64));
     FontMetrics fontMetrics = g.getFontMetrics();
     g.setColor(Color.red);
     g.drawString(ctrl, 150, handler.getHeight()/4);
     g.drawString(key, handler.getWidth() - 150 - fontMetrics.stringWidth(key), handler.getHeight()/4);
     g.drawLine(150, handler.getHeight()/4 + 15, handler.getWidth() - 150 , handler.getHeight()/4 + 15);
     g.setFont(new Font("default", Font.BOLD, 32));
     fontMetrics = g.getFontMetrics();
     g.setColor(Color.white);
    
     for(int i = 0; i < controls.size(); i++){
         g.drawString(controls.get(i), 150, handler.getHeight()/4 + 75 + (i*50));

         g.drawString(keys.get(i), handler.getWidth() - 150 - fontMetrics.stringWidth(keys.get(i)), handler.getHeight()/4 + 75 + (i*50));
     }
    }
}
