package fr.istic.sit.codisgroupea.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The symbol payload.
 */
@Entity
@Data
@NoArgsConstructor
public class Payload {
    /** The id of the payload */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** The identifier of the payload */
    private String identifier;

    /** The details of the payload */
    private String details;

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
}
