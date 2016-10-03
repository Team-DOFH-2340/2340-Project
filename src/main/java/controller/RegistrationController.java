package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.UserType;

/** Controller for the registration screen. */
public class RegistrationController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField nameField;
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
        boolean invalidLogin = false;
        if (usernameField.getText().equals("")) {
            System.out.println("invalid username");
            invalidLogin = true;
        }
        if (passwordField.getText().equals("")) {
            System.out.println("invalid password");
            invalidLogin = true;
        }
        if (invalidLogin) {
            return;
        }
        if (controller.SQLInterface.createLogin(usernameField.getText(), passwordField.getText(), nameField.getText())) {
            System.out.println("registration successful");
            hasRegistered = true;
            ((Stage) passwordField.getScene().getWindow()).close();
        } else {
            System.out.println("registration failure");
        }
    }
}
