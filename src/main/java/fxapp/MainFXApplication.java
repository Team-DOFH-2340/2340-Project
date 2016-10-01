package fxapp;

import controller.LoginController;
import controller.MainScreenController;
import controller.SQLInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Person;

import java.io.IOException;

public class MainFXApplication extends Application {
    public Person currentUser;

    @Override
    public void start(Stage stage) throws Exception {
        SQLInterface.init();
        SQLInterface.checkDatabase();
        mainScreen = stage;
        boolean loggedIn = showLogin();
        if (!loggedIn) {
            return;
        }
        initRootLayout(stage);
    }

    private boolean showLogin() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApplication.class.getResource("../login.fxml"));
        Parent loginRoot = loader.load();
        Scene scene = new Scene(loginRoot, 400, 300);
        LoginController controller = loader.getController();
        controller.setMainApp(this);
        stage.setTitle("Welcome");
        stage.setScene(scene);
        stage.showAndWait();
        return controller.isLoggedIn;
    }


    public Stage mainScreen;
    private BorderPane rootLayout;

    /** Initialize main view. First show registration. */
    private void initRootLayout(Stage mainScreen) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApplication.class.getResource("../main.fxml"));
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