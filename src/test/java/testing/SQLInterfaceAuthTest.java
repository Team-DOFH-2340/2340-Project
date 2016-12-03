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
        if (SQLInterface.getInstance().createLogin("dude", "a", "dude", 3)) {
            userNames.add("dude");
        }
        if (SQLInterface.getInstance().createLogin("steve11", "steveJobs", "Steve Jobs", 3)) {
            userNames.add("steve11");
        }

        if (SQLInterface.getInstance().createLogin("bobwaters", "bobw4t3rs", "Bob Waters", 1)) {
            userNames.add("bobwaters");
        }
        if (SQLInterface.getInstance().createLogin("bottledwaters", "fake1", "Bottled Waters", 2)) {
            userNames.add("bottlewaters");
        }

    }
    @After
    public void tearDown() {
        for (String name: userNames) {
            SQLInterface.getInstance().deleteUser(name);
        }
    }
    @Test
    public void authenticate() {
        assertTrue(SQLInterface.getInstance().authenticate("dude", "a").getName().equals("dude"));
        assertFalse(SQLInterface.getInstance().authenticate("steve11", "steveJobs").getTitle().equals("CIO"));
        assertTrue(SQLInterface.getInstance().authenticate("bobwaters", "bobw4t3rs").getName().equals("Bob Waters"));
        assertFalse(SQLInterface.getInstance().authenticate("bottledwaters", "fake1").getName().equals("Bob Waters"));
        assertNull(SQLInterface.getInstance().authenticate("bobmarley", "fake2"));
        assertNull(SQLInterface.getInstance().authenticate("Harambe", "I am Dead"));
        assertNull(SQLInterface.getInstance().authenticate("bobwaters", "bobwaters"));
    }

}
