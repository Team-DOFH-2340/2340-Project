package fxapp;

import controller.LoginController;
import controller.MainScreenController;
import controller.SQLInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
        Scene scene = new Scene(loginRoot, 500, 400);
        LoginController controller = loader.getController();
        controller.setMainApp(this);
        stage.setTitle("Welcome");
        stage.setScene(scene);
        stage.showAndWait();
        return controller.loggedIn;
    }


    public Stage mainScreen;
    private Pane rootLayout;

    /**
     * Initialize main view. First show registration.
     */
    private void initRootLayout(Stage mainScreen) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApplication.class.getResource("../main.fxml"));
        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            System.out.println(e);
        }

        MainScreenController controller = loader.getController();
        controller.user = currentUser;
        controller.disableAdminScreen();
        controller.setMainApp(this);

        mainScreen.setTitle("Clean Water");

        Scene scene = new Scene(rootLayout, 800, 600);
        mainScreen.setScene(scene);
        mainScreen.show();
    }

    public static void main(String... args) {
        // This method is here so that you get a more helpful stacktrace if the app fails to construct.
        launch(args);
    }
}