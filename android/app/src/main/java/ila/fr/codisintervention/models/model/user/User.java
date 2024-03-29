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

    public User(ila.fr.codisintervention.models.messages.User userMessage){
        this.username = userMessage.getUsername();
        this.role = userMessage.getRole();
    }

    public boolean isCodisUser() {
        return role.equals("ROLE_CODIS_USER");
    }

    public boolean isSimpleUser() {
        return role.equals("ROLE_SIMPLE_USER");
    }
}
