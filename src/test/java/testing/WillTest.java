package testing;

import java.util.ArrayList;
import controller.SQLInterface;
import model.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by willi on 11/14/2016.
 */
public class WillTest {
    private ArrayList<Person> users = new ArrayList<>();
    @Before
    public void setUp() {
        if (SQLInterface.getInstance().createLogin("Harambe", "asdf", "Harambe", 1)) {
            users.add(new Person("Harambe", "Harambe"));
            users.get(0).setTitle("Gorilla");
        }
        if (SQLInterface.getInstance().createLogin("ForeverAlone", "asdf", "ForeverAlone", 1)) {
            users.add(new Person("ForeverAlone", "ForeverAlone"));
            users.get(1).setTitle("Sad");
        }
        if (SQLInterface.getInstance().createLogin("Pepe", "asdf", "Pepe", 1)) {
            users.add(new Person("Pepe", "Pepe"));
            users.get(2).setTitle("Frog");
        }
        if (SQLInterface.getInstance().createLogin("WillyWonka", "asdf", "WillyWonka", 1)) {
            users.add(new Person("WillyWonka", "WillyWonka"));
            users.get(3).setTitle("TellMeMore");
        }
    }
    @After
    public void tearDown() {
        for (Person user: users) {
            SQLInterface.getInstance().deleteUser(user.getName());
        }
    }
    @Test
    public void updateUser() {
        for (Person user : users) {
            SQLInterface.getInstance().updateUser(user);
        }
        assertTrue(SQLInterface.getInstance().authenticate("Harambe", "asdf").getTitle().equals("Gorilla"));
        assertTrue(SQLInterface.getInstance().authenticate("ForeverAlone", "asdf").getTitle().equals("Sad"));
        assertTrue(SQLInterface.getInstance().authenticate("Pepe", "asdf").getTitle().equals("Frog"));
        assertTrue(SQLInterface.getInstance().authenticate("WillyWonka", "asdf").getTitle().equals("TellMeMore"));
    }
}
