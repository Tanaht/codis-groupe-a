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

    /** The point where the photo was taken */
    @NotNull
    private Integer point;

    /**
     * Instantiates a new Photo.
     *
     * @param uri          the uri
     * @param coordinates  the coordinates
     * @param date         the date
     * @param intervention the intervention
     * @param point        the point
     */
    public Photo(String uri, Position coordinates, Timestamp date, Intervention intervention, Integer point) {
        this.uri = uri;
        this.coordinates = coordinates;
        this.date = date;
        this.intervention = intervention;
        this.point = point;
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
     * Gets uri.
     *
     * @return the uri
     */
    @NotNull
    public String getUri() {
        return uri;
    }

    /**
     * Sets uri.
     *
     * @param uri the uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * Gets coordinates.
     *
     * @return the coordinates
     */
    @NotNull
    @OneToOne
    public Position getCoordinates() {
        return coordinates;
    }

    /**
     * Sets coordinates.
     *
     * @param coordinates the coordinates
     */
    public void setCoordinates(Position coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    @NotNull
    public Timestamp getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(Timestamp date) {
        this.date = date;
    }

    /**
     * Gets intervention.
     *
     * @return the intervention
     */
    @NotNull
    @ManyToOne
    public Intervention getIntervention() {
        return intervention;
    }

    /**
     * Sets intervention.
     *
     * @param intervention the intervention
     */
    public void setIntervention(Intervention intervention) {
        this.intervention = intervention;
    }
}
