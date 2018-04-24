package fr.istic.sit.codisgroupea.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity used to perform the binding between a Unit and it's Vehicle
 * This class Manage to properties, the VehicleType and the VehicleStatus
 */
@Entity
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
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets unit.
     *
     * @return the unit
     */
    public Unit getUnit() {
        return unit;
    }

    /**
     * Sets unit.
     *
     * @param unit the unit
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /**
     * Gets assigned vehicle.
     *
     * @return the assigned vehicle
     */
    public Vehicle getAssignedVehicle() {
        return assignedVehicle;
    }

    /**
     * Sets assigned vehicle.
     *
     * @param assignedVehicle the assigned vehicle
     */
    public void setAssignedVehicle(Vehicle assignedVehicle) {
        this.assignedVehicle = assignedVehicle;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public VehicleType getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(VehicleType type) {
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
