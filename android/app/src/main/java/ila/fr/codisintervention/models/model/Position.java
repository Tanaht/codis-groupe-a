package ila.fr.codisintervention.models.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents coordinates in terms of latitude, longitude and altitude.
 */
@Getter
@Setter
public class Position {

    /** The latitude of the position */
    private double latitude;

    /** The longitude of the position */
    private double longitude;


    public Position(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
