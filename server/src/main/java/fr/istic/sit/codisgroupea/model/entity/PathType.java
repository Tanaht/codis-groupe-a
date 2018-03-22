package fr.istic.sit.codisgroupea.model.entity;

/**
 * Type of path a drone may follow.
 */
public enum PathType {
    /** Segment path type, the drone follows the segment and goes back endlessly */
    SEGMENT,
    /** Cycle path type, the drone travels around the zone */
    CYCLE,
    /** Grid path type, the drone travels the whole zone */
    GRID
}
