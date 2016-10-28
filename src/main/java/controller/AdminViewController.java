package controller;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.Report;
import model.WaterSourceReport;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Controller for the Admin view.
 */
public class AdminViewController {

    @FXML
    private TableView<WaterSourceReport> tableView;
    @FXML
    private TableColumn<WaterSourceReport, Integer> idField;
    @FXML
    private TableColumn<WaterSourceReport, String> usernameField;
    @FXML
    private TableColumn<WaterSourceReport, String> dateField;
    @FXML
    private TableColumn<WaterSourceReport, Integer> hourField;
    @FXML
    private TableColumn<WaterSourceReport, Integer> minuteField;
    @FXML
    private TableColumn<WaterSourceReport, Double> latitudeField;
    @FXML
    private TableColumn<WaterSourceReport, Double> longitudeField;
    @FXML
    private TableColumn<WaterSourceReport, Integer> typeField;
    @FXML
    private TableColumn<WaterSourceReport, Integer> conditionField;

    /** Loads data from database into the admin view table. */
    public void loadData() {
        Collection<WaterSourceReport> reports = SQLInterface.getAllSourceReportsInSystem();

        idField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Integer>("report_id"));
        usernameField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, String>("name"));
        dateField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, String>("date"));
        hourField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Integer>("hour"));
        minuteField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Integer>("minute"));
        latitudeField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Double>("latitude"));
        longitudeField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Double>("longitude"));
        typeField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Integer>("type"));
        conditionField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Integer>("condition"));

        for (WaterSourceReport report: reports) {
            tableView.getItems().add(report);
        }
    }
}
