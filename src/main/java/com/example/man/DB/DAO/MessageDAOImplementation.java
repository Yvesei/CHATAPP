package com.example.man.DB.DAO;

import com.example.man.DB.DAO.entities.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDAOImplementation implements Message_interfaceDAO{


    @Override
    public void save(Message message) {
        Connection connection=DbSingleton.getConnection();
        try {
            PreparedStatement ptsm= connection.prepareStatement("INSERT INTO message(ID_client,ChatId,Content)"+ "VALUES(?,?,?)");
            ptsm.setInt(1,message.getSenderId());
            ptsm.setInt(2,message.getChatId());
            ptsm.setString(3,message.getContent());
            ptsm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeById(Integer i) {
    }
    @Override
    public List<Message> getMessagesByChatId(int chatId) {
        List<Message> messages = new ArrayList<>();
        Connection connection = DbSingleton.getConnection();

        try {
            PreparedStatement ptsm = connection.prepareStatement("SELECT * FROM message WHERE ChatId = ?");
            ptsm.setInt(1, chatId);

            ResultSet resultSet = ptsm.executeQuery();

            while (resultSet.next()) {
                int clientId = resultSet.getInt("ID_client");
                String content = resultSet.getString("Content");
                int ChatId = resultSet.getInt("ChatId");

                Message message = new Message( clientId, chatId, content);
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }
    @Override
    public Message getById(Integer i) {
        return null;
    }

    @Override
    public List<Message> getAll() {
        return null;
    }

    @Override
    public void Update(Message o) {

    }
}
