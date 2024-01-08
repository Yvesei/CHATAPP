package com.example.man.DB.DAO;

import com.example.man.DB.DAO.entities.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClientDaoImplemantation implements Client_InterfaceDAO{

    @Override
    public List<client> SearchClientByQuery(String query) {
        Connection connection=DbSingleton.getConnection();
        List<client> clients=new ArrayList<>();

        try {
            PreparedStatement ptsm= connection.prepareStatement("SELECT * FROM client WHERE Name LIKE ?");
            ptsm.setString(1,"%"+query+"%");

            ResultSet resultSet=ptsm.executeQuery();
            while(resultSet.next()){
                client client=new client();
                client.setID_client(resultSet.getInt("ID_client"));
                client.setName(resultSet.getString("Name"));
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public client getByUserName(String name) {
        Connection connection=DbSingleton.getConnection();
        client client=null;
        try {
            PreparedStatement ptsm= connection.prepareStatement("SELECT * FROM client WHERE Name=?");
            ptsm.setString(1,name);
            ResultSet resultSet=ptsm.executeQuery();
            if(resultSet.next()){
                client=new client();
                client.setName(resultSet.getString("Name"));
                client.setID_client(resultSet.getInt("ID_client"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public void UpdatePassWord(client o) {
        Connection connection=DbSingleton.getConnection();
        try {
            PreparedStatement ptsm= connection.prepareStatement("UPDATE client SET password=?  WHERE ID_client=?");
            ptsm.setString(1,o.getPassword());
            ptsm.setInt(2,o.getID_client());
            ptsm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(client o) {
        Connection connection=DbSingleton.getConnection();
        try {
            PreparedStatement ptsm= connection.prepareStatement("INSERT INTO client(Name,password)"+
                    "VALUES(?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
            ptsm.setString(1,o.getName());
            ptsm.setString(2,o.getPassword());
            int affectedRows =ptsm.executeUpdate();
            if(affectedRows>0){
                try {
                    ResultSet generatedKeys=ptsm.getGeneratedKeys();
                    if(generatedKeys.next()){
                        int id=generatedKeys.getInt(1);
                        o.setID_client(id);
                    }else{
                        throw new SQLException("la crteation du client a échoué");
                    }
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeById(Integer i) {
        Connection connection=DbSingleton.getConnection();
        try {
            PreparedStatement ptsm= connection.prepareStatement("DELETE FROM client WHERE ID_client=?");
            ptsm.setInt(1,i);
            ptsm.executeUpdate();
    } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public client getById(Integer i) {
        Connection connection=DbSingleton.getConnection();
        client client=null;
        try {
            PreparedStatement ptsm= connection.prepareStatement("SELECT * FROM client WHERE ID_client=?");
            ptsm.setInt(1,i);
            ResultSet resultSet=ptsm.executeQuery();
            if(resultSet.next()){
                client=new client();
                client.setID_client(resultSet.getInt("ID_client"));
                client.setName(resultSet.getString("Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    //getALL done
    @Override
    public List<client> getAll() {
        Connection connection=DbSingleton.getConnection();
        List<client> clients=new ArrayList<>();

        try {
            PreparedStatement ptsm= connection.prepareStatement("SELECT * FROM client");

            ResultSet resultSet=ptsm.executeQuery();
            while(resultSet.next()){
                client client=new client();
                client.setID_client(resultSet.getInt("ID_client"));
                client.setName(resultSet.getString("Name"));
                client.setPassword(resultSet.getString("password"));
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public void Update(client o) {
        Connection connection=DbSingleton.getConnection();
        try {
            PreparedStatement ptsm= connection.prepareStatement("UPDATE client SET Name=?  WHERE ID_client=?");
            ptsm.setString(1,o.getName());
            ptsm.setInt(2,o.getID_client());
            ptsm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
