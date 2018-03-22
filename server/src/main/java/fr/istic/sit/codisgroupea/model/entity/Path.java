package fr.istic.sit.codisgroupea.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Representation of the drone's path.
 */
@Entity
public class Path {
    private int id;
    private double altitude;
    private List<Position> points;
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
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets altitude.
     *
     * @return the altitude
     */
    @NotNull
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
    @OneToMany
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
    @Enumerated
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
