package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Person;

/**
 * Controller for the Edit Profile view. Can display a user and update their info in the database.
 */
public class EditProfileController {
     private MainFXApplication mainApplication;

    private Person user;

    @FXML
    private Label usernameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField titleField;

    @FXML
    private TextField emailField;
    @FXML
    private TextField addressLine1Field;
    @FXML
    private TextField addressLine2Field;
    @FXML
    private TextField addressLine3Field;

    /**
     * Save the user info in the view to the database.
     */
    @FXML
    private void save() {
        user.setName(nameField.getText());
        user.getHomeAddress().setLine1(addressLine1Field.getText());
        user.getHomeAddress().setLine2(addressLine2Field.getText());
        user.getHomeAddress().setLine3(addressLine3Field.getText());
        user.setTitle(titleField.getText());
        user.setEmail(emailField.getText());
        SQLInterface.getInstance().updateUser(user);
        ((Stage)emailField.getScene().getWindow()).close();
    }

    /** Close the edit profile view without saving. */
    @FXML
    private void cancel() {
        ((Stage)emailField.getScene().getWindow()).close();
    }

    /** Called to set up the view.
     * @param user Person whose info we'll display
     */
    public void setUser(Person user) {
        this.user = user;
        this.titleField.setText(user.getTitle());
        this.usernameField.setText(user.getUsername());
        this.nameField.setText(user.getName());
        this.emailField.setText(user.getEmail());
        this.addressLine1Field.setText(user.getHomeAddress().getLine1());
        this.addressLine2Field.setText(user.getHomeAddress().getLine2());
        this.addressLine3Field.setText(user.getHomeAddress().getLine3());
    }
}
