package dev.brendan.snake;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

public class Runner {

   // private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    private static Random r = new Random();
    private static int WIDTH, HEIGHT;
    public static void main(String[] args) {
        
        do{
            WIDTH = r.nextInt(1800);
        }while(WIDTH % 20 != 0 && WIDTH < 300);
        do{
            HEIGHT = r.nextInt(1080);
        }while(HEIGHT % 20 != 0 && HEIGHT < 300);
        
        
        Game game = new Game("Snake",1000,800);
        game.start();
        
        
//        MusicPlayer player = new MusicPlayer("music");
            

    }
    
    private static boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.youtube.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            return true;
        } catch (MalformedURLException e) { 
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }
    
}
