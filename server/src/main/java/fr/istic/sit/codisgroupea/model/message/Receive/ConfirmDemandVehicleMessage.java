package fr.istic.sit.codisgroupea.model.message.Receive;

import fr.istic.sit.codisgroupea.model.message.intervention.IdMessage;

public class ConfirmDemandVehicleMessage {
    private IdMessage vehicle;

    public IdMessage getVehicle() {
        return vehicle;
    }

    public void setVehicle(IdMessage vehicle) {
        this.vehicle = vehicle;
    }
}
