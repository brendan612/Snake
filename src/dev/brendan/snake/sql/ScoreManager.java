package dev.brendan.snake.sql;

import java.sql.Connection;

import dev.brendan.snake.Handler;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ScoreManager {
    
    private ConnectSQL connect;
    private Connection conn;
    private Statement statement;
    private PreparedStatement prepStatement;
    private ResultSet rs;
    
    private final Handler handler;
    
    private ArrayList<Integer> highScores;
    private ArrayList<String> highScoresNames;
    private int topScore = 0;
    private String playerName;
    public ScoreManager(Handler handler){
        
        this.handler = handler;
        
        connect = new ConnectSQL();
        conn = connect.getConnect();
        
        highScores = new ArrayList<>();
        highScoresNames = new ArrayList<>();
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(ScoreManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        playerName = JOptionPane.showInputDialog("Enter your name: ");
        while(playerName.length() > 10)
            playerName = JOptionPane.showInputDialog("Sorry, your name was more than 10 characters. Please try again: ");
        if (playerName.equalsIgnoreCase(""))
            playerName = "Anonymous";
        getScores();
    }
    
    public void addScore(int score){
        
        try{
            boolean top5 = false;
            if (highScores.size() > 5){
                for (int i : highScores){
                    if(score > i)
                        top5 = true;
                }
                if (top5){
                    highScores.add(score);
                    highScoresNames.add(playerName);
                    sortArray();
                    statement = conn.createStatement();
                    statement.executeUpdate("INSERT INTO SnakeScores (Score,Name) " + "VALUES ("+score+",'"+getPlayerName()+"')");
                }
            } else {
                statement = conn.createStatement();
                statement.executeUpdate("INSERT INTO SnakeScores (Score,Name) " + "VALUES ("+score+", '"+getPlayerName()+"')");  
            }
            getScores();
        } catch (SQLException ex) {
            System.out.println("Score already exists");
        }
    }
    
    public void getScores(){
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT TOP 5 * From SnakeScores Order by Score desc");
                highScores.removeAll(highScores);
                highScoresNames.removeAll(highScoresNames);
                while(rs.next()){
                    highScores.add(rs.getInt("Score"));
                    if(rs.getString("Name") == null)
                        highScoresNames.add("Anonymous");
                    else
                        highScoresNames.add(rs.getString("Name"));
                }
                try{
                    topScore = highScores.get(0);
                } catch (Exception e){
                    topScore = 0;
                }
            
        } catch (SQLException ex) {
            System.out.println("Error: "+ ex.getMessage());
        }
    }
    
    public String readScores(int index){
        return ""+ highScores.get(index);
    }
    
    public String readNames(int index){
        return highScoresNames.get(index);
    }
    public void sortArray(){
        boolean flag = true;
        while(flag){
            flag = false;
            for( int i = 0; i < highScores.size()-1; i++){

                if(highScores.get(i) < highScores.get(i+1)){
                    int temp = highScores.get(i);
                    highScores.set(i, highScores.get(i+1));
                    highScores.set(i+1, temp);
                    flag = true;
                }
            }
        }
    }

    public ConnectSQL getConnect() {
        return connect;
    }

    public void setConnect(ConnectSQL connect) {
        this.connect = connect;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public ArrayList<Integer> getHighScores() {
        return highScores;
    }

    public void setHighScores(ArrayList<Integer> highScores) {
        this.highScores = highScores;
    }

    public ArrayList<String> getHighScoresNames() {
        return highScoresNames;
    }

    public void setHighScoresNames(ArrayList<String> highScoresNames) {
        this.highScoresNames = highScoresNames;
    }
    
    public int getTopScore() {
        return topScore;
    }

    public void setTopScore(int topScore) {
        this.topScore = topScore;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
    public String getTopPlayerName(){
        if(highScoresNames.isEmpty())
            return "No recorded Scores";
        return highScoresNames.get(0);
    }
    
    
    
    
    
    
}
