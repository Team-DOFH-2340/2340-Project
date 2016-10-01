import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Enum used to choose account type.
 */
public enum UserType {
    USER("User", new Privilege[]{ Privilege.VIEW_SOURCES, Privilege.SUBMIT_WATER_AVAILABILITY }, null ),
    WORKER("Worker", new Privilege[]{ Privilege.SUBMIT_WATER_PURITY }, USER.privileges),
    MANAGER("Manager", new Privilege[]{ Privilege.VIEW_TRENDS, Privilege.DELETE_REPORTS}, WORKER.privileges),
    ADMIN("Admin", new Privilege[] { Privilege.BAN_USER, Privilege.DELETE_USER, Privilege.UNBLOCK_USER, Privilege.VIEW_LOG },
            MANAGER.privileges);

    private String name;
    private List<Privilege> privileges;

    private UserType(String name, Privilege[] privileges, List<Privilege> extraPrivileges) {
        this.name = name;
        this.privileges = new LinkedList<>(Arrays.asList(privileges));
        this.privileges.addAll(extraPrivileges);
    }

    @Override
    public String toString() { return name; }
}
