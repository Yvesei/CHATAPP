package com.example.man.DB.DAO.entities;

import java.io.Serializable;

public class client implements Serializable {
    private int ID_client;
    private String Name;

    public client() {
    }

    public client(int ID_client, String name) {
        this.ID_client = ID_client;
        Name = name;
    }

    public client(String name) {
        Name = name;
    }

    public int getID_client() {
        return ID_client;
    }

    public void setID_client(int ID_client) {
        this.ID_client = ID_client;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
