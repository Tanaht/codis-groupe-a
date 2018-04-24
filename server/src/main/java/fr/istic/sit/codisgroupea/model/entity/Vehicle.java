package fr.istic.sit.codisgroupea.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * This class is the vehicle entity. It represents an actual vehicle, not a tactical unit.
 *
 * @see Unit
 */
@Entity
public class Vehicle {

    /** The id of the vehicle */
    private Integer id;

    /** The label of the vehicle */
    private String label;

    /**
     * Attribute that associate a vehicle with a Unit
     * In this class we can gain the current status of the vehicle in the context of the unit
     */
    private UnitVehicle unitVehicle;


    /** Instance of the {@link VehicleType} for the type of the vehicle */
    private VehicleType type;

    /**
     * Default constructor.
     */
    public Vehicle() {
    }

    /**
     * Constructor by value.
     *
     * @param label the vehicle unique label
     * @param type the vehicle type
     */
    public Vehicle(String label, VehicleType type) {
        this.label = label;
        this.type = type;
    }

    /**
     * Getter of ID.
     *
     * @return the ID
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    /**
     * Setter of ID.
     *
     * @param id the ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter of the unique label.
     *
     * @return the label
     */
    @NotNull
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Getter of the vehicle type.
     *
     * @return the vehicle type
     */
    @NotNull
    @ManyToOne
    public VehicleType getType() {
        return type;
    }

    /**
     * Setter of the vehicle type.
     *
     * @param type the vehicle type
     */
    public void setType(VehicleType type) {
        this.type = type;
    }

    /**
     * Getter of the vehicle status.
     * A vehicle not linked to a Unit has always the state AVAILABLE
     * @return the vehicle status
     */
    @Transient
    public VehicleStatus getStatus() {
        return unitVehicle == null ? VehicleStatus.AVAILABLE : unitVehicle.getStatus();
    }

    /**
     * @return the unit vehicle if present
     */
    @OneToOne
    public UnitVehicle getUnitVehicle() {
        return unitVehicle;
    }

    public void setUnitVehicle(UnitVehicle unitVehicle) {
        this.unitVehicle = unitVehicle;
    }
}
