package com.example.man;

import java.io.*;
import java.net.Socket;

class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final Chat chat;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName;
    private MessageCallback messageCallback;


    public void setMessageCallback(MessageCallback callback) {
        this.messageCallback = callback;
    }

    public MessageCallback getMessageCallback() {
        return messageCallback;
    }

    public ClientHandler(Socket clientSocket, Chat chat, String username) {
        System.out.println("ClientHandler constructor entered");
        this.clientSocket = clientSocket;
        this.chat = chat;
        this.clientName = username;

    }
    public void sendMessage(String message) {
        out.println(message);
    }
    public String getClientName() {
        return clientName;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out.println("Client connected : " + clientName + "!");
            chat.addClient(this);
            System.out.println("entered run in clientHandler");

            String message;
            // listens on messages coming from client socket
            while ((message = in.readLine()) != null) {
                    String[] parts = message.split(" ", 2);
                    chat.sendPrivateMessage(parts[1], this, parts[0]);
//
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
}
}