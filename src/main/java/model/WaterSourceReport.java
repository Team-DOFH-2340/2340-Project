package main.java.model;
import java.time.LocalDate;

/**
 * Data holder for everything in a source report.
 */
public class WaterSourceReport extends Report {
    private WaterSourceCondition condition;
    private WaterSourceType type;

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
        return super.toInfoWindow() + condition + "<br/>" + type;
    }

    @Override
    public String getIconURL() {
        return "http://maps.google.com/mapfiles/ms/icons/red-dot.png";
    }

    public WaterSourceReport(int reportId, String reportedBy, LocalDate date, int hour, int minute, double lat, double longi,
                             WaterSourceType type, WaterSourceCondition cond) {
        super(reportId, reportedBy, date, hour, minute, lat, longi);
        this.type = type;
        this.condition = cond;
    }

    public WaterSourceReport() { super(); }

    @Override
    public boolean equals(Object o) {
        if ((o instanceof Report)) {
            Report r = (Report) o;
            return r.getDate().equals(this.getDate()) && this.getHour() == r.getHour() && this.getMinute() == r.getMinute() &&
                    this.getReportedBy().equals(r.getReportedBy());
        } else {
            System.out.println("NOT REPORT!");
            return false;
        }
    }
}
