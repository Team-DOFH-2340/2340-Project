package model;

/**
 * Enum for all of the possible types of water source.
 * */
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
