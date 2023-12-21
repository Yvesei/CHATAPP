package com.example.man;

import java.io.*;
import java.net.Socket;

class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final Chat chat;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName;




    public ClientHandler(Socket clientSocket, Chat chat, String username) {
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

            out.println("Welcome to the /chat, " + clientName + "!");

            chat.addClient(this);

            String message;
            while ((message = in.readLine()) != null) {
                if (message.startsWith("/private")) {
                    // Format: /private recipientName message
                    String[] parts = message.split(" ", 3);
                    if (parts.length == 3) {
                        chat.sendPrivateMessage(parts[2], this, parts[1]);
                    } else {
                        sendMessage("Invalid private message format. Usage: /private recipientName message");
                    }
                } else if (message.equals("/list")) {
                    chat.listConnectedClients(this);
                } else {
                    chat.broadcastMessage(message, this);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
}
}