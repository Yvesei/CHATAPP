package com.example.man;

import com.example.man.DB.DAO.entities.client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class Server {
    public static void main(String[] args) {
        new Server().startServer(5000);
    }

    private void startServer(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is running on port " + port);
            Chat chat = new Chat();
            while (true) {
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
                // object input - output
                ObjectInputStream objectInput = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream objectOutput = new ObjectOutputStream(clientSocket.getOutputStream());
                // log
                System.out.println("New client connected: " + clientSocket);
                //reading client object
//                String username = in.readLine();
//                int clientId = in.read();
//                client user = new client(clientId,username);
                client user = (client) objectInput.readObject();
                ClientHandler clientHandler = new ClientHandler(clientSocket, chat, user);

                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

