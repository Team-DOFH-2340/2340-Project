package model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import static model.Privilege.*;

/**
 * Enum used to choose account type.
 */
public enum UserType {
    USER("User", new Privilege[]{VIEW_SOURCES, SUBMIT_WATER_AVAILABILITY}, null),
    WORKER("Worker", new Privilege[]{SUBMIT_WATER_PURITY}, USER.privileges),
    MANAGER("Manager", new Privilege[]{VIEW_TRENDS, DELETE_REPORTS}, WORKER.privileges),
    ADMIN("Admin", new Privilege[]{BAN_USER, DELETE_USER, UNBLOCK_USER, VIEW_LOG, VIEW_ADMIN_SCREEN}, MANAGER.privileges);

    private String name;
    private List<Privilege> privileges;

    UserType(String name, Privilege[] privileges, List<Privilege> extraPrivileges) {
        this.name = name;
        this.privileges = new LinkedList<>(Arrays.asList(privileges));
        if (extraPrivileges != null) {
            this.privileges.addAll(extraPrivileges);
        }
    }

    public List<Privilege> getPrivileges() {
        return this.privileges;
    }

    public boolean hasPrivilege(Privilege p) {
        return this.privileges.contains(p);
    }

    @Override
    public String toString() {
        return name;
    }
}
