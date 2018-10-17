package server;
import java.sql.*;

public class SQLHandler {
    
    private static Connection connection;
    private static Statement stmt;
    private static final String CONN_STRING = "C:\\alex\\_________________________________________________NewCode\\_Java2Lesson8\\Server\\src\\server\\database.db";
    
    public static void connect()
    {
        try
        {
            //For SQLLite
            //Class.forName("org.sqlite.JDBC");
            //connection = DriverManager.getConnection("jdbc:sqlite:" + CONN_STRING);

            //For MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/world?useSSL=false","root","DeltaDental!");
            System.out.println("Connected");
            stmt = connection.createStatement();
        }
        catch(Exception ex)
        {
            ex.getStackTrace();
            System.out.println("Not connected");
        }
    }//connect()
    
    
    public static String getNickByLogPass(String login, String password)
    {
        try {
            //For SQLLite
            //ResultSet rs = stmt.executeQuery("SELECT nickname FROM users WHERE login='" + login + "' AND password='" + password + "';");
            //if(rs.next())//если в наборе есть данные, ...
            //   return rs.getString("nickname");//вернуть строку из набора

            //For MySQL
            ResultSet rs = stmt.executeQuery("SELECT user_name FROM users WHERE user_login='" + login + "' AND user_password='" + password + "';");
            if(rs.next())//если в наборе есть данные, ...
            {
                return rs.getString("user_name");//вернуть строку из набора
            }

        } catch (SQLException ex) {
            ex.getStackTrace();
        }
       return null;//нет такого клиента 
    }
    
    
    public static void disconnect()
    {
        try
        {
            connection.close();//осовбодить ресурсы подключения
        }
        catch(Exception ex)
        {
            ex.getStackTrace();
        }
    }//disconnect()
}//public class SQLHandler
