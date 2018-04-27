package fr.istic.sit.codisgroupea.model.message.utils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Represent a GPS coordinate
 */
public class Location {

    /** Latitude of the location. */
    @NotNull
    @Min(-90)
    @Max(90)
    private double lat;

    /** Longitude of the location. */
    @NotNull
    @Min(-180)
    @Max(180)
    private double lng;

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    /**
     * Empty Constructor
     */
    public Location () {}

    /**
     * @param lat the latitude.
     * @param lng the longitude.
     */
    public Location (double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
