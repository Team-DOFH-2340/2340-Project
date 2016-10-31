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
    private TableView<WaterSourceReport> sourceView;
    @FXML
    private TableColumn<WaterSourceReport, Integer> s_idField;
    @FXML
    private TableColumn<WaterSourceReport, String> s_usernameField;
    @FXML
    private TableColumn<WaterSourceReport, String> s_dateField;
    @FXML
    private TableColumn<WaterSourceReport, Integer> s_hourField;
    @FXML
    private TableColumn<WaterSourceReport, Integer> s_minuteField;
    @FXML
    private TableColumn<WaterSourceReport, Double> s_latitudeField;
    @FXML
    private TableColumn<WaterSourceReport, Double> s_longitudeField;
    @FXML
    private TableColumn<WaterSourceReport, Integer> s_typeField;
    @FXML
    private TableColumn<WaterSourceReport, Integer> s_conditionField;

    /** Loads data from database into the admin view table. */
    public void loadData() {
        Collection<WaterSourceReport> reports = SQLInterface.getAllSourceReportsInSystem();

        s_idField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Integer>("report_id"));
        s_usernameField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, String>("reportedBy"));
        s_dateField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, String>("date"));
        s_hourField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Integer>("hour"));
        s_minuteField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Integer>("minute"));
        s_latitudeField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Double>("latitude"));
        s_longitudeField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Double>("longitude"));
        s_typeField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Integer>("type"));
        s_conditionField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Integer>("condition"));

        for (WaterSourceReport report: reports) {
            sourceView.getItems().add(report);
        }
    }
}
