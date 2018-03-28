package fr.istic.sit.codisgroupea.model.message;

import fr.istic.sit.codisgroupea.model.entity.Vehicle;
import fr.istic.sit.codisgroupea.model.entity.VehicleStatus;

/**
 * Represent a vehicule send to the client
 */
public class VehicleMessage {
    public VehicleMessage(){
        
    }
    /**
     * vehicle label
     */
    private String label;
    /**
     * Type vehicle
     */
    private String type;
    /**
     * status vehicle from the class {@link VehicleStatus}
     */
    private VehicleStatus status;

    /**
     *
     * @param vehicle Vehicule to send in a message
     */
    public VehicleMessage(Vehicle vehicle){
        label = vehicle.getLabel();
        type = vehicle.getType().getName();
        status = vehicle.getStatus();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }
}
