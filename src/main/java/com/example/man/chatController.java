package com.example.man;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.PrintWriter;

public class chatController implements MessageCallback{
    @FXML
    private  TextField messageInput;

    @FXML
    private  VBox messageContainer;
    private Client client ;

    private PrintWriter serverOut;
    @Override
    public void onMessageReceived(String message) {
        showMessage(message);
    }

    public void setServerOut(PrintWriter serverOut) {
        this.serverOut = serverOut;
    }

    public void setUsername(Client user){
        this.client = user;
    }

    public void showusername(){
        System.out.println(this.client.getUsername());
    }
    @FXML
    public void onEnterPressed(ActionEvent ae) {
        System.out.println("dkhl l 0");
        String enteredMessage =  messageInput.getText().trim();
        System.out.println("dkhl l 1");

        if (!enteredMessage.isEmpty()) {
            System.out.println("here!!!");
            Main.getServerOut().println(enteredMessage);
            showMessage(this.client.getUsername() + ": " +enteredMessage);
        }
    }
    public void showMessage(String enteredMessage){
        Label newLabel = new Label(enteredMessage);
        messageContainer.getChildren().add(newLabel);
        // Clear the input field after adding the message
        messageInput.clear();
        // Scroll to the bottom to show the latest message
        ScrollPane scrollPane = (ScrollPane) messageContainer.getParent().getParent();
        scrollPane.setVvalue(1.0);
    }

}
