package controller;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.Person;
import model.Report;
import model.WaterQualityReport;
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
    @FXML
    private TableView<WaterQualityReport> qualityView;
    @FXML
    private TableColumn<WaterSourceReport, Integer> q_idField;
    @FXML
    private TableColumn<WaterSourceReport, String> q_usernameField;
    @FXML
    private TableColumn<WaterSourceReport, String> q_dateField;
    @FXML
    private TableColumn<WaterSourceReport, Integer> q_hourField;
    @FXML
    private TableColumn<WaterSourceReport, Integer> q_minuteField;
    @FXML
    private TableColumn<WaterSourceReport, Double> q_latitudeField;
    @FXML
    private TableColumn<WaterSourceReport, Double> q_longitudeField;
    @FXML
    private TableColumn<WaterSourceReport, Double> q_virusField;
    @FXML
    private TableColumn<WaterSourceReport, Double> q_contaminantField;
    @FXML
    private TableColumn<WaterSourceReport, Integer> q_conditionField;
    @FXML
    private TableView<Person> userView;
    @FXML
    private TableColumn<Person, String> u_typeField;
    @FXML
    private TableColumn<Person, String> u_titleField;
    @FXML
    private TableColumn<Person, String> u_usernameField;
    @FXML
    private TableColumn<Person, String> u_nameField;
    @FXML
    private TableColumn<Person, String> u_emailField;
    @FXML
    private TableColumn<Person, String> u_addressField;

    /** Loads data from database into the admin view table. */
    public void loadData() {
        Collection<Person> people1 = SQLInterface.getAllUsersInSystem();

        u_typeField.setCellValueFactory(new PropertyValueFactory<Person, String>("type"));
        u_titleField.setCellValueFactory(new PropertyValueFactory<Person, String>("title"));
        u_usernameField.setCellValueFactory(new PropertyValueFactory<Person, String>("username"));
        u_nameField.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        u_emailField.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
        u_addressField.setCellValueFactory(new PropertyValueFactory<Person, String>("address"));

        for (Person person: people1) {
            userView.getItems().add(person);
        }

        Collection<WaterSourceReport> reports1 = SQLInterface.getAllSourceReportsInSystem();

        s_idField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Integer>("report_id"));
        s_usernameField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, String>("reportedBy"));
        s_dateField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, String>("date"));
        s_hourField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Integer>("hour"));
        s_minuteField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Integer>("minute"));
        s_latitudeField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Double>("latitude"));
        s_longitudeField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Double>("longitude"));
        s_typeField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Integer>("type"));
        s_conditionField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Integer>("condition"));

        for (WaterSourceReport report: reports1) {
            sourceView.getItems().add(report);
        }

        Collection<WaterQualityReport> reports2 = SQLInterface.getAllQualityReportsInSysten();

        q_idField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Integer>("report_id"));
        q_usernameField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, String>("reportedBy"));
        q_dateField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, String>("date"));
        q_hourField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Integer>("hour"));
        q_minuteField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Integer>("minute"));
        q_latitudeField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Double>("latitude"));
        q_longitudeField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Double>("longitude"));
        q_virusField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Double>("virusPPM"));
        q_contaminantField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Double>("contaminantPPM"));
        q_conditionField.setCellValueFactory(new PropertyValueFactory<WaterSourceReport, Integer>("condition"));

        for (WaterQualityReport report: reports2) {
            qualityView.getItems().add(report);
        }
    }
}
