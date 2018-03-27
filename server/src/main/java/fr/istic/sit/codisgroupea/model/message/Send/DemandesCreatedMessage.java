package fr.istic.sit.codisgroupea.model.message.Send;

import fr.istic.sit.codisgroupea.model.entity.Unit;
import fr.istic.sit.codisgroupea.model.entity.Vehicle;

public class DemandesCreatedMessage {

    /** The id of the demand */
    private int id;

    /** Instance of {@link VehicleDemandesCreatedMessage} for the vehicle */
    private VehicleDemandesCreatedMessage vehicle;

    /**
     * Constructor of the class {@link DemandesCreatedMessage}
     *
     * @param unit The unit
     */
    public DemandesCreatedMessage(Unit unit){
        id = unit.getId();
        vehicle = new VehicleDemandesCreatedMessage(unit.getVehicle());
    }

    public static class VehicleDemandesCreatedMessage{

        /** The type of the vehicle */
        private String type;

        /** The status of the vehicle */
        private String status;

        /**
         * Constructor of the class {@link VehicleDemandesCreatedMessage}
         *
         * @param vehicle The vehicle concerned
         */
        public VehicleDemandesCreatedMessage(Vehicle vehicle){
            type = vehicle.getType().getName();
            status = vehicle.getStatus().toString();
        }
    }
}
