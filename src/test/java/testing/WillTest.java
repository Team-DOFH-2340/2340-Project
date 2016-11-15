package test.java.testing;

import java.util.ArrayList;
import java.util.List;

import main.java.controller.SQLInterface;
import main.java.model.HomeAddress;
import main.java.model.Person;
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
    private ArrayList<Person> person_list = new ArrayList<Person>();
    @Before
    public void setUp() {
        SQLInterface.init();
        SQLInterface.clean();

//        List<Person> person_list = new ArrayList<Person>();

        SQLInterface.createLogin("Harambe", "asdf", "Harambe", 1);
        SQLInterface.createLogin("ForeverAlone", "asdf", "ForeverAlone", 1);
        SQLInterface.createLogin("Pepe", "asdf", "Pepe", 1);
        SQLInterface.createLogin("WillyWonka", "asdf", "WillyWonka", 1);
    }
    @After
    public void tearDown() {
        SQLInterface.clean();
    }
    @Test
    public void updateUser() {
        Person p = new Person();
        p.setHomeAddress(new HomeAddress());
        p.setUsername("Harambe");
        p.setName("Harambe");
        p.setTitle("Gorilla");
        SQLInterface.updateUser(p);
        p.setUsername("ForeverAlone");
        p.setName("ForeverAlone");
        p.setTitle("Sad");
        SQLInterface.updateUser(p);
        p.setUsername("Pepe");
        p.setName("Pepe");
        p.setTitle("Frog");
        SQLInterface.updateUser(p);
        p.setUsername("WillyWonka");
        p.setName("WillyWonka");
        p.setTitle("TellMeMore");
        SQLInterface.updateUser(p);
        assertTrue(SQLInterface.authenticate("Harambe", "asdf").getTitle().equals("Gorilla"));
        assertTrue(SQLInterface.authenticate("ForeverAlone", "asdf").getTitle().equals("Sad"));
        assertTrue(SQLInterface.authenticate("Pepe", "asdf").getTitle().equals("Frog"));
        assertTrue(SQLInterface.authenticate("WillyWonka", "asdf").getTitle().equals("TellMeMore"));
    }
}