package com.example.man.DB.DAO;

import com.example.man.DB.DAO.entities.Message;

import java.util.List;

public interface Message_interfaceDAO extends DAO<Message,Integer>{
    List<Message> getMessagesByChatId(int chatId);
    //public void save(Message message);
}
