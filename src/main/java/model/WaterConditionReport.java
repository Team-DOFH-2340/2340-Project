package model;

/**
 * Enum for the qualities that a water source can have.
 */
public enum WaterConditionReport {
    WASTE("Waste"),
    TREATABLE_CLEAR("Treatable-Clear"),
    TREATABLE_MUDDY("Treatable-Muddy"),
    POTABLE("Potable");

    private String name;

    WaterConditionReport(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
