package fr.istic.sit.codisgroupea.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * The type User.
 */
@Entity
public class User {
    private Long id;
    private String username;

    private transient String password;
    private Role role;

    /**
     * Gets id.
     *
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @NotNull
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
    @NotNull
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
    @OneToOne
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
