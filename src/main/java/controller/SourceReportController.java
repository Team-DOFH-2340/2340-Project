package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Person;
import model.WaterSourceCondition;
import model.WaterSourceReport;
import model.WaterSourceType;

import java.time.LocalDate;

/**
 * Controller for SourceReport creation view.
 */
public class SourceReportController {
    @FXML
    private Button submitBtn;
    @FXML
    private ChoiceBox<WaterSourceType> typeField;
    @FXML
    private ChoiceBox<WaterSourceCondition> conditionField;
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

    private Person user;

    private MainScreenController mainscreencontroller;

    @FXML
    private void initialize() {
        typeField.getItems().setAll(WaterSourceType.values());
        typeField.setValue(WaterSourceType.BOTTLED);
        this.configTimeFields();
        conditionField.getItems().setAll(WaterSourceCondition.values());
        conditionField.setValue(WaterSourceCondition.POTABLE);
        this.dateField.setValue(LocalDate.now());
    }

    private void configTimeFields() {
        for (int i = 1; i <= 12; ++i) {
            timeHourField.getItems().add(i);
        }
        timeHourField.setValue(12);
//        latitudeField.setText("33.762909");
//        longitudeField.setText("-84.422675");

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
        WaterSourceReport newReport = new WaterSourceReport();
        newReport.setName(user.getName());
        newReport.setDate(dateField.getValue());
        int amPm = amPmField.getValue().equals("AM") ? 0 : 12;
        newReport.setHour(timeHourField.getValue() + amPm);
        String minute = timeMinuteField.getValue();
        newReport.setMinute(Integer.parseInt(minute));
        newReport.setLatitude(Double.parseDouble(latitudeField.getText()));
        newReport.setLongitude(Double.parseDouble(longitudeField.getText()));
        newReport.setType(typeField.getValue());
        newReport.setCondition(conditionField.getValue());
        SQLInterface.createWaterSourceReport(newReport);
        System.out.println("Report entered successfully");
        // Refresh unconditionally from controller upon return instead of this?
        mainscreencontroller.refreshMapPins();
        ((Stage)submitBtn.getScene().getWindow()).close();
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
