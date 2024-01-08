package com.example.man.DB.SERVICE;

import com.example.man.DB.DAO.Message_interfaceDAO;
import com.example.man.DB.DAO.entities.Message;

public class IServiceMessageImpl implements IMessageService{
    Message_interfaceDAO messageInterfaceDAO;

    public IServiceMessageImpl(Message_interfaceDAO messageInterfaceDAO) {
        this.messageInterfaceDAO = messageInterfaceDAO;
    }

    @Override
    public void SaveMsg(Message msg) {
        messageInterfaceDAO.save(msg);
    }
}
