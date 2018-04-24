package ila.fr.codisintervention.models.model.map_icon.drone;

import java.util.List;

import ila.fr.codisintervention.models.model.Position;
import lombok.Getter;
import lombok.Setter;

/**
 * Representation of the drone's path.
 */
@Getter
@Setter
public class PathDrone {

    /** The id of the path */
    private Integer id;

    /** The altitude of the path */
    private double altitude;

    /** List of {@link Position} for all the point in the path */
    private List<Position> points;

    /** Instance of {@link PathDroneType} for the type of the path */
    private PathDroneType type;

}
