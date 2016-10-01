package model;

/**
 * Class that defines a user.
 * Created by Hayden on 10/1/2016.
 */
public class Person {
    public UserType type;
    public String name;
    public String password;
    public String email;
    public HomeAddress homeAddress;
    public String title;

    public Person(UserType type, String name, String password) {
        this.type = type;
        this.name = name;
        this.password = password;
    }
}
