package com.example.man.DB.SERVICE;

import com.example.man.DB.DAO.Client_InterfaceDAO;
import com.example.man.DB.DAO.entities.client;

import java.util.List;

public class IServiceClientImpl implements IClientService{
    Client_InterfaceDAO clientDao;

    public IServiceClientImpl(Client_InterfaceDAO clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public void addClient(client client) {
        clientDao.save(client);

    }

    @Override
    public List<client> getAllClient() {
        return clientDao.getAll();
    }

    @Override
    public List<client> SearchClientQuery(String name) {
        return clientDao.SearchClientByQuery(name);
    }

    @Override
    public void Update(client o) {
        clientDao.Update(o);
    }

    @Override
    public void UpdatePassWord(client o) {
        clientDao.UpdatePassWord(o);
    }
}
