package main.java.controller;

import main.java.fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/** Controller for the login screen. */
public class LoginController {
    private MainFXApplication mainApplication;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    public boolean loggedIn = false;

    /**
     * allow for calling back to the main application code if necessary
     * @param main the reference to the FX Application instance
     */
    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }

    /**
     * Checks the database to see if login credentials given in view are good.
     */
    public void login() {
        System.out.println(usernameField.getText());
        System.out.println(passwordField.getText());
        mainApplication.currentUser = SQLInterface.authenticate(usernameField.getText(), passwordField.getText());
        if (mainApplication.currentUser != null) {
            System.out.println("login successful");
            this.loggedIn = true;
            ((Stage) passwordField.getScene().getWindow()).close();
        } else {
            System.out.println("login failure");
        }
    }

    /** Opens the register screen and stores whether or not the user registered successfully. */
    public void register() {
        this.loggedIn = false;
        showRegistration();
    }

    /**
     * @return True if user successfully created a new account, false otherwise
     */
    private boolean showRegistration() {
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader();
            stage.initModality(Modality.APPLICATION_MODAL);
            loader.setLocation(MainFXApplication.class.getResource("register.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 400, 300);
            stage.setTitle("Registration");
            stage.setScene(scene);
            RegistrationController controller = loader.getController();
            stage.showAndWait();
            return controller.hasRegistered;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Shouldn't be here!");
        return false;
    }
}
