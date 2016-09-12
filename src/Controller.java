import javafx.scene.control.TextField;
import javafx.fxml.FXML;

public class Controller {

	@FXML private TextField usernameField;
	@FXML private TextField passwordField;

	public void login() {
		System.out.println(usernameField.getText());
		System.out.println(passwordField.getText());
		if (SQLInterface.authenticate(usernameField.getText(), passwordField.getText())) {
			System.out.println("login successful");
		} else {
			System.out.println("login failure");
		}
	}

	public void register() {
		System.out.println(usernameField.getText());
		System.out.println(passwordField.getText());
		if (SQLInterface.createLogin(usernameField.getText(), passwordField.getText())) {
			System.out.println("registration successful");
		} else {
			System.out.println("registration failure");
		}
	}
}