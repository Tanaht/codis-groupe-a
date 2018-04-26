package ila.fr.codisintervention.models.model.map_icon.drone;

import java.util.List;

import ila.fr.codisintervention.models.messages.Location;
import ila.fr.codisintervention.models.model.Position;
import lombok.Getter;
import lombok.Setter;

/**
 * Representation of the drone's path.
 */
@Getter
@Setter
public class PathDrone {

    /** The altitude of the path */
    private double altitude;

    /** List of {@link Position} for all the point in the path */
    private List<Location> points;

    /** Instance of {@link PathDroneType} for the type of the path */
    private PathDroneType type;

    /**
     * Constructor for PathDrone that takes an instance of {@link ila.fr.codisintervention.models.messages.PathDrone}
     * @param pathDrone
     */
    public PathDrone(ila.fr.codisintervention.models.messages.PathDrone pathDrone) {
        this.setAltitude(pathDrone.getAltitude());
        this.setType(PathDroneType.valueOf(pathDrone.getType()));
        this.setPoints(pathDrone.getPath());
    }
}
