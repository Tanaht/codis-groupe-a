package ila.fr.codisintervention.models.model;

import ila.fr.codisintervention.models.messages.Location;
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

    public Position(Location loc) {
        latitude = loc.getLat();
        longitude = loc.getLng();
    }

    public void load(Position pos){
        latitude = pos.getLatitude();
        longitude = pos.getLongitude();
    }
}
