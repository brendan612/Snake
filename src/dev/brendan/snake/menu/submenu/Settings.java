package dev.brendan.snake.menu.submenu;

import dev.brendan.snake.Handler;
import dev.brendan.snake.entities.player.Player;
import dev.brendan.snake.entities.statics.Obstacle;
import dev.brendan.snake.menu.Menu;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Settings {

    private Handler handler;
    private Player player;
    private Menu menu;
    private Obstacle obstacle;
    
    private Color[] colors = {Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA,new Color(175, 38, 17)};
    private int selectedSetting = 1;
    private int selectedPlayerColor = colors.length;
    private int selectedTailColor = colors.length;
    private int selectedObstacleOption = 1;
    private ArrayList<String> setting;
    private String settings = "Settings";
    private Image selectorTriangle;
    private int[] speeds = {100,150,200,250,300};
    private long lastInput, coolDown = 170, timer = coolDown,
                 pColorLastInput, pColorCoolDown = 170, pColorTimer = pColorCoolDown;
    private boolean right = false,left = false;
    private final int COLOR_SQUARE_SIZE = 32;
    private final int COLOR_SQUARE_GAP = 20;
    public Settings(Handler handler, Menu menu, Player player, Obstacle obstacle){
        
        this.handler = handler;
        this.player = player;
        this.menu = menu;
        this.obstacle = obstacle;
        setting = new ArrayList<>();
        setting.add("Head Color");
        setting.add("Tail Color");
        setting.add("Speed");
        setting.add("Obstacles");
        
        selectorTriangle = new ImageIcon("res/images/selectorTriangle.gif").getImage();
    }
    
    public void tick(){
        timer += System.currentTimeMillis() - lastInput;
        lastInput = System.currentTimeMillis();
        pColorTimer += System.currentTimeMillis() - pColorLastInput;
        pColorLastInput = System.currentTimeMillis();
        getInput();
        if(handler.getKeyManager().esc){
            menu.setInSubMenu(false);
            menu.setSelectSettings(false);
            handler.getKeyManager().esc = false;
        }
    }
    
    public void render(Graphics g){
        g.setFont(new Font("default", Font.BOLD, 64));
        FontMetrics fontMetrics = g.getFontMetrics();
        g.setColor(Color.red);
        g.drawString(settings, handler.getWidth()/2 - fontMetrics.stringWidth(settings)/2, handler.getHeight()/4);
        
        g.drawLine(150, handler.getHeight()/4 + 15, handler.getWidth() - 150 , handler.getHeight()/4 + 15);
        g.setFont(new Font("default", Font.BOLD, 32));
        fontMetrics = g.getFontMetrics();
        g.setColor(Color.white);
        
        renderPlayerColor(g);
        renderTailColor(g);
        renderSpeed(g);
        renderObstacle(g);
        
    }
    
    public void renderPlayerColor(Graphics g){
        g.setColor(Color.DARK_GRAY);
        g.fillRect(handler.getWidth() - 150 + 18 - (COLOR_SQUARE_SIZE*selectedPlayerColor)
                - (COLOR_SQUARE_GAP*selectedPlayerColor), handler.getHeight()/4 + 40, COLOR_SQUARE_SIZE+6, COLOR_SQUARE_SIZE+6);
        g.setColor(Color.white);
        if(selectedSetting == 1){
            if((handler.getKeyManager().right || handler.getKeyManager().rightArrow) && pColorTimer >= pColorCoolDown){
                selectedPlayerColor--;
                pColorTimer = 0;
            } else if ((handler.getKeyManager().left || handler.getKeyManager().leftArrow) && pColorTimer >= pColorCoolDown){
                selectedPlayerColor++;
                pColorTimer = 0;
            }
            
            if(selectedPlayerColor < 1)
                selectedPlayerColor = colors.length;
            else if (selectedPlayerColor > colors.length)
                selectedPlayerColor = 0;
        
            g.fillRect(handler.getWidth() - 150 + 18 - (COLOR_SQUARE_SIZE*selectedPlayerColor)
                     - (COLOR_SQUARE_GAP*selectedPlayerColor), handler.getHeight()/4 + 40, COLOR_SQUARE_SIZE+6, COLOR_SQUARE_SIZE+6);
        }
       
        for(int i = 0; i < colors.length; i++){
            g.setColor(colors[i]);
            g.fillRect(handler.getWidth() - 150 - 32 - (COLOR_SQUARE_SIZE*i) - (COLOR_SQUARE_GAP*i-1), handler.getHeight()/4 + 43, COLOR_SQUARE_SIZE, COLOR_SQUARE_SIZE);
        }
        if(selectedSetting == 1){
            g.setColor(Color.white);
        } else {
            g.setColor(Color.red);
        }
        g.drawString(setting.get(0), 150, handler.getHeight()/4 + 75);
        
        if(handler.getKeyManager().enter &&  selectedSetting == 1)
            setPlayerColor(colors[selectedPlayerColor-1 ]);
    }
    
    
    public void renderTailColor(Graphics g){
        g.setColor(Color.DARK_GRAY);
        g.fillRect(handler.getWidth() - 150 + 18 - (COLOR_SQUARE_SIZE*selectedTailColor)
                     - (COLOR_SQUARE_GAP*selectedTailColor), handler.getHeight()/4 + 93, COLOR_SQUARE_SIZE+6, COLOR_SQUARE_SIZE+6);
        if(selectedSetting == 2){
            if((handler.getKeyManager().right || handler.getKeyManager().rightArrow) && pColorTimer >= pColorCoolDown){
                selectedTailColor--;
                pColorTimer = 0;
            } else if ((handler.getKeyManager().left || handler.getKeyManager().leftArrow) && pColorTimer >= pColorCoolDown){
                selectedTailColor++;
                pColorTimer = 0;
            }
            
            if(selectedTailColor == 0)
                selectedTailColor = colors.length;
            else if (selectedTailColor > colors.length)
                selectedTailColor = 0;
            g.setColor(Color.white);
            g.fillRect(handler.getWidth() - 150 + 18 - (COLOR_SQUARE_SIZE*selectedTailColor)
                     - (COLOR_SQUARE_GAP*selectedTailColor), handler.getHeight()/4 + 93, COLOR_SQUARE_SIZE+6, COLOR_SQUARE_SIZE+6);
        }
        
        for(int i = 0; i < colors.length; i++){
            g.setColor(colors[i]);
            g.fillRect(handler.getWidth() - 150 - 32 - (COLOR_SQUARE_SIZE*i) - (COLOR_SQUARE_GAP*i-1), handler.getHeight()/4 + 96, COLOR_SQUARE_SIZE, COLOR_SQUARE_SIZE);
        }
        if(selectedSetting == 2){
            g.setColor(Color.white);
        } else {
            g.setColor(Color.red);
        }
        g.drawString(setting.get(1), 150, handler.getHeight()/4 + 75 + 50);
            
        if(handler.getKeyManager().enter &&  selectedSetting == 2)
            setTailColor(colors[selectedTailColor-1]);

    }
    public void renderSpeed(Graphics g){
        
        if(selectedSetting == 3){
            g.setColor(Color.white);
        } else {
            g.setColor(Color.red);
        }
        g.drawString(setting.get(2), 150, handler.getHeight()/4 + 75 + 100);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(handler.getWidth() - 150 + 18 - (COLOR_SQUARE_SIZE*colors.length) - (COLOR_SQUARE_GAP*colors.length-1) , handler.getHeight()/4 + 156,
                (COLOR_SQUARE_SIZE*colors.length) + (COLOR_SQUARE_GAP*(colors.length-1)), 10);
        

    }
    
    public void renderObstacle(Graphics g){
        if (selectedSetting == 4){
            g.setColor(Color.white);
        } else
            g.setColor(Color.red);
        g.drawString(setting.get(3), 150, handler.getHeight()/4 + 75 + 150);
        g.drawImage(selectorTriangle, ((handler.getWidth() - 150+18)*selectedObstacleOption+1)-(COLOR_SQUARE_SIZE*colors.length) - (COLOR_SQUARE_GAP*colors.length-1), handler.getHeight()/4 + 216, null);
        
        if(right)
            selectedObstacleOption++;
        else if(left)
            selectedObstacleOption--;
            
        if(selectedObstacleOption < 0)
            selectedObstacleOption = 1;
        else if (selectedObstacleOption > 1)
            selectedObstacleOption = 0;
        

    }
    
    
    public void getInput(){
        if(handler.getKeyManager().downArrow || handler.getKeyManager().down && timer >= coolDown){
            selectedSetting++;

            timer = 0;
        } else if (handler.getKeyManager().upArrow || handler.getKeyManager().up && timer >= coolDown){
            selectedSetting--;
            left = true;
            right = false;
            timer = 0;
        }
        right = handler.getKeyManager().right || handler.getKeyManager().rightArrow && timer >= coolDown;
        left = handler.getKeyManager().left || handler.getKeyManager().leftArrow && timer >= coolDown;
        
        if(right){
            timer = 0;
            left = false;
        } else if (left){
            timer = 0;
            right = false;
        }
        if (selectedSetting > setting.size())
            selectedSetting = 1;
        else if (selectedSetting < 1){
            selectedSetting = setting.size();
        }
        
        
    }
    public void setSpeed(int input){
        player.setCoolDown(input);
        player.setTimer(player.getCoolDown());
        player.setCollCoolDown(input);
        player.setCollTimer(player.getCollCoolDown());
        player.setAddCoolDown(input);
        player.setAddTimer(player.getAddCoolDown());
    }
    
    public void setPlayerColor(Color color){
        player.setColor(color);
    }
    
    public void setTailColor(Color color){
        player.setTailColor(color);
    }
}
