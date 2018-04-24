package ila.fr.codisintervention.models.model.map_icon.vehicle;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is the vehicle entity. It represents an actual vehicle, not a tactical unit.
 *
 */
@Getter
@Setter
public class Vehicle {

    /** The id of the vehicle */
    private Integer id;

    /** The label of the vehicle */
    private String label;

    private String type;

    /** Instance of {@link VehicleStatus} for the status of the vehicle */
    private VehicleStatus status;
}
