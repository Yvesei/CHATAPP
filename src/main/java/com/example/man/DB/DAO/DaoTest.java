package com.example.man.DB.DAO;

import com.example.man.DB.DAO.entities.client;

import java.util.List;

public class DaoTest {
    public static void main(String[] args) {
        Client_InterfaceDAO clientInterfaceDAO=new ClientDaoImplemantation();

        List<client> clients=clientInterfaceDAO.getAll();

        client client=new client();
       // client.setName("toto");
        //clientInterfaceDAO.save(client);

        for (client c:clients){
            System.out.println("ID : "+c.getID_client()+" Name : "+c.getName());
        }
        System.out.println("*********************************************");

        List<client> liste1=clientInterfaceDAO.SearchClientByQuery("adham");


        for (client c:liste1){
            System.out.println("ID : "+c.getID_client()+" Name : "+c.getName());
        }
        System.out.println("*********************************************");

       client c=clientInterfaceDAO.getByUserName("adham");
       if(c!=null){
           System.out.println("client trouvé");
       }else {
           System.out.println("client non trouvé");
       }

        System.out.println("*********************************************");

    }
}
