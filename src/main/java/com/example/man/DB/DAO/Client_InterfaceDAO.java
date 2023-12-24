package com.example.man.DB.DAO;

import com.example.man.DB.DAO.entities.client;

import java.util.List;

public interface Client_InterfaceDAO extends DAO<client,Integer>{
    List<client> SearchClientByQuery(String query);
    client getByUserName(String name);
}
