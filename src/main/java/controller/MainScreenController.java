package controller;

import fxapp.MainFXApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.Person;

/**
 * Controller for the main map screen of the app.
 */
public class MainScreenController {
    /**
     * reference back to mainApplication if needed
     */
    private MainFXApplication mainApplication;

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
}
