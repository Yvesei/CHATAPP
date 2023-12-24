package com.example.man;
import com.example.man.DB.DAO.entities.client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.io.PrintWriter;
import java.util.List;

public class chatController implements MessageCallback{
    @FXML
    private  TextField messageInput;

    @FXML
    private  VBox messageContainer;
    private client client ;

    private PrintWriter serverOut;

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
            Label userLabel = new Label(c.getName());
            // Set other properties as needed
            userLabel.setOnMouseClicked(event -> handleUserClick(userLabel)); // Attach click event
            usersList.getChildren().add(userLabel); // Append label to VBox
        }

    }

    // Handle label click events
    private static void handleUserClick(Label clickedLabel) {
        String username = clickedLabel.getText();
        System.out.println("Clicked on User: " + username);
        // Add further handling as needed
    }
    @Override
    public void onMessageReceived(String message) {
        showMessage(message);
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
        System.out.println("dkhl l 0");
        String enteredMessage =  messageInput.getText().trim();
        System.out.println("dkhl l 1");

        if (!enteredMessage.isEmpty()) {
            System.out.println("here!!!");
            Main.getServerOut().println(enteredMessage);
            showMessage(this.client.getName() + ": " +enteredMessage);
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
