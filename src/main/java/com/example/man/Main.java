package com.example.man;
// Main.java
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        showLoginView();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Load and show the login view
    public static void showLoginView() {

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Login View");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load and show the chat view
    public static void showChatView(Client user) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("chat-view.fxml"));

            Parent root = (Parent)fxmlLoader.load();
            chatController controller = fxmlLoader.<chatController>getController();
            controller.setUsername(user);
            controller.showusername();
            Scene scene = new Scene(root);

            primaryStage.setScene(scene);

            primaryStage.show();
//            Parent root = loader.load();
//            primaryStage.setScene(new Scene(root));
//            primaryStage.setTitle("Chat View");
//            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
