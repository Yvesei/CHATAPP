package com.example.man;
import com.example.man.DB.DAO.ClientDaoImplemantation;
import com.example.man.DB.DAO.ChatDAOImplemetation;
import com.example.man.DB.DAO.MessageDAOImplementation;

import com.example.man.DB.DAO.entities.Message;
import com.example.man.DB.DAO.entities.client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.PrintWriter;
import java.util.List;

public class chatController implements MessageCallback{
    @FXML
    private  TextField messageInput;
    @FXML
    private  VBox messageContainer;
    @FXML
    private VBox usersList;
    @FXML
    private Label LogedinUserName;

    @FXML
    private ImageView LogedinUserImage;

    @FXML
    private Label clickedUserName;
    @FXML
    private ImageView clickedUserImage;
    private client client ;
    private int chatId;
    private ChatDAOImplemetation dao = new ChatDAOImplemetation();
    private ClientDaoImplemantation daoClient = new ClientDaoImplemantation();
    private client clickedClient = new client();
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
//    public void initializeUserLabels() {
//        for (com.example.man.DB.DAO.entities.client c:availableClients){
//            if (!c.getName().equals(client.getName())){
//            Label userLabel = new Label(c.getName());
//            usersList.setMargin(userLabel, new Insets(5, 0, 5, 0));
//            usersList.getStyleClass().add("users-container");
//            userLabel.getStyleClass().add("user");
//            userLabel.setOnMouseClicked(event -> handleUserClick(userLabel)); // Attach click event
//            usersList.getChildren().add(userLabel); // Append label to VBox
//            }
//        }
//    }

    public void initializeUserLabels() {
        LogedinUserName.setText(this.client.getName());
        LogedinUserImage.setImage(new Image("file:/Users/test/Downloads/bruh.jpeg"));
        for (com.example.man.DB.DAO.entities.client c : availableClients) {
            if (!c.getName().equals(client.getName())) {
                HBox userBox = createUserBox(c);
                userBox.setOnMouseClicked(event -> handleUserClick(c.getName())); // Attach click event
                usersList.getChildren().add(userBox); // Append HBox to VBox

            }
        }
    }

    private HBox createUserBox(com.example.man.DB.DAO.entities.client user) {
        HBox userBox = new HBox();
        userBox.getStyleClass().add("user-box");

        // ImageView for user image
        ImageView userImage = new ImageView(new Image("file:/Users/test/Downloads/bruh.jpeg")); // Replace with the actual image path
        userImage.setFitHeight(50.0);
        userImage.setFitWidth(50.0);
        userImage.setPreserveRatio(true);

        // VBox for user details
        VBox userDetails = new VBox();
        userDetails.getStyleClass().add("user-con");
        userDetails.setPrefHeight(51.0);
        userDetails.setPrefWidth(108.0);

        // Label for user name
        Label nameLabel = new Label(user.getName());
        nameLabel.getStyleClass().add("user-name");
        // here we will get a new
        // Label for user message
        Label messageLabel = new Label("You: asahbdbsad");
        messageLabel.getStyleClass().add("user-message");

        userDetails.getChildren().addAll(nameLabel, messageLabel);

        // Circle for notification
        Circle notificationCircle = new Circle(9.0, Color.DODGERBLUE);
        notificationCircle.setStroke(Color.BLACK);
        Label notificationLabel = new Label("0");
        notificationLabel.getStyleClass().add("notification-label");
        StackPane notificationStack = new StackPane(notificationCircle, notificationLabel);
        // to be changed back to hidden .
        notificationStack.getStyleClass().add("notificationStack");
        notificationStack.setVisible(false);

        HBox.setMargin(notificationStack, new Insets(6.0, 0, 0, 0));

        userBox.getChildren().addAll(userImage, userDetails, new Pane(), notificationStack);
        return userBox;
    }

    private void handleUserClick(String username) {
        showNotification(username, false);
        for (client user:availableClients){
            if (user.getName().equals(username)){
                this.setClickedClient(user);
                System.out.println("[chatController ] (clicked client is : )" + this.clickedClient.getName() );
                this.clickedUserName.setText(username);
                // might need to set the user image after
                this.chatId = dao.getChatIdByUserIds(client.getID_client(), clickedClient.getID_client());
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
        System.out.println("[message sent] - to : " + clickedClient.getName() +" | " + "content :" + enteredMessage );
        if (!enteredMessage.isEmpty()) {
            Message msg = new Message(this.client.getID_client(),this.chatId,enteredMessage);
            daoMessage.save(msg);
            Main.getServerOut().println(clickedClient.getName() + " " + enteredMessage);
            // sends it to client handler
            showMessage(enteredMessage, true);
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
        System.out.println("[chatController] showMessageReceived() : enteredMessage : " + enteredMessage );
        String[] parts = enteredMessage.split(" ", 3);
        if (this.clickedClient.getName() !=null) {
            if (this.client.getName().equals(parts[0]) && this.clickedClient.getName().equals(parts[1])) {
                Label newLabel = new Label(parts[2]);
                newLabel.getStyleClass().add(isSent ? "sent" : "received");
                VBox.setMargin(newLabel, new Insets(5, 0, 5, 0));
                messageContainer.getChildren().add(newLabel);
                // Clear the input field after adding the message
                messageInput.clear();
                // Scroll to the bottom to show the latest message
//        ScrollPane scrollPane = (ScrollPane) messageContainer.getParent().getParent();
//        scrollPane.setVvalue(1.0);
            } else if (this.client.getName().equals(parts[0]) && !this.clickedClient.getName().equals(parts[1])) {
                this.showNotification(parts[1], true);
            }
        }else{
            this.showNotification(parts[1], true);
        }
    }
    private void showNotification(String username, boolean show) {
        System.out.println("enetered in show notification!!!!");

        for (Node node : usersList.getChildren()) {
            if (node instanceof HBox) {
                HBox userBox = (HBox) node;

                // Find the user name label within the user box
                Label nameLabel = findUserNameLabel(userBox);
                String usernameLabel =  nameLabel.getText();
                // Check if the label has the style class "user-name"
                if (nameLabel != null && usernameLabel.equals(username)) {
                    this.shownotif(userBox, show);
                }
            }
        }
    }
    private void shownotif(HBox userBox, boolean show){
        StackPane notificationStack = (StackPane) userBox.lookup(".notificationStack");
        Label notificationAllert = (Label) userBox.lookup(".notification-label");
        notificationAllert.setText("1");
        notificationStack.setVisible(show);

    }

    private Label findUserNameLabel(HBox userBox) {
        System.out.println("enetered in show findUserNameLabel!!!!");

        for (Node node : userBox.getChildren()) {
            if (node instanceof VBox) {
                VBox userDetails = (VBox) node;

                for (Node innerNode : userDetails.getChildren()) {
                    if (innerNode instanceof Label && innerNode.getStyleClass().contains("user-name")) {
                        return (Label) innerNode;
                    }
                }
            }
        }

        return null;
    }

    // Helper method to find the notification circle within a user box


    // Helper method to find the label within the notification circle
    private Label findNotificationLabel(Circle notificationCircle) {
        System.out.println("enetered in show findNotificationLabel!!!!");

        for (Node node : ((StackPane) notificationCircle.getParent()).getChildren()) {
            if (node instanceof Label && node.getStyleClass().contains("notification-label")) {
                return (Label) node;
            }
        }

        return null;
    }

}
