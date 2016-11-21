package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.java.model.*;

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

    @FXML
    private void initialize() {
        typeField.getItems().setAll(TrendReportType.values());
    }

    /** Creates a new WaterSourceReport and populates its data from the view. */
    public void submit() throws Exception { // TODO REMOVE
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("trendview.fxml"));
            Parent loginRoot = loader.load();
            Scene scene = new Scene(loginRoot, 800, 600);
            loader.setRoot(scene);

            TrendController controller = loader.getController();
            controller.setUp(startDateField.getValue(), endDateField.getValue(), Double.parseDouble(latitudeField.getText()), Double.parseDouble(longitudeField.getText()), Double.parseDouble(radiusField.getText()), typeField.getValue());
            stage.setTitle("Historical Trends");
            stage.setScene(scene);
            ((Stage) submitBtn.getScene().getWindow()).close();
            Thread.sleep(1000);
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

    /** Closes the window. */
    public void cancel() {
        ((Stage)submitBtn.getScene().getWindow()).close();
    }
}
