package fr.istic.sit.codisgroupea.model.message.Receive;

import fr.istic.sit.codisgroupea.model.message.intervention.IdMessage;

public class ConfirmDemandVehicleMessage {

    /** Instance of {@link fr.istic.sit.codisgroupea.model.message.intervention.IdMessage} for the id of the vehicle */
    private IdMessage vehicle;

    /**
     * Getter of the vehicle
     * @return the vehicle
     */
    public IdMessage getVehicle() {
        return vehicle;
    }

    /**
     * Setter of the vehicle
     * @param vehicle the new id of the vehicle
     */
    public void setVehicle(IdMessage vehicle) {
        this.vehicle = vehicle;
    }
}
