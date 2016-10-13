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
import model.UserType;
import model.WaterSourceReport;

import java.io.IOException;
import java.util.ArrayList;

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
    @FXML
    private Button source_report;
    @FXML
    private Button admin_screen;

    public Person user;

    /**
     * allow for calling back to the main application code if necessary
     *
     * @param main the reference to the FX Application instance
     */
    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }

    public void disableAdminScreen() {
        if (user.type == UserType.USER || user.type == UserType.WORKER) {
            admin_screen.setVisible(false);
        }
    }

    public void logout() throws Exception {
        System.out.println("Logging out of " + user.name);
        ((Stage) source_report.getScene().getWindow()).close();
        mainApplication.start(new Stage());
    }

    public void editProfile() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApplication.class.getResource("../editProfile.fxml"));
        Parent loginRoot = loader.load();
        Scene scene = new Scene(loginRoot, 350, 350);
        EditProfileController controller = loader.getController();
        controller.setUser(user);
        stage.setTitle("Edit Profile");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void source_report() throws Exception {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApplication.class.getResource("../sourcereport.fxml"));
        Parent loginRoot = loader.load();
        Scene scene = new Scene(loginRoot, 800, 600);
        SourceReportController controller = loader.getController();
        controller.setUser(user);
        stage.setTitle("Source Report");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void admin_screen() throws Exception {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApplication.class.getResource("../adminviewcontroller.fxml"));
        Parent loginRoot = loader.load();
        Scene scene = new Scene(loginRoot, 800, 600);
        AdminViewController controller = loader.getController();
        controller.loadData();
        stage.setTitle("Admin Tools");
        stage.setScene(scene);
        stage.showAndWait();
    }
}
