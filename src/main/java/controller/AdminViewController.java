package main.java.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import main.java.model.*;

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
    private TableColumn<WaterSourceReport, String> s_typeField;
    @FXML
    private TableColumn<WaterSourceReport, String> s_conditionField;
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

    private MainScreenController mainscreencontroller;

    /** Loads data from database into the admin view table. */
    public void loadData() {
        Collection<Person> people1 = SQLInterface.getAllUsersInSystem();

        u_typeField.setCellValueFactory(new PropertyValueFactory<>("type"));
        u_titleField.setCellValueFactory(new PropertyValueFactory<>("title"));
        u_usernameField.setCellValueFactory(new PropertyValueFactory<>("username"));
        u_nameField.setCellValueFactory(new PropertyValueFactory<>("name"));
        u_emailField.setCellValueFactory(new PropertyValueFactory<>("email"));
        u_addressField.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHomeAddress().toString()));

        for (Person person: people1) {
            userView.getItems().add(person);
        }

        Collection<WaterSourceReport> reports1 = SQLInterface.getAllSourceReportsInSystem();

        s_idField.setCellValueFactory(new PropertyValueFactory<>("report_id"));
        s_usernameField.setCellValueFactory(new PropertyValueFactory<>("reportedBy"));
        s_dateField.setCellValueFactory(new PropertyValueFactory<>("date"));
        s_hourField.setCellValueFactory(new PropertyValueFactory<>("hour"));
        s_minuteField.setCellValueFactory(new PropertyValueFactory<>("minute"));
        s_latitudeField.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        s_longitudeField.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        s_typeField.setCellValueFactory(new PropertyValueFactory<>("type"));
        s_conditionField.setCellValueFactory(new PropertyValueFactory<>("condition"));

        for (WaterSourceReport report: reports1) {
            sourceView.getItems().add(report);
        }

        Collection<WaterQualityReport> reports2 = SQLInterface.getAllQualityReportsInSysten();

        q_idField.setCellValueFactory(new PropertyValueFactory<>("report_id"));
        q_usernameField.setCellValueFactory(new PropertyValueFactory<>("reportedBy"));
        q_dateField.setCellValueFactory(new PropertyValueFactory<>("date"));
        q_hourField.setCellValueFactory(new PropertyValueFactory<>("hour"));
        q_minuteField.setCellValueFactory(new PropertyValueFactory<>("minute"));
        q_latitudeField.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        q_longitudeField.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        q_virusField.setCellValueFactory(new PropertyValueFactory<>("virusPPM"));
        q_contaminantField.setCellValueFactory(new PropertyValueFactory<>("contaminantPPM"));
        q_conditionField.setCellValueFactory(new PropertyValueFactory<>("condition"));

        for (WaterQualityReport report: reports2) {
            qualityView.getItems().add(report);
        }

        sourceView.setOnKeyPressed(t -> {
            if (t.getCode() == KeyCode.DELETE) {
                WaterSourceReport report = sourceView.getSelectionModel().getSelectedItem();
                SQLInterface.deleteReport("WaterSource", report.getReport_id());
                sourceView.getItems().remove(report);
                try {
                    mainscreencontroller.removePin(false, report.getReport_id());
                } catch(Exception ignored) {

                }
            }
        });

        qualityView.setOnKeyPressed(t -> {
            if (t.getCode() == KeyCode.DELETE) {
                WaterQualityReport report = qualityView.getSelectionModel().getSelectedItem();
                SQLInterface.deleteReport("WaterQuality", report.getReport_id());
                qualityView.getItems().remove(report);
                try {
                    mainscreencontroller.removePin(true, report.getReport_id());
                } catch(Exception ignored) {

                }
            }
        });
    }

    /** Called to give a reference to the main controller. */
    public void linkMainController(MainScreenController controller) {
        mainscreencontroller = controller;
    }
}