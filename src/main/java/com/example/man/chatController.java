package com.example.man;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class chatController {
    @FXML
    private TextField messageInput;

    @FXML
    private VBox messageContainer;
    private Client client ;
    public void setUsername(Client user){
        this.client = user;
    }

    public void showusername(){
        System.out.println(this.client);
    }
    @FXML
    public void onEnterPressed(ActionEvent ae) {
        String enteredMessage = this.client.getUsername() + ": " + messageInput.getText().trim();
        client.send(enteredMessage);
        if (!enteredMessage.isEmpty()) {
            Label newLabel = new Label(enteredMessage);
            messageContainer.getChildren().add(newLabel);
            // Clear the input field after adding the message
            messageInput.clear();
            // Scroll to the bottom to show the latest message
            ScrollPane scrollPane = (ScrollPane) messageContainer.getParent().getParent();
            scrollPane.setVvalue(1.0);
        }
    }

}
