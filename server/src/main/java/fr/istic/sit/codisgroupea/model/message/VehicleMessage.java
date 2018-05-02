package fr.istic.sit.codisgroupea.model.message;

import fr.istic.sit.codisgroupea.constraints.IsVehicleStatus;
import fr.istic.sit.codisgroupea.constraints.groups.Message;
import fr.istic.sit.codisgroupea.model.entity.UnitVehicle;
import fr.istic.sit.codisgroupea.model.entity.Vehicle;
import fr.istic.sit.codisgroupea.model.entity.VehicleStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Represent a vehicule send to the client
 */
@Getter
@Setter
public class VehicleMessage {
    /**
     * vehicle label
     */
    @NotEmpty(groups = Message.UnitMessageReception.class)
    @NotNull(groups = Message.UnitMessageReception.class)
    private String label;
    /**
     * Type vehicle
     */
    @NotEmpty(groups = Message.UnitMessageReception.class)
    @NotNull(groups = Message.UnitMessageReception.class)
    private String type;
    /**
     * status vehicle from the class {@link VehicleStatus}
     */
    @NotNull(groups = Message.UnitMessageReception.class)
    @NotEmpty(groups = Message.UnitMessageReception.class)
    @IsVehicleStatus(groups = Message.UnitMessageReception.class, message = "Vehicle Status on VehicleMessage received is not as expected")
    private String status;

    /**
     * Default Constructor.
     */
    public VehicleMessage(){}

    /**
     * Instantiates a new Vehicle message.
     *
     * @param vehicle Vehicule to send in a message
     */
    public VehicleMessage(Vehicle vehicle){
        label = vehicle.getLabel();
        type = vehicle.getType().getName();
        status = vehicle.getStatus().name();
    }

    /**
     * Instantiates a new Vehicle message from a UnitVehicle
     * @param unitVehicle the unit vehicle
     */
    public VehicleMessage(UnitVehicle unitVehicle){
        if(unitVehicle.getAssignedVehicle() != null)
            label = unitVehicle.getAssignedVehicle().getLabel();
        type = unitVehicle.getType().getName();
        status = unitVehicle.getStatus().name();
    }
}
