package fr.istic.sit.codisgroupea.model.message.intervention;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * The Id message.
 */
public class IdMessage {
    /**
     * The Id.
     */
    @NotEmpty
    @NotNull
    public int id;

    /**
     * Instantiates a new Id message.
     *
     * @param id the id
     */
    public IdMessage(int id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Instantiates a new Id message.
     */
    public IdMessage(){

    }
}
