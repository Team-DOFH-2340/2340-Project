package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.UserType;
public class Controller {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private ChoiceBox<UserType> userTypeField = new ChoiceBox<>();
    @FXML
    private Button logout;

    public void login() {
        System.out.println(usernameField.getText());
        System.out.println(passwordField.getText());
        if (SQLInterface.authenticate(usernameField.getText(), passwordField.getText())) {
            System.out.println("login successful");
            Stage stage = new Stage();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
                Scene scene = new Scene(root, 800, 600);
                stage.setTitle("Harambe");
                stage.setScene(scene);

                //close the current stage:
                Stage login = (Stage) usernameField.getScene().getWindow();
                login.close();

                stage.show();
            } catch (Exception e) {
                // Profit ??
                e.printStackTrace();
            }
        } else {
            System.out.println("login failure");
        }
    }

    public void logout() {
        System.out.println("logout");
        Stage login = (Stage) logout.getScene().getWindow();
        login.close();
        Stage stage = new Stage();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Scene scene = new Scene(root, 400, 300);
            stage.setTitle("Welcome");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            // Profit ??
            e.printStackTrace();
        }
    }
}