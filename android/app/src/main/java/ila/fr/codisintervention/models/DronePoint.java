package ila.fr.codisintervention.models;

/**
 * Created by aminesoumiaa on 25/04/18.
 */

public class DronePoint {
    /**
     * The id of the point
     */
    private int id = 0;
    /**
     * The latitude of the gps coordinate
     */
    private double lat;
    /**
     * The longitude of the gps coordinate
     */
    private double lon;

    /**
     * Instantiate new DronePoint
     * @param num
     * @param lat
     * @param lon
     */
    public DronePoint(int num, double lat, double lon) {
        this.id = num;
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * Gets Point Id
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets Point Id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets Point latitude
     * @return the latitude
     */
    public double getLat() {
        return lat;
    }

    /**
     * Sets Point latitude
     * @param lat
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * Gets Point longitude
     * @return the longitude
     */
    public double getLon() {
        return lon;
    }

    /**
     * Sets Point longitude
     * @param lon
     */
    public void setLon(double lon) {
        this.lon = lon;
    }
}
