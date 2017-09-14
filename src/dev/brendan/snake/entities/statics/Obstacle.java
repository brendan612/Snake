package dev.brendan.snake.entities.statics;

import dev.brendan.snake.Handler;
import dev.brendan.snake.entities.player.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Obstacle extends StaticEntity{
    
    private Handler handler;
    private Player player;
    private Food food;
    private Color color;
    
    private ArrayList<Point> pieces;
    
    private int x,y, tempX, tempY, obstacleCount;
    private boolean gen = true;
    private Random r = new Random();
    
    public Obstacle(Handler handler, int x, int y, Color color){
        super(handler,x,y,18,18);
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.color = color;
        this.player = player;
        
        pieces = new ArrayList<>();
        obstacleCount = r.nextInt(50);
        
        genObstacles();
    }

    @Override
    public void tick() {
        Point playerPos = new Point(handler.getGame().getGameState().getBoard().getPlayer().getX(),
                handler.getGame().getGameState().getBoard().getPlayer().getY());
        if(pieces.contains(playerPos))
            handler.getGame().getGameState().getBoard().getPlayer().die();
    }

    @Override
    public void render(Graphics g) {
        
        g.setColor(Color.white);
        for(int i = 0; i < pieces.size(); i++)
            g.fillRect(pieces.get(i).x, pieces.get(i).y, width, height);
    }

    public void genObstacles(){
        pieces.removeAll(pieces);
        for(int i = 0; i < obstacleCount; i++){
            Point tempPoint;
            do{
                do{
                    tempX = r.nextInt(1000);
                }while(tempX % 20 != 0 || tempX < 100 || tempX > handler.getWidth()-40);
                do{
                    tempY = r.nextInt(1000);
                }while(tempY % 20 != 0 || tempY < 100 || tempY > handler.getHeight()-40);
                
                tempPoint = new Point(tempX,tempY);
            }while(pieces.contains(tempPoint));
            pieces.add(tempPoint);
        }
    }
    @Override
    public void die() {}

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ArrayList<Point> getPieces() {
        return pieces;
    }

    public void setPieces(ArrayList<Point> pieces) {
        this.pieces = pieces;
    }

    public int getObstacleCount() {
        return obstacleCount;
    }

    public void setObstacleCount(int obstacleCount) {
        this.obstacleCount = obstacleCount;
    }
    
    
    
}
