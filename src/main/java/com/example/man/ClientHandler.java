package com.example.man;


import com.example.man.DB.DAO.entities.Message;
import javafx.application.Platform;
import com.example.man.DB.DAO.entities.client;

import java.io.*;
import java.net.Socket;

class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final Chat chat;
    private PrintWriter out;
    private BufferedReader in;
    private client client;

    public Socket getClientSocket() {
        return clientSocket;
    }

    public Chat getChat() {
        return chat;
    }

    public PrintWriter getOut() {
        return out;
    }

    public BufferedReader getIn() {
        return in;
    }

    public com.example.man.DB.DAO.entities.client getClient() {
        return client;
    }

    public ClientHandler(Socket clientSocket, Chat chat, client user) {
        System.out.println("ClientHandler constructor entered");
        this.clientSocket = clientSocket;
        this.chat = chat;
        this.client = user;
    }
    public void sendMessage(String message){
        System.out.println("[clientHandler] of : " + client.getName() +" sending message : " + message );
        out.println(message);
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            chat.addClient(this);

            new Thread(() -> {
                try {
                    String message;
                    // listens on messages coming from client socket
                    while ((message = in.readLine()) != null) {
                        System.out.println("[clientHandler1] msg received : " + message + " by : " + this.client.getName() );
                        String[] parts = message.split(" ", 2);
                        Message msg = new Message();
                        chat.sendPrivateMessage(parts[1], this.client, parts[0]);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
}

}