package fxapp;

import controller.MainScreenController;
import controller.SQLInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFXApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        SQLInterface.init();
        SQLInterface.checkDatabase();

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));

        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Welcome");
        stage.setScene(scene);
        stage.show();
    }

    public Stage mainScreen;
    private BorderPane rootLayout;

    /** Initialize main view. First show registration. */
    private void initRootLayout(Stage mainScreen) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApplication.class.getResource("../view/MainScreen.fxml"));
        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            System.out.println(e);
        }

        // Give the controller access to the main app.
        MainScreenController controller = loader.getController();
        controller.setMainApp(this);

        // Set the Main fxapp.MainFXApplication title
        mainScreen.setTitle("Course Registration");

        // Show the scene containing the root layout.
        Scene scene = new Scene(rootLayout);
        mainScreen.setScene(scene);
        mainScreen.show();
    }

    public static void main(String...args) {
        // This method is here so that you get a helpful stacktrace if the app fails to construct.
        launch(args);
    }
}