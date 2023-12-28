package com.example.man.DB.DAO;

import com.example.man.DB.DAO.entities.Chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ChatDAOImplemetation implements Chat_interfaceDAO{
    @Override
    public int CreateChat(Chat chat) {
        Connection connection=DbSingleton.getConnection();
        int chatId = -1;
        try {
            PreparedStatement ptsm=connection.prepareStatement("INSERT INTO chat (firstUserId, secondUserId) VALUES (?, ?)",PreparedStatement.RETURN_GENERATED_KEYS);
            ptsm.setInt(1,chat.getFirstUserId());
            ptsm.setInt(2,chat.getSecondUserId());
            int i= ptsm.executeUpdate();
            if(i>0){
                ResultSet resultSet=ptsm.getGeneratedKeys();
                if (resultSet.next()) {
                    chatId = resultSet.getInt(1);
                    chat.setId_chat(chatId);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
    }
        return chatId;
    }

    @Override
    public int getChatIdByUserIds(int firstUserId, int secondUserId) {
        Connection connection = DbSingleton.getConnection();
        int chatId = -1;

        try {
            PreparedStatement ptsm = connection.prepareStatement("SELECT id_chat FROM chat WHERE (firstUserId = ? AND secondUserId = ?) OR (firstUserId = ? AND secondUserId = ?)");
            ptsm.setInt(1, firstUserId);
            ptsm.setInt(2, secondUserId);
            ptsm.setInt(3, secondUserId);
            ptsm.setInt(4, firstUserId);

            ResultSet resultSet = ptsm.executeQuery();

            if (resultSet.next()) {
                chatId = resultSet.getInt("id_chat");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (chatId == -1) {
            // Chat does not exist, create a new one
            Chat newChat = new Chat(firstUserId, secondUserId);
            chatId = CreateChat(newChat);
        }

        return chatId;
    }



    @Override
    public void save(Chat o) {

    }

    @Override
    public void removeById(Integer i) {

    }

    @Override
    public Chat getById(Integer i) {
        return null;
    }

    @Override
    public List<Chat> getAll() {
        return null;
    }

    @Override
    public void Update(Chat o) {

    }
}
