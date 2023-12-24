package com.example.man.DB.SERVICE;

import com.example.man.DB.DAO.entities.client;

import java.util.List;

public interface IClientService {
    public void addClient(client o);
    public List<client> getAllClient();
    public List<client> SearchClientQuery(String q);
}
