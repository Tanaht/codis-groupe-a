package ila.fr.codisintervention.models.model.mapIcon.vehicle;

/**
 * This class is the vehicle entity. It represents an actual vehicle, not a tactical unit.
 *
 */
public class Vehicle {

    /** The id of the vehicle */
    private Integer id;

    /** The label of the vehicle */
    private String label;

    /** Instance of the {@link VehicleType} for the type of the vehicle */
    private VehicleType type;

    /** Instance of {@link VehicleStatus} for the status of the vehicle */
    private VehicleStatus status;
}
