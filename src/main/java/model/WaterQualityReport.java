package model;

import java.time.LocalDate;

/**
 * Describes a water quality report.
 */
public class WaterQualityReport extends Report {
    private Condition condition;
    private double virusPPM;
    private double contaminantPPM;

    public WaterQualityReport() {

    }

    public WaterQualityReport(Condition condition, double virusPPM, double contaminantPPM, LocalDate date, int hour, int minute, double longi, double lati, String reportedBy) {
        this.condition = condition;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
        this.setDate(date);
        this.setHour(hour);
        this.setMinute(minute);
        this.setLongitude(longi);
        this.setLatitude(lati);
        this.setReportedBy(reportedBy);
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public double getVirusPPM() {
        return virusPPM;
    }

    public void setVirusPPM(double virusPPM) {
        this.virusPPM = virusPPM;
    }

    public double getContaminantPPM() {
        return contaminantPPM;
    }

    public void setContaminantPPM(double contaminantPPM) {
        this.contaminantPPM = contaminantPPM;
    }

    /**
     * @return HTML encoding to be used in map view.
     */
    public String toInfoWindow() {
        return super.toInfoWindow() + condition + "<br/>" + virusPPM + " " + contaminantPPM;
    }

    @Override
    public String getIconURL() {
        return "http://maps.google.com/mapfiles/ms/icons/blue-dot.png";
    }

}
