package dev.brendan.snake.sql;

import java.sql.Connection;
import java.sql.DriverManager;



public class ConnectSQL {

    public Connection getConnect(){
        Connection conn = null;
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://bitsql.wctc.edu;databaseName=NW_Traders_35_BJB", "bbishop6", "000504727");
        } catch(Exception e){
            System.err.println("Error: "+ e.toString());
        }
        return conn;
    }
}
