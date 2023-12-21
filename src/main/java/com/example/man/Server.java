package com.example.man;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
                System.out.println("New client connected: " + clientSocket);
                String username = in.readLine();
                ClientHandler clientHandler = new ClientHandler(clientSocket, chat, username);

                new Thread(() -> {
                    try {
                        String msg;
                        while ((msg = in.readLine()) != null) {
                            System.out.println(msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

                new Thread(clientHandler).start();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

