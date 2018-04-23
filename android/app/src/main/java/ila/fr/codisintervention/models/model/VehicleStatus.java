package ila.fr.codisintervention.models.model;

/**
 * Status of a vehicle.
 */
public enum VehicleStatus {
    /** Requested for an intervention */
    REQUESTED,
    /** Already used in an intervention */
    USED,
    /** Available for an intervention */
    AVAILABLE,
    /** In intervention, waiting for orders */
    CRM
}
