package com.example.man.DB.PRESENTATION;

import com.example.man.DB.DAO.ClientDaoImplemantation;
import com.example.man.DB.DAO.entities.client;
import com.example.man.DB.SERVICE.IClientService;
import com.example.man.DB.SERVICE.IServiceClientImpl;

import java.util.List;

public class TestConsoleApp {
    public static void main(String[] args) {
        IClientService service=new IServiceClientImpl(new ClientDaoImplemantation());
        List<client> clients=service.getAllClient();
        for(client c:clients){
            System.out.println("ID : "+c.getID_client()+" Name : "+c.getName());
        }
    }
}
