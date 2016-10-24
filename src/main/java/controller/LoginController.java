package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Person;
import model.UserType;

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
     *
     * @param main the reference to the FX Application instance
     */
    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }

    public void login() {
        System.out.println(usernameField.getText());
        System.out.println(passwordField.getText());
        if (SQLInterface.authenticate(usernameField.getText(), passwordField.getText())) {
            System.out.println("login successful");
            this.loggedIn = true;
            // mainApplication.currentUser = SQLInterface.getUser() GET USER FROM DATABASE, PLACEHOLDER VV
            mainApplication.currentUser = SQLInterface.getUser();
            ((Stage) passwordField.getScene().getWindow()).close();
        } else {
            System.out.println("login failure");
        }
    }

    public void register() {
        this.loggedIn = false;
        boolean registered = showRegistration();
    }

    private boolean showRegistration() {
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader();
            stage.initModality(Modality.APPLICATION_MODAL);
            loader.setLocation(MainFXApplication.class.getResource("../register.fxml"));
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
