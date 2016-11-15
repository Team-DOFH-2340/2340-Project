package testing;

import controller.SQLInterface;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by jminieri on 11/10/16.
 */
public class SQLInterfaceAuthTest {
    private ArrayList<String> userNames = new ArrayList<>();
    @Before
    public void setUp() {
        SQLInterface.init();
        if (SQLInterface.createLogin("dude", "a", "dude", 3)) {
            userNames.add("dude");
        }
        if (SQLInterface.createLogin("steve11", "steveJobs", "Steve Jobs", 3)) {
            userNames.add("steve11");
        }

        if (SQLInterface.createLogin("bobwaters", "bobw4t3rs", "Bob Waters", 1)) {
            userNames.add("bobwaters");
        }
        if (SQLInterface.createLogin("bottledwaters", "fake1", "Bottled Waters", 2)) {
            userNames.add("bottlewaters");
        }

    }
    @After
    public void tearDown() {
        for (String name: userNames) {
            SQLInterface.deleteUser(name);
        }
    }
    @Test
    public void authenticate() {
        assertTrue(SQLInterface.authenticate("dude", "a").getName().equals("dude"));
        assertFalse(SQLInterface.authenticate("steve11", "steveJobs").getTitle().equals("CIO"));
        assertTrue(SQLInterface.authenticate("bobwaters", "bobw4t3rs").getName().equals("Bob Waters"));
        assertFalse(SQLInterface.authenticate("bottledwaters", "fake1").getName().equals("Bob Waters"));
        assertNull(SQLInterface.authenticate("bobmarley", "fake2"));
        assertNull(SQLInterface.authenticate("Harambe", "I am Dead"));
        assertNull(SQLInterface.authenticate("bobwaters", "bobwaters"));
    }

}