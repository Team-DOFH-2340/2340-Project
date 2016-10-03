package model;

/**
 * Class that defines a user.
 * Created by Hayden on 10/1/2016.
 */
public class Person {
    public UserType type;
    public String username;
    public String name;
    public String password;
    public String email;
    public HomeAddress homeAddress;
    public String title;

    public Person() {}

    public Person(String username, String password, UserType type)  {
        this.type = type;
        this.username = username;
        this.password = password;

        this.name = "";
        this.email = "";
        this.homeAddress = new HomeAddress();
        this.title = "Duelmaster";
    }
}
