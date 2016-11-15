package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import model.TrendReportType;
import model.WaterQualityReport;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import com.lynden.gmapsfx.javascript.object.LatLong;

/**
 * Controller for the TrendView.
 */
public class TrendController implements Initializable {

    @FXML
    ScatterChart<String, Double> chart;

    ScatterChart.Series<String, Double> series  = new ScatterChart.Series<>();

    private final String[] months = new String[] {"Jan", "Feb", "March", "April", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};


    @FXML
    NumberAxis yAxis;
    @FXML
    CategoryAxis xAxis;

    public TrendController() { System.out.println("Constructed with no args.");}

    public TrendController(LocalDate startDate, LocalDate endDate, Double latitude, Double longitude, Double radius, TrendReportType type) {
        System.out.println("Constructed with args.");

    }
    /** Called as the Controller is starting. */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chart.setAnimated(false);
        //yAxis.setUpperBound(1000);
        yAxis.setLowerBound(0);
        ObservableList<String> xLabels = FXCollections.observableArrayList();
        xLabels.addAll(months);
        xAxis.setCategories(xLabels);
        xAxis.setTickLabelsVisible(true);
        yAxis.setAutoRanging(true);
    }

    public void setUp(LocalDate startDate, LocalDate endDate, Double latitude, Double longitude, Double radius, TrendReportType type) {
        Collection<WaterQualityReport> a = SQLInterface.getAllQualityReportsInSysten();
        LatLong searchPoint = new LatLong(latitude, longitude);


        HashMap<String, Double> sums = new HashMap<>();
        HashMap<String, Integer> count = new HashMap<>();
        for (WaterQualityReport w: a) {
            if (startDate.compareTo(w.getDate()) <= 0 && endDate.compareTo(w.getDate()) >= 0 && searchPoint.distanceFrom(new LatLong(w.getLatitude(), w.getLongitude())) <= radius) {
                String key = months[w.getDate().getMonth().getValue() - 1];
                count.put(key, count.getOrDefault(key, 0) + 1);
                Double putting = sums.getOrDefault(key, 0.00);
                Double adding;
                if(type == TrendReportType.VIRUS) {
                    adding = w.getVirusPPM();
                } else {
                    adding = w.getContaminantPPM();
                }
                sums.put(key, putting + adding);
            }
        }

        ScatterChart.Series<String, Double> series = new ScatterChart.Series<>();
        for (String month : months) { // 1 to skip null first field
            Double average = sums.getOrDefault(month, 0.00) / count.getOrDefault(month, 1);
            series.getData().add(new ScatterChart.Data<>(month, average));
        }
        series.setName("PPM");
        ObservableList<ScatterChart.Series<String, Double>> data = FXCollections.observableArrayList();
        data.addAll(series);
        chart.getData().clear();
        chart.getData().addAll(series);
    }
}
