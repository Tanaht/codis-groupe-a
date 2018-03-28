package fr.istic.sit.codisgroupea.model.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * The type Role.
 */
@Entity
public class Role implements GrantedAuthority {

    /** The id of the role */
    private Long id;

    /** The label of the role */
    private String label;

    /**
     * Gets id.
     *
     * @return the id
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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
     * Gets label.
     *
     * @return the label
     */
    @NotNull
    public String getLabel() {
        return label;
    }

    /**
     * Sets label.
     *
     * @param label the label
     */
    public void setLabel(String label) {
        this.label = label;
    }

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
