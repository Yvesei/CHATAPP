package com.example.man.DB.DAO;

import com.example.man.DB.DAO.entities.client;
import com.example.man.DB.SERVICE.IClientService;
import com.example.man.DB.SERVICE.IServiceClientImpl;

import java.util.List;

public class DaoTest {
    public static void main(String[] args) {
        //Client_InterfaceDAO clientInterfaceDAO=new ClientDaoImplemantation();
        IClientService iClientService=new IServiceClientImpl(new ClientDaoImplemantation());
        //client client=new client("mimi","rgerg");
        //iClientService.addClient(client);
        //System.out.println("id :"+client.getID_client()+" Nom :"+client.getName()+" Mots de Passe :"+client.getPassword());
       /*
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

       */
        List<client> clients=iClientService.getAllClient();
        System.out.println("liste des clients");
        for(client c:clients){
            System.out.println("id :"+c.getID_client()+" Nom :"+c.getName()+" Mots de Passe :"+c.getPassword());
        }


    }
}
