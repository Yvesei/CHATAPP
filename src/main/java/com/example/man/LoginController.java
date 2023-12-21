package com.example.man;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginController {

    @FXML
    private TextField username;
    @FXML
    private Button connect;

    public void handleConnectButtonClick(ActionEvent event) throws IOException {
        String enteredUsername = username.getText();
        Client c = new Client(enteredUsername);
        c.startClient();
        Main.showChatView(c);
    }

}
