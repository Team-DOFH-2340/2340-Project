package model;

/**
 * Enum for the qualities that a water source can have.
 */
public enum WaterSourceCondition {
    WASTE("Waste"),
    TREATABLE_CLEAR("Treatable-Clear"),
    TREATABLE_MUDDY("Treatable-Muddy"),
    POTABLE("Potable");

    private String name;

    WaterSourceCondition(String name) {
        this.name = name;
    }
}
