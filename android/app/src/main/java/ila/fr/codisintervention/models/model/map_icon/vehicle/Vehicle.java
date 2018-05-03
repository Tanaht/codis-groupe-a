package ila.fr.codisintervention.models.model.map_icon.vehicle;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is the vehicle entity. It represents an actual vehicle, not a tactical unit.
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class Vehicle {


    /** The label of the vehicle */
    private String label;

    private String type;

    private boolean selected;

    /** Instance of {@link VehicleStatus} for the status of the vehicle */
    private VehicleStatus status;

    public Vehicle( String label, String type ){
        this.label = label;
        this.type = type;
    }

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
