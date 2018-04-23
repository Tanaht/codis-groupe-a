package ila.fr.codisintervention.models.model;

import java.util.List;

/**
 * Representation of the drone's path.
 */
public class Path {

    /** The id of the path */
    private Integer id;

    /** The altitude of the path */
    private double altitude;

    /** List of {@link Position} for all the point in the path */
    private List<Position> points;

    /** Instance of {@link PathType} for the type of the path */
    private PathType type;

    /**
     * Instantiates a new Path.
     */
    public Path() {
        this.altitude = 30.0;
    }

    /**
     * Instantiates a new Path.
     *
     * @param altitude the altitude
     * @param points   the points
     * @param type     the type
     */
    public Path(double altitude, List<Position> points, PathType type) {
        this.altitude = altitude;
        this.points = points;
        this.type = type;
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
     * Gets altitude.
     *
     * @return the altitude
     */
    public double getAltitude() {
        return altitude;
    }

    /**
     * Sets altitude.
     *
     * @param altitude the altitude
     */
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    /**
     * Gets points.
     *
     * @return the points
     */
    public List<Position> getPoints() {
        return points;
    }

    /**
     * Sets points.
     *
     * @param points the points
     */
    public void setPoints(List<Position> points) {
        this.points = points;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public PathType getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(PathType type) {
        this.type = type;
    }
}
