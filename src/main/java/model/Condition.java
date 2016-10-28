package model;

/**
 * Created by hayde on 10/27/2016.
 */
public enum Condition {
    SAFE("Safe"),
    TREATABLE("Treatable"),
    UNSAFE("Unsafe");

    private final String desc;

    /**
     * @param desc: Human readable name of the priviledge
     */
    Condition(final String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return desc;
    }
}
