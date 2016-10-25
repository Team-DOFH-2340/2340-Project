package model;

import java.time.LocalDate;

/**
 * Data holder for everything in a source report.
 */
public class WaterSourceReport {
    private int report_id;
    private String name;
    private LocalDate date;
    private int hour;
    private int minute;
    private double latitude;
    private double longitude;
    private WaterSourceCondition condition;
    private WaterSourceType type;

    public int getReport_id() {
        return report_id;
    }

    public void setReport_id(int report_id) {
        this.report_id = report_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public WaterSourceCondition getCondition() {
        return condition;
    }

    public void setCondition(WaterSourceCondition condition) {
        this.condition = condition;
    }

    public WaterSourceType getType() {
        return type;
    }

    public void setType(WaterSourceType type) {
        this.type = type;
    }

    /**
     * @return HTML encoding to be used in map view.
     */
    public String toInfoWindow() {
        return "<h3>" + date.toString() + " " + hour + ":" + minute + "</h3>" + condition + "<br/>" + type;
    }
}
