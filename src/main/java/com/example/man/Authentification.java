package com.example.man;

import com.example.man.DB.DAO.DbSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authentification {
    private Connection connection= DbSingleton.getConnection();
    public boolean authenticatedUser(String username,String password){
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("SELECT * FROM client WHERE Name=? AND password=?");
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            ResultSet resultSet= preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean signUpUser(String username, String password){
        if (userExists(username)) {
            return false;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO client (Name, password) VALUES (?, ?)")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private boolean userExists(String username){
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM client WHERE Name = ?")) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
