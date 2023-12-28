package com.example.man.DB.SERVICE;

import com.example.man.DB.DAO.Chat_interfaceDAO;
import com.example.man.DB.DAO.entities.Chat;

public class IServiceChatImpl implements IChatService{
    Chat_interfaceDAO chatInterfaceDAO;

    public IServiceChatImpl(Chat_interfaceDAO chatInterfaceDAO) {
        this.chatInterfaceDAO = chatInterfaceDAO;
    }

    @Override
    public void Create(Chat chat) {
        chatInterfaceDAO.CreateChat(chat);
    }
}
