package fr.istic.sit.codisgroupea.model.entity;

import fr.istic.sit.codisgroupea.model.message.utils.Location;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Represents coordinates in terms of latitude, longitude and altitude.
 */
@Entity
public class Position {

    /** The id of the position */
    private Integer id;

    /** The latitude of the position */
    private double latitude;

    /** The longitude of the position */
    private double longitude;

    /**
     * Default constructor.
     */
    public Position() {
    }

    /**
     * Constructor by values
     *
     * @param latitude the latitude
     * @param longitude the longitude
     */
    public Position(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Position Constructor from a Location Message
     * @param location
     */
    public Position(@NotNull Location location) {
        this.setLatitude(location.getLat());
        this.setLongitude(location.getLng());
    }

    /**
     * Getter of ID.
     *
     * @return the ID
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    /**
     * Setter of ID.
     *
     * @param id the ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter of the latitude.
     *
     * @return the latitude
     */
    @NotNull
    public double getLatitude() {
        return latitude;
    }

    /**
     * Setter of the latitude.
     *
     * @param latitude the latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Getter of the longitude.
     *
     * @return the longitude
     */
    @NotNull
    public double getLongitude() {
        return longitude;
    }

    /**
     * Setter of the longitude.
     *
     * @param longitude the longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
