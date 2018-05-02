package fr.istic.sit.codisgroupea.model.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * The type Role.
 */
@Entity
@Data
public class Role implements GrantedAuthority {
    /** The id of the role */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    /** The label of the role */
    @NotNull
    private String label;

    /**
     * Use with spring security, transient because it's the same data in label
     * @return name of the role
     */
    @Transient
    @Override
    public String getAuthority() {
        return label;
    }
}
