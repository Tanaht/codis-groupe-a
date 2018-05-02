package fr.istic.sit.codisgroupea.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity used to perform the binding between a Unit and it's Vehicle
 * This class Manage to properties, the VehicleType and the IsVehicleStatus
 */
@Entity
@Data
public class UnitVehicle {

    /** The id of the unit */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    /**
     * A unit is always associated with a UnitVehicle instance
     */
    @NotNull
    @OneToOne
    private Unit unit;

    /**
     * The vehicle assigned to the unit
     */
    @OneToOne
    private Vehicle assignedVehicle;

    /**
     * type of the vehicle requested in the unit
     */
    @ManyToOne
    @NotNull
    private VehicleType type;

    /**
     * status of the unit (that is also the status of the vehicle if the vehicle is assigned to the unit)
     */
    @Enumerated
    @NotNull
    private VehicleStatus status;

    /**
     * Sets assigned vehicle.
     *
     * @param assignedVehicle the assigned vehicle
     */
    public void setAssignedVehicle(Vehicle assignedVehicle) {
        this.assignedVehicle = assignedVehicle;
        this.assignedVehicle.setUnitVehicle(this);
    }
}
