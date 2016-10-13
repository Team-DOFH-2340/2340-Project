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
 * Created by willi on 10/6/2016.
 */
public class SourceReportController {
    @FXML
    private Button submitBtn;
    @FXML
    private Button CancelBtn;
    @FXML
    private ChoiceBox<WaterSourceType> typeField;
    @FXML
    private ChoiceBox<WaterSourceCondition> conditionField;
    @FXML
    private DatePicker dateField;
    @FXML
    private ChoiceBox<String> timeMinuteField;
    @FXML
    private ChoiceBox<Integer> timeHourField;
    @FXML
    private TextField latitudeField;
    @FXML
    private TextField longitudeField;

    private Person user;

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
        for (int i = 1; i <= 24; ++i) {
            timeHourField.getItems().add(i);
        }
        timeHourField.setValue(12);
        latitudeField.setText("33.762909");
        longitudeField.setText("-84.422675");

        timeMinuteField.getItems().add("00");
        timeMinuteField.getItems().add("15");
        timeMinuteField.getItems().add("30");
        timeMinuteField.getItems().add("45");
        timeMinuteField.setValue("00");
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public void submit() {
        WaterSourceReport newReport = new WaterSourceReport();
        newReport.setName(user.name);
        newReport.setDate(dateField.getValue());
        newReport.setHour(timeHourField.getValue());
        String minute = timeMinuteField.getValue();
        if (minute.equals("00")) {
            newReport.setMinute(0);
        } else if (minute.equals("15")) {
            newReport.setMinute(15);
        } else if (minute.equals("30")) {
            newReport.setMinute(30);
        } else {
            newReport.setMinute(45);
        }
        newReport.setLatitude(Double.parseDouble(latitudeField.getText()));
        newReport.setLongitude(Double.parseDouble(longitudeField.getText()));
        newReport.setType(typeField.getValue());
        newReport.setCondition(conditionField.getValue());
        SQLInterface.createWaterSourceReport(newReport);
        System.out.println("Report entered successfully");
        ((Stage)submitBtn.getScene().getWindow()).close();
    }

    public void cancel() {
        ((Stage)submitBtn.getScene().getWindow()).close();
    }
}
