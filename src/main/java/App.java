import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.*;
import javafx.fxml.*;

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