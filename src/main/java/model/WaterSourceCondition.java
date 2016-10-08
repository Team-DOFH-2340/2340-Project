package model;

/**
 * Created by willi on 10/7/2016.
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
