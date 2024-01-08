package com.example.man.DB.DAO.entities;

import java.io.Serializable;

public class client implements Serializable {
    private int ID_client;
    private String Name;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public client(String name, String password) {
        Name = name;
        this.password = password;
    }

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
