package fr.istic.sit.codisgroupea.model.message.Send;

import fr.istic.sit.codisgroupea.model.entity.Unit;
import fr.istic.sit.codisgroupea.model.entity.Vehicle;

public class DemandesCreatedMessage {

    private int id;
    private VehicleDemandesCreatedMessage vehicle;

    public DemandesCreatedMessage(Unit unit){
        id = unit.getId();
        vehicle = new VehicleDemandesCreatedMessage(unit.getVehicle());
    }

    public static class VehicleDemandesCreatedMessage{
        private String type;
        private String status;

        public VehicleDemandesCreatedMessage(Vehicle vehicle){
            type = vehicle.getType().getName();
            status = vehicle.getStatus().toString();
        }
    }
}
