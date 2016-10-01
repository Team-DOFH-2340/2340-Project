package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import fxapp.MainFXApplication;

/**
 * Created by Hayden on 10/1/2016.
 */
public class MainScreenController {
    /** reference back to mainApplication if needed */
    private MainFXApplication mainApplication;

    /**
     * allow for calling back to the main application code if necessary
     * @param main   the reference to the FX Application instance
     * */
    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }
}
