package ila.fr.codisintervention.models.model.user;

/**
 * The type User.
 */
public class User {

    /** The username of the user */
    private String username;

    private String role;


    public User(ila.fr.codisintervention.models.messages.User user){
        username = user.getUsername();
        role = user.getRole();
    }

}
