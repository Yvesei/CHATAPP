package com.example.man;

import com.example.man.DB.DAO.ClientDaoImplemantation;
import com.example.man.DB.DAO.entities.client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;

public class LoginController {
    @FXML
    private TextField username;
    @FXML
    private Button connect;
    private ClientDaoImplemantation daoClient = new ClientDaoImplemantation();

    private ObjectOutputStream objectOutput;

    public void setObjectOutput(ObjectOutputStream objectOutput) {
        this.objectOutput = objectOutput;
    }
    public void handleConnectButtonClick(ActionEvent event) throws IOException {
        String enteredUsername = username.getText();
        client c = daoClient.getByUserName(enteredUsername);
        System.out.println("[New] client created  : "+ c.getID_client());
        Main.getInstance().setClient(c);
        objectOutput.writeObject(c);
        objectOutput.flush();
        Main.showChatView(c, Main.getServerOut());
    }

}
