package ila.fr.codisintervention.models.model;

import java.sql.Timestamp;

/**
 * Photo informations stored in the database.
 */
public class Photo {

    /** The id of the photo */
    private Integer id;

    /** The url of the photo */
    private String uri;

    /** Instance of {@link Position} for the location of the photo */
    private Position coordinates;

    /** Instance of {@link Timestamp} for the date of the photo */
    private Timestamp date;

    /** Instance of {@link InterventionModel} for the intervention of the photo */
    private InterventionModel intervention;

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
     * @param date         the date
     * @param intervention the intervention
     */
    public Photo(String uri, Position coordinates, Timestamp date, InterventionModel intervention) {
        this.uri = uri;
        this.coordinates = coordinates;
        this.date = date;
        this.intervention = intervention;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
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
    public InterventionModel getIntervention() {
        return intervention;
    }

    /**
     * Sets intervention.
     *
     * @param intervention the intervention
     */
    public void setIntervention(InterventionModel intervention) {
        this.intervention = intervention;
    }
}
