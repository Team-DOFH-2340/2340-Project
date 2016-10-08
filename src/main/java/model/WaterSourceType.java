package model;

/**
 * Created by willi on 10/7/2016.
 */
public enum WaterSourceType {
    BOTTLED("Bottled"),
    WELL("Well"),
    STREAM("Stream"),
    LAKE("Lake"),
    SPRING("Spring"),
    OTHER("Other");

    private String name;

    WaterSourceType(String name) {
        this.name = name;
    }
}
