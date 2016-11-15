package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.java.model.*;

import java.time.LocalDate;

/**
 * Controller for QualityReport creation view.
 */
public class QualityReportController {
    @FXML
    private Button submitBtn;
    @FXML
    private ChoiceBox<Condition> conditionField;
    @FXML
    private DatePicker dateField;
    @FXML
    private ChoiceBox<String> timeMinuteField;
    @FXML
    private ChoiceBox<String> amPmField;
    @FXML
    private ChoiceBox<Integer> timeHourField;
    @FXML
    private TextField latitudeField;
    @FXML
    private TextField longitudeField;
    @FXML
    private TextField virusField;
    @FXML
    private TextField contaminantField;

    private Person user;

    private MainScreenController mainscreencontroller;

    @FXML
    private void initialize() {
        this.configTimeFields();
        conditionField.getItems().setAll(Condition.values());
        conditionField.setValue(Condition.SAFE);
        this.dateField.setValue(LocalDate.now());
    }

    private void configTimeFields() {
        for (int i = 1; i <= 12; ++i) {
            timeHourField.getItems().add(i);
        }
        timeHourField.setValue(12);

        latitudeField.setEditable(false);
        longitudeField.setEditable(false);

        timeMinuteField.getItems().addAll("00", "15", "30", "45");
        timeMinuteField.setValue("00");
        amPmField.getItems().addAll("AM", "PM");
        amPmField.setValue("AM");
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public void setLatLon(double lat, double lon) {
        latitudeField.setText(Double.toString(lat));
        longitudeField.setText(Double.toString(lon));
    }

    /** Creates a new WaterSourceReport and populates its data from the view. */
    public void submit() {
        try {
            WaterQualityReport newReport = new WaterQualityReport();
            newReport.setReportedBy(user.getName());
            newReport.setDate(dateField.getValue());
            int amPm = amPmField.getValue().equals("AM") ? 0 : 12;
            int hour = timeHourField.getValue() == 12 ? 0 : timeHourField.getValue();
            newReport.setHour(hour + amPm);
            String minute = timeMinuteField.getValue();
            newReport.setMinute(Integer.parseInt(minute));
            newReport.setLatitude(Double.parseDouble(latitudeField.getText()));
            newReport.setLongitude(Double.parseDouble(longitudeField.getText()));
            newReport.setContaminantPPM(Double.parseDouble(contaminantField.getText()));
            newReport.setVirusPPM(Double.parseDouble(virusField.getText()));
            newReport.setCondition(conditionField.getValue());
            SQLInterface.createWaterQualityReport(newReport);
            System.out.println("Report entered successfully");
            // Refresh unconditionally from controller upon return instead of this?
            mainscreencontroller.addPin(newReport);
            ((Stage)submitBtn.getScene().getWindow()).close();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Virus and/or Contaminant Field");
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
