package fr.istic.sit.codisgroupea.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Representation of an intervention.
 */
@Entity
public class Intervention {
    private int id;
    private Position position;
    private String address;
    private SinisterCode sinisterCode;

    /**
     * Default constructor.
     */
    public Intervention() {
    }

    /**
     * Constructor by value.
     *
     * @param position the location of the intervention
     * @param address the address of the intervention
     * @param sinisterCode the sinister code
     */
    public Intervention(Position position, String address, SinisterCode sinisterCode) {
        this.position = position;
        this.address = address;
        this.sinisterCode = sinisterCode;
    }

    /**
     * Getter of ID.
     *
     * @return the ID
     */
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    /**
     * Setter of ID.
     *
     * @param id the ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter of the position.
     *
     * @return the position
     */
    @NotNull
    public Position getPosition() {
        return position;
    }

    /**
     * Setter of the position.
     *
     * @param position the position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Getter of the address.
     *
     * @return the address
     */
    @NotNull
    public String getAddress() {
        return address;
    }

    /**
     * Setter of address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter of the sinister code.
     *
     * @return the sinister code
     */
    @NotNull
    public SinisterCode getSinisterCode() {
        return sinisterCode;
    }

    /**
     * Setter of the sinister code.
     *
     * @param sinisterCode the sinister code
     */
    public void setSinisterCode(SinisterCode sinisterCode) {
        this.sinisterCode = sinisterCode;
    }
}
