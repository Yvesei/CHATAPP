package com.example.man;
import com.example.man.DB.DAO.ClientDaoImplemantation;
import com.example.man.DB.DAO.ChatDAOImplemetation;
import com.example.man.DB.DAO.MessageDAOImplementation;

import com.example.man.DB.DAO.entities.Message;
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

public class chatController implements MessageCallback{
    @FXML
    private  TextField messageInput;
    @FXML
    private  VBox messageContainer;
    @FXML
    private VBox usersList;
    private client client ;
    private int chatId;
    private ChatDAOImplemetation dao = new ChatDAOImplemetation();
    private ClientDaoImplemantation daoClient = new ClientDaoImplemantation();
    private client clickedClient;
    private PrintWriter serverOut;
    private List<com.example.man.DB.DAO.entities.client> availableClients;
    private MessageDAOImplementation daoMessage = new MessageDAOImplementation();

    public client getClickedClient() {
        return clickedClient;
    }
    public void setClickedClient(client clickedClient) {
        this.clickedClient = clickedClient;
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

    public void setAvailableClients(List<com.example.man.DB.DAO.entities.client> availableClients) {
        this.availableClients = availableClients;
    }

    // mehtods *****************************************************************************************************
    public void initializeUserLabels() {
        for (com.example.man.DB.DAO.entities.client c:availableClients){
            if (!c.getName().equals(client.getName())){
            Label userLabel = new Label(c.getName());
            usersList.setMargin(userLabel, new Insets(5, 0, 5, 0));
            userLabel.setOnMouseClicked(event -> handleUserClick(userLabel)); // Attach click event
            usersList.getChildren().add(userLabel); // Append label to VBox
            }
        }
    }
    private void handleUserClick(Label clickedLabel) {
        String username = clickedLabel.getText();
        for (client user:availableClients){
            if (user.getName().equals(username)){
                this.setClickedClient(user);
                System.out.println("client.getID_client : "+  client.getID_client() + " "+  "clickedClient.getID_client :  " + clickedClient.getID_client());
                this.chatId = dao.getChatIdByUserIds(client.getID_client(), clickedClient.getID_client());
                System.out.println("chat clicked : " + chatId);
                this.renderChat(this.chatId);
            }
        }
        // Add further handling as needed
    }
    private void renderChat(int chatId){
        messageContainer.getChildren().clear();
        List<Message> messages = daoMessage.getMessagesByChatId(chatId);
        for (Message message : messages) {
            boolean sentByCurrentUser = (message.getSenderId() == client.getID_client());
            showMessage(message.getContent(), sentByCurrentUser);
        }


    }
    @Override
    public void onMessageReceived(String message) {
        showMessage(message, false);
    }
    @FXML
    public void onEnterPressed(ActionEvent ae) {
        String enteredMessage =  messageInput.getText().trim();

        if (!enteredMessage.isEmpty()) {
            Message msg = new Message(this.client.getID_client(),this.chatId,enteredMessage);
            daoMessage.save(msg);
            Main.getServerOut().println(clickedClient.getName() + " " + enteredMessage);
            // sends it to client handler
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
//        ScrollPane scrollPane = (ScrollPane) messageContainer.getParent().getParent();
//        scrollPane.setVvalue(1.0);
    }

    public void showMessageReceived(String enteredMessage, Boolean isSent){
        System.out.println("[chatController] (whole message : )" + enteredMessage);
        String[] parts = enteredMessage.split(" ", 3);
        if(this.client.getName().equals(parts[0])) {
            Label newLabel = new Label(parts[2]);
            newLabel.getStyleClass().add(isSent ? "sent" : "received");
            VBox.setMargin(newLabel, new Insets(5, 0, 5, 0));
            messageContainer.getChildren().add(newLabel);
            // Clear the input field after adding the message
            messageInput.clear();
            // Scroll to the bottom to show the latest message
//        ScrollPane scrollPane = (ScrollPane) messageContainer.getParent().getParent();
//        scrollPane.setVvalue(1.0);
        }
    }
}
