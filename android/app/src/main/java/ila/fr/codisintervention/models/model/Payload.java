package fr.istic.sit.codisgroupea.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The symbol payload.
 */
@Entity
public class Payload {

    /** The id of the payload */
    private Integer id;

    /** The identifier of the payload */
    private String identifier;

    /** The details of the payload */
    private String details;

    /**
     * Instantiates a new Payload.
     */
    public Payload() {
    }

    /**
     * Instantiates a new Payload.
     *
     * @param identifier the identifier
     * @param details    the details
     */
    public Payload(String identifier, String details) {
        this.identifier = identifier;
        this.details = details;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets identifier.
     *
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets identifier.
     *
     * @param identifier the identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Gets details.
     *
     * @return the details
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets details.
     *
     * @param details the details
     */
    public void setDetails(String details) {
        this.details = details;
    }
}
