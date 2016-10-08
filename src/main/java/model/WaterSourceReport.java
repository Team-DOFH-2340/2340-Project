package model;

import java.time.LocalDate;

/**
 * Created by willi on 10/7/2016.
 */
public class WaterSourceReport {
    private int report_id;
    private String name;
    private LocalDate date;
    private int hour;
    private int minute;
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
}
