package ila.fr.codisintervention.models.model;

/**
 * The type User.
 */
public class User {

    /** The id of the user */
    private Long id;

    /** The username of the user */
    private String username;

    /** The password of the user */
    private transient String password;

    /** Instance of {@link Role} for the role of the user */
    private Role role;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets roles.
     *
     * @return the roles
     */
    public Role getRoles() {
        return role;
    }

    /**
     * Sets roles.
     *
     * @param role the role
     */
    public void setRoles(Role role) {
        this.role = role;
    }
}
