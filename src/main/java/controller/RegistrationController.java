package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.UserType;

/**
 * Created by Hayden on 10/1/2016.
 */
public class RegistrationController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private ChoiceBox<UserType> userTypeField = new ChoiceBox<>();

    public boolean hasRegistered;

    @FXML
    private void initialize() {
        userTypeField.getItems().setAll(UserType.values());
        userTypeField.setValue(UserType.USER);
        hasRegistered = false;
    }

    public void register() {
        if (controller.SQLInterface.createLogin(usernameField.getText(), passwordField.getText())) {
            System.out.println("registration successful");
            hasRegistered = true;
            System.out.println("BEFORE: " + hasRegistered());
            ((Stage) passwordField.getScene().getWindow()).close();
            System.out.println("AFter: " + hasRegistered());
        } else {
            System.out.println("registration failure");
        }
    }

    public boolean hasRegistered() {
        return hasRegistered;
    }
}
