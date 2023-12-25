package com.example.man;

import java.util.HashSet;

class Chat {
    private final HashSet<ClientHandler> clients = new HashSet<>();

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

    public synchronized void sendPrivateMessage(String message, ClientHandler sender, String recipientName) {
        for (ClientHandler client : clients) {
            if (client.getClientName().equals(recipientName)) {
                // here
                // write the message to DB
                client.sendMessage( sender.getClientName() + ": " + message);
                return;
            }
        }
    }

    public synchronized void listConnectedClients(ClientHandler sender) {
        StringBuilder clientList = new StringBuilder("Connected Clients: ");
        for (ClientHandler client : clients) {
            clientList.append(client.getClientName()).append(", ");
        }
        sender.sendMessage(clientList.toString());
    }
}
