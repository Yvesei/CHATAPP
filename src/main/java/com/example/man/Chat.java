package com.example.man;

import java.util.HashSet;

import com.example.man.DB.DAO.MessageDAOImplementation;
import com.example.man.DB.DAO.entities.Message;
import com.example.man.DB.DAO.entities.client;


class Chat {
    private final HashSet<ClientHandler> clients = new HashSet<>();
    private MessageDAOImplementation dao = new MessageDAOImplementation();


    public synchronized void addClient(ClientHandler client) {
        clients.add(client);
    }

    public synchronized void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    public synchronized void broadcastMessage(String message, ClientHandler sender) {
        System.out.println("entered : broadcastMessage");
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(sender.getClientName() + ": " + message);
            }
        }
    }

    public synchronized void sendPrivateMessage(String message, client sender) {
        for (ClientHandler client : clients) {
            if (client.getClientName().equals(sender.getName())) {
                // needs modification
//                Message msg = new Message(sender.getID_client(),2,message);
//                dao.save(msg);
                client.sendMessage( sender.getName() + ": " + message);
                return;
            }
        }
    }


}
