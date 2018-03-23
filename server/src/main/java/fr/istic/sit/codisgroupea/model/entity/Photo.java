package fr.istic.sit.codisgroupea.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Photo informations stored in the database.
 */
@Entity
public class Photo {
    private int id;
    private String uri;
    private Position coordinates;
    private Intervention intervention;

    /**
     * Instantiates a new Photo.
     */
    public Photo() {

    }

    /**
     * Instantiates a new Photo.
     *
     * @param uri          the uri
     * @param coordinates  the coordinates
     * @param intervention the intervention
     */
    public Photo(String uri, Position coordinates, Intervention intervention) {
        this.uri = uri;
        this.coordinates = coordinates;
        this.intervention = intervention;
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
