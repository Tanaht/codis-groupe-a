package fr.istic.sit.codisgroupea.model.entity;

import fr.istic.sit.codisgroupea.sig.SigEntry;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Graphical symbol on the SITAC.
 */
@Entity
public class Symbol implements SigEntry {
    private int id;
    private Position location;
    private Color color;
    private Shape shape;
    private String payload;

    /**
     * Instantiates a new Symbol.
     */
    public Symbol() {
    }

    /**
     * Instantiates a new Symbol.
     *
     * @param location the location
     * @param color    the color
     * @param shape    the shape
     * @param payload  the payload
     */
    public Symbol(Position location, Color color, Shape shape, String payload) {
        this.location = location;
        this.color = color;
        this.shape = shape;
        this.payload = payload;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    @Id
    @GeneratedValue
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
     * Gets location.
     *
     * @return the location
     */
    @OneToOne
    @NotNull
    public Position getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(Position location) {
        this.location = location;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    @NotNull
    @Enumerated
    public Color getColor() {
        return color;
    }

    /**
     * Sets color.
     *
     * @param color the color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Gets shape.
     *
     * @return the shape
     */
    @NotNull
    @Enumerated
    public Shape getShape() {
        return shape;
    }

    /**
     * Sets shape.
     *
     * @param shape the shape
     */
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    /**
     * Gets payload.
     *
     * @return the payload
     */
    public String getPayload() {
        return payload;
    }

    /**
     * Sets payload.
     *
     * @param payload the payload
     */
    public void setPayload(String payload) {
        this.payload = payload;
    }
}
