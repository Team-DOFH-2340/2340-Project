package model;

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
}
