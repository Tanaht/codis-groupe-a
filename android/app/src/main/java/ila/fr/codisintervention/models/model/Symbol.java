package fr.istic.sit.codisgroupea.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Graphical symbol regardless of its position.
 */
@Entity
public class Symbol {

    /** The id of the symbol */
    private Integer id;

    /** Instance of {@link Color} for the color of the symbol */
    private Color color;

    /** Instance of {@link Shape} for the shape of the symbol */
    private Shape shape;

    /**
     * Instantiates a new Symbol.
     */
    public Symbol() {
    }

    /**
     * Instantiates a new Symbol.
     *
     * @param color    the color
     * @param shape    the shape
     */
    public Symbol(Color color, Shape shape) {
        this.color = color;
        this.shape = shape;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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
}
