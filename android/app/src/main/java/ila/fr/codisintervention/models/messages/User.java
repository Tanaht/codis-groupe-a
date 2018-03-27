package ila.fr.codisintervention.models.messages;

/**
 * Created by tanaky on 27/03/18.
 */

public class User {
    public static final String ROLE_CODIS_USER = "ROLE_CODIS_USER";
    public static final String ROLE_SIMPLE_USER = "ROLE_SIMPLE_USER";
    private String username;
    private String role;

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public boolean isCodisUser() {
        return ROLE_CODIS_USER.equals(role);
    }

    public boolean isSimpleUser() {
        return ROLE_SIMPLE_USER.equals(role);
    }
}
