package fr.istic.sit.codisgroupea.model.message.utils;

/**
 * Represent a GPS coordinate
 */
public class Location {

    /** Latitude of the location. */
    private double lat;

    /** Longitude of the location. */
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
