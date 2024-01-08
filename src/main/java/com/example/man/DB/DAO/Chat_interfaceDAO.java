package com.example.man.DB.DAO;
import com.example.man.DB.DAO.entities.Chat;
public interface Chat_interfaceDAO extends DAO<Chat,Integer>{
    public int CreateChat(Chat chat);

    int getChatIdByUserIds(int firstUserId, int secondUserId);
}
