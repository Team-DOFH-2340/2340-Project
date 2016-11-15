package main.java.model;

/**
 * Enum for all of the possible types of water source.
 * */
public enum TrendReportType {
    VIRUS("Virus"),
    CONTAMINANT("Contaminant");

    private final String name;

    TrendReportType(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return name;
    }
}
