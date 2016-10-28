package model;

import java.time.LocalDate;

public class Report {
    private int report_id;
    private String reportedBy;
    private LocalDate date;
    private int hour;
    private int minute;
    private double latitude;
    private double longitude;

    public int getReport_id() {
        return report_id;
    }

    public void setReport_id(int report_id) {
        this.report_id = report_id;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String name) {
        this.reportedBy = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHour() { return hour; }

    public void setHour(int hour) { this.hour = hour; }

    public int getMinute() { return minute; }

    public void setMinute(int minute) { this.minute = minute; }

    public double getLatitude() { return latitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    /**
     * @return HTML encoding to be used in map view.
     */
    public String toInfoWindow() {
        return "<h3>" + getDate().toString() + " " + getHour() + ":" + getMinute() + "</h3>";
    }

}
