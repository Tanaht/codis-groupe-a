package fr.istic.sit.codisgroupea.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * The type User.
 */
@Entity
@Data
public class User {

    /** The id of the user */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    /** The username of the user */
    @NotNull
    private String username;

    /** The password of the user */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    private String password;

    /** Instance of {@link Role} for the role of the user */
    @OneToOne
    private Role role;
}
