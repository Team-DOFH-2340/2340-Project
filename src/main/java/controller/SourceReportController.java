package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Person;
import model.WaterSourceCondition;
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
    private TextField nameField;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField timeField;
    @FXML
    private TextField locationField;

    private Person user;

    @FXML
    private void initialize() {
        typeField.getItems().setAll(WaterSourceType.values());
        typeField.setValue(WaterSourceType.BOTTLED);
        conditionField.getItems().setAll(WaterSourceCondition.values());
        conditionField.setValue(WaterSourceCondition.POTABLE);
        this.dateField.setValue(LocalDate.now());
    }

    public void setUser(Person user) {
        this.user = user;
        this.nameField.setText(user.name);

    }

    public void submit() {

    }

    public void cancel() {
        ((Stage)submitBtn.getScene().getWindow()).close();
    }
}
