package fr.istic.sit.codisgroupea.model.message.receive;

import fr.istic.sit.codisgroupea.model.message.utils.LabelMessage;

import javax.validation.Valid;

public class ConfirmDemandVehicleMessage {

    /** Instance of {@link fr.istic.sit.codisgroupea.model.message.intervention.IdMessage} for the id of the vehicle */
    @Valid
    private LabelMessage vehicle;

    /**
     * Getter of the vehicle
     * @return the vehicle
     */
    public LabelMessage getVehicle() {
        return vehicle;
    }

    /**
     * Setter of the vehicle
     * @param vehicle the new id of the vehicle
     */
    public void setVehicle(LabelMessage vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * Empty Constructor
     */
    public ConfirmDemandVehicleMessage () {}
}
