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


    /** The label of the vehicle */
    private String label;

    private String type;

    /** Instance of {@link VehicleStatus} for the status of the vehicle */
    private VehicleStatus status;

    public Vehicle(ila.fr.codisintervention.models.messages.Vehicle vehicle) {
        label = vehicle.getLabel();
        type = vehicle.getType();
        status = VehicleStatus.getStatusEnumFromString(vehicle.getStatus());
    }

    public void load(Vehicle vehicle){
        label = vehicle.getLabel();
        type = vehicle.getType();
        status = vehicle.getStatus();
    }
}
