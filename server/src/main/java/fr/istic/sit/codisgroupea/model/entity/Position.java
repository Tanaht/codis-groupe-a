package fr.istic.sit.codisgroupea.model.entity;

import fr.istic.sit.codisgroupea.model.message.utils.Location;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Represents coordinates in terms of latitude, longitude and altitude.
 */
@Entity
@Data
@NoArgsConstructor
public class Position {

    /** The id of the position */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    /** The latitude of the position */
    @NotNull
    private double latitude;

    /** The longitude of the position */
    @NotNull
    private double longitude;

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
     * @param location the location
     */
    public Position(@NotNull Location location) {
        this.latitude = location.getLat();
        this.longitude = location.getLng();
    }
}
