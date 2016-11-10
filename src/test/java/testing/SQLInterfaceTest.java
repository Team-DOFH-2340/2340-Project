package testing;

import controller.SQLInterface;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jminieri on 11/10/16.
 */
public class SQLInterfaceTest {
    @Before
    public void setUp() {
        SQLInterface.init();

    }

    @Test
    public void authenticate() {
        assertTrue(SQLInterface.authenticate("Harambe", "a").getName().equals("Harambe"));
        assertFalse(SQLInterface.authenticate("steve11", "steveJobs").getTitle().equals("CIO"));
        assertTrue(SQLInterface.authenticate("bobwaters", "bobw4t3rs").getName().equals("Bob Waters"));
        assertFalse(SQLInterface.authenticate("bottledwaters", "fake1").getName().equals("Bob Waters"));
        assertNull(SQLInterface.authenticate("bobmarley", "fake2"));
        assertNull(SQLInterface.authenticate("Harambe", "I am Dead"));
        assertNull(SQLInterface.authenticate("bobwaters", "bobwaters"));
    }

}