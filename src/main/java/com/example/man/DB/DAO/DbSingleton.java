package com.example.man.DB.DAO;

import java.sql.*;
public class DbSingleton {
    private static Connection cnx;
    static {
       try{
           Class.forName("com.mysql.cj.jdbc.Driver");
           cnx=DriverManager.getConnection("jdbc:mysql://localhost:3306/chatapp","root","");
       }catch (SQLException | ClassNotFoundException e){
           e.printStackTrace( );
       }
    }
    public static Connection getConnection(){
        return cnx;
    }
}
