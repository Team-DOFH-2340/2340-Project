package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

    /** Called when the user clicks register. If successful, closes window. */
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Empty username AND/OR password");
            alert.showAndWait();
            return;
        }
        if (controller.SQLInterface.getInstance().createLogin(usernameField.getText(), passwordField.getText(), nameField.getText(), userTypeField.getValue().ordinal())) {
            System.out.println("registration successful");
            hasRegistered = true;
            ((Stage) passwordField.getScene().getWindow()).close();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Username already exists in system");
            alert.showAndWait();
            System.out.println("registration failure");
        }
    }
}
