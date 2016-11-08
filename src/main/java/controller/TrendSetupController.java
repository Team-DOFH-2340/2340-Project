package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.time.LocalDate;

/**
 * Controller for QualityReport creation view.
 */
public class TrendSetupController {
    @FXML
    private Button submitBtn;
    @FXML
    private ChoiceBox<TrendReportType> typeField;
    @FXML
    private DatePicker startDateField;
    @FXML
    private DatePicker endDateField;
    @FXML
    private TextField latitudeField;
    @FXML
    private TextField longitudeField;
    @FXML
    private TextField radiusField;

    private Person user;

    private MainScreenController mainscreencontroller;

    @FXML
    private void initialize() {
        typeField.getItems().setAll(TrendReportType.values());
    }

    public void setUser(Person user) {
        this.user = user;
    }

    /** Creates a new WaterSourceReport and populates its data from the view. */
    public void submit() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../trendview.fxml"));
            Parent loginRoot = loader.load();
            Scene scene = new Scene(loginRoot, 800, 600);
            TrendController controller = loader.getController();
            controller.setCriteria(startDateField.getValue(), endDateField.getValue(), Double.parseDouble(latitudeField.getText()), Double.parseDouble(longitudeField.getText()), Double.parseDouble(radiusField.getText()), typeField.getValue());
            stage.setTitle("Historical Trends");
            stage.setScene(scene);
            ((Stage) submitBtn.getScene().getWindow()).close();
            stage.showAndWait();
        } catch (java.io.IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Failed to display trend window");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Invalid numerical field");
            alert.showAndWait();
        }
    }

    /** Called to give a reference to the main controller. */
    public void linkMainController(MainScreenController controller) {
        mainscreencontroller = controller;
    }

    /** Closes the window. */
    public void cancel() {
        ((Stage)submitBtn.getScene().getWindow()).close();
    }
}
