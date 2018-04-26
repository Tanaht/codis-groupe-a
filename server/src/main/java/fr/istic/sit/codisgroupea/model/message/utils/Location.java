package fr.istic.sit.codisgroupea.model.message.utils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Represent a GPS coordinate
 */
public class Location {

    /** Latitude of the location. */
    @NotNull
    @Pattern(regexp = "^(\\+|-)?(?:90(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,6})?))$")
    private double lat;

    /** Longitude of the location. */
    @NotNull
    @Pattern(regexp = "^(\\+|-)?(?:180(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,6})?))$")
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
