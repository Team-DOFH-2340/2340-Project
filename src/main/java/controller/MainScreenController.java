package controller;

import fxapp.MainFXApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Person;

import java.io.IOException;

/**
 * Controller for the main map screen of the app.
 */
public class MainScreenController {
    /**
     * reference back to mainApplication if needed
     */
    private MainFXApplication mainApplication;

    @FXML
    private Button edit;
    @FXML
    private Button logout;

    public Person user;

    /**
     * allow for calling back to the main application code if necessary
     *
     * @param main the reference to the FX Application instance
     */
    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }

    public void logout() {
        System.out.println("Logging out of " + user.name);
        Platform.exit();
    }

    public void editProfile() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApplication.class.getResource("../editProfile.fxml"));
        Parent loginRoot = loader.load();
        Scene scene = new Scene(loginRoot, 400, 300);
        EditProfileController controller = loader.getController();
        controller.setUser(user);
        stage.setTitle("Welcome");
        stage.setScene(scene);
        stage.showAndWait();
    }
}
