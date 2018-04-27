package fr.istic.sit.codisgroupea.model.message.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * Represent a Payload
 */
@Getter
@Setter
public class Payload {

    /**
     * Instantiates a new Payload.
     */
    public Payload() {
    }

    /**
     * The Identifier.
     */
    public String identifier;
    /**
     * The Details.
     */
    public String details;

    public Payload(String identifier, String details) {
        this.identifier = identifier;
        this.details = details;
    }
}
