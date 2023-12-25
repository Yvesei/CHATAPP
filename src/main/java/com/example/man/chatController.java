package com.example.man;
import com.example.man.DB.DAO.entities.client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.io.PrintWriter;
import java.util.List;
import com.example.man.DB.DAO.ClientDaoImplemantation;

public class chatController implements MessageCallback{
    @FXML
    private  TextField messageInput;

    @FXML
    private  VBox messageContainer;
    private client client ;

    private client clickedClient;
    private PrintWriter serverOut;

    public client getClickedClient() {
        return clickedClient;
    }

    public void setClickedClient(client clickedClient) {
        this.clickedClient = clickedClient;
    }

    @FXML
    private VBox usersList;

    private List<com.example.man.DB.DAO.entities.client> availableClients;

    public void setAvailableClients(List<com.example.man.DB.DAO.entities.client> availableClients) {
        this.availableClients = availableClients;
    }

    // Other existing code...

    // Method to initialize labels for users and add them to the VBox
    public void initializeUserLabels() {
        for (com.example.man.DB.DAO.entities.client c:availableClients){
            if (!c.getName().equals(client.getName())){
            Label userLabel = new Label(c.getName());
            usersList.setMargin(userLabel, new Insets(5, 0, 5, 0));
            // Set other properties as needed
            userLabel.setOnMouseClicked(event -> handleUserClick(userLabel)); // Attach click event
            usersList.getChildren().add(userLabel); // Append label to VBox
            }
        }

    }
    // Handle label click events
    private void handleUserClick(Label clickedLabel) {
        String username = clickedLabel.getText();
        for (client user:availableClients){
            if (user.getName().equals(username)){
                this.clickedClient = user ;
            }
        }
        // Add further handling as needed
    }
    @Override
    public void onMessageReceived(String message) {
        showMessage(message, false);
    }

    public void setServerOut(PrintWriter serverOut) {
        this.serverOut = serverOut;
    }

    public void setUsername(client user){
        this.client = user;
    }

    public void showusername(){
        System.out.println(this.client.getName());
    }
    @FXML
    public void onEnterPressed(ActionEvent ae) {
        String enteredMessage =  messageInput.getText().trim();
        if (!enteredMessage.isEmpty()) {
            Main.getServerOut().println(clickedClient.getName() + " " + enteredMessage);
            showMessage(this.client.getName() + ": " +enteredMessage, true);
        }
    }
    public void showMessage(String enteredMessage, Boolean isSent){
        Label newLabel = new Label(enteredMessage);
        newLabel.getStyleClass().add(isSent ? "sent" : "received");
        VBox.setMargin(newLabel, new Insets(5, 0, 5, 0));
        messageContainer.getChildren().add(newLabel);
        // Clear the input field after adding the message
        messageInput.clear();
        // Scroll to the bottom to show the latest message
        ScrollPane scrollPane = (ScrollPane) messageContainer.getParent().getParent();
        scrollPane.setVvalue(1.0);
    }
}
