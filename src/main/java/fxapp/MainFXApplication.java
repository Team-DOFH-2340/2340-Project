package main.java.fxapp;

import main.java.controller.LoginController;
import main.java.controller.MainScreenController;
import main.java.controller.SQLInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.java.model.Person;

import java.io.IOException;

/**
 * Main starting point for the application.
 */
public class MainFXApplication extends Application {
    public Person currentUser;

    /**
     * @param stage Stage to start the app inside
     * @throws Exception if any errors.
     */
    @Override
    public void start(Stage stage) throws Exception {
        SQLInterface.init();
        SQLInterface.checkDatabase();
        SQLInterface.clean();
        boolean loggedIn = showLogin();
        if (!loggedIn) {
            return;
        }
        initRootLayout(stage);
    }

    /**
     * @return if login was successful
     * @throws IOException If can't find view template.
     */
    private boolean showLogin() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApplication.class.getResource("login.fxml"));
        Parent loginRoot = loader.load();
        Scene scene = new Scene(loginRoot, 500, 400);
        LoginController controller = loader.getController();
        controller.setMainApp(this);
        stage.setTitle("Welcome");
        stage.setScene(scene);
        stage.showAndWait();
        return controller.loggedIn;
    }


    private Pane rootLayout;

    /**
     * Initialize main view. First show registration.
     */
    private void initRootLayout(Stage mainScreen) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApplication.class.getResource("main.fxml"));
        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            //System.out.println(e);
        }

        MainScreenController controller = loader.getController();
        controller.user = currentUser;
        controller.setup();
        controller.setMainApp(this);

        mainScreen.setTitle("Clean Water");

        Scene scene = new Scene(rootLayout, 800, 600);
        mainScreen.setScene(scene);
        mainScreen.show();
    }

    /** This method is here so that you get a more helpful stacktrace if the app fails to construct.
     * @param args Runtime args. Unused.
     */
    public static void main(String... args) {
        launch(args);
    }
}