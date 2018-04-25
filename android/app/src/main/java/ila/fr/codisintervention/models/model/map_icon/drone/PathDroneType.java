package ila.fr.codisintervention.models.model.map_icon.drone;

/**
 * Type of path a drone may follow.
 */
public enum PathDroneType {
    /** Segment path type, the drone follows the segment and goes back endlessly */
    SEGMENT,
    /** Cycle path type, the drone travels around the zone */
    CYCLE,
    /** Grid path type, the drone travels the whole zone */
    GRID
}
