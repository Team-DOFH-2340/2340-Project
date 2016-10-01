package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Person;
import model.UserType;

/**
 * Created by Hayden on 10/1/2016.
 */
public class EditProfileController {
     private MainFXApplication mainApplication;

    private Person user;

    @FXML
    private Label usernameField;
    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;
    @FXML
    private TextField addressLine1Field;
    @FXML
    private TextField addressLine2Field;
    @FXML
    private TextField addressLine3Field;

    public boolean loggedIn = false;

    @FXML
    private void initialize() {
    }

    /**
     * allow for calling back to the main application code if necessary
     *
     * @param main the reference to the FX Application instance
     */
    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }

    public void setUser(Person user) {
        this.user = user;
        this.usernameField.setText(user.username);
        this.nameField.setText(user.name);
        this.emailField.setText(user.email);
        this.addressLine1Field.setText(user.homeAddress.line1);
        this.addressLine2Field.setText(user.homeAddress.line2);
        this.addressLine2Field.setText(user.homeAddress.line3);
    }
}
