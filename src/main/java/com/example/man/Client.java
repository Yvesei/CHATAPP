package com.example.man;

import java.io.*;
import java.net.Socket;

import static java.lang.System.out;

public class Client {
    private String username;

    Client(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public static void main(String[] args) {
        new Client(args[0]).startClient();
    }



    public void startClient() {
//    try {
//        Socket socket = new Socket("localhost", 5000);
//        // inputs
//        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
//        out.println(username);
//
//        // listen on uncoming messages and print them
//        new Thread(() -> {
//            try {
//                String serverMessage;
//                while ((serverMessage = in.readLine()) != null) {
//                    System.out.println(serverMessage);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();
//
//
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
}


}
