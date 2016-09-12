import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.*;
import javafx.fxml.*;

public class Test extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		SQLInterface.init();
		SQLInterface.checkDatabase();


		Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));

		Scene scene = new Scene(root, 800, 600);
		stage.setTitle("Welcome");
		stage.setScene(scene);
		stage.show();
	}
}