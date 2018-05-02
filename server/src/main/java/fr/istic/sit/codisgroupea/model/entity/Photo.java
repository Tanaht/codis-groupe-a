package fr.istic.sit.codisgroupea.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Photo informations stored in the database.
 */
@Entity
@Data
@NoArgsConstructor
public class Photo {
    /** The id of the photo */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    /** The url of the photo */
    @NotNull
    private String uri;

    /** Instance of {@link Position} for the location of the photo */
    @NotNull
    @OneToOne
    private Position coordinates;

    /** Instance of {@link Timestamp} for the date of the photo */
    @NotNull
    private Timestamp date;

    /** Instance of {@link Intervention} for the intervention of the photo */
    @NotNull
    @ManyToOne
    private Intervention intervention;

    /**
     * Instantiates a new Photo.
     *
     * @param uri          the uri
     * @param coordinates  the coordinates
     * @param date         the date
     * @param intervention the intervention
     */
    public Photo(String uri, Position coordinates, Timestamp date, Intervention intervention) {
        this.uri = uri;
        this.coordinates = coordinates;
        this.date = date;
        this.intervention = intervention;
    }
}
