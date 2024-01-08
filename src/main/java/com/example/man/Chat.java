package com.example.man;

import java.util.HashSet;

import com.example.man.DB.DAO.MessageDAOImplementation;
import com.example.man.DB.DAO.entities.Message;
import com.example.man.DB.DAO.entities.client;


class Chat {
    private static final HashSet<ClientHandler> clients = new HashSet<>();
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
                client.sendMessage(sender.getClient().getName() + ": " + message);
            }
        }
    }

    public synchronized void sendPrivateMessage(String message, client sender, String reciever) {
        System.out.println("[Chat:sendPrivateMessage] message : " + message);
        for (ClientHandler client:clients) {
            if (client.getClient().getName().equals(reciever)) {
                // needs modification
                client.sendMessage( reciever + " " + sender.getName() + " " + message);
                return;
            }
        }
    }


}
