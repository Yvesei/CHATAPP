package com.example.man;
// Main.java
import javafx.application.Application;
import com.example.man.DB.DAO.entities.client;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.man.DB.DAO.ClientDaoImplemantation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;


public class Main extends Application {

    private static Stage primaryStage;
    private static Main instance; // Store a reference to the instance
    private static client client;
    private static PrintWriter serverOut;
    private static chatController controllerInstance;
    private ClientDaoImplemantation dao = new ClientDaoImplemantation();

    private static List<client> availableClients;

    public static void setControllerInstance(chatController controller) {
        controllerInstance = controller;
    }

    public static PrintWriter getServerOut() {
        return serverOut;
    }

    public static client getClient() {
        return client;
    }

    public void setClient(client client) {
        this.client = client;
    }

    public static Main getInstance() {
        return instance;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
        Main.primaryStage = primaryStage;
        availableClients = dao.getAll();
        try {
            System.out.println("entered in try main");
            Socket socket = new Socket("localhost", 5000);
            // inputs
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
            showLoginView(out);

//            out.println(client.getUsername());
            // listen on uncoming messages and print them
            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = consoleInput.readLine()) != null) {
                        out.println(serverMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            // listen in console (not working ???)
            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                        final String finalServerMessage = serverMessage;
                        Platform.runLater(() -> {
                            controllerInstance.showMessage(finalServerMessage, false);
                        });
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();


            serverOut = out;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);

    }

    public static void showLoginView( PrintWriter serverOut) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            LoginController controller = fxmlLoader.<LoginController>getController();
            controller.setServerOut(serverOut);
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Login View");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showChatView(client user, PrintWriter serverOut) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("chat-view.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            chatController controller = fxmlLoader.<chatController>getController();
            controller.setUsername(user);
            controller.setServerOut(serverOut);
            controller.setAvailableClients(availableClients);
            setControllerInstance(controller);
            controller.initializeUserLabels();
            controller.showusername();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
