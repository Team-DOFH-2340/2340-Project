import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
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
}