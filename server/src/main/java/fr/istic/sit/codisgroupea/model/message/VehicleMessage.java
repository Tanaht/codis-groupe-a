package fr.istic.sit.codisgroupea.model.message;

import fr.istic.sit.codisgroupea.model.entity.Vehicle;
import fr.istic.sit.codisgroupea.model.entity.VehicleStatus;

/**
 * Represent a vehicule send to the client
 */
public class VehicleMessage {
    /**
     * Instantiates a new Vehicle message.
     */
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
     * Instantiates a new Vehicle message.
     *
     * @param vehicle Vehicule to send in a message
     */
    public VehicleMessage(Vehicle vehicle){
        label = vehicle.getLabel();
        type = vehicle.getType().getName();
        status = vehicle.getStatus();
    }

    /**
     * Gets label.
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets label.
     *
     * @param label the label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public VehicleStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(VehicleStatus status) {
        this.status = status;
    }
}
