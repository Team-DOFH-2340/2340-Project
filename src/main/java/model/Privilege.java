package model;

/**
 * Defines the abilities that a user of the application can have.
 */
public enum Privilege {
    VIEW_ADMIN_SCREEN("View admin screen"),
    BAN_USER("Ban users"),
    DELETE_USER("Delete users"),
    UNBLOCK_USER("Unblock users"),
    VIEW_SOURCES("View water sources"),
    DELETE_REPORTS("Delete water reports"),
    VIEW_TRENDS("View purity history/trends"),
    VIEW_LOG("View security log"),
    SUBMIT_WATER_AVAILABILITY("Submit water availability reports"),
    SUBMIT_WATER_PURITY("Submit water purity reports");

    private final String name;

    /**
     * @param name: Human readable name of the priviledge
     */
    Privilege(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
