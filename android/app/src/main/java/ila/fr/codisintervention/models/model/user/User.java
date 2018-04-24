package ila.fr.codisintervention.models.model.user;

import lombok.Getter;
import lombok.Setter;

/**
 * The type User.
 */
@Getter
@Setter
public class User {

    /** The username of the user */
    private String username;

    private String role;


<<<<<<< Updated upstream
    public User(ila.fr.codisintervention.models.messages.User user){
        username = user.getUsername();
        role = user.getRole();
    }

=======
    public User(ila.fr.codisintervention.models.messages.User userMessage){
        this.username = userMessage.getUsername();
        this.role = userMessage.getRole();
    }
>>>>>>> Stashed changes
}
