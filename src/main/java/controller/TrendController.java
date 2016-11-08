package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import model.WaterQualityReport;

import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by hayde on 11/7/2016.
 */
public class TrendController implements Initializable {

    ScatterChart<LocalDate, Double> chart;

    /** Called as the Controller is starting. */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Collection<WaterQualityReport> a = SQLInterface.getAllQualityReportsInSysten();
        ScatterChart.Series<LocalDate, Double> series  = new ScatterChart.Series<>();
        for (WaterQualityReport w: a) {
            series.getData().add(new ScatterChart.Data<LocalDate, Double>(w.getDate(), w.getContaminantPPM()));
        }
        chart.getData().add(series);
    }
}
