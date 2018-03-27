package fr.istic.sit.codisgroupea.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Representation of an intervention.
 */
@Entity
public class Intervention {
    private int id;
    private long date;
    private Position position;
    private String address;
    private SinisterCode sinisterCode;
    private boolean opened;

    /**
     * Default constructor.
     */
    public Intervention() {
    }

    /**
     * Constructor by value.
     *
     * @param date         the date of the intervention
     * @param position     the location of the intervention
     * @param address      the address of the intervention
     * @param sinisterCode the sinister code
     * @param opened       is the intervention opened
     */
    public Intervention(long date, Position position, String address, SinisterCode sinisterCode, boolean opened) {
        this.date = date;
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
     * Getter of the date.
     *
     * @return the date
     */
    @NotNull
    public long getDate() {
        return date;
    }

    /**
     * Setter of the date.
     *
     * @param date the date
     */
    public void setDate(long date) {
        this.date = date;
    }

    /**
     * Getter of the position.
     *
     * @return the position
     */
    @NotNull
    @OneToOne
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
    @ManyToOne
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

    /**
     * Is the intervention opened.
     *
     * @return the boolean
     */
    public boolean isOpened() {
        return opened;
    }

    /**
     * Sets opened.
     *
     * @param opened the opened
     */
    public void setOpened(boolean opened) {
        this.opened = opened;
    }
}
