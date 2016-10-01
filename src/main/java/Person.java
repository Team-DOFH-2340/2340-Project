import java.util.List;

/**
 * Interface that defines the properties all users of the app have.
 * Created by Hayden on 10/1/2016.
 */
public interface Person {
    List<Privilege> getPrivileges();
    String getName();
    String getPassword();
}
