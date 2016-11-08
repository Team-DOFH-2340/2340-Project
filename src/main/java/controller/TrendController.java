package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import model.TrendReportType;
import model.WaterQualityReport;

import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;
import com.lynden.gmapsfx.javascript.object.LatLong;

/**
 * Created by hayde on 11/7/2016.
 */
public class TrendController implements Initializable {

    @FXML
    ScatterChart<String, Double> chart;

    ScatterChart.Series<String, Double> series  = new ScatterChart.Series<>();

    /** Called as the Controller is starting. */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Collection<WaterQualityReport> a = SQLInterface.getAllQualityReportsInSysten();
        chart.getData().add(series);
        for (WaterQualityReport w: a) {
            // this part makes the data show up on the graph, but it shows all the data
            series.getData().add(new ScatterChart.Data<>(w.getDate().toString(), w.getVirusPPM()));
            series.getData().add(new ScatterChart.Data<>(w.getDate().toString(), w.getContaminantPPM()));
        }
    }

    public void setCriteria(LocalDate startDate, LocalDate endDate, Double latitude, Double longitude, Double radius, TrendReportType type) {
        Collection<WaterQualityReport> a = SQLInterface.getAllQualityReportsInSysten();
        series.nameProperty().setValue(type.toString());

        LatLong searchPoint = new LatLong(latitude, longitude);

        for (WaterQualityReport w: a) {
            /*System.out.println(w.getContaminantPPM());
            System.out.println(w.getVirusPPM());
            System.out.println("Start date: " + startDate.compareTo(w.getDate()));
            System.out.println("End date: " + endDate.compareTo(w.getDate()));
            System.out.println("Distance from search point: " + searchPoint.distanceFrom(new LatLong(w.getLatitude(), w.getLongitude())));
            // I'm pretty sure this filter part works, but I can't add data at this point.
            if (startDate.compareTo(w.getDate()) <= 0 && endDate.compareTo(w.getDate()) >= 0 && searchPoint.distanceFrom(new LatLong(w.getLatitude(), w.getLongitude())) <= radius) {
                System.out.println("Checks out!");
                if(type == TrendReportType.VIRUS) {
                    System.out.println("Add virus data");*/
                    series.getData().add(new ScatterChart.Data<>(w.getDate().toString(), w.getVirusPPM()));
                /*} else {
                    System.out.println("Add contaminant data");*/
                    series.getData().add(new ScatterChart.Data<>(w.getDate().toString(), w.getContaminantPPM()));
                /*}
            } else {
                System.out.println("One of those failed.");
            }*/
        }

    }
}
