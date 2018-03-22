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
    private int id;
    private String label;
    private VehicleType type;
    private VehicleStatus status;

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
     * @param status the vehicle status
     */
    public Vehicle(String label, VehicleType type, VehicleStatus status) {
        this.label = label;
        this.type = type;
        this.status = status;
    }

    /**
     * Getter of ID.
     *
     * @return the ID
     */
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    /**
     * Setter of ID.
     *
     * @param id the ID
     */
    public void setId(int id) {
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
     *
     * @return the vehicle status
     */
    @Enumerated
    public VehicleStatus getStatus() {
        return status;
    }

    /**
     * Setter of the status.
     *
     * @param status the status
     */
    public void setStatus(VehicleStatus status) {
        this.status = status;
    }
}
