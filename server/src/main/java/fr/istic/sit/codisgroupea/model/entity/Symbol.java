package fr.istic.sit.codisgroupea.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Graphical symbol regardless of its position.
 */
@Entity
@Data
@NoArgsConstructor
public class Symbol {

    /** The id of the symbol */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    /** Instance of {@link Color} for the color of the symbol */
    @NotNull
    @Enumerated
    private Color color;

    /** Instance of {@link Shape} for the shape of the symbol */
    @NotNull
    @Enumerated
    private Shape shape;

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
}
