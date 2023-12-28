package com.example.man.DB.DAO.entities;

import java.io.Serializable;

public class Message implements Serializable {
    private int id_message;
    private int SenderId;
    private int ChatId;
    private String Content;

    public Message() {
    }

    public Message( int senderId, int chatId, String content) {
        SenderId = senderId;
        ChatId = chatId;
        Content = content;
    }

    public int getId_message() {
        return id_message;
    }

    public void setId_message(int id_message) {
        this.id_message = id_message;
    }

    public int getSenderId() {
        return SenderId;
    }

    public void setSenderId(int senderId) {
        SenderId = senderId;
    }

    public int getChatId() {
        return ChatId;
    }

    public void setChatId(int chatId) {
        ChatId = chatId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
