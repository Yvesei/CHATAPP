package com.example.man.DB.DAO.entities;

import java.io.Serializable;

public class Chat implements Serializable {
    private int id_chat;
    private int firstUserId;
    private int secondUserId;

    public Chat() {
    }

    public Chat(int firstUserId, int secondeUserId) {
        this.firstUserId = firstUserId;
        this.secondUserId = secondeUserId;
    }

    public int getId_chat() {
        return id_chat;
    }

    public int getFirstUserId() {
        return firstUserId;
    }

    public int getSecondUserId() {
        return secondUserId;
    }

    public void setFirstUserId(int firstUserId) {
        this.firstUserId = firstUserId;
    }

    public void setSecondUserId(int secondeUserId) {
        this.secondUserId = secondeUserId;
    }

    public void setId_chat(int id_chat) {
        this.id_chat = id_chat;
    }
}
