package com.example.man;

import com.example.man.DB.DAO.ClientDaoImplemantation;
import com.example.man.DB.DAO.entities.client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

public class LoginController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button connect;
    @FXML
    private Button signUpButton;
    private Authentification authentification=new Authentification();
    private ClientDaoImplemantation daoClient = new ClientDaoImplemantation();

    private ObjectOutputStream objectOutput;

    public void setObjectOutput(ObjectOutputStream objectOutput) {
        this.objectOutput = objectOutput;
    }
    public void handleConnectButtonClick(ActionEvent event) throws IOException {
        String enteredUsername = username.getText();
        String enteredPassword=password.getText();
        if(authentification.authenticatedUser(enteredUsername,enteredPassword)){
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Authentication Successful");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Welcome, " + enteredUsername + "!");
            successAlert.showAndWait();

            client c = daoClient.getByUserName(enteredUsername);
            System.out.println("[New] client created  : "+ c.getID_client());
            Main.getInstance().setClient(c);
            if (objectOutput != null) {
                objectOutput.writeObject(c);
            } else {
                System.err.println("Error: objectOutput is null");
            }
             //objectOutput.flush();
            Main.showChatView(c, Main.getServerOut());
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Authentication Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password");
            alert.showAndWait();
        }
    }

    public void handleSignUpButtonClick(ActionEvent event) throws IOException{
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();


        Authentification authentification = new Authentification();
        if (authentification.signUpUser(enteredUsername, enteredPassword)) {
            // Afficher un message de succès pour l'inscription
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Sign Up Successful");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Account created successfully!");
            successAlert.showAndWait();

            client c = daoClient.getByUserName(enteredUsername);
            System.out.println("[New] client created  : "+ c.getID_client());
            Main.getInstance().setClient(c);
            objectOutput.writeObject(c);
            Main.showChatView(c, Main.getServerOut());
        } else {
            // Afficher un message d'erreur pour l'inscription échouée
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Sign Up Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Username already exists. Please choose another username.");
            errorAlert.showAndWait();
        }
    }
    public void SignupWindow(ActionEvent event) throws IOException{
        try {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp-view.fxml"));
            Parent root = loader.load();
            LoginController signUpController = loader.getController();
            signUpController.setObjectOutput(objectOutput);
            Scene signUpScene = new Scene(root);

            Stage currentStage = (Stage) signUpButton.getScene().getWindow();
            currentStage.setScene(signUpScene);
            currentStage.show();
        } catch (IOException e) {
            // Accédez à l'exception réelle
            Throwable cause = e.getCause();
            cause.printStackTrace(); // Affichez les détails de l'exception réelle
        }
    }

}
