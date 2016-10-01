package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import fxapp.MainFXApplication;
import model.Person;

/**
 * Created by Hayden on 10/1/2016.
 */
public class MainScreenController {
    /** reference back to mainApplication if needed */
    private MainFXApplication mainApplication;

    @FXML
    private Button logout;

    public Person user;

    /**
     * allow for calling back to the main application code if necessary
     * @param main   the reference to the FX Application instance
     * */
    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }

    public void logout() {
        System.out.println("Logging out of " + user.name);
        Platform.exit();
    }
}
