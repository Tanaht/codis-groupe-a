package ila.fr.codisintervention.models.model.mapIcon.drone;

import java.util.List;

import ila.fr.codisintervention.models.model.Position;

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

}
